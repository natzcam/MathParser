/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type.instance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nac.mp.EvalException;
import nac.mp.ObjectStore;
import nac.mp.ast.BasicScope;
import nac.mp.ast.Block;
import nac.mp.ast.Scope;
import nac.mp.type.Type;

/**
 *
 * @author nathaniel
 */
public class MPFunc extends MPObject {

  protected final Block body;
  protected final List<String> formalArgs = new ArrayList<>();

  public MPFunc(Scope parent, Block body) {
    super(parent, null);
    this.body = body;
  }

  public Block getBody() {
    return body;
  }

  public List<String> getFormalArgs() {
    return formalArgs;
  }

  @Override
  public Type getType() {
    return Type.FUNCTION;
  }

  public MPObject call(MPObject thisRef, List<MPObject> argsValues, ObjectStore store) throws EvalException {
    if (formalArgs.size() != argsValues.size()) {
      throw new EvalException("Argument mismatch: " + this, this);
    }
    Scope newScope = new BasicScope(parent);
    for (int i = 0; i < formalArgs.size(); i++) {
      newScope.declareLocalVar(formalArgs.get(i), argsValues.get(i));
    }
    newScope.setLocalVar("this", thisRef);
    return body.eval(newScope, store);
  }

  public MPObject call(MPObject thisRef, List<MPObject> argsValues, Map<String, MPObject> optsValues, ObjectStore store) throws EvalException {
    if (formalArgs.size() != argsValues.size()) {
      throw new EvalException("Argument mismatch: " + this, this);
    }

    Scope newScope = new BasicScope(parent);
    for (int i = 0; i < formalArgs.size(); i++) {
      newScope.declareLocalVar(formalArgs.get(i), argsValues.get(i));
    }

    MPObject opts = new MPBaseObj(parent, null);
    for (String key : optsValues.keySet()) {
      opts.setLocalVar(key, optsValues.get(key));
    }

    newScope.setLocalVar("opts", opts);
    newScope.setLocalVar("this", thisRef);
    return body.eval(newScope, store);
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
  public MPObject getVar(String name, ObjectStore store) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void setVar(String name, MPObject value, ObjectStore store) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
}
