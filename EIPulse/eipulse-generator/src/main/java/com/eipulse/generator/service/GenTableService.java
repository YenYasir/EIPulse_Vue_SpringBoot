package com.eipulse.generator.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eipulse.common.constant.Constants;
import com.eipulse.common.constant.GenConstants;
import com.eipulse.common.core.dao.BaseDao;
import com.eipulse.common.core.page.Page;
import com.eipulse.common.core.service.BaseService;
import com.eipulse.common.core.text.CharsetKit;
import com.eipulse.common.exception.ServiceException;
import com.eipulse.common.utils.SecurityUtils;
import com.eipulse.common.utils.StringUtils;
import com.eipulse.generator.dao.GenTableColumnDao;
import com.eipulse.generator.dao.GenTableDao;
import com.eipulse.generator.domain.GenTable;
import com.eipulse.generator.domain.GenTableColumn;
import com.eipulse.generator.util.GenUtils;
import com.eipulse.generator.util.VelocityInitializer;
import com.eipulse.generator.util.VelocityUtils;

/**
 * 業務 服務層實現
 * 
 * @author eipulse
 */
@Service
public class GenTableService extends BaseService<GenTable, Long> {
	private static final Logger log = LoggerFactory.getLogger(GenTableService.class);

	@Autowired
	private GenTableDao genTableDao;

	@Autowired
	private GenTableColumnDao genTableColumnDao;

	@Override
	protected BaseDao<GenTable, Long> getDao() {
		return genTableDao;
	}

	/**
	 * 查詢業務資訊
	 * 
	 * @param id 業務ID
	 * @return 業務資訊
	 */
	public GenTable selectGenTableById(Long id) {
		GenTable genTable = (GenTable) genTableDao.getObject(id);
		setTableFromOptions(genTable);
		return genTable;
	}

	/**
	 * 查詢業務列表
	 * 
	 * @param genTable 業務資訊
	 * @return 業務集合
	 */
	public Page selectGenTableList(GenTable genTable) {
		return genTableDao.selectGenTableList(genTable);
	}

	/**
	 * 查詢據庫列表
	 * 
	 * @param genTable 業務資訊
	 * @return 數據庫表集合
	 */
	public Page selectDbTableList(GenTable genTable) {
		return genTableDao.selectDbTableList(genTable);
	}

	/**
	 * 查詢據庫列表
	 * 
	 * @param tableNames 表名稱組
	 * @return 數據庫表集合
	 */
	public List<GenTable> selectDbTableListByNames(String[] tableNames) {
		return genTableDao.selectDbTableListByNames(tableNames);
	}

	/**
	 * 查詢所有表資訊
	 * 
	 * @return 表資訊集合
	 */
	public List<GenTable> selectGenTableAll() {
		return genTableDao.selectGenTableAll();
	}

	/**
	 * 修改業務
	 * 
	 * @param genTable 業務資訊
	 * @return 結果
	 */
	@Transactional
	public void updateGenTable(GenTable genTable) {
		String options = JSON.toJSONString(genTable.getParams());
		genTable.setOptions(options);
		genTableDao.updateObject(genTable);
		for (GenTableColumn cenTableColumn : genTable.getColumns()) {
			genTableColumnDao.updateObject(cenTableColumn);
		}
	}

	/**
	 * 刪除業務物件
	 * 
	 * @param tableIds 需要刪除的數據ID
	 * @return 結果
	 */
	@Transactional
	public void deleteGenTableByIds(Long[] tableIds) {
		genTableDao.deleteObject(tableIds);
		genTableColumnDao.deleteGenTableColumnByIds(tableIds);
	}

	/**
	 * 導入表結構
	 * 
	 * @param tableList 導入表列表
	 */

	@Transactional
	public void importGenTable(List<GenTable> tableList) {
		String operName = SecurityUtils.getUsername();
		try {
			for (GenTable table : tableList) {
				String tableName = table.getTableName();
				GenUtils.initTable(table, operName);
				genTableDao.addObject(table);
				// 保存列資訊
				List<GenTableColumn> genTableColumns = genTableColumnDao.selectDbTableColumnsByName(tableName);
				for (GenTableColumn column : genTableColumns) {
					GenUtils.initColumnField(column, table);
					genTableColumnDao.addObject(column);
				}

			}
		} catch (Exception e) {
			throw new ServiceException("導入失敗：" + e.getMessage());
		}
	}

	/**
	 * 預覽程式碼
	 * 
	 * @param tableId 表編號
	 * @return 預覽數據列表
	 */

	public Map<String, String> previewCode(Long tableId) {
		Map<String, String> dataMap = new LinkedHashMap<>();
		// 查詢表資訊
		GenTable table = (GenTable) genTableDao.getObject(tableId);
		// 設置主子表資訊
		setSubTable(table);
		// 設置主鍵列資訊
		setPkColumn(table);
		VelocityInitializer.initVelocity();

		VelocityContext context = VelocityUtils.prepareContext(table);

		// 獲取模板列表
		List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory());
		for (String template : templates) {
			// 渲染模板
			StringWriter sw = new StringWriter();
			Template tpl = Velocity.getTemplate(template, Constants.UTF8);
			tpl.merge(context, sw);
			dataMap.put(template, sw.toString());
		}
		return dataMap;
	}

	/**
	 * 生成程式碼（下載方式）
	 * 
	 * @param tableName 表名稱
	 * @return 數據
	 */

	public byte[] downloadCode(String tableName) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ZipOutputStream zip = new ZipOutputStream(outputStream);
		generatorCode(tableName, zip);
		IOUtils.closeQuietly(zip);
		return outputStream.toByteArray();
	}

	/**
	 * 生成程式碼（自定義路徑）
	 * 
	 * @param tableName 表名稱
	 */

	public void generatorCode(String tableName) {
		// 查詢表資訊
		GenTable table = (GenTable) genTableDao.getObject(tableName);
		// 設置主子表資訊
		setSubTable(table);
		// 設置主鍵列資訊
		setPkColumn(table);

		VelocityInitializer.initVelocity();

		VelocityContext context = VelocityUtils.prepareContext(table);

		// 獲取模板列表
		List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory());
		for (String template : templates) {
			if (!StringUtils.containsAny(template, "sql.vm", "api.js.vm", "index.vue.vm", "index-tree.vue.vm")) {
				// 渲染模板
				StringWriter sw = new StringWriter();
				Template tpl = Velocity.getTemplate(template, Constants.UTF8);
				tpl.merge(context, sw);
				try {
					String path = getGenPath(table, template);
					FileUtils.writeStringToFile(new File(path), sw.toString(), CharsetKit.UTF_8);
				} catch (IOException e) {
					throw new ServiceException("渲染模板失敗，表名：" + table.getTableName());
				}
			}
		}
	}

	/**
	 * 同步數據庫
	 * 
	 * @param tableName 表名稱
	 */

	@Transactional
	public void synchDb(String tableName) {
		GenTable table = (GenTable) genTableDao.getObject(tableName);
		List<GenTableColumn> tableColumns = table.getColumns();
		List<String> tableColumnNames = tableColumns.stream().map(GenTableColumn::getColumnName)
				.collect(Collectors.toList());

		List<GenTableColumn> dbTableColumns = genTableColumnDao.selectDbTableColumnsByName(tableName);
		if (StringUtils.isEmpty(dbTableColumns)) {
			throw new ServiceException("同步數據失敗，原表結構不存在");
		}
		List<String> dbTableColumnNames = dbTableColumns.stream().map(GenTableColumn::getColumnName)
				.collect(Collectors.toList());

		dbTableColumns.forEach(column -> {
			if (!tableColumnNames.contains(column.getColumnName())) {
				GenUtils.initColumnField(column, table);
				genTableColumnDao.addObject(column);
			}
		});

		List<GenTableColumn> delColumns = tableColumns.stream()
				.filter(column -> !dbTableColumnNames.contains(column.getColumnName())).collect(Collectors.toList());
		if (StringUtils.isNotEmpty(delColumns)) {
			for (GenTableColumn genTableColumn : delColumns) {
				genTableColumnDao.deleteObject(genTableColumn.getColumnId());
			}
		}

	}

	/**
	 * 批量生成程式碼（下載方式）
	 * 
	 * @param tableNames 表數組
	 * @return 數據
	 */

	public byte[] downloadCode(String[] tableNames) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ZipOutputStream zip = new ZipOutputStream(outputStream);
		for (String tableName : tableNames) {
			generatorCode(tableName, zip);
		}
		IOUtils.closeQuietly(zip);
		return outputStream.toByteArray();
	}

	/**
	 * 查詢表資訊並生成程式碼
	 */
	private void generatorCode(String tableName, ZipOutputStream zip) {
		// 查詢表資訊
		GenTable table = genTableDao.selectGenTableByName(tableName);
		// 設置主子表資訊
		setSubTable(table);
		// 設置主鍵列資訊
		setPkColumn(table);

		VelocityInitializer.initVelocity();

		VelocityContext context = VelocityUtils.prepareContext(table);

		// 獲取模板列表
		List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory());
		for (String template : templates) {
			// 渲染模板
			StringWriter sw = new StringWriter();
			Template tpl = Velocity.getTemplate(template, Constants.UTF8);
			tpl.merge(context, sw);
			try {
				// 添加到zip
				zip.putNextEntry(new ZipEntry(VelocityUtils.getFileName(template, table)));
				IOUtils.write(sw.toString(), zip, Constants.UTF8);
				IOUtils.closeQuietly(sw);
				zip.flush();
				zip.closeEntry();
			} catch (IOException e) {
				log.error("渲染模板失敗，表名：" + table.getTableName(), e);
			}
		}
	}

	/**
	 * 修改保存參數校驗
	 * 
	 * @param genTable 業務資訊
	 */

	public void validateEdit(GenTable genTable) {
		if (GenConstants.TPL_TREE.equals(genTable.getTplCategory())) {
			String options = JSON.toJSONString(genTable.getParams());
			JSONObject paramsObj = JSONObject.parseObject(options);
			if (StringUtils.isEmpty(paramsObj.getString(GenConstants.TREE_CODE))) {
				throw new ServiceException("樹編碼欄位不能為空");
			} else if (StringUtils.isEmpty(paramsObj.getString(GenConstants.TREE_PARENT_CODE))) {
				throw new ServiceException("樹父編碼欄位不能為空");
			} else if (StringUtils.isEmpty(paramsObj.getString(GenConstants.TREE_NAME))) {
				throw new ServiceException("樹名稱欄位不能為空");
			} else if (GenConstants.TPL_SUB.equals(genTable.getTplCategory())) {
				if (StringUtils.isEmpty(genTable.getSubTableName())) {
					throw new ServiceException("關聯子表的表名不能為空");
				} else if (StringUtils.isEmpty(genTable.getSubTableFkName())) {
					throw new ServiceException("子表關聯的外鍵名不能為空");
				}
			}
		}
	}

	/**
	 * 設置主鍵列資訊
	 * 
	 * @param table 業務表資訊
	 */
	public void setPkColumn(GenTable table) {
		for (GenTableColumn column : table.getColumns()) {
			if (column.isPk()) {
				table.setPkColumn(column);
				break;
			}
		}
		if (StringUtils.isNull(table.getPkColumn())) {
			table.setPkColumn(table.getColumns().get(0));
		}
		if (GenConstants.TPL_SUB.equals(table.getTplCategory())) {
			for (GenTableColumn column : table.getSubTable().getColumns()) {
				if (column.isPk()) {
					table.getSubTable().setPkColumn(column);
					break;
				}
			}
			if (StringUtils.isNull(table.getSubTable().getPkColumn())) {
				table.getSubTable().setPkColumn(table.getSubTable().getColumns().get(0));
			}
		}
	}

	/**
	 * 設置主子表資訊
	 * 
	 * @param table 業務表資訊
	 */
	public void setSubTable(GenTable table) {
		String subTableName = table.getSubTableName();
		if (StringUtils.isNotEmpty(subTableName)) {
			table.setSubTable(genTableDao.selectGenTableByName(subTableName));
		}
	}

	/**
	 * 設置程式碼生成其他選項值
	 * 
	 * @param genTable 設置後的生成物件
	 */
	public void setTableFromOptions(GenTable genTable) {
		JSONObject paramsObj = JSONObject.parseObject(genTable.getOptions());
		if (StringUtils.isNotNull(paramsObj)) {
			String treeCode = paramsObj.getString(GenConstants.TREE_CODE);
			String treeParentCode = paramsObj.getString(GenConstants.TREE_PARENT_CODE);
			String treeName = paramsObj.getString(GenConstants.TREE_NAME);
			String parentMenuId = paramsObj.getString(GenConstants.PARENT_MENU_ID);
			String parentMenuName = paramsObj.getString(GenConstants.PARENT_MENU_NAME);

			genTable.setTreeCode(treeCode);
			genTable.setTreeParentCode(treeParentCode);
			genTable.setTreeName(treeName);
			genTable.setParentMenuId(parentMenuId);
			genTable.setParentMenuName(parentMenuName);
		}
	}

	/**
	 * 獲取程式碼生成地址
	 * 
	 * @param table    業務表資訊
	 * @param template 模板文件路徑
	 * @return 生成位址
	 */
	public static String getGenPath(GenTable table, String template) {
		String genPath = table.getGenPath();
		if (StringUtils.equals(genPath, "/")) {
			return System.getProperty("user.dir") + File.separator + "src" + File.separator
					+ VelocityUtils.getFileName(template, table);
		}
		return genPath + File.separator + VelocityUtils.getFileName(template, table);
	}
}