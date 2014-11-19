/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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

  @Parameters
  public static Collection<Object[]> data() throws IOException {
    List<Path> files = new ArrayList<>();
    Collection<Object[]> data = new ArrayList<>();
    try (DirectoryStream<Path> ds = Files.newDirectoryStream(Paths.get(NEGATIVE_EVAL_DIR))) {

      for (Path p : ds) {
        files.add(p);
      }

      Collections.sort(files, new Comparator<Path>() {
        @Override
        public int compare(Path o1, Path o2) {
          return o1.getFileName().toString().compareTo(o2.getFileName().toString());
        }
      });
    }
    for (Path p : files) {
      data.add(new Object[]{p});
    }
    return data;
  }
  private final Path file;

  public MPTestNegativeEval(Path file) {
    this.file = file;
  }

  @Test(expected = EvalException.class)
  public void testNegativeEval() throws ParseException, EvalException {
    MathParser mp = new MathParser();
    try {
      mp.control(file);
    } finally {
      mp.cleanup();
    }
  }

}
