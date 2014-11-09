model Parent{
  string attr1;
  string attr2;
  int attr3;
}

var i = 1;
var y;
while(i < 100){
  print i;
  y = new Parent();
  y.id = i;
  y.attr1 = "attr1_" + i;
  y.attr2 = "attr2_" + i;
  
  if(i < 30){
    y.attr3 = i;
  }else{
    y.attr3 = 3;
  }

  print y;
  save y;

  i = i + 1;
}