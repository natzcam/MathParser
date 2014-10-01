class Subject{
  var name;
  var teacher;
  var students;
}

class Teacher{
  var name;
  var(onetomany on Student.teacher) students;
  func __init__(name){
    this.name = name;
  }
}

class Student{
  Teacher teacher
}

//var t = new Teacher("test");
//print t.name;
//persist t;

var t = get Teacher 1;
print t.__id__;
print t.name;