package com.seahouse.compoment.utils.dao.po;

import javax.persistence.ParameterMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <ul>
 * <li>文件名称: StoredProcedurePO</li>
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
public class StoredProcedurePO {
    private String procedureName;
    private final List<StoredProcedurePO.Request> requestParams = new ArrayList(4);
    private Map<String, Object> outPuts;
    private List<Object[]> resultList;

    public StoredProcedurePO(String procedureName) {
        this.procedureName = procedureName;
    }

    public void addInParam(Object value) {
        this.requestParams.add(new StoredProcedurePO.Request(value));
    }

    public void addInOutParam(Object value, String outParamName) {
        this.requestParams.add(new StoredProcedurePO.Request(value, ParameterMode.INOUT, outParamName));
    }

    public void addOutParam(Class<?> paramClass, String outParamName) {
        this.requestParams.add(new StoredProcedurePO.Request(paramClass, outParamName));
    }

    public List<StoredProcedurePO.Request> getRequestParams() {
        return this.requestParams;
    }

    public String getProcedureName() {
        return this.procedureName;
    }

    public Map<String, Object> getOutPuts() {
        return this.outPuts;
    }

    public void setOutPuts(Map<String, Object> outPuts) {
        this.outPuts = outPuts;
    }

    public List<Object[]> getResultList() {
        return this.resultList;
    }

    public void setResultList(List<Object[]> resultList) {
        this.resultList = resultList;
    }

    public class Request {
        private Object value;
        private Class<?> paramClass;
        private ParameterMode parameterMode;
        private String outParamName;

        public Request(Object value) {
            this.value = value;
            this.parameterMode = ParameterMode.IN;
        }

        public Request(Object value, ParameterMode parameterMode, String outParamName) {
            this.value = value;
            this.parameterMode = ParameterMode.INOUT;
            this.outParamName = outParamName;
        }

        public Request(Class<?> paramClass, String outParamName) {
            this.paramClass = paramClass;
            this.parameterMode = ParameterMode.OUT;
            this.outParamName = outParamName;
        }

        public Object getValue() {
            return this.value;
        }

        public Class<?> getParamClass() {
            return this.paramClass;
        }

        public ParameterMode getParameterMode() {
            return this.parameterMode;
        }

        public String getOutParamName() {
            return this.outParamName;
        }
    }
}
