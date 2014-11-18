/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.store.frostbyte;

import nac.mp.EvalException;
import nac.mp.MathParser;
import nac.mp.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author camomon
 */
public class RunControl {

  private static final Logger log = LogManager.getLogger(RunControl.class);

  public static void main(String[] args) {
    MathParser mp = new MathParser();
    try {
      mp.control("src/main/resources/mp/test.mp");
    } catch (EvalException | ParseException ex) {
      log.error("Parse/Eval failed", ex);
    } finally {
      mp.cleanup();
    }

  }
}
