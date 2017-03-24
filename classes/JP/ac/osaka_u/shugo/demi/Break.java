/* Break.java */

package JP.ac.osaka_u.shugo.demi;

import java.util.Stack;

public class Break extends Jump {

    Scope scope;

    public Break(SimpleNode node, Stack stack, Scope scope) {
	super(node, stack, "break called outside of iteration");
	this.scope = scope;
    }
}
