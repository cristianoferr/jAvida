using netAvida.interfaces;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace netAvida
{
    public partial class TierraViewer : Form, IReferView
    {
        TierraController controller;
        ReferView referView;
        TerraDrawer drawer;

        public TierraViewer()
        {
            InitializeComponent();
            drawer = new TerraDrawer(panelDraw,this);
            controller = new TierraController(this, drawer);
            RegisterElements();
        }

        private void RegisterElements()
        {
            referView = new ReferView();
            referView.Register("lblOrgs", lblOrgs);
            referView.Register("lblRatio", lblRatio);
            
        }

        #region IReferView

        void IReferView.ClearList(string v)
        {
            referView.ClearList(v);
        }

        void IReferView.AddList(string v, string papel)
        {
            referView.AddList(v, papel);
        }

        void IReferView.SetChecked(string v, bool flagCompra)
        {
            referView.SetChecked(v, flagCompra);
        }

        void IReferView.SetText(string v1, string v2)
        {
            referView.SetText(v1, v2);
        }

        bool IReferView.IsChecked(string v)
        {
            return referView.IsChecked(v);
        }

        string IReferView.Text(string v)
        {
            return referView.Text(v);
        }

        void ClearRows(string v)
        {
            referView.ClearRows(v);
        }


        void IReferView.ClearRows(string v)
        {
            referView.ClearRows(v);
        }

        public void SetVisible(string v1, bool v2)
        {
            referView.SetVisible(v1, v2);
        }

        public void SetEnabled(string v1, bool v2)
        {
            referView.SetEnabled(v1, v2);
        }

        public void AddItem(string v, object tradeSystem)
        {
            referView.AddItem(v, tradeSystem);
        }

        public void SetListItem(string v, int index, object var)
        {
            referView.SetListItem(v, index, var);
        }
        public void SetStatus(string v)
        {
            referView.SetStatus(v);
        }
        public void SetTitle(string v)
        {
            Text = "Backtester [" + v + "]";
        }
        #endregion IReferView

       

        private void btnRun_Click(object sender, EventArgs e)
        {
            controller.Roda();

        }

        private void btnRunSingleThread_Click(object sender, EventArgs e)
        {
            controller.RodaSingleThread();
        }
    }
}
