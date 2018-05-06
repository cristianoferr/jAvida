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
    class DelayedDraw
    {
        public string key;
        public int color;
        public int a;
        public int b;
        public int c;
        public int d;

        public DelayedDraw(string key, int color, int a, int b, int c, int d)
        {
            this.key = key;
            this.color = color;
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
        }
    }

    public class TerraDrawerMultiThread : IViewLife
    {
        TerraDrawer drawer;
        // IList<Shape> shapes;
        private IList<DelayedDraw> actions;
        private IList<DelayedDraw> actionsRecycled;

        public TerraDrawerMultiThread(Panel panelDraw, IReferView refer,int memorySize)
        {
            drawer = new TerraDrawer(panelDraw, refer,memorySize);
            actions = new List<DelayedDraw>();
            actionsRecycled = new List<DelayedDraw>();
            //DrawRect(0, 0, 0, drawer.width, drawer.height);
            AddDelayed("DrawRect", 0, 0, 0, drawer.width, drawer.height);
        }

        internal void Repaint()
        {
            drawer.Repaint();
        }

        public void Update()
        {
            int size = actions.Count;
            int pos = 0;
            bool changed = false;
            while (pos<size)
            {
                changed = true;
                DelayedDraw action = actions[0];
                if (action != null)
                {
                    if (action.key == "DrawCircle")
                    {
                        drawer.DrawCircle(action.color, action.a, action.b, action.c, action.d);
                    }
                    else if (action.key == "DrawLine")
                    {
                        drawer.DrawLine(action.color, action.a, action.b, action.c, action.d);
                    }
                    else if (action.key == "DrawRect")
                    {
                        drawer.DrawRect(action.color, action.a, action.b, action.c, action.d);
                    }
                }
                try
                {
                actions.RemoveAt(0);
                } catch (Exception)
                {
                }
                //actionsRecycled.Add(action);
                pos++;
            }
            if (changed)
            {
                drawer.Update();
            }


        }
        private void AddDelayed(string key, int color, int a, int b, int c, int d)
        {
            DelayedDraw draw;
          
                draw = new DelayedDraw(key, color, a, b, c, d);
           // }
            actions.Add(draw);
        }

        public void SetMundo(IWorld world)
        {
            drawer.SetMundo(world);
        }

        public void CheckTick()
        {
            drawer.CheckTick();
        }

        public void DrawCircle(int color, int x, int y, int i, int j)
        {
            AddDelayed("DrawCircle", color, x, y, i, j);
        }


        public void DrawLine(int cor, int x1, int y1, int x2, int y2)
        {
            AddDelayed("DrawLine", cor, x1, y1, x2, y2);
        }

        public void DrawRect(int cor, int x, int y, int graphWidth, int graphWidth2)
        {
            if (!ALifeConsts.starting)
            {

            }
            //AddDelayed("DrawRect", cor, x, y, graphWidth, graphWidth2);
            drawer.DrawRect(cor, x, y, graphWidth, graphWidth2);
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
