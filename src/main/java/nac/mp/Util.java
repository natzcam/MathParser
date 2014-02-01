/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author user
 */
public class Util {

  public static String readFile(String path, Charset encoding)
          throws IOException {
    byte[] encoded = Files.readAllBytes(Paths.get(path));
    return encoding.decode(ByteBuffer.wrap(encoded)).toString();
  }

  public static String readFileClass(Class cls, String path, Charset encoding)
          throws IOException, URISyntaxException {
    URL url = cls.getResource(path);
    byte[] encoded = Files.readAllBytes(Paths.get(url.toURI()));
    return encoding.decode(ByteBuffer.wrap(encoded)).toString();
  }
  
  public static String readFile(String path)
          throws IOException {
    byte[] encoded = Files.readAllBytes(Paths.get(path));
    return Charset.forName("UTF-8").decode(ByteBuffer.wrap(encoded)).toString();
  }
  
  public static String readFileClass(Class cls, String path)
          throws IOException, URISyntaxException {
    URL url = cls.getResource(path);
    byte[] encoded = Files.readAllBytes(Paths.get(url.toURI()));
    return Charset.forName("UTF-8").decode(ByteBuffer.wrap(encoded)).toString();
  }
  
}
