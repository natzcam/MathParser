/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author natz
 */
public class Log {

  public static void error(Exception ex) {
    Logger.getLogger(MathParser.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
  }

}
