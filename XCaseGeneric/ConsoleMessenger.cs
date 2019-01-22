namespace XCaseGeneric
{
    using System;
    using XCaseBase;
    using log4net;

    /// <summary>
    /// This class is used to write messages to the console.
    /// </summary>
    public class ConsoleMessenger : IMessenger
    {
        #region Logger Setup
        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILog log = LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        #endregion

        /// <summary>
        /// This method closes the messenger.
        /// </summary>
        /// <returns>Returns true.</returns>
        public bool Close()
        {
            return true;
        }

        /// <summary>
        /// This method initializes the messenger.
        /// </summary>
        /// <param name="s">This parameter is not used.</param>
        /// <returns>Returns true.</returns>
        public bool Init(string s)
        {
            log.Debug("starting init()");
            return true;
        }

        /// <summary>
        /// This method writes a warning message to the messenger.
        /// </summary>
        /// <param name="message">The message parameter.</param>
        /// <returns>Returns true.</returns>
        public bool Error(string message)
        {
            return true;
        }

        /// <summary>
        /// This method writes a message to the messenger.
        /// </summary>
        /// <param name="message">The message parameter.</param>
        /// <returns>Returns true.</returns>
        public bool Message(string message)
        {
            return true;
        }

        /// <summary>
        /// This method writes a warning message to the messenger.
        /// </summary>
        /// <param name="message">The message parameter.</param>
        /// <returns>Returns true.</returns>
        public bool Warn(string message)
        {
            return true;
        }

        /// <summary>
        /// This method writes an error message to the messenger.
        /// </summary>
        /// <param name="operationResult">The operation result parameter.</param>
        /// <returns>Returns true.</returns>
        public bool Error(OperationResult operationResult)
        {
            Console.ForegroundColor = ConsoleColor.Red;
            System.Console.Out.WriteLine(operationResult.Message);
            Console.ForegroundColor = ConsoleColor.White;
            return true;
        }

        /// <summary>
        /// This method writes a message to the messenger.
        /// </summary>
        /// <param name="operationResult">The operation result parameter.</param>
        /// <returns>Returns true.</returns>
        public bool Message(OperationResult operationResult)
        {
            Console.ForegroundColor = ConsoleColor.Green;
            System.Console.Out.WriteLine(operationResult.Message);
            Console.ForegroundColor = ConsoleColor.White;
            return true;
        }

        /// <summary>
        /// This method writes a warning message to the messenger.
        /// </summary>
        /// <param name="operationResult">The operation result parameter.</param>
        /// <returns>Returns true.</returns>
        public bool Warn(OperationResult operationResult)
        {
            Console.ForegroundColor = ConsoleColor.Yellow;
            System.Console.Out.WriteLine(operationResult.Message);
            Console.ForegroundColor = ConsoleColor.White;
            return true;
        }
    }
}
