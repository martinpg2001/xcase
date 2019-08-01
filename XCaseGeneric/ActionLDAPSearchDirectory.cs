// ----------------------------------------------------------------------------
// <copyright file="ActionLDAPSearchDirectory.cs" company="XCase">
//   Copyright XCase
// </copyright>
// <summary>
//   This action class searches an LDAP directory.
// </summary>
// ----------------------------------------------------------------------------
namespace XCaseGeneric
{
    using System;
    using System.Collections.Generic;
    using System.IO;
    using System.Linq;
    using System.Net;
    using System.Text;
    using System.Xml;
    using System.Xml.XPath;
    using TestClientBase;

    /// <summary>
    /// This action class searches an LDAP directory. The XML file should be of this form:
    /// <operation id="Search LDAP directory" class="XCaseGeneric.ActionLDAPSearchDirectory">
    ///     <ldap server="auk" username="Administrator" password="password" base="OU=LDAPTest1,dc=xcase-auk,dc=com" searchfilter="(objectClass=user)" sortoption="" />
    /// </operation>
    /// The sortoption attribute is optional.
    /// </summary>
    public class ActionLDAPSearchDirectory : ActionAbstract
    {
        /// <summary>
        /// This method searches an LDAP directory. The XML file should be of this form:
        /// <operation id="Search LDAP directory" class="XCaseGeneric.ActionLDAPSearchDirectory">
        ///     <ldap server="auk" username="Administrator" password="password" base="OU=LDAPTest1,dc=xcase-auk,dc=com" searchfilter="(objectClass=user)" sortoption="" />
        /// </operation>
        /// The sortoption attribute is optional.
        /// </summary>
        /// <param name="document">The operation document.</param>
        /// <returns>The result of the operation.</returns>
        public override OperationResult Execute(XmlDocument document)
        {
            Helper.LogDebug(GetType().Name + ": starting Execute()");
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
                        string server = Helper.GetStringNodeValue(ldapNode, "@server", true);
                        Helper.LogDebug(GetType().Name + ": server is " + server);
                        ldapDictionary.Add("server", server);
                        string username = Helper.GetStringNodeValue(ldapNode, "@username", false);
                        Helper.LogDebug(GetType().Name + ": username is " + username);
                        ldapDictionary.Add("username", username);
                        string password = Helper.GetStringNodeValue(ldapNode, "@password", false);
                        Helper.LogDebug(GetType().Name + ": password is " + password);
                        ldapDictionary.Add("password", password);
                        ldapDatasource = new LDAP(ldapDictionary);
                    }

                    string baseString = Helper.GetStringNodeValue(ldapNode, "@base", true);
                    Helper.LogDebug(GetType().Name + ": baseString is " + baseString);
                    string searchFilter = Helper.GetStringNodeValue(ldapNode, "@searchfilter", false);
                    Helper.LogDebug(GetType().Name + ": searchFilter is " + searchFilter);
                    string sortOptionString = Helper.GetStringNodeValue(ldapNode, "@sortoption", false);
                    Helper.LogDebug(GetType().Name + ": sortOptionString is " + sortOptionString);
                    string uriPath = "LDAP://" + ldapDatasource.Host + "/" + baseString;
                    Helper.LogDebug(GetType().Name + ": uriPath is " + uriPath);
                    System.DirectoryServices.DirectoryEntry directoryEntry = new System.DirectoryServices.DirectoryEntry(uriPath, ldapDatasource.Username, ldapDatasource.Password);
                    Helper.LogDebug(GetType().Name + ": created directory entry");
                    System.DirectoryServices.DirectorySearcher directorySearcher = new System.DirectoryServices.DirectorySearcher(directoryEntry);
                    directorySearcher.Filter = searchFilter;
                    Helper.LogDebug(GetType().Name + ": set search filter");
                    System.DirectoryServices.SortOption sortOption = new System.DirectoryServices.SortOption(sortOptionString, System.DirectoryServices.SortDirection.Ascending);
                    directorySearcher.Sort = sortOption;
                    Helper.LogDebug(GetType().Name + ": set sort option");
                    System.DirectoryServices.SearchResultCollection searchResultCollection = directorySearcher.FindAll();
                    Helper.LogDebug(GetType().Name + ": performed search");
                    foreach (System.DirectoryServices.SearchResult searchResult in directorySearcher.FindAll())
                    {
                        Helper.LogDebug(GetType().Name + ": next search result");
                        System.DirectoryServices.DirectoryEntry directorEntry = searchResult.GetDirectoryEntry();
                    }
                }
                else
                {
                    string missingLDAPNodeMessage = "Missing ldap node";
                    Helper.LogWarn(GetType().Name + ": " + missingLDAPNodeMessage);
                    return new OperationResult(false, missingLDAPNodeMessage);
                }

                return operationResult;
            }
            catch (Exception e)
            {
                string exceptionMessage = "Exception searching LDAP directory " + e.Message;
                Helper.LogWarn(GetType().Name + ": " + exceptionMessage);
                return new OperationResult(false, exceptionMessage);
            }
        }
    }
}
