package com.cloud.base;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagerView<T> {
	private int total;
	private List<T> rows;
}
