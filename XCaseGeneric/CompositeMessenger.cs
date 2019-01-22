namespace XCaseGeneric
{
    using System.Collections.Generic;
    using XCaseBase;
    using log4net;

    /// <summary>
    /// This class writes messages to other messengers.
    /// </summary>
    public class CompositeMessenger : IMessenger
    {
        #region Logger Setup
        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILog log = LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        #endregion

        /// <summary>
        /// The list of messengers.
        /// </summary>
        private List<IMessenger> messengerList;

        /// <summary>
        /// This method closes all messengers.
        /// </summary>
        /// <returns>Returns true.</returns>
        public bool Close()
        {
            log.Debug("starting Close()");
            foreach (IMessenger messenger in this.messengerList)
            {
                messenger.Close();
            }

            return true;
        }

        /// <summary>
        /// This method initializes all messengers.
        /// </summary>
        /// <param name="connectionString">The connectionString parameter.</param>
        /// <returns>Returns true.</returns>
        public bool Init(string connectionString)
        {
            log.Debug("starting Init()");
            this.messengerList = new List<IMessenger>();
            this.PopulateMessenger(connectionString);
            return true;
        }

        /// <summary>
        /// This method writes an error message to all messengers.
        /// </summary>
        /// <param name="message">The message parameter.</param>
        /// <returns>Returns true.</returns>
        public bool Error(string message)
        {
            log.Debug("starting Error()");
            foreach (IMessenger messenger in this.messengerList)
            {
                messenger.Error(message);
            }

            return true;
        }

        /// <summary>
        /// This method writes a message to all messengers.
        /// </summary>
        /// <param name="message">The message parameter.</param>
        /// <returns>Returns true.</returns>
        public bool Message(string message)
        {
            log.Debug("starting message()");
            foreach (IMessenger messenger in this.messengerList)
            {
                messenger.Message(message);
            }

            return true;
        }

        /// <summary>
        /// This method writes a warning message to all messengers.
        /// </summary>
        /// <param name="message">The message parameter.</param>
        /// <returns>Returns true.</returns>
        public bool Warn(string message)
        {
            log.Debug("starting warn()");
            foreach (IMessenger messenger in this.messengerList)
            {
                messenger.Warn(message);
            }

            return true;
        }

        /// <summary>
        /// This method writes an error message to all messengers.
        /// </summary>
        /// <param name="operationResult">The operation result parameter.</param>
        /// <returns>Returns true.</returns>
        public bool Error(OperationResult operationResult)
        {
            log.Debug("starting error()");
            foreach (IMessenger messenger in this.messengerList)
            {
                messenger.Error(operationResult);
            }

            return true;
        }

        /// <summary>
        /// This method writes a message to all messengers.
        /// </summary>
        /// <param name="operationResult">The operation result parameter.</param>
        /// <returns>Returns true.</returns>
        public bool Message(OperationResult operationResult)
        {
            log.Debug("starting message()");
            foreach (IMessenger messenger in this.messengerList)
            {
                messenger.Message(operationResult);
            }

            return true;
        }

        /// <summary>
        /// This method writes a warning message to all messengers.
        /// </summary>
        /// <param name="operationResult">The operation result parameter.</param>
        /// <returns>Returns true.</returns>
        public bool Warn(OperationResult operationResult)
        {
            log.Debug("starting warn()");
            foreach (IMessenger messenger in this.messengerList)
            {
                messenger.Warn(operationResult);
            }

            return true;
        }

        /// <summary>
        /// This method initializes all messengers.
        /// </summary>
        /// <param name="configString">The configString parameter.</param>
        public void PopulateMessenger(string configString)
        {
            log.Debug("starting populateMessenger()");
            string[] messengerArray = configString.Split('!');
            foreach (string messenger in messengerArray)
            {
                string[] messengerValues = messenger.Split(';');
                switch (messengerValues[0])
                {
                    case "ConsoleMessenger":
                        log.Debug("messenger is ConsoleMessenger");
                        ConsoleMessenger consoleMessenger = new ConsoleMessenger();
                        consoleMessenger.Init(messenger);
                        this.messengerList.Add(consoleMessenger);
                        break;
                    case "DatabaseMessenger":
                        log.Debug("messenger is DatabaseMessenger");
                        DatabaseMessenger databaseMessenger = new DatabaseMessenger();
                        databaseMessenger.Init(messenger);
                        this.messengerList.Add(databaseMessenger);
                        break;
                    case "XMLMessenger":
                        log.Debug("messenger is XMLMessenger");
                        XMLMessenger xmlMessenger = new XMLMessenger();
                        xmlMessenger.Init(messenger);
                        this.messengerList.Add(xmlMessenger);
                        break;
                    default:
                        log.Info("messenger name is unrecognized " + messengerValues[0]);
                        break;
                }
            }

            log.Debug("finishing populateMessenger()");
        }
    }
}
