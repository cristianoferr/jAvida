using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Reflection;
using System.Text;
using System.Linq;

namespace netAvida.backend
{
    public class Utils
    {




        static readonly log4net.ILog log =
            log4net.LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);

        public static string getFormulaNameFromCode(string code)
        {
            if (!code.Contains("(")) return code;
            return code.Substring(0, code.IndexOf("("));
        }

        //Esse metodo retira o parametro do codigo
        public static string getFormulaParFromCode(string code)
        {
            string name = getFormulaNameFromCode(code);
            if (code == name) return "";
            string par = code.Substring(name.Length + 1);
            par = par.Substring(0, par.Length - 1);
            return par;
        }

        public static string[] SplitParameters(string par)
        {
            string[] pars = new string[5];
            int p = 0;
            int flagParenteses = 0;
            string s = "";
            for (int i = 0; i < par.Length; i++)
            {
                if (par[i] == '(') flagParenteses++;
                if (par[i] == ')')
                {
                    flagParenteses--;
                }
                if ((flagParenteses == 0) && (par[i] == ','))
                {
                    pars[p] = s;
                    p++;
                    s = "";
                }
                else
                    s = s + par[i];
            }
            pars[p] = s;
            for (int i = 0; i < pars.Length; i++)
            {
                if (pars[i] != null) if (pars[i].EndsWith(".0")) pars[i] = pars[i].Replace(".0", "");
            }
            return pars;
        }


        public static void Info(string msg)
        {
            Console.WriteLine("[INFO] " + msg);
            log.Info(msg);
        }

        public static void Error(string msg)
        {
            Console.WriteLine("[ERROR] " + msg);
            log.Error(msg);
        }

        static Random rnd = new Random();
        public static float Random(float min, float max)
        {
            return rnd.Next((int)min * 1000, (int)max * 1000) / 1000f;
        }

        public static int RandomInt(float min, float max)
        {
            return rnd.Next((int)min, (int)max);
        }

        public static string FormatCurrency(float valor)
        {
            return valor.ToString("0.00");
        }


        public static bool IsNumber(string input)
        {
            try
            {
                float n;
                return float.TryParse(input, out n);
            }
            catch (Exception e)
            {
                return false;
            }
        }

        public static float Round(float perc, int casas)
        {
            return (float)Math.Round(perc, casas);
        }

        public static void println(string p)
        {
            Utils.Info(p);

        }

        public static string GetEnumDescription(Enum value)
        {
            FieldInfo fi = value.GetType().GetField(value.ToString());

            DescriptionAttribute[] attributes =
                (DescriptionAttribute[])fi.GetCustomAttributes(
                typeof(DescriptionAttribute),
                false);

            if (attributes != null &&
                attributes.Length > 0)
                return attributes[0].Description;
            else
                return value.ToString();
        }

        public static float Random(Random rnd, float v, float percGap)
        {
            float min = v * 10000;
            float val = percGap * 10000;
            return rnd.Next((int)min, (int)val) / 10000f;
        }

        public static string[] SeparaEmElementos(string par)
        {
            string[] pars = new string[3];
            int p = 0;
            int flagParenteses = 0;
            string s = "";
            for (int i = 0; i < par.Length; i++)
            {
                if (par[i] == '(') flagParenteses++;
                if (par[i] == ')')
                {
                    flagParenteses--;
                }
                if (i < par.Length - 1 && flagParenteses == 0 && ((par[i] == '|' && par[i + 1] == '|') || (par[i] == '&' && par[i + 1] == '&')))
                {
                    pars[p] = s;
                    p++;
                    pars[p] = "" + par[i] + par[i + 1];
                    p++;
                    s = "";
                    i++;
                }
                else
                    s = s + par[i];
            }
            pars[p] = s;
            for (int i = 0; i < pars.Length; i++)
            {
                if (pars[i] != null)
                {
                    if (pars[i].EndsWith(".0")) pars[i] = pars[i].Replace(".0", "");
                    pars[i] = pars[i].Trim();
                }
            }
            return pars;
        }

        public static DateTime UnixTimeStampToDateTime(double unixTimeStamp)
        {
            // Unix timestamp is seconds past epoch
            System.DateTime dtDateTime = new DateTime(1970, 1, 1, 0, 0, 0, 0, System.DateTimeKind.Utc);
            dtDateTime = dtDateTime.AddSeconds(unixTimeStamp).ToLocalTime();

            return dtDateTime;
        }

        public static double DateTimeToUnixTimestamp(DateTime dateTime)
        {
            return (TimeZoneInfo.ConvertTimeToUtc(dateTime) -
                   new DateTime(1970, 1, 1, 0, 0, 0, 0, System.DateTimeKind.Utc)).TotalSeconds;
        }

        public static void CreateFolder(string subPath)
        {
            bool exists = System.IO.Directory.Exists(subPath);

            if (!exists)
                System.IO.Directory.CreateDirectory(subPath);
        }

        public static bool ContemNumero(string input)
        {
            return input.Any(c => char.IsDigit(c));
        }

        public static string GetFirstFile(string path)
        {
            string[] files = System.IO.Directory.GetFiles(System.IO.Directory.GetCurrentDirectory() + "\\" + path);
            if (files == null || files.Length == 0) return null;
            return files[0];
        }

        public static void DeleteFile(string file)
        {
            System.IO.File.Delete(file);
        }
    }

}
