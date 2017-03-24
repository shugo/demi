/* Numeric.java */

package JP.ac.osaka_u.shugo.demi;

import java.util.*;
import java.math.*;

public class Numeric {
    public static int compare(Number a, Number b) {
	if (a instanceof Float && b instanceof Float) {
	    if (a.floatValue() == b.floatValue())
		return 0;
	    else if (a.floatValue() > b.floatValue())
		return 1;
	    else
		return -1;
	} else if ((a instanceof Float ||
		    a instanceof Double ||
		    a instanceof BigDecimal) ||
		   (b instanceof Float ||
		    b instanceof Double ||
		    b instanceof BigDecimal)) {
	    return toBigDecimal(a).compareTo(toBigDecimal(b));
	} else if (a instanceof BigInteger || b instanceof BigInteger) {
	    return toBigInteger(a).compareTo(toBigInteger(b));
	} else if (a instanceof Long || b instanceof Long) {
	    if (a.longValue() == b.longValue())
		return 0;
	    else if (a.longValue() > b.longValue())
		return 1;
	    else
		return -1;
	} else {
	    if (a.intValue() == b.intValue())
		return 0;
	    else if (a.intValue() > b.intValue())
		return 1;
	    else
		return -1;
	}
    }

    public static Number negate(Number n) {
	if (n instanceof Float) {
	    return new Float(-n.floatValue());
	} else if (n instanceof Double) {
	    return new Double(-n.doubleValue());
	} else if (n instanceof BigDecimal) {
	    return (((BigDecimal) n).negate());
	} else if (n instanceof BigInteger) {
	    return (((BigInteger) n).negate());
	} else if (n instanceof Long) {
	    return new Long(-n.longValue());
	} else {
	    return new Integer(-n.intValue());
	}
    }

    public static Number not(Number n) {
	if (n instanceof Float ||
	    n instanceof Double ||
	    n instanceof BigDecimal) {
	    throw new IllegalArgumentException("illegal argument " + n +
					       " for ~");
	} else if (n instanceof BigInteger) {
	    return (((BigInteger) n).not());
	} else if (n instanceof Long) {
	    return new Long(~n.longValue());
	} else {
	    return new Integer(~n.intValue());
	}
    }

    public static Number add(Number a, Number b) {
	if (a instanceof Float && b instanceof Float) {
	    double r = a.doubleValue() + b.doubleValue();
	    if (r >= Float.MIN_VALUE && r <= Float.MAX_VALUE)
		return new Float((float) r);
	    else
		return new Double(r);
	} else if ((a instanceof Float ||
		    a instanceof Double ||
		    a instanceof BigDecimal) ||
		   (b instanceof Float ||
		    b instanceof Double ||
		    b instanceof BigDecimal)) {
	    return downsize(toBigDecimal(a).add(toBigDecimal(b)));
	} else if ((a instanceof Integer ||
		    a instanceof Short ||
		    a instanceof Byte) &&
		   (b instanceof Integer ||
		    b instanceof Short ||
		    b instanceof Byte)) {
	    long r = a.longValue() + b.longValue();
	    if (r >= Integer.MIN_VALUE && r <= Integer.MAX_VALUE)
		return new Integer((int) r);
	    else
		return new Long(r);
	} else {
	    return downsize(toBigInteger(a).add(toBigInteger(b)));
	}
    }

    public static Number subtract(Number a, Number b) {
	if (a instanceof Float && b instanceof Float) {
	    double r = a.doubleValue() - b.doubleValue();
	    if (r >= Float.MIN_VALUE && r <= Float.MAX_VALUE)
		return new Float((float) r);
	    else
		return new Double(r);
	} else if ((a instanceof Float ||
		    a instanceof Double ||
		    a instanceof BigDecimal) ||
		   (b instanceof Float ||
		    b instanceof Double ||
		    b instanceof BigDecimal)) {
	    return downsize(toBigDecimal(a).subtract(toBigDecimal(b)));
	} else if ((a instanceof Integer ||
		    a instanceof Short ||
		    a instanceof Byte) &&
		   (b instanceof Integer ||
		    b instanceof Short ||
		    b instanceof Byte)) {
	    long r = a.longValue() - b.longValue();
	    if (r >= Integer.MIN_VALUE && r <= Integer.MAX_VALUE)
		return new Integer((int) r);
	    else
		return new Long(r);
	} else {
	    return downsize(toBigInteger(a).subtract(toBigInteger(b)));
	}
    }

    public static Number multiply(Number a, Number b) {
	if (a instanceof Float && b instanceof Float) {
	    double r = a.doubleValue() * b.doubleValue();
	    if (r >= Float.MIN_VALUE && r <= Float.MAX_VALUE)
		return new Float((float) r);
	    else
		return new Double(r);
	} else if ((a instanceof Float ||
		    a instanceof Double ||
		    a instanceof BigDecimal) ||
		   (b instanceof Float ||
		    b instanceof Double ||
		    b instanceof BigDecimal)) {
	    return downsize(toBigDecimal(a).multiply(toBigDecimal(b)));
	} else if ((a instanceof Integer ||
		    a instanceof Short ||
		    a instanceof Byte) &&
		   (b instanceof Integer ||
		    b instanceof Short ||
		    b instanceof Byte)) {
	    long r = a.longValue() * b.longValue();
	    if (r >= Integer.MIN_VALUE && r <= Integer.MAX_VALUE)
		return new Integer((int) r);
	    else
		return new Long(r);
	} else {
	    return downsize(toBigInteger(a).multiply(toBigInteger(b)));
	}
    }

    public static Number divide(Number a, Number b) {
	if (a instanceof Float && b instanceof Float) {
	    double r = a.doubleValue() / b.doubleValue();
	    if (r >= Float.MIN_VALUE && r <= Float.MAX_VALUE)
		return new Float((float) r);
	    else
		return new Double(r);
	} else if ((a instanceof Float ||
		    a instanceof Double ||
		    a instanceof BigDecimal) ||
		   (b instanceof Float ||
		    b instanceof Double ||
		    b instanceof BigDecimal)) {
	    return downsize(toBigDecimal(a).divide(toBigDecimal(b),
					 BigDecimal.ROUND_HALF_UP));
	} else if (a instanceof BigInteger || b instanceof BigInteger) {
	    return downsize(toBigInteger(a).divide(toBigInteger(b)));
	} else if (a instanceof Long || b instanceof Long) {
	    long r = a.longValue() / b.longValue();
	    return new Long(r);
	} else {
	    int r = a.intValue() / b.intValue();
	    return new Integer(r);
	}
    }

    public static Number mod(Number a, Number b) {
	if ((a instanceof Float ||
	     a instanceof Double) ||
	    (b instanceof Float ||
	     b instanceof Double)) {
	    double r = a.doubleValue() % b.doubleValue();
	    if (r >= Float.MIN_VALUE && r <= Float.MAX_VALUE)
		return new Float((float) r);
	    else
		return new Double(r);
	} else if (a instanceof BigDecimal ||
		   b instanceof BigDecimal) {
	    throw new IllegalArgumentException("illegal arguments " +
					       a + ", " + b +
					       " for %");
	} else if (a instanceof BigInteger || b instanceof BigInteger) {
	    return downsize(toBigInteger(a).mod(toBigInteger(b)));
	} else if (a instanceof Long || b instanceof Long) {
	    long r = a.longValue() % b.longValue();
	    return new Long(r);
	} else {
	    int r = a.intValue() % b.intValue();
	    return new Integer(r);
	}
    }

    public static Number and(Number a, Number b) {
	if ((a instanceof Float ||
	     a instanceof Double ||
	     a instanceof BigDecimal) ||
	    (b instanceof Float ||
	     b instanceof Double ||
	     b instanceof BigDecimal)) {
	    throw new IllegalArgumentException("illegal arguments " +
					       a + ", " + b +
					       " for &");
	} else if (a instanceof BigInteger || b instanceof BigInteger) {
	    return downsize(toBigInteger(a).and(toBigInteger(b)));
	} else if (a instanceof Long || b instanceof Long) {
	    long r = a.longValue() & b.longValue();
	    return new Long(r);
	} else {
	    int r = a.intValue() & b.intValue();
	    return new Integer(r);
	}
    }

    public static Number or(Number a, Number b) {
	if ((a instanceof Float ||
	     a instanceof Double ||
	     a instanceof BigDecimal) ||
	    (b instanceof Float ||
	     b instanceof Double ||
	     b instanceof BigDecimal)) {
	    throw new IllegalArgumentException("illegal arguments " +
					       a + ", " + b +
					       " for |");
	} else if (a instanceof BigInteger || b instanceof BigInteger) {
	    return downsize(toBigInteger(a).or(toBigInteger(b)));
	} else if (a instanceof Long || b instanceof Long) {
	    long r = a.longValue() | b.longValue();
	    return new Long(r);
	} else {
	    int r = a.intValue() | b.intValue();
	    return new Integer(r);
	}
    }

    public static Number xor(Number a, Number b) {
	if ((a instanceof Float ||
	     a instanceof Double ||
	     a instanceof BigDecimal) ||
	    (b instanceof Float ||
	     b instanceof Double ||
	     b instanceof BigDecimal)) {
	    throw new IllegalArgumentException("illegal arguments " +
					       a + ", " + b +
					       " for ^");
	} else if (a instanceof BigInteger || b instanceof BigInteger) {
	    return downsize(toBigInteger(a).xor(toBigInteger(b)));
	} else if (a instanceof Long || b instanceof Long) {
	    long r = a.longValue() ^ b.longValue();
	    return new Long(r);
	} else {
	    int r = a.intValue() ^ b.intValue();
	    return new Integer(r);
	}
    }

    public static Number shiftLeft(Number a, Number b) {
	if ((a instanceof Float ||
	     a instanceof Double ||
	     a instanceof BigDecimal) ||
	    (b instanceof Float ||
	     b instanceof Double ||
	     b instanceof BigDecimal)) {
	    throw new IllegalArgumentException("illegal arguments " +
					       a + ", " + b +
					       " for <<");
	} else if (a instanceof BigInteger) {
	    return downsize(toBigInteger(a).shiftLeft(b.intValue()));
	} else if (a instanceof Long) {
	    long r = a.longValue() << b.intValue();
	    return new Long(r);
	} else {
	    int r = a.intValue() << b.intValue();
	    return new Integer(r);
	}
    }

    public static Number shiftRight(Number a, Number b) {
	if ((a instanceof Float ||
	     a instanceof Double ||
	     a instanceof BigDecimal) ||
	    (b instanceof Float ||
	     b instanceof Double ||
	     b instanceof BigDecimal)) {
	    throw new IllegalArgumentException("illegal arguments " +
					       a + ", " + b +
					       " for >>");
	} else if (a instanceof BigInteger) {
	    return downsize(toBigInteger(a).shiftRight(b.intValue()));
	} else if (a instanceof Long) {
	    long r = a.longValue() >> b.intValue();
	    return new Long(r);
	} else {
	    int r = a.intValue() >> b.intValue();
	    return new Integer(r);
	}
    }

    public static Number unsignedShiftRight(Number a, Number b) {
	if ((a instanceof Float ||
	     a instanceof Double ||
	     a instanceof BigDecimal) ||
	    (b instanceof Float ||
	     b instanceof Double ||
	     b instanceof BigDecimal)) {
	    throw new IllegalArgumentException("illegal arguments " +
					       a + ", " + b +
					       " for >>>");
	} else if (a instanceof BigInteger) {
	    return downsize(toBigInteger(a).shiftRight(b.intValue()));
	} else if (a instanceof Long) {
	    long r = a.longValue() >>> b.intValue();
	    return new Long(r);
	} else {
	    int r = a.intValue() >>> b.intValue();
	    return new Integer(r);
	}
    }

    public static BigInteger toBigInteger(Number num) {
	if (num instanceof BigInteger) {
	    return (BigInteger) num;
	} else if (num instanceof Byte) {
	    byte n = num.byteValue();
	    return new BigInteger(new byte[]{n});
	} else if (num instanceof Short) {
	    short n = num.shortValue();
	    return new BigInteger(new byte[]{(byte) ((n >>> 8) & 0xFF),
					     (byte) ((n >>> 0) & 0xFF)});
	} else if (num instanceof Integer) {
	    int n = num.intValue();
	    return new BigInteger(new byte[]{(byte) ((n >>> 24) & 0xFF),
					     (byte) ((n >>> 16) & 0xFF),
					     (byte) ((n >>> 8) & 0xFF),
					     (byte) ((n >>> 0) & 0xFF)});
	} else {
	    long n = num.longValue();
	    return new BigInteger(new byte[]{(byte) ((n >>> 56) & 0xFF),
					     (byte) ((n >>> 48) & 0xFF),
					     (byte) ((n >>> 40) & 0xFF),
					     (byte) ((n >>> 32) & 0xFF),
					     (byte) ((n >>> 24) & 0xFF),
					     (byte) ((n >>> 16) & 0xFF),
					     (byte) ((n >>> 8) & 0xFF),
					     (byte) ((n >>> 0) & 0xFF)});
	}
    }

    public static BigDecimal toBigDecimal(Number n) {
	if (n instanceof BigInteger) {
	    return new BigDecimal((BigInteger) n);
	} else {
	    return new BigDecimal(n.doubleValue());
	}
    }

    static final BigInteger INT_MIN =
	toBigInteger(new Integer(Integer.MIN_VALUE));
    static final BigInteger INT_MAX =
	toBigInteger(new Integer(Integer.MAX_VALUE));
    static final BigInteger LONG_MIN =
	toBigInteger(new Long(Long.MIN_VALUE));
    static final BigInteger LONG_MAX =
	toBigInteger(new Long(Long.MAX_VALUE));

    public static Number downsize(BigInteger n) {
	if (n.compareTo(INT_MIN) >= 0 &&
	    n.compareTo(INT_MAX) <= 0) {
	    return new Integer(n.intValue());
	} else if (n.compareTo(LONG_MIN) >= 0 &&
		   n.compareTo(LONG_MAX) <= 0) {
	    return new Long(n.longValue());
	} else {
	    return n;
	}
    }

    public static Number downsize(BigDecimal n) {
	if (n.compareTo(new BigDecimal(n.floatValue())) == 0) {
	    return new Float(n.floatValue());
	} else if (n.compareTo(new BigDecimal(n.doubleValue())) == 0) {
	    return new Double(n.doubleValue());
	} else {
	    return n;
	}
    }
}
