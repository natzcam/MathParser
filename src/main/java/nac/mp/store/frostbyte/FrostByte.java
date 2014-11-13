/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.store.frostbyte;

import java.io.File;
import java.io.IOException;
import java.util.NavigableSet;
import nac.mp.EvalException;
import nac.mp.MathParser;
import nac.mp.ParseException;
import nac.mp.Util;
import nac.mp.ast.statement.AttributeDecl;
import nac.mp.ast.statement.AttributeDecl.Type;
import nac.mp.ast.statement.ModelDecl;
import nac.mp.type.MPAttribute;
import nac.mp.type.MPInteger;
import nac.mp.type.MPList;
import nac.mp.type.MPModel;
import nac.mp.type.MPModelObject;
import nac.mp.type.MPObject;
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
    if (temp) {
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
        throw new ParseException("Can't define custom id property.");
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

    for (String k : model.getLocalVarKeys()) {
      if (k.equals("id")) {
        continue;
      }

      //create index for property
      final MPAttribute attr = (MPAttribute) model.getVar(k);
      if (attr.getType() == Type.REF) {
        MPModelObject mo = (MPModelObject) obj.getVar(attr.getName());
        save(mo);
      } else if (attr.getType() == Type.LIST) {
        MPList ml = (MPList) obj.getVar(attr.getName());
        for(MPObject mo: ml.getList()){
          MPModelObject mdo = (MPModelObject) mo;
          save(mdo);
        }
      }
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

  public static void main(String[] args) {
    MathParser mp = new MathParser();
    try {
      mp.eval(Util.readFile("src/main/resources/mp/test.mp"));
    } catch (IOException | EvalException | ParseException ex) {
      log.error("Parse/Eval failed", ex);
      return;
    }
  }

}
