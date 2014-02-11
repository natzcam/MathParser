object hi{
  class TestExtend{
    func hi(){
      print "hi there";
    }
  }

}
object hello{
  class Test extends hi.TestExtend{
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

}


var z = new hello.Test(22);
z.testf();
print z.getprop();
z.wtf = "zzz";
z.hi();

print z.wtf;
var z2 = new hello.Test(21);
z2.testf();
print z2.getprop();
