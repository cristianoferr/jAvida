package com.cristiano.alife.world;

import com.cristiano.alife.consts.ALifeConsts;

public class Pilha {
	private int[]stack;
	int pos=0;
	private IOrganismo organismo;
	public Pilha(IOrganismo o){
		stack=new int[ALifeConsts.MAX_STACK];
		this.organismo=o;
	}
	public void push(int i) {
		if (pos>=ALifeConsts.MAX_STACK){
			pos=ALifeConsts.MAX_STACK-1;
		}
		stack[pos]=i;
		pos++;
	}
	public int pop() {
		pos--;
		if (pos<0){
			pos=0;
		}
		int v=stack[pos];
		return v;
	}
	public String debugInfo() {
		String s="Stack: ";
		for (int i=0;i<pos;i++){
			s+=ALifeConsts.numberFormat(stack[i])+" | ";
		}
		return s;
	}

}
