/* Continue.java */

package JP.ac.osaka_u.shugo.demi;

import java.util.Stack;

public class Continue extends Jump {

    Scope scope;

    public Continue(SimpleNode node, Stack stack, Scope scope) {
	super(node, stack, "continue called outside of iteration");
	this.scope = scope;
    }
}
