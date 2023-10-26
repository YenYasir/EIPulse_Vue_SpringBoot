package com.eipulse.common.utils.poi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFPictureData;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTMarker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eipulse.common.annotation.Excel;
import com.eipulse.common.annotation.Excel.ColumnType;
import com.eipulse.common.annotation.Excel.Type;
import com.eipulse.common.annotation.Excels;
import com.eipulse.common.config.EipulseConfig;
import com.eipulse.common.core.domain.AjaxResult;
import com.eipulse.common.core.text.Convert;
import com.eipulse.common.exception.UtilException;
import com.eipulse.common.utils.DateUtils;
import com.eipulse.common.utils.DictUtils;
import com.eipulse.common.utils.StringUtils;
import com.eipulse.common.utils.file.FileTypeUtils;
import com.eipulse.common.utils.file.FileUtils;
import com.eipulse.common.utils.file.ImageUtils;
import com.eipulse.common.utils.reflect.ReflectUtils;

/**
 * Excel相關處理
 * 
 * @author eipulse
 */
public class ExcelUtil<T> {
	private static final Logger log = LoggerFactory.getLogger(ExcelUtil.class);

	/**
	 * Excel sheet最大行數，默認65536
	 */
	public static final int sheetSize = 65536;

	/**
	 * 工作表名稱
	 */
	private String sheetName;

	/**
	 * 導出類型（EXPORT:導出數據；IMPORT：導入模板）
	 */
	private Type type;

	/**
	 * 工作薄物件
	 */
	private Workbook wb;

	/**
	 * 工作表物件
	 */
	private Sheet sheet;

	/**
	 * 樣式列表
	 */
	private Map<String, CellStyle> styles;

	/**
	 * 導入導出數據列表
	 */
	private List<T> list;

	/**
	 * 註解列表
	 */
	private List<Object[]> fields;

	/**
	 * 當前行號
	 */
	private int rownum;

	/**
	 * 標題
	 */
	private String title;

	/**
	 * 最大高度
	 */
	private short maxHeight;

	/**
	 * 統計列表
	 */
	private Map<Integer, Double> statistics = new HashMap<Integer, Double>();

	/**
	 * 數字格式
	 */
	private static final DecimalFormat DOUBLE_FORMAT = new DecimalFormat("######0.00");

	/**
	 * 實體物件
	 */
	public Class<T> clazz;

	public ExcelUtil(Class<T> clazz) {
		this.clazz = clazz;
	}

	public void init(List<T> list, String sheetName, String title, Type type) {
		if (list == null) {
			list = new ArrayList<T>();
		}
		this.list = list;
		this.sheetName = sheetName;
		this.type = type;
		this.title = title;
		createExcelField();
		createWorkbook();
		createTitle();
	}

	/**
	 * 創建excel第一行標題
	 */
	public void createTitle() {
		if (StringUtils.isNotEmpty(title)) {
			Row titleRow = sheet.createRow(rownum == 0 ? rownum++ : 0);
			titleRow.setHeightInPoints(30);
			Cell titleCell = titleRow.createCell(0);
			titleCell.setCellStyle(styles.get("title"));
			titleCell.setCellValue(title);
			sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(), titleRow.getRowNum(), titleRow.getRowNum(),
					this.fields.size() - 1));
		}
	}

	/**
	 * 對excel表單默認第一個索引名轉換成list
	 * 
	 * @param is 輸入流
	 * @return 轉換後集合
	 */
	public List<T> importExcel(InputStream is) throws Exception {
		return importExcel(is, 0);
	}

	/**
	 * 對excel表單默認第一個索引名轉換成list
	 * 
	 * @param is       輸入流
	 * @param titleNum 標題占用行數
	 * @return 轉換後集合
	 */
	public List<T> importExcel(InputStream is, int titleNum) throws Exception {
		return importExcel(StringUtils.EMPTY, is, titleNum);
	}

	/**
	 * 對excel表單指定表格索引名轉換成list
	 * 
	 * @param sheetName 表格索引名
	 * @param titleNum  標題占用行數
	 * @param is        輸入流
	 * @return 轉換後集合
	 */
	public List<T> importExcel(String sheetName, InputStream is, int titleNum) throws Exception {
		this.type = Type.IMPORT;
		this.wb = WorkbookFactory.create(is);
		List<T> list = new ArrayList<T>();
		// 如果指定sheet名,則取指定sheet中的內容 否則默認指向第1個sheet
		Sheet sheet = StringUtils.isNotEmpty(sheetName) ? wb.getSheet(sheetName) : wb.getSheetAt(0);
		if (sheet == null) {
			throw new IOException("文件sheet不存在");
		}
		boolean isXSSFWorkbook = !(wb instanceof HSSFWorkbook);
		Map<String, PictureData> pictures;
		if (isXSSFWorkbook) {
			pictures = getSheetPictures07((XSSFSheet) sheet, (XSSFWorkbook) wb);
		} else {
			pictures = getSheetPictures03((HSSFSheet) sheet, (HSSFWorkbook) wb);
		}
		// 獲取最後一個非空行的行下標，比如總行數為n，則返回的為n-1
		int rows = sheet.getLastRowNum();

		if (rows > 0) {
			// 定義一個map用於存放excel列的序號和field.
			Map<String, Integer> cellMap = new HashMap<String, Integer>();
			// 獲取表頭
			Row heard = sheet.getRow(titleNum);
			for (int i = 0; i < heard.getPhysicalNumberOfCells(); i++) {
				Cell cell = heard.getCell(i);
				if (StringUtils.isNotNull(cell)) {
					String value = this.getCellValue(heard, i).toString();
					cellMap.put(value, i);
				} else {
					cellMap.put(null, i);
				}
			}
			// 有數據時才處理 得到類的所有field.
			List<Object[]> fields = this.getFields();
			Map<Integer, Object[]> fieldsMap = new HashMap<Integer, Object[]>();
			for (Object[] objects : fields) {
				Excel attr = (Excel) objects[1];
				Integer column = cellMap.get(attr.name());
				if (column != null) {
					fieldsMap.put(column, objects);
				}
			}
			for (int i = titleNum + 1; i <= rows; i++) {
				// 從第2行開始取數據,默認第一行是表頭.
				Row row = sheet.getRow(i);
				// 判斷當前行是否是空行
				if (isRowEmpty(row)) {
					continue;
				}
				T entity = null;
				for (Map.Entry<Integer, Object[]> entry : fieldsMap.entrySet()) {
					Object val = this.getCellValue(row, entry.getKey());

					// 如果不存在實例則新建.
					entity = (entity == null ? clazz.newInstance() : entity);
					// 從map中得到對應列的field.
					Field field = (Field) entry.getValue()[0];
					Excel attr = (Excel) entry.getValue()[1];
					// 取得類型,並根據物件類型設置值.
					Class<?> fieldType = field.getType();
					if (String.class == fieldType) {
						String s = Convert.toStr(val);
						if (StringUtils.endsWith(s, ".0")) {
							val = StringUtils.substringBefore(s, ".0");
						} else {
							String dateFormat = field.getAnnotation(Excel.class).dateFormat();
							if (StringUtils.isNotEmpty(dateFormat)) {
								val = DateUtils.parseDateToStr(dateFormat, (Date) val);
							} else {
								val = Convert.toStr(val);
							}
						}
					} else if ((Integer.TYPE == fieldType || Integer.class == fieldType)
							&& StringUtils.isNumeric(Convert.toStr(val))) {
						val = Convert.toInt(val);
					} else if (Long.TYPE == fieldType || Long.class == fieldType) {
						val = Convert.toLong(val);
					} else if (Double.TYPE == fieldType || Double.class == fieldType) {
						val = Convert.toDouble(val);
					} else if (Float.TYPE == fieldType || Float.class == fieldType) {
						val = Convert.toFloat(val);
					} else if (BigDecimal.class == fieldType) {
						val = Convert.toBigDecimal(val);
					} else if (Date.class == fieldType) {
						if (val instanceof String) {
							val = DateUtils.parseDate(val);
						} else if (val instanceof Double) {
							val = DateUtil.getJavaDate((Double) val);
						}
					} else if (Boolean.TYPE == fieldType || Boolean.class == fieldType) {
						val = Convert.toBool(val, false);
					}
					if (StringUtils.isNotNull(fieldType)) {
						String propertyName = field.getName();
						if (StringUtils.isNotEmpty(attr.targetAttr())) {
							propertyName = field.getName() + "." + attr.targetAttr();
						} else if (StringUtils.isNotEmpty(attr.readConverterExp())) {
							val = reverseByExp(Convert.toStr(val), attr.readConverterExp(), attr.separator());
						} else if (StringUtils.isNotEmpty(attr.dictType())) {
							val = reverseDictByExp(Convert.toStr(val), attr.dictType(), attr.separator());
						} else if (!attr.handler().equals(ExcelHandlerAdapter.class)) {
							val = dataFormatHandlerAdapter(val, attr);
						} else if (ColumnType.IMAGE == attr.cellType() && StringUtils.isNotEmpty(pictures)) {
							PictureData image = pictures.get(row.getRowNum() + "_" + entry.getKey());
							if (image == null) {
								val = "";
							} else {
								byte[] data = image.getData();
								val = FileUtils.writeImportBytes(data);
							}
						}
						ReflectUtils.invokeSetter(entity, propertyName, val);
					}
				}
				list.add(entity);
			}
		}
		return list;
	}

	/**
	 * 對list數據源將其裏面的數據導入到excel表單
	 * 
	 * @param list      導出數據集合
	 * @param sheetName 工作表的名稱
	 * @return 結果
	 */
	public AjaxResult exportExcel(List<T> list, String sheetName) {
		return exportExcel(list, sheetName, StringUtils.EMPTY);
	}

	/**
	 * 對list數據源將其裏面的數據導入到excel表單
	 * 
	 * @param list      導出數據集合
	 * @param sheetName 工作表的名稱
	 * @param title     標題
	 * @return 結果
	 */
	public AjaxResult exportExcel(List<T> list, String sheetName, String title) {
		this.init(list, sheetName, title, Type.EXPORT);
		return exportExcel();
	}

	/**
	 * 對list數據源將其裏面的數據導入到excel表單
	 * 
	 * @param response  返回數據
	 * @param list      導出數據集合
	 * @param sheetName 工作表的名稱
	 * @return 結果
	 * @throws IOException
	 */
	public void exportExcel(HttpServletResponse response, List<T> list, String sheetName) throws IOException {
		exportExcel(response, list, sheetName, StringUtils.EMPTY);
	}

	/**
	 * 對list數據源將其裏面的數據導入到excel表單
	 * 
	 * @param response  返回數據
	 * @param list      導出數據集合
	 * @param sheetName 工作表的名稱
	 * @param title     標題
	 * @return 結果
	 * @throws IOException
	 */
	public void exportExcel(HttpServletResponse response, List<T> list, String sheetName, String title)
			throws IOException {
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setCharacterEncoding("utf-8");
		this.init(list, sheetName, title, Type.EXPORT);
		exportExcel(response.getOutputStream());
	}

	/**
	 * 對list數據源將其裏面的數據導入到excel表單
	 * 
	 * @param sheetName 工作表的名稱
	 * @return 結果
	 */
	public AjaxResult importTemplateExcel(String sheetName) {
		return importTemplateExcel(sheetName, StringUtils.EMPTY);
	}

	/**
	 * 對list數據源將其裏面的數據導入到excel表單
	 * 
	 * @param sheetName 工作表的名稱
	 * @param title     標題
	 * @return 結果
	 */
	public AjaxResult importTemplateExcel(String sheetName, String title) {
		this.init(null, sheetName, title, Type.IMPORT);
		return exportExcel();
	}

	/**
	 * 對list數據源將其裏面的數據導入到excel表單
	 * 
	 * @param sheetName 工作表的名稱
	 * @return 結果
	 */
	public void importTemplateExcel(HttpServletResponse response, String sheetName) throws IOException {
		importTemplateExcel(response, sheetName, StringUtils.EMPTY);
	}

	/**
	 * 對list數據源將其裏面的數據導入到excel表單
	 * 
	 * @param sheetName 工作表的名稱
	 * @param title     標題
	 * @return 結果
	 */
	public void importTemplateExcel(HttpServletResponse response, String sheetName, String title) throws IOException {
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setCharacterEncoding("utf-8");
		this.init(null, sheetName, title, Type.IMPORT);
		exportExcel(response.getOutputStream());
	}

	/**
	 * 對list數據源將其裏面的數據導入到excel表單
	 * 
	 * @return 結果
	 */
	public void exportExcel(OutputStream out) {
		try {
			writeSheet();
			wb.write(out);
		} catch (Exception e) {
			log.error("導出Excel異常{}", e.getMessage());
		} finally {
			IOUtils.closeQuietly(wb);
			IOUtils.closeQuietly(out);
		}
	}

	/**
	 * 對list數據源將其裏面的數據導入到excel表單
	 * 
	 * @return 結果
	 */
	public AjaxResult exportExcel() {
		OutputStream out = null;
		try {
			writeSheet();
			String filename = encodingFilename(sheetName);
			out = new FileOutputStream(getAbsoluteFile(filename));
			wb.write(out);
			return AjaxResult.success(filename);
		} catch (Exception e) {
			log.error("導出Excel異常{}", e.getMessage());
			throw new UtilException("導出Excel失敗，請聯系網站管理員！");
		} finally {
			IOUtils.closeQuietly(wb);
			IOUtils.closeQuietly(out);
		}
	}

	/**
	 * 創建寫入數據到Sheet
	 */
	public void writeSheet() {
		// 取出一共有多少個sheet.
		int sheetNo = Math.max(1, (int) Math.ceil(list.size() * 1.0 / sheetSize));
		for (int index = 0; index < sheetNo; index++) {
			createSheet(sheetNo, index);

			// 產生一行
			Row row = sheet.createRow(rownum);
			int column = 0;
			// 寫入各個字段的列頭名稱
			for (Object[] os : fields) {
				Excel excel = (Excel) os[1];
				this.createCell(excel, row, column++);
			}
			if (Type.EXPORT.equals(type)) {
				fillExcelData(index, row);
				addStatisticsRow();
			}
		}
	}

	/**
	 * 填充excel數據
	 * 
	 * @param index 序號
	 * @param row   單元格行
	 */
	public void fillExcelData(int index, Row row) {
		int startNo = index * sheetSize;
		int endNo = Math.min(startNo + sheetSize, list.size());
		for (int i = startNo; i < endNo; i++) {
			row = sheet.createRow(i + 1 + rownum - startNo);
			// 得到導出物件.
			T vo = list.get(i);
			int column = 0;
			for (Object[] os : fields) {
				Field field = (Field) os[0];
				Excel excel = (Excel) os[1];
				this.addCell(excel, row, vo, field, column++);
			}
		}
	}

	/**
	 * 創建表格樣式
	 * 
	 * @param wb 工作薄物件
	 * @return 樣式列表
	 */
	private Map<String, CellStyle> createStyles(Workbook wb) {
		// 寫入各條記錄,每條記錄對應excel表中的一行
		Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
		CellStyle style = wb.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		Font titleFont = wb.createFont();
		titleFont.setFontName("Arial");
		titleFont.setFontHeightInPoints((short) 16);
		titleFont.setBold(true);
		style.setFont(titleFont);
		styles.put("title", style);

		style = wb.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setBorderRight(BorderStyle.THIN);
		style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderLeft(BorderStyle.THIN);
		style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderTop(BorderStyle.THIN);
		style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderBottom(BorderStyle.THIN);
		style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		Font dataFont = wb.createFont();
		dataFont.setFontName("Arial");
		dataFont.setFontHeightInPoints((short) 10);
		style.setFont(dataFont);
		styles.put("data", style);

		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		Font headerFont = wb.createFont();
		headerFont.setFontName("Arial");
		headerFont.setFontHeightInPoints((short) 10);
		headerFont.setBold(true);
		headerFont.setColor(IndexedColors.WHITE.getIndex());
		style.setFont(headerFont);
		styles.put("header", style);

		style = wb.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		Font totalFont = wb.createFont();
		totalFont.setFontName("Arial");
		totalFont.setFontHeightInPoints((short) 10);
		style.setFont(totalFont);
		styles.put("total", style);

		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		style.setAlignment(HorizontalAlignment.LEFT);
		styles.put("data1", style);

		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		style.setAlignment(HorizontalAlignment.CENTER);
		styles.put("data2", style);

		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		style.setAlignment(HorizontalAlignment.RIGHT);
		styles.put("data3", style);

		return styles;
	}

	/**
	 * 創建單元格
	 */
	public Cell createCell(Excel attr, Row row, int column) {
		// 創建列
		Cell cell = row.createCell(column);
		// 寫入列信息
		cell.setCellValue(attr.name());
		setDataValidation(attr, row, column);
		cell.setCellStyle(styles.get("header"));
		return cell;
	}

	/**
	 * 設置單元格信息
	 * 
	 * @param value 單元格值
	 * @param attr  註解相關
	 * @param cell  單元格信息
	 */
	public void setCellVo(Object value, Excel attr, Cell cell) {
		if (ColumnType.STRING == attr.cellType()) {
			cell.setCellValue(StringUtils.isNull(value) ? attr.defaultValue() : value + attr.suffix());
		} else if (ColumnType.NUMERIC == attr.cellType()) {
			if (StringUtils.isNotNull(value)) {
				cell.setCellValue(StringUtils.contains(Convert.toStr(value), ".") ? Convert.toDouble(value)
						: Convert.toInt(value));
			}
		} else if (ColumnType.IMAGE == attr.cellType()) {
			ClientAnchor anchor = new XSSFClientAnchor(0, 0, 0, 0, (short) cell.getColumnIndex(),
					cell.getRow().getRowNum(), (short) (cell.getColumnIndex() + 1), cell.getRow().getRowNum() + 1);
			String imagePath = Convert.toStr(value);
			if (StringUtils.isNotEmpty(imagePath)) {
				byte[] data = ImageUtils.getImage(imagePath);
				getDrawingPatriarch(cell.getSheet()).createPicture(anchor,
						cell.getSheet().getWorkbook().addPicture(data, getImageType(data)));
			}
		}
	}

	/**
	 * 獲取畫布
	 */
	public static Drawing<?> getDrawingPatriarch(Sheet sheet) {
		if (sheet.getDrawingPatriarch() == null) {
			sheet.createDrawingPatriarch();
		}
		return sheet.getDrawingPatriarch();
	}

	/**
	 * 獲取圖片類型,設置圖片插入類型
	 */
	public int getImageType(byte[] value) {
		String type = FileTypeUtils.getFileExtendName(value);
		if ("JPG".equalsIgnoreCase(type)) {
			return Workbook.PICTURE_TYPE_JPEG;
		} else if ("PNG".equalsIgnoreCase(type)) {
			return Workbook.PICTURE_TYPE_PNG;
		}
		return Workbook.PICTURE_TYPE_JPEG;
	}

	/**
	 * 創建表格樣式
	 */
	public void setDataValidation(Excel attr, Row row, int column) {
		if (attr.name().indexOf("註：") >= 0) {
			sheet.setColumnWidth(column, 6000);
		} else {
			// 設置列寬
			sheet.setColumnWidth(column, (int) ((attr.width() + 0.72) * 256));
		}
		// 如果設置了提示信息則鼠標放上去提示.
		if (StringUtils.isNotEmpty(attr.prompt())) {
			// 這裏默認設了2-101列提示.
			setXSSFPrompt(sheet, "", attr.prompt(), 1, 100, column, column);
		}
		// 如果設置了combo屬性則本列只能選擇不能輸入
		if (attr.combo().length > 0) {
			// 這裏默認設了2-101列只能選擇不能輸入.
			setXSSFValidation(sheet, attr.combo(), 1, 100, column, column);
		}
	}

	/**
	 * 添加單元格
	 */
	public Cell addCell(Excel attr, Row row, T vo, Field field, int column) {
		Cell cell = null;
		try {
			// 設置行高
			row.setHeight(maxHeight);
			// 根據Excel中設置情況決定是否導出,有些情況需要保持為空,希望用戶填寫這一列.
			if (attr.isExport()) {
				// 創建cell
				cell = row.createCell(column);
				int align = attr.align().value();
				cell.setCellStyle(styles.get("data" + (align >= 1 && align <= 3 ? align : "")));

				// 用於讀取物件中的屬性
				Object value = getTargetValue(vo, field, attr);
				String dateFormat = attr.dateFormat();
				String readConverterExp = attr.readConverterExp();
				String separator = attr.separator();
				String dictType = attr.dictType();
				if (StringUtils.isNotEmpty(dateFormat) && StringUtils.isNotNull(value)) {
					cell.setCellValue(DateUtils.parseDateToStr(dateFormat, (Date) value));
				} else if (StringUtils.isNotEmpty(readConverterExp) && StringUtils.isNotNull(value)) {
					cell.setCellValue(convertByExp(Convert.toStr(value), readConverterExp, separator));
				} else if (StringUtils.isNotEmpty(dictType) && StringUtils.isNotNull(value)) {
					cell.setCellValue(convertDictByExp(Convert.toStr(value), dictType, separator));
				} else if (value instanceof BigDecimal && -1 != attr.scale()) {
					cell.setCellValue((((BigDecimal) value).setScale(attr.scale(), attr.roundingMode())).toString());
				} else if (!attr.handler().equals(ExcelHandlerAdapter.class)) {
					cell.setCellValue(dataFormatHandlerAdapter(value, attr));
				} else {
					// 設置列類型
					setCellVo(value, attr, cell);
				}
				addStatisticsData(column, Convert.toStr(value), attr);
			}
		} catch (Exception e) {
			log.error("導出Excel失敗{}", e);
		}
		return cell;
	}

	/**
	 * 設置 POI XSSFSheet 單元格提示
	 * 
	 * @param sheet         表單
	 * @param promptTitle   提示標題
	 * @param promptContent 提示內容
	 * @param firstRow      開始行
	 * @param endRow        結束行
	 * @param firstCol      開始列
	 * @param endCol        結束列
	 */
	public void setXSSFPrompt(Sheet sheet, String promptTitle, String promptContent, int firstRow, int endRow,
			int firstCol, int endCol) {
		DataValidationHelper helper = sheet.getDataValidationHelper();
		DataValidationConstraint constraint = helper.createCustomConstraint("DD1");
		CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
		DataValidation dataValidation = helper.createValidation(constraint, regions);
		dataValidation.createPromptBox(promptTitle, promptContent);
		dataValidation.setShowPromptBox(true);
		sheet.addValidationData(dataValidation);
	}

	/**
	 * 設置某些列的值只能輸入預制的數據,顯示下拉框.
	 * 
	 * @param sheet    要設置的sheet.
	 * @param textlist 下拉框顯示的內容
	 * @param firstRow 開始行
	 * @param endRow   結束行
	 * @param firstCol 開始列
	 * @param endCol   結束列
	 * @return 設置好的sheet.
	 */
	public void setXSSFValidation(Sheet sheet, String[] textlist, int firstRow, int endRow, int firstCol, int endCol) {
		DataValidationHelper helper = sheet.getDataValidationHelper();
		// 加載下拉列表內容
		DataValidationConstraint constraint = helper.createExplicitListConstraint(textlist);
		// 設置數據有效性加載在哪個單元格上,四個參數分別是：起始行、終止行、起始列、終止列
		CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
		// 數據有效性物件
		DataValidation dataValidation = helper.createValidation(constraint, regions);
		// 處理Excel兼容性問題
		if (dataValidation instanceof XSSFDataValidation) {
			dataValidation.setSuppressDropDownArrow(true);
			dataValidation.setShowErrorBox(true);
		} else {
			dataValidation.setSuppressDropDownArrow(false);
		}

		sheet.addValidationData(dataValidation);
	}

	/**
	 * 解析導出值 0=男,1=女,2=未知
	 * 
	 * @param propertyValue 參數值
	 * @param converterExp  翻譯註解
	 * @param separator     分隔符
	 * @return 解析後值
	 */
	public static String convertByExp(String propertyValue, String converterExp, String separator) {
		StringBuilder propertyString = new StringBuilder();
		String[] convertSource = converterExp.split(",");
		for (String item : convertSource) {
			String[] itemArray = item.split("=");
			if (StringUtils.containsAny(separator, propertyValue)) {
				for (String value : propertyValue.split(separator)) {
					if (itemArray[0].equals(value)) {
						propertyString.append(itemArray[1] + separator);
						break;
					}
				}
			} else {
				if (itemArray[0].equals(propertyValue)) {
					return itemArray[1];
				}
			}
		}
		return StringUtils.stripEnd(propertyString.toString(), separator);
	}

	/**
	 * 反向解析值 男=0,女=1,未知=2
	 * 
	 * @param propertyValue 參數值
	 * @param converterExp  翻譯註解
	 * @param separator     分隔符
	 * @return 解析後值
	 */
	public static String reverseByExp(String propertyValue, String converterExp, String separator) {
		StringBuilder propertyString = new StringBuilder();
		String[] convertSource = converterExp.split(",");
		for (String item : convertSource) {
			String[] itemArray = item.split("=");
			if (StringUtils.containsAny(separator, propertyValue)) {
				for (String value : propertyValue.split(separator)) {
					if (itemArray[1].equals(value)) {
						propertyString.append(itemArray[0] + separator);
						break;
					}
				}
			} else {
				if (itemArray[1].equals(propertyValue)) {
					return itemArray[0];
				}
			}
		}
		return StringUtils.stripEnd(propertyString.toString(), separator);
	}

	/**
	 * 解析字典值
	 * 
	 * @param dictValue 字典值
	 * @param dictType  字典類型
	 * @param separator 分隔符
	 * @return 字典標簽
	 */
	public static String convertDictByExp(String dictValue, String dictType, String separator) {
		return DictUtils.getDictLabel(dictType, dictValue, separator);
	}

	/**
	 * 反向解析值字典值
	 * 
	 * @param dictLabel 字典標簽
	 * @param dictType  字典類型
	 * @param separator 分隔符
	 * @return 字典值
	 */
	public static String reverseDictByExp(String dictLabel, String dictType, String separator) {
		return DictUtils.getDictValue(dictType, dictLabel, separator);
	}

	/**
	 * 數據處理器
	 * 
	 * @param value 數據值
	 * @param excel 數據註解
	 * @return
	 */
	public String dataFormatHandlerAdapter(Object value, Excel excel) {
		try {
			Object instance = excel.handler().newInstance();
			Method formatMethod = excel.handler().getMethod("format", new Class[] { Object.class, String[].class });
			value = formatMethod.invoke(instance, value, excel.args());
		} catch (Exception e) {
			log.error("不能格式化數據 " + excel.handler(), e.getMessage());
		}
		return Convert.toStr(value);
	}

	/**
	 * 合計統計信息
	 */
	private void addStatisticsData(Integer index, String text, Excel entity) {
		if (entity != null && entity.isStatistics()) {
			Double temp = 0D;
			if (!statistics.containsKey(index)) {
				statistics.put(index, temp);
			}
			try {
				temp = Double.valueOf(text);
			} catch (NumberFormatException e) {
			}
			statistics.put(index, statistics.get(index) + temp);
		}
	}

	/**
	 * 創建統計行
	 */
	public void addStatisticsRow() {
		if (statistics.size() > 0) {
			Row row = sheet.createRow(sheet.getLastRowNum() + 1);
			Set<Integer> keys = statistics.keySet();
			Cell cell = row.createCell(0);
			cell.setCellStyle(styles.get("total"));
			cell.setCellValue("合計");

			for (Integer key : keys) {
				cell = row.createCell(key);
				cell.setCellStyle(styles.get("total"));
				cell.setCellValue(DOUBLE_FORMAT.format(statistics.get(key)));
			}
			statistics.clear();
		}
	}

	/**
	 * 編碼文件名
	 */
	public String encodingFilename(String filename) {
		filename = UUID.randomUUID().toString() + "_" + filename + ".xlsx";
		return filename;
	}

	/**
	 * 獲取下載路徑
	 * 
	 * @param filename 文件名稱
	 */
	public String getAbsoluteFile(String filename) {
		String downloadPath = EipulseConfig.getDownloadPath() + filename;
		File desc = new File(downloadPath);
		if (!desc.getParentFile().exists()) {
			desc.getParentFile().mkdirs();
		}
		return downloadPath;
	}

	/**
	 * 獲取bean中的屬性值
	 * 
	 * @param vo    實體物件
	 * @param field 字段
	 * @param excel 註解
	 * @return 最終的屬性值
	 * @throws Exception
	 */
	private Object getTargetValue(T vo, Field field, Excel excel) throws Exception {
		Object o = field.get(vo);
		if (StringUtils.isNotEmpty(excel.targetAttr())) {
			String target = excel.targetAttr();
			if (target.indexOf(".") > -1) {
				String[] targets = target.split("[.]");
				for (String name : targets) {
					o = getValue(o, name);
				}
			} else {
				o = getValue(o, target);
			}
		}
		return o;
	}

	/**
	 * 以類的屬性的get方法方法形式獲取值
	 * 
	 * @param o
	 * @param name
	 * @return value
	 * @throws Exception
	 */
	private Object getValue(Object o, String name) throws Exception {
		if (StringUtils.isNotNull(o) && StringUtils.isNotEmpty(name)) {
			Class<?> clazz = o.getClass();
			Field field = clazz.getDeclaredField(name);
			field.setAccessible(true);
			o = field.get(o);
		}
		return o;
	}

	/**
	 * 得到所有定義字段
	 */
	private void createExcelField() {
		this.fields = getFields();
		this.fields = this.fields.stream().sorted(Comparator.comparing(objects -> ((Excel) objects[1]).sort()))
				.collect(Collectors.toList());
		this.maxHeight = getRowHeight();
	}

	/**
	 * 獲取字段註解信息
	 */
	public List<Object[]> getFields() {
		List<Object[]> fields = new ArrayList<Object[]>();
		List<Field> tempFields = new ArrayList<>();
		tempFields.addAll(Arrays.asList(clazz.getSuperclass().getDeclaredFields()));
		tempFields.addAll(Arrays.asList(clazz.getDeclaredFields()));
		for (Field field : tempFields) {
			// 單註解
			if (field.isAnnotationPresent(Excel.class)) {
				Excel attr = field.getAnnotation(Excel.class);
				if (attr != null && (attr.type() == Type.ALL || attr.type() == type)) {
					field.setAccessible(true);
					fields.add(new Object[] { field, attr });
				}
			}

			// 多註解
			if (field.isAnnotationPresent(Excels.class)) {
				Excels attrs = field.getAnnotation(Excels.class);
				Excel[] excels = attrs.value();
				for (Excel attr : excels) {
					if (attr != null && (attr.type() == Type.ALL || attr.type() == type)) {
						field.setAccessible(true);
						fields.add(new Object[] { field, attr });
					}
				}
			}
		}
		return fields;
	}

	/**
	 * 根據註解獲取最大行高
	 */
	public short getRowHeight() {
		double maxHeight = 0;
		for (Object[] os : this.fields) {
			Excel excel = (Excel) os[1];
			maxHeight = maxHeight > excel.height() ? maxHeight : excel.height();
		}
		return (short) (maxHeight * 20);
	}

	/**
	 * 創建一個工作簿
	 */
	public void createWorkbook() {
		this.wb = new SXSSFWorkbook(500);
		this.sheet = wb.createSheet();
		wb.setSheetName(0, sheetName);
		this.styles = createStyles(wb);
	}

	/**
	 * 創建工作表
	 * 
	 * @param sheetNo sheet數量
	 * @param index   序號
	 */
	public void createSheet(int sheetNo, int index) {
		// 設置工作表的名稱.
		if (sheetNo > 1 && index > 0) {
			this.sheet = wb.createSheet();
			this.createTitle();
			wb.setSheetName(index, sheetName + index);
		}
	}

	/**
	 * 獲取單元格值
	 * 
	 * @param row    獲取的行
	 * @param column 獲取單元格列號
	 * @return 單元格值
	 */
	public Object getCellValue(Row row, int column) {
		if (row == null) {
			return row;
		}
		Object val = "";
		try {
			Cell cell = row.getCell(column);
			if (StringUtils.isNotNull(cell)) {
				if (cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA) {
					val = cell.getNumericCellValue();
					if (DateUtil.isCellDateFormatted(cell)) {
						val = DateUtil.getJavaDate((Double) val); // POI Excel 日期格式轉換
					} else {
						if ((Double) val % 1 != 0) {
							val = new BigDecimal(val.toString());
						} else {
							val = new DecimalFormat("0").format(val);
						}
					}
				} else if (cell.getCellType() == CellType.STRING) {
					val = cell.getStringCellValue();
				} else if (cell.getCellType() == CellType.BOOLEAN) {
					val = cell.getBooleanCellValue();
				} else if (cell.getCellType() == CellType.ERROR) {
					val = cell.getErrorCellValue();
				}

			}
		} catch (Exception e) {
			return val;
		}
		return val;
	}

	/**
	 * 判斷是否是空行
	 * 
	 * @param row 判斷的行
	 * @return
	 */
	private boolean isRowEmpty(Row row) {
		if (row == null) {
			return true;
		}
		for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
			Cell cell = row.getCell(i);
			if (cell != null && cell.getCellType() != CellType.BLANK) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 獲取Excel2003圖片
	 *
	 * @param sheet    當前sheet物件
	 * @param workbook 工作簿物件
	 * @return Map key:圖片單元格索引（1_1）String，value:圖片流PictureData
	 */
	public static Map<String, PictureData> getSheetPictures03(HSSFSheet sheet, HSSFWorkbook workbook) {
		Map<String, PictureData> sheetIndexPicMap = new HashMap<String, PictureData>();
		List<HSSFPictureData> pictures = workbook.getAllPictures();
		if (!pictures.isEmpty()) {
			for (HSSFShape shape : sheet.getDrawingPatriarch().getChildren()) {
				HSSFClientAnchor anchor = (HSSFClientAnchor) shape.getAnchor();
				if (shape instanceof HSSFPicture) {
					HSSFPicture pic = (HSSFPicture) shape;
					int pictureIndex = pic.getPictureIndex() - 1;
					HSSFPictureData picData = pictures.get(pictureIndex);
					String picIndex = String.valueOf(anchor.getRow1()) + "_" + String.valueOf(anchor.getCol1());
					sheetIndexPicMap.put(picIndex, picData);
				}
			}
			return sheetIndexPicMap;
		} else {
			return sheetIndexPicMap;
		}
	}

	/**
	 * 獲取Excel2007圖片
	 *
	 * @param sheet    當前sheet物件
	 * @param workbook 工作簿物件
	 * @return Map key:圖片單元格索引（1_1）String，value:圖片流PictureData
	 */
	public static Map<String, PictureData> getSheetPictures07(XSSFSheet sheet, XSSFWorkbook workbook) {
		Map<String, PictureData> sheetIndexPicMap = new HashMap<String, PictureData>();
		for (POIXMLDocumentPart dr : sheet.getRelations()) {
			if (dr instanceof XSSFDrawing) {
				XSSFDrawing drawing = (XSSFDrawing) dr;
				List<XSSFShape> shapes = drawing.getShapes();
				for (XSSFShape shape : shapes) {
					if (shape instanceof XSSFPicture) {
						XSSFPicture pic = (XSSFPicture) shape;
						XSSFClientAnchor anchor = pic.getPreferredSize();
						CTMarker ctMarker = anchor.getFrom();
						String picIndex = ctMarker.getRow() + "_" + ctMarker.getCol();
						sheetIndexPicMap.put(picIndex, pic.getPictureData());
					}
				}
			}
		}
		return sheetIndexPicMap;
	}
}
