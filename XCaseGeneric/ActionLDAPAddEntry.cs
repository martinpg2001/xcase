// ----------------------------------------------------------------------------
// <copyright file="ActionLDAPAddEntry.cs" company="XCase">
//   Copyright XCase
// </copyright>
// <summary>
//   This action class adds an LDAP entry.
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
            Helper.LogDebug(GetType().Name + ": starting Execute()");
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
                    string newEntryName = Helper.GetStringNodeValue(ldapNode, "@newentryname", true);
                    Helper.LogDebug(GetType().Name + ": newEntryName is " + newEntryName);
                    string newEntrySchemaClassName = Helper.GetStringNodeValue(ldapNode, "@newentryschemaclassname", true);
                    Helper.LogDebug(GetType().Name + ": newEntrySchemaClassName is " + newEntrySchemaClassName);
                    string uriPath = "LDAP://" + ldapDatasource.Host + "/" + baseString;
                    Helper.LogDebug(GetType().Name + ": uriPath is " + uriPath);
                    System.DirectoryServices.DirectoryEntry directoryEntry = new System.DirectoryServices.DirectoryEntry(uriPath, ldapDatasource.Username, ldapDatasource.Password);
                    Helper.LogDebug(GetType().Name + ": created base directory entry");
                    System.DirectoryServices.DirectoryEntry newDirectoryEntry = directoryEntry.Children.Add(newEntryName, newEntrySchemaClassName);
                    Helper.LogDebug(GetType().Name + ": created new directory entry for " + newEntryName);
                    XmlNodeList propertyNodeList = ldapNode.SelectNodes("property");
                    foreach (XmlNode propertyNode in propertyNodeList)
                    {
                        string propertyName = propertyNode.SelectSingleNode("@name").Value;
                        string propertyValue = propertyNode.SelectSingleNode("@value").Value;
                        newDirectoryEntry.Properties[propertyName].Add(propertyValue);
                        Helper.LogDebug(GetType().Name + ": added new directory entry property " + propertyName);
                    }

                    newDirectoryEntry.CommitChanges();
                    Helper.LogDebug(GetType().Name + ": added new directory entry");
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
                string exceptionMessage = "Exception adding LDAP entry " + e.Message;
                Helper.LogWarn(GetType().Name + ": " + exceptionMessage);
                return new OperationResult(false, exceptionMessage);
            }
        }
    }
}
