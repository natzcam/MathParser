model Parent{
  string attr1;
  string attr2;
  int attr3;
}

//relate Parent.children <>> Child;
//relate Parent.children2 <>> Child.parent;
//relate Parent <>> Child.parent2;

var x = select Parent where {this.attr3 == 3;};
print x;