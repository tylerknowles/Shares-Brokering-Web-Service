using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Windows.Forms;

namespace SharesBrokeringClient
{
    public partial class AddShares : Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            SharesBrokeringWSReference.SharesBrokeringWSClient javaWSclient = new SharesBrokeringWSReference.SharesBrokeringWSClient();

            String[] currencies = javaWSclient.GetCurrencyList();
            foreach (String currency in currencies)
            {
                CurrencyListBox.Items.Add(currency);
            }
        }

        protected void AddShare(object sender, EventArgs e)
        {
            SharesBrokeringWSReference.SharesBrokeringWSClient javaWSclient = new SharesBrokeringWSReference.SharesBrokeringWSClient();

            if (!javaWSclient.AddShare(CompanySymbolTextBox.Text, CompanyNameTextBox.Text, Int32.Parse(QuantityAvailableTextBox.Text),
                CurrencyListBox.SelectedValue.Substring(0, 3), Double.Parse(PriceTextBox.Text)))
            {
                MessageBox.Show("That company symbol already exists", "Failed to add new share", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            else
            {
                MessageBox.Show("The share has successfully been added", "Share successfully added", MessageBoxButtons.OK, MessageBoxIcon.Information);
                Response.Redirect("AvailableShares.aspx");
            }

           

        }
        
    }
}