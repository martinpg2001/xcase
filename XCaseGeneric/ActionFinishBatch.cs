namespace XCaseGeneric
{
    using System.Xml;
    using XCaseBase;

    /// <summary>
    /// This action class is used to finish a batch of tests.
    /// </summary>
    public class ActionFinishBatch : ActionAbstract
    {
        /// <summary>
        /// Initializes a new instance of the ActionFinishBatch class.
        /// </summary>
        public ActionFinishBatch()
        {
        }

        /// <summary>
        /// This action method is used to finish a batch of tests.
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
