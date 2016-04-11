package com.emapper.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import android.os.Environment;
import android.util.Log;

public class SDCard {

	public static final String TAG = "SDCard";
	protected static String SECONDARY_SD_ROOT = null;
	protected static String CHIEF_SD_ROOT = null;

	public static String getSDCardPath() {
		String sdcardRootPath = SDCard.getChiefSDCardRootPath();
		String chiefSdcard = SDCard.getChiefSDCardRootPath();
		String secondSdcard = SDCard.getSecondarySDCardRootPath();
		long chiefSize = 0;
		long secondSize = 0;
		if (chiefSdcard != null) {
			chiefSize = SDCard.getSDCardTotalSize(chiefSdcard);
		}
		if (secondSdcard != null) {
			secondSize = SDCard.getSDCardTotalSize(secondSdcard);
		}
		sdcardRootPath = (chiefSize >= secondSize) ? chiefSdcard : secondSdcard;
		// sdcardRootPath ="/storage/extSdCard";
		return sdcardRootPath;
	}

	/**
	 * 外置主要的存储卡路径
	 */
	public static String getChiefSDCardRootPath() {
		if (StrUtils.isEmpty(CHIEF_SD_ROOT)) {
			CHIEF_SD_ROOT = Environment.getExternalStorageDirectory()
					.getAbsolutePath().toString();
		}
		return CHIEF_SD_ROOT;
	}

	/***
	 * 外置次要的存储卡路径
	 */
	public static String getSecondarySDCardRootPath() {
		if (StrUtils.isEmpty(SECONDARY_SD_ROOT)) {
			getExternalSDCardStoragePath();
		}
		return SECONDARY_SD_ROOT;
	}

	@SuppressWarnings("deprecation")
	public static long getSDCardAvailSize(String sdcardPath) {
		// 取得SDCard当前的状态
		String status = android.os.Environment.getExternalStorageState();
		if (status.equals(android.os.Environment.MEDIA_MOUNTED)) {
			android.os.StatFs statfs = new android.os.StatFs(sdcardPath);
			// 获取SDCard上BLOCK总数
			long nTotalBlocks = statfs.getBlockCount();
			// 获取SDCard上每个block的SIZE
			long nBlocSize = statfs.getBlockSize();
			// 获取可供程序使用的Block的数量
			long nAvailaBlock = statfs.getAvailableBlocks();
			// 获取剩下的所有Block的数量(包括预留的一般程序无法使用的块)
			long nFreeBlock = statfs.getFreeBlocks();
			// 计算SDCard 总容量大小MB
			long nSDTotalSize = nTotalBlocks * nBlocSize / 1024 / 1024;
			// 计算 SDCard 剩余大小MB
			long nSDFreeSize = nAvailaBlock * nBlocSize / 1024 / 1024;
			return nSDFreeSize;
		}
		return 0;
	}

	public static long getSDCardTotalSize(String sdcardPath) {
		// 取得SDCard当前的状态
		String status = android.os.Environment.getExternalStorageState();
		if (status.equals(android.os.Environment.MEDIA_MOUNTED)) {
			android.os.StatFs statfs = new android.os.StatFs(sdcardPath);
			// 获取SDCard上BLOCK总数
			long nTotalBlocks = statfs.getBlockCount();
			// 获取SDCard上每个block的SIZE
			long nBlocSize = statfs.getBlockSize();
			// 获取可供程序使用的Block的数量
			long nAvailaBlock = statfs.getAvailableBlocks();
			// 获取剩下的所有Block的数量(包括预留的一般程序无法使用的块)
			long nFreeBlock = statfs.getFreeBlocks();
			// 计算SDCard 总容量大小MB
			long nSDTotalSize = nTotalBlocks * nBlocSize / 1024 / 1024;
			// 计算 SDCard 剩余大小MB
			long nSDFreeSize = nAvailaBlock * nBlocSize / 1024 / 1024;
			return nSDTotalSize;
		}
		return 0;
	}

	private static String getExternalSDCardStoragePath() {
		File file = new File("/system/etc/vold.fstab");
		FileReader fr = null;
		BufferedReader br = null;
		String path = null;
		try {
			fr = new FileReader(file);
		} catch (FileNotFoundException e) {
			Log.d(TAG, "getExternalSDCardStoragePath - file not found!!");
		}
		try {
			if (fr != null) {
				br = new BufferedReader(fr);
				String s = br.readLine();
				while (s != null) {
					Log.d(TAG, "vold.fstab : " + s);
					if (s.startsWith("dev_mount")) {
						String[] tokens = s.split("\\s");
						path = tokens[2];
						if (!Environment.getExternalStorageDirectory()
								.getAbsolutePath().equals(path)) {
							break;
						}
					}
					s = br.readLine();
				}
			}
		} catch (IOException e) {
			Log.d(TAG, "getExternalSDCardStoragePath - IOException");
		} finally {
			try {
				if (fr != null) {
					fr.close();
				}
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				Log.d(TAG,
						"getExternalSDCardStoragePath - finally - IOException");
			}
		}
		SECONDARY_SD_ROOT = path;
		return path;
	}
}
