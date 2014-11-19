/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nac.mp.type;

import nac.mp.EvalException;
import nac.mp.ObjectStore;
import nac.mp.ast.Scope;

/**
 *
 * @author user
 */
public interface Creator {

  MPObject newInstance(ObjectStore store) throws EvalException;
  
}
