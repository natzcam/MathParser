var p1list = select Parent where {
  this.name == "P1";
};

if(p1list.length() == 0){

  print p1list.length();

  var c = new Child();
  c.name = "natz";

  print Child.parent;

  var p1 = new Parent();
  p1.name = "P1";
  p1.children.add(c);
  save p1;
}else{
  print "contains someting";
  print p1list[0].children[0];
}

