/* LoadFunction.java */

package JP.ac.osaka_u.shugo.demi;

import java.io.*;
import java.util.*;

public class LoadFunction extends BuiltInFunction {

    public LoadFunction(Demi demi) {
	super(demi, Symbol.get("load"));
    }

    public Object call(Object[] args) throws Throwable {
	if (args.length != 1) {
	    String msg =
		"wrong number of arguments (" + args.length + " for 1)";
	    throw new IllegalArgumentException(msg);
	}
	if (!(args[0] instanceof String))
	    throw new IllegalTypeException(args[0], "String");
	String fileName = (String) args[0];
	demi.loadFile(fileName);
	return null;
    }
}
