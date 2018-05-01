using netAvida.backend.interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace netAvida.Backend.instructions
{
    public class BaseInstruction : IInstruction
    {
        protected String name = "";
        private int id;
        protected IWorld mundo;
        public BaseInstruction(IWorld mundo)
        {
            this.mundo = mundo;
        }

        public void setName(String n)
        {
            this.name = n;
        }

        public void setId(int n)
        {
            this.id = n;
        }

        public int getId()
        {
            return id;
        }

        public String getName()
        {
            return name;
        }


        public String toString()
        {
            return name;
        }


        public int getStep()
        {
            return 1;
        }


        public String getDescription(IOrganismo o, int ip)
        {
            return name;
        }

        protected int getByteOrganismo(IOrganismo o, int pos)
        {
            int inst = o.getMemory(o.ip + pos);
            return inst;
        }

        protected String comment(String description, String comment)
        {
            int espacos = 20;
            String saida = description;
            for (int i = description.Length; i < espacos; i++)
            {
                saida += " ";
            }
            saida += "// " + comment;
            return saida;

        }


        public bool requiresTemplate()
        {
            return false;
        }


    }
}
