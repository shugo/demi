/* PFunction.java */

package JP.ac.osaka_u.shugo.demi;

public class PFunction extends BuiltInFunction {

    public PFunction(Demi demi) {
	super(demi, Symbol.get("p"));
    }

    public Object call(Object[] args) {
	for (int i = 0; i < args.length; i++) {
	    demi.out.println(Demi.inspect(args[i]));
	}
	return null;
    }
}
