namespace XCaseNUnitRunner
{
    using System;
    using System.Collections.Specialized;
    using System.Configuration;
    using System.IO;
    using log4net;
    using XCaseNUnitRunner.Core;

    /// <summary>
    /// This is the base class for XCase projects. It contains properties and base methods for running tests.
    /// </summary>
    public abstract class XCaseBaseTest : BaseTest
    {
        /// <summary>
        ///  A log4net log instance.
        /// </summary>
        private static readonly ILog Log = LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);

        /// <summary>
        /// The run test manager instance that is used to run tests.
        /// </summary>
        private readonly RunTestManager runTestManager = new RunTestManager();

        #region Public Properties

        #endregion Public Properties

        #region Methods

        /// <summary>
        /// The test fixture setup.
        /// </summary>
        protected override void OnTestFixtureSetUp()
        {
            try
            {
                /* Insert any common initialization code required */
                /* Configure log4net using XCaseTestRunner.dll.config */
                log4net.Config.XmlConfigurator.Configure();
            }
            catch (Exception e)
            {
                Log.Warn(string.Format("{0}: {1}", GetType().Name, e.Message), e);
                throw;
            }
        }

        /// <summary>
        /// The test fixture teardown.
        /// </summary>
        protected override void OnTestFixtureTearDown()
        {
            try
            {
                /* Insert any common finalization code required */
            }
            catch (Exception e)
            {
                Log.Warn(string.Format("{0}: {1}", GetType().Name, e.Message), e);
                throw;
            }
        }

        #endregion Methods
    }
}
