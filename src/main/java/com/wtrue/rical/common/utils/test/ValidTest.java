package com.wtrue.rical.common.utils.test;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/1/19 10:51 AM
 */
public class ValidTest {

    public void test(){
        Kobe kobe = new Kobe();
        kobe.setRun("mamba");
        Goal goal = new Goal();
        goal.setType(2);
        goal.setStep(2);
        kobe.setGoal(goal);

//        FuncValid funcValid = new FuncValid("k", () -> kobe)
//                .notNull("run")
//                .sub("goal")
//                .ifAIsBThenCMustD("type", 2, "step", 3);

//        FuncValid k = new ValidUtil().getProxy("k", () -> kobe);
//        k.notNull("run")
//                .sub("goal")
//                .ifAIsBThenCMustD("type", 2, "step", 3);
//
////        System.out.println(funcValid.toString());
////        System.out.println(funcValid.getError());
//        System.out.println(k.getError());
    }

    public static void main(String[] args) {
        final ValidTest validTest = new ValidTest();
        validTest.test();
    }
}
