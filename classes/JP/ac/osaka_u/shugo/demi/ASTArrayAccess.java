/* Generated By:JJTree: Do not edit this line. ASTArrayAccess.java */

package JP.ac.osaka_u.shugo.demi;

import java.lang.reflect.*;

public class ASTArrayAccess extends SimpleNode {
    public ASTArrayAccess(int id) {
	super(id);
    }

    public ASTArrayAccess(Parser p, int id) {
	super(p, id);
    }

    protected Object evaluate0(Demi demi) throws Throwable {
	Object obj = ((SimpleNode) jjtGetChild(0)).evaluate(demi);
	Object index = ((SimpleNode) jjtGetChild(1)).evaluate(demi);
	if (obj.getClass().isArray()) {
	    if (!(index instanceof Number))
		throw new IllegalTypeException(index, "Number");
	    int i = ((Number) index).intValue();
	    return Array.get(obj, i);
	} else {
	    Class[] paramTypes;
	    Method method;
	    try {
		paramTypes = new Class[]{Integer.TYPE};
		method =
		    obj.getClass().getMethod("charAt", paramTypes);
		return method.invoke(obj, new Object[]{index});
	    } catch (NoSuchMethodException e) {
	    }
	    try {
		paramTypes = new Class[]{Integer.TYPE};
		method = obj.getClass().getMethod("elementAt", paramTypes);
		return method.invoke(obj, new Object[]{index});
	    } catch (NoSuchMethodException e) {
	    }
	    try {
		paramTypes = new Class[]{Class.forName("java.lang.Object")};
		method = obj.getClass().getMethod("get", paramTypes);
		return method.invoke(obj, new Object[]{index});
	    } catch (NoSuchMethodException e) {
	    }
	}
	throw new NoSuchMethodException("no such method [] for " +
					(obj == null ? "null" :
					 obj.getClass().toString()));
    }
}
