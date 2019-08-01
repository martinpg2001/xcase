// ----------------------------------------------------------------------------
// <copyright file="ActionLDAPModifyEntry.cs" company="XCase">
//   Copyright XCase
// </copyright>
// <summary>
//   This action class modifies an LDAP entry.
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
    /// This action class modifies an LDAP entry. The XML file should be of this form:
    /// <operation id="Modify an LDAP entry" class="XCaseGeneric.ActionLDAPModifyEntry">
    ///     <ldap base="cn=Users,dc=xcase-auk,dc=com" server="Auk" username="Administrator" password="password" entryname="" >
    ///         <property name="" value="" />
    ///         <property name="" value="" />
    ///     </ldap>
    /// </operation>
    /// The ssl attribute is optional.
    /// </summary>
    public class ActionLDAPModifyEntry : ActionAbstract
    {
        /// <summary>
        /// This method modifies an LDAP entry. The XML file should be of this form:
        /// <operation id="Modify an LDAP entry" class="XCaseGeneric.ActionLDAPModifyEntry">
        ///     <ldap base="cn=Users,dc=xcase-auk,dc=com" server="Auk" username="Administrator" password="password" entryname="cn=martin" >
        ///         <property name="" value="" />
        ///         <property name="" value="" />
        ///     </ldap>
        /// </operation>
        /// The ssl attribute is optional.
        /// </summary>
        /// <param name="document">The operation document.</param>
        /// <returns>The result of the operation.</returns>
        public override OperationResult Execute(XmlDocument document)
        {
            Helper.LogDebug(GetType().Name + ": starting Execute()");
            try
            {
                OperationResult operationResult = new OperationResult("Success modifying LDAP entry");
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
                    string entryName = Helper.GetStringNodeValue(ldapNode, "@entryname", true);
                    Helper.LogDebug(GetType().Name + ": newEntryName is " + entryName);
                    string uriPath = "LDAP://" + ldapDatasource.Host + "/" + baseString;
                    Helper.LogDebug(GetType().Name + ": uriPath is " + uriPath);
                    System.DirectoryServices.DirectoryEntry directoryEntry = new System.DirectoryServices.DirectoryEntry(uriPath, ldapDatasource.Username, ldapDatasource.Password);
                    Helper.LogDebug(GetType().Name + ": got base directory entry");
                    System.DirectoryServices.DirectoryEntries childDirectoryEntries = directoryEntry.Children;
                    Helper.LogDebug(GetType().Name + ": got child directory entries");
                    System.DirectoryServices.DirectoryEntry modifyDirectoryEntry = childDirectoryEntries.Find(entryName);
                    XmlNodeList propertyNodeList = ldapNode.SelectNodes("property");
                    foreach (XmlNode propertyNode in propertyNodeList)
                    {
                        string propertyName = propertyNode.SelectSingleNode("@name").Value;
                        string propertyValue = propertyNode.SelectSingleNode("@value").Value;
                        modifyDirectoryEntry.Properties[propertyName].Value = propertyValue;
                        Helper.LogDebug(GetType().Name + ": updated directory entry property " + propertyName);
                    }

                    modifyDirectoryEntry.CommitChanges();
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
                string exceptionMessage = "Exception modifying LDAP entry " + e.Message;
                Helper.LogWarn(GetType().Name + ": " + exceptionMessage);
                return new OperationResult(false, exceptionMessage);
            }
        }
    }
}
