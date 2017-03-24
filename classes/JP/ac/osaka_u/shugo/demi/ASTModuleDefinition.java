/* Generated By:JJTree: Do not edit this line. ASTModuleDefinition.java */

package JP.ac.osaka_u.shugo.demi;

public class ASTModuleDefinition extends SimpleNode {

    Symbol name;

    public ASTModuleDefinition(int id) {
	super(id);
    }

    public ASTModuleDefinition(Parser p, int id) {
	super(p, id);
    }

    public static Node jjtCreate(int id) {
	return new ASTModuleDefinition(id);
    }

    public static Node jjtCreate(Parser p, int id) {
	return new ASTModuleDefinition(p, id);
    }

    protected Object evaluate0(Demi demi) throws Throwable {
	Scope scope = demi.getScope();
	Module module;
	if (scope.module.hasVariable(name, false) &&
	    scope.module.getVariable(name) instanceof Module) {
	    module = (Module) scope.module.getVariable(name);
	} else {
	    module = new Module(name);
	    module.include(scope.module);
	    scope.module.setVariable(name, module);
	}
	demi.pushScope(new Scope(module, null));
	try {   
	    ((SimpleNode) jjtGetChild(0)).evaluate(demi);
	} finally {
	    demi.popScope();
	}
	return module;
    }
}