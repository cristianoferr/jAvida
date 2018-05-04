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
    public partial class TierraViewer : Form
    {
        TierraController controller;
        ReferViewMultiThread referView;
        TerraDrawer drawer;

        public TierraViewer()
        {
            InitializeComponent();
            RegisterElements();
            drawer = new TerraDrawer(panelDraw, referView);
            controller = new TierraController(referView, drawer);
        }

        private void RegisterElements()
        {
            referView = new ReferViewMultiThread();
            referView.Register("lblOrgs", lblOrgs);
            referView.Register("lblRatio", lblRatio);
            
        }

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
