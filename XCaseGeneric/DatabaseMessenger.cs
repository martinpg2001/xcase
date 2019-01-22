namespace XCaseGeneric
{
    using System;
    using System.Data.SqlClient;
    using System.Text;
    using XCaseBase;
    using log4net;

    /// <summary>
    /// This class writes messages to a database.
    /// </summary>
    public class DatabaseMessenger : IMessenger
    {
        #region Logger Setup
        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILog log = LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        #endregion

        /// <summary>
        /// The messagesTableExists field.
        /// </summary>
        private bool messagesTableExists = false;

        /// <summary>
        /// The testBatchesTableExists field.
        /// </summary>
        private bool testBatchesTableExists = false;

        /// <summary>
        /// The testInfoTableExists field.
        /// </summary>
        private bool testInfoTableExists = false;

        /// <summary>
        /// The testRunsTableExists field.
        /// </summary>
        private bool testRunsTableExists = false;

        /// <summary>
        /// The messageLength field.
        /// </summary>
        private int messageLength = 1024;

        /// <summary>
        /// The testNameLength field.
        /// </summary>
        private int testNameLength = 128;

        /// <summary>
        /// The testBatchID field.
        /// </summary>
        private int testBatchID = -1;

        /// <summary>
        /// The testInfoID field.
        /// </summary>
        private int testInfoID = -1;

        /// <summary>
        /// The testRunID field.
        /// </summary>
        private int testRunID;

        /// <summary>
        /// The connection field.
        /// </summary>
        private SqlConnection connection;

        /// <summary>
        /// The dbHost field.
        /// </summary>
        private string databaseHost = "Qawin1";

        /// <summary>
        /// The dbPort field.
        /// </summary>
        private string databasePort = "1433";

        /// <summary>
        /// The dbProtocol field.
        /// </summary>
        private string databaseProtocol;

        /// <summary>
        /// The dbDatabase field.
        /// </summary>
        private string databaseDatabase = "QATestResults";

        /// <summary>
        /// The dbUsername field.
        /// </summary>
        private string databaseUsername = "sa";

        /// <summary>
        /// The dbPassword field.
        /// </summary>
        private string databasePassword = string.Empty;

        /// <summary>
        /// The databaseIntegratedSecurity field.
        /// </summary>
        private string databaseIntegratedSecurity;

        /// <summary>
        /// The operationID field.
        /// </summary>
        private string operationID;

        /// <summary>
        /// This method closes the database connection.
        /// </summary>
        /// <returns>Returns true.</returns>
        public bool Close()
        {
            this.connection.Close();
            return true;
        }

        /// <summary>
        /// This method initializes the database connection and database.
        /// </summary>
        /// <param name="connectionString">The connectionString parameter.</param>
        /// <returns>Returns true.</returns>
        public bool Init(string connectionString)
        {
            ////log.Debug("starting Init()");
            if (connectionString != null)
            {
                this.PopulateMessenger(connectionString);
            }

            ////log.Debug("populated messenger");
            this.connection = this.GetDBConnection();
            this.connection.Open();
            this.testInfoTableExists = this.CheckTESTINFOExists();
            ////log.Debug("m_TESTINFOExists is " + this.testInfoTableExists);
            this.testBatchesTableExists = this.CheckTESTBATCHESExists();
            ////log.Debug("m_TESTBATCHESExists is " + this.testBatchesTableExists);
            this.testRunsTableExists = this.CheckTESTRUNSExists();
            ////log.Debug("m_TESTRUNSExists is " + this.testRunsTableExists);
            this.messagesTableExists = this.CheckMESSAGESExists();
            ////log.Debug("m_MESSAGESExists is " + this.messagesTableExists);
            this.connection.Close();
            ////log.Debug("finishing Init()");
            return true;
        }

        /// <summary>
        /// This method writes an error message.
        /// </summary>
        /// <param name="message">The message parameter.</param>
        /// <returns>Returns true.</returns>
        public bool Error(string message)
        {
            ////log.Debug("starting Error()");
            return this.Write(message, "ERROR");
        }

        /// <summary>
        /// This method writes a message.
        /// </summary>
        /// <param name="message">The message parameter.</param>
        /// <returns>Returns true.</returns>
        public bool Message(string message)
        {
            ////log.Debug("starting Message()");
            return this.Write(message, "MESSAGE");
        }

        /// <summary>
        /// This method writes a warning message.
        /// </summary>
        /// <param name="message">The message parameter.</param>
        /// <returns>Returns true.</returns>
        public bool Warn(string message)
        {
            ////log.Debug("starting Warn()");
            return this.Write(message, "WARN");
        }

        /// <summary>
        /// This method writes a message.
        /// </summary>
        /// <param name="message">The message parameter.</param>
        /// <param name="status">The status level parameter.</param>
        /// <returns>Returns true.</returns>
        public bool Write(string message, string status)
        {
            ////log.Debug("starting Write()");
            string insertStatement = "INSERT INTO MESSAGES (TestRunID, Message, MessageTime, MessageLevel) VALUES (" + this.testRunID + ", @message, GETDATE(), '" + status + "')";
            ////log.Debug("insertStatement is " + insertStatement);
            this.connection.Open();
            using (SqlCommand command = new SqlCommand(insertStatement, this.connection))
            {
                command.Parameters.AddWithValue("@message", message.Substring(0, this.messageLength - 1));
                SqlDataReader reader = command.ExecuteReader();
            }

            this.connection.Close();
            return true;
        }

        /// <summary>
        /// This method writes a message.
        /// </summary>
        /// <param name="operationResult">The operation result parameter.</param>
        /// <param name="status">The status level parameter.</param>
        /// <returns>Returns true.</returns>
        public bool Write(OperationResult operationResult, string status)
        {
            ////log.Debug("starting Write() for " + operationResult.Message);
            this.connection.Open();
            this.UpdateBatchInfo(operationResult);
            ////log.Debug("updated Batch information");
            this.UpdateTestInfo(operationResult);
            ////log.Debug("updated Test information");
            this.UpdateRunInfo(operationResult);
            ////log.Debug("updated Run information");
            if (!this.messagesTableExists)
            {
                this.CheckMESSAGESExists();
            }

            ////log.Debug("checked for MESSAGES");
            try
            {
                using (SqlCommand command = new SqlCommand("INSERT INTO MESSAGES (TestRunID, Operation, Message, MessageTime, MessageLevel) VALUES (" + this.testRunID + ", @operation, @message, GETDATE(), '" + status + "')", this.connection))
                {
                    int length = Math.Min(operationResult.Message.Length, this.messageLength);
                    ////log.Debug("length is " + length);
                    string truncatedOperationResultMessage = operationResult.Message.Substring(0, length);
                    ////log.Debug("truncatedOperationResultMessage is " + truncatedOperationResultMessage);
                    command.Parameters.AddWithValue("@message", truncatedOperationResultMessage);
                    command.Parameters.AddWithValue("@operation", operationResult.Id);
                    ////log.Debug("about to execute command");
                    SqlDataReader reader = command.ExecuteReader();
                    /////log.Debug("executed command");
                }

                ////log.Debug("inserted message into MESSAGES");
            }
            catch (Exception e)
            {
                string exceptionMessage = "Exception inserting into MESSAGES " + e.Message;
                log.Warn(exceptionMessage);
            }

            this.connection.Close();
            return true;
        }

        /// <summary>
        /// This method writes an error message.
        /// </summary>
        /// <param name="operationResult">The operation result parameter.</param>
        /// <returns>Returns true.</returns>
        public bool Error(OperationResult operationResult)
        {
            ////log.Debug("starting Error() for " + operationResult.Message);
            return this.Write(operationResult, "ERROR");
        }

        /// <summary>
        /// This method writes a message.
        /// </summary>
        /// <param name="operationResult">The operation result parameter.</param>
        /// <returns>Returns true.</returns>
        public bool Message(OperationResult operationResult)
        {
            ////log.Debug("starting Message() for " + operationResult.Message);
            return this.Write(operationResult, "MESSAGE");
        }

        /// <summary>
        /// This method writes a warning message.
        /// </summary>
        /// <param name="operationResult">The operation result parameter.</param>
        /// <returns>Returns true.</returns>
        public bool Warn(OperationResult operationResult)
        {
            ////log.Debug("starting warn() for " + operationResult.Message);
            return this.Write(operationResult, "WARN");
        }

        /// <summary>
        /// Populates the database connection information. Note that the form of the connection
        /// string can come in different flavours, such as:
        /// server=Krish;database=QAWallsTestResults;Integrated Security=SSPI
        /// Data Source=Krish;Initial Catalog=QAWallsTestResults;User Id=sa;Password=;Integrated Security=SSPI
        /// dbHost=Krish;dbDatabase=QAWallsTestResults;dbUsername=IAINTERNAL\Administrator;dbPassword=tsunami;dbIntegratedSecurity=SSPI
        /// Please add other names if you discover a problem.
        /// </summary>
        /// <param name="configString">The configString parameter.</param>
        public void PopulateMessenger(string configString)
        {
            ////log.Debug("starting populateMessenger()");
            string[] configArray = configString.Split(';');
            foreach (string parameter in configArray)
            {
                string[] nameValue = parameter.Split('=');
                switch (nameValue[0])
                {
                    case "database":
                    case "Database":
                    case "dbDatabase":
                    case "Initial Catalog":
                        log.Debug("database is " + nameValue[1]);
                        this.databaseDatabase = nameValue[1];
                        break;
                    case "Data Source":
                    case "dbHost":
                    case "server":
                    case "Server":
                        log.Debug("dbHost is " + nameValue[1]);
                        this.databaseHost = nameValue[1];
                        break;
                    case "dbPort":
                        log.Debug("dbPort is " + nameValue[1]);
                        this.databasePort = nameValue[1];
                        break;
                    case "dbProtocol":
                        log.Debug("dbProtocol is " + nameValue[1]);
                        this.databaseProtocol = nameValue[1];
                        break;
                    case "dbUsername":
                    case "UID":
                    case "User Id":
                        log.Debug("dbUsername is " + nameValue[1]);
                        this.databaseUsername = nameValue[1];
                        break;
                    case "dbPassword":
                    case "password":
                    case "Password":
                        log.Debug("dbPassword is " + nameValue[1]);
                        this.databasePassword = nameValue[1];
                        break;
                    case "dbIntegratedSecurity":
                    case "Integrated Security":
                        log.Debug("Integrated Security is " + nameValue[1]);
                        this.databaseIntegratedSecurity = nameValue[1];
                        break;
                    case "operationID":
                        log.Debug("operationID is " + nameValue[1]);
                        this.operationID = nameValue[1];
                        break;
                    default:
                        log.Info("parameter name is unrecognized " + nameValue[0]);
                        break;
                }
            }
        }

        /// <summary>
        /// This method creates the database connection.
        /// </summary>
        /// <returns>Returns true.</returns>
        public SqlConnection GetDBConnection()
        {
            ////log.Debug("starting GetDBConnection()");
            string connectionString = "Data Source=" + this.databaseHost + ";Initial Catalog=" + this.databaseDatabase + ";User Id=" + this.databaseUsername + ";Password=" + this.databasePassword;
            if (this.databaseIntegratedSecurity != null)
            {
                connectionString = connectionString + ";Integrated Security=" + this.databaseIntegratedSecurity;
            }

            ////log.Debug("connectionString is " + connectionString);
            SqlConnection connection = new SqlConnection(connectionString);
            return connection;
        }

        /// <summary>
        /// This method updates the batch information.
        /// </summary>
        /// <param name="operationResult">The operation result parameter.</param>
        public void UpdateBatchInfo(OperationResult operationResult)
        {
            ////log.Debug("starting UpdateBatchInfo() for id " + operationResult.Id);
            if (operationResult.Id == "Start Batch")
            {
                ////log.Debug("operation result id is Start Batch");
                if (!this.testBatchesTableExists)
                {
                    log.Debug("TEST_BATCHES may not exist");
                    this.CheckTESTBATCHESExists();
                }

                ////log.Debug("about to start a new batch ID");
                string insertBatchStatement = "INSERT INTO TEST_BATCHES (StartTime) VALUES (GETDATE())";
                using (SqlCommand command = new SqlCommand(insertBatchStatement, this.connection))
                {
                    SqlDataReader reader = command.ExecuteReader();
                    reader.Close();
                }

                ////log.Debug("inserted batch");
            }

            ////log.Debug("about to select test batch id");
            string selectBatchStatement = "SELECT MAX(TestBatchID) AS LastID FROM TEST_BATCHES";
            using (SqlCommand command = new SqlCommand(selectBatchStatement, this.connection))
            {
                SqlDataReader reader = command.ExecuteReader();
                ////log.Debug("executed SELECT MAX(TestBatchID) AS LastID FROM TEST_BATCHES");
                while (reader.Read())
                {
                    if (int.TryParse(reader["LastID"].ToString(), out this.testBatchID))
                    {
                        ////log.Debug("test batch id is " + this.testBatchID);
                    }
                    else
                    {
                        ////log.Debug("failed to retrieve test batch id");
                        reader.Close();
                        string insertBatchStatement = "INSERT INTO TEST_BATCHES (StartTime) VALUES (GETDATE())";
                        using (SqlCommand insertBatchCommand = new SqlCommand(insertBatchStatement, this.connection))
                        {
                            SqlDataReader insertBatchReader = insertBatchCommand.ExecuteReader();
                            insertBatchReader.Close();
                        }

                        ////log.Debug("inserted batch");
                        using (SqlCommand selectBatchCommand = new SqlCommand(selectBatchStatement, this.connection))
                        {
                            SqlDataReader selectBatchReader = selectBatchCommand.ExecuteReader();
                            ////log.Debug("executed SELECT MAX(TestBatchID) AS LastID FROM TEST_BATCHES");
                            while (selectBatchReader.Read())
                            {
                                if (int.TryParse(selectBatchReader["LastID"].ToString(), out this.testBatchID))
                                {
                                    log.Debug("test batch id is " + this.testBatchID);
                                }
                                else
                                {
                                    log.Debug("failed to create test batch id");
                                }
                            }

                            selectBatchReader.Close();
                            ////log.Debug("closed select batch reader");
                        }

                        break;
                    }
                }

                ////log.Debug("testBatchID is " + this.testBatchID);
                reader.Close();
            }

            ////log.Debug("set test batch id");
            if (operationResult.Id == "Finish Batch")
            {
                ////log.Debug("about to set finish time for the current batch");
                string updateBatchStatement = "UPDATE TEST_BATCHES SET FinishTime = GETDATE() WHERE TestBatchID = " + this.testBatchID;
                using (SqlCommand updateBatchCommand = new SqlCommand(updateBatchStatement, this.connection))
                {
                    SqlDataReader updateBatchReader = updateBatchCommand.ExecuteReader();
                    updateBatchReader.Close();
                }

                ////log.Debug("updated batch finish time");
            }  
        }

        /// <summary>
        /// This method updates the test information.
        /// </summary>
        /// <param name="operationResult">The operation result parameter.</param>
        public void UpdateTestInfo(OperationResult operationResult)
        {
            ////log.Debug("starting UpdateTestInfo() for id " + operationResult.Id);
            this.testInfoID = -1;
            /* Update the TEST_INFO table if need be */
            if (!this.testInfoTableExists)
            {
                this.CheckTESTINFOExists();
            }

            ////log.Debug("checked for TEST_INFO");
            int nameTruncationLength = Math.Min(operationResult.Id.Length, this.testNameLength);
            string truncatedOperationResultID = operationResult.Id.Substring(0, nameTruncationLength);
            ////log.Debug("truncatedOperationResultID is " + truncatedOperationResultID);
            string selectTestInfoStatement = "SELECT TestInfoID FROM TEST_INFO WHERE TestName = @truncatedOperationResultID";
            ////log.Debug("selectTestInfoStatement is " + selectTestInfoStatement);
            using (SqlCommand command = new SqlCommand(selectTestInfoStatement, this.connection))
            {
                command.Parameters.AddWithValue("@truncatedOperationResultID", truncatedOperationResultID);
                SqlDataReader reader = command.ExecuteReader();
                while (reader.Read())
                {
                    this.testInfoID = int.Parse(reader["TestInfoID"].ToString());
                }

                ////log.Debug("testInfoID is " + this.testInfoID);
                reader.Close();
            }

            if (this.testInfoID > 0)
            {
                ////log.Debug("testInfoID is " + this.testInfoID);
            }
            else
            {
                string insertTestInfoStatement = "INSERT INTO TEST_INFO (TestName) VALUES (@truncatedOperationResultID)";
                ////log.Debug("insertTestInfoStatement is " + insertTestInfoStatement);
                using (SqlCommand command = new SqlCommand(insertTestInfoStatement, this.connection))
                {
                    command.Parameters.AddWithValue("@truncatedOperationResultID", truncatedOperationResultID);
                    SqlDataReader reader = command.ExecuteReader();
                    reader.Close();
                }

                using (SqlCommand command = new SqlCommand(selectTestInfoStatement, this.connection))
                {
                    command.Parameters.AddWithValue("@truncatedOperationResultID", truncatedOperationResultID);
                    SqlDataReader reader = command.ExecuteReader();
                    while (reader.Read())
                    {
                        this.testInfoID = int.Parse(reader["TestInfoID"].ToString());
                    }

                    ////log.Debug("testInfoID is " + this.testInfoID);
                    reader.Close();
                }
            }
        }

        /// <summary>
        /// This method updates the test run information.
        /// </summary>
        /// <param name="operationResult">The operation result parameter.</param>
        public void UpdateRunInfo(OperationResult operationResult)
        {
            ////log.Debug("starting UpdateRunInfo() for id " + operationResult.Id);
            if (!this.testRunsTableExists)
            {
                this.CheckTESTRUNSExists();
            }

            ////log.Debug("checked for TEST_RUNS");
            if (operationResult.Id == "Start Run")
            {
                ////log.Debug("operationResult.id is " + operationResult.Id);
                /* Now insert into TEST_RUNS table */
                string insertRunStatement = "INSERT INTO TEST_RUNS (TestInfoID, TestBatchID, Operations, StartTime) VALUES (@testInfoID , @testBatchID, @message, GETDATE())";
                ////log.Debug("insertRunStatement is " + insertRunStatement);
                using (SqlCommand insertRunCommand = new SqlCommand(insertRunStatement, this.connection))
                {
                    insertRunCommand.Parameters.AddWithValue("@testInfoID", this.testInfoID);
                    insertRunCommand.Parameters.AddWithValue("@testBatchID", this.testBatchID);
                    insertRunCommand.Parameters.AddWithValue("@message", operationResult.Message);
                    SqlDataReader reader = insertRunCommand.ExecuteReader();
                    reader.Close();
                }

                ////log.Debug("inserted test run");
            }

            string selectTestRunIDStatement = "SELECT MAX(TestRunID) AS LastID FROM TEST_RUNS";
            ////log.Debug("selectTestRunIDStatement is " + selectTestRunIDStatement);
            using (SqlCommand command = new SqlCommand(selectTestRunIDStatement, this.connection))
            {
                SqlDataReader reader = command.ExecuteReader();
                if (!reader.Read())
                {
                    ////log.Debug("reader has no rows");
                    /* Insert initial row into TEST_RUNS table */
                    string insertInitialRunStatement = "INSERT INTO TEST_RUNS (TestInfoID, TestBatchID, Operations, StartTime) VALUES (1, @testBatchID, @message, GETDATE())";
                    ////log.Debug("insertInitialRunStatement is " + insertInitialRunStatement);
                    using (SqlCommand insertInitialCommand = new SqlCommand(insertInitialRunStatement, this.connection))
                    {
                        insertInitialCommand.Parameters.AddWithValue("@testBatchID", this.testBatchID);
                        insertInitialCommand.Parameters.AddWithValue("@message", operationResult.Message);
                        SqlDataReader insertInitialReader = insertInitialCommand.ExecuteReader();
                        insertInitialReader.Close();
                    }

                    this.testRunID = 1;
                    ////log.Debug("inserted test run");                 
                }
                else
                {
                    ////log.Debug("reader has rows");
                    if (reader.IsDBNull(0))
                    {
                        ////log.Debug("last ID is null");
                        reader.Close();
                        /* Insert initial row into TEST_RUNS table */
                        string insertInitialRunStatement = "INSERT INTO TEST_RUNS (TestInfoID, TestBatchID, Operations, StartTime) VALUES (1, @testBatchID, @message, GETDATE())";
                        ////log.Debug("insertInitialRunStatement is " + insertInitialRunStatement);
                        using (SqlCommand insertInitialCommand = new SqlCommand(insertInitialRunStatement, this.connection))
                        {
                            insertInitialCommand.Parameters.AddWithValue("@testBatchID", this.testBatchID);
                            insertInitialCommand.Parameters.AddWithValue("@message", operationResult.Message);
                            SqlDataReader insertInitialReader = insertInitialCommand.ExecuteReader();
                            insertInitialReader.Close();
                        }

                        this.testRunID = 1;
                        ////log.Debug("inserted test run");
                    }
                    else
                    {
                        ////log.Debug("last ID is not null");
                        string testRun = reader["LastID"].ToString();
                        ////log.Debug("testRun is " + testRun);
                        this.testRunID = int.Parse(testRun);
                        ////log.Debug("testRunID is " + this.testRunID);
                    }
                }

                ////log.Debug("testRunID is " + this.testRunID);
                reader.Close();
            }

            if (operationResult.Id == "Finish Run")
            {
                ////log.Debug("operationResult.id is " + operationResult.Id);
                ////log.Debug("operationResult.result is " + operationResult.Result);
                /* Now update TEST_RUNS table */
                string updateRunStatement = "UPDATE TEST_RUNS SET FinishTime = GETDATE(), TestRunStatus = @result, TestStatus = @status WHERE TestRunID = @testRunID";
                ////log.Debug("updateRunStatement is " + updateRunStatement);
                using (SqlCommand command = new SqlCommand(updateRunStatement, this.connection))
                {
                    command.Parameters.AddWithValue("@result", operationResult.Result.ToString());
                    command.Parameters.AddWithValue("@status", operationResult.Status);
                    command.Parameters.AddWithValue("@testRunID", this.testRunID);
                    SqlDataReader reader = command.ExecuteReader();
                    reader.Close();
                }

                ////log.Debug("inserted test run");
            }
        }

        /// <summary>
        /// This method checks that a database table exists and if it does not, can create it.
        /// </summary>
        /// <param name="tableName">The table name parameter.</param>
        /// <param name="tableCreateStatement">The CREATE table statement parameter.</param>
        /// <returns>Returns true if the table now exists.</returns>
        public bool CheckTableExists(string tableName, string tableCreateStatement)
        {
            ////log.Debug("starting checkTableExists()");
            bool tableExists = false;
            string checkTable = string.Format("IF OBJECT_ID('{0}', 'U') IS NOT NULL SELECT 'true' ELSE SELECT 'false'", tableName);
            ////log.Debug("checkTable is " + checkTable);
            SqlCommand checkCommand = new SqlCommand(checkTable, this.connection);
            if (!Convert.ToBoolean(checkCommand.ExecuteScalar()))
            {
                ////log.Debug("table does not exist " + tableName);
                using (SqlCommand command = new SqlCommand(tableCreateStatement, this.connection))
                {
                    ////log.Debug("tableCreateStatement is " + tableCreateStatement);
                    SqlDataReader reader = command.ExecuteReader();
                    reader.Close();
                }

                tableExists = true;
                ////log.Debug("table created " + tableName);
            }
            else
            {
                tableExists = true;
                ////log.Debug("table exists " + tableName);
            }

            return tableExists;
        }

        /// <summary>
        /// This method checks that the MESSAGES database table exists and if it does not, can create it.
        /// </summary>
        /// <returns>Returns true if the MESSAGES table exists.</returns>
        public bool CheckMESSAGESExists()
        {
            return this.CheckTableExists("MESSAGES", "CREATE TABLE MESSAGES (MessageID INT NOT NULL IDENTITY(1,1), TestRunID INT NOT NULL, Operation VARCHAR(1024), Message VARCHAR(" + this.messageLength + "), MessageTime DATETIME, MessageLevel VARCHAR(32), PRIMARY KEY (MessageID), FOREIGN KEY(TestRunID) REFERENCES TEST_RUNS(TestRunID))");
        }

        /// <summary>
        /// This method checks that the TEST_BATCHES database table exists and if it does not, can create it.
        /// </summary>
        /// <returns>Returns true if the TEST_BATCHES table exists.</returns>
        public bool CheckTESTBATCHESExists()
        {
            return this.CheckTableExists("TEST_BATCHES", "CREATE TABLE TEST_BATCHES (TestBatchID INT NOT NULL IDENTITY(1,1), Version VARCHAR(32) DEFAULT NULL, FinishTime DATETIME, StartTime DATETIME, Comment TEXT, PRIMARY KEY(TestBatchID))");
        }

        /// <summary>
        /// This method checks that the TEST_INFO database table exists and if it does not, can create it.
        /// </summary>
        /// <returns>Returns true if the TEST_INFO table exists.</returns>
        public bool CheckTESTINFOExists()
        {
            return this.CheckTableExists("TEST_INFO", "CREATE TABLE TEST_INFO (TestInfoID INT NOT NULL IDENTITY(1,1), TestName VARCHAR(128), TestDescription VARCHAR(1024), PRIMARY KEY (TestInfoID))");
        }

        /// <summary>
        /// This method checks that the TEST_RUNS database table exists and if it does not, can create it.
        /// </summary>
        /// <returns>Returns true if the TEST_RUNS table exists.</returns>
        public bool CheckTESTRUNSExists()
        {
            return this.CheckTableExists("TEST_RUNS", "CREATE TABLE TEST_RUNS (TestRunID INT NOT NULL IDENTITY(1,1), TestInfoID INT NOT NULL, TestBatchID INT NOT NULL, Operations VARCHAR(1024), TestRunStatus VARCHAR(32), TestStatus VARCHAR(32), FinishTime DATETIME, StartTime DATETIME, PRIMARY KEY(TestRunID), FOREIGN KEY(TestInfoID) REFERENCES TEST_INFO(TestInfoID), FOREIGN KEY(TestBatchID) REFERENCES TEST_BATCHES(TestBatchID))");
        }

        /// <summary>
        /// This method deletes test batches older than one month.
        /// </summary>
        /// <returns>Returns a string representing log of executing method.</returns>
        public string DeleteTestBatches()
        {
            /* By default, delete test batches more than one month old */
            TimeSpan timeSpan = DateTime.Now - DateTime.Now.AddMonths(-1);
            return this.DeleteTestBatches(timeSpan);
        }

        /// <summary>
        /// This method deletes test batches for the given lookback period.
        /// Valid period values are: Day, Week, Month.
        /// </summary>
        /// <param name="lookbackPeriod">The lookbackPeriod period.</param>
        /// <returns>Returns a string representing log of executing method.</returns>
        public string DeleteTestBatches(string lookbackPeriod)
        {
            if (lookbackPeriod == null)
            {
                return this.DeleteTestBatches();
            }
            else if (lookbackPeriod.Equals("Day"))
            {
                TimeSpan timeSpan = DateTime.Now - DateTime.Now.AddDays(-1);
                return this.DeleteTestBatches(timeSpan);
            }
            else if (lookbackPeriod.Equals("Week"))
            {
                TimeSpan timeSpan = DateTime.Now - DateTime.Now.AddDays(-7);
                return this.DeleteTestBatches(timeSpan);
            }
            else if (lookbackPeriod.Equals("Month"))
            {
                TimeSpan timeSpan = DateTime.Now - DateTime.Now.AddMonths(-1);
                return this.DeleteTestBatches(timeSpan);
            }
            else
            {
                return this.DeleteTestBatches();
            }
        }

        /// <summary>
        /// This method deletes old test batches.
        /// </summary>
        /// <param name="lookbackTimeSpan">The TimeSpan to look back.</param>
        /// <returns>Returns a string representing log of executing method.</returns>
        public string DeleteTestBatches(TimeSpan lookbackTimeSpan)
        {
            StringBuilder resultStringBuilder = new StringBuilder();
            resultStringBuilder.Append("Starting DeleteTestBatches()" + Environment.NewLine);
            ////TimeSpan timeSpan = DateTime.Now - DateTime.Now.AddMonths(-1);
            ////resultStringBuilder.Append("timeSpan is " + timeSpan + Environment.NewLine);
            DateTime expirationDateTime = DateTime.Now - lookbackTimeSpan;
            resultStringBuilder.Append("expirationDateTime is " + expirationDateTime + Environment.NewLine);
            string sqlDateTime = expirationDateTime.Date.ToString("yyyy-MM-dd HH:mm:ss");
            try
            {
                string whereClause = " WHERE StartTime < '" + sqlDateTime + "'";
                string deleteMessages = "DELETE FROM MESSAGES WHERE TestRunID IN (SELECT TestRunID FROM TEST_RUNS WHERE TestBatchID IN (SELECT TestBatchID FROM TEST_BATCHES" + whereClause + "))";
                resultStringBuilder.Append("deleteMessages is " + deleteMessages + Environment.NewLine);
                string deleteRuns = "DELETE FROM TEST_RUNS WHERE TestRunID IN (SELECT TestRunID FROM TEST_RUNS WHERE TestBatchID IN (SELECT TestBatchID FROM TEST_BATCHES" + whereClause + "))";
                resultStringBuilder.Append("deleteRuns is " + deleteRuns + Environment.NewLine);
                string deleteBatches = "DELETE FROM TEST_BATCHES" + whereClause;
                resultStringBuilder.Append("deleteBatches is " + deleteBatches + Environment.NewLine);
                this.connection = this.GetDBConnection();
                resultStringBuilder.Append("Got connection" + Environment.NewLine);
                this.connection.Open();
                resultStringBuilder.Append("Opened connection to " + this.connection.ConnectionString + Environment.NewLine);
                using (SqlCommand command = new SqlCommand(deleteMessages, this.connection))
                {
                    command.ExecuteNonQuery();
                    resultStringBuilder.Append("Executed deleteMessages" + Environment.NewLine);
                }

                using (SqlCommand command = new SqlCommand(deleteRuns, this.connection))
                {
                    command.ExecuteNonQuery();
                    resultStringBuilder.Append("Executed deleteRuns" + Environment.NewLine);
                }

                using (SqlCommand command = new SqlCommand(deleteBatches, this.connection))
                {
                    command.ExecuteNonQuery();
                    resultStringBuilder.Append("Executed deleteBatches" + Environment.NewLine);
                }

                this.connection.Close();
                log.Debug("finished deleting batches");
                return resultStringBuilder.ToString();
            }
            catch (Exception e)
            {
                string exceptionMessage = "Exception deleting old batches " + e.Message;
                log.Warn(exceptionMessage);
                resultStringBuilder.Append(exceptionMessage + Environment.NewLine);
                return resultStringBuilder.ToString();
            }
        }

        /// <summary>
        /// This method deletes a spcific test batch.
        /// </summary>
        /// <param name="i">The batch ID to be deleted.</param>
        /// <returns>A brief status message.</returns>
        public string DeleteTestBatches(int i)
        {
            StringBuilder resultStringBuilder = new StringBuilder();
            resultStringBuilder.Append("Starting DeleteTestBatches(" + i + ")" + Environment.NewLine);
            try
            {
                string deleteMessages = "DELETE FROM MESSAGES WHERE TestRunID IN (SELECT TestRunID FROM TEST_RUNS WHERE TestBatchID = " + i + ")";
                string deleteRuns = "DELETE FROM TEST_RUNS WHERE TestRunID IN (SELECT TestRunID FROM TEST_RUNS WHERE TestBatchID = " + i + ")";
                string deleteBatches = "DELETE FROM TEST_BATCHES WHERE TestBatchID = " + i;
                this.connection = this.GetDBConnection();
                resultStringBuilder.Append("Got connection to " + this.connection.ConnectionString + Environment.NewLine);
                this.connection.Open();
                resultStringBuilder.Append("Opened connection to " + this.connection.ConnectionString + Environment.NewLine);
                using (SqlCommand command = new SqlCommand(deleteMessages, this.connection))
                {
                    resultStringBuilder.Append("About to execute " + deleteMessages + Environment.NewLine);
                    command.ExecuteNonQuery();
                    resultStringBuilder.Append("Executed " + deleteMessages + Environment.NewLine);
                }

                using (SqlCommand command = new SqlCommand(deleteRuns, this.connection))
                {
                    resultStringBuilder.Append("About to execute " + deleteRuns + Environment.NewLine);
                    command.ExecuteNonQuery();
                    resultStringBuilder.Append("Executed " + deleteRuns + Environment.NewLine);
                }

                using (SqlCommand command = new SqlCommand(deleteBatches, this.connection))
                {
                    resultStringBuilder.Append("About to execute " + deleteBatches + Environment.NewLine);
                    command.ExecuteNonQuery();
                    resultStringBuilder.Append("Executed " + deleteBatches + Environment.NewLine);
                }

                this.connection.Close();
                log.Debug("finished deleting batches" + Environment.NewLine);
                return resultStringBuilder.ToString();
            }
            catch (Exception e)
            {
                string exceptionMessage = "Exception deleting batches " + e.Message;
                log.Warn(exceptionMessage);
                return exceptionMessage;
            }
        }
    }
}
