namespace XCaseGeneric
{
    using System;
    using System.Collections.Generic;
    using System.Xml;
    using XCaseBase;
    using log4net;

    /// <summary>
    /// This action class adds an LDAP entry. The XML file should be of this form:
    /// <operation id="Add an LDAP entry" class="XCaseGeneric.ActionLDAPAddEntry">
    ///     <ldap base="cn=Users,dc=xcase-auk,dc=com" server="Auk" username="Administrator" password="tsunami" newentryname="" newentryschemaclassname="" >
    ///         <property name="" value="" />
    ///         <property name="" value="" />
    ///     </ldap>
    /// </operation>
    /// The ssl attribute is optional.
    /// </summary>
    public class ActionLDAPAddEntry : ActionAbstract
    {
        #region Logger Setup
        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILog log = LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        #endregion

        /// <summary>
        /// This method adds an LDAP entry. The XML file should be of this form:
        /// <operation id="Add an LDAP entry" class="XCaseGeneric.ActionLDAPAddEntry">
        ///     <ldap base="cn=Users,dc=xcase-auk,dc=com" server="Auk" username="Administrator" password="tsunami" newentryname="cn=martin" newentryschemaclassname="user" >
        ///         <property name="cn" value="martin" />
        ///         <property name="sn" value="philip" />
        ///     </ldap>
        /// </operation>
        /// The base, newentryname, and newentryschemaclassname attributes are required.
        /// </summary>
        /// <param name="document">The operation document.</param>
        /// <returns>The result of the operation.</returns>
        public override OperationResult Execute(XmlDocument document)
        {
            log.Debug("starting Execute()");
            try
            {
                OperationResult operationResult = new OperationResult("Success adding LDAP entry");
                XmlNode ldapNode = document.SelectSingleNode("operation/ldap");
                if (ldapNode != null)
                {
                    LDAP ldapDatasource = null;
                    if (ldapNode.SelectSingleNode("@datasource") != null)
                    {
                        string datasourceName = Helper.GetStringNodeValue(ldapNode, "@datasource", true);
                        ldapDatasource = (LDAP)Helper.GetHelperDictionary()[datasourceName];
                    }
                    else
                    {
                        Dictionary<string, string> ldapDictionary = new Dictionary<string, string>();
                        string server = Helper.GetStringNodeValue(ldapNode, "@server", true);
                        log.Debug("server is " + server);
                        ldapDictionary.Add("host", server);
                        string username = Helper.GetStringNodeValue(ldapNode, "@username", false);
                        log.Debug("username is " + username);
                        ldapDictionary.Add("username", username);
                        string password = Helper.GetStringNodeValue(ldapNode, "@password", false);
                        log.Debug("password is " + password);
                        ldapDictionary.Add("password", password);
                        ldapDatasource = new LDAP(ldapDictionary);
                    }
                    
                    string baseString = Helper.GetStringNodeValue(ldapNode, "@base", true);
                    log.Debug("baseString is " + baseString);
                    string newEntryName = Helper.GetStringNodeValue(ldapNode, "@newentryname", true);
                    log.Debug("newEntryName is " + newEntryName);
                    string newEntrySchemaClassName = Helper.GetStringNodeValue(ldapNode, "@newentryschemaclassname", true);
                    log.Debug("newEntrySchemaClassName is " + newEntrySchemaClassName);
                    string uriPath = "LDAP://" + ldapDatasource.Host + "/" + baseString;
                    log.Debug("uriPath is " + uriPath);
                    System.DirectoryServices.DirectoryEntry directoryEntry = new System.DirectoryServices.DirectoryEntry(uriPath, ldapDatasource.Username, ldapDatasource.Password);
                    log.Debug("created base directory entry");
                    System.DirectoryServices.DirectoryEntry newDirectoryEntry = directoryEntry.Children.Add(newEntryName, newEntrySchemaClassName);
                    log.Debug("created new directory entry for " + newEntryName);
                    XmlNodeList propertyNodeList = ldapNode.SelectNodes("property");
                    foreach (XmlNode propertyNode in propertyNodeList)
                    {
                        string propertyName = propertyNode.SelectSingleNode("@name").Value;
                        string propertyValue = propertyNode.SelectSingleNode("@value").Value;
                        newDirectoryEntry.Properties[propertyName].Add(propertyValue);
                        log.Debug("added new directory entry property " + propertyName);
                    }

                    newDirectoryEntry.CommitChanges();
                    log.Debug("added new directory entry");
                }
                else
                {
                    string missingLDAPNodeMessage = "Missing ldap node";
                    log.Warn(missingLDAPNodeMessage);
                    return new OperationResult(false, missingLDAPNodeMessage);
                }

                return operationResult;
            }
            catch (Exception e)
            {
                string exceptionMessage = "Exception adding LDAP entry " + e.Message;
                log.Warn(exceptionMessage);
                return new OperationResult(false, exceptionMessage);
            }
        }
    }
}
