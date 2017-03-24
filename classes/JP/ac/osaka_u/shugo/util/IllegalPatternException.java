package JP.ac.osaka_u.shugo.util;

import com.oroinc.text.regex.*;

public class IllegalPatternException
    extends MalformedPatternException {

    public IllegalPatternException() {
	super();
    }

    public IllegalPatternException(String msg) {
	super(msg);
    }
}

