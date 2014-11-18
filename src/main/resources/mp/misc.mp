model Parent{
  string name;
  list:Child children;
}

model Child{
  string name;
}

var p = new Parent();
p.id = 1;
p.name = "P1";
var c = new Child();
c.name = "natz";
p.children.add(c);
save p;

p = new Parent();
p.id = 2;
p.name = "P2";
//save to object db
save p;


//select
var resultList = select Parent where {
  this.name == (select Parent where {this.name == "P1";})[0].name;
};
var result = resultList[0];
print result;

var rc = select Child where {this.name == "natz";};
print rc;
