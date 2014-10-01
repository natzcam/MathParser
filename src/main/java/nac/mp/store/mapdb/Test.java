/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.store.mapdb;

import java.util.NavigableSet;
import org.mapdb.BTreeKeySerializer;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Fun;

/**
 *
 * @author camomon
 */
public class Test {

  public static void main(String[] args) {

    DB db = DBMaker.newMemoryDB().make();

        // this is wrong, do not do it !!!
    //  Map<String,List<Long>> map
    //correct way is to use composite set, where 'map key' is primary key and 'map value' is secondary value
    NavigableSet<Fun.Tuple2<String, Integer>> multiMap = db.getTreeSet("test");

        //optionally you can use set with Delta Encoding. This may save lot of space
    multiMap.add(new Fun.Tuple2("aa", 1));
    multiMap.add(new Fun.Tuple2("aa", 2));
    multiMap.add(new Fun.Tuple2("aa", 3));
    multiMap.add(new Fun.Tuple2("bb", 1));

    //find all values for a key
    for (Integer l : Fun.filter(multiMap, "aa")) {
      System.out.println("value for key 'aa': " + l);
    }

        //check if pair exists
    boolean found = multiMap.contains(new Fun.Tuple2("bb", 1));
    System.out.println("Found: " + found);

    db.close();

  }
}
