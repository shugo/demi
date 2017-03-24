package JP.ac.osaka_u.shugo.demi;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
	Hashtable options = new Hashtable();
	args = parseArgs(args, options);
	if (options.containsKey("-h") ||
	    options.containsKey("-help")) {
	    printUsage();
	    System.exit(0);
	}
	if (options.containsKey("-v") ||
	    options.containsKey("-version")) {
	    printVersion();
	    System.exit(0);
	}
	Demi demi = new Demi();
	initLoadPath(demi);
	try {
	    if (args.length == 0) {
		demi.setKernelVariable(Symbol.get("programname"), "-");
		demi.setKernelVariable(Symbol.get("args"), new Object[0]);
	    } else {
		demi.setKernelVariable(Symbol.get("programname"), args[0]);
		Object[] arguments = new Object[args.length - 1];
		System.arraycopy(args, 1, arguments, 0, arguments.length);
		demi.setKernelVariable(Symbol.get("args"), arguments);
	    }
	    demi.setKernelVariable(Symbol.get("debug"),
				   new Boolean(options.containsKey("-d")));
	    if (options.containsKey("-r"))
		demi.requireFile((String) options.get("-r"));
	    if (args.length == 0) {
		printVersion();
		DemiShell shell = new DemiShell(demi, System.in);
		shell.run();
	    } else {
		if (options.containsKey("-c")) {
		    InputStream in = new FileInputStream(args[0]);
		    Parser parser = new Parser(in);
		    parser.CompilationUnit();
		    System.out.println("- syntax OK");
		} else {
		    demi.loadFile(args[0]);
		}
	    }
	} catch (ParseException e) {
	    String msg = e.getMessage();
	    StringTokenizer tokenizer =
		new StringTokenizer(msg, "\r\n");
	    System.err.println(tokenizer.nextToken());
	    System.exit(1);
	} catch (TokenMgrError e) {
	    String msg = e.getMessage();
	    StringTokenizer tokenizer =
		new StringTokenizer(msg, "\r\n");
	    System.err.println(tokenizer.nextToken());
	    System.exit(1);
	} catch (Jump e) {
	    e.printBackTrace();
	    System.exit(1);
	} catch (Throwable e) {
	    System.err.println(e);
	    System.exit(1);
	}
    }

    private static void initLoadPath(Demi demi) {
	String str = System.getProperty("demi.loadpath");
	if (str == null) return;
	StringTokenizer tokenizer =
	    new StringTokenizer(str, File.pathSeparator);
	while (tokenizer.hasMoreTokens()) {
	    demi.addLoadPath(tokenizer.nextToken());
	}
    }

    private static String[] parseArgs(String[] args, Hashtable options) {
	int i;
	for (i = 0; i < args.length; i++) {
	    if (args[i].startsWith("-")) {
		if (args[i].equals("-r")) {
		    if (i < args.length - 1)
			options.put("-r", args[++i]);
		} else {
		    options.put(args[i], Boolean.TRUE);
		}
	    } else {
		break;
	    }
	}
	String[] rest = new String[args.length - i];
	System.arraycopy(args, i, rest, 0, rest.length);
	return rest;
    }

    private static void printVersion() {
	System.out.println("Demi version " + Demi.version);
	System.out.println("Copyright (C) 1997-1998  Shugo Maeda");
    }

    private static void printUsage() {
	System.out.println("usage: demi [switches] [file] [arguments]");
	System.out.println();
	System.out.println("  -c                check syntax only");
	System.out.println("  -d                set debug flag");
	System.out.println("  -r <filename>     require specified file");
	System.out.println("  -h, -help         display this help and exit");
	System.out.println("  -v, -version      output version information and exit");
    }
}
