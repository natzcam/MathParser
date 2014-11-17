/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.store.frostbyte;

import nac.mp.EvalException;
import nac.mp.ParseException;
import nac.mp.ast.statement.AttributeDecl;
import nac.mp.ast.statement.ModelDecl;
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

/**
 *
 * @author user
 */
public class FrostByte {

  private static final Logger log = LogManager.getLogger(FrostByte.class);
  private static final String FILE_OBJECT = "data/object.data";
  private static final String FILE_INDEX = "data/index.data";
  private static final String APPEND_MODEL = "_model";
  private static final String APPEND_SEQUENCE = "_sequence";
  private static final String APPEND_INDEX = "_index";
  private final MVStore objectDB;
  private final MVStore indexDB;

  public FrostByte() {
    objectDB = MVStore.open(FILE_OBJECT);
    indexDB = MVStore.open(FILE_INDEX);
  }

  public void register(ModelDecl model) throws ParseException {
    MVMap<Long, MPModelObject> objectMap = objectDB.openMap(model.getName() + APPEND_MODEL);

    for (AttributeDecl ad : model.getAttrDecls()) {
      final String attrName = ad.getIdentifier();
      if (attrName.equals("id")) {
        throw new ParseException("Can't define custom id property.", model);
      }
    }
  }

  public void save(MPModelObject obj) {

    MPModel model = obj.getModel();
    MPInteger id = obj.getId();

    MVMap<Long, MPModelObject> objectMap = objectDB.openMap(model.getName() + APPEND_MODEL);

//    if (id == null) {
//      Atomic.Long keyinc = objectDB.getAtomicLong(model.getName() + APPEND_SEQUENCE);
//      Long key = keyinc.incrementAndGet();
//      id = new MPInteger(key);
//      obj.setVar("id", id);
//    }

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

//  public MPModelObject getById(MPModel model, MPInteger id) {
//    BTreeMap<Long, MPModelObject> objectMap = objectDB.getTreeMap(model.getName() + APPEND_MODEL);
//    return objectMap.get(id.getInt());
//  }
//
//  public List<MPModelObject> getByIndexedAttr(MPModel model, String attr, MPObject value) {
//    BTreeMap<Long, MPModelObject> objectMap = objectDB.getTreeMap(model.getName() + APPEND_MODEL);
//    NavigableSet<Fun.Tuple2<MPObject, Long>> attrIndex = indexDB.getTreeSet(model.getName() + "_" + attr + APPEND_INDEX);
//
//    Iterable<Long> ids = Fun.filter(attrIndex, value);
//    List<MPModelObject> result = new ArrayList<>();
//    for (Long lid : ids) {
//      result.add(objectMap.get(lid));
//    }
//    return result;
//  }
  public MPList select(String modelName, QueryPredicate predicate) throws EvalException {
    MVMap<Long, MPModelObject> objectMap = objectDB.openMap(modelName + APPEND_MODEL);
    MPList result = new MPList(10, null);
    for (MPModelObject obj : objectMap.values()) {
      if (predicate.call(obj)) {
        result.add(obj);
      }
    }
    return result;
  }

  public void close() {
    objectDB.close();
    indexDB.close();
  }

}
