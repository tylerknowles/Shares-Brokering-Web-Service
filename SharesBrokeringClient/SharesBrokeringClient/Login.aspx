<%@ Page Title="Login" Language="C#" MasterPageFile="~/StartSite.Master" AutoEventWireup="true" CodeBehind="Login.aspx.cs" Inherits="SharesBrokeringClient.Login" %>
<asp:Content ID="BodyContent" ContentPlaceHolderID="MainContent" runat="server">
    <h2><%: Title %></h2>
    <h4>Enter your username and password to log in to your account.</h4>
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
        <asp:TextBox ID="PasswordTextBox" type="password" runat="server" RequiredFieldValidator="True"></asp:TextBox>
        <asp:RequiredFieldValidator runat="server" id="requiredPassword" controltovalidate="PasswordTextBox" errormessage="Please enter a Password" />
    </p>
    <br />
    <asp:Button ID="LoginButton" runat="server" Text="Login" OnClick="LogIn" />
</asp:Content>

