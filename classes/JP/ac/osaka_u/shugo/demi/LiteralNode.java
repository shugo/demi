/* LiteralNode.java */

package JP.ac.osaka_u.shugo.demi;

public class LiteralNode extends SimpleNode {

    Object value;
    
    public LiteralNode(int id) {
	super(id);
    }

    public LiteralNode(Parser p, int id) {
	super(p, id);
    }

    protected Object evaluate0(Demi demi) {
	return value;
    }
}
