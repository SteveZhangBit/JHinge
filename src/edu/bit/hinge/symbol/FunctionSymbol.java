package edu.bit.hinge.symbol;

import java.util.HashMap;
import java.util.Map;

public class FunctionSymbol extends Symbol implements Scope {

	private Scope enclosingScope;
	private Map<String, Symbol> members = new HashMap<String, Symbol>();
	
	public FunctionSymbol(String name, Scope enclosingScope) {
		super(name);
		this.enclosingScope = enclosingScope;
	}

	@Override
	public String getScopeName() {
		// TODO Auto-generated method stub
		return getName();
	}

	@Override
	public Scope getEnclosingScope() {
		// TODO Auto-generated method stub
		return enclosingScope;
	}

	@Override
	public Map<String, Symbol> getMembers() {
		// TODO Auto-generated method stub
		return members;
	}

}
