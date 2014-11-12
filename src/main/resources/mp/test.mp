model Parent{
  string name;
  list:Child children;
}

model Child{
  string name
}

if(1 == 2 || 2 == 2){
  print "yes";
}

var x = select Parent where {this.attr3 == 3 && this.attr1 == "attr1_50";};
print x;