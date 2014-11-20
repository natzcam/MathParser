/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp;

import java.util.Collection;
import java.util.List;
import nac.mp.type.instance.MPList;
import nac.mp.type.MPModel;
import nac.mp.type.instance.MPModelObj;
import nac.mp.type.instance.MPRef;
import nac.mp.type.instance.MPRefList;
import nac.mp.type.instance.QueryPredicate;

/**
 *
 * @author user
 */
public interface ObjectStore {

  public void register(MPModel model) throws EvalException;

  public Collection<MPModel> getModels();

  public void save(MPModelObj mpObj);

  public MPList select(MPModel model, QueryPredicate predicate) throws EvalException;

  public List<MPModelObj> select(MPRefList refList);
  
  public MPModelObj dereference(MPRef ref);

  public void close();
}
