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

  public ParseException(String message, LinkedList<Token> history) {
    super(message + ":" + printHistory(history));
  }

  private static String printHistory(LinkedList<Token> history) {
    StringBuilder sb = new StringBuilder();
    sb.append("\n");
    for (Token token : history) {
      sb.append(token).append("\n");
    }
    return sb.toString();
  }

  public ParseException(String message) {
    super(message);
  }

  public ParseException(Throwable cause, LinkedList<Token> history) {
    super(history.toString(), cause);
  }

}
