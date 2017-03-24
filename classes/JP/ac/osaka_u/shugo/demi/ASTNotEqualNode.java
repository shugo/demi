/* Generated By:JJTree: Do not edit this line. ASTNotEqualNode.java */

package JP.ac.osaka_u.shugo.demi;

public class ASTNotEqualNode extends SimpleNode {
    public ASTNotEqualNode(int id) {
	super(id);
    }

    public ASTNotEqualNode(Parser p, int id) {
	super(p, id);
    }

    protected Object evaluate0(Demi demi) throws Throwable {
	Object left = ((SimpleNode) jjtGetChild(0)).evaluate(demi);
	Object right = ((SimpleNode) jjtGetChild(1)).evaluate(demi);
	if (left == null) {
	    return new Boolean(right != null);
	} else if (left instanceof Number && right instanceof Number) {
	    return new Boolean(Numeric.compare((Number) left,
					       (Number) right) != 0);
	} else {
	    return new Boolean(!left.equals(right));
	}
    }
}