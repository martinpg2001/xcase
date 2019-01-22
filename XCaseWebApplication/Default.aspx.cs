namespace XCaseWebApplication
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Web;
    using System.Web.UI;
    using System.Web.UI.WebControls;
    using NUnit.Core;

    public partial class _Default : Page
    {
        private WebResultsVisualizer webResultsVisualizer;
        private WebResultsVisualizer testNamesVisualizer;
        private CustomTestRunner testRunner;
        public string assemblyLocation;
        protected override void OnInit(EventArgs e)
        {
            base.OnInit(e);
            testRunner = new WebCustomTestRunner();
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            string requestTestString = Request.QueryString["Test"];
            string requestTestGroupString = Request.Params["testgroup"];
            string[] requestTestsString = null;
            if (requestTestGroupString != null)
            {
                requestTestsString = requestTestGroupString.Split(',');
            }

            testNamesVisualizer = new WebResultsVisualizer(this.TestNamesPanel);
            webResultsVisualizer = new WebResultsVisualizer(this.TestResultsPanel);
            Dictionary<string, TestName> testNameDictionary = testRunner.GetTestDictionary();
            testNamesVisualizer.VisualizeTestDictionary(testNameDictionary);
            if (requestTestString != null)
            {
                System.Console.WriteLine("WebCustomTestRunner: requestTestString is not null");
                List<TestResult> testResults = testRunner.RunTest(requestTestString);
                webResultsVisualizer.VisualizeTestResults(testResults);
            }

            if (requestTestsString != null)
            {
                System.Console.WriteLine("WebCustomTestRunner: requestTestsString is not null");
                List<TestResult> testResults = testRunner.RunTests(requestTestsString);
                webResultsVisualizer.VisualizeTestResults(testResults);
            }
        }
    }
}
