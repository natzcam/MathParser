
var what = object{
  var x = 10;
  var y = 20;
  func test(){
    print this.y + this.x;
  }
};


func What(param1, param2){
  object zzz {
    var x = param1;
    var y = param2;
  } prototype what;
  return zzz;
}


var obj1 = What(11, 21);
print obj1.x;
print obj1.y;
obj1.test();
var obj2 = What(12, 22);
print obj2.x;
print obj2.y;
obj2.test();