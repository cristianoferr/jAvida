using netAvida.backend.interfaces;
using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace netAvida.Backend.interfaces
{
    public interface IViewLife
    {
        void drawRect(Color cor, int x, int y, int graphWidth, int graphWidth2);

        void showDetails(IOrganismo o);

        void repaint();

        void checkTick();

        void transpRect(int i, int j, int p, int p2);

        void line(int xp, int yp, int i, int j);

        int selectedOrgId();

        void drawCircle(Color red, int x, int y, int i, int j);

        void drawLine(Color cor, int x1, int y1, int x2, int y2);

        void setSelected(IOrganismo o);
    }
}
