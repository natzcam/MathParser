
var what = object{
  var x = 10;
  func test(){
    print "hello world";
  }
};

var what2 = object prototype what{
  
  var y = 20;
};


what.test();
what.x = 30;
print what.x;
print what2.y;
print what2.x;
what2.test();
