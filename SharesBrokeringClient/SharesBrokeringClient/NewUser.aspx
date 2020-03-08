<%@ Page Title="New User" Language="C#" MasterPageFile="~/StartSite.Master" AutoEventWireup="true" CodeBehind="NewUser.aspx.cs" Inherits="SharesBrokeringClient.NewUser" %>
<asp:Content ID="BodyContent" ContentPlaceHolderID="MainContent" runat="server">
    <h2><%: Title %></h2>
    <h4>Choose a username, a password and the currency you wish to use.</h4>
    <br />
    <p>
        <asp:Label ID="UsernameLabel" runat="server" Text="Username"></asp:Label>
    </p>
    <p>
        <asp:TextBox ID="UsernameTextBox" runat="server" ></asp:TextBox>
        <asp:RequiredFieldValidator runat="server" id="reqUserame" controltovalidate="UsernameTextBox" errormessage="Please enter a Username" />
    </p>
    <br />
    <p>
        <asp:Label ID="PasswordLabel" runat="server" Text="Password"></asp:Label>
    </p>
    <p>
        <asp:TextBox ID="PasswordTextBox" type="password" runat="server" ></asp:TextBox>
        <asp:RequiredFieldValidator runat="server" id="requiredPassword" controltovalidate="PasswordTextBox" errormessage="Please enter a Password" />
    </p>
    <br />
    <p>
        <asp:Label ID="CurrencyLabel" runat="server" Text="Currency"></asp:Label>
    </p>
    <p>
        <asp:ListBox ID="CurrencyListBox" runat="server" Width="100%"></asp:ListBox>
        <asp:RequiredFieldValidator runat="server" id="reqCurrency" controltovalidate="CurrencyListBox" errormessage="Please choose a Currency" />
    </p>
    <br />
    <p>
        <asp:Label ID="Wallet" runat="server" Text="Wallet"></asp:Label>
    </p>
    <p>
        <asp:TextBox ID="WalletTextBox" runat="server" Text="0.0" ></asp:TextBox>
        <asp:RequiredFieldValidator runat="server" id="reqWallet" controltovalidate="WalletTextBox" errormessage="Please enter a Wallet amount" />
        <asp:CompareValidator ID="doubleWallet" runat="server" ControlToValidate="WalletTextBox" Type="Double" Operator="DataTypeCheck" ErrorMessage="Please enter a decimal number for the Wallet amount"></asp:CompareValidator>
    </p>
    <br />
    <asp:Button ID="SubmitButton" runat="server" Text="Submit" OnClick="AddUser" />
</asp:Content>

