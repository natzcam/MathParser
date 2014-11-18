
var c = new Child();
c.name = "natz";
c.id = 1;
save c;

var p1 = new Parent();
p1.id = 1;
p1.name = "P1";
p1.children.add(c);
save p1;

var p2 = new Parent();
p2.id = 2;
p2.name = "P2";
save p2;