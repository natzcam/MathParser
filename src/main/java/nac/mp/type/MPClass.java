/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type;

import java.util.List;
import java.util.Map;
import nac.mp.BasicScope;
import nac.mp.EvalException;
import nac.mp.Scope;
import nac.mp.Type;
import nac.mp.ast.Block;

/**
 *
 * @author camomon
 */
public class MPClass extends MPFunc {

  private final MPObject prototype;

  public MPClass(MPObject prototype, Scope scope, Block body) {
    super(scope, body);
    this.prototype = prototype;
  }

  @Override
  public Type.Hint getHint() {
    return Type.Hint.CLASS;
  }

  @Override
  public String toString() {
    return "class:" + this.hashCode();
  }

  @Override
  public Type equal(Type right) {
    switch (right.getHint()) {
      case CLASS:
        return new MPBoolean(this == right);
    }
    return new MPBoolean(false);
  }

  @Override
  public Type notEqual(Type right) {
    switch (right.getHint()) {
      case CLASS:
        return new MPBoolean(this != right);
    }
    return new MPBoolean(true);
  }

  @Override
  public Type call(MPObject thisRef, List<Type> argsValues) throws EvalException {
    thisRef = new MPObject(scope, prototype);
    if (formalArgs.size() != argsValues.size()) {
      throw new EvalException("Argument mismatch: " + this);
    }
    Scope newScope = new BasicScope(scope);
    for (int i = 0; i < formalArgs.size(); i++) {
      newScope.setVarLocal(formalArgs.get(i), argsValues.get(i));
    }
    newScope.setVarLocal("this", thisRef);
    body.eval(newScope);
    return thisRef;
  }

  @Override
  public Type call(MPObject thisRef, List<Type> argsValues, Map<String, Type> optsValues) throws EvalException {
    thisRef = new MPObject(scope, prototype);
    if (formalArgs.size() != argsValues.size()) {
      throw new EvalException("Argument mismatch: " + this);
    }

    Scope newScope = new BasicScope(scope);
    for (int i = 0; i < formalArgs.size(); i++) {
      newScope.setVarLocal(formalArgs.get(i), argsValues.get(i));
    }

    MPObject opts = new MPObject(scope, null);
    for (String key : optsValues.keySet()) {
      opts.setVarLocal(key, optsValues.get(key));
    }

    newScope.setVarLocal("opts", opts);
    newScope.setVarLocal("this", thisRef);
    body.eval(newScope);
    return thisRef;
  }
}
