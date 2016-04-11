package com.emapper.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 轨迹实体类
 * @author weizm
 *
 */
public class TrailEntity implements Serializable {

	private int id;
	private String name;//轨迹名称
	private String description;//轨迹描述
	private long starttime;//起点轨迹点时间
	private long endtime;//终点轨迹点时间
	private double length;//轨迹长度
	public int icount;//轨迹点个数
	public List<PositionEntity> list;//不需要存储
	
	public TrailEntity(int id,String name,String des,long start,long end,double length){
		this.id=id;
		this.description=des;
		this.name=name;
		this.starttime=start;
		this.endtime=end;
		this.length=length;
	}
	
	public TrailEntity(){
		
	}
	
	public int getIcount() {
		return icount;
	}

	public void setIcount(int icount) {
		this.icount = icount;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getStarttime() {
		return starttime;
	}
	public void setStarttime(long starttime) {
		this.starttime = starttime;
	}
	public long getEndtime() {
		return endtime;
	}
	public void setEndtime(long endtime) {
		this.endtime = endtime;
	}
	public double getLength() {
		return length;
	}
	public void setLength(double length) {
		this.length = length;
	}
	@Override
	public String toString() {
		return "TrailEntity [id=" + id + ", name=" + name + ", description="
				+ description + ", starttime=" + starttime + ", endtime="
				+ endtime + ", length=" + length + "]";
	}
	
	
}
