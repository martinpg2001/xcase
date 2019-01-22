namespace XCaseGeneric
{
    using System;
    using System.IO;
    using XCaseBase;
    using log4net;

    /// <summary>
    /// This class writes messages to an XML file.
    /// </summary>
    public class XMLMessenger : IMessenger
    {
        #region Logger Setup
        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILog log = LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        #endregion

        /// <summary>
        /// The name of the output file.
        /// </summary>
        private string outputFileString = "Output.xml";

        /// <summary>
        /// The name of the output stylesheet.
        /// </summary>
        private string outputStylesheetString = "Output.xsl";

        /// <summary>
        /// The name of the output directory.
        /// </summary>
        private string outputDirectoryString = Environment.GetEnvironmentVariable("TEMP");

        /// <summary>
        /// The output StreamWriter.
        /// </summary>
        private StreamWriter outputTextWriter;

        /// <summary>
        /// This method closes the output file.
        /// </summary>
        /// <returns>Returns true.</returns>
        public bool Close()
        {
            ////this.outputTextWriter.WriteLine("</operations>");
            this.outputTextWriter.Flush();
            this.outputTextWriter.Close();
            return true;
        }

        /// <summary>
        /// This method initializes the output file.
        /// </summary>
        /// <param name="s">Configuration information for the output file.</param>
        /// <returns>Returns true.</returns>
        public bool Init(string s)
        {
            log.Debug("starting Init()");
            this.PopulateMessenger(s);
            log.Debug("populated messenger");
            /* Create output directory if it does not exist */
            if (!Directory.Exists(this.outputDirectoryString))
            {
                log.Debug("output directory does not exist");
                Directory.CreateDirectory(this.outputDirectoryString); 
            }

            /* Write XSL file to same directory */
            if (!File.Exists(this.outputDirectoryString + Path.DirectorySeparatorChar + this.outputStylesheetString))
            {
                log.Debug("output stylesheet does not exist");
                this.outputTextWriter = new StreamWriter(this.outputDirectoryString + Path.DirectorySeparatorChar + this.outputStylesheetString);
                this.outputTextWriter.WriteLine("<?xml version=\"1.0\"?>");
                this.outputTextWriter.WriteLine("<xsl:stylesheet version=\"1.0\"");
                this.outputTextWriter.WriteLine("xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">");
                this.outputTextWriter.WriteLine("<xsl:template match=\"/\">");
                this.outputTextWriter.WriteLine("<html>");
                this.outputTextWriter.WriteLine("<body>");
                this.outputTextWriter.WriteLine("<h2>Test Results</h2>");
                this.outputTextWriter.WriteLine("<table border=\"1\">");
                this.outputTextWriter.WriteLine("<tr bgcolor=\"#329acd\">");
                this.outputTextWriter.WriteLine("<th>ID</th>");
                this.outputTextWriter.WriteLine("<th>Result</th>");
                this.outputTextWriter.WriteLine("<th>Message</th>");
                this.outputTextWriter.WriteLine("</tr>");
                this.outputTextWriter.WriteLine("<xsl:apply-templates/>");
                this.outputTextWriter.WriteLine("</table>");
                this.outputTextWriter.WriteLine("</body>");
                this.outputTextWriter.WriteLine("</html>");
                this.outputTextWriter.WriteLine("</xsl:template>");
                this.outputTextWriter.WriteLine("<xsl:template match=\"batch/operations\">");
                this.outputTextWriter.WriteLine("<xsl:if test=\"@result = \'True\'\">");
                this.outputTextWriter.WriteLine("<tr bgcolor=\"#9acd32\">");
                this.outputTextWriter.WriteLine("<td><b><xsl:value-of select=\"@id\"/></b></td>");
                this.outputTextWriter.WriteLine("<td><xsl:value-of select=\"@result\"/></td>");
                this.outputTextWriter.WriteLine("<td><xsl:value-of select=\"@message\"/></td>");
                this.outputTextWriter.WriteLine("</tr>");
                this.outputTextWriter.WriteLine("</xsl:if>");
                this.outputTextWriter.WriteLine("<xsl:if test=\"@result = \'False\'\">");
                this.outputTextWriter.WriteLine("<tr bgcolor=\"#cd3232\">");
                this.outputTextWriter.WriteLine("<td><b><xsl:value-of select=\"@id\"/></b></td>");
                this.outputTextWriter.WriteLine("<td><xsl:value-of select=\"@result\"/></td>");
                this.outputTextWriter.WriteLine("<td><xsl:value-of select=\"@message\"/></td>");
                this.outputTextWriter.WriteLine("</tr>");
                this.outputTextWriter.WriteLine("</xsl:if>");
                this.outputTextWriter.WriteLine("<xsl:apply-templates/>");
                this.outputTextWriter.WriteLine("</xsl:template>");
                this.outputTextWriter.WriteLine("<xsl:template match=\"operation\">");
                this.outputTextWriter.WriteLine("<xsl:if test=\"@result = \'True\'\">");
                this.outputTextWriter.WriteLine("<tr bgcolor=\"#9acd32\">");
                this.outputTextWriter.WriteLine("<td>&#160;&#160;<xsl:value-of select=\"@id\"/></td>");
                this.outputTextWriter.WriteLine("<td><xsl:value-of select=\"@result\"/></td>");
                this.outputTextWriter.WriteLine("<td><xsl:value-of select=\"@message\"/></td>");
                this.outputTextWriter.WriteLine("</tr>");
                this.outputTextWriter.WriteLine("</xsl:if>");
                this.outputTextWriter.WriteLine("<xsl:if test=\"@result = \'False\'\">");
                this.outputTextWriter.WriteLine("<tr bgcolor=\"#cd3232\">");
                this.outputTextWriter.WriteLine("<td>&#160;&#160;<xsl:value-of select=\"@id\"/></td>");
                this.outputTextWriter.WriteLine("<td><xsl:value-of select=\"@result\"/></td>");
                this.outputTextWriter.WriteLine("<td><xsl:value-of select=\"@message\"/></td>");
                this.outputTextWriter.WriteLine("</tr>");
                this.outputTextWriter.WriteLine("</xsl:if>");
                this.outputTextWriter.WriteLine("</xsl:template>");
                this.outputTextWriter.WriteLine("</xsl:stylesheet>");
                this.outputTextWriter.Flush();
                this.outputTextWriter.Close();
            }

            ////string dateTime = DateTime.Now.ToString().Replace(" ", string.Empty).Replace("/", string.Empty).Replace(":", string.Empty);
            ////log.Debug("dateTime is " + dateTime);
            this.outputTextWriter = new StreamWriter(this.outputDirectoryString + Path.DirectorySeparatorChar + this.outputFileString, true);
            ////this.outputTextWriter.WriteLine("<operations>");
            ////this.outputTextWriter.Flush();
            return true;
        }

        /// <summary>
        /// This method writes an error message.
        /// </summary>
        /// <param name="message">The message parameter.</param>
        /// <returns>Returns true.</returns>
        public bool Error(string message)
        {
            return true;
        }

        /// <summary>
        /// This method writes a message.
        /// </summary>
        /// <param name="message">The message parameter.</param>
        /// <returns>Returns true.</returns>
        public bool Message(string message)
        {
            this.outputTextWriter.WriteLine(message);
            this.outputTextWriter.Flush();
            return true;
        }

        /// <summary>
        /// This method writes a warning message.
        /// </summary>
        /// <param name="message">The message parameter.</param>
        /// <returns>Returns true.</returns>
        public bool Warn(string message)
        {
            return true;
        }

        /// <summary>
        /// This method writes an error message.
        /// </summary>
        /// <param name="operationResult">The operation result parameter.</param>
        /// <returns>Returns true.</returns>
        public bool Error(OperationResult operationResult)
        {
            return this.Write(operationResult, "error");
        }

        /// <summary>
        /// This method writes a message.
        /// </summary>
        /// <param name="operationResult">The operation result parameter.</param>
        /// <returns>Returns true.</returns>
        public bool Message(OperationResult operationResult)
        {
            return this.Write(operationResult, "message");
        }

        /// <summary>
        /// This method writes a warning message.
        /// </summary>
        /// <param name="operationResult">The operation result parameter.</param>
        /// <returns>Returns true.</returns>
        public bool Warn(OperationResult operationResult)
        {
            return this.Write(operationResult, "warn");
        }

        /// <summary>
        /// This method writes the operation result and message to the XML file.
        /// </summary>
        /// <param name="operationResult">The operation result parameter.</param>
        /// <param name="status">The status parameter.</param>
        /// <returns>Returns true.</returns>
        public bool Write(OperationResult operationResult, string status)
        {
            if (operationResult.Id == "Start Batch")
            {
                log.Debug("operation result id is Start Batch");
                this.UpdateBatchInfo(operationResult);
            }

            if (operationResult.Id == "Start Run")
            {
                log.Debug("operation result id is Start Run");
                this.UpdateRunInfo(operationResult);
            }

            string message = "<operation id=\"" + System.Security.SecurityElement.Escape(operationResult.Id) + "\" message=\"" + System.Security.SecurityElement.Escape(operationResult.Message) + "\" result=\"" + operationResult.Result + "\" status=\"" + System.Security.SecurityElement.Escape(status) + "\" />";
            this.outputTextWriter.WriteLine(message);
            this.outputTextWriter.Flush();
            if (operationResult.Id == "Finish Run")
            {
                log.Debug("operation result id is Finish Run");
                this.UpdateRunInfo(operationResult);
            }

            if (operationResult.Id == "Finish Batch")
            {
                log.Debug("operation result id is Finish Batch");
                this.UpdateBatchInfo(operationResult);
            }

            return true;
        }

        /// <summary>
        /// This method opens and closes batch elements.
        /// </summary>
        /// <param name="operationResult">The operationResult parameter.</param>
        public void UpdateBatchInfo(OperationResult operationResult)
        {
            ////log.Debug("starting updateBatchInfo() for id " + operationResult.Id);
            if (operationResult.Id == "Start Batch")
            {
                log.Debug("operation result id is Start Batch");
                this.outputTextWriter.WriteLine("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
                this.outputTextWriter.WriteLine("<?xml-stylesheet type=\"text/xsl\" href=\"" + this.outputStylesheetString + "\"?>");
                string message = "<batch>";
                this.outputTextWriter.WriteLine(message);
                this.outputTextWriter.Flush();
                log.Debug("started batch");
            }
            else if (operationResult.Id == "Finish Batch")
            {
                log.Debug("operation result id is Finish Batch");
                string message = "</batch>";
                this.outputTextWriter.WriteLine(message);
                this.outputTextWriter.Flush();
                log.Debug("finished batch");
            }
        }

        /// <summary>
        /// This method is used to open and close operations elements.
        /// </summary>
        /// <param name="operationResult">The operationResult parameter.</param>
        public void UpdateRunInfo(OperationResult operationResult)
        {
            log.Debug("starting UpdateRunInfo()");
            if (operationResult.Id == "Start Run")
            {
                log.Debug("operation result id is Start Run");
                string message = "<operations id=\"" + System.Security.SecurityElement.Escape(operationResult.Id) + "\" message=\"" + System.Security.SecurityElement.Escape(operationResult.Message) + "\" result=\"" + operationResult.Result + "\" >";
                this.outputTextWriter.WriteLine(message);
                this.outputTextWriter.Flush();
                log.Debug("started run");
            }
            else if (operationResult.Id == "Finish Run")
            {
                log.Debug("operation result id is Finish Run");
                string message = "</operations>";
                this.outputTextWriter.WriteLine(message);
                this.outputTextWriter.Flush();
                log.Debug("finished run");
            }
            else
            {
                /* Do nothing */
            }
        }

        /// <summary>
        /// This method populates the file information.
        /// </summary>
        /// <param name="configString">The configString parameter.</param>
        public void PopulateMessenger(string configString)
        {
            log.Debug("starting PopulateMessenger()");
            string[] configArray = configString.Split(';');
            foreach (string parameter in configArray)
            {
                string[] nameValue = parameter.Split('=');
                switch (nameValue[0])
                {
                    case "outputFile":
                        log.Debug("outputFile is " + nameValue[1]);
                        this.outputFileString = nameValue[1];
                        break;
                    case "outputDirectory":
                        log.Debug("outputDirectory is " + nameValue[1]);
                        this.outputDirectoryString = nameValue[1];
                        break;
                    default:
                        log.Info("parameter name is unrecognized " + nameValue[0]);
                        break;
                }
            }

            log.Debug("finishing PopulateMessenger()");
        }
    }
}
