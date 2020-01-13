package com.seahouse.compoment.utils.lucene.luceneutil;

import org.apache.lucene.search.BooleanClause;

/**
 * <ul>
 * <li>文件名称: BooleanQueryEntity</li>
 * <li>文件描述:</li>
 * <li>版权所有: 版权所有(C) 2017</li>
 * <li>公 司: 厦门云顶伟业信息技术有限公司</li>
 * <li>内容摘要:</li>
 * <li>其他说明:</li>
 * <li>创建日期:2018/5/15 0015</li>
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
public class BooleanQueryEntity {
//    查询的字段名
    private String fieldName;
    //查询字段的值
    private String value;
    /**
     * Occur.MUST：必须满足此条件，相当于    and
     *
     * Occur.SHOULD：应该满足，但是不满足也可以，相当于 or
     *
     * Occur.MUST_NOT：必须不满足。相当于  not
     *
     * 类似must  但是不参与评分    不推荐使用
     * Like {must} except that these clauses do not participate in scoring.
     * Occur.FILTER
     */
    private BooleanClause.Occur occur;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public BooleanClause.Occur getOccur() {
        return occur;
    }

    public void setOccur(BooleanClause.Occur occur) {
        this.occur = occur;
    }
}
