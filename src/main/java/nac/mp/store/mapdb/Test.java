/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.store.mapdb;

import java.util.NavigableSet;
import java.util.TreeSet;
import org.mapdb.BTreeMap;
import org.mapdb.Bind;
import org.mapdb.DBMaker;
import org.mapdb.Fun;

/**
 *
 * @author camomon
 */
public class Test {

  public static void main(String[] args) {
    
        // stores string under id
        BTreeMap<Long, String> primary = DBMaker.newTempTreeMap();


        // stores value hash from primary map
        NavigableSet<Fun.Tuple2<Integer,Long>> valueHash =
                new TreeSet<Fun.Tuple2<Integer,Long>>(); //any Set will do

        // bind secondary to primary so it contains secondary key
        Bind.secondaryKey(primary, valueHash, new Fun.Function2<Integer, Long, String>() {
            @Override
            public Integer run(Long key, String value) {
                return value.hashCode();
            }
        });


        //insert some stuff into primary
        primary.put(111L, "some value");
        primary.put(112L, "some value");

        //shot content of secondary
        System.out.println(valueHash);

        //get all keys where value hashCode is N
        Iterable<Long> ids = Fun.filter(valueHash, 1571230533);
        System.out.println(ids.iterator().next());

  }
}
