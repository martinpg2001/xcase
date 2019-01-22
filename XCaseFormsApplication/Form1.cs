namespace XCaseFormsApplication
{
    using System;
    using System.Drawing;
    using System.IO;
    using System.Reflection;
    using System.Text;
    using System.Threading;
    using System.Threading.Tasks;
    using System.Windows.Forms;
    using System.Xml;
    using log4net;
    using log4net.Core;
    using XCaseBase;

    public partial class Form1 : Form
    {
        #region Static Fields

        /// <summary>
        /// The log.
        /// </summary>
        private static readonly ILog Log = LogManager.GetLogger(MethodBase.GetCurrentMethod().DeclaringType);
        private log4net.Appender.MemoryAppender logger;
        private LogWatcher logWatcher; 

        #endregion

        public Form1()
        {
            InitializeComponent();
            logger = new log4net.Appender.MemoryAppender();
            log4net.Config.BasicConfigurator.Configure(logger);
            logWatcher = new LogWatcher();
            logWatcher.Updated += logWatcher_Updated;
        }

        private void openToolStripMenuItem_Click(object sender, EventArgs e)
        {
            openFileDialog1.Title = "Select a test file";
            if (openFileDialog1.ShowDialog() == DialogResult.OK)
            {
                textBox2.Text = string.Empty;
                logWatcher.Clear();
                textBox3.ResetText();
                try
                {
                    using (StreamReader sr = File.OpenText(openFileDialog1.FileName))
                    {
                        textBox1.Text = File.ReadAllText(openFileDialog1.FileName);
                    }

                    this.Text = openFileDialog1.FileName;
                }
                catch (Exception ex)
                {
                    MessageBox.Show("An error occured: " + ex.Message);
                }
            }
        }

        private void exitToolStripMenuItem_Click(object sender, EventArgs e)
        {
            Application.Exit();
        }

        private void goToolStripMenuItem_Click(object sender, EventArgs ea)
        {
            Log.DebugFormat("starting goToolStripMenuItem_Click()");
            try
            {
                Cursor.Current = Cursors.WaitCursor;
                XmlDocument xmlDocument = new XmlDocument();
                xmlDocument.LoadXml(textBox1.Text);
                ProcessDocumentResult processDocumentResult = DocumentProcessor.ProcessDocument(new ProcessEnvironment(), xmlDocument);
                if (processDocumentResult.Result)
                {
                    textBox2.ForeColor = Color.Green;
                    textBox2.Text = string.Format("Success: {0}", processDocumentResult.Message);
                }
                else
                {
                    textBox2.ForeColor = Color.Red;
                    textBox2.Text = string.Format("Failure: {0}", processDocumentResult.Message);
                }
            }
            catch (Exception e)
            {
                textBox2.Text = e.Message;
            }
            finally
            {
                Cursor.Current = Cursors.Default;
            }

            Log.DebugFormat("finishing goToolStripMenuItem_Click()");
        }

        private void saveToolStripMenuItem_Click(object sender, EventArgs e)
        {
            Log.DebugFormat("starting saveToolStripMenuItem_Click()");
            File.WriteAllText(this.Text, textBox1.Text);
            Log.DebugFormat("finishing saveToolStripMenuItem_Click()");
        }

        public void logWatcher_Updated(object sender, EventArgs e)
        {
            UpdateLogTextbox(logWatcher.LogContent);
        }

        public void UpdateLogTextbox(string value)
        {
            if (InvokeRequired)
            {

                this.BeginInvoke(new Action(() => this.UpdateLogTextbox(value)));
                return;
            }

            textBox3.Text = value;
        }

        private void aboutToolStripMenuItem_Click(object sender, EventArgs e)
        {
            new AboutBox().ShowDialog();
        }
    }
}
