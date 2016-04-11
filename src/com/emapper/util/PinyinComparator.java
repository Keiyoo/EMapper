package com.emapper.util;

import java.util.Comparator;

import com.emapper.entity.ShippingPointEntity;
public class PinyinComparator implements Comparator<ShippingPointEntity> {

	public int compare(ShippingPointEntity o1, ShippingPointEntity o2) {
		if (o1.sortLetters.equals("@")
				|| o2.sortLetters.equals("#")) {
			return -1;
		} else if (o1.sortLetters.equals("#")
				|| o2.sortLetters.equals("@")) {
			return 1;
		} else {
			return o1.sortLetters.compareTo(o2.sortLetters);
		}
	}

}
