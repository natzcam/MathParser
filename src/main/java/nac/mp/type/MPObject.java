/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type;

import nac.mp.ast.Scope;
import nac.mp.type.natv.MPBoolean;

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

  public abstract Type getType();

  public boolean getBoolean() {
    throw new UnsupportedOperationException("No boolean representation: " + getType());
  }

  public long getInt() {
    throw new UnsupportedOperationException("No int representation: " + getType());
  }

  public double getFloat() {
    throw new UnsupportedOperationException("No float representation: " + getType());
  }

  public String getString() {
    throw new UnsupportedOperationException("No string representation: " + getType());
  }

  public boolean isVoid() {
    throw new UnsupportedOperationException("No isVoid representation: " + getType());
  }

  public MPObject notEqual(MPObject right) {
    return new MPBoolean(this != right);
  }

  public MPObject isEqual(MPObject right) {
    return new MPBoolean(this == right);
  }

  public MPObject plus(MPObject right) {
    throw new UnsupportedOperationException(getType() + " + " + right.getType() + " not supported");
  }

  public MPObject dash(MPObject right) {
    throw new UnsupportedOperationException(getType() + " - " + right.getType() + " not supported");
  }

  public MPObject star(MPObject right) {
    throw new UnsupportedOperationException(getType() + " * " + right.getType() + " not supported");
  }

  public MPObject slash(MPObject right) {
    throw new UnsupportedOperationException(getType() + " / " + right.getType() + " not supported");
  }

  public MPObject lte(MPObject right) {
    throw new UnsupportedOperationException(getType() + " <= " + right.getType() + " not supported");
  }

  public MPObject lt(MPObject right) {
    throw new UnsupportedOperationException(getType() + " < " + right.getType() + " not supported");
  }

  public MPObject mte(MPObject right) {
    throw new UnsupportedOperationException(getType() + " >= " + right.getType() + " not supported");
  }

  public MPObject mt(MPObject right) {
    throw new UnsupportedOperationException(getType() + " > " + right.getType() + " not supported");
  }

  public MPObject la(MPObject right) {
    throw new UnsupportedOperationException(getType() + " && " + right.getType() + " not supported");
  }

  public MPObject lo(MPObject right) {
    throw new UnsupportedOperationException(getType() + " || " + right.getType() + " not supported");
  }

  public MPObject pa(MPObject right) {
    throw new UnsupportedOperationException(getType() + " += " + right.getType() + " not supported");
  }
}
