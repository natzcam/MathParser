/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.store.mysql;

import nac.mp.ParseException;
import nac.mp.ast.statement.OneToManyDecl;
import nac.mp.store.Emittable;

/**
 *
 * @author camomon
 */
public class MySQLOneToMany implements Emittable {

  private final OneToManyDecl oneToMany;

  public MySQLOneToMany(OneToManyDecl oneToMany) throws ParseException {
    this.oneToMany = oneToMany;
  }

  @Override
  public void emit(StringBuilder query) {
//    if (oneToMany.isBidi()) {
//      query.append("ALTER TABLE ").append(oneToMany.getRightModel().getName());
//      query.append("\n");
//      query.append("ADD CONSTRAINT ").append(oneToMany.getRightModel().getName()).append("_").append(oneToMany.getRightAttr().getIdentifier());
//      query.append("_").append(oneToMany.getLeftModel().getName()).append("_").append(oneToMany.getLeftAttr().getIdentifier());
//      query.append("\n");
//      query.append("FOREIGN KEY (").append(oneToMany.getRightAttr().getIdentifier()).append(")");
//      query.append("\n");
//      query.append("REFERENCES ").append(oneToMany.getLeftModel().getName()).append("(id);");
//    }
  }

}
