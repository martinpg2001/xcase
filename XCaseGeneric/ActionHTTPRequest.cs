namespace XCaseGeneric
{
    using System;
    using System.Collections.Generic;
    using System.IO;
    using System.Net;
    using System.Text;
    using System.Threading;
    using System.Xml;
    using XCaseBase;
    using log4net;

    /// <summary>
    /// This action class is used to send HTTP requests to an HTTP server.
    /// </summary>
    public class ActionHTTPRequest : ActionAbstract
    {
        #region Logger Setup
        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILog log = LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        #endregion

        /// <summary>
        /// This action class posts an HTTP request to a Web server. The XML file should be of this
        /// form:
        /// <operation id="Post HTTP request to web server" class="XCaseGeneric.ActionHTTPRequest">
        ///     <credentials domain="DUMMY" password="password" username="Administrator" />
        ///     <url datasourcename="datasourceName" contenttype="text/html" host="server" method="POST" path="/walls452" querystring="id=45" saveresponse="true" scheme="http" />
        ///     <xml><example>Example xml element if using XML post capability</example></xml>
        ///     <expected>
        ///         <response code="OK" />
        ///     </expected>
        /// </operation>
        /// You can use a datasource attribute to specify basic HTTP datasource information.
        /// You can also use this class to send XML documents via HTTP.
        /// </summary>
        /// <param name="document">The operation document.</param>
        /// <returns>The result of the operation.</returns>
        public override OperationResult Execute(XmlDocument document)
        {
            try
            {
                bool saveResponse = false;
                string contentType = "text/html";
                string host = "server";
                string method = "POST";
                string path = string.Empty;
                string querystring = string.Empty;
                string scheme = "http";
                string xmlString = string.Empty;
                string successMessage = "Success posting HTTP request";
                XmlNode operationNode = document.SelectSingleNode("operation");
                HTTP httpDatasource = null;
                XmlNode urlNode = null;
                if (operationNode.SelectSingleNode("url") != null)
                {
                    urlNode = operationNode.SelectSingleNode("url");
                    if (urlNode.SelectSingleNode("@datasource") != null)
                    {
                        string name = Helper.GetStringNodeValue(urlNode, "@datasource", true);
                        httpDatasource = (HTTP)Helper.GetHelperDictionary()[name];
                        if (httpDatasource != null)
                        {
                            log.Debug("HTTP datasource is not null");
                        }
                        else
                        {
                            string missingHTTPDatasourceMessage = string.Format("Missing HTTP datasource for name {0}", name);
                            log.Warn(missingHTTPDatasourceMessage);
                        }
                    }
                    else
                    {
                        Dictionary<string, string> httpDictionary = new Dictionary<string, string>();
                        host = Helper.GetStringNodeValue(urlNode, "@host", false);
                        httpDictionary.Add("host", host);
                        httpDatasource = new HTTP(httpDictionary);
                    }

                    contentType = Helper.GetStringNodeValue(urlNode, "@contenttype", "text/html", false);
                    method = Helper.GetStringNodeValue(urlNode, "@method", "POST", false);
                    path = Helper.GetStringNodeValue(urlNode, "@path", false);
                    querystring = Helper.GetStringNodeValue(urlNode, "@querystring", false);
                    saveResponse = Helper.GetBooleanNodeValue(urlNode, "@saveresponse", false, false);
                    scheme = Helper.GetStringNodeValue(urlNode, "@scheme", "http", false);
                }
                else
                {
                    string missingURLMessage = "Missing URL element";
                    log.Warn(missingURLMessage);
                    return new OperationResult(false, missingURLMessage);
                }

                XmlNode xmlNode = operationNode.SelectSingleNode("xml");
                if (xmlNode != null)
                {
                    xmlString = xmlNode.InnerXml;
                    log.DebugFormat("xmlString is {0}", xmlString);
                }

                string uriString = httpDatasource.Host;
                if (!uriString.Contains("://"))
                {
                    uriString = string.Format("{0}://{1}", scheme, uriString);
                }

                if (!string.IsNullOrEmpty(path))
                {
                    uriString = string.Format("{0}/{1}", uriString , path);
                }

                if (!string.IsNullOrEmpty(querystring))
                {
                    uriString = string.Format("{0}?{1}", uriString, querystring);
                }

                log.DebugFormat("uriString is {0}", uriString);
                Uri requestUri = new Uri(uriString);
                WebRequest webRequest = HttpWebRequest.Create(requestUri);
                webRequest.Method = method;
                log.DebugFormat("set request method to {0}", method);
                webRequest.ContentType = contentType;
                log.DebugFormat("about to retrieve credentials");
                XmlNode credentialsNode = operationNode.SelectSingleNode("credentials");
                if (credentialsNode == null)
                {
                    log.DebugFormat("no credentials provided");
                }
                else
                {
                    log.DebugFormat("credentials provided");
                    string username = Helper.GetStringNodeValue(credentialsNode, "@username", false);
                    log.DebugFormat("username is {0}", username);
                    httpDatasource.Username = username;
                    string password = Helper.GetStringNodeValue(credentialsNode, "@password", false);
                    log.DebugFormat("password is {0}", password);
                    httpDatasource.Password = password;
                    string domain = Helper.GetStringNodeValue(credentialsNode, "@domain", false);
                    log.DebugFormat("domain is {0}", domain);
                    httpDatasource.Domain = domain;
                }

                CredentialCache credentialsCache = new CredentialCache();
                NetworkCredential credentials = new NetworkCredential(httpDatasource.Username, httpDatasource.Password, httpDatasource.Domain);
                credentialsCache.Add(requestUri, "NTLM", credentials);
                credentialsCache.Add(requestUri, "Kerberos", credentials);
                webRequest.Credentials = credentialsCache;
                log.DebugFormat("credentials set");
                ServicePointManager.ServerCertificateValidationCallback = new System.Net.Security.RemoteCertificateValidationCallback(this.AcceptAllCertifications);
                HttpWebResponse webResponse;
                HttpStatusCode expectedResponseStatusCode = HttpStatusCode.OK;
                /* Check if no content type is in the header. If so, then set fake content type. */
                if (contentType == string.Empty)
                {
                    contentType = "text/html";
                }

                int timeout = Helper.GetIntNodeValue(urlNode, "@timeout", -1, false);
                if (timeout > -1)
                {
                    webRequest.Timeout = timeout * 1000;
                }

                /* For the moment, if threadcount is specified, then execute the requests, and then return */
                int threadCount = Helper.GetIntNodeValue(urlNode, "@threadcount", -1, false);
                if (threadCount > 0)
                {
                    log.DebugFormat("threadCount is {0}", threadCount);
                    if (contentType.Equals("text/xml"))
                    {
                        log.Info("content type is XML");
                        StreamWriter writer = new StreamWriter(webRequest.GetRequestStream());
                        log.DebugFormat("xmlString is {0}", xmlString);
                        writer.WriteLine(xmlString);
                        log.Debug("wrote line to stream");
                        writer.Close();
                    }

                    for (int i = 0; i < threadCount; i++)
                    {
                        RequestThread requestThread = new RequestThread(webRequest);
                        Thread threadCreateRequest = new Thread(new ThreadStart(requestThread.CreateRequest));
                        threadCreateRequest.Start();
                        log.Debug("started thread");
                    }

                    return new OperationResult("Success executing threaded requests");
                }
                        
                if (contentType.Equals("text/html"))
                {
                    log.DebugFormat("about to set content length");
                    webRequest.ContentLength = 0;
                    try
                    {
                        using (webResponse = (HttpWebResponse)webRequest.GetResponse())
                        {
                            log.DebugFormat("successful status code is {0}", webResponse.StatusCode);
                            webResponse.Close();
                            XmlNode expectedNode = operationNode.SelectSingleNode("expected");
                            if (expectedNode != null)
                            {
                                log.Debug("expected node is not null");
                                if (expectedNode.SelectSingleNode("response/@code") != null)
                                {
                                    string expectedResponseCode = Helper.GetStringNodeValue(expectedNode, "response/@code", string.Empty, false);
                                    log.DebugFormat("expected response code is {0}", expectedResponseCode);
                                    expectedResponseStatusCode = (HttpStatusCode)Enum.Parse(typeof(HttpStatusCode), expectedResponseCode, true);
                                    log.DebugFormat("expected response status code is {0}", expectedResponseStatusCode);
                                }
                                else
                                {
                                    log.Debug("expected response code node is null");
                                }
                            }

                            if (webResponse.StatusCode == expectedResponseStatusCode)
                            {
                                log.DebugFormat("actual status code {0} matches expected {1}", webResponse.StatusCode, expectedResponseStatusCode);
                                return new OperationResult(successMessage);
                            }
                            else
                            {
                                log.WarnFormat("actual status code {0} does not match expected {1}", webResponse.StatusCode, expectedResponseStatusCode);
                                return new OperationResult(false, string.Format("Actual response code is unexpected {0}", webResponse.StatusCode));
                            }
                        }
                    }
                    catch (WebException we)
                    {
                        log.WarnFormat("catching web exception {0}", we.Message);
                        if (we.Response != null)
                        {
                            using (WebResponse webExceptionResponse = we.Response)
                            {
                                HttpWebResponse httpWebResponse = (HttpWebResponse)webExceptionResponse;
                                log.DebugFormat("status code is {0}", httpWebResponse.StatusCode);
                                webExceptionResponse.Close();
                                XmlNode expectedNode = operationNode.SelectSingleNode("expected");
                                if (expectedNode != null)
                                {
                                    log.Debug("expected node is not null");
                                    if (expectedNode.SelectSingleNode("response/@code") != null)
                                    {
                                        string expectedResponseCode = Helper.GetStringNodeValue(expectedNode, "response/@code", string.Empty, false);
                                        log.DebugFormat("expected response code is {0}", expectedResponseCode);
                                        expectedResponseStatusCode = (HttpStatusCode)Enum.Parse(typeof(HttpStatusCode), expectedResponseCode, true);
                                        log.DebugFormat("expected response status code is {0}", expectedResponseStatusCode);
                                    }
                                    else
                                    {
                                        log.Debug("expected response code node is null");
                                    }
                                }

                                if (httpWebResponse.StatusCode == expectedResponseStatusCode)
                                {
                                    string successCorrectResponseCodeMessage = string.Format("Actual status code {0} matches expected {1}", httpWebResponse.StatusCode, expectedResponseStatusCode);
                                    log.DebugFormat("{0}", successCorrectResponseCodeMessage);
                                    return new OperationResult(successCorrectResponseCodeMessage);
                                }
                                else
                                {
                                    string errorIncorrectResponseCodeMessage = string.Format("Actual status code {0} does not match expected {1}", httpWebResponse.StatusCode, expectedResponseStatusCode);
                                    log.Warn(errorIncorrectResponseCodeMessage);
                                    return new OperationResult(false, errorIncorrectResponseCodeMessage);
                                }
                            }
                        }
                        else
                        {
                            string nullWebResponseMessage = "Web response is null: probably a timeout";
                            log.Warn(nullWebResponseMessage);
                            bool expectedNullResponse = Helper.GetBooleanNodeValueDefaultFalse(urlNode, "@expectednullresponse", false);
                            if (expectedNullResponse)
                            {
                                return new OperationResult("Expected null Web response");
                            }

                            return new OperationResult(false, nullWebResponseMessage);
                        }
                    }
                }
                else
                {
                    if (contentType.Equals("text/xml"))
                    {
                        log.Info("content type is XML");
                        StreamWriter writer = new StreamWriter(webRequest.GetRequestStream());
                        log.DebugFormat("xmlString is {0}", xmlString);
                        writer.WriteLine(xmlString);
                        log.Debug("wrote line to stream");
                        writer.Close();
                        webResponse = (HttpWebResponse)webRequest.GetResponse();
                        WebHeaderCollection webHeaderCollection = webResponse.Headers;
                        foreach (string key in webHeaderCollection.Keys)
                        {
                            log.DebugFormat("key is {0}", key);
                            object value = webHeaderCollection[key];
                            log.DebugFormat("value is {0}", value.ToString());
                        }

                        Stream responseStream = webResponse.GetResponseStream();
                        Encoding encode = System.Text.Encoding.GetEncoding("utf-8");
                        StreamReader readStream = new StreamReader(responseStream, encode);
                        log.Debug("response stream received");
                        if (saveResponse)
                        {
                            string responseFile = "Response.xml";
                            StreamWriter fileWriter = new StreamWriter(responseFile);
                            fileWriter.Write(readStream.ReadToEnd());
                            readStream.Close();
                            fileWriter.Flush();
                            fileWriter.Close();
                        }
                        else
                        {
                            char[] read = new char[1024];
                            int cursor = readStream.Read(read, 0, 1024);
                            while (cursor > 0)
                            {
                                string str = new string(read, 0, cursor);
                                log.DebugFormat("{0}", str);
                                cursor = readStream.Read(read, 0, 1024);
                            }

                            readStream.Close();
                        }

                        log.Debug("closed read stream");
                        responseStream.Close();
                        log.DebugFormat("status code is {0}", webResponse.StatusCode);
                        webResponse.Close();
                        if (webResponse.StatusCode == HttpStatusCode.OK)
                        {
                            return new OperationResult(successMessage);
                        }
                        else
                        {
                            return new OperationResult(false, string.Format("Response code is {0}", webResponse.StatusCode));
                        }
                    }

                    string unrecognizedContentTypeMessage = string.Format("Unrecognized content type {0}", contentType);
                    log.Warn(unrecognizedContentTypeMessage);
                    return new OperationResult(false, unrecognizedContentTypeMessage);
                }
            }
            catch (Exception e)
            {
                string exceptionMessage = string.Format("Exception posting HTTP request: {0}", e.Message);
                log.Warn(exceptionMessage);
                return new OperationResult(false, exceptionMessage);
            }
        }

        /// <summary>
        /// This method just accepts all SSL certificates.
        /// </summary>
        /// <param name="sender">The sender parameter.</param>
        /// <param name="certification">The certification parameter.</param>
        /// <param name="chain">The chain parameter.</param>
        /// <param name="sslPolicyErrors">The sslPolicyErrors parameter.</param>
        /// <returns>Returns true.</returns>
        public bool AcceptAllCertifications(object sender, System.Security.Cryptography.X509Certificates.X509Certificate certification, System.Security.Cryptography.X509Certificates.X509Chain chain, System.Net.Security.SslPolicyErrors sslPolicyErrors)
        {
            return true;
        }

        public class RequestThread
        {
            private WebRequest webRequest;
            public RequestThread(WebRequest webRequest)
            {
                this.webRequest = webRequest;
            }

            public void CreateRequest()
            {
                HttpWebResponse webResponse = (HttpWebResponse)this.webRequest.GetResponse();
                log.Debug("created request");
            }
        }
    }
}
