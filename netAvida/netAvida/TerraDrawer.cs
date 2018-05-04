using netAvida.Backend.interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using netAvida.backend.interfaces;
using System.Drawing;
using System.Windows.Forms;
using netAvida.interfaces;
using netAvida.backend;
using Microsoft.VisualBasic.PowerPacks;

namespace netAvida
{
    public class TerraDrawer : IViewLife
    {
        private Panel panelDraw;
        private IReferView refer;
        private IWorld mundo;

        ShapeContainer canvas;
        IList<Shape> shapes;

        public TerraDrawer(Panel panelDraw, IReferView refer)
        {
            this.panelDraw = panelDraw;
            this.refer = refer;

            canvas = new ShapeContainer();
            canvas.Parent = panelDraw;
            shapes=new List<Shape>();
        }

        public void SetMundo(IWorld world)
        {
            this.mundo= world;
        }

        public void checkTick()
        {
            float perc = mundo.getMemoryUsePerc();
            perc = (float)Utils.Round(perc, 2);
            refer.SetText("lblOrgs", "#:" + mundo.size() + "  " + perc + "%");
            refer.SetText("lblRatio","Mut.Ratio: "
                    + Utils.Round(mundo.getMutation().calcMutationChance(1, null),2));

        }

        public void drawCircle(Color red, int x, int y, int i, int j)
        {
            throw new NotImplementedException();
        }

        public void drawLine(Color cor, int x1, int y1, int x2, int y2)
        {
            LineShape theLine = new LineShape();
            shapes.Add(theLine);
            theLine.Parent = canvas;
            theLine.BorderColor = cor;
            theLine.StartPoint = new System.Drawing.Point(x1, y1);
            theLine.EndPoint = new System.Drawing.Point(x2, y2);
        }

        public void drawRect(Color cor, int x, int y, int graphWidth, int graphWidth2)
        {
            RectangleShape theLine = new RectangleShape(x, y, graphWidth, graphWidth2);
            shapes.Add(theLine);
            theLine.Parent = canvas;
            theLine.BorderColor = cor;
            theLine.FillColor = cor;
        }
        

        public void repaint()
        {
           // throw new NotImplementedException();
        }

        public int selectedOrgId()
        {
            throw new NotImplementedException();
        }

        public void setSelected(IOrganismo o)
        {
            throw new NotImplementedException();
        }

        public void showDetails(IOrganismo o)
        {
            throw new NotImplementedException();
        }

        public void transpRect(int i, int j, int p, int p2)
        {
            throw new NotImplementedException();
        }
    }
}
