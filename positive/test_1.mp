


func make(y){
    object obj2{
      var helo = 2;
      func inc(){
         helo = helo + y;
      }
      func dec(){
        helo = helo - y;
      }
    }
    return obj2;
}
var obj2 = make(11);
obj2.inc();
print obj2.helo;
obj2.dec();
print obj2.helo;

obj2 = make(15);
obj2.inc();
print obj2.helo;
obj2.dec();
print obj2.helo;


