/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type;

import nac.mp.EvalException;
import nac.mp.ast.Scope;
import nac.mp.ast.statement.ModelDecl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author camomon
 */
public class MPModel extends MPObject implements Creator {

  private static final Logger log = LogManager.getLogger(MPModel.class);
  private final ModelDecl modelDecl;

  public MPModel(Scope parent, ModelDecl modelDecl) {
    super(parent, null);
    this.modelDecl = modelDecl;
  }

  public String getName() {
    return modelDecl.getName();
  }

  @Override
  public MPObject.Hint getHint() {
    return MPObject.Hint.MODEL;
  }

  @Override
  public String toString() {
    return "model:" + modelDecl.getName();
  }

  @Override
  public MPObject notEqual(MPObject right) {
    switch (right.getHint()) {
      case MODEL:
        return new MPBoolean(this != right);
    }
    return new MPBoolean(true);
  }

  @Override
  public MPObject newInstance() throws EvalException {
    MPObject obj = new MPModelObject(parent, this);
//    obj.setVar("id", new MPVoid());
    for (MPObject v : vars.values()) {
      if (v instanceof MPAttribute) {
        MPAttribute attr = (MPAttribute) v;
        obj.declareVarLocal(attr.getName(), attr.newInstance());
      }
    }
    return obj;
  }
}
