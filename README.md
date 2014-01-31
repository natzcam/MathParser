//MathParser
//==========
//My own exploration into programming languages.
//No wiki yet, although I use this README.md as my test file so what
//inside here is an example in itself.
//You can look for more examples in <a href="https://github.com/natzcam/MathParser/tree/master/positive">positive</a> and <a href="https://github.com/natzcam/MathParser/tree/master/negative">negative</a> subfolder.

var what = object{
  var x = 10;
  var y = 20;
  func test(){
    print this.y + this.x;
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
obj1.test();
var obj2 = What(12, 22);
print obj2.x;
print obj2.y;
obj2.test();
