<%@ Page Title="My Shares" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="MyShares.aspx.cs" EnableEventValidation = "false" Inherits="SharesBrokeringClient.MyShares" %>
<asp:Content ID="BodyContent" ContentPlaceHolderID="MainContent" runat="server">
    <h2><%: Title %></h2>
    <h4>The shares you own.</h4>
    <br />
    <p >
        <asp:Label ID="CompanySymbolLabel" runat="server" Text="Company Symbol:" Width="118px"></asp:Label>
        <asp:TextBox ID="SymbolTextBox" runat="server" Width="60px"></asp:TextBox>
        <asp:Label ID="MinQuantityLabel" runat="server" Text="Min Quantity:" Width="92px" style="text-align: center;" ></asp:Label>
        <asp:TextBox ID="MinQuantityTextBox" runat="server" Width="40px"></asp:TextBox>
        <asp:Label ID="MaxQuantityLabel" runat="server" Text="Max Quantity:" Width="92px" style="text-align: center;" ></asp:Label>
        <asp:TextBox ID="MaxQuantityTextBox" runat="server" Width="40px"></asp:TextBox>
        <asp:Button ID="SharesSearchButton" runat="server" Text="Search" OnClick="SharesSearchButton_Click" />
    </p>
    
    <asp:Label ID="NoSharesFoundLabel" runat="server" Text="No shares could be found, please change your filters and search again" Font-Bold="True" Visible="false"></asp:Label>

    <br />
    <p>
        <asp:GridView ID="MySharesGridView" runat="server" OnRowDataBound="OnceBound" OnSelectedIndexChanged="OnSelectedIndexChanged" Width="100%" BorderColor="White" Font-Size="10.5pt"></asp:GridView>
        <%--<asp:Table ID="SharesTable" runat="server" width="100%">
            <asp:TableHeaderRow id="Table1HeaderRow"   
            runat="server">
            <asp:TableHeaderCell 
                Scope="Column" 
                Text="Company Symbol" HorizontalAlign="Left" />
            <asp:TableHeaderCell  
                Scope="Column" 
                Text="Quantity" HorizontalAlign="Left" />
        </asp:TableHeaderRow>    
            <asp:TableRow ID="CompanySymbol" runat="server" HorizontalAlign="Left">
            </asp:TableRow>
            <asp:TableRow ID="Quantity" runat="server" HorizontalAlign="Left">
            </asp:TableRow>
        </asp:Table>--%>
    </p>
</asp:Content>
