namespace XCaseBase
{
    /// <summary>
    /// The object used to return the results of entity comparison.
    /// </summary>
    public class CompareResult
    {
        /// <summary>
        /// Initializes a new instance of the CompareResult class. The default constructor.
        /// </summary>
        public CompareResult()
        {
            this.Result = true;
            this.Message = "Success comparing entities";
        }

        /// <summary>
        /// Initializes a new instance of the CompareResult class: the constructor when only the result is known.
        /// </summary>
        /// <param name="result">The result boolean.</param>
        public CompareResult(bool result)
        {
            this.Result = result;
            if (result)
            {
                this.Message = "Success comparing entities";
            }
            else
            {
                this.Message = "Failure comparing entities";
            }
        }

        /// <summary>
        /// Initializes a new instance of the CompareResult class: the constructor when only the message is known.
        /// </summary>
        /// <param name="message">The message string.</param>
        public CompareResult(string message)
        {
            this.Result = true;
            this.Message = message;
        }

        /// <summary>
        /// Initializes a new instance of the CompareResult class: the constructor when both the result and message are known.
        /// </summary>
        /// <param name="result">The result boolean.</param>
        /// <param name="message">The message string.</param>
        public CompareResult(bool result, string message)
        {
            this.Result = result;
            this.Message = message;
        }

        /// <summary>
        /// Gets or sets a value indicating whether the comparison result is successful (true) or not (false).
        /// </summary>
        /// <value>
        /// The comparison result.
        /// </value>
        public bool Result { get; set; }

        /// <summary>
        /// Gets or sets a message.
        /// </summary>
        /// <value>
        /// The comparison message.
        /// </value>
        public string Message { get; set; }

        /// <summary>
        /// This method overrides the default Equals() method.
        /// </summary>
        /// <param name="obj">The obj parameter.</param>
        /// <returns>True if the CompareResult is not null and its fields match.</returns>
        public override bool Equals(object obj)
        {
            if (obj == null)
            {
                return false;
            }

            /* If parameter cannot be cast to CompareResult, then return false. */
            CompareResult targetCompareResult = obj as CompareResult;
            if ((object)targetCompareResult == null)
            {
                return false;
            }

            if (this.Result == targetCompareResult.Result && this.Message.Equals(targetCompareResult.Message))
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        /// <summary>
        /// This method provides a generic override of GetHashCode().
        /// </summary>
        /// <returns>The base value of GetHashCode().</returns>
        public override int GetHashCode()
        {
            return base.GetHashCode();
        }
    }
}
