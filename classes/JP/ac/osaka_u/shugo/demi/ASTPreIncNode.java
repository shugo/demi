/* Generated By:JJTree: Do not edit this line. ASTPreIncNode.java */

package JP.ac.osaka_u.shugo.demi;

public class ASTPreIncNode extends SimpleNode {
    public ASTPreIncNode(int id) {
	super(id);
    }

    public ASTPreIncNode(Parser p, int id) {
	super(p, id);
    }

    public static Node jjtCreate(int id) {
	return new ASTPreIncNode(id);
    }

    public static Node jjtCreate(Parser p, int id) {
	return new ASTPreIncNode(p, id);
    }

    protected Object evaluate0(Demi demi) throws Throwable {
	SimpleNode lhs = (SimpleNode) jjtGetChild(0);
	Object obj = lhs.evaluate(demi);
	if (!(obj instanceof Number))
	    throw new IllegalTypeException(obj, "Number");
	Object val = Numeric.add((Number) obj, new Integer(1));
	return assign(demi, lhs, val);
    }
}
