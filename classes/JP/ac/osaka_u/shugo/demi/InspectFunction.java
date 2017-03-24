/* InspectFunction.java */

package JP.ac.osaka_u.shugo.demi;

import java.io.*;
import java.util.*;

public class InspectFunction extends BuiltInFunction {

    public InspectFunction(Demi demi) {
	super(demi, Symbol.get("inspect"));
    }

    public Object call(Object[] args) throws Throwable {
	if (args.length != 1) {
	    String msg =
		"wrong number of arguments (" + args.length + " for 1)";
	    throw new IllegalArgumentException(msg);
	}
	return Demi.inspect(args[0]);
    }
}
