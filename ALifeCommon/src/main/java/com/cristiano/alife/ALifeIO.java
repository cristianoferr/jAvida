package com.cristiano.alife;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import com.cristiano.alife.consts.ALifeConsts;
import com.cristiano.alife.instructions.Instruction;
import com.cristiano.alife.world.IOrganismo;
import com.cristiano.alife.world.IWorld;
import com.cristiano.utils.Log;

public class ALifeIO {

	private static String currentDir;

	public static void cleanUpGenebank(IWorld m) {
		File[] listFiles = ALifeIO.listFiles(ALifeConsts.GENEBANK_PATH, m);
		if (listFiles.length > ALifeConsts.MAX_SAVED_PROGRAMS) {
			int count = 0;
			do {
				long min = 0;

				for (int i = 0; i < listFiles.length; i++) {
					if (listFiles[i] != null) {
						min = listFiles[i].lastModified();
					}
				}
				int index = 0;
				for (int i = 0; i < listFiles.length; i++) {
					if (listFiles[i] != null) {
						count++;
						if (listFiles[i].lastModified() < min) {
							min = listFiles[i].lastModified();
							index = i;

						}
					}
				}
				if (listFiles[index] != null) {
					Log.debug("Mais Antigo:" + listFiles[index].getName());
					listFiles[index].delete();
					listFiles[index] = null;
				} else {
					return;
				}
			} while (count > ALifeConsts.MAX_SAVED_PROGRAMS);
		}
	}

	public static File[] listFiles(String path, Object owner) {
		getCurrentDir(owner);
		File folder = new File(currentDir + path);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
			} else if (listOfFiles[i].isDirectory()) {
			}
		}
		return listOfFiles;
	}

	public static IOrganismo loadFromFile(String fileName, IWorld mundo) throws IOException {
		getCurrentDir(mundo);

		BufferedReader br;
		File file = new File(currentDir + fileName);
		FileReader fr = new FileReader(file);
		IOrganismo o = null;
		br = new BufferedReader(fr);
		try {
			String line = br.readLine();
			int memSize = getSize(line);
			o = mundo.criaOrganismo(memSize);
			
			line = loadInstructions(br, line, mundo, o);
		} finally {
			br.close();
		}
		return o;
	}

	public static void loadRegs(String line, IOrganismo o) {
		if (line==null){
			return;
		}
		if (line.startsWith("#REGS: ")) {
			String[] pars = line.split(" ");
			for (int i = 0; i < pars.length; i++) {
				String par = pars[i].trim();
				if (par.contains("[")) {
					par = par.substring(0, par.indexOf("[")).trim();
				}
				if (par.contains("X:")) {
					String letter = par.substring(0, 1);
					String val = par.substring(par.indexOf(":") + 1);
					o.setReg(ALifeConsts.getLetterNumber(letter.toCharArray()[0]), Integer.parseInt(val));

				}
				if (par.contains("SP:")) {
					String val = par.substring(par.indexOf(":") + 1);
					o.setStartPoint(Integer.parseInt(val));
				}
				if (par.contains("IP:")) {
					String val = par.substring(par.indexOf(":") + 1);
					o.setIp(Integer.parseInt(val) + 1);
				}
			}
		}

	}

	private static void getCurrentDir(Object mundo) {
		URL resource = mundo.getClass().getResource(".");
		currentDir = resource.getPath().substring(0, resource.getPath().lastIndexOf("/bin") + 1) + "cells/";
	}

	private static String loadInstructions(BufferedReader br, String line, IWorld mundo, IOrganismo o) throws IOException {
		int pos = 0;
		while (line != null) {
			line = br.readLine();
			loadRegs(line, o);
			if (line != null) {

				pos = loadInstruction(line.trim(), mundo, o, pos);

			}
		}
		return line;
	}

	private static int loadInstruction(String line, IWorld mundo, IOrganismo o, int pos) {
		if (line.startsWith("#")) {
			return pos;
		}
		if (line.contains("=")) {
			line = line.substring(line.indexOf("=") + 1).trim();
		}
		if (line.contains("//")) {
			line = line.substring(0, line.indexOf("//")).trim();
		}
		line += " ";
		int indexOf = line.indexOf(" ");
		String param = line.substring(indexOf).trim();
		String instrCode = line.substring(0, indexOf).trim();

		Instruction instruction = mundo.getInstruction(instrCode);
		int step = 1;
		if (instruction == null) {
			o.setMemory(o.sp() + pos, Integer.parseInt(instrCode));
		} else {
			o.setMemory(o.sp() + pos, instruction.getId());
			step = instruction.getStep();
		}
		pos++;
		if (step > 1) {
			pos = loadParams(instruction, param, o, pos);
		}
		return pos;
	}

	private static int loadParams(Instruction instruction, String params, IOrganismo o, int pos) {
		String[] pars = params.split(",");
		for (int i = 0; i < pars.length; i++) {
			String par = pars[i].trim();
			if (par.length() > 0) {
				char p = par.toCharArray()[0];
				if (p >= 'A' && p <= 'Z') {
					o.setMemory(o.sp() + pos + i, ALifeConsts.getLetterNumber(p));
				} else {
					o.setMemory(o.sp() + pos + i, Integer.parseInt(par));
				}
			} else {
				pos--;
			}
		}
		return pos + pars.length;
	}

	private static int getSize(String line) {
		String sizeStr = "Size:";
		String pos = line.substring(line.indexOf(sizeStr) + sizeStr.length()).trim();
		return Integer.parseInt(pos);
	}

	public static void saveToFile(IOrganismo organismo) {
		getCurrentDir(organismo);

		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(currentDir + ALifeConsts.GENEBANK_PATH + organismo.hash() + ".txt"));
			writer.write(organismo.toString());

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
