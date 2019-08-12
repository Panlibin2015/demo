package com.lbpan.demo.qlexpress.demo01;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import com.ql.util.express.IExpressContext;
import com.ql.util.express.Operator;
import org.junit.Test;

/**
 * 操作符
 */
public class OperatorTest {

    /**
     * 不支持try{}catch{}
     * 不支持java8的lambda表达式
     * 不支持for循环集合操作for (GRCRouteLineResultDTO item : list)
     * 弱类型语言，请不要定义类型声明,更不要用Templete（Map<String,List>之类的）
     * array的声明不一样
     * min,max,round,print,println,like,in 都是系统默认函数的关键字，请不要作为变量名
     */

    /**
     * import com.ql.util.express.test.OrderQuery;
     * //系统自动会import java.lang.*,import java.util.*;
     *
     *
     * query = new OrderQuery();//创建class实例,会根据classLoader信息，自动补全类路径
     * query.setCreateDate(new Date());//设置属性
     * query.buyer = "张三";//调用属性,默认会转化为setBuyer("张三")
     * result = bizOrderDAO.query(query);//调用bean对象的方法
     * System.out.println(result.getId());//静态方法
     */

    /**
     * 支持 +,-,*,/,<,>,<=,>=,==,!=,<>【等同于!=】,%,mod【取模等同于%】,++,--,
     * in【类似sql】,like【sql语法】,&&,||,!,等操作符
     * 支持for，break、continue、if then else 等标准的程序控制逻辑
     */
    @Test
    public void basicOperator() throws Exception {
        // 基础运算
        ExpressRunner expressRunner = new ExpressRunner();
        Object execute = expressRunner.execute("1 + 5 mod 3 == 2+1", null, null, true, true);
        System.out.println(execute);

        // in 与 like
        System.out.println(expressRunner.execute("1 in ( 1,3 )", null, null, true, true));
    }

    /**
     * for 语句
     *
     * @throws Exception
     */
    @Test
    public void forOperator() throws Exception {
        // 基础运算
        ExpressRunner expressRunner = new ExpressRunner();

        // for 循环 官方给到例子有问题（无法执行）
        String exp = "int n=10;" +
                "int sum=0;" +
                "for(int i=0;i<n;i++){" +
                "sum=sum+i;" +
                "}" +
                "return sum;";
        System.out.println(expressRunner.execute(exp, null, null, true, true));
    }

    /**
     * 逻辑三元操作
     *
     * @throws Exception
     */
    @Test
    public void logicOperator() throws Exception {
        // 基础运算
        ExpressRunner expressRunner = new ExpressRunner();

        // for 循环 官方给到例子有问题（无法执行）
        String exp = "int a=1;int b=2;a > b ? a:b";
        System.out.println(expressRunner.execute(exp, null, null, true, true));
    }

    /**
     * 操作java对象
     *
     * @throws Exception
     */
    @Test
    public void javaObjectOperator() throws Exception {
        // 基础运算
        ExpressRunner expressRunner = new ExpressRunner();

        String exp = "import com.lbpan.demo.bean.demo01.JavaObject;" +
                "JavaObject javaObject = new JavaObject();" +
                "javaObject.setName(\"测试名\");" +
                "javaObject.setAge(1);" +
                "System.out.println(javaObject.toString());";
        expressRunner.execute(exp, null, null, true, true);
    }

    /**
     * function 操作
     */
    @Test
    public void functionOperator() throws Exception {
        // 基础运算
        ExpressRunner expressRunner = new ExpressRunner();

        String exp = "function add(int a,int b) {" +
                "return a+b;" +
                "};" +
                "function sub(int a,int b){" +
                "return a-b;" +
                "};" +
                "int a=10;" +
                "return add(a,4) + sub(a,9);";
        System.out.println(expressRunner.execute(exp, null, null, true, true));
    }

    /**
     * 操作符 别名处理
     */
    @Test
    public void aliasOperator() throws Exception {
        // 基础运算
        ExpressRunner runner = new ExpressRunner();

        runner.addOperatorWithAlias("如果", "if", null);
        runner.addOperatorWithAlias("则", "then", null);
        runner.addOperatorWithAlias("否则", "else", null);

        String exp = "如果  (语文+数学+英语>270) 则 {return 1;} 否则 {return 0;}";
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        System.out.println(runner.execute(exp, context, null, false, false, null));
    }

    /**
     * 扩展操作符：Operator
     */
    @Test
    public void operatorDesOperator() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        runner.addOperator("参与", new Operator() {
            @Override
            public Object executeInner(Object[] list) throws Exception {
                String who = (String) list[0];
                String activityValue = (String) list[1];
                System.out.println("参与Operator:" + who + "参与处理" + activityValue);
                return true;
            }
        });

        String exp = "if( \"小明\" 参与 \"邀请活动\" ) then {return \"参与成功\";} else {return \"参与失败\";}";
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        System.out.println(runner.execute(exp, context, null, false, false, null));
    }

    /**
     * 绑定java类或者对象的method
     */
    @Test
    public void bindJavaMethod() throws Exception {
        ExpressRunner runner = new ExpressRunner();

        runner.addFunctionOfClassMethod("取绝对值", Math.class.getName(), "abs",
                new String[]{"double"}, null);
        runner.addFunctionOfClassMethod("转换为大写", BeanExample.class.getName(),
                "upper", new String[]{"String"}, null);

        runner.addFunctionOfServiceMethod("打印", System.out, "println", new String[]{"String"}, null);
        runner.addFunctionOfServiceMethod("contains", new BeanExample(), "anyContains",
                new Class[]{String.class, String.class}, null);

        String exp = "取绝对值(-100);转换为大写(\"hello world\");打印(\"你好吗？\");contains(\"helloworld\",\"aeiou\")";
        runner.execute(exp, null, null, false, false);
    }

    /**
     * macro 宏定义
     */
    @Test
    public void macro() throws Exception {
        ExpressRunner runner = new ExpressRunner();
        runner.addMacro("计算平均成绩", "(语文+数学+英语)/3.0");
        runner.addMacro("是否优秀", "计算平均成绩>90");
        IExpressContext<String, Object> context = new DefaultContext<String, Object>();
        context.put("语文", 88);
        context.put("数学", 99);
        context.put("英语", 95);
        Object result = runner.execute("是否优秀", context, null, false, false);
        System.out.println(result);
    }

    /**
     * 查询宏定义需要到参数
     *
     * @throws Exception
     */
    @Test
    public void macroInt() throws Exception {
        String express = "int 平均分 = (语文+数学+英语+综合考试.科目2)/4.0;return 平均分";
        ExpressRunner expressRunner = new ExpressRunner(false, false);
        String[] names = expressRunner.getOutVarNames(express);
        for (String s : names) {
            System.out.println("var : " + s);
        }
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
