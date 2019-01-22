namespace XCaseGeneric
{
    using System;
    using System.Collections.Generic;
    using System.Xml;
    using XCaseBase;
    using log4net; 

    /// <summary>
    /// This action class creates one or more datasources. The XML file should be of this form:
    /// <operation id="Create FTP datasource" class="XCaseGeneric.ActionCreateDatasource">
    ///     <datasource name="myFTPserver" type="ftp" binary="true|false" password="" path="" file="" server="" ssl="true|false" username="" />
    ///     <datasource name="myDatabase" type="database" database="" encrypted="true|false" multipleactiveresultsets="true|false" password="" server="" supportedtype="" trustedconnection="" username="" />
    ///     ...
    /// </operation>
    /// You can create one or more datasources.
    /// </summary>
    public class ActionCreateDatasource : ActionAbstract
    {
        #region Logger Setup
        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILog log = LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        #endregion

        /// <summary>
        /// This method creates a datasource.
        /// </summary>
        /// <param name="document">The document parameter.</param>
        /// <returns>The operation result.</returns>
        public override OperationResult Execute(XmlDocument document)
        {
            log.Debug("starting Execute()");
            try
            {
                OperationResult operationResult = new OperationResult("Success creating datasource(s)");
                XmlNodeList datasourceNodeList = document.SelectNodes("operation/datasource");
                foreach (XmlNode datasourceNode in datasourceNodeList)
                {
                    string name = Helper.GetStringNodeValue(datasourceNode, "@name", true);
                    log.Debug("name is " + name);
                    string type = Helper.GetStringNodeValue(datasourceNode, "@type", true);
                    log.Debug("type is " + type);
                    Dictionary<string, string> datasourceDictionary = new Dictionary<string, string>();
                    string server = Helper.GetStringNodeValue(datasourceNode, "@server", string.Empty, false);
                    datasourceDictionary.Add("host", server);
                    log.Debug("host is " + server);
                    string domain = Helper.GetStringNodeValue(datasourceNode, "@domain", string.Empty, false);
                    datasourceDictionary.Add("domain", domain);
                    log.Debug("domain is " + domain);
                    string username = Helper.GetStringNodeValue(datasourceNode, "@username", string.Empty, false);
                    datasourceDictionary.Add("username", username);
                    log.Debug("username is " + username);
                    string password = Helper.GetStringNodeValue(datasourceNode, "@password", string.Empty, false);
                    datasourceDictionary.Add("password", password);
                    log.Debug("password is " + password);
                    switch (type)
                    {
                        case "database":
                            log.Debug("datasource type is database");
                            Database databaseDatasource = null;
                            if (datasourceNode.SelectSingleNode("@database") != null)
                            {
                                string database = Helper.GetStringNodeValue(datasourceNode, "@database", string.Empty, false);
                                log.Debug("database is " + database);
                                datasourceDictionary.Add("database", database);
                                string encrypted = Helper.GetStringNodeValue(datasourceNode, "@encrypted", string.Empty, false);
                                log.Debug("encrypted is " + encrypted);
                                datasourceDictionary.Add("encrypted", encrypted);
                                string multipleActiveResultSets = Helper.GetStringNodeValue(datasourceNode, "@multipleactiveresultsets", "false", false);
                                log.Debug("multipleActiveResultSets is " + multipleActiveResultSets);
                                datasourceDictionary.Add("multipleActiveResultSets", multipleActiveResultSets);
                                string supportedType = Helper.GetStringNodeValue(datasourceNode, "@supportedtype", string.Empty, false);
                                log.Debug("supportedType is " + supportedType);
                                datasourceDictionary.Add("supportedType", supportedType);
                                string trustedConnection = Helper.GetStringNodeValue(datasourceNode, "@trustedconnection", "false", false);
                                log.Debug("trustedConnection is " + trustedConnection);
                                datasourceDictionary.Add("trustedConnection", trustedConnection);
                                databaseDatasource = new Database(datasourceDictionary);
                            }
                            else if (datasourceNode.SelectSingleNode("@connectionstring") != null)
                            {
                                string connectionString = Helper.GetStringNodeValue(datasourceNode, "@connectionstring", string.Empty, false);
                                log.Debug("connectionString is " + connectionString);
                                databaseDatasource = new Database(connectionString);
                            }
                            else
                            {
                                log.Warn("neither database or connectionstring defined");
                            }

                            log.Debug("created database datasource");
                            Helper.GetHelperDictionary().Add(name, databaseDatasource);
                            break;
                        case "ftp":
                            log.Debug("datasource type is ftp");
                            bool ssl = Helper.GetBooleanNodeValue(datasourceNode, "@ssl", false, false);
                            log.Debug("ssl is " + ssl);
                            datasourceDictionary.Add("ssl", ssl.ToString());
                            bool binary = Helper.GetBooleanNodeValue(datasourceNode, "@binary", false, false);
                            log.Debug("binary is " + binary);
                            datasourceDictionary.Add("binary", binary.ToString());
                            FTP ftpDatasource = new FTP(datasourceDictionary);
                            log.Debug("created FTP datasource");
                            Helper.GetHelperDictionary().Add(name, ftpDatasource);
                            break;
                        case "http":
                            log.Debug("datasource type is http");
                            HTTP httpDatasource = new HTTP(datasourceDictionary);
                            log.Debug("created HTTP datasource");
                            Helper.GetHelperDictionary().Add(name, httpDatasource);
                            break;
                        case "ldap":
                            log.Debug("datasource type is ldap");
                            LDAP ldapDatasource = new LDAP(datasourceDictionary);
                            log.Debug("created LDAP datasource with host " + ldapDatasource.Host);
                            Helper.GetHelperDictionary().Add(name, ldapDatasource);
                            break;
                        case "webdav":
                            log.Debug("datasource type is webdav");
                            WebDAV webdavDatasource = new WebDAV(datasourceDictionary);
                            log.Debug("created WebDAV datasource");
                            Helper.GetHelperDictionary().Add(name, webdavDatasource);
                            break;
                        case "webservice":
                            log.Debug("datasource type is webservice");
                            string endpoint = Helper.GetStringNodeValue(datasourceNode, "@endpoint", string.Empty, false);
                            log.Debug("endpoint is " + endpoint);
                            datasourceDictionary.Add("endpoint", endpoint);
                            WebService webServiceDatasource = new WebService(datasourceDictionary);
                            log.Debug("created WebService datasource");
                            Helper.GetHelperDictionary().Add(name, webServiceDatasource);
                            break;
                        default:
                            throw new Exception("Unrecognized datasource type " + type);
                    }
                }

                return operationResult;
            }
            catch (Exception e)
            {
                string exceptionMessage = "Exception creating datasource(s) " + e.Message;
                log.Warn(exceptionMessage);
                return new OperationResult(false, exceptionMessage);
            }
        }
    }
}
