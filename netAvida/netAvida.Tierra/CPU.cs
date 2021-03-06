﻿using netAvida.backend;
using netAvida.backend.interfaces;
using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using netAvida.Backend.interfaces;

namespace netAvida.Tierra
{
    public class CPU : ICPU
    {
        // Memory Instructions (soup)
        private int[] memory;
        // Memory use (owner id)
        private int[] memoryUse;
        public int memorySize;
        private IWorld mundo;
        public int lastDeallocate = 0;

        public int allocatedMemory = 0;
        private IViewLife viewer;

        public CPU(IWorld mundo, int memorySize)
        {
            this.mundo = mundo;
            this.memorySize = memorySize;
            memory = new int[memorySize];
            memoryUse = new int[memorySize];
            for (int i = 0; i < memorySize; i++)
            {
                memory[i] = 0;
                memoryUse[i] = -1;
            }

        }

        public void allocate(int sp, int memSize, IOrganismo o)
        {
            for (int i = 0; i < memSize; i++)
            {
                setMemoryOwner(sp + i, o.id);
            }
            allocatedMemory += memSize;

        }

        internal void setViewer(IViewLife viewer)
        {
            this.viewer = viewer;
        }

        public int getValidMemory(int memSize)
        {
            int sp = 0;
            int count = 0;
            if (isMemoryFree(lastDeallocate, memSize))
            {
                return lastDeallocate;
            }
            for (int i = 0; i < memorySize; i++)
            {
                if (getMemoryOwner(i) == -1)
                {
                    count++;
                }
                else
                {
                    sp = i + 1;
                    count = -1;
                }
                if (count >= memSize)
                {
                    return sp;
                }
            }
            return -1;
        }

        public bool isMemoryFree(int sp, int memSize)
        {
            for (int i = sp; i < sp + memSize; i++)
            {
                if (getMemoryOwner(i) >= 0)
                {
                    return false;
                }
            }
            return true;
        }

        public int getMemory(int pos)
        {
            return memory[ALifeConsts.calcIndex(pos, memorySize)];
        }

        public int getMemoryOwner(int pos)
        {
            return memoryUse[ALifeConsts.calcIndex(pos, memorySize)];
        }

        public void setMemoryOwner(int pos, int id)
        {
            int calcIndex = ALifeConsts.calcIndex(pos, memorySize);
            memoryUse[calcIndex] = id;
            // if (id<0){
            //drawPixel(calcIndex, id < 0 ? Color.Black : Color.Red, false);
            // }
        }

        public void start(IOrganismo o)
        {
            Color cor = Color.Blue;
           // drawOwner(o, cor);
        }

        private void DrawOwner(IOrganismo o, int cor)
        {
            int ini = o.sp();
            int size = o.getMemorySize();
            for (int i = ini; i < ini + size; i++)
            {
                int calcIndex = ALifeConsts.calcIndex(i, memorySize);
                DrawPixel(calcIndex, 255, false);
            }
        }

        public void setMemory(int pos, int v)
        {
            int calcIndex = ALifeConsts.calcIndex(pos, memorySize);
            if (calcIndex < 0)
            {
                return;
            }
            memory[calcIndex] = v;

            DrawPixel(calcIndex, v, true);
        }

        private void DrawPixel(int pos, int value, bool top)
        {
            int y = pos / TierraConsts.GRAPH_WIDTH;
            int x = pos % TierraConsts.GRAPH_WIDTH;

            int x2 = TierraConsts.GRAPH_OFFSET + x * TierraConsts.GRAPH_SIZE;
            int y2 = TierraConsts.GRAPH_OFFSET + y * TierraConsts.GRAPH_SIZE;
            if (top)
            {
                viewer.DrawRect(value, x2, y2, TierraConsts.GRAPH_SIZE, TierraConsts.GRAPH_SIZE);
            }
            else
            {
                int offset = (int)TierraConsts.MEMORY_SIZE / TierraConsts.GRAPH_WIDTH * TierraConsts.GRAPH_SIZE;
                viewer.DrawRect(value, x2, y2 + offset, TierraConsts.GRAPH_SIZE, TierraConsts.GRAPH_SIZE);
            }

        }


        public void deallocate(IOrganismo child)
        {
            int sp = child.sp();
            lastDeallocate = sp;
            for (int i = 0; i < child.getMemorySize(); i++)
            {
                if (getMemoryOwner(sp + i) > 0)
                {
                    setMemoryOwner(sp + i, -1);

                    //setMemory(sp + i, 0);

                    allocatedMemory--;
                }
            }
        }

        public float getMemoryUsePerc()
        {
            float perc = ((float)allocatedMemory / (float)memorySize) * 100;
            return perc;
        }


    }
}
