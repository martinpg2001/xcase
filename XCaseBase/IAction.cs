namespace XCaseBase
{
    using System.Xml;

    /// <summary>
    /// The main Execute method required by all action classes.
    /// </summary>
    public interface IAction
    {
        /// <summary>
        /// The main method to execute an operation.
        /// </summary>
        /// <param name="document">The document parameter.</param>
        /// <returns>The result of performing the operation.</returns>
        OperationResult Execute(XmlDocument document);
    }
}
