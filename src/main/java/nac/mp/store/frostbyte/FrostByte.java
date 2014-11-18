/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.store.frostbyte;

import com.esotericsoftware.kryo.Kryo;
import java.io.File;
import java.util.Collection;
import nac.mp.ObjectStore;
import nac.mp.EvalException;
import nac.mp.type.MPAttribute;
import nac.mp.type.natv.MPInteger;
import nac.mp.type.MPList;
import nac.mp.type.MPModel;
import nac.mp.type.MPModelObj;
import nac.mp.type.QueryPredicate;
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
  public void register(MPModel model) throws EvalException {
    BTreeMap<String, MPModel> modelMap = modelDB.getTreeMap(APPEND_MODEL);
    for (String attrName : model.getAttributes().keySet()) {
      if (attrName.equals("id")) {
        throw new EvalException("Can't define custom id property.", model);
      }
    }
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

  @Override
  public void save(MPModelObj obj) {

    MPModel model = obj.getModel();
    MPInteger id = obj.getId();

    BTreeMap<Long, MPModelObj> objectMap = getObjectMap(model);

    if (id == null) {
      Atomic.Long keyinc = objectDB.getAtomicLong(model.getName() + APPEND_SEQUENCE);
      Long key = keyinc.incrementAndGet();
      id = new MPInteger(key);
      obj.setVar("id", id);
    }

    objectMap.put(id.getInt(), obj);
    log.info("Save {}: {}", model.getName(), obj);

    objectDB.commit();
  }

  @Override
  public MPList select(MPModel model, QueryPredicate predicate) throws EvalException {
    BTreeMap<Long, MPModelObj> objectMap = getObjectMap(model);
    MPList result = new MPList();
    for (MPModelObj obj : objectMap.values()) {
      if (predicate.call(obj)) {
        result.add(obj);
      }
    }
    return result;
  }

  @Override
  public void close() {
    modelDB.close();
    objectDB.close();
    indexDB.close();
  }

}
