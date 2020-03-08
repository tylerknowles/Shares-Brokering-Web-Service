using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace SharesBrokeringClient
{
    public partial class MyShares : Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            int minQty = string.IsNullOrEmpty(MinQuantityTextBox.Text) ? -1 : Int32.Parse(MinQuantityTextBox.Text);
            int maxQty = string.IsNullOrEmpty(MaxQuantityTextBox.Text) ? -1 : Int32.Parse(MaxQuantityTextBox.Text);

            DataTable dt = new DataTable();
            dt.Columns.Add("Company Symbol");
            dt.Columns.Add("Quantity", typeof(int));

            String username = Session["Username"].ToString();

            SharesBrokeringWSReference.SharesBrokeringWSClient javaWSclient = new SharesBrokeringWSReference.SharesBrokeringWSClient();

            SharesBrokeringWSReference.ShareInfo[] shareInfos = javaWSclient.GetUserShares(username, SymbolTextBox.Text, minQty, maxQty);

            if (shareInfos!=null)
            {
                NoSharesFoundLabel.Visible = false;

                foreach (SharesBrokeringWSReference.ShareInfo s in shareInfos)
                {
                    DataRow row = dt.NewRow();
                    row[0] = s.CompanySymbol;
                    row[1] = s.Quantity;
                    dt.Rows.Add(row);

                }
            }
            else
            {
                NoSharesFoundLabel.Visible = true;
            }
            MySharesGridView.DataSource = dt;
            MySharesGridView.DataBind();
        }

        protected void OnceBound(object sender, GridViewRowEventArgs e)
        {
            if (e.Row.RowType == DataControlRowType.DataRow)
            {
                e.Row.Attributes["onclick"] = Page.ClientScript.GetPostBackClientHyperlink(MySharesGridView, "Select$" + e.Row.RowIndex);
            }
        }

        protected void OnSelectedIndexChanged(object sender, EventArgs e)
        {
            Session["sellCompanySymbol"] = MySharesGridView.Rows[MySharesGridView.SelectedIndex].Cells[0].Text;
            Session["sellQuantity"] = MySharesGridView.Rows[MySharesGridView.SelectedIndex].Cells[1].Text;

            Response.Redirect("SellShare.aspx");
            
        }


        protected void SharesSearchButton_Click(object sender, EventArgs e)
        {

        }
    }
}