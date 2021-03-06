﻿using netAvida.backend.interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace netAvida.interfaces
{
    public interface IReferView
    {
        void Update();
        void ClearList(string v);
        void AddList(string v, string papel);
        void SetChecked(string v, bool flagCompra);
        void SetText(string v1, string v2);
        bool IsChecked(string v);
        string Text(string v);
        void ClearRows(string v);
        //DataGridViewRowCollection GetRows(string v);
        void SetVisible(string v1, bool v2);
        void SetEnabled(string v1, bool v2);
        void AddItem(string v, Object tradeSystem);
        void SetListItem(string v, int index, Object var);

        void RemoveOrganismo(IOrganismo o);
        void AddOrganismo(IOrganismo o);
    }
}
