package JP.ac.osaka_u.shugo.demi;

import java.util.*;

public class SymbolTable extends Hashtable {

    private static Object Null = new Object();

    public Object get(Object symbol) {
	Object ret = super.get(symbol);
	if (ret == Null)
	    return null;
	else
	    return ret;
    }

    public Object put(Object symbol, Object value) {
	if (value == null)
	    return super.put(symbol, Null);
	else
	    return super.put(symbol, value);
    }
}
