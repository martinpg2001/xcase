namespace XCaseBase
{
    using System.Xml;

    public abstract class ActionAbstract : IAction
    {
        /// <summary>
        /// The main method to execute an operation.
        /// </summary>
        /// <param name="document">The document parameter.</param>
        /// <returns>The result of performing the operation.</returns>
        public abstract OperationResult Execute(XmlDocument document);
    }
}
