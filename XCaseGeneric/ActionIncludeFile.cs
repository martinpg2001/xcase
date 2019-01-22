namespace XCaseGeneric
{
    using System;
    using System.IO;
    using System.Xml;
    using XCaseBase;
    using log4net;

    /// <summary>
    /// This action class reads in a file and executes the operations in it. 
    /// The XML file should be of this form:
    /// <operation id="Include TestCase10b.xml" class="XCaseGeneric.ActionIncludeFile">
    ///     <file name="TestCase10b.xml" />
    /// </operation>
    /// The included file must be in the same directory as the including file. Its form must be:
    /// <operations>
    ///     <operation id="" />
    ///     ...
    ///     <operation id="" />
    /// </operations>
    /// or
    /// <operation id="">
    /// </operation>
    /// Each operation can be any valid operation element.
    /// </summary>
    public class ActionIncludeFile : ActionAbstract
    {
        #region Logger Setup
        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILog log = LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        #endregion

        /// <summary>
        /// This method returns true if all of the operations in it return true, otherwise false.
        /// </summary>
        /// <param name="document">The operation document.</param>
        /// <returns>Returns true.</returns>
        public override OperationResult Execute(XmlDocument document)
        {
            log.Debug("starting Execute()");
            try
            {
                if (document.SelectSingleNode("operation/file/@name") != null)
                {
                    string fileName = Helper.GetStringNodeValue(document, "operation/file/@name", string.Empty, false);
                    log.Debug("fileName is " + fileName);
                    FileStream stream = null;
                    XmlDocument includedDocument = new XmlDocument();
                    stream = new FileStream(fileName, FileMode.Open, FileAccess.Read);
                    includedDocument.Load(stream);
                    stream.Close();
                    /* Check for environment element */
                    ProcessEnvironment processEnvironment = new ProcessEnvironment();
                    if (includedDocument.SelectSingleNode("operation/environment") != null)
                    {
                        log.Debug("document includes environment");
                        processEnvironment.Process(includedDocument.SelectSingleNode("operation/environment"));
                    }

                    log.Debug("about to process included document");
                    ProcessDocumentResult processDocumentResult = DocumentProcessor.ProcessDocument(processEnvironment, includedDocument);
                    if (processDocumentResult.Result)
                    {
                        log.Debug("success processing document");
                        return new OperationResult("Success executing included file: " + processDocumentResult.Message);
                    }
                    else
                    {
                        log.Debug("failed processing document");
                        return new OperationResult(false, "Failed executing included file: " + processDocumentResult.Message);
                    }
                }
                else
                {
                    string missingFileNameNodeMessage = "No file name specified";
                    log.Warn(missingFileNameNodeMessage);
                    return new OperationResult(false, missingFileNameNodeMessage);
                }
            }
            catch (Exception e)
            {
                string processingFileException = "Exception processing included file " + e.Message;
                log.Warn(processingFileException);
                return new OperationResult(false, processingFileException);
            }
        }
    }
}
