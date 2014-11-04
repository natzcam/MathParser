/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import nac.mp.EvalException;
import nac.mp.BasicScope;
import nac.mp.Scope;
import nac.mp.ast.Block;

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
  public MPObject.Hint getHint() {
    return MPObject.Hint.FUNCTION;
  }

  @Override
  public String toString() {
    return "func:" + this.hashCode();
  }

  public MPObject call(MPObject thisRef, List<MPObject> argsValues) throws EvalException {
    if (formalArgs.size() != argsValues.size()) {
      throw new EvalException("Argument mismatch: " + this);
    }
    Scope newScope = new BasicScope(parent);
    for (int i = 0; i < formalArgs.size(); i++) {
      newScope.declareVarLocal(formalArgs.get(i), argsValues.get(i));
    }
    newScope.setVarLocal("this", thisRef);
    return body.eval(newScope);
  }

  public MPObject call(MPObject thisRef, List<MPObject> argsValues, Map<String, MPObject> optsValues) throws EvalException {
    if (formalArgs.size() != argsValues.size()) {
      throw new EvalException("Argument mismatch: " + this);
    }

    Scope newScope = new BasicScope(parent);
    for (int i = 0; i < formalArgs.size(); i++) {
      newScope.declareVarLocal(formalArgs.get(i), argsValues.get(i));
    }

    MPObject opts = new MPObject(parent, null);
    for (String key : optsValues.keySet()) {
      opts.setVarLocal(key, optsValues.get(key));
    }

    newScope.setVarLocal("opts", opts);
    newScope.setVarLocal("this", thisRef);
    return body.eval(newScope);
  }

  @Override
  public MPObject notEqual(MPObject right) {
    switch (right.getHint()) {
      case FUNCTION:
        return new MPBoolean(this != right);
    }
    return new MPBoolean(true);
  }
}
