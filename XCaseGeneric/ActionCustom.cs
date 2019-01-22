namespace XCaseGeneric
{
    using System;
    using System.Xml;
    using XCaseBase;
    using log4net;

    /// <summary>
    /// This action class is intended to be customized: it should not take any parameters.
    /// You should this class when you want to do something very specific for a test that
    /// will not be re-used. Do not check in any changes to this class.
    /// <operation id="Custom action" class="XCaseGeneric.ActionCustom">   
    /// </operation>
    /// </summary>
    public class ActionCustom : ActionAbstract
    {
        #region Logger Setup
        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILog log = LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        #endregion

        /// <summary>
        /// This action class is intended to be customized.
        /// </summary>
        /// <param name="document">The operation document.</param>
        /// <returns>The result of the operation.</returns>
        public override OperationResult Execute(XmlDocument document)
        {
            ////log.Debug(" starting Execute()");
            try
            {
                return new OperationResult(true, "Success");
            }
            catch (Exception e)
            {
                string exceptionMessage = "An exception has occurred: " + e.Message;
                log.Warn(" " + exceptionMessage);
                return new OperationResult(false, exceptionMessage);
            }
        }
    }
}
