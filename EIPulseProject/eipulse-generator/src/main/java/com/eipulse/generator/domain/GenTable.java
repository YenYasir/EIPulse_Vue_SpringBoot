package com.eipulse.generator.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.eipulse.common.constant.GenConstants;
import com.eipulse.common.core.domain.BaseEntity;
import com.eipulse.common.utils.StringUtils;

/**
 * 業務表 gen_table
 */
@Entity
@Table(name = "gen_table")
@DynamicInsert()
@DynamicUpdate()
public class GenTable extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 編號
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "table_id")
	private Long tableId;

	/**
	 * 表名稱
	 */
	@NotBlank(message = "表名稱不能為空")
	@Column(name = "table_name")
	private String tableName;

	/**
	 * 表描述
	 */
	@NotBlank(message = "表描述不能為空")
	@Column(name = "table_comment")
	private String tableComment;

	/**
	 * 實體類名稱(首字母大寫)
	 */
	@NotBlank(message = "實體類名稱不能為空")
	@Column(name = "class_name")
	private String className;

	/**
	 * 使用的模板（crud單表操作 tree樹表操作）
	 */
	@Column(name = "tpl_category")
	private String tplCategory;

	/**
	 * 生成包路徑
	 */
	@NotBlank(message = "生成包路徑不能為空")
	@Column(name = "package_name")
	private String packageName;

	/**
	 * 生成模組名
	 */
	@NotBlank(message = "生成模組名不能為空")
	@Column(name = "module_name")
	private String moduleName;

	/**
	 * 生成業務名
	 */
	@NotBlank(message = "生成業務名不能為空")
	@Column(name = "business_name")
	private String businessName;

	/**
	 * 生成功能名
	 */
	@NotBlank(message = "生成功能名不能為空")
	@Column(name = "function_name")
	private String functionName;

	/**
	 * 生成作者
	 */
	@NotBlank(message = "作者不能為空")
	@Column(name = "function_author")
	private String functionAuthor;

	/**
	 * 生成代碼方式（0zip壓縮包 1自訂路徑）
	 */
	@Column(name = "gen_type")
	private String genType;

	/**
	 * 生成路徑（不填默認項目路徑）
	 */
	@Column(name = "gen_path")
	private String genPath;

	/**
	 * 其它生成選項
	 */
	@Column(name = "options")
	private String options;

	@Column(name = "create_by")
	private String createBy;

	@Column(name = "create_time")
	private Date createTime;

	@Column(name = "update_by")
	private String updateBy;

	@Column(name = "update_time")
	private Date updateTime;

	@Column(name = "remark")
	private String remark;

	/**
	 * 主鍵資訊
	 */
	@Transient
	private GenTableColumn pkColumn;

	/**
	 * 表列資訊
	 */
	@Valid
	@Transient
	private List<GenTableColumn> columns;

	/**
	 * 樹編碼欄位
	 */
	@Transient
	private String treeCode;

	/**
	 * 樹父編碼欄位
	 */
	@Transient
	private String treeParentCode;

	/**
	 * 樹名稱欄位
	 */
	@Transient
	private String treeName;

	/**
	 * 上級菜單ID欄位
	 */
	@Transient
	private String parentMenuId;

	/**
	 * 上級菜單名稱欄位
	 */
	@Transient
	private String parentMenuName;

	public Long getTableId() {
		return tableId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableComment() {
		return tableComment;
	}

	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getTplCategory() {
		return tplCategory;
	}

	public void setTplCategory(String tplCategory) {
		this.tplCategory = tplCategory;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getFunctionAuthor() {
		return functionAuthor;
	}

	public void setFunctionAuthor(String functionAuthor) {
		this.functionAuthor = functionAuthor;
	}

	public String getGenType() {
		return genType;
	}

	public void setGenType(String genType) {
		this.genType = genType;
	}

	public String getGenPath() {
		return genPath;
	}

	public void setGenPath(String genPath) {
		this.genPath = genPath;
	}

	public GenTableColumn getPkColumn() {
		return pkColumn;
	}

	public void setPkColumn(GenTableColumn pkColumn) {
		this.pkColumn = pkColumn;
	}

	public List<GenTableColumn> getColumns() {
		return columns;
	}

	public void setColumns(List<GenTableColumn> columns) {
		this.columns = columns;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public String getTreeCode() {
		return treeCode;
	}

	public void setTreeCode(String treeCode) {
		this.treeCode = treeCode;
	}

	public String getTreeParentCode() {
		return treeParentCode;
	}

	public void setTreeParentCode(String treeParentCode) {
		this.treeParentCode = treeParentCode;
	}

	public String getTreeName() {
		return treeName;
	}

	public void setTreeName(String treeName) {
		this.treeName = treeName;
	}

	public String getParentMenuId() {
		return parentMenuId;
	}

	public void setParentMenuId(String parentMenuId) {
		this.parentMenuId = parentMenuId;
	}

	public String getParentMenuName() {
		return parentMenuName;
	}

	public void setParentMenuName(String parentMenuName) {
		this.parentMenuName = parentMenuName;
	}

	public boolean isTree() {
		return isTree(this.tplCategory);
	}

	public static boolean isTree(String tplCategory) {
		return tplCategory != null && StringUtils.equals(GenConstants.TPL_TREE, tplCategory);
	}

	public boolean isCrud() {
		return isCrud(this.tplCategory);
	}

	public static boolean isCrud(String tplCategory) {
		return tplCategory != null && StringUtils.equals(GenConstants.TPL_CRUD, tplCategory);
	}

	public boolean isSuperColumn(String javaField) {
		return isSuperColumn(this.tplCategory, javaField);
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public static boolean isSuperColumn(String tplCategory, String javaField) {
		if (isTree(tplCategory)) {
			return StringUtils.equalsAnyIgnoreCase(javaField,
					ArrayUtils.addAll(GenConstants.TREE_ENTITY, GenConstants.BASE_ENTITY));
		}
		return StringUtils.equalsAnyIgnoreCase(javaField, GenConstants.BASE_ENTITY);
	}
}
