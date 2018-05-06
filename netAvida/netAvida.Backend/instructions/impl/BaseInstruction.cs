using netAvida.backend;
using netAvida.backend.interfaces;
using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace netAvida.Backend.instructions.impl
{
    public abstract class BaseInstruction:Instruction
    {
        protected string name = "";
        private int id;
        protected IWorld mundo;
        public BaseInstruction(IWorld mundo)
        {
            this.mundo = mundo;
        }

        public void setName(string n)
        {
            this.name = n;
        }

        public  abstract void executa(IOrganismo o);

        public virtual void setId(int n)
        {
            this.id = n;
            
        }

        public int getId()
        {
            return id;
        }

        public string getName()
        {
            return name;
        }


        public string tostring()
        {
            return name;
        }


        public virtual int getStep()
        {
            return 1;
        }


        public virtual string getDescription(IOrganismo o, int ip)
        {
            return name;
        }

        protected int getByteOrganismo(IOrganismo o, int pos)
        {
            int inst = o.getMemory(o.ip + pos);
            return inst;
        }

        protected string comment(string description, string comment)
        {
            int espacos = 20;
            string saida = description;
            for (int i = description.Length; i < espacos; i++)
            {
                saida += " ";
            }
            saida += "// " + comment;
            return saida;

        }


        public virtual bool requiresTemplate()
        {
            return false;
        }

    }
}
