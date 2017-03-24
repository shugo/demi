/* EvalFunction.java */

package JP.ac.osaka_u.shugo.demi;

import java.io.*;
import java.util.*;

public class EvalFunction extends BuiltInFunction {

    public EvalFunction(Demi demi) {
	super(demi, Symbol.get("eval"));
    }

    public Object call(Object[] args) throws Throwable {
	if (args.length != 1) {
	    String msg =
		"wrong number of arguments (" + args.length + " for 1)";
	    throw new IllegalArgumentException(msg);
	}
	if (!(args[0] instanceof String))
	    throw new IllegalTypeException(args[0], "String");
	String str = (String) args[0];
	return demi.evalString(str);
    }
}
