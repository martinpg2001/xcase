namespace XCaseGeneric
{
    using System;
    using System.Xml;
    using System.Diagnostics;
    using log4net;
    using XCaseBase;

    /// <summary>
    /// This action class does nothing. Its purpose is to check that the test
    /// framework is working correctly. The XML file should be of this form:
    /// <operation id="Do Nothing" class="XCaseGeneric.ActionDoNothing">   
    /// </operation>
    /// You can use this class to set conditionals because it always returns true.
    /// </summary>
    public class ActionDeleteProcesses : ActionAbstract
    {
        #region Logger Setup
        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILog log = LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        #endregion

        /// <summary>
        /// This method returns true.
        /// </summary>
        /// You can also use this class to set conditionals because it always returns true.
        /// <param name="document">The operation document.</param>
        /// <returns>Returns true.</returns>
        public override OperationResult Execute(XmlDocument document)
        {
            try
            {
                OperationResult operationResult = new OperationResult("Success deleting process(es)");
                XmlNodeList processNodeList = document.SelectNodes("operation/processes/process");
                foreach (XmlNode processNode in processNodeList)
                {
                    string name = Helper.GetStringNodeValue(processNode, "@name", true);
                    Process[] processes = Process.GetProcessesByName(name);
                    foreach (Process process in processes)
                    {
                        log.DebugFormat("Driver Name is: {0}", process.ProcessName);
                        if (process.ProcessName == name)
                        {
                            log.DebugFormat("about to kill the process: {0}", process.ProcessName);
                            process.Kill();
                        }
                    }
                }

                return operationResult;
            }
            catch (Exception e)
            {
                string exceptionMessage = "Exception deleting datasource(s) " + e.Message;
                log.Warn(exceptionMessage);
                return new OperationResult(false, exceptionMessage);
            }
        }
    }
}
