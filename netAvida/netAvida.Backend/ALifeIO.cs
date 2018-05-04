using System;
using netAvida.backend.interfaces;
using System.IO;
using netAvida.backend;

namespace netAvida.backend
{
    internal class ALifeIO
    {
        internal static void saveToFile(IOrganismo organismo)
        {
            string orgCode = organismo.ToString();
            string path = ALifeConsts.GENEBANK_PATH +"saved/" +ALifeConsts.GENEBANK_PATH + organismo.hash() + ".txt";
            File.WriteAllText(path, orgCode);
            Log.Info("Salvando organismo "+path);
        }

        internal static IOrganismo loadFromFile(string fileName, MundoBase mundo)
        {
            string path = ALifeConsts.GENEBANK_PATH + fileName;

            // This text is added only once to the file.
            if (!File.Exists(path))
            {
                // Create a file to write to.
                string[] readText = File.ReadAllLines(path);
                IOrganismo o = null;
                    int memSize = getSize(readText[0]);
                    o = mundo.criaOrganismo(memSize);

                    loadInstructions(readText, mundo, o);
                return o;
            }
            else
            {
                Log.fatal("Arquivo não encontrado: " + path);
                return null;
            }

        }

        private static int getSize(string line)
        {
            string sizeStr = "Size:";
            string pos = line.Substring(line.IndexOf(sizeStr) + sizeStr.Length).Trim();
            return int.Parse(pos);
        }

        public static void loadRegs(string line, IOrganismo o)
        {
            if (line == null)
            {
                return;
            }
            if (line.StartsWith("#REGS: "))
            {
                string[] pars = line.Split(' ');
                for (int i = 0; i < pars.Length; i++)
                {
                    string par = pars[i].Trim();
                    if (par.Contains("["))
                    {
                        par = par.Substring(0, par.IndexOf("[")).Trim();
                    }
                    if (par.Contains("X:"))
                    {
                        string letter = par.Substring(0, 1);
                        string val = par.Substring(par.IndexOf(":") + 1);
                        o.setReg(ALifeConsts.getLetterNumber(letter.ToCharArray()[0]), int.Parse(val));

                    }
                    if (par.Contains("SP:"))
                    {
                        string val = par.Substring(par.IndexOf(":") + 1);
                        o.setStartPoint(int.Parse(val));
                    }
                    if (par.Contains("IP:"))
                    {
                        string val = par.Substring(par.IndexOf(":") + 1);
                        o.ip = int.Parse(val) + 1;
                    }
                }
            }

        }



        private static void loadInstructions(string[] readText, IWorld mundo, IOrganismo o)
        {
            int pos = 0;
            for (int i = 1; i < readText.Length; i++) {
                string line = readText[i];

                loadRegs(line, o);
                if (line != null)
                {
                    pos = loadInstruction(line.Trim(), mundo, o, pos);
                }
            }
        }

        private static int loadInstruction(string line, IWorld mundo, IOrganismo o, int pos)
        {
            if (line.StartsWith("#"))
            {
                return pos;
            }
            if (line.Contains("="))
            {
                line = line.Substring(line.IndexOf("=") + 1).Trim();
            }
            if (line.Contains("//"))
            {
                line = line.Substring(0, line.IndexOf("//")).Trim();
            }
            line += " ";
            int indexOf = line.IndexOf(" ");
            string param = line.Substring(indexOf).Trim();
            string instrCode = line.Substring(0, indexOf).Trim();

            Instruction instruction = mundo.getInstruction(instrCode);
            int step = 1;
            if (instruction == null)
            {
                o.setMemory(o.sp() + pos, int.Parse(instrCode));
            }
            else
            {
                o.setMemory(o.sp() + pos, instruction.getId());
                step = instruction.getStep();
            }
            pos++;
            if (step > 1)
            {
                pos = loadParams(instruction, param, o, pos);
            }
            return pos;
        }

        private static int loadParams(Instruction instruction, string parms, IOrganismo o, int pos)
        {
            string[] pars = parms.Split(',');
            for (int i = 0; i < pars.Length; i++)
            {
                string par = pars[i].Trim();
                if (par.Length > 0)
                {
                    char p = par.ToCharArray()[0];
                    if (p >= 'A' && p <= 'Z')
                    {
                        o.setMemory(o.sp() + pos + i, ALifeConsts.getLetterNumber(p));
                    }
                    else
                    {
                        o.setMemory(o.sp() + pos + i, int.Parse(par));
                    }
                }
                else
                {
                    pos--;
                }
            }
            return pos + pars.Length;
        }
    }
}