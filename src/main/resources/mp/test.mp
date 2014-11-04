model Client{
  key id;
  string firstName;
  string lastName;
  int age;
  int sex;
  list doctors;
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

var x = new Hospital();
print x;
//relationship Client.currentDoctor <> Doctor.id
//relationship Client.doctors <<>> Physician.clients;
//relationship Hospital.clients <<> Client.hospital;