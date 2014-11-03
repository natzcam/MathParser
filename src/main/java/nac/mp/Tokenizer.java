/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp;

import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 *
 * @author natz
 */
public class Tokenizer {

  private static final Logger log = LogManager.getLogger(Tokenizer.class);
  private final Pattern p = Pattern.compile(TokenType.getAllRegex());
  private Matcher m;
  private final Map<String, TokenType> keywordMap = TokenType.getKeywordMap();
  private final TokenType[] nonKeywords = TokenType.getNonKeywords();
  private final LinkedList<Token> queue = new LinkedList<>();
  private String input;

  public void process(String input) {
    this.input = input;
    queue.clear();
    m = p.matcher(input);
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
    log.trace(t);
    return t;
  }

  private Token moveRight() throws ParseException {
    Token result = null;
    if (m.find()) {
      for (int i = 0; i < nonKeywords.length - 1; i++) {
        String str = m.group(i + 1);

        if (str != null) {

          TokenType t = nonKeywords[i];

          if (t == TokenType.IDENTIFIER) {
            if (keywordMap.containsKey(str)) {
              result = new Token(keywordMap.get(str), str, m.start(), m.end());
            } else {
              result = new Token(t, str, m.start(), m.end());
            }
          } else {
            result = new Token(t, str, m.start(), m.end());
          }
        }
      }
      if (result == null) {
        throw new ParseException("Invalid token next to " + m.start() + ":" + m.end());
      }
    } else {
      result = new Token(TokenType.EOF, null, 0, 0);
    }
    //skip comments;
    if (result.type == TokenType.COMMENTS || result.type == TokenType.COMMENTS2) {
      log.trace(result);
      result = moveRight();
    }
    return result;
  }
}
