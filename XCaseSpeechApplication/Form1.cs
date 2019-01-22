namespace XCaseSpeechApplication
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel;
    using System.Configuration;
    using System.Data;
    using System.Drawing;
    using System.IO;
    using System.Linq;
    using System.Reflection;
    using System.Speech;
    using System.Speech.Recognition;
    using System.Text;
    using System.Threading.Tasks;
    using System.Windows.Forms;
    using log4net;
    using XCaseNUnitRunner.Core;

    public partial class Form1 : Form
    {
        #region Static Fields

        /// <summary>
        /// The log.
        /// </summary>
        private static readonly ILog Log = LogManager.GetLogger(MethodBase.GetCurrentMethod().DeclaringType);

        #endregion

        /// <summary>
        /// The run test manager instance that is used to run tests.
        /// </summary>
        private readonly RunTestManager runTestManager = new RunTestManager();

        #region Public Properties

        /// <summary>
        /// Gets the base directory of all xml test plans directory. 
        /// </summary>
        /// <exception cref="DirectoryNotFoundException">Is thrown in case test files directory is not specified in app config.</exception>
        public string BaseTestsDirectory
        {
            get
            {
                /* Get from app.config */
                string baseTestsDirectory = ConfigurationManager.AppSettings["TestFilesDirectory"];
                if (string.IsNullOrWhiteSpace(baseTestsDirectory))
                {
                    throw new DirectoryNotFoundException("The test files directory is not specified.");
                }

                return baseTestsDirectory;
            }
        }

        /// <summary>
        /// Gets directory where XML files for tests located. 
        /// </summary>
        public string XmlTestCasesDirectory
        {
            get
            {
                return Path.Combine(BaseTestsDirectory, @"sample");
            }
        }

        #endregion

        public Form1()
        {
            InitializeComponent();
            log4net.Config.XmlConfigurator.Configure();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            Log.DebugFormat("starting Form1_Load()");
            this.Text = "Speech Recognizer";
            SpeechRecognizer speechRecognizer = new SpeechRecognizer();
            Choices tests = new Choices();
            string[] testArray = new string[] { "red", "green", "blue" };
            tests.Add(testArray);
            GrammarBuilder grammarBuilder = new GrammarBuilder();
            grammarBuilder.Append(tests);
            Grammar grammar = new Grammar(grammarBuilder);
            speechRecognizer.LoadGrammar(grammar);
            speechRecognizer.SpeechRecognized += new EventHandler<SpeechRecognizedEventArgs>(sr_SpeechRecognized);
            Log.DebugFormat("finishing Form1_Load()");
        }

        private void sr_SpeechRecognized(object sender, SpeechRecognizedEventArgs e)
        {
            switch (e.Result.Text)
            {
                case "red":
                    MessageBox.Show("Running SampleTest1.xml...");
                    runTestManager.RunTest(XmlTestCasesDirectory, "SampleTest1.xml");
                    break;
                case "green":
                    MessageBox.Show("Running SampleTest2.xml...");
                    runTestManager.RunTest(XmlTestCasesDirectory, "SampleTest2.xml");
                    break;
                case "blue":
                    MessageBox.Show("To test specified");
                    break;
                default:
                    MessageBox.Show("Unrecognized command");
                    break;
            }
        }
    }
}
