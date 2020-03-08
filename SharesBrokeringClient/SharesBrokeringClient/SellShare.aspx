<%@ Page Title="Sell Shares" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="SellShare.aspx.cs" Inherits="SharesBrokeringClient.SellShare" %>
<asp:Content ID="BodyContent" ContentPlaceHolderID="MainContent" runat="server">
    <h2><%: Title %></h2>
    <h4>Choose how many shares you wish to sell.</h4>
    <br />
    <p>
        <asp:Label ID="CompanySymbolLabel" runat="server" Text="Company Symbol:" Font-Bold="True"></asp:Label>
        <asp:Label ID="CompanySymbolValueLabel" runat="server"></asp:Label>
    </p>
    <p>
        <asp:Label ID="CompanyNameLabel" runat="server" Text="Company Name:" Font-Bold="True"></asp:Label>
        <asp:Label ID="CompanyNameValueLabel" runat="server"></asp:Label>
    </p>
    <p>
        <asp:Label ID="QuantityLabel" runat="server" Text="Quantity Owned:" Font-Bold="True"></asp:Label>
        <asp:Label ID="QuantityValueLabel" runat="server"></asp:Label>
    </p>
    <p>
        <asp:Label ID="PriceLabel" runat="server" Text="Price:" Font-Bold="True"></asp:Label>
        <asp:Label ID="PriceValueLabel" runat="server"></asp:Label>
        <%--<asp:Button ID="SharesSearchButton" runat="server" Text="Search" OnClick="SharesSearchButton_Click" />--%>
    </p>
        <asp:Label ID="ShareFailedLabel" runat="server" Text="Couldn't find share information" Font-Bold="True" Visible="false"></asp:Label>
    <br />
    <p>
        <asp:Label ID="QuantityToSellLabel" runat="server" Text="Quantity To Sell:" Font-Bold="True"></asp:Label>
        <asp:TextBox ID="QuantityToSellTextBox" runat="server" Width="45px"></asp:TextBox>
        <asp:RequiredFieldValidator runat="server" id="requiredQuantityToSell" controltovalidate="QuantityToSellTextBox" errormessage="Please enter Quantity to sell" />
        <asp:CompareValidator ID="intQuantityToSell" runat="server" ControlToValidate="QuantityToSellTextBox" Type="Integer" Operator="DataTypeCheck" ErrorMessage="Please enter a whole number for the Quantity"></asp:CompareValidator>
    </p>
    <p>
        <asp:Button ID="CalculateTotalPriceButton" runat="server" Text="Calculate Total" OnClick="CalculateTotalPrice"></asp:Button>
        <asp:Label ID="TotalPriceLabel" runat="server" Text="Total Price:" Font-Bold="True"></asp:Label>
        <asp:Label ID="TotalPriceValueLabel" runat="server" Font-Bold="False"></asp:Label>
        </p>
    <br />
        
    <p>
        <asp:Button ID="ConfirmSaleButton" runat="server" Text="Confirm" OnClick="ConfirmSaleButton_Click" Visible="false"></asp:Button>
    </p>
    <br />
    <br />
    <p>
        <asp:Label ID="NewsLabel" runat="server" Text="News" Font-Bold="True"></asp:Label>
    </p>
    <br />
    <p>
        <asp:Label ID="Headline1Label" runat="server" Text="Headline:" Font-Bold="True" ></asp:Label>
        <asp:Label ID="Headline1ValueLabel" runat="server"></asp:Label>
    </p>
    <p>
        <asp:Label ID="URL1Label" runat="server" Text="URL:" Font-Bold="True" ></asp:Label>
        <asp:HyperLink ID="URL1HyperLink" runat="server" ></asp:HyperLink>
    </p>
    <p>
        <asp:Label ID="Summary1Label" runat="server" Text="Summary:" Font-Bold="True" ></asp:Label>
        <asp:Label ID="Summary1ValueLabel" runat="server"></asp:Label>
    </p>
    <br />
    <p>
        <asp:Label ID="Headline2Label" runat="server" Text="Headline:" Font-Bold="True" ></asp:Label>
        <asp:Label ID="Headline2ValueLabel" runat="server"></asp:Label>
    </p>
    <p>
        <asp:Label ID="URL2Label" runat="server" Text="URL:" Font-Bold="True" ></asp:Label>
        <asp:HyperLink ID="URL2HyperLink" runat="server" ></asp:HyperLink>
    </p>
    <p>
        <asp:Label ID="Summary2Label" runat="server" Text="Summary:" Font-Bold="True" ></asp:Label>
        <asp:Label ID="Summary2ValueLabel" runat="server"></asp:Label>
    </p>
    <br />
    <p>
        <asp:Label ID="Headline3Label" runat="server" Text="Headline:" Font-Bold="True" ></asp:Label>
        <asp:Label ID="Headline3ValueLabel" runat="server"></asp:Label>
    </p>
    <p>
        <asp:Label ID="URL3Label" runat="server" Text="URL:" Font-Bold="True" ></asp:Label>
        <asp:HyperLink ID="URL3HyperLink" runat="server"></asp:HyperLink>
    </p>
    <p>
        <asp:Label ID="Summary3Label" runat="server" Text="Summary:" Font-Bold="True" ></asp:Label>
        <asp:Label ID="Summary3ValueLabel" runat="server"></asp:Label>
    </p>
</asp:Content>
