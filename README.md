/#

<div>MathParser</div>
==========
<p>My own exploration into programming languages.</p>
<p>No wiki yet, although I use this README.md as a test file so below is an example in itself.</p>
<p>More examples in <a href="https://github.com/natzcam/MathParser/tree/master/positive">positive</a> and <a href="https://github.com/natzcam/MathParser/tree/master/negative">negative</a> subfolder.</p>

#/

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
