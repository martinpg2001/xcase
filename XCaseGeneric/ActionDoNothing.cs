namespace XCaseGeneric
{
    using System.Xml;
    using XCaseBase;

    /// <summary>
    /// This action class does nothing. Its purpose is to check that the test
    /// framework is working correctly. The XML file should be of this form:
    /// <operation id="Do Nothing" class="XCaseGeneric.ActionDoNothing">   
    /// </operation>
    /// You can use this class to set conditionals because it always returns true.
    /// </summary>
    public class ActionDoNothing : ActionAbstract
    {
        /// <summary>
        /// This method returns true.
        /// </summary>
        /// You can also use this class to set conditionals because it always returns true.
        /// <param name="document">The operation document.</param>
        /// <returns>Returns true.</returns>
        public override OperationResult Execute(XmlDocument document)
        {
            ////log.Debug("starting Execute()");
            return new OperationResult(true, "Success");
        }
    }
}
