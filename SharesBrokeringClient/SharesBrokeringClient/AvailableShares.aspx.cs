using System;
using System.Collections.Generic;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace SharesBrokeringClient
{
    public partial class AvailableShares : Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            DataTable dt = new DataTable();
            dt.Columns.Add("Company Symbol");
            dt.Columns.Add("Company Name");
            dt.Columns.Add("Quantity Available", typeof(int));
            dt.Columns.Add("Price", typeof(Double));
            dt.Columns.Add("Update Date", typeof(DateTime));

            String companySymbol = SymbolTextBox.Text;
            String companyName = CompanyNameTextBox.Text;
            int minQtyAvailable = string.IsNullOrEmpty(MinQuantityTextBox.Text) ? -1 : Int32.Parse(MinQuantityTextBox.Text);
            int maxQtyAvailable = string.IsNullOrEmpty(MaxQuantityTextBox.Text) ? -1 : Int32.Parse(MaxQuantityTextBox.Text);
            Double minPrice = string.IsNullOrEmpty(MinPriceTextBox.Text) ? -1 : Double.Parse(MinPriceTextBox.Text);
            Double maxPrice = string.IsNullOrEmpty(MaxPriceTextBox.Text) ? -1 : Double.Parse(MaxPriceTextBox.Text);

            SharesBrokeringWSReference.SharesBrokeringWSClient javaWSclient = new SharesBrokeringWSReference.SharesBrokeringWSClient();

            SharesBrokeringWSReference.user u = javaWSclient.getUser(Session["username"].ToString());
            Session["currency"] = u.Currency;

            SharesBrokeringWSReference.Share[] shares = javaWSclient.GetFilteredShares(companySymbol, companyName, minQtyAvailable, maxQtyAvailable, minPrice, maxPrice, u.Currency);
            
            if (shares!=null)
            {
                NoSharesFoundLabel.Visible = false;

                foreach (SharesBrokeringWSReference.Share s in shares)
                {
                    DataRow row = dt.NewRow();
                    row[0] = s.CompanySymbol;
                    row[1] = s.CompanyName;
                    row[2] = s.QtyAvailable;
                    row[3] = s.Price.Value;
                    row[4] = s.UpdateDate;
                    dt.Rows.Add(row);
                }
                
            }
            else
            {
                NoSharesFoundLabel.Visible = true;
            }
            SharesGridView.DataSource = dt;
            SharesGridView.DataBind();
        }


        protected void OnceBound(object sender, GridViewRowEventArgs e)
        {
            if (e.Row.RowType == DataControlRowType.DataRow && Int32.Parse(e.Row.Cells[2].Text) > 0 && Double.Parse(e.Row.Cells[3].Text)> -1)
            {
                e.Row.Attributes["onclick"] = Page.ClientScript.GetPostBackClientHyperlink(SharesGridView, "Select$" + e.Row.RowIndex);
            }
        }

        protected void SharesSearchButton_Click(object sender, EventArgs e)
        {

        }

        protected void OnSelectedIndexChanged(object sender, EventArgs e)
        {
            Session["buyCompanySymbol"] = SharesGridView.Rows[SharesGridView.SelectedIndex].Cells[0].Text;
            Session["buyCompanyName"] = SharesGridView.Rows[SharesGridView.SelectedIndex].Cells[1].Text;
            Session["buyQuantityAvailable"] = SharesGridView.Rows[SharesGridView.SelectedIndex].Cells[2].Text;
            Session["buyPrice"] = SharesGridView.Rows[SharesGridView.SelectedIndex].Cells[3].Text;

            Response.Redirect("BuyShare.aspx");
     
        }

        
    }
}