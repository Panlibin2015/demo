package com.lbpan.demo;

import com.ql.util.express.Operator;

public class LimitOperator extends Operator {
    @Override
    public Object executeInner(Object[] objects) throws Exception {
        Integer fact = (Integer) objects[0];
        Integer expect = (Integer) objects[1];

        if (fact < expect) {
            System.out.println("可以领取");
            return true;
        } else {
            return false;
        }
    }
}
