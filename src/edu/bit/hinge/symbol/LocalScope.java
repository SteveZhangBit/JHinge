package edu.bit.hinge.symbol;

public class LocalScope extends BaseScope {
	
	private Scope enclosingScope;
	
	public LocalScope(Scope enclosingScope) {
		this.enclosingScope = enclosingScope;
	}

	@Override
	public String getScopeName() {
		// TODO Auto-generated method stub
		return "_LOCAL_SCOPE_";
	}

	@Override
	public Scope getEnclosingScope() {
		// TODO Auto-generated method stub
		return enclosingScope;
	}

}
