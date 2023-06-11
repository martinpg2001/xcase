using Microsoft.CodeAnalysis;
using Microsoft.CodeAnalysis.CSharp;
using Microsoft.CodeAnalysis.Emit;
using Microsoft.CSharp;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Logging;
using Newtonsoft.Json.Linq;
using Serilog;
using Serilog.Core;
using Serilog.Events;
using Serilog.Formatting.Json;
using Serilog.Configuration;
using Serilog.Settings;
using System;
using System.CodeDom.Compiler;
using System.Collections;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Security;
using System.Reflection;
using System.Runtime.Serialization;
using System.Security.Cryptography.X509Certificates;
using System.ServiceModel;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Xml;
using XCase.ProxyGenerator;
using XCase.REST.ProxyGenerator;
using XCase.REST.ProxyGenerator.Generator;
using Newtonsoft.Json;
using Newtonsoft.Json.Converters;

namespace XCaseServiceClient
{
    public partial class XCaseServiceClientForm : Form
    {
        #region Logger Setup

        /// <summary>
        /// A log instance.
        /// </summary>
        private static readonly Serilog.ILogger Log = new LoggerConfiguration().Enrich.WithProperty("Class", "XCaseServiceClientForm").ReadFrom.Configuration(new ConfigurationBuilder()
            .SetBasePath(Directory.GetCurrentDirectory())
            .AddJsonFile("appsettings.json", optional: false, reloadOnChange: true)
            .Build()).CreateLogger();

        #endregion

        #region Properties Setup

        Assembly m_Assembly = null;
        bool m_OnPremise = true;
        bool m_ProxyEnable = false;
        bool m_Starting = true;
        public static bool multiline = true;
        public static Color productColor = Color.LightSkyBlue;
        public static Color labelBackgroundColor = Color.SlateGray;
        ComboBox m_LanguageComboBox = new ComboBox();
        ComboBox m_ServicesComboBox = new ComboBox();
        ComboBox m_TypeComboBox = new ComboBox();
        FontFamily m_FontFamily = new FontFamily("Arial");
        int m_MaxArrayLength = 10;
        int m_MethodPanelInset = 20;
        int m_ProxyPort = -1;
        int m_Timeout = 30;
        int m_TabPanelBuffer = 40;
        int m_TopPanelHeight = 100;
        int m_TopPanelBuffer = 0;
        int m_WindowHeight = 0;
        int m_WindowHeightIndent = 80;
        int m_WindowWidth = 0;
        int m_WindowWidthIndent = 40;
        Label m_BindingLabel = new Label();
        Label m_SecurityLabel = new Label();
        Label m_MessageCredentialTypeLabel = new Label();
        Label m_ClientCredentialTypeLabel = new Label();
        object m_RESTServiceClient = null;
        object m_ServiceClient = null;
        string m_Binding = "Basic";
        string m_ClientCredentialDomain = null;
        string m_ClientCredentialPassword = null;
        string m_ClientCredentialType = "None";
        string m_ClientCredentialUserName = null;
        string m_CurrentDirectory = "";// Properties.Settings.Default.ServicesDirectory;
        string m_Language = "CSharp";
        string m_MessageCredentialType = "None";
        string m_ProxyAddress = null;
        string m_SecurityMode = "None";
        string m_SecurityProtocol = "Tls11";
        string m_ServiceDescriptionURL = null;
        string m_ServiceName = "Service Name";
        string m_Type = "SOAP";
        string m_WindowTitle = "XCase Web Service Client";
        //        string[] m_References = new string[] { "System.dll", "System.ComponentModel.DataAnnotations.dll", "System.Core.dll", "System.Data.dll", "System.Net.dll", "System.Net.Http.dll", "System.Runtime.Serialization.dll", "System.ServiceModel.dll", "System.Web.dll", "System.Web.Services.dll", "System.Xml.dll" };
        string[] m_Services = new string[] { };
        string[] m_SourceStringArray = new string[] { };
        IProxyGenerator m_SwaggerProxyGenerator = new SwaggerCSharpProxyGenerator();
        RESTApiProxySettingsEndPoint restApiProxySettingsEndPoint = new RESTApiProxySettingsEndPoint("CSharp");
        IServiceDefinition restServiceDefinition = null;
        TabControl m_MethodsTabControl;
        TableLayoutPanel m_TopTableLayoutPanel;
        TextBox m_DomainTextBox = new TextBox();
        TextBox m_MaxArrayLengthTextBox = new TextBox();
        TextBox m_PasswordTextBox = new TextBox();
        TextBox m_ServiceDescriptionURLTextBox = new TextBox();
        TextBox m_TimeoutTextBox = new TextBox();
        TextBox m_UsernameTextBox = new TextBox();
        RichTextBox m_ViewRichTextBox = new RichTextBox();
        [JsonConverter(typeof(StringEnumConverter))]
        public languages codeLanguages { get; set; }
        public enum languages
        {
            [EnumMember(Value = "CSharp")]
            CSharp,
            [EnumMember(Value = "Java")]
            Java,
            [EnumMember(Value = "Python")]
            Python,
        }

        #endregion

        public XCaseServiceClientForm()
        {
            //ILoggerFactory loggerFactory = new LoggerFactory().AddConsole();
            //ILogger logger = loggerFactory.CreateLogger<Program>();
            Log.Information("this is a log message");
            Initialize();
        }

        private void Initialize()
        {
            Log.Debug("starting Initialize()");
            Log.Debug("about to initialize component");
            InitializeComponent();
            Log.Debug("initialized component");
            System.Net.ServicePointManager.SecurityProtocol = SecurityProtocolType.Tls | SecurityProtocolType.Tls11 | SecurityProtocolType.Tls12;
            Rectangle workingRectangle = Screen.PrimaryScreen.WorkingArea;
            this.m_WindowWidth = workingRectangle.Width - m_WindowWidthIndent;
            this.m_WindowHeight = workingRectangle.Height - m_WindowHeightIndent;
            this.m_TopTableLayoutPanel = new TableLayoutPanel();
            this.SuspendLayout();
            this.AutoScroll = true;
            //
            // topTableLayoutPanel
            //
            this.m_TopTableLayoutPanel.Location = new Point(0, 30);
            this.m_TopTableLayoutPanel.Name = "topTableLayoutPanel";
            this.m_TopTableLayoutPanel.Height = m_TopPanelHeight;
            this.m_TopTableLayoutPanel.Width = m_WindowWidth;
            this.m_TopTableLayoutPanel.BackColor = productColor;
            this.m_TopTableLayoutPanel.CellBorderStyle = TableLayoutPanelCellBorderStyle.Single;
            this.m_TopTableLayoutPanel.TabIndex = 0;
            //
            // Form1
            //
            this.ClientSize = new Size(m_WindowWidth, m_WindowHeight);
            this.Controls.Add(this.m_TopTableLayoutPanel);
            this.Name = "XCaseServiceClientForm";
            this.Text = m_WindowTitle;
            this.ResumeLayout(false);
            StartPosition = FormStartPosition.CenterScreen;
            /* Code to dynamically generate UI */
            m_TopTableLayoutPanel.RowCount = 2;
            m_TopTableLayoutPanel.ColumnCount = 10;
            TableLayoutColumnStyleCollection tableLayoutColumnStyleCollection = m_TopTableLayoutPanel.ColumnStyles;
            TableLayoutRowStyleCollection tableLayoutRowStyleCollection = m_TopTableLayoutPanel.RowStyles;
            ColumnStyle urlColumnStyle = new ColumnStyle
            {
                SizeType = SizeType.AutoSize
            };
            tableLayoutColumnStyleCollection.Add(urlColumnStyle);
            for (int i = 1; i < m_TopTableLayoutPanel.ColumnCount; i++)
            {
                ColumnStyle columnStyle = new ColumnStyle
                {
                    SizeType = SizeType.Percent
                };
                switch (i)
                {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 9:
                        columnStyle.Width = 9;
                        break;
                    case 7:
                    case 8:
                        columnStyle.Width = 13;
                        break;
                    default:
                        break;
                }

                tableLayoutColumnStyleCollection.Add(columnStyle);
            }

            for (int i = 0; i < m_TopTableLayoutPanel.RowCount; i++)
            {
                RowStyle rowStyle = new RowStyle
                {
                    SizeType = SizeType.Percent,
                    Height = 50
                };
                tableLayoutRowStyleCollection.Add(rowStyle);
            }

            m_TopTableLayoutPanel.Controls.Add(m_TypeComboBox, 0, 0);
            Log.Debug("added m_TypeComboBox");
            m_TypeComboBox.DataSource = new string[] { "Custom", "Integrate", "KlearNow", "NetDocs", "Open", "PlatformCDS", "PlatformCDSCM", "PlatformDocument", "PlatformRefData", "PlatformSanctionLists", "PlatformTMS", "RAML", "REST", "SOAP", "Source", "Swagger", "Time", "View" };
            ComboBox.ObjectCollection typeObjectCollection = m_TypeComboBox.Items;
            if (typeObjectCollection.Contains(m_Type))
            {
                Log.Debug("objectCollection contains {0}", m_Type);
                int index = typeObjectCollection.IndexOf(m_Type);
                m_TypeComboBox.SelectedIndex = index;
            }
            else
            {
                Log.Debug("objectCollection does not contain {0}", m_Type);
                int index = typeObjectCollection.IndexOf("SOAP");
                Log.Debug("index of SOAP is {0}", index);
                m_TypeComboBox.SelectedIndex = index;
            }

            m_TypeComboBox.SelectionChangeCommitted += delegate (object o, EventArgs ev)
            {
                Log.Debug("m_TypeComboBox SelectionChangeCommitted");
                m_Type = (string)m_TypeComboBox.SelectedItem;
                Log.Debug("m_Type is {0}", m_Type);
            };
            m_ServiceDescriptionURLTextBox.Text = m_ServiceDescriptionURL;
            m_ServiceDescriptionURLTextBox.Width = 250;
            m_ServiceDescriptionURLTextBox.TextChanged += delegate (object o, EventArgs ev)
            {
                m_ServiceDescriptionURL = m_ServiceDescriptionURLTextBox.Text;
            };
            m_TopTableLayoutPanel.Controls.Add(m_ServiceDescriptionURLTextBox, 0, 1);
            Log.Debug("added m_ServiceDescriptionURLTextBox");
            Label languageLabel = new Label();
            languageLabel.Text = XCaseServiceClient.Properties.Resources.Language;
            m_TopTableLayoutPanel.Controls.Add(languageLabel, 1, 0);
            Log.Debug("added languageLabel");
            m_TopTableLayoutPanel.Controls.Add(m_LanguageComboBox, 1, 1);
            Log.Debug("added m_LanguageComboBox");
            m_LanguageComboBox.DataSource = new string[] { "CSharp", "Java" };
            ComboBox.ObjectCollection languageObjectCollection = m_LanguageComboBox.Items;
            if (languageObjectCollection.Contains(m_Language))
            {
                Log.Debug("languageObjectCollection contains {0}", m_Language);
                int index = languageObjectCollection.IndexOf(m_Language);
                m_LanguageComboBox.SelectedIndex = index;
            }
            else
            {
                Log.Debug("languageObjectCollection does not contain {0}", m_Language);
                int index = languageObjectCollection.IndexOf("CSharp");
                Log.Debug("index of CSharp is {0}", index);
                m_LanguageComboBox.SelectedIndex = index;
            }

            m_LanguageComboBox.SelectionChangeCommitted += delegate (object o, EventArgs ev)
            {
                Log.Debug("m_TypeComboBox SelectionChangeCommitted");
                m_Language = (string)m_LanguageComboBox.SelectedItem;
                Log.Debug("m_Language is {0}", m_Language);
            };
            Label serviceURLLabel = new Label();
            serviceURLLabel.Text = XCaseServiceClient.Properties.Resources.ServiceName;
            m_TopTableLayoutPanel.Controls.Add(serviceURLLabel, 2, 0);
            Log.Debug("added serviceURLLabel");
            m_ServicesComboBox.DataSource = m_Services;
            if (m_Services.Contains<string>(m_ServiceName))
            {
                m_ServicesComboBox.SelectedItem = m_ServiceName;
            }

            m_ServicesComboBox.DataSourceChanged += delegate (object o, EventArgs ev)
            {
                m_ServicesComboBox.Refresh();
            };
            m_ServicesComboBox.SelectionChangeCommitted += delegate (object o, EventArgs ev)
            {
                Log.Debug("m_ServicesComboBox SelectionChangeCommitted");
                m_ServiceName = (string)m_ServicesComboBox.SelectedItem;
                Log.Debug("m_ServiceName is {0}", m_ServiceName);
                ProcessServicesChange();
            };
            m_TopTableLayoutPanel.Controls.Add(m_ServicesComboBox, 2, 1);
            Log.Debug("added m_ServicesComboBox");
            Label domainLabel = new Label();
            domainLabel.Text = XCaseServiceClient.Properties.Resources.Domain;
            m_TopTableLayoutPanel.Controls.Add(domainLabel, 3, 0);
            Log.Debug("added domainLabel");
            m_DomainTextBox.Text = m_ClientCredentialDomain;
            m_TopTableLayoutPanel.Controls.Add(m_DomainTextBox, 3, 1);
            Log.Debug("added m_DomainTextBox");
            Label usernameLabel = new Label();
            usernameLabel.Text = XCaseServiceClient.Properties.Resources.Username;
            m_TopTableLayoutPanel.Controls.Add(usernameLabel, 4, 0);
            Log.Debug("added usernameLabel");
            m_UsernameTextBox.Text = m_ClientCredentialUserName;
            m_TopTableLayoutPanel.Controls.Add(m_UsernameTextBox, 4, 1);
            Log.Debug("added m_UsernameTextBox");
            Label passwordLabel = new Label();
            passwordLabel.Text = XCaseServiceClient.Properties.Resources.Password;
            m_TopTableLayoutPanel.Controls.Add(passwordLabel, 5, 0);
            Log.Debug("added passwordLabel");
            m_PasswordTextBox.PasswordChar = '*';
            m_PasswordTextBox.Text = m_ClientCredentialPassword;
            m_TopTableLayoutPanel.Controls.Add(m_PasswordTextBox, 5, 1);
            Log.Debug("added m_PasswordTextBox");
            /* Array length control */
            Label m_MaxArrayLengthLabel = new Label();
            m_MaxArrayLengthLabel.Text = XCaseServiceClient.Properties.Resources.ArrayLength;
            m_TopTableLayoutPanel.Controls.Add(m_MaxArrayLengthLabel, 6, 0);
            Log.Debug("added m_MaxArrayLengthLabel");
            m_MaxArrayLengthTextBox.Text = m_MaxArrayLength.ToString();
            m_MaxArrayLengthTextBox.Visible = true;
            m_TopTableLayoutPanel.Controls.Add(m_MaxArrayLengthTextBox, 6, 1);
            Log.Debug("added m_MaxArrayLengthTextBox");
            m_MaxArrayLengthTextBox.TextChanged += delegate (object o, EventArgs ev)
            {
                try
                {
                    m_MaxArrayLength = Convert.ToInt32(m_MaxArrayLengthTextBox.Text);
                }
                catch (Exception e)
                {
                    Log.Debug("max array length format incorrect {0}", e.Message);
                }
            };
            /* Binding settings */
            m_BindingLabel.AutoEllipsis = true;
            m_BindingLabel.AutoSize = true;
            m_BindingLabel.Text = string.Format("Binding: {0}", m_Binding);
            m_TopTableLayoutPanel.Controls.Add(m_BindingLabel, 7, 0);
            m_SecurityLabel.AutoEllipsis = true;
            m_SecurityLabel.AutoSize = true;
            m_SecurityLabel.Text = string.Format("Security: {0}", m_SecurityMode);
            m_TopTableLayoutPanel.Controls.Add(m_SecurityLabel, 7, 1);
            m_MessageCredentialTypeLabel.AutoEllipsis = true;
            m_MessageCredentialTypeLabel.AutoSize = true;
            m_MessageCredentialTypeLabel.Text = string.Format("Message: {0}", m_MessageCredentialType);
            m_TopTableLayoutPanel.Controls.Add(m_MessageCredentialTypeLabel, 8, 0);
            Log.Debug("added m_MessageCredentialTypeLabel");
            m_ClientCredentialTypeLabel.AutoEllipsis = true;
            m_ClientCredentialTypeLabel.AutoSize = true;
            m_ClientCredentialTypeLabel.Text = string.Format("Client: {0}", m_ClientCredentialType);
            m_TopTableLayoutPanel.Controls.Add(m_ClientCredentialTypeLabel, 8, 1);
            Log.Debug("added m_ClientCredentialTypeLabel");
            /* Buttons */
            Button goButton = new Button();
            goButton.Dock = DockStyle.Fill;
            goButton.Text = XCaseServiceClient.Properties.Resources.Go;
            m_TopTableLayoutPanel.Controls.Add(goButton, 9, 0);
            Log.Debug("added goButton");
            goButton.MouseClick += delegate (object o, MouseEventArgs mev)
            {
                Log.Debug("Go clicked");
                ProcessGoClicked();
            };
            Button fileButton = new Button();
            fileButton.Dock = DockStyle.Fill;
            fileButton.Text = XCaseServiceClient.Properties.Resources.File;
            fileButton.MouseClick += delegate (object o, MouseEventArgs mev)
            {
                OpenFileDialog openFileDialog = new OpenFileDialog();
                Log.Debug("service name is " + m_ServiceName);
                if (m_CurrentDirectory == null)
                {
                    m_CurrentDirectory = Path.GetDirectoryName(Path.GetDirectoryName(Directory.GetCurrentDirectory()));
                }

                openFileDialog.InitialDirectory = m_CurrentDirectory;
                openFileDialog.Filter = "WSDL Files (*.wsdl)|*.WSDL";
                if (openFileDialog.ShowDialog() == DialogResult.OK)
                {
                    string fileName = openFileDialog.FileName;
                    Log.Debug("fileName is {0}", fileName);
                    //ProcessWSDLFile(fileName);
                }
            };
            m_TopTableLayoutPanel.Controls.Add(fileButton, 9, 1);
            Log.Debug("added fileButton");
            /* Top panel rendered. */
            RenderServiceControl(m_ServiceClient);
            this.Controls.Add(m_MethodsTabControl);
            Log.Debug("added m_MethodsTabControl");
            this.ResumeLayout(true);
            m_Starting = false;
            Log.Debug("finishing Initialize()");
        }

        private void ProcessServicesChange()
        {
            Log.Debug("starting ProcessServicesChange()");
            Log.Debug("m_Language is {0}", m_Language);
            Log.Debug("m_Type is {0}", m_Type);
            m_ServiceName = (string)m_ServicesComboBox.SelectedValue;
            Log.Debug("service name changed to " + (string)m_ServicesComboBox.SelectedValue);
            Log.Debug("about to execute switch statement on " + m_Type);
            switch (m_Type)
            {
                case "Custom":
                    ProcessCustomType(false);
                    break;
                case "Integrate":
                    //ProcessIntegrateType(false);
                    break;
                case "Open":
                    ProcessSwaggerType(false);
                    break;
                case "PlatformCDS":
                    //ProcessPlatformCDSType(false);
                    break;
                case "PlatformDocument":
                    //ProcessPlatformDocumentType(false);
                    break;
                case "PlatformRefData":
                    //ProcessPlatformRefDataType(false);
                    break;
                case "PlatformSanctionLists":
                    //ProcessPlatformSanctionListsType(false);
                    break;
                case "PlatformTMS":
                    //ProcessPlatformTMSType(false);
                    break;
                case "RAML":
                    ProcessRAMLType(false);
                    break;
                case "REST":
                    ProcessSwaggerType(false);
                    break;
                case "SOAP":
                    Log.Debug("about to get service contract client from WSDL");
                    //m_ServiceClient = GetServiceContractClientFromWSDL(m_ServiceName);
                    Log.Debug("got service contract client from WSDL");
                    break;
                case "Swagger":
                    ProcessSwaggerType(false);
                    break;
                case "Time":
                    //ProcessTimeType(false);
                    break;
                default:
                    Log.Debug("about to get service contract client from WSDL");
                    //m_ServiceClient = GetServiceContractClientFromWSDL(m_ServiceName);
                    Log.Debug("got service contract client from WSDL");
                    break;
            }

            /*
            if (string.IsNullOrEmpty(m_Type) || m_Type == "SOAP")
            {

                if (string.IsNullOrEmpty(m_ServiceName))
                {
                    Log.Debug("service name is empty");
                }
                else
                {
                    Log.Debug("about to get service contract client from WSDL");
                    //m_ServiceClient = GetServiceContractClientFromWSDL(m_ServiceName);
                    Log.Debug("got service contract client from WSDL");
                }
            }
            else if (m_Type == "Custom")
            {
                m_ServiceName = (string)m_ServicesComboBox.SelectedValue;
                Log.Debug("service name changed to " + (string)m_ServicesComboBox.SelectedValue);
                ProcessCustomType(false);
            }
            else if (m_Type == "Integrate")
            {
                m_ServiceName = (string)m_ServicesComboBox.SelectedValue;
                Log.Debug("service name changed to " + (string)m_ServicesComboBox.SelectedValue);
                //ProcessIntegrateType(false);
            }
            else if (m_Type == "Open")
            {
                m_ServiceName = (string)m_ServicesComboBox.SelectedValue;
                Log.Debug("service name changed to " + (string)m_ServicesComboBox.SelectedValue);
                ProcessSwaggerType(false);
            }
            else if (m_Type == "PlatformCDS")
            {
                m_ServiceName = (string)m_ServicesComboBox.SelectedValue;
                Log.Debug("service name changed to " + (string)m_ServicesComboBox.SelectedValue);
                //ProcessPlatformCDSType(false);
            }
            else if (m_Type == "PlatformDocument")
            {
                m_ServiceName = (string)m_ServicesComboBox.SelectedValue;
                Log.Debug("service name changed to " + (string)m_ServicesComboBox.SelectedValue);
                //ProcessPlatformDocumentType(false);
            }
            else if (m_Type == "PlatformRefData")
            {
                m_ServiceName = (string)m_ServicesComboBox.SelectedValue;
                Log.Debug("service name changed to " + (string)m_ServicesComboBox.SelectedValue);
                //ProcessPlatformRefDataType(false);
            }
            else if (m_Type == "PlatformSanctionLists")
            {
                m_ServiceName = (string)m_ServicesComboBox.SelectedValue;
                Log.Debug("service name changed to " + (string)m_ServicesComboBox.SelectedValue);
                //ProcessPlatformSanctionListsType(false);
            }
            else if (m_Type == "PlatformTMS")
            {
                m_ServiceName = (string)m_ServicesComboBox.SelectedValue;
                Log.Debug("service name changed to " + (string)m_ServicesComboBox.SelectedValue);
                //ProcessPlatformTMSType(false);
            }
            else if (m_Type == "RAML")
            {
                m_ServiceName = (string)m_ServicesComboBox.SelectedValue;
                Log.Debug("service name changed to " + (string)m_ServicesComboBox.SelectedValue);
                ProcessRAMLType(false);
            }
            else if (m_Type == "REST")
            {
                m_ServiceName = (string)m_ServicesComboBox.SelectedValue;
                Log.Debug("service name changed to " + (string)m_ServicesComboBox.SelectedValue);
                ProcessSwaggerType(false);
            }
            else if (m_Type == "Swagger")
            {
                m_ServiceName = (string)m_ServicesComboBox.SelectedValue;
                Log.Debug("service name changed to " + (string)m_ServicesComboBox.SelectedValue);
                ProcessSwaggerType(false);
            }
            else if (m_Type == "Time")
            {
                m_ServiceName = (string)m_ServicesComboBox.SelectedValue;
                Log.Debug("service name changed to " + (string)m_ServicesComboBox.SelectedValue);
                //ProcessTimeType(false);
            }
            */
        }

        private void ProcessCustomType(Boolean refresh)
        {
            Log.Debug("starting ProcessCustomType()");
            try
            {
                this.Controls.Remove(m_ViewRichTextBox);
                Log.Debug("m_Language is {0}", m_Language);
                Log.Debug("m_Type is {0}", m_Type);
                m_ClientCredentialDomain = m_DomainTextBox.Text;
                Log.Debug("m_ClientCredentialDomain is {0}", m_ClientCredentialDomain);
                m_ClientCredentialUserName = m_UsernameTextBox.Text;
                Log.Debug("m_ClientCredentialUserName is {0}", m_ClientCredentialUserName);
                m_ClientCredentialPassword = m_PasswordTextBox.Text;
                Log.Debug("m_ClientCredentialPassword is {0}", m_ClientCredentialPassword);
                m_ServiceDescriptionURL = m_ServiceDescriptionURLTextBox.Text;
                Log.Debug("about to get REST description from " + m_ServiceDescriptionURL);
                this.Text = m_WindowTitle + " - retrieving REST description from " + m_ServiceDescriptionURL;
                if (refresh)
                {
                    Log.Debug("refresh is true");
                    this.Controls.Remove(m_MethodsTabControl);
                    if (!string.IsNullOrEmpty(m_Language) && m_Language == "Java")
                    {
                        m_SwaggerProxyGenerator = new SwaggerJavaProxyGenerator();
                        restApiProxySettingsEndPoint = new RESTApiProxySettingsEndPoint("Java");
                    }
                    else
                    {
                        m_SwaggerProxyGenerator = new SwaggerCSharpProxyGenerator();
                        restApiProxySettingsEndPoint = new RESTApiProxySettingsEndPoint("CSharp", "CustomBaseProxy");
                        restApiProxySettingsEndPoint.Accept = "application/json";
                    }

                    restApiProxySettingsEndPoint.Url = m_ServiceDescriptionURL;
                    Log.Debug("m_ServiceDescriptionURL is {0}", m_ServiceDescriptionURL);
                    RESTApiProxySettingsEndPoint[] endpoints = new RESTApiProxySettingsEndPoint[] { restApiProxySettingsEndPoint };
                    restServiceDefinition = m_SwaggerProxyGenerator.GenerateSourceString(endpoints);
                    //Log.Debug("swaggerServiceDefinition EndPoint is {0}", restServiceDefinition.GetEndPoint());
                    this.Text = m_WindowTitle + " - got REST service definition";
                    m_SourceStringArray = restServiceDefinition.GetSourceStrings();
                    if (m_SourceStringArray != null)
                    {
                        foreach (string sourceString in m_SourceStringArray)
                        {
                            Log.Debug("sourceString is {0}", sourceString);
                        }
                    }

                    Log.Debug("endpoint is {0}", restServiceDefinition.GetEndPoint());
                    if (string.IsNullOrEmpty(m_Language) || m_Language == "CSharp")
                    {
                        m_ServicesComboBox.DataSource = restServiceDefinition.GetProxyClasses();
                        if (restServiceDefinition.GetProxyClasses().Contains<string>(((string)m_ServicesComboBox.SelectedItem)))
                        {
                            m_ServicesComboBox.SelectedItem = restServiceDefinition.GetProxyClasses().First<string>(pc => pc == ((string)m_ServicesComboBox.SelectedItem));
                        }
                        else
                        {
                            m_ServicesComboBox.SelectedItem = restServiceDefinition.GetProxyClasses().First<string>();
                        }

                        List<SyntaxTree> syntaxTreeList = CreateSyntaxTreeListFromSourceStringArray();
                        string assemblyName = Path.GetRandomFileName();
                        Log.Debug("assemblyName is {0}", assemblyName);
                        List<MetadataReference> metadataReferenceList = CreateMetadataReferenceList();
                        Log.Debug("created metadataReferenceList");
                        CSharpCompilation cSharpCompilation = CSharpCompilation.Create(assemblyName, syntaxTrees: syntaxTreeList, references: metadataReferenceList, options: new CSharpCompilationOptions(OutputKind.DynamicallyLinkedLibrary));
                        Log.Debug("created cSharpCompilation");
                        m_Assembly = CreateAssemblyFromCSharpCompilation(cSharpCompilation);
                        Log.Debug("created assembly");
                        object[] args = new object[] { new Uri(restServiceDefinition.GetEndPoint()) };
                        string proxyClass = string.Format("{0}.{1}", restApiProxySettingsEndPoint.Namespace, m_ServicesComboBox.SelectedItem);
                        m_RESTServiceClient = m_Assembly.CreateInstance(proxyClass, false, BindingFlags.CreateInstance, null, args, null, null);
                        if (m_RESTServiceClient != null)
                        {
                            NetworkCredential networkCredential = new NetworkCredential(m_ClientCredentialUserName, m_ClientCredentialPassword, m_ClientCredentialDomain);
                            ((SwaggerProxy)m_RESTServiceClient).ClientCredentials = networkCredential;
                            if (m_ProxyEnable)
                            {
                                ((SwaggerProxy)m_RESTServiceClient).Proxy = new WebProxy(m_ProxyAddress, m_ProxyPort);
                            }

                            Log.Debug("set client credentials");
                        }
                        else
                        {
                            Log.Debug("m_RESTServiceClient is null");
                        }

                        Log.Debug("about to re-render service control");
                        RerenderServiceControl(m_RESTServiceClient);
                    }
                    else if (m_Language == "Java")
                    {
                        MessageBox.Show("Finished generating classes.");
                    }

                    Log.Debug("{0}", restServiceDefinition.GetEndPoint());
                }
                else
                {
                    /* refresh is false, but service or proxy class has changed */
                    Log.Debug("refresh is false");
                    if (string.IsNullOrEmpty(m_Language) || m_Language == "CSharp")
                    {
                        object[] args = new object[] { new Uri(restServiceDefinition.GetEndPoint()) };
                        string proxyClass = string.Format("{0}.{1}", restApiProxySettingsEndPoint.Namespace, m_ServicesComboBox.SelectedItem);
                        m_RESTServiceClient = m_Assembly.CreateInstance(proxyClass, false, BindingFlags.CreateInstance, null, args, null, null);
                        if (m_RESTServiceClient != null)
                        {
                            NetworkCredential networkCredential = new NetworkCredential(m_ClientCredentialUserName, m_ClientCredentialPassword, m_ClientCredentialDomain);
                            ((SwaggerProxy)m_RESTServiceClient).ClientCredentials = networkCredential;
                            Log.Debug("set client credentials");
                        }
                        else
                        {
                            Log.Debug("m_RESTServiceClient is null");
                        }

                        Log.Debug("about to re-render service control");
                        RerenderServiceControl(m_RESTServiceClient);
                    }

                    Log.Debug("{0}", restServiceDefinition.GetEndPoint());
                }

                Log.Debug("finishing ProcessCustomType()");
            }
            catch (AggregateException ae)
            {
                Log.Debug("aggregate exception thrown: " + ae.Message);
                if (!m_Starting)
                {
                    MessageBox.Show("Aggregate exception thrown: " + ae.Message);
                }
            }
            catch (Exception e)
            {
                Log.Debug("exception thrown: " + e.Message);
                if (!m_Starting)
                {
                    MessageBox.Show("Exception thrown: " + e.Message);
                }
            }
        }

        private static Assembly CreateAssemblyFromCSharpCompilation(CSharpCompilation cSharpCompilation)
        {
            Log.Debug("starting CreateAssemblyFromCSharpCompilation()");
            using (MemoryStream memoryStream = new MemoryStream())
            {
                EmitResult result = cSharpCompilation.Emit(memoryStream);
                if (!result.Success)
                {
                    Log.Warning("result is {0}", result.Success);
                    IEnumerable<Diagnostic> failures = result.Diagnostics.Where(diagnostic => diagnostic.IsWarningAsError || diagnostic.Severity == DiagnosticSeverity.Error);
                    foreach (Diagnostic diagnostic in failures)
                    {
                        Log.Warning("{0}: {1}", diagnostic.Id, diagnostic.GetMessage());
                    }

                    return null;
                }
                else
                {
                    memoryStream.Seek(0, SeekOrigin.Begin);
                    return Assembly.Load(memoryStream.ToArray());
                }
            }
        }

        private static List<MetadataReference> CreateMetadataReferenceList()
        {
            List<MetadataReference> metadataReferenceList = new List<MetadataReference>();
            AppDomain currentDomain = AppDomain.CurrentDomain;
            Assembly[] assemblyArray = currentDomain.GetAssemblies();
            foreach (Assembly domainAssembly in assemblyArray)
            {
                //Log.DebugFormat("next domainAssembly {0}", domainAssembly.GetName());
                try
                {
                    AssemblyMetadata assemblyMetadata = AssemblyMetadata.CreateFromFile(domainAssembly.Location);
                    //Log.DebugFormat("got assemblyMetadata {0}", domainAssembly.GetName());
                    MetadataReference metadataReference = assemblyMetadata.GetReference();
                    //Log.DebugFormat("got metadataReference {0}", domainAssembly.GetName());
                    metadataReferenceList.Add(metadataReference);
                    //Log.DebugFormat("added reference {0}", domainAssembly.GetName());
                }
                catch (Exception e)
                {
                    Log.Debug("failed to get MetadataReference {0}", e.Message);
                }
            }

            foreach (MetadataReference metadataReference in metadataReferenceList)
            {
                //Log.Debug("metadataReference Display is {0}", metadataReference.Properties.Kind);
            }

            var assemblyPath = Path.GetDirectoryName(typeof(object).Assembly.Location);
            metadataReferenceList.Add(MetadataReference.CreateFromFile(Path.Combine(assemblyPath, "System.Runtime.Serialization.Primitives.dll")));

            return metadataReferenceList;
        }

        private List<SyntaxTree> CreateSyntaxTreeListFromSourceStringArray()
        {
            List<SyntaxTree> syntaxTreeList = new List<SyntaxTree>();
            if (m_SourceStringArray != null)
            {
                foreach (string sourceString in m_SourceStringArray)
                {
                    Log.Debug("sourceString is {0}", sourceString);
                    SyntaxTree syntaxTree = CSharpSyntaxTree.ParseText(sourceString);
                    syntaxTreeList.Add(syntaxTree);
                }
            }

            return syntaxTreeList;
        }

        private List<SyntaxTree> CreateSyntaxTreeListFromSourceStringArray(string[] sourceStringArray)
        {
            List<SyntaxTree> syntaxTreeList = new List<SyntaxTree>();
            if (sourceStringArray != null)
            {
                foreach (string sourceString in sourceStringArray)
                {
                    //Log.DebugFormat("sourceString is {0}", sourceString);
                    SyntaxTree syntaxTree = CSharpSyntaxTree.ParseText(sourceString);
                    syntaxTreeList.Add(syntaxTree);
                }
            }

            return syntaxTreeList;
        }

        private void ProcessGoClicked()
        {
            Log.Debug("starting ProcessGoClicked()");
            Log.Debug("m_Language is {0}", m_Language);
            Log.Debug("m_Type is {0}", m_Type);
            if (string.IsNullOrEmpty(m_Type))
            {
                ProcessSOAPType();
            }
            else
            {
                switch (m_Type)
                {
                    case "Custom":
                        m_TypeComboBox.Text = "Custom";
                        ProcessCustomType(true);
                        break;
                    case "Integrate":
                        m_TypeComboBox.Text = "Integrate";
                        ProcessIntegrateType();
                        break;
                    case "KlearNow":
                        m_TypeComboBox.Text = "KlearNow";
                        ProcessKlearNowType(true);
                        break;
                    case "NetDocs":
                        m_TypeComboBox.Text = "NetDocs";
                        ProcessNetDocsType();
                        break;
                    case "Open":
                        m_TypeComboBox.Text = "Open";
                        ProcessOpenType();
                        break;
                    case "PlatformCDS":
                        m_TypeComboBox.Text = "PlatformCDS";
                        ProcessPlatformCDSType();
                        break;
                    case "PlatformCDSCM":
                        m_TypeComboBox.Text = "PlatformCDSCM";
                        ProcessPlatformCDSCMType();
                        break;
                    case "PlatformDocument":
                        m_TypeComboBox.Text = "PlatformDocument";
                        ProcessPlatformDocumentType();
                        break;
                    case "PlatformRefData":
                        m_TypeComboBox.Text = "PlatformRefData";
                        ProcessPlatformRefDataType();
                        break;
                    case "PlatformSanctionLists":
                        m_TypeComboBox.Text = "PlatformSanctionLists";
                        ProcessPlatformSanctionListsType();
                        break;
                    case "PlatformTMS":
                        m_TypeComboBox.Text = "PlatformTMS";
                        ProcessPlatformTMSType();
                        break;
                    case "RAML":
                        m_TypeComboBox.Text = "RAML";
                        ProcessRAMLType(true);
                        break;
                    case "REST":
                        m_TypeComboBox.Text = "REST";
                        ProcessSwaggerType(true);
                        break;
                    case "SOAP":
                        m_TypeComboBox.Text = "SOAP";
                        ProcessSOAPType();
                        break;
                    case "Source":
                        m_TypeComboBox.Text = "Source";
                        ProcessSourceType();
                        break;
                    case "Swagger":
                        m_TypeComboBox.Text = "Swagger";
                        ProcessSwaggerType(true);
                        break;
                    case "Time":
                        m_TypeComboBox.Text = "Time";
                        ProcessTimeType();
                        break;
                    case "View":
                        m_TypeComboBox.Text = "View";
                        ProcessViewType();
                        break;
                    default:
                        m_TypeComboBox.Text = "SOAP";
                        ProcessSOAPType();
                        break;
                }
            }
        }

        private void ProcessKlearNowType(bool refresh)
        {
            Log.Debug("starting ProcessKlearNowType()");
            try
            {
                /* Force loading System.Net libraries */
                System.Net.Http.HttpClient httpClient = new();
                httpClient.GetStringAsync("http://www.google.com");

                /* Force loading the Newtonsoft library */
                JObject jObject = JObject.Parse("{}");
                this.Controls.Remove(m_ViewRichTextBox);
                Log.Debug("m_Language is {0}", m_Language);
                Log.Debug("m_Type is {0}", m_Type);
                m_ClientCredentialDomain = m_DomainTextBox.Text;
                Log.Debug("m_ClientCredentialDomain is {0}", m_ClientCredentialDomain);
                m_ClientCredentialUserName = m_UsernameTextBox.Text;
                Log.Debug("m_ClientCredentialUserName is {0}", m_ClientCredentialUserName);
                m_ClientCredentialPassword = m_PasswordTextBox.Text;
                Log.Debug("m_ClientCredentialPassword is {0}", m_ClientCredentialPassword);
                m_ServiceDescriptionURL = m_ServiceDescriptionURLTextBox.Text;
                Log.Debug("about to get REST description from " + m_ServiceDescriptionURL);
                this.Text = m_WindowTitle + " - retrieving REST description from " + m_ServiceDescriptionURL;
                if (refresh)
                {
                    Log.Debug("refresh is true");
                    RESTServiceDefinition klearNowServiceDefinition = new RESTServiceDefinition();
                    klearNowServiceDefinition.SetEndPoint(m_ServiceDescriptionURL);
                    Log.Debug("klearNowServiceDefinition EndPoint is {0}", klearNowServiceDefinition.GetEndPoint());
                    this.Text = m_WindowTitle + " - got REST service definition";
                    string userInterfaceString = @"namespace XCaseServiceClient
    {
        /* This file was generated by XCase.REST.SwaggerCSharpProxyGenerator */

        using System;
        using System.Collections.Generic;
        using System.Threading.Tasks;
        using System.Net;
        using System.Net.Http;
        using System.Net.Http.Headers;
        using System.Reflection;
        using System.Runtime.Serialization;
        using System.Text;
        using System.Web;
        using log4net;
        using Newtonsoft.Json;
        using Newtonsoft.Json.Converters;
        using Newtonsoft.Json.Linq;
        using XCase.REST.ProxyGenerator;
        using XCase.REST.ProxyGenerator.Proxy;

        /// <summary>
        /// Web Proxy interface for User operations
        /// </summary>
        public interface IUserProxy
        {
            public void logout();
        }
    }";
                    string shipmentInterfaceString = @"namespace XCaseServiceClient
    {
        /* This file was generated by XCase.REST.SwaggerCSharpProxyGenerator */

        using System;
        using System.Collections.Generic;
        using System.Threading.Tasks;
        using System.Net;
        using System.Net.Http;
        using System.Net.Http.Headers;
        using System.Reflection;
        using System.Runtime.Serialization;
        using System.Text;
        using System.Web;
        using log4net;
        using Newtonsoft.Json;
        using Newtonsoft.Json.Converters;
        using Newtonsoft.Json.Linq;
        using XCase.REST.ProxyGenerator;
        using XCase.REST.ProxyGenerator.Proxy;

        /// <summary>
        /// Web Proxy interface for User operations
        /// </summary>
        public interface IShipmentProxy
        {
            public string CreateShipment(Shipment shipment);
        }
    }";
                    string userProxyString = @"namespace XCaseServiceClient {
    /* This file was generated by XCase.REST.SwaggerCSharpProxyGenerator */

                    using System;
                    using System.Collections.Generic;
                    using System.Threading.Tasks;
                    using System.Net;
                    using System.Net.Http;
                    using System.Net.Http.Headers;
                    using System.Reflection;
                    using System.Runtime.Serialization;
                    using System.Text;
                    using System.Web;
                    using log4net;
                    using Newtonsoft.Json;
                    using Newtonsoft.Json.Converters;
                    using Newtonsoft.Json.Linq;
                    using XCase.REST.ProxyGenerator;
                    using XCase.REST.ProxyGenerator.Proxy;

    /// <summary>
    /// Web Proxy for user operations
    /// </summary>
        public class UserProxy : KlearNowProxy, IUserProxy
        {

            private static readonly ILogger Log = (new LoggerFactory()).CreateLogger(MethodBase.GetCurrentMethod().DeclaringType);

            public UserProxy(Uri baseUrl) : base(baseUrl)
            {
                _username = ""Admin"";
                _password = ""password"";
                _tenantId = ""domain"";
            }

            public UserProxy(Uri baseUrl, string username, string password, string domain) : base(baseUrl, username, password, domain)
            {
                _username = username;
                _password = password;
                _tenantId = domain;
            }

            public void logout()
            {
                Log.Debug(""starting logout()"");
                string url = string.Empty;
                using (HttpClient apiClient = BuildHttpClient())
                {
                    string requestURL = string.Format(""{0}{1}"", apiClient.BaseAddress, url);
                    HttpRequestMessage httpRequestMessage = new HttpRequestMessage() { RequestUri = new Uri(requestURL), Method = System.Net.Http.HttpMethod.Get };
                    httpRequestMessage.Headers.Accept.Add(MediaTypeWithQualityHeaderValue.Parse(""application/json""));
                    httpRequestMessage.Headers.Authorization = new AuthenticationHeaderValue(""kxToken"", token);
                    HttpResponseMessage response = apiClient.SendAsync(httpRequestMessage).Result;
                    if (response.IsSuccessStatusCode)
                    {
                        string content = response.Content.ReadAsStringAsync().Result;
                    }
                    else
                    {
                        throw new Exception(""Failure invoking method: "" + response.StatusCode + "":"" + response.Content.ReadAsStringAsync().Result);
                    }
                }
            }
       }
    }";
                    string shipmentProxyString = @"namespace XCaseServiceClient {
    /* This file was generated by XCase.REST.SwaggerCSharpProxyGenerator */

                    using System;
                    using System.Collections.Generic;
                    using System.Threading.Tasks;
                    using System.Net;
                    using System.Net.Http;
                    using System.Net.Http.Headers;
                    using System.Reflection;
                    using System.Runtime.Serialization;
                    using System.Text;
                    using System.Web;
                    using log4net;
                    using Newtonsoft.Json;
                    using Newtonsoft.Json.Converters;
                    using Newtonsoft.Json.Linq;
                    using XCase.REST.ProxyGenerator;
                    using XCase.REST.ProxyGenerator.Proxy;

    /// <summary>
    /// Web Proxy for shipment operations
    /// </summary>
        public class ShipmentProxy : KlearNowProxy, IShipmentProxy
        {

            private static readonly ILogger Log = (new LoggerFactory()).CreateLogger(MethodBase.GetCurrentMethod().DeclaringType);

            public ShipmentProxy(Uri baseUrl) : base(baseUrl)
            {
                _username = ""Admin"";
                _password = ""password"";
                _tenantId = ""domain"";
            }

            public ShipmentProxy(Uri baseUrl, string username, string password, string domain) : base(baseUrl, username, password, domain)
            {
                _username = username;
                _password = password;
                _tenantId = domain;
            }

            public string CreateShipment(Shipment shipment)
            {
                Log.DebugFormat(""starting CreateShipment()"");
                string url = ""shipment"";
                using (HttpClient apiClient = BuildHttpClient())
                {
                    string requestURL = string.Format(""{0}{1}"", apiClient.BaseAddress.ToString(), url);
                    Log.DebugFormat(""requestURL is {0}"", requestURL);
                    HttpRequestMessage httpRequestMessage = new HttpRequestMessage() { RequestUri = new Uri(requestURL), Method = System.Net.Http.HttpMethod.Get };
                    Log.DebugFormat(""created httpRequestMessage"");
                    httpRequestMessage.Headers.Accept.Add(MediaTypeWithQualityHeaderValue.Parse(""application/json""));
                    Log.DebugFormat(""set Accept header"");
                    httpRequestMessage.Headers.Authorization = new AuthenticationHeaderValue(""kxToken"", token);
                    Log.DebugFormat(""set Authorization header"");
                    HttpResponseMessage response = apiClient.SendAsync(httpRequestMessage).Result;
                    Log.DebugFormat(""got response"");
                    if (response != null)
                    {
                        if (response.IsSuccessStatusCode)
                        {
                            Log.DebugFormat(""got response {0}"", response.IsSuccessStatusCode);
                            string content = response.Content.ReadAsStringAsync().Result;
                            return content;
                        }
                        else
                        {
                            Log.WarnFormat(""response is not success"");
                            throw new Exception(""Failure invoking method: "" + response.StatusCode + "":"" + response.Content.ReadAsStringAsync().Result);
                        }
                    }
                    else
                    {
                        Log.WarnFormat(""response is null"");
                        return null;
                    }
                }
            }
       }
    }";
                    string shipmentClassString = @"namespace XCaseServiceClient
    {
        /* This file was generated by XCase.REST.SwaggerCSharpProxyGenerator */

        using System;
        using System.Collections.Generic;
        using System.Threading.Tasks;
        using System.Net;
        using System.Net.Http;
        using System.Net.Http.Headers;
        using System.Reflection;
        using System.Runtime.Serialization;
        using System.Text;
        using System.Web;
        using log4net;
        using Newtonsoft.Json;
        using Newtonsoft.Json.Converters;
        using Newtonsoft.Json.Linq;
        using XCase.REST.ProxyGenerator;
        using XCase.REST.ProxyGenerator.Proxy;

        /// <summary>
        /// Shipment object
        /// </summary>
        public class Shipment
        {
            public string supplierEmail { get; set; }
            public string shipmentId { get; set; }
            public string houseBolNumber { get; set; }
            public int departureDate { get; set; }
            public int arrivalDate { get; set; }
            public string portOfLadingCode { get; set; }
            public string portOfUnladingCode { get; set; }
            public string supplierActorId { get; set; }
            public string sellerActorId { get; set; }
            public string manufacturerActorId { get; set; }
        }
    }";
                    List<string> proxyList = new List<string>();
                    proxyList.Add("ShipmentProxy");
                    proxyList.Add("UserProxy");
                    klearNowServiceDefinition.SetProxyClasses(proxyList);
                    string[] sourceStringArray = new string[] { shipmentClassString, shipmentInterfaceString, shipmentProxyString, userInterfaceString, userProxyString };
                    klearNowServiceDefinition.SetSourceStrings(sourceStringArray);
                    if (sourceStringArray != null)
                    {
                        foreach (string sourceString in sourceStringArray)
                        {
                            Log.Debug("sourceString is {0}", sourceString);
                        }
                    }

                    m_ServicesComboBox.DataSource = klearNowServiceDefinition.GetProxyClasses();
                    Log.Debug("set m_ServicesComboBox datasource");
                    if (klearNowServiceDefinition.GetProxyClasses().Contains<string>(((string)m_ServicesComboBox.SelectedItem)))
                    {
                        m_ServicesComboBox.SelectedItem = klearNowServiceDefinition.GetProxyClasses().First<string>(pc => pc == ((string)m_ServicesComboBox.SelectedItem));
                    }
                    else
                    {
                        m_ServicesComboBox.SelectedItem = klearNowServiceDefinition.GetProxyClasses().First<string>();
                    }

                    Log.Debug("selected item is {0}", m_ServicesComboBox.SelectedItem);
                    List<SyntaxTree> syntaxTreeList = CreateSyntaxTreeListFromSourceStringArray(sourceStringArray);
                    string assemblyName = Path.GetRandomFileName();
                    Log.Debug("assemblyName is {0}", assemblyName);
                    List<MetadataReference> metadataReferenceList = CreateMetadataReferenceList();
                    Log.Debug("created metadataReferenceList");
                    CSharpCompilation cSharpCompilation = CSharpCompilation.Create(assemblyName, syntaxTrees: syntaxTreeList, references: metadataReferenceList, options: new CSharpCompilationOptions(OutputKind.DynamicallyLinkedLibrary));
                    Log.Debug("created cSharpCompilation");
                    m_Assembly = CreateAssemblyFromCSharpCompilation(cSharpCompilation);
                    Log.Debug("created assembly");
                    string endpoint = klearNowServiceDefinition.GetEndPoint();
                    Log.Debug("endpoint is {0}", endpoint);
                    Uri endpointUri = new Uri(klearNowServiceDefinition.GetEndPoint());
                    Log.Debug("endpointUri is {0}", endpointUri);
                    object[] args = new object[] { endpointUri, m_ClientCredentialUserName, m_ClientCredentialPassword, m_ClientCredentialDomain };
                    Log.Debug("Namespace is {0}", restApiProxySettingsEndPoint.Namespace);
                    string proxyClass = string.Format("{0}.{1}", restApiProxySettingsEndPoint.Namespace, m_ServicesComboBox.SelectedItem);
                    m_RESTServiceClient = m_Assembly.CreateInstance(proxyClass, false, BindingFlags.CreateInstance, null, args, null, null);
                    if (m_RESTServiceClient != null)
                    {
                        NetworkCredential networkCredential = new NetworkCredential(m_ClientCredentialUserName, m_ClientCredentialPassword, m_ClientCredentialDomain);
                        ((SwaggerProxy)m_RESTServiceClient).ClientCredentials = networkCredential;
                        if (m_ProxyEnable)
                        {
                            ((SwaggerProxy)m_RESTServiceClient).Proxy = new WebProxy(m_ProxyAddress, m_ProxyPort);
                        }

                        Log.Debug("set client credentials");
                    }
                    else
                    {
                        Log.Debug("m_RESTServiceClient is null");
                    }

                    Log.Debug("about to re-render service control");
                    RerenderServiceControl(m_RESTServiceClient);
                }
                else
                {
                    Log.Debug("refresh is true");
                }
 
            }
            catch (Exception e)
            {
                Log.Debug("exception thrown: " + e.Message);
                if (!m_Starting)
                {
                    MessageBox.Show("Exception thrown: " + e.Message);
                }
            }
        }

        private void ProcessIntegrateType()
        {
            throw new NotImplementedException();
        }

        private void ProcessNetDocsType()
        {
            throw new NotImplementedException();
        }

        private void ProcessOpenType()
        {
            throw new NotImplementedException();
        }

        private void ProcessPlatformCDSType()
        {
            throw new NotImplementedException();
        }

        private void ProcessPlatformCDSCMType()
        {
            throw new NotImplementedException();
        }

        private void ProcessPlatformDocumentType()
        {
            throw new NotImplementedException();
        }

        private void ProcessPlatformRefDataType()
        {
            throw new NotImplementedException();
        }

        private void ProcessPlatformSanctionListsType()
        {
            throw new NotImplementedException();
        }

        private void ProcessPlatformTMSType()
        {
            throw new NotImplementedException();
        }

        private void ProcessRAMLType(bool refresh)
        {
            Log.Debug("starting ProcessRAMLType()");
            try
            {
                /* Force loading the Newtonsoft library */
                JObject jObject = JObject.Parse("{}");
                this.Controls.Remove(m_ViewRichTextBox);
                Log.Debug("m_Language is {0}", m_Language);
                Log.Debug("m_Type is {0}", m_Type);
                m_ClientCredentialDomain = m_DomainTextBox.Text;
                Log.Debug("m_ClientCredentialDomain is {0}", m_ClientCredentialDomain);
                m_ClientCredentialUserName = m_UsernameTextBox.Text;
                Log.Debug("m_ClientCredentialUserName is {0}", m_ClientCredentialUserName);
                m_ClientCredentialPassword = m_PasswordTextBox.Text;
                Log.Debug("m_ClientCredentialPassword is {0}", m_ClientCredentialPassword);
                m_ServiceDescriptionURL = m_ServiceDescriptionURLTextBox.Text;
                Log.Debug("about to get REST description from " + m_ServiceDescriptionURL);
                this.Text = m_WindowTitle + " - retrieving REST description from " + m_ServiceDescriptionURL;
                if (refresh)
                {
                    Log.Debug("refresh is true");
                    CSharpProxyGenerator restProxyGenerator = null;
                    this.Controls.Remove(m_MethodsTabControl);
                    if (!string.IsNullOrEmpty(m_Language) && m_Language == "Java")
                    {
                        m_SwaggerProxyGenerator = new SwaggerJavaProxyGenerator();
                        restApiProxySettingsEndPoint = new RESTApiProxySettingsEndPoint("Java");
                    }
                    else
                    {
                        restProxyGenerator = new RAMLCSharpProxyGenerator();
                        restApiProxySettingsEndPoint = new RESTApiProxySettingsEndPoint("CSharp", "CustomBaseProxy");
                        restApiProxySettingsEndPoint.Accept = "application/json";
                    }

                    restApiProxySettingsEndPoint.Url = m_ServiceDescriptionURL;
                    Log.Debug("m_ServiceDescriptionURL is {0}", m_ServiceDescriptionURL);
                    RESTApiProxySettingsEndPoint[] endpoints = new RESTApiProxySettingsEndPoint[] { restApiProxySettingsEndPoint };
                    restServiceDefinition = restProxyGenerator.GenerateSourceString(endpoints);
                    Log.Debug("restServiceDefinition EndPoint is {0}", restServiceDefinition.GetEndPoint());
                    this.Text = m_WindowTitle + " - got REST service definition";
                    m_SourceStringArray = restServiceDefinition.GetSourceStrings();
                    if (m_SourceStringArray != null)
                    {
                        foreach (string sourceString in m_SourceStringArray)
                        {
                            Log.Debug("sourceString is {0}", sourceString);
                        }
                    }

                    Log.Debug("endpoint is {0}", restServiceDefinition.GetEndPoint());
                    if (string.IsNullOrEmpty(m_Language) || m_Language == "CSharp")
                    {
                        m_ServicesComboBox.DataSource = restServiceDefinition.GetProxyClasses();
                        if (restServiceDefinition.GetProxyClasses().Contains<string>(((string)m_ServicesComboBox.SelectedItem)))
                        {
                            m_ServicesComboBox.SelectedItem = restServiceDefinition.GetProxyClasses().First<string>(pc => pc == ((string)m_ServicesComboBox.SelectedItem));
                        }
                        else
                        {
                            m_ServicesComboBox.SelectedItem = restServiceDefinition.GetProxyClasses().First<string>();
                        }

                        List<SyntaxTree> syntaxTreeList = CreateSyntaxTreeListFromSourceStringArray();
                        string assemblyName = Path.GetRandomFileName();
                        Log.Debug("assemblyName is {0}", assemblyName);
                        List<MetadataReference> metadataReferenceList = CreateMetadataReferenceList();
                        Log.Debug("created metadataReferenceList");
                        CSharpCompilation cSharpCompilation = CSharpCompilation.Create(assemblyName, syntaxTrees: syntaxTreeList, references: metadataReferenceList, options: new CSharpCompilationOptions(OutputKind.DynamicallyLinkedLibrary));
                        Log.Debug("created cSharpCompilation");
                        m_Assembly = CreateAssemblyFromCSharpCompilation(cSharpCompilation);
                        Log.Debug("created assembly");
                        string endpoint = restServiceDefinition.GetEndPoint();
                        Log.Debug("endpoint is {0}", endpoint);
                        Uri endpointUri = new Uri(restServiceDefinition.GetEndPoint());
                        Log.Debug("endpointUri is {0}", endpointUri);
                        object[] args = new object[] { endpointUri };
                        string proxyClass = string.Format("{0}.{1}", restApiProxySettingsEndPoint.Namespace, m_ServicesComboBox.SelectedItem);
                        if (m_Assembly != null)
                        {
                            m_RESTServiceClient = m_Assembly.CreateInstance(proxyClass, false, BindingFlags.CreateInstance, null, args, null, null);
                            if (m_RESTServiceClient != null)
                            {
                                NetworkCredential networkCredential = new NetworkCredential(m_ClientCredentialUserName, m_ClientCredentialPassword, m_ClientCredentialDomain);
                                ((SwaggerProxy)m_RESTServiceClient).ClientCredentials = networkCredential;
                                if (m_ProxyEnable)
                                {
                                    ((SwaggerProxy)m_RESTServiceClient).Proxy = new WebProxy(m_ProxyAddress, m_ProxyPort);
                                }

                                Log.Debug("set client credentials");
                                Log.Debug("about to re-render service control");
                                RerenderServiceControl(m_RESTServiceClient);
                            }
                            else
                            {
                                Log.Debug("m_RESTServiceClient is null");
                                MessageBox.Show("m_RESTServiceClient is null. Check the log for compilation errors");
                            }
                        }
                        else
                        {
                            Log.Debug("m_Assembly is null");
                            MessageBox.Show("m_Assembly is null. Check the log for compilation errors");
                        }
                    }
                    else if (m_Language == "Java")
                    {
                        MessageBox.Show("Finished generating classes.");
                    }

                    Log.Debug("{0}", restServiceDefinition.GetEndPoint());
                }
                else
                {
                    /* refresh is false, but service or proxy class has changed */
                    Log.Debug("refresh is false");
                    if (string.IsNullOrEmpty(m_Language) || m_Language == "CSharp")
                    {
                        Log.Debug("m_Language is null or empty or CSharp");
                        string restServiceDefinitionEndpoint = restServiceDefinition.GetEndPoint();
                        Log.Debug("restServiceDefinitionEndpoint is {0}", restServiceDefinitionEndpoint);
                        object[] args = new object[] { new Uri(restServiceDefinitionEndpoint) };
                        Log.Debug("created args array");
                        string proxyClass = string.Format("{0}.{1}", restApiProxySettingsEndPoint.Namespace, m_ServicesComboBox.SelectedItem);
                        m_RESTServiceClient = m_Assembly.CreateInstance(proxyClass, false, BindingFlags.CreateInstance, null, args, null, null);
                        if (m_RESTServiceClient != null)
                        {
                            NetworkCredential networkCredential = new NetworkCredential(m_ClientCredentialUserName, m_ClientCredentialPassword, m_ClientCredentialDomain);
                            ((SwaggerProxy)m_RESTServiceClient).ClientCredentials = networkCredential;
                            Log.Debug("set client credentials");
                        }
                        else
                        {
                            Log.Debug("m_RESTServiceClient is null");
                        }

                        Log.Debug("about to re-render service control");
                        RerenderServiceControl(m_RESTServiceClient);
                    }

                    Log.Debug("{0}", restServiceDefinition.GetEndPoint());
                }

                Log.Debug("finishing ProcessRAMLType()");
            }
            catch (AggregateException ae)
            {
                Log.Debug("aggregate exception thrown: " + ae.Message);
                if (!m_Starting)
                {
                    MessageBox.Show("Aggregate exception thrown: " + ae.Message);
                }
            }
            catch (Exception e)
            {
                Log.Debug("exception thrown: " + e.Message);
                if (!m_Starting)
                {
                    MessageBox.Show("Exception thrown: " + e.Message);
                }
            }
        }

        private void ProcessSwaggerType(bool refresh)
        {
            Log.Debug("starting ProcessSwaggerType()");
            try
            {
                /* Force loading the Newtonsoft library */
                JObject jObject = JObject.Parse("{}");
                /* Force loading the System.Runtime.Serialization library */
                codeLanguages = languages.CSharp;
                this.Controls.Remove(m_ViewRichTextBox);
                Log.Debug("m_Language is {0}", m_Language);
                Log.Debug("m_Type is {0}", m_Type);
                m_ClientCredentialDomain = m_DomainTextBox.Text;
                Log.Debug("m_ClientCredentialDomain is {0}", m_ClientCredentialDomain);
                m_ClientCredentialUserName = m_UsernameTextBox.Text;
                Log.Debug("m_ClientCredentialUserName is {0}", m_ClientCredentialUserName);
                m_ClientCredentialPassword = m_PasswordTextBox.Text;
                Log.Debug("m_ClientCredentialPassword is {0}", m_ClientCredentialPassword);
                m_ServiceDescriptionURL = m_ServiceDescriptionURLTextBox.Text;
                Log.Debug("about to get REST description from " + m_ServiceDescriptionURL);
                this.Text = m_WindowTitle + " - retrieving REST description from " + m_ServiceDescriptionURL;
                if (refresh)
                {
                    Log.Debug("refresh is true");
                    this.Controls.Remove(m_MethodsTabControl);
                    if (!string.IsNullOrEmpty(m_Language) && m_Language == "Java")
                    {
                        m_SwaggerProxyGenerator = new SwaggerJavaProxyGenerator();
                        restApiProxySettingsEndPoint = new RESTApiProxySettingsEndPoint("Java");
                    }
                    else
                    {
                        m_SwaggerProxyGenerator = new SwaggerCSharpProxyGenerator();
                        restApiProxySettingsEndPoint = new RESTApiProxySettingsEndPoint("CSharp");
                    }

                    restApiProxySettingsEndPoint.Url = m_ServiceDescriptionURL;
                    Log.Debug("m_ServiceDescriptionURL is {0}", m_ServiceDescriptionURL);
                    RESTApiProxySettingsEndPoint[] endpoints = new RESTApiProxySettingsEndPoint[] { restApiProxySettingsEndPoint };
                    restServiceDefinition = m_SwaggerProxyGenerator.GenerateSourceString(endpoints);
                    Log.Debug("swaggerServiceDefinition EndPoint is {0}", restServiceDefinition.GetEndPoint());
                    this.Text = m_WindowTitle + " - got REST service definition";
                    m_SourceStringArray = restServiceDefinition.GetSourceStrings();
                    if (m_SourceStringArray != null)
                    {
                        foreach (string sourceString in m_SourceStringArray)
                        {
                            Log.Debug("sourceString is {0}", sourceString);
                        }
                    }

                    if (string.IsNullOrEmpty(m_Language) || m_Language == "CSharp")
                    {
                        if (restServiceDefinition.GetProxyClasses().Contains<string>(((string)m_ServicesComboBox.SelectedItem)))
                        {
                            m_ServicesComboBox.SelectedItem = restServiceDefinition.GetProxyClasses().First<string>(pc => pc == ((string)m_ServicesComboBox.SelectedItem));
                        }
                        else
                        {
                            m_ServicesComboBox.SelectedItem = restServiceDefinition.GetProxyClasses().First<string>();
                        }

                        m_ServicesComboBox.DataSource = restServiceDefinition.GetProxyClasses();
                        List<SyntaxTree> syntaxTreeList = CreateSyntaxTreeListFromSourceStringArray();
                        string assemblyName = Path.GetRandomFileName();
                        Log.Debug("assemblyName is {0}", assemblyName);
                        List<MetadataReference> metadataReferenceList = CreateMetadataReferenceList();
                        Log.Debug("created metadataReferenceList");
                        CSharpCompilation cSharpCompilation = CSharpCompilation.Create(assemblyName, syntaxTrees: syntaxTreeList, references: metadataReferenceList, options: new CSharpCompilationOptions(OutputKind.DynamicallyLinkedLibrary));
                        Log.Debug("created cSharpCompilation");
                        m_Assembly = CreateAssemblyFromCSharpCompilation(cSharpCompilation);
                        Log.Debug("created assembly");
                        if (m_Assembly != null)
                        {
                            object[] args = new object[] { new Uri(restServiceDefinition.GetEndPoint()) };
                            string proxyClass = string.Format("{0}.{1}", restApiProxySettingsEndPoint.Namespace, m_ServicesComboBox.SelectedItem);
                            m_RESTServiceClient = m_Assembly.CreateInstance(proxyClass, false, BindingFlags.CreateInstance, null, args, null, null);
                            if (m_RESTServiceClient != null)
                            {
                                NetworkCredential networkCredential = new NetworkCredential(m_ClientCredentialUserName, m_ClientCredentialPassword, m_ClientCredentialDomain);
                                ((SwaggerProxy)m_RESTServiceClient).ClientCredentials = networkCredential;
                                if (m_ProxyEnable)
                                {
                                    ((SwaggerProxy)m_RESTServiceClient).Proxy = new WebProxy(m_ProxyAddress, m_ProxyPort);
                                }

                                Log.Debug("set client credentials");
                            }
                            else
                            {
                                Log.Debug("m_RESTServiceClient is null");
                            }

                            Log.Debug("about to re-render service control");
                            RerenderServiceControl(m_RESTServiceClient);
                        }
                        else
                        {
                            Log.Debug("m_Assembly is null");
                            MessageBox.Show("m_Assembly is null. Check the log for compilation errors");
                        }
                    }
                    else if (m_Language == "Java")
                    {
                        MessageBox.Show("Finished generating classes.");
                    }

                    Log.Debug("REST service definiton endpoint is {0}", restServiceDefinition.GetEndPoint());
                }
                else
                {
                    /* refresh is false, but service or proxy class has changed */
                    Log.Debug("refresh is false");
                    if (string.IsNullOrEmpty(m_Language) || m_Language == "CSharp")
                    {
                        string endpoint = restServiceDefinition.GetEndPoint();
                        Log.Debug("endpoint is {0}", endpoint);
                        object[] args = new object[] { new Uri(endpoint) };
                        string proxyClass = string.Format("{0}.{1}", restApiProxySettingsEndPoint.Namespace, m_ServicesComboBox.SelectedItem);
                        m_RESTServiceClient = m_Assembly.CreateInstance(proxyClass, false, BindingFlags.CreateInstance, null, args, null, null);
                        if (m_RESTServiceClient != null)
                        {
                            NetworkCredential networkCredential = new NetworkCredential(m_ClientCredentialUserName, m_ClientCredentialPassword, m_ClientCredentialDomain);
                            ((SwaggerProxy)m_RESTServiceClient).ClientCredentials = networkCredential;
                            Log.Debug("set client credentials");
                        }
                        else
                        {
                            Log.Debug("m_RESTServiceClient is null");
                        }

                        Log.Debug("about to re-render service control");
                        RerenderServiceControl(m_RESTServiceClient);
                    }

                    Log.Debug("REST service definiton endpoint is {0}", restServiceDefinition.GetEndPoint());
                }

                Log.Debug("finishing ProcessSwaggerType()");
            }
            catch (AggregateException ae)
            {
                Log.Debug("aggregate exception thrown: " + ae.Message);
                if (!m_Starting)
                {
                    MessageBox.Show("Aggregate exception thrown: " + ae.Message);
                }
            }
            catch (Exception e)
            {
                Log.Debug("exception thrown: " + e.Message);
                if (!m_Starting)
                {
                    MessageBox.Show("Exception thrown: " + e.Message);
                }
            }
        }

        private void ProcessSourceType()
        {
            Log.Debug("starting ProcessSourceType()");
            try
            {
                this.Text = m_WindowTitle + " - using source service definition";
                List<string> argStringList = new List<string>();
                List<string> dllStringList = new List<string>();
                string package = "";
                List<string> proxyStringList = new List<string>();
                List<string> sourceStringList = new List<string>();
                string[] lineList = File.ReadAllLines("Source.txt");
                foreach (string line in lineList)
                {
                    if (line.StartsWith("arg:"))
                    {
                        argStringList.Add(line.Substring(4));
                    }
                    else if (line.StartsWith("dll:"))
                    {
                        dllStringList.Add(line.Substring(4));
                    }
                    else if (line.StartsWith("file:"))
                    {
                        string sourceString = GetStringFromFile(line.Substring(5));
                        sourceStringList.Add(sourceString);
                    }
                    else if (line.StartsWith("package:"))
                    {
                        package = line.Substring(8);
                    }
                    else if (line.StartsWith("proxy:"))
                    {
                        proxyStringList.Add(line.Substring(6));
                    }
                    else
                    {
                        Log.Warning("unrecognized prefix");
                    }
                }

                m_SourceStringArray = sourceStringList.ToArray<string>();
                if (m_SourceStringArray != null)
                {
                    foreach (string sourceString in m_SourceStringArray)
                    {
                        Log.Debug("sourceString is {0}", sourceString);
                    }
                }

                this.Controls.Remove(m_MethodsTabControl);
                if (!string.IsNullOrEmpty(m_Language) && m_Language == "Java")
                {
                    m_SwaggerProxyGenerator = new SwaggerJavaProxyGenerator();
                    restApiProxySettingsEndPoint = new RESTApiProxySettingsEndPoint("Java");
                }
                else
                {
                    m_SwaggerProxyGenerator = new SwaggerCSharpProxyGenerator();
                    restApiProxySettingsEndPoint = new RESTApiProxySettingsEndPoint("CSharp", "CustomBaseProxy");
                    restApiProxySettingsEndPoint.Accept = "application/json";
                }

                restApiProxySettingsEndPoint.Url = m_ServiceDescriptionURL;
                Log.Debug("m_ServiceDescriptionURL is {0}", m_ServiceDescriptionURL);
                RESTApiProxySettingsEndPoint[] endpoints = new RESTApiProxySettingsEndPoint[] { restApiProxySettingsEndPoint };
                this.Text = m_WindowTitle + " - got source service definition";
                Log.Debug("endpoint is {0}", restServiceDefinition.GetEndPoint());
                if (string.IsNullOrEmpty(m_Language) || m_Language == "CSharp")
                {
                    m_ServicesComboBox.DataSource = proxyStringList;
                    if (proxyStringList.Contains<string>(((string)m_ServicesComboBox.SelectedItem)))
                    {
                        m_ServicesComboBox.SelectedItem = proxyStringList.First<string>(pc => pc == ((string)m_ServicesComboBox.SelectedItem));
                    }
                    else
                    {
                        m_ServicesComboBox.SelectedItem = proxyStringList.First<string>();
                    }

                    List<SyntaxTree> syntaxTreeList = CreateSyntaxTreeListFromSourceStringArray();
                    string assemblyName = Path.GetRandomFileName();
                    Log.Debug("assemblyName is {0}", assemblyName);
                    List<MetadataReference> metadataReferenceList = CreateMetadataReferenceList();
                    Log.Debug("created metadataReferenceList");
                    CSharpCompilation cSharpCompilation = CSharpCompilation.Create(assemblyName, syntaxTrees: syntaxTreeList, references: metadataReferenceList, options: new CSharpCompilationOptions(OutputKind.DynamicallyLinkedLibrary));
                    Log.Debug("created cSharpCompilation");
                    m_Assembly = CreateAssemblyFromCSharpCompilation(cSharpCompilation);
                    Log.Debug("created assembly");
                    object[] args = argStringList.ToArray();
                    string proxyClass = string.Format("{0}.{1}", restApiProxySettingsEndPoint.Namespace, m_ServicesComboBox.SelectedItem);
                    m_RESTServiceClient = m_Assembly.CreateInstance(proxyClass, false, BindingFlags.CreateInstance, null, args, null, null);
                    if (m_RESTServiceClient != null)
                    {
                        NetworkCredential networkCredential = new NetworkCredential(m_ClientCredentialUserName, m_ClientCredentialPassword, m_ClientCredentialDomain);
                        ((SwaggerProxy)m_RESTServiceClient).ClientCredentials = networkCredential;
                        if (m_ProxyEnable)
                        {
                            ((SwaggerProxy)m_RESTServiceClient).Proxy = new WebProxy(m_ProxyAddress, m_ProxyPort);
                        }

                        Log.Debug("set client credentials");
                    }
                    else
                    {
                        Log.Debug("m_RESTServiceClient is null");
                    }

                    Log.Debug("about to re-render service control");
                    RerenderServiceControl(m_RESTServiceClient);
                }
            }
            catch (Exception e)
            {
                Log.Warning("exception processing source type: " + e.Message);
                MessageBox.Show("Exception thrown: " + e.Message);
            }
        }

        public string GetStringFromFile(string fileName)
        {
            return File.ReadAllText(fileName);
        }

        private void ProcessTimeType()
        {
            throw new NotImplementedException();
        }

        private void ProcessViewType()
        {
            throw new NotImplementedException();
        }

        private void ProcessSOAPType()
        {
            throw new NotImplementedException();
        }

        public void RenderServiceControl(object client)
        {
            Log.Debug("starting RenderServiceControl()");
            if (client == null)
            {
                Log.Debug("client is null");
                return;
            }

            if (client != null)
            {
                Type clientType = client.GetType();
                Log.Debug("client type is " + clientType);
                Type baseClientType = clientType.BaseType;
                Log.Debug("base client type is " + baseClientType);
                if (baseClientType != null)
                {
                    Type baseBaseClientType = baseClientType.BaseType;
                    Log.Debug("base base client type is " + baseBaseClientType);
                }

                Type[] clientInterfaceTypes = clientType.GetInterfaces();
                foreach (Type clientInterfaceType in clientInterfaceTypes)
                {
                    Log.Debug("client interface type is " + clientInterfaceType);
                }

                this.Controls.Remove(m_MethodsTabControl);
                m_MethodsTabControl = new TabControl();
                this.Controls.Add(m_MethodsTabControl);
                m_MethodsTabControl.Location = new Point(0, m_TopPanelHeight + m_TabPanelBuffer);
                m_MethodsTabControl.Multiline = multiline;
                m_MethodsTabControl.Name = "MethodsTabControl";
                m_MethodsTabControl.Size = new Size(m_WindowWidth, m_WindowHeight - (m_TopPanelHeight + m_TabPanelBuffer));
                m_MethodsTabControl.BackColor = Color.LightSteelBlue;
                /* Get public methods of Web service */
                MethodInfo[] allPublicMethodInfoArray = clientType.GetMethods(BindingFlags.Public | BindingFlags.Instance | BindingFlags.DeclaredOnly);
                IEnumerable<MethodInfo> methodArray = allPublicMethodInfoArray.Where(m => !m.IsConstructor).OrderBy(m => m.Name);
                foreach (MethodInfo methodInfo in methodArray)
                {
                    Log.Debug("** Method is " + methodInfo.Name + " **");
                    this.Text = m_WindowTitle + " - rendering " + methodInfo.Name;
                    TabPage methodTabPage = CreateXCaseTabPageForMethod(client, methodInfo);
                    Log.Debug("created methodTabPage");
                    m_MethodsTabControl.Controls.Add(methodTabPage);
                    Log.Debug("added methodTabPage");
                    methodTabPage.Select();
                    Log.Debug("selected methodTabPage");
                    this.Text = m_WindowTitle + " - rendered " + methodInfo.Name;
                }

                m_MethodsTabControl.ResumeLayout(true);
            }

            this.Text = m_WindowTitle;
            Log.Debug("finishing RenderServiceControl()");
        }

        private TabPage CreateXCaseTabPageForMethod(object client, MethodInfo methodInfo)
        {
            XCaseTabPage methodXCaseTabPage = new XCaseTabPage(methodInfo.Name);
            methodXCaseTabPage.Client = client;
            methodXCaseTabPage.MethodInfo = methodInfo;
            methodXCaseTabPage.Size = new Size(m_WindowWidth, m_WindowHeight - (m_TopPanelHeight + m_TopPanelBuffer + m_TabPanelBuffer));
            methodXCaseTabPage.AutoScroll = true;
            methodXCaseTabPage.Enter += new EventHandler(MethodTab_Entered);
            return methodXCaseTabPage;
        }

        private void MethodTab_Entered(object sender, EventArgs e)
        {
            Log.Debug("starting MethodTab_Entered()");
            TableLayoutPanel methodTableLayoutPanel = CreateTableLayoutPanelForMethod(((XCaseTabPage)sender));
            ((XCaseTabPage)sender).Controls.Add(methodTableLayoutPanel);
        }

        private TableLayoutPanel CreateTableLayoutPanelForMethod(XCaseTabPage xcaseTabPage)
        {
            TableLayoutPanel methodTableLayoutPanel = new TableLayoutPanel();
            object client = xcaseTabPage.Client;
            MethodInfo methodInfo = xcaseTabPage.MethodInfo;
            methodTableLayoutPanel.CellBorderStyle = TableLayoutPanelCellBorderStyle.Inset;
            methodTableLayoutPanel.ColumnStyles.Add(new ColumnStyle(SizeType.Percent, 50));
            methodTableLayoutPanel.ColumnStyles.Add(new ColumnStyle(SizeType.Percent, 50));
            methodTableLayoutPanel.Size = new Size(xcaseTabPage.Width - m_MethodPanelInset, xcaseTabPage.Height - m_MethodPanelInset);
            methodTableLayoutPanel.RowCount = 1;
            methodTableLayoutPanel.ColumnCount = 2;
            methodTableLayoutPanel.AutoScroll = true;
            TableLayoutPanel requestTableLayoutPanel = new TableLayoutPanel();
            TableLayoutPanel resultTableLayoutPanel = new TableLayoutPanel();
            requestTableLayoutPanel.AutoSize = true;
            requestTableLayoutPanel.CellBorderStyle = TableLayoutPanelCellBorderStyle.Single;
            ParameterInfo[] parameterInfoArray = methodInfo.GetParameters();
            object[] parameterValueArray = new object[parameterInfoArray.Length];
            requestTableLayoutPanel.RowCount = parameterInfoArray.Length + 3;
            requestTableLayoutPanel.ColumnCount = 3;
            requestTableLayoutPanel.AutoScroll = true;
            requestTableLayoutPanel.ControlRemoved += delegate (object sender, ControlEventArgs cea)
            {
                requestTableLayoutPanel.PerformLayout();
            };
            Label headerLabel = new Label();
            headerLabel.Text = "Enter data here:";
            requestTableLayoutPanel.Controls.Add(headerLabel, 0, 0);
            Type returnType = methodInfo.ReturnType;
            Log.Debug("returnType is {0}", returnType);
            Log.Debug("returnType name is {0}", returnType.Name);
            Label returnLabel = new Label();
            returnLabel.AutoSize = true;
            string returnLabelText = "Returns: " + returnType.Name;
            Log.Debug("returnLabelText is {0}", returnLabelText);
            returnLabel.Text = returnLabelText;
            requestTableLayoutPanel.Controls.Add(returnLabel, 1, 0);
            for (int i = 0; i < parameterInfoArray.Length; i++)
            {
                ParameterInfo parameterInfo = parameterInfoArray[i];
                Log.Debug("parameter name is {0}", parameterInfo.Name);
                if (parameterInfo.IsOut)
                {
                    continue;
                }

                /* For each input parameter, create a parameter object */
                Log.Debug("parameter type is {0}", parameterInfo.ParameterType);
                object parameterObject = ObjectFactory.CreateDefaultObject(parameterInfo.ParameterType);
                if (parameterObject != null)
                {
                    Log.Debug("parameter object type is {0}", parameterObject.GetType());
                }

                parameterValueArray[i] = parameterObject;
                /* Created a parameter object and added to parameter value array. */
                RenderParameterObject(i, requestTableLayoutPanel, parameterInfo, parameterInfoArray, parameterObject, parameterValueArray);
            }

            Log.Debug("finished laying out parameter");
            TextBox resultTextBox = new TextBox();
            resultTextBox.Size = new Size(100, 100);
            resultTextBox.Multiline = true;
            resultTextBox.ScrollBars = ScrollBars.Both;
            resultTextBox.Lines = new string[10];
            resultTextBox.WordWrap = true;
            resultTextBox.Dock = DockStyle.Fill;
            XCaseButton submitButton = new XCaseButton();
            submitButton.Text = XCaseServiceClient.Properties.Resources.Submit;
            submitButton.Method = methodInfo;
            submitButton.MouseClick += delegate (object o, MouseEventArgs mev)
            {
                Log.Debug("Submit button clicked");
                this.Text = m_WindowTitle + " - invoking " + methodInfo.Name;
                Cursor.Current = Cursors.WaitCursor;
                Log.Debug("parameter value array length is {0}", parameterValueArray.Length);
                for (int i = 0; i < parameterValueArray.Length; i++)
                {
                    UpdateParameterValue(parameterValueArray[i]);
                    if (parameterValueArray[i] is XCaseDateTime)
                    {
                        parameterValueArray[i] = ((XCaseDateTime)parameterValueArray[i]).GetDateTime();
                    }
                }

                Log.Debug("finished assembling parameter value array");
                LogParameterValueArray(parameterValueArray);
                try
                {
                    string methodName = submitButton.Method.Name;
                    Log.Debug("methodName is " + methodName);
                    MethodInfo clientMethodInfo = null;
                    if (!parameterValueArray.Any<object>(parameter => parameter == null))
                    {
                        /* If the parameterValueArray does not contain nulls, then can use parameterValueArray to get method signature */
                        Type[] typeArray = GetTypeArrayFromObjectArray(parameterValueArray);
                        Log.Debug("got Type array");
                        clientMethodInfo = client.GetType().GetMethod(methodName, typeArray);
                    }
                    else
                    {
                        /* parameterValueArray does contain nulls, so use name to get method */
                        clientMethodInfo = client.GetType().GetMethod(methodName);
                    }

                    Log.Debug("method is " + clientMethodInfo.Name);
                    ServicePointManager.Expect100Continue = false;
                    Log.Debug("about to invoke method " + methodName);
                    object resultObject = submitButton.Method.Invoke(client, parameterValueArray);
                    Log.Debug("invoked method " + methodName);
                    resultTextBox.Text = string.Format("Success invoking {0}!", methodName);
                    if (resultObject != null)
                    {
                        Log.Debug("result object type is {0}", resultObject.GetType());
                    }
                    else
                    {
                        Log.Debug("result object is null");
                        if (HasOutParameter(parameterInfoArray))
                        {
                            resultObject = GetOutParameter(parameterInfoArray, parameterValueArray);
                            if (resultObject != null)
                            {
                                Log.Debug("result object type is {0}", resultObject.GetType());
                            }
                        }
                    }

                    resultTableLayoutPanel = ObjectRenderer.RenderResultObject(resultObject, m_MaxArrayLength);
                    Log.Debug("rendered result object");
                    Cursor.Current = Cursors.Default;
                    resultTableLayoutPanel.AutoScroll = true;
                    resultTableLayoutPanel.AutoSize = true;
                    resultTableLayoutPanel.Dock = DockStyle.Fill;
                    resultTableLayoutPanel.PerformLayout();
                    resultTableLayoutPanel.Visible = true;
                    Log.Debug("finished displaying result table panel layout");
                    methodTableLayoutPanel.Controls.Clear();
                    methodTableLayoutPanel.Controls.Add(requestTableLayoutPanel, 0, 0);
                    methodTableLayoutPanel.Controls.Add(resultTableLayoutPanel, 1, 0);
                    methodTableLayoutPanel.AutoSize = true;
                    methodTableLayoutPanel.PerformLayout();
                    methodTableLayoutPanel.Visible = true;
                    this.Text = m_WindowTitle + " - invoked " + methodInfo.Name;
                    Log.Debug("finished try block of Submit button mouse click");
                }
                catch (Exception e)
                {
                    resultTextBox.Text = e.Message + "\n" + e.InnerException;
                    Log.Debug("method invoked with exception " + e.Message);
                }

                Log.Debug("finishing Submit button mouse click");
            };
            requestTableLayoutPanel.Controls.Add(submitButton, 0, parameterInfoArray.Length + 1);
            requestTableLayoutPanel.SetColumnSpan(submitButton, 3);
            submitButton.Dock = DockStyle.Fill;
            /* Added button to panel */
            requestTableLayoutPanel.Controls.Add(resultTextBox, 0, parameterInfoArray.Length + 2);
            requestTableLayoutPanel.SetColumnSpan(resultTextBox, 3);
            requestTableLayoutPanel.PerformLayout();
            requestTableLayoutPanel.Visible = true;
            methodTableLayoutPanel.Controls.Add(requestTableLayoutPanel, 0, 0);
            requestTableLayoutPanel.Dock = DockStyle.Fill;
            methodTableLayoutPanel.Controls.Add(resultTableLayoutPanel, 1, 0);
            methodTableLayoutPanel.AutoSize = true;
            methodTableLayoutPanel.PerformLayout();
            methodTableLayoutPanel.Visible = true;
            return methodTableLayoutPanel;
        }

        private static object GetOutParameter(ParameterInfo[] parameterInfoArray, object[] parameterValueArray)
        {
            Log.Debug("starting GetOutParameter()");
            int index = 0;
            foreach (ParameterInfo parameterInfo in parameterInfoArray)
            {
                if (parameterInfo.IsOut)
                {
                    return parameterValueArray[index];
                }

                index++;
            }

            return null;
        }

        private bool HasOutParameter(ParameterInfo[] parameterInfoArray)
        {
            Log.Debug("starting HasOutParameter()");
            foreach (ParameterInfo parameterInfo in parameterInfoArray)
            {
                if (parameterInfo.IsOut)
                {
                    return true;
                }
            }

            return false;
        }

        private Type[] GetTypeArrayFromObjectArray(object[] objectArray)
        {
            Log.Debug("starting GetTypeArrayFromObjectArray()");
            List<Type> typeList = new List<Type>();
            foreach (object objectObject in objectArray)
            {
                if (objectObject != null)
                {
                    Type type = objectObject.GetType();
                    typeList.Add(type);
                }
                else
                {
                    typeList.Add(null);
                }
            }

            return typeList.ToArray<Type>();
        }

        private static void LogParameterValueArray(object[] parameterValueArray)
        {
            Log.Debug("starting LogParameterValueArray()");
            Log.Debug("parameterValueArray length is {0}", parameterValueArray.Length);
            for (int i = 0; i < parameterValueArray.Length; i++)
            {
                object parameterValue = parameterValueArray[i];
                if (parameterValue != null)
                {
                    Log.Debug("parameterValue is not null");
                    Log.Debug("parameterValue type is {0}", parameterValue.GetType());
                    if (ObjectFactory.IsArrayType(parameterValue.GetType()))
                    {
                        Log.Debug("parameterValue type is array");
                        if (((Array)parameterValue).Length > 0)
                        {
                            object firstArrayValue = (parameterValue as Array).GetValue(0);
                            if (firstArrayValue != null)
                            {
                                Log.Debug("firstArrayValue type is {0}", firstArrayValue.GetType());
                                Log.Debug("firstArrayValue is {0}", firstArrayValue);
                            }
                        }
                    }
                    else if (ObjectFactory.IsListType(parameterValue.GetType()))
                    {
                        Log.Debug("parameterValue type is list");
                        if ((parameterValue as IList).Count > 0)
                        {
                            object firstListValue = (parameterValue as IList)[0];
                            if (firstListValue != null)
                            {
                                Log.Debug("firstListValue type is {0}", firstListValue.GetType());
                                Log.Debug("firstListValue is {0}", firstListValue);
                            }
                        }
                    }
                    else
                    {
                        Log.Debug("parameterValue is {0}", parameterValue);
                    }
                }
                else
                {
                    Log.Debug("parameterValue is null");
                }
            }

            Log.Debug("finishing LogParameterValueArray()");
        }

        private void UpdateParameterValue(object parameterValue)
        {
            Log.Debug("starting UpdateParameterValue()");
            if (parameterValue != null)
            {
                Log.Debug("parameter type is {0}", parameterValue.GetType());
                if (parameterValue.GetType() == typeof(XmlDocument))
                {
                    /* Just print it out to check it is the right document */
                    Log.Debug("parameter type is XmlDocument");
                    Log.Debug(((XmlDocument)parameterValue).OuterXml);
                }

                if (parameterValue.GetType() == typeof(XmlElement))
                {
                    /* Just print it out to check it is the right element */
                    Log.Debug("parameter type is XmlElement");
                    Log.Debug(((XmlElement)parameterValue).InnerXml);
                }

                if (parameterValue.GetType() != null && parameterValue.GetType().IsArray)
                {
                    Log.Debug("parameter is array");
                    ArrayList tempArrayList = new ArrayList(0);
                    Log.Debug("parameter value array length is {0}", ((Array)parameterValue).Length);
                    for (int j = 0; j < ((Array)parameterValue).Length; j++)
                    {
                        Log.Debug(j + " value: " + ((Array)parameterValue).GetValue(j));
                        if (((Array)parameterValue).GetValue(j) != null)
                        {
                            Log.Debug("parameter value is not null at " + j);
                            tempArrayList.Add(((Array)parameterValue).GetValue(j));
                        }
                    }

                    Log.Debug("length of array list is " + tempArrayList.Count);
                    Array truncatedArray = Array.CreateInstance(parameterValue.GetType().GetElementType(), tempArrayList.Count);
                    for (int k = 0; k < tempArrayList.Count; k++)
                    {
                        truncatedArray.SetValue(tempArrayList[k], k);
                    }

                    parameterValue = truncatedArray;
                    Log.Debug("set parameter to truncated array");
                }
            }
            else
            {
                Log.Debug("parameterValue is null");
            }
        }

        private void RenderParameterObject(int i, TableLayoutPanel requestTableLayoutPanel, ParameterInfo parameterInfo, ParameterInfo[] parameterInfoArray, object parameterObject, object[] parameterValueArray)
        {
            Log.Debug("starting RenderParameterObject()");
            Label parameterLabel = new Label();
            parameterLabel.AutoSize = true;
            parameterLabel.Text = string.Format("{0} ({1})", parameterInfoArray[i].Name, parameterInfo.ParameterType.Name);
            requestTableLayoutPanel.Controls.Add(parameterLabel, 0, i + 1);
            if (ObjectFactory.IsArrayType(parameterInfo.ParameterType))
            {
                /* Parameter is array type */
                Log.Debug("parameter is array type");
                ObjectRenderer.RenderArray(requestTableLayoutPanel, parameterValueArray, parameterObject, i);
            }
            else if (ObjectFactory.IsListType(parameterInfo.ParameterType))
            {
                /* Parameter is list type */
                Log.Debug("parameter is list type");
                ObjectRenderer.RenderList(requestTableLayoutPanel, parameterValueArray, parameterObject, i);
            }
            else if (ObjectFactory.IsBooleanType(parameterInfo.ParameterType))
            {
                /* Parameter is boolean type */
                Log.Debug("parameter is boolean type");
                if (parameterObject != null)
                {
                    ((bool)parameterObject).RenderBoolean(requestTableLayoutPanel, parameterValueArray, i);
                }
                else
                {
                    (false).RenderBoolean(requestTableLayoutPanel, parameterValueArray, i);
                }
            }
            else if (ObjectFactory.IsByteType(parameterInfo.ParameterType))
            {
                /* Parameter is Byte type */
                Log.Debug("parameter is Byte type");
                if (parameterObject != null)
                {
                    ((Byte)parameterObject).RenderByte(requestTableLayoutPanel, parameterValueArray, i);
                }
                else
                {
                    ((Byte)Byte.MinValue).RenderByte(requestTableLayoutPanel, parameterValueArray, i);
                }
            }
            else if (ObjectFactory.IsCharType(parameterInfo.ParameterType))
            {
                /* Parameter is Char type */
                Log.Debug("parameter is Char type");
                if (parameterObject != null)
                {
                    ((Char)parameterObject).RenderChar(requestTableLayoutPanel, parameterValueArray, i);
                }
                else
                {
                    ((Char)Char.MinValue).RenderChar(requestTableLayoutPanel, parameterValueArray, i);
                }
            }
            else if (ObjectFactory.IsDateTimeType(parameterInfo.ParameterType))
            {
                /* Parameter is datetime type */
                Log.Debug("parameter is datetime type");
                if (parameterObject != null)
                {
                    if (parameterObject is DateTime)
                    {
                        ((DateTime)parameterObject).RenderDateTime(requestTableLayoutPanel, parameterValueArray, i);
                    }
                    else if (parameterObject is XCaseDateTime)
                    {
                        ((XCaseDateTime)parameterObject).Date.RenderDateTime(requestTableLayoutPanel, parameterValueArray, i);
                    }
                }
                else
                {
                    ((DateTime)DateTime.MinValue).RenderDateTime(requestTableLayoutPanel, parameterValueArray, i);
                }
            }
            else if (ObjectFactory.IsDecimalType(parameterInfo.ParameterType))
            {
                /* Parameter is Decimal type */
                Log.Debug("parameter is Decimal type");
                if (parameterObject != null)
                {
                    ((Decimal)parameterObject).RenderDecimal(requestTableLayoutPanel, parameterValueArray, i);
                }
                else
                {
                    ((Decimal)Decimal.MinValue).RenderDecimal(requestTableLayoutPanel, parameterValueArray, i);
                }
            }
            else if (ObjectFactory.IsDoubleType(parameterInfo.ParameterType))
            {
                /* Parameter is Double type */
                Log.Debug("parameter is Double type");
                if (parameterObject != null)
                {
                    ((Double)parameterObject).RenderDouble(requestTableLayoutPanel, parameterValueArray, i);
                }
                else
                {
                    ((Double)Double.MinValue).RenderDouble(requestTableLayoutPanel, parameterValueArray, i);
                }
            }
            else if (ObjectFactory.IsEnumType(parameterInfo.ParameterType))
            {
                /* Parameter is enum type */
                Log.Debug("parameter is enum type");
                ((System.Enum)parameterObject).RenderEnum(requestTableLayoutPanel, parameterValueArray, i);
            }
            else if (ObjectFactory.IsIntegerType(parameterInfo.ParameterType))
            {
                /* Parameter is integer type */
                Log.Debug("parameter is integer type");
                if (parameterObject != null)
                {
                    ((int)parameterObject).RenderInteger(requestTableLayoutPanel, parameterValueArray, i);
                }
                else
                {
                    ((int)Int32.MinValue).RenderInteger(requestTableLayoutPanel, parameterValueArray, i);
                }
            }
            else if (ObjectFactory.IsSingleType(parameterInfo.ParameterType))
            {
                /* Parameter is Single type */
                Log.Debug("parameter is Single type");
                if (parameterObject != null)
                {
                    ((Single)parameterObject).RenderSingle(requestTableLayoutPanel, parameterValueArray, i);
                }
                else
                {
                    ((Single)Single.MinValue).RenderSingle(requestTableLayoutPanel, parameterValueArray, i);
                }
            }
            else if (ObjectFactory.IsStringType(parameterInfo.ParameterType))
            {
                /* Parameter is string type */
                Log.Debug("parameter is string type");
                ((string)parameterObject).RenderString(requestTableLayoutPanel, parameterValueArray, i);
            }
            else if (ObjectFactory.IsTimeSpanType(parameterInfo.ParameterType))
            {
                /* Parameter is TimeSpan type */
                Log.Debug("parameter is TimeSpan type");
                ((TimeSpan)parameterObject).RenderTimeSpan(requestTableLayoutPanel, parameterValueArray, i);
            }
            else if (ObjectFactory.IsXmlDocumentType(parameterInfo.ParameterType))
            {
                /* Parameter is XML document type */
                Log.Debug("parameter is XML document type");
                ObjectRenderer.RenderXmlDocument(requestTableLayoutPanel, parameterValueArray, parameterObject, i);
            }
            else if (ObjectFactory.IsXmlNodeType(parameterInfo.ParameterType))
            {
                Log.Debug("parameter is XML node type");
                ObjectRenderer.RenderXmlNode(requestTableLayoutPanel, parameterValueArray, parameterObject, i);
            }
            else
            {
                /* Parameter is not standard type */
                Log.Debug("parameter is not standard type");
                if (ObjectFactory.IsNullableType(parameterInfo.ParameterType))
                {
                    /* Parameter is not array and is nullable type */
                    Log.Debug("parameter is not array and is nullable type");
                    Type parameterUnderlyingFieldType = Nullable.GetUnderlyingType(parameterInfo.ParameterType);
                    if (parameterInfo.IsOut)
                    {
                        parameterUnderlyingFieldType = parameterInfo.ParameterType.GetElementType();
                    }

                    if (ObjectFactory.IsStringType(parameterUnderlyingFieldType))
                    {
                        /* Parameter is not array and is nullable type and underlying type is string */
                        //Log.Debug("parameter is not array and is nullable type and underlying type is string");
                        XCaseTextBox textBox = new XCaseTextBox();
                        textBox.Index = i;
                        textBox.FieldType = parameterInfo.ParameterType;
                        requestTableLayoutPanel.Controls.Add(textBox, 1, i + 1);
                        textBox.TextChanged += delegate (object sender, EventArgs e)
                        {
                            string value = textBox.Text;
                            int index = textBox.Index;
                            Type fieldType = textBox.FieldType;
                            Type underlyingFieldType = Nullable.GetUnderlyingType(fieldType);
                            //Log.Debug("about to create object for underlying string");
                            object underlyingParameterObject = ObjectFactory.CreateObjectFromTypeAndValue(underlyingFieldType, value);
                            parameterObject = Activator.CreateInstance(fieldType, underlyingParameterObject);
                            parameterValueArray[index] = parameterObject;
                        };
                    }
                    else if (ObjectFactory.IsIntegerType(parameterUnderlyingFieldType))
                    {
                        /* Parameter is not array and is nullable type and underlying type is integer */
                        //Log.Debug("parameter is not array and is nullable type and underlying type is integer");
                        XCaseTextBox textBox = new XCaseTextBox();
                        textBox.Index = i;
                        textBox.FieldType = parameterInfo.ParameterType;
                        requestTableLayoutPanel.Controls.Add(textBox, 1, i + 1);
                        textBox.TextChanged += delegate (object sender, EventArgs e)
                        {
                            string value = textBox.Text;
                            int index = textBox.Index;
                            Type fieldType = textBox.FieldType;
                            Type underlyingFieldType = Nullable.GetUnderlyingType(fieldType);
                            //Log.Debug("about to create object for underlying string");
                            object underlyingParameterObject = ObjectFactory.CreateObjectFromTypeAndValue(underlyingFieldType, value);
                            parameterObject = Activator.CreateInstance(fieldType, underlyingParameterObject);
                            parameterValueArray[index] = parameterObject;
                        };
                    }
                    else if (ObjectFactory.IsBooleanType(parameterUnderlyingFieldType))
                    {
                        /* Parameter is not array and is nullable type and underlying type is boolean */
                        Log.Debug("parameter is not array and is nullable type and underlying type is boolean");
                        XCaseCheckBox checkBox = new XCaseCheckBox();
                        checkBox.Index = i;
                        checkBox.FieldType = parameterInfo.ParameterType;
                        requestTableLayoutPanel.Controls.Add(checkBox, 1, i + 1);
                        checkBox.CheckedChanged += delegate (object sender, EventArgs e)
                        {
                            bool value = checkBox.Checked;
                            int index = checkBox.Index;
                            Type fieldType = checkBox.FieldType;
                            Type underlyingFieldType = Nullable.GetUnderlyingType(fieldType);
                            //Log.Debug("about to create object for underlying boolean");
                            object underlyingParameterObject = ObjectFactory.CreateObjectFromTypeAndValue(underlyingFieldType, value);
                            parameterObject = Activator.CreateInstance(fieldType, underlyingParameterObject);
                            parameterValueArray[index] = parameterObject;
                        };
                    }
                    else if (parameterUnderlyingFieldType.IsEnum)
                    {
                        /* Parameter is not array and is nullable type and underlying type is enum */
                        Log.Debug("parameter is not array and is nullable type and underlying type is enum");
                        XCaseComboBox comboBox = new XCaseComboBox();
                        comboBox.Index = i;
                        comboBox.FieldType = parameterInfo.ParameterType;
                        comboBox.DataSource = System.Enum.GetValues(parameterUnderlyingFieldType);
                        requestTableLayoutPanel.Controls.Add(comboBox, 1, i + 1);
                        comboBox.SelectedIndexChanged += delegate (object sender, EventArgs e)
                        {
                            System.Enum value = (System.Enum)comboBox.SelectedValue;
                            int index = comboBox.Index;
                            Type fieldType = comboBox.FieldType;
                            Type underlyingFieldType = Nullable.GetUnderlyingType(fieldType);
                            //Log.Debug("about to create object for underlying enum");
                            object underlyingParameterObject = ObjectFactory.CreateObjectFromTypeAndValue(underlyingFieldType, value);
                            parameterObject = Activator.CreateInstance(fieldType, underlyingParameterObject);
                            parameterValueArray[index] = parameterObject;
                        };
                    }
                    else if (ObjectFactory.IsLongType(parameterUnderlyingFieldType))
                    {
                        /* Parameter is not array and is nullable type and underlying type is long */
                        Log.Debug("parameter is not array and is nullable type and underlying type is long");
                        XCaseTextBox textBox = new XCaseTextBox();
                        textBox.Index = i;
                        textBox.FieldType = parameterInfo.ParameterType;
                        requestTableLayoutPanel.Controls.Add(textBox, 1, i + 1);
                        textBox.TextChanged += delegate (object sender, EventArgs e)
                        {
                            string value = textBox.Text;
                            int index = textBox.Index;
                            Type fieldType = textBox.FieldType;
                            Type underlyingFieldType = Nullable.GetUnderlyingType(fieldType);
                            object underlyingParameterObject = ObjectFactory.CreateInt64ObjectFromTypeAndValue(underlyingFieldType, value);
                            parameterObject = underlyingParameterObject;
                            parameterValueArray[index] = parameterObject;
                        };
                    }
                    else
                    {
                        /* Parameter is not array and is nullable type and underlying type is none of string, boolean, or enum */
                        Log.Debug("parameter is not array and is nullable type and underlying type is none of string, boolean, or enum");
                    }
                }
                else if (parameterInfo.ParameterType.IsEnum)
                {
                    /* Parameter is not array and is not nullable and is enum */
                    //Log.Debug("parameter is not array and is not nullable and is enum");
                    XCaseComboBox comboBox = new XCaseComboBox();
                    comboBox.Index = i;
                    comboBox.FieldType = parameterInfo.ParameterType;
                    comboBox.DataSource = System.Enum.GetValues(parameterInfo.ParameterType);
                    requestTableLayoutPanel.Controls.Add(comboBox, 1, i + 1);
                    comboBox.SelectedIndexChanged += delegate (object sender, EventArgs e)
                    {
                        System.Enum value = (System.Enum)comboBox.SelectedValue;
                        int index = comboBox.Index;
                        Type fieldType = comboBox.FieldType;
                        Log.Debug("about to create object for not nullable enum");
                        parameterObject = ObjectFactory.CreateObjectFromTypeAndValue(fieldType, value);
                        parameterValueArray[index] = parameterObject;
                    };
                }
                else
                {
                    /* Parameter is a complex type */
                    Log.Debug("parameter is a complex type");
                    if (parameterObject != null)
                    {
                        Log.Debug("parameter object is not null");
                        ObjectXMLRenderer.WriteXmlNodeToLog(ObjectXMLRenderer.RenderObject(null, parameterObject, parameterInfo.Name));
                        TableLayoutPanel propertyTableLayoutPanel = ObjectRenderer.RenderParameterObject(parameterObject);
                        requestTableLayoutPanel.Controls.Add(propertyTableLayoutPanel, 1, i + 1);
                    }
                    else
                    {
                        Log.Debug("parameter object is null");
                    }
                }
            }

            Button nullButton = new Button();
            requestTableLayoutPanel.Controls.Add(nullButton, 2, i + 1);
            nullButton.Text = XCaseServiceClient.Properties.Resources.Null;
            nullButton.MouseClick += delegate (object o, MouseEventArgs mev)
            {
                parameterObject = null;
                parameterValueArray[i] = null;
            };
        }

        public void RerenderServiceControl(object client)
        {
            Log.Debug("starting RerenderServiceControl()");
            if (client == null)
            {
                Log.Debug("client is null");
                return;
            }

            RenderServiceControl(client);
            Log.Debug("finishing RerenderServiceControl()");
        }

        public bool AcceptAllCertifications(object sender, X509Certificate certification, X509Chain chain, SslPolicyErrors sslPolicyErrors)
        {
            return true;
        }
    }

    public class XCaseButton : Button
    {
        public int ArrayLength { get; set; }
        public MethodInfo Method { get; set; }
        public object PropertyObject { get; set; }
        public TableLayoutPanel ObjectTableLayoutPanel { get; set; }
        public Type FieldType { get; set; }
    }

    public class XCaseComboBox : ComboBox
    {
        public int Index { get; set; }
        public Type FieldType { get; set; }
    }

    public class XCaseDateTimePicker : DateTimePicker
    {
        #region Logger Setup

        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly Serilog.ILogger Log = new LoggerConfiguration().ReadFrom.Configuration(new ConfigurationBuilder()
            .SetBasePath(Directory.GetCurrentDirectory())
            .AddJsonFile("appsettings.json", optional: false, reloadOnChange: true)
            .Build()).CreateLogger();
            
        #endregion

        public XCaseDateTimePicker()
        {
            Format = DateTimePickerFormat.Custom;
            CustomFormat = "MM/dd/yyyyThh:mm:ssTtt";
            Value = DateTime.Now;
        }

        public XCaseDateTimePicker(object dateTime)
        {
            //Log.Debug("starting XCaseDateTimePicker()");
            Format = DateTimePickerFormat.Custom;
            CustomFormat = "MM/dd/yyyyThh:mm:ssTtt";
            Log.Debug("object is {0}", dateTime);
            try
            {
                Value = (DateTime)dateTime;
            }
            catch (Exception)
            {
                Value = DateTime.Now;
            }

            Log.Debug("value is {0}", Value);
        }

        public int Index { get; set; }
        public Type FieldType { get; set; }
    }

    public class XCaseTimeSpanPicker : DateTimePicker
    {
        #region Logger Setup

        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly Serilog.ILogger Log = new LoggerConfiguration().ReadFrom.Configuration(new ConfigurationBuilder()
            .SetBasePath(Directory.GetCurrentDirectory())
            .AddJsonFile("appsettings.json", optional: false, reloadOnChange: true)
            .Build()).CreateLogger();

        public new TimeSpan Value;

        #endregion

        public XCaseTimeSpanPicker()
        {
            Format = DateTimePickerFormat.Custom;
            CustomFormat = "MM/dd/yyyyThh:mm:ssTtt";
            Value = TimeSpan.Zero;
        }

        public XCaseTimeSpanPicker(object timeSpan)
        {
            //Log.Debug("starting XCaseDateTimePicker()");
            Format = DateTimePickerFormat.Custom;
            CustomFormat = "MM/dd/yyyyThh:mm:ssTtt";
            Log.Debug("object is {0}", timeSpan);
            try
            {
                Value = (TimeSpan)timeSpan;
            }
            catch (Exception)
            {
                Value = TimeSpan.Zero;
            }

            Log.Debug("value is {0}", Value);
        }

        public int Index { get; set; }
        public Type FieldType { get; set; }
    }

    public class XCaseListBox : ListBox
    {
        public int Index { get; set; }
        public Type FieldType { get; set; }
    }

    public class XCaseTableLayoutPanel : TableLayoutPanel
    {
        public object PropertyObject { get; set; }
        public int Index { get; set; }
    }

    public class XCaseTextBox : TextBox
    {
        public int Index { get; set; }
        public Type FieldType { get; set; }
    }

    public class XCaseCheckBox : CheckBox
    {
        public int Index { get; set; }
        public Type FieldType { get; set; }
    }

    public class XCaseDateTime
    {
        public DateTime Date { get; set; }
        public TimeSpan TimeOfDay { get; set; }

        public XCaseDateTime()
        {
            Date = DateTime.Now.Date;
            TimeOfDay = DateTime.Now.TimeOfDay;
        }

        public XCaseDateTime(DateTime dateTime)
        {
            Date = dateTime.Date;
            TimeOfDay = dateTime.TimeOfDay;
        }

        public DateTime GetDateTime()
        {
            return new DateTime(Date.Year, Date.Month, Date.Day, TimeOfDay.Hours, TimeOfDay.Minutes, TimeOfDay.Seconds);
        }
    }

    public class XCaseTabPage : TabPage
    {
        public object Client { get; set; }
        public MethodInfo MethodInfo { get; set; }

        public XCaseTabPage(string text) : base(text)
        {

        }
    }

    public enum Language
    {
        CSharp,
        Java
    }

    public enum SourceType
    {
        SOAP,
        Swagger
    }
}
