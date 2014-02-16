/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type;

import java.util.List;
import java.util.Map;
import nac.mp.EvalException;

/**
 *
 * @author nathaniel
 */
public class MPJavaFunc extends MPFunc {
  
  public MPJavaFunc() {
    super(null, null);
  }
  
  @Override
  public MPObject.Hint getHint() {
    return MPObject.Hint.FUNCTION;
  }
  
  @Override
  public String toString() {
    return "func:" + this.hashCode();
  }
  
  @Override
  public MPObject call(MPObject thisRef, List<MPObject> argsValues) throws EvalException {
    return new MPInteger(Long.parseLong(((MPString) thisRef).getString()));
  }
  
  @Override
  public MPObject call(MPObject thisRef, List<MPObject> argsValues, Map<String, MPObject> optsValues) throws EvalException {
    return null;
  }
}
