package com.eipulse.generator.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.eipulse.common.core.domain.BaseEntity;
import com.eipulse.common.utils.StringUtils;

/**
 * 代碼生成業務欄位表 gen_table_column
 */
@Entity
@Table(name = "gen_table_column")
public class GenTableColumn extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 編號
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "column_id")
	private Long columnId;

	/**
	 * 歸屬表編號
	 */
	@Column(name = "table_id")
	private Long tableId;

	/**
	 * 列名稱
	 */
	@Column(name = "column_name")
	private String columnName;

	/**
	 * 列描述
	 */
	@Column(name = "column_comment")
	private String columnComment;

	/**
	 * 列類型
	 */
	@Column(name = "column_type")
	private String columnType;

	/**
	 * JAVA類型
	 */
	@Column(name = "java_type")
	private String javaType;

	/**
	 * JAVA欄位名
	 */
	@NotBlank(message = "Java屬性不能為空")
	@Column(name = "java_field")
	private String javaField;

	/**
	 * 是否主鍵（1是）
	 */
	@Column(name = "is_pk")
	private String isPk;

	/**
	 * 是否自增（1是）
	 */
	@Column(name = "is_increment")
	private String isIncrement;

	/**
	 * 是否必填（1是）
	 */
	@Column(name = "is_required")
	private String isRequired;

	/**
	 * 是否為插入欄位（1是）
	 */
	@Column(name = "is_insert")
	private String isInsert;

	/**
	 * 是否編輯欄位（1是）
	 */
	@Column(name = "is_edit")
	private String isEdit;

	/**
	 * 是否列表欄位（1是）
	 */
	@Column(name = "is_list")
	private String isList;

	/**
	 * 是否查詢欄位（1是）
	 */
	@Column(name = "is_query")
	private String isQuery;

	/**
	 * 查詢方式（EQ等於、NE不等於、GT大於、LT小於、LIKE模糊、BETWEEN範圍）
	 */
	@Column(name = "query_type")
	private String queryType;

	/**
	 * 顯示類型（input文本框、textarea文本域、select下拉框、checkbox複選框、radio單選框、datetime日期控制項、upload上傳控制項、editor富文本控制項）
	 */
	@Column(name = "html_type")
	private String htmlType;

	/**
	 * 字典類型
	 */
	@Column(name = "dict_type")
	private String dictType;

	/**
	 * 排序
	 */
	@Column(name = "sort")
	private Integer sort;

	@Column(name = "create_by")
	private String createBy;

	@Column(name = "create_time")
	private Date createTime;

	@Column(name = "update_by")
	private String updateBy;

	@Column(name = "update_time")
	private Date updateTime;

	public void setColumnId(Long columnId) {
		this.columnId = columnId;
	}

	public Long getColumnId() {
		return columnId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public Long getTableId() {
		return tableId;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}

	public String getColumnComment() {
		return columnComment;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	public String getJavaType() {
		return javaType;
	}

	public void setJavaField(String javaField) {
		this.javaField = javaField;
	}

	public String getJavaField() {
		return javaField;
	}

	public void setIsPk(String isPk) {
		this.isPk = isPk;
	}

	public String getIsPk() {
		return isPk;
	}

	public boolean isPk() {
		return isPk(this.isPk);
	}

	public boolean isPk(String isPk) {
		return isPk != null && StringUtils.equals("1", isPk);
	}

	public String getIsIncrement() {
		return isIncrement;
	}

	public void setIsIncrement(String isIncrement) {
		this.isIncrement = isIncrement;
	}

	public boolean isIncrement() {
		return isIncrement(this.isIncrement);
	}

	public boolean isIncrement(String isIncrement) {
		return isIncrement != null && StringUtils.equals("1", isIncrement);
	}

	public void setIsRequired(String isRequired) {
		this.isRequired = isRequired;
	}

	public String getIsRequired() {
		return isRequired;
	}

	public boolean isRequired() {
		return isRequired(this.isRequired);
	}

	public boolean isRequired(String isRequired) {
		return isRequired != null && StringUtils.equals("1", isRequired);
	}

	public void setIsInsert(String isInsert) {
		this.isInsert = isInsert;
	}

	public String getIsInsert() {
		return isInsert;
	}

	public boolean isInsert() {
		return isInsert(this.isInsert);
	}

	public boolean isInsert(String isInsert) {
		return isInsert != null && StringUtils.equals("1", isInsert);
	}

	public void setIsEdit(String isEdit) {
		this.isEdit = isEdit;
	}

	public String getIsEdit() {
		return isEdit;
	}

	public boolean isEdit() {
		return isInsert(this.isEdit);
	}

	public boolean isEdit(String isEdit) {
		return isEdit != null && StringUtils.equals("1", isEdit);
	}

	public void setIsList(String isList) {
		this.isList = isList;
	}

	public String getIsList() {
		return isList;
	}

	public boolean isList() {
		return isList(this.isList);
	}

	public boolean isList(String isList) {
		return isList != null && StringUtils.equals("1", isList);
	}

	public void setIsQuery(String isQuery) {
		this.isQuery = isQuery;
	}

	public String getIsQuery() {
		return isQuery;
	}

	public boolean isQuery() {
		return isQuery(this.isQuery);
	}

	public boolean isQuery(String isQuery) {
		return isQuery != null && StringUtils.equals("1", isQuery);
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public String getQueryType() {
		return queryType;
	}

	public String getHtmlType() {
		return htmlType;
	}

	public void setHtmlType(String htmlType) {
		this.htmlType = htmlType;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
	}

	public String getDictType() {
		return dictType;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getSort() {
		return sort;
	}

	public String getCreateBy() {
		return createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public boolean isSuperColumn() {
		return isSuperColumn(this.javaField);
	}

	public static boolean isSuperColumn(String javaField) {
		return StringUtils.equalsAnyIgnoreCase(javaField,
				// TreeEntity
				"parentName", "parentId", "orderNum", "ancestors");
	}

	public boolean isUsableColumn() {
		return isUsableColumn(javaField);
	}

	public static boolean isUsableColumn(String javaField) {
		// isSuperColumn()中的名單用於避免生成多餘Domain屬性，若某些屬性在生成頁面時需要用到不能忽略，則放在此處白名單
		return StringUtils.equalsAnyIgnoreCase(javaField, "parentId", "orderNum", "remark");
	}

	public String readConverterExp() {
		String remarks = StringUtils.substringBetween(this.columnComment, "（", "）");
		StringBuffer sb = new StringBuffer();
		if (StringUtils.isNotEmpty(remarks)) {
			for (String value : remarks.split(" ")) {
				if (StringUtils.isNotEmpty(value)) {
					Object startStr = value.subSequence(0, 1);
					String endStr = value.substring(1);
					sb.append("").append(startStr).append("=").append(endStr).append(",");
				}
			}
			return sb.deleteCharAt(sb.length() - 1).toString();
		} else {
			return this.columnComment;
		}
	}
}
