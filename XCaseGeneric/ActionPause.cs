namespace XCaseGeneric
{
    using System;
    using System.Xml;
    using XCaseBase;
    using log4net;

    /// <summary>
    /// This action class is used to pause the test run.
    /// </summary>
    public class ActionPause : ActionAbstract
    {
        #region Logger Setup
        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILog log = LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        #endregion

        /// <summary>
        /// Initializes a new instance of the ActionPause class.
        /// </summary>
        public ActionPause()
        {
        }

        /// <summary>
        /// This method pauses either for a specified number of seconds or until the user responds. The XmlDocument must be of this form:
        /// <operation id="Pause id" class="XCaseGeneric.ActionPause">
        ///   <pause time="-1" />
        /// </operation>
        /// The special value -1 waits for user input.
        /// </summary>
        /// <param name="document">The operation document.</param>
        /// <returns>The result of the operation.</returns>
        public override OperationResult Execute(XmlDocument document)
        {
            ////log.Debug("starting Execute()");
            try
            {
                bool pauseSilent = true;
                if (document.SelectSingleNode("operation/@silent") != null)
                {
                    string silentString = Helper.GetStringNodeValue(document, "operation/@silent", "true", false);
                    bool.TryParse(silentString, out pauseSilent);
                }

                int pauseTime = -1;
                XmlNode pauseTimeNode = document.SelectSingleNode("operation/pause/@time");
                if (pauseTimeNode != null)
                {
                    log.Debug("pauseTimeNode not null");
                    //string pauseTimeString = Helper.GetStringNodeValue(document, "operation/pause/@time", string.Empty, false);
                    pauseTime = Helper.GetIntNodeValue(document, "operation/pause/@time", -1, false);
                    if (pauseTime > 0)
                    {
                        log.Info("pausing time is " + pauseTime);
                        System.Threading.Thread.Sleep(1000 * pauseTime);
                    }
                    else
                    {
                        /* Do not prompt if running in silent mode */
                        if (!pauseSilent)
                        {
                            log.Info("pausing for prompt...");
                            System.Console.Read();
                        }
                        else
                        {
                            log.Debug("running in silent mode");
                        }
                    }
                }
                else
                {
                    /* Do not prompt if running in silent mode */
                    if (!pauseSilent)
                    {
                        System.Console.Read();
                    }
                    else
                    {
                        log.Debug("running in silent mode");
                    }
                }
            }
            catch (System.IO.IOException sie)
            {
                string ioexceptionMessage = "Thrown if running client without console window available " + sie.Message;
                log.Info(ioexceptionMessage);
            }
            catch (Exception e)
            {
                string exceptionMessage = "Failed to parse pause node " + e.Message;
                log.Info(exceptionMessage);
                return new OperationResult(false, exceptionMessage);
            }

            string successMessage = "Success completing pause";
            return new OperationResult(successMessage);
        }
    }
}
