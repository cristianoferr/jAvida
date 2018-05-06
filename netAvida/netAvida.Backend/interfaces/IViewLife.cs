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
        void DrawRect(int value, int x, int y, int graphWidth, int graphWidth2);

        void ShowDetails(IOrganismo o);

        void Update();

        void CheckTick();

        void TranspRect(int i, int j, int p, int p2);

        int SelectedOrgId();

        void DrawCircle(int value, int x, int y, int i, int j);

        void DrawLine(int value, int x1, int y1, int x2, int y2);
        void SetMundo(IWorld world);
        void SetSelected(IOrganismo o);

        
    }
}
