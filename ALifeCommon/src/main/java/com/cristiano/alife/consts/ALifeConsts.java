package com.cristiano.alife.consts;

import com.cristiano.alife.world.IOrganismo;
import com.cristiano.utils.CRMathUtils;

public class ALifeConsts {
	public static final float MAX_MEMORY_CHILD = 1.5f;
	public static final float MIN_MEMORY_CHILD = 0.5f;
	public static final String GENEBANK_PATH="genebank/";

	public static final int IP_REG=3;
	
	public static final int GRAPH_WIDTH = 900;
	public static final int GRAPH_SIZE=1;
	
	public static final int TEMPLATE_LIMIT = 1024;
	
	public static final int REGISTRADORES = 10;
	public static final int STACKS = 2;
	public static final int MAX_STACK = 32;//qtd maxima por pilha
	public static final int MAX_BUFFER=16;
	
	//limite superior e inferior do erro 
	public static final int ERROR_UPPER_LIMIT = 10000;

	
	public static final int AUTO_SAVE_PROGRAM_WITH_CHILD_COUNT = 10;
	public static final int MAX_NEIGHBOURS = 8;
	public static final int MAX_SAVED_PROGRAMS = 100;
	
	public static final float FITNESS_TO_ENERGY_RATIO = 10;
	public static final int MUTTYPE_OCCUPATION_RATIO = 1;
	public static final int MUTTYPE_POSITIONAL_CENTER = 2;
	
	
	public static  int IMAGE_WIDTH = 1248;
	public static  int IMAGE_HEIGTH = 1248;
	
	//preenchido automaticamente
	public static int NOP0=0;
	public static int NOP1=0;
	
	public static int calcIndex(int i,int max){
		while (i<0 && max>0){
			i=max+i;
		}
		return i%max;
	}
	
	public static String numberFormat(int v){
		return v+"["+CRMathUtils.toHexString(v%REGISTRADORES)+"]";
	}
	
	public static String getLetter(int i) {
		return Character.toString((char) (65 + i%REGISTRADORES));
	}

	public static int getLetterNumber(char p) {
		return (p-65);
	}
	
	public static int getBit(int num,int position)
	{
	   return (num >> position) & 1;
	}
	
	public static int setBit(int num, int pos, int bit) {
		if (bit<0){
			bit =0;
		}
		if (bit>1){
			bit=1;
		}
		num |= (bit << pos);
		return num;
	}
	
	public static int validateMemorySize(IOrganismo pai, int memSize) {
		if (pai==null){
			return memSize;
		}
		int maxMem = (int) (ALifeConsts.MAX_MEMORY_CHILD * pai.getMemorySize());
		int minMem = (int) (ALifeConsts.MIN_MEMORY_CHILD * pai.getMemorySize());
		if (memSize > maxMem) {
			return 0;
		}
		if (memSize <= minMem) {
			return 0;
		}
		return memSize;
	}

}
