/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.store.frostbyte;

import java.nio.ByteBuffer;
import org.h2.mvstore.WriteBuffer;
import org.h2.mvstore.type.DataType;

/**
 *
 * @author user
 */
class MPModelObjectDataType implements DataType {

  public MPModelObjectDataType() {
  }

  @Override
  public int compare(Object a, Object b) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public int getMemory(Object obj) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void write(WriteBuffer buff, Object obj) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void write(WriteBuffer buff, Object[] obj, int len, boolean key) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public Object read(ByteBuffer buff) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void read(ByteBuffer buff, Object[] obj, int len, boolean key) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

}
