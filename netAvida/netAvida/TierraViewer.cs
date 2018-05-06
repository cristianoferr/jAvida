using netAvida.backend;
using netAvida.interfaces;
using netAvida.Tierra;
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
        TerraDrawerMultiThread drawer;

        public TierraViewer()
        {
            InitializeComponent();
            RegisterElements();
            drawer = new TerraDrawerMultiThread(panelDraw, referView, TierraConsts.MEMORY_SIZE);
            controller = new TierraController(referView, drawer);
        }

        private void RegisterElements()
        {
            referView = new ReferViewMultiThread();
            referView.Register("lblOrgs", lblOrgs);
            referView.Register("lblRatio", lblRatio);
            referView.Register("dataGridOrgs", dataGridOrgs);

        }

        private void btnRun_Click(object sender, EventArgs e)
        {
            controller.Roda();

        }

        private void btnRunSingleThread_Click(object sender, EventArgs e)
        {
            controller.RodaSingleThread();
        }

        private void panelDraw_Paint(object sender, PaintEventArgs e)
        {
            drawer.Repaint();
        }
    }
}
