/* Generated By:JJTree: Do not edit this line. ASTAndAssign.java */

package JP.ac.osaka_u.shugo.demi;

public class ASTAndAssign extends ASTBitwiseAndNode {
    public ASTAndAssign(int id) {
	super(id);
    }

    public ASTAndAssign(Parser p, int id) {
	super(p, id);
    }

    public static Node jjtCreate(int id) {
	return new ASTAndAssign(id);
    }

    public static Node jjtCreate(Parser p, int id) {
	return new ASTAndAssign(p, id);
    }

    protected Object evaluate0(Demi demi) throws Throwable {
	SimpleNode lhs = (SimpleNode) jjtGetChild(0);
	Object val = super.evaluate(demi);
	return assign(demi, lhs, val);
    }
}
