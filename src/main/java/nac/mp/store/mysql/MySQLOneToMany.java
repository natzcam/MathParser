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
  private AttributeDecl attrDecl;

  public MySQLOneToMany(Map<String, ModelDecl> modelRepo, RelDecl relDecl) {
    this.modelRepo = modelRepo;
    this.relDecl = relDecl;
    
//    MemberExp
//    attrDecl = modelRepo.get()
  }

  @Override
  public void emit(StringBuilder query) {
   
  }

}
