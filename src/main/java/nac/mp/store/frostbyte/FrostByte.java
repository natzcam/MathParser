/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.store.frostbyte;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import nac.mp.EvalException;
import nac.mp.ObjectStore;
import nac.mp.type.MPModel;
import nac.mp.type.MPObject;
import nac.mp.type.instance.MPInteger;
import nac.mp.type.instance.MPList;
import nac.mp.type.instance.MPModelObj;
import nac.mp.type.instance.MPRef;
import nac.mp.type.instance.MPRefList;
import nac.mp.type.instance.QueryPredicate;
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
public class FrostByte implements ObjectStore {

  private static final Logger log = LogManager.getLogger(FrostByte.class);
  private static final String FILE_MODEL = "data/model.data";
  private static final String FILE_OBJECT = "data/object.data";
  private static final String FILE_INDEX = "data/index.data";
  private static final String APPEND_MODEL = "_model";
  private static final String APPEND_SEQUENCE = "_sequence";
  private static final String APPEND_INDEX = "_index";
  private final DB modelDB;
  private final DB objectDB;
  private final DB indexDB;

  public FrostByte() {
    modelDB = DBMaker.newFileDB(new File(FILE_MODEL)).closeOnJvmShutdown().make();
    objectDB = DBMaker.newFileDB(new File(FILE_OBJECT)).closeOnJvmShutdown().make();
    indexDB = DBMaker.newFileDB(new File(FILE_INDEX)).closeOnJvmShutdown().make();
  }

  @Override
  public void register(MPModel model) {
    BTreeMap<String, MPModel> modelMap = modelDB.getTreeMap(APPEND_MODEL);
    modelMap.put(model.getName(), model);
    modelDB.commit();
  }

  @Override
  public Collection<MPModel> getModels() {
    BTreeMap<String, MPModel> modelMap = modelDB.getTreeMap(APPEND_MODEL);
    return modelMap.values();
  }

  private BTreeMap<Long, MPModelObj> getObjectMap(MPModel model) {
    return objectDB.getTreeMap(model.getName() + APPEND_MODEL);
  }

  private BTreeMap<Long, MPModelObj> getObjectMap(String name) {
    return objectDB.getTreeMap(name + APPEND_MODEL);
  }

  private Atomic.Long getSequence(MPModel model) {
    Atomic.Long keyinc = objectDB.getAtomicLong(model.getName() + APPEND_SEQUENCE);
    return keyinc;
  }

  @Override
  public void save(MPModelObj obj) {

    MPModel model = obj.getModel();
    MPObject id = obj.getId();
    System.out.println("mpobject: " + id);
    if (obj.getObjectStore() == null && id != null) {
      throw new EvalException("Id is managed by the object store for new objects", obj);
    }

    obj.setObjectStore(this);

    BTreeMap<Long, MPModelObj> objectMap = getObjectMap(model);

    if (id == null) {
      Atomic.Long keyinc = objectDB.getAtomicLong(model.getName() + APPEND_SEQUENCE);
      Long key = keyinc.incrementAndGet();
      id = new MPInteger(key);
      obj.setLocalVar("id", id);
    }

    objectMap.put(id.getInt(), obj);
    log.info("Save {}: {}", model.getName(), obj);

    objectDB.commit();
  }

  @Override
  public MPList select(MPModel model, QueryPredicate predicate) {
    BTreeMap<Long, MPModelObj> objectMap = getObjectMap(model);
    MPList result = new MPList();
    for (MPModelObj obj : objectMap.values()) {
      if (predicate.call(obj, this)) {
        obj.setObjectStore(this);
        result.add(obj);
      }
    }
    return result;
  }

  @Override
  public List<MPModelObj> select(MPRefList refList) {
    BTreeMap<Long, MPModelObj> objectMap = getObjectMap(refList.getModel());
    List<MPModelObj> result = new ArrayList<>();
    for (MPObject id : refList.getRefList()) {
      MPModelObj obj = objectMap.get(id.getInt());
      obj.setObjectStore(this);
      result.add(obj);
    }
    return result;
  }

  @Override
  public MPModelObj dereference(MPRef ref) {
    BTreeMap<Long, MPModelObj> objectMap = getObjectMap(ref.getModelName());
    MPModelObj obj = objectMap.get(ref.getId().getInt());
    obj.setObjectStore(this);
    return obj;
  }

  @Override
  public void close() {
    modelDB.close();
    objectDB.close();
    indexDB.close();
  }

}
