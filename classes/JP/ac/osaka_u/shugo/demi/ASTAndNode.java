/* Generated By:JJTree: Do not edit this line. ASTAndNode.java */

package JP.ac.osaka_u.shugo.demi;

public class ASTAndNode extends SimpleNode {
    public ASTAndNode(int id) {
	super(id);
    }

    public ASTAndNode(Parser p, int id) {
	super(p, id);
    }

    protected Object evaluate0(Demi demi) throws Throwable {
	Object left = ((SimpleNode) jjtGetChild(0)).evaluate(demi);
	if (!Demi.test(left))
	    return Boolean.FALSE;
	Object right = ((SimpleNode) jjtGetChild(1)).evaluate(demi);
	return new Boolean(Demi.test(left) && Demi.test(right));
    }
}
