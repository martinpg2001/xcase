namespace XCaseGeneric
{
    using System;
    using System.Linq;
    using System.Management;
    using System.Xml;
    using XCaseBase;

    /// <summary>
    /// This action class restarts Windows services.
    /// </summary>
    public class ActionRestartWindowsService : ActionWindowsService
    {
        /// <summary>
        /// This method restarts Windows services specified by the services
        /// element. The XML document should be of this form:
        /// <operation class="XCaseGeneric.ActionRestartWindowsService">
        ///     <windowsconnectionstring>server=krish.internal.xcase.net;username=Administrator;password=tsunami;domain=IAINTERNAL</windowsconnectionstring>
        ///     <services>WBExtensionService,WBSchedulerService</services>
        /// </operation>
        /// You must be able to access the Windows server to use this class.
        /// </summary>
        /// <param name="document">The operation document.</param>
        /// <returns>The result of the operation.</returns>
        public override OperationResult Execute(XmlDocument document)
        {
            ////log.Debug("starting Execute()");
            try
            {
                XmlNode operationNode = document.SelectSingleNode("operation");
                int timeout = Helper.GetIntNodeValue(operationNode, "services/@timeout", 15, false);
                int wait = Helper.GetIntNodeValue(operationNode, "services/@wait", 15, false);
                ManagementScope managementScope;
                ManagementObjectCollection services = this.GetServices(operationNode, out managementScope);
                foreach (ManagementObject managementObject in services)
                {
                    string name = (string)managementObject["Name"];
                    if (this.ServicesStringArray.Contains(name))
                    {
                        Log.DebugFormat("found service is: {0}", name);
                        string state = (string)managementObject["State"];
                        Log.DebugFormat("initial service state is: {0}", state);
                        if (state.Equals("stopped", StringComparison.InvariantCultureIgnoreCase))
                        {
                            Log.Debug("service is already stopped so just start it");
                            this.StartService(managementScope, managementObject, timeout, wait);
                            Log.Debug("started service");
                        }
                        else
                        {
                            this.StopService(managementScope, managementObject, timeout, wait);
                            Log.Debug("stopped service");
                            this.StartService(managementScope, managementObject, timeout, wait);
                            Log.Debug("started service");
                        }
                    }
                }

                Log.Debug("restarted services");
                return new OperationResult("Success restarting services");
            }
            catch (Exception e)
            {
                string exceptionMessage = "Exception restarting Windows services: " + e.Message;
                Log.Warn(exceptionMessage);
                return new OperationResult(false, exceptionMessage);
            }
        }
    }
}
