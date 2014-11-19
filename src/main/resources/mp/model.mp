model Parent{
  string name;
  list:Child children;
}

model Child{
  string name;
  ref:Parent parent;
}