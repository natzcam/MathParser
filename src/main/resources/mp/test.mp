model Parent{
  string attr1;
  string attr2;
  int attr3;
}

//relate Parent.children <>> Child;
//relate Parent.children2 <>> Child.parent;
//relate Parent <>> Child.parent2;

if(1 == 2 || 2 == 2){
  print "yes";
}

var x = select Parent where {this.attr3 == 3 && this.attr1 == "attr1_50";};
print x;