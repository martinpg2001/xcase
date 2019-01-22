namespace SampleRunner
{
    using System.IO;
    using log4net;
    using NUnit.Framework;
    using XCaseNUnitRunner;

    /// <summary>
    /// A sample test.
    /// </summary>
    public class SampleNUnitTest : XCaseBaseTest
    {
        #region Properties

        /// <summary>
        ///  A log4net log instance.
        /// </summary>
        private static readonly ILog Log = LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);

        /// <summary>
        /// Gets directory where XML files for tests located. 
        /// </summary>
        public override string XmlTestCasesDirectory
        {
            get
            {
                return Path.Combine(BaseTestsDirectory, @"sample");
            }
        }

        #endregion Properties

        #region Public Methods

        /// <summary>
        /// Run Sample tests.
        /// <list type="bullet">
        /// <listheader>
        /// <term>Sample Test Plan</term>
        /// </listheader>
        /// <item>
        /// TODO: Descriptions
        /// <term>Test Case 1</term>
        /// <description>
        /// This test ...
        /// </description>
        /// </item>
        /// <item>
        /// <term>Test Case 2</term>
        /// <description>
        /// This test ...
        /// </description>
        /// </item>
        /// </list>
        /// </summary>
        [CategoryAttribute("SampleTestCases")]
        [CategoryAttribute("SampleTestPlan")]
        [Test]
        [TestCase("SampleTest1.xml", TestName = "SampleTest1")]
        [TestCase("SampleTest2.xml", TestName = "SampleTest2")]
        public void RunSampleTests(string xmlFileName)
        {
            Log.InfoFormat("about to run test {0}", xmlFileName);
            RunTest(xmlFileName);
            Log.InfoFormat("ran test {0}", xmlFileName);
        }

        #endregion

        #region Methods

        /// <summary>
        /// The test fixture setup for sample tests.
        /// </summary>
        protected override void OnTestFixtureSetUp()
        {
            base.OnTestFixtureSetUp();
            RunTest("SampleSetup.xml");
        }

        /// <summary>
        /// The test fixture teardown for sample tests.
        /// </summary>
        protected override void OnTestFixtureTearDown()
        {
            RunTest("SampleTeardown.xml");
            base.OnTestFixtureTearDown();
        }

        #endregion Methods
    }
}
