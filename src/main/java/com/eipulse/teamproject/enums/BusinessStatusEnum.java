package com.eipulse.teamproject.enums;

import org.apache.poi.ss.formula.functions.T;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BusinessStatusEnum implements BaseEnum<T> {

	SUCCESS(200, "成功"), 
	ERROR(300, "失敗"), 
	FILE_NOT_EXIST(400, "檔案不存在"), 
	FILE_READ_ERROR(500, "檔案讀取失敗"),
	FILE_WRITE_ERROR(600, "檔案寫入失敗"), 
	FILE_UPLOAD_ERROR(700, "檔案上傳失敗'"), 
	TOKEN_NOT_EXIST(800, "token不存在，請重新登入"),
	TOKEN_INVALID(900, "token無效，請重新登入"), 
	DATA_IMPORT_ERROR(1000, "數據導入失敗"), 
	BATCH_DELETE_ERROR(1100, "批量刪除失敗"),
	STAFF_NOT_EXIST(1200, "無此員工，請重新登入"), 
	STAFF_STATUS_ERROR(1300, "狀態異常，請聯繫管理員");

	private final Integer code;
	private final String message;
}