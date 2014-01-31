/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author natz
 */
public class Tokenizer {

  private final Pattern p = Pattern.compile(TokenType.getAllRegex());
  private Matcher m;
  private final TokenType[] all = TokenType.values();
  private final LinkedList<Token> queue = new LinkedList<>();
  private String input;
  private boolean dumpTokens = false;

  public void process(String input) {
    this.input = input;
    queue.clear();
    m = p.matcher(input);
  }

  public void setDumpTokens(boolean dumpTokens) {
    this.dumpTokens = dumpTokens;
  }

  public Token lookahead(int l) throws ParseException {
    Token la = null;
    if (!queue.isEmpty() && l <= queue.size()) {
      return queue.get(l - 1);
    } else {
      int size = queue.size();
      for (int i = 0; i < l - size; i++) {
        la = moveRight();
        queue.offer(la);
      }
    }
    return la;
  }

  Token consume() throws ParseException {
    Token t;
    if (!queue.isEmpty()) {
      t = queue.poll();
    } else {
      t = moveRight();
    }
    if (dumpTokens) {
      System.out.println(t);
    }
    return t;
  }

  private Token moveRight() throws ParseException {
    Token result = null;
    if (m.find()) {
      for (int i = 0; i < all.length - 1; i++) {
        String str = m.group(i + 1);
        TokenType t = all[i];
        if (str != null) {
          result = new Token(t, str, input, m.start(), m.end());
        }
      }
      if (result == null) {
        throw new ParseException("Invalid token next to " + m.start() + ":" + m.end());
      }
    } else {
      result = new Token(TokenType.EOF, null, "", 0, 0);
    }
    if (result.type == TokenType.COMMENTS) {
      if (dumpTokens) {
        System.out.println(result);
      }
      result = moveRight();
    }
    return result;
  }
}
