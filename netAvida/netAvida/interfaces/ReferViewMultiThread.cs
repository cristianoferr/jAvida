using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace netAvida.interfaces
{
    class DelayedAction
    {
        public string action;
        public string key;
        public string value;
        public object valueObj;
        public int intVal;


        public DelayedAction(string action, string key)
        {
            this.action = action;
            this.key = key;
        }
        public DelayedAction(string action, string key, string value):this(action,key)
        {
            this.value = value;
        }

        public DelayedAction(string action, string key, object obj) : this(action, key)
        {
            this.valueObj = obj;
        }

        public DelayedAction(string action, string key, int intVal, object obj) : this(action, key, obj)
        {
            this.intVal = intVal;
        }
    }
    public class ReferViewMultiThread : IReferView
    {
        private ReferView view;
        private IList<DelayedAction> actions;

        public ReferViewMultiThread()
        {
            this.view = new ReferView();
            actions = new List<DelayedAction>();
        }

        public void Update()
        {
            foreach (DelayedAction action in actions)
            {
                if (action.action== "AddList")
                {
                    view.AddList(action.key, action.value);
                }
                else if (action.action == "ClearList")
                {
                    view.ClearList(action.key );
                }
                else if (action.action == "ClearRows")
                {
                    view.ClearRows(action.key );
                }
                else if (action.action == "SetChecked")
                {
                    view.SetChecked(action.key,action.value=="T");
                }
                else if (action.action == "SetText")
                {
                    view.SetText(action.key, action.value);
                }
                else if (action.action == "SetVisible")
                {
                    view.SetVisible(action.key, action.value == "T");
                }
                else if (action.action == "SetEnabled")
                {
                    view.SetEnabled(action.key, action.value == "T");
                }
                else if (action.action == "AddItem")
                {
                    view.AddItem(action.key, action.valueObj);
                }
                else if (action.action == "SetListItem")
                {
                    view.SetListItem(action.key, action.intVal, action.valueObj);
                }


            }
            actions.Clear();
        }


        public Control GetControl(string v)
        {
            return view.GetControl(v);
        }

        /**
         * Adiciona uma ação para ser executado quando for seguro
         */
        private void AddDelayed(string action, string key, string value=null)
        {
            actions.Add(new DelayedAction(action, key, value));
        }

        private void AddDelayedObject(string action, string key, object value = null)
        {
            actions.Add(new DelayedAction(action, key, value));
        }

        private void AddDelayedIntObject(string action, string key, int index, object var)
        {
            actions.Add(new DelayedAction(action, key, index,var));
        }

        public void AddList(string v, string papel)
        {
            AddDelayed("AddList", v, papel);
        }


        public void ClearList(string v)
        {
            AddDelayed("ClearList", v);
        }

        public void ClearRows(string v)
        {
            AddDelayed("ClearRows", v);
            
        }


        public bool IsChecked(string v)
        {
            return view.IsChecked(v);
        }

        public void SetChecked(string v, bool flagCompra)
        {
            AddDelayed("SetChecked", v,flagCompra?"T":"F");
            
        }

        public void SetText(string v1, string v2)
        {
            AddDelayed("SetText", v1, v2);
            
        }

        public string Text(string v)
        {
            return view.Text(v);
        }

        public void SetVisible(string v1, bool v2)
        {
            AddDelayed("SetVisible", v1, v2?"T":"F");
            
        }

        public void SetEnabled(string v1, bool v2)
        {
            AddDelayed("SetEnabled", v1, v2 ? "T" : "F");
          
        }

        public void AddItem(string v, Object tradeSystem)
        {
            AddDelayedObject("AddItem", v,tradeSystem);
        }

        public void SetListItem(string v, int index, object var)
        {
            AddDelayedIntObject("SetListItem", v,index, var);
        }

        public void Register(string key, Control obj)
        {
            view.Register(key, obj);
        }
    }
}
