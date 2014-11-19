/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nac.mp;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;

/**
 *
 * @author user
 */
public class TestMethodHandle {
  public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, Throwable {
    MethodHandle findSetter = MethodHandles.lookup().findSetter(Test.class, "hey", String.class);
    Test test = new Test();
    findSetter.invokeExact(test, "Hello world");
  }
  
  public static class Test{
    public String hey;
  }
}
