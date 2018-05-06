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
using netAvida.Tierra;

namespace netAvida
{
    public class TerraDrawer : IViewLife
    {
        private Panel panelDraw;
        private IReferView refer;
        private IWorld mundo;
        Graphics graphics;
        Graphics graphicsCanvas;

        ShapeContainer canvas;
        IList<Shape> shapes;
        Bitmap img;




        public TerraDrawer(Panel panelDraw, IReferView refer,int memorySize)
        {
            this.panelDraw = panelDraw;
            this.refer = refer;

            canvas = new ShapeContainer();
            canvas.Parent = panelDraw;
            shapes=new List<Shape>();

            graphicsCanvas = canvas.CreateGraphics();
            this.width = TierraConsts.GRAPH_OFFSET*2+TierraConsts.GRAPH_WIDTH* TierraConsts.GRAPH_SIZE;
            this.height = TierraConsts.GRAPH_OFFSET*2 + (memorySize / TierraConsts.GRAPH_WIDTH) * TierraConsts.GRAPH_SIZE;
            img = new Bitmap(this.width, this.height);
            graphics= Graphics.FromImage(img);
            this.clip = new Rectangle(0, 0, this.width, this.height);

        }


        public void SetMundo(IWorld world)
        {
            this.mundo= world;
        }

        public void Update()
        {
            
            //canvas.DrawToBitmap(img, clip);
            graphicsCanvas.DrawImage(img,0,0,this.width,this.height);
            
            
        }

            public void CheckTick()
        {
            float perc = mundo.getMemoryUsePerc();
            perc = (float)Utils.Round(perc, 2);
            refer.SetText("lblOrgs", "#:" + mundo.size() + "  " + perc + "%");
            refer.SetText("lblRatio","Mut.Ratio: "
                    + Utils.Round(mundo.getMutation().calcMutationChance(1, null),2));

        }

        public void DrawCircle(int red, int x, int y, int i, int j)
        {
            //OvalShape oval;
            throw new NotImplementedException();
        }

        public void DrawLine(int cor, int x1, int y1, int x2, int y2)
        {
            
          /*  LineShape theLine = new LineShape();
            //shapes.Add(theLine);
            theLine.Parent = canvas;
            theLine.BorderColor = cor;
            theLine.StartPoint = new System.Drawing.Point(x1, y1);
            theLine.EndPoint = new System.Drawing.Point(x2, y2);*/
        }

        Dictionary<int, Brush> brushes = new Dictionary<int, Brush>();
        public int width;
        public int height;
        private Rectangle clip;

        public void DrawRect(int value, int x, int y, int graphWidth, int graphHeight)
        {
            Brush brush = GetBrush( value);
            graphics.FillRectangle(brush, x, y, graphWidth, graphHeight);
        }

        Dictionary<int, Color> cores = new Dictionary<int, Color>();

        private Brush GetBrush(int value)
        {
            int id = value;
            Color cor;
            if (cores.ContainsKey(id)) {
                cor = cores[value];
            } else 
            {
                value = value * 1000;
                int red = value % 255;
                value -= red*40;
                int green = value % 255;
                value -= green*30;
                int blue = value % 255;

                cor = Color.FromArgb(red > 0 ? red : 0, green > 0 ? green : 0, blue > 0 ? blue : 0);
                cores.Add(id, cor);
            }

            Brush brush;
            if (brushes.ContainsKey(cor.ToArgb()))
            {
                brush = brushes[cor.ToArgb()];
            }
            else
            {
                brush = new SolidBrush(cor);
                brushes.Add(cor.ToArgb(), brush);
            }

            return brush;
        }

        public void Repaint()
        {
           // throw new NotImplementedException();
        }

        public int SelectedOrgId()
        {
            throw new NotImplementedException();
        }

        public void SetSelected(IOrganismo o)
        {
            throw new NotImplementedException();
        }

        public void ShowDetails(IOrganismo o)
        {
            throw new NotImplementedException();
        }

        public void TranspRect(int i, int j, int p, int p2)
        {
            throw new NotImplementedException();
        }
    }
}
