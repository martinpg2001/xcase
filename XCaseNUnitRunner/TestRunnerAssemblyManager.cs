using System;
using System.Collections.Generic;
using System.Configuration;
using System.Xml;
using NUnit.Framework;
using XCaseBase;
using XCaseNUnitRunner.Core;

/// <summary>
/// The XCase assembly manager.
/// </summary>
/// <remarks>
/// This class is used to configure each test run.
/// Configuration include definition for messenger (logger) for tests set. 
/// </remarks>
[SetUpFixture]
public class TestRunnerAssemblyManager : BaseTestAssemblyManager
{
    #region Public Properties

    /// <summary>
    /// Gets the process environment.
    /// </summary>
    /// <remarks>
    /// The setting contains the definition for logger configuration.
    /// It determines the type of messenger (Console/Database/both).
    /// </remarks>
    public static ProcessEnvironment ProcessEnvironment { get; private set; }

    #endregion Public Properties

    #region Public Methods and Operators

    /// <summary>
    /// Called before any test is run in current executing assembly which contains unit tests.
    /// </summary>
    [SetUp]
    public override void OnUnitTestSessionStart()
    {
        base.OnUnitTestSessionStart();

        // Get configuration for messenger.
        ProcessEnvironment = this.ConfigureMessenger();

        DocumentProcessor.ProcessDocument(
            ProcessEnvironment,
            new XmlDocument
                {
                    InnerXml =
                        "<?xml version=\"1.0\" encoding=\"utf-8\"?><operation id=\"Start Batch\" class=\"XCaseGeneric.ActionStartBatch\" />"
                });
    }

    /// <summary>
    /// This method will be called by nUnit runner when execution of the all unit test (in current executing assembly) is completed.
    /// Performs application-defined tasks associated with freeing, releasing, or resetting unmanaged resources.
    /// </summary>
    [TearDown]
    public override void OnUnitTestSessionEnd()
    {
        base.OnUnitTestSessionEnd();

        DocumentProcessor.ProcessDocument(
            ProcessEnvironment,
            new XmlDocument
                {
                    InnerXml =
                        "<?xml version=\"1.0\" encoding=\"utf-8\"?><operation id=\"Finish Batch\" class=\"XCaseGeneric.ActionFinishBatch\" />"
                });
    }

    #endregion Public Methods and Operators

    #region Methods

    /// <summary>
    /// The method to define messenger.
    /// </summary>
    /// <returns>
    /// The <see cref="ProcessEnvironment"/> value with messenger defined from app.config.
    /// </returns>
    protected ProcessEnvironment ConfigureMessenger()
    {
        ProcessEnvironment processEnvironment = new ProcessEnvironment();
        string messengerConfigurationString = ConfigurationManager.AppSettings["MessengerConfiguration"];
        if (!string.IsNullOrWhiteSpace(messengerConfigurationString))
        {
            string[] messengerConfigurations = messengerConfigurationString.Split(new[] { '-' }, StringSplitOptions.RemoveEmptyEntries);
            Dictionary<string, string> messengerConfig = new Dictionary<string, string>();
            foreach (string messengerConfiguration in messengerConfigurations)
            {
                string[] keyValue = messengerConfiguration.Split(new[] { ' ' }, StringSplitOptions.RemoveEmptyEntries);
                string key = keyValue[0].Trim();
                messengerConfig[key] = keyValue[1].Trim();
            }

            if (messengerConfig.ContainsKey("messengerType"))
            {
                processEnvironment.MessengerTypeString = messengerConfig["messengerType"];
            }

            if (messengerConfig.ContainsKey("messengerTypeConfiguration"))
            {
                processEnvironment.MessengerTypeConfigurationString = messengerConfig["messengerTypeConfiguration"];
            }

            if (messengerConfig.ContainsKey("silent"))
            {
                processEnvironment.Silent = messengerConfig["silent"] == "True" || messengerConfig["silent"] == "true";
            }
        }
        else
        {
            processEnvironment.MessengerTypeString = "XCaseGeneric.ConsoleMessenger";
        }

        return processEnvironment;
    }

    #endregion Methods
}