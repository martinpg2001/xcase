namespace SampleRunner
{
    using System.IO;
    using log4net;
    using Xunit;
    using Xunit.Abstractions;
    using XCaseXUnitRunner;

    /// <summary>
    /// A sample test.
    /// </summary>
    public class SampleXUnitTest : XCaseBaseTest
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
        [Theory]
        [InlineData("SampleTest1.xml")]
        [InlineData("SampleTest2.xml")]
        public void RunSampleTests(string xmlFileName)
        {
            log4net.Config.XmlConfigurator.Configure();
            Log.InfoFormat("about to run test {0}", xmlFileName);
            RunTest(xmlFileName);
            Log.InfoFormat("ran test {0}", xmlFileName);
        }

        #endregion

        #region Methods

        #endregion Methods
    }
}
