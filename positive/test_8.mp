
var what = object{
  var x = 10;
  var y = 20;
  func test(){
    print "hello world";
  }
};


func What(param1, param2){
  object zzz prototype what{
    var x = param1;
    var y = param2;
  }
  return zzz;
}


var obj1 = What(11, 21);
print obj1.x;
print obj1.y;
var obj2 = What(12, 22);
print obj2.x;
print obj2.y;