package com.emapper.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.emapper.db.SQLiteAssetHelper;
import com.emapper.entity.PositionEntity;
import com.emapper.util.SysConstant;

public class PositionDAO extends SQLiteAssetHelper {

	// 外部数据库
	private static final String DATABASE_NAME = "EMapper.db3";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLENAME = "position";
	SQLiteDatabase db;

	public PositionDAO(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public void Add(PositionEntity entity) {
		db = getReadableDatabase();
		db.execSQL(
				"insert into "
						+ TABLENAME
						+ "(lon,lat,description,datetime,state,speed,direction) values(?,?,?,?,?,?,?)",
				new Object[] { entity.getLon(), entity.getLat(),
						entity.getDescription(), entity.getDatetime(),
						entity.getState(), entity.getSpeed(),
						entity.getDirction() });
		db.close();
	}

	public void Delete() {

	}
	public void clear() {
		db = getReadableDatabase();
		db.execSQL("delete from " + TABLENAME);
		db.close();
	}
	public void Update(PositionEntity entity) {
		db = getReadableDatabase();
		db.execSQL(
				"update "
						+ TABLENAME
						+ " set lon=?,lat=?,description=?,datetime=?,state=?,speed=?,direction=? where ID = ?",
				new Object[] { entity.getLon(), entity.getLat(),
						entity.getDescription(), entity.getDatetime(),
						entity.getState(), entity.getSpeed(),
						entity.getDirction(), entity.getId() });
		db.close();
	}

	/**
	 * 根据位置点上传状态获取位置点集
	 * 
	 * @param state
	 * @return
	 */
	public List<PositionEntity> GetData(int state) {
		List<PositionEntity> collects = new ArrayList<PositionEntity>();
		db = getReadableDatabase();
		String sql = "select * from " + TABLENAME + " where state = ?";
		Cursor cursor = db
				.rawQuery(sql, new String[] { String.valueOf(state) });
		try {
			while (cursor.moveToNext()) {
				collects.add(new PositionEntity(cursor.getInt(cursor
						.getColumnIndex("ID")), cursor.getString(cursor
						.getColumnIndex("description")), cursor
						.getDouble(cursor.getColumnIndex("lon")), cursor
						.getDouble(cursor.getColumnIndex("lat")), cursor
						.getLong(cursor.getColumnIndex("datetime")), cursor
						.getInt(cursor.getColumnIndex("state")), cursor
						.getDouble(cursor.getColumnIndex("speed")), cursor
						.getDouble(cursor.getColumnIndex("direction"))));
			}
		} finally {
			cursor.close();
		}
		return collects;
	}

	public List<PositionEntity> Find(long form, long to) {
		List<PositionEntity> collects = new ArrayList<PositionEntity>();
		db = getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from " + TABLENAME
				+ " where datetime " + " BETWEEN ? AND ?", new String[] {
				String.valueOf(form), String.valueOf(to) });
		// Cursor cursor=db.rawQuery("select * from "+TABLENAME, null);
		try {
			while (cursor.moveToNext()) {
				collects.add(new PositionEntity(cursor.getInt(cursor
						.getColumnIndex("ID")), cursor.getString(cursor
						.getColumnIndex("description")), cursor
						.getDouble(cursor.getColumnIndex("lon")), cursor
						.getDouble(cursor.getColumnIndex("lat")), cursor
						.getLong(cursor.getColumnIndex("datetime")), cursor
						.getInt(cursor.getColumnIndex("state")), cursor
						.getDouble(cursor.getColumnIndex("speed")), cursor
						.getDouble(cursor.getColumnIndex("direction"))));
			}
		} finally {
			cursor.close();
		}
		return collects;
	}

	public PositionEntity Find(int id) {
		db = getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from " + TABLENAME
				+ " where ID = ?", new String[] { String.valueOf(id) });
		try {
			if (cursor.moveToNext()) {
				return new PositionEntity(cursor.getInt(cursor
						.getColumnIndex("ID")), cursor.getString(cursor
						.getColumnIndex("description")),
						cursor.getDouble(cursor.getColumnIndex("lon")),
						cursor.getDouble(cursor.getColumnIndex("lat")),
						cursor.getLong(cursor.getColumnIndex("datetime")),
						cursor.getInt(cursor.getColumnIndex("state")),
						cursor.getDouble(cursor.getColumnIndex("speed")),
						cursor.getDouble(cursor.getColumnIndex("direction")));
			}
		} finally {
			cursor.close();
		}
		return null;
	}

	public List<PositionEntity> GetScrollData(int start, int count) {

		List<PositionEntity> collects = new ArrayList<PositionEntity>();
		db = getReadableDatabase();
		Cursor cursor = db.rawQuery(
				"select * from " + TABLENAME + " limit ?,?", new String[] {
						String.valueOf(start), String.valueOf(count) });
		try {
			while (cursor.moveToNext()) {
				collects.add(new PositionEntity(cursor.getInt(cursor
						.getColumnIndex("ID")), cursor.getString(cursor
						.getColumnIndex("description")), cursor
						.getDouble(cursor.getColumnIndex("lon")), cursor
						.getDouble(cursor.getColumnIndex("lat")), cursor
						.getLong(cursor.getColumnIndex("datetime")), cursor
						.getInt(cursor.getColumnIndex("state")), cursor
						.getDouble(cursor.getColumnIndex("speed")), cursor
						.getDouble(cursor.getColumnIndex("direction"))));
			}
		} finally {
			cursor.close();
		}
		return collects;
	}

	public long GetCount() {
		db = getReadableDatabase();
		Cursor cursor = db.rawQuery("select count(ID) from " + TABLENAME, null);
		try {
			if (cursor.moveToNext()) {
				return cursor.getLong(0);
			}
		} finally {
			cursor.close();
		}
		return 0;
	}
	
	/**
	 * 获取当前存储的百分比
	 * @return
	 */
	public int GetPercent(){
		db = getReadableDatabase();
		Cursor cursor = db.rawQuery("select count(ID) from " + TABLENAME, null);
		try {
			if (cursor.moveToNext()) {
				return (int)(cursor.getLong(0)*100)/SysConstant.TRAIL_RELATE.TOTALCOUNT;
			}
		} finally {
			cursor.close();
		}
		return 0;
	}
}
