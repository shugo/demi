/* Demi.java */

package JP.ac.osaka_u.shugo.demi;

import java.io.*;
import java.util.*;
import java.lang.reflect.*;

public class Demi {

    public static final String version = "0.1.6";

    PrintStream out = System.out;
    Vector loadpath = new Vector();
    Vector importedPackages = new Vector();
    Vector providedFiles = new Vector();
    Hashtable scopeTable = new Hashtable();
    Hashtable callInfoTable = new Hashtable();
    Hashtable exceptionTable = new Hashtable();
    Module global, kernel;

    public Demi() {
	global = new Module(Symbol.get("global"));
	kernel = new Module(Symbol.get("kernel"));
	global.include(kernel);
	kernel.include(global);

	try {
	    global.addSpecialVariable(Symbol.get("global"),
				      new SpecialVariableGetter() {
		public Object get(Symbol name) {
		    return global;
		}
	    }, null);
	    global.addSpecialVariable(Symbol.get("kernel"),
				      new SpecialVariableGetter() {
		public Object get(Symbol name) {
		    return kernel;
		}
	    }, null);

	    kernel.setVariable(Symbol.get("boolean"), Boolean.TYPE);
	    kernel.setVariable(Symbol.get("character"), Character.TYPE);
	    kernel.setVariable(Symbol.get("byte"), Byte.TYPE);
	    kernel.setVariable(Symbol.get("short"), Short.TYPE);
	    kernel.setVariable(Symbol.get("int"), Integer.TYPE);
	    kernel.setVariable(Symbol.get("long"), Long.TYPE);
	    kernel.setVariable(Symbol.get("float"), Float.TYPE);
	    kernel.setVariable(Symbol.get("double"), Double.TYPE);

	    kernel.setVariable(Symbol.get("Function"),
			   Class.forName("JP.ac.osaka_u.shugo.demi.Function"));
	    kernel.setVariable(Symbol.get("Closure"),
			   Class.forName("JP.ac.osaka_u.shugo.demi.Closure"));
	    kernel.setVariable(Symbol.get("Range"),
			   Class.forName("JP.ac.osaka_u.shugo.demi.Range"));
	    kernel.setVariable(Symbol.get("Symbol"),
			   Class.forName("JP.ac.osaka_u.shugo.demi.Symbol"));

	    kernel.setVariable(Symbol.get("version"), version);
	    kernel.addSpecialVariable(Symbol.get("loadpath"),
				      new SpecialVariableGetter() {
		public Object get(Symbol name) {
		    return loadpath;
		}
	    }, new SpecialVariableSetter() {
		public void set(Symbol name, Object val) {
			loadpath = (Vector) val;
		}
	    });

	    kernel.setVariable(Symbol.get("stdin"), System.in);
	    kernel.addSpecialVariable(Symbol.get("stdout"),
				      new SpecialVariableGetter() {
		public Object get(Symbol name) {
		    return out;
		}
	    }, new SpecialVariableSetter() {
		public void set(Symbol name, Object val) {
		    out = (PrintStream) val;
		}
	    });
	    kernel.setVariable(Symbol.get("stderr"), System.err);

	    kernel.setVariable(Symbol.get("eval"),
			       new EvalFunction(this));
	    kernel.setVariable(Symbol.get("exit"),
			       new ExitFunction(this));
	    kernel.setVariable(Symbol.get("include"),
			       new IncludeFunction(this));
	    kernel.setVariable(Symbol.get("inspect"),
			       new InspectFunction(this));
	    kernel.setVariable(Symbol.get("intern"),
			       new InternFunction(this));
	    kernel.setVariable(Symbol.get("load"),
			       new LoadFunction(this));
	    kernel.setVariable(Symbol.get("p"),
			       new PFunction(this));
	    kernel.setVariable(Symbol.get("print"),
			       new PrintFunction(this));
	    kernel.setVariable(Symbol.get("println"),
			       new PrintlnFunction(this));
	    kernel.setVariable(Symbol.get("require"),
			       new RequireFunction(this));

    	    Class.forName("com.oroinc.text.regex.Pattern");
	    kernel.setVariable(Symbol.get("RegExp"),
		   Class.forName("JP.ac.osaka_u.shugo.util.RegExp"));
	    kernel.setVariable(Symbol.get("IllegalPatternException"),
	    Class.forName("JP.ac.osaka_u.shugo.util.IllegalPatternException"));
	} catch (Throwable e) {
	}

	importedPackages.addElement("java.lang");
	importedPackages.addElement("java.io");
	importedPackages.addElement("java.util");
    }

    public static boolean canConvert(Object obj, Class type) {
	if (type.isPrimitive()) {
	    if (type == Boolean.TYPE) {
		return obj instanceof Boolean;
	    } else {
		return obj instanceof Number ||
		    obj instanceof Character;
	    }
	} else if (obj == null || type.isInstance(obj)) {
	    return true;
	} else if (type.isArray()) {
	    if (!obj.getClass().isArray())
		return false;
	    Class componentType = type.getComponentType();
	    return Array.getLength(obj) == 0 ||
		canConvert(Array.get(obj, 0), componentType);
	} else {
	    return false;
	}
    }

    public static Object convert(Object obj, Class type) {
	if (obj == null || type.isInstance(obj))
	    return obj;
	if (type.isPrimitive()) {
	    if (type == Boolean.TYPE) {
		return new Boolean(test(obj));
	    } else if (type == Character.TYPE) {
		if (obj instanceof Number)
		    return new Character((char) ((Number) obj).intValue());
		else
		    return obj;
	    } else {
		if (obj instanceof Character)
		    obj = new Integer(((Character) obj).charValue());
		if (type == Byte.TYPE) {
		    return new Byte(((Number) obj).byteValue());
		} else if (type == Short.TYPE) {
		    return new Short(((Number) obj).shortValue());
		} else if (type == Integer.TYPE) {
		    return new Integer(((Number) obj).intValue());
		} else if (type == Long.TYPE) {
		return new Long(((Number) obj).longValue());
		} else if (type == Float.TYPE) {
		    return new Float(((Number) obj).floatValue());
		} else if (type == Double.TYPE) {
		    return new Double(((Number) obj).doubleValue());
		} else {
		    return obj;
		}
	    }
	} else if (type.isArray()) {
	    Class componentType = type.getComponentType();
	    int length = Array.getLength(obj);
	    Object ary = Array.newInstance(componentType, length);
	    for (int i = 0; i < Array.getLength(obj); i++) {
		Array.set(ary, i,
			  convert(Array.get(obj, i), componentType));
	    }
	    return ary;
	} else {
	    return obj;
	}
    }

    public static boolean test(Object obj) {
	if (obj instanceof Boolean) {
	    return ((Boolean) obj).booleanValue();
	} else {
	    return obj != null;
	}
    }

    public static Symbol intern(String str) {
	return Symbol.get(str);
    }

    public static String inspect(Object obj) {
	if (obj == null) {
	    return "null";
	} else if (obj.getClass().isArray()) {
	    StringBuffer buff = new StringBuffer("(");
	    int length = Array.getLength(obj);
	    for (int i = 0; i < length; i++) {
		if (i != 0) buff.append(", ");
		buff.append(inspect(Array.get(obj, i)));
	    }
	    buff.append(")");
	    return new String(buff);
	} else if (obj instanceof String) {
	    return "\"" + obj + "\"";
	} else if (obj instanceof Character) {
	    return "'" + obj + "'";
	} else {
	    return obj.toString();
	}
    }

    Scope getScope() {
	Stack stack;
	if (!scopeTable.containsKey(Thread.currentThread())) {
	    stack = new Stack();
	    Scope scope = new Scope(global, null);
	    stack.push(scope);
	    scopeTable.put(Thread.currentThread(), stack);
	    return scope;
	} else {
	    stack = (Stack) scopeTable.get(Thread.currentThread());
	    return (Scope) stack.peek();
	}
    }

    void pushScope(Scope scope) {
	Stack stack;
	if (!scopeTable.containsKey(Thread.currentThread())) {
	    stack = new Stack();
	    stack.push(new Scope(global, null));
	    stack.push(scope);
	    scopeTable.put(Thread.currentThread(), stack);
	} else {
	    stack = (Stack) scopeTable.get(Thread.currentThread());
	    stack.push(scope);
	}
    }

    Scope popScope() throws NoSuchElementException {
	if (!scopeTable.containsKey(Thread.currentThread())) {
	    throw new NoSuchElementException("scope stack is empty");
	} else {
	    Stack stack = (Stack) scopeTable.get(Thread.currentThread());
	    return (Scope) stack.pop();
	}
    }

    Stack getCallInfoStack() {
	if (!callInfoTable.containsKey(Thread.currentThread())) {
	    return null;
	} else {
	    return (Stack) callInfoTable.get(Thread.currentThread());
	}
    }

    void pushCallInfo(Function function, SimpleNode node) {
	CallInfo callInfo = new CallInfo(function, node);
	Stack stack;
	if (!callInfoTable.containsKey(Thread.currentThread())) {
	    stack = new Stack();
	    stack.push(callInfo);
	    callInfoTable.put(Thread.currentThread(), stack);
	} else {
	    stack = (Stack) callInfoTable.get(Thread.currentThread());
	    stack.push(callInfo);
	}
    }

    CallInfo popCallInfo() throws NoSuchElementException {
	if (!callInfoTable.containsKey(Thread.currentThread())) {
	    throw new NoSuchElementException("callInfo stack is empty");
	} else {
	    Stack stack = (Stack) callInfoTable.get(Thread.currentThread());
	    return (CallInfo) stack.pop();
	}
    }

    public PrintStream getOutputStream() {
	return out;
    }

    public void setOutputStream(PrintStream out) {
	this.out = out;
    }

    public void addLoadPath(String path) {
	loadpath.addElement(path);
    }

    public Object getGlobalVariable(Symbol symbol) throws Throwable {
	return global.getVariable(symbol);
    }

    public void setGlobalVariable(Symbol symbol, Object value)
	throws Throwable {
	global.setVariable(symbol, value);
    }

    public Object getKernelVariable(Symbol symbol) throws Throwable {
	return kernel.getVariable(symbol);
    }

    public void setKernelVariable(Symbol symbol, Object value)
	throws Throwable {
	kernel.setVariable(symbol, value);
    }

    public Object evalString(String str)
	throws ParseException, Jump {
	InputStream in = new ByteArrayInputStream(str.getBytes());
	Parser parser = new Parser(in);
	parser.CompilationUnit();
	return ((SimpleNode) parser.jjtree.rootNode()).evaluate(this);
    }

    public void loadInputStream(InputStream in)
	throws ParseException, Jump {
	Parser parser = new Parser(in);
	parser.CompilationUnit();
	((SimpleNode) parser.jjtree.rootNode()).evaluate(this);
    }

    private File findFile(String fileName) {
	File file = new File(fileName);
	if (file.exists())
	    return file;
	if (fileName.startsWith(File.separator) ||
	    fileName.length() >= 2 && fileName.charAt(1) == ':')
	    return null;
	Enumeration dirs = loadpath.elements();
	while (dirs.hasMoreElements()) {
	    String dir = (String) dirs.nextElement();
	    file = new File(dir, fileName);
	    if (file.exists())
		return file;
	}
	if (fileName.endsWith(".dm")) {
	    return null;
	} else {
	    return findFile(fileName + ".dm");
	}
    }

    private void loadFile0(File file)
	throws FileNotFoundException, NoSuchElementException,
	       ParseException, Jump {
	InputStream in = new FileInputStream(file);
	Parser parser = new Parser(in);
	parser.fileName = file.getPath();
	parser.CompilationUnit();
	pushScope(new Scope(global, null));
	try {
	    ((SimpleNode) parser.jjtree.rootNode()).evaluate(this);
	} finally {
	    popScope();
	}
    }

    public void loadFile(String fileName)
	throws FileNotFoundException, NoSuchElementException,
	       ParseException, Jump {
	File file = findFile(fileName);
	if (file == null)
	    throw new FileNotFoundException(fileName);
	loadFile0(file);
    }

    public boolean requireFile(String fileName)
	throws FileNotFoundException, NoSuchElementException,
	       ParseException, Jump {
	File file = findFile(fileName);
	if (file == null)
	    throw new FileNotFoundException(fileName);
	if (providedFiles.contains(file.getPath()))
	    return false;
	loadFile0(file);
	providedFiles.addElement(file.getPath());
	return true;
    }
}
