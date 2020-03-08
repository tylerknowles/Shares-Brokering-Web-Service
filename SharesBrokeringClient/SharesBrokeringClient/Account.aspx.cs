using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Windows.Forms;

namespace SharesBrokeringClient
{
    public partial class Account : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            CurrencyListBox.Visible = false;
            ConfirmCurrencyButton.Visible = false;
            AmountLabel.Visible = false;
            AmountTextBox.Visible = false;
            ConfirmDepositButton.Visible = false;
            ConfirmWithdrawButton.Visible = false;

            SharesBrokeringWSReference.SharesBrokeringWSClient javaWSclient = new SharesBrokeringWSReference.SharesBrokeringWSClient();

            SharesBrokeringWSReference.user u = javaWSclient.getUser(Session["username"].ToString());

            CurrentCurrencyValueLabel.Text = u.Currency;
            Session["currency"] = u.Currency;
            CurrentWalletValueLabel.Text = u.Wallet.ToString();
            Session["wallet"] = u.Wallet;
        }

        protected void DepositButton_Click(object sender, EventArgs e)
        {
            AmountLabel.Visible = true;
            AmountTextBox.Visible = true;
            ConfirmDepositButton.Visible = true;
        }

        protected void WithdrawButton_Click(object sender, EventArgs e)
        {
            AmountLabel.Visible = true;
            AmountTextBox.Visible = true;
            ConfirmWithdrawButton.Visible = true;
        }

        protected void ChangeCurrencyButton_Click(object sender, EventArgs e)
        {
            CurrencyListBox.Visible = true;
            ConfirmCurrencyButton.Visible = true;
            SharesBrokeringWSReference.SharesBrokeringWSClient javaWSclient = new SharesBrokeringWSReference.SharesBrokeringWSClient();

            String[] currencies = javaWSclient.GetCurrencyList();
            foreach (String currency in currencies)
            {
                CurrencyListBox.Items.Add(currency);
            }
        }
        protected void ConfirmCurrencyButton_Click(object sender, EventArgs e)
        {
            SharesBrokeringWSReference.SharesBrokeringWSClient javaWSclient = new SharesBrokeringWSReference.SharesBrokeringWSClient();

            if (!javaWSclient.ExchangeWallet(Session["username"].ToString(), CurrencyListBox.SelectedValue.Substring(0, 3)))
            {
                MessageBox.Show("Failed to convert currency and wallet amount", "Currency conversion failed", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            else
            {
                MessageBox.Show("Your currency and wallet amount has successfully been converted", "Currency conversion successful", MessageBoxButtons.OK, MessageBoxIcon.Information);
                Page_Load(sender, e);
            }
        }

        protected void ConfirmDepositButton_Click(object sender, EventArgs e)
        {
            SharesBrokeringWSReference.SharesBrokeringWSClient javaWSclient = new SharesBrokeringWSReference.SharesBrokeringWSClient();

            if(!javaWSclient.adjustWallet(Session["username"].ToString(),Double.Parse(AmountTextBox.Text),true))
            {
                MessageBox.Show("Failed to deposit funds, please try again", "Deposit failed", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            else
            {
                MessageBox.Show("Your deposit has successfully been added", "Deposit successfully added", MessageBoxButtons.OK, MessageBoxIcon.Information);
                Page_Load(sender, e);
            }
        }

        protected void ConfirmWithdrawButton_Click(object sender, EventArgs e)
        {
            SharesBrokeringWSReference.SharesBrokeringWSClient javaWSclient = new SharesBrokeringWSReference.SharesBrokeringWSClient();

            if (!javaWSclient.adjustWallet(Session["username"].ToString(), Double.Parse(AmountTextBox.Text), false))
            {
                MessageBox.Show("Failed to withdraw funds, please try again", "Withdraw failed", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            else
            {
                MessageBox.Show("Your funds have successfully been withdrew", "Funds successfully withdrew", MessageBoxButtons.OK, MessageBoxIcon.Information);
                Page_Load(sender, e);
            }
        }

    }
}