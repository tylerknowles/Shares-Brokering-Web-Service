<%@ Page Title="Available Shares" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="AvailableShares.aspx.cs" EnableEventValidation = "false" Inherits="SharesBrokeringClient.AvailableShares" %>

<asp:Content ID="BodyContent" ContentPlaceHolderID="MainContent" runat="server">
    <h2><%: Title %></h2>
    <h4>The shares currently available to purchase. Click a share to be able to purchase it.</h4>
    <br />
    <p>
        <asp:Label ID="CompanyLabel" runat="server" Text="Company" Font-Bold="True" Width="146px"></asp:Label>
        <asp:Label ID="QuantityAvailableLabel" runat="server" Text="Quantity Available" Font-Bold="True" Width="130px" ></asp:Label>
        <asp:Label ID="PriceLabel" runat="server" Text="Price" Font-Bold="True" Width="40px"></asp:Label>

    </p>
    <p>
        <asp:Label ID="CompanySymbolLabel" runat="server" Text="Symbol" Width="60px"></asp:Label>
        <asp:TextBox ID="SymbolTextBox" runat="server" Width="60px"></asp:TextBox>
        <asp:Label ID="MinQuantityLabel" runat="server" Text="Min " Width="70px"  style="text-align: center;" ></asp:Label>
        <asp:TextBox ID="MinQuantityTextBox" runat="server" Width="50px"></asp:TextBox>
        <asp:Label ID="MinPriceLabel" runat="server" Text="Min" Width="79px" style="text-align: center;"></asp:Label>
        <asp:TextBox ID="MinPriceTextBox" runat="server" Width="60px"></asp:TextBox>
         </p>
    <p>
        <asp:Label ID="CompanyNameLabel" runat="server" Text="Name" Width="60px"></asp:Label>
        <asp:TextBox ID="CompanyNameTextBox" runat="server" Width="60px"></asp:TextBox>
        <asp:Label ID="MaxQuantityLabel" runat="server" Text="Max" Width="70px" style="text-align: center;"></asp:Label>
        <asp:TextBox ID="MaxQuantityTextBox" runat="server" Width="50px"></asp:TextBox>
        <asp:Label ID="MaxPriceLabel" runat="server" Text="Max" Width="79px" style="text-align: center;"></asp:Label>
        <asp:TextBox ID="MaxPriceTextBox" runat="server" Width="60px"></asp:TextBox>
        </p>
    <p>
        <asp:Button ID="SharesSearchButton" runat="server" Text="Search" OnClick="SharesSearchButton_Click" />
    </p>
    
        <asp:Label ID="NoSharesFoundLabel" runat="server" Text="No shares could be found, please change your filters and search again" Font-Bold="True" Visible="false"></asp:Label>
    
    <br />
    <p>

        <asp:GridView ID="SharesGridView" runat="server" OnRowDataBound="OnceBound" OnSelectedIndexChanged="OnSelectedIndexChanged" Width="100%" BorderColor="White" Font-Size="10.5pt"></asp:GridView>
    </p>
   <%-- <br />
    <p>
        <asp:Table ID="SharesTable" runat="server" width="100%">
            <asp:TableHeaderRow id="Table1HeaderRow"   
            runat="server">
            <asp:TableHeaderCell 
                Scope="Column" 
                Text="Company Symbol" HorizontalAlign="Left" />
            <asp:TableHeaderCell  
                Scope="Column" 
                Text="Company Name" HorizontalAlign="Left" />
            <asp:TableHeaderCell 
                Scope="Column" 
                Text="Quantity Available" HorizontalAlign="Left" />
            <asp:TableHeaderCell  
                Scope="Column" 
                Text="Price" HorizontalAlign="Left" />
            <asp:TableHeaderCell 
                Scope="Column" 
                Text="Update Date" HorizontalAlign="Left" />
        </asp:TableHeaderRow>    
            <asp:TableRow ID="CompanySymbol" runat="server" HorizontalAlign="Left">
            </asp:TableRow>
            <asp:TableRow ID="CompanyName" runat="server" HorizontalAlign="Left">
            </asp:TableRow>
            <asp:TableRow ID="QuantityAvailable" runat="server" HorizontalAlign="Left">
            </asp:TableRow>
            <asp:TableRow ID="Price" runat="server" HorizontalAlign="Left">
            </asp:TableRow>
            <asp:TableRow ID="UpdateDate" runat="server" HorizontalAlign="Left">
            </asp:TableRow>
        </asp:Table>
    </p>--%>
    <%--<br />
    <p>
        <asp:Button ID="BuyShareButton" runat="server" Text="Buy Share" Visible="False" OnClick="BuyShareButton_Click" />

    </p>
    <p>
        <asp:Label ID="QuantityLabel" runat="server" Text="Quantity" Visible="False"></asp:Label>
        <asp:TextBox ID="QuantityTextBox" runat="server" Width="40px" Visible="False"></asp:TextBox>

    </p>
    <p>
        <asp:Label ID="TotalCostLabel" runat="server" Text="Total Cost:" Visible="False"></asp:Label>
        <asp:Label ID="TotalCostValueLabel" runat="server" Visible="False"></asp:Label>

    </p>--%>

</asp:Content>
