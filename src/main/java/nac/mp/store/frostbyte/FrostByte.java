/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.store.frostbyte;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import java.io.ByteArrayOutputStream;
import java.util.Collection;
import nac.mp.ObjectStore;
import nac.mp.EvalException;
import nac.mp.MathParser;
import nac.mp.ParseException;
import nac.mp.type.MPAttribute;
import nac.mp.type.natv.MPInteger;
import nac.mp.type.MPList;
import nac.mp.type.MPModel;
import nac.mp.type.MPModelObj;
import nac.mp.type.QueryPredicate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.h2.mvstore.MVMap;
import org.h2.mvstore.MVStore;
import org.h2.mvstore.type.ObjectDataType;

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
  private final MVStore modelDB;
  private final MVStore objectDB;
  private final MVStore indexDB;
  private final Kryo kryo = new Kryo();

  public FrostByte() {
    modelDB = MVStore.open(FILE_MODEL);
    objectDB = MVStore.open(FILE_OBJECT);
    indexDB = MVStore.open(FILE_INDEX);
  }

  @Override
  public void register(MPModel model) throws EvalException {
    MVMap<String, MPModel> modelMap = modelDB.openMap(APPEND_MODEL);
    for (MPAttribute ad : model.getAttributes().values()) {
      final String attrName = ad.getName();
      if (attrName.equals("id")) {
        throw new EvalException("Can't define custom id property.", model);
      }
    }
    modelMap.put(model.getName(), model);
    modelDB.commit();
  }

  @Override
  public Collection<MPModel> getModels() {
    MVMap<String, MPModel> modelMap = modelDB.openMap(APPEND_MODEL);
    return modelMap.values();
  }

  private MVMap<Long, MPModelObj> getObjectMap(MPModel model) {
    MVMap.Builder<Long, MPModelObj> builder = new MVMap.Builder<>();
    builder.valueType(new MPModelObjDateType(model));
    return objectDB.openMap(model.getName() + APPEND_MODEL, builder);
  }

  @Override
  public void save(MPModelObj obj) {

    MPModel model = obj.getModel();
    MPInteger id = obj.getId();

    MVMap<Long, MPModelObj> objectMap = getObjectMap(model);

    if (id == null) {
//      Atomic.Long keyinc = objectDB.getAtomicLong(model.getName() + APPEND_SEQUENCE);
//      Long key = keyinc.incrementAndGet();
      id = new MPInteger(System.currentTimeMillis());
      obj.setVar("id", id);
    }
    for (MPAttribute attr : model.getAttributes().values()) {
      if (attr.getName().equals("id")) {
        continue;
      }

//      if (attr.getType() == Type.REF) {
//        MPModelObj mo = (MPModelObj) obj.getVar(attr.getName());
//        obj.setVar(attr.getName(), mo.getReference());
//      } else if (attr.getType() == Type.LIST) {
//        MPList ml = (MPList) obj.getVar(attr.getName());
//        List<MPReference> refList = new ArrayList<>();
//        for (MPObject mo : ml.getList()) {
//          MPModelObj mdo = (MPModelObj) mo;
//          refList.add(mdo.getReference());
//        }
//        ml.getList().clear();
//        ml.getList().addAll(refList);
//      }
    }

    objectMap.put(id.getInt(), obj);
    log.info("Save {}: {}", model.getName(), obj);
//
//    indexDB.commit();
//    objectDB.commit();
  }

  @Override
  public MPList select(MPModel model, QueryPredicate predicate) throws EvalException {
    MVMap<Long, MPModelObj> objectMap = getObjectMap(model);
    MPList result = new MPList(10, null);
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
