/* Closure.java */

package JP.ac.osaka_u.shugo.demi;

import java.util.*;

public class Closure extends AbstractFunction {

    Symbol[] argumentList;
    SimpleNode body;
    Module module;
    SymbolTable localVariables;
    Scope scope;

    public Closure(Demi demi, Symbol[] args,
		   SimpleNode body, Scope scope) {
	super(demi);
	argumentList = args;
	this.body = body;
	this.scope = scope;
    }

    public Symbol getName() {
	return Symbol.get("closure$" +
			  Integer.toHexString(hashCode()));
    }

    public String toString() {
	return "<closure at " +
	    Integer.toHexString(hashCode()) + ">";
    }

    public Object call(Object[] args) throws Throwable {
	if (args.length != argumentList.length) {
	    String msg = "wrong number of arguments (" +
		args.length + " for " + argumentList.length + ")";
	    throw new IllegalArgumentException(msg);
	}
	for (int i = 0; i < args.length; i++) {
	    scope.setVariable(argumentList[i], args[i]);
	}
	demi.pushScope(scope);
	try {
	    return body.evaluate(demi);
	} finally {
	    demi.popScope();
	}
    }

    public void ifTrue(Function func) throws Throwable {
	Object[] args = new Object[0];
	if (Demi.test(call(args))) {
	    func.call(args);
	}
    }

    public void ifFalse(Function func) throws Throwable {
	Object[] args = new Object[0];
	if (!Demi.test(call(args))) {
	    func.call(args);
	}
    }

    public void whileTrue(Function func) throws Throwable {
	Object[] args = new Object[0];
	while (Demi.test(call(args))) {
	    func.call(args);
	}
    }

    public void whileFalse(Function func) throws Throwable {
	Object[] args = new Object[0];
	while (!Demi.test(call(args))) {
	    func.call(args);
	}
    }

    public void repeat(int n) throws Throwable {
	Object[] args = new Object[0];
	for (int i = 0; i < n; i++) {
	    call(args);
	}
    }
}
