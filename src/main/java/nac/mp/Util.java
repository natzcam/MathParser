/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp;

import java.io.IOException;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author user
 */
public class Util {

  private static final Logger log = LogManager.getLogger(Util.class);
  
  public static String readFile(Path path)
          throws IOException {
    byte[] encoded = Files.readAllBytes(path);
    return Charset.forName("UTF-8").decode(ByteBuffer.wrap(encoded)).toString();
  }
  
  public static String readFile(String path)
          throws IOException {
    byte[] encoded = Files.readAllBytes(Paths.get(path));
    return Charset.forName("UTF-8").decode(ByteBuffer.wrap(encoded)).toString();
  }
  
  public static void closeQuietly(Reader reader) {
    if (reader != null) {
      try {
        reader.close();
      } catch (IOException ex) {
        log.error(ex);
      }
    }
  }
  
}
