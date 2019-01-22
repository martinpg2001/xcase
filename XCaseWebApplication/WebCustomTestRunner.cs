namespace XCaseWebApplication
{
    using System;
    using System.Collections.Generic;
    using System.IO;
    using System.Reflection;
    using NUnit.Core;
    using NUnit.Core.Filters;
    using log4net;
    using XCaseBase;

    public class WebCustomTestRunner : CustomTestRunner
    {
        /// <summary>
        ///  A log4net log instance.
        /// </summary>
        private static readonly ILog Log = LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        private static string testPackageString = "SampleRunner";
        private static string assemblyString = "SampleRunner.dll";

        public Dictionary<string, TestName> GetTestDictionary()
        {
            Dictionary<string, TestName> testNameDictionary = new Dictionary<string, TestName>();
            CoreExtensions.Host.InitializeService();
            SimpleTestRunner runner = new SimpleTestRunner();
            TestPackage testPackage = new TestPackage(testPackageString);
            Log.Debug("About to load assembly");
            string assemblyLocation = new Uri(Assembly.GetExecutingAssembly().CodeBase).LocalPath;
            string testLocation = Path.GetDirectoryName(assemblyLocation) + @"\" + assemblyString;
            testPackage.Assemblies.Add(testLocation);
            if (runner.Load(testPackage))
            {
                Log.Debug("Loaded package");
                TestNode rootTestNode = (TestNode)runner.Test;
                AddTestNodeToDictionary(testNameDictionary, rootTestNode);
            }

            return testNameDictionary;
        }

        private void AddTestNodeToDictionary(Dictionary<string, TestName> testNameDictionary, TestNode testNode)
        {
            testNameDictionary[testNode.TestName.FullName] = testNode.TestName;
            if (testNode.Tests != null)
            {
                foreach (TestNode childTestNode in testNode.Tests)
                {
                    AddTestNodeToDictionary(testNameDictionary, childTestNode);
                }
            }
        }

        public List<TestResult> RunTests()
        {
            TestResultsEventListener eventListener = new TestResultsEventListener();
            CoreExtensions.Host.InitializeService();
            SimpleTestRunner runner = new SimpleTestRunner();
            TestPackage testPackage = new TestPackage(testPackageString);
            Log.Debug("About to load assembly");
            string assemblyLocation = new Uri(Assembly.GetExecutingAssembly().CodeBase).LocalPath;
            string testLocation = Path.GetDirectoryName(assemblyLocation) + @"\" + assemblyString;
            testPackage.Assemblies.Add(testLocation);
            TestFilter createReferenceDataTestFilter = new CategoryFilter("ReferenceData");
            System.Collections.IList assemblyList = testPackage.Assemblies;
            if (runner.Load(testPackage))
            {
                Log.Debug("Loaded package");
            }

            return eventListener.ListResults;
        }

        public List<TestResult> RunTest(string name)
        {
            TestResultsEventListener eventListener = new TestResultsEventListener();
            Dictionary<string, TestName> testNameDictionary = new Dictionary<string, TestName>();
            CoreExtensions.Host.InitializeService();
            SimpleTestRunner runner = new SimpleTestRunner();
            TestPackage testPackage = new TestPackage(testPackageString);
            Log.Debug("About to load assembly");
            string assemblyLocation = new Uri(Assembly.GetExecutingAssembly().CodeBase).LocalPath;
            string testLocation = Path.GetDirectoryName(assemblyLocation) + @"\" + assemblyString;
            testPackage.Assemblies.Add(testLocation);
            if (name != null && runner.Load(testPackage))
            {
                Log.Warn("Loaded package");
                TestNode rootTestNode = (TestNode)runner.Test;
                AddTestNodeToDictionary(testNameDictionary, rootTestNode);
                TestName selectedTestName = testNameDictionary[name];
                if (selectedTestName != null)
                {
                    TestFilter nameFilter = new NameFilter(selectedTestName);
                    runner.Run(eventListener, nameFilter, true, LoggingThreshold.All);
                }
            }

            return eventListener.ListResults;
        }

        public List<TestResult> RunTests(string[] names)
        {
            System.Console.WriteLine("WebCustomTestRunner: starting RunTests()");
            TestResultsEventListener eventListener = new TestResultsEventListener();
            Dictionary<string, TestName> testNameDictionary = new Dictionary<string, TestName>();
            CoreExtensions.Host.InitializeService();
            SimpleTestRunner runner = new SimpleTestRunner();
            TestPackage testPackage = new TestPackage(testPackageString);
            Log.Debug("About to load assembly");
            string assemblyLocation = new Uri(Assembly.GetExecutingAssembly().CodeBase).LocalPath;
            string testLocation = Path.GetDirectoryName(assemblyLocation) + @"\" + assemblyString;
            testPackage.Assemblies.Add(testLocation);
            if (names != null && names.Length > 0 && runner.Load(testPackage))
            {
                Log.Debug("Loaded package");
                TestNode rootTestNode = (TestNode)runner.Test;
                AddTestNodeToDictionary(testNameDictionary, rootTestNode);
                if (names != null)
                {
                    TestFilter orNameFilter = CreateNamesFilter(testNameDictionary, names);
                    runner.Run(eventListener, orNameFilter, true, LoggingThreshold.All);
                }
            }

            return eventListener.ListResults;
        }

        public TestFilter CreateNamesFilter(Dictionary<string, TestName> testNameDictionary, string[] names)
        {
            Log.DebugFormat("starting CreateNamesFilter()");
            OrFilter orNamesFilter = new OrFilter();
            foreach (string name in names)
            {
                 TestName testName = testNameDictionary[name];
                 if (testName != null)
                 {
                     NameFilter nameFilter = new NameFilter(testName);
                     orNamesFilter.Add(nameFilter);
                 }
            }

            return orNamesFilter;
        }
    }
}
