namespace XCase.REST.ProxyGenerator
{
    using System;
    using System.Collections.Generic;
    using System.IO;
    using System.Linq;
    using System.Net;
    using System.Net.Http;
    using System.Net.Http.Headers;
    using System.Reflection;
    using System.Threading.Tasks;
    using System.Web;
    using log4net;
    using Newtonsoft.Json;
    using Newtonsoft.Json.Linq;

    public abstract class RESTProxy : IRESTProxy
    {
        #region Logger Setup

        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILog Log = log4net.LogManager.GetLogger(MethodBase.GetCurrentMethod().DeclaringType);

        #endregion

        public Uri _baseUrl;
        public string _password = string.Empty;
        public string _redirectURL = string.Empty;
        public string _tenantId = string.Empty;
        public string _username = "Admin";
        public string token;
        public NetworkCredential ClientCredentials { get; set; }
        public WebProxy Proxy;

        public RESTProxy()
        {
;
        }

        public RESTProxy(Uri baseUrl)
        { 
            _baseUrl = baseUrl;
        }

        public RESTProxy(Uri baseUrl, string username, string password, string tenant)
        {
            _baseUrl = baseUrl;
            _username = username;
            _password = password;
            _tenantId = tenant;
        }

        public abstract HttpClient BuildHttpClient();

        public abstract HttpClient BuildHttpClient(string username, string password, string tenantId);

        public abstract string GetAccessToken(HttpClient client, string userName = "admin", string password = "", string tenantId = null);

        /// <summary>
        /// This method adds a parameter to a URL.
        /// </summary>
        /// <param name="currentUrl"></param>
        /// <param name="paramName"></param>
        /// <param name="value"></param>
        /// <returns></returns>
        public string AppendQuery(string currentUrl, string paramName, string value)
        {
            if (currentUrl.Contains("?"))
            {
                currentUrl += string.Format("&{0}={1}", paramName, Uri.EscapeUriString(value));
            }
            else
            {
                currentUrl += string.Format("?{0}={1}", paramName, Uri.EscapeUriString(value));
            }

            return currentUrl;
        }

        public async Task EnsureSuccessStatusCodeAsync(HttpResponseMessage response)
        {
            Log.DebugFormat("starting EnsureSuccessStatusCodeAsync()");
            if (response.IsSuccessStatusCode)
            {
                Log.DebugFormat("response IsSuccessStatusCode");
                return;
            }

            try
            {
                Log.DebugFormat("trying to read content");
                var content = await response.Content.ReadAsStringAsync().ConfigureAwait(false); ;
                throw new SimpleHttpResponseException(response.StatusCode, content);
            }
            finally
            {
                if (response.Content != null)
                {
                    Log.DebugFormat("about to dispose of content");
                    response.Content.Dispose();
                }
            }
        }

        /// <summary>
        /// This method double-encodes selected special characters.
        /// </summary>
        /// <param name="parameter"></param>
        /// <returns></returns>
        public string EncodeParameter(object parameter)
        {
            if (parameter != null && (parameter is DateTime || parameter is DateTime?))
            {
                return ((DateTime)parameter).ToString("o");
            }

            string parameterString = (parameter ?? string.Empty).ToString();
            string[] specialCharacterArray = new string[] { "/", @"\", "@", "#", "%", "&", "+", "<", ">" };
            List<string> encodedParameterStringList = new List<string>();
            string[] parameterStringArray = parameterString.ToCharArray().Select(c => c.ToString()).ToArray();
            foreach (string parameterCharacter in parameterStringArray)
            {
                //Log.DebugFormat("parameterCharacter is {0}", parameterCharacter);
                if (specialCharacterArray.Contains<string>(parameterCharacter))
                {
                    encodedParameterStringList.Add(HttpUtility.UrlEncode(HttpUtility.UrlEncode(parameterCharacter)));
                }
                else if (parameterCharacter.Equals("*"))
                {
                    encodedParameterStringList.Add("%252a");
                }
                else
                {
                    encodedParameterStringList.Add(parameterCharacter);
                }
            }

            string encodedParameterString = string.Join("", encodedParameterStringList);
            Log.DebugFormat("encodedParameterString is {0}", encodedParameterString);
            return encodedParameterString;
        }

        public bool ContainsSpecialCharacters(string source)
        {
            if (source != null && (source.Contains("/") || source.Contains("\\") || source.Contains("@") || source.Contains("#") || source.Contains("%") || source.Contains("&") || source.Contains("<") || source.Contains(">")))
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        public bool ContainsExtraSpecialCharacters(string source)
        {
            if (source != null && (source.Contains("/") || source.Contains("\\") || source.Contains("@")))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    }

    public class SimpleHttpResponseException : Exception
    {
        public HttpStatusCode StatusCode { get; private set; }

        public SimpleHttpResponseException(HttpStatusCode statusCode, string content)
            : base(content)
        {
            StatusCode = statusCode;
        }
    }
}
