/* Generated By:JJTree: Do not edit this line. ASTException.java */

package JP.ac.osaka_u.shugo.demi;

public class ASTException extends SimpleNode {
    public ASTException(int id) {
	super(id);
    }

    public ASTException(Parser p, int id) {
	super(p, id);
    }

    protected Object evaluate0(Demi demi) {
	return demi.exceptionTable.get(Thread.currentThread());
    }
}
