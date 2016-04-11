package com.emapper.db;

import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 数据字典查询帮助类
 * 
 * @author lijun.xu
 * 
 */
public class SelectedDataDBHelper extends SQLiteAssetHelper{
	private static final String DATABASE_NAME = "db_data.db";
	private static final int DATABASE_VERSION = 1;
	
	private static final String VALUEQSQL = "SELECT NAME FROM TB_DATA_INFO WHERE ID = ?"; // 通过uuid查询器值
	private static final String UUIDQSQL = "SELECT ID FROM TB_DATA_INFO WHERE NAME = ?"; // 通过值查询器uuid
	private static final String SEQNOSQL = "SELECT SEQNO FROM TB_DATA_INFO WHERE NAME = ?"; // 通过值查询下拉序列值
	private static final String SEQNOSQLBYUUID = "SELECT SEQNO FROM TB_DATA_INFO WHERE ID = ?"; // 通过uuid查询器下拉序列值
	private static final String SELECTEDQSQL = "SELECT NAME FROM TB_DATA_INFO WHERE TYPEID = ? ORDER BY SEQNO ASC";
	
	
	public SelectedDataDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * 通过uuid查询器对应下拉中文字符串
	 * 
	 * @param uuid
	 *            uuid值
	 * @return
	 */
	public String queryValue(String uuid) {
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery(VALUEQSQL, new String[] { uuid });
		try {
			if (cursor != null && cursor.moveToFirst()) {
				String name = cursor.getString(cursor.getColumnIndex("name"));
				return name;
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return null;
	}

	/**
	 * 通过下拉中文字符串查询对应的uuid值
	 * 
	 * @param value
	 *            中文字符串
	 * @return
	 */
	public String queryUUID(String value) {
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery(UUIDQSQL, new String[] { value });
		try {
			if (cursor != null && cursor.moveToFirst()) {
				String uuid = cursor.getString(cursor.getColumnIndex("id"));
				return uuid;
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return null;
	}

	public int querySeqnoByValue(String value) {
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery(SEQNOSQL, new String[] { value });
		try {
			if (cursor != null && cursor.moveToFirst()) {
				int seqid = cursor.getInt(cursor.getColumnIndex("seqno"));
				return seqid;
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return 0;
	}
	
	public int querySeqnoByUUID(String uuid){
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery(SEQNOSQLBYUUID, new String[] { uuid });
		try {
			if (cursor != null && cursor.moveToFirst()) {
				int seqid = cursor.getInt(cursor.getColumnIndex("seqno"));
				return seqid;
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return 0;
	}
	
	
	
	
	
	public List<String> queryAllSelectedValue(String parentUUID) {

		return null;
	}	

}
