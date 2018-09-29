package com.taotao.service;

import java.util.List;

import com.taotao.common.pojo.EasyUIResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

public interface ContentService {
	
	public EasyUIResult getContentList(long catId, Integer page, Integer rows);
	
	public TaotaoResult saveContent(TbContent content);
	
	public TaotaoResult updateContent(TbContent content);
	
	public TaotaoResult deleteContent(String ids);

}
