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
  final String parent;

  public Token(TokenType type, String text, String parent, int start, int end) {
    this.type = type;
    this.text = text;
    this.parent = parent;
    this.start = start;
    this.end = end;
  }

  @Override
  public String toString() {
    String pre = parent.substring(start - 10 < 0 ? 0 : start - 10, start);
    String post = parent.substring(end, end + 10 > parent.length() ? parent.length() : end + 10);
    String vicinity = pre + "=> " + text + " <=" + post;
    vicinity = vicinity.replaceAll("\\r", " ").replaceAll("\\n", " ");

    return type + " \"" + vicinity + "\"";
  }
}
