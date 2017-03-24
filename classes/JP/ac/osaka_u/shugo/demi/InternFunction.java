/* InternFunction.java */

package JP.ac.osaka_u.shugo.demi;

public class InternFunction extends BuiltInFunction {

    public InternFunction(Demi demi) {
	super(demi, Symbol.get("intern"));
    }

    public Object call(Object[] args)
	throws IllegalArgumentException, IllegalTypeException {
	if (args.length != 1) {
	    String msg =
		"wrong number of arguments (" + args.length + " for 1)";
	    throw new IllegalArgumentException(msg);
	}
	if (!(args[0] instanceof String))
	    throw new IllegalTypeException(args[0], "String");
	return Demi.intern((String) args[0]);
    }
}
