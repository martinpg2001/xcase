namespace XCaseBase
{

    /// <summary>
    /// This class is used to return the result of performing an operation.
    /// </summary>
    public class OperationResult
    {
        /// <summary>
        /// Initializes a new instance of the OperationResult class.
        /// </summary>
        public OperationResult()
        {
            this.Result = true;
            this.Message = "Success executing operation";
            this.Status = string.Empty;
        }

        /// <summary>
        /// Initializes a new instance of the OperationResult class.
        /// </summary>
        /// <param name="result">The result parameter.</param>
        public OperationResult(bool result)
        {
            this.Result = result;
            if (result)
            {
                this.Message = "Success executing operation";
            }
            else
            {
                this.Message = "Failure executing operation";
            }

            this.Status = string.Empty;
        }

        /// <summary>
        /// Initializes a new instance of the OperationResult class.
        /// </summary>
        /// <param name="message">The message parameter.</param>
        public OperationResult(string message)
        {
            this.Result = true;
            this.Message = message;
            this.Status = string.Empty;
        }

        /// <summary>
        /// Initializes a new instance of the OperationResult class.
        /// </summary>
        /// <param name="result">The result parameter.</param>
        /// <param name="message">The message parameter.</param>
        public OperationResult(bool result, string message)
        {
            this.Result = result;
            this.Message = message;
            this.Status = string.Empty;
        }

        /// <summary>
        /// Initializes a new instance of the OperationResult class.
        /// </summary>
        /// <param name="result">The result parameter.</param>
        /// <param name="id">The operation id parameter.</param>
        /// <param name="message">The message parameter.</param>
        public OperationResult(bool result, string id, string message)
        {
            this.Result = result;
            this.Id = id;
            this.Message = message;
            this.Status = string.Empty;
        }

        /// <summary>
        /// Initializes a new instance of the OperationResult class.
        /// </summary>
        /// <param name="result">The result parameter.</param>
        /// <param name="id">The operation id parameter.</param>
        /// <param name="message">The message parameter.</param>
        /// <param name="status">The status parameter.</param>
        public OperationResult(bool result, string id, string message, string status)
        {
            this.Result = result;
            this.Id = id;
            this.Message = message;
            this.Status = status;
        }

        /// <summary>
        /// Gets or sets the operation id of the operation.
        /// </summary>
        /// <value>
        /// Takes its value from the id attribute of the operation.
        /// </value>
        public string Id { get; set; }

        /// <summary>
        /// Gets or sets the message from the operation execution.
        /// </summary>
        /// <value>
        /// The message from the operation execution.
        /// </value>
        public string Message { get; set; }

        /// <summary>
        /// Gets or sets a value indicating whether the operation was successful.
        /// </summary>
        /// <value>
        /// True if the operation is successful.
        /// </value>
        public bool Result { get; set; }

        /// <summary>
        /// Gets or sets a value indicating the status of the operation.
        /// </summary>
        /// <value>
        /// True if the operation is successful.
        /// </value>
        public string Status { get; set; }
    }
}
