package edu.bit.hinge.memery;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.Map.Entry;

import edu.bit.hinge.AST.AST;
import edu.bit.hinge.visitor.EvalVisitor;


public class FunctionInstance extends MemerySpace implements Function {
	
	private LinkedHashMap<String, Optional<Object>> parameters =
			new LinkedHashMap<String, Optional<Object>>();
	private AST body;
	
	public FunctionInstance(String name, MemerySpace enclosingSpace, AST body) {
		super(name, enclosingSpace);
		this.body = body;
		
		put(name, this);
	}
	
	public LinkedHashMap<String, Optional<Object>> getParameters() {
		return parameters;
	}
	
	public void putParameter(String name, Optional<Object> value) {
		parameters.put(name, value);
	}
	
	public AST getBody() {
		return body;
	}
	
	@Override
	public Object run(List<Object> args) {
		MemerySpace space = new MemerySpace(getName(), this);
		
		// add parameters to function space
		int i = 0;
		if (args == null || args.size() <= parameters.size()) {
			for (Entry<String, Optional<Object>> e : parameters.entrySet()) {
				String param = e.getKey();
				Optional<Object> defValue = e.getValue();
				
				if (args != null && i < args.size()) {
					space.put(param, args.get(i));
				}
				else if (defValue.isPresent()) {
					space.put(param, defValue.get());
				}
				else {
					throw new Error("Less parameters of function");
				}
				
				++i;
			}
		} else {
			throw new Error("Too many parameters of function");
		}
		
		Object r = null;
		try {
			body.visit(new EvalVisitor(space));
		} catch (ReturnValue e) {
			// TODO: handle exception
			r = e.getValue();
		}
		
		return r;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "<function " + getName() + ">";
	}

}
