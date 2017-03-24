/* PrintlnFunction.java */

package JP.ac.osaka_u.shugo.demi;

public class PrintlnFunction extends BuiltInFunction {

    public PrintlnFunction(Demi demi) {
	super(demi, Symbol.get("println"));
    }

    public Object call(Object[] args) {
	for (int i = 0; i < args.length; i++) {
	    demi.out.print(args[i]);
	}
	demi.out.println();
	return null;
    }
}
