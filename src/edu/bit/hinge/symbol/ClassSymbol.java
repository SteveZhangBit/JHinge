package edu.bit.hinge.symbol;

import java.util.HashMap;
import java.util.Map;

public class ClassSymbol extends Symbol implements Scope {
	
	private Scope enclosingScope;
	private ClassSymbol superClass;
	private Map<String, Symbol> members = new HashMap<String, Symbol>();

	public ClassSymbol(String name, ClassSymbol superClass, Scope enclosingScope) {
		super(name);
		// TODO Auto-generated constructor stub
		this.superClass = superClass;
		this.enclosingScope = enclosingScope;
		
		// add 'self' symbol
		members.put("self", this);
	}
	
	public Symbol resolveMember(String name) {
		Symbol s = members.get(name);
		if (s != null) {
			return s;
		}
		
		if (getSuperClass() != null) {
			return getSuperClass().resolveMember(name);
		}
		
		return null;
	}

	@Override
	public Map<String, Symbol> getMembers() {
		// TODO Auto-generated method stub
		return members;
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
	
	public ClassSymbol getSuperClass() {
		return superClass;
	}

}
