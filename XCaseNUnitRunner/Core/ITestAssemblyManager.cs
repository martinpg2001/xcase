namespace XCaseNUnitRunner.Core
{
    /// <summary>
    /// Interface IUnitTestAssemblyManager specifies global event handlers methods.
    /// </summary>
    public interface ITestAssemblyManager
    {
        #region Public Methods and Operators

        /// <summary>
        /// This method will be called by nUnit runner when execution of the all unit test (in current executing assembly) is completed.
        /// Performs application-defined tasks associated with freeing, releasing, or resetting unmanaged resources.
        /// </summary>
        void OnUnitTestSessionEnd();

        /// <summary>
        /// Called before any test is run in current executing assembly which contains unit tests.
        /// </summary>
        void OnUnitTestSessionStart();

        #endregion Public Methods and Operators
    }
}
