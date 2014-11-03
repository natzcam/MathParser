/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp;

/**
 *
 * @author natz
 */
public class Token {

  final TokenType type;
  final String text;
  final int start;
  final int end;

  public Token(TokenType type, String text, int start, int end) {
    this.type = type;
    this.text = text;
    this.start = start;
    this.end = end;
  }

  @Override
  public String toString() {
    return type + " \"" + text + "\"";
  }
}
