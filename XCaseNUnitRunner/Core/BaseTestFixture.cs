namespace XCaseNUnitRunner.Core
{
    using NUnit.Framework;

    /// <summary>
    /// Implements base class definition for all test fixtures.
    /// </summary>
    [TestFixture]
    public abstract class BaseTestFixture : ITestMethodEventHandler, ITestFixtureEventHandler
    {
        #region Public Methods and Operators

        /// <summary>
        /// This method is called before any tests in a fixture are run.
        /// </summary>
        /// <remarks>
        /// Instead of this method <seealso cref="OnTestFixtureSetUp"/> method should be overridden in inherited classes.
        /// </remarks>
        [TestFixtureSetUp]
        public void TestFixtureSetUp()
        {
            this.OnTestFixtureSetUp();
        }

        /// <summary>
        /// Method that is called after all the tests in a fixture have run. 
        /// This method is guaranteed to be called, even if an exception is thrown.
        /// </summary>
        /// <remarks>
        /// Instead of this method <seealso cref="OnTestFixtureTearDown"/> method should be overridden in inherited classes.
        /// </remarks>
        [TestFixtureTearDown]
        public void TestFixtureTearDown()
        {
            this.OnTestFixtureTearDown();
        }

        /// <summary>
        /// Called just before each test method is called. 
        /// </summary>
        /// <remarks>
        /// Instead of this method <seealso cref="OnTestMethodSetUp"/> method should be overridden in inherited classes.
        /// </remarks>
        [SetUp]
        public void TestMethodSetUp()
        {
            this.OnTestMethodSetUp();
        }

        /// <summary>
        /// Called immediately after each test is run. 
        /// The method is guaranteed to be called, even if an exception is thrown.
        /// </summary>
        /// <remarks>
        /// Instead of this method <seealso cref="OnTestMethodTearDown"/> method should be overridden in inherited classes.
        /// </remarks>
        [TearDown]
        public void TestMethodTearDown()
        {
            this.OnTestMethodTearDown();
        }

        #endregion Public Methods and Operators

        #region Explicit Interface Methods

        /// <summary>
        /// This method is called before any tests in a fixture are run.
        /// </summary>
        void ITestFixtureEventHandler.OnTestFixtureSetUp()
        {
            this.OnTestFixtureSetUp();
        }

        /// <summary>
        /// Method that is called after all the tests in a fixture have run. 
        /// This method is guaranteed to be called, even if an exception is thrown.
        /// </summary>
        void ITestFixtureEventHandler.OnTestFixtureTearDown()
        {
            this.OnTestFixtureTearDown();
        }

        /// <summary>
        /// Called just before each test method is called. 
        /// </summary>
        void ITestMethodEventHandler.OnTestMethodSetUp()
        {
            this.TestMethodSetUp();
        }

        /// <summary>
        /// Called immediately after each test method is run. 
        /// The method is guaranteed to be called, even if an exception is thrown.
        /// </summary>
        void ITestMethodEventHandler.OnTestMethodTearDown()
        {
            this.TestMethodTearDown();
        }

        #endregion

        #region Methods

        /// <summary>
        /// This method is called before any tests in a fixture are run.
        /// </summary>
        /// <remarks>
        /// Override this method to inject logic in child classes.
        /// </remarks>
        protected virtual void OnTestFixtureSetUp()
        {
        }

        /// <summary>
        /// Method that is called after all the tests in a fixture have run. 
        /// This method is guaranteed to be called, even if an exception is thrown.
        /// </summary>
        /// <remarks>
        /// Override this method to inject logic in child classes.
        /// </remarks>
        protected virtual void OnTestFixtureTearDown()
        {
        }

        /// <summary>
        /// Called just before each test method is called. 
        /// </summary>
        /// <remarks>
        /// Override this method to inject logic in child classes.
        /// </remarks>
        protected virtual void OnTestMethodSetUp()
        {
        }

        /// <summary>
        /// Called immediately after each test method is run. 
        /// The method is guaranteed to be called, even if an exception is thrown.
        /// </summary>
        /// <remarks>
        /// Override this method to inject logic in child classes.
        /// </remarks>
        protected virtual void OnTestMethodTearDown()
        {
        }

        #endregion Methods
    }
}
