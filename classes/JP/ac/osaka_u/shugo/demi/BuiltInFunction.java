/* BuiltInFunction.java */

package JP.ac.osaka_u.shugo.demi;

public abstract class BuiltInFunction extends AbstractFunction {

    Symbol name;

    public BuiltInFunction(Demi demi, Symbol name) {
	super(demi);
	this.name = name;
    }

    public Symbol getName() {
	return name;
    }

    public String toString() {
	return "<built-in function " + name.getName() + ">";
    }
}
