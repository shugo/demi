/* Scope.java */

package JP.ac.osaka_u.shugo.demi;

public class Scope {

    SymbolTable localVariables;
    Module module;

    public Scope(Module module, SymbolTable localVariables) {
	this.module = module;
	this.localVariables = localVariables;
    }

    public Scope(Module module) {
	this(module, new SymbolTable());
    }

    public Object getVariable(Symbol symbol) throws NameError {
	if (localVariables != null &&
	    localVariables.containsKey(symbol))
	    return localVariables.get(symbol);
	if (module.hasVariable(symbol))
	    return module.getVariable(symbol);
	throw new NameError(symbol);
    }

    public void setVariable(Symbol symbol, Object val) {
	if (localVariables == null) {
	    module.setVariable(symbol, val);
	} else {
	    localVariables.put(symbol, val);
	}
    }
}
