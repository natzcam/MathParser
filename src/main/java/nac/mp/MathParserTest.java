/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 *
 * @author camomon
 */
public class MathParserTest {

  private static final String positiveDir = "src/test/mp/positive";
  private static final String negativeDir = "src/test/mp/negative";

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {

    System.out.println("POSITIVE TESTING START");
    int count = 0;
    int passedCount = 0;
    try (DirectoryStream<Path> ds = Files.newDirectoryStream(FileSystems.getDefault().getPath(positiveDir))) {
      for (Path p : ds) {
        boolean passed = true;

        System.out.println("Positive Testing: " + p.getFileName());

        try {
          MathParser mp = new MathParser();
          mp.getTokenizer().setDumpTokens(false);
          mp.eval(readFile(p, Charset.forName("UTF-8")));
        } catch (IOException | EvalException | ParseException ex) {
          Log.error(ex);
          passed = false;
        }

        if (passed) {
          passedCount++;
          System.out.println("Test passed: " + p.getFileName());
        } else {
          System.out.println("Test failed: " + p.getFileName());
        }
        count++;
      }

    } catch (IOException e) {
      Log.error(e);
    }
    System.out.println("POSITIVE TESTING END " + passedCount + "/" + count);

    System.out.println("NEGATIVE TESTING START");
    count = 0;
    passedCount = 0;
    try (DirectoryStream<Path> ds = Files.newDirectoryStream(FileSystems.getDefault().getPath(negativeDir))) {
      for (Path p : ds) {
        boolean passed = false;

        System.out.println("Negative Testing: " + p.getFileName());

        try {
          MathParser mp = new MathParser();
          mp.eval(readFile(p, Charset.forName("UTF-8")));
        } catch (IOException | EvalException | ParseException ex) {
          Log.error(ex);
          passed = true;
        }

        if (passed) {
          passedCount++;
          System.out.println("Test passed");
        } else {
          System.out.println("Test failed");
        }
        count++;
      }

    } catch (IOException e) {
      Log.error(e);
    }
    System.out.println("NEGATIVE TESTING END " + passedCount + "/" + count);
  }

  private static String readFile(Path path, Charset encoding)
          throws IOException {
    byte[] encoded = Files.readAllBytes(path);
    return encoding.decode(ByteBuffer.wrap(encoded)).toString();
  }

}
