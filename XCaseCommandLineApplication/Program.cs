// ----------------------------------------------------------------------------
// <copyright file="XCaseCommandLineApplication.cs" company="XCase">
//   Copyright XCase
// </copyright>
// <summary>
//   This class is the entry point for the XCase command line application.
// </summary>
// ----------------------------------------------------------------------------
namespace XCaseCommandLineApplication
{
    using System;
    using System.Collections.Generic;
    using System.IO;
    using System.Linq;
    using System.Reflection;
    using System.Text;
    using System.Xml;
    using System.Xml.XPath;
    using log4net;
    using XCaseBase;

    /// <summary>
    /// This class is the entry point for the TestTools command line application. Its primary purpose is to 
    /// retrieve the XML file, process any other parameters passed in at the command line, and then to pass
    /// off the XML file to the DocumentProcessor class. The Main method exits with status 0 
    /// if the operation(s) result is true, and if no exception is bubbled 
    /// up to the Main method.
    /// </summary>
    public class Program
    {
        /// <summary>
        ///  A log4net log instance.
        /// </summary>
        private static readonly ILog Log = LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);

        /// <summary>
        /// This field is used to specify the environment in which the document is to be processed.
        /// </summary>
        private static ProcessEnvironment processEnvironment = null;

        /// <summary>
        /// The result of executing all the test operations.
        /// </summary>
        private static bool totalResult = true;

        /// <summary>
        /// This field is used to specify whether the test should prompt for user input or not.
        /// </summary>
        private static bool silentBool = false;

        /// <summary>
        /// This field stores the input file name.
        /// </summary>
        private static string fileName = null;

        /// <summary>
        /// The Main entry point.
        /// </summary>
        /// <param name="args">The standard args parameter.</param>
        public static void Main(string[] args)
        {
            ////System.Console.WriteLine(MethodBase.GetCurrentMethod().DeclaringType.Name + ": starting Main");
            log4net.Config.XmlConfigurator.Configure();
            processEnvironment = new ProcessEnvironment();
            Log.Debug("created default process environment");
            FileStream stream = null;
            /* If no file name is provided, then prompt at the command line.
             * If a file name is provided, then process that file.
             * If other configuration information is provided, then process the arguments.
             * If a property name-value pair is provided, then override the value in the XML file.
             */
            XmlDocument document = new XmlDocument();
            if (args.Length > 0)
            {
                try
                {
                    Log.Info("file name is " + args[0]);
                    stream = new FileStream(args[0], FileMode.Open, FileAccess.Read);
                    document.Load(stream);
                    stream.Close();
                    ProcessEnvironmentArguments(args);
                    ProcessDocumentOverrides(document, args);
                    ProcessDocumentResult processDocumentResult = DocumentProcessor.ProcessDocument(processEnvironment, document);
                    totalResult = processDocumentResult.Result;
                }
                catch (Exception e)
                {
                    string openingFileException = "Exception opening file " + e.Message;
                    Log.Warn(openingFileException);
                    Environment.Exit(1);
                }
            }
            else
            {
                try
                {
                    while (true)
                    {
                        System.Console.WriteLine("Please provide path to XML file:");
                        string filename = System.Console.ReadLine();
                        stream = new FileStream(filename, FileMode.Open, FileAccess.Read);
                        Log.Debug("opened stream");
                        document.Load(stream);
                        stream.Close();
                        processEnvironment = new ProcessEnvironment();
                        ProcessDocumentResult processDocumentResult = DocumentProcessor.ProcessDocument(processEnvironment, document);
                    }
                }
                catch (Exception e)
                {
                    string openingFileException = "Exception opening file: " + e.Message;
                    Log.Info(openingFileException);
                    System.Console.In.Read();
                    Environment.Exit(1);
                }
            }

            if (totalResult)
            {
                Environment.Exit(0);
            }
            else
            {
                Environment.Exit(1);
            }
        }

        /// <summary>
        /// This method processes the command line arguments.
        /// </summary>
        /// <param name="args">The command line arguments passed into the application.</param>
        public static void ProcessEnvironmentArguments(string[] args)
        {
            Log.Debug("starting ProcessEnvironmentArguments()");
            if (args.Length > 0)
            {
                fileName = args[0];
            }

            for (int i = 1; i < args.Length; i++)
            {
                string name = args[i];
                Log.Debug("parameter name is " + name);
                string value = args[i + 1];
                Log.Debug("parameter value is " + value);
                switch (name)
                {
                    case "-messengerType":
                        processEnvironment.MessengerTypeString = value;
                        Log.Info("m_MessengerTypeString is " + value);
                        break;
                    case "-messengerTypeConfiguration":
                        processEnvironment.MessengerTypeConfigurationString = value;
                        Log.Info("m_MessengerTypeConfigurationString is " + value);
                        break;
                    case "-silent":
                        silentBool = bool.Parse(value);
                        Log.Info("m_SilentBool is " + silentBool);
                        processEnvironment.Silent = silentBool;
                        break;
                    case "-xmlProperty":
                        Log.Info("ignore -xmlProperty values");
                        break;
                    default:
                        Log.Debug("Invalid parameter name " + name);
                        break;
                }

                i++;
            }
        }

        /// <summary>
        /// This method processes the -xmlProperty overrides.
        /// </summary>
        /// <param name="document">The test document.</param>
        /// <param name="args">The command line arguments.</param>
        public static void ProcessDocumentOverrides(XmlDocument document, string[] args)
        {
            ////Log.Debug("starting ProcessDocumentOverrides()");
            if (args.Length > 0)
            {
                for (int i = 1; i < args.Length; i++)
                {
                    string name = args[i];
                    Log.Debug("parameter name is " + name);
                    string value = args[i + 1];
                    Log.Debug("parameter value is " + value);
                    switch (name)
                    {
                        case "-xmlProperty":
                            string[] nameValue = value.Split(':');
                            Log.Debug("path is " + nameValue[0]);
                            string xmlPropertyValue = value.Substring(nameValue[0].Length + 1);
                            Log.Debug("xmlPropertyValue is " + xmlPropertyValue);
                            xmlPropertyValue = ProcessScriptVariable(xmlPropertyValue);
                            XmlNodeList xmlNodeList = document.SelectNodes(nameValue[0]);
                            foreach (XmlNode xmlNode in xmlNodeList)
                            {
                                if (xmlNode.NodeType == XmlNodeType.Attribute)
                                {
                                    xmlNode.Value = xmlPropertyValue;
                                    Log.Debug("updated attribute value to " + xmlPropertyValue);
                                }
                                else if (xmlNode.NodeType == XmlNodeType.Element)
                                {
                                    xmlNode.InnerText = xmlPropertyValue;
                                    Log.Debug("updated element value to " + xmlPropertyValue);
                                }
                                else
                                {
                                    Log.Debug("xmlNode is neither attribute nor element");
                                }
                            }

                            Log.Debug("updated document value to " + xmlPropertyValue);
                            break;
                        default:
                            Log.Debug("property name is not -xmlProperty");
                            break;
                    }

                    i++;
                }
            }
        }

        /// <summary>
        /// This method replaces variables such as $date, $datetime with current strings.
        /// </summary>
        /// <param name="variable">The variable parameter.</param>
        /// <returns>The updated variable with replaced strings.</returns>
        public static string ProcessScriptVariable(string variable)
        {
            if (variable == null)
            {
                return variable;
            }
            else
            {
                if (!variable.StartsWith("$"))
                {
                    return variable;
                }
                else if (variable == "$datetime")
                {
                    Log.Debug("variable is $datetime");
                    return DateTime.Now.ToString();
                }
                else if (variable == "$date")
                {
                    Log.Debug("variable is $date");
                    return DateTime.Now.Date.ToString();
                }
                else if (variable == "$utcdatetime")
                {
                    Log.Debug("variable is $utcdatetime");
                    TimeZone localTimeZone = System.TimeZone.CurrentTimeZone;
                    Log.Debug("localTimeZone is " + localTimeZone.StandardName);
                    TimeSpan localTimeZoneOffset = localTimeZone.GetUtcOffset(DateTime.Now);
                    DateTime localDateTime = DateTime.Now.Subtract(localTimeZoneOffset);
                    return localDateTime.ToString();
                }
                else
                {
                    Log.Debug("unrecognized variable " + variable);
                    return variable;
                }
            }
        }
    }
}