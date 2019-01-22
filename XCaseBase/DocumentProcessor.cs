namespace XCaseBase
{
    using System;
    using System.Collections.Generic;
    using System.Collections.Specialized;
    using System.Configuration;
    using System.Diagnostics;
    using System.Globalization;
    using System.IO;
    using System.Linq;
    using System.Reflection;
    using System.Text;
    using System.Text.RegularExpressions;
    using System.Xml;
    using log4net;

    /// <summary>
    /// This class is the main class to perform one or more operations. The form of the input document
    /// must be either a single operation element or an operations element that contains zero or more
    /// operation elements.
    /// </summary>
    public class DocumentProcessor
    {
        #region Logger Setup

        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILog log = LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);

        #endregion

        /// <summary>
        /// The field used to track the success of operations.
        /// </summary>
        //private static bool operationResultBool = true;

        /// <summary>
        /// The custom ISO format to serialize and deserialize.
        /// </summary>
        public const string DateTimeFormat = "yyyy-MM-ddTHH:mm:ss.fff";

        /// <summary>
        /// The field to use to track conditionals for test branching.
        /// </summary>
        ////private static Dictionary<string, bool> conditionalsDictionary = new Dictionary<string, bool>();

        /// <summary>
        /// The field to use to track the messenger type.
        /// </summary>
        private static string messengerTypeString = "XCaseGeneric.ConsoleMessenger";
        ////static string m_MessengerTypeString = "XCaseGeneric.DatabaseMessenger";
        ////static string m_MessengerTypeString = "XCaseGeneric.XMLMessenger";

        /// <summary>
        /// The configuration string used to configure the messenger.
        /// </summary>
        private static string messengerTypeConfigurationString = "dbHost=Qawin1;dbPort=1433;dbDatabase=QATestResults;dbUsername=sa;dbPassword=";

        /// <summary>
        /// This method process the input XML file. It identifies the operation element(s)
        /// and performs them in turn. The success or failure of each operation is recorded
        /// in m_OperationResult and the total result is returned in the ProcessDocumentResult.
        /// </summary>
        /// <param name="processEnvironment">The environment parameters passed into the method.</param>
        /// <param name="document">The operation(s) document.</param>
        /// <returns>The result of processing the document.</returns>
        public static ProcessDocumentResult ProcessDocument(ProcessEnvironment processEnvironment, XmlDocument document)
        {
            ////log.Info("starting ProcessDocument()");
            try
            {
                bool processDocumentBool = true;
                StringBuilder processDocumentStringBuilder = new StringBuilder();
                ProcessDocumentResult processDocumentResult = new ProcessDocumentResult();
                if (processEnvironment == null)
                {
                    processEnvironment = new ProcessEnvironment();
                }

                /* Load dictionary from file system */
                Helper.LoadDictionary();
                /* Initialize messenger */
                messengerTypeString = processEnvironment.MessengerTypeString;
                log.Debug("m_MessengerTypeString is " + messengerTypeString);
                if (messengerTypeString == null)
                {
                    messengerTypeString = "XCaseGeneric.ConsoleMessenger";
                }

                messengerTypeConfigurationString = processEnvironment.MessengerTypeConfigurationString;
                log.Debug("messengerTypeConfigurationString is " + messengerTypeConfigurationString);
                string messengerTypeAssemblyString = messengerTypeString + ", " + "XCaseGeneric";
                log.Debug("messengerTypeAssemblyString is " + messengerTypeAssemblyString);
                Type messengerType = Type.GetType(messengerTypeAssemblyString, true);
                IMessenger messenger = (IMessenger)Activator.CreateInstance(messengerType);
                log.Debug("created messenger instance");
                messenger.Init(messengerTypeConfigurationString);
                log.Debug("initialized messenger instance");
                /* Replace all $guid elements */
                document = SubstituteGUIDStrings(document);
                /* Replace all $datetime and $utcdatetime elements */
                document = SubstituteDateTime(document);
                /* Replace all $rand elements */
                document = SubstituteRandom(document);
                // Replace all $env elements.
                document = SubstituteTestVariablesWithAppSettings(document);
                log.Debug("substituted $env values");
                XmlNode operationsNode = document.SelectSingleNode("operations");
                if (operationsNode == null)
                {
                    log.Debug("operations node is null");
                    /* Process environment */
                    XmlElement environmentElement = document.CreateElement("environment");
                    XmlAttribute messengerTypeAttribute = document.CreateAttribute("messengertype");
                    messengerTypeAttribute.Value = processEnvironment.MessengerTypeString;
                    environmentElement.Attributes.Append(messengerTypeAttribute);
                    XmlAttribute messengerTypeConfigurationAttribute = document.CreateAttribute("messengertypeconfiguration");
                    messengerTypeConfigurationAttribute.Value = processEnvironment.MessengerTypeConfigurationString;
                    environmentElement.Attributes.Append(messengerTypeConfigurationAttribute);
                    XmlAttribute silentAttribute = document.CreateAttribute("silent");
                    silentAttribute.Value = processEnvironment.Silent.ToString();
                    environmentElement.Attributes.Append(silentAttribute);
                    XmlNode operationNode = document.SelectSingleNode("/operation");
                    if (operationNode != null)
                    {
                        operationNode.AppendChild(environmentElement);
                    }

                    OperationResult operationResult = PerformOperation(document);
                    log.DebugFormat("operation result is {0}", operationResult.Result);
                    processDocumentBool = operationResult.Result;
                    processDocumentStringBuilder.Append(operationResult.Message);
                    string idString = Helper.GetStringNodeValue(operationNode, "@id", string.Empty, false);
                    log.DebugFormat("idString is {0}", idString);
                    operationResult.Id = idString;
                    if (operationResult.Result)
                    {
                        messenger.Message(operationResult);
                    }
                    else
                    {
                        messenger.Error(operationResult);
                    }
                }
                else
                {
                    /* operations node is not null */
                    log.Debug("operations node is not null");
                    string operationsIDString = Helper.GetStringNodeValue(operationsNode, "@id", "Starting run...", false);
                    log.DebugFormat("operationsIDString is {0}", operationsIDString);
                    bool executeBool = Helper.GetBooleanNodeValueDefaultTrue(operationsNode, "@execute", false);
                    if (!executeBool)
                    {
                        string checkExecuteMessage = "Execute is false";
                        log.Info(checkExecuteMessage);
                        return new ProcessDocumentResult(true, checkExecuteMessage); 
                    }

                    /* If a conditional is specified, then check that it is true, otherwise return immediately */
                    XmlNode checkConditionalNode = document.SelectSingleNode("/operations/@checkconditional");
                    if (checkConditionalNode != null)
                    {
                        string checkConditionalName = Helper.GetStringNodeValue(document, "/operations/@checkconditional", string.Empty, false);
                        log.Info("check conditional name is " + checkConditionalName);
                        bool checkConditionalBool = true;
                        checkConditionalBool = Helper.GetConditional(checkConditionalName);
                        if (!checkConditionalBool)
                        {
                            string checkConditionalMessage = "Conditional " + checkConditionalName + " is false";
                            log.Info(checkConditionalMessage);
                            return new ProcessDocumentResult(true, checkConditionalMessage);
                        }
                    }

                    /* If a not conditional is specified, then check that it is not true, otherwise return immediately */
                    XmlNode checkNotConditionalNode = document.SelectSingleNode("/operations/@checknotconditional");
                    if (checkNotConditionalNode != null)
                    {
                        string checkNotConditionalName = Helper.GetStringNodeValue(document, "/operations/@checknotconditional", string.Empty, false);
                        log.Info("check not conditional name is " + checkNotConditionalName);
                        bool checkNotConditionalBool = true;
                        checkNotConditionalBool = Helper.GetConditional(checkNotConditionalName);
                        if (checkNotConditionalBool)
                        {
                            string checkNotConditionalMessage = "Not conditional " + checkNotConditionalName + " is true";
                            log.Info(checkNotConditionalMessage);
                            return new ProcessDocumentResult(true, checkNotConditionalMessage);
                        }
                    }

                    int repeat = 1;
                    if (document.SelectSingleNode("operations/@repeat") != null)
                    {
                        string operationsRepeatString = document.SelectSingleNode("operations/@repeat").Value;
                        log.Debug("operationsRepeatString is " + operationsRepeatString);
                        int.TryParse(operationsRepeatString, out repeat);
                    }

                    /* Start new run */
                    for (int operationsIndex = 0; operationsIndex < repeat; operationsIndex++)
                    {
                        log.Info("starting operations loop " + operationsIndex);
                        OperationResult startRunOperationresult = new OperationResult(true, "Start Run", operationsIDString);
                        messenger.Message(startRunOperationresult);
                        /* Read in the node list of operations and execute them one-by-one */
                        XmlNodeList operationNodeList = document.SelectNodes("operations/operation");
                        log.Info("operation nodelist of length " + operationNodeList.Count);
                        int index = 0;
                        foreach (XmlNode operationNode in operationNodeList)
                        {
                            index++;
                            log.Debug("next operation node");
                            /* Process environment */
                            XmlElement environmentElement = document.CreateElement("environment");
                            XmlAttribute messengerTypeAttribute = document.CreateAttribute("messengertype");
                            messengerTypeAttribute.Value = processEnvironment.MessengerTypeString;
                            environmentElement.Attributes.Append(messengerTypeAttribute);
                            XmlAttribute messengerTypeConfigurationAttribute = document.CreateAttribute("messengertypeconfiguration");
                            messengerTypeConfigurationAttribute.Value = processEnvironment.MessengerTypeConfigurationString;
                            environmentElement.Attributes.Append(messengerTypeConfigurationAttribute);
                            XmlAttribute silentAttribute = document.CreateAttribute("silent");
                            silentAttribute.Value = processEnvironment.Silent.ToString();
                            environmentElement.Attributes.Append(silentAttribute);
                            if (operationNode != null)
                            {
                                operationNode.AppendChild(environmentElement);
                            }

                            XmlDocument operationDocument = new XmlDocument();
                            operationDocument.AppendChild(operationDocument.ImportNode(operationNode, true));
                            string idString = Helper.GetStringNodeValue(operationNode, "@id", string.Empty, false);
                            /* log.Debug("about to perform operation"); */
                            Console.WriteLine("Operation {0} out of {1}: {2}", index, operationNodeList.Count, idString);
                            OperationResult operationResult = PerformOperation(operationDocument);
                            //log.DebugFormat("performed operation with result {0}", operationResult.Result);
                            if (!operationResult.Result)
                            {
                                processDocumentBool = operationResult.Result;
                                processDocumentStringBuilder.Append(operationResult.Message + ";");
                                Console.WriteLine(" - Fail: {0}", operationResult.Message);
                            }
                            else
                            {
                                Console.WriteLine(" - OK ");
                            }

                            operationResult.Id = idString;
                            /* Try persisting operation result, but continue whatever happens */
                            try
                            {
                                if (operationResult.Result)
                                {
                                    ////log.Debug("operation result is true");
                                    messenger.Message(operationResult);
                                }
                                else
                                {
                                    ////log.Debug("operation result is false");
                                    messenger.Error(operationResult);
                                }
                            }
                            catch (Exception e)
                            {
                                string messengerException = "Exception while invoking messenger: " + e.Message;
                                log.Warn(messengerException);
                            }

                            log.InfoFormat("processDocumentBool is currently {0}", processDocumentBool);
                        }

                        log.InfoFormat("processDocumentBool is {0}", processDocumentBool);
                        /* Finish current run */
                        OperationResult finishRunOperationResult = new OperationResult(processDocumentBool, "Finish Run", "Finishing run...");
                        string status = Helper.GetStringNodeValue(operationsNode, "@status", "Undefined", false);
                        log.DebugFormat("status is {0}", status);
                        finishRunOperationResult.Status = status;
                        log.DebugFormat("operations result is {0}", finishRunOperationResult.Result);
                        messenger.Message(finishRunOperationResult);
                        log.InfoFormat("finishing operations loop {0}", operationsIndex);
                    }

                    log.Debug("about to close messenger");
                    messenger.Close();
                }

                /* Save dictionary to file system */
                Helper.SaveDictionary();
                /* Return result of processing document */
                return new ProcessDocumentResult(processDocumentBool, processDocumentStringBuilder.ToString());
            }
            catch (Exception e)
            {
                string message = "Exception processing document: " + e.Message;
                log.Warn(message);
                return new ProcessDocumentResult(false, message);
            }
        }

        /// <summary>
        /// This method substitutes $env tokens with values of environment variables.
        /// </summary>
        /// <param name="document">The document parameter.</param>
        /// <returns>The updated document.</returns>
        public static XmlDocument SubstituteEnvironment(XmlDocument document)
        {
            log.Debug("starting SubstituteEnvironment()");
            XmlElement documentElement = document.DocumentElement;
            if (documentElement.SelectSingleNode("@initializeenvironment") != null)
            {
                log.Debug("initializeenvironment node is not null");
                bool initializeEnvironment = Helper.GetBooleanNodeValueDefaultFalse(documentElement, "@initializeenvironment", false);
                log.Debug("initializeEnvironment is " + initializeEnvironment);
                if (initializeEnvironment)
                {
                    InitializeEnvironment(documentElement);
                }
            }
            else
            {
                log.Debug("initializeenvironment node is null");
            }

            string sourceString = document.InnerXml;
            while (sourceString.Contains("$env"))
            {
                ////log.Debug("source string contains $env");
                System.Text.RegularExpressions.Regex adjustEnvRegex = new System.Text.RegularExpressions.Regex(@"\$env(\w*)");
                System.Text.RegularExpressions.MatchCollection matchCollection = adjustEnvRegex.Matches(sourceString);
                ////log.Debug("number of matches is " + matchCollection.Count);
                foreach (System.Text.RegularExpressions.Match match in matchCollection)
                {
                    System.Text.RegularExpressions.GroupCollection groupCollection = match.Groups;
                    ////log.Debug("group one is " + groupCollection[1]);
                    
                    string environmentVariable = groupCollection[1].ToString();
                    log.Debug("environmentVariable is " + environmentVariable);
                    
                    string environmentValue = Environment.GetEnvironmentVariable(environmentVariable);
                    log.Debug("environmentValue is " + environmentValue);
                    
                    if (environmentValue == null)
                    {
                        environmentValue = string.Empty;
                    }

                    sourceString = adjustEnvRegex.Replace(sourceString, environmentValue, 1);
                    ////log.Debug("sourceString is " + sourceString);
                }
            }

            ////log.Debug("completed substituting environment");
            document.InnerXml = sourceString;
            ////log.Debug("finishing SubstituteEnvironment()");
            return document;
        }

        /// <summary>
        /// The substitute test variables with app settings in application configuration file.
        /// </summary>
        /// <param name="document">The document to proceed.</param>
        /// <returns>The <see cref="XmlDocument"/> with replaced variables with specified values from application configuration file.</returns>
        private static XmlDocument SubstituteTestVariablesWithAppSettings(XmlDocument document)
        {
            string sourceString = document.InnerXml;
            // Get all app settings.
            NameValueCollection appSettingsCollection = ConfigurationManager.AppSettings;

            if (appSettingsCollection.Count == 0)
            {
                throw new ConfigurationErrorsException("No application settings were found in application configuration file.");
            }

            Dictionary<string, string> appSettings = appSettingsCollection.Cast<string>().Select(key => new { Key = key.ToLower(), Value = appSettingsCollection[key] }).ToDictionary(dict => dict.Key, dict => dict.Value);
            // Create RegEx for variables in test and RegEx to adjust it with any non-word chars.
            Regex adjustEnvRegex = new Regex(@"\$env(\w*)");
            Regex variablesRegex = new Regex(@"[^\p{L}\p{N}]+");
            // Proceed with look over all variables in a test.
            while (sourceString.Contains("$env"))
            {
                // Get all variables that match the RegEx.
                MatchCollection matchCollection = adjustEnvRegex.Matches(sourceString);
                foreach (Match match in matchCollection)
                {
                    // Get all groups for the match. The variable is placed in the first group.
                    GroupCollection groupCollection = match.Groups;
                    string variableName = variablesRegex.Replace(groupCollection[1].ToString(), string.Empty).ToLower();
                    // Get value from app.config for got key.
                    // If it was not found - throw the exception.
                    if (appSettings.ContainsKey(variableName))
                    {
                        string appConfigValue = appSettings[variableName];
                        sourceString = adjustEnvRegex.Replace(sourceString, appConfigValue, 1);
                    }
                    else
                    {
                        throw new SettingsPropertyNotFoundException(string.Format("The variable '{0}' was not replaced because '{1}' setting was not found in application configuration.", groupCollection[1], variableName));
                    }
                }
            }

            document.InnerXml = sourceString;
            /* Substituted all $env variables */
            return document;
        }

        public static void InitializeEnvironment(XmlElement documentElement)
        {
            try
            {
                string testFilesDirectory = string.Empty;
                string fullPathTestFilesDirectory = string.Empty;
                if (documentElement.SelectSingleNode("testfilesdirectory") != null)
                {
                    testFilesDirectory = Helper.GetStringNodeValue(documentElement, "testfilesdirectory", ".", false);
                }
                else if (Environment.GetEnvironmentVariable("TEST_FILES_DIRECTORY") != null && !Environment.GetEnvironmentVariable("TEST_FILES_DIRECTORY").Equals(string.Empty))
                {
                    testFilesDirectory = Environment.GetEnvironmentVariable("TEST_FILES_DIRECTORY");
                }

                fullPathTestFilesDirectory = Path.GetFullPath(testFilesDirectory);
                log.DebugFormat("fullPathTestFilesDirectory is {0}", fullPathTestFilesDirectory);
                string testEnvironmentBatchFile = "TestEnvironment.bat";
                string testLocalEnvironmentBatchFile = "TestLocalEnvironment.bat";
                string testEnvironmentFileName = @testFilesDirectory + Path.DirectorySeparatorChar + testEnvironmentBatchFile;
                log.DebugFormat("testEnvironmentFileName is {0}", testEnvironmentFileName);
                string testLocalEnvironmentFileName = @testFilesDirectory + Path.DirectorySeparatorChar + testLocalEnvironmentBatchFile;
                log.DebugFormat("testLocalEnvironmentFileName is {0}", testLocalEnvironmentFileName);
                string[] environmentFileNames = { testEnvironmentFileName, testLocalEnvironmentFileName };
                foreach (string environmentFileName in environmentFileNames)
                {
                    if (File.Exists(environmentFileName))
                    {
                        using (StreamReader streamReader = new StreamReader(environmentFileName))
                        {
                            string fileLine;
                            while ((fileLine = streamReader.ReadLine()) != null)
                            {
                                if (fileLine.StartsWith("SET"))
                                {
                                    string setEnvironmentVariable = fileLine.Substring(4, fileLine.Length - 4);
                                    ////log.Debug(" setEnvironmentVariable is " + setEnvironmentVariable);
                                    int indexOfNameVariableEquals = setEnvironmentVariable.IndexOf('=');
                                    string environmentVariableName = setEnvironmentVariable.Substring(0, indexOfNameVariableEquals);
                                    ////log.Debug(" environmentVariableName is " + environmentVariableName);
                                    string environmentVariableValue = setEnvironmentVariable.Substring(indexOfNameVariableEquals + 1);
                                    ////log.Debug(" environmentVariableValue is " + environmentVariableValue);
                                    /* If environment variable references other environment variables, then substitue these */
                                    while (environmentVariableValue.Contains('%'))
                                    {
                                        ////log.Debug(" environmentVariableValue contains environment variable");
                                        int firstIndexPercent = environmentVariableValue.IndexOf('%');
                                        /////log.Debug(" firstIndexPercent is " + firstIndexPercent);
                                        int secondIndexPercent = environmentVariableValue.IndexOf('%', firstIndexPercent + 1);
                                        ////log.Debug(" secondIndexPercent is " + secondIndexPercent);
                                        string subEnvironmentVariableName = environmentVariableValue.Substring(firstIndexPercent + 1, secondIndexPercent - (firstIndexPercent + 1));
                                        ////log.Debug(" subEnvironmentVariableName is " + subEnvironmentVariableName);
                                        string subEnvironmentVariableValue = Environment.GetEnvironmentVariable(subEnvironmentVariableName);
                                        ////log.Debug(" subEnvironmentVariableValue is " + subEnvironmentVariableValue);
                                        environmentVariableValue = environmentVariableValue.Replace('%' + subEnvironmentVariableName + '%', subEnvironmentVariableValue);
                                    }

                                    log.Debug("about to set " + environmentVariableName + " to " + environmentVariableValue);
                                    Environment.SetEnvironmentVariable(environmentVariableName, environmentVariableValue);
                                }
                            }
                        }
                    }
                    else
                    {
                        log.Warn("environment file does not exist " + environmentFileName);
                    }
                }

                log.Info("Success initializing environment");
            }
            catch (Exception e)
            {
                string exceptionMessage = "Exception initializing environment " + e.Message;
                log.Warn(exceptionMessage);
                log.Warn(e.InnerException);
            }
        }

        /// <summary>
        /// This method performs each operation. It reads the operation XML element and then
        /// invokes the corresponding Action class based on the name attribute of the element.
        /// Success or failure of the operation is recorded in m_OperationResult.
        /// </summary>
        /// <param name="operationDocument">The operation document.</param>
        /// <returns>The result of performing the operation.</returns>
        public static OperationResult PerformOperation(XmlDocument operationDocument)
        {
            bool fillZeros = true;
            bool operationResultBool = true;
            int endLoop = 0;
            int startLoop = 0;
            int subEndLoop = 0;
            int subStartLoop = 0;
            int sleep = 0;
            string conditionalName = string.Empty;
            try
            {
                string operationID = Helper.GetStringNodeValue(operationDocument, "/operation/@id", string.Empty, false);
                log.InfoFormat("operation id is {0}", operationID);
                bool executeBool = Helper.GetBooleanNodeValueDefaultTrue(operationDocument, "/operation/@execute", false);
                if (!executeBool)
                {
                    string checkExecuteMessage = "Execute is false";
                    log.Info(checkExecuteMessage);
                    return new OperationResult(true, checkExecuteMessage);
                }

                /* If a conditional is specified, then check that it is true, otherwise return immediately */
                XmlNode checkConditionalNode = operationDocument.SelectSingleNode("/operation/@checkconditional");
                if (checkConditionalNode != null)
                {
                    string checkConditionalName = Helper.GetStringNodeValue(operationDocument, "/operation/@checkconditional", string.Empty, false);
                    log.Info("check conditional name is " + checkConditionalName);
                    bool checkConditionalBool = true;
                    checkConditionalBool = Helper.GetConditional(checkConditionalName);
                    if (!checkConditionalBool)
                    {
                        string checkConditionalMessage = "Conditional " + checkConditionalName + " is false";
                        log.Info(checkConditionalMessage);
                        return new OperationResult(true, checkConditionalMessage);
                    }
                }

                /* If a not conditional is specified, then check that it is not true, otherwise return immediately */
                XmlNode checkNotConditionalNode = operationDocument.SelectSingleNode("/operation/@checknotconditional");
                if (checkNotConditionalNode != null)
                {
                    string checkNotConditionalName = Helper.GetStringNodeValue(operationDocument, "/operation/@checknotconditional", string.Empty, false);
                    log.Info("check not conditional name is " + checkNotConditionalName);
                    bool checkNotConditionalBool = true;
                    checkNotConditionalBool = Helper.GetConditional(checkNotConditionalName);
                    if (checkNotConditionalBool)
                    {
                        string checkNotConditionalMessage = "Not conditional " + checkNotConditionalName + " is true";
                        log.Info(checkNotConditionalMessage);
                        return new OperationResult(true, checkNotConditionalMessage);
                    }
                }

                Type actionType = null;
                XmlNode operationClassNode = operationDocument.SelectSingleNode("/operation/@class");
                if (operationClassNode != null)
                {
                    string actionClass = operationClassNode.Value;
                    log.DebugFormat("action class is {0}", actionClass);
                    string[] actionAssemblyClass = actionClass.Split('.');
                    string assembly = actionAssemblyClass[0];
                    //log.DebugFormat("assembly is {0}", assembly);
                    string actionClassAssembly = actionClass + ", " + assembly;
                    //log.DebugFormat("actionClassAssembly is {0}", actionClassAssembly);
                    actionType = Type.GetType(actionClassAssembly, true);
                }
                else
                {
                    log.Info("operation class node is null");
                    string actionOperation = "XCaseGeneric.ActionDoNothing,XCaseGeneric";
                    actionType = Type.GetType(actionOperation, true);
                }

                XmlNode operationFillZerosNode = operationDocument.SelectSingleNode("/operation/@fillzeros");
                if (operationFillZerosNode != null)
                {
                    string fillZerosString = operationFillZerosNode.Value;
                    log.Info("fillZerosString is " + fillZerosString);
                    bool.TryParse(fillZerosString, out fillZeros);
                }

                XmlNode operationSleepNode = operationDocument.SelectSingleNode("/operation/@sleep");
                if (operationSleepNode != null)
                {
                    string operationSleep = operationSleepNode.Value;
                    log.Info("operation sleep is " + operationSleep);
                    sleep = int.Parse(operationSleep) * 1000;
                }

                XmlNode operationSetConditionalNode = operationDocument.SelectSingleNode("/operation/@setconditional");
                if (operationSetConditionalNode != null)
                {
                    conditionalName = operationSetConditionalNode.Value;
                    log.Info("conditional name is " + conditionalName);
                }

                IAction action = (IAction)Activator.CreateInstance(actionType);
                OperationResult returnOperationResult = null;
                OperationResult localOperationResult = null;
                /* Start calculate start and end of loops based on input parameters */
                startLoop = CalculateStartLoop(operationDocument);
                /* log.Debug("startLoop is " + startLoop); */
                endLoop = CalculateEndLoop(operationDocument);
                /* log.Debug("endLoop is " + endLoop); */
                subStartLoop = CalculateSubStartLoop(operationDocument);
                /* log.Debug("subStartLoop is " + subStartLoop); */
                /* Finish calculate start and end of main loop and start of sub-loop based on input parameters */
                for (int i = startLoop; i <= endLoop; i++)
                {
                    /* Calculate end of sub-loop based on input parameters */
                    subEndLoop = CalculateSubEndLoop(operationDocument);
                    /* log.Debug("subEndLoop is " + subEndLoop); */
                    for (int j = subStartLoop; j <= subEndLoop; j++)
                    {
                        XmlDocument indexDocument = (XmlDocument)operationDocument.Clone();
                        for (int k = 0; k < 3; k++)
                        {
                            indexDocument = SubstituteGUIDN(indexDocument, k);
                        }

                        if (endLoop > 0)
                        {
                            indexDocument = SubstituteIndex(indexDocument, i, NumberOfDigits(endLoop), fillZeros);
                        }

                        if (subEndLoop > 0)
                        {
                            indexDocument = SubstituteSubindex(indexDocument, j, NumberOfDigits(subEndLoop), fillZeros);
                        }

                        try
                        {
                            localOperationResult = action.Execute(indexDocument);
                            //log.Debug("local operation result is " + localOperationResult.Result);
                        }
                        catch (Exception e)
                        {
                            log.Debug("catching operation exception");
                            if (indexDocument.SelectSingleNode("/operation/@exceptionexpected") != null)
                            {
                                if (indexDocument.SelectSingleNode("/operation/@exceptionexpected").Value == "true")
                                {
                                    string message = "Operation exception is expected";
                                    log.Debug(message);
                                    localOperationResult = new OperationResult(true, message);
                                }
                                else
                                {
                                    string message = "Operation exception is not expected";
                                    log.Debug(message);
                                    localOperationResult = new OperationResult(false, message);
                                }
                            }
                            else
                            {
                                log.Info("exception in subloop " + i + "-" + j + " " + e.ToString());
                                localOperationResult = new OperationResult(false, e.Message);
                            }
                        }

                        operationResultBool = operationResultBool && localOperationResult.Result;
                        ////log.Info("completed subloop " + i + "-" + j);
                    }

                    ////log.Info("completed loop " + i);
                    System.Threading.Thread.Sleep(sleep);
                }

                log.InfoFormat("operation result is {0}", operationResultBool);
                if (!conditionalName.Equals(string.Empty))
                {
                    if (operationResultBool)
                    {
                        ////conditionalsDictionary.Add(conditionalName, true);
                        Helper.SetConditional(conditionalName, true);
                    }
                    else
                    {
                        ////conditionalsDictionary.Add(conditionalName, false);
                        Helper.SetConditional(conditionalName, false);
                    }
                }

                returnOperationResult = new OperationResult(operationResultBool, localOperationResult.Message);
                return returnOperationResult;
            }
            catch (Exception e)
            {
                string exceptionMessage = "Exception executing operation " + e.Message;
                log.Warn(exceptionMessage);
                return new OperationResult(false, exceptionMessage);
            }
        }

        /// <summary>
        /// This method calculates the value to start the operation loop.
        /// </summary>
        /// <param name="operationDocument">The operation element can contain an optional attribute startloop.</param>
        /// <returns>The value of startloop if set.</returns>
        public static int CalculateStartLoop(XmlDocument operationDocument)
        {
            ////log.Debug("starting CalculateStartLoop()");
            int startLoop = 0;
            XmlNode startLoopNode = operationDocument.SelectSingleNode("/operation/@startloop");
            if (startLoopNode != null)
            {
                if (int.TryParse(startLoopNode.Value, out startLoop))
                {
                    log.Debug("startLoop is " + startLoop);
                }
            }

            return startLoop;
        }

        /// <summary>
        /// This method calculates the value to end the operation loop.
        /// </summary>
        /// <param name="operationDocument">The operation document.</param>
        /// <returns>The last value used by the loop operation.</returns>
        public static int CalculateEndLoop(XmlDocument operationDocument)
        {
            ////log.Debug("starting CalculateEndLoop()");
            int endLoop = 0;
            int repeat = 0;
            XmlNode endLoopNode = operationDocument.SelectSingleNode("/operation/@endloop");
            if (endLoopNode != null)
            {
                if (int.TryParse(endLoopNode.Value, out endLoop))
                {
                    log.Debug("endLoop is " + endLoop);
                }
            }
            else
            {
                XmlNode repeatNode = operationDocument.SelectSingleNode("/operation/@repeat");
                if (repeatNode != null)
                {
                    string repeatString = repeatNode.Value;
                    if (repeatString.StartsWith("$rand:"))
                    {
                        int rangeInt = 0;
                        string rangeString = repeatString.Substring(6);
                        log.Debug("rangeString is " + rangeString);
                        if (int.TryParse(rangeString, out rangeInt))
                        {
                            repeat = (new System.Random()).Next(rangeInt);
                            log.Debug("repeat is " + repeat);
                            endLoop = repeat - 1;
                        }
                    }
                    else if (int.TryParse(repeatString, out repeat))
                    {
                        log.Debug("repeat is " + repeat);
                        endLoop = repeat - 1;
                    }
                }
            }

            return endLoop;
        }

        /// <summary>
        /// This method calculates the value to start the operation subloop.
        /// </summary>
        /// <param name="operationDocument">The operation document.</param>
        /// <returns>The start value of the start loop.</returns>
        public static int CalculateSubStartLoop(XmlDocument operationDocument)
        {
            ////log.Debug("starting CalculateSubStartLoop()");
            int subStartLoop = 0;
            XmlNode subStartLoopNode = operationDocument.SelectSingleNode("/operation/@substartloop");
            if (subStartLoopNode != null)
            {
                if (int.TryParse(subStartLoopNode.Value, out subStartLoop))
                {
                    log.Debug("subStartLoop is " + subStartLoop);
                }
            }

            return subStartLoop;
        }

        /// <summary>
        /// This method calculates the value to end the operation subloop.
        /// </summary>
        /// <param name="operationDocument">The operation document.</param>
        /// <returns>The end value for the subloop.</returns>
        public static int CalculateSubEndLoop(XmlDocument operationDocument)
        {
            ////log.Debug("starting CalculateSubEndLoop()");
            int subEndLoop = 0;
            int subRepeat = 0;
            XmlNode subEndLoopNode = operationDocument.SelectSingleNode("/operation/@subendloop");
            if (subEndLoopNode != null)
            {
                if (int.TryParse(subEndLoopNode.Value, out subEndLoop))
                {
                    log.Debug("subEndLoop is " + subEndLoop);
                }
            }
            else
            {
                XmlNode subRepeatNode = operationDocument.SelectSingleNode("/operation/@subrepeat");
                if (subRepeatNode != null)
                {
                    string subRepeatString = subRepeatNode.Value;
                    if (subRepeatString.StartsWith("$rand:"))
                    {
                        int subrangeInt = 1;
                        string rangeString = subRepeatString.Substring(6);
                        log.Debug("rangeString is " + rangeString);
                        if (int.TryParse(rangeString, out subrangeInt))
                        {
                            subRepeat = (new System.Random()).Next(subrangeInt);
                            log.Debug("subRepeat is " + subRepeat);
                            subEndLoop = subRepeat - 1;
                        }
                    }
                    else if (int.TryParse(subRepeatNode.Value, out subRepeat))
                    {
                        log.Debug("subRepeat is " + subRepeat);
                        subEndLoop = subRepeat - 1;
                    }
                }
            }

            /* log.Debug("subEndLoop is " + subEndLoop); */
            return subEndLoop;
        }

        /// <summary>
        /// This method will replace all occurrences of $index with the current
        /// value of index.
        /// </summary>
        /// <param name="sourceDocument">The operation document.</param>
        /// <param name="index">The current value of the subindex.</param>
        /// <param name="fillLength">The length of the replacing string.</param>
        /// <param name="fillZeros">Whether to fill the replacing string with zeros when the value of the index has fewer digits than the fillLength.</param>
        /// <returns>The processed operation document.</returns>
        public static XmlDocument SubstituteIndex(XmlDocument sourceDocument, int index, int fillLength, bool fillZeros)
        {
            ////log.Debug("starting SubstituteIndex()");
            string sourceString = sourceDocument.InnerXml;
            string indexString = Convert.ToString(index);
            if (fillZeros)
            {
                string indexFillString = "D" + fillLength;
                log.Debug("indexFillString is " + indexFillString);
                indexString = string.Format("{0:" + indexFillString + "}", index);
            }

            string targetString = sourceString.Replace("$index", indexString);
            sourceDocument.InnerXml = targetString;
            return sourceDocument;
        }

        /// <summary>
        /// This method will replace all occurrences of $subindex with the current
        /// value of subindex.
        /// </summary>
        /// <param name="sourceDocument">The operation document.</param>
        /// <param name="subindex">The current value of the subindex.</param>
        /// <param name="fillLength">The length of the replacing string.</param>
        /// <param name="fillZeros">Whether to fill the replacing string with zeros when the value of the index has fewer digits than the fillLength.</param>
        /// <returns>The processed operation document.</returns>
        public static XmlDocument SubstituteSubindex(XmlDocument sourceDocument, int subindex, int fillLength, bool fillZeros)
        {
            ////log.Debug("starting SubstituteSubindex()");
            string sourceString = sourceDocument.InnerXml;
            string subindexString = Convert.ToString(subindex);
            if (fillZeros)
            {
                string subindexFillString = "D" + fillLength;
                log.Debug("subindexFillString is " + subindexFillString);
                subindexString = string.Format("{0:" + subindexFillString + "}", subindex);
            }

            string targetString = sourceString.Replace("$subindex", subindexString);
            sourceDocument.InnerXml = targetString;
            return sourceDocument;
        }

        /// <summary>
        /// This method returns the number of digits in an integer.
        /// </summary>
        /// <param name="i">An integer.</param>
        /// <returns>The number of digits (base 10) in the integer.</returns>
        public static int NumberOfDigits(int i)
        {
            return (int)Math.Floor(Math.Log10(i)) + 1;
        }

        /// <summary>
        /// This method performs all the substituions of dates and times in the operation document.
        /// </summary>
        /// <param name="sourceDocument">The operation document.</param>
        /// <returns>The operation document with all $datetime strings processed.</returns>
        public static XmlDocument SubstituteDateTime(XmlDocument sourceDocument)
        {
            log.Debug("starting SubstituteDateTime()");
            string documentString = sourceDocument.InnerXml;
            string dateTime = DateTime.Now.ToString();
            documentString = documentString.Replace("$datetime", dateTime);
            TimeZone localTimeZone = System.TimeZone.CurrentTimeZone;
            log.Debug("localTimeZone is " + localTimeZone.StandardName);
            TimeSpan localTimeZoneOffset = localTimeZone.GetUtcOffset(DateTime.Now);
            DateTime localDateTime = DateTime.Now.Subtract(localTimeZoneOffset);
            string utcDateTime = localDateTime.ToString();
            documentString = documentString.Replace("$utcdatetime", utcDateTime);
            string adjustedDocumentString = AdjustDateTimes(documentString);
            sourceDocument.InnerXml = adjustedDocumentString;
            return sourceDocument;
        }

        /// <summary>
        /// Adjusts datetimes in the form $adjustdatetime(-minus for shift to past, Days, Hours, Minutes, Seconds).
        /// </summary>
        /// <param name="sourceString">The source $adjustdatetime string.</param>
        /// <returns>The adjusted string.</returns>
        public static string AdjustDateTimes(string sourceString)
        {
            log.Debug("starting AdjustDateTimes()");
            while (sourceString.Contains("$adjustdatetime"))
            {
                log.Debug("source string contains $adjustdatetime");
                System.Text.RegularExpressions.Regex adjustDatetimeRegex = new System.Text.RegularExpressions.Regex(@"\$adjustdatetime\((\-,)?(\d*)??,?(\d*),(\d*),(\d*)(?:\,(?<midnight>m))?\)");
                System.Text.RegularExpressions.MatchCollection matchCollection = adjustDatetimeRegex.Matches(sourceString);
                log.Debug("created match collection");
                if (matchCollection.Count > 0)
                {
                    log.Debug("match collection count is " + matchCollection.Count);
                    foreach (System.Text.RegularExpressions.Match match in matchCollection)
                    {
                        System.Text.RegularExpressions.GroupCollection groupCollection = match.Groups;
                        DateTime adjustedDateTime = DateTime.Now;
                        log.Debug("found minus sign: " + groupCollection[1].Success);
                        log.Debug("optional group (days) is " + groupCollection[2]);

                        try
                        {
                            int days = groupCollection[2].Success ? int.Parse(groupCollection[2].ToString()) : 0;
                            int hours = int.Parse(groupCollection[3].ToString());
                            int minutes = int.Parse(groupCollection[4].ToString());
                            int seconds = int.Parse(groupCollection[5].ToString());

                            TimeSpan timeSpan = new TimeSpan(days, hours, minutes, seconds);

                            if (!string.IsNullOrEmpty(groupCollection[1].ToString()))
                            {
                                log.Debug("optional group is not null or empty");
                                adjustedDateTime = adjustedDateTime - timeSpan;
                            }
                            else
                            {
                                log.Debug("optional group is not null or empty");
                                adjustedDateTime = adjustedDateTime + timeSpan;
                            }

                            log.Debug("adjusted datetime");
                        }
                        catch (Exception)
                        {
                            log.Warn("failed to parse adjustment");
                        }

                        // Check flag if need to reset time to midnight.
                        if (groupCollection["midnight"].Success)
                        {
                            log.Debug("optional group (midnight) is null or empty");
                            adjustedDateTime = adjustedDateTime.Date;
                        }

                        string tobereplacedString = groupCollection[0].ToString();
                        sourceString = sourceString.Replace(tobereplacedString, adjustedDateTime.ToString());
                    }
                }
                else
                {
                    string exceptionMessage = "Match collection count is zero for $adjustdatetime";
                    log.Warn(exceptionMessage);
                    throw new Exception(exceptionMessage);
                }
            }

            while (sourceString.Contains("$utcadjustdatetime"))
            {
                log.Debug("source string contains $utcadjustdatetime");
                System.Text.RegularExpressions.Regex utcAdjustDatetimeRegex = new System.Text.RegularExpressions.Regex(@"\$utcadjustdatetime\((\-,)?(\d*)??,?(\d*),(\d*),(\d*)(?:\,(?<midnight>m))?\)");
                System.Text.RegularExpressions.MatchCollection matchCollection = utcAdjustDatetimeRegex.Matches(sourceString);
                log.Debug("created match collection");
                if (matchCollection.Count > 0)
                {
                    log.Debug("match collection count is " + matchCollection.Count);
                    foreach (System.Text.RegularExpressions.Match match in matchCollection)
                    {
                        log.Debug("next match");
                        System.Text.RegularExpressions.GroupCollection groupCollection = match.Groups;
                        TimeSpan localTimeZoneOffset = System.TimeZone.CurrentTimeZone.GetUtcOffset(DateTime.Now);
                        log.Debug("got local timezone offset and zero timespan");
                        DateTime adjustedDateTime = DateTime.Now;
                        log.Debug("optional group is " + groupCollection[1]);
                        try
                        {
                            int days = groupCollection[2].Success ? int.Parse(groupCollection[2].ToString()) : 0;
                            int hours = int.Parse(groupCollection[3].ToString());
                            int minutes = int.Parse(groupCollection[4].ToString());
                            int seconds = int.Parse(groupCollection[5].ToString());
                            TimeSpan timeSpan = new TimeSpan(days, hours, minutes, seconds);
                            if (!string.IsNullOrEmpty(groupCollection[1].ToString()))
                            {
                                log.Debug("optional group is not null or empty");
                                adjustedDateTime = adjustedDateTime - timeSpan;
                            }
                            else
                            {
                                log.Debug("optional group is not null or empty");
                                adjustedDateTime = adjustedDateTime + timeSpan;
                            }

                            log.Debug("adjusted date time is " + adjustedDateTime);
                        }
                        catch (Exception e)
                        {
                            log.Warn("failed to parse adjustment " + e.Message);
                        }

                        DateTime utcAdjustedDateTime = adjustedDateTime.Subtract(localTimeZoneOffset);
                        log.Debug("UTC adjusted date time is " + utcAdjustedDateTime);

                        /* Check flag if need to reset time to midnight. */
                        if (groupCollection["midnight"].Success)
                        {
                            log.Debug("optional group (midnight) is null or empty");
                            utcAdjustedDateTime = utcAdjustedDateTime.Date.ToUniversalTime();
                        }

                        string tobereplacedString = groupCollection[0].ToString();
                        log.Debug("tobereplacedString is " + tobereplacedString);
                        sourceString = sourceString.Replace(tobereplacedString, utcAdjustedDateTime.ToString());
                    }
                }
                else
                {
                    string exceptionMessage = "Match collection count is zero for $utcadjustdatetime";
                    log.Warn(exceptionMessage);
                    throw new Exception(exceptionMessage);
                }
            }

            while (sourceString.Contains("$absolutedatetime"))
            {
                log.Debug("source string contains $absolutedatetime");
                System.Text.RegularExpressions.Regex absoluteDatetimeRegex = new System.Text.RegularExpressions.Regex(@"\$absolutedatetime\(((?<date>\d*/\d*/\d*),)?(\d*),(\d*),(\d*)\)");
                System.Text.RegularExpressions.MatchCollection matchCollection = absoluteDatetimeRegex.Matches(sourceString);
                log.Debug("created match collection");
                if (matchCollection.Count > 0)
                {
                    log.Debug("match collection count is " + matchCollection.Count);
                    foreach (System.Text.RegularExpressions.Match match in matchCollection)
                    {
                        log.Debug("next match");
                        System.Text.RegularExpressions.GroupCollection groupCollection = match.Groups;
                        TimeSpan localTimeZoneOffset = System.TimeZone.CurrentTimeZone.GetUtcOffset(DateTime.Now);
                        DateTime absoluteDateTime = new DateTime();
                        string absoluteTime = null;
                        log.Debug("optional group is " + groupCollection["date"]);
                        if (!groupCollection["date"].Success)
                        {
                            log.Debug("optional group is null or empty");
                            absoluteTime = DateTime.Now.Date.ToString("M/d/yyyy") + " " + groupCollection[2] + ":" + groupCollection[3] + ":" + groupCollection[4];
                        }
                        else
                        {
                            log.Debug("optional group is not null or empty " + groupCollection["date"].ToString());
                            absoluteTime = groupCollection["date"] + " " + groupCollection[2] + ":" + groupCollection[3] + ":" + groupCollection[4];
                        }

                        log.Debug("absoluteTime is " + absoluteTime);
                        if (DateTime.TryParse(absoluteTime, out absoluteDateTime))
                        {
                            log.Debug("absolute datetime is " + absoluteDateTime);
                        }
                        else
                        {
                            log.Warn("failed to parse absolute time");
                        }

                        string tobereplacedString = groupCollection[0].ToString();
                        sourceString = sourceString.Replace(tobereplacedString, absoluteDateTime.ToString());
                    }
                }
                else
                {
                    string exceptionMessage = "Match collection count is zero for $absolutedatetime";
                    log.Warn(exceptionMessage);
                    throw new Exception(exceptionMessage);
                }
            }

            while (sourceString.Contains("$utcabsolutedatetime"))
            {
                log.Debug("source string contains $utcabsolutedatetime");
                System.Text.RegularExpressions.Regex absoluteDatetimeRegex = new System.Text.RegularExpressions.Regex(@"\$utcabsolutedatetime\(((?<date>\d*/\d*/\d*),)?(\d*),(\d*),(\d*)\)");
                System.Text.RegularExpressions.MatchCollection matchCollection = absoluteDatetimeRegex.Matches(sourceString);
                log.Debug("created match collection");
                if (matchCollection.Count > 0)
                {
                    log.Debug("match collection count is " + matchCollection.Count);
                    foreach (System.Text.RegularExpressions.Match match in matchCollection)
                    {
                        log.Debug("next match");
                        System.Text.RegularExpressions.GroupCollection groupCollection = match.Groups;
                        TimeSpan localTimeZoneOffset = System.TimeZone.CurrentTimeZone.GetUtcOffset(DateTime.Now);
                        DateTime absoluteDateTime = new DateTime();
                        string absoluteTime = null;
                        log.Debug("optional group is " + groupCollection["date"]);
                        if (!groupCollection["date"].Success)
                        {
                            log.Debug("optional group is null or empty");
                            absoluteTime = DateTime.UtcNow.Date.ToString("d") + " " + groupCollection[2] + ":" + groupCollection[3] + ":" + groupCollection[4];
                        }
                        else
                        {
                            log.Debug("optional group is not null or empty " + groupCollection["date"].ToString());
                            absoluteTime = groupCollection["date"] + " " + groupCollection[2] + ":" + groupCollection[3] + ":" + groupCollection[4];
                        }

                        log.Debug("absoluteTime is " + absoluteTime);
                        if (DateTime.TryParse(absoluteTime, out absoluteDateTime))
                        {
                            log.Debug("absolute datetime is " + absoluteDateTime);
                        }
                        else
                        {
                            log.Warn("failed to parse absolute time");
                        }

                        DateTime utcAbsoluteDateTime = absoluteDateTime.Subtract(localTimeZoneOffset);
                        string tobereplacedString = groupCollection[0].ToString();
                        sourceString = sourceString.Replace(tobereplacedString, utcAbsoluteDateTime.ToString());
                    }
                }
                else
                {
                    string exceptionMessage = "Match collection count is zero for $utcabsolutedatetime";
                    log.Warn(exceptionMessage);
                    throw new Exception(exceptionMessage);
                }
            }

            // Offset from the current day by a specified number of days.
            // Time is specified directly (Hours, Minutes and Seconds)
            // This means that if current date is 3/11/2014
            // and XML contain $adjustDateWithAbsoluteTime (-, 10, 14, 30, 0)
            // then result datetime is 3/2/2014 02:30:00 PM
            while (sourceString.Contains("$adjustDateWithAbsoluteTime"))
            {
                log.Debug("source string contains $adjustDateWithAbsoluteTime");
                System.Text.RegularExpressions.Regex adjustDatetimeRegex = new System.Text.RegularExpressions.Regex(@"\$adjustDateWithAbsoluteTime\((\-,)?(\d*)??,?(\d*),(\d*),(\d*)(?:\,(?<midnight>m))?\)");
                System.Text.RegularExpressions.MatchCollection matchCollection = adjustDatetimeRegex.Matches(sourceString);
                log.Debug("created match collection");
                if (matchCollection.Count > 0)
                {
                    log.Debug("match collection count is " + matchCollection.Count);
                    foreach (System.Text.RegularExpressions.Match match in matchCollection)
                    {
                        System.Text.RegularExpressions.GroupCollection groupCollection = match.Groups;
                        DateTime adjustedDateTime = DateTime.Today;
                        log.Debug("found minus sign: " + groupCollection[1].Success);
                        log.Debug("optional group (days) is " + groupCollection[2]);

                        try
                        {
                            int days = groupCollection[2].Success ? int.Parse(groupCollection[2].ToString()) : 0;
                            int hours = int.Parse(groupCollection[3].ToString());
                            int minutes = int.Parse(groupCollection[4].ToString());
                            int seconds = int.Parse(groupCollection[5].ToString());

                            TimeSpan timeSpan = new TimeSpan(days, 0, 0, 0);
                            TimeSpan absoluteTime = new TimeSpan(0, hours, minutes, seconds);

                            if (!string.IsNullOrEmpty(groupCollection[1].ToString()))
                            {
                                log.Debug("optional group is not null or empty");
                                adjustedDateTime = adjustedDateTime - timeSpan;
                            }
                            else
                            {
                                log.Debug("optional group is not null or empty");
                                adjustedDateTime = adjustedDateTime + timeSpan;
                            }

                            adjustedDateTime = adjustedDateTime + absoluteTime;

                            log.Debug("adjusted date with absolute time");
                        }
                        catch (Exception)
                        {
                            log.Warn("failed to parse adjustment");
                        }

                        string tobereplacedString = groupCollection[0].ToString();
                        sourceString = sourceString.Replace(tobereplacedString, adjustedDateTime.ToString(DateTimeFormat, CultureInfo.InvariantCulture));
                    }
                }
                else
                {
                    string exceptionMessage = "Match collection count is zero for $adjustDateWithAbsoluteTime";
                    log.Warn(exceptionMessage);
                    throw new Exception(exceptionMessage);
                }
            }

            while (sourceString.Contains("$utcAdjustDateWithAbsoluteTime"))
            {
                log.Debug("source string contains $utcAdjustDateWithAbsoluteTime");
                System.Text.RegularExpressions.Regex adjustDatetimeRegex = new System.Text.RegularExpressions.Regex(@"\$utcAdjustDateWithAbsoluteTime\((\-,)?(\d*)??,?(\d*),(\d*),(\d*)(?:\,(?<midnight>m))?\)");
                System.Text.RegularExpressions.MatchCollection matchCollection = adjustDatetimeRegex.Matches(sourceString);
                log.Debug("created match collection");
                if (matchCollection.Count > 0)
                {
                    log.Debug("match collection count is " + matchCollection.Count);
                    foreach (System.Text.RegularExpressions.Match match in matchCollection)
                    {
                        System.Text.RegularExpressions.GroupCollection groupCollection = match.Groups;
                        DateTime adjustedDateTime = DateTime.Today.ToUniversalTime();
                        log.Debug("found minus sign: " + groupCollection[1].Success);
                        log.Debug("optional group (days) is " + groupCollection[2]);

                        try
                        {
                            int days = groupCollection[2].Success ? int.Parse(groupCollection[2].ToString()) : 0;
                            int hours = int.Parse(groupCollection[3].ToString());
                            int minutes = int.Parse(groupCollection[4].ToString());
                            int seconds = int.Parse(groupCollection[5].ToString());

                            TimeSpan timeSpan = new TimeSpan(days, 0, 0, 0);
                            TimeSpan absoluteTime = new TimeSpan(0, hours, minutes, seconds);

                            if (!string.IsNullOrEmpty(groupCollection[1].ToString()))
                            {
                                log.Debug("optional group is not null or empty");
                                adjustedDateTime = adjustedDateTime - timeSpan;
                            }
                            else
                            {
                                log.Debug("optional group is not null or empty");
                                adjustedDateTime = adjustedDateTime + timeSpan;
                            }

                            adjustedDateTime = adjustedDateTime + absoluteTime;

                            log.Debug("adjusted date with absolute time");
                        }
                        catch (Exception)
                        {
                            log.Warn("failed to parse adjustment");
                        }

                        string tobereplacedString = groupCollection[0].ToString();
                        sourceString = sourceString.Replace(tobereplacedString, adjustedDateTime.ToString(DateTimeFormat, CultureInfo.InvariantCulture));
                    }
                }
                else
                {
                    string exceptionMessage = "Match collection count is zero for $utcAdjustDateWithAbsoluteTime";
                    log.Warn(exceptionMessage);
                    throw new Exception(exceptionMessage);
                }
            }

            return sourceString;
        }

        public static XmlDocument SubstituteGUIDStrings(XmlDocument sourceDocument)
        {
            string documentString = sourceDocument.InnerXml;
            Regex guidRegex = new Regex("(\\$guid)[a-zA-Z0-9]+");
            while (guidRegex.IsMatch(documentString))
            {
                string guidString = System.Guid.NewGuid().ToString();
                Match match = guidRegex.Match(documentString);
                Regex matchRegex = new Regex(string.Format("\\{0}", match.Value));
                documentString = matchRegex.Replace(documentString, guidString);
            }

            sourceDocument.InnerXml = documentString;
            return sourceDocument;
        }


        /// <summary>
        /// This method generates GUIDs when reuqired to create unique strings in operations.
        /// </summary>
        /// <param name="sourceDocument">The operation document.</param>
        /// <returns>The updated operation document.</returns>
        public static XmlDocument SubstituteGUID(XmlDocument sourceDocument)
        {
            ////log.Debug("starting SubstituteGUID()");
            string documentString = sourceDocument.InnerXml;
            for (int i = 0; i < 11; i++)
            {
                string guidString = System.Guid.NewGuid().ToString();
                documentString = documentString.Replace("$guid" + i, guidString);
            }

            sourceDocument.InnerXml = documentString;
            return sourceDocument;
        }

        /// <summary>
        /// This method generates GUIDs when reuqired to create unique strings in operations.
        /// </summary>
        /// <param name="sourceDocument">The operation document.</param>
        /// <param name="i">An index to keep track of strings that must have the same GUID.</param>
        /// <returns>The updated operation document.</returns>
        public static XmlDocument SubstituteGUIDN(XmlDocument sourceDocument, int i)
        {
            ////log.Debug("starting SubstituteGUIDN()");
            string sourceString = sourceDocument.InnerXml;
            string guidString = System.Guid.NewGuid().ToString();
            string targetString = sourceString.Replace("$guidn" + i, guidString);
            sourceDocument.InnerXml = targetString;
            return sourceDocument;
        }

        /// <summary>
        /// This method generates random numbers when required in operations.
        /// </summary>
        /// <param name="sourceDocument">The operation document.</param>
        /// <returns>The processed operation document.</returns>
        public static XmlDocument SubstituteRandom(XmlDocument sourceDocument)
        {
            ////log.Debug("starting SubstituteRandom()");
            string sourceString = sourceDocument.InnerXml;
            for (int i = 0; i < 10; i++)
            {
                while (sourceString.Contains("$rand" + i))
                {
                    log.Debug("source string contains $rand" + i);
                    string regexString = @"\$rand" + i.ToString() + @":(\d*)";
                    log.Debug("regexString is " + regexString);
                    System.Text.RegularExpressions.Regex adjustRandomRegex = new System.Text.RegularExpressions.Regex(regexString);
                    System.Text.RegularExpressions.MatchCollection matchCollection = adjustRandomRegex.Matches(sourceString);
                    log.Debug("number of matches is " + matchCollection.Count);
                    foreach (System.Text.RegularExpressions.Match match in matchCollection)
                    {
                        log.Debug("processing next match");
                        System.Text.RegularExpressions.GroupCollection groupCollection = match.Groups;
                        string tobereplacedString = groupCollection[0].ToString();
                        log.Debug("tobereplacedString is " + tobereplacedString);
                        int randomInt = 0;
                        int rangeInt = 0;
                        string rangeString = groupCollection[1].ToString();
                        log.Debug("rangeString is " + rangeString);
                        if (int.TryParse(rangeString, out rangeInt))
                        {
                            randomInt = (new System.Random()).Next(rangeInt);
                            log.Debug("randomInt is " + randomInt);
                        }

                        log.Debug("sourceString is " + sourceString);
                        sourceString = sourceString.Replace(tobereplacedString, randomInt.ToString());
                        log.Debug("sourceString is " + sourceString);
                    }
                }
            }

            sourceDocument.InnerXml = sourceString;
            log.Debug("finishing substituteRandom()");
            return sourceDocument;
        }
    }
}
