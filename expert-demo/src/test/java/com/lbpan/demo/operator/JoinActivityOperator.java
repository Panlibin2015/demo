package com.lbpan.demo.operator;


import com.ql.util.express.ExpressRunner;
import com.ql.util.express.Operator;

public class JoinActivityOperator extends Operator {
    private ExpressRunner expressRunner = new ExpressRunner(true, true);
    ;

    public JoinActivityOperator(ExpressRunner expressRunner) {
        this.expressRunner = expressRunner;

    }

    public JoinActivityOperator(String aAliasName, String aName,
                                String aErrorInfo) {
        this.name = aName;
        this.aliasName = aAliasName;
        this.errorInfo = aErrorInfo;

        // operator
        // operator
    }

    @Override
    public Object executeInner(Object[] list) throws Exception {
        String who = (String) list[0];
        String activityValue = (String) list[1];

        // 处理基本校验逻辑
        Object execute = expressRunner.execute("1 > 2", null, null, false, false);
        System.out.println("内部处理结果:" + execute);
        return false;
    }
}
