package com.lbpan.demo;

import com.myspace.membership.DrowLottery;
import org.drools.core.common.DefaultFactHandle;
import org.kie.api.KieServices;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

import java.util.Collection;

public class Test {
    public static void main(String[] args) {
        KieServices ks = KieServices.Factory.get();
        ReleaseId releaseId = ks.newReleaseId("com.nfsq", "membership", "1.0.0");
        KieContainer kContainer = ks.newKieContainer(releaseId);
        KieSession kSession = kContainer.newKieSession();

        DrowLottery drowLottery = new DrowLottery();
        drowLottery.setDrawLimit(0);
//
        DrowLottery drowLottery2 = new DrowLottery();
        drowLottery2.setDrawLimit(4);
        FactHandle insert = kSession.insert(drowLottery);
        kSession.insert(drowLottery2);
        int i = kSession.fireAllRules();
        DrowLottery drowLottery1 = new DrowLottery();
        Collection<DefaultFactHandle> factHandles = kSession.getFactHandles();
        for (DefaultFactHandle factHandle : factHandles) {
            DrowLottery object = (DrowLottery) factHandle.getObject();
            System.out.println(object.getSendFlag());
        }
        kSession.dispose();

        System.out.println(drowLottery.getSendFlag());
        System.out.println(drowLottery1.getSendFlag());
//        System.out.println(drowLottery2.getId());
    }

}
