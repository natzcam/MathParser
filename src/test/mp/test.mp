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

//var t = new Teacher("test");
//print t.name;

var t = get Teacher 1;
print t.__id__;
print t.name;