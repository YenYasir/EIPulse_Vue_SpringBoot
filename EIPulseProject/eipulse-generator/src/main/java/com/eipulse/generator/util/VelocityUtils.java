package com.eipulse.generator.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.velocity.VelocityContext;

import com.alibaba.fastjson.JSONObject;
import com.eipulse.common.constant.GenConstants;
import com.eipulse.common.utils.DateUtils;
import com.eipulse.common.utils.StringUtils;
import com.eipulse.generator.domain.GenTable;
import com.eipulse.generator.domain.GenTableColumn;

/**
 * 模板處理工具類
 */
public class VelocityUtils {
	/**
	 * 項目空間路徑
	 */
	private static final String PROJECT_PATH = "main/java";

	/**
	 * 默認上級菜單，系統工具
	 */
	private static final String DEFAULT_PARENT_MENU_ID = "3";

	/**
	 * 設置模板變數資訊
	 *
	 * @return 模板列表
	 */
	public static VelocityContext prepareContext(GenTable genTable) {
		String moduleName = genTable.getModuleName();
		String businessName = genTable.getBusinessName();
		String packageName = genTable.getPackageName();
		String tplCategory = genTable.getTplCategory();
		String functionName = genTable.getFunctionName();

		VelocityContext velocityContext = new VelocityContext();
		velocityContext.put("tplCategory", genTable.getTplCategory());
		velocityContext.put("tableName", genTable.getTableName());
		velocityContext.put("functionName", StringUtils.isNotEmpty(functionName) ? functionName : "【請填寫功能名稱】");
		velocityContext.put("ClassName", genTable.getClassName());
		velocityContext.put("className", StringUtils.uncapitalize(genTable.getClassName()));
		velocityContext.put("moduleName", genTable.getModuleName());
		velocityContext.put("BusinessName", StringUtils.capitalize(genTable.getBusinessName()));
		velocityContext.put("businessName", genTable.getBusinessName());
		velocityContext.put("basePackage", getPackagePrefix(packageName));
		velocityContext.put("packageName", packageName);
		velocityContext.put("author", genTable.getFunctionAuthor());
		velocityContext.put("datetime", DateUtils.getDate());
		velocityContext.put("pkColumn", genTable.getPkColumn());
		velocityContext.put("importList", getImportList(genTable.getColumns()));
		velocityContext.put("permissionPrefix", getPermissionPrefix(moduleName, businessName));
		velocityContext.put("columns", genTable.getColumns());
		velocityContext.put("table", genTable);
		setMenuVelocityContext(velocityContext, genTable);
		if (GenConstants.TPL_TREE.equals(tplCategory)) {
			setTreeVelocityContext(velocityContext, genTable);
		}
		return velocityContext;
	}

	public static void setMenuVelocityContext(VelocityContext context, GenTable genTable) {
		String options = genTable.getOptions();
		JSONObject paramsObj = JSONObject.parseObject(options);
		String parentMenuId = getParentMenuId(paramsObj);
		context.put("parentMenuId", parentMenuId);
	}

	public static void setTreeVelocityContext(VelocityContext context, GenTable genTable) {
		String options = genTable.getOptions();
		JSONObject paramsObj = JSONObject.parseObject(options);
		String treeCode = getTreecode(paramsObj);
		String treeParentCode = getTreeParentCode(paramsObj);
		String treeName = getTreeName(paramsObj);

		context.put("treeCode", treeCode);
		context.put("treeParentCode", treeParentCode);
		context.put("treeName", treeName);
		context.put("expandColumn", getExpandColumn(genTable));
		if (paramsObj.containsKey(GenConstants.TREE_PARENT_CODE)) {
			context.put("tree_parent_code", paramsObj.getString(GenConstants.TREE_PARENT_CODE));
		}
		if (paramsObj.containsKey(GenConstants.TREE_NAME)) {
			context.put("tree_name", paramsObj.getString(GenConstants.TREE_NAME));
		}
	}

	/**
	 * 獲取模板資訊
	 *
	 * @return 模板列表
	 */
	public static List<String> getTemplateList(String tplCategory) {
		List<String> templates = new ArrayList<>();
		templates.add("vm/java/domain.java.vm");
		templates.add("vm/java/dao.java.vm");
		templates.add("vm/java/service.java.vm");
		templates.add("vm/java/serviceImpl.java.vm");
		templates.add("vm/java/controller.java.vm");
		templates.add("vm/sql/sql.vm");
		templates.add("vm/js/api.js.vm");
		if (GenConstants.TPL_CRUD.equals(tplCategory)) {
			templates.add("vm/vue/index.vue.vm");
		} else if (GenConstants.TPL_TREE.equals(tplCategory)) {
			templates.add("vm/vue/index-tree.vue.vm");
		}
		return templates;
	}

	/**
	 * 獲取檔案名
	 */
	public static String getFileName(String template, GenTable genTable) {
		// 檔案名稱
		String fileName = "";
		// 包路徑
		String packageName = genTable.getPackageName();
		// 模組名
		String moduleName = genTable.getModuleName();
		// 大寫類名
		String className = genTable.getClassName();
		// 業務名稱
		String businessName = genTable.getBusinessName();

		String javaPath = PROJECT_PATH + "/" + StringUtils.replace(packageName, ".", "/");
		String vuePath = "vue";

		if (template.contains("domain.java.vm")) {
			fileName = StringUtils.format("{}/domain/{}.java", javaPath, className);
		} else if (template.contains("dao.java.vm")) {
			fileName = StringUtils.format("{}/dao/{}Dao.java", javaPath, className);
		} else if (template.contains("service.java.vm")) {
			fileName = StringUtils.format("{}/service/I{}Service.java", javaPath, className);
		} else if (template.contains("serviceImpl.java.vm")) {
			fileName = StringUtils.format("{}/service/impl/{}ServiceImpl.java", javaPath, className);
		} else if (template.contains("controller.java.vm")) {
			fileName = StringUtils.format("{}/controller/{}Controller.java", javaPath, className);
		} else if (template.contains("sql.vm")) {
			fileName = businessName + "Menu.sql";
		} else if (template.contains("api.js.vm")) {
			fileName = StringUtils.format("{}/api/{}/{}.js", vuePath, moduleName, businessName);
		} else if (template.contains("index.vue.vm")) {
			fileName = StringUtils.format("{}/views/{}/{}/index.vue", vuePath, moduleName, businessName);
		} else if (template.contains("index-tree.vue.vm")) {
			fileName = StringUtils.format("{}/views/{}/{}/index.vue", vuePath, moduleName, businessName);
		}
		return fileName;
	}

	/**
	 * 獲取包前綴
	 *
	 * @param packageName 包名稱
	 * @return 包前綴名稱
	 */
	public static String getPackagePrefix(String packageName) {
		int lastIndex = packageName.lastIndexOf(".");
		String basePackage = StringUtils.substring(packageName, 0, lastIndex);
		return basePackage;
	}

	/**
	 * 根據列類型獲取導入包
	 *
	 * @param columns 列集合
	 * @return 返回需要導入的包列表
	 */
	public static HashSet<String> getImportList(List<GenTableColumn> columns) {
		HashSet<String> importList = new HashSet<>();
		for (GenTableColumn column : columns) {
			if (!column.isSuperColumn() && GenConstants.TYPE_DATE.equals(column.getJavaType())) {
				importList.add("java.util.Date");
				importList.add("com.fasterxml.jackson.annotation.JsonFormat");
			} else if (!column.isSuperColumn() && GenConstants.TYPE_BIGDECIMAL.equals(column.getJavaType())) {
				importList.add("java.math.BigDecimal");
			}
		}
		return importList;
	}

	/**
	 * 獲取權限前綴
	 *
	 * @param moduleName   模組名稱
	 * @param businessName 業務名稱
	 * @return 返回權限前綴
	 */
	public static String getPermissionPrefix(String moduleName, String businessName) {
		return StringUtils.format("{}:{}", moduleName, businessName);
	}

	/**
	 * 獲取上級菜單ID欄位
	 *
	 * @param paramsObj 生成其他選項
	 * @return 上級菜單ID欄位
	 */
	public static String getParentMenuId(JSONObject paramsObj) {
		if (StringUtils.isNotEmpty(paramsObj) && paramsObj.containsKey(GenConstants.PARENT_MENU_ID)) {
			return paramsObj.getString(GenConstants.PARENT_MENU_ID);
		}
		return DEFAULT_PARENT_MENU_ID;
	}

	/**
	 * 獲取樹編碼
	 *
	 * @param paramsObj 生成其他選項
	 * @return 樹編碼
	 */
	public static String getTreecode(JSONObject paramsObj) {
		if (paramsObj.containsKey(GenConstants.TREE_CODE)) {
			return StringUtils.toCamelCase(paramsObj.getString(GenConstants.TREE_CODE));
		}
		return StringUtils.EMPTY;
	}

	/**
	 * 獲取樹父編碼
	 *
	 * @param paramsObj 生成其他選項
	 * @return 樹父編碼
	 */
	public static String getTreeParentCode(JSONObject paramsObj) {
		if (paramsObj.containsKey(GenConstants.TREE_PARENT_CODE)) {
			return StringUtils.toCamelCase(paramsObj.getString(GenConstants.TREE_PARENT_CODE));
		}
		return StringUtils.EMPTY;
	}

	/**
	 * 獲取樹名稱
	 *
	 * @param paramsObj 生成其他選項
	 * @return 樹名稱
	 */
	public static String getTreeName(JSONObject paramsObj) {
		if (paramsObj.containsKey(GenConstants.TREE_NAME)) {
			return StringUtils.toCamelCase(paramsObj.getString(GenConstants.TREE_NAME));
		}
		return StringUtils.EMPTY;
	}

	/**
	 * 獲取需要在哪一列上面顯示展開按鈕
	 *
	 * @param genTable 業務表物件
	 * @return 展開按鈕列序號
	 */
	public static int getExpandColumn(GenTable genTable) {
		String options = genTable.getOptions();
		JSONObject paramsObj = JSONObject.parseObject(options);
		String treeName = paramsObj.getString(GenConstants.TREE_NAME);
		int num = 0;
		for (GenTableColumn column : genTable.getColumns()) {
			if (column.isList()) {
				num++;
				String columnName = column.getColumnName();
				if (columnName.equals(treeName)) {
					break;
				}
			}
		}
		return num;
	}
}
