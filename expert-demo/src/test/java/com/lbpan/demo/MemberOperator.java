package com.lbpan.demo;


public class MemberOperator {

    public int joinActivity(String memberId, Integer ruleId) {
        if (ruleId > 0)
            return 3;
        else return 0;
    }

    public boolean isNotMember(String openid) {
        return openid.length() > 10 ? true : false;
    }
}
