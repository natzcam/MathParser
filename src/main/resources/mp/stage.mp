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

template Subject{
  var name;
  var teacher;
  var students;
}

template Teacher{
  var name;
  func __init__(name){
    this.name = name;
  }
}

templadte Student{
  var subjects;
}

var t = new Teacher("test");
print t.name;
//persist t;

//var t = get Teacher with id = 1;
//print t.id;
//print t.name;

//hey"test"

/*
ladjsflajdsf lcjaldsf print
lajdsl dflkdjfdlk 
sldkjflds k dkfjkd /5234/
*/