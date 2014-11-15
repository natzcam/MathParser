/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp;

import java.util.LinkedList;

/**
 *
 * @author natz
 */
public class ParseException extends Exception {

  public ParseException(String message, Tokenizer tokenizer, Token t) {
    super(message + ":\n" + printTokenVicinity(tokenizer, t));
  }

  private static String printTokenVicinity(Tokenizer tokenizer, Token t) {
    String tv = tokenizer.getCurrentLine();
    return "[" + t.line + "] " + tv.substring(t.start, t.end);
  }

  public ParseException(String message) {
    super(message);
  }

  public ParseException(Throwable cause) {
    super(cause);
  }

}
