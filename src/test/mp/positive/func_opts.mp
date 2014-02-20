


func hello(firstParam, secondParam){
  var x = firstParam + secondParam;
  var y = opts.op1 - opts.op2;
  assert x == 3;
  assert y == 5;
}

hello(1, 2; op1:10, op2: 5);

func retfunc(x){
   return x;
}

assert 2 == retfunc(2);

//opts only

func optsonly(){
  assert opts.z == 11;
  print opts.z;
}

optsonly(;z:11);