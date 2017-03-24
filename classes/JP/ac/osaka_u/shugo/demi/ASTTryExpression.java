/* Generated By:JJTree: Do not edit this line. ASTTryExpression.java */

package JP.ac.osaka_u.shugo.demi;

public class ASTTryExpression extends SimpleNode {
    public ASTTryExpression(int id) {
	super(id);
    }

    public ASTTryExpression(Parser p, int id) {
	super(p, id);
    }

    protected Object evaluate0(Demi demi) throws Throwable {
	SimpleNode finallyBlock = null;
	if (jjtGetNumChildren() % 2 == 0) {
	    finallyBlock =
		(SimpleNode) jjtGetChild(jjtGetNumChildren() - 1);
	}
	try {
	    return ((SimpleNode) jjtGetChild(0)).evaluate(demi);
	} catch (DemiException de) {
	    Throwable e = de.getOriginalException();
	    int num = finallyBlock == null ?
		jjtGetNumChildren() : jjtGetNumChildren() - 1;
	    for (int i = 1; i < num; i += 2) {
		Class exc =
		    (Class) ((SimpleNode) jjtGetChild(i)).evaluate(demi);
		if (exc.isInstance(e)) {
		    demi.exceptionTable.put(Thread.currentThread(), e);
		    return ((SimpleNode) jjtGetChild(i + 1)).evaluate(demi);
		}
	    }
	    throw de;
	} catch (Jump jump) {
	    throw jump;
	} finally {
	    demi.exceptionTable.remove(Thread.currentThread());
	    if (finallyBlock != null)
		finallyBlock.evaluate(demi);
	}
    }
}