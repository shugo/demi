/* Generated By:JJTree: Do not edit this line. ASTArrayExpression.java */

package JP.ac.osaka_u.shugo.demi;

public class ASTArrayExpression extends SimpleNode {
    public ASTArrayExpression(int id) {
	super(id);
    }

    public ASTArrayExpression(Parser p, int id) {
	super(p, id);
    }

    protected Object evaluate0(Demi demi) throws Throwable {
	Object[] array = new Object[jjtGetNumChildren()];
	for (int i = 0; i < array.length; i++) {
	    array[i] = ((SimpleNode) jjtGetChild(i)).evaluate(demi);
	}
	return array;
    }
}
