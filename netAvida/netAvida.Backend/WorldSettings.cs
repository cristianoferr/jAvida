using System;
using System.Collections.Generic;
using System.Text;

namespace netAvida.backend
{
    public class WorldSettings
    {

        public const string DIV_MUT_CHANCE = "divMutChance";
        public const string RANDOM_MUTATION_CHANCE = "mutationChance";
        public const string ERROR_KILL_CHANCE = "errorKillChance";
        public const string WRITE_MUTATION_CHANCE = "writeInstructionMutationChance";
        public const string OCUPPATION_RATIO = "occupationRatio";
        private string iniFile;
        List<string> configs = new List<string>();

        public WorldSettings(string file, int maxOrgs)
        {
            this.iniFile = file;
            this.maxOrganismos = maxOrgs;

            configs.Add(DIV_MUT_CHANCE);
            configs.Add(RANDOM_MUTATION_CHANCE);
            configs.Add(ERROR_KILL_CHANCE);
            configs.Add(WRITE_MUTATION_CHANCE);
            configs.Add(OCUPPATION_RATIO);
            loadIni();
        }

        private void loadIni()
        {
            File file = new File(iniFile);

            ini = new BasicIniFile();

            IniFileReader reader = new IniFileReader(ini, file);
            try
            {
                reader.read();
            }
            catch (FormatException e)
            {
                // e.printStackTrace();
            }
            catch (IOException e)
            {
                // e.printStackTrace();
            }
            initSettingsSection();

        }

        private void initSettingsSection()
        {
            settings = ini.getSection("SETTINGS");
            if (settings == null)
            {
                settings = ini.addSection("SETTINGS");
            }

            autoSave = false;
            foreach (string prop in configs)
            {
                IniItem item = settings.getItem(prop);
                if (item != null)
                {
                    changeProperty(prop, Float.parseFloat(item.getValue()));
                }
                else
                {
                    settings.addItem(prop).setValue(getProperty(prop));
                }
            }
            autoSave = true;
        }

        private void updateSettingsSection()
        {

            for (string prop : configs)
            {
                IniItem item = settings.getItem(prop);
                item.setValue(getProperty(prop));
            }
        }

        public float taskCompleteBonusFitness = 50;
        public float taskCompleteBonusEnergy = 1000;

        //usado para calcular o 
        public float occupationRatio = 0.75f;

        public float memoryUseLimitPerc = 95;

        public float writeInstructionMutationChance = 0.08f;


        public float getDivisionMutationChance = 0.2f;

        public float getRandomMutation = 0.0005f;// applied each tick

        public int getInstructionCount = 0;

        public float getErrorLimit = 2800;

        public float errorKillChance = 0.01f;

        public float errorCritical = 100;

        public float errorNormal = 20;

        public float fitnessNormal = 1;// rewarded to cells

        public float childReward = 10f;

        public float sizeReward = .1f; // recompensa dada ao organismo para cada
                                       // byte

        // multiplicado para cada diferença média entre o pai e o filho
        public int diffFromFatherReward = 1;

        private bool autoSave = true;

        public float maxOrganismos = 0;

        public int mutationType = CONSTS.MUTTYPE_OCCUPATION_RATIO;



        public void changeProperty(string name, float val)
        {
            if (name == DIV_MUT_CHANCE)
            {
                getDivisionMutationChance = val;
            }
            else if (name == WRITE_MUTATION_CHANCE)
            {
                writeInstructionMutationChance = val;
            }
            else if (name == RANDOM_MUTATION_CHANCE)
            {
                getRandomMutation = val;
            }
            else if (name == OCUPPATION_RATIO)
            {
                occupationRatio = val;
            }
            else if (name == ERROR_KILL_CHANCE)
            {
                errorKillChance = val;
            }
            else
            {
                Log.error("Desconhecido:" + name);
            }
            if (autoSave)
            {
                saveIni();
            }
        }

        private void saveIni()
        {
            updateSettingsSection();
            File file = new File(iniFile);
            //file.delete();
            IniFileWriter writer = new IniFileWriter(ini, file);
            Log.info("Saving settings: " + iniFile);
            try
            {
                writer.write();
            }
            catch (Exception e)
            {
                //e.printStackTrace();
            }

        }

        public float getProperty(string name)
        {
            if (name == WRITE_MUTATION_CHANCE)
            {
                return writeInstructionMutationChance;
            }
            if (name == DIV_MUT_CHANCE)
            {
                return getDivisionMutationChance;
            }
            if (name == RANDOM_MUTATION_CHANCE)
            {
                return getRandomMutation;
            }
            if (name == ERROR_KILL_CHANCE)
            {
                return errorKillChance;
            }
            if (name == OCUPPATION_RATIO)
            {
                return occupationRatio;
            }
            Log.error("Desconhecido:" + name);
            return 0;
        }


    }
}
