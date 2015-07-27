package edu.bit.hinge.visitor;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import edu.bit.hinge.AST.AST;
import edu.bit.hinge.AST.ArithmaticNode;
import edu.bit.hinge.AST.AssignmentNode;
import edu.bit.hinge.AST.AttributeNode;
import edu.bit.hinge.AST.BreakNode;
import edu.bit.hinge.AST.CallNode;
import edu.bit.hinge.AST.ClassNode;
import edu.bit.hinge.AST.ComparisonNode;
import edu.bit.hinge.AST.ContinueNode;
import edu.bit.hinge.AST.DefParamNode;
import edu.bit.hinge.AST.DoWhileNode;
import edu.bit.hinge.AST.ExpressionListNode;
import edu.bit.hinge.AST.ForStatementNode;
import edu.bit.hinge.AST.FunctionNode;
import edu.bit.hinge.AST.IdentifierNode;
import edu.bit.hinge.AST.IfStatementNode;
import edu.bit.hinge.AST.IndexingNode;
import edu.bit.hinge.AST.LiteralNode;
import edu.bit.hinge.AST.LogicalNode;
import edu.bit.hinge.AST.ParamListNode;
import edu.bit.hinge.AST.ReturnNode;
import edu.bit.hinge.AST.StatementListNode;
import edu.bit.hinge.AST.UnaryNode;
import edu.bit.hinge.AST.WhileStatementNode;
import edu.bit.hinge.memery.Break;
import edu.bit.hinge.memery.ClassInstance;
import edu.bit.hinge.memery.Continue;
import edu.bit.hinge.memery.Function;
import edu.bit.hinge.memery.FunctionInstance;
import edu.bit.hinge.memery.Instance;
import edu.bit.hinge.memery.MemerySpace;
import edu.bit.hinge.memery.MethodFunctionInstance;
import edu.bit.hinge.memery.ReturnValue;
import edu.bit.hinge.memery.Space;

public class EvalVisitor extends Visitor {
	
	private MemerySpace currentSpace;
	
	public EvalVisitor(MemerySpace currentSpace) {
		this.currentSpace = currentSpace;
	}

	@Override
	public void visit(AST tree) {
		// TODO Auto-generated method stub
		if (tree instanceof StatementListNode) {
			statements((StatementListNode) tree);
		}
		else if (tree instanceof ClassNode) {
			defClass((ClassNode) tree);
		}
		else if (tree instanceof FunctionNode) {
			defFunction((FunctionNode) tree);
		}
		else if (tree instanceof AssignmentNode) {
			assign((AssignmentNode) tree);
		}
		else if (tree instanceof IfStatementNode) {
			ifStmt((IfStatementNode) tree);
		}
		else if (tree instanceof WhileStatementNode) {
			whileStmt((WhileStatementNode) tree);
		}
		else if (tree instanceof ForStatementNode) {
			forStmt((ForStatementNode) tree); 
		}
		else if (tree instanceof DoWhileNode) {
			doWhileStmt((DoWhileNode) tree);
		}
		else if (tree instanceof ReturnNode) {
			ret((ReturnNode) tree);
		}
		else if (tree instanceof BreakNode) {
			throw new Break();
		}
		else if (tree instanceof ContinueNode) {
			throw new Continue();
		}
		else if (tree instanceof CallNode) {
			eval((CallNode) tree);
		}
	}

	public void statements(StatementListNode node) {
		for (AST child : node.getList()) {
			visit(child);
		}
	}
	
	public void defClass(ClassNode node) {
		IdentifierNode identifier = (IdentifierNode) node.getIdentifier();
		IdentifierNode inheritance = (IdentifierNode) node.getInheritance();
		
		ClassInstance classInstance = null;
		if (inheritance != null) {
			classInstance = new ClassInstance(
					identifier.getValue(), currentSpace, (ClassInstance) eval(inheritance));
		} else {
			classInstance = new ClassInstance(identifier.getValue(), currentSpace, null);
		}
		currentSpace.put(identifier.getValue(), classInstance);
		
		MemerySpace previousSpace = currentSpace;
		currentSpace = classInstance;
		visit(node.getBody());
		
		currentSpace = previousSpace;
	}
	
	public void defFunction(FunctionNode node) {
		IdentifierNode identifier = (IdentifierNode) node.getIdentifier();
		ParamListNode parameters = (ParamListNode) node.getParameters();
		AST body = node.getBody();
		
		FunctionInstance function;
		if (currentSpace instanceof ClassInstance) {
			function = new MethodFunctionInstance(identifier.getValue(), currentSpace, body);
		} else {
			function = new FunctionInstance(identifier.getValue(), currentSpace, body);
		}
		currentSpace.put(function.getName(), function);
		
		if (parameters != null) {
			for (AST child : parameters.getList()) {
				DefParamNode param = (DefParamNode) child;
				identifier = (IdentifierNode) param.getIdentifier();
				if (param.getDefValue() != null) {
					function.putParameter(identifier.getValue(),
							Optional.of(expression(param.getDefValue())));
				} else {
					function.putParameter(identifier.getValue(), Optional.ofNullable(null));
				}
			}
		}
	}
	
	public void assign(AssignmentNode node) {
		Object val = expression(node.getExpression());
		for (AST target : node.getTarget()) {
			if (target instanceof ExpressionListNode) {
				List<AST> targetList = ((ExpressionListNode) target).getList();
				List<Object> valList = (List<Object>) val;
				
				if (targetList.size() == valList.size()) {
					for (int i = 0; i < valList.size(); i++) {
						assign(targetList.get(i), valList.get(i));
					}
				} else {
					throw new Error("Unmatchable pack");
				}
			} else {
				assign(target, val);
			}
		}
	}
	
	public void assign(AST target, Object val) {
		if (target instanceof IdentifierNode) {
			assign((IdentifierNode) target, val);
		}
		else if (target instanceof AttributeNode) {
			assign((AttributeNode) target, val);
		}
		else if (target instanceof IndexingNode) {
			assign((IndexingNode) target, val);
		}
		else {
			throw new Error("Can't assign to '" + target.getClass() + "'");
		}
	}
	
	public void assign(IdentifierNode target, Object val) {
		currentSpace.put(target.getValue(), val);
	}
	
	public void assign(AttributeNode target, Object val) {
		Space space = (Space) expression(target.getIdentifier());
		IdentifierNode attribute = (IdentifierNode) target.getAttribute();
		space.put(attribute.getValue(), val);
	}
	
	public void assign(IndexingNode target, Object val) {
		throw new Error("Unimplemented Method 'void assign(IndexingNode target, Object val)'");
	}
	
	public void ifStmt(IfStatementNode node) {
		Number condition = (Number) expression(node.getCondition());
		if (condition.doubleValue() != 0) {
			visit(node.getIfbody());
		}
		else if (node.getElsebody() != null) {
			visit(node.getElsebody());
		}
	}
	
	public void whileStmt(WhileStatementNode node) {
		AST condition = node.getCondition();
		while (((Number) expression(condition)).doubleValue() != 0) {
			try {
				visit(node.getBody());
			} catch (Break e) {
				break;
			} catch (Continue e) {
				continue;
			}
		}
	}
	
	public void forStmt(ForStatementNode node) {
		throw new Error("Unimplemented method");
	}
	
	public void doWhileStmt(DoWhileNode node) {
		AST condition = node.getCondition();
		do {
			try {
				visit(node.getBody());
			} catch (Break e) {
				break;
			} catch (Continue e) {
				continue;
			}
		} while (((Number) expression(condition)).doubleValue() != 0);
	}

	public Object expression(AST expr) {
		if (expr instanceof ExpressionListNode) {
			ExpressionListNode exprList = (ExpressionListNode) expr;
			List<Object> vals = new ArrayList<Object>(exprList.getList().size());
			for (AST sub : exprList.getList()) {
				vals.add(expression(sub));
			}
			return vals;
		}
		else if (expr instanceof ArithmaticNode) {
			return eval((ArithmaticNode) expr);
		}
		else if (expr instanceof ComparisonNode) {
			return eval((ComparisonNode) expr);
		}
		else if (expr instanceof LogicalNode) {
			return eval((LogicalNode) expr);
		}
		else if (expr instanceof UnaryNode) {
			return eval((UnaryNode) expr);
		}
		else if (expr instanceof LiteralNode<?>) {
			return eval((LiteralNode<?>) expr);
		}
		else if (expr instanceof AttributeNode) {
			return eval((AttributeNode) expr);
		}
		else if (expr instanceof IndexingNode) {
			throw new Error("Bad expression node");
		}
		else if (expr instanceof CallNode) {
			return eval((CallNode) expr);
		}
		else {
			throw new Error("Bad expression node");
		}
	}
	
	public Object eval(ArithmaticNode expr) {
		Object left = expression(expr.getLeft());
		Object right = expression(expr.getRight());
		if (left instanceof Number && right instanceof Number) {
			return arithmatic((Number) left, expr.getOperator(), (Number) right);
		} else {
			throw new Error("Bad operand '" + left.getClass()
					+ "' and '" + right.getClass() + "'");
		}
	}
	
	public Object arithmatic(Number left, String op, Number right) {
		if (left instanceof Double || right instanceof Double) {
			double x = left.doubleValue();
			double y = right.doubleValue();
			switch (op) {				
			case "+": return x + y;
			case "-": return x - y;
			case "*": return x * y;
			case "/": return x / y;
			case "%": return x % y;
			case "**": return Math.pow(x, y);
			default:
				throw new Error("Unsupported operator '" + op
						+ "' for 'Double'");
			}
		}
		else if (left instanceof Long || right instanceof Long) {
			long x = left.longValue();
			long y = right.longValue();
			switch (op) {
			case "|": return x | y;
			case "^": return x ^ y;
			case "&": return x & y;
			case "<<": return x << y;
			case ">>": return x >> y;
			case "+": return x + y;
			case "-": return x - y;
			case "*": return x * y;
			case "/": return x / y;
			case "%": return x % y;
			case "**": return (long) Math.pow(x, y);
			default:
				throw new Error("Unsupported operator '" + op
						+ "' for 'Long'");
			}
		}
		else if (left instanceof Integer || right instanceof Integer) {
			int x = left.intValue();
			int y = right.intValue();
			switch (op) {
			case "|": return x | y;
			case "^": return x ^ y;
			case "&": return x & y;
			case "<<": return x << y;
			case ">>": return x >> y;
			case "+": return x + y;
			case "-": return x - y;
			case "*": return x * y;
			case "/": return x / y;
			case "%": return x % y;
			case "**":
				double r = Math.pow(x, y);
				if (r > Integer.MAX_VALUE) {
					return (long) r;
				}
				return (int) r;
			default:
				throw new Error("Unsupported operator '" + op
						+ "' for 'Integer'");
			}
		}
		else {
			throw new Error("Bad operand '" + left.getClass()
				+ "' and '" + right.getClass() + "'");
		}
	}

	public Integer eval(ComparisonNode expr) {
		Object left = expression(expr.getLeft());
		Object right = expression(expr.getRight());
		switch (expr.getOperator()) {
		case "==": return left.equals(right) ? 1 : 0;
		case "!=": return left.equals(right) ? 0 : 1;
		case "is": return left.equals(right) ? 1 : 0;
		case "is not": return left.equals(right) ? 0 : 1;
		default: return compete(left, expr.getOperator(), right);
		}
	}
	
	public Integer compete(Object left, String op, Object right) {
		if (left instanceof Number && right instanceof Number) {
			double x = ((Number) left).doubleValue();
			double y = ((Number) right).doubleValue();
			switch (op) {
			case "<": return x < y ? 1 : 0;
			case ">": return x > y ? 1 : 0;
			case "<=": return x <= y ? 1 : 0;
			case ">=": return x >= y ? 1 : 0;
			case "in":
			case "not in":
			default: throw new Error("Unknown operator '" + op + "'");
			}
		}
		return 0;
	}

	public Integer eval(LogicalNode expr) {
		Number left = (Number) expression(expr.getLeft());
		
		switch (expr.getOperator()) {
		case "and":
			if (left.doubleValue() == 0) {
				return 0;
			} else {
				Number right = (Number) expression(expr.getRight());
				return right.doubleValue() == 0 ? 0 : 1;
			}
		case "or":
			if (left.doubleValue() != 0) {
				return 1;
			} else {
				Number right = (Number) expression(expr.getRight());
				return right.doubleValue() == 0 ? 0 : 1;
			}
		default: throw new Error("Unknown operator '" + expr.getOperator() + "'");
		}
	}
	
	public Object eval(UnaryNode expr) {
		Number val = (Number) expression(expr.getChild());
		switch (expr.getOperator()) {
		case "+": return val;
		case "-":
			if (val instanceof Double) {
				return -val.doubleValue();
			} else if (val instanceof Long) {
				return -val.longValue();
			} else if (val instanceof Integer) {
				return -val.intValue();
			} else {
				throw new Error("Bad operand '" + val.getClass() + "'");
			}
		case "~":
			if (val instanceof Long) {
				return ~val.longValue();
			} else if (val instanceof Integer) {
				return ~val.intValue();
			} else {
				throw new Error("Bad operand '" + val.getClass() + "'");
			}
		case "not": return val.doubleValue() == 0 ? 1 : 0;
		default: throw new Error("Unknown operator '" + expr.getOperator() + "'");
		}
	}

	public Object eval(LiteralNode<?> expr) {
		if (expr instanceof IdentifierNode) {
			String identifier = (String) expr.getValue();
			Object val = currentSpace.get(identifier);
			if (val != null) {
				return val;
			}
			else {
				throw new Error("Undefined identifier '" + expr.getValue() + "'");
			}
		} else {
			return expr.getValue();
		}
	}

	public Object eval(AttributeNode expr) {
		IdentifierNode attribute = (IdentifierNode) expr.getAttribute();
		
		Space parentSpace = (Space) expression(expr.getIdentifier());
		Object val = parentSpace.getAttribute(attribute.getValue());
		if (val != null) {
			return val;
		} else {
			throw new Error("Can't find attribute '" + attribute.getValue() + "'");
		}
	}
	
	public Object eval(CallNode expr) {
		Function function = (Function) expression(expr.getIdentifier());
		AST arguments = expr.getArguments();
		
		// evaluate arguments that passed in
		List<Object> vals = null;
		if (arguments != null) {
			if (arguments instanceof ExpressionListNode) {
				vals = (List<Object>) expression(arguments);
			} else {
				vals = new LinkedList<Object>();
				vals.add(expression(arguments));
			}
		}
		
		if (function instanceof MethodFunctionInstance) {
			AttributeNode attribute = (AttributeNode) expr.getIdentifier();
			Instance instance = (Instance) expression(attribute.getIdentifier());
			return ((MethodFunctionInstance) function).run(instance, vals);
		} else {
			return function.run(vals);
		}
	}
	
	public void ret(ReturnNode node) {
		if (node.getExpressionList() != null) {
			throw new ReturnValue(expression(node.getExpressionList()));
		} else {
			throw new ReturnValue(null);
		}
	}

}
