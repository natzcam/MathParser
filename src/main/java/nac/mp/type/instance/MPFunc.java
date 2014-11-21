/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type.instance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import nac.mp.EvalException;
import nac.mp.ObjectStore;
import nac.mp.ast.BasicScope;
import nac.mp.ast.Expression;
import nac.mp.ast.Scope;
import nac.mp.type.MPObject;
import nac.mp.type.Type;

/**
 *
 * @author nathaniel
 */
public class MPFunc extends MPObject {

  protected final Expression body;
  protected final List<String> formalArgs = new ArrayList<>();

  public MPFunc(Scope parent, Expression body) {
    super(parent, null);
    this.body = body;
  }

  public Expression getBody() {
    return body;
  }

  public List<String> getFormalArgs() {
    return formalArgs;
  }

  @Override
  public Type getType() {
    return Type.FUNCTION;
  }

  public MPObject call(MPObject thisRef, List<MPObject> argsValues, ObjectStore store) {
    if (formalArgs.size() != argsValues.size()) {
      throw new EvalException("Argument mismatch: " + formalArgs.size() + "!=" + argsValues.size(), this);
    }
    Scope newScope = new BasicScope(parent);
    for (int i = 0; i < formalArgs.size(); i++) {
      newScope.declareVar(formalArgs.get(i), argsValues.get(i));
    }
    newScope.setLocalVar("this", thisRef);
    return body.eval(newScope, store);
  }

  public MPObject call(MPObject thisRef, List<MPObject> argsValues, Map<String, MPObject> optsValues, ObjectStore store) {
    if (formalArgs.size() != argsValues.size()) {
      throw new EvalException("Argument mismatch: " + formalArgs.size() + "!=" + argsValues.size(), this);
    }

    Scope newScope = new BasicScope(parent);
    for (int i = 0; i < formalArgs.size(); i++) {
      newScope.declareVar(formalArgs.get(i), argsValues.get(i));
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
  public String toString() {
    return getType() + ":" + formalArgs;
  }

}
