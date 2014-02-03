


object testobj{
  var x = 0;
  func setX(y){
     x = y;
  }
  func getX(){
     return x;
  }
  var y;
}

testobj.setX(24);
print testobj.getX();