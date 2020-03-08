using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Windows.Forms;

namespace SharesBrokeringClient
{
    public partial class NewUser : Page
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

        protected void AddUser(object sender, EventArgs e)
        {
            SharesBrokeringWSReference.SharesBrokeringWSClient javaWSclient = new SharesBrokeringWSReference.SharesBrokeringWSClient();

            if (!javaWSclient.AddNewUser(UsernameTextBox.Text, PasswordTextBox.Text, CurrencyListBox.SelectedValue.Substring(0, 3),Double.Parse(WalletTextBox.Text)))
            {
                MessageBox.Show("That Username already exists", "Failed to add new User", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            else
            {
                MessageBox.Show("The User has successfully been added", "User successfully added", MessageBoxButtons.OK, MessageBoxIcon.Information);
                Response.Redirect("Login.aspx");
            }
            
        }

    }
}