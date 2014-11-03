/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 *
 * @author user
 */
@RunWith(Parameterized.class)
public class MPTestPositive {

  private static final Logger log = LogManager.getLogger(MPTestPositive.class);
  private static final String POSITIVE_DIR = "src/test/mp/positive";
  private final Path file;

  public MPTestPositive(Path file) {
    this.file = file;
  }

  @Parameters
  public static Collection<Object[]> data() throws IOException {
    Collection<Object[]> data = new ArrayList<>();
    try (DirectoryStream<Path> ds = Files.newDirectoryStream(FileSystems.getDefault().getPath(POSITIVE_DIR))) {
      for (Path p : ds) {
        data.add(new Object[]{p});
      }
    }
    return data;
  }

  @Test
  public void testPositive() throws ParseException, EvalException, IOException {
    MathParser mp = new MathParser();
    mp.eval(Util.readFile(file));
  }
}
