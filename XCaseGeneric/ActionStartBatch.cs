namespace XCaseGeneric
{
    using System.Xml;
    using XCaseBase;
    using log4net;

    /// <summary>
    /// This action class starts a test batch.
    /// </summary>
    public class ActionStartBatch : ActionAbstract
    {
        #region Logger Setup
        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILog log = LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        #endregion

        /// <summary>
        /// Initializes a new instance of the ActionStartBatch class.
        /// </summary>
        public ActionStartBatch()
        {
        }

        /// <summary>
        /// This method does nothing.
        /// </summary>
        /// <param name="document">The operation document.</param>
        /// <returns>The result of the operation.</returns>
        public override OperationResult Execute(XmlDocument document)
        {
            ////log.Debug("starting Execute()");
            return new OperationResult();
        }
    }
}
