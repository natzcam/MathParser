///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package nac.mp.ast.expression;
//
//import java.util.ArrayList;
//import java.util.List;
//import nac.mp.EvalException;
//import nac.mp.Scope;
//import nac.mp.ast.Expression;
//import nac.mp.type.MPClass;
//import nac.mp.type.MPFunc;
//import nac.mp.type.MPObject;
//
///**
// *
// * @author camomon
// */
//public class NewExpr implements Expression {
//
//  private final String[] path;
//  private final List<Expression> args = new ArrayList<>();
//
//  public NewExpr(String[] path) {
//    this.path = path;
//  }
//
//  public List<Expression> getArgs() {
//    return args;
//  }
//
//  @Override
//  public MPObject eval(Scope scope) throws EvalException {
//    MPClass clazz;
//    MPObject c = null;
//    if (path.length == 1) {
//      clazz = (MPClass) scope.getVar(path[0]);
//    } else {
//      c = (MPObject) scope.getVar(path[0]);
//      for (int i = 1; i < path.length - 1; i++) {
//        c = (MPObject) c.getVar(path[i]);
//      }
//      clazz = (MPClass) c.getVar(path[path.length - 1]);
//    }
//
//    List<MPObject> argValues = new ArrayList<>();
//    for (Expression exp : args) {
//      argValues.add(exp.eval(scope));
//    }
//
//    c = clazz.create();
//    MPFunc ctor = (MPFunc) c.getVar("__init__");
//    
//    if (ctor != null) {
//      ctor.call(c, argValues);
//    }
//    
//    return c;
//  }
//}
