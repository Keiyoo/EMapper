package com.emapper.util;

import java.io.StringWriter;
import java.util.List;

import org.xmlpull.v1.XmlSerializer;

import com.android.gis.GeoLine;
import com.android.gis.Point2D;
import com.emapper.activity.EApplication;
import com.emapper.entity.MeasureLineEntity;

import android.util.Log;
import android.util.Xml;


public class XmlParser {
	public  String serialize(MeasureLineEntity entity) throws Exception { 
		
        XmlSerializer serializer = Xml.newSerializer(); //由android.util.Xml创建一个XmlSerializer实例  
        StringWriter writer = new StringWriter();
        GeoLine geoline;
        geoline=MapHelper.getMeasureLineGeo(EApplication.getInstance().getWorkspace(), entity.smid);
        if(geoline!=null){
        	int num=(geoline.getPoints(0)).length;
            Point2D point2D;
            serializer.setOutput(writer);   //设置输出方向为writer  
            serializer.startDocument("UTF-8", true);  
            serializer.startTag("", "Measure");
            
            serializer.startTag("", "name");
            serializer.text(entity.name+ ""); 
            serializer.endTag("", "name");
            
            serializer.startTag("", "Type");
            serializer.text("Line");
            serializer.endTag("",  "Type");
            
            serializer.startTag("", "Length");
            serializer.text(entity.disatance+ "");
            serializer.endTag("", "Length");
            
            serializer.startTag("", "PCount");
            serializer.text(num+"");
            serializer.endTag("", "PCount");
            
            serializer.startTag("", "Points");
            for (int i = 0; i < num; i++) {
            	point2D=(geoline.getPoints(0))[i];
            	serializer.startTag("", "Point");
            	
            	serializer.startTag("", "x");
            	serializer.text(point2D.x+ "");
            	serializer.endTag("", "x");
            	
            	serializer.startTag("", "y");
            	serializer.text(point2D.y+ "");
            	serializer.endTag("", "y");
            	
            	serializer.endTag("", "Point");
    		}
            serializer.endTag("", "Points");
            serializer.endTag("", "Measure");
            serializer.endDocument();  
        }else {
        }
        
		
        return writer.toString();  
    }  
}
