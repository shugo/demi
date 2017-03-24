/* RegExp.java

 Please get OROMatcher(TM) at http://www.oroinc.com/. */

package JP.ac.osaka_u.shugo.util;

import java.util.*;
import com.oroinc.text.regex.*;
import JP.ac.osaka_u.shugo.demi.*;

public class RegExp {

    public static int CASE_INSENSITIVE = Perl5Compiler.CASE_INSENSITIVE_MASK;
    public static int DEFAULT = Perl5Compiler.DEFAULT_MASK;
    public static int EXTENDED = Perl5Compiler.EXTENDED_MASK;
    public static int MULTILINE = Perl5Compiler.MULTILINE_MASK;
    public static int SINGLELINE = Perl5Compiler.SINGLELINE_MASK;

    static Perl5Compiler compiler = new Perl5Compiler();

    Perl5Matcher matcher;
    Pattern pattern;

    public RegExp(String pat) throws IllegalPatternException {
	try {
	    pattern = compiler.compile(pat);
	    matcher = new Perl5Matcher();
	} catch (MalformedPatternException e) {
	    throw new IllegalPatternException(e.getMessage());
	}
    }

    public RegExp(String pat, int options) throws IllegalPatternException {
	try {
	    pattern = compiler.compile(pat, options);
	    matcher = new Perl5Matcher();
	} catch (MalformedPatternException e) {
	    throw new IllegalPatternException(e.getMessage());
	}
    }

    public String toString() {
	return "/" + pattern.getPattern() + "/";
    }

    public Pattern getPattern() {
	return pattern;
    }

    public String getPatternString() {
	return pattern.getPattern();
    }

    public int getOptions() {
	return pattern.getOptions();
    }

    public Match[] match(String str) {
	synchronized (matcher) {
	    if (matcher.contains(str, pattern))
	        return getMatch();
	    else
		return null;
	}
    }

    public Match[] match(PatternMatcherInput input) {
	synchronized (matcher) {
	    if (matcher.contains(input, pattern))
	        return getMatch();
	    else
		return null;
	}
    }

    public Match[] getMatch() {
	MatchResult mr = matcher.getMatch();
	int length = mr.groups();
	Match[] result = new Match[length];
	for (int i = 0; i < length; i++) {
	    result[i] =
		new Match(mr.group(i), mr.beginOffset(i), mr.endOffset(i));
	}
	return result;
    }

    public String[] scan(String str) {
	MatchResult match;
	String[] result;
	synchronized (matcher) {
	    if (matcher.contains(str, pattern))
		match = matcher.getMatch();
	    else
		return null;
	}
	int length = match.groups();
	result = new String[length];
	for (int i = 0; i < length; i++) {
	    result[i] = match.group(i);
	}
	return result;
    }

    public Vector split(String str) {
	return Util.split(matcher, pattern, str);
    }

    public Vector split(String str, int limit) {
	return Util.split(matcher, pattern, str, limit);
    }

    public String sub(String str, String sub) {
	return Util.substitute(matcher, pattern, sub, str);
    }

    public String gsub(String str, String sub) {
	return Util.substitute(matcher, pattern, sub, str,
			       Util.SUBSTITUTE_ALL);
    }

    public String sub(String str, Function func) throws Throwable {
	Match[] m = match(str);
	if (m == null) return str;
	Object obj = func.call(new Object[]{m[0].string()});
	return str.substring(0, m[0].begin()) + obj.toString() +
	    str.substring(m[0].end(), str.length());
    }

    public String gsub(String str, Function func) throws Throwable {
	StringBuffer buff = new StringBuffer();
	Match[] m;
	while ((m = match(str)) != null) {
	    Object obj = func.call(new Object[]{m[0].string()});
	    buff.append(str.substring(0, m[0].begin()));
	    buff.append(obj.toString());
	    str = str.substring(m[0].end(), str.length());
	}
	buff.append(str);
	return new String(buff);
    }
}
