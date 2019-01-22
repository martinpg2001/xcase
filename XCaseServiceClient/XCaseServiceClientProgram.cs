namespace XCaseServiceClient
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Reflection;
    using System.Windows.Forms;
    using log4net;

    static class XCaseServiceClientClientProgram
    {
        #region Logger Setup

        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILog Log = log4net.LogManager.GetLogger(MethodBase.GetCurrentMethod().DeclaringType);

        #endregion

        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main(string[] args)
        {
            Log.DebugFormat("starting Main()");
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            if (args.Length > 0)
            {
                Log.DebugFormat("fileName is {0}", args[0]);
                Application.Run(new XCaseServiceClientForm(args[0]));
            }
            else
            {
                Log.DebugFormat("no arguments");
                Application.Run(new XCaseServiceClientForm());
            }
        }
    }
}
