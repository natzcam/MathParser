/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.statement;

import nac.mp.ast.Statement;
import nac.mp.Type;
import nac.mp.Scope;

/**
 *
 * @author nathaniel
 */
public class Exit implements Statement {

  @Override
  public Type eval(Scope scope) {
    System.exit(0);
    return null;
  }
}
