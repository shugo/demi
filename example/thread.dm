// thread.dm

thread1 = new Thread([
    name = "thread1";
    while true:
	println(name);
	Thread::sleep(100);
    end;
]);

thread2 = new Thread([
    name = "thread2";
    while true:
	println(name);
	Thread::sleep(200);
    end;
]);

thread1.start();
thread2.start();
