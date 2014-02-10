/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import nac.mp.EvalException;
import nac.mp.Type;
import nac.mp.BasicScope;
import nac.mp.Scope;
import nac.mp.ast.Block;

/**
 *
 * @author nathaniel
 */
public class MPFunc extends Type {

  protected final Scope scope;
  protected final Block body;
  protected final List<String> formalArgs = new ArrayList<>();

  public MPFunc(Scope scope, Block body) {
    this.scope = scope;
    this.body = body;
  }

  public Block getBody() {
    return body;
  }

  public List<String> getFormalArgs() {
    return formalArgs;
  }

  @Override
  public Type.Hint getHint() {
    return Type.Hint.FUNCTION;
  }

  @Override
  public String toString() {
    return "func:" + this.hashCode();
  }

  public Type call(MPObject thisRef, List<Type> argsValues) throws EvalException {
    if (formalArgs.size() != argsValues.size()) {
      throw new EvalException("Argument mismatch: " + this);
    }
    Scope newScope = new BasicScope(scope);
    for (int i = 0; i < formalArgs.size(); i++) {
      newScope.setVarLocal(formalArgs.get(i), argsValues.get(i));
    }
    newScope.setVarLocal("this", thisRef);
    return body.eval(newScope);
  }

  public Type call(MPObject thisRef, List<Type> argsValues, Map<String, Type> optsValues) throws EvalException {
    if (formalArgs.size() != argsValues.size()) {
      throw new EvalException("Argument mismatch: " + this);
    }

    Scope newScope = new BasicScope(scope);
    for (int i = 0; i < formalArgs.size(); i++) {
      newScope.setVarLocal(formalArgs.get(i), argsValues.get(i));
    }

    MPObject opts = new MPObject(scope);
    for (String key : optsValues.keySet()) {
      opts.setVarLocal(key, optsValues.get(key));
    }

    newScope.setVarLocal("opts", opts);
    newScope.setVarLocal("this", thisRef);
    return body.eval(newScope);
  }

  @Override
  public Type equal(Type right) {
    switch (right.getHint()) {
      case FUNCTION:
        return new MPBoolean(this == right);
    }
    return new MPBoolean(false);
  }

  @Override
  public Type notEqual(Type right) {
    switch (right.getHint()) {
      case FUNCTION:
        return new MPBoolean(this != right);
    }
    return new MPBoolean(true);
  }
}
