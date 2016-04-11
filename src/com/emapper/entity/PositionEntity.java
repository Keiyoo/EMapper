package com.emapper.entity;

import java.io.Serializable;

public class PositionEntity implements Serializable {

	String description;//描述
	double lon;//经度
	double lat;//纬度
	long datetime;//时间
	int id;//id
	int state; //状态-是否上传 0-未上传 ；1-已上传
	double speed;//速度
	double direction;//方向
	
	public PositionEntity(int id,String des,double lon,double lat,long time,int state,double speed,double dir){
		this.id=id;
		this.description=des;
		this.lon=lon;
		this.lat=lat;
		this.datetime=time;
		this.state=state;
		this.speed=speed;
		this.direction=dir;
	}
	public PositionEntity(){
		
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public long getDatetime() {
		return datetime;
	}
	public void setDatetime(long datetime) {
		this.datetime = datetime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	public double getDirction() {
		return direction;
	}
	public void setDirction(double dirction) {
		this.direction = dirction;
	}
	@Override
	public String toString() {
		return "PositionEntity [description=" + description + ", lon=" + lon
				+ ", lat=" + lat + ", datetime=" + datetime + ", id=" + id
				+ "]";
	}
}
