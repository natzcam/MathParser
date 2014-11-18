/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp;

import nac.mp.type.MPList;
import nac.mp.type.MPModel;
import nac.mp.type.MPModelObj;
import nac.mp.type.QueryPredicate;

/**
 *
 * @author user
 */
public interface ObjectStore {

  public void register(MPModel model) throws EvalException;

  public void save(MPModelObj mpObj);

  public MPList select(MPModel model, QueryPredicate predicate) throws EvalException;

  public void close();
}
