/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast;

import java.io.Serializable;
import nac.mp.EvalException;
import nac.mp.type.MPObject;

/**
 *
 * @author camomon
 */
public interface Expression extends Serializable{

  public MPObject eval(Scope scope) throws EvalException;
  
  
}
