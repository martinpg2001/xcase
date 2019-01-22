namespace XCaseGeneric
{
    using System;
    using System.Xml;
    using XCaseBase;
    using log4net;

    /// <summary>
    /// This action class writes a message to the messenger class. The XML file should be of this form:
    /// <operation id="Write message" class="XCaseGeneric.ActionWriteMessage">
    ///     <message>This is a message</message>
    /// </operation>
    /// The message is returned in the operation result.
    /// </summary>
    public class ActionWriteMessage : ActionAbstract
    {
        #region Logger Setup
        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILog log = LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        #endregion

        /// <summary>
        /// This method writes a message to the messenger class.
        /// </summary>
        /// <param name="document">The operation document.</param>
        /// <returns>The result of the operation.</returns>
        public override OperationResult Execute(XmlDocument document)
        {
            ////log.Debug("starting Execute()");
            string message = null;
            try
            {
                XmlNode messageNode = document.SelectSingleNode("operation/message");
                if (messageNode != null)
                {
                    message = Helper.GetStringNodeValue(document, "operation/message", string.Empty, false);
                }
            }
            catch (Exception e)
            {
                string errorMessage = "Exception getting message " + e.Message;
                log.Warn(errorMessage);
                return new OperationResult(false, errorMessage);
            }

            return new OperationResult(true, message);
        }
    }
}
