/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.store.frostbyte;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import nac.mp.type.MPModelObject;
import org.h2.mvstore.DataUtils;
import org.h2.mvstore.WriteBuffer;
import org.h2.mvstore.type.DataType;

/**
 *
 * @author user
 */
class MPModelObjectDataType implements DataType {

  private final Kryo kryo;

  public MPModelObjectDataType(Kryo kryo) {
    this.kryo = kryo;
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
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    try (Output output = new Output(os)) {
      kryo.writeObject(output, obj);
      output.flush();
    }
    byte[] data = os.toByteArray();
    buff.putVarInt(data.length);
    buff.put(data);

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
    System.out.println("test:  =" + len);
    byte[] data = DataUtils.newBytes(len);
    buff.get(data);
    Input input = new Input(data);
    return kryo.readObject(input, MPModelObject.class);
  }

  @Override
  public void read(ByteBuffer buff, Object[] obj, int len, boolean key) {
    for (int i = 0; i < len; i++) {
      obj[i] = read(buff);
    }
  }

}
