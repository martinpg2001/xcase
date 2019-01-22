namespace XCaseBase
{
    /// <summary>
    /// This class is used to represent the result of processing the operations document.
    /// </summary>
    public class ProcessDocumentResult
    {
        /// <summary>
        /// Initializes a new instance of the ProcessDocumentResult class.
        /// </summary>
        public ProcessDocumentResult()
        {
            this.Result = true;
            this.Message = "Success processing document";
        }

        /// <summary>
        /// Initializes a new instance of the ProcessDocumentResult class.
        /// </summary>
        /// <param name="result">The result parameter.</param>
        public ProcessDocumentResult(bool result)
        {
            this.Result = result;
            if (result)
            {
                this.Message = "Success processing document";
            }
            else
            {
                this.Message = "Failure processing document";
            }
        }

        /// <summary>
        /// Initializes a new instance of the ProcessDocumentResult class.
        /// </summary>
        /// <param name="message">The message parameter.</param>
        public ProcessDocumentResult(string message)
        {
            this.Result = true;
            this.Message = message;
        }

        /// <summary>
        /// Initializes a new instance of the ProcessDocumentResult class.
        /// </summary>
        /// <param name="result">The result parameter.</param>
        /// <param name="message">The message parameter.</param>
        public ProcessDocumentResult(bool result, string message)
        {
            this.Result = result;
            this.Message = message;
        }

        /// <summary>
        /// Initializes a new instance of the ProcessDocumentResult class.
        /// </summary>
        /// <param name="result">The result parameter.</param>
        /// <param name="id">The operation(s) id parameter.</param>
        /// <param name="message">The message parameter.</param>
        public ProcessDocumentResult(bool result, string id, string message)
        {
            this.Result = result;
            this.Id = id;
            this.Message = message;
        }

        /// <summary>
        /// Gets or sets a value indicating whether the processing of the document was successful.
        /// </summary>
        /// <value>
        /// True if the processing is successful.
        /// </value>
        public bool Result { get; set; }

        /// <summary>
        /// Gets or sets the operation(s) id.
        /// </summary>
        /// <value>
        /// The operation(s) id.
        /// </value>
        public string Id { get; set; }

        /// <summary>
        /// Gets or sets the result message.
        /// </summary>
        /// <value>
        /// The message.
        /// </value>
        public string Message { get; set; }
    }
}
