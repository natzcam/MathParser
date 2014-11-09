model Parent{
  string name;
  list children;
  list children2;
}

model Child{
  string name;
  ref parent;
  ref parent2;
}

relate Parent.children <>> Child;
relate Parent.children2 <>> Child.parent;
relate Parent <>> Child.parent2;


var x = new Parent();
print x;
x.name = "FB";