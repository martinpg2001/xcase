namespace XCaseNUnitRunner.Core
{
    using System.IO;
    using System.Xml;
    using log4net;
    using NUnit.Framework;
    using XCaseBase;

    /// <summary>
    /// The class to configure and run specified tests.
    /// </summary>
    public class RunTestManager
    {
        #region Private Fields

        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILog Log = LogManager.GetLogger("TestToolLogger");
        #endregion Private Fields

        #region Public Methods and Operators

        /// <summary>
        /// The method to run specified test.
        /// </summary>
        /// <param name="directory">The test file directory (without XML test file name).</param>
        /// <param name="filename">The XML test file name.</param>
        /// <returns>
        /// The <see cref="ProcessDocumentResult"/> value with results of test execution.
        /// </returns>
        public void RunTest(string directory, string filename)
        {
            // Combine the full path to the XML test file.
            string fullPathToXmlFile = Path.Combine(directory, filename);
            if (!File.Exists(fullPathToXmlFile))
            {
                throw new FileNotFoundException(string.Format("Specified file '{0}' was not found.", fullPathToXmlFile));
            }

            using (FileStream fileStream = new FileStream(fullPathToXmlFile, FileMode.Open, FileAccess.Read, FileShare.ReadWrite))
            {
                XmlDocument xmlDocument = new XmlDocument();
                xmlDocument.Load(fileStream);
                ProcessEnvironment processEnvironment = TestRunnerAssemblyManager.ProcessEnvironment;
                ProcessDocumentResult testResult = DocumentProcessor.ProcessDocument(processEnvironment, xmlDocument);
                Assert.IsTrue(testResult.Result, testResult.Message);
            }
        }

        #endregion Public Methods and Operators
    }
}
