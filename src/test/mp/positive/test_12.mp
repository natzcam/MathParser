object zz{
func test(){
  return func(w){
    print w;
  };
}

}



zz.test()("hello");

var x = "11000";
print x.to_int();