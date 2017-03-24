/* Symbol.java */

package JP.ac.osaka_u.shugo.demi;

import java.util.*;

public class Symbol {

    private static Hashtable table = new Hashtable();
    private String name;

    private Symbol(String name) {
	this.name = name.intern();
    }

    public static Symbol get(String name) {
	if (table.containsKey(name)) {
	    return (Symbol) table.get(name);
	} else {
	    Symbol symbol = new Symbol(name);
	    table.put(name, symbol);
	    return symbol;
	}
    }

    public String toString() {
	return name;
    }

    public String getName() {
	return name;
    }
}

