/* Generated By:JJTree: Do not edit this line. ASTOrNode.java */

package JP.ac.osaka_u.shugo.demi;

public class ASTOrNode extends SimpleNode {
    public ASTOrNode(int id) {
	super(id);
    }

    public ASTOrNode(Parser p, int id) {
	super(p, id);
    }

    protected Object evaluate0(Demi demi) throws Throwable {
	Object left = ((SimpleNode) jjtGetChild(0)).evaluate(demi);
	if (Demi.test(left))
	    return Boolean.TRUE;
	Object right = ((SimpleNode) jjtGetChild(1)).evaluate(demi);
	return new Boolean(Demi.test(left) || Demi.test(right));
    }
}