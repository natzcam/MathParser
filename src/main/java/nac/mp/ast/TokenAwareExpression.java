/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast;

import java.util.List;
import nac.mp.Token;

/**
 *
 * @author user
 */
public abstract class TokenAwareExpression implements Expression {

  protected List<Token> tokens = null;

  @Override
  public void relateTokens(List<Token> tokens) {
    this.tokens = tokens;
  }

  @Override
  public String toString() {
    return "EXP:" + tokens;
  }

}
