using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Windows.Forms;

namespace SharesBrokeringClient
{
    public partial class BuyShare : Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            CompanySymbolValueLabel.Text = Session["buyCompanySymbol"].ToString();
            CompanyNameValueLabel.Text = Session["buyCompanyName"].ToString();
            QuantityAvailableValueLabel.Text = Session["buyQuantityAvailable"].ToString();
            PriceValueLabel.Text = Session["buyPrice"].ToString();

            SharesBrokeringWSReference.SharesBrokeringWSClient javaWSclient = new SharesBrokeringWSReference.SharesBrokeringWSClient();
            String news = javaWSclient.getNews(Session["buyCompanySymbol"].ToString());

            if (news == "")
            {
                NewsLabel.Text = "Couldn't find news for " + Session["buyCompanySymbol"].ToString();

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
                URL1HyperLink.NavigateUrl= newsSplit[1];
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

        protected void ConfirmPurchaseButton_Click(object sender, EventArgs e)
        {
            int QuantityToBuy = Int32.Parse(QuantityToBuyTextBox.Text);
            if(QuantityToBuy<1 || QuantityToBuy > Int32.Parse(Session["buyQuantityAvailable"].ToString()))
            {
                MessageBox.Show("Please enter a Quantity to buy within the Quantity Available", "Incorrect Quantity to buy", MessageBoxButtons.OK, MessageBoxIcon.Error);
                ConfirmPurchaseButton.Visible = false;
            }
            else
            {
                SharesBrokeringWSReference.SharesBrokeringWSClient javaWSclient = new SharesBrokeringWSReference.SharesBrokeringWSClient();
                if(!javaWSclient.AddUserShare(Session["username"].ToString(), CompanySymbolValueLabel.Text, QuantityToBuy,Double.Parse(TotalPriceValueLabel.Text)))
                {
                    MessageBox.Show("Your Share purchase failed, please try again", "Failed to purchase Share", MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
                else
                {
                    MessageBox.Show("Your Share purchase was successful", "Share purchase successful", MessageBoxButtons.OK, MessageBoxIcon.Information);
                    Response.Redirect("MyShares.aspx");
                }
            }



        }

        protected void CalculateTotalPrice(object sender, EventArgs e)
        {
            TotalPriceValueLabel.Text = (Double.Parse(Session["buyPrice"].ToString())*Int32.Parse(QuantityToBuyTextBox.Text)).ToString();
            ConfirmPurchaseButton.Visible = true;
        }
    }
}