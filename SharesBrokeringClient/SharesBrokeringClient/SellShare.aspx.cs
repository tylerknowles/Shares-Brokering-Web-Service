using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Windows.Forms;

namespace SharesBrokeringClient
{
    public partial class SellShare : Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            CompanySymbolValueLabel.Text = Session["sellCompanySymbol"].ToString();
            QuantityValueLabel.Text = Session["sellQuantity"].ToString();

            SharesBrokeringWSReference.SharesBrokeringWSClient javaWSclient = new SharesBrokeringWSReference.SharesBrokeringWSClient();
            SharesBrokeringWSReference.Share s = javaWSclient.getShare(Session["sellCompanySymbol"].ToString(),Session["currency"].ToString());
            if (s!=null)
            {
                CompanyNameValueLabel.Text = s.CompanyName;
                PriceValueLabel.Text = s.Price.Value.ToString();
            }
            else
            {
                ShareFailedLabel.Visible = true;
                QuantityToSellLabel.Visible=false;
                QuantityToSellTextBox.Visible = false;
                CalculateTotalPriceButton.Visible = false;
            }
            

            String news = javaWSclient.getNews(Session["sellCompanySymbol"].ToString());

            if (news == "")
            {
                NewsLabel.Text = "Couldn't find news for " + Session["sellCompanySymbol"].ToString();

                Headline1Label.Visible = false;
                URL1Label.Visible = false;
                Summary1Label.Visible = false;
                Headline2Label.Visible = false;
                URL2Label.Visible = false;
                Summary2Label.Visible = false;
                Headline3Label.Visible = false;
                URL3Label.Visible = false;
                Summary3Label.Visible = false;
            }
            else
            {
                news = news.Replace("^//", "\"").Replace("^/", "'");
                String[] sep = { "###" };
                String[] newsSplit = news.Split(sep, StringSplitOptions.RemoveEmptyEntries);

                Headline1ValueLabel.Text = newsSplit[0];
                URL1HyperLink.Text = newsSplit[1];
                URL1HyperLink.NavigateUrl = newsSplit[1];
                Summary1ValueLabel.Text = newsSplit[2];
                Headline2ValueLabel.Text = newsSplit[3];
                URL2HyperLink.Text = newsSplit[4];
                URL2HyperLink.NavigateUrl = newsSplit[4];
                Summary2ValueLabel.Text = newsSplit[5];
                Headline3ValueLabel.Text = newsSplit[6];
                URL3HyperLink.Text = newsSplit[7];
                URL3HyperLink.NavigateUrl = newsSplit[7];
                Summary3ValueLabel.Text = newsSplit[8];
            }

        }

        protected void ConfirmSaleButton_Click(object sender, EventArgs e)
        {
            int QuantityToSell = Int32.Parse(QuantityToSellTextBox.Text);
            if (QuantityToSell < 1 || QuantityToSell > Int32.Parse(Session["sellQuantity"].ToString()))
            {
                MessageBox.Show("Please enter a Quantity to sell within the Quantity owned", "Incorrect Quantity to sell", MessageBoxButtons.OK, MessageBoxIcon.Error);
                ConfirmSaleButton.Visible = false;
            }
            else
            {
                SharesBrokeringWSReference.SharesBrokeringWSClient javaWSclient = new SharesBrokeringWSReference.SharesBrokeringWSClient();
                if (!javaWSclient.SellShare(Session["username"].ToString(), CompanySymbolValueLabel.Text, QuantityToSell, Double.Parse(TotalPriceValueLabel.Text)))
                {
                    MessageBox.Show("Your Share sale failed, please try again", "Failed to sell Share", MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
                else
                {
                    MessageBox.Show("Your Share sale was successful", "Share sale successful", MessageBoxButtons.OK, MessageBoxIcon.Information);
                    Response.Redirect("MyShares.aspx");
                }
            }



        }

        protected void CalculateTotalPrice(object sender, EventArgs e)
        {
            TotalPriceValueLabel.Text = (Double.Parse(PriceValueLabel.Text) * Int32.Parse(QuantityToSellTextBox.Text)).ToString();
            ConfirmSaleButton.Visible = true;
        }
        
    }
}