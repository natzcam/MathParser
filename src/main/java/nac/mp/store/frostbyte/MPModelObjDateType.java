/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.store.frostbyte;

import java.nio.ByteBuffer;
import nac.mp.type.MPModel;
import org.h2.mvstore.DataUtils;
import org.h2.mvstore.WriteBuffer;
import org.h2.mvstore.type.DataType;

/**
 *
 * @author user
 */
class MPModelObjDateType implements DataType {

  private MPModel model;

  public MPModelObjDateType(MPModel model) {
    this.model = model;
  }

  @Override
  public int compare(Object aObj, Object bObj) {
    return 0;
  }

  @Override
  public int getMemory(Object obj) {
    return 10000;
  }

  @Override
  public void write(WriteBuffer buff, Object obj) {

  }

  @Override
  public void write(WriteBuffer buff, Object[] obj, int len, boolean key) {
    for (int i = 0; i < len; i++) {
      write(buff, obj[i]);
    }
  }

  @Override
  public Object read(ByteBuffer buff) {
    int len = DataUtils.readVarInt(buff);
    byte[] data = DataUtils.newBytes(len);
    return null;
  }

  @Override
  public void read(ByteBuffer buff, Object[] obj, int len, boolean key) {
    for (int i = 0; i < len; i++) {
      obj[i] = read(buff);
    }
  }

}
