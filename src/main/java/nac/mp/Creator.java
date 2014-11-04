/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nac.mp;

import nac.mp.EvalException;
import nac.mp.type.MPObject;

/**
 *
 * @author user
 */
public interface Creator {

  MPObject create() throws EvalException;
  
}
