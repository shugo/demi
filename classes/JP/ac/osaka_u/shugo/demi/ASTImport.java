/* Generated By:JJTree: Do not edit this line. ASTImport.java */

package JP.ac.osaka_u.shugo.demi;

public class ASTImport extends SimpleNode {

    String packageName;

    public ASTImport(int id) {
	super(id);
    }

    public ASTImport(Parser p, int id) {
	super(p, id);
    }

    protected Object evaluate0(Demi demi) {
	if (!demi.importedPackages.contains(packageName))
	    demi.importedPackages.addElement(packageName);
	return null;
    }
}