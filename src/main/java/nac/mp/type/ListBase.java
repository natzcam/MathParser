/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type;

import nac.mp.ObjectStore;

/**
 *
 * @author user
 */
public interface ListBase {

  public void set(MPObject index, MPObject elem);

  public void add(MPObject obj);

  public MPObject get(MPObject index, ObjectStore store);
}
