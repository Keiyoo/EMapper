package com.emapper.entity;

import java.io.Serializable;
import java.util.List;

public class ShippingLineEntity implements Serializable {
	public int smid;
	public String name;
	public int num;
	public double distance;
	public String strpoints;
	public List<ShippingPointEntity> list;//不需要存储
	public int linetype;//线型0是细线1是粗线
	public String color;//线颜色
	public String sortLetters;

	public ShippingLineEntity() {
		super();
	}


	public ShippingLineEntity(String name, int num) {
		this.name = name;
		this.num = num;
	}

}
