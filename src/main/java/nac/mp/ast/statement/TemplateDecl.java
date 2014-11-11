/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.statement;

import java.util.ArrayList;
import java.util.List;
import nac.mp.EvalException;
import nac.mp.ast.Scope;
import nac.mp.type.MPObject;
import nac.mp.ast.Expression;
import nac.mp.type.MPTemplate;

/**
 *
 * @author camomon
 */
public class TemplateDecl implements Expression {

  private final Expression extnds;
  private final String name;
  private final List<Expression> declarations = new ArrayList<>();

  public TemplateDecl(Expression extnds, String name) {
    this.extnds = extnds;
    this.name = name;
  }

  public List<Expression> getDeclarations() {
    return declarations;
  }

  @Override
  public MPObject eval(Scope scope) throws EvalException {
    MPTemplate eclas = null;
    if (extnds != null) {
      eclas = (MPTemplate) extnds.eval(scope);
    }
    MPTemplate template = new MPTemplate(scope, name, eclas, declarations);
    scope.declareLocalVar(name, template);

    return null;
  }
}
