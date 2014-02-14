object test{
  object test{
    var y = 10;
    func z(ld){
      print this.y + ld;
    }
  }
}

object what{
  var y = 20;
}


test.test.z(20);
what.z = test.test.z;
what.z(20);
test.test.z(20);