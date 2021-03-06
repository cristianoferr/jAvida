﻿using netAvida.backend.interfaces;
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
            stack = new int[ALifeConsts.MAX_STACK];
            this.organismo = o;
        }
        public void push(int i)
        {
            if (pos >= ALifeConsts.MAX_STACK)
            {
                pos = ALifeConsts.MAX_STACK - 1;
            }
            stack[pos] = i;
            pos++;
        }
        public int GetAtPos(int i)
        {
            return stack[i];
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
                s += ALifeConsts.numberFormat(stack[i]) + " | ";
            }
            return s;
        }

        public int Size()
        {
            return pos;
        }
    }
}
