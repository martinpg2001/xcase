namespace XCaseGeneric
{
    using System;
    using System.Xml;
    using XCaseBase;
    using log4net;

    public class ActionClearHelperDictionary : ActionAbstract
    {
        #region Logger Setup
        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILog log = LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        #endregion


        /// <summary>
        /// This method clears the Helper dictionary. The operation file should be of
        /// this form:
        /// <operation id="Clear Helper dictionary" class="XCaseGeneric.ActionClearHelperDictionary" />
        /// You should invoke this operation before creating datasources.
        /// </summary>
        /// <param name="document">The document parameter.</param>
        /// <returns>The operation result.</returns>
        public override OperationResult Execute(XmlDocument document)
        {
            log.Debug("starting Execute()");
            try
            {
                Helper.ClearDictionary();
            }
            catch (Exception e)
            {
                string clearDictionaryException = "Exception clearing Helper dictionary " + e.Message;
                log.Warn(clearDictionaryException);
                return new OperationResult(false, clearDictionaryException);
            }

            return new OperationResult("Success clearing Helper dictionary");
        }
    }
}
