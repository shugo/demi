/* ExitFunction.java */

package JP.ac.osaka_u.shugo.demi;

public class ExitFunction extends BuiltInFunction {

    public ExitFunction(Demi demi) {
	super(demi, Symbol.get("exit"));
    }

    public Object call(Object[] args) {
	if (args.length > 1) {
	    String msg =
		"wrong number of arguments (" + args.length + " for 1)";
	    throw new IllegalArgumentException(msg);
	} else if (args.length == 1) {
	    System.exit(((Number) args[0]).intValue());
	} else {
	    System.exit(0);
	}
	return null;
    }
}
