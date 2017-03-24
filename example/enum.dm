// enum.dm

require("enumeration");

ary = #(1, 2, 3, 4, 5);

doubles = collect(ary, [ i: i * 2 ]);
p(doubles);

evens = select(ary, [ i: i % 2 == 0 ]);
p(evens);

odds = reject(ary, [ i: i % 2 == 0 ]);
p(odds);

three = detect(ary, [ i: i == 3 ]);
p(three);
