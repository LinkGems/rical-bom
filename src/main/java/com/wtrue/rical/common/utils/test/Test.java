package com.wtrue.rical.common.utils.test;

import com.alibaba.fastjson.JSON;
import com.wtrue.rical.common.utils.validate.ValidUtil;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/1/20 8:34 PM
 */
public class Test {

    public static void main(String[] args) {
        Kobe kobe = new Kobe();
        kobe.setRun("mamba");
        Goal goal = new Goal();
        goal.setType(2);
        goal.setStep(2);
        kobe.setGoal(goal);
        ValidUtil validUtil = new ValidUtil("k", () -> kobe)
                .notNull("run", "goal")
                .sub("goal")
                .notNull("type","step")
                .supSub("goal")
                .ifAIsBThenCMustD("type", 2, "step", 3);
        System.out.println(JSON.toJSONString(validUtil));
    }
}
