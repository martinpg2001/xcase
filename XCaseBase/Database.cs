namespace XCaseBase
{
    using System;
    using System.Collections.Generic;
    using System.Data.SqlClient;
    using log4net; 

    /// <summary>
    /// This class represents a database datasource.
    /// </summary>
    public class Database : Datasource
    {
        #region Logger Setup
        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILog log = LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        #endregion

        /// <summary>
        /// This field.
        /// </summary>
        public const int SQLSERVER = 0;

        /// <summary>
        /// This field.
        /// </summary>
        public const int SQLSERVER2005 = 5;

        /// <summary>
        /// This field.
        /// </summary>
        public const int SQLSERVER2008 = 8;

        /// <summary>
        /// This field.
        /// </summary>
        public const int SQLSERVERAZURE = 9;

        /// <summary>
        /// This field.
        /// </summary>
        public const int ORACLE = 10;

        /// <summary>
        /// This field.
        /// </summary>
        public const int DB2 = 20;

        /// <summary>
        /// This field.
        /// </summary>
        public const int INFORMIXSE = 30;

        /// <summary>
        /// This field.
        /// </summary>
        public const int INFORMIXIDS = 35;

        /// <summary>
        /// This field.
        /// </summary>
        public const int MYSQL3OR4 = 40;

        /// <summary>
        /// This field.
        /// </summary>
        public const int MYSQL5 = 45;

        /// <summary>
        /// This field.
        /// </summary>
        public const int ODBCEXCEL = 50;

        /// <summary>
        /// This field.
        /// </summary>
        public const int ODBCACCESS = 60;

        /// <summary>
        /// This field.
        /// </summary>
        public const int ODBCPROGRESS = 65;

        /// <summary>
        /// This field.
        /// </summary>
        public const int POSTGRESQL = 70;

        /// <summary>
        /// This field.
        /// </summary>
        public const int UNRECOGNIZED = -10;

        /// <summary>
        /// Initializes a new instance of the Database class using a Dictionary of property values.
        /// </summary>
        /// <param name="argv">The argv parameter.</param>
        public Database(Dictionary<string, string> argv)
        {
            log.Debug("starting Dictionary constructor");
            this.SupportedType = (string)argv["supportedType"];
            log.Debug("SupportedType is " + this.SupportedType);
            this.SupportedTypeInt = this.ConvertSupportedType(this.SupportedType);
            switch (this.SupportedTypeInt)
            {
                case SQLSERVER:
                case SQLSERVER2005:
                case SQLSERVER2008:
                case SQLSERVERAZURE:
                    ////System.out.println("Database: Case Microsoft SQL Server");
                    this.DbType = "jtds:sqlserver";
                    this.Driver = "net.sourceforge.jtds.jdbc.Driver";
                    this.Encrypted = "false";
                    this.Port = "1433";
                    this.Username = "sa";
                    this.Password = string.Empty;
                    break;
                case ORACLE:
                    ////println("Database: Case Oracle")
                    this.DbType = "oracle:thin";
                    this.Port = "1521";
                    this.Driver = "oracle.jdbc.driver.OracleDriver";
                    this.Username = "system";
                    this.Password = "password";
                    break;
                case MYSQL3OR4:
                case MYSQL5:
                    ////System.out.println("Database: Case MySQL");
                    this.DbType = "mysql";
                    this.Port = "3306";
                    this.Driver = "com.mysql.jdbc.Driver";
                    this.Username = "root";
                    this.Password = string.Empty;
                    break;
                case DB2:
                    ////println("Database: Case DB2")
                    this.DbType = "db2";
                    this.Port = "50000";
                    this.Driver = "com.ibm.db2.jcc.DB2Driver";
                    this.Username = string.Empty;
                    this.Password = string.Empty;
                    break;
                case INFORMIXSE:
                    ////println("Database: Case Informix SE")
                    this.DbType = "informix-sqli";
                    this.Port = string.Empty;
                    this.Driver = "com.informix.jdbc.IfxDriver";
                    this.Username = string.Empty;
                    this.Password = string.Empty;
                    break;
                case INFORMIXIDS:
                    ////println("Database: Case Informix IDS")
                    this.DbType = "informix-sqli";
                    this.Port = string.Empty;
                    this.Driver = "com.informix.jdbc.IfxDriver";
                    this.Username = string.Empty;
                    this.Password = string.Empty;
                    break;
                case ODBCEXCEL:
                    ////println("Database: Case MS Excel")
                    ////leave this blank for now
                    this.DbType = string.Empty;
                    this.Port = "10120";
                    this.Driver = string.Empty;
                    this.Username = string.Empty;
                    this.Password = string.Empty;
                    break;
                case ODBCACCESS:
                    ////println("Database: Case MS Access")
                    ////leave this blank for now
                    this.DbType = string.Empty;
                    this.Port = "10120";
                    this.Driver = string.Empty;
                    this.Username = string.Empty;
                    this.Password = string.Empty;
                    break;
                case ODBCPROGRESS:
                    ////println("Database: Case ODBC Progress")
                    ////leave this blank for now
                    this.DbType = string.Empty;
                    this.Port = "10120";
                    this.Driver = string.Empty;
                    this.Username = string.Empty;
                    this.Password = string.Empty;
                    break;
                case POSTGRESQL:
                    ////println("Database: Case PostgreSQL")
                    this.DbType = "postgresql";
                    this.Port = "5432";
                    this.Driver = "org.postgresql.Driver";
                    this.Username = string.Empty;
                    this.Password = string.Empty;
                    break;
                default:
                    throw new Exception("Could not find database type " + this.SupportedType);
            }

            log.Debug("about to override default parameters");
            this.DatabaseName = argv.ContainsKey("database") ? (string)argv["database"] : this.DatabaseName;
            log.Debug("DatabaseName is " + this.DatabaseName);
            this.DbType = argv.ContainsKey("dbType") ? (string)argv["dbType"] : this.DbType;
            log.Debug("DbType is " + this.DbType);
            this.Driver = argv.ContainsKey("driver") ? (string)argv["driver"] : this.DbType;
            log.Debug("Driver is " + this.Driver);
            this.Encrypted = argv.ContainsKey("encrypted") ? (string)argv["encrypted"] : "false";
            log.Debug("Encrypted is " + this.Encrypted);
            this.Host = argv.ContainsKey("host") ? (string)argv["host"] : string.Empty;
            log.Debug("Host is " + this.Host);
            this.MultipleActiveResultSets = argv.ContainsKey("multipleActiveResultSets") ? (string)argv["multipleActiveResultSets"] : this.MultipleActiveResultSets;
            log.Debug("MultipleActiveResultSets is " + this.MultipleActiveResultSets);
            this.Password = argv.ContainsKey("password") ? (string)argv["password"] : this.Password;
            log.Debug("Password is " + this.Password);
            this.Port = argv.ContainsKey("port") ? (string)argv["port"] : this.Port;
            log.Debug("Port is " + this.Port);
            this.TrustedConnection = argv.ContainsKey("trustedConnection") ? (string)argv["trustedConnection"] : this.TrustedConnection;
            log.Debug("TrustedConnection is " + this.TrustedConnection);
            this.Username = argv.ContainsKey("username") ? (string)argv["username"] : this.Username;
            log.Debug("Username is " + this.Username);
            string connectionString = "Data Source=" + this.Host + ";Initial Catalog=" + this.DatabaseName + ";User Id=" + this.Username + ";Password=" + this.Password + ";Trusted_Connection=" + this.TrustedConnection + ";";
            bool encrypted = false;
            if (bool.TryParse(this.Encrypted, out encrypted))
            {
                connectionString = connectionString + "Encrypt=" + encrypted + ";";
            }

            bool multipleActiveResultSets = false;
            if (bool.TryParse(this.MultipleActiveResultSets, out multipleActiveResultSets))
            {
                connectionString = connectionString + "MultipleActiveResultSets=" + multipleActiveResultSets + ";";
            }

            log.Debug("connectionString is " + connectionString);
            this.ConnectionString = connectionString;
        }

        /// <summary>
        /// Initializes a new instance of the Database class using a connection string.
        /// </summary>
        /// <param name="connectionString">The connectionString parameter.</param>
        public Database(string connectionString)
        {
            this.ConnectionString = connectionString;
        }

        /// <summary>
        /// Initializes a new instance of the Database class using a datasource node.
        /// </summary>
        /// <param name="databaseDatasourceNode">The databaseDatasourceNode parameter.</param>
        public Database(System.Xml.XmlNode databaseDatasourceNode)
        {
            log.Debug("starting XmlNode constructor");
            this.SupportedType = Helper.GetStringNodeValue(databaseDatasourceNode, "@supportedtype", string.Empty, true);
            log.Debug("SupportedType is " + this.SupportedType);
            this.SupportedTypeInt = this.ConvertSupportedType(this.SupportedType);
            switch (this.SupportedTypeInt)
            {
                case SQLSERVER:
                case SQLSERVER2005:
                case SQLSERVER2008:
                case SQLSERVERAZURE:
                    ////System.out.println("Database: Case Microsoft SQL Server");
                    this.DbType = "jtds:sqlserver";
                    this.Driver = "net.sourceforge.jtds.jdbc.Driver";
                    this.Encrypted = "false";
                    this.Port = "1433";
                    this.Username = "sa";
                    this.Password = string.Empty;
                    break;
                case ORACLE:
                    ////println("Database: Case Oracle")
                    this.DbType = "oracle:thin";
                    this.Port = "1521";
                    this.Driver = "oracle.jdbc.driver.OracleDriver";
                    this.Username = "system";
                    this.Password = "password";
                    break;
                case MYSQL3OR4:
                case MYSQL5:
                    ////System.out.println("Database: Case MySQL");
                    this.DbType = "mysql";
                    this.Port = "3306";
                    this.Driver = "com.mysql.jdbc.Driver";
                    this.Username = "root";
                    this.Password = string.Empty;
                    break;
                case DB2:
                    ////println("Database: Case DB2")
                    this.DbType = "db2";
                    this.Port = "50000";
                    this.Driver = "com.ibm.db2.jcc.DB2Driver";
                    this.Username = string.Empty;
                    this.Password = string.Empty;
                    break;
                case INFORMIXSE:
                    ////println("Database: Case Informix SE")
                    this.DbType = "informix-sqli";
                    this.Port = string.Empty;
                    this.Driver = "com.informix.jdbc.IfxDriver";
                    this.Username = string.Empty;
                    this.Password = string.Empty;
                    break;
                case INFORMIXIDS:
                    ////println("Database: Case Informix IDS")
                    this.DbType = "informix-sqli";
                    this.Port = string.Empty;
                    this.Driver = "com.informix.jdbc.IfxDriver";
                    this.Username = string.Empty;
                    this.Password = string.Empty;
                    break;
                case ODBCEXCEL:
                    ////println("Database: Case MS Excel")
                    ////leave this blank for now
                    this.DbType = string.Empty;
                    this.Port = "10120";
                    this.Driver = string.Empty;
                    this.Username = string.Empty;
                    this.Password = string.Empty;
                    break;
                case ODBCACCESS:
                    ////println("Database: Case MS Access")
                    ////leave this blank for now
                    this.DbType = string.Empty;
                    this.Port = "10120";
                    this.Driver = string.Empty;
                    this.Username = string.Empty;
                    this.Password = string.Empty;
                    break;
                case ODBCPROGRESS:
                    ////println("Database: Case ODBC Progress")
                    ////leave this blank for now
                    this.DbType = string.Empty;
                    this.Port = "10120";
                    this.Driver = string.Empty;
                    this.Username = string.Empty;
                    this.Password = string.Empty;
                    break;
                case POSTGRESQL:
                    ////println("Database: Case PostgreSQL")
                    this.DbType = "postgresql";
                    this.Port = "5432";
                    this.Driver = "org.postgresql.Driver";
                    this.Username = string.Empty;
                    this.Password = string.Empty;
                    break;
                default:
                    throw new Exception("Could not find database type " + this.SupportedType);
            }

            log.Debug("about to override default parameters");
            this.DatabaseName = Helper.GetStringNodeValue(databaseDatasourceNode, "@database", string.Empty, true);
            log.Debug("DatabaseName is " + this.DatabaseName);
            this.DbType = Helper.GetStringNodeValue(databaseDatasourceNode, "@dbtype", string.Empty, false);
            log.Debug("DbType is " + this.DbType);
            this.Driver = Helper.GetStringNodeValue(databaseDatasourceNode, "@driver", string.Empty, false);
            log.Debug("Driver is " + this.Driver);
            this.Encrypted = Helper.GetStringNodeValue(databaseDatasourceNode, "@encrypted", "False", true);
            log.Debug("Encrypted is " + this.Encrypted);
            Helper.GetStringNodeValue(databaseDatasourceNode, "@host", string.Empty, true);
            log.Debug("Host is " + this.Host);
            this.MultipleActiveResultSets = Helper.GetStringNodeValue(databaseDatasourceNode, "@multipleactiveresultsets", "False", false);
            log.Debug("MultipleActiveResultSets is " + this.MultipleActiveResultSets);
            this.Password = Helper.GetStringNodeValue(databaseDatasourceNode, "@password", string.Empty, false);
            log.Debug("Password is " + this.Password);
            this.Port = Helper.GetStringNodeValue(databaseDatasourceNode, "@port", "1433", false);
            log.Debug("Port is " + this.Port);
            this.TrustedConnection = Helper.GetStringNodeValue(databaseDatasourceNode, "@trustedconnection", "False", false);
            log.Debug("TrustedConnection is " + this.TrustedConnection);
            this.Username = Helper.GetStringNodeValue(databaseDatasourceNode, "@username", string.Empty, true);
            log.Debug("Username is " + this.Username);
            string connectionString = "Data Source=" + this.Host + ";Initial Catalog=" + this.DatabaseName + ";User Id=" + this.Username + ";Password=" + this.Password + ";Trusted_Connection=" + this.TrustedConnection + ";";
            bool encrypted = false;
            if (bool.TryParse(this.Encrypted, out encrypted))
            {
                connectionString = connectionString + "Encrypt=" + encrypted + ";";
            }

            bool multipleActiveResultSets = false;
            if (bool.TryParse(this.MultipleActiveResultSets, out multipleActiveResultSets))
            {
                connectionString = connectionString + "MultipleActiveResultSets=" + multipleActiveResultSets + ";";
            }

            log.Debug("connectionString is " + connectionString);
            this.ConnectionString = connectionString;
        }

        /// <summary>
        /// Gets or sets.
        /// </summary>
        public string ConnectionString { get; set; }

        /// <summary>
        /// Gets or sets.
        /// </summary>
        public string DatabaseName { get; set; }

        /// <summary>
        /// Gets or sets.
        /// </summary>
        public string DbType { get; set; }

        /// <summary>
        /// Gets or sets.
        /// </summary>
        public string Driver { get; set; }

        /// <summary>
        /// Gets or sets.
        /// </summary>
        public string Encrypted { get; set; }

        /// <summary>
        /// Gets or sets.
        /// </summary>
        public string MultipleActiveResultSets { get; set; }

        /// <summary>
        /// Gets or sets.
        /// </summary>
        public string Port { get; set; }

        /// <summary>
        /// Gets or sets.
        /// </summary>
        public string SupportedType { get; set; }

        /// <summary>
        /// Gets or sets.
        /// </summary>
        public int SupportedTypeInt { get; set; }

        /// <summary>
        /// Gets or sets.
        /// </summary>
        public string TrustedConnection { get; set; }

        /// <summary>
        /// This method.
        /// </summary>
        /// <returns>The database type.</returns>
        public SqlConnection GetConnection()
        {
            ////System.out.println("Database: starting getConnection()");
            string url = this.GetUrl();
            ////System.out.println("Database: url is " + url);
            string connectionUsername = this.GetLoginUsername();
            ////System.out.println("Database: connectionUsername is " + connectionUsername);
            ////System.out.println("Database: password is " + password);
            ////System.out.println("Database: driver is " + driver);
            return new SqlConnection(this.GetConnectionString());
        }

        /// <summary>
        /// This method returns the connection string for SQL Server databases.
        /// </summary>
        /// <returns>The database type.</returns>
        public string GetConnectionString()
        {
            return this.ConnectionString;
        }

        /// <summary>
        /// This method sets the database connection string.
        /// </summary>
        /// <param name="connectionString">The connectionString parameter.</param>
        public void SetConnectionString(string connectionString)
        {
            this.ConnectionString = connectionString;
        }

        /// <summary>
        /// This method.
        /// </summary>
        /// <param name="statement">The database type.</param>
        public void Execute(string statement)
        {
            log.Debug("starting Execute()");
            if (!statement.Equals(string.Empty))
            {
                ////System.out.println("Database: about to get connection");
                SqlConnection sql = this.GetConnection();
                ////System.out.println("Database: got connection");
                try
                {
                    ////System.out.println("Database: statement is " + statement);
                    ////sql.execute(statement);
                    ////System.out.println("Database: executed statement");
                }
                catch (Exception e)
                {
                    log.Debug("errored executing statement " + e.ToString());
                    throw e;
                }
                finally
                {
                    sql.Close();
                    ////System.out.println("Database: closed connection");
                }
            }

            log.Debug("finishing Execute()");
        }

        /// <summary>
        /// This method.
        /// </summary>
        /// <param name="statement">The statement parameter.</param>
        /// <param name="values">The values parameter.</param>
        public void Execute(string statement, List<object> values)
        {
            log.Debug("starting Execute()");
            if (!statement.Equals(string.Empty))
            {
                SqlConnection sql = this.GetConnection();
                try
                {
                    ////sql.execute(statement, values);
                }
                finally
                {
                    sql.Close();
                }
            }
        }

        /// <summary>
        /// This method.
        /// </summary>
        /// <param name="statements">The database type.</param>
        public void Execute(List<string> statements)
        {
            log.Debug("starting Execute()");
            if (statements.Count != 0)
            {
                SqlConnection sql = this.GetConnection();
                try
                {
                    for (int i = 0; i < statements.Count; i++)
                    {
                        string statement = (string)statements[i];
                        log.Debug("statement is " + statement);
                    }
                }
                catch (Exception e)
                {
                    log.Debug("errored executing statement " + e.ToString());
                    throw e;
                }
                finally
                {
                    sql.Close();
                }
            }
        }

        ////public void eachRow(string statement, Closure cl)
        ////{
        ////    //System.out.println("Database: starting eachRow()");
        ////    if (!statement.equals("")) {
        ////        //System.out.println("Database: statement is " + statement);
        ////        SqlConnection sql = GetConnection();
        ////        //System.out.println("Database: got connection");
        ////        try {
        ////            ////sql.eachRow(statement, cl);
        ////        } catch (Exception e) {
        ////            log.Debug("errored executing statement " + e.ToString());
        ////            throw e;
        ////        } finally {
        ////            sql.Close();
        ////        }
        ////    }
        ////    //System.out.println("Database: finishing eachRow()");
        ////}

        /// <summary>
        /// This method get the domain name from NT Authentication logins
        /// of the form DOMAIN (backslash) username.
        /// </summary>
        /// <returns>The database type.</returns>
        public string GetDomain()
        {
            if (this.Username.Contains("\\"))
            {
                char[] backSlash = { '\\' };
                return this.Username.Split(backSlash)[0];
            }
            else
            {
                return string.Empty;
            }
        }

        /// <summary>
        /// Return only the login name of an NT Authentication login
        /// of the form DOMAIN (backslash) username.  If the username
        /// is not in this form, then the full username is returned.
        /// </summary>
        /// <returns>The database type.</returns>
        public string GetLoginUsername()
        {
            log.Debug("starting GetLoginUsername()");
            if (this.Username.Contains("\\"))
            {
                char[] backSlash = { '\\' };
                string loginUsername = this.Username.Split(backSlash)[1];
                log.Debug("loginUsername is " + loginUsername);
                return loginUsername;
            }
            else
            {
                log.Debug("Username is " + this.Username);
                return this.Username;
            }
        }

        /// <summary>
        /// This method.
        /// </summary>
        /// <returns>The url.</returns>
        public string GetUrl()
        {
            log.Debug("starting GetUrl()");
            string url = string.Empty;
            log.Debug("supportedType is " + this.SupportedType);
            this.SupportedTypeInt = this.ConvertSupportedType(this.SupportedType);
            switch (this.SupportedTypeInt)
            {
                case SQLSERVER:
                case SQLSERVER2005:
                case SQLSERVER2008:
                case SQLSERVERAZURE:
                    url = "jdbc:" + this.DbType + "://" + this.Host + ":" + this.Port + "/" + this.DatabaseName;
                    string domain = this.GetDomain();
                    if (!domain.Equals(string.Empty))
                    {
                        url = url + ";domain=" + domain;
                    }

                    break;
                case ORACLE:
                    url = "jdbc:" + this.DbType + ":@" + this.Host + ":" + this.Port + ":" + this.DatabaseName;
                    break;
                case MYSQL3OR4:
                case MYSQL5:
                case POSTGRESQL:
                    ////System.out.println("Database: case is MySQL");
                    url = "jdbc:" + this.DbType + "://" + this.Host + ":" + this.Port + "/" + this.DatabaseName;
                    break;
                case DB2:
                    url = "jdbc:" + this.DbType + "://" + this.Host + ":" + this.Port + "/" + this.DatabaseName;
                    break;
                case INFORMIXIDS:
                    string appendIDSSpace = string.Empty;
                    if (this.DatabaseName.StartsWith("/"))
                    {
                        appendIDSSpace = " ";
                    }

                    url = "jdbc:" + this.DbType + "://" + this.Host + ":" + this.Port + "/" + appendIDSSpace + this.DatabaseName;
                    break;
                case INFORMIXSE:
                    string appendSpace = string.Empty;
                    if (this.DatabaseName.StartsWith("/"))
                    {
                        appendSpace = " ";
                    }

                    url = "jdbc:" + this.DbType + "://" + this.Host + ":" + this.Port + "/" + appendSpace + this.DatabaseName;
                    break;
                default:
                    throw new Exception("Could not find database type " + this.SupportedType);
            }
            ////println("Database: url is ${url}")
            return url;
        }

        /// <summary>
        /// This method is used to set database-specific properties for a database that has been created
        /// using the DatabaseServer.GetNewDatabase() method.
        /// </summary>
        /// <param name="databaseName">The dbName parameter.</param>
        public void SetDatabaseProperties(string databaseName)
        {
            log.Debug("starting SetDatabaseProperties()");
            this.SupportedTypeInt = this.ConvertSupportedType(this.SupportedType);
            switch (this.SupportedTypeInt)
            {
                case SQLSERVER:
                case SQLSERVER2005:
                case SQLSERVER2008:
                case SQLSERVERAZURE:
                    this.DatabaseName = databaseName;
                    break;
                case ORACLE:
                    this.Username = databaseName;
                    this.Password = databaseName;
                    break;
                case MYSQL3OR4:
                case MYSQL5:
                case POSTGRESQL:
                    this.DatabaseName = databaseName;
                    break;
                case DB2:
                    break;
                case INFORMIXIDS:
                    break;
                case INFORMIXSE:
                    break;
                default:
                    throw new Exception("Could not find database type " + this.SupportedType);
            }

            log.Debug("finishing SetDatabaseProperties()");
        }

        /// <summary>
        /// This method.
        /// </summary>
        /// <param name="supportedType">The suportedType parameter.</param>
        /// <returns>The database type.</returns>
        public int ConvertSupportedType(string supportedType)
        {
            log.Debug("starting ConvertSupportedType()");
            if (supportedType != null)
            {
                if (supportedType.Equals("Microsoft SQL Server 2000") || supportedType.Equals("Microsoft SQL Server 6.5") || supportedType.Equals("Microsoft SQL Server 7.0"))
                {
                    return SQLSERVER;
                }
                else if (supportedType.Equals("Microsoft SQL Server 2005"))
                {
                    return SQLSERVER2005;
                }
                else if (supportedType.Equals("Microsoft SQL Server 2008"))
                {
                    return SQLSERVER2008;
                }
                else if (supportedType.Equals("Microsoft SQL Server Azure"))
                {
                    return SQLSERVERAZURE;
                }
                else if (supportedType.Equals("Oracle 8i") || supportedType.Equals("Oracle 9i") || supportedType.Equals("Oracle 10g") || supportedType.Equals("Oracle 11g"))
                {
                    return ORACLE;
                }
                else if (supportedType.Equals("MySQL 3.x") || supportedType.Equals("4.x") || supportedType.Equals("MySQL 3.x, 4.x"))
                {
                    return MYSQL3OR4;
                }
                else if (supportedType.Equals("MySQL 5.0"))
                {
                    return MYSQL5;
                }
                else if (supportedType.Equals("PostgreSQL"))
                {
                    return POSTGRESQL;
                }
                else if (supportedType.Equals("IBM DB2 UDB for Windows/Unix/Linux"))
                {
                    return DB2;
                }
                else if (supportedType.Equals("Informix Online/Dynamic Server"))
                {
                    return INFORMIXIDS;
                }
                else if (supportedType.Equals("Informix SE"))
                {
                    return INFORMIXSE;
                }
                else if (supportedType.Equals("ODBC for MS Excel"))
                {
                    return ODBCEXCEL;
                }
                else if (supportedType.Equals("ODBC for MS Access"))
                {
                    return ODBCACCESS;
                }
                else if (supportedType.Equals("ODBC for Progress OpenEdge"))
                {
                    return ODBCPROGRESS;
                }
                else
                {
                    return UNRECOGNIZED;
                }
            }
            else
            {
                return UNRECOGNIZED;
            }
        }

        /// <summary>
        /// This method returns the datasource string.
        /// </summary>
        /// <returns>The datasource string.</returns>
        public string ToDatasourceString()
        {
            return "Database:" + this.Host + ":" + this.Port + ":" + this.DatabaseName + ":" + this.Username + ":" + this.Password;
        }
    }
}
