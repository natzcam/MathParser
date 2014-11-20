/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp;

import nac.mp.ast.statement.ModelDecl;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 *
 * @author natz
 */
public class ParseException extends Exception {

  private static int vicinity = 50;

  private static String printTokenVicinity(Tokenizer tokenizer, Token t) {
    String tv = tokenizer.getCurrentLine();
    String ln = "[line " + t.line + "] ";
    String line = tv.substring(t.start - vicinity < 0 ? 0 : t.start,
            t.end + vicinity > tv.length() - 1 ? tv.length() : t.end);
    StringBuilder buf = new StringBuilder(tv.length());
    buf.append(System.lineSeparator());
    for (int i = 0; i < ln.length(); i++) {
      buf.append(" ");
    }
    for (int i = 0; i < line.length(); i++) {
      if (t.start == i) {
        buf.append("^");
      } else if (t.end == i) {
        buf.append("^");
      } else {
        buf.append(" ");
      }

    }
    return ln + line + buf.toString();
  }

  public ParseException(String message, Tokenizer tokenizer, Token found) {
    super(message + ": " + tokenizer.getCurrentFile() + System.lineSeparator()
            + found + " found:" + System.lineSeparator()
            + printTokenVicinity(tokenizer, found));
  }

  public ParseException(String message, Tokenizer tokenizer, TokenType expected, Token found) {
    super(message + ": " + tokenizer.getCurrentFile() + System.lineSeparator()
            + expected + " expected: " + found + " found:" + System.lineSeparator()
            + printTokenVicinity(tokenizer, found));
  }

  public ParseException(String message, Throwable cause) {
    super(message, cause);
  }

}
