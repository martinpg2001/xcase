using System;
using System.Drawing;
using System.Drawing.Text;
using System.IO;
using System.Net;
using System.Reflection;
using System.Text;
using System.Windows.Forms;
using System.Xml;

namespace XCaseServiceClient
{
    partial class XCaseServiceClientForm
    {
        /// <summary>
        ///  Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        string appDataDirectoryString = Environment.GetEnvironmentVariable("APPDATA");

        /// <summary>
        ///  Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }

            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        ///  Required method for Designer support - do not modify
        ///  the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Text = "XCase Service Client";
            MenuStrip menuStrip = CreateMainMenu();
            this.MainMenuStrip = menuStrip;
            this.Controls.Add(menuStrip);
        }


        private MenuStrip CreateMainMenu()
        {
            MenuStrip menuStrip = new MenuStrip();
            ToolStripMenuItem fileMenuItem = new ToolStripMenuItem("&File");
            ToolStripMenuItem openMenuItem = new ToolStripMenuItem("&Open");
            openMenuItem.Click += new EventHandler(FileOpen_Click);
            ToolStripMenuItem saveMenuItem = new ToolStripMenuItem("&Save");
            saveMenuItem.Click += new EventHandler(FileSave_Click);
            ToolStripMenuItem saveAsMenuItem = new ToolStripMenuItem("&Save As");
            saveAsMenuItem.Click += new EventHandler(FileSaveAs_Click);
            ToolStripMenuItem loadMenuItem = new ToolStripMenuItem("&Load");
            loadMenuItem.Click += new EventHandler(FileLoad_Click);
            ToolStripMenuItem exitMenuItem = new ToolStripMenuItem("&Exit");
            exitMenuItem.Click += new EventHandler(FileExit_Click);
            ToolStripMenuItem settingsMenuItem = new ToolStripMenuItem("&Settings");
            ToolStripMenuItem proxyMenuItem = new ToolStripMenuItem("&Proxy");
            proxyMenuItem.Click += new EventHandler(Proxy_Click);
            ToolStripMenuItem interfaceMenuItem = new ToolStripMenuItem("&Appearance");
            interfaceMenuItem.Click += new EventHandler(Interface_Click);
            ToolStripMenuItem securityProtocolMenuItem = new ToolStripMenuItem("&Security Protocol");
            securityProtocolMenuItem.Click += new EventHandler(Security_Protocol_Click);
            ToolStripMenuItem transportMenuItem = new ToolStripMenuItem("&Transport");
            transportMenuItem.Click += new EventHandler(Transport_Click);
            ToolStripMenuItem helpMenuItem = new ToolStripMenuItem("&Help");
            ToolStripMenuItem viewHelpMenuItem = new ToolStripMenuItem("&View Help");
            viewHelpMenuItem.Click += new EventHandler(HelpViewHelp_Click);
            ToolStripMenuItem aboutMenuItem = new ToolStripMenuItem("&About");
            aboutMenuItem.Click += new EventHandler(HelpAbout_Click);
            menuStrip.Items.Add(fileMenuItem);
            fileMenuItem.DropDownItems.Add(openMenuItem);
            fileMenuItem.DropDownItems.Add(saveMenuItem);
            fileMenuItem.DropDownItems.Add(saveAsMenuItem);
            fileMenuItem.DropDownItems.Add(loadMenuItem);
            fileMenuItem.DropDownItems.Add(exitMenuItem);
            menuStrip.Items.Add(settingsMenuItem);
            settingsMenuItem.DropDownItems.Add(proxyMenuItem);
            settingsMenuItem.DropDownItems.Add(interfaceMenuItem);
            settingsMenuItem.DropDownItems.Add(securityProtocolMenuItem);
            settingsMenuItem.DropDownItems.Add(transportMenuItem);
            menuStrip.Items.Add(helpMenuItem);
            helpMenuItem.DropDownItems.Add(viewHelpMenuItem);
            helpMenuItem.DropDownItems.Add(aboutMenuItem);
            menuStrip.Dock = DockStyle.Top;
            return menuStrip;
        }

        #endregion

        private void FileOpen_Click(object sender, EventArgs e)
        {
            Log.Debug("starting FileOpen_Click()");
            OpenFileDialog openFileDialog = new OpenFileDialog();
            Log.Debug("m_CurrentDirectory is " + m_CurrentDirectory);
            if (m_CurrentDirectory == null)
            {
                Log.Debug("m_CurrentDirectory is null");
                m_CurrentDirectory = Path.GetDirectoryName(Path.GetDirectoryName(Directory.GetCurrentDirectory()));
                //Properties.Settings.Default.ServicesDirectory = m_CurrentDirectory;
            }

            openFileDialog.InitialDirectory = m_CurrentDirectory;
            Log.Debug("set InitialDirectory");
            openFileDialog.Filter = "Service Files (*.xsvc)|*.xsvc|All Files (*.*)|*.*";
            openFileDialog.Multiselect = false;
            Log.Debug("set Multiselect to false");
            bool? userClickedOK = openFileDialog.ShowDialog() == DialogResult.OK;
            Log.DebugFormat("userClickedOK is {0}", userClickedOK);
            if (userClickedOK == true)
            {
                Log.Debug("user clicked OK");
                m_CurrentDirectory = Path.GetDirectoryName(openFileDialog.FileName);
                Log.Debug("m_CurrentDirectory is " + m_CurrentDirectory);
                Stream fileStream = openFileDialog.OpenFile();
                using (StreamReader reader = new StreamReader(fileStream))
                {
                    try
                    {
                        Config config = XCaseServiceDescription.OpenFromFile(openFileDialog.FileName);
                        SetServicePropertiesFromConfig(config);
                        Log.Debug("loaded service file");
                    }
                    catch (Exception exception)
                    {
                        Log.Warn("exception opening service file: " + exception.Message);
                        MessageBox.Show("Exception opening service file: " + exception.Message);
                    }
                }

                fileStream.Close();
            }
            else
            {
                Log.Debug("user clicked Cancel");
            }

            Log.Debug("finishing FileOpen_Click()");
        }

        private void SetServicePropertiesFromConfig(Config config)
        {
            Log.Debug("starting SetServicePropertiesFromConfig()");
            m_Binding = config.binding;
            Log.DebugFormat("m_Binding is {0}", m_Binding);
            m_BindingLabel.Text = string.Format("Binding: {0}", m_Binding);
            m_ClientCredentialType = config.client;
            Log.DebugFormat("m_ClientCredentialType is {0}", m_ClientCredentialType);
            m_MessageCredentialTypeLabel.Text = string.Format("Client: {0}", m_ClientCredentialType);
            m_ClientCredentialDomain = config.domain;
            m_DomainTextBox.Text = m_ClientCredentialDomain;
            m_ServiceDescriptionURL = config.endpoint;
            Log.DebugFormat("m_ServiceDescriptionURL is {0}", m_ServiceDescriptionURL);
            m_ServiceDescriptionURLTextBox.Text = m_ServiceDescriptionURL;
            m_MessageCredentialType = config.message;
            Log.DebugFormat("m_MessageCredentialType is {0}", m_MessageCredentialType);
            m_MessageCredentialTypeLabel.Text = string.Format("Message: {0}", m_MessageCredentialType);
            m_OnPremise = config.onpremise;
            Log.DebugFormat("m_OnPremise is {0}", m_OnPremise);
            m_SecurityMode = config.security;
            Log.DebugFormat("m_SecurityMode is {0}", m_SecurityMode);
            m_SecurityLabel.Text = string.Format("Message: {0}", m_SecurityMode);
            m_ClientCredentialUserName = config.username;
            m_UsernameTextBox.Text = m_ClientCredentialUserName;
            m_ClientCredentialPassword = config.password;
            m_PasswordTextBox.Text = m_ClientCredentialPassword;
            m_Language = config.language ?? "CSharp";
            Log.DebugFormat("m_Language is {0}", m_Language);
            m_LanguageComboBox.SelectedItem = m_Language;
            m_LanguageComboBox.Text = m_Language;
            m_Timeout = config.timeout;
            m_TimeoutTextBox.Text = Convert.ToString(m_Timeout);
            m_Type = config.type ?? "SOAP";
            Log.DebugFormat("m_Type is {0}", m_Type);
            m_TypeComboBox.SelectedItem = m_Type;
            m_TypeComboBox.Text = m_Type;
            Log.Debug("finishing SetServicePropertiesFromConfig()");
        }

        private void FileSave_Click(object sender, EventArgs e)
        {
            string fileName = appDataDirectoryString + Path.DirectorySeparatorChar + "XCaseService.xsvc";
            using (System.IO.Stream myStream = File.Create(fileName))
            {
                Log.Debug("myStream is not null");
                XmlWriter xmlWriter = new XmlTextWriter(myStream, Encoding.UTF8);
                Config config = SetConfigFromServiceProperties();
                XCaseServiceDescription.SaveToFile(myStream, config);
                myStream.Close();
                Log.Debug("saved service file");
            }

            Log.Debug("saved service file");
        }

        private void FileSaveAs_Click(object sender, EventArgs e)
        {
            Log.Debug("starting FileSaveAs_Click()");
            Stream myStream;
            SaveFileDialog saveFileDialog = new SaveFileDialog();
            saveFileDialog.Filter = "Service Files (*.xsvc)|*.xsvc";
            saveFileDialog.FilterIndex = 2;
            saveFileDialog.RestoreDirectory = true;
            if (saveFileDialog.ShowDialog() == DialogResult.OK)
            {
                if ((myStream = saveFileDialog.OpenFile()) != null)
                {
                    Log.Debug("myStream is not null");
                    XmlWriter xmlWriter = new XmlTextWriter(myStream, Encoding.UTF8);
                    Config config = SetConfigFromServiceProperties();
                    XCaseServiceDescription.SaveToFile(myStream, config);
                    myStream.Close();
                    Log.Debug("saved service file");
                }
            }
        }

        private Config SetConfigFromServiceProperties()
        {
            Config config = new Config();
            config.binding = m_Binding;
            config.client = m_ClientCredentialType;
            config.domain = m_ClientCredentialDomain;
            config.endpoint = m_ServiceDescriptionURL;
            config.language = m_Language;
            config.message = m_MessageCredentialType;
            config.password = m_ClientCredentialPassword;
            config.security = m_SecurityMode;
            config.timeout = m_Timeout;
            config.type = m_Type;
            config.username = m_ClientCredentialUserName;
            return config;
        }

        private void FileLoad_Click(object sender, EventArgs e)
        {
            string fileName = appDataDirectoryString + Path.DirectorySeparatorChar + "XCaseService.xsvc";
            try
            {
                Config config = XCaseServiceDescription.OpenFromFile(fileName);
                SetServicePropertiesFromConfig(config);
                Log.Debug("loaded service file");
            }
            catch (Exception exception)
            {
                Log.Warn("exception opening service file: " + exception.Message);
                MessageBox.Show("Exception opening service file: " + exception.Message);
            }
        }

        private void FileExit_Click(object sender, EventArgs e)
        {
            Environment.Exit(0);
        }

        private void HelpAbout_Click(object sender, EventArgs e)
        {
            MessageBox.Show(string.Format("Version {0}", Assembly.GetExecutingAssembly().GetName().Version.ToString()), "XCase Service Client", MessageBoxButtons.OK);
        }

        private void HelpViewHelp_Click(object sender, EventArgs e)
        {
            WebBrowser webBrowser = new WebBrowser();
            webBrowser.AllowNavigation = false;
            webBrowser.Navigate(Application.StartupPath + "\\Help.html", true);
        }

        private void Interface_Click(object sender, EventArgs e)
        {
            Log.Debug("starting Interface_Click()");
            Form interfaceForm = new Form();
            interfaceForm.StartPosition = FormStartPosition.CenterParent;
            interfaceForm.Text = XCaseServiceClient.Properties.Resources.Appearance;
            TableLayoutPanel interfaceTableLayoutPanel = new TableLayoutPanel();
            interfaceTableLayoutPanel.CellBorderStyle = TableLayoutPanelCellBorderStyle.Single;
            interfaceTableLayoutPanel.RowCount = 4;
            interfaceTableLayoutPanel.ColumnCount = 2;
            /* Color picker */
            Label colorLabel = new Label();
            colorLabel.Text = XCaseServiceClient.Properties.Resources.Color;
            colorLabel.Dock = DockStyle.Fill;
            interfaceTableLayoutPanel.Controls.Add(colorLabel, 0, 0);
            ComboBox colorComboBox = new ComboBox();
            colorComboBox.DataSource = Enum.GetValues(typeof(KnownColor));
            colorComboBox.SelectionChangeCommitted += delegate (object o, EventArgs ev)
            {
                Log.Debug("colorComboBox SelectionChangeCommitted");
                productColor = Color.FromKnownColor((KnownColor)colorComboBox.SelectedItem);
                Log.DebugFormat("m_Color is {0}", productColor);
            };
            interfaceTableLayoutPanel.Controls.Add(colorComboBox, 1, 0);
            /* Font picker */
            Label fontLabel = new Label();
            fontLabel.Text = XCaseServiceClient.Properties.Resources.Font;
            fontLabel.Dock = DockStyle.Fill;
            interfaceTableLayoutPanel.Controls.Add(fontLabel, 0, 1);
            ComboBox fontComboBox = new ComboBox();
            fontComboBox.DataSource = new InstalledFontCollection().Families;
            fontComboBox.SelectionChangeCommitted += delegate (object o, EventArgs ev)
            {
                Log.Debug("fontComboBox SelectionChangeCommitted");
                m_FontFamily = (FontFamily)fontComboBox.SelectedItem;
            };
            fontComboBox.Dock = DockStyle.Fill;
            interfaceTableLayoutPanel.Controls.Add(fontComboBox, 1, 1);
            /* Multiline picker */
            Label multilineLabel = new Label();
            multilineLabel.Text = XCaseServiceClient.Properties.Resources.Multiline;
            multilineLabel.Dock = DockStyle.Fill;
            interfaceTableLayoutPanel.Controls.Add(multilineLabel, 0, 2);
            CheckBox multilineCheckBox = new CheckBox();
            multilineCheckBox.Checked = multiline;
            multilineCheckBox.CheckedChanged += delegate (object o, EventArgs ev)
            {
                Log.Debug("multilineCheckBox CheckedChanged");
            };
            interfaceTableLayoutPanel.Controls.Add(multilineCheckBox, 1, 2);
            /* Buttons row */
            Button okButton = new Button();
            okButton.Text = "OK";
            interfaceTableLayoutPanel.Controls.Add(okButton, 0, 3);
            Button cancelButton = new Button();
            cancelButton.Text = "Cancel";
            interfaceTableLayoutPanel.Controls.Add(cancelButton, 1, 3);
            /* Layout */
            interfaceTableLayoutPanel.Dock = DockStyle.Fill;
            interfaceTableLayoutPanel.PerformLayout();
            interfaceForm.Controls.Add(interfaceTableLayoutPanel);
            okButton.DialogResult = DialogResult.OK;
            cancelButton.DialogResult = DialogResult.Cancel;
            interfaceForm.AcceptButton = okButton;
            interfaceForm.CancelButton = cancelButton;
            DialogResult dialogResult = interfaceForm.ShowDialog();
            if (dialogResult == DialogResult.OK)
            {
                m_TopTableLayoutPanel.BackColor = productColor;
                foreach (Control control in this.Controls)
                {
                    Font old = control.Font;
                    control.Font = new Font(m_FontFamily.Name, 8.25f, old.Style);
                }

                multiline = multilineCheckBox.Checked;
            }
            else
            {

            }

            interfaceForm.Dispose();
        }

        private void Security_Protocol_Click(object sender, EventArgs e)
        {
            Log.Debug("starting Security_Protocol_Click()");
            Form securityProtocolForm = new Form();
            securityProtocolForm.StartPosition = FormStartPosition.CenterParent;
            securityProtocolForm.Text = "Security_Protocol";
            TableLayoutPanel securityProtocolTableLayoutPanel = new TableLayoutPanel();
            securityProtocolTableLayoutPanel.CellBorderStyle = TableLayoutPanelCellBorderStyle.Single;
            securityProtocolTableLayoutPanel.RowCount = 2;
            securityProtocolTableLayoutPanel.ColumnCount = 2;
            Label securityProtocolLabel = new Label();
            securityProtocolLabel.Text = "Security Protocol";
            securityProtocolLabel.Dock = DockStyle.Fill;
            securityProtocolTableLayoutPanel.Controls.Add(securityProtocolLabel, 0, 0);
            ComboBox securityProtocolComboBox = new ComboBox();
            securityProtocolComboBox.DropDownStyle = ComboBoxStyle.DropDownList;
            securityProtocolComboBox.Items.Add("Ssl3");
            securityProtocolComboBox.Items.Add("Tls");
            securityProtocolComboBox.Items.Add("Tls11");
            securityProtocolComboBox.Items.Add("Tls12");
            securityProtocolComboBox.DisplayMember = m_SecurityProtocol;
            securityProtocolComboBox.SelectedIndex = securityProtocolComboBox.FindStringExact(m_SecurityProtocol);
            if (m_SecurityProtocol != null)
            {
                securityProtocolComboBox.SelectedIndex = securityProtocolComboBox.Items.IndexOf(m_SecurityProtocol);
            }

            securityProtocolComboBox.SelectedItem = m_SecurityProtocol;
            securityProtocolComboBox.SelectedText = m_SecurityProtocol;
            securityProtocolComboBox.SelectedValue = m_SecurityProtocol;
            securityProtocolComboBox.SelectionChangeCommitted += delegate (object o, EventArgs ev)
            {
                Log.DebugFormat("securityProtocolComboBox SelectionChangeCommitted");
                securityProtocolComboBox.SelectedIndex = securityProtocolComboBox.FindStringExact(securityProtocolComboBox.SelectedItem.ToString());
                Log.DebugFormat("securityProtocolComboBox SelectedIndex is {0}", securityProtocolComboBox.SelectedIndex);
            };
            securityProtocolTableLayoutPanel.Controls.Add(securityProtocolComboBox, 1, 0);
            /* Buttons row */
            Button okButton = new Button();
            okButton.Text = "OK";
            securityProtocolTableLayoutPanel.Controls.Add(okButton, 0, 1);
            Button cancelButton = new Button();
            cancelButton.Text = "Cancel";
            securityProtocolTableLayoutPanel.Controls.Add(cancelButton, 1, 1);
            /* Layout */
            securityProtocolTableLayoutPanel.Dock = DockStyle.Fill;
            securityProtocolTableLayoutPanel.PerformLayout();
            securityProtocolForm.Controls.Add(securityProtocolTableLayoutPanel);
            okButton.DialogResult = DialogResult.OK;
            cancelButton.DialogResult = DialogResult.Cancel;
            securityProtocolForm.AcceptButton = okButton;
            securityProtocolForm.CancelButton = cancelButton;
            DialogResult dialogResult = securityProtocolForm.ShowDialog();
            if (dialogResult == DialogResult.OK)
            {
                SecurityProtocolType securityProtocolType = SecurityProtocolType.Tls11;
                Enum.TryParse<SecurityProtocolType>(m_SecurityProtocol, out securityProtocolType);
                ServicePointManager.SecurityProtocol = securityProtocolType;
            }

            securityProtocolForm.Dispose();
        }

        private void Proxy_Click(object sender, EventArgs e)
        {
            Log.Debug("starting Proxy_Click()");
            Form proxyForm = new Form();
            proxyForm.StartPosition = FormStartPosition.CenterParent;
            proxyForm.Text = "Proxy Server";
            TableLayoutPanel proxyTableLayoutPanel = new TableLayoutPanel();
            proxyTableLayoutPanel.CellBorderStyle = TableLayoutPanelCellBorderStyle.Single;
            proxyTableLayoutPanel.RowCount = 4;
            proxyTableLayoutPanel.ColumnCount = 2;
            /* Address row */
            Label addressLabel = new Label();
            addressLabel.Text = "Address";
            addressLabel.Dock = DockStyle.Fill;
            proxyTableLayoutPanel.Controls.Add(addressLabel, 0, 0);
            TextBox addressTextBox = new TextBox();
            addressTextBox.Text = m_ProxyAddress;
            proxyTableLayoutPanel.Controls.Add(addressTextBox, 1, 0);
            /* Port row */
            Label portLabel = new Label();
            portLabel.Text = "Port";
            portLabel.Dock = DockStyle.Fill;
            proxyTableLayoutPanel.Controls.Add(portLabel, 0, 1);
            TextBox portTextBox = new TextBox();
            portTextBox.Text = m_ProxyPort.ToString();
            proxyTableLayoutPanel.Controls.Add(portTextBox, 1, 1);
            /* Enable row */
            Label enableLabel = new Label();
            enableLabel.Text = "Enable";
            enableLabel.Dock = DockStyle.Fill;
            proxyTableLayoutPanel.Controls.Add(enableLabel, 0, 2);
            CheckBox enableCheckBox = new CheckBox();
            enableCheckBox.Checked = m_ProxyEnable;
            proxyTableLayoutPanel.Controls.Add(enableCheckBox, 1, 2);
            /* Buttons row */
            Button okButton = new Button();
            okButton.Text = "OK";
            proxyTableLayoutPanel.Controls.Add(okButton, 0, 3);
            Button cancelButton = new Button();
            cancelButton.Text = "Cancel";
            proxyTableLayoutPanel.Controls.Add(cancelButton, 1, 3);
            /* Layout */
            proxyTableLayoutPanel.Dock = DockStyle.Fill;
            proxyTableLayoutPanel.PerformLayout();
            proxyForm.Controls.Add(proxyTableLayoutPanel);
            okButton.DialogResult = DialogResult.OK;
            cancelButton.DialogResult = DialogResult.Cancel;
            proxyForm.AcceptButton = okButton;
            proxyForm.CancelButton = cancelButton;
            DialogResult dialogResult = proxyForm.ShowDialog();
            if (dialogResult == DialogResult.OK && enableCheckBox.Checked)
            {
                m_ProxyAddress = addressTextBox.Text;
                m_ProxyEnable = true;
                Int32.TryParse(portTextBox.Text, out m_ProxyPort);
            }
            else if (dialogResult == DialogResult.OK && !enableCheckBox.Checked)
            {
                m_ProxyAddress = addressTextBox.Text;
                m_ProxyEnable = false;
                Int32.TryParse(portTextBox.Text, out m_ProxyPort);
            }

            proxyForm.Dispose();
        }

        private void Transport_Click(object sender, EventArgs e)
        {
            Log.Debug("starting Transport_Click()");
            Form transportForm = new Form();
            transportForm.StartPosition = FormStartPosition.CenterParent;
            transportForm.Text = "Transport";
            TableLayoutPanel transportTableLayoutPanel = new TableLayoutPanel();
            transportTableLayoutPanel.CellBorderStyle = TableLayoutPanelCellBorderStyle.Single;
            transportTableLayoutPanel.RowCount = 4;
            transportTableLayoutPanel.ColumnCount = 2;
            /* Binding row */
            Label bindingLabel = new Label();
            bindingLabel.Text = "Binding";
            bindingLabel.Dock = DockStyle.Fill;
            transportTableLayoutPanel.Controls.Add(bindingLabel, 0, 0);
            ComboBox bindingComboBox = new ComboBox();
            bindingComboBox.DropDownStyle = ComboBoxStyle.DropDownList;
            bindingComboBox.Items.Add("Basic");
            bindingComboBox.Items.Add("WebService");
            bindingComboBox.DisplayMember = m_Binding;
            bindingComboBox.SelectedIndex = bindingComboBox.FindStringExact(m_Binding);
            if (m_Binding != null)
            {
                bindingComboBox.SelectedIndex = bindingComboBox.Items.IndexOf(m_Binding);
            }

            bindingComboBox.SelectedItem = m_Binding;
            bindingComboBox.SelectedText = m_Binding;
            bindingComboBox.SelectedValue = m_Binding;
            bindingComboBox.SelectionChangeCommitted += delegate (object o, EventArgs ev)
            {
                Log.DebugFormat("bindingComboBox SelectionChangeCommitted");
                bindingComboBox.SelectedIndex = bindingComboBox.FindStringExact(bindingComboBox.SelectedItem.ToString());
                Log.DebugFormat("bindingComboBox SelectedIndex is {0}", bindingComboBox.SelectedIndex);
            };
            transportTableLayoutPanel.Controls.Add(bindingComboBox, 1, 0);
            /* Security mode row */
            Label securityModeLabel = new Label();
            securityModeLabel.Text = "Security Mode";
            securityModeLabel.Dock = DockStyle.Fill;
            transportTableLayoutPanel.Controls.Add(securityModeLabel, 0, 1);
            ComboBox securityModeComboBox = new ComboBox();
            securityModeComboBox.DropDownStyle = ComboBoxStyle.DropDownList;
            securityModeComboBox.Items.Add("Message");
            securityModeComboBox.Items.Add("None");
            securityModeComboBox.Items.Add("Transport");
            securityModeComboBox.Items.Add("TransportCredentialOnly");
            securityModeComboBox.Items.Add("TransportWithMessageCredential");
            securityModeComboBox.DisplayMember = m_SecurityMode;
            securityModeComboBox.SelectedIndex = securityModeComboBox.FindStringExact(m_SecurityMode);
            if (m_SecurityMode != null)
            {
                securityModeComboBox.SelectedIndex = bindingComboBox.Items.IndexOf(m_SecurityMode);
            }

            securityModeComboBox.SelectedItem = m_SecurityMode;
            securityModeComboBox.SelectedText = m_SecurityMode;
            securityModeComboBox.SelectedValue = m_SecurityMode;
            securityModeComboBox.SelectionChangeCommitted += delegate (object o, EventArgs ev)
            {
                Log.Debug("securityModeComboBox SelectionChangeCommitted");
                securityModeComboBox.SelectedIndex = securityModeComboBox.FindStringExact(securityModeComboBox.SelectedItem.ToString());
                Log.DebugFormat("securityModeComboBox SelectedIndex is {0}", securityModeComboBox.SelectedIndex);
            };
            transportTableLayoutPanel.Controls.Add(securityModeComboBox, 1, 1);
            /* Message client credential type row */
            Label messageClientCredentialTypeLabel = new Label();
            messageClientCredentialTypeLabel.Text = "Message Client Credential Type";
            messageClientCredentialTypeLabel.Dock = DockStyle.Fill;
            transportTableLayoutPanel.Controls.Add(messageClientCredentialTypeLabel, 0, 2);
            ComboBox messageClientCredentialTypeComboBox = new ComboBox();
            messageClientCredentialTypeComboBox.DropDownStyle = ComboBoxStyle.DropDownList;
            messageClientCredentialTypeComboBox.Items.Add("Certificate");
            messageClientCredentialTypeComboBox.Items.Add("IssuedToken");
            messageClientCredentialTypeComboBox.Items.Add("None");
            messageClientCredentialTypeComboBox.Items.Add("UserName");
            messageClientCredentialTypeComboBox.Items.Add("Windows");
            messageClientCredentialTypeComboBox.DisplayMember = m_MessageCredentialType;
            messageClientCredentialTypeComboBox.SelectedIndex = messageClientCredentialTypeComboBox.FindStringExact(m_MessageCredentialType);
            if (m_MessageCredentialType != null)
            {
                messageClientCredentialTypeComboBox.SelectedIndex = messageClientCredentialTypeComboBox.Items.IndexOf(m_MessageCredentialType);
            }

            messageClientCredentialTypeComboBox.SelectedItem = m_MessageCredentialType;
            messageClientCredentialTypeComboBox.SelectedText = m_MessageCredentialType;
            messageClientCredentialTypeComboBox.SelectedValue = m_MessageCredentialType;
            messageClientCredentialTypeComboBox.SelectionChangeCommitted += delegate (object o, EventArgs ev)
            {
                Log.Debug("messageClientCredentialTypeComboBox SelectionChangeCommitted");
                messageClientCredentialTypeComboBox.SelectedIndex = messageClientCredentialTypeComboBox.FindStringExact(messageClientCredentialTypeComboBox.SelectedItem.ToString());
                Log.DebugFormat("messageClientCredentialTypeComboBox SelectedIndex is {0}", messageClientCredentialTypeComboBox.SelectedIndex);
            };
            transportTableLayoutPanel.Controls.Add(messageClientCredentialTypeComboBox, 1, 2);
            /* Transport client credential type row */
            Label transportClientCredentialTypeLabel = new Label();
            transportClientCredentialTypeLabel.Text = "Transport Client Credential Type";
            transportClientCredentialTypeLabel.Dock = DockStyle.Fill;
            transportTableLayoutPanel.Controls.Add(transportClientCredentialTypeLabel, 0, 3);
            ComboBox transportClientCredentialTypeComboBox = new ComboBox();
            transportClientCredentialTypeComboBox.DropDownStyle = ComboBoxStyle.DropDownList;
            transportClientCredentialTypeComboBox.Items.Add("Basic");
            transportClientCredentialTypeComboBox.Items.Add("Certificate");
            transportClientCredentialTypeComboBox.Items.Add("Digest");
            transportClientCredentialTypeComboBox.Items.Add("None");
            transportClientCredentialTypeComboBox.Items.Add("Ntlm");
            transportClientCredentialTypeComboBox.Items.Add("Windows");
            transportClientCredentialTypeComboBox.DisplayMember = m_ClientCredentialType;
            transportClientCredentialTypeComboBox.SelectedIndex = transportClientCredentialTypeComboBox.FindStringExact(m_ClientCredentialType);
            if (m_ClientCredentialType != null)
            {
                transportClientCredentialTypeComboBox.SelectedIndex = transportClientCredentialTypeComboBox.Items.IndexOf(m_ClientCredentialType);
            }

            transportClientCredentialTypeComboBox.SelectedItem = m_ClientCredentialType;
            transportClientCredentialTypeComboBox.SelectedText = m_ClientCredentialType;
            transportClientCredentialTypeComboBox.SelectedValue = m_ClientCredentialType;
            transportClientCredentialTypeComboBox.SelectionChangeCommitted += delegate (object o, EventArgs ev)
            {
                Log.Debug("transportClientCredentialTypeComboBox SelectionChangeCommitted");
                transportClientCredentialTypeComboBox.SelectedIndex = transportClientCredentialTypeComboBox.FindStringExact(transportClientCredentialTypeComboBox.SelectedItem.ToString());
                Log.DebugFormat("transportClientCredentialTypeComboBox SelectedIndex is {0}", transportClientCredentialTypeComboBox.SelectedIndex);
            };
            transportTableLayoutPanel.Controls.Add(transportClientCredentialTypeComboBox, 1, 3);
            /* Buttons row */
            Button okButton = new Button();
            okButton.Text = "OK";
            transportTableLayoutPanel.Controls.Add(okButton, 0, 4);
            Button cancelButton = new Button();
            cancelButton.Text = "Cancel";
            transportTableLayoutPanel.Controls.Add(cancelButton, 1, 4);
            /* Layout */
            transportTableLayoutPanel.Dock = DockStyle.Fill;
            transportTableLayoutPanel.PerformLayout();
            transportForm.Controls.Add(transportTableLayoutPanel);
            okButton.DialogResult = DialogResult.OK;
            cancelButton.DialogResult = DialogResult.Cancel;
            transportForm.AcceptButton = okButton;
            transportForm.CancelButton = cancelButton;
            DialogResult dialogResult = transportForm.ShowDialog();
            if (dialogResult == DialogResult.OK)
            {
                m_Binding = (string)bindingComboBox.SelectedItem;
                m_BindingLabel.Text = string.Format("Binding: {0}", m_Binding);
                m_SecurityMode = (string)securityModeComboBox.SelectedItem;
                m_SecurityLabel.Text = string.Format("Security: {0}", m_SecurityMode);
                m_MessageCredentialType = (string)messageClientCredentialTypeComboBox.SelectedItem;
                m_MessageCredentialTypeLabel.Text = string.Format("Message: {0}", m_MessageCredentialType);
                m_ClientCredentialType = (string)transportClientCredentialTypeComboBox.SelectedItem;
                m_ClientCredentialTypeLabel.Text = string.Format("Client: {0}", m_ClientCredentialType);
            }

            transportForm.Dispose();
        }
    }
}

