package com.wtrue.rical.common.utils.validate;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/1/23 5:33 PM
 */
public interface IObjectValidate {

    IObjectValidate sub(String fieldName);

    IObjectValidate sup();

    IObjectValidate supSub(String fieldName);

    /**
     * 对象构建方法，需保留
     * @return
     */
    ValidateUtil valid();

    /**
     * 判断对象属性非空
     * @param fieldName
     * @return
     */
    IObjectValidate notNull(String fieldName);

    /**
     * 批量判断对象属性非空
     * @param fieldNames
     * @return
     */
    IObjectValidate notNull(String... fieldNames);

    /**
     * ifAIsBThenCMustD
     * 如果A的值为B，那么C的值必须为D
     * @param fieldNameA
     * @param expectValueB
     * @param fieldNameC
     * @param expectValueD
     * @return
     */
    IObjectValidate ifThenMust(String fieldNameA, Object expectValueB, String fieldNameC, Object expectValueD);

    /**
     * ifAIsNotBThenCMustD
     * 如果A的值不为B，那么C的值必须为D
     * @param fieldNameA
     * @param expectValueB
     * @param fieldNameC
     * @param expectValueD
     * @return
     */
    IObjectValidate ifNotThenMust(String fieldNameA, Object expectValueB, String fieldNameC, Object expectValueD);

    /**
     * ifAIsBThenCMustNotD
     * 如果A的值为B，那么C的值必须不能为D
     * @param fieldNameA
     * @param expectValueB
     * @param fieldNameC
     * @param expectValueD
     * @return
     */
    IObjectValidate ifThenMustNot(String fieldNameA, Object expectValueB, String fieldNameC, Object expectValueD);

    /**
     * ifAIsNotBThenCMustNotD
     * 如果A的值为B，那么C的值必须不能为D
     * @param fieldNameA
     * @param expectValueB
     * @param fieldNameC
     * @param expectValueD
     * @return
     */
    IObjectValidate ifNotThenMustNot(String fieldNameA, Object expectValueB, String fieldNameC, Object expectValueD);
    /**
     * ifAIsNullThenCMustD
     * 如果A的值为空，那么C的值必须为D
     * @param fieldNameA
     * @param fieldNameC
     * @param expectValueD
     * @return
     */
    IObjectValidate ifNullThenMust(String fieldNameA, String fieldNameC, Object expectValueD);

    /**
     * ifAIsNotNullThenCMustD
     * 如果A的值不为空，那么C的值必须为D
     * @param fieldNameA
     * @param fieldNameC
     * @param expectValueD
     * @return
     */
    IObjectValidate ifNotNullThenMust(String fieldNameA, String fieldNameC, Object expectValueD);

    /**
     * ifAIsBThenCMustNotNull
     * 如果A的值为B，那么C的值必须不能为空
     * @param fieldNameA
     * @param expectValueB
     * @param fieldNameC
     * @return
     */
    IObjectValidate ifThenMustNotNull(String fieldNameA, Object expectValueB, String fieldNameC);

    /**
     * ifAIsNotBThenCMustNotNull
     * 如果A的值为B，那么C的值必须不能为空
     * @param fieldNameA
     * @param expectValueB
     * @param fieldNameC
     * @return
     */
    IObjectValidate ifNotThenMustNotNull(String fieldNameA, Object expectValueB, String fieldNameC);

    /**
     * 对象属性最大值校验
     * @param fieldName
     * @param max
     * @return
     */
    IObjectValidate max(String fieldName, long max);

    /**
     * 对象属性最小值校验
     * @param fieldName
     * @param min
     * @return
     */
    IObjectValidate min(String fieldName, long min);

    /**
     * 对象属性在两值之间校验
     * @param fieldName
     * @param min
     * @param max
     * @return
     */
    IObjectValidate betweenLong(String fieldName, long min, long max);

    /**
     * 集合最大数量
     * @param fieldName
     * @param max
     * @return
     */
    IObjectValidate listMaxSize(String fieldName, long max);

    /**
     * 集合最小数量
     * @param fieldName
     * @param min
     * @return
     */
    IObjectValidate listMinSize(String fieldName, long min);
}
