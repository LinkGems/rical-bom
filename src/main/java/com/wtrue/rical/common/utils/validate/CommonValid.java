package com.wtrue.rical.common.utils.validate;

import com.alibaba.fastjson.JSON;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/1/19 10:26 AM
 */
class CommonValid extends InitValid{

    /**
     * 如果A的值为B，那么C的值必须为D
     * @param fieldNameA
     * @param expectValueB
     * @param fieldNameC
     * @param expectValueD
     * @return
     */
    public CommonValid ifAIsBThenCMustD(String fieldNameA, Object expectValueB, String fieldNameC, Object expectValueD){
        // assert
        if(!this.valid){
            return this;
        }
        // main
        if(JSON.toJSONString(getMapValue(fieldNameA)).equals(JSON.toJSONString(expectValueB))
                && !JSON.toJSONString(getMapValue(fieldNameC)).equals(JSON.toJSONString(expectValueD))){
            populateError("%s is %s, but %s is not %s", fieldNameA, JSON.toJSONString(expectValueB), fieldNameC, JSON.toJSONString(expectValueD));
        }
        return this;
    }

    /**
     * 如果A的值不为B，那么C的值必须为D
     * @param fieldNameA
     * @param expectValueB
     * @param fieldNameC
     * @param expectValueD
     * @return
     */
    public CommonValid ifAIsNotBThenCMustD(String fieldNameA, Object expectValueB, String fieldNameC, Object expectValueD){
        // assert
        if(!this.valid){
            return this;
        }
        // main
        if(!JSON.toJSONString(getMapValue(fieldNameA)).equals(JSON.toJSONString(expectValueB))
                && !JSON.toJSONString(getMapValue(fieldNameC)).equals(JSON.toJSONString(expectValueD))){
            populateError("%s is not %s, but %s is not %s", fieldNameA, JSON.toJSONString(expectValueB), fieldNameC, JSON.toJSONString(expectValueD));
        }
        return this;
    }

    /**
     * 如果A的值为B，那么C的值必须不能为D
     * @param fieldNameA
     * @param expectValueB
     * @param fieldNameC
     * @param expectValueD
     * @return
     */
    public CommonValid ifAIsBThenCMustNotD(String fieldNameA, Object expectValueB, String fieldNameC, Object expectValueD){
        // assert
        if(!this.valid){
            return this;
        }
        // main
        if(JSON.toJSONString(getMapValue(fieldNameA)).equals(JSON.toJSONString(expectValueB))
                && JSON.toJSONString(getMapValue(fieldNameC)).equals(JSON.toJSONString(expectValueD))){
            populateError("%s is %s, but %s is %s", fieldNameA, JSON.toJSONString(expectValueB), fieldNameC, JSON.toJSONString(expectValueD));
        }
        return this;
    }

    /**
     * 如果A的值为B，那么C的值必须不能为D
     * @param fieldNameA
     * @param expectValueB
     * @param fieldNameC
     * @param expectValueD
     * @return
     */
    public CommonValid ifAIsNotBThenCMustNotD(String fieldNameA, Object expectValueB, String fieldNameC, Object expectValueD){
        // assert
        if(!this.valid){
            return this;
        }
        // main
        if(!JSON.toJSONString(getMapValue(fieldNameA)).equals(JSON.toJSONString(expectValueB))
                && JSON.toJSONString(getMapValue(fieldNameC)).equals(JSON.toJSONString(expectValueD))){
            populateError("%s is not %s, but %s is %s", fieldNameA, JSON.toJSONString(expectValueB), fieldNameC, JSON.toJSONString(expectValueD));
        }
        return this;
    }
}
