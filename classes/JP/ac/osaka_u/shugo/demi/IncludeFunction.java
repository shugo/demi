/* IncludeFunction.java */

package JP.ac.osaka_u.shugo.demi;

public class IncludeFunction extends BuiltInFunction {

    public IncludeFunction(Demi demi) {
	super(demi, Symbol.get("include"));
    }

    public Object call(Object[] args)
	throws IllegalArgumentException, IllegalTypeException {
	if (args.length != 1) {
	    String msg =
		"wrong number of arguments (" + args.length + " for 1)";
	    throw new IllegalArgumentException(msg);
	}
	if (!(args[0] instanceof Module))
	    throw new IllegalTypeException(args[0], "Module");
	return new Boolean(demi.getScope().module
			   .include((Module) args[0]));
    }
}
