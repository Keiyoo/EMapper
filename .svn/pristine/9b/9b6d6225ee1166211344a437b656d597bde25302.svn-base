package com.emapper.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.emapper.db.SQLiteAssetHelper;
import com.emapper.entity.TrailEntity;

public class TrailDAO extends SQLiteAssetHelper {
	// 外部数据库
	private static final String DATABASE_NAME = "EMapper.db3";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLENAME = "trail";
	SQLiteDatabase db;

	public TrailDAO(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public void Add(TrailEntity entity) {
		db = getReadableDatabase();
		db.execSQL(
				"insert into "
						+ TABLENAME
						+ " (name, description, starttime,endtime, length) values(?,?,?,?,?)",
				new Object[] { entity.getName(), entity.getDescription(),
						entity.getStarttime(), entity.getEndtime(),
						entity.getLength() });
		db.close();
	}

	public void clear() {
		db = getReadableDatabase();
		db.execSQL("delete from " + TABLENAME);
		db.close();
	}

	public void delete(int id) {
		db = getReadableDatabase();
		db.execSQL(" delete from " + TABLENAME + " where ID = ?",
				new String[] { String.valueOf(id) });
		db.close();
	}

	public void Update(TrailEntity entity) {
		db = getReadableDatabase();
		db.execSQL(
				"update "
						+ TABLENAME
						+ " set name=?,description=?,starttime=?,endtime=?,length=? where ID = ?",
				new Object[] { entity.getName(), entity.getDescription(),
						entity.getStarttime(), entity.getEndtime(),
						entity.getLength(), entity.getId() });
		db.close();
	}

	public TrailEntity Find(int id) {
		db = getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from " + TABLENAME
				+ " where ID = ?", new String[] { String.valueOf(id) });
		try {
			if (cursor.moveToNext()) {
				return new TrailEntity(cursor.getInt(cursor
						.getColumnIndex("ID")), cursor.getString(cursor
						.getColumnIndex("name")), cursor.getString(cursor
						.getColumnIndex("description")), cursor.getLong(cursor
						.getColumnIndex("starttime")), cursor.getLong(cursor
						.getColumnIndex("endtime")), cursor.getDouble(cursor
						.getColumnIndex("length")));
			}
		} finally {
			cursor.close();
		}
		return null;
	}

	public List<TrailEntity> GetScrollData(int start, int count) {

		List<TrailEntity> collects = new ArrayList<TrailEntity>();
		Cursor cursor = null;
		try {
			db = getReadableDatabase();
			cursor = db.rawQuery("select * from " + TABLENAME
					+ " order by starttime desc" + " limit ?,?", new String[] {
					String.valueOf(start), String.valueOf(count) });
			while (cursor.moveToNext()) {
				collects.add(new TrailEntity(cursor.getInt(cursor
						.getColumnIndex("ID")), cursor.getString(cursor
						.getColumnIndex("name")), cursor.getString(cursor
						.getColumnIndex("description")), cursor.getLong(cursor
						.getColumnIndex("starttime")), cursor.getLong(cursor
						.getColumnIndex("endtime")), cursor.getDouble(cursor
						.getColumnIndex("length"))));
			}
		} finally {
			cursor.close();
		}
		return collects;
	}

	// +" order by starttime desc"
	public List<TrailEntity> getData() {

		List<TrailEntity> collects = new ArrayList<TrailEntity>();
		Cursor cursor = null;
		try {
			db = getReadableDatabase();
			cursor = db.rawQuery("select * from " + TABLENAME

			, null);
			while (cursor.moveToNext()) {
				collects.add(new TrailEntity(cursor.getInt(cursor
						.getColumnIndex("ID")), cursor.getString(cursor
						.getColumnIndex("name")), cursor.getString(cursor
						.getColumnIndex("description")), cursor.getLong(cursor
						.getColumnIndex("starttime")), cursor.getLong(cursor
						.getColumnIndex("endtime")), cursor.getDouble(cursor
						.getColumnIndex("length"))));
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
}
