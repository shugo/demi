/* Generated By:JJTree: Do not edit this line. ASTModAssign.java */

package JP.ac.osaka_u.shugo.demi;

public class ASTModAssign extends ASTModNode {
    public ASTModAssign(int id) {
	super(id);
    }

    public ASTModAssign(Parser p, int id) {
	super(p, id);
    }

    public static Node jjtCreate(int id) {
	return new ASTModAssign(id);
    }

    public static Node jjtCreate(Parser p, int id) {
	return new ASTModAssign(p, id);
    }

    protected Object evaluate0(Demi demi) throws Throwable {
	SimpleNode lhs = (SimpleNode) jjtGetChild(0);
	Object val = super.evaluate(demi);
	return assign(demi, lhs, val);
    }
}