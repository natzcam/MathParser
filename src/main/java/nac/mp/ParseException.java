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
    return "[line " + t.line + "] " + tv.substring(t.start - vicinity < 0 ? 0 : t.start,
            t.end + vicinity > tv.length() - 1 ? tv.length() : t.end);
  }

  public ParseException(String message, Tokenizer tokenizer, Token t) {
    super(message + ":" + System.lineSeparator() + printTokenVicinity(tokenizer, t));
  }

  public ParseException(String message, ModelDecl model) {
    super(message + ":" + System.lineSeparator() + ToStringBuilder.reflectionToString(model));
  }

  public ParseException(String message, Throwable cause) {
    super(cause);
  }

}
