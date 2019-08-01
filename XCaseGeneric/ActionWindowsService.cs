namespace XCaseGeneric
{
    using System;
    using System.Linq;
    using System.Management;
    using System.Reflection;
    using System.Threading;
    using System.Xml;
    using XCaseBase;
    using log4net;

    /// <summary>
    /// This action is used for working with Windows services.
    /// </summary>
    public abstract class ActionWindowsService : ActionAbstract
    {
        #region Constants and Fields

        /// <summary>
        /// A log4net log instance.
        /// </summary>
        protected static readonly ILog Log = LogManager.GetLogger(MethodBase.GetCurrentMethod().DeclaringType);

        /// <summary>
        /// This is the domain string.
        /// </summary>
        protected string Domain = "xcase-auk";

        /// <summary>
        /// This is the password string.
        /// </summary>
        protected string Password = "password";

        /// <summary>
        /// This is the appPoolsStringArray string array.
        /// </summary>
        protected string[] ServicesStringArray;

        /// <summary>
        /// This is the username string.
        /// </summary>
        protected string Username = "Administrator";

        /// <summary>
        /// This is the windowsServer string.
        /// </summary>
        protected string WindowsServer = "Auk";

        #endregion

        #region Methods

        protected ManagementObjectCollection GetServices(XmlNode operationNode, out ManagementScope managementScope)
        {
            XmlNode windowsConnectionstringNode = operationNode.SelectSingleNode("windowsconnectionstring");
            if (windowsConnectionstringNode != null)
            {
                string windowsConnectionString = Helper.GetStringNodeValue(operationNode, "windowsconnectionstring", string.Empty, false);
                Log.DebugFormat("windowsConnectionString is {0}", windowsConnectionString);
                this.ParseConfigurationString(windowsConnectionString);
            }
            else
            {
                Log.Warn("windowsConnectionstringNode is null");
            }

            XmlNode servicesNode = operationNode.SelectSingleNode("services");
            if (servicesNode != null)
            {
                Log.Debug("servicesNode is not null");
                string servicesString = Helper.GetStringNodeValue(operationNode, "services", string.Empty, false);
                Log.DebugFormat("servicesString is {0}", servicesString);
                this.ServicesStringArray = this.ParseServicesString(servicesString);
            }
            else
            {
                Log.Warn("servicesNode is null");
            }

            /* Now recycle the services*/
            string userId = this.Domain + "\\" + this.Username;
            Log.Debug("userId is " + userId);
            ConnectionOptions connectionOptions = new ConnectionOptions();
            if (!Helper.IsServerLocal(this.WindowsServer))
            {
                Log.Debug("server is not local");
                connectionOptions.Authority = "ntlmdomain:" + this.Domain;
                connectionOptions.Username = this.Username;
                connectionOptions.Password = this.Password;
            }

            connectionOptions.EnablePrivileges = true;
            connectionOptions.Authentication = AuthenticationLevel.PacketPrivacy;
            Log.Debug("created connection options");
            managementScope = new ManagementScope(@"\\" + this.WindowsServer + @"\root\cimv2", connectionOptions);
            managementScope.Connect();
            Log.Debug("connected to " + this.WindowsServer);
            ObjectQuery getService = new ObjectQuery("Select * from Win32_Service");
            ManagementObjectSearcher search = new ManagementObjectSearcher(managementScope, getService);
            ManagementObjectCollection services = search.Get();
            return services;
        }

        /// <summary>
        /// This methods parses the configuration string.
        /// </summary>
        /// <param name="configString">The configuration string.</param>
        protected void ParseConfigurationString(string configString)
        {
            Log.Debug("starting ParseConfigurationString()");
            string[] configArray = configString.Split(';');
            foreach (string parameter in configArray)
            {
                string[] nameValue = parameter.Split('=');
                switch (nameValue[0])
                {
                    case "server":
                        Log.Debug("server is " + nameValue[1]);
                        this.WindowsServer = nameValue[1];
                        break;
                    case "domain":
                        Log.Debug("domain is " + nameValue[1]);
                        this.Domain = nameValue[1];
                        break;
                    case "username":
                        Log.Debug("username is " + nameValue[1]);
                        this.Username = nameValue[1];
                        break;
                    case "password":
                        Log.Debug("password is " + nameValue[1]);
                        this.Password = nameValue[1];
                        break;
                    default:
                        Log.Info("parameter name is unrecognized " + nameValue[0]);
                        break;
                }
            }
        }

        /// <summary>
        /// This method parses the servicesString into an array.
        /// </summary>
        /// <param name="servicesString">The servicesString parameter: a comma-delimited string of service names.</param>
        /// <returns>The array of service names.</returns>
        protected string[] ParseServicesString(string servicesString)
        {
            Log.Debug("starting ParseServicesString()");
            if (servicesString != null)
            {
                return servicesString.Split(',');
            }

            return null;
        }

        /// <summary>
        /// This method starts a service identified by the managementObject.
        /// </summary>
        /// <param name="managementScope"></param>
        /// <param name="managementObject"></param>
        /// <param name="timeout"></param>
        /// <param name="wait"></param>
        protected void StartService(ManagementScope managementScope, ManagementObject managementObject, int timeout, int wait)
        {
            managementObject.InvokeMethod("StartService", null);
            string name = (string)managementObject["Name"];
            string query = string.Format("Select State from Win32_Service Where Name = '{0}'", name);
            for (int i = 0; i < timeout; i++)
            {
                ObjectQuery getService = new ObjectQuery(query);
                ManagementObjectSearcher search = new ManagementObjectSearcher(managementScope, getService);
                ManagementObjectCollection services = search.Get();
                foreach (string state in from ManagementObject m in services select (string)m["State"])
                {
                    if (state.Equals("running", StringComparison.InvariantCultureIgnoreCase))
                    {
                        Log.Debug("service is started successfully");
                        Thread.Sleep(wait * 1000);
                        return;
                    }

                    Thread.Sleep(1000);
                }
            }

            throw new TimeoutException(string.Format("Service could not start for {0} seconds", timeout));
        }

        /// <summary>
        /// Starts the service.
        /// </summary>
        /// <param name="managementScope">The management scope.</param>
        /// <param name="managementObject">The management object.</param>
        /// <exception cref="System.TimeoutException">Service could not start for 15 seconds.</exception>
        protected void StartService(ManagementScope managementScope, ManagementObject managementObject)
        {
            this.StartService(managementScope, managementObject, 15, 15);
        }

        /// <summary>
        /// Stops the service.
        /// </summary>
        /// <param name="managementScope">The management scope.</param>
        /// <param name="managementObject">The management object.</param>
        /// <param name="timeout"></param>
        /// <param name="wait"></param>
        /// <exception cref="System.TimeoutException">Service could not stop for 15 seconds.</exception>
        protected void StopService(ManagementScope managementScope, ManagementObject managementObject, int timeout, int wait)
        {
            managementObject.InvokeMethod("StopService", null);
            string name = (string)managementObject["Name"];
            string query = string.Format("Select State from Win32_Service Where Name = '{0}'", name);
            for (int i = 0; i < timeout; i++)
            {
                ObjectQuery getService = new ObjectQuery(query);
                ManagementObjectSearcher search = new ManagementObjectSearcher(managementScope, getService);
                ManagementObjectCollection services = search.Get();
                foreach (string state in from ManagementObject m in services select (string)m["State"])
                {
                    if (state.Equals("stopped", StringComparison.InvariantCultureIgnoreCase))
                    {
                        Log.Debug("service is stopped successfully");
                        Thread.Sleep(wait * 1000);
                        return;
                    }

                    Thread.Sleep(1000);
                }
            }

            throw new TimeoutException(string.Format("Service could not stop for {0} seconds", timeout));
        }

        /// <summary>
        /// Stops the service.
        /// </summary>
        /// <param name="managementScope">The management scope.</param>
        /// <param name="managementObject">The management object.</param>
        /// <exception cref="System.TimeoutException">Service could not stop for 15 seconds.</exception>
        protected void StopService(ManagementScope managementScope, ManagementObject managementObject)
        {
            this.StopService(managementScope, managementObject, 15, 15);
        }

        #endregion
    }
}