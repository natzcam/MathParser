/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast;

import nac.mp.Scope;
import nac.mp.EvalException;
import nac.mp.Type;

/**
 *
 * @author camomon
 */
public interface Expression {

  public Type eval(Scope scope) throws EvalException;
}
