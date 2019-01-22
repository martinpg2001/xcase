namespace XCaseNUnitRunner.Core
{
    using System.Configuration;
    using System.IO;
    using log4net;
    using NUnit.Framework;
    using XCaseBase;

    /// <summary>
    /// The base test class.
    /// </summary>
    public abstract class BaseTest : BaseTestFixture
    {
        #region Private Fields
        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILog Log = LogManager.GetLogger("TestToolLogger");

        /// <summary>
        /// The instance of run test manager to run tests.
        /// </summary>
        private readonly RunTestManager runTestManager = new RunTestManager();

        #endregion Private Fields

        #region Public Properties

        /// <summary>
        /// Gets the base directory of all xml test plans directory. 
        /// </summary>
        /// <exception cref="DirectoryNotFoundException">Is thrown in case test files directory is not specified in app config.</exception>
        public string BaseTestsDirectory
        {
            get
            {
                // Get from app.config
                string baseTestsDirectory = ConfigurationManager.AppSettings["TestFilesDirectory"];
                if (string.IsNullOrWhiteSpace(baseTestsDirectory))
                {
                    throw new DirectoryNotFoundException("The test files directory is not specified.");
                }

                return baseTestsDirectory;
            }
        }

        /// <summary>
        /// Gets directory where xml files for this test fixture located. 
        /// </summary>
        public abstract string XmlTestCasesDirectory { get; }

        #endregion Public Properties

        #region Public Methods and Operators

        /// <summary>
        /// Runs specific test from XML file. 
        /// </summary>
        /// <param name="xmlFileName">Name of xml file with test in <see cref="XmlTestCasesDirectory"/> folder.</param>
        public void RunTest(string xmlFileName)
        {
            runTestManager.RunTest(this.XmlTestCasesDirectory, xmlFileName);
        }

        #endregion Public Methods and Operators

        #region Methods

        /// <summary>
        /// The test fixture setup.
        /// </summary>
        protected override void OnTestFixtureSetUp()
        {

        }

        /// <summary>
        /// The test fixture teardown.
        /// </summary>
        protected override void OnTestFixtureTearDown()
        {

        }

        #endregion Methods
    }
}
