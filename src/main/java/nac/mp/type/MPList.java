/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nac.mp.EvalException;
import nac.mp.ObjectStore;
import nac.mp.type.natv.MPInteger;

/**
 *
 * @author camomon
 */
public class MPList extends MPObject {
  private static final MPFunc ADD = new MPFunc(null, null) {
    
    @Override
    public MPObject call(MPObject thisRef, List<MPObject> argsValues, ObjectStore store) throws EvalException {
      MPList thisList = (MPList) thisRef;
      thisList.add(argsValues.get(0));
      return null;
    }
    
    @Override
    public MPObject call(MPObject thisRef, List<MPObject> argsValues, Map<String, MPObject> optsValues, ObjectStore store) throws EvalException {
      return call(thisRef, argsValues, store);
    }
  };

  private final List<MPObject> list;

  public MPList(int capacity, List<MPObject> initialValues) {
    super(null, null);
    list = new ArrayList<>(capacity);
    if (initialValues != null) {
      list.addAll(initialValues);
    }
  }

  public MPList(List<MPObject> initialValues) {
    super(null, null);
    list = new ArrayList<>();
    if (initialValues != null) {
      list.addAll(initialValues);
    }
  }

  public MPList() {
    super(null, null);
    list = new ArrayList<>();
  }

  @Override
  public MPObject getVar(String name, ObjectStore store) {
    switch (name) {
      case "add":
        return ADD;
    }
    return null;
  }

  public MPObject get(MPInteger index) {
    return list.get((int) index.getInt());
  }

  public void set(MPInteger index, MPObject elem) {
    list.set((int) index.getInt(), elem);
  }

  public void add(MPObject obj) {
    list.add(obj);
  }


  @Override
  public Type getType() {
    return Type.LIST;
  }

  @Override
  public void setLocalVar(String name, MPObject value) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void setLocalVars(Map<String, MPObject> vars) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void declareLocalVar(String name, MPObject defaultValue) throws EvalException {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public Set<String> getLocalVarKeys() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public Collection<MPObject> getLocalVarValues() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public boolean containsVar(String name, ObjectStore store) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void setVar(String name, MPObject value, ObjectStore store) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

}
