/* Generated By:JJTree: Do not edit this line. ASTRangeNode.java */

package JP.ac.osaka_u.shugo.demi;

public class ASTRangeNode extends SimpleNode {
    public ASTRangeNode(int id) {
	super(id);
    }

    public ASTRangeNode(Parser p, int id) {
	super(p, id);
    }

    protected Object evaluate0(Demi demi) throws Throwable {
	Object left = ((SimpleNode) jjtGetChild(0)).evaluate(demi);
	Object right = ((SimpleNode) jjtGetChild(1)).evaluate(demi);
	if (!(left instanceof Number))
	    throw new Exception(left.toString() + " is not number.");
	if (!(right instanceof Number))
	    throw new Exception(right.toString() + " is not number.");
	return new Range((Number) left, (Number) right);
    }
}
