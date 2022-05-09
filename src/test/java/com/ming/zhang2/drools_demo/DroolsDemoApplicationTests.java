package com.ming.zhang2.drools_demo;

import org.drools.core.impl.KnowledgeBaseImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.utils.KieHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DroolsDemoApplicationTests {

    @Autowired
    private TaxiFareCalculatorService taxiFareCalculatorService;

    @Test
    public void contextLoads() {
        TaxiRide taxiRide = new TaxiRide(true, new BigDecimal(1));
        TaxiFare rideFare = new TaxiFare();

        TaxiRide taxiRide2 = new TaxiRide(false, new BigDecimal(10));
        TaxiFare rideFare2 = new TaxiFare();

        BigDecimal totalCharge = taxiFareCalculatorService.calculateFare(taxiRide, rideFare);
        String totalString = totalCharge.setScale(1,BigDecimal.ROUND_UP).stripTrailingZeros().toPlainString();
        System.out.println(totalString);

        BigDecimal totalCharge2 = taxiFareCalculatorService.calculateFare(taxiRide2, rideFare2);
        String totalString2 = totalCharge2.setScale(1,BigDecimal.ROUND_UP).stripTrailingZeros().toPlainString();
        System.out.println(totalString2);
    }


    @Test
    public void test117() throws Exception {
        String drlStr="package com.ming.zhang2.drools_demo;\n" +
                "\n" +
                "import java.math.BigDecimal;\n" +
                "import org.kie.api.runtime.KieContainer;\n" +
                "import org.kie.api.runtime.KieSession;\n" +
                "\n" +
                "dialect  \"mvel\"\n" +
                "global com.ming.zhang2.drools_demo.TaxiFare rideFare\n" +
                "\n" +
                "/**\n" +
                "*  的士打表计价Drools\n" +
                "* @Author by zhengkai.blog.csdn.net\n" +
                "*/\n" +
                "rule \"Calculate Taxi Fare - Output \"\n" +
                "salience 100\n" +
                "    when\n" +
                "        $taxiRide:TaxiRide();\n" +
                "    then\n" +
                "        System.out.println(\"#公里数 : \"+$taxiRide.getDistanceInMile());\n" +
                "        System.out.println(\"#起步价 : \"+12);\n" +
                "        rideFare.setNightSurcharge(BigDecimal.ZERO);\n" +
                "        rideFare.setRideFare(BigDecimal.ZERO);\n" +
                "end\n" +
                "\n" +
                "\n" +
                "rule \"Calculate Taxi Fare - Less than three kilometers\"\n" +
                "salience 99\n" +
                "    when\n" +
                "    \t//起步价：首3公里12元;  不论白天黑夜 || distanceInMile = 3\n" +
                "        $taxiRide:TaxiRide(distanceInMile <= 3);\n" +
                "    then\n" +
                "        rideFare.setRideFare(new BigDecimal(12));\n" +
                "end\n" +
                "\n" +
                "rule \"Calculate Taxi Fare - 3 ~ 15 kilometers\"\n" +
                "salience 99\n" +
                "    when\n" +
                "    \t//续租价：超过3公里部分，每公里2.6元; 非夜间\n" +
                "        $taxiRide:TaxiRide( isNightSurcharge == false , distanceInMile > 3 , distanceInMile <= 15);\n" +
                "    then\n" +
                "\n" +
                "        BigDecimal secondFare = ($taxiRide.getDistanceInMile().subtract(new BigDecimal(3))).multiply(new BigDecimal(2.6));\n" +
                "        System.out.println(\"#续租价 : \"+secondFare);\n" +
                "        rideFare.setRideFare(new BigDecimal(12).add(secondFare));\n" +
                "        System.out.println(\"#应付金额 : \"+rideFare.getRideFare());\n" +
                "end\n" +
                "\n" +
                "rule \"Calculate Taxi Fare - 15~25 kilometers\"\n" +
                "salience 99\n" +
                "    when\n" +
                "    \t//续租价：超过3公里部分，每公里2.6元;\n" +
                "\t\t//返空费实行阶梯附加，15至25公里按照续租价加收20%\n" +
                "        $taxiRide:TaxiRide( isNightSurcharge == false , distanceInMile > 15 , distanceInMile <= 25);\n" +
                "    then\n" +
                "\n" +
                "        BigDecimal secondFare = ($taxiRide.getDistanceInMile().subtract(new BigDecimal(15))).multiply(new BigDecimal(2.6)).multiply(new BigDecimal(1.2));\n" +
                "        System.out.println(\"#续租价 : \"+secondFare);\n" +
                "        //12+13x2.6\n" +
                "        rideFare.setRideFare(new BigDecimal(45.8).add(secondFare));\n" +
                "        System.out.println(\"#应付金额 : \"+rideFare.getRideFare());\n" +
                "end";
        String drlStr2 = "package com.ming.zhang2.drools_demo;\n" +
                "\n" +
                "import java.math.BigDecimal;\n" +
                "import org.kie.api.runtime.KieContainer;\n" +
                "import org.kie.api.runtime.KieSession;\n" +
                "\n" +
                "dialect  \"mvel\"\n" +
                "global com.ming.zhang2.drools_demo.TaxiFare rideFare\n" +
                "\n" +
                "\n" +
                "\n" +
                "rule \"Calculate Taxi Fare - Output11 \"\n" +
                "salience 100\n" +
                "    when\n" +
                "        $taxiRide:TaxiRide();\n" +
                "    then\n" +
                "        System.out.println(\"#公里数 : \"+$taxiRide.getDistanceInMile());\n" +
                "        System.out.println(\"#起步价11 : \"+12);\n" +
                "        rideFare.setNightSurcharge(BigDecimal.ZERO);\n" +
                "        rideFare.setRideFare(BigDecimal.ZERO);\n" +
                "end\n" +
                "\n" +
                "\n" +
                "rule \"Calculate Taxi Fare - 25~35 kilometers222\"\n" +
                "salience 99\n" +
                "    when\n" +
                "    \t//续租价：超过3公里部分，每公里2.6元;\n" +
                "\t\t//返空费实行阶梯附加，15至25公里按照续租价加收20%\n" +
                "        $taxiRide:TaxiRide( isNightSurcharge == false , distanceInMile > 25 , distanceInMile <= 35);\n" +
                "    then\n" +
                "\n" +
                "        BigDecimal secondFare = ($taxiRide.getDistanceInMile().subtract(new BigDecimal(25))).multiply(new BigDecimal(2.6)).multiply(new BigDecimal(1.2));\n" +
                "        System.out.println(\"#续租价 : \"+secondFare);\n" +
                "        //12+13x2.6\n" +
                "        rideFare.setRideFare(new BigDecimal(45.8).add(secondFare));\n" +
                "        System.out.println(\"#应付金额222 : \"+rideFare.getRideFare());\n" +
                "end\n";
        KieHelper helper=new KieHelper();
        helper.addContent(drlStr, ResourceType.DRL);
        helper.addContent(drlStr2, ResourceType.DRL);
        KnowledgeBaseImpl kieBase =(KnowledgeBaseImpl) helper.build();
        KieSession kieSession = kieBase.newKieSession();

        TaxiRide taxiRide = new TaxiRide(true, new BigDecimal(1));
        aaa(kieSession, taxiRide);

        //移除规则
        kieBase.removeRule("com.ming.zhang2.drools_demo","Calculate Taxi Fare - Less than three kilometers");

        TaxiRide taxiRide2 = new TaxiRide(false, new BigDecimal(1));
        aaa(kieSession, taxiRide2);

        TaxiRide taxiRide3 = new TaxiRide(false, new BigDecimal(10));
        aaa(kieSession, taxiRide3);

        TaxiRide taxiRide4 = new TaxiRide(false, new BigDecimal(30));
        aaa(kieSession, taxiRide4);

        kieSession.dispose();
//        //重新添加规则
//        KnowledgeBuilder kb = KnowledgeBuilderFactory.newKnowledgeBuilder();
//        //装入规则，可以装入多个
//        kb.add(ResourceFactory.newByteArrayResource(drlStr.getBytes("utf-8")), ResourceType.DRL);
//        kieBase.addKnowledgePackages(kb.getKnowledgePackages());

//        session.fireAllRules();

    }

    private void aaa(KieSession kieSession, TaxiRide taxiRide) throws InterruptedException {
        TaxiFare rideFare = new TaxiFare();
        kieSession.setGlobal("rideFare", rideFare);
        kieSession.insert(taxiRide);
        Thread.sleep(2000);
        kieSession.fireAllRules();
        String totalString = rideFare.total().setScale(1,BigDecimal.ROUND_UP).stripTrailingZeros().toPlainString();
        System.out.println(totalString);
    }
}
