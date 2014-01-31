func testfunc(){
   print "test";
}

testfunc();

func testfuncex(){
   return "pak";
}

print testfuncex();


func testfunc2(hello, hi){
  print hello;
  print hi;
}

testfunc2("Hello", "WOrld");

func testfunc2ex(hello, hi){
  return hello + hi;
}

print testfunc2ex(1, 2);

func testfunc3(hello, hi){
  print hello;
  print hi;
  print opts;
  print opts.mami;
}

testfunc3("Hello", "WOrld"; mami:2);

func testfunc3ex(hello, hi){
  return hello + hi + opts.mami;
}

print testfunc3ex(2, 2; mami:2);

 