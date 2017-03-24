// hello.dm

import java.awt;

frame = new Frame("Test");
frame.setLayout(new FlowLayout());

hello = new Button("Hello");
hello.addActionListener([ e:
    println("Hello, World!");
]);
frame.add(hello);

quit = new Button("Quit");
quit.addActionListener([ e:
    exit(0);
]);
frame.add(quit);

frame.pack();
frame.show();
