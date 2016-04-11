package com.emapper.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.util.Log;

public class Files {
	public static void saveToSD(String text,String name ) {
		String dirpath = SDCard.getSDCardPath() + "/EMapper/export/";
		File file = new File(dirpath);
		if (!file.exists()) {
			file.mkdirs();
		}
		String filename = dirpath+name+".xml";
		Log.v("gis",filename + "filename");
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(filename);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
		     byte [] bytes = text.getBytes();   
		  
		      try {
				fout.write(bytes);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}   
	       try {
			fout.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   

	}
}
