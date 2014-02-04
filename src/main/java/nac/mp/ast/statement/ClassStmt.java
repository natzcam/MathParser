/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.statement;

import java.util.ArrayList;
import java.util.List;
import nac.mp.EvalException;
import nac.mp.Scope;
import nac.mp.Type;
import nac.mp.ast.Declaration;
import nac.mp.ast.Expression;
import nac.mp.ast.Statement;

/**
 *
 * @author camomon
 */
public class ClassStmt implements Statement {

  private final String name;
  private Expression prototype;
  private final List<Declaration> declarations = new ArrayList<>();

  public ClassStmt(String name) {
    this.name = name;
  }

  public void setPrototype(Expression prototype) {
    this.prototype = prototype;
  }

  public List<Declaration> getDeclarations() {
    return declarations;
  }

  @Override
  public Type eval(Scope scope) throws EvalException {
    return null;
  }
}
