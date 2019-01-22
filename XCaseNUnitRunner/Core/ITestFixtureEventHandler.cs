namespace XCaseNUnitRunner.Core
{
    /// <summary>
    /// Interface which should be implemented by all classes 
    /// which interest in performing specific action before/after any/all test methods in specified TestFixture are/have run.
    /// </summary>
    public interface ITestFixtureEventHandler
    {
        #region Public Methods and Operators

        /// <summary>
        /// This method is called once before any tests in a fixture are run.
        /// </summary>
        void OnTestFixtureSetUp();

        /// <summary>
        /// Method that is called after all the tests in a fixture have run. 
        /// This method is guaranteed to be called, even if an exception is thrown.
        /// </summary>
        void OnTestFixtureTearDown();

        #endregion
    }
}
