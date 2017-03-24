/* PrintFunction.java */

package JP.ac.osaka_u.shugo.demi;

public class PrintFunction extends BuiltInFunction {

    public PrintFunction(Demi demi) {
	super(demi, Symbol.get("print"));
    }

    public Object call(Object[] args) {
	for (int i = 0; i < args.length; i++) {
	    demi.out.print(args[i]);
	}
	return null;
    }
}
