using netAvida.backend.interfaces;
using System;
using System.Collections.Generic;
using System.Text;

namespace netAvida.backend
{
    public class Pilha
    {
        private int[] stack;
        int pos = 0;
        private IOrganismo organismo;
        public Pilha(IOrganismo o)
        {
            stack = new int[CONSTS.MAX_STACK];
            this.organismo = o;
        }
        public void push(int i)
        {
            if (pos >= CONSTS.MAX_STACK)
            {
                pos = CONSTS.MAX_STACK - 1;
            }
            stack[pos] = i;
            pos++;
        }
        public int pop()
        {
            pos--;
            if (pos < 0)
            {
                pos = 0;
            }
            int v = stack[pos];
            return v;
        }
        public String debugInfo()
        {
            String s = "Stack: ";
            for (int i = 0; i < pos; i++)
            {
                s += CONSTS.numberFormat(stack[i]) + " | ";
            }
            return s;
        }
    }
}
