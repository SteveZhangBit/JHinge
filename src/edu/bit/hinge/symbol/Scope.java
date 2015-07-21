package edu.bit.hinge.symbol;

import java.util.Map;

public interface Scope {
	
	Map<String, Symbol> getMembers();

	String getScopeName();
	
	Scope getEnclosingScope();
	
	default void define(Symbol symbol) {
		// TODO Auto-generated method stub
		System.out.println("define " + symbol.getName() + " in " + getScopeName());
		getMembers().put(symbol.getName(), symbol);
	}

	default Symbol resolve(String name) {
		// TODO Auto-generated method stub
		Symbol s = getMembers().get(name);
		if (s != null) {
			return s;
		}
		
		if (getEnclosingScope() != null) {
			return getEnclosingScope().resolve(name);
		}
		
		return null;
	}
}
