model Parent{
  string name;
  list:Child children;
}

model Child{
  string name;
}

var p1 = new Parent();
p1.id = 1;
p1.name = "P1";
var c = new Child();
c.name = "natz";
p1.children.add(c);
save p1;

var p2 = new Parent();
p2.id = 2;
p2.name = "P2";
//save to object db
save p2;

//select
var rp = select Parent where {
  this.name == "P1";
};
print rp[0].children[0];

var rc = select Child where {this.name == "natz";};
print rc[0];

print rp[0].children[0] == rc[0];

