// regex.dm
// please get OROMatcher(TM) at http://www.oroinc.com/.

re = new RegExp("[0-9]+");
p(re.match("I'm 22 years old."));
re = new RegExp("([a-z]+)");
p(re.sub("123abc", "<$1>"));
p(re.sub("123abc", [s: s.toUpperCase()]));
p((new RegExp(":")).split("a:b:c"));
