/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast;

import nac.mp.EvalException;
import nac.mp.ObjectStore;
import nac.mp.type.MPObject;

/**
 *
 * @author camomon
 */
public abstract class LValue extends TokenAwareExpression {

  public abstract void setValue(Scope scope, MPObject value, ObjectStore store) throws EvalException;
}
