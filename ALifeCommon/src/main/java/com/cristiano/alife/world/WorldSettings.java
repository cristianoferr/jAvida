package com.cristiano.alife.world;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.dtools.ini.BasicIniFile;
import org.dtools.ini.FormatException;
import org.dtools.ini.IniFile;
import org.dtools.ini.IniFileReader;
import org.dtools.ini.IniFileWriter;
import org.dtools.ini.IniItem;
import org.dtools.ini.IniSection;

import com.cristiano.alife.consts.ALifeConsts;
import com.cristiano.utils.Log;

public class WorldSettings {

	public static final String DIV_MUT_CHANCE = "divMutChance";

	public static final String RANDOM_MUTATION_CHANCE = "mutationChance";
	public static final String ERROR_KILL_CHANCE = "errorKillChance";
	public static final String WRITE_MUTATION_CHANCE = "writeInstructionMutationChance";
	public static final String OCUPPATION_RATIO = "occupationRatio";
	private String iniFile;
	List<String> configs = new ArrayList<String>();

	public WorldSettings(String file,int maxOrgs) {
		this.iniFile = file;
		this.maxOrganismos=maxOrgs;

		configs.add(DIV_MUT_CHANCE);
		configs.add(RANDOM_MUTATION_CHANCE);
		configs.add(ERROR_KILL_CHANCE);
		configs.add(WRITE_MUTATION_CHANCE);
		configs.add(OCUPPATION_RATIO);
		loadIni();
	}

	private void loadIni() {
		File file = new File(iniFile);

		ini = new BasicIniFile();

		IniFileReader reader = new IniFileReader(ini, file);
		try {
			reader.read();
		} catch (FormatException e) {
			// e.printStackTrace();
		} catch (IOException e) {
			// e.printStackTrace();
		}
		initSettingsSection();

	}

	private void initSettingsSection() {
		settings = ini.getSection("SETTINGS");
		if (settings == null) {
			settings = ini.addSection("SETTINGS");
		}

		autoSave = false;
		for (String prop : configs) {
			IniItem item = settings.getItem(prop);
			if (item != null) {
				changeProperty(prop, Float.parseFloat(item.getValue()));
			} else {
				settings.addItem(prop).setValue(getProperty(prop));
			}
		}
		autoSave = true;
	}

	private void updateSettingsSection() {

		for (String prop : configs) {
			IniItem item = settings.getItem(prop);
			item.setValue(getProperty(prop));
			}
	}
	
	public float taskCompleteBonusFitness=50;
	public float taskCompleteBonusEnergy=1000;

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

	private IniFile ini;

	private IniSection settings;

	private boolean autoSave = true;

	public float maxOrganismos=0;

	public int mutationType=ALifeConsts.MUTTYPE_OCCUPATION_RATIO;

	

	public void changeProperty(String name, float val) {
		if (name.equals(DIV_MUT_CHANCE)) {
			getDivisionMutationChance = val;
		} else if (name.equals(WRITE_MUTATION_CHANCE)) {
			writeInstructionMutationChance = val;
		} else if (name.equals(RANDOM_MUTATION_CHANCE)) {
			getRandomMutation = val;
		} else if (name.equals(OCUPPATION_RATIO)) {
			occupationRatio = val;
		}else if (name.equals(ERROR_KILL_CHANCE)) {
			errorKillChance = val;
		} else {
			Log.error("Desconhecido:" + name);
		}
		if (autoSave) {
			saveIni();
		}
	}

	private void saveIni() {
		updateSettingsSection();
		File file = new File(iniFile);
		//file.delete();
		IniFileWriter writer = new IniFileWriter(ini, file);
		Log.info("Saving settings: "+iniFile);
		try {
			writer.write();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public float getProperty(String name) {
		if (name.equals(WRITE_MUTATION_CHANCE)) {
			return writeInstructionMutationChance;
		}
		if (name.equals(DIV_MUT_CHANCE)) {
			return getDivisionMutationChance;
		}
		if (name.equals(RANDOM_MUTATION_CHANCE)) {
			return getRandomMutation;
		}
		if (name.equals(ERROR_KILL_CHANCE)) {
			return errorKillChance;
		}
		if (name.equals(OCUPPATION_RATIO)) {
			return occupationRatio;
		}
		Log.error("Desconhecido:" + name);
		return 0;
	}

}
