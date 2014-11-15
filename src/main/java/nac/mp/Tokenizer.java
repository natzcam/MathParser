/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * Tokenizer that stores tokens in a queue and reads tokens on demand. If the
 * lookahead is past the end of the file, null will be returned
 *
 * @author natz
 */
public class Tokenizer {

  private static final Logger log = LogManager.getLogger(Tokenizer.class);
  private final static Pattern pattern = Pattern.compile(TokenType.getAllRegex());
  private final static Map<String, TokenType> tokenMap = TokenType.getTokenMap();
  private final static TokenType[] nonKeywords = TokenType.getNonKeywords();
  private final LinkedList<Token> queue = new LinkedList<>();
  private File file;
  private LineNumberReader reader;
  private Matcher matcher;

  public Tokenizer(File file) throws IOException {
    this.file = file;
    reader = new LineNumberReader(new FileReader(this.file));
    String line = reader.readLine();
    matcher = pattern.matcher(line);
  }

  public Token lookahead(int l) throws ParseException {
    int size = queue.size();
    if (queue.isEmpty() || l > size) {
      for (int i = 0; i < l - size; i++) {
        try {
          queue.offer(moveRight());
        } catch (IOException ex) {
          throw new ParseException(ex);
        }
      }
    }
    return queue.get(l - 1);
  }

  Token consume() throws ParseException {
    Token t;
    if (queue.isEmpty()) {
      try {
        t = moveRight();
      } catch (IOException ex) {
        throw new ParseException(ex);
      }
    } else {
      t = queue.poll();
    }
    log.trace(t);
    return t;
  }

  private Token moveRight() throws ParseException, IOException {
    Token result = new Token(TokenType.EOF, "EOF", -1, -1, -1);

    if (matcher.find()) {

      for (int i = 0; i < nonKeywords.length - 1; i++) {
        String str = matcher.group(i + 1);
        //found group
        if (str != null) {

          TokenType t = nonKeywords[i];

          if (t == TokenType.IDENTIFIER && tokenMap.containsKey(str)) {
            result = new Token(tokenMap.get(str), str, reader.getLineNumber(), matcher.start(), matcher.end());
          } else {
            result = new Token(t, str, reader.getLineNumber(), matcher.start(), matcher.end());
          }

        }
      }
    } else {
      String line = reader.readLine();

      if (line != null) {
        matcher = matcher.reset(line);
        result = moveRight();
      }
    }

    //skip comments;
    if (result.type == TokenType.COMMENTS || result.type == TokenType.COMMENTS2) {
      log.trace(result);
      result = moveRight();
    }
    
    return result;
  }
}
