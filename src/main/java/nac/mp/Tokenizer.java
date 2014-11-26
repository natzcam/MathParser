/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp;

import java.io.File;
import java.io.FileNotFoundException;
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
  private final static Pattern PATTERN = Pattern.compile(TokenType.getAllRegex());
  private final static Map<String, TokenType> TOKEN_MAP = TokenType.getTokenMap();
  private final static TokenType[] NON_KEYWORD = TokenType.getNonKeywords();
  private final LinkedList<Token> llQueue = new LinkedList<>();
  private File currentFile = null;
  private LineNumberReader reader = null;
  private String currentLine = null;
  private Matcher matcher = null;
  private State state = State.NON_COMMENT;

  public String getCurrentLine() {
    return currentLine;
  }

  public void setCurrentFile(File file) throws ParseException {
    this.currentFile = file;
    try {
      reader = new LineNumberReader(new FileReader(this.currentFile));
    } catch (FileNotFoundException ex) {
      throw new ParseException("Source file not found.", this, ex);
    }
    currentLine = null;
    matcher = null;
    state = State.NON_COMMENT;
  }

  public File getCurrentFile() {
    return currentFile;
  }

  public Token lookahead(int l) throws ParseException {
    int size = llQueue.size();
    if (llQueue.isEmpty() || l > size) {
      for (int i = 0; i < l - size; i++) {
        llQueue.offer(moveRight());
      }
    }

    return llQueue.get(l - 1);
  }

  public Token consume() throws ParseException {
    Token t;
    if (llQueue.isEmpty()) {
      t = moveRight();
    } else {
      t = llQueue.poll();
    }
    return t;
  }

  private Token moveRight() throws ParseException {
    Token result = new Token(TokenType.EOF, null, -1, -1, -1);

    if (currentLine == null) {
      try {
        currentLine = reader.readLine();
      } catch (IOException ex) {
        Util.closeQuietly(reader);
        reader = null;
        throw new ParseException("Error reading file.", this, ex);
      }
      if (currentLine == null) {
        Util.closeQuietly(reader);
        reader = null;
        return result;
      }
      matcher = PATTERN.matcher(currentLine);
    }

    if (matcher.find()) {

      for (int i = 0; i < NON_KEYWORD.length - 1; i++) {
        String str = matcher.group(i + 1);
        //found group
        if (str != null) {

          TokenType t = NON_KEYWORD[i];

          if (t == TokenType.IDENTIFIER && TOKEN_MAP.containsKey(str)) {
            result = new Token(TOKEN_MAP.get(str), str, reader.getLineNumber(), matcher.start(), matcher.end());
          } else {
            result = new Token(t, str, reader.getLineNumber(), matcher.start(), matcher.end());
          }

        }
      }
    } else {
      currentLine = null;
      result = moveRight();
    }

    //skip comments;
    if (result.type == TokenType.COMMENTS) {
      result = moveRight();
    } else if (result.type == TokenType.COMMENTS2 && state == State.NON_COMMENT) {
      state = State.COMMENT;
      result = moveRight();
    } else if (result.type == TokenType.COMMENTS3 && state == State.COMMENT) {
      state = State.NON_COMMENT;
      result = moveRight();
    } else if (state == State.COMMENT) {
      result = moveRight();
    }
    return result;
  }

  public static enum State {

    NON_COMMENT, COMMENT
  }
}
