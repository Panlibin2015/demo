package com.lbpan.demo;

import com.ql.util.express.Operator;

public class MaxIssueOperator extends Operator {
    @Override
    public Object executeInner(Object[] objects) throws Exception {
        System.out.println(objects.toString());
        return 1;
    }
}
