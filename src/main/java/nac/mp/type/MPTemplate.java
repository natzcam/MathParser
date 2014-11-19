/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nac.mp.EvalException;
import nac.mp.ast.Scope;
import nac.mp.ast.Expression;

/**
 *
 * @author camomon
 */
public class MPTemplate extends MPObject implements Creator {

  private final MPTemplate extParent;
  private final String name;
  transient private final List<Expression> declarations;

  public MPTemplate(Scope parent, String name, MPTemplate extParent, List<Expression> declarations) {
    super(parent, null);
    this.name = name;
    this.extParent = extParent;
    this.declarations = declarations;
  }

  public String getName() {
    return name;
  }

  public List<Expression> getDeclarations() {
    return declarations;
  }

  @Override
  public Type getType() {
    return Type.TEMPLATE;
  }

  @Override
  public MPObject newInstance() throws EvalException {
    MPObject obj = new MPBaseObj(parent, this);
    if (extParent != null) {
      for (Expression d : extParent.declarations) {
        d.eval(obj);
      }
    }
    for (Expression d : declarations) {
      d.eval(obj);
    }
    return obj;
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
  public boolean containsVar(String name) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public MPObject getVar(String name) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void setVar(String name, MPObject value) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
}
