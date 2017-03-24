package JP.ac.osaka_u.shugo.demi;

import java.util.*;

public class Module {

    private Symbol name;
    private Vector includedModules;
    private SymbolTable symbolTable = new SymbolTable();
    private SymbolTable specialVariables = new SymbolTable();

    public Module(Symbol name) {
	this.name = name;
	includedModules = new Vector();
    }

    public String toString() {
	return "<module " + name.getName() + " at " +
	    Integer.toHexString(hashCode()) + ">";
    }

    public Symbol getName() {
	return name;
    }

    public void addSpecialVariable(Symbol name, SpecialVariable sv) {
	specialVariables.put(name, sv);
    }

    public void addSpecialVariable(Symbol name,
				   SpecialVariableGetter getter,
				   SpecialVariableSetter setter) {
	SpecialVariable sv = new SpecialVariable(getter, setter);
	specialVariables.put(name, sv);
    }

    public SpecialVariable removeSpecialVariable(Symbol name) {
	return (SpecialVariable) specialVariables.remove(name);
    }

    public boolean hasVariable(Symbol symbol, Vector visitedModules) {
	if (symbolTable.containsKey(symbol) ||
	    specialVariables.containsKey(symbol))
	    return true;
	visitedModules.addElement(this);
	for (int i =  includedModules.size() - 1; i >= 0; i--) {
	    Module module = (Module) includedModules.elementAt(i);
	    if (visitedModules.contains(module))
		continue;
	    if (module.hasVariable(symbol, visitedModules))
		return true;
	}
	return false;
    }

    public boolean hasVariable(Symbol symbol,
			       boolean searchIncludedModules) {
	if (searchIncludedModules) {
	    return hasVariable(symbol, new Vector());
	} else {
	    return symbolTable.containsKey(symbol) ||
		specialVariables.containsKey(symbol);
	}
    }

    public boolean hasVariable(Symbol symbol) {
	return hasVariable(symbol, new Vector());
    }

    public Object getVariable(Symbol symbol, Vector visitedModules)
	throws Throwable {
	if (specialVariables.containsKey(symbol)) {
	    SpecialVariable sv =
		(SpecialVariable) specialVariables.get(symbol);
	    return sv.get(symbol);
	}
	if (symbolTable.containsKey(symbol))
	    return symbolTable.get(symbol);
	visitedModules.addElement(this);
	for (int i =  includedModules.size() - 1; i >= 0; i--) {
	    Module module = (Module) includedModules.elementAt(i);
	    if (visitedModules.contains(module))
		continue;
	    try {
		return module.getVariable(symbol, visitedModules);
	    } catch (NameError e) {
	    }
	}
	throw new NameError(name.getName() + "::" + symbol.getName());
    }

    public Object getVariable(Symbol symbol,
			      boolean searchIncludedModules)
	throws Throwable {
	if (searchIncludedModules) {
	    return getVariable(symbol, new Vector());
	} else {
	    if (specialVariables.containsKey(symbol)) {
		SpecialVariable sv =
		    (SpecialVariable) specialVariables.get(symbol);
		return sv.get(symbol);
	    }
	    if (symbolTable.containsKey(symbol))
		return symbolTable.get(symbol);
	    throw new NameError(name.getName() + "::" + symbol.getName());
	}
    }

    public Object getVariable(Symbol symbol) throws Throwable {
	return getVariable(symbol, new Vector());
    }

    public void setVariable(Symbol symbol, Object val) throws Throwable {
	if (specialVariables.containsKey(symbol)) {
	    SpecialVariable sv =
		(SpecialVariable) specialVariables.get(symbol);
	    sv.set(symbol, val);
	}
	symbolTable.put(symbol, val);
    }

    public boolean hasIncluded(Module module, Vector visitedModules) {
	if (module == this || includedModules.contains(module))
	    return true;
	visitedModules.addElement(this);
	for (int i =  includedModules.size() - 1; i >= 0; i--) {
	    Module mod = (Module) includedModules.elementAt(i);
	    if (visitedModules.contains(mod))
		continue;
	    if (mod.hasIncluded(module, visitedModules))
		return true;
	}
	return false;
    }

    public boolean hasIncluded(Module module) {
	return hasIncluded(module, new Vector());
    }

    public boolean include(Module module) {
	if (hasIncluded(module)) {
	    return false;
	} else {
	    includedModules.addElement(module);
	    return true;
	}
    }

    public Enumeration variables() {
	return symbolTable.elements();
    }

    public Enumeration variableNames() {
	return symbolTable.keys();
    }
}
