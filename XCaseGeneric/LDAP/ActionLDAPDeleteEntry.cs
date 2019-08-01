namespace XCaseGeneric
{
    using System;
    using System.Collections.Generic;
    using System.Xml;
    using XCaseBase;
    using log4net;

    /// <summary>
    /// This action class deletes an LDAP entry. The XML file should be of this form:
    /// <operation id="Delete an LDAP entry" class="XCaseGeneric.ActionLDAPDeleteEntry">
    ///     <ldap base="cn=Users,dc=xcase-ldapserver,dc=com" server="" username="" password="" entryname="cn=martin" />
    /// </operation>
    /// The ssl attribute is optional.
    /// </summary>
    public class ActionLDAPDeleteEntry : ActionAbstract
    {
        #region Logger Setup
        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILog log = LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        #endregion

        /// <summary>
        /// This method deletes an LDAP entry. The XML file should be of this form:
        /// <operation id="Delete an LDAP entry" class="XCaseGeneric.ActionLDAPDeleteEntry">
        ///     <ldap base="cn=Users,dc=xcase-ldapserver,dc=com" server="ldapserver" username="Administrator" password="password" entryname="cn=martin" />
        /// </operation>
        /// The ssl attribute is optional.
        /// </summary>
        /// <param name="document">The operation document.</param>
        /// <returns>The result of the operation.</returns>
        public override OperationResult Execute(XmlDocument document)
        {
            log.Debug("starting Execute()");
            try
            {
                OperationResult operationResult = new OperationResult("Success deleting LDAP entry");
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
                    string entryName = Helper.GetStringNodeValue(ldapNode, "@entryname", true);
                    log.Debug("newEntryName is " + entryName);
                    string uriPath = "LDAP://" + ldapDatasource.Host + "/" + baseString;
                    log.Debug("uriPath is " + uriPath);
                    System.DirectoryServices.DirectoryEntry directoryEntry = new System.DirectoryServices.DirectoryEntry(uriPath, ldapDatasource.Username, ldapDatasource.Password);
                    log.Debug("got base directory entry");
                    System.DirectoryServices.DirectoryEntries childDirectoryEntries = directoryEntry.Children;
                    log.Debug("got child directory entries");
                    System.DirectoryServices.DirectoryEntry deleteObject = childDirectoryEntries.Find(entryName);
                    log.Debug("found object");
                    deleteObject.DeleteTree();
                    log.Debug("deleted tree");
                    directoryEntry.CommitChanges();
                    log.Debug("committed changes");
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
                string exceptionMessage = "Exception deleting LDAP entry " + e.Message;
                log.Warn(exceptionMessage);
                return new OperationResult(false, exceptionMessage);
            }
        }
    }
}
