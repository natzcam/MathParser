object zz{
func test(){
  return func(w){
    print w;
  };
}

}



zz.test()("hello");

var x = "11000";
print x.toInt();

var y = "122.00";
print y.toFloat();

class Subject{
  var name;
  var teacher;
  var students;
}

class Teacher{
  var name;
  func __init__(name){
    this.name = name;
  }
}

class Student{
  var subjects;
}

var t = new Teacher("test");
print t.name;
//persist t;

//var t = get Teacher with id = 1;
//print t.__id__;
//print t.name;