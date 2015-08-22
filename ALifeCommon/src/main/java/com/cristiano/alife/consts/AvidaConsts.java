package com.cristiano.alife.consts;


public class AvidaConsts {
	//public static final String DEFAULT_ANCESTOR = "genebank/101_389D8.txt";
	public static final String DEFAULT_ANCESTOR = "ancestor.txt";
	
	public static final int WORD_SIZE=(int) Math.pow(2, 12);
	
	public static final int ORGS_AXIS_X=120;
	public static final int ORGS_AXIS_Y=100;

	public static final int GRAPH_WIDTH=6;
	public static final int GRAPH_OFFSET=0;

	public static final int MAX_ORGANISMOS = ORGS_AXIS_X*ORGS_AXIS_Y;

	
	public static final int STARTING_POINT = ALifeConsts.REGISTRADORES+1+1+1;//IP+SP+MemorySize+REGS
	public static final int POSITION_IP = 0;
	public static final int POSITION_SP = POSITION_IP+1;
	public static final int POSITION_MEMSIZE = POSITION_SP+1;
	public static final int POSITION_REGS = POSITION_MEMSIZE+1;

	public static final int ALLOC_SEARCH_LIMIT=1;
	public static final boolean ALLOC_KILL_WORST =false;
	
	public static final boolean ALLOW_NEIGHBOUR_ACCESS =true;
	public static final int MAX_NEIGHBOURS =8;
	
	public static final int MEMORY_POOL_SIZE =(ALLOW_NEIGHBOUR_ACCESS?MAX_NEIGHBOURS:0);

	/*
	public static final int ALLOC_SEARCH_LIMIT= AvidaConsts.ORGS_AXIS_X;
	
	//Caso true eu desaloco o pior caso não encontre no search, senão falha
	public static final boolean ALLOC_KILL_WORST =true;*/

	

}
