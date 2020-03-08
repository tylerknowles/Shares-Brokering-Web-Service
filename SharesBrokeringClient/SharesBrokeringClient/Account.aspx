<%@ Page Title="Account" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="Account.aspx.cs" Inherits="SharesBrokeringClient.Account" %>
<asp:Content ID="BodyContent" ContentPlaceHolderID="MainContent" runat="server">
<h2><%: Title %></h2>
    <h4>Manage your account.</h4>
    <br />
    <p>
        <asp:Label ID="CurrentCurrencyLabel" runat="server" Text="Current Currency:" Font-Bold="True" Width="130px"></asp:Label>
        <asp:Label ID="CurrentCurrencyValueLabel" runat="server" Width="60px"></asp:Label> 
        <asp:Button ID="ChangeCurrencyButton" runat="server" Text="Change" OnClick="ChangeCurrencyButton_Click" />
    </p>
    <br />
    <asp:ListBox ID="CurrencyListBox" runat="server" Width="100%" Visible="false" ></asp:ListBox>
        <asp:RequiredFieldValidator runat="server" id="reqCurrency" controltovalidate="CurrencyListBox" errormessage="Please choose a Currency" />
    <br />
    <p>
        <asp:Button ID="ConfirmCurrencyButton" runat="server" Text="Confirm" Visible="false" OnClick="ConfirmCurrencyButton_Click" />
    </p>
    <br />
    <br />
    <br />
    <p>
        <asp:Label ID="CurrentWalletLabel" runat="server" Text="Current Wallet:" Font-Bold="True" Width="110px" ></asp:Label>
        <asp:Label ID="CurrentWalletValueLabel" runat="server" Width="130px"></asp:Label>
        <asp:Button ID="DepositButton" runat="server" Text="Deposit" OnClick="DepositButton_Click" />
        <asp:Button ID="WithdrawButton" runat="server" Text="Withdraw" OnClick="WithdrawButton_Click" />
    </p>
    <br />
    <p>
        <asp:Label ID="AmountLabel" runat="server" Text="Amount:" Font-Bold="True" Width="64px" Visible="false"></asp:Label>
        <asp:TextBox ID="AmountTextBox" runat="server" Width="60px" Visible="false"></asp:TextBox>
        <asp:RequiredFieldValidator runat="server" id="requiredAmount" controltovalidate="AmountTextBox" errormessage="Please enter an Amount" />
        <asp:CompareValidator ID="doubleAmount" runat="server" ControlToValidate="AmountTextBox" Type="Double" Operator="DataTypeCheck" ErrorMessage="Please enter a decimal number for the Amount"></asp:CompareValidator>
    </p>
    <br />
    <p>
        <asp:Button ID="ConfirmDepositButton" runat="server" Text="Confirm" Visible="false" OnClick="ConfirmDepositButton_Click" />
        <asp:Button ID="ConfirmWithdrawButton" runat="server" Text="Confirm" Visible="false" OnClick="ConfirmWithdrawButton_Click" />
    </p>
</asp:Content>
