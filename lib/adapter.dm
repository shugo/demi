/* adapter.dm

ex1).

btn = new Button("Quit");
bind(btn, "actionPerformed", [ e:
    exit(0);
]);

ex2).

ConcreteWindowListener = adapter::get(WindowListener);

def opened(e):
    ...
end;

def closing(e):
    ...
end;

frame.addWindowListener(new ConcreteWindowListener({
    "windowOpened" => opened, "windowClosing" => closing
}));

*/

import JP.ac.osaka_u.shugo.util;
import java.awt.event;

module adapter:
    
    classLoader = new ConcreteClassLoader();
    cache = {};
    
    def get(interface):
	if cache.containsKey(interface):
	    return cache[interface];
	end;
	
	bout = new ByteArrayOutputStream();
	dout = new DataOutputStream(bout);
	
	methods = interface.getMethods();
	event = methods[0].getParameterTypes()[0];
	
	// magic
	code = #(0xca, 0xfe, 0xba, 0xbe, 0x00, 0x03, 0x00, 0x2d, 0x00);
	dout.write(code);
	
	// constant_pool_count
	dout.write(30 + (2 * methods.length));     // constant pool size + 1
	
	// constant_pool
	// 1
	dout.write(1);		// CONSTANT_Utf8
	dout.writeUTF("Code");
	// 2
	dout.write(1);		// CONSTANT_Utf8
	dout.writeUTF("java/lang/Object");
	// 3 class java.lang.Object
	dout.write(7);		// CONSTANT_Class
	dout.writeShort(2);	// name
	// 4
	dout.write(1);		// CONSTANT_Utf8
	dout.writeUTF("<init>");
	// 5
	dout.write(1);		// CONSTANT_Utf8
	dout.writeUTF("()V");
	// 6 java.lang.Object#<init>()V
	dout.write(10);		// CONSTANT_Methodref
	dout.writeShort(3);	// class
	dout.writeShort(7);	// name and type
	// 7 <init>()V
	dout.write(12);		// CONSTANT_NameAndType
	dout.writeShort(4);	// name
	dout.writeShort(5);	// type
	// 8  this_class
	dout.write(7);		// CONSTANT_Class
	dout.writeShort(16);	// name
	// 9  interface
	dout.write(7);		// CONSTANT_Class
	dout.writeShort(17);
	// 10
	dout.write(1);		// CONSTANT_Utf8
	dout.writeUTF("JP/ac/osaka_u/shugo/demi/Function");
	// 11 class Function
	dout.write(7);		// CONSTANT_Class
	dout.writeShort(10);	// name
	// 12
	dout.write(1);		// CONSTANT_Utf8
	dout.writeUTF("callSafely");
	// 13
	dout.write(1);		// CONSTANT_Utf8
	dout.writeUTF("([Ljava/lang/Object;)Ljava/lang/Object;");
	// 14 Function#callSafely
	dout.write(11);		// CONSTANT_InterfaceMethodref
	dout.writeShort(11);	// class
	dout.writeShort(15);	// name and type
	// 15
	dout.write(12);		// CONSTANT_NameAndType
	dout.writeShort(12);	// name
	dout.writeShort(13);	// type
	// 16
	dout.write(1);		// CONSTANT_Utf8
	n = interface.getName();
	dout.writeUTF("Concrete" + lastId(n));
	// 17
	dout.write(1);		// CONSTANT_Utf8
	dout.writeUTF(n.replace('.', '/'));
	// 18
	dout.write(1);		// CONSTANT_Utf8
	dout.writeUTF("(L" + event.getName().replace('.', '/') + ";)V");
	// 19
	dout.write(1);		// CONSTANT_Utf8
	dout.writeUTF("(Ljava/util/Hashtable;)V");
	// 20
	dout.write(1);		// CONSTANT_Utf8
	dout.writeUTF("java/util/Hashtable");
	// 21 class Hashtable
	dout.write(7);		// CONSTANT_Class
	dout.writeShort(20);	// name
	// 22 field name
	dout.write(1);		// CONSTANT_Utf8
	dout.writeUTF("functionTable");
	// 23 field descripter
	dout.write(1);		// CONSTANT_Utf8
	dout.writeUTF("Ljava/util/Hashtable;");
	// 24 field functionTable
	dout.write(9);		// CONSTANT_Fieldref
	dout.writeShort(8);	// class
	dout.writeShort(25);	// name and type
	// 25
	dout.write(12);		// CONSTANT_NameAndType
	dout.writeShort(22);	// name
	dout.writeShort(23);	// type
	// 26
	dout.write(1);		// CONSTANT_Utf8
	dout.writeUTF("get");
	// 27
	dout.write(1);		// CONSTANT_Utf8
	dout.writeUTF("(Ljava/lang/Object;)Ljava/lang/Object;");
	// 28 java.util.Hashtable#get(Ljava/lang/Object;)Ljava/lang/Object;
	dout.write(10);		// CONSTANT_Methodref
	dout.writeShort(21);	// class
	dout.writeShort(29);	// name and type
	// 29
	dout.write(12);		// CONSTANT_NameAndType
	dout.writeShort(26);	// name
	dout.writeShort(27);	// type
	
	for i in 0 .. methods.length - 1:
	    // 30 + (2 * i)
	    dout.write(1);	// CONSTANT_Utf8
	    dout.writeUTF(methods[i].getName());
	    // 31 + (2 * i)
	    dout.write(8);	// CONSTANT_String
	    dout.writeShort(30 + (2 * i));  // string
	end;
	
	// access flags
	dout.writeShort(1);	// ACC_PUBLIC
	
	dout.writeShort(8);	// this
	dout.writeShort(3);	// super
	dout.writeShort(1);	// interface count
	dout.writeShort(9);	// interface
	
	dout.writeShort(1);	// field count
	dout.writeShort(2);	// ACC_PRIVATE
	dout.writeShort(22);	// name
	dout.writeShort(23);	// descripter
	dout.writeShort(0);	// attributes count
	
	dout.writeShort(methods.length + 1); // method count
	
	// constructor
	dout.writeShort(1);	// ACC_PUBLIC
	dout.writeShort(4);	// <init>
	dout.writeShort(19);	// (Ljava/util/Hashtable;)V
	dout.writeShort(1);   // attribute count
	// attribute name
	dout.writeShort(1);   // Code
	init = #(0x00, 0x02, // max_stack = 2
		 0x00, 0x02, // max_locals = 2
		 0x00, 0x00, 0x00, 0x0a, // code length = 10
		 
		 0x2a,		// aload_0
		 0xb7, 0x00, 0x06,  // invokespecial Object.<init>()V
		 0x2a,		// aload_0
		 0x2b,		// aload_1
		 0xb5, 0x00, 0x18,  // putfield functionTable
		 0xb1,		// return
		 
		 0x00, 0x00, // exception table length = 0
		 0x00, 0x00  // attributes length = 0
		 );
	// attribute length
	dout.writeInt(init.length);
	dout.write(init);
	
	
	// methods
	m1 = #(0x00, 0x05,	// max_stack
	       0x00, 0x03,	// max_locals
	       0x00, 0x00, 0x00, 0x21,  // code length
	       
	       0x2a,		// aload_0
	       0xb4, 0x00, 0x18,  // getfield functionTable
	       0x12, 0x00,	// ldc methodName
	       0xb6, 0x00, 0x1c,  // invokevirtual Hashtable#get
	       0xc0, 0x00, 0x0b,  // checkcast Function
	       0x4d,		// astore_2
	       0x2c,		// aload_2
	       0xc6, 0x00, 0x12,  // ifnull return
	       0x2c,		// aload_2
	       0x04,		// iconst_1
	       0xbd, 0x00, 0x03,  // anewarray Object
	       0x59,		// dup
	       0x03,		// iconst_0
	       0x2b,		// aload_1
	       0x53,		// aastore
	       0xb9, 0x00, 0x0e, 0x02, 0x00,  // invokeinterface callSafely
	       0x57,		// pop
	       
	       0xb1,		// return

	       0x00, 0x00, // exception table length = 0
	       0x00, 0x00  // attributes length = 0
	       );
	for i in 0 .. methods.length - 1:
	    dout.writeShort(1);   // public
	    dout.writeShort(30 + (2 * i));  // 1st method
	    dout.writeShort(18);  // 1st method's signature
	    dout.writeShort(1);   // attribute count
	    dout.writeShort(1);   // Code
	    m1[13] = new Byte(31 + (2 * i));
	    dout.writeInt(m1.length);    // size of 1st method
	    dout.write(m1);
	end;
	
	// attributes count
	dout.writeShort(0);
	
	cls = this::classLoader.loadClass(bout.toByteArray());
	cache[interface] = cls;
	return cls;
    end;
    
    def lastId(name):
	idx = name.lastIndexOf('.');
	if idx > 0:
	    return name.substring(name.lastIndexOf('.') + 1);
	else:
	    return name;
	end;
    end;
    
    listenerTable = {};
    
    def register(listener):
	m = listener.getMethods();
	for i in m:
	    listenerTable.put(i.getName(), listener);
	end;
    end;

    register(ActionListener);
    register(WindowListener);
    register(FocusListener);
    register(ComponentListener);
    register(ContainerListener);
    register(MouseListener);
    register(MouseMotionListener);
    register(KeyListener);
    register(TextListener);
    register(AdjustmentListener);
    register(ItemListener);

    def bind(component, action, func):
	if action instanceof Symbol:
	    action = action.getName();
	end;
	listener = listenerTable.get(action);
	name = "add" + lastId(listener.getName());
	m = component.getClass().getMethod(name, #(listener));
	adapter = get(listener);
	m.invoke(component, #(new adapter({action => func})));
    end;
end;

bind = adapter::bind;
