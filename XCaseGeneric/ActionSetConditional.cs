namespace XCaseGeneric
{
    using System;
    using System.Xml;
    using XCaseBase;
    using log4net;

    public class ActionSetConditional : ActionAbstract
    {
        #region Logger Setup
        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILog log = LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        #endregion

        /// <summary>
        /// This method sets conditional values. The XML document should be of the form:
        /// <operation id="Set conditionals" class="XCaseGeneric.ActionSetConditional" >
        ///     <conditional name="conditional" value="true|false" />
        ///     ...
        /// </operation>
        /// The operation returns false if any of the conditional elements is missing its name or value attributes.
        /// </summary>
        /// <param name="document">The document parameter.</param>
        /// <returns>The OperationResult object.</returns>
        public override OperationResult Execute(XmlDocument document)
        {
            log.Debug("starting Execute()");
            try
            {
                XmlNodeList conditionalsList = document.SelectNodes("operation/conditional");
                foreach (XmlNode conditionalNode in conditionalsList)
                {
                    string name = Helper.GetStringNodeValue(conditionalNode, "@name", string.Empty, true);
                    bool value = Helper.GetBooleanNodeValue(conditionalNode, "@value", true, true);
                    Helper.SetConditional(name, value);
                }

                return new OperationResult("Success setting conditionals");
            }
            catch (Exception e)
            {
                string exceptionMessage = "Exception setting conditionals " + e.Message;
                log.Warn(exceptionMessage);
                return new OperationResult(false, exceptionMessage);
            }
        }
    }
}
