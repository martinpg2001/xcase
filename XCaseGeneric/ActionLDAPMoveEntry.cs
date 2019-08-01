// ----------------------------------------------------------------------------
// <copyright file="ActionLDAPMoveEntry.cs" company="XCase">
//   Copyright XCase
// </copyright>
// <summary>
//   This action class moves an LDAP entry.
// </summary>
// ----------------------------------------------------------------------------
namespace TestClientBase
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
    /// This action class moves an LDAP entry. The XML file should be of this form:
    /// <operation id="Move an LDAP entry" class="XCaseGeneric.ActionLDAPMoveEntry">
    ///     <ldap base="cn=Users,dc=xcase-auk,dc=com" server="Auk" username="Administrator" password="password" entryname="cn=martin" newbase="" />
    /// </operation>
    /// The base attribute is required.
    /// </summary>
    public class ActionLDAPMoveEntry : ActionAbstract
    {
        /// <summary>
        /// This method moves an LDAP entry. The XML file should be of this form:
        /// <operation id="Move an LDAP entry" class="XCaseGeneric.ActionLDAPMoveEntry">
        ///     <ldap base="cn=Users,dc=xcase-auk,dc=com" server="Auk" username="Administrator" password="password" entryname="cn=martin" newbase="" />
        /// </operation>
        /// The base attribute is required.
        /// </summary>
        /// <param name="document">The operation document.</param>
        /// <returns>The result of the operation.</returns>
        public override OperationResult Execute(XmlDocument document)
        {
            Helper.LogDebug(GetType().Name + ": starting Execute()");
            try
            {
                OperationResult operationResult = new OperationResult("Success moving LDAP entry");
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
                    string newBaseString = Helper.GetStringNodeValue(ldapNode, "@newbase", true);
                    Helper.LogDebug(GetType().Name + ": newBaseString is " + newBaseString);
                    string entryName = Helper.GetStringNodeValue(ldapNode, "@entryname", true);
                    Helper.LogDebug(GetType().Name + ": entryName is " + entryName);
                    string uriPath = "LDAP://" + ldapDatasource.Host + "/" + baseString;
                    Helper.LogDebug(GetType().Name + ": uriPath is " + uriPath);
                    string newUriPath = "LDAP://" + ldapDatasource.Host + "/" + newBaseString;
                    Helper.LogDebug(GetType().Name + ": newUriPath is " + newUriPath);
                    System.DirectoryServices.DirectoryEntry directoryEntry = new System.DirectoryServices.DirectoryEntry(uriPath, ldapDatasource.Username, ldapDatasource.Password);
                    System.DirectoryServices.DirectoryEntry newParentDirectoryEntry = new System.DirectoryServices.DirectoryEntry(newUriPath, ldapDatasource.Username, ldapDatasource.Password);
                    Helper.LogDebug(GetType().Name + ": got base directory entry");
                    System.DirectoryServices.DirectoryEntries childDirectoryEntries = directoryEntry.Children;
                    Helper.LogDebug(GetType().Name + ": got child directory entries");
                    System.DirectoryServices.DirectoryEntry moveDirectoryEntry = childDirectoryEntries.Find(entryName);
                    moveDirectoryEntry.MoveTo(newParentDirectoryEntry);
                    moveDirectoryEntry.CommitChanges();
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
                string exceptionMessage = "Exception moving LDAP entry " + e.Message;
                Helper.LogWarn(GetType().Name + ": " + exceptionMessage);
                return new OperationResult(false, exceptionMessage);
            }
        }
    }
}
