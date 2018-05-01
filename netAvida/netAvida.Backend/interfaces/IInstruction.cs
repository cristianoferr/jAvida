using netAvida.backend.interfaces;
using System;
using System.Collections.Generic;
using System.Text;

namespace netAvida.backend
{
    public interface Instruction
    {
        void executa(IOrganismo o);

        void setName(String name);

        void setId(int id);

        int getId();

        String getName();


        int getStep();

        String getDescription(IOrganismo o, int ip);

        //se true então é uma instrução que usa de template (sequencias de nop0 e nop1)
        bool requiresTemplate();
    }
    }
