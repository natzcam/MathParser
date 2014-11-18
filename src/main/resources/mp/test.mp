
var c = new Child();
c.name = "natz";
c.id = 1;
save c;

var p1 = new Parent();
p1.id = 1;
p1.name = "P1";
save p1;

var p2 = new Parent();
p2.id = 2;
p2.name = "P2";
save p2;

print Parent.name;

//select
var rp = select Parent where {
  this.name == "P1";
};
print rp[0].children[0];

var rc = select Child where {this.name == "natz";};
print rc[0];

print rp[0].children[0] == rc[0];



