/* Generated By:JJTree: Do not edit this line. ASTPostDecNode.java */

package JP.ac.osaka_u.shugo.demi;

public class ASTPostDecNode extends SimpleNode {
    public ASTPostDecNode(int id) {
	super(id);
    }

    public ASTPostDecNode(Parser p, int id) {
	super(p, id);
    }

    public static Node jjtCreate(int id) {
	return new ASTPostDecNode(id);
    }

    public static Node jjtCreate(Parser p, int id) {
	return new ASTPostDecNode(p, id);
    }

    protected Object evaluate0(Demi demi) throws Throwable {
	SimpleNode lhs = (SimpleNode) jjtGetChild(0);
	Object obj = lhs.evaluate(demi);
	if (!(obj instanceof Number))
	    throw new IllegalTypeException(obj, "Number");
	Object val = Numeric.subtract((Number) obj, new Integer(1));
	assign(demi, lhs, val);
	return obj;
    }
}
