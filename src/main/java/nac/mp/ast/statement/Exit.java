/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.statement;

import nac.mp.ObjectStore;
import nac.mp.ast.Scope;
import nac.mp.ast.TokenAwareExpression;
import nac.mp.type.instance.MPObject;

/**
 *
 * @author nathaniel
 */
public class Exit extends TokenAwareExpression {

  @Override
  public MPObject eval(Scope scope, ObjectStore store) {
    System.exit(0);
    return null;
  }
}
