package com.cristiano.alife.consts;


public class TierraConsts {
	
	public static final String DEFAULT_ANCESTOR = "ancestor.txt";
	public static final String GENEBANK_PATH="genebank/";
	public static final int REGISTRADORES = 4;
	
	public static final int STACKS = 2;
	public static final int MAX_STACK = 32;//qtd maxima por pilha
	
	public static final int MEMORY_SIZE = 220000;
	
	public static final int MAX_BUFFER=16;
	
	public static final int NORMAL_ERROR = 20;
	public static final int CRITICAL_ERROR = 150;
	public static final int NORMAL_FITNESS = 1;//rewarded to cells
	public static final float MEMORY_USE = 80f;
	
	public static final int GRAPH_WIDTH = 900;
	public static final int GRAPH_SIZE=2;
	public static final int GRAPH_OFFSET=10;
	
	public static final float MUTATION_CHANCE = 0.03f;
	public static final float MAX_MEMORY_CHILD = 1.5f;
	public static final float MIN_MEMORY_CHILD = 0.5f;
	public static final float DIVISION_MUTATION = 0.02f; 
	public static final float DIVISION_MIN_AMOUNT = 650;//quantidade minima de organismos para ficar ativo
	public static final float RANDOM_MUTATION = 0.03f;//applied each CheckTick
	public static final int ERROR_LIMIT = 500;
	

	public static final float SIZE_REWARD = 0.1f; //recompensa dada ao organismo para cada byte
	public static final float CHILD_REWARD = 10f;
	

	public static final float ERROR_KILL_CHANCE = 0.02f;
	public static final int MAX_ORGANISMOS = 500; 

}
