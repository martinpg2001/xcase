namespace XCaseWebApplication
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Web;
    using NUnit.Core;

    public interface CustomTestRunner
    {
        Dictionary<string, TestName> GetTestDictionary();
        List<TestResult> RunTest(string testName);
        List<TestResult> RunTests();
        List<TestResult> RunTests(string[] testNames);
    }
}