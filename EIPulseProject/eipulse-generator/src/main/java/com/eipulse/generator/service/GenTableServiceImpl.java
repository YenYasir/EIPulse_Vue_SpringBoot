package com.eipulse.generator.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eipulse.common.constant.Constants;
import com.eipulse.common.constant.GenConstants;
import com.eipulse.common.core.page.PageDomain;
import com.eipulse.common.core.page.TableSupport;
import com.eipulse.common.core.text.CharsetKit;
import com.eipulse.common.exception.CustomException;
import com.eipulse.common.utils.DateUtils;
import com.eipulse.common.utils.SecurityUtils;
import com.eipulse.common.utils.StringUtils;
import com.eipulse.common.utils.file.FileUtils;
import com.eipulse.common.utils.sql.SqlUtil;
import com.eipulse.generator.dao.GenTableColumnDao;
import com.eipulse.generator.dao.GenTableDao;
import com.eipulse.generator.domain.GenTable;
import com.eipulse.generator.domain.GenTableColumn;
import com.eipulse.generator.util.GenUtils;
import com.eipulse.generator.util.VelocityInitializer;
import com.eipulse.generator.util.VelocityUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 業務 服務層實現
 */
@Transactional(readOnly = true)
@Slf4j
@Service
public class GenTableServiceImpl implements IGenTableService {

	@Autowired
	private GenTableDao genTableDao;

	@Autowired
	private GenTableColumnDao genTableColumnDao;

	/**
	 * 查詢業務資訊
	 *
	 * @param id 業務ID
	 * @return 業務資訊
	 */
	@Override
	public GenTable selectGenTableById(Long id) {
		Optional<GenTable> tableOptional = genTableDao.findById(id);
		if (!tableOptional.isPresent()) {
			return new GenTable();
		}
		GenTable genTable = tableOptional.get();
		List<GenTableColumn> genTableColumns = genTableColumnDao.findByTableId(genTable.getTableId());
		genTable.setColumns(genTableColumns);
		setTableFromOptions(genTable);
		return genTable;
	}

	/**
	 * 查詢業務列表
	 *
	 * @param req 業務資訊
	 * @return 業務集合
	 */
	@Override
	public Page<GenTable> selectGenTableList(GenTable req) {
		PageDomain pageDomain = TableSupport.buildPageRequest();
		if (StringUtils.isNotNull(pageDomain.getPageNum()) && StringUtils.isNotNull(pageDomain.getPageSize())) {
			String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
		}
		Specification<GenTable> example = new Specification<GenTable>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<GenTable> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();
				if (StringUtils.isNoneBlank(req.getTableName())) {
					Predicate pre = cb.like(root.get("tableName").as(String.class),
							"%" + req.getTableName().trim().toLowerCase() + "%");
					list.add(pre);
				}
				if (StringUtils.isNoneBlank(req.getTableComment())) {
					Predicate pre = cb.equal(root.get("tableComment").as(String.class),
							"%" + req.getTableComment().trim().toLowerCase() + "%");
					list.add(pre);
				}
				if (null != req.getParams().get("beginTime")) {
					Predicate pre = cb.greaterThanOrEqualTo(root.get("createTime").as(Date.class),
							DateUtils.parseDate(req.getParams().get("beginTime")));
					list.add(pre);
				}
				if (null != req.getParams().get("endTime")) {
					Predicate pre = cb.lessThanOrEqualTo(root.get("createTime").as(Date.class),
							DateUtils.parseDate(req.getParams().get("endTime")));
					list.add(pre);
				}
				if (list.isEmpty()) {
					return null;
				}
				return cb.and(list.toArray(new Predicate[0]));
			}
		};
		Pageable pageable = PageRequest.of(pageDomain.getPageNo(), pageDomain.getPageSize(), Sort.Direction.DESC,
				Optional.ofNullable(pageDomain.getOrderByColumn()).orElse("createTime"));
		Page<GenTable> page = genTableDao.findAll(example, pageable);
		return page;
	}

	/**
	 * 查詢據庫列表
	 *
	 * @param genTable 業務資訊
	 * @return 資料庫表集合
	 */
	@Override
	public Page<GenTable> selectDbTableList(GenTable genTable) {
		PageDomain pageReq = TableSupport.buildPageRequest();
		Page<GenTable> genTables = genTableDao.findDbTableList(genTable,
				PageRequest.of(pageReq.getPageNum() - 1, pageReq.getPageSize()));
		return genTables;
	}

	/**
	 * 查詢據庫列表
	 *
	 * @param tableNames 表名稱組
	 * @return 資料庫表集合
	 */
	@Override
	public List<GenTable> selectDbTableListByNames(String[] tableNames) {
		List<GenTable> genTables = genTableDao.findDbTableListByNames(Arrays.asList(tableNames));
		return genTables;
	}

	/**
	 * 修改業務
	 *
	 * @param genTable 業務資訊
	 * @return 結果
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateGenTable(GenTable genTable) {
		String options = JSON.toJSONString(genTable.getParams());
		genTable.setOptions(options);
		genTable.setUpdateTime(new Date());
		GenTable save = genTableDao.save(genTable);
		if (save != null) {
			for (GenTableColumn cenTableColumn : genTable.getColumns()) {
				cenTableColumn.setUpdateTime(new Date());
				genTableColumnDao.save(cenTableColumn);
			}
		}
	}

	/**
	 * 刪除業務物件
	 *
	 * @param tableIds 需要刪除的數據ID
	 * @return 結果
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteGenTableByIds(Long[] tableIds) {
		genTableDao.deleteByTableIdIn(Arrays.asList(tableIds));
		genTableColumnDao.deleteByTableIdIn(Arrays.asList(tableIds));

	}

	/**
	 * 導入表結構
	 *
	 * @param tableList 導入表列表
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void importGenTable(List<GenTable> tableList) {
		String operName = SecurityUtils.getUsername();
		Date now = new Date();
		try {
			for (GenTable table : tableList) {
				String tableName = table.getTableName();
				GenUtils.initTable(table, operName);
				table.setCreateTime(now);
				GenTable genTable = genTableDao.save(table);
				if (null != genTable) {
					// 保存列資訊
					List<GenTableColumn> genTableColumns = genTableColumnDao.findDbTableColumnsByName(tableName);
					for (GenTableColumn column : genTableColumns) {
						GenUtils.initColumnField(column, table);
						column.setCreateTime(now);
						genTableColumnDao.save(column);
					}
				}
			}
		} catch (Exception e) {
			throw new CustomException("導入失敗：" + e.getMessage());
		}
	}

	/**
	 * 預覽程式碼
	 *
	 * @param tableId 表編號
	 * @return 預覽數據列表
	 */
	@Override
	public Map<String, String> previewCode(Long tableId) {
		Map<String, String> dataMap = new LinkedHashMap<>();
		// 查詢表資訊
		Optional<GenTable> tableOptional = genTableDao.findById(tableId);
		if (!tableOptional.isPresent()) {
			return new HashMap<>();
		}
		GenTable table = tableOptional.get();
		List<GenTableColumn> genTableColumns = genTableColumnDao.findByTableId(table.getTableId());
		table.setColumns(genTableColumns);
		// 查詢列資訊
		List<GenTableColumn> columns = table.getColumns();
		setPkColumn(table, columns);
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
	@Override
	public byte[] downloadCode(String tableName) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ZipOutputStream zip = new ZipOutputStream(outputStream);
		generatorCode(tableName, zip);
		IOUtils.closeQuietly(zip);
		return outputStream.toByteArray();
	}

	/**
	 * 生成程式碼（自訂路徑）
	 *
	 * @param tableName 表名稱
	 */
	@Override
	public void generatorCode(String tableName) {
		// 查詢表資訊
		GenTable table = genTableDao.findByTableName(tableName).get();
		List<GenTableColumn> genTableColumns = genTableColumnDao.findByTableId(table.getTableId());
		table.setColumns(genTableColumns);
		// 查詢列資訊
		List<GenTableColumn> columns = table.getColumns();
		setPkColumn(table, columns);
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
					throw new CustomException("渲染模板失敗，表名：" + table.getTableName());
				}
			}
		}
	}

	/**
	 * 同步資料庫
	 *
	 * @param tableName 表名稱
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void synchDb(String tableName) {
		// 查詢表資訊
		GenTable table = genTableDao.findByTableName(tableName).get();
		List<GenTableColumn> genTableColumns = genTableColumnDao.findByTableId(table.getTableId());
		table.setColumns(genTableColumns);
		List<GenTableColumn> tableColumns = table.getColumns();
		List<String> tableColumnNames = tableColumns.stream().map(GenTableColumn::getColumnName)
				.collect(Collectors.toList());
		List<GenTableColumn> dbTableColumns = genTableColumnDao.findDbTableColumnsByName(tableName);
		List<String> dbTableColumnNames = dbTableColumns.stream().map(GenTableColumn::getColumnName)
				.collect(Collectors.toList());
		dbTableColumns.forEach(column -> {
			if (!tableColumnNames.contains(column.getColumnName())) {
				GenUtils.initColumnField(column, table);
				column.setCreateTime(new Date());
				genTableColumnDao.save(column);
			}
		});
		List<GenTableColumn> delColumns = tableColumns.stream()
				.filter(column -> !dbTableColumnNames.contains(column.getColumnName())).collect(Collectors.toList());
		if (StringUtils.isNotEmpty(delColumns)) {
			List<Long> columnIds = delColumns.stream().map(c -> c.getColumnId()).collect(Collectors.toList());
			genTableColumnDao.deleteByColumnIdIn(columnIds);
		}
	}

	/**
	 * 批次生成程式碼（下載方式）
	 *
	 * @param tableNames 表數組
	 * @return 數據
	 */
	@Override
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
		GenTable table = genTableDao.findByTableName(tableName).get();
		List<GenTableColumn> genTableColumns = genTableColumnDao.findByTableId(table.getTableId());
		table.setColumns(genTableColumns);

		// 查詢列資訊
		List<GenTableColumn> columns = table.getColumns();
		setPkColumn(table, columns);

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
	@Override
	public void validateEdit(GenTable genTable) {
		if (GenConstants.TPL_TREE.equals(genTable.getTplCategory())) {
			String options = JSON.toJSONString(genTable.getParams());
			JSONObject paramsObj = JSONObject.parseObject(options);
			if (StringUtils.isEmpty(paramsObj.getString(GenConstants.TREE_CODE))) {
				throw new CustomException("樹編碼欄位不能為空");
			} else if (StringUtils.isEmpty(paramsObj.getString(GenConstants.TREE_PARENT_CODE))) {
				throw new CustomException("樹父編碼欄位不能為空");
			} else if (StringUtils.isEmpty(paramsObj.getString(GenConstants.TREE_NAME))) {
				throw new CustomException("樹名稱欄位不能為空");
			}
		}
	}

	/**
	 * 設置主鍵列資訊
	 *
	 * @param table   業務表資訊
	 * @param columns 業務欄位列表
	 */
	public void setPkColumn(GenTable table, List<GenTableColumn> columns) {
		for (GenTableColumn column : columns) {
			if (column.isPk()) {
				table.setPkColumn(column);
				break;
			}
		}
		if (StringUtils.isNull(table.getPkColumn())) {
			table.setPkColumn(columns.get(0));
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
	 * 獲取程式碼生成位址
	 *
	 * @param table    業務表資訊
	 * @param template 模板檔案路徑
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
