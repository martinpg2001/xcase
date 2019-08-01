namespace XCaseGeneric
{
    using System;
    using System.Collections.Generic;
    using System.Data.SqlClient;
    using System.Xml;
    using XCaseBase;
    using log4net;

    /// <summary>
    /// This action class tests the connection to a SQL Server database.
    /// </summary>
    public class ActionSQLTestConnection : ActionAbstract
    {
        #region Logger Setup
        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILog log = LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        #endregion

        /// <summary>
        /// This method tests the connection to a SQL Server database. The XmlDocument must be of this form:
        /// <operation id="Execute SQL queries to update CM_RECENTLY_USED table" class="XCaseGeneric.ActionSQLTestConnection">
        ///     <databasedatasource name="" />
        ///     <connectionstring>Data Source=Krish;Initial Catalog=walls416;User Id=XCASE\Administrator;Password=password;integrated security=true;</connectionstring>
        /// </operation>
        /// You can specify the database either as a databasedatasource or as a connection string. If both are specified, then
        /// the databasedatasource takes precedence.
        /// </summary>
        /// <param name="document">The operation document.</param>
        /// <returns>The result of the operation.</returns>
        public override OperationResult Execute(XmlDocument document)
        {
            ////log.Debug("starting Execute()");
            try
            {
                OperationResult operationResult = new OperationResult(true, "Successfully tested connection");
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
                    string missingConnectionStringInformation = "Neither datasource or connectionstring element defined";
                    log.Warn(missingConnectionStringInformation);
                    return new OperationResult(false, missingConnectionStringInformation);
                }

                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    try
                    {
                        connection.Open();
                        string commandString = "SELECT 1";
                        log.Debug("commandString is " + commandString);
                        using (SqlCommand command = new SqlCommand(commandString, connection))
                        {
                            int scalarResult = (int)command.ExecuteScalar();
                            if (scalarResult != 1)
                            {
                                operationResult.Result = false;
                                operationResult.Message = "Connection test result is not 1 for " + connectionString;
                            }
                            else
                            {
                                operationResult.Message = "Successfully tested connection against " + connectionString;
                            }
                        }

                        connection.Close();
                    }
                    catch (Exception e)
                    {
                        string exceptionMessage = "Exception executing testing connection " + e.Message;
                        log.Warn(exceptionMessage);
                        log.Warn(e.InnerException);
                        return new OperationResult(false, exceptionMessage);
                    }
                }

                return operationResult;
            }
            catch (Exception e)
            {
                string exceptionMessage = "Exception executing testing connections " + e.Message;
                log.Warn(exceptionMessage);
                log.Warn(e.InnerException);
                return new OperationResult(false, exceptionMessage);
            }
        }
    }
}
