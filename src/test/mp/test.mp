

class Test(xparam) {
  func testf(){
    print "tesf";
  }
}

var z = new Test;
z.testf();
print z.x;

var z2 = new Test;
print z2.x;
