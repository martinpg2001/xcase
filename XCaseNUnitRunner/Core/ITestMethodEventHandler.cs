namespace XCaseNUnitRunner.Core
{
    /// <summary>
    /// Interface which should be implemented by all classes 
    /// which interest in performing specific action before/after test method in specified TestFixture is called.
    /// </summary>
    public interface ITestMethodEventHandler
    {
        #region Public Methods and Operators

        /// <summary>
        /// Called just before each test method is called.
        /// </summary>
        void OnTestMethodSetUp();

        /// <summary>
        /// Called immediately after each test method is run. 
        /// The method is guaranteed to be called, even if an exception is thrown.
        /// </summary>
        void OnTestMethodTearDown();

        #endregion
    }
}
