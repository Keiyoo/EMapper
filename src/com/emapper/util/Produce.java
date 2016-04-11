package com.emapper.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Produce {
	//按规则参数文件夹名
	public static String produceFile(){
		Random ran = new Random();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String fileName = sdf.format(new Date()) + ran.nextInt(1000);
		return fileName+".jpg";
	}

	
	public static String UniqueName(){
		Random ran = new Random();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String fileName = sdf.format(new Date()) + ran.nextInt(1000);
		
		return fileName;
	}

	
}
