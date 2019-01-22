namespace XCaseBase
{
    using System;
    using System.Collections.Generic;
    using System.Configuration;
    using System.IO;
    using System.Linq;
    using System.Reflection;
    using System.Text;
    using System.Xml;
    using log4net;

    /// <summary>
    /// This class provides general utility methods for all action classes.
    /// </summary>
    public class Helper
    {
        #region Logger Setup
        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILog log = LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        #endregion

        /// <summary>
        /// The _logger source.
        /// </summary>
        private static readonly ILog TestToolLogger = LogManager.GetLogger("TestToolLogger");

        /// <summary>
        /// This dictionary is used to retain a mapping between strings and objects.
        /// </summary>
        private static Dictionary<string, object> helperDictionary = new Dictionary<string, object>();

        /// <summary>
        /// The directory location where the helperDictionary is persisted.
        /// </summary>
        private static string outputDirectoryString = Environment.GetEnvironmentVariable("TEMP");

        /// <summary>
        /// The file location where the helperDictionary is persisted.
        /// </summary>
        private static string outputFileString = "Dictionary.txt";

        /// <summary>
        /// The field to use to track conditionals for test branching.
        /// </summary>
        private static Dictionary<string, bool> conditionalsDictionary = new Dictionary<string, bool>();

        /// <summary>
        /// Gets the test tool logger.
        /// </summary>
        /// <value>
        /// The logger.
        /// </value>
        public static ILog TestToolLog
        {
            get
            {
                return TestToolLogger;
            }
        }

        /// <summary>
        /// This method is used to retrieve the HelperDictionary.
        /// </summary>
        /// <returns>The HelperDictionary.</returns>
        public static Dictionary<string, object> GetHelperDictionary()
        {
            return helperDictionary;
        }

        /// <summary>
        /// This method saves the HelperDictionary to the file system.
        /// </summary>
        public static void SaveDictionary()
        {
            log.Debug("starting SaveDictionary()");
            string dictionaryPath = outputDirectoryString + Path.DirectorySeparatorChar + outputFileString;
            try
            {
                log.Debug("dictionaryPath is " + dictionaryPath);
                using (StreamWriter dictionaryStreamWriter = new StreamWriter(dictionaryPath))
                {
                    foreach (string key in helperDictionary.Keys)
                    {
                        dictionaryStreamWriter.WriteLine(key + ":" + helperDictionary[key]);
                    }
                }

                log.Debug("saved dictionary");
            }
            catch (Exception e)
            {
                string saveDictionaryException = "Could not save dictionary to " + dictionaryPath + " " + e.Message;
                log.Debug(saveDictionaryException);
            }
        }

        /// <summary>
        /// This method loads the HelperDictionary from the file system.
        /// </summary>
        public static void LoadDictionary()
        {
            log.Debug("starting LoadDictionary()");
            string dictionaryPath = outputDirectoryString + Path.DirectorySeparatorChar + outputFileString;
            try
            {
                log.Debug("dictionaryPath is " + dictionaryPath);
                log.Debug("dictionaryPath is " + dictionaryPath);
                if (!File.Exists(dictionaryPath))
                {
                    log.Debug("dictionary file does not exist");
                    FileStream dictionaryFileStream = File.Create(dictionaryPath);
                    dictionaryFileStream.Close();
                }
                else
                {
                    log.Debug("dictionary file does exist");
                }

                StreamReader dictionaryStreamReader = new StreamReader(dictionaryPath);
                log.Debug("created stream reader from " + dictionaryPath);
                using (dictionaryStreamReader)
                {
                    string dictionaryLine;
                    while ((dictionaryLine = dictionaryStreamReader.ReadLine()) != null)
                    {
                        string[] nameValueArray = dictionaryLine.Split(':');
                        helperDictionary[nameValueArray[0]] = nameValueArray[1];
                        log.Debug("retrieved dictionary entry " + nameValueArray[0]);
                    }
                }

                log.Debug("loaded dictionary");
            }
            catch (Exception e)
            {
                string loadDictionaryException = "Could not load dictionary from " + dictionaryPath + " " + e.Message;
                log.Debug(loadDictionaryException);
            }
        }

        /// <summary>
        /// This method is used to retrieve the conditionalsDictionary.
        /// </summary>
        /// <returns>The conditionalsrDictionary.</returns>
        public static Dictionary<string, bool> GetConditionalsDictionary()
        {
            return conditionalsDictionary;
        }

        /// <summary>
        /// This method checks the Conditionals dictionary for the value of a conditional.
        /// </summary>
        /// <param name="conditionalName">The conditionalName parameter.</param>
        /// <returns>true if the contional exists and its value is true, else false.</returns>
        public static bool GetConditional(string conditionalName)
        {
            log.Debug("starting GetConditional()");
            bool conditionalBool = false;
            conditionalsDictionary.TryGetValue(conditionalName, out conditionalBool);
            log.Info("conditionalBool is " + conditionalBool);
            return conditionalBool;
        }

        /// <summary>
        /// This method sets the value of a conditional in the Conditionals dictionary.
        /// </summary>
        /// <param name="conditionalName">The conditionalName parameter.</param>
        /// <param name="conditionalValue">The conditionalValue parameter.</param>
        public static void SetConditional(string conditionalName, bool conditionalValue)
        {
            log.Debug("starting SetConditional()");
            if (conditionalsDictionary.ContainsKey(conditionalName))
            {
                conditionalsDictionary[conditionalName] = conditionalValue;
            }
            else
            {
                conditionalsDictionary.Add(conditionalName, conditionalValue);
            }
        }

        /// <summary>
        /// This method is used to parse string value into nullable int.
        /// </summary>
        /// <param name="value">The value parameter.</param>
        /// <returns>null, if string value is null or empty, otherwise return int value.</returns>
        public static int? TryParseNullable(string value)
        {
            if (string.IsNullOrEmpty(value))
                return null;  
            int outValue;
            return int.TryParse(value, out outValue) ? (int?)outValue : null;
        }

        /// <summary>
        /// This method compares two unordered lists of ints.
        /// </summary>
        /// <param name="sourceIntList">The sourceIntList parameter.</param>
        /// <param name="targetIntList">The targetIntList parameter.</param>
        /// <returns>The CompareResult object.</returns>
        public static CompareResult CompareUnorderedIntList(List<int> sourceIntList, List<int> targetIntList)
        {
            log.Debug("starting CompareUnorderedIntList()");
            CompareResult compareResult = new CompareResult("Int lists match");
            if (sourceIntList == null || targetIntList == null)
            {
                string nullMessage = "Cannot compare null lists";
                log.Info(nullMessage);
                return new CompareResult(false, nullMessage);
            }

            log.Debug("lists are not null");
            IEnumerable<int> differencesIntEnumerable = sourceIntList.Where(x => !targetIntList.Any(y => x == y)).Union(targetIntList.Where(x => !sourceIntList.Any(y => x == y)));
            if (differencesIntEnumerable != null && differencesIntEnumerable.Any<int>())
            {
                IEnumerable<int> sourceIntsNotInTargetIntsEnumerable = sourceIntList.Where(x => !targetIntList.Any(y => x == y));
                int[] sourceIntsNotInTargetIntsEnumerableArray = sourceIntsNotInTargetIntsEnumerable.ToArray<int>();
                string sourceIntsNotInTargetInts = string.Join(",", Array.ConvertAll<int, string>(sourceIntsNotInTargetIntsEnumerableArray, Convert.ToString));
                IEnumerable<int> targetIntsNotInSourceIntsEnumerable = targetIntList.Where(x => !sourceIntList.Any(y => x == y));
                int[] targetIntsNotInSourceIntsEnumerableArray = targetIntsNotInSourceIntsEnumerable.ToArray<int>();
                string targetIntsNotInSourceInts = string.Join(",", Array.ConvertAll<int, string>(targetIntsNotInSourceIntsEnumerableArray, Convert.ToString));
                string differenceMessage = "Source list has {" + sourceIntsNotInTargetInts + "} not in target list and target list has {" + targetIntsNotInSourceInts + "} not in source list";
                log.Info(differenceMessage);
                return new CompareResult(false, differenceMessage);
            }

            return compareResult;
        }

        /// <summary>
        /// This method compares to Lists of strings. Both lists should be not null and have the same number of members.
        /// </summary>
        /// <param name="sourceStringList"></param>
        /// <param name="targetStringList"></param>
        /// <returns></returns>
        public static CompareResult CompareUnorderedStringLists(List<string> sourceStringList, List<string> targetStringList)
        {
            //log.Debug("starting CompareUnorderedStringLists()");
            bool compareStringListsBool = true;
            StringBuilder compareStringListsStringBuilder = new StringBuilder();
            foreach (string sourceString in sourceStringList)
            {
                if (!targetStringList.Any<string>(s => s == sourceString))
                {
                    compareStringListsBool = false;
                    compareStringListsStringBuilder.Append("Source string not in target string list: " + sourceString + ";");
                }
            }

            foreach (string targetString in targetStringList)
            {
                if (!sourceStringList.Any<string>(s => s == targetString))
                {
                    compareStringListsBool = false;
                    compareStringListsStringBuilder.Append("Target string not in source string list: " + targetString + ";");
                }
            }

            return new CompareResult(compareStringListsBool, compareStringListsStringBuilder.ToString());
        }

        /// <summary>
        /// This method compares two string arrays and returns true if they have the exact same strings in each.
        /// Note that currently, this method does not detect the difference between two string arrays if each array has the 
        /// same strings, but one or more of the strings occur more than once (for example {"abc", "def", "def"} 
        /// and {"abc", "abc", "def"}.
        /// </summary>
        /// <param name="sourceStringArray">The source string array.</param>
        /// <param name="targetStringArray">The target string array.</param>
        /// <returns>It returns true if the arrays are the same length and contain the same strings.</returns>
        public static CompareResult CompareUnorderedStringArrays(string[] sourceStringArray, string[] targetStringArray)
        {
            ////log.Debug("starting CompareUnorderedStringArrays()");
            CompareResult compareResult = new CompareResult("String arrays match");
            if (sourceStringArray == null || targetStringArray == null)
            {
                string nullMessage = "Cannot compare null arrays";
                log.Info(nullMessage);
                return new CompareResult(false, nullMessage);
            }

            if (sourceStringArray.Length != targetStringArray.Length)
            {
                string lengthMessage = "Source array length " + sourceStringArray.Length + " does not match target array length " + targetStringArray.Length;
                log.Debug("" + lengthMessage);
                foreach (string sourceString in sourceStringArray)
                {
                    log.Debug("source string: " + sourceString);
                }

                foreach (string targetString in targetStringArray)
                {
                    log.Debug("target string: " + targetString);
                }

                return new CompareResult(false, lengthMessage);
            }

            log.Debug("arrays are not null and match length");
            StringBuilder compareStringBuilder = new StringBuilder();
            bool compareResultBool = true;
            foreach (string targetString in targetStringArray)
            {
                ////string targetString = targetStringArray[i];
                log.Debug("next target string is " + targetString);
                if (!sourceStringArray.Contains(targetString))
                {
                    compareStringBuilder.Append("Source array does not contain " + targetString + ";");
                    log.Debug("" + targetString + " is not in sourceStringArray");
                    compareResultBool = false;
                }
            }

            foreach (string sourceString in sourceStringArray)
            {
                ////string sourceString = sourceStringArray[i];
                log.Debug("next source string is " + sourceString);
                if (!targetStringArray.Contains(sourceString))
                {
                    compareStringBuilder.Append("Target array does not contain " + sourceString + ";");
                    log.Debug("" + sourceString + " is not in targetStringArray");
                    compareResultBool = false;
                }
            }

            if (!compareResultBool)
            {
                return new CompareResult(false, compareStringBuilder.ToString());
            }

            return compareResult;
        }

        /// <summary>
        /// The default level log method defaults to info.
        /// </summary>
        /// <param name="s">The string to be logged.</param>
        [Obsolete("This methods are going to be removed in TEST-43. So avoid using it and instead use approach described in TEST-45.")]
        public static void Log(string s)
        {
            Console.ForegroundColor = ConsoleColor.White;
            ILog logger = LogManager.GetLogger("WebServiceClientBase.Helper");
            logger.Info(s);
        }

        /// <summary>
        /// The info level log method.
        /// </summary>
        /// <param name="s">The string to be logged.</param>
        [Obsolete("This methods are going to be removed in TEST-43. So avoid using it and instead use approach described in TEST-45.")]
        public static void LogInfo(string s)
        {
            Console.ForegroundColor = ConsoleColor.White;
            ILog logger = LogManager.GetLogger("WebServiceClientBase.Helper");
            logger.Info(s);
        }

        /// <summary>
        /// The debug level log method.
        /// </summary>
        /// <param name="s">The string to be logged.</param>
        [Obsolete("This methods are going to be removed in TEST-43. So avoid using it and instead use approach described in TEST-45.")]
        public static void LogDebug(string s)
        {
            Console.ForegroundColor = ConsoleColor.Blue;
            ILog logger = LogManager.GetLogger("WebServiceClientBase.Helper");
            logger.Debug(s);
            Console.ForegroundColor = ConsoleColor.White;
        }

        /// <summary>
        /// The warn level log method.
        /// </summary>
        /// <param name="s">The string to be logged.</param>
        [Obsolete("This methods are going to be removed in TEST-43. So avoid using it and instead use approach described in TEST-45.")]
        public static void LogWarn(string s)
        {
            Console.ForegroundColor = ConsoleColor.Red;
            ILog logger = LogManager.GetLogger("WebServiceClientBase.Helper");
            logger.Warn(s);
            Console.ForegroundColor = ConsoleColor.White;
        }

        /// <summary>
        /// Parses $datetime strings.
        /// </summary>
        /// <param name="dateTimeString">The $datetime string.</param>
        /// <returns>The dateTime object represented by the string.</returns>
        public static DateTime ParseDateTime(string dateTimeString)
        {
            DateTime dateTime = DateTime.Now;
            log.DebugFormat("dateTimeString is {0}", dateTimeString);
            if (dateTimeString != null && dateTimeString.StartsWith("$"))
            {
                string timeSpanString = dateTimeString.Substring(1);
                log.DebugFormat("timeSpanString is {0}", timeSpanString);
                try
                {
                    TimeSpan timeSpan = TimeSpan.Parse(timeSpanString);
                    log.DebugFormat("timeSpan is {0}", timeSpan);
                    dateTime += timeSpan;
                }
                catch (Exception e)
                {
                    string timeSpanStringException = string.Format("timeSpanString is invalid: {0}:{1}", timeSpanString, e.Message);
                    log.Warn(timeSpanStringException);
                }
            }
            else if (dateTimeString != null)
            {
                try
                {
                    dateTime = DateTime.Parse(dateTimeString);
                }
                catch (Exception e)
                {
                    string dateTimeStringException = string.Format("dateTimeString is invalid: {0}:{1}", dateTimeString, e.Message);
                    log.Warn(dateTimeStringException);
                }
            }
            else
            {
                string dateTimeStringNull = "dateTimeString is null";
                log.Debug(dateTimeStringNull);
            }

            return dateTime;
        }

        /// <summary>
        /// This method compares two DateTime objects and regards them as equal 
        /// if their time differnce is less than the specified ignoreInterval TimeSpan.
        /// </summary>
        /// <param name="sourceDateTime">The source DateTime.</param>
        /// <param name="targetDateTime">The target DateTime.</param>
        /// <param name="ignoreInterval">The maximum gap between the two times.</param>
        /// <returns>true if the times are differ by less than the ignoreInterval.</returns>
        public static bool CompareDateTimes(DateTime? sourceDateTime, DateTime? targetDateTime, TimeSpan ignoreInterval)
        {
            if (sourceDateTime == null && targetDateTime == null)
            {
                /* Both date times are null, so regard them as equal */
                return true;
            }
            else if (sourceDateTime != null && targetDateTime != null)
            {
                /* Both date times are not null, so test them */
                log.DebugFormat("sourceDateTime is {0}", sourceDateTime);
                TimeSpan interval = ((DateTime)sourceDateTime).Subtract((DateTime)targetDateTime);
                log.DebugFormat("targetDateTime is {0}", targetDateTime);
                if (Math.Abs(interval.TotalSeconds) < Math.Abs(ignoreInterval.TotalSeconds))
                {
                    log.Debug("interval time is less than ignoreInterval time");
                    return true;
                }
            }

            /* Return false if only one is null or their DateTimes differ by more than the ignoreInterval. */
            return false;
        }

        /// <summary>
        /// This method checks to see if the IIS server is running on the same machine as the test.
        /// The server name may include its FQDN, so we remove this before comparing to the test
        /// machine name.
        /// </summary>
        /// <param name="server">The name of the IIS server.</param>
        /// <returns>true if the test machine name is the same as the server parameter.</returns>
        public static bool IsServerLocal(string server)
        {
            //log.Debug("starting IsServerLocal()");
            if (server.ToLowerInvariant() == "localhost")
                return true;
            string machineName = Environment.MachineName;
            log.DebugFormat("machineName is {0}", machineName);
            string serverName = server.Split(new char[] { '.' })[0];
            log.DebugFormat("serverName is {0}", serverName);
            if (string.Compare(machineName, serverName, true) == 0)
            {
                return true;
            }

            return false;
        }

        /// <summary>
        /// This method writes the XmlNode (or XmlDocument) to the console.
        /// </summary>
        /// <param name="xmlNode">The xmlNode parameter.</param>
        public static void WriteXmlNodeToSystemConsole(XmlNode xmlNode)
        {
            StringWriter stringWriter = new StringWriter();
            XmlTextWriter xmlTextWriter = new XmlTextWriter(stringWriter);
            xmlTextWriter.Formatting = Formatting.Indented;
            xmlNode.WriteTo(xmlTextWriter);
            xmlTextWriter.Flush();
            System.Console.Write(stringWriter.ToString() + Environment.NewLine);
        }

        /// <summary>
        /// This method escapes special XML characters.
        /// </summary>
        /// <param name="unescapedString">The string to be escaped.</param>
        /// <returns>The escaped string.</returns>
        public static string EscapeXMLString(string unescapedString)
        {
            if (string.IsNullOrEmpty(unescapedString))
            {
                return unescapedString;
            }

            string returnString = unescapedString;
            returnString = returnString.Replace("'", "&apos;");
            returnString = returnString.Replace("\"", "&quot;");
            returnString = returnString.Replace(">", "&gt;");
            returnString = returnString.Replace("<", "&lt;");
            returnString = returnString.Replace("&", "&amp;");
            return returnString;
        }

        /// <summary>
        /// This method unescapes special XML characters.
        /// </summary>
        /// <param name="escapedString">The escaped string.</param>
        /// <returns>The unescaped string.</returns>
        public static string UnescapeXMLString(string escapedString)
        {
            if (string.IsNullOrEmpty(escapedString))
            {
                return escapedString;
            }

            string returnString = escapedString;
            returnString = returnString.Replace("&apos;", "'");
            returnString = returnString.Replace("&quot;", "\"");
            returnString = returnString.Replace("&gt;", ">");
            returnString = returnString.Replace("&lt;", "<");
            returnString = returnString.Replace("&amp;", "&");
            return returnString;
        }

        /// <summary>
        /// This method compares two strings, ignoring differences between the endInitial and startFinal positions.
        /// </summary>
        /// <param name="sourceString">The source string parameter.</param>
        /// <param name="targetString">The target string parameter.</param>
        /// <param name="endInitial">The end position of the initial string.</param>
        /// <param name="startFinal">The start position of the final string.</param>
        /// <returns>true if the strings match except for what is between the endInitial and startFinal positions.</returns>
        public static bool CompareButIgnore(string sourceString, string targetString, int endInitial, int startFinal)
        {
            log.DebugFormat("sourceString is {0}", sourceString);
            log.DebugFormat("targetString is {0}", targetString);
            log.DebugFormat("endInitial is {0}", endInitial);
            log.DebugFormat("startFinal is {0}", startFinal);
            string sourceStartString = sourceString.Substring(0, endInitial);
            log.DebugFormat("sourceStartString is {0}", sourceStartString);
            string targetStartString = targetString.Substring(0, endInitial);
            log.DebugFormat("targetStartString is {0}", targetStartString);
            string sourceEndString = sourceString.Substring(sourceString.Length - startFinal, startFinal);
            log.DebugFormat("sourceEndString is {0}", sourceEndString);
            string targetEndString = targetString.Substring(targetString.Length - startFinal, startFinal);
            log.DebugFormat("targetEndString is {0}", targetEndString);
            if ((string.Compare(sourceStartString, targetStartString) == 0) && (string.Compare(sourceEndString, targetEndString) == 0))
            {
                return true;
            }

            return false;
        }

        /// <summary>
        /// This method returns the value of a contained node as a string. It throws an exception if the node is null and 
        /// throwExceptionIfMissing is set to true.
        /// </summary>
        /// <param name="xmlNode">The containing node.</param>
        /// <param name="path">The path to the contained node.</param>
        /// <param name="throwExceptionIfMissing">Throw an exception if there is no node at this path.</param>
        /// <returns>The value of the specified node.</returns>
        public static string GetStringNodeValue(XmlNode xmlNode, string path, bool throwExceptionIfMissing)
        {
            return GetStringNodeValue(xmlNode, path, string.Empty, throwExceptionIfMissing);
        }

        /// <summary>
        /// This method returns the value of a contained node as a string. It throws an exception if the node is null and 
        /// throwExceptionIfMissing is set to true.
        /// </summary>
        /// <param name="xmlNode">The containing node.</param>
        /// <param name="path">The path to the contained node.</param>
        /// <param name="defaultValue">The value that willbe returned if the node is missing.</param>
        /// <param name="throwExceptionIfMissing">Throw an exception if there is no node at this path.</param>
        /// <returns>The value of the specified node.</returns>
        public static string GetStringNodeValue(XmlNode xmlNode, string path, string defaultValue, bool throwExceptionIfMissing)
        {
            if (xmlNode != null)
            {
                XmlNode pathNode = xmlNode.SelectSingleNode(path);
                if (pathNode != null)
                {
                    if (pathNode.NodeType == XmlNodeType.Attribute)
                    {
                        return pathNode.Value;
                    }
                    else if (pathNode.NodeType == XmlNodeType.Element)
                    {
                        return pathNode.InnerText;
                    }
                    else
                    {
                        /* Return an empty string if the node is not an attribute or element */
                        string incorrectNodeTypeMessage = "Node type is not attribute or element";
                        log.Warn(incorrectNodeTypeMessage);
                        return defaultValue;
                    }
                }
                else
                {
                    string missingPathNodeMessage = string.Format("Missing path node {0}", path);
                    log.Debug(missingPathNodeMessage);
                    if (throwExceptionIfMissing)
                    {
                        throw new Exception(missingPathNodeMessage);
                    }
                }
            }

            /* Return an empty string if the node is missing */
            return defaultValue;
        }

        /// <summary>
        /// This method returns the value of a contained node as a bool. It throws an exception if the node is null and 
        /// throwExceptionIfMissing is set to true. 
        /// </summary>
        /// <param name="xmlNode">The containing node.</param>
        /// <param name="path">The path to the contained node.</param>
        /// <param name="throwExceptionIfMissing">Throw an exception if there is no node at this path.</param>
        /// <param name="defaultValue">Return value if parsing fails.</param>
        /// <returns>The value of the specified node.</returns>
        public static bool GetBooleanNodeValue(XmlNode xmlNode, string path, bool throwExceptionIfMissing, bool defaultValue)
        {
            bool nodeValue = defaultValue;
            if (xmlNode != null)
            {
                string nodeValueString = GetStringNodeValue(xmlNode, path, defaultValue.ToString(), throwExceptionIfMissing);
                log.DebugFormat("node string value is {0}", nodeValueString);
                bool tryParse = bool.TryParse(nodeValueString, out nodeValue);
                //log.DebugFormat("tryParse is {0}", tryParse);
                //log.DebugFormat("node value is {0}", nodeValue);
            }

            return nodeValue;
        }

        /// <summary>
        /// This method returns the value of a contained node as a bool. It throws an exception if the node is null and 
        /// throwExceptionIfMissing is set to true. 
        /// </summary>
        /// <param name="xmlNode">The containing node.</param>
        /// <param name="path">The path to the contained node.</param>
        /// <param name="throwExceptionIfMissing">Throw an exception if there is no node at this path.</param>
        /// <returns>The value of the specified node.</returns>
        public static bool GetBooleanNodeValueDefaultTrue(XmlNode xmlNode, string path, bool throwExceptionIfMissing)
        {
            return GetBooleanNodeValue(xmlNode, path, throwExceptionIfMissing, true);
        }

        /// <summary>
        /// This method returns the value of a contained node as a bool. It throws an exception if the node is null and 
        /// throwExceptionIfMissing is set to true. 
        /// </summary>
        /// <param name="xmlNode">The containing node.</param>
        /// <param name="path">The path to the contained node.</param>
        /// <param name="throwExceptionIfMissing">Throw an exception if there is no node at this path.</param>
        /// <returns>The value of the specified node.</returns>
        public static bool GetBooleanNodeValueDefaultFalse(XmlNode xmlNode, string path, bool throwExceptionIfMissing)
        {
            return GetBooleanNodeValue(xmlNode, path, throwExceptionIfMissing, false);
        }

        /// <summary>
        /// This method clears the Helper dictionary of all keys.
        /// </summary>
        public static void ClearDictionary()
        {
            //log.Debug("starting ClearDictionary()");
            helperDictionary.Clear();
        }

        /// <summary>
        /// This method returns the value of a contained node as an integer. It throws an exception if the node is null and 
        /// throwExceptionIfMissing is set to true. 
        /// </summary>
        /// <param name="xmlNode">The containing node.</param>
        /// <param name="path">The path to the contained node.</param>
        /// <param name="defaultValue">Return value if parsing fails.</param>
        /// <param name="throwExceptionIfMissing">Throw an exception if there is no node at this path.</param>
        /// <returns>The value of the specified node.</returns>
        public static int GetIntNodeValue(XmlNode xmlNode, string path, int defaultValue, bool throwExceptionIfMissing)
        {
            int nodeValue = defaultValue;
            if (xmlNode != null)
            {
                string nodeValueString = GetStringNodeValue(xmlNode, path, throwExceptionIfMissing);
                log.DebugFormat("node string value is {0}", nodeValueString);
                bool tryParse = int.TryParse(nodeValueString, out nodeValue);
                if (tryParse)
                {
                    //log.DebugFormat("success parsing {0}", nodeValueString);
                }
                else
                {
                    //log.DebugFormat("failure parsing {0}", nodeValueString);
                    nodeValue = defaultValue;
                }
            }

            //log.DebugFormat("node value is {0}", nodeValue);
            return nodeValue;
        }

        /// <summary>
        /// The get null able integer node value.
        /// </summary>
        /// <param name="xmlNode">The xml node.</param>
        /// <param name="path">The path.</param>
        /// <param name="defaultValue">The default value.</param>
        /// <param name="throwExceptionIfMissing">The throw exception if missing.</param>
        /// <returns>
        /// The <see cref="int?"/> value represents from specified node.
        /// </returns>
        public static int? GetNullableIntNodeValue(XmlNode xmlNode, string path, int? defaultValue, bool throwExceptionIfMissing)
        {
            int? nodeValue = defaultValue;
            string nodeValueString = GetStringNodeValue(xmlNode, path, throwExceptionIfMissing);
            log.DebugFormat("node string value is {0}", nodeValueString);
            int intValue = 1;
            bool tryParse = int.TryParse(nodeValueString, out intValue);
            if (tryParse)
            {
                //log.DebugFormat("success parsing {0}", nodeValueString);
                nodeValue = intValue;
            }
            else
            {
                //log.DebugFormat("failure parsing {0}", nodeValueString);
            }

            //log.Debug("node value is {0}", nodeValue);
            return nodeValue;
        }

        /// <summary>
        /// This method returns the value of a contained node as a DateTime. It throws an exception if the node is null and 
        /// throwExceptionIfMissing is set to true. 
        /// </summary>
        /// <param name="xmlNode">The containing node.</param>
        /// <param name="path">The path to the contained node.</param>
        /// <param name="defaultValue">Return value if parsing fails.</param>
        /// <param name="throwExceptionIfMissing">Throw an exception if there is no node at this path.</param>
        /// <returns>The value of the specified node.</returns>
        public static DateTime GetDateNodeValue(XmlNode xmlNode, string path, DateTime defaultValue, bool throwExceptionIfMissing)
        {
            DateTime nodeValue = defaultValue;
            if (xmlNode != null)
            {
                string nodeValueString = GetStringNodeValue(xmlNode, path, throwExceptionIfMissing);
                log.DebugFormat("date node string value is {0}", nodeValueString);
                if (!string.IsNullOrEmpty(nodeValueString))
                {
                    DateTime.TryParse(nodeValueString, out nodeValue);
                    //log.DebugFormat("date node value is {0}", nodeValue);
                }
            }

            return nodeValue;
        }

        /// <summary>
        /// This method returns the value of a contained node as a null able DateTime. It throws an exception if the node is null and 
        /// throwExceptionIfMissing is set to true. 
        /// </summary>
        /// <param name="xmlNode">The containing node.</param>
        /// <param name="path">The path to the contained node.</param>
        /// <param name="defaultValue">Return value if parsing fails.</param>
        /// <param name="throwExceptionIfMissing">Throw an exception if there is no node at this path.</param>
        /// <returns>The value of the specified node.</returns>
        public static DateTime? GetNullableDateNodeValue(XmlNode xmlNode, string path, DateTime? defaultValue, bool throwExceptionIfMissing)
        {
            DateTime nodeValue;
            DateTime? nullableNodeValue = defaultValue;
            string nodeValueString = GetStringNodeValue(xmlNode, path, throwExceptionIfMissing);
            log.DebugFormat("date node string value is {0}", nodeValueString);
            if (!string.IsNullOrEmpty(nodeValueString))
            {
                nullableNodeValue = DateTime.TryParse(nodeValueString, out nodeValue) ? nodeValue : defaultValue;
                //log.DebugFormat("date node value is {0}", nodeValue);
            }

            return nullableNodeValue;
        }

        /// <summary>
        /// This method returns the value of a contained node as a GUID. It throws an exception if the node is null.
        /// </summary>
        /// <param name="xmlNode">The containing node.</param>
        /// <param name="path">The path to the contained node.</param>
        /// <param name="defaultValue">Return value if parsing fails.</param>
        /// <param name="throwExceptionIfMissing">Throw an exception if there is no node at this path.</param>
        /// <returns>The value of the specified node.</returns>
        public static Guid GetGuidNodeValue(XmlNode xmlNode, string path, Guid defaultValue, bool throwExceptionIfMissing)
        {
            Guid nodeValue = defaultValue;
            string nodeValueString = GetStringNodeValue(xmlNode, path, throwExceptionIfMissing);
            //log.DebugFormat("node string value is {0}", nodeValueString);
            bool tryParse = Guid.TryParse(nodeValueString, out nodeValue);
            if (tryParse)
            {
                //log.DebugFormat("success parsing {0}", nodeValueString);
            }
            else
            {
                //log.DebugFormat("failure parsing {0}", nodeValueString);
                nodeValue = defaultValue;
            }

            //log.DebugFormat("node value is {0}", nodeValue);
            return nodeValue;
        }

        public static CompareResult CompareObjects(object sourceObject, object targetObject)
        {
            //log.Debug("starting CompareObjects()");
            bool compareObjectsBool = true;
            StringBuilder compareObjectsStringBuilder = new StringBuilder();
            if (sourceObject == null && targetObject == null)
            {
                return new CompareResult("Source and target objects are both null");
            }

            //log.Debug("at least one of source and target objects are not null");
            if (sourceObject == null && targetObject != null)
            {
                //log.Debug("source object is null and target object is not null");
                return new CompareResult(false, "Source object is null and target object is not null");
            }
            else if (sourceObject != null && targetObject == null)
            {
                //log.Debug("source object is not null and target object is null");
                return new CompareResult(false, "Source object is not null and target object is null");
            }

            //log.Debug("both source and target objects are not null");
            Type sourceObjectType = sourceObject.GetType();
            //log.DebugFormat("source object type is {0}", sourceObjectType);
            if (sourceObjectType != targetObject.GetType())
            {
                string mismatchTypesMessage = string.Format("Source object type {0} does not match target object type {1};", sourceObject.GetType(), targetObject.GetType());
                return new CompareResult(false, mismatchTypesMessage);
            }

            //log.DebugFormat("object type is {0}", sourceObject.GetType());
            if (sourceObjectType == typeof(string))
            {
                //log.Debug("object type is string");
                if (!string.Equals(sourceObject, targetObject))
                {
                    string mismatchStringsMessage = string.Format("Mismatch between source string {0} and target string {1};", sourceObject, targetObject);
                    log.Warn(mismatchStringsMessage);
                    compareObjectsBool = false;
                    compareObjectsStringBuilder.Append(mismatchStringsMessage);
                }
            }
            else if (sourceObjectType == typeof(int))
            {
                //log.Debug("object type is int");
                if (((int)targetObject) != 0 && !int.Equals(sourceObject, targetObject))
                {
                    string mismatchIntsMessage = string.Format("Mismatch between source int {0} and target int {1};", sourceObject, targetObject);
                    log.Warn(mismatchIntsMessage);
                    compareObjectsBool = false;
                    compareObjectsStringBuilder.Append(mismatchIntsMessage);
                }
            }
            else if (sourceObjectType == typeof(bool))
            {
                //log.Debug("object type is bool");
                if (!bool.Equals(sourceObject, targetObject))
                {
                    string mismatchBoolsMessage = string.Format("Mismatch between source bool {0} and target bool {1};", sourceObject, targetObject);
                    log.Warn(mismatchBoolsMessage);
                    compareObjectsBool = false;
                    compareObjectsStringBuilder.Append(mismatchBoolsMessage);
                }
            }
            else if (sourceObjectType == typeof(DateTime))
            {
                //log.Debug("object type is DateTime");
                if (((DateTime)targetObject) != DateTime.MinValue && !DateTime.Equals(sourceObject, targetObject))
                {
                    string mismatchDateTimesMessage = string.Format("Mismatch between source DateTime {0} and target DateTime {1};", sourceObject, targetObject);
                    log.Warn(mismatchDateTimesMessage);
                    compareObjectsBool = false;
                    compareObjectsStringBuilder.Append(mismatchDateTimesMessage);
                }
            }
            else if (sourceObject is Array)
            {
                //log.Debug("source object type is Array");
                if (targetObject is Array)
                {
                    CompareResult compareArraysResult = CompareArrays((Array)sourceObject, (Array)targetObject);
                    if (!compareArraysResult.Result)
                    {
                        string mismatchPropertyMessage = string.Format("Source array does not match target array: {0};", compareArraysResult.Message);
                        log.Warn(mismatchPropertyMessage);
                        compareObjectsBool = false;
                        compareObjectsStringBuilder.Append(mismatchPropertyMessage);
                    }
                }
                else
                {
                    //log.Debug("target object type is not IDictionary");
                }
            }
            else if (sourceObject is IDictionary<object, object>)
            {
                //log.Debug("source object type is IDictionary");
                if (targetObject is IDictionary<object, object>)
                {
                    CompareResult compareDictionariesResult = CompareDictionaries((IDictionary<object, object>)sourceObject, (IDictionary<object, object>)targetObject);
                    if (!compareDictionariesResult.Result)
                    {
                        string mismatchDictionariesMessage = string.Format("Source dictionary does not match target dictionary: {0};", compareDictionariesResult.Message);
                        log.Warn(mismatchDictionariesMessage);
                        compareObjectsBool = false;
                        compareObjectsStringBuilder.Append(mismatchDictionariesMessage);
                    }
                }
                else
                {
                    //log.Debug("target object type is not IDictionary");
                }
            }
            else if (sourceObject is IEnumerable<object>)
            {
                //log.Debug("source object type is IEnumerable");
                if (targetObject is IEnumerable<object>)
                {
                    CompareResult compareEnumerablesResult = CompareEnumerables((IEnumerable<object>)sourceObject, (IEnumerable<object>)targetObject);
                    if (!compareEnumerablesResult.Result)
                    {
                        string mismatchEnumerablesMessage = string.Format("Source enumerable does not match target enumerable: {0};", compareEnumerablesResult.Message);
                        log.Warn(mismatchEnumerablesMessage);
                        compareObjectsBool = false;
                        compareObjectsStringBuilder.Append(mismatchEnumerablesMessage);
                    }
                }
                else
                {
                    //log.Debug("target object type is not IEnumerable");
                }
            }
            else
            {
                //log.Debug("object type is not IDictionary");
                log.DebugFormat("object type is {0}", sourceObject.GetType());
                //log.DebugFormat("object type name is {0}", sourceObject.GetType().Name);
                if (IsSupportedType(sourceObject.GetType()))
                {
                    //log.Debug("object type is supported");
                    PropertyInfo[] sourcePropertyInfoArray = sourceObject.GetType().GetProperties(BindingFlags.Public | BindingFlags.NonPublic | BindingFlags.Instance);
                    foreach (PropertyInfo sourcePropertyInfo in sourcePropertyInfoArray)
                    {
                        string propertyInfoName = sourcePropertyInfo.Name;
                        log.DebugFormat("property info name is {0}", propertyInfoName);
                        Type sourcePropertyInfoType = sourcePropertyInfo.PropertyType;
                        if (IsSupportedType(sourcePropertyInfoType))
                        {
                            PropertyInfo targetPropertyInfo = targetObject.GetType().GetProperty(propertyInfoName);
                            if (targetPropertyInfo.CanWrite)
                            {
                                object targetProperty = targetPropertyInfo.GetValue(targetObject, null);
                                if (targetProperty != null)
                                {
                                    object sourceProperty = sourcePropertyInfo.GetValue(sourceObject, null);
                                    //log.Debug("about to compare property objects for " + propertyInfoName);
                                    CompareResult comparePropertyResult = CompareObjects(sourceProperty, targetProperty);
                                    //log.Debug("compared property objects for " + propertyInfoName);
                                    if (!comparePropertyResult.Result)
                                    {
                                        string mismatchPropertyMessage = string.Format("Source and target objects for {0} do not match: {1}", propertyInfoName, comparePropertyResult.Message);
                                        log.Warn(mismatchPropertyMessage);
                                        compareObjectsBool = false;
                                        compareObjectsStringBuilder.AppendFormat("{0};", mismatchPropertyMessage);
                                    }
                                }
                            }
                            else
                            {
                                log.Debug("target property info is not writable");
                            }
                        }
                    }
                }
                else
                {
                    log.Debug("object type is not supported");
                }
            }

            return new CompareResult(compareObjectsBool, compareObjectsStringBuilder.ToString());
        }

        public static CompareResult CompareEnumerables(IEnumerable<object> sourceEnumerable, IEnumerable<object> targetEnumerable)
        {
            //log.Debug("starting CompareEnumerables()");
            bool compareArraysBool = true;
            StringBuilder compareArraysStringBuilder = new StringBuilder();
            Type sourceElementType = sourceEnumerable.GetType().GetElementType();
            Type targetElementType = targetEnumerable.GetType().GetElementType();
            if (sourceElementType != targetElementType)
            {
                return new CompareResult(false, "Enumerable types do not match");
            }

            log.Debug("array types match");
            foreach (object sourceEnumerableObject in sourceEnumerable)
            {
                if (targetEnumerable.Any(teo => teo == sourceEnumerableObject))
                {
                    string missingElementInEnumerableMessage = "Source enumerable object not in target enumerable";
                    log.Warn(missingElementInEnumerableMessage);
                    compareArraysBool = false;
                    compareArraysStringBuilder.AppendFormat("{0};", missingElementInEnumerableMessage);
                }
            }

            foreach (object targetEnumerableObject in targetEnumerable)
            {
                if (sourceEnumerable.Any(seo => seo == targetEnumerableObject))
                {
                    string missingElementInEnumerableMessage = "Target enumerable object not in source enumerable";
                    log.Warn(missingElementInEnumerableMessage);
                    compareArraysBool = false;
                    compareArraysStringBuilder.AppendFormat("{0};", missingElementInEnumerableMessage);
                }
            }

            return new CompareResult(compareArraysBool, compareArraysStringBuilder.ToString());
        }

        public static CompareResult CompareArrays(Array sourceArray, Array targetArray)
        {
            //log.Debug("starting CompareArrays()");
            bool compareArraysBool = true;
            StringBuilder compareArraysStringBuilder = new StringBuilder();
            Type sourceElementType = sourceArray.GetType().GetElementType();
            Type targetElementType = targetArray.GetType().GetElementType();
            if (sourceElementType != targetElementType)
            {
                return new CompareResult(false, "Array types do not match");
            }

            //log.Debug("array types match");
            foreach (object sourceArrayObject in sourceArray)
            {
                if (Array.IndexOf(targetArray, sourceArrayObject) < 0)
                {
                    string missingElementInArrayMessage = "Source array object not in target array";
                    log.Warn(missingElementInArrayMessage);
                    compareArraysBool = false;
                    compareArraysStringBuilder.AppendFormat("{0};", missingElementInArrayMessage);
                }
            }

            foreach (object targetArrayObject in targetArray)
            {
                if (Array.IndexOf(sourceArray, targetArrayObject) < 0)
                {
                    string missingElementInArrayMessage = "Target array object not in source array";
                    log.Warn(missingElementInArrayMessage);
                    compareArraysBool = false;
                    compareArraysStringBuilder.AppendFormat("{0};", missingElementInArrayMessage);
                }
            }

            return new CompareResult(compareArraysBool, compareArraysStringBuilder.ToString());
        }

        public static CompareResult CompareDictionaries(IDictionary<object, object> sourceDictionary, IDictionary<object, object> targetDictionary)
        {
            //log.Debug("starting CompareDictionaries()");
            bool compareDictionariesBool = true;
            StringBuilder compareDictionariesStringBuilder = new StringBuilder();
            Type[] sourceTypes = sourceDictionary.GetType().GetGenericArguments();
            Type[] targetTypes = targetDictionary.GetType().GetGenericArguments();
            if (sourceTypes[0] != targetTypes[0] || sourceTypes[1] != targetTypes[1])
            {
                return new CompareResult(false, "Dictionary types do not match");
            }

            //log.Debug("dictionary types match");
            foreach (object key in sourceDictionary.Keys)
            {
                log.DebugFormat("source key is {0}", key);
                object sourceValue = sourceDictionary[key];
                log.DebugFormat("source value type is {0}", sourceValue.GetType());
                log.DebugFormat("sourceValue is {0}", sourceValue);
                object targetValue = targetDictionary[key];
                log.DebugFormat("target value type is {0}", targetValue.GetType());
                log.DebugFormat("targetValue is {0}", targetValue);
                CompareResult compareResult = CompareObjects(sourceValue, targetValue);
                if (!compareResult.Result)
                {
                    log.Warn(compareResult.Message);
                    compareDictionariesBool = false;
                    compareDictionariesStringBuilder.AppendFormat("{0};", compareResult.Message);
                }
                else
                {
                    log.Warn("dictionary values match for source key");
                }
            }

            foreach (object key in targetDictionary.Keys)
            {
                log.DebugFormat("target key is {0}", key);
                object sourceValue = sourceDictionary[key];
                log.DebugFormat("source value type is {0}", sourceValue.GetType());
                log.DebugFormat("sourceValue is {0}", sourceValue);
                object targetValue = targetDictionary[key];
                log.DebugFormat("target value type is {0}", targetValue.GetType());
                log.DebugFormat("targetValue is {0}", targetValue);
                CompareResult compareResult = CompareObjects(sourceValue, targetValue);
                if (!compareResult.Result)
                {
                    log.Warn(compareResult.Message);
                    compareDictionariesBool = false;
                    compareDictionariesStringBuilder.AppendFormat("{0};", compareResult.Message);
                }
                else
                {
                    log.Warn("dictionary values match for target key");
                }
            }

            return new CompareResult(compareDictionariesBool, compareDictionariesStringBuilder.ToString());
        }

        public static bool IsSupportedType(Type type)
        {
            //log.Debug("starting IsSupportedType()");
            log.DebugFormat("type is {0}", type);
            //log.DebugFormat("type name is {0}", type.Name);
            if (type.IsArray || type.Name.StartsWith("Dictionary") || type.Name.StartsWith("KeyCollection") || type.Name.StartsWith("System.Collection"))
            {
                return false;
            }

            return true;
        }

        /// <summary>
        /// Returns the Connection String for current connection.
        /// </summary>
        public static string GetConnectionString()
        {
            /* Get Connection String from app configuration. */
            if (ConfigurationManager.ConnectionStrings["DatabaseHarnessTest"] == null)
            {
                throw new ConfigurationErrorsException("Connection string was not found in application configuration file.");
            }

            string connectionString = ConfigurationManager.ConnectionStrings["DatabaseHarnessTest"].ConnectionString;
            return connectionString;
        }
    }
}
