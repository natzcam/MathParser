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
import java.util.ArrayList;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 *
 * @author user
 */
@RunWith(Parameterized.class)
public class MPTestNegativeEval {

  private static final String NEGATIVE_EVAL_DIR = "src/test/mp/negative/eval";
  private final Path file;

  public MPTestNegativeEval(Path file) {
    this.file = file;
  }

  @Parameters
  public static Collection<Object[]> data() throws IOException {

    Collection<Object[]> data = new ArrayList<>();
    try (DirectoryStream<Path> ds = Files.newDirectoryStream(FileSystems.getDefault().getPath(NEGATIVE_EVAL_DIR))) {
      for (Path p : ds) {
        data.add(new Object[]{p});
      }
    }
    return data;
  }

  @Test(expected = EvalException.class)
  public void testNegativeEval() throws ParseException, EvalException, IOException {
    MathParser mp = new MathParser();
    mp.eval(file);
  }
}
