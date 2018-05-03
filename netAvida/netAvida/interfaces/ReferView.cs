using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace netAvida.interfaces
{
    public class ReferView : IReferView
    {
        Dictionary<string, Control> controles;
        private ToolStripStatusLabel status;

        public ReferView()
        {
            controles = new Dictionary<string, Control>();
        }


        public Control GetControl(string v)
        {
            return controles[v];
        }

        public void Register(string key, Control listPapeis)
        {
            controles.Add(key, listPapeis);
        }

        public void AddList(string v, string papel)
        {
            ListBox c = controles[v] as ListBox;
            c.Items.Add(papel);
        }

        public void ClearList(string v)
        {
            ListBox c = controles[v] as ListBox;
            if (c != null)
                c.Items.Clear();
            ComboBox cb = controles[v] as ComboBox;
            if (cb != null)
                cb.Items.Clear();
        }

        public void ClearRows(string v)
        {
            DataGridView c = controles[v] as DataGridView;
            c.Rows.Clear();
        }

        public DataGridViewRowCollection GetRows(string v)
        {
            DataGridView c = controles[v] as DataGridView;
            return c.Rows;
        }



        public bool IsChecked(string v)
        {
            CheckBox c = controles[v] as CheckBox;
            if (c != null)
                return c.Checked;
            RadioButton r = controles[v] as RadioButton;
            if (r != null)
                return r.Checked;
            return false;
        }

        public void SetChecked(string v, bool flagCompra)
        {
            CheckBox c = controles[v] as CheckBox;
            if (c != null) c.Checked = flagCompra;
            RadioButton r = controles[v] as RadioButton;
            if (r != null) r.Checked = flagCompra;
        }

        public void SetText(string v1, string v2)
        {
            TextBox c = controles[v1] as TextBox;
            if (c != null)
                c.Text = v2;
            MaskedTextBox c2 = controles[v1] as MaskedTextBox;
            if (c2 != null)
                c2.Text = v2;
            Label l = controles[v1] as Label;
            if (l != null)
                l.Text = v2;
        }

        public string Text(string v)
        {
            TextBox c = controles[v] as TextBox;
            if (c != null)
                return c.Text;
            MaskedTextBox c2 = controles[v] as MaskedTextBox;
            if (c2 != null)
                return c2.Text;
            Label l = controles[v] as Label;
            return l.Text;
        }

        public void SetVisible(string v1, bool v2)
        {
            Control c = controles[v1] as Control;
            c.Visible = v2;
        }

        public void SetEnabled(string v1, bool v2)
        {
            Control c = controles[v1] as Control;
            c.Enabled = v2;
        }

        public void AddItem(string v, Object tradeSystem)
        {
            ListBox l = controles[v] as ListBox;
            if (l != null) l.Items.Add(tradeSystem);
            ComboBox c = controles[v] as ComboBox;
            if (c != null) c.Items.Add(tradeSystem);
        }

        public void SetListItem(string v, int index, object var)
        {
            ListBox l = controles[v] as ListBox;
            l.Items[index] = var;
        }

        public void SetStatusComponent(ToolStripStatusLabel txtStatus)
        {
            this.status = txtStatus;
        }

        public void SetStatus(string v)
        {
            status.Text = v;
        }

        public void SetTitle(string v)
        {
        }
    }
}
