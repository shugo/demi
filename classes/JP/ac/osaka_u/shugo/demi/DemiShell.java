/* DemiShell.java */

package JP.ac.osaka_u.shugo.demi;

import java.io.*;
import java.util.*;

public class DemiShell {

    public static String PS1 = "demi> ";
    public static String PS2 = "demi- ";

    Demi demi;
    BufferedReader input;
    String prompt;
    StringBuffer buff;

    public DemiShell(Demi demi, InputStream in) {
	this.demi = demi;
	InputStreamReader isr = new InputStreamReader(in);
	input = new BufferedReader(isr);
	buff = new StringBuffer();
	prompt = PS1;
    }

    public void run() {
    mainloop:
	while (true) {
	    System.out.print(prompt);
	    System.out.flush();
	    try {
		String line = input.readLine();
		if (line == null && buff.length() == 0)
		    break;
		if (line != null && line.trim().endsWith("\\")) {
		    line = line.trim();
		    line = line.substring(0, line.length() - 1);
		    buff.append(line);
		    prompt = PS2;
		    continue;
		}
		buff.append(line);
		buff.append('\n');
		try {
		    Object obj = demi.evalString(new String(buff));
		    System.out.println(Demi.inspect(obj));
		} catch (ParseException e) {
		    if (e.expectedTokenSequences != null) {
			int maxSize = 0;
			for (int i = 0;
			     i < e.expectedTokenSequences.length;
			     i++) {
			    if (maxSize < e.expectedTokenSequences[i].length) {
				maxSize = e.expectedTokenSequences[i].length;
			    }
			}
			Token t = e.currentToken.next;
			for (int i = 0; i < maxSize; i++) {
			    if (t.kind == 0) {
				prompt = PS2;
				continue mainloop;
			    }
			    t = t.next;
			}
		    }
		    String msg = e.getMessage();
		    StringTokenizer tokenizer =
			new StringTokenizer(msg, "\r\n");
		    System.err.println(tokenizer.nextToken());
		} catch (TokenMgrError e) {
		    String msg = e.getMessage();
		    StringTokenizer tokenizer =
			new StringTokenizer(msg, "\r\n");
		    System.err.println(tokenizer.nextToken());
		} catch (Jump e) {
		    e.printBackTrace();
		}
		if (line == null) break;
	    } catch (IOException e) {
		System.err.println(e);
		System.exit(1);
	    }
	    buff = new StringBuffer();
	    prompt = PS1;
	}
	System.out.println();
    }
}

