var mami = "mami";

//declare function
func simplef(){
  assert mami == "mami";
}

simplef();

func simplefunc(){
  print "hey";
}

simplefunc();


func hello(firstParam, secondParam){
  var x = firstParam + secondParam;
  assert x == 3;
}

hello(1, 2);

func retfunc(x){
   return x;
}

assert 2 == retfunc(2);