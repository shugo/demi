/* Generated By:JJTree: Do not edit this line. ASTGlobalVariable.java */

package JP.ac.osaka_u.shugo.demi;

public class ASTGlobalVariable extends ASTVariable {
    public ASTGlobalVariable(int id) {
	super(id);
    }

    public ASTGlobalVariable(Parser p, int id) {
	super(p, id);
    }

    protected Object evaluate0(Demi demi) {
	return demi.getGlobalVariable(symbol);
    }
}