package JP.ac.osaka_u.shugo.util;

public class ConcreteClassLoader extends ClassLoader {
    protected Class loadClass(String name, boolean resolve)
	throws ClassNotFoundException {
	Class cls = findSystemClass(name);
	if (resolve && cls != null)
	    resolveClass(cls);
	return cls;
    }

    public Class loadClass(byte data[], int offset, int length) {
	return defineClass(null, data, offset, length);
    }

    public Class loadClass(byte data[]) {
	return defineClass(null, data, 0, data.length);
    }
}
