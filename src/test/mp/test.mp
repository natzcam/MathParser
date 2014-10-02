class Subject{
  var name;
  manytomany(Teacher.subjects) teachers;
  manytomany(Student.subjects) students;
}

class Teacher{
  var name;
  onetomany(Student.teacher) students;
  func __init__(name){
    this.name = name;
  }
}

class Student{
  manytomany(Subject.students) subjects
}

//var t = new Teacher("test");
//print t.name;
//persist t;

var t = get Teacher 1;
print t.__id__;
print t.name;