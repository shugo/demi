/* Generated By:JJTree: Do not edit this line. ASTReturn.java */

package JP.ac.osaka_u.shugo.demi;

import java.util.Stack;

public class ASTReturn extends SimpleNode {
    public ASTReturn(int id) {
	super(id);
    }

    public ASTReturn(Parser p, int id) {
	super(p, id);
    }

    protected Object evaluate0(Demi demi) throws Throwable {
	Object obj = null;
	if (jjtGetNumChildren() != 0)
	    obj = ((SimpleNode) jjtGetChild(0)).evaluate(demi);
	Stack stack = demi.getCallInfoStack();
	if (stack != null)
	    stack = (Stack) stack.clone();
	throw new Return(this, stack, demi.getScope(), obj);
    }
}
