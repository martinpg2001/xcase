namespace XCaseXUnitRunner
{
    using System;
    using System.Collections.Generic;
    using System.Configuration;
    using System.IO;
    using System.Linq;
    using System.Text;
    using System.Threading.Tasks;

    public abstract class XCaseBaseTest : BaseTest
    {
        /// <summary>
        /// The instance of run test manager to run tests.
        /// </summary>
        private readonly RunTestManager runTestManager = new RunTestManager();

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

        /// <summary>
        /// Runs specific test from xml file. 
        /// </summary>
        /// <param name="xmlFileName">Name of xml file with test in <see cref="XmlTestCasesDirectory"/> folder.</param>
        public void RunTest(string xmlFileName)
        {
            runTestManager.RunTest(this.XmlTestCasesDirectory, xmlFileName);
        }
    }
}
