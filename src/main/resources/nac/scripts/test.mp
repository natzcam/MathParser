
var what = object{
  var x = 10;
  var y = 20;
  var z = "hello";
  func test(){
    print this.y + this.x;
  }
};

entity Test prototype what;