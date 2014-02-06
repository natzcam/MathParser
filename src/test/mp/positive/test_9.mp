var obj = object{
  func testf(){
    print "tesf";
  }
};


class Test() prototype obj{
  var x = 10;
  func y(param){
    print param;
  }

  object z{
    var zx = 20;
  }
}

var z = Test();
print z.x;
z.testf();