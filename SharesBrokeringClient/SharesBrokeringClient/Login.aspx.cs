using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Windows.Forms;

namespace SharesBrokeringClient
{
    public partial class Login : Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {

        }

        protected void LogIn(object sender, EventArgs e)
        {
            SharesBrokeringWSReference.SharesBrokeringWSClient javaWSclient = new SharesBrokeringWSReference.SharesBrokeringWSClient();

            if (!javaWSclient.Login(UsernameTextBox.Text, PasswordTextBox.Text))
            {
                MessageBox.Show("Either the Username or Password entered is incorrect", "Failed to log in", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            else
            {
                MessageBox.Show("You have successfully logged in", "Login successful", MessageBoxButtons.OK, MessageBoxIcon.Information);
                Session["username"] = UsernameTextBox.Text;
                Response.Redirect("AvailableShares.aspx");
            }
            
        }


    }
}