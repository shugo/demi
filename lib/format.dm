/* format.dm

Please download printf package at:

http://www.efd.lth.se/~d93hb/java/printf/


ex).

printf("%-15s %4s %7s\n", "NAME:", "AGE:", "SCORE:");
printf("============================\n");
printf("%-15s %4d %6.2f%%\n", "Buffalo Bill", 45, 78.43);
printf("%-15s %4d %6.2f%%\n", "Donald Duck", 58, 97.98);
printf("%-15s %4d %6.2f%%\n", "Bill Gates", 40, 1.02);

*/

import java.math;
import hb.format;

def printf(fmt, *args):
    params = #[];
    for i in args:
	if i instanceof BigInteger:
	    i = i.longValue();
	elsif i instanceof BigDecimal:
	    i = i.doubleValue();
	end;
	params << i;
    end;
    Format::printf(fmt, params);
end;

def fprintf(out, fmt, *args):
    params = #[];
    for i in args:
	if i instanceof BigInteger:
	    i = i.longValue();
	elsif i instanceof BigDecimal:
	    i = i.doubleValue();
	end;
	params << i;
    end;
    Format::fprintf(out, fmt, params);
end;

def sprintf(fmt, *args):
    params = #[];
    for i in args:
	if i instanceof BigInteger:
	    i = i.longValue();
	elsif i instanceof BigDecimal:
	    i = i.doubleValue();
	end;
	params << i;
    end;
    return Format::sprintf(fmt, params);
end;

format = sprintf;		// alias
