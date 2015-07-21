package edu.bit.hinge.symbol;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseScope implements Scope {
	
	private Map<String, Symbol> members = new HashMap<String, Symbol>();
	
	@Override
	public Map<String, Symbol> getMembers() {
		// TODO Auto-generated method stub
		return members;
	}

}
