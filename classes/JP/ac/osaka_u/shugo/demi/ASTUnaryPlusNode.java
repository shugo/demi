/* Generated By:JJTree: Do not edit this line. ASTUnaryPlusNode.java */

package JP.ac.osaka_u.shugo.demi;

public class ASTUnaryPlusNode extends SimpleNode {
    public ASTUnaryPlusNode(int id) {
	super(id);
    }

    public ASTUnaryPlusNode(Parser p, int id) {
	super(p, id);
    }

    protected Object evaluate0(Demi demi) throws Throwable {
	Object obj = ((SimpleNode) jjtGetChild(0)).evaluate(demi);
	return obj;
    }
}
