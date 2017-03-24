/* Range.java */

package JP.ac.osaka_u.shugo.demi;

import java.util.*;

public class Range {

    static Integer zero = new Integer(0);
    static Integer plusOne = new Integer(1);
    static Integer minusOne = new Integer(-1);

    Number first;
    Number last;
    Number step;

    public Range(Number first, Number last) {
	this.first = first;
	this.last = last;
	if (Numeric.compare(first, last) <= 0)
	    step = plusOne;
	else
	    step = minusOne;
    }

    public String toString() {
	return first.toString() + ".." + last.toString();
    }

    public Number first() {
	return first;
    }

    public Number last() {
	return last;
    }

    public boolean contains(Object obj) {
	if (!(obj instanceof Number))
	    return false;
	if (Numeric.compare(first, last) <= 0) {
	    return Numeric.compare(first, (Number) obj) <= 0 &&
		Numeric.compare((Number) obj, last) <= 0;
	} else {
	    return Numeric.compare(first, (Number) obj) >= 0 &&
		Numeric.compare((Number) obj, last) >= 0;
	}
    }

    public Enumeration elements() {
	return new Enumerator();
    }

    class Enumerator implements Enumeration {

	Number current;

	public Enumerator() {
	    current = first;
	}

	public boolean hasMoreElements() {
	    return (Numeric.compare(step, zero) > 0 &&
		    Numeric.compare(current, last) <= 0) ||
		(Numeric.compare(step, zero) < 0 &&
		 Numeric.compare(current, last) >= 0);
	}

	public Object nextElement() {
	    if ((Numeric.compare(step, zero) > 0 &&
		 Numeric.compare(current, last) <= 0) ||
		(Numeric.compare(step, zero) < 0 &&
		 Numeric.compare(current, last) >= 0)) {
		Object result = current;
		current = Numeric.add(current, step);
		return result;
	    }
	    throw new NoSuchElementException("Range");
	}
    }
}
