package com.seahouse.compoment.utils.dao.po;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <ul>
 * <li>文件名称: StoredFunctionPO</li>
 * <li>文件描述:</li>
 * <li>版权所有: 版权所有(C) 2017</li>
 * <li>公 司: 厦门云顶伟业信息技术有限公司</li>
 * <li>内容摘要:</li>
 * <li>其他说明:</li>
 * <li>创建日期:2019/8/21</li>
 * </ul>
 * <ul>
 * <li>修改记录:</li>
 * <li>版 本 号:</li>
 * <li>修改日期:</li>
 * <li>修 改 人:</li>
 * <li>修改内容:</li>
 * </ul>
 *
 * @author majx
 * @version 1.0.0
 */
public class StoredFunctionPO {
    private String functionName;
    private int returnType;
    private Object returnResult;
    private List<Request> requestParams = new ArrayList();
    private Map<String, Object> outPuts;

    public StoredFunctionPO(String functionName, int returnType) {
        this.functionName = functionName;
        this.returnType = returnType;
    }

    public void addInParam(Object value) {
        this.requestParams.add(new StoredFunctionPO.Request(value, 0));
    }

    public void addInOutParam(Object value, int oracleTypes, String outParamName) {
        this.requestParams.add(new StoredFunctionPO.Request(value, oracleTypes, 1, outParamName));
    }

    public void addOutParam(int oracleTypes, String outParamName) {
        this.requestParams.add(new StoredFunctionPO.Request(oracleTypes, 2, outParamName));
    }

    public String getFunctionName() {
        return this.functionName;
    }

    public int getReturnType() {
        return this.returnType;
    }

    public List<StoredFunctionPO.Request> getRequestParams() {
        return this.requestParams;
    }

    public Object getReturnResult() {
        return this.returnResult;
    }

    public void setReturnResult(Object returnResult) {
        this.returnResult = returnResult;
    }

    public Map<String, Object> getOutPuts() {
        return this.outPuts;
    }

    public void setOutPuts(Map<String, Object> outPuts) {
        this.outPuts = outPuts;
    }

    public class Request {
        public static final int ORACLE_IN = 0;
        public static final int ORACLE_INOUT = 1;
        public static final int ORACLE_OUT = 2;
        private Object value;
        private int oracleTypes;
        private int paramType;
        private String outParamName;

        public Request(Object value, int paramType) {
            this.value = value;
            this.paramType = paramType;
        }

        public Request(int oracleTypes, int paramType, String outParamName) {
            this.oracleTypes = oracleTypes;
            this.paramType = paramType;
            this.outParamName = outParamName;
        }

        public Request(Object value, int oracleTypes, int paramType, String outParamName) {
            this.value = value;
            this.oracleTypes = oracleTypes;
            this.paramType = paramType;
            this.outParamName = outParamName;
        }

        public Object getValue() {
            return this.value;
        }

        public int getOracleTypes() {
            return this.oracleTypes;
        }

        public int getParamType() {
            return this.paramType;
        }

        public String getOutParamName() {
            return this.outParamName;
        }
    }
}
