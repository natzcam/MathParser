model Parent{
  string name;
  list:Child children;
}

model Child{
  string name;
}

var p = new Parent();
p.id = 2;
p.name = "P2";
var c = new Child();
c.name = "natz";

p.children.add(c);

//save to object db
save p;

//select
var resultList = select Parent where {
  this.name == "P2";
};
var result = resultList[0];
print result.children[0].name;

var rc = select Child where {this.name == "natz";};
print rc;
