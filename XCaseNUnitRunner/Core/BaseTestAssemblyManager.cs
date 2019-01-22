namespace XCaseNUnitRunner.Core
{
    using System;

    /// <summary>
    /// BaseTestAssemblyManager responsive for global initialization of the test environment.
    /// </summary>
    public class BaseTestAssemblyManager : ITestAssemblyManager
    {
        #region Static Fields

        /// <summary>
        /// Singleton instance of the assembly manager.
        /// </summary>
        private static ITestAssemblyManager instance;

        #endregion Static Fields

        #region Constructors and Destructors

        /// <summary>
        /// Initializes a new instance of the <see cref="BaseTestAssemblyManager"/> class.
        /// </summary>
        protected BaseTestAssemblyManager()
        {
        }

        #endregion Constructors and Destructors

        #region Public Properties

        /// <summary>
        /// Gets the instance.
        /// </summary>
        /// <value>
        /// The singleton instance of unit test assembly manager.
        /// </value>
        /// <exception cref="System.ApplicationException">
        /// If instance of the <see cref="ITestAssemblyManager"/> has not been initialized.
        /// </exception>
        public static ITestAssemblyManager AssemblyManager
        {
            get
            {
                if (instance != null)
                {
                    return instance;
                }

                const string ErrorMessage =
                    "Instance has not been already initialized.\n"
                    + "Ensure that in assembly with tests global UnitTestAssemblyManager class definition is added.\n"
                    + "Ensure that global UnitTestAssemblyManager is  outside any namespace or"
                    + "that [SetUpFixture] attribute is specified.\n";

                throw new ApplicationException(ErrorMessage);
            }
        }

        #endregion Public Properties

        #region Public Methods and Operators

        /// <summary>
        /// This method will be called by nUnit runner when execution of the all test (in current executing assembly) is completed.
        /// </summary>
        public virtual void OnUnitTestSessionEnd()
        {
        }

        /// <summary>
        /// Called before any test is run in current executing assembly which contains tests.
        /// </summary>
        public virtual void OnUnitTestSessionStart()
        {
            instance = this;
        }

        #endregion Public Methods and Operators
    }
}
