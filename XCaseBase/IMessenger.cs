namespace XCaseBase
{
    /// <summary>
    /// This interface is implemented by all of the Messenger classes.
    /// </summary>
    public interface IMessenger
    {
        /// <summary>
        /// This method closes the access to the messenger's target.
        /// </summary>
        /// <returns>true if the closing of access is successful.</returns>
        bool Close();

        /// <summary>
        /// This method initializes the access to the messenger's target.
        /// </summary>
        /// <param name="s">Connection information to the messenger's target.</param>
        /// <returns>true if the initialization is successful.</returns>
        bool Init(string s);

        /// <summary>
        /// This method writes an error message to the messenger's target.
        /// </summary>
        /// <param name="message">The message string.</param>
        /// <returns>true if writing the message is successful.</returns>
        bool Error(string message);

        /// <summary>
        /// This method writes an message to the messenger's target.
        /// </summary>
        /// <param name="message">The message string.</param>
        /// <returns>true if writing the message is successful.</returns>
        bool Message(string message);

        /// <summary>
        /// This method writes an warning message to the messenger's target.
        /// </summary>
        /// <param name="message">The message string.</param>
        /// <returns>true if writing the message is successful.</returns>
        bool Warn(string message);

        /// <summary>
        /// This method writes an error message to the messenger's target.
        /// </summary>
        /// <param name="operationResult">The operation result that contains the message string.</param>
        /// <returns>true if writing the message is successful.</returns>
        bool Error(OperationResult operationResult);

        /// <summary>
        /// This method writes an message to the messenger's target.
        /// </summary>
        /// <param name="operationResult">The operation result that contains the message string.</param>
        /// <returns>true if writing the message is successful.</returns>
        bool Message(OperationResult operationResult);

        /// <summary>
        /// This method writes an warning message to the messenger's target.
        /// </summary>
        /// <param name="operationResult">The operation result that contains the message string.</param>
        /// <returns>true if writing the message is successful.</returns>
        bool Warn(OperationResult operationResult);
    }
}
