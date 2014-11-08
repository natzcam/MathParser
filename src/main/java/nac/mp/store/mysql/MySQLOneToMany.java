/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.store.mysql;

import java.util.Map;
import nac.mp.ParseException;
import nac.mp.ast.Expression;
import nac.mp.ast.expression.MemberExpr;
import nac.mp.ast.expression.VarExpr;
import nac.mp.ast.statement.AttributeDecl;
import nac.mp.ast.statement.ModelDecl;
import nac.mp.ast.statement.OneToManyDecl;
import nac.mp.store.Emittable;

/**
 *
 * @author camomon
 */
public class MySQLOneToMany implements Emittable {

  private final OneToManyDecl oneToMany;
  private final Map<String, ModelDecl> modelRepo;
  private final ModelDecl leftModel;
  private final AttributeDecl leftAttr;
  private final ModelDecl rightModel;
  private AttributeDecl rightAttr = null;
  private boolean bidi = true;

  public MySQLOneToMany(Map<String, ModelDecl> modelRepo, OneToManyDecl oneToMany) throws ParseException {
    this.modelRepo = modelRepo;
    this.oneToMany = oneToMany;

    MemberExpr l = oneToMany.getLeft();
    String l1 = ((VarExpr) l.getLeft()).getId();
    String l2 = l.getId();
    leftModel = modelRepo.get(l1);
    leftAttr = leftModel.getAttrDecl(l2);

    Expression re = oneToMany.getRight();
    if (re instanceof MemberExpr) {
      MemberExpr r = (MemberExpr) re;
      String r1 = ((VarExpr) r.getLeft()).getId();
      String r2 = r.getId();
      rightModel = modelRepo.get(r1);
      rightAttr = rightModel.getAttrDecl(r2);
    } else {
      VarExpr r = (VarExpr) re;
      rightModel = modelRepo.get(r.getId());
      bidi = false;
    }
  }

  public boolean isBidi() {
    return bidi;
  }

  @Override
  public void emit(StringBuilder query) {
    if (isBidi()) {
      query.append("ALTER TABLE ").append(rightModel.getName());
      query.append("\n");
      query.append("ADD CONSTRAINT ").append(rightModel.getName()).append("_").append(rightAttr.getIdentifier());
      query.append("_").append(leftModel.getName()).append("_").append(leftAttr.getIdentifier());
      query.append("\n");
      query.append("FOREIGN KEY (").append(rightAttr.getIdentifier()).append(")");
      query.append("\n");
      query.append("REFERENCES ").append(leftModel.getName()).append("(id);");
    }
  }

}
