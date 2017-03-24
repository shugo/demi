/* Return.java */

package JP.ac.osaka_u.shugo.demi;

import java.util.Stack;

public class Return extends Jump {

    Scope scope;
    Object value;

    public Return(SimpleNode node, Stack stack, Scope scope, Object value) {
	super(node, stack, "return called outside of function scope");
	this.scope = scope;
	this.value = value;
    }
}
