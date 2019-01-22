namespace XCaseGeneric
{
    using System;
    using System.Collections.Generic;
    using System.Xml;
    using XCaseBase;
    using log4net;

    /// <summary>
    /// This action class searches an LDAP directory. The XML file should be of this form:
    /// <operation id="Search LDAP directory" class="XCaseGeneric.ActionLDAPSearchDirectory">
    ///     <ldap server="auk" username="Administrator" password="tsunami" base="OU=LDAPTest1,dc=xcase-auk,dc=com" searchfilter="(objectClass=user)" sortoption="" />
    /// </operation>
    /// The sortoption attribute is optional.
    /// </summary>
    public class ActionLDAPSearchDirectory : ActionAbstract
    {
        #region Logger Setup
        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILog log = LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        #endregion

        /// <summary>
        /// This method searches an LDAP directory. The XML file should be of this form:
        /// <operation id="Search LDAP directory" class="XCaseGeneric.ActionLDAPSearchDirectory">
        ///     <ldap server="auk" username="Administrator" password="tsunami" base="OU=LDAPTest1,dc=xcase-auk,dc=com" searchfilter="(objectClass=user)" sortoption="" />
        /// </operation>
        /// The sortoption attribute is optional.
        /// </summary>
        /// <param name="document">The operation document.</param>
        /// <returns>The result of the operation.</returns>
        public override OperationResult Execute(XmlDocument document)
        {
            log.Debug("starting Execute()");
            try
            {
                OperationResult operationResult = new OperationResult("Success searching LDAP directory");
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
                        string domain = Helper.GetStringNodeValue(ldapNode, "@domain", true);
                        log.Debug("domain is " + domain);
                        ldapDictionary.Add("domain", domain);
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
                    string searchFilter = Helper.GetStringNodeValue(ldapNode, "@searchfilter", false);
                    log.Debug("searchFilter is " + searchFilter);
                    string sortOptionString = Helper.GetStringNodeValue(ldapNode, "@sortoption", false);
                    log.Debug("sortOptionString is " + sortOptionString);
                    string uriPath = "LDAP://" + ldapDatasource.Host + "/" + baseString;
                    log.Debug("uriPath is " + uriPath);
                    System.DirectoryServices.DirectoryEntry directoryEntry = new System.DirectoryServices.DirectoryEntry(uriPath, ldapDatasource.Username, ldapDatasource.Password);
                    log.Debug("created directory entry");
                    System.DirectoryServices.DirectorySearcher directorySearcher = new System.DirectoryServices.DirectorySearcher(directoryEntry);
                    directorySearcher.Filter = searchFilter;
                    log.Debug("set search filter");
                    System.DirectoryServices.SortOption sortOption = new System.DirectoryServices.SortOption(sortOptionString, System.DirectoryServices.SortDirection.Ascending);
                    directorySearcher.Sort = sortOption;
                    log.Debug("set sort option");
                    System.DirectoryServices.SearchResultCollection searchResultCollection = directorySearcher.FindAll();
                    log.Debug("performed search");
                    foreach (System.DirectoryServices.SearchResult searchResult in directorySearcher.FindAll())
                    {
                        log.Debug("next search result");
                        System.DirectoryServices.DirectoryEntry directorEntry = searchResult.GetDirectoryEntry();
                        log.Debug("directory entry name is " + directorEntry.Name);
                    }
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
                string exceptionMessage = "Exception searching LDAP directory " + e.Message;
                log.Warn(exceptionMessage);
                return new OperationResult(false, exceptionMessage);
            }
        }
    }
}
