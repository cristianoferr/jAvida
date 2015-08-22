package com.cristiano.alife.world;

import java.awt.Color;

import com.cristiano.alife.instructions.Instruction;

public abstract class BaseInstruction implements Instruction {
	protected String name="";
	private int id;
	protected Color color=null;
	protected IWorld mundo;
	public BaseInstruction(IWorld mundo) {
		this.mundo=mundo;
	}

	public void setName(String n){
		this.name=n;
	}
	
	public void setId(int n){
		this.id=n;
		color=new Color(n*500);
	}
	
	public int getId(){
		return id;
	}
	
	public String getName(){
		return name;
	}
	
	@Override
	public String toString(){
		return name;
	}
	
	@Override
	public Color getColor(){
		return color;
	}
	
	@Override
	public int getStep() {
		return 1;
	}
	
	@Override
	public String getDescription(IOrganismo o,int ip){
		return name;
	}
	
	protected int getByteOrganismo(IOrganismo o,int pos) {
		int inst=o.getMemory(o.ip()+pos);
		return inst;
	}
	
	protected String comment(String description,String comment){
		int espacos=20;
		String saida=description;
		for (int i=description.length();i<espacos;i++){
			saida+=" ";
		}
		saida+="// "+comment;
		return saida;
		
	}
	
	@Override
	public boolean requiresTemplate() {
		return false;
	}
	

}
