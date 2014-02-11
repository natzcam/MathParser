

class Test{
  var prop;
 
  func testf(){
    print "tesf";
  }
  func getprop(){
    return prop;
  }

 func __init__(param){
    prop = param;
  }
}

var z = new Test(22);
z.testf();
print z.getprop();

var z2 = new Test(21);
z2.testf();
print z2.getprop();
