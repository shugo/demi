/* DemiException.java */

package JP.ac.osaka_u.shugo.demi;

import java.util.*;

public class DemiException extends Jump {

    private Throwable originalException;

    public DemiException(SimpleNode node,  Stack stack, Throwable e) {
	super(node, stack);
	originalException = e;
    }

    public String getExceptionMessgae() {
	if (originalException instanceof ParseException ||
	    originalException instanceof TokenMgrError) {
	    String msg = originalException.getMessage();
	    StringTokenizer tokenizer =
		new StringTokenizer(msg, "\r\n");
	    return tokenizer.nextToken();
	} else {
	    return originalException.toString();
	}
    }

    public Throwable getOriginalException() {
	return originalException;
    }
}
