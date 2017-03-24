// enumeration.dm

def foreach(elements, func):
    for i in elements:
	func(i);
    end;
end;

def collect(elements, func):
    result = #[];
    for i in elements:
	result << func(i);
    end;
    return result;
end;
      
def select(elements, func):
    result = #[];
    for i in elements:
	if func(i):
	    result << i;
	end;
    end;
    return result;
end;
      
def reject(elements, func):
    result = #[];
    for i in elements:
	if !func(i):
	    result << i;
	end;
    end;
    return result;
end;

def detect(elements, func):
    for i in elements:
	if func(i):
	    return i;
	end;
    end;
    return null;
end;
