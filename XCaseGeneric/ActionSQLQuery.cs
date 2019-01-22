namespace XCaseGeneric
{
    using System;
    using System.Collections.Generic;
    using System.Data.SqlClient;
    using System.Text;
    using System.Xml;
    using XCaseBase;
    using log4net;

    /// <summary>
    /// This action class executes one or more SQL queries.
    /// </summary>
    public class ActionSQLQuery : ActionAbstract
    {
        #region Logger Setup
        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILog log = LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        #endregion

        /// <summary>
        /// This method executes SQL queries. The XmlDocument must be of this form:
        /// <operation id="Execute SQL queries to update CM_RECENTLY_USED table" class="XCaseGeneric.ActionSQLQuery">
        ///     <databasedatasource name="" />
        ///     <connectionstring>Data Source=Krish;Initial Catalog=walls416;User Id=IAINTERNAL\Administrator;Password=tsunami;integrated security=true;</connectionstring>
        ///     <commandstring>INSERT INTO dbo.CM_RECENTLY_USED (TimekeeperID, ClientRemoteSystemID, MatterRemoteSystemID) VALUES (3, 25757000, NULL)</commandstring>
        ///     <commandstring>INSERT INTO dbo.CM_RECENTLY_USED (TimekeeperID, ClientRemoteSystemID, MatterRemoteSystemID) VALUES (4, 25757001, NULL)</commandstring>
        /// </operation>
        /// You can specify the database either as a datasource or as a connection string.
        /// If an exception occurs executing a query, the method continues to execute the others.
        /// </summary>
        /// <param name="document">The operation document.</param>
        /// <returns>The result of the operation.</returns>
        public override OperationResult Execute(XmlDocument document)
        {
            ////log.Debug("starting Execute()");
            try
            {
                OperationResult operationResult = new OperationResult(true, "Successfully executed SQL statements");
                XmlNode operationNode = document.SelectSingleNode("operation");
                string connectionString = "Data Source=Qawin1;Initial Catalog=walls414;User Id=sa;Password=;";
                if (operationNode.SelectSingleNode("databasedatasource") != null)
                {
                    XmlNode databaseDatasourceNode = operationNode.SelectSingleNode("databasedatasource");
                    Database databaseDatasource = null;
                    if (databaseDatasourceNode.SelectSingleNode("@name") != null)
                    {
                        string name = Helper.GetStringNodeValue(databaseDatasourceNode, "@name", true);
                        log.Debug("databasedatasource name is " + name);
                        databaseDatasource = (Database)Helper.GetHelperDictionary()[name];
                    }
                    else if (databaseDatasourceNode.SelectSingleNode("@host") != null)
                    {
                        Dictionary<string, string> databaseDictionary = new Dictionary<string, string>();
                        string host = Helper.GetStringNodeValue(databaseDatasourceNode, "@host", true);
                        databaseDictionary.Add("host", host);
                        string database = Helper.GetStringNodeValue(databaseDatasourceNode, "@database", true);
                        databaseDictionary.Add("database", database);
                        string username = Helper.GetStringNodeValue(databaseDatasourceNode, "@username", true);
                        databaseDictionary.Add("username", username);
                        string password = Helper.GetStringNodeValue(databaseDatasourceNode, "@password", true);
                        databaseDictionary.Add("password", password);
                        string port = Helper.GetStringNodeValue(databaseDatasourceNode, "@port", true);
                        databaseDictionary.Add("port", port);
                        string trustedConnection = Helper.GetStringNodeValue(databaseDatasourceNode, "@trustedconnection", true);
                        databaseDictionary.Add("trustedConnection", trustedConnection);
                        databaseDatasource = new Database(databaseDictionary);
                    }
                    else if (databaseDatasourceNode.SelectSingleNode("@connectionstring") != null)
                    {
                        string databaseDatasourceConnectionString = Helper.GetStringNodeValue(databaseDatasourceNode, "@connectionstring", string.Empty, false);
                        log.Debug("databaseDatasourceConnectionString name is " + databaseDatasourceConnectionString);
                        databaseDatasource = new Database(databaseDatasourceConnectionString);
                    }

                    if (databaseDatasource != null)
                    {
                        connectionString = databaseDatasource.GetConnectionString();
                        log.Debug("connectionString is " + connectionString);
                    }
                    else
                    {
                        string missingDatabaseMessage = "Missing database datasource information";
                        log.Warn(missingDatabaseMessage);
                        return new OperationResult(false, missingDatabaseMessage);
                    }
                }
                else if (operationNode.SelectSingleNode("connectionstring") != null)
                {
                    connectionString = Helper.GetStringNodeValue(operationNode, "connectionstring", true);
                    log.Debug("connectionString is " + connectionString);
                }
                else
                {
                    string missingConnectionStringInformation = "No datasource or connectionstring element defined";
                    log.Warn(missingConnectionStringInformation);
                    return new OperationResult(false, missingConnectionStringInformation);
                }

                XmlNodeList commandStringNodeList = document.SelectNodes("operation/commandstring");
                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    bool sqlResultStatus = true;
                    StringBuilder sqlStringBuilder = new StringBuilder();
                    connection.Open();
                    foreach (XmlNode commandStringNode in commandStringNodeList)
                    {
                        string commandString = commandStringNode.InnerText;
                        log.Debug("commandString is " + commandString);
                        int commandTimeout = Helper.GetIntNodeValue(commandStringNode, "@timeout", -1, false);
                        log.Debug("commandTimeout is " + commandTimeout);
                        using (SqlCommand command = new SqlCommand(commandString, connection))
                        {
                            if (commandTimeout > -1)
                            {
                                command.CommandTimeout = commandTimeout;
                            }

                            /* Try and catch SQL errors for each command */
                            try
                            {
                                SqlDataReader reader = command.ExecuteReader();
                                reader.Close();
                            }
                            catch (Exception e)
                            {
                                string sqlExceptionMessage = "Exception executing SQL query " + e.Message;
                                log.Warn(sqlExceptionMessage);
                                sqlStringBuilder.Append(sqlExceptionMessage + ";");
                                log.Warn(e.InnerException);
                                sqlResultStatus = false;
                            }
                        }
                    }

                    if (!sqlResultStatus)
                    {
                        operationResult.Result = false;
                        operationResult.Message = "Exception executing SQL query against " + connectionString + ": " + sqlStringBuilder.ToString();
                    }
                    else
                    {
                        operationResult.Message = "Successfully executed SQL queries against " + connectionString;
                    }

                    connection.Close();
                }

                return operationResult;
            }
            catch (Exception e)
            {
                string exceptionMessage = "Exception executing SQL query " + e.Message;
                log.Warn(exceptionMessage);
                log.Warn(e.InnerException);
                return new OperationResult(false, exceptionMessage);
            }
        }
    }
}
