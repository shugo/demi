/* Match.java */

package JP.ac.osaka_u.shugo.util;

import java.util.*;
import com.oroinc.text.regex.*;

public class Match {

    String string;
    int begin;
    int end;

    public Match(String string, int begin, int end) {
	this.string = string;
	this.begin = begin;
	this.end = end;
    }

    public String toString() {
	return string + "(" + begin + ":" + end + ")";
    }

    public String string() {
	return string;
    }

    public int begin() {
	return begin;
    }

    public int end() {
	return end;
    }
}
