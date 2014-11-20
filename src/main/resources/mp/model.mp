model Parent{
  string name;
  reflist:Child children;
}

model Child{
  string name;
  ref:Parent parent;
}