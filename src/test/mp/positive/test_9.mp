

class Test(xparam) prototype object{
  func testf(){
    print "tesf";
  }
}{
  this.x = xparam;
  this.y = func(param){
    print param;
  };

  this.z = object{
    var zx = 20;
  };
}

var z = Test(10);
print z.x;

var z2 = Test(11);
print z2.x;
