<%@ Page Title="XCase Test Runner" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="Default.aspx.cs" Inherits="XCaseWebApplication._Default" %>

<asp:Content runat="server" ID="BodyContent" ContentPlaceHolderID="MainContent">
    <table>
    <tr><td style="text-align:center">Tests</td><td style="text-align:center">Results</td></tr>
    <tr>
        <td style="vertical-align:top">
    <asp:Panel ID="TestNamesPanel" runat="server">
    </asp:Panel>
        </td>
        <td style="vertical-align:top">
    <asp:Panel ID="TestResultsPanel" runat="server">
    </asp:Panel>
        </td>
    </tr>
    </table>
</asp:Content>
