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
