package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.pojo.TbItemParamExample.Criteria;
import com.taotao.service.ItemParamService;
@Service
public class ItemParamServiceImpl implements ItemParamService {

	@Autowired
	private TbItemParamMapper tbItemParamMapper;
	
	@Override
	public TaotaoResult getItemParamByCid(long cid) {
		
		TbItemParamExample example =new TbItemParamExample();
		
		Criteria criteria = example.createCriteria();
		
		criteria.andItemCatIdEqualTo(cid);
		
		List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(example);
		
        System.out.println(list.get(0).getParamData());
       
        
		if(list!=null&&list.size()>0){


			return TaotaoResult.ok(list.get(0));
			
		}
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult saveItemParam(long cid, String itemParam) {
       //创建TbItemParam对象
		TbItemParam param = new TbItemParam();
		param.setItemCatId(cid);
		param.setParamData(itemParam);
		param.setCreated(new Date());
		param.setUpdated(new Date());
		//向数据库添加数据
		tbItemParamMapper.insert(param);
		
		return TaotaoResult.ok();
	}

	@Override
	public EasyUIResult getItemParamList(Integer page, Integer rows) throws Exception {
		//分页处理
		PageHelper.startPage(page, rows);
		//查询规格列表
		List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(new TbItemParamExample());
		//取分页信息
		PageInfo<TbItemParam> pageInfo = new PageInfo<>(list);
		//返回结果
		EasyUIResult result = new EasyUIResult(pageInfo.getTotal(), list);
		
		return result;
	}

	@Override
	public TaotaoResult deleteItemParamByIds(String ids) {
		//批量删除商品规格参数
		List<Long> listid=new ArrayList<>();
		String [] strid=ids.split(",");
		for (String str : strid) {
			listid.add(Long.parseLong(str));
		}
		TbItemParamExample example = new TbItemParamExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdIn(listid);
		int ok = tbItemParamMapper.deleteByExample(example);
		if(ok>0){
			return TaotaoResult.ok(null);
		}
		return null;
	}
}
