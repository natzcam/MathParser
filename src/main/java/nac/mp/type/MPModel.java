/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type;

import java.util.ArrayList;
import java.util.List;
import nac.mp.EvalException;
import nac.mp.Scope;
import nac.mp.store.mysql.MySQLTable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author camomon
 */
public class MPModel extends MPObject {

  private static final Logger log = LogManager.getLogger(MPModel.class);
  private final String name;
  private final List<MPAttribute> attributes = new ArrayList<>();

  public MPModel(Scope parent, String name) {
    super(parent, null);
    this.name = name;
  }

  public List<MPAttribute> getAttributes() {
    return attributes;
  }

  @Override
  public MPObject.Hint getHint() {
    return MPObject.Hint.MODEL;
  }

  @Override
  public String toString() {
    return "model:" + name;
  }

  @Override
  public MPObject notEqual(MPObject right) {
    switch (right.getHint()) {
      case MODEL:
        return new MPBoolean(this != right);
    }
    return new MPBoolean(true);
  }

  public void register() throws EvalException {
    MySQLTable table = new MySQLTable(name);
    for (MPAttribute mPAttribute : attributes) {
      table.getColumns().add(mPAttribute.column());
    }
    StringBuilder sb = new StringBuilder();
    table.emit(sb);
    log.trace(sb.toString());
  }
}
