/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.store.mapdb;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import nac.mp.type.MPClass;
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
  public static final String META_COLL = "__meta_coll__";

  public ObjectStorage() {
    dataFile = new File("src/main/resources/data/obj.data");
    db = DBMaker.newFileDB(dataFile).closeOnJvmShutdown().make();
  }
  
  public MPInteger register(MPClass clazz){
    clazz.getDeclarations();
    return put(META_COLL, clazz);
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

  public MPObject getById(String collectionName, MPInteger id) {
    BTreeMap<Long, MPObject> omap = db.getTreeMap(collectionName);
    return omap.get(id.getInt());
  }
  
  public List<MPObject> getByProperty(){
    return new ArrayList<MPObject>();
  }
  
  public void close(){
    if(!db.isClosed()){
      db.close();
    }
  }
}
