/* ModuleFunction.java */

package JP.ac.osaka_u.shugo.demi;

public class ModuleFunction extends AbstractFunction {

    Module module;
    Symbol name;
    Symbol[] argumentList;
    int minArguments;
    SymbolTable defaultValues;
    Symbol rest;
    SimpleNode body;

    public ModuleFunction(Demi demi, Module module, Symbol name,
			  Symbol[] args, int min,
			  SymbolTable defaults, Symbol rest,
			  SimpleNode body) {
	super(demi);
	this.module = module;
	this.name = name;
	argumentList = args;
	minArguments = min;
	defaultValues = defaults;
	this.rest = rest;
	this.body = body;
    }

    public Module getModule() {
	return module;
    }

    public Symbol getName() {
	return name;
    }

    public String toString() {
	return "<function " + name.getName() + " at " +
	    Integer.toHexString(hashCode()) + ">";
    }

    public Object call(Object[] args) throws Throwable {
	if (args.length < minArguments)
	    throw new IllegalArgumentException("not enough arguments");
	if (rest == null && args.length > argumentList.length)
	    throw new IllegalArgumentException("too many arguments");
	Scope scope = new Scope(module);
	int length = args.length > argumentList.length ?
	    argumentList.length : args.length;
	for (int i = 0; i < length; i++) {
	    scope.localVariables.put(argumentList[i], args[i]);
	}
	for (int i = args.length; i < argumentList.length; i++) {
	    scope.localVariables.put(argumentList[i],
				       defaultValues.get(argumentList[i]));
	}
	if (rest != null) {
	    int restCount = args.length - argumentList.length;
	    if (restCount < 0) restCount = 0;
	    Object[] restOfArgs = new Object[restCount];
	    for (int i = 0; i < restCount; i++) {
		restOfArgs[i] = args[argumentList.length + i];
	    }
	    scope.localVariables.put(rest, restOfArgs);
	}
	demi.pushScope(scope);
	try {
	    return body.evaluate(demi);
	} catch (Return ret) {
	    if (ret.scope == scope) {
		return ret.value;
	    } else {
		throw ret;
	    }
	} finally {
	    demi.popScope();
	}
    }
}
