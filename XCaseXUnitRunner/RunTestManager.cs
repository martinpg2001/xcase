namespace XCaseXUnitRunner
{
    using System;
    using System.Collections.Generic;
    using System.IO;
    using System.Linq;
    using System.Text;
    using System.Threading.Tasks;
    using System.Xml;
    using Xunit;
    using Xunit.Extensions;
    using XCaseBase;

    public class RunTestManager
    {
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

            using (FileStream fileStream = new FileStream(fullPathToXmlFile, FileMode.Open, FileAccess.Read))
            {
                XmlDocument xmlDocument = new XmlDocument();
                xmlDocument.Load(fileStream);
                ProcessEnvironment processEnvironment = new ProcessEnvironment();
                ProcessDocumentResult testResult = DocumentProcessor.ProcessDocument(processEnvironment, xmlDocument);
                Assert.True(testResult.Result, testResult.Message);
            }
        }

        #endregion Public Methods and Operators
    }
}
