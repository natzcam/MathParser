/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.store.frostbyte;

import java.io.File;
import java.io.IOException;
import nac.mp.EvalException;
import nac.mp.MathParser;
import nac.mp.ParseException;
import nac.mp.Util;
import nac.mp.type.MPInteger;
import nac.mp.type.MPModel;
import nac.mp.type.MPModelObject;
import nac.mp.type.MPObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mapdb.Atomic;
import org.mapdb.BTreeMap;
import org.mapdb.DB;
import org.mapdb.DBMaker;

/**
 *
 * @author user
 */
public class FrostByte {

  private static final Logger log = LogManager.getLogger(FrostByte.class);
  private static final File FILE_META = new File("data/meta.data");
  private static final File FILE_OBJECT = new File("data/object.data");
  private static final String APPEND_SEQUENCE = "_sequence";

  private final DB metaDB;
  private final DB objectDB;

  public FrostByte() {
    metaDB = DBMaker.newFileDB(FILE_META).make();
    objectDB = DBMaker.newFileDB(FILE_OBJECT).make();
  }

  public void save(MPModelObject obj) {
    BTreeMap<Long, MPModelObject> objectMap = objectDB.getTreeMap(obj.getModel().getName());
    MPInteger id = (MPInteger) obj.getVar("id");
    if (id == null) {
      Atomic.Long keyinc = objectDB.getAtomicLong(obj.getModel().getName() + APPEND_SEQUENCE);
      Long key = keyinc.incrementAndGet();
      id = new MPInteger(key);
      obj.setVar("id", id);
    }
    objectMap.put(id.getInt(), obj);
    log.info("Save: {}", obj);
    objectDB.commit();
  }

  public MPModelObject get(MPModel model, MPInteger id) {
    BTreeMap<Long, MPModelObject> objectMap = objectDB.getTreeMap(model.getName());
    return objectMap.get(id.getInt());
  }

  public void close() {
    metaDB.close();
    objectDB.close();
  }

  public static void main(String[] args) {
    MathParser mp = new MathParser();
    try {
      mp.eval(Util.readFile("src/main/resources/mp/test.mp"));
    } catch (IOException | EvalException | ParseException ex) {
      log.error(ex);
    }
    FrostByte fb = new FrostByte();
        
    MPObject x = mp.getGlobal("x");
    log.info("x {}", x);
  }

}
