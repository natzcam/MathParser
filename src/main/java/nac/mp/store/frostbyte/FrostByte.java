/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.store.frostbyte;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableSet;
import nac.mp.EvalException;
import nac.mp.ParseException;
import nac.mp.ast.statement.AttributeDecl;
import nac.mp.ast.statement.AttributeDecl.Type;
import nac.mp.ast.statement.ModelDecl;
import nac.mp.type.MPAttribute;
import nac.mp.type.MPInteger;
import nac.mp.type.MPList;
import nac.mp.type.MPModel;
import nac.mp.type.MPModelObject;
import nac.mp.type.MPObject;
import nac.mp.type.MPReference;
import nac.mp.type.QueryPredicate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mapdb.Atomic;
import org.mapdb.BTreeMap;
import org.mapdb.Bind;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Fun;

/**
 *
 * @author user
 */
public class FrostByte {

  private static final Logger log = LogManager.getLogger(FrostByte.class);
  private static final File FILE_OBJECT = new File("data/object.data");
  private static final File FILE_INDEX = new File("data/index.data");
  private static final String APPEND_MODEL = "_model";
  private static final String APPEND_SEQUENCE = "_sequence";
  private static final String APPEND_INDEX = "_index";
  private final DB objectDB;
  private final DB indexDB;

  public FrostByte(boolean temp) {
    if (false) {
      objectDB = DBMaker.newTempFileDB().make();
      indexDB = DBMaker.newTempFileDB().make();
    } else {
      objectDB = DBMaker.newFileDB(FILE_OBJECT).make();
      indexDB = DBMaker.newFileDB(FILE_INDEX).make();
    }
  }

  public void register(ModelDecl model) throws ParseException {
    BTreeMap<Long, MPModelObject> objectMap = objectDB.getTreeMap(model.getName() + APPEND_MODEL);

    for (AttributeDecl ad : model.getAttrDecls()) {
      final String attrName = ad.getIdentifier();
      if (attrName.equals("id")) {
        throw new ParseException("Can't define custom id property.", model);
      }

      //create index for property
      if (ad.isNative()) {
        NavigableSet<Fun.Tuple2<MPObject, Long>> attrIndex = indexDB.getTreeSet(model.getName() + "_" + attrName + APPEND_INDEX);

        Bind.secondaryKey(objectMap, attrIndex, new Fun.Function2<MPObject, Long, MPModelObject>() {
          @Override
          public MPObject run(Long key, MPModelObject value) {
            return value.getVar(attrName);
          }
        });
      } else {

      }

    }
  }

  public void save(MPModelObject obj) {

    MPModel model = obj.getModel();
    MPInteger id = obj.getId();

    BTreeMap<Long, MPModelObject> objectMap = objectDB.getTreeMap(model.getName() + APPEND_MODEL);

    if (id == null) {
      Atomic.Long keyinc = objectDB.getAtomicLong(model.getName() + APPEND_SEQUENCE);
      Long key = keyinc.incrementAndGet();
      id = new MPInteger(key);
      obj.setVar("id", id);
    }

    for (MPAttribute attr : model.getAttributes().values()) {
//      if (attr.getName().equals("id")) {
//        continue;
//      }
//
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
    BTreeMap<Long, MPModelObject> objectMap = objectDB.getTreeMap(modelName + APPEND_MODEL);
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
