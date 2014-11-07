model Parent{
  string name;
  list children;
}

model Child{
  string name;
  ref parent;
  ref parent;
}

rel Parent.children1 <>> Child;
rel Parent.children2 <>> Child.parent;
rel Parent <>> Child.parent2;
