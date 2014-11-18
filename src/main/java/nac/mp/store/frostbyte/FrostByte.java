/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.store.frostbyte;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import java.io.ByteArrayOutputStream;
import nac.mp.ObjectStore;
import nac.mp.EvalException;
import nac.mp.type.MPAttribute;
import nac.mp.type.MPInteger;
import nac.mp.type.MPList;
import nac.mp.type.MPModel;
import nac.mp.type.MPModelObject;
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
  private static final String FILE_OBJECT = "data/object.data";
  private static final String FILE_INDEX = "data/index.data";
  private static final String APPEND_MODEL = "_model";
  private static final String APPEND_SEQUENCE = "_sequence";
  private static final String APPEND_INDEX = "_index";
  private final MVStore objectDB;
  private final MVStore indexDB;
  private final Kryo kryo = new Kryo();

  public FrostByte() {
    objectDB = MVStore.open(FILE_OBJECT);
    indexDB = MVStore.open(FILE_INDEX);
  }

  @Override
  public void register(MPModel model) throws EvalException {
//    MVMap.Builder<Long, MPModel> b = new MVMap.Builder<Long, MPModel>();
//    b.keyType(new ObjectDataType()).valueType(new MPModelDataType());
//
//    MVMap<Long, MPModel> objectMap = objectDB.openMap(model.getName() + APPEND_MODEL,);
//    for (MPAttribute ad : model.getAttributes().values()) {
//      final String attrName = ad.getName();
//      if (attrName.equals("id")) {
//        throw new EvalException("Can't define custom id property.", model);
//      }
//    }
  }

  private MVMap<Long, MPModelObject> getObjectMap(MPModel model) {
    MVMap.Builder<Long, MPModelObject> builder = new MVMap.Builder<>();
    builder.valueType(new MPModelObjectDataType(kryo));
    return objectDB.openMap(model.getName() + APPEND_MODEL, builder);
  }

  @Override
  public void save(MPModelObject obj) {

    MPModel model = obj.getModel();
    MPInteger id = obj.getId();

    MVMap<Long, MPModelObject> objectMap = getObjectMap(model);

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
//        MPModelObject mo = (MPModelObject) obj.getVar(attr.getName());
//        obj.setVar(attr.getName(), mo.getReference());
//      } else if (attr.getType() == Type.LIST) {
//        MPList ml = (MPList) obj.getVar(attr.getName());
//        List<MPReference> refList = new ArrayList<>();
//        for (MPObject mo : ml.getList()) {
//          MPModelObject mdo = (MPModelObject) mo;
//          refList.add(mdo.getReference());
//        }
//        ml.getList().clear();
//        ml.getList().addAll(refList);
//      }
    }

    objectMap.put(id.getInt(), obj);
    log.info("Save {}: {}", model.getName(), obj);

    indexDB.commit();
    objectDB.commit();
  }

  @Override
  public MPList select(MPModel model, QueryPredicate predicate) throws EvalException {
    MVMap<Long, MPModelObject> objectMap = getObjectMap(model);
    MPList result = new MPList(10, null);
    for (MPModelObject obj : objectMap.values()) {
      if (predicate.call(obj)) {
        result.add(obj);
      }
    }
    return result;
  }

  @Override
  public void close() {
    objectDB.closeImmediately();
    indexDB.closeImmediately();
  }

}
