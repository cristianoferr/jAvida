using System;
using System.Collections.Generic;
using System.Text;

namespace netAvida.backend.interfaces
{
    interface ICPU
    {
        void setMemory(int memoryPos, int rnd);
        int getMemory(int i);
    }
}
