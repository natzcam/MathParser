print Parent.children;
print Parent.name;

//select
var rp = select Parent where {
  this.name == "P1";
};
print rp[0].children[0];

var rc = select Child where {this.name == "natz";};
print rc[0];

print rp[0].children[0] == rc[0];
print rc[0].parent;
print rc[0].parent.name;



