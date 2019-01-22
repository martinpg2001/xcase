namespace XCaseWebApplication
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Web;
    using System.Web.UI.HtmlControls;
    using System.Web.UI.WebControls;
    using NUnit.Core;

    public class WebResultsVisualizer
    {
        private Panel panel;
        private HtmlGenericControl _htmlBRControl;

        public WebResultsVisualizer(Panel panel)
        {
            this.panel = panel;
        }

        public void AddTestResult(Table testResultsTable, TestResult testResult)
        {
            TableRow testResultTableRow = new TableRow();
            TableCell testNameTableCell = new TableCell();
            testNameTableCell.Text = testResult.Name;
            testResultTableRow.Cells.Add(testNameTableCell);
            TableCell testStateTableCell = new TableCell();
            testStateTableCell.Text = testResult.ResultState.ToString();
            testResultTableRow.Cells.Add(testStateTableCell);
            TableCell testMessageTableCell = new TableCell();
            testMessageTableCell.Text = testResult.Message;
            testResultTableRow.Cells.Add(testMessageTableCell);
            TableCell testImageTableCell = new TableCell();
            testImageTableCell.Controls.Add(ImageResult(testResult.ResultState));
            testResultTableRow.Cells.Add(testImageTableCell);
            TableCell testLogTableCell = new TableCell();
            System.Web.UI.WebControls.HyperLink logHyperlink = new HyperLink();
            logHyperlink.NavigateUrl = "Logs/trace.log";
            logHyperlink.Text = "Log";
            testLogTableCell.Controls.Add(logHyperlink);
            testResultTableRow.Cells.Add(testLogTableCell);
            testResultsTable.Rows.Add(testResultTableRow);
        }

        public void AddTestName(Table testNamesTable, TestName testName)
        {
            TableRow testNameTableRow = new TableRow();
            TableCell testNameTableCell = new TableCell();
            testNameTableCell.Text = testName.FullName;
            testNameTableRow.Cells.Add(testNameTableCell);
            TableCell testRunTableCell = new TableCell();
            System.Web.UI.WebControls.HyperLink runHyperlink = new HyperLink();
            runHyperlink.NavigateUrl = "Default.aspx?Test=" + testName.FullName;
            runHyperlink.Text = "Run";
            testRunTableCell.Controls.Add(runHyperlink);
            testNameTableRow.Cells.Add(testRunTableCell);
            TableCell testActionTableCell = new TableCell();
            //HtmlInputCheckBox actionCheckbox = new HtmlInputCheckBox();
            //actionCheckbox.ClientID = "testgroup";
            //actionCheckbox.Name = "testgroup";
            //actionCheckbox.Value = testName.FullName;
            testActionTableCell.Text = "<input type=\"checkbox\" name=\"testgroup\" value=\"" + testName.FullName + "\" />";
            testNameTableRow.Cells.Add(testActionTableCell);
            testNamesTable.Rows.Add(testNameTableRow);
        }

        public void AddTestName(string testName)
        {
            Label testNameLabel = LabelByContent("Name");
            Label resultState = LabelByContent(testName);
            Label message = LabelByContent(testName);
            System.Web.UI.WebControls.HyperLink runHyperlink = new HyperLink();
            runHyperlink.NavigateUrl = "Default.aspx?Test=" + testName;
            runHyperlink.Text = testName;
            panel.Controls.Add(testNameLabel);
            panel.Controls.Add(LabelByContent(" "));
            panel.Controls.Add(resultState);
            panel.Controls.Add(LabelByContent(": "));
            panel.Controls.Add(runHyperlink);
            _htmlBRControl = new HtmlGenericControl("br");
            panel.Controls.Add(_htmlBRControl);
        }

        public void VisualizeTestDictionary(Dictionary<string, TestName> testNameDictionary)
        {
            HtmlForm testNamesForm = new HtmlForm();
            testNamesForm.Action = "Default.aspx";
            Table testNamesTable = new Table();
            TableHeaderRow tableHeaderRow = new TableHeaderRow();
            TableCell testNameTableCell = new TableCell();
            testNameTableCell.Text = "Full Name";
            tableHeaderRow.Cells.Add(testNameTableCell);
            TableCell testRunTableCell = new TableCell();
            testRunTableCell.Text = "Run";
            tableHeaderRow.Cells.Add(testRunTableCell);
            TableCell testActionTableCell = new TableCell();
            Button runButton = new Button();
            runButton.UseSubmitBehavior = true;
            runButton.Text = "Run";
            testActionTableCell.Controls.Add(runButton);
            tableHeaderRow.Cells.Add(testActionTableCell);
            testNamesTable.Rows.Add(tableHeaderRow);
            foreach (string keyName in testNameDictionary.Keys)
            {
                AddTestName(testNamesTable, testNameDictionary[keyName]);
            }

            testNamesForm.Controls.Add(testNamesTable);
            panel.Controls.Add(testNamesForm);
        }

        public void VisualizeTestNames(List<string> testNames)
        {
            foreach (string testName in testNames)
            {
                AddTestName(testName);
            }
        }

        public void VisualizeTestResults(List<TestResult> testResults)
        {
            Table testResultsTable = new Table();
            TableHeaderRow tableHeaderRow = new TableHeaderRow();
            TableCell testNameTableCell = new TableCell();
            testNameTableCell.Text = "Name";
            tableHeaderRow.Cells.Add(testNameTableCell);
            TableCell testStateTableCell = new TableCell();
            testStateTableCell.Text = "State";
            tableHeaderRow.Cells.Add(testStateTableCell);
            TableCell testMessageTableCell = new TableCell();
            testMessageTableCell.Text = "Message";
            tableHeaderRow.Cells.Add(testMessageTableCell);
            TableCell testImageTableCell = new TableCell();
            testImageTableCell.Text = "Image";
            tableHeaderRow.Cells.Add(testImageTableCell);
            TableCell logTableCell = new TableCell();
            logTableCell.Text = "Log";
            tableHeaderRow.Cells.Add(logTableCell);
            testResultsTable.Rows.Add(tableHeaderRow);
            foreach (TestResult testResult in testResults)
            {
                AddTestResult(testResultsTable, testResult);
            }

            panel.Controls.Add(testResultsTable);
        }

        private Label LabelByContent(string content)
        {
            Label label = new Label();
            label.Text = content;
            return label;
        }

        private Image ImageResult(ResultState resState)
        {
            Image image = new Image();
            if (resState.Equals(ResultState.Success))
            {
                image.ImageUrl = "~/img/passed.png";
            }
            else if (resState.Equals(ResultState.Ignored))
            {
                image.ImageUrl = "~/img/Ignored.png";
            }
            else
            {
                image.ImageUrl = "~/img/failed.png";
            }
            return image;
        }
    }
}