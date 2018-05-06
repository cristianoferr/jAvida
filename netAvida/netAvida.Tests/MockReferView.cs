using netAvida.Backend.interfaces;
using netAvida.interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using netAvida.backend.interfaces;

namespace netAvida.Tests
{
    class MockReferView : IReferView, IViewLife
    {
        public void CheckTick()
        {
            
        }

        public void DrawCircle(int value, int x, int y, int i, int j)
        {
            
        }

        public void DrawLine(int value, int x1, int y1, int x2, int y2)
        {
            
        }

        public void DrawRect(int value, int x, int y, int graphWidth, int graphWidth2)
        {
            
        }

        public int SelectedOrgId()
        {
            return 0;
        }

        public void SetMundo(IWorld world)
        {
            
        }

        public void SetSelected(IOrganismo o)
        {
            
        }

        public void ShowDetails(IOrganismo o)
        {
            
        }

        public void TranspRect(int i, int j, int p, int p2)
        {
            
        }

        public void Update()
        {
            
        }

        void IReferView.AddItem(string v, object tradeSystem)
        {
            
        }

        void IReferView.AddList(string v, string papel)
        {
            
        }

        void IReferView.AddOrganismo(IOrganismo o)
        {
            
        }

        void IReferView.ClearList(string v)
        {
            
        }

        void IReferView.ClearRows(string v)
        {
            
        }

        bool IReferView.IsChecked(string v)
        {
            return true;   
        }

        void IReferView.RemoveOrganismo(IOrganismo o)
        {
            
        }

        void IReferView.SetChecked(string v, bool flagCompra)
        {
            
        }

        void IReferView.SetEnabled(string v1, bool v2)
        {
            
        }

        void IReferView.SetListItem(string v, int index, object var)
        {
            
        }

        void IReferView.SetText(string v1, string v2)
        {
            
        }

        void IReferView.SetVisible(string v1, bool v2)
        {
            
        }

        string IReferView.Text(string v)
        {
            return "";   
        }

        void IReferView.Update()
        {
            
        }
    }
}
