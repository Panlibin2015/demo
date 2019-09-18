package com.lbpan.demo;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import com.ql.util.express.Operator;
import org.junit.Test;

import java.util.Arrays;

public class QLExpressLangurageTest {

    @Test
    public void demo1() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<>();
        context.put("a", 1);
        context.put("b", 2);
        context.put("c", 3);
        String express = "a+b*c";
        Object r = runner.execute(express, context, null, true, false);
        System.out.println(r);

    }

    /**
     * 函数计算
     *
     * @throws Exception
     */
    @Test
    public void function1() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<>();
        context.put("a", 1);
        String express = "function add(int a,int b){\n" +
                "  return a+b;\n" +
                "};\n" +
                "\n" +
                "function sub(int a,int b){\n" +
                "  return a - b;\n" +
                "};\n" +
                "\n" +
                "return add(a,4) + sub(a,9);";
        Object r = runner.execute(express, context, null, true, false);
        System.out.println(r);
    }

    /**
     * 扩展操作符
     *
     * @throws Exception
     */
    @Test
    public void operator() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        runner.addOperatorWithAlias("如果", "if", null);
        runner.addOperatorWithAlias("则", "then", null);
        runner.addOperatorWithAlias("否则", "else", null);

        String express = "如果(语文+数学+英语>270)则{return 1;}否则{return 0;}";

        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        context.put("语文", 270);
        context.put("数学", 1);
        context.put("英语", 1);
        Object r = runner.execute(express, context, null, true, false);
        System.out.println(r);
    }

    @Test
    public void bindMethod() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();

        runner.addFunctionOfClassMethod("取绝对值", Math.class.getName(), "abs",
                new String[]{"double"}, null);
        runner.addFunctionOfClassMethod("转换为大写", BeanExample.class.getName(),
                "upper", new String[]{"String"}, null);

        runner.addFunctionOfServiceMethod("打印", System.out, "println", new String[]{"String"}, null);
        runner.addFunctionOfServiceMethod("contains", new BeanExample(), "anyContains",
                new Class[]{String.class, String.class}, null);

        String exp = "取绝对值(-100);转换为大写(\"hello world\");打印(\"你好吗？\");contains(\"helloworld\",\"aeiou\")";
        runner.execute(exp, context, null, false, false);

    }

    @Test
    public void bindMethod2() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        runner.addOperatorWithAlias("如果", "if", null);
        runner.addOperatorWithAlias("则", "then", null);
        runner.addOperatorWithAlias("否则", "else", null);
        runner.addFunctionOfClassMethod("参与活动次数", MemberOperator.class.getName(), "joinActivity",
                new String[]{"String", "Integer"}, null);
        runner.addFunctionOfClassMethod("发放优惠券", IssueCoupon.class.getName(), "issue",
                new String[]{"String", "Integer"}, null);

        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        context.put("会员", "张三");
        context.put("活动", 10);

        String exp = "如果 " +
                "(参与活动次数(会员,活动) < 2) " + // condition
                "则 发放优惠券(会员,活动) " + // action
                "否则 " +
                "{return \"参与活动超过限制\";}"; // action
        Object execute = // 结果
                runner.execute(exp, context, null, false, false);
        System.out.println(execute);

    }

    @Test
    public void bindMethod3() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        runner.addOperatorWithAlias("如果", "if", null);
        runner.addOperatorWithAlias("则", "then", null);
        runner.addOperatorWithAlias("否则", "else", null);

        runner.addFunctionOfClassMethod("用户不是会员", MemberOperator.class.getName(), "isNotMember", new String[]{"String"}, null);
        // 会员 参与活动 领取优惠券

        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        context.put("用户openid", "kajdflasjflajsf");

        String exp = "如果 " +
                "用户不是会员(用户openid) " +// condition
                "则 " +
                "{return \"您不是会员,请注册后参与\";}";// action
        Object execute = // 结果
                runner.execute(exp, context, null, false, false);
        System.out.println(execute);

        DefaultContext<String, Object> context2 = new DefaultContext<String, Object>();
        context2.put("用户openid", "1;f");

        Object execute2 = // 结果
                runner.execute(exp, context2, null, false, false);
        System.out.println(execute2);

        // 1.开发人员:构建逻辑单元
        // 2.测试人员:验证逻辑单元
        // 3.业务:编排逻辑单元+逻辑单元数据绑定（动态绑定-来自事实上下文+静态绑定)->规则文件
        // 4.业务:选择规则模板->设置规则属性 => 业务规则
        // 5.发布规则 -> 验证规则 -> 规则上线


        // 是不是会员 ${member.memberId}

        // 活动已经结束

        // 活动未开始

        // 活动已经关闭

        // 优惠券已经发放完毕 ${issueCount}

        // 参与活动次数 ${joinTimes}

        // 每天参与次数 ${dayJoinTimes}

        // 随机发放奖项 ${generateLotteryItem}


    }

    @Test
    public void operator2() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        // 操作符号
        runner.addOperator("小于等于", new Operator() {
            @Override
            public Object executeInner(Object[] list) throws Exception {
                int a = (int) list[0];
                int b = (int) list[1];
                return a <= b;
            }
        });

        long start = System.currentTimeMillis();
        for (int i = 0; i < 50; i++) {
            boolean a = (boolean) runner.execute("3 小于等于 4 ", null, null, true, false);
        }
        long end = System.currentTimeMillis();
        System.out.println();
        System.out.println((end - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < 50; i++) {
            boolean a = 1 <= 2;
        }
        end = System.currentTimeMillis();
        System.out.println();
        System.out.println((end - start) );
    }

    public static class BeanExample {
        public static String upper(String abc) {
            return abc.toUpperCase();
        }

        public boolean anyContains(String str, String searchStr) {

            char[] s = str.toCharArray();
            for (char c : s) {
                if (searchStr.contains(c + "")) {
                    return true;
                }
            }
            return false;
        }
    }
}
