package com.mdd.admin.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Ztree 树
 * @author Administrator
 *
 */
@Data
public class ZtreeVo implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long id;

	private Long pid;

	private String value;

	private String label;
	
	private String url;
	
	private Boolean open =true;
	
	private Boolean isParent;
	
	private String icon;
	
	private List<ZtreeVo> children;
}
