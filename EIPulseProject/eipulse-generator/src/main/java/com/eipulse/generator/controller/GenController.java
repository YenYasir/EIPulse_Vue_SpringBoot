package com.eipulse.generator.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eipulse.common.annotation.Log;
import com.eipulse.common.core.controller.BaseController;
import com.eipulse.common.core.domain.AjaxResult;
import com.eipulse.common.core.page.TableDataInfo;
import com.eipulse.common.core.text.Convert;
import com.eipulse.common.enums.BusinessType;
import com.eipulse.generator.domain.GenTable;
import com.eipulse.generator.domain.GenTableColumn;
import com.eipulse.generator.service.IGenTableColumnService;
import com.eipulse.generator.service.IGenTableService;

/**
 * 代碼生成 操作處理
 */
@RestController
@RequestMapping("/tool/gen")
public class GenController extends BaseController {
	@Autowired
	private IGenTableService genTableService;

	@Autowired
	private IGenTableColumnService genTableColumnService;

	/**
	 * 查詢代碼生成列表
	 */
	@PreAuthorize("@ss.hasPermi('tool:gen:list')")
	@GetMapping("/list")
	public TableDataInfo genList(GenTable genTable) {
		Page<GenTable> page = genTableService.selectGenTableList(genTable);
		return getDataTable(page);
	}

	/**
	 * 修改代碼生成業務
	 */
	@PreAuthorize("@ss.hasPermi('tool:gen:query')")
	@GetMapping(value = "/{talbleId}")
	public AjaxResult getInfo(@PathVariable Long talbleId) {
		GenTable table = genTableService.selectGenTableById(talbleId);
		List<GenTableColumn> list = genTableColumnService.selectGenTableColumnListByTableId(talbleId);
		Map<String, Object> map = new HashMap<>();
		map.put("info", table);
		map.put("rows", list);
		return AjaxResult.success(map);
	}

	/**
	 * 查詢資料庫列表
	 */
	@PreAuthorize("@ss.hasPermi('tool:gen:list')")
	@GetMapping("/db/list")
	public TableDataInfo dataList(GenTable genTable) {
		Page<GenTable> page = genTableService.selectDbTableList(genTable);
		return getDataTable(page);
	}

	/**
	 * 查詢數據表欄位列表
	 */
	@PreAuthorize("@ss.hasPermi('tool:gen:list')")
	@GetMapping(value = "/column/{talbleId}")
	public TableDataInfo columnList(Long tableId) {
		TableDataInfo dataInfo = new TableDataInfo();
		List<GenTableColumn> list = genTableColumnService.selectGenTableColumnListByTableId(tableId);
		dataInfo.setRows(list);
		dataInfo.setTotal(list.size());
		return dataInfo;
	}

	/**
	 * 導入表結構（保存）
	 */
	@PreAuthorize("@ss.hasPermi('tool:gen:list')")
	@Log(title = "代碼生成", businessType = BusinessType.IMPORT)
	@PostMapping("/importTable")
	public AjaxResult importTableSave(String tables) {
		String[] tableNames = Convert.toStrArray(tables);
		// 查詢表資訊
		List<GenTable> tableList = genTableService.selectDbTableListByNames(tableNames);
		genTableService.importGenTable(tableList);
		return AjaxResult.success();
	}

	/**
	 * 修改保存代碼生成業務
	 */
	@PreAuthorize("@ss.hasPermi('tool:gen:edit')")
	@Log(title = "代碼生成", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult editSave(@Validated @RequestBody GenTable genTable) {
		genTableService.validateEdit(genTable);
		genTableService.updateGenTable(genTable);
		return AjaxResult.success();
	}

	/**
	 * 刪除代碼生成
	 */
	@PreAuthorize("@ss.hasPermi('tool:gen:remove')")
	@Log(title = "代碼生成", businessType = BusinessType.DELETE)
	@DeleteMapping("/{tableIds}")
	public AjaxResult remove(@PathVariable Long[] tableIds) {
		genTableService.deleteGenTableByIds(tableIds);
		return AjaxResult.success();
	}

	/**
	 * 預覽代碼
	 */
	@PreAuthorize("@ss.hasPermi('tool:gen:preview')")
	@GetMapping("/preview/{tableId}")
	public AjaxResult preview(@PathVariable("tableId") Long tableId) throws IOException {
		Map<String, String> dataMap = genTableService.previewCode(tableId);
		return AjaxResult.success(dataMap);
	}

	/**
	 * 生成代碼（下載方式）
	 */
	@PreAuthorize("@ss.hasPermi('tool:gen:code')")
	@Log(title = "代碼生成", businessType = BusinessType.GENCODE)
	@GetMapping("/download/{tableName}")
	public void download(HttpServletResponse response, @PathVariable("tableName") String tableName) throws IOException {
		byte[] data = genTableService.downloadCode(tableName);
		genCode(response, data);
	}

	/**
	 * 生成代碼（自訂路徑）
	 */
	@PreAuthorize("@ss.hasPermi('tool:gen:code')")
	@Log(title = "代碼生成", businessType = BusinessType.GENCODE)
	@GetMapping("/genCode/{tableName}")
	public AjaxResult genCode(@PathVariable("tableName") String tableName) {
		genTableService.generatorCode(tableName);
		return AjaxResult.success();
	}

	/**
	 * 同步資料庫
	 */
	@PreAuthorize("@ss.hasPermi('tool:gen:edit')")
	@Log(title = "代碼生成", businessType = BusinessType.UPDATE)
	@GetMapping("/synchDb/{tableName}")
	public AjaxResult synchDb(@PathVariable("tableName") String tableName) {
		genTableService.synchDb(tableName);
		return AjaxResult.success();
	}

	/**
	 * 批次生成代碼
	 */
	@PreAuthorize("@ss.hasPermi('tool:gen:code')")
	@Log(title = "代碼生成", businessType = BusinessType.GENCODE)
	@GetMapping("/batchGenCode")
	public void batchGenCode(HttpServletResponse response, String tables) throws IOException {
		String[] tableNames = Convert.toStrArray(tables);
		byte[] data = genTableService.downloadCode(tableNames);
		genCode(response, data);
	}

	/**
	 * 生成zip檔案
	 */
	private void genCode(HttpServletResponse response, byte[] data) throws IOException {
		response.reset();
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
		response.setHeader("Content-Disposition", "attachment; filename=\"ruoyi.zip\"");
		response.addHeader("Content-Length", "" + data.length);
		response.setContentType("application/octet-stream; charset=UTF-8");
		IOUtils.write(data, response.getOutputStream());
	}
}
