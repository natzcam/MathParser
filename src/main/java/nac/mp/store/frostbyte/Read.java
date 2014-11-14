/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.store.frostbyte;

import java.io.IOException;
import nac.mp.EvalException;
import nac.mp.MathParser;
import nac.mp.ParseException;
import nac.mp.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author user
 */
public class Read {

  private static final Logger log = LogManager.getLogger(Read.class);

  public static void main(String[] args) {
    MathParser mp = new MathParser();
    try {
      mp.eval(Util.readFile("src/main/resources/mp/read.mp"));
    } catch (IOException | EvalException | ParseException ex) {
      log.error("Parse/Eval failed", ex);
    }
  }
}
