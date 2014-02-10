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
public enum TokenType {

  VAR("var"),
  FUNC("func"),
  OBJECT("object"),
  PRINT("print"),
  INPUT("input"),
  EXIT("exit"),
  IF("if"),
  ELSE("else"),
  WHILE("while"),
  RETURN("return"),
  TRUE("true"),
  FALSE("false"),
  ASSERT("assert"),
  RESTORE("restore"),
  PERSIST("persist"),
  CLASS("class"),
  PLUS("\\+"),
  MINUS("-"),
  STAR("\\*"),
  COMMENTS("//.*"),
  COMMENTS2("/*[\\s\\S]*\\*/"),
  SLASH("/"),
  LTE("<="),
  LT("<"),
  MTE(">="),
  MT(">"),
  EQUAL("=="),
  NOT_EQUAL("!="),
  ASSIGN("="),
  LBRACE("\\{"),
  RBRACE("\\}"),
  LPAREN("\\("),
  RPAREN("\\)"),
  COLON(":"),
  COMMA(","),
  SEMICOLON(";"),
  FLOAT("\\d+\\.\\d+"),
  INT("\\d+"),
  STRING("\"[^\"]*\""),
  //TODO underscore not allowed?
  IDENTIFIER("[a-zA-Z]+\\d*[\\.[a-zA-Z]+\\d*]*"),
  EOF(null);
  private final String regex;

  TokenType(String regex) {
    this.regex = regex;
  }

  public static String getAllRegex() {
    String allRegex = "";
    boolean bar = false;
    for (TokenType t : TokenType.values()) {
      if (t.regex != null) {
        if (bar) {
          allRegex += "|";
        }
        bar = true;
        allRegex += "(" + t.regex + ")";
      }
    }
    return allRegex;
  }
}
