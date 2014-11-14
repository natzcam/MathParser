/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type;

import nac.mp.ast.Scope;

/**
 *
 * @author user
 */
public abstract class MPObject implements Scope {

  protected final Creator creator;
  protected Scope parent;

  public MPObject(Scope parent, Creator creator) {
    this.creator = creator;
    this.parent = parent;
  }

  public Creator getCreator() {
    return creator;
  }

  @Override
  public Scope getParent() {
    return parent;
  }

  public abstract MPObject.Hint getHint();

  public static enum Hint {

    BOOLEAN,
    INTEGER,
    FLOAT,
    STRING,
    VOID,
    FUNCTION,
    OBJECT,
    MODEL_OBJECT,
    TEMPLATE,
    LIST,
    MODEL,
    ATTRIBUTE,
    REFERENCE;
  }

  public boolean getBoolean() {
    throw new UnsupportedOperationException("No boolean representation: " + getHint());
  }

  public long getInt() {
    throw new UnsupportedOperationException("No int representation: " + getHint());
  }

  public double getFloat() {
    throw new UnsupportedOperationException("No float representation: " + getHint());
  }

  public String getString() {
    throw new UnsupportedOperationException("No string representation: " + getHint());
  }

  public boolean isVoid() {
    throw new UnsupportedOperationException("No isVoid representation: " + getHint());
  }

  public MPObject notEqual(MPObject right) {
    throw new UnsupportedOperationException(getHint() + " != " + right.getHint() + " not supported");
  }

  public MPObject plus(MPObject right) {
    throw new UnsupportedOperationException(getHint() + " + " + right.getHint() + " not supported");
  }

  public MPObject dash(MPObject right) {
    throw new UnsupportedOperationException(getHint() + " - " + right.getHint() + " not supported");
  }

  public MPObject star(MPObject right) {
    throw new UnsupportedOperationException(getHint() + " * " + right.getHint() + " not supported");
  }

  public MPObject slash(MPObject right) {
    throw new UnsupportedOperationException(getHint() + " / " + right.getHint() + " not supported");
  }

  public MPObject lte(MPObject right) {
    throw new UnsupportedOperationException(getHint() + " <= " + right.getHint() + " not supported");
  }

  public MPObject lt(MPObject right) {
    throw new UnsupportedOperationException(getHint() + " < " + right.getHint() + " not supported");
  }

  public MPObject mte(MPObject right) {
    throw new UnsupportedOperationException(getHint() + " >= " + right.getHint() + " not supported");
  }

  public MPObject mt(MPObject right) {
    throw new UnsupportedOperationException(getHint() + " > " + right.getHint() + " not supported");
  }

  public MPObject la(MPObject right) {
    throw new UnsupportedOperationException(getHint() + " && " + right.getHint() + " not supported");
  }

  public MPObject lo(MPObject right) {
    throw new UnsupportedOperationException(getHint() + " || " + right.getHint() + " not supported");
  }

  public MPObject pa(MPObject right) {
    throw new UnsupportedOperationException(getHint() + " += " + right.getHint() + " not supported");
  }
}
