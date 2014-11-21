var a = 2;

if(true){
  assert a == 2;
}

var x = "hey";
var testvar = 0;

if(x == "hey"){
  testvar = 1;
}else if(x == "hello"){
  testvar = 2;
}

print testvar;
assert testvar == 1;

x = "hello";

if(x == "hey"){
  testvar = 1;
}else{
  testvar = 2;
}

assert testvar == 2;
