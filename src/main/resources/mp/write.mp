model Parent{
  string name;
  list:Child children;
}

model Child{
  string name;
  ref:Parent parent;
}

var p1 = new Parent();
p1.id = 1;
p1.name = "P1";
var c = new Child();
c.name = "natz";
c.parent = p1;
c.id = 1;
p1.children.add(c);
save p1;

var p2 = new Parent();
p2.id = 2;
p2.name = "P2";
//save to object db
save p2;