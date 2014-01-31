
var what = object{
  var x = 10;
  func test(){
    print "hello world";
  }
};


object what2 prototype object{
  var x = 10;
  func test(){
    print "hello world";
  }
}{
  
}

object what3 prototype what{
  
}

what2.test();
what.x = 30;
print what2.x;
 
what3.test();