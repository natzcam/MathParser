model Client{
  identifier id;
  identifier{
    firstName, lastName;
  }
  string firstName;
  string lastName;
  int age;
  int sex;
  list:Physician doctors;
  Physician currentDoctor;
  Hospital hospitalConfined;
}

model Physician{
  string firstName;
  string lastName;
  list:Client clients;
}

model Hospital{
  string name;
  string address;
  list:Client clients;
}

relationship Client.currentDoctor <> Doctor.id
relationship Client.doctors <<>> Physician.clients;
relationship Hospital.clients <<> Client.hospital;