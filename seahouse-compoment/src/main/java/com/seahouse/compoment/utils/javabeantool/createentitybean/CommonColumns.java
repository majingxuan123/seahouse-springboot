package com.seahouse.compoment.utils.javabeantool.createentitybean;

import java.io.Serializable;

/**
 * 公用实体类   用于生成hibernate实体类
 */
@SuppressWarnings("serial")
public class CommonColumns implements Serializable {
	private String columnname;// 列名
	private String datatype;// 数据类型
	private Long datalength;// 数据长度
	private String nullable;// 是否为空
	private Long dataprecision;// 列相关数据类型(数字(number)类型)的具体长度(有效位数)
	private Long datascale;// 小数位数
	private String comments;// 注释

	public String getColumnname() {
		return columnname;
	}

	public void setColumnname(String columnname) {
		this.columnname = columnname;
	}

	public String getDatatype() {
		return datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	public Long getDatalength() {
		return datalength;
	}

	public void setDatalength(Long datalength) {
		this.datalength = datalength;
	}

	public String getNullable() {
		return nullable;
	}

	public void setNullable(String nullable) {
		this.nullable = nullable;
	}

	public Long getDataprecision() {
		return dataprecision;
	}

	public void setDataprecision(Long dataprecision) {
		this.dataprecision = dataprecision;
	}

	public Long getDatascale() {
		return datascale;
	}

	public void setDatascale(Long datascale) {
		this.datascale = datascale;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "CommonColumns [columnname=" + columnname + ", datatype="
				+ datatype + ", datalength=" + datalength + ", nullable="
				+ nullable + ", dataprecision=" + dataprecision
				+ ", datascale=" + datascale + ", comments=" + comments + "]";
	}

}
