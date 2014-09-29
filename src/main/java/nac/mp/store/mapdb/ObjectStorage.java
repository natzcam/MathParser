/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.store.mapdb;

import java.io.File;
import nac.mp.type.MPInteger;
import nac.mp.type.MPObject;
import org.mapdb.Atomic;
import org.mapdb.BTreeMap;
import org.mapdb.DB;
import org.mapdb.DBMaker;

/**
 *
 * @author user
 */
public class ObjectStorage {

  private final File dataFile;
  private final DB db;

  public ObjectStorage() {
    dataFile = new File("src/main/resources/data/obj.data");
    db = DBMaker.newFileDB(dataFile).make();
  }

  public MPInteger put(String collectionName, MPObject object) {
    BTreeMap<Long, MPObject> omap = db.getTreeMap(collectionName);
    MPInteger key = (MPInteger) object.getVar("__id__");
    if (key == null) {
      Atomic.Long keyinc = db.getAtomicLong(collectionName + "keyinc");
      key = new MPInteger(keyinc.incrementAndGet());
      object.setVar("__id__", key);
    }
    omap.put(key.getInt(), object);
    db.commit();
    return key;
  }

  public MPObject get(String collectionName, MPInteger id) {
    BTreeMap<Long, MPObject> omap = db.getTreeMap(collectionName);
    return omap.get(id.getInt());
  }
  
  public void close(){
    if(!db.isClosed()){
      db.close();
    }
  }
}
