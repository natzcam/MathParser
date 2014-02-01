/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp;

/**
 *
 * @author user
 */
public abstract class Type {

  public static enum Hint {

    BOOLEAN,
    INTEGER,
    FLOAT,
    STRING,
    VOID,
    FUNCTION,
    OBJECT
  }

  public boolean getBoolean() {
    throw new UnsupportedOperationException("No boolean representation: " + getHint());
  }

  public long getInt() {
    throw new UnsupportedOperationException("No int representation: " + getHint());
  }

  public float getFloat() {
    throw new UnsupportedOperationException("No float representation: " + getHint());
  }

  public String getString() {
    throw new UnsupportedOperationException("No string representation: " + getHint());
  }

  public boolean isVoid() {
    return false;
  }

  public abstract Hint getHint();

  public Type plus(Type right) {
    throw new UnsupportedOperationException(getHint() + " + " + right.getHint() + " not supported");
  }

  public Type dash(Type right) {
    throw new UnsupportedOperationException(getHint() + " - " + right.getHint() + " not supported");
  }

  public Type star(Type right) {
    throw new UnsupportedOperationException(getHint() + " * " + right.getHint() + " not supported");
  }

  public Type slash(Type right) {
    throw new UnsupportedOperationException(getHint() + " / " + right.getHint() + " not supported");
  }

  public Type lte(Type right) {
    throw new UnsupportedOperationException(getHint() + " <= " + right.getHint() + " not supported");
  }

  public Type lt(Type right) {
    throw new UnsupportedOperationException(getHint() + " < " + right.getHint() + " not supported");
  }

  public Type mte(Type right) {
    throw new UnsupportedOperationException(getHint() + " >= " + right.getHint() + " not supported");
  }

  public Type mt(Type right) {
    throw new UnsupportedOperationException(getHint() + " > " + right.getHint() + " not supported");
  }

  public abstract Type equal(Type right);

  public abstract Type notEqual(Type right);
}
