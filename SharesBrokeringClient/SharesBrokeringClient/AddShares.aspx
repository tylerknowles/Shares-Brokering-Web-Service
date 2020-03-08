<%@ Page Title="Add Shares" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="AddShares.aspx.cs" Inherits="SharesBrokeringClient.AddShares" %>
<asp:Content ID="BodyContent" ContentPlaceHolderID="MainContent" runat="server">
    <h2><%: Title %></h2>
    <h4>Add shares to the broker.</h4>
    <br />
    <p>
        <asp:Label ID="CompanySymbolLabel" runat="server" Text="Company Symbol"></asp:Label>
    </p>
    <p>
        <asp:TextBox ID="CompanySymbolTextBox" runat="server" ></asp:TextBox>
        <asp:RequiredFieldValidator runat="server" id="requiredSymbol" controltovalidate="CompanySymbolTextBox" errormessage="Please enter a Company Symbol" />
    </p>
    <br />
    <p>
        <asp:Label ID="CompanyNameLabel" runat="server" Text="Company Name"></asp:Label>
    </p>
    <p>
        <asp:TextBox ID="CompanyNameTextBox" runat="server" ></asp:TextBox>
        <asp:RequiredFieldValidator runat="server" id="requiredName" controltovalidate="CompanyNameTextBox" errormessage="Please enter a Company Name" />
    </p>
    <br />
    <p>
        <asp:Label ID="QuantityAvailableLabel" runat="server" Text="Quantity Available"></asp:Label>
    </p>
    <p>
        <asp:TextBox ID="QuantityAvailableTextBox" runat="server" ></asp:TextBox>
        <asp:RequiredFieldValidator runat="server" id="requiredQuantity" controltovalidate="QuantityAvailableTextBox" errormessage="Please enter the Quantity Available" />
        <asp:CompareValidator ID="intQuantityAvailable" runat="server" ControlToValidate="QuantityAvailableTextBox" Type="Integer" Operator="DataTypeCheck" ErrorMessage="Please enter a whole number for the Quantity Available"></asp:CompareValidator>
    </p>
    <br />
    <p>
        <asp:Label ID="CurrencyLabel" runat="server" Text="Currency"></asp:Label>
    </p>
    <p>
        <asp:ListBox ID="CurrencyListBox" runat="server" Width="100%"></asp:ListBox>
        <asp:RequiredFieldValidator runat="server" id="requiredCurrency" controltovalidate="CurrencyListBox" errormessage="Please choose a Currency" />
    </p>
    <br />
    <p>
        <asp:Label ID="PriceLabel" runat="server" Text="Price"></asp:Label>
    </p>
    <p>
        <asp:TextBox ID="PriceTextBox" runat="server" ></asp:TextBox>
        <asp:RequiredFieldValidator runat="server" id="requiredPrice" controltovalidate="PriceTextBox" errormessage="Please enter a Price" />
        <asp:CompareValidator ID="doublePrice" runat="server" ControlToValidate="PriceTextBox" Type="Double" Operator="DataTypeCheck" ErrorMessage="Please enter a decimal number for the Price"></asp:CompareValidator>
    </p>
        <asp:Button ID="SubmitButton" runat="server" Text="Submit" OnClick="AddShare" />
    <br />
    </asp:Content>
