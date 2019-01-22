namespace XCaseGeneric
{
    using System;
    using System.Xml;
    using log4net;
    using XCaseBase;

    /// <summary>
    /// This action class deletes one or more datasources. The XML file should be of this form:
    /// <operation id="Delete FTP datasources" class="XCaseGeneric.ActionDeleteDatasource">
    ///     <datasource name="datasourcename1" />
    ///     <datasource name="datasourcename2" />
    /// </operation>
    /// Its purpose is to remove the datasources from the Helper dictionary.
    /// </summary>
    public class ActionDeleteDatasource : ActionAbstract
    {
        #region Logger Setup
        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILog log = LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        #endregion

        /// <summary>
        /// This method deletes one or more datasources.
        /// </summary>
        /// <param name="document">The document parameter.</param>
        /// <returns>The operation result.</returns>
        public override OperationResult Execute(XmlDocument document)
        {
            log.Debug("starting Execute()");
            try
            {
                OperationResult operationResult = new OperationResult("Success deleting datasource(s)");
                XmlNodeList datasourceNodeList = document.SelectNodes("operation/datasource");
                foreach (XmlNode datasourceNode in datasourceNodeList)
                {
                    string name = Helper.GetStringNodeValue(datasourceNode, "@name", true);
                    Helper.GetHelperDictionary().Remove(name);
                    log.Debug("removed datasource " + name);
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
