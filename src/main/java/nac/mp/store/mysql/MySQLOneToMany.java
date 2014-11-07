/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.store.mysql;

import java.util.Map;
import nac.mp.ast.statement.AttributeDecl;
import nac.mp.ast.statement.ModelDecl;
import nac.mp.ast.statement.RelDecl;
import nac.mp.store.Emittable;

/**
 *
 * @author camomon
 */
public class MySQLOneToMany implements Emittable {

  private RelDecl relDecl;
  private Map<String, ModelDecl> modelRepo;
  private AttributeDecl left;
  private AttributeDecl right;

  public MySQLOneToMany(Map<String, ModelDecl> modelRepo, RelDecl relDecl) {
    this.modelRepo = modelRepo;
    this.relDecl = relDecl;
    
    relDecl.getLeft()
//    MemberExp
    left = modelRepo.get(relDecl.getLeft().)
  }

  @Override
  public void emit(StringBuilder query) {
   
  }

}
