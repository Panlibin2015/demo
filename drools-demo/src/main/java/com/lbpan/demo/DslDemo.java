package com.lbpan.demo;

import org.drools.compiler.compiler.DrlParser;
import org.drools.compiler.compiler.DroolsParserException;
import org.drools.compiler.lang.dsl.DefaultExpanderResolver;

import java.io.IOException;
import java.io.StringReader;

public class DslDemo {

    public static void main(String[] args) throws IOException, DroolsParserException {

        String dslContent = "[when] 这里有个人 = $p:Person()\n" +
                "[when] - 年龄大于 {id:\\d*} = id > {id}\n" +
                "[then] 输出 = System.out.println(\"I am fired!\")";

        String dslrContent = "rule 'test-dsl'\n" +
                "\n" +
                "when\n" +
                "    这里有个人\n" +
                "    - 年龄大于 10\n" +
                "then\n" +
                "    输出\n" +
                "end";

        DefaultExpanderResolver resolver = new DefaultExpanderResolver(new StringReader(dslContent));

        DrlParser parser = new DrlParser();

        String string = parser.getExpandedDRL(dslrContent, resolver);

        System.out.println(string);

    }
}
