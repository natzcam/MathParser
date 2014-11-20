
var c = new Child();
c.name = "natz";
c.id = 1;
save c;

print Child.parent;

var p1 = new Parent();
p1.id = 1;
p1.name = "P1";
p1.children.add(c);
save p1;

c.parent = p1;
save c;

var p2 = new Parent();
p2.id = 2;
p2.name = "P2";
save p2;