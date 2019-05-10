namespace XCaseServiceClient
{
    using System;
    using System.CodeDom;
    using System.CodeDom.Compiler;
    using System.Collections;
    using System.Collections.Generic;
    using System.Collections.ObjectModel;
    using System.ComponentModel;
    using System.Data;
    using System.Drawing;
    using System.Drawing.Text;
    using System.IO;
    using System.Linq;
    using System.Net;
    using System.Net.Security;
    using System.Reflection;
    using System.Security.Cryptography.X509Certificates;
    using System.ServiceModel;
    using System.ServiceModel.Description;
    using System.ServiceModel.Security;
    using System.Text;
    using System.Web;
    using System.Web.Services.Description;
    using System.Web.Services.Protocols;
    using System.Windows.Forms;
    using System.Xml;
    using System.Xml.Linq;
    using System.Xml.Schema;
    using System.Xml.Serialization;
    using log4net;
    using Microsoft.CSharp;
    using Newtonsoft.Json;
    using NUnit.Framework;
    using XCase.ProxyGenerator;
    using XCase.REST.ProxyGenerator;
    using XCase.REST.ProxyGenerator.Generator;

    public partial class XCaseServiceClientForm : Form
    {
        #region Logger Setup

        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILog Log = log4net.LogManager.GetLogger(MethodBase.GetCurrentMethod().DeclaringType);

        #endregion

        bool m_OnPremise = true;
        bool m_ProxyEnable = false;
        bool m_Starting = true;
        public static bool multiline = true;
        public static Color productColor = Color.LightSkyBlue;
        public static Color labelBackgroundColor = Color.SlateGray;
        ComboBox m_LanguageComboBox = new ComboBox();
        ComboBox m_ServicesComboBox = new ComboBox();
        ComboBox m_TypeComboBox = new ComboBox();
        CompilerResults m_CompilerResults = null;
        EndpointAddress m_ServiceEndpoint = null;
        FontFamily m_FontFamily = new FontFamily("Arial");
        int m_MaxArrayLength = 10;
        int m_MethodPanelInset = 20;
        int m_ProxyPort = -1;
        int m_Timeout = 30;
        int m_TabPanelBuffer = 0;
        int m_TopPanelHeight = 60;
        int m_TopPanelBuffer = 60;
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
        string m_CurrentDirectory = Properties.Settings.Default.ServicesDirectory;
        string m_Language = "CSharp";
        string m_MessageCredentialType = "None";
        string m_ProxyAddress = null;
        string m_SecurityMode = "None";
        string m_SecurityProtocol = "Tls11";
        string m_ServiceDescriptionURL = null;
        string m_ServiceName = "Service Name";
        string m_Type = "SOAP";
        string m_WindowTitle = "XCase Web Service Client";
        string m_ServiceDescriptor = null;
        string[] m_References = new string[] { "System.dll", "System.ComponentModel.DataAnnotations.dll", "System.Core.dll", "System.Data.dll", "System.Net.dll", "System.Net.Http.dll", "System.Runtime.Serialization.dll", "System.ServiceModel.dll", "System.Web.dll", "System.Web.Services.dll", "System.Xml.dll" };
        string[] m_Services = new string[] { };
        string[] m_SourceStringArray = new string[] { };
        IProxyGenerator m_SwaggerProxyGenerator = new SwaggerCSharpProxyGenerator();
        RESTApiProxySettingsEndPoint m_SwaggerApiProxySettingsEndPoint = new RESTApiProxySettingsEndPoint("CSharp");
        IServiceDefinition m_SwaggerServiceDefinition = null;
        TabControl m_MethodsTabControl;
        TableLayoutPanel m_TopTableLayoutPanel;
        TextBox m_DomainTextBox = new TextBox();
        TextBox m_MaxArrayLengthTextBox = new TextBox();
        TextBox m_PasswordTextBox = new TextBox();
        TextBox m_ServiceDescriptionURLTextBox = new TextBox();
        TextBox m_TimeoutTextBox = new TextBox();
        TextBox m_UsernameTextBox = new TextBox();
        RichTextBox m_ViewRichTextBox = new RichTextBox();

        public XCaseServiceClientForm()
        {
            log4net.Config.XmlConfigurator.Configure();
            Log.DebugFormat("no arguments constructor");
            Initialize();
        }

        public XCaseServiceClientForm(string fileName)
        {
            log4net.Config.XmlConfigurator.Configure();
            Log.DebugFormat("fileName is {0}", fileName);
            if (File.Exists(fileName))
            {
                if (fileName.EndsWith("xsvc"))
                {
                    Log.DebugFormat("file exists and ends in xsvc");
                    try
                    {
                        Config config = XCaseServiceDescription.OpenFromFile(fileName);
                        Log.DebugFormat("created config from {0}", fileName);
                        SetServicePropertiesFromConfig(config);
                        Log.Debug("loaded service file");
                    }
                    catch (Exception exception)
                    {
                        Log.Warn("exception opening service file: " + exception.Message);
                        MessageBox.Show("Exception opening service file: " + exception.Message);
                    }

                    Initialize();
                }
                else if (fileName.EndsWith("wsdl"))
                {
                    Log.DebugFormat("file exists and ends in wsdl");
                    try
                    {
                        m_ServiceDescriptor = GetWSDLFromFile(fileName);
                        Log.DebugFormat("read file into m_WSDL");
                        m_Services = GetServicesFromFile(fileName);
                        Log.DebugFormat("got services from file");
                        m_ServicesComboBox.DataSource = m_Services;
                        m_ServicesComboBox.SelectedItem = m_ServiceName;
                        Log.Debug("about to get service client from file");
                        m_ServiceClient = GetServiceClientFromFile(fileName);
                        Assert.IsNotNull(m_ServiceClient);
                        Environment.Exit(0);
                    }
                    catch (Exception e)
                    {
                        Log.Debug("exception testing WSDL: " + e.Message);
                        Environment.Exit(1);
                    }
                }
                else
                {
                    Log.WarnFormat("unrecognized file extension");
                    Initialize();
                }
            }
            else
            {
                Log.WarnFormat("file does not exist");
                try
                {
                    Environment.Exit(0);
                }
                catch (Exception e)
                {
                    Log.Debug("exception exiting: " + e.Message);
                }
            }
        }

        private void Initialize()
        {
            Log.Debug("starting Initialize()");
            System.Net.ServicePointManager.SecurityProtocol = SecurityProtocolType.Ssl3 | SecurityProtocolType.Tls | SecurityProtocolType.Tls11 | SecurityProtocolType.Tls12;
            Rectangle workingRectangle = Screen.PrimaryScreen.WorkingArea;
            this.m_WindowWidth = workingRectangle.Width - m_WindowWidthIndent;
            this.m_WindowHeight = workingRectangle.Height - m_WindowHeightIndent;
            this.m_TopTableLayoutPanel = new TableLayoutPanel();
            this.SuspendLayout();
            this.AutoScroll = true;
            //
            // topTableLayoutPanel
            //
            this.m_TopTableLayoutPanel.Location = new Point(0, 0);
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
            MainMenu mainMenu = CreateMainMenu();
            this.Menu = mainMenu;
            StartPosition = FormStartPosition.CenterScreen;
            Log.Debug("about to initialize component");
            InitializeComponent();
            Log.Debug("initialized component");
            /* Code to dynamically generate UI */
            m_TopTableLayoutPanel.RowCount = 2;
            m_TopTableLayoutPanel.ColumnCount = 10;
            TableLayoutColumnStyleCollection tableLayoutColumnStyleCollection = m_TopTableLayoutPanel.ColumnStyles;
            TableLayoutRowStyleCollection tableLayoutRowStyleCollection = m_TopTableLayoutPanel.RowStyles;
            ColumnStyle urlColumnStyle = new ColumnStyle();
            urlColumnStyle.SizeType = SizeType.AutoSize;
            tableLayoutColumnStyleCollection.Add(urlColumnStyle);
            for (int i = 1; i < m_TopTableLayoutPanel.ColumnCount; i++)
            {
                ColumnStyle columnStyle = new ColumnStyle();
                columnStyle.SizeType = SizeType.Percent;
                switch(i)
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
                RowStyle rowStyle = new RowStyle();
                rowStyle.SizeType = SizeType.AutoSize;
                //rowStyle.Height = 50;
                tableLayoutRowStyleCollection.Add(rowStyle);
            }

            m_TopTableLayoutPanel.Controls.Add(m_TypeComboBox, 0, 0);
            Log.Debug("added m_TypeComboBox");
            m_TypeComboBox.DataSource = new string[] {"Integrate", "NetDocs", "Open", "PlatformCDS", "PlatformCDSCM", "PlatformDocument", "PlatformRefData", "PlatformSanctionLists", "PlatformTMS", "RAML", "REST", "SOAP", "Source", "Time", "View" };
            ComboBox.ObjectCollection typeObjectCollection = m_TypeComboBox.Items;
            if (typeObjectCollection.Contains(m_Type))
            {
                Log.DebugFormat("objectCollection contains {0}", m_Type);
                int index = typeObjectCollection.IndexOf(m_Type);
                m_TypeComboBox.SelectedIndex = index;
            }
            else
            {
                Log.DebugFormat("objectCollection does not contain {0}", m_Type);
                int index = typeObjectCollection.IndexOf("SOAP");
                Log.DebugFormat("index of SOAP is {0}", index);
                m_TypeComboBox.SelectedIndex = index;
            }

            m_TypeComboBox.SelectionChangeCommitted += delegate(object o, EventArgs ev)
            {
                Log.Debug("m_TypeComboBox SelectionChangeCommitted");
                m_Type = (string)m_TypeComboBox.SelectedItem;
                Log.DebugFormat("m_Type is {0}", m_Type);
            };
            m_ServiceDescriptionURLTextBox.Text = m_ServiceDescriptionURL;
            m_ServiceDescriptionURLTextBox.Width = 250;
            m_ServiceDescriptionURLTextBox.TextChanged += delegate(object o, EventArgs ev)
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
                Log.DebugFormat("languageObjectCollection contains {0}", m_Language);
                int index = languageObjectCollection.IndexOf(m_Language);
                m_LanguageComboBox.SelectedIndex = index;
            }
            else
            {
                Log.DebugFormat("languageObjectCollection does not contain {0}", m_Language);
                int index = languageObjectCollection.IndexOf("CSharp");
                Log.DebugFormat("index of CSharp is {0}", index);
                m_LanguageComboBox.SelectedIndex = index;
            }

            m_LanguageComboBox.SelectionChangeCommitted += delegate(object o, EventArgs ev)
            {
                Log.Debug("m_TypeComboBox SelectionChangeCommitted");
                m_Language = (string)m_LanguageComboBox.SelectedItem;
                Log.DebugFormat("m_Language is {0}", m_Language);
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

            m_ServicesComboBox.DataSourceChanged += delegate(object o, EventArgs ev)
            {
                m_ServicesComboBox.Refresh();
            };
            m_ServicesComboBox.SelectionChangeCommitted += delegate(object o, EventArgs ev)
            {
                Log.Debug("m_ServicesComboBox SelectionChangeCommitted");
                m_ServiceName = (string)m_ServicesComboBox.SelectedItem;
                Log.DebugFormat("m_ServiceName is {0}", m_ServiceName);
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
            m_MaxArrayLengthTextBox.TextChanged += delegate(object o, EventArgs ev)
            {
                try
                {
                    m_MaxArrayLength = Convert.ToInt32(m_MaxArrayLengthTextBox.Text);
                }
                catch (Exception e)
                {
                    Log.DebugFormat("max array length format incorrect {0}", e.Message);
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
            goButton.Anchor = AnchorStyles.None;
            goButton.Text = XCaseServiceClient.Properties.Resources.Go;
            m_TopTableLayoutPanel.Controls.Add(goButton, 9, 0);
            Log.Debug("added goButton");
            goButton.MouseClick += delegate(object o, MouseEventArgs mev)
            {
                Log.Debug("Go clicked");
                ProcessGoClicked();
            };
            Button fileButton = new Button();
            fileButton.Anchor = AnchorStyles.None;
            fileButton.Text = XCaseServiceClient.Properties.Resources.File;
            fileButton.MouseClick += delegate(object o, MouseEventArgs mev)
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
                    Log.DebugFormat("fileName is {0}", fileName);
                    ProcessWSDLFile(fileName);
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

        private void ProcessWSDLFile(string fileName)
        {
            m_CurrentDirectory = Path.GetDirectoryName(fileName);
            m_ServiceDescriptionURLTextBox.Text = fileName;
            StreamReader streamReader = new StreamReader(fileName);
            m_ServiceDescriptor = streamReader.ReadToEnd();
            streamReader.Close();
            Log.DebugFormat("read file into m_WSDL");
            m_Services = GetServicesFromFile(fileName);
            Log.DebugFormat("got services from file");
            m_ServicesComboBox.DataSource = m_Services;
            m_ServicesComboBox.SelectedItem = m_ServiceName;
            Log.Debug("about to get service client from file");
            m_ServiceClient = GetServiceClientFromFile(fileName);
            Log.Debug("got service client from file");
            this.Text = m_WindowTitle + " - got service client";
            m_Timeout = Convert.ToInt32(m_TimeoutTextBox.Text);
            Log.Debug("about to re-render service control");
            RerenderServiceControl(m_ServiceClient);
        }

        private void ProcessIntegrateType()
        {
            Log.Debug("starting ProcessIntegrateType()");
            this.Controls.Remove(m_ViewRichTextBox);
            Log.DebugFormat("m_Language is {0}", m_Language);
            Log.DebugFormat("m_Type is {0}", m_Type);
            m_ClientCredentialDomain = m_DomainTextBox.Text;
            Log.DebugFormat("m_ClientCredentialDomain is {0}", m_ClientCredentialDomain);
            m_ClientCredentialUserName = m_UsernameTextBox.Text;
            Log.DebugFormat("m_ClientCredentialUserName is {0}", m_ClientCredentialUserName);
            m_ClientCredentialPassword = m_PasswordTextBox.Text;
            Log.DebugFormat("m_ClientCredentialPassword is {0}", m_ClientCredentialPassword);
            m_ServiceDescriptionURL = m_ServiceDescriptionURLTextBox.Text;
            Log.DebugFormat("m_ServiceDescriptionURL is {0}", m_ServiceDescriptionURL);
            this.Text = m_WindowTitle + " - retrieving Swagger description from " + m_ServiceDescriptionURL;
            try
            {
                ISwaggerProxy swaggerProxy = new IntegrateSwaggerProxy(new Uri(m_ServiceDescriptionURL), m_ClientCredentialUserName, m_ClientCredentialPassword, m_ClientCredentialDomain);
                string swaggerDocument = swaggerProxy.GetSwaggerDocument();
                Log.DebugFormat("swaggerDocument is {0}", swaggerDocument);
                if (!string.IsNullOrEmpty(m_Language) && m_Language == "Java")
                {
                    m_SwaggerProxyGenerator = new SwaggerJavaProxyGenerator();
                    m_SwaggerApiProxySettingsEndPoint = new RESTApiProxySettingsEndPoint("Java", swaggerProxy.GetType().Name);
                }
                else
                {
                    m_SwaggerProxyGenerator = new SwaggerCSharpProxyGenerator();
                    m_SwaggerApiProxySettingsEndPoint = new RESTApiProxySettingsEndPoint("CSharp", swaggerProxy.GetType().Name);
                    m_SwaggerApiProxySettingsEndPoint.Url = m_ServiceDescriptionURL;
                }

                m_SwaggerApiProxySettingsEndPoint.Url = m_ServiceDescriptionURL;
                RESTApiProxySettingsEndPoint[] endpoints = new RESTApiProxySettingsEndPoint[] { m_SwaggerApiProxySettingsEndPoint };
                m_SwaggerServiceDefinition = m_SwaggerProxyGenerator.GenerateSourceString(m_SwaggerApiProxySettingsEndPoint, swaggerDocument, m_ClientCredentialUserName, m_ClientCredentialPassword, m_ClientCredentialDomain);
                Log.DebugFormat("swaggerServiceDefinition EndPoint is {0}", m_SwaggerServiceDefinition.GetEndPoint());
                this.Text = m_WindowTitle + " - got REST service definition";
                m_SourceStringArray = m_SwaggerServiceDefinition.GetSourceStrings();
                if (m_SourceStringArray != null)
                {
                    if (true)
                    {
                        foreach (string sourceString in m_SourceStringArray)
                        {
                            Log.DebugFormat("sourceString is \n{0}", sourceString);
                        }
                    }
                    else
                    {
                        /* This code to be enabled if you need to dump source to file */
                        //using (System.IO.StreamWriter classDefinitionFile = new System.IO.StreamWriter(@"C:\temp\ClassDefinitions.cs"))
                        //{
                        //    {
                        //        foreach (string sourceString in m_SourceStringArray)
                        //        {
                        //            classDefinitionFile.Write(sourceString);
                        //        }
                        //    }
                        //}
                    }
                }

                Log.DebugFormat("{0}", m_SwaggerServiceDefinition.GetEndPoint());
                if (m_SwaggerServiceDefinition.GetProxyClasses().Contains<string>(((string)m_ServicesComboBox.SelectedItem)))
                {
                    m_ServicesComboBox.SelectedItem = m_SwaggerServiceDefinition.GetProxyClasses().First<string>(pc => pc == ((string)m_ServicesComboBox.SelectedItem));
                }
                else
                {
                    m_ServicesComboBox.SelectedItem = m_SwaggerServiceDefinition.GetProxyClasses().First<string>();
                }

                if (string.IsNullOrEmpty(m_Language) || m_Language == "CSharp")
                {
                    CSharpCodeProvider codeProvider = new CSharpCodeProvider();
                    CompilerParameters compilerParameters = CreateCompilerParameters(m_References);
                    m_CompilerResults = codeProvider.CompileAssemblyFromSource(compilerParameters, m_SourceStringArray);
                    if (!m_CompilerResults.Errors.HasErrors)
                    {
                        m_ServicesComboBox.DataSource = m_SwaggerServiceDefinition.GetProxyClasses();
                    }
                    else
                    {
                        foreach (CompilerError error in m_CompilerResults.Errors)
                        {
                            Log.DebugFormat("compiler error {0}", error.ErrorText);
                        }
                    }

                    object[] args = new object[] { new Uri(m_SwaggerServiceDefinition.GetEndPoint()) };
                    string proxyClass = string.Format("{0}.{1}", m_SwaggerApiProxySettingsEndPoint.Namespace, m_ServicesComboBox.SelectedItem);
                    m_RESTServiceClient = m_CompilerResults.CompiledAssembly.CreateInstance(proxyClass, false, BindingFlags.CreateInstance, null, args, null, null);
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

                Log.DebugFormat("{0}", m_SwaggerServiceDefinition.GetEndPoint());
            }
            catch (Exception e)
            {
                Log.WarnFormat("exception processing Integrate type: " + e.Message);
                MessageBox.Show("Exception thrown: " + e.Message);
            }
        }

        private void ProcessIntegrateType(bool refresh)
        {
            Log.Debug("starting ProcessIntegrateType()");
            this.Controls.Remove(m_ViewRichTextBox);
            Log.DebugFormat("m_Language is {0}", m_Language);
            Log.DebugFormat("m_Type is {0}", m_Type);
            m_ClientCredentialDomain = m_DomainTextBox.Text;
            Log.DebugFormat("m_ClientCredentialDomain is {0}", m_ClientCredentialDomain);
            m_ClientCredentialUserName = m_UsernameTextBox.Text;
            Log.DebugFormat("m_ClientCredentialUserName is {0}", m_ClientCredentialUserName);
            m_ClientCredentialPassword = m_PasswordTextBox.Text;
            Log.DebugFormat("m_ClientCredentialPassword is {0}", m_ClientCredentialPassword);
            m_ServiceDescriptionURL = m_ServiceDescriptionURLTextBox.Text;
            Log.DebugFormat("m_ServiceDescriptionURL is {0}", m_ServiceDescriptionURL);
            this.Text = m_WindowTitle + " - retrieving Swagger description from " + m_ServiceDescriptionURL;
            try
            {
                if (refresh)
                {
                    this.Controls.Remove(m_MethodsTabControl);
                    if (!string.IsNullOrEmpty(m_Language) && m_Language == "Java")
                    {
                        m_SwaggerProxyGenerator = new SwaggerJavaProxyGenerator();
                        m_SwaggerApiProxySettingsEndPoint = new RESTApiProxySettingsEndPoint("Java");
                    }
                    else
                    {
                        m_SwaggerProxyGenerator = new SwaggerCSharpProxyGenerator();
                        m_SwaggerApiProxySettingsEndPoint = new RESTApiProxySettingsEndPoint("CSharp");
                    }

                    m_SwaggerApiProxySettingsEndPoint.Url = m_ServiceDescriptionURL;
                    Log.DebugFormat("m_ServiceDescriptionURL is {0}", m_ServiceDescriptionURL);
                    RESTApiProxySettingsEndPoint[] endpoints = new RESTApiProxySettingsEndPoint[] { m_SwaggerApiProxySettingsEndPoint };
                    m_SwaggerServiceDefinition = m_SwaggerProxyGenerator.GenerateSourceString(endpoints);
                    Log.DebugFormat("swaggerServiceDefinition EndPoint is {0}", m_SwaggerServiceDefinition.GetEndPoint());
                    this.Text = m_WindowTitle + " - got REST service definition";
                    m_SourceStringArray = m_SwaggerServiceDefinition.GetSourceStrings();
                    if (m_SourceStringArray != null)
                    {
                        foreach (string sourceString in m_SourceStringArray)
                        {
                            Log.DebugFormat("sourceString is {0}", sourceString);
                        }
                    }

                    Log.DebugFormat("endpoint is {0}", m_SwaggerServiceDefinition.GetEndPoint());
                    if (m_SwaggerServiceDefinition.GetProxyClasses().Contains<string>(((string)m_ServicesComboBox.SelectedItem)))
                    {
                        m_ServicesComboBox.SelectedItem = m_SwaggerServiceDefinition.GetProxyClasses().First<string>(pc => pc == ((string)m_ServicesComboBox.SelectedItem));
                    }
                    else
                    {
                        m_ServicesComboBox.SelectedItem = m_SwaggerServiceDefinition.GetProxyClasses().First<string>();
                    }

                    if (string.IsNullOrEmpty(m_Language) || m_Language == "CSharp")
                    {
                        CSharpCodeProvider codeProvider = new CSharpCodeProvider();
                        CompilerParameters compilerParameters = CreateCompilerParameters(m_References);
                        m_CompilerResults = codeProvider.CompileAssemblyFromSource(compilerParameters, m_SourceStringArray);
                        if (!m_CompilerResults.Errors.HasErrors)
                        {
                            m_ServicesComboBox.DataSource = m_SwaggerServiceDefinition.GetProxyClasses();
                        }
                        else
                        {
                            foreach (CompilerError error in m_CompilerResults.Errors)
                            {
                                Log.DebugFormat("compiler error {0}", error.ErrorText);
                            }
                        }

                        object[] args = new object[] { new Uri(m_SwaggerServiceDefinition.GetEndPoint()) };
                        string proxyClass = string.Format("{0}.{1}", m_SwaggerApiProxySettingsEndPoint.Namespace, m_ServicesComboBox.SelectedItem);
                        m_RESTServiceClient = m_CompilerResults.CompiledAssembly.CreateInstance(proxyClass, false, BindingFlags.CreateInstance, null, args, null, null);
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

                    Log.DebugFormat("{0}", m_SwaggerServiceDefinition.GetEndPoint());
                }
                else
                {
                    /* refresh is false, but service or proxy class has changed */
                    if (string.IsNullOrEmpty(m_Language) || m_Language == "CSharp")
                    {
                        CSharpCodeProvider codeProvider = new CSharpCodeProvider();
                        CompilerParameters compilerParameters = CreateCompilerParameters(m_References);
                        m_CompilerResults = codeProvider.CompileAssemblyFromSource(compilerParameters, m_SourceStringArray);
                        if (!m_CompilerResults.Errors.HasErrors)
                        {
                            m_ServicesComboBox.DataSource = m_SwaggerServiceDefinition.GetProxyClasses();
                        }
                        else
                        {
                            foreach (CompilerError error in m_CompilerResults.Errors)
                            {
                                Log.DebugFormat("compiler error {0}", error.ErrorText);
                            }
                        }

                        object[] args = new object[] { new Uri(m_SwaggerServiceDefinition.GetEndPoint()) };
                        string proxyClass = string.Format("{0}.{1}", m_SwaggerApiProxySettingsEndPoint.Namespace, m_ServicesComboBox.SelectedItem);
                        m_RESTServiceClient = m_CompilerResults.CompiledAssembly.CreateInstance(proxyClass, false, BindingFlags.CreateInstance, null, args, null, null);
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

                    Log.DebugFormat("{0}", m_SwaggerServiceDefinition.GetEndPoint());
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

        private void ProcessNetDocsType()
        {
            Log.Debug("starting ProcessIntegrateType()");
            this.Controls.Remove(m_ViewRichTextBox);
            Log.DebugFormat("m_Language is {0}", m_Language);
            Log.DebugFormat("m_Type is {0}", m_Type);
            m_ClientCredentialDomain = m_DomainTextBox.Text;
            Log.DebugFormat("m_ClientCredentialDomain is {0}", m_ClientCredentialDomain);
            m_ClientCredentialUserName = m_UsernameTextBox.Text;
            Log.DebugFormat("m_ClientCredentialUserName is {0}", m_ClientCredentialUserName);
            m_ClientCredentialPassword = m_PasswordTextBox.Text;
            Log.DebugFormat("m_ClientCredentialPassword is {0}", m_ClientCredentialPassword);
            m_ServiceDescriptionURL = m_ServiceDescriptionURLTextBox.Text;
            Log.DebugFormat("m_ServiceDescriptionURL is {0}", m_ServiceDescriptionURL);
            this.Text = m_WindowTitle + " - retrieving access token from " + m_ServiceDescriptionURL;
            try
            {
                NetDocsRESTProxy netDocsRESTProxy = new NetDocsRESTProxy(new Uri(m_ServiceDescriptionURL), m_ClientCredentialUserName, m_ClientCredentialPassword, m_ClientCredentialDomain);
            }
            catch (Exception e)
            {
                Log.WarnFormat("exception processing NetDocs type: " + e.Message);
                MessageBox.Show("Exception thrown: " + e.Message);
            }
        }

        public string GetStringFromFile(string fileName)
        {
            return File.ReadAllText(fileName);
        }

        private void ProcessSourceType()
        {
            Log.Debug("starting ProcessPetstoreType()");
            try
            {
                this.Text = m_WindowTitle + " - using source service definition";
                List<string> argStringList = new List<string>();
                List<string> dllStringList = new List<string>();
                string package = "";
                List<string> proxyStringList = new List<string>();
                List< string >  sourceStringList = new List<string>();
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
                        Log.Warn("unrecognized prefix");
                    }
                }

                m_SourceStringArray = sourceStringList.ToArray<string>();
                List<string> referencesList = m_References.ToList<string>();
                foreach (string dll in dllStringList)
                {
                    referencesList.Add(dll);
                }

                m_References = referencesList.ToArray<string>();
                foreach (string dll in m_References)
                {
                    Log.DebugFormat("dll is " + dll);
                }

                if (string.IsNullOrEmpty(m_Language) || m_Language == "CSharp")
                {
                    CSharpCodeProvider codeProvider = new CSharpCodeProvider();
                    Log.DebugFormat("created code provider");
                    CompilerParameters compilerParameters = CreateCompilerParameters(m_References);
                    Log.DebugFormat("created compiler parameters");
                    m_CompilerResults = codeProvider.CompileAssemblyFromSource(compilerParameters, m_SourceStringArray);
                    Log.DebugFormat("compiled assembly");
                    if (!m_CompilerResults.Errors.HasErrors)
                    {
                        m_ServicesComboBox.DataSource = proxyStringList;
                    }
                    else
                    {
                        foreach (CompilerError error in m_CompilerResults.Errors)
                        {
                            Log.DebugFormat("compiler error {0}", error.ErrorText);
                        }
                    }

                    Log.Debug("about to set proxy class");
                    object[] args = argStringList.ToArray();
                    string proxyClass = string.Format("{0}.{1}", package, proxyStringList.First<string>());
                    object m_RESTServiceClient = m_CompilerResults.CompiledAssembly.CreateInstance(proxyClass, false, BindingFlags.CreateInstance, null, args, null, null);
                    Log.Debug("about to re-render service control");
                    RerenderServiceControl(m_RESTServiceClient);
                }
            }
            catch (Exception e)
            {
                Log.WarnFormat("exception processing source type: " + e.Message);
                MessageBox.Show("Exception thrown: " + e.Message);
            }
        }

        private void ProcessOpenType()
        {
            Log.Debug("starting ProcessOpenType()");
            this.Controls.Remove(m_ViewRichTextBox);
            Log.DebugFormat("m_Language is {0}", m_Language);
            Log.DebugFormat("m_Type is {0}", m_Type);
            m_ClientCredentialDomain = m_DomainTextBox.Text;
            Log.DebugFormat("m_ClientCredentialDomain is {0}", m_ClientCredentialDomain);
            m_ClientCredentialUserName = m_UsernameTextBox.Text;
            Log.DebugFormat("m_ClientCredentialUserName is {0}", m_ClientCredentialUserName);
            m_ClientCredentialPassword = m_PasswordTextBox.Text;
            Log.DebugFormat("m_ClientCredentialPassword is {0}", m_ClientCredentialPassword);
            m_ServiceDescriptionURL = m_ServiceDescriptionURLTextBox.Text;
            Log.DebugFormat("m_ServiceDescriptionURL is {0}", m_ServiceDescriptionURL);
            this.Text = m_WindowTitle + " - retrieving REST description from " + m_ServiceDescriptionURL;
            try
            {
                ISwaggerProxy openSwaggerProxy = null;
                if (m_OnPremise)
                {
                    openSwaggerProxy = new OpenSwaggerProxy(new Uri(m_ServiceDescriptionURL), m_ClientCredentialUserName, m_ClientCredentialPassword, m_ClientCredentialDomain);
                }
                else
                {
                    openSwaggerProxy = new OpenCloudSwaggerProxy(new Uri(m_ServiceDescriptionURL), m_ClientCredentialUserName, m_ClientCredentialPassword, m_ClientCredentialDomain);
                }

                string swaggerDocument = openSwaggerProxy.GetSwaggerDocument();
                Log.DebugFormat("swaggerDocument is {0}", swaggerDocument);
                if (!string.IsNullOrEmpty(m_Language) && m_Language == "Java")
                {
                    m_SwaggerProxyGenerator = new SwaggerJavaProxyGenerator();
                    m_SwaggerApiProxySettingsEndPoint = new RESTApiProxySettingsEndPoint("Java", openSwaggerProxy.GetType().Name);
                }
                else
                {
                    m_SwaggerProxyGenerator = new SwaggerCSharpProxyGenerator();
                    m_SwaggerApiProxySettingsEndPoint = new RESTApiProxySettingsEndPoint("CSharp", openSwaggerProxy.GetType().Name);
                    m_SwaggerApiProxySettingsEndPoint.Url = m_ServiceDescriptionURL;
                }

                m_SwaggerApiProxySettingsEndPoint.Url = m_ServiceDescriptionURL;
                RESTApiProxySettingsEndPoint[] endpoints = new RESTApiProxySettingsEndPoint[] { m_SwaggerApiProxySettingsEndPoint };
                m_SwaggerServiceDefinition = m_SwaggerProxyGenerator.GenerateSourceString(m_SwaggerApiProxySettingsEndPoint, swaggerDocument, m_ClientCredentialUserName, m_ClientCredentialPassword, m_ClientCredentialDomain);
                Log.DebugFormat("swaggerServiceDefinition EndPoint is {0}", m_SwaggerServiceDefinition.GetEndPoint());
                this.Text = m_WindowTitle + " - got REST service definition";
                m_SourceStringArray = m_SwaggerServiceDefinition.GetSourceStrings();
                if (m_SourceStringArray != null)
                {
                    if (true)
                    {
                        foreach (string sourceString in m_SourceStringArray)
                        {
                            Log.DebugFormat("sourceString is \n{0}", sourceString);
                        }
                    }
                    else
                    {
                        /* This code to be enabled if you need to dump source to file */
                        //using (System.IO.StreamWriter classDefinitionFile = new System.IO.StreamWriter(@"C:\temp\ClassDefinitions.cs"))
                        //{
                        //    {
                        //        foreach (string sourceString in m_SourceStringArray)
                        //        {
                        //            classDefinitionFile.Write(sourceString);
                        //        }
                        //    }
                        //}
                    }
                }

                Log.DebugFormat("{0}", m_SwaggerServiceDefinition.GetEndPoint());
                if (m_SwaggerServiceDefinition.GetProxyClasses().Contains<string>(((string)m_ServicesComboBox.SelectedItem)))
                {
                    m_ServicesComboBox.SelectedItem = m_SwaggerServiceDefinition.GetProxyClasses().First<string>(pc => pc == ((string)m_ServicesComboBox.SelectedItem));
                }
                else
                {
                    m_ServicesComboBox.SelectedItem = m_SwaggerServiceDefinition.GetProxyClasses().First<string>();
                }

                if (string.IsNullOrEmpty(m_Language) || m_Language == "CSharp")
                {
                    CSharpCodeProvider codeProvider = new CSharpCodeProvider();
                    CompilerParameters compilerParameters = CreateCompilerParameters(m_References);
                    m_CompilerResults = codeProvider.CompileAssemblyFromSource(compilerParameters, m_SourceStringArray);
                    if (!m_CompilerResults.Errors.HasErrors)
                    {
                        m_ServicesComboBox.DataSource = m_SwaggerServiceDefinition.GetProxyClasses();
                    }
                    else
                    {
                        foreach (CompilerError error in m_CompilerResults.Errors)
                        {
                            Log.DebugFormat("compiler error {0}", error.ErrorText);
                        }
                    }

                    object[] args = new object[] { new Uri(m_SwaggerServiceDefinition.GetEndPoint()) };
                    string proxyClass = string.Format("{0}.{1}", m_SwaggerApiProxySettingsEndPoint.Namespace, m_ServicesComboBox.SelectedItem);
                    m_RESTServiceClient = m_CompilerResults.CompiledAssembly.CreateInstance(proxyClass, false, BindingFlags.CreateInstance, null, args, null, null);
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

                Log.DebugFormat("{0}", m_SwaggerServiceDefinition.GetEndPoint());
            }
            catch (Exception e)
            {
                Log.WarnFormat("exception getting Open REST document: " + e.Message);
                MessageBox.Show("Exception thrown: " + e.Message);
            }
        }

        private void ProcessPlatformCDSType(bool refresh)
        {
            Log.Debug("starting ProcessPlatformCDSType()");
            ProcessPlatformCDSType();
        }

        private void ProcessPlatformCDSType()
        {
            Log.Debug("starting ProcessPlatformCDSType()");
            this.Controls.Remove(m_ViewRichTextBox);
            Log.DebugFormat("m_Language is {0}", m_Language);
            Log.DebugFormat("m_Type is {0}", m_Type);
            m_ClientCredentialDomain = m_DomainTextBox.Text;
            Log.DebugFormat("m_ClientCredentialDomain is {0}", m_ClientCredentialDomain);
            m_ClientCredentialUserName = m_UsernameTextBox.Text;
            Log.DebugFormat("m_ClientCredentialUserName is {0}", m_ClientCredentialUserName);
            m_ClientCredentialPassword = m_PasswordTextBox.Text;
            Log.DebugFormat("m_ClientCredentialPassword is {0}", m_ClientCredentialPassword);
            m_ServiceDescriptionURL = m_ServiceDescriptionURLTextBox.Text;
            Log.DebugFormat("m_ServiceDescriptionURL is {0}", m_ServiceDescriptionURL);
            this.Text = m_WindowTitle + " - retrieving REST description from " + m_ServiceDescriptionURL;
            try
            {
                ISwaggerProxy platformSwaggerProxy = new PlatformCDSSwaggerProxy(new Uri(m_ServiceDescriptionURL), m_ClientCredentialUserName, m_ClientCredentialPassword, m_ClientCredentialDomain);
                string swaggerDocument = platformSwaggerProxy.GetSwaggerDocument();
                Log.DebugFormat("swaggerDocument is {0}", swaggerDocument);
                if (!string.IsNullOrEmpty(m_Language) && m_Language == "Java")
                {
                    m_SwaggerProxyGenerator = new SwaggerJavaProxyGenerator();
                    m_SwaggerApiProxySettingsEndPoint = new RESTApiProxySettingsEndPoint("Java", platformSwaggerProxy.GetType().Name);
                }
                else
                {
                    m_SwaggerProxyGenerator = new SwaggerCSharpProxyGenerator();
                    m_SwaggerApiProxySettingsEndPoint = new RESTApiProxySettingsEndPoint("CSharp", platformSwaggerProxy.GetType().Name);
                    m_SwaggerApiProxySettingsEndPoint.Url = m_ServiceDescriptionURL;
                }

                m_SwaggerApiProxySettingsEndPoint.Url = m_ServiceDescriptionURL;
                RESTApiProxySettingsEndPoint[] endpoints = new RESTApiProxySettingsEndPoint[] { m_SwaggerApiProxySettingsEndPoint };
                m_SwaggerServiceDefinition = m_SwaggerProxyGenerator.GenerateSourceString(m_SwaggerApiProxySettingsEndPoint, swaggerDocument, m_ClientCredentialUserName, m_ClientCredentialPassword, m_ClientCredentialDomain);
                Log.DebugFormat("swaggerServiceDefinition EndPoint is {0}", m_SwaggerServiceDefinition.GetEndPoint());
                this.Text = m_WindowTitle + " - got REST service definition";
                m_SourceStringArray = m_SwaggerServiceDefinition.GetSourceStrings();
                if (m_SourceStringArray != null)
                {
                    if (true)
                    {
                        foreach (string sourceString in m_SourceStringArray)
                        {
                            Log.DebugFormat("sourceString is \n{0}", sourceString);
                        }
                    }
                    else
                    {
                        /* This code to be enabled if you need to dump source to file */
                        //using (System.IO.StreamWriter classDefinitionFile = new System.IO.StreamWriter(@"C:\temp\ClassDefinitions.cs"))
                        //{
                        //    {
                        //        foreach (string sourceString in m_SourceStringArray)
                        //        {
                        //            classDefinitionFile.Write(sourceString);
                        //        }
                        //    }
                        //}
                    }
                }

                Log.DebugFormat("{0}", m_SwaggerServiceDefinition.GetEndPoint());
                string selectedService = (string)m_ServicesComboBox.SelectedItem;
                Log.DebugFormat("selectedService is {0}", selectedService);
                if (m_SwaggerServiceDefinition.GetProxyClasses().Contains<string>(selectedService))
                {
                    Log.DebugFormat("SwaggerCSharpProxyGenerator service definition proxy classes contains {0}", selectedService);
                    m_ServicesComboBox.SelectedItem = m_SwaggerServiceDefinition.GetProxyClasses().First<string>(pc => pc == ((string)m_ServicesComboBox.SelectedItem));
                }
                else
                {
                    Log.DebugFormat("SwaggerCSharpProxyGenerator service definition proxy classes does not contain {0}", selectedService);
                    selectedService = m_SwaggerServiceDefinition.GetProxyClasses().First<string>();
                    Log.DebugFormat("selectedService is {0}", selectedService);
                    m_ServicesComboBox.SelectedItem = selectedService;
                }

                Log.DebugFormat("selectedService is {0}", selectedService);
                m_ServicesComboBox.Refresh();
                if (string.IsNullOrEmpty(m_Language) || m_Language == "CSharp")
                {
                    CSharpCodeProvider codeProvider = new CSharpCodeProvider();
                    CompilerParameters compilerParameters = CreateCompilerParameters(m_References);
                    m_CompilerResults = codeProvider.CompileAssemblyFromSource(compilerParameters, m_SourceStringArray);
                    if (!m_CompilerResults.Errors.HasErrors)
                    {
                        m_ServicesComboBox.DataSource = m_SwaggerServiceDefinition.GetProxyClasses();
                        m_ServicesComboBox.SelectedItem = selectedService;
                        m_ServicesComboBox.Refresh();
                    }
                    else
                    {
                        foreach (CompilerError error in m_CompilerResults.Errors)
                        {
                            Log.DebugFormat("compiler error {0}", error.ErrorText);
                        }
                    }

                    object[] args = new object[] { new Uri(m_SwaggerServiceDefinition.GetEndPoint()) };
                    string proxyClass = string.Format("{0}.{1}", m_SwaggerApiProxySettingsEndPoint.Namespace, selectedService);
                    m_RESTServiceClient = m_CompilerResults.CompiledAssembly.CreateInstance(proxyClass, false, BindingFlags.CreateInstance, null, args, null, null);
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

                Log.DebugFormat("{0}", m_SwaggerServiceDefinition.GetEndPoint());
            }
            catch (Exception e)
            {
                Log.WarnFormat("exception getting Platform REST document: " + e.Message);
                MessageBox.Show("Exception thrown: " + e.Message);
            }
        }

        private void ProcessPlatformCDSCMType(bool refresh)
        {
            Log.Debug("starting ProcessPlatformCDSCMType()");
            ProcessPlatformCDSCMType();
        }

        private void ProcessPlatformCDSCMType()
        {
            Log.Debug("starting ProcessPlatformCDSCMType()");
            this.Controls.Remove(m_ViewRichTextBox);
            Log.DebugFormat("m_Language is {0}", m_Language);
            Log.DebugFormat("m_Type is {0}", m_Type);
            m_ClientCredentialDomain = m_DomainTextBox.Text;
            Log.DebugFormat("m_ClientCredentialDomain is {0}", m_ClientCredentialDomain);
            m_ClientCredentialUserName = m_UsernameTextBox.Text;
            Log.DebugFormat("m_ClientCredentialUserName is {0}", m_ClientCredentialUserName);
            m_ClientCredentialPassword = m_PasswordTextBox.Text;
            Log.DebugFormat("m_ClientCredentialPassword is {0}", m_ClientCredentialPassword);
            m_ServiceDescriptionURL = m_ServiceDescriptionURLTextBox.Text;
            Log.DebugFormat("m_ServiceDescriptionURL is {0}", m_ServiceDescriptionURL);
            this.Text = m_WindowTitle + " - retrieving REST description from " + m_ServiceDescriptionURL;
            try
            {
                ISwaggerProxy platformSwaggerProxy = new PlatformCDSCMSwaggerProxy(new Uri(m_ServiceDescriptionURL), m_ClientCredentialUserName, m_ClientCredentialPassword, m_ClientCredentialDomain);
                string swaggerDocument = platformSwaggerProxy.GetSwaggerDocument();
                Log.DebugFormat("swaggerDocument is {0}", swaggerDocument);
                if (!string.IsNullOrEmpty(m_Language) && m_Language == "Java")
                {
                    m_SwaggerProxyGenerator = new SwaggerJavaProxyGenerator();
                    m_SwaggerApiProxySettingsEndPoint = new RESTApiProxySettingsEndPoint("Java", platformSwaggerProxy.GetType().Name);
                }
                else
                {
                    m_SwaggerProxyGenerator = new SwaggerCSharpProxyGenerator();
                    m_SwaggerApiProxySettingsEndPoint = new RESTApiProxySettingsEndPoint("CSharp", platformSwaggerProxy.GetType().Name);
                    m_SwaggerApiProxySettingsEndPoint.Url = m_ServiceDescriptionURL;
                }

                m_SwaggerApiProxySettingsEndPoint.Url = m_ServiceDescriptionURL;
                RESTApiProxySettingsEndPoint[] endpoints = new RESTApiProxySettingsEndPoint[] { m_SwaggerApiProxySettingsEndPoint };
                m_SwaggerServiceDefinition = m_SwaggerProxyGenerator.GenerateSourceString(m_SwaggerApiProxySettingsEndPoint, swaggerDocument, m_ClientCredentialUserName, m_ClientCredentialPassword, m_ClientCredentialDomain);
                Log.DebugFormat("swaggerServiceDefinition EndPoint is {0}", m_SwaggerServiceDefinition.GetEndPoint());
                this.Text = m_WindowTitle + " - got REST service definition";
                m_SourceStringArray = m_SwaggerServiceDefinition.GetSourceStrings();
                if (m_SourceStringArray != null)
                {
                    if (true)
                    {
                        foreach (string sourceString in m_SourceStringArray)
                        {
                            Log.DebugFormat("sourceString is \n{0}", sourceString);
                        }
                    }
                    else
                    {
                        /* This code to be enabled if you need to dump source to file */
                        //using (System.IO.StreamWriter classDefinitionFile = new System.IO.StreamWriter(@"C:\temp\ClassDefinitions.cs"))
                        //{
                        //    {
                        //        foreach (string sourceString in m_SourceStringArray)
                        //        {
                        //            classDefinitionFile.Write(sourceString);
                        //        }
                        //    }
                        //}
                    }
                }

                Log.DebugFormat("{0}", m_SwaggerServiceDefinition.GetEndPoint());
                string selectedService = (string)m_ServicesComboBox.SelectedItem;
                Log.DebugFormat("selectedService is {0}", selectedService);
                if (m_SwaggerServiceDefinition.GetProxyClasses().Contains<string>(selectedService))
                {
                    Log.DebugFormat("SwaggerCSharpProxyGenerator service definition proxy classes contains {0}", selectedService);
                    m_ServicesComboBox.SelectedItem = m_SwaggerServiceDefinition.GetProxyClasses().First<string>(pc => pc == ((string)m_ServicesComboBox.SelectedItem));
                }
                else
                {
                    Log.DebugFormat("SwaggerCSharpProxyGenerator service definition proxy classes does not contain {0}", selectedService);
                    selectedService = m_SwaggerServiceDefinition.GetProxyClasses().First<string>();
                    Log.DebugFormat("selectedService is {0}", selectedService);
                    m_ServicesComboBox.SelectedItem = selectedService;
                }

                Log.DebugFormat("selectedService is {0}", selectedService);
                m_ServicesComboBox.Refresh();
                if (string.IsNullOrEmpty(m_Language) || m_Language == "CSharp")
                {
                    CSharpCodeProvider codeProvider = new CSharpCodeProvider();
                    CompilerParameters compilerParameters = CreateCompilerParameters(m_References);
                    m_CompilerResults = codeProvider.CompileAssemblyFromSource(compilerParameters, m_SourceStringArray);
                    if (!m_CompilerResults.Errors.HasErrors)
                    {
                        m_ServicesComboBox.DataSource = m_SwaggerServiceDefinition.GetProxyClasses();
                        m_ServicesComboBox.SelectedItem = selectedService;
                        m_ServicesComboBox.Refresh();
                    }
                    else
                    {
                        foreach (CompilerError error in m_CompilerResults.Errors)
                        {
                            Log.DebugFormat("compiler error {0}", error.ErrorText);
                        }
                    }

                    object[] args = new object[] { new Uri(m_SwaggerServiceDefinition.GetEndPoint()) };
                    string proxyClass = string.Format("{0}.{1}", m_SwaggerApiProxySettingsEndPoint.Namespace, selectedService);
                    m_RESTServiceClient = m_CompilerResults.CompiledAssembly.CreateInstance(proxyClass, false, BindingFlags.CreateInstance, null, args, null, null);
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

                Log.DebugFormat("{0}", m_SwaggerServiceDefinition.GetEndPoint());
            }
            catch (Exception e)
            {
                Log.WarnFormat("exception getting Platform REST document: " + e.Message);
                MessageBox.Show("Exception thrown: " + e.Message);
            }
        }

        private void ProcessPlatformDocumentType(bool refresh)
        {
            Log.Debug("starting ProcessPlatformDocumentType()");
            ProcessPlatformDocumentType();
        }

        private void ProcessPlatformDocumentType()
        {
            Log.Debug("starting ProcessPlatformDocumentType()");
            this.Controls.Remove(m_ViewRichTextBox);
            Log.DebugFormat("m_Language is {0}", m_Language);
            Log.DebugFormat("m_Type is {0}", m_Type);
            m_ClientCredentialDomain = m_DomainTextBox.Text;
            Log.DebugFormat("m_ClientCredentialDomain is {0}", m_ClientCredentialDomain);
            m_ClientCredentialUserName = m_UsernameTextBox.Text;
            Log.DebugFormat("m_ClientCredentialUserName is {0}", m_ClientCredentialUserName);
            m_ClientCredentialPassword = m_PasswordTextBox.Text;
            Log.DebugFormat("m_ClientCredentialPassword is {0}", m_ClientCredentialPassword);
            m_ServiceDescriptionURL = m_ServiceDescriptionURLTextBox.Text;
            Log.DebugFormat("m_ServiceDescriptionURL is {0}", m_ServiceDescriptionURL);
            this.Text = m_WindowTitle + " - retrieving REST description from " + m_ServiceDescriptionURL;
            try
            {
                ISwaggerProxy platformSwaggerProxy = new PlatformDocumentSwaggerProxy(new Uri(m_ServiceDescriptionURL), m_ClientCredentialUserName, m_ClientCredentialPassword, m_ClientCredentialDomain);
                string swaggerDocument = platformSwaggerProxy.GetSwaggerDocument();
                Log.DebugFormat("swaggerDocument is {0}", swaggerDocument);
                if (!string.IsNullOrEmpty(m_Language) && m_Language == "Java")
                {
                    m_SwaggerProxyGenerator = new SwaggerJavaProxyGenerator();
                    m_SwaggerApiProxySettingsEndPoint = new RESTApiProxySettingsEndPoint("Java", platformSwaggerProxy.GetType().Name);
                }
                else
                {
                    m_SwaggerProxyGenerator = new SwaggerCSharpProxyGenerator();
                    m_SwaggerApiProxySettingsEndPoint = new RESTApiProxySettingsEndPoint("CSharp", platformSwaggerProxy.GetType().Name);
                    m_SwaggerApiProxySettingsEndPoint.Url = m_ServiceDescriptionURL;
                }

                m_SwaggerApiProxySettingsEndPoint.Url = m_ServiceDescriptionURL;
                RESTApiProxySettingsEndPoint[] endpoints = new RESTApiProxySettingsEndPoint[] { m_SwaggerApiProxySettingsEndPoint };
                m_SwaggerServiceDefinition = m_SwaggerProxyGenerator.GenerateSourceString(m_SwaggerApiProxySettingsEndPoint, swaggerDocument, m_ClientCredentialUserName, m_ClientCredentialPassword, m_ClientCredentialDomain);
                Log.DebugFormat("swaggerServiceDefinition EndPoint is {0}", m_SwaggerServiceDefinition.GetEndPoint());
                this.Text = m_WindowTitle + " - got REST service definition";
                m_SourceStringArray = m_SwaggerServiceDefinition.GetSourceStrings();
                if (m_SourceStringArray != null)
                {
                    if (true)
                    {
                        foreach (string sourceString in m_SourceStringArray)
                        {
                            Log.DebugFormat("sourceString is \n{0}", sourceString);
                        }
                    }
                    else
                    {
                        /* This code to be enabled if you need to dump source to file */
                        //using (System.IO.StreamWriter classDefinitionFile = new System.IO.StreamWriter(@"C:\temp\ClassDefinitions.cs"))
                        //{
                        //    {
                        //        foreach (string sourceString in m_SourceStringArray)
                        //        {
                        //            classDefinitionFile.Write(sourceString);
                        //        }
                        //    }
                        //}
                    }
                }

                Log.DebugFormat("{0}", m_SwaggerServiceDefinition.GetEndPoint());
                string selectedService = (string)m_ServicesComboBox.SelectedItem;
                Log.DebugFormat("selectedService is {0}", selectedService);
                if (m_SwaggerServiceDefinition.GetProxyClasses().Contains<string>(selectedService))
                {
                    Log.DebugFormat("SwaggerCSharpProxyGenerator service definition proxy classes contains {0}", selectedService);
                    m_ServicesComboBox.SelectedItem = m_SwaggerServiceDefinition.GetProxyClasses().First<string>(pc => pc == ((string)m_ServicesComboBox.SelectedItem));
                }
                else
                {
                    Log.DebugFormat("SwaggerCSharpProxyGenerator service definition proxy classes does not contain {0}", selectedService);
                    selectedService = m_SwaggerServiceDefinition.GetProxyClasses().First<string>();
                    Log.DebugFormat("selectedService is {0}", selectedService);
                    m_ServicesComboBox.SelectedItem = selectedService;
                }

                Log.DebugFormat("selectedService is {0}", selectedService);
                m_ServicesComboBox.Refresh();
                if (string.IsNullOrEmpty(m_Language) || m_Language == "CSharp")
                {
                    CSharpCodeProvider codeProvider = new CSharpCodeProvider();
                    CompilerParameters compilerParameters = CreateCompilerParameters(m_References);
                    m_CompilerResults = codeProvider.CompileAssemblyFromSource(compilerParameters, m_SourceStringArray);
                    if (!m_CompilerResults.Errors.HasErrors)
                    {
                        m_ServicesComboBox.DataSource = m_SwaggerServiceDefinition.GetProxyClasses();
                        m_ServicesComboBox.SelectedItem = selectedService;
                        m_ServicesComboBox.Refresh();
                    }
                    else
                    {
                        foreach (CompilerError error in m_CompilerResults.Errors)
                        {
                            Log.DebugFormat("compiler error {0}", error.ErrorText);
                        }
                    }

                    object[] args = new object[] { new Uri(m_SwaggerServiceDefinition.GetEndPoint()) };
                    string proxyClass = string.Format("{0}.{1}", m_SwaggerApiProxySettingsEndPoint.Namespace, selectedService);
                    m_RESTServiceClient = m_CompilerResults.CompiledAssembly.CreateInstance(proxyClass, false, BindingFlags.CreateInstance, null, args, null, null);
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

                Log.DebugFormat("{0}", m_SwaggerServiceDefinition.GetEndPoint());
            }
            catch (Exception e)
            {
                Log.WarnFormat("exception getting Platform REST document: " + e.Message);
                MessageBox.Show("Exception thrown: " + e.Message);
            }
        }

        private void ProcessPlatformRefDataType(bool refresh)
        {
            Log.Debug("starting ProcessPlatformRefDataType()");
            ProcessPlatformRefDataType();
        }

        private void ProcessPlatformRefDataType()
        {
            Log.Debug("starting ProcessPlatformRefDataType()");
            this.Controls.Remove(m_ViewRichTextBox);
            Log.DebugFormat("m_Language is {0}", m_Language);
            Log.DebugFormat("m_Type is {0}", m_Type);
            m_ClientCredentialDomain = m_DomainTextBox.Text;
            Log.DebugFormat("m_ClientCredentialDomain is {0}", m_ClientCredentialDomain);
            m_ClientCredentialUserName = m_UsernameTextBox.Text;
            Log.DebugFormat("m_ClientCredentialUserName is {0}", m_ClientCredentialUserName);
            m_ClientCredentialPassword = m_PasswordTextBox.Text;
            Log.DebugFormat("m_ClientCredentialPassword is {0}", m_ClientCredentialPassword);
            m_ServiceDescriptionURL = m_ServiceDescriptionURLTextBox.Text;
            Log.DebugFormat("m_ServiceDescriptionURL is {0}", m_ServiceDescriptionURL);
            this.Text = m_WindowTitle + " - retrieving REST description from " + m_ServiceDescriptionURL;
            try
            {
                ISwaggerProxy platformSwaggerProxy = new PlatformRefDataSwaggerProxy(new Uri(m_ServiceDescriptionURL), m_ClientCredentialUserName, m_ClientCredentialPassword, m_ClientCredentialDomain);
                string swaggerDocument = platformSwaggerProxy.GetSwaggerDocument();
                Log.DebugFormat("swaggerDocument is {0}", swaggerDocument);
                if (!string.IsNullOrEmpty(m_Language) && m_Language == "Java")
                {
                    m_SwaggerProxyGenerator = new SwaggerJavaProxyGenerator();
                    m_SwaggerApiProxySettingsEndPoint = new RESTApiProxySettingsEndPoint("Java", platformSwaggerProxy.GetType().Name);
                }
                else
                {
                    m_SwaggerProxyGenerator = new SwaggerCSharpProxyGenerator();
                    m_SwaggerApiProxySettingsEndPoint = new RESTApiProxySettingsEndPoint("CSharp", platformSwaggerProxy.GetType().Name);
                    m_SwaggerApiProxySettingsEndPoint.Url = m_ServiceDescriptionURL;
                }

                m_SwaggerApiProxySettingsEndPoint.Url = m_ServiceDescriptionURL;
                RESTApiProxySettingsEndPoint[] endpoints = new RESTApiProxySettingsEndPoint[] { m_SwaggerApiProxySettingsEndPoint };
                m_SwaggerServiceDefinition = m_SwaggerProxyGenerator.GenerateSourceString(m_SwaggerApiProxySettingsEndPoint, swaggerDocument, m_ClientCredentialUserName, m_ClientCredentialPassword, m_ClientCredentialDomain);
                Log.DebugFormat("swaggerServiceDefinition EndPoint is {0}", m_SwaggerServiceDefinition.GetEndPoint());
                this.Text = m_WindowTitle + " - got REST service definition";
                m_SourceStringArray = m_SwaggerServiceDefinition.GetSourceStrings();
                if (m_SourceStringArray != null)
                {
                    if (true)
                    {
                        foreach (string sourceString in m_SourceStringArray)
                        {
                            Log.DebugFormat("sourceString is \n{0}", sourceString);
                        }
                    }
                    else
                    {
                        /* This code to be enabled if you need to dump source to file */
                        //using (System.IO.StreamWriter classDefinitionFile = new System.IO.StreamWriter(@"C:\temp\ClassDefinitions.cs"))
                        //{
                        //    {
                        //        foreach (string sourceString in m_SourceStringArray)
                        //        {
                        //            classDefinitionFile.Write(sourceString);
                        //        }
                        //    }
                        //}
                    }
                }

                Log.DebugFormat("{0}", m_SwaggerServiceDefinition.GetEndPoint());
                string selectedService = (string)m_ServicesComboBox.SelectedItem;
                Log.DebugFormat("selectedService is {0}", selectedService);
                if (m_SwaggerServiceDefinition.GetProxyClasses().Contains<string>(selectedService))
                {
                    Log.DebugFormat("SwaggerCSharpProxyGenerator service definition proxy classes contains {0}", selectedService);
                    m_ServicesComboBox.SelectedItem = m_SwaggerServiceDefinition.GetProxyClasses().First<string>(pc => pc == ((string)m_ServicesComboBox.SelectedItem));
                }
                else
                {
                    Log.DebugFormat("SwaggerCSharpProxyGenerator service definition proxy classes does not contain {0}", selectedService);
                    selectedService = m_SwaggerServiceDefinition.GetProxyClasses().First<string>();
                    Log.DebugFormat("selectedService is {0}", selectedService);
                    m_ServicesComboBox.SelectedItem = selectedService;
                }

                Log.DebugFormat("selectedService is {0}", selectedService);
                m_ServicesComboBox.Refresh();
                if (string.IsNullOrEmpty(m_Language) || m_Language == "CSharp")
                {
                    CSharpCodeProvider codeProvider = new CSharpCodeProvider();
                    CompilerParameters compilerParameters = CreateCompilerParameters(m_References);
                    m_CompilerResults = codeProvider.CompileAssemblyFromSource(compilerParameters, m_SourceStringArray);
                    if (!m_CompilerResults.Errors.HasErrors)
                    {
                        m_ServicesComboBox.DataSource = m_SwaggerServiceDefinition.GetProxyClasses();
                        m_ServicesComboBox.SelectedItem = selectedService;
                        m_ServicesComboBox.Refresh();
                    }
                    else
                    {
                        foreach (CompilerError error in m_CompilerResults.Errors)
                        {
                            Log.DebugFormat("compiler error {0}", error.ErrorText);
                        }
                    }

                    object[] args = new object[] { new Uri(m_SwaggerServiceDefinition.GetEndPoint()) };
                    string proxyClass = string.Format("{0}.{1}", m_SwaggerApiProxySettingsEndPoint.Namespace, selectedService);
                    m_RESTServiceClient = m_CompilerResults.CompiledAssembly.CreateInstance(proxyClass, false, BindingFlags.CreateInstance, null, args, null, null);
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

                Log.DebugFormat("{0}", m_SwaggerServiceDefinition.GetEndPoint());
            }
            catch (Exception e)
            {
                Log.WarnFormat("exception getting Platform REST document: " + e.Message);
                MessageBox.Show("Exception thrown: " + e.Message);
            }
        }

        private void ProcessPlatformSanctionListsType(bool refresh)
        {
            Log.Debug("starting ProcessPlatformSanctionListsType()");
            ProcessPlatformSanctionListsType();
        }

        private void ProcessPlatformSanctionListsType()
        {
            Log.Debug("starting ProcessPlatformSanctionListsType()");
            this.Controls.Remove(m_ViewRichTextBox);
            Log.DebugFormat("m_Language is {0}", m_Language);
            Log.DebugFormat("m_Type is {0}", m_Type);
            m_ClientCredentialDomain = m_DomainTextBox.Text;
            Log.DebugFormat("m_ClientCredentialDomain is {0}", m_ClientCredentialDomain);
            m_ClientCredentialUserName = m_UsernameTextBox.Text;
            Log.DebugFormat("m_ClientCredentialUserName is {0}", m_ClientCredentialUserName);
            m_ClientCredentialPassword = m_PasswordTextBox.Text;
            Log.DebugFormat("m_ClientCredentialPassword is {0}", m_ClientCredentialPassword);
            m_ServiceDescriptionURL = m_ServiceDescriptionURLTextBox.Text;
            Log.DebugFormat("m_ServiceDescriptionURL is {0}", m_ServiceDescriptionURL);
            this.Text = m_WindowTitle + " - retrieving REST description from " + m_ServiceDescriptionURL;
            try
            {
                ISwaggerProxy platformSwaggerProxy = new PlatformSanctionListsSwaggerProxy(new Uri(m_ServiceDescriptionURL), m_ClientCredentialUserName, m_ClientCredentialPassword, m_ClientCredentialDomain);
                string swaggerDocument = platformSwaggerProxy.GetSwaggerDocument();
                Log.DebugFormat("swaggerDocument is {0}", swaggerDocument);
                if (!string.IsNullOrEmpty(m_Language) && m_Language == "Java")
                {
                    m_SwaggerProxyGenerator = new SwaggerJavaProxyGenerator();
                    m_SwaggerApiProxySettingsEndPoint = new RESTApiProxySettingsEndPoint("Java", platformSwaggerProxy.GetType().Name);
                }
                else
                {
                    m_SwaggerProxyGenerator = new SwaggerCSharpProxyGenerator();
                    m_SwaggerApiProxySettingsEndPoint = new RESTApiProxySettingsEndPoint("CSharp", platformSwaggerProxy.GetType().Name);
                    m_SwaggerApiProxySettingsEndPoint.Url = m_ServiceDescriptionURL;
                }

                m_SwaggerApiProxySettingsEndPoint.Url = m_ServiceDescriptionURL;
                RESTApiProxySettingsEndPoint[] endpoints = new RESTApiProxySettingsEndPoint[] { m_SwaggerApiProxySettingsEndPoint };
                m_SwaggerServiceDefinition = m_SwaggerProxyGenerator.GenerateSourceString(m_SwaggerApiProxySettingsEndPoint, swaggerDocument, m_ClientCredentialUserName, m_ClientCredentialPassword, m_ClientCredentialDomain);
                Log.DebugFormat("swaggerServiceDefinition EndPoint is {0}", m_SwaggerServiceDefinition.GetEndPoint());
                this.Text = m_WindowTitle + " - got REST service definition";
                m_SourceStringArray = m_SwaggerServiceDefinition.GetSourceStrings();
                if (m_SourceStringArray != null)
                {
                    if (true)
                    {
                        foreach (string sourceString in m_SourceStringArray)
                        {
                            Log.DebugFormat("sourceString is \n{0}", sourceString);
                        }
                    }
                    else
                    {
                        /* This code to be enabled if you need to dump source to file */
                        //using (System.IO.StreamWriter classDefinitionFile = new System.IO.StreamWriter(@"C:\temp\ClassDefinitions.cs"))
                        //{
                        //    {
                        //        foreach (string sourceString in m_SourceStringArray)
                        //        {
                        //            classDefinitionFile.Write(sourceString);
                        //        }
                        //    }
                        //}
                    }
                }

                Log.DebugFormat("{0}", m_SwaggerServiceDefinition.GetEndPoint());
                string selectedService = (string)m_ServicesComboBox.SelectedItem;
                Log.DebugFormat("selectedService is {0}", selectedService);
                if (m_SwaggerServiceDefinition.GetProxyClasses().Contains<string>(selectedService))
                {
                    Log.DebugFormat("SwaggerCSharpProxyGenerator service definition proxy classes contains {0}", selectedService);
                    m_ServicesComboBox.SelectedItem = m_SwaggerServiceDefinition.GetProxyClasses().First<string>(pc => pc == ((string)m_ServicesComboBox.SelectedItem));
                }
                else
                {
                    Log.DebugFormat("SwaggerCSharpProxyGenerator service definition proxy classes does not contain {0}", selectedService);
                    selectedService = m_SwaggerServiceDefinition.GetProxyClasses().First<string>();
                    Log.DebugFormat("selectedService is {0}", selectedService);
                    m_ServicesComboBox.SelectedItem = selectedService;
                }

                Log.DebugFormat("selectedService is {0}", selectedService);
                m_ServicesComboBox.Refresh();
                if (string.IsNullOrEmpty(m_Language) || m_Language == "CSharp")
                {
                    CSharpCodeProvider codeProvider = new CSharpCodeProvider();
                    CompilerParameters compilerParameters = CreateCompilerParameters(m_References);
                    m_CompilerResults = codeProvider.CompileAssemblyFromSource(compilerParameters, m_SourceStringArray);
                    if (!m_CompilerResults.Errors.HasErrors)
                    {
                        m_ServicesComboBox.DataSource = m_SwaggerServiceDefinition.GetProxyClasses();
                        m_ServicesComboBox.SelectedItem = selectedService;
                        m_ServicesComboBox.Refresh();
                    }
                    else
                    {
                        foreach (CompilerError error in m_CompilerResults.Errors)
                        {
                            Log.DebugFormat("compiler error {0}", error.ErrorText);
                        }
                    }

                    object[] args = new object[] { new Uri(m_SwaggerServiceDefinition.GetEndPoint()) };
                    string proxyClass = string.Format("{0}.{1}", m_SwaggerApiProxySettingsEndPoint.Namespace, selectedService);
                    m_RESTServiceClient = m_CompilerResults.CompiledAssembly.CreateInstance(proxyClass, false, BindingFlags.CreateInstance, null, args, null, null);
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

                Log.DebugFormat("{0}", m_SwaggerServiceDefinition.GetEndPoint());
            }
            catch (Exception e)
            {
                Log.WarnFormat("exception getting Platform REST document: " + e.Message);
                MessageBox.Show("Exception thrown: " + e.Message);
            }
        }

        private void ProcessPlatformTMSType(bool refresh)
        {
            Log.Debug("starting ProcessPlatformTMSType()");
            ProcessPlatformTMSType();
        }

        private void ProcessPlatformTMSType()
        {
            Log.Debug("starting ProcessPlatformTMSType()");
            this.Controls.Remove(m_ViewRichTextBox);
            Log.DebugFormat("m_Language is {0}", m_Language);
            Log.DebugFormat("m_Type is {0}", m_Type);
            m_ClientCredentialDomain = m_DomainTextBox.Text;
            Log.DebugFormat("m_ClientCredentialDomain is {0}", m_ClientCredentialDomain);
            m_ClientCredentialUserName = m_UsernameTextBox.Text;
            Log.DebugFormat("m_ClientCredentialUserName is {0}", m_ClientCredentialUserName);
            m_ClientCredentialPassword = m_PasswordTextBox.Text;
            Log.DebugFormat("m_ClientCredentialPassword is {0}", m_ClientCredentialPassword);
            m_ServiceDescriptionURL = m_ServiceDescriptionURLTextBox.Text;
            Log.DebugFormat("m_ServiceDescriptionURL is {0}", m_ServiceDescriptionURL);
            this.Text = m_WindowTitle + " - retrieving REST description from " + m_ServiceDescriptionURL;
            try
            {
                ISwaggerProxy platformSwaggerProxy = new PlatformTMSSwaggerProxy(new Uri(m_ServiceDescriptionURL), m_ClientCredentialUserName, m_ClientCredentialPassword, m_ClientCredentialDomain);
                string swaggerDocument = platformSwaggerProxy.GetSwaggerDocument();
                Log.DebugFormat("swaggerDocument is {0}", swaggerDocument);
                if (!string.IsNullOrEmpty(m_Language) && m_Language == "Java")
                {
                    m_SwaggerProxyGenerator = new SwaggerJavaProxyGenerator();
                    m_SwaggerApiProxySettingsEndPoint = new RESTApiProxySettingsEndPoint("Java", platformSwaggerProxy.GetType().Name);
                }
                else
                {
                    m_SwaggerProxyGenerator = new SwaggerCSharpProxyGenerator();
                    m_SwaggerApiProxySettingsEndPoint = new RESTApiProxySettingsEndPoint("CSharp", platformSwaggerProxy.GetType().Name);
                    m_SwaggerApiProxySettingsEndPoint.Url = m_ServiceDescriptionURL;
                }

                m_SwaggerApiProxySettingsEndPoint.Url = m_ServiceDescriptionURL;
                RESTApiProxySettingsEndPoint[] endpoints = new RESTApiProxySettingsEndPoint[] { m_SwaggerApiProxySettingsEndPoint };
                m_SwaggerServiceDefinition = m_SwaggerProxyGenerator.GenerateSourceString(m_SwaggerApiProxySettingsEndPoint, swaggerDocument, m_ClientCredentialUserName, m_ClientCredentialPassword, m_ClientCredentialDomain);
                Log.DebugFormat("swaggerServiceDefinition EndPoint is {0}", m_SwaggerServiceDefinition.GetEndPoint());
                this.Text = m_WindowTitle + " - got REST service definition";
                m_SourceStringArray = m_SwaggerServiceDefinition.GetSourceStrings();
                if (m_SourceStringArray != null)
                {
                    if (true)
                    {
                        foreach (string sourceString in m_SourceStringArray)
                        {
                            Log.DebugFormat("sourceString is \n{0}", sourceString);
                        }
                    }
                    else
                    {
                        /* This code to be enabled if you need to dump source to file */
                        //using (System.IO.StreamWriter classDefinitionFile = new System.IO.StreamWriter(@"C:\temp\ClassDefinitions.cs"))
                        //{
                        //    {
                        //        foreach (string sourceString in m_SourceStringArray)
                        //        {
                        //            classDefinitionFile.Write(sourceString);
                        //        }
                        //    }
                        //}
                    }
                }

                Log.DebugFormat("{0}", m_SwaggerServiceDefinition.GetEndPoint());
                string selectedService = (string)m_ServicesComboBox.SelectedItem;
                Log.DebugFormat("selectedService is {0}", selectedService);
                if (m_SwaggerServiceDefinition.GetProxyClasses().Contains<string>(selectedService))
                {
                    Log.DebugFormat("SwaggerCSharpProxyGenerator service definition proxy classes contains {0}", selectedService);
                    m_ServicesComboBox.SelectedItem = m_SwaggerServiceDefinition.GetProxyClasses().First<string>(pc => pc == ((string)m_ServicesComboBox.SelectedItem));
                }
                else
                {
                    Log.DebugFormat("SwaggerCSharpProxyGenerator service definition proxy classes does not contain {0}", selectedService);
                    selectedService = m_SwaggerServiceDefinition.GetProxyClasses().First<string>();
                    Log.DebugFormat("selectedService is {0}", selectedService);
                    m_ServicesComboBox.SelectedItem = selectedService;
                }

                Log.DebugFormat("selectedService is {0}", selectedService);
                m_ServicesComboBox.Refresh();
                if (string.IsNullOrEmpty(m_Language) || m_Language == "CSharp")
                {
                    CSharpCodeProvider codeProvider = new CSharpCodeProvider();
                    CompilerParameters compilerParameters = CreateCompilerParameters(m_References);
                    m_CompilerResults = codeProvider.CompileAssemblyFromSource(compilerParameters, m_SourceStringArray);
                    if (!m_CompilerResults.Errors.HasErrors)
                    {
                        m_ServicesComboBox.DataSource = m_SwaggerServiceDefinition.GetProxyClasses();
                        m_ServicesComboBox.SelectedItem = selectedService;
                        m_ServicesComboBox.Refresh();
                    }
                    else
                    {
                        foreach (CompilerError error in m_CompilerResults.Errors)
                        {
                            Log.DebugFormat("compiler error {0}", error.ErrorText);
                        }
                    }

                    object[] args = new object[] { new Uri(m_SwaggerServiceDefinition.GetEndPoint()) };
                    string proxyClass = string.Format("{0}.{1}", m_SwaggerApiProxySettingsEndPoint.Namespace, selectedService);
                    m_RESTServiceClient = m_CompilerResults.CompiledAssembly.CreateInstance(proxyClass, false, BindingFlags.CreateInstance, null, args, null, null);
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

                Log.DebugFormat("{0}", m_SwaggerServiceDefinition.GetEndPoint());
            }
            catch (Exception e)
            {
                Log.WarnFormat("exception getting Platform REST document: " + e.Message);
                MessageBox.Show("Exception thrown: " + e.Message);
            }
        }

        private void ProcessRAMLType(bool refresh)
        {
            Log.Debug("starting ProcessRAMLType()");
            this.Controls.Remove(m_ViewRichTextBox);
            Log.DebugFormat("m_Language is {0}", m_Language);
            Log.DebugFormat("m_Type is {0}", m_Type);
            m_ClientCredentialDomain = m_DomainTextBox.Text;
            Log.DebugFormat("m_ClientCredentialDomain is {0}", m_ClientCredentialDomain);
            m_ClientCredentialUserName = m_UsernameTextBox.Text;
            Log.DebugFormat("m_ClientCredentialUserName is {0}", m_ClientCredentialUserName);
            m_ClientCredentialPassword = m_PasswordTextBox.Text;
            Log.DebugFormat("m_ClientCredentialPassword is {0}", m_ClientCredentialPassword);
            m_ServiceDescriptionURL = m_ServiceDescriptionURLTextBox.Text;
            Log.Debug("about to get RAML description from " + m_ServiceDescriptionURL);
            this.Text = m_WindowTitle + " - retrieving RAML description from " + m_ServiceDescriptionURL;
            try
            {
                IRESTProxy restProxy = new RAMLProxy();
                RESTApiProxySettingsEndPoint ramlApiProxySettingsEndPoint = null;
                RESTApiProxySettingsEndPoint[] endpoints = null;
                IServiceDefinition ramlServiceDefinition = null;
                if (refresh)
                {
                    this.Controls.Remove(m_MethodsTabControl);
                    RESTProxyGenerator ramlProxyGenerator = null;
                    if (!string.IsNullOrEmpty(m_Language) && m_Language == "Java")
                    {
                        ramlProxyGenerator = new RAMLJavaProxyGenerator();
                        ramlApiProxySettingsEndPoint = new RESTApiProxySettingsEndPoint("Java", restProxy.GetType().Name);
                    }
                    else
                    {
                        ramlProxyGenerator = new RAMLCSharpProxyGenerator();
                        ramlApiProxySettingsEndPoint = new RESTApiProxySettingsEndPoint("CSharp", restProxy.GetType().Name);
                    }

                    ramlApiProxySettingsEndPoint.Url = m_ServiceDescriptionURL;
                    Log.DebugFormat("m_ServiceDescriptionURL is {0}", m_ServiceDescriptionURL);
                    endpoints = new RESTApiProxySettingsEndPoint[] { ramlApiProxySettingsEndPoint };
                    ramlServiceDefinition = ramlProxyGenerator.GenerateSourceString(endpoints);
                    Log.DebugFormat("swaggerServiceDefinition EndPoint is {0}", ramlServiceDefinition.GetEndPoint());
                    this.Text = m_WindowTitle + " - got RAML service definition";
                    m_SourceStringArray = ramlServiceDefinition.GetSourceStrings();
                    if (m_SourceStringArray != null)
                    {
                        foreach (string sourceString in m_SourceStringArray)
                        {
                            Log.DebugFormat("sourceString is {0}", sourceString);
                        }
                    }

                    Log.DebugFormat("endpoint is {0}", ramlServiceDefinition.GetEndPoint());
                    if (ramlServiceDefinition.GetProxyClasses().Contains<string>(((string)m_ServicesComboBox.SelectedItem)))
                    {
                        m_ServicesComboBox.SelectedItem = ramlServiceDefinition.GetProxyClasses().First<string>(pc => pc == ((string)m_ServicesComboBox.SelectedItem));
                    }
                    else
                    {
                        m_ServicesComboBox.SelectedItem = ramlServiceDefinition.GetProxyClasses().First<string>();
                    }

                    if (string.IsNullOrEmpty(m_Language) || m_Language == "CSharp")
                    {
                        CSharpCodeProvider codeProvider = new CSharpCodeProvider();
                        CompilerParameters compilerParameters = CreateCompilerParameters(m_References);
                        m_CompilerResults = codeProvider.CompileAssemblyFromSource(compilerParameters, m_SourceStringArray);
                        if (!m_CompilerResults.Errors.HasErrors)
                        {
                            m_ServicesComboBox.DataSource = ramlServiceDefinition.GetProxyClasses();
                        }
                        else
                        {
                            foreach (CompilerError error in m_CompilerResults.Errors)
                            {
                                Log.DebugFormat("compiler error {0}", error.ErrorText);
                            }
                        }

                        object[] args = new object[] { new Uri(ramlServiceDefinition.GetEndPoint()) };
                        string proxyClass = string.Format("{0}.{1}", ramlApiProxySettingsEndPoint.Namespace, m_ServicesComboBox.SelectedItem);
                        m_RESTServiceClient = m_CompilerResults.CompiledAssembly.CreateInstance(proxyClass, false, BindingFlags.CreateInstance, null, args, null, null);
                        if (m_RESTServiceClient != null)
                        {
                            NetworkCredential networkCredential = new NetworkCredential(m_ClientCredentialUserName, m_ClientCredentialPassword, m_ClientCredentialDomain);
                            ((RESTProxy)m_RESTServiceClient).ClientCredentials = networkCredential;
                            if (m_ProxyEnable)
                            {
                                ((RESTProxy)m_RESTServiceClient).Proxy = new WebProxy(m_ProxyAddress, m_ProxyPort);
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

                    Log.DebugFormat("{0}", ramlServiceDefinition.GetEndPoint());
                }
                else
                {
                    /* refresh is false, but service or proxy class has changed */
                    if (string.IsNullOrEmpty(m_Language) || m_Language == "CSharp")
                    {
                        CSharpCodeProvider codeProvider = new CSharpCodeProvider();
                        CompilerParameters compilerParameters = CreateCompilerParameters(m_References);
                        m_CompilerResults = codeProvider.CompileAssemblyFromSource(compilerParameters, m_SourceStringArray);
                        if (!m_CompilerResults.Errors.HasErrors)
                        {
                            m_ServicesComboBox.DataSource = ramlServiceDefinition.GetProxyClasses();
                        }
                        else
                        {
                            foreach (CompilerError error in m_CompilerResults.Errors)
                            {
                                Log.DebugFormat("compiler error {0}", error.ErrorText);
                            }
                        }

                        object[] args = new object[] { new Uri(ramlServiceDefinition.GetEndPoint()) };
                        string proxyClass = string.Format("{0}.{1}", ramlApiProxySettingsEndPoint.Namespace, m_ServicesComboBox.SelectedItem);
                        m_RESTServiceClient = m_CompilerResults.CompiledAssembly.CreateInstance(proxyClass, false, BindingFlags.CreateInstance, null, args, null, null);
                        if (m_RESTServiceClient != null)
                        {
                            NetworkCredential networkCredential = new NetworkCredential(m_ClientCredentialUserName, m_ClientCredentialPassword, m_ClientCredentialDomain);
                            ((RESTProxy)m_RESTServiceClient).ClientCredentials = networkCredential;
                            Log.Debug("set client credentials");
                        }
                        else
                        {
                            Log.Debug("m_RESTServiceClient is null");
                        }

                        Log.Debug("about to re-render service control");
                        RerenderServiceControl(m_RESTServiceClient);
                    }

                    Log.DebugFormat("{0}", ramlServiceDefinition.GetEndPoint());
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
            this.Controls.Remove(m_ViewRichTextBox);
            Log.DebugFormat("m_Language is {0}", m_Language);
            Log.DebugFormat("m_Type is {0}", m_Type);
            m_ClientCredentialDomain = m_DomainTextBox.Text;
            Log.DebugFormat("m_ClientCredentialDomain is {0}", m_ClientCredentialDomain);
            m_ClientCredentialUserName = m_UsernameTextBox.Text;
            Log.DebugFormat("m_ClientCredentialUserName is {0}", m_ClientCredentialUserName);
            m_ClientCredentialPassword = m_PasswordTextBox.Text;
            Log.DebugFormat("m_ClientCredentialPassword is {0}", m_ClientCredentialPassword);
            m_ServiceDescriptionURL = m_ServiceDescriptionURLTextBox.Text;
            Log.Debug("about to get REST description from " + m_ServiceDescriptionURL);
            this.Text = m_WindowTitle + " - retrieving REST description from " + m_ServiceDescriptionURL;
            try
            {
                if (refresh)
                {
                    this.Controls.Remove(m_MethodsTabControl);
                    if (!string.IsNullOrEmpty(m_Language) && m_Language == "Java")
                    {
                        m_SwaggerProxyGenerator = new SwaggerJavaProxyGenerator();
                        m_SwaggerApiProxySettingsEndPoint = new RESTApiProxySettingsEndPoint("Java");
                    }
                    else
                    {
                        m_SwaggerProxyGenerator = new SwaggerCSharpProxyGenerator();
                        m_SwaggerApiProxySettingsEndPoint = new RESTApiProxySettingsEndPoint("CSharp");
                    }

                    m_SwaggerApiProxySettingsEndPoint.Url = m_ServiceDescriptionURL;
                    Log.DebugFormat("m_ServiceDescriptionURL is {0}", m_ServiceDescriptionURL);
                    RESTApiProxySettingsEndPoint[] endpoints = new RESTApiProxySettingsEndPoint[] { m_SwaggerApiProxySettingsEndPoint };
                    m_SwaggerServiceDefinition = m_SwaggerProxyGenerator.GenerateSourceString(endpoints);
                    Log.DebugFormat("swaggerServiceDefinition EndPoint is {0}", m_SwaggerServiceDefinition.GetEndPoint());
                    this.Text = m_WindowTitle + " - got REST service definition";
                    m_SourceStringArray = m_SwaggerServiceDefinition.GetSourceStrings();
                    if (m_SourceStringArray != null)
                    {
                        foreach (string sourceString in m_SourceStringArray)
                        {
                            Log.DebugFormat("sourceString is {0}", sourceString);
                        }
                    }

                    Log.DebugFormat("endpoint is {0}", m_SwaggerServiceDefinition.GetEndPoint());
                    if (m_SwaggerServiceDefinition.GetProxyClasses().Contains<string>(((string)m_ServicesComboBox.SelectedItem)))
                    {
                        m_ServicesComboBox.SelectedItem = m_SwaggerServiceDefinition.GetProxyClasses().First<string>(pc => pc == ((string)m_ServicesComboBox.SelectedItem));
                    }
                    else
                    {
                        m_ServicesComboBox.SelectedItem = m_SwaggerServiceDefinition.GetProxyClasses().First<string>();
                    }

                    if (string.IsNullOrEmpty(m_Language) || m_Language == "CSharp")
                    {
                        CSharpCodeProvider codeProvider = new CSharpCodeProvider();
                        CompilerParameters compilerParameters = CreateCompilerParameters(m_References);
                        m_CompilerResults = codeProvider.CompileAssemblyFromSource(compilerParameters, m_SourceStringArray);
                        if (!m_CompilerResults.Errors.HasErrors)
                        {
                            m_ServicesComboBox.DataSource = m_SwaggerServiceDefinition.GetProxyClasses();
                        }
                        else
                        {
                            foreach (CompilerError error in m_CompilerResults.Errors)
                            {
                                Log.DebugFormat("compiler error {0}", error.ErrorText);
                            }
                        }

                        object[] args = new object[] { new Uri(m_SwaggerServiceDefinition.GetEndPoint()) };
                        string proxyClass = string.Format("{0}.{1}", m_SwaggerApiProxySettingsEndPoint.Namespace, m_ServicesComboBox.SelectedItem);
                        m_RESTServiceClient = m_CompilerResults.CompiledAssembly.CreateInstance(proxyClass, false, BindingFlags.CreateInstance, null, args, null, null);
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

                    Log.DebugFormat("{0}", m_SwaggerServiceDefinition.GetEndPoint());
                }
                else
                {
                    /* refresh is false, but service or proxy class has changed */
                    if (string.IsNullOrEmpty(m_Language) || m_Language == "CSharp")
                    {
                        CSharpCodeProvider codeProvider = new CSharpCodeProvider();
                        CompilerParameters compilerParameters = CreateCompilerParameters(m_References);
                        m_CompilerResults = codeProvider.CompileAssemblyFromSource(compilerParameters, m_SourceStringArray);
                        if (!m_CompilerResults.Errors.HasErrors)
                        {
                            m_ServicesComboBox.DataSource = m_SwaggerServiceDefinition.GetProxyClasses();
                        }
                        else
                        {
                            foreach (CompilerError error in m_CompilerResults.Errors)
                            {
                                Log.DebugFormat("compiler error {0}", error.ErrorText);
                            }
                        }

                        object[] args = new object[] { new Uri(m_SwaggerServiceDefinition.GetEndPoint()) };
                        string proxyClass = string.Format("{0}.{1}", m_SwaggerApiProxySettingsEndPoint.Namespace, m_ServicesComboBox.SelectedItem);
                        m_RESTServiceClient = m_CompilerResults.CompiledAssembly.CreateInstance(proxyClass, false, BindingFlags.CreateInstance, null, args, null, null);
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

                    Log.DebugFormat("{0}", m_SwaggerServiceDefinition.GetEndPoint());
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

        private void ProcessTimeType(bool refresh)
        {
            Log.Debug("starting ProcessTimeType()");
            ProcessTimeType();
        }

        private void ProcessTimeType()
        {
            Log.Debug("starting ProcessTimeType()");
            this.Controls.Remove(m_ViewRichTextBox);
            Log.DebugFormat("m_Language is {0}", m_Language);
            Log.DebugFormat("m_Type is {0}", m_Type);
            m_ClientCredentialDomain = m_DomainTextBox.Text;
            Log.DebugFormat("m_ClientCredentialDomain is {0}", m_ClientCredentialDomain);
            m_ClientCredentialUserName = m_UsernameTextBox.Text;
            Log.DebugFormat("m_ClientCredentialUserName is {0}", m_ClientCredentialUserName);
            m_ClientCredentialPassword = m_PasswordTextBox.Text;
            Log.DebugFormat("m_ClientCredentialPassword is {0}", m_ClientCredentialPassword);
            m_ServiceDescriptionURL = m_ServiceDescriptionURLTextBox.Text;
            Log.DebugFormat("m_ServiceDescriptionURL is {0}", m_ServiceDescriptionURL);
            this.Text = m_WindowTitle + " - retrieving REST description from " + m_ServiceDescriptionURL;
            try
            {
                ISwaggerProxy swaggerProxy = new TimeSwaggerProxy(new Uri(m_ServiceDescriptionURL), m_ClientCredentialUserName, m_ClientCredentialPassword, m_ClientCredentialDomain);
                string swaggerDocument = swaggerProxy.GetSwaggerDocument();
                Log.DebugFormat("swaggerDocument is {0}", swaggerDocument);
                if (!string.IsNullOrEmpty(m_Language) && m_Language == "Java")
                {
                    m_SwaggerProxyGenerator = new SwaggerJavaProxyGenerator();
                    m_SwaggerApiProxySettingsEndPoint = new RESTApiProxySettingsEndPoint("Java", swaggerProxy.GetType().Name);
                }
                else
                {
                    m_SwaggerProxyGenerator = new SwaggerCSharpProxyGenerator();
                    m_SwaggerApiProxySettingsEndPoint = new RESTApiProxySettingsEndPoint("CSharp", swaggerProxy.GetType().Name);
                    m_SwaggerApiProxySettingsEndPoint.Url = m_ServiceDescriptionURL;
                }

                m_SwaggerApiProxySettingsEndPoint.Url = m_ServiceDescriptionURL;
                RESTApiProxySettingsEndPoint[] endpoints = new RESTApiProxySettingsEndPoint[] { m_SwaggerApiProxySettingsEndPoint };
                m_SwaggerServiceDefinition = m_SwaggerProxyGenerator.GenerateSourceString(m_SwaggerApiProxySettingsEndPoint, swaggerDocument, m_ClientCredentialUserName, m_ClientCredentialPassword, m_ClientCredentialDomain);
                Log.DebugFormat("swaggerServiceDefinition EndPoint is {0}", m_SwaggerServiceDefinition.GetEndPoint());
                this.Text = m_WindowTitle + " - got REST service definition";
                m_SourceStringArray = m_SwaggerServiceDefinition.GetSourceStrings();
                if (m_SourceStringArray != null)
                {
                    if (true)
                    {
                        foreach (string sourceString in m_SourceStringArray)
                        {
                            Log.DebugFormat("sourceString is \n{0}", sourceString);
                        }
                    }
                    else
                    {
                        /* This code to be enabled if you need to dump source to file */
                        //using (System.IO.StreamWriter classDefinitionFile = new System.IO.StreamWriter(@"C:\temp\ClassDefinitions.cs"))
                        //{
                        //    {
                        //        foreach (string sourceString in m_SourceStringArray)
                        //        {
                        //            classDefinitionFile.Write(sourceString);
                        //        }
                        //    }
                        //}
                    }
                }

                Log.DebugFormat("{0}", m_SwaggerServiceDefinition.GetEndPoint());
                string selectedService = (string)m_ServicesComboBox.SelectedItem;
                Log.DebugFormat("selectedService is {0}", selectedService);
                if (m_SwaggerServiceDefinition.GetProxyClasses().Contains<string>(selectedService))
                {
                    Log.DebugFormat("SwaggerCSharpProxyGenerator service definition proxy classes contains {0}", selectedService);
                    m_ServicesComboBox.SelectedItem = m_SwaggerServiceDefinition.GetProxyClasses().First<string>(pc => pc == ((string)m_ServicesComboBox.SelectedItem));
                }
                else
                {
                    Log.DebugFormat("SwaggerCSharpProxyGenerator service definition proxy classes does not contain {0}", selectedService);
                    selectedService = m_SwaggerServiceDefinition.GetProxyClasses().First<string>();
                    Log.DebugFormat("selectedService is {0}", selectedService);
                    m_ServicesComboBox.SelectedItem = selectedService;
                }

                Log.DebugFormat("selectedService is {0}", selectedService);
                m_ServicesComboBox.Refresh();
                if (string.IsNullOrEmpty(m_Language) || m_Language == "CSharp")
                {
                    CSharpCodeProvider codeProvider = new CSharpCodeProvider();
                    CompilerParameters compilerParameters = CreateCompilerParameters(m_References);
                    m_CompilerResults = codeProvider.CompileAssemblyFromSource(compilerParameters, m_SourceStringArray);
                    if (!m_CompilerResults.Errors.HasErrors)
                    {
                        m_ServicesComboBox.DataSource = m_SwaggerServiceDefinition.GetProxyClasses();
                        m_ServicesComboBox.SelectedItem = selectedService;
                        m_ServicesComboBox.Refresh();
                    }
                    else
                    {
                        foreach (CompilerError error in m_CompilerResults.Errors)
                        {
                            Log.DebugFormat("compiler error {0}", error.ErrorText);
                        }
                    }

                    object[] args = new object[] { new Uri(m_SwaggerServiceDefinition.GetEndPoint()) };
                    string proxyClass = string.Format("{0}.{1}", m_SwaggerApiProxySettingsEndPoint.Namespace, selectedService);
                    m_RESTServiceClient = m_CompilerResults.CompiledAssembly.CreateInstance(proxyClass, false, BindingFlags.CreateInstance, null, args, null, null);
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

                Log.DebugFormat("{0}", m_SwaggerServiceDefinition.GetEndPoint());
            }
            catch (Exception e)
            {
                Log.WarnFormat("exception getting Time Swagger document: " + e.Message);
                MessageBox.Show("Exception thrown: " + e.Message);
            }
        }

        private static CompilerParameters CreateCompilerParameters(string[] references)
        {
            Log.Debug("starting CreateCompilerParameters()");
            CompilerParameters compilerParameters = new CompilerParameters(references);
            compilerParameters.GenerateExecutable = false;
            string executingAssemblyLocation = Path.GetDirectoryName(System.Reflection.Assembly.GetExecutingAssembly().Location);
            Log.DebugFormat("executingAssemblyLocation is {0}", executingAssemblyLocation);
            compilerParameters.ReferencedAssemblies.Add(Path.Combine(executingAssemblyLocation, "System.Net.Http.Formatting.dll"));
            compilerParameters.ReferencedAssemblies.Add(Path.Combine(executingAssemblyLocation, "log4net.dll"));
            compilerParameters.ReferencedAssemblies.Add(Path.Combine(executingAssemblyLocation, "Newtonsoft.Json.dll"));
            compilerParameters.ReferencedAssemblies.Add(Path.Combine(executingAssemblyLocation, "XCase.REST.ProxyGenerator.dll"));
            return compilerParameters;
        }

        private void ProcessGoClicked()
        {
            Log.Debug("starting ProcessGoClicked()");
            Log.DebugFormat("m_Language is {0}", m_Language);
            Log.DebugFormat("m_Type is {0}", m_Type);
            if (string.IsNullOrEmpty(m_Type))
            {
                ProcessSOAPType();
            }
            else
            {
                switch(m_Type)
                {
                    case "Integrate":
                        m_TypeComboBox.Text = "Integrate";
                        ProcessIntegrateType();
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

        private void ProcessViewType()
        {
            Log.Debug("starting ProcessViewType()");
            this.Controls.Remove(m_MethodsTabControl);
            m_ServiceDescriptionURL = m_ServiceDescriptionURLTextBox.Text;
            Log.Debug("m_ServiceDescriptionURL is " + m_ServiceDescriptionURL);
            Log.Debug("about to get descriptor from " + m_ServiceDescriptionURL);
            this.Text = m_WindowTitle + " - retrieving descriptor from " + m_ServiceDescriptionURL;
            m_ServiceDescriptor = GetWSDLFromWSDLURL(m_ServiceDescriptionURL, m_ClientCredentialUserName, m_ClientCredentialPassword, m_ClientCredentialDomain);
            if (!string.IsNullOrEmpty(m_ServiceDescriptor))
            {
                this.Text = m_WindowTitle + " - got descriptor";
                m_ViewRichTextBox.Location = new Point(0, (m_TopPanelHeight + m_TopPanelBuffer));
                m_ViewRichTextBox.Width = m_WindowWidth;
                m_ViewRichTextBox.Height = m_WindowHeight - (m_TopPanelHeight + m_TopPanelBuffer);
                try
                {
                    XDocument serviceDescriptorXmlDocument = XDocument.Parse(m_ServiceDescriptor);
                    m_ServiceDescriptor = serviceDescriptorXmlDocument.ToString();
                }
                catch (Exception e)
                {
                    Log.Debug("exception loading service descriptor as XML: " + e.Message);
                    try
                    {
                        object serviceDescriptorJsonDocument = JsonConvert.DeserializeObject(m_ServiceDescriptor);
                        m_ServiceDescriptor = JsonConvert.SerializeObject(serviceDescriptorJsonDocument, Newtonsoft.Json.Formatting.Indented);
                    }
                    catch (Exception f)
                    {
                        Log.Debug("exception loading service descriptor as Json: " + f.Message);
                    }
                }

                m_ViewRichTextBox.Text = m_ServiceDescriptor;
                m_ViewRichTextBox.Multiline = true;
                m_ViewRichTextBox.PerformLayout();
                this.Controls.Add(this.m_ViewRichTextBox);
            }
            else
            {
                MessageBox.Show(string.Format("Failed to get descriptor from {0}", m_ServiceDescriptionURL));
            }

            Log.Debug("finishing ProcessViewType()");
        }

        private void ProcessSOAPType()
        {
            Log.Debug("starting ProcessSOAPType()");
            this.Controls.Remove(m_ViewRichTextBox);
            Log.DebugFormat("m_Language is {0}", m_Language);
            Log.DebugFormat("m_Type is {0}", m_Type);
            m_ServiceDescriptionURL = m_ServiceDescriptionURLTextBox.Text;
            Log.Debug("m_WSDLURL is " + m_ServiceDescriptionURL);
            m_ServiceName = (string)m_ServicesComboBox.SelectedItem;
            m_ClientCredentialDomain = m_DomainTextBox.Text;
            m_ClientCredentialUserName = m_UsernameTextBox.Text;
            m_ClientCredentialPassword = m_PasswordTextBox.Text;
            Log.Debug("about to get WSDL from " + m_ServiceDescriptionURL);
            this.Text = m_WindowTitle + " - retrieving WSDL from " + m_ServiceDescriptionURL;
            m_ServiceDescriptor = GetWSDLFromWSDLURL(m_ServiceDescriptionURL, m_ClientCredentialUserName, m_ClientCredentialPassword, m_ClientCredentialDomain);
            if (!string.IsNullOrEmpty(m_ServiceDescriptor))
            {
                this.Text = m_WindowTitle + " - got WSDL";
                Log.Debug("about to get services from WSDL");
                m_Services = GetServicesFromWSDL();
                this.Text = m_WindowTitle + " - got services";
                m_ServicesComboBox.DataSource = m_Services;
                m_ServicesComboBox.SelectedItem = m_ServiceName;
                Log.Debug("about to get service client from WSDL");
                m_ServiceClient = GetServiceContractClientFromWSDL();
                Log.Debug("got service client from WSDL");
                this.Text = m_WindowTitle + " - got service client";
                Log.Debug("about to re-render service control");
                RerenderServiceControl(m_ServiceClient);
            }

            Log.Debug("finishing ProcessSOAPType()");
        }

        private object GetServiceContractClientFromWSDL()
        {
            return GetServiceContractClientFromWSDL(null);
        }

        private object GetServiceContractClientFromWSDL(string serviceName)
        {
            Log.Debug("starting GetServiceContractClientFromWSDL()");
            object client = null;
            try
            {
                System.Web.Services.Description.ServiceDescription serviceDescription = System.Web.Services.Description.ServiceDescription.Read(new XmlTextReader(new StringReader(m_ServiceDescriptor)));
                MetadataSection section = MetadataSection.CreateFromServiceDescription(serviceDescription);
                MetadataSet metaDocs = new MetadataSet(new MetadataSection[] { section });
                WsdlImporter importer = new WsdlImporter(metaDocs);
                foreach (XmlSchema wsdlSchema in serviceDescription.Types.Schemas)
                {
                    foreach (XmlSchemaObject externalSchema in wsdlSchema.Includes)
                    {
                        if (externalSchema is XmlSchemaImport)
                        {
                            Uri baseUri = new Uri(m_ServiceDescriptionURL);
                            if (string.IsNullOrEmpty((externalSchema as XmlSchemaExternal).SchemaLocation))
                            {
                                continue;
                            }

                            Uri schemaUri = new Uri(baseUri, (externalSchema as XmlSchemaExternal).SchemaLocation);
                            StreamReader streamReader = GetHttpWebResponse(schemaUri.ToString());
                            System.Xml.Schema.XmlSchema schema = XmlSchema.Read(streamReader, null);
                            importer.XmlSchemas.Add(schema);
                        }
                    }
                }

                ServiceContractGenerator serviceContractGenerator = new ServiceContractGenerator();
                ServiceEndpointCollection serviceEndpointCollection = importer.ImportAllEndpoints();
                foreach (ServiceEndpoint serviceEndpoint in serviceEndpointCollection)
                {
                    Log.DebugFormat("serviceEndpoint name is {0}", serviceEndpoint.Name);
                    Log.DebugFormat("serviceEndpoint AbsoluteUri is {0}", serviceEndpoint.Address.Uri.AbsoluteUri);
                }

                m_ServiceEndpoint = serviceEndpointCollection.FirstOrDefault<ServiceEndpoint>().Address;
                IEnumerable<ContractDescription> contractDescriptionEnumerable = importer.ImportAllContracts();
                foreach (ContractDescription contractDescription in contractDescriptionEnumerable)
                {
                    serviceContractGenerator.GenerateServiceContractType(contractDescription);
                    Log.DebugFormat("generated service contract {0}", contractDescription.Name);
                }

                if (serviceContractGenerator.Errors.Count != 0)
                {
                    foreach (MetadataConversionError metadataConversionError in serviceContractGenerator.Errors)
                    {
                        Log.WarnFormat("metadataConversionError message is {0}", metadataConversionError.Message);
                    }

                    throw new Exception("errors generating service contract types");
                }
                else
                {
                    if (contractDescriptionEnumerable != null && contractDescriptionEnumerable.Count<ContractDescription>() > 0)
                    {
                        if (string.IsNullOrEmpty(serviceName))
                        {
                            ContractDescription contractDescription = contractDescriptionEnumerable.FirstOrDefault<ContractDescription>();
                            CodeTypeReference codeTypeReference = serviceContractGenerator.GenerateServiceContractType(contractDescription);
                            /* Remove the "I" from the contract base type */
                            string codeTypeReferenceBaseType = codeTypeReference.BaseType;
                            if (codeTypeReferenceBaseType.StartsWith("I"))
                            {
                                m_ServiceName = string.Format("{0}Client", codeTypeReferenceBaseType.Substring(1));
                            }
                            else
                            {
                                m_ServiceName = string.Format("{0}Client", codeTypeReferenceBaseType);
                            }
                        }
                        else
                        {
                            m_ServiceName = string.Format("{0}Client", serviceName);
                        }
                    }

                    Log.DebugFormat("m_ServiceName is {0}", m_ServiceName);
                    CodeNamespace codeNamespace = new CodeNamespace();
                    CodeCompileUnit codeUnit = new CodeCompileUnit();
                    codeUnit.Namespaces.Add(codeNamespace);
                    CodeDomProvider compiler = CodeDomProvider.CreateProvider("CSharp");
                    string[] references = new string[5] { "System.Web.Services.dll", "System.Xml.dll", "System.ServiceModel.dll", "System.configuration.dll", "System.Runtime.Serialization.dll" };
                    string progFiles = Environment.GetFolderPath(Environment.SpecialFolder.ProgramFiles);
                    CompilerParameters parameters = new CompilerParameters(references);
                    CompilerResults results = compiler.CompileAssemblyFromDom(parameters, serviceContractGenerator.TargetCompileUnit);
                    if (results.Errors.Count != 0)
                    {
                        foreach (CompilerError compilerError in results.Errors)
                        {
                            Log.WarnFormat("compilerError text is {0}", compilerError.ErrorText);
                        }

                        throw new Exception("errors generating service contract types");
                    }
                    else
                    {
                        object[] args = null;
                        if (string.IsNullOrEmpty(m_Binding))
                        {
                            m_Binding = "Basic";
                        }

                        if (string.IsNullOrEmpty(m_SecurityMode))
                        {
                            m_SecurityMode = "None";
                        }

                        if (string.IsNullOrEmpty(m_ClientCredentialType))
                        {
                            m_ClientCredentialType = "None";
                        }

                        if (string.IsNullOrEmpty(m_MessageCredentialType))
                        {
                            m_MessageCredentialType = "None";
                        }

                        if (m_Binding.Equals("Basic"))
                        {
                            Log.DebugFormat("m_Binding is {0}", m_Binding);
                            BasicHttpSecurityMode basicSecurityMode = GetBasicHttpSecurityModeFromString(m_SecurityMode);
                            BasicHttpBinding basicHttpBinding = new BasicHttpBinding(basicSecurityMode);
                            Log.DebugFormat("set basicSecurityMode to {0}", basicSecurityMode);
                            HttpClientCredentialType httpClientCredentialType = GetHttpClientCredentialType(m_ClientCredentialType);
                            if (basicSecurityMode == BasicHttpSecurityMode.Transport)
                            {
                                basicHttpBinding.Security.Transport.ClientCredentialType = httpClientCredentialType;
                                Log.DebugFormat("set Transport.ClientCredentialType to {0}", httpClientCredentialType);
                            }
                            else if (basicSecurityMode == BasicHttpSecurityMode.Message)
                            {
                                basicHttpBinding.Security.Message.ClientCredentialType = BasicHttpMessageCredentialType.UserName;
                                Log.DebugFormat("set Security.Message.ClientCredentialType to {0}", BasicHttpMessageCredentialType.UserName);
                            }
                            else if (basicSecurityMode == BasicHttpSecurityMode.TransportWithMessageCredential)
                            {
                                basicHttpBinding.Security.Message.ClientCredentialType = BasicHttpMessageCredentialType.UserName;
                                Log.DebugFormat("set Security.Message.ClientCredentialType to {0}", BasicHttpMessageCredentialType.UserName);
                            }
                            else if (basicSecurityMode == BasicHttpSecurityMode.TransportCredentialOnly)
                            {
                                basicHttpBinding.Security.Transport.ClientCredentialType = httpClientCredentialType;
                                Log.DebugFormat("set Security.Transport.ClientCredentialType to {0}", httpClientCredentialType);
                            }
                            else
                            {
                                Log.DebugFormat("basicSecurityMode is {0}", basicSecurityMode);
                            }

                            basicHttpBinding.MaxReceivedMessageSize = Int32.MaxValue;
                            args = new object[] { basicHttpBinding, m_ServiceEndpoint };
                        }
                        else
                        {
                            Log.DebugFormat("m_Binding is {0}", m_Binding);
                            SecurityMode wsSecurityMode = GetSecurityModeFromString(m_SecurityMode);
                            WSHttpBinding wsHttpBinding = new WSHttpBinding(wsSecurityMode);
                            HttpClientCredentialType httpClientCredentialType = GetHttpClientCredentialType(m_ClientCredentialType);
                            MessageCredentialType messageCredentialType = GetMessageCredentialType(m_MessageCredentialType);
                            if (wsSecurityMode == SecurityMode.Transport)
                            {
                                wsHttpBinding.Security.Transport.ClientCredentialType = httpClientCredentialType;
                                Log.DebugFormat("set Security.Transport.ClientCredentialType to {0}", httpClientCredentialType);
                            }
                            else if (wsSecurityMode == SecurityMode.Message)
                            {
                                wsHttpBinding.Security.Message.ClientCredentialType = messageCredentialType;
                                Log.DebugFormat("set Security.Message.ClientCredentialType to {0}", messageCredentialType);
                            }
                            else if (wsSecurityMode == SecurityMode.TransportWithMessageCredential)
                            {
                                wsHttpBinding.Security.Message.ClientCredentialType = messageCredentialType;
                                Log.DebugFormat("set Security.Message.ClientCredentialType to {0}", messageCredentialType);
                            }
                            else
                            {
                                Log.DebugFormat("wsSecurityMode is {0}", wsSecurityMode);
                            }

                            wsHttpBinding.MaxReceivedMessageSize = Int32.MaxValue;
                            args = new object[] { wsHttpBinding, m_ServiceEndpoint };
                        }

                        client = results.CompiledAssembly.CreateInstance(m_ServiceName, true, BindingFlags.CreateInstance, null, args, null, null);
                        if (client != null)
                        {
                            NetworkCredential networkCredential = new NetworkCredential(m_ClientCredentialUserName, m_ClientCredentialPassword, m_ClientCredentialDomain);
                            Type clientType = client.GetType();
                            Log.DebugFormat("client type is {0}", clientType.Name);
                            PropertyInfo clientCredsPropertyInfo = clientType.GetProperty("ClientCredentials");
                            ClientCredentials creds = (ClientCredentials)clientCredsPropertyInfo.GetValue(client, null);
                            if (!string.IsNullOrEmpty(m_ClientCredentialDomain))
                            {
                                creds.UserName.UserName = string.Format("{0}\\{1}", m_ClientCredentialDomain, m_ClientCredentialUserName);
                            }
                            else
                            {
                                creds.UserName.UserName = m_ClientCredentialUserName;
                            }

                            creds.UserName.Password = m_ClientCredentialPassword;
                            PropertyInfo windowsCredsPropertyInfo = creds.GetType().GetProperty("Windows");
                            WindowsClientCredential windowsCreds = (WindowsClientCredential)windowsCredsPropertyInfo.GetValue(creds, null);
                            PropertyInfo allowNtlmPropertyInfo = windowsCreds.GetType().GetProperty("AllowNtlm");
                            allowNtlmPropertyInfo.SetValue(windowsCreds, true, null);
                            PropertyInfo clientCredentialPropertyInfo = windowsCreds.GetType().GetProperty("ClientCredential");
                            clientCredentialPropertyInfo.SetValue(windowsCreds, networkCredential, null);
                            PropertyInfo allowedImpersonationLevelPropertyInfo = windowsCreds.GetType().GetProperty("AllowedImpersonationLevel");
                            allowedImpersonationLevelPropertyInfo.SetValue(windowsCreds, System.Security.Principal.TokenImpersonationLevel.Impersonation, null);
                        }
                    }
                }

                return client;
            }
            catch (Exception e)
            {
                MessageBox.Show("Exception thrown: " + e.Message);
                return client;
            }
        }

        private MessageCredentialType GetMessageCredentialType(string messageCredentialTypeString)
        {
            MessageCredentialType messageCredentialType = MessageCredentialType.None;
            if (System.Enum.TryParse<MessageCredentialType>(messageCredentialTypeString, out messageCredentialType))
            {
                return messageCredentialType;
            }
            else
            {
                throw new Exception(string.Format("Exception parsing messageCredentialType", messageCredentialTypeString));
            }
        }

        private HttpClientCredentialType GetHttpClientCredentialType(string clientCredentialType)
        {
            HttpClientCredentialType httpClientCredentialType = HttpClientCredentialType.None;
            if (System.Enum.TryParse<HttpClientCredentialType>(clientCredentialType, out httpClientCredentialType))
            {
                return httpClientCredentialType;
            }
            else
            {
                throw new Exception(string.Format("Exception parsing clientCredentialType", clientCredentialType));
            }
        }

        private static BasicHttpSecurityMode GetBasicHttpSecurityModeFromString(string securityMode)
        {
            BasicHttpSecurityMode basicHttpSecurityMode = BasicHttpSecurityMode.None;
            if (System.Enum.TryParse<BasicHttpSecurityMode>(securityMode, out basicHttpSecurityMode))
            {
                return basicHttpSecurityMode;
            }
            else
            {
                throw new Exception(string.Format("Exception parsing BasicHttpSecurityMode", securityMode));
            }
        }

        private static SecurityMode GetSecurityModeFromString(string securityMode)
        {
            SecurityMode serviceModelSecurityMode = SecurityMode.None;
            if (System.Enum.TryParse<SecurityMode>(securityMode, out serviceModelSecurityMode))
            {
                return serviceModelSecurityMode;
            }
            else
            {
                throw new Exception(string.Format("Exception parsing SecurityMode {0}", securityMode));
            }
        }

        private StreamReader GetHttpWebResponse(string url)
        {
            if (String.IsNullOrEmpty(url))
            {
                throw new Exception("url Cannot be empty");
            }

            HttpWebRequest httpWebRequest = (HttpWebRequest)WebRequest.Create(url);
            httpWebRequest.Timeout = 30000;
            HttpWebResponse httpWebResponse = (HttpWebResponse)httpWebRequest.GetResponse();
            Encoding encoding = Encoding.GetEncoding(1252);
            return new StreamReader(httpWebResponse.GetResponseStream(), encoding);
        }

        private void ProcessServicesChange()
        {
            Log.Debug("starting ProcessServicesChange()");
            Log.DebugFormat("m_Language is {0}", m_Language);
            Log.DebugFormat("m_Type is {0}", m_Type);
            if (string.IsNullOrEmpty(m_Type) || m_Type == "SOAP")
            {
                m_ServiceName = (string)m_ServicesComboBox.SelectedValue;
                Log.Debug("service name changed to " + (string)m_ServicesComboBox.SelectedValue);
                if (string.IsNullOrEmpty(m_ServiceName))
                {
                    Log.Debug("service name is empty");
                }
                else
                {
                    Log.Debug("about to get service contract client from WSDL");
                    m_ServiceClient = GetServiceContractClientFromWSDL(m_ServiceName);
                    Log.Debug("got service contract client from WSDL");
                }
            }
            else if (m_Type == "Integrate")
            {
                m_ServiceName = (string)m_ServicesComboBox.SelectedValue;
                Log.Debug("service name changed to " + (string)m_ServicesComboBox.SelectedValue);
                ProcessIntegrateType(false);
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
                ProcessPlatformCDSType(false);
            }
            else if (m_Type == "PlatformDocument")
            {
                m_ServiceName = (string)m_ServicesComboBox.SelectedValue;
                Log.Debug("service name changed to " + (string)m_ServicesComboBox.SelectedValue);
                ProcessPlatformDocumentType(false);
            }
            else if (m_Type == "PlatformRefData")
            {
                m_ServiceName = (string)m_ServicesComboBox.SelectedValue;
                Log.Debug("service name changed to " + (string)m_ServicesComboBox.SelectedValue);
                ProcessPlatformRefDataType(false);
            }
            else if (m_Type == "PlatformSanctionLists")
            {
                m_ServiceName = (string)m_ServicesComboBox.SelectedValue;
                Log.Debug("service name changed to " + (string)m_ServicesComboBox.SelectedValue);
                ProcessPlatformSanctionListsType(false);
            }
            else if (m_Type == "PlatformTMS")
            {
                m_ServiceName = (string)m_ServicesComboBox.SelectedValue;
                Log.Debug("service name changed to " + (string)m_ServicesComboBox.SelectedValue);
                ProcessPlatformTMSType(false);
            }
            else if (m_Type == "REST")
            {
                m_ServiceName = (string)m_ServicesComboBox.SelectedValue;
                Log.Debug("service name changed to " + (string)m_ServicesComboBox.SelectedValue);
                ProcessSwaggerType(false);
            }
            else if (m_Type == "Time")
            {
                m_ServiceName = (string)m_ServicesComboBox.SelectedValue;
                Log.Debug("service name changed to " + (string)m_ServicesComboBox.SelectedValue);
                ProcessTimeType(false);
            }
        }

        private MainMenu CreateMainMenu()
        {
            MainMenu mainMenu = new MainMenu();
            MenuItem fileMenuItem = new MenuItem("&File");
            MenuItem openMenuItem = new MenuItem("&Open");
            openMenuItem.Click += new EventHandler(FileOpen_Click);
            MenuItem saveMenuItem = new MenuItem("&Save");
            saveMenuItem.Click += new EventHandler(FileSave_Click);
            MenuItem saveAsMenuItem = new MenuItem("&Save As");
            saveAsMenuItem.Click += new EventHandler(FileSaveAs_Click);
            MenuItem loadMenuItem = new MenuItem("&Load");
            loadMenuItem.Click += new EventHandler(FileLoad_Click);
            MenuItem exitMenuItem = new MenuItem("&Exit");
            exitMenuItem.Click += new EventHandler(FileExit_Click);
            MenuItem settingsMenuItem = new MenuItem("&Settings");
            MenuItem proxyMenuItem = new MenuItem("&Proxy");
            proxyMenuItem.Click += new EventHandler(Proxy_Click);
            MenuItem interfaceMenuItem = new MenuItem("&" + XCaseServiceClient.Properties.Resources.Appearance);
            interfaceMenuItem.Click += new EventHandler(Interface_Click);
            MenuItem securityProtocolMenuItem = new MenuItem("&Security Protocol");
            securityProtocolMenuItem.Click += new EventHandler(Security_Protocol_Click);
            MenuItem transportMenuItem = new MenuItem("&Transport");
            transportMenuItem.Click += new EventHandler(Transport_Click);
            MenuItem helpMenuItem = new MenuItem("&Help");
            MenuItem viewHelpMenuItem = new MenuItem("&View Help");
            viewHelpMenuItem.Click += new EventHandler(HelpViewHelp_Click);
            MenuItem aboutMenuItem = new MenuItem("&About");
            aboutMenuItem.Click += new EventHandler(HelpAbout_Click);
            mainMenu.MenuItems.Add(fileMenuItem);
            fileMenuItem.MenuItems.Add(openMenuItem);
            fileMenuItem.MenuItems.Add(saveMenuItem);
            fileMenuItem.MenuItems.Add(saveAsMenuItem);
            fileMenuItem.MenuItems.Add(loadMenuItem);
            fileMenuItem.MenuItems.Add(exitMenuItem);
            mainMenu.MenuItems.Add(settingsMenuItem);
            settingsMenuItem.MenuItems.Add(proxyMenuItem);
            settingsMenuItem.MenuItems.Add(interfaceMenuItem);
            settingsMenuItem.MenuItems.Add(securityProtocolMenuItem);
            settingsMenuItem.MenuItems.Add(transportMenuItem);
            mainMenu.MenuItems.Add(helpMenuItem);
            helpMenuItem.MenuItems.Add(viewHelpMenuItem);
            helpMenuItem.MenuItems.Add(aboutMenuItem);
            return mainMenu;
        }

        private string GetWSDLFromFile(string fileName)
        {
            Log.Debug("starting GetWSDLFromFile()");
            try
            {
                Log.DebugFormat("fileName is {0}", fileName);
                using (FileStream fileStream = new FileStream(fileName, FileMode.Open))
                {
                    StreamReader fileStreamReader = new StreamReader(fileStream);
                    string wsdlString = fileStreamReader.ReadToEnd();
                    Log.Debug("finishing GetWSDLFromFile()");
                    return wsdlString;
                }
            }
            catch (Exception e)
            {
                Log.DebugFormat("exception getting WSDL from file: {0}", e.Message);
                if (!m_Starting)
                {
                    MessageBox.Show("Exception thrown: " + e.Message);
                }

                return null;
            }
        }

        private string GetWSDLFromWSDLURL(string wsdlURL, string username, string password, string domain)
        {
            Log.Debug("starting GetWSDLFromURL()");
            try
            {
                WebRequest webRequest = GetHttpWebRequestFromURL(wsdlURL, username, password, domain);
                using (Stream webResponseStream = webRequest.GetResponse().GetResponseStream())
                {
                    StreamReader webResponseStreamReader = new StreamReader(webResponseStream);
                    string webResponseString = webResponseStreamReader.ReadToEnd();
                    Log.Debug("finishing GetWSDLFromURL()");
                    return webResponseString;
                }
            }
            catch (Exception e)
            {
                Log.DebugFormat("exception getting WSDL from URL: {0}", e.Message);
                if (!m_Starting)
                {
                    MessageBox.Show(e.Message);
                }

                return null;
            }
        }

        private object GetServiceClientFromStream(Stream stream)
        {
            Log.Debug("starting GetServiceClientFromStream()");
            System.Web.Services.Description.ServiceDescription serviceDescription = System.Web.Services.Description.ServiceDescription.Read(stream);
            ServiceDescriptionImporter serviceDescriptionImporter = new ServiceDescriptionImporter();
            serviceDescriptionImporter.Style = ServiceDescriptionImportStyle.Client;
            serviceDescriptionImporter.ProtocolName = "SOAP";
            serviceDescriptionImporter.CodeGenerationOptions = CodeGenerationOptions.GenerateProperties;
            serviceDescriptionImporter.AddServiceDescription(serviceDescription, null, null);
            foreach (XmlSchema wsdlSchema in serviceDescription.Types.Schemas)
            {
                Log.Debug("next wsdlSchema");
                foreach (XmlSchemaObject externalSchema in wsdlSchema.Includes)
                {
                    Log.Debug("next externalSchema");
                    if (externalSchema is XmlSchemaImport)
                    {
                        Log.Debug("externalSchema is XmlSchemaImport");
                        Uri schemaUri = null;
                        if (!string.IsNullOrEmpty(m_ServiceDescriptionURL))
                        {
                            WebClient webClient = new WebClient();
                            Uri baseUri = new Uri(m_ServiceDescriptionURL);
                            schemaUri = new Uri(baseUri, ((XmlSchemaExternal)externalSchema).SchemaLocation);
                            Log.DebugFormat("schemaUri is {0}", schemaUri.AbsoluteUri);
                            Stream schemaStream = webClient.OpenRead(schemaUri);
                            XmlSchema schema = XmlSchema.Read(schemaStream, null);
                            schemaStream.Close();
                            serviceDescriptionImporter.Schemas.Add(schema);
                        }
                        else
                        {
                            //schemaUri = new Uri(((XmlSchemaExternal)externalSchema).SchemaLocation);
                        }
                    }
                }
            }

            CodeNamespace codeNamespace = new CodeNamespace();
            CodeCompileUnit codeCompileUnit = new CodeCompileUnit();
            codeCompileUnit.Namespaces.Add(codeNamespace);
            serviceDescriptionImporter.Import(codeNamespace, codeCompileUnit);
            CodeDomProvider codeDomProvider = CodeDomProvider.CreateProvider("CSharp");
            string[] references = new string[4] { "System.Data.dll", "System.ServiceModel.dll", "System.Web.Services.dll", "System.Xml.dll" };
            CompilerParameters compilerParameters = new CompilerParameters(references);
            CompilerResults compilerResults = codeDomProvider.CompileAssemblyFromDom(compilerParameters, codeCompileUnit);
            foreach (CompilerError compilerError in compilerResults.Errors)
            {
                Log.Debug("compiler error: " + compilerError.ToString());
                throw new Exception("Compilation error creating assembly: " + compilerError.ErrorNumber + ": " + compilerError.ErrorText);
            }

            object client = null;
            if (m_Services.Contains<string>(m_ServiceName))
            {
                Log.Debug("service name is in list of services " + m_ServiceName);
                try
                {
                    client = compilerResults.CompiledAssembly.CreateInstance(m_ServiceName);
                }
                catch (Exception e)
                {
                    Log.Debug("exception creating assembly instance: " + e.Message);
                }
            }
            else
            {
                Log.DebugFormat("services do not contain {0}", m_ServiceName);
            }

            if (client != null)
            {
                NetworkCredential networkCredential = new NetworkCredential(m_ClientCredentialUserName, m_ClientCredentialPassword, m_ClientCredentialDomain);
                ((SoapHttpClientProtocol)client).Credentials = networkCredential;
            }
            else
            {
                Log.Debug("client is null");
            }

            Log.Debug("finishing GetServiceClientFromStream()");
            return (SoapHttpClientProtocol)client;
        }

        /// <summary>
        /// This method gets the services listed in the WSDL stored in m_WSDL.
        /// </summary>
        /// <returns>The string array of service names.</returns>
        private string[] GetServicesFromWSDL()
        {
            Log.Debug("starting GetServicesFromWSDL()");
            try
            {
                using (MemoryStream memoryStream = new MemoryStream(Encoding.UTF8.GetBytes(m_ServiceDescriptor)))
                {
                    string[] services = GetServicesFromStream(memoryStream);
                    Log.Debug("finishing GetServicesFromWSDL()");
                    return services;
                }
            }
            catch (Exception e)
            {
                Log.Debug("exception getting services from WSDL: " + e.Message);
                if (!m_Starting)
                {
                    MessageBox.Show(e.Message);
                }

                return new string[0];
            }
        }

        private string[] GetServicesFromStream(Stream stream)
        {
            Log.Debug("starting GetServicesFromStream()");
            try
            {
                System.Web.Services.Description.ServiceDescription serviceDescription = System.Web.Services.Description.ServiceDescription.Read(stream);
                stream.Close();
                stream.Dispose();
                Log.Debug("closed stream");
                ServiceCollection serviceCollection = serviceDescription.Services;
                List<string> serviceList = new List<string>();
                foreach (Service service in serviceCollection)
                {
                    string serviceName = service.Name;
                    serviceList.Add(serviceName);
                    Log.Debug("service name is " + serviceName);
                }

                string[] serviceArray = serviceList.ToArray();
                if (serviceArray.Length > 0)
                {
                    m_ServiceName = serviceArray[0];
                    Log.Debug("m_ServiceName set to " + m_ServiceName);
                }

                Log.Debug("finishing GetServicesFromStream()");
                return serviceArray;
            }
            catch (Exception e)
            {
                Log.Debug("exception getting services from stream: " + e.Message);
                if (!m_Starting)
                {
                    MessageBox.Show(e.Message);
                }

                throw e;
            }
        }

        private string[] GetServicesFromFile(string fileName)
        {
            Log.Debug("starting GetServicesFromFile()");
            try
            {
                using (FileStream fileStream = new FileStream(fileName, FileMode.Open))
                {
                    string[] services = GetServicesFromStream(fileStream);
                    Log.Debug("finishing GetServicesFromFile()");
                    return services;
                }
            }
            catch (Exception e)
            {
                Log.Debug("exception getting services from file: " + e.Message);
                if (!m_Starting)
                {
                    MessageBox.Show(e.Message);
                }

                throw e;
            }
        }

        private string[] GetServicesFromWSDLURL(string wsdlURL, string username, string password, string domain)
        {
            Log.Debug("starting GetServicesFromWSDLURL()");
            try
            {
                if (string.IsNullOrEmpty(wsdlURL))
                {
                    Log.Debug("wsdlURL is empty");
                    return new string[0];
                }

                WebRequest webRequest = GetHttpWebRequestFromURL(wsdlURL, username, password, domain);
                using (Stream webResponseStream = webRequest.GetResponse().GetResponseStream())
                {
                    string[] services = GetServicesFromStream(webResponseStream);
                    Log.Debug("finishing GetServicesFromWSDLURL()");
                    return services;
                }
            }
            catch (Exception e)
            {
                Log.Debug("exception getting services from WSDL: " + e.Message);
                if (!m_Starting)
                {
                    MessageBox.Show(e.Message);
                }

                return new string[0];
            }
        }

        private HttpWebRequest GetHttpWebRequestFromURL(string wsdlURL, string username, string password, string domain)
        {
            Log.DebugFormat("wsdlURL is {0}", wsdlURL);
            HttpWebRequest webRequest = WebRequest.CreateHttp(wsdlURL);
            Log.DebugFormat("created Web request using {0}", wsdlURL);
            webRequest.Credentials = new NetworkCredential(username, password, domain);
            webRequest.KeepAlive = false;
            ServicePointManager.Expect100Continue = false;
            ServicePointManager.MaxServicePointIdleTime = 10000;
            ServicePointManager.SecurityProtocol = SecurityProtocolType.Tls11;
            ServicePointManager.ServerCertificateValidationCallback = new RemoteCertificateValidationCallback(AcceptAllCertifications);
            return webRequest;
        }

        private object GetServiceClientFromWSDLURL(string wsdlURL, string username, string password, string domain)
        {
            Log.Debug("starting GetServiceClientFromWSDLURL()");
            try
            {
                if (string.IsNullOrEmpty(wsdlURL))
                {
                    Log.Debug("wsdlURL is empty");
                    return new string[0];
                }

                WebRequest webRequest = GetHttpWebRequestFromURL(wsdlURL, username, password, domain);
                using (Stream webResponseStream = webRequest.GetResponse().GetResponseStream())
                {
                    object client = GetServiceClientFromStream(webResponseStream);
                    Log.Debug("finishing GetServiceClientFromWSDLURL()");
                    return client;
                }
            }
            catch (Exception e)
            {
                Log.Debug("exception getting serviceclient from WSDL URL: " + e.Message);
                if (!m_Starting)
                {
                    MessageBox.Show(e.Message);
                }

                return null;
            }
        }

        private object GetServiceClientFromFile(string fileName)
        {
            Log.Debug("starting GetServiceClientFromFile()");
            try
            {
                m_ServiceDescriptor = File.ReadAllText(fileName);
                object client = GetServiceContractClientFromWSDL();
                Log.Debug("finishing GetServiceClientFromFile()");
                return client;
            }
            catch (Exception e)
            {
                Log.Debug("exception getting service client from file: " + e.Message);
                if (!m_Starting)
                {
                    MessageBox.Show(e.Message);
                }

                return null;
            }
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
                m_MethodsTabControl.Location = new Point(0, m_TopPanelHeight);
                m_MethodsTabControl.Multiline = multiline;
                m_MethodsTabControl.Name = "MethodsTabControl";
                m_MethodsTabControl.Size = new Size(m_WindowWidth, m_WindowHeight - (m_TopPanelHeight + m_TabPanelBuffer));
                m_MethodsTabControl.BackColor = Color.LightSteelBlue;
                /* Get public methods of Web service */
                MethodInfo[] allPublicMethodInfoArray = clientType.GetMethods(BindingFlags.Public | BindingFlags.Instance | BindingFlags.DeclaredOnly);
                IEnumerable<MethodInfo> methodArray = allPublicMethodInfoArray.Where(m => !m.IsConstructor).OrderBy(m => m.Name);
                foreach (MethodInfo methodInfo in methodArray)
                {
                    Log.DebugFormat("** Method is " + methodInfo.Name + " **");
                    this.Text = m_WindowTitle + " - rendering " + methodInfo.Name;
                    TabPage methodTabPage = CreateXCaseTabPageForMethod(client, methodInfo);
                    Log.DebugFormat("created methodTabPage");
                    m_MethodsTabControl.Controls.Add(methodTabPage);
                    Log.DebugFormat("added methodTabPage");
                    methodTabPage.Select();
                    Log.DebugFormat("selected methodTabPage");
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
            methodXCaseTabPage.client = client;
            methodXCaseTabPage.methodInfo = methodInfo;
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
            object client = xcaseTabPage.client;
            MethodInfo methodInfo = xcaseTabPage.methodInfo;
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
            requestTableLayoutPanel.ControlRemoved += delegate(object sender, ControlEventArgs cea)
            {
                requestTableLayoutPanel.PerformLayout();
            };
            Label headerLabel = new Label();
            headerLabel.Text = "Enter data here:";
            requestTableLayoutPanel.Controls.Add(headerLabel, 0, 0);
            Type returnType = methodInfo.ReturnType;
            Log.DebugFormat("returnType is {0}", returnType);
            Log.DebugFormat("returnType name is {0}", returnType.Name);
            Label returnLabel = new Label();
            returnLabel.AutoSize = true;
            string returnLabelText = "Returns: " + returnType.Name;
            Log.DebugFormat("returnLabelText is {0}", returnLabelText);
            returnLabel.Text = returnLabelText;
            requestTableLayoutPanel.Controls.Add(returnLabel, 1, 0);
            for (int i = 0; i < parameterInfoArray.Length; i++)
            {
                ParameterInfo parameterInfo = parameterInfoArray[i];
                Log.DebugFormat("parameter name is {0}", parameterInfo.Name);
                if (parameterInfo.IsOut)
                {
                    continue;
                }

                /* For each input parameter, create a parameter object */
                Log.DebugFormat("parameter type is {0}", parameterInfo.ParameterType);
                object parameterObject = ObjectFactory.CreateDefaultObject(parameterInfo.ParameterType);
                if (parameterObject != null)
                {
                    Log.DebugFormat("parameter object type is {0}", parameterObject.GetType());
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
            submitButton.MouseClick += delegate(object o, MouseEventArgs mev)
            {
                Log.Debug("Submit button clicked");
                this.Text = m_WindowTitle + " - invoking " + methodInfo.Name;
                Cursor.Current = Cursors.WaitCursor;
                Log.DebugFormat("parameter value array length is {0}", parameterValueArray.Length);
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
                        Log.DebugFormat("result object type is {0}", resultObject.GetType());
                    }
                    else
                    {
                        Log.Debug("result object is null");
                        if (HasOutParameter(parameterInfoArray))
                        {
                            resultObject = GetOutParameter(parameterInfoArray, parameterValueArray);
                            if (resultObject != null)
                            {
                                Log.DebugFormat("result object type is {0}", resultObject.GetType());
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
            Log.DebugFormat("parameterValueArray length is {0}", parameterValueArray.Length);
            for (int i = 0; i < parameterValueArray.Length; i++ )
            {
                object parameterValue = parameterValueArray[i];
                if (parameterValue != null)
                {
                    Log.Debug("parameterValue is not null");
                    Log.DebugFormat("parameterValue type is {0}", parameterValue.GetType());
                    if (ObjectFactory.IsArrayType(parameterValue.GetType()))
                    {
                        Log.DebugFormat("parameterValue type is array");
                        if (((Array)parameterValue).Length > 0)
                        {
                            object firstArrayValue = (parameterValue as Array).GetValue(0);
                            if (firstArrayValue != null)
                            {
                                Log.DebugFormat("firstArrayValue type is {0}", firstArrayValue.GetType());
                                Log.DebugFormat("firstArrayValue is {0}", firstArrayValue);
                            }
                        }
                    }
                    else if (ObjectFactory.IsListType(parameterValue.GetType()))
                    {
                        Log.DebugFormat("parameterValue type is list");
                        if ((parameterValue as IList).Count > 0)
                        {
                            object firstListValue = (parameterValue as IList)[0];
                            if (firstListValue != null)
                            {
                                Log.DebugFormat("firstListValue type is {0}", firstListValue.GetType());
                                Log.DebugFormat("firstListValue is {0}", firstListValue);
                            }
                        }
                    }
                    else
                    {
                        Log.DebugFormat("parameterValue is {0}", parameterValue);
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
                Log.DebugFormat("parameter type is {0}", parameterValue.GetType());
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
                    Log.DebugFormat("parameter value array length is {0}", ((Array)parameterValue).Length);
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
                Log.DebugFormat("parameterValue is null");
            }
        }

        private void RenderParameterObject(int i, TableLayoutPanel requestTableLayoutPanel, ParameterInfo parameterInfo, ParameterInfo[] parameterInfoArray, object parameterObject, object[] parameterValueArray)
        {
            Log.DebugFormat("starting RenderParameterObject()");
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
                        ((XCaseDateTime)parameterObject).date.RenderDateTime(requestTableLayoutPanel, parameterValueArray, i);
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
                        textBox.TextChanged += delegate(object sender, EventArgs e)
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
                        textBox.TextChanged += delegate(object sender, EventArgs e)
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
                        checkBox.CheckedChanged += delegate(object sender, EventArgs e)
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
                        comboBox.SelectedIndexChanged += delegate(object sender, EventArgs e)
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
                        textBox.TextChanged += delegate(object sender, EventArgs e)
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
                    comboBox.SelectedIndexChanged += delegate(object sender, EventArgs e)
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
            nullButton.MouseClick += delegate(object o, MouseEventArgs mev)
            {
                parameterObject = null;
                parameterValueArray[i] = null;
            };
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
        public object propertyObject { get; set; }
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
        private static readonly ILog Log = log4net.LogManager.GetLogger(MethodBase.GetCurrentMethod().DeclaringType);

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
            Log.DebugFormat("object is {0}", dateTime);
            try
            {
                Value = (DateTime)dateTime;
            }
            catch (Exception)
            {
                Value = DateTime.Now;
            }

            Log.DebugFormat("value is {0}", Value);
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
        private static readonly ILog Log = log4net.LogManager.GetLogger(MethodBase.GetCurrentMethod().DeclaringType);

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
            Log.DebugFormat("object is {0}", timeSpan);
            try
            {
                Value = (TimeSpan)timeSpan;
            }
            catch (Exception)
            {
                Value = TimeSpan.Zero;
            }

            Log.DebugFormat("value is {0}", Value);
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
        public object propertyObject { get; set; }
        public int index { get; set; }
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
        public DateTime date { get; set; }
        public TimeSpan timeOfDay { get; set; }

        public XCaseDateTime()
        {
            date = DateTime.Now.Date;
            timeOfDay = DateTime.Now.TimeOfDay;
        }

        public XCaseDateTime(DateTime dateTime)
        {
            date = dateTime.Date;
            timeOfDay = dateTime.TimeOfDay;
        }

        public DateTime GetDateTime()
        {
            return new DateTime(date.Year, date.Month, date.Day, timeOfDay.Hours, timeOfDay.Minutes, timeOfDay.Seconds);
        }
    }

    public class XCaseTabPage : TabPage
    {
        public object client { get; set; }
        public MethodInfo methodInfo { get; set; }

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
