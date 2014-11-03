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
  MODEL("get"),
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
  CLASS("class"),
  EXTENDS("extends"),
  NEW("new"),
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
  LBRACKET("\\["),
  RBRACKET("\\]"),
  COLON(":"),
  COMMA(","),
  DOT("\\."),
  SEMICOLON(";"),
  FLOAT("\\d+\\.\\d+"),
  INT("\\d+"),
  STRING("\"[^\"]*\""),
  IDENTIFIER("[a-zA-Z_][a-zA-Z_\\d]*"),
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
