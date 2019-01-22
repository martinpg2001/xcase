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
    /// This action class checks the values of columns in a database table.
    /// </summary>
    public class ActionSQLCheckTable : ActionAbstract
    {
        #region Logger Setup
        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILog log = LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        #endregion

        /// <summary>
        /// This action class checks the values of columns in a database table. The XML document should be of this form:
        /// <operation id="Query users table" class="XCaseGeneric.ActionSQLCheckTable">
        ///     <datasource name="" />
        ///     <connectionstring>Data Source=Qawin1;Initial Catalog=walls414;User Id=sa;Password=;integrated=false</connectionstring>
        ///     <querystring>SELECT Name, Description FROM USERS WHERE Id = 25</querystring>
        ///     <checkcolumns>
        ///            <checkcolumn name="Name" expectedvalue="Martin" trim="true" />
        ///            <checkcolumn name="Description" expectedvalue="QA Manager" trim="false" />
        ///     </checkcolumns>
        /// </operation>
        /// You can specify the database either as a datasource or as a connection string.
        /// Expected values should be strings. Use the trim attribute to indicate whether actual and expected
        /// values should be trimmed before comparing. By default, values are trimmed.
        /// </summary>
        /// <param name="document">The operation document.</param>
        /// <returns>The result of the operation.</returns>
        public override OperationResult Execute(XmlDocument document)
        {
            ////log.Debug("starting Execute()");
            try
            {
                OperationResult operationResult = new OperationResult(true, "Successfully checked table");
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

                XmlNode querystringNode = document.SelectSingleNode("operation/querystring");
                if (querystringNode != null)
                {
                    log.Debug("querystringNode is not null");
                    string queryString = Helper.GetStringNodeValue(document, "operation/querystring", string.Empty, true);
                    log.Debug("queryString is " + queryString);
                    StringBuilder sqlStringBuilder = new StringBuilder();
                    /* If checkcolumns node contains entries check that values in checkcolumn matches */
                    if (document.SelectNodes("operation/checkcolumns/checkcolumn").Count > 0)
                    {
                        XmlNodeList checkColumnsNodeList = document.SelectNodes("operation/checkcolumns/checkcolumn");
                        using (SqlConnection connection = new SqlConnection(connectionString))
                        {
                            bool sqlResultStatus = true;
                            connection.Open();
                            using (SqlCommand command = new SqlCommand(queryString, connection))
                            {
                                /* Try and catch SQL errors for each command */
                                try
                                {
                                    SqlDataReader reader = command.ExecuteReader();
                                    if (reader.Read())
                                    {
                                        foreach (XmlNode checkColumnNode in checkColumnsNodeList)
                                        {
                                            bool trim = true;
                                            string column = Helper.GetStringNodeValue(checkColumnNode, "@name", string.Empty, false);
                                            log.Debug("column is " + column);
                                            string expectedValue = Helper.GetStringNodeValue(checkColumnNode, "@expectedvalue", string.Empty, false);
                                            log.Debug("expectedValue is " + expectedValue);
                                            if (checkColumnNode.SelectSingleNode("@trim") != null)
                                            {
                                                //bool.TryParse(checkColumnNode.SelectSingleNode("@trim").Value, out trim);
                                                trim = Helper.GetBooleanNodeValue(checkColumnNode, "@trim", false, true);
                                            }

                                            string actualValue = reader[column].ToString();
                                            log.Debug("actualValue is " + actualValue);
                                            if (trim)
                                            {
                                                log.Debug("trim values");
                                                expectedValue = expectedValue.Trim();
                                                actualValue = actualValue.Trim();
                                            }

                                            if (actualValue != expectedValue)
                                            {
                                                sqlResultStatus = false;
                                                sqlStringBuilder.Append("Column " + column + " actual value '" + actualValue + "' and expected value '" + expectedValue + "' do not match");
                                            }
                                        }
                                    }
                                    else
                                    {
                                        log.Warn("no rows returned");
                                        sqlStringBuilder.Append("No rows returned;");
                                        sqlResultStatus = false;
                                    }

                                    reader.Close();
                                }
                                catch (Exception e)
                                {
                                    string sqlExceptionMessage = "Exception executing SQL query " + e.Message;
                                    log.Warn(sqlExceptionMessage);
                                    sqlStringBuilder.Append(sqlExceptionMessage + ";");
                                    sqlResultStatus = false;
                                }
                            }

                            if (!sqlResultStatus)
                            {
                                operationResult.Result = false;
                                operationResult.Message = "Error: " + sqlStringBuilder.ToString();
                            }

                            connection.Close();
                        }
                    }
                    else
                    {
                        /* If checkcolumns node does not contain entries, then check that queryString returns no rows */
                        using (SqlConnection connection = new SqlConnection(connectionString))
                        {
                            bool sqlResultStatus = true;
                            connection.Open();
                            using (SqlCommand command = new SqlCommand(queryString, connection))
                            {
                                /* Try and catch SQL errors for each command */
                                try
                                {
                                    SqlDataReader reader = command.ExecuteReader();

                                    if (reader.HasRows)
                                    {
                                        log.Warn("expected no rows, but rows are returned");
                                        sqlStringBuilder.Append("Expected no rows, but rows are returned;");
                                        sqlResultStatus = false;
                                    }

                                    reader.Close();
                                }
                                catch (Exception e)
                                {
                                    string sqlExceptionMessage = "Exception executing SQL query " + e.Message;
                                    log.Warn(sqlExceptionMessage);
                                    sqlStringBuilder.Append(sqlExceptionMessage + ";");
                                    sqlResultStatus = false;
                                }
                            }

                            if (!sqlResultStatus)
                            {
                                operationResult.Result = false;
                                operationResult.Message = "Error: " + sqlStringBuilder.ToString();
                            }

                            connection.Close();
                        }
                    }
                }
                else
                {
                    log.Warn("querystring node is null");
                }

                return operationResult;
            }
            catch (Exception e)
            {
                string exceptionMessage = "Exception checking table " + e.Message;
                log.Warn(exceptionMessage);
                return new OperationResult(false, exceptionMessage);
            }
        }
    }
}
