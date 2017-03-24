// fib.dm

def fib(n):
    if n < 2:
	return n;
    else:
	fib(n - 2) + fib(n - 1);
    end;
end;

println(fib(10));
