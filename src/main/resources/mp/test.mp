model Client{
  Id id;
  String firstName;
  String lastName;
  Int age;
  Int sex;
  List doctors;
  Physician currentDoctor;
  Hospital hospitalConfined;
}

model Physician{
  string firstName;
  string lastName;
  list clients;
}

model Hospital{
  string name;
  string address;
  list clients;
}

//relationship Client.currentDoctor <> Doctor.id
//relationship Client.doctors <<>> Physician.clients;
//relationship Hospital.clients <<> Client.hospital;