/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.statement;

import java.util.Scanner;
import nac.mp.EvalException;
import nac.mp.type.MPObject;
import nac.mp.ast.Scope;
import nac.mp.ast.Expression;
import nac.mp.type.MPString;

/**
 *
 * @author nathaniel
 */
public class Input implements Expression {

  private final String identifier;
  private final Scanner scanner;

  public Input(Scanner scanner, String identifier) {
    this.scanner = scanner;
    this.identifier = identifier;
  }

  @Override
  public MPObject eval(Scope scope) throws EvalException {
    if (scope.containsVar(identifier)) {
      scope.setVar(identifier, new MPString(scanner.nextLine()));
    } else {
      scope.declareLocalVar(identifier, new MPString(scanner.nextLine()));
    }
    return null;
  }
}
