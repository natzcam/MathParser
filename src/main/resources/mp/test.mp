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
var resultList = select Parent where {
  this.name == (select Parent where {this.name == "P1";})[0].name;
};
var result = resultList[0];
print result;

var rc = select Child where {this.name == "natz";};
print rc;
