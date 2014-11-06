model Parent{
  string name;
  list children;
}

model Child{
  string name;
  ref parent;
}

rel Parent.children <>> Child;
rel Parent <>> Child.parent;
