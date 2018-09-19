package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemDescExample;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemExample.Criteria;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.service.ItemService;

/**
 * 商品管理Service
 * <p>Title: ItemServiceImpl</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.com</p> 
 * @author	入云龙
 * @date	2015年9月2日上午10:47:14
 * @version 1.0
 */
@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	
	@Autowired
	private TbItemDescMapper itemDescMapper;
	
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;
	
	@Override
	public TbItem getItemById(long itemId) {
		
		//TbItem item = itemMapper.selectByPrimaryKey(itemId);
		//添加查询条件
		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(itemId);
		List<TbItem> list = itemMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			TbItem item = list.get(0);
			return item;
		}
		return null;
	}

	/**
	 * 商品列表查询
	 * <p>Title: getItemList</p>
	 * <p>Description: </p>
	 * @param page
	 * @param rows
	 * @return
	 * @see com.taotao.service.ItemService#getItemList(long, long)
	 */
	@Override
	public EUDataGridResult getItemList(int page, int rows) {
		//查询商品列表
		TbItemExample example = new TbItemExample();
		//分页处理
		PageHelper.startPage(page, rows);
		List<TbItem> list = itemMapper.selectByExample(example);
		//创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
		//取记录总条数
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	@Override
	public TaotaoResult createItem(TbItem item, String desc, String itemParam) throws Exception {
		//item补全
		//生成商品ID
		Long itemId = IDUtils.genItemId();
		item.setId(itemId);
		// '商品状态，1-正常，2-下架，3-删除',
		item.setStatus((byte) 1);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		//插入到数据库
		itemMapper.insert(item);
		//添加商品描述信息
		TaotaoResult result = insertItemDesc(itemId, desc);
		if (result.getStatus() != 200) {
			throw new Exception();
		}
		//添加规格参数
		result = insertItemParamItem(itemId, itemParam);
		if (result.getStatus() != 200) {
			throw new Exception();
		}
		return TaotaoResult.ok();
	}
	/* (修改商品)
	 * @see com.taotao.service.ItemService#updateItem(com.taotao.pojo.TbItem, java.lang.String, java.lang.String)
	 */
	@Override
	public TaotaoResult updateItem(TbItem item, String desc,long itemParamId, String itemParams) throws Exception {
		//修改时间
		item.setUpdated(new Date());
		//修改到数据库		
		int ok = itemMapper.updateByPrimaryKeySelective(item);
		//System.out.println("#########################"+ok);
		if(ok<=0){
			return TaotaoResult.build(100, "修改商品失败！");
		}
		//修改商品描述信息
		TaotaoResult result = updateItemDesc(item.getId(),desc);
		if (result.getStatus() != 200) {			
			return TaotaoResult.build(100, "修改商品描述失败！");			
		}
		//修改商品规格
		result= updateItemParamItem(itemParamId,itemParams);
		if (result.getStatus() != 200) {
			return TaotaoResult.build(100, "修改商品规格失败！");
		}
		return TaotaoResult.ok(null);
	}
	/**
	 * 修改商品描述
	 * <p>Title: insertItemDesc</p>
	 * <p>Description: </p>
	 * @param desc
	 */
	private TaotaoResult updateItemDesc(Long itemId, String desc) {
		TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);		
		itemDesc.setUpdated(new Date());
		itemDesc.setItemDesc(desc);
		int ok =itemDescMapper.updateByPrimaryKeySelective(itemDesc);
		if( ok > 0){
			return TaotaoResult.ok(null);
		}
		return TaotaoResult.build(100,"修改商品描述失败！");
	}
	/**
	 * 添加商品描述
	 * <p>Title: insertItemDesc</p>
	 * <p>Description: </p>
	 * @param desc
	 */
	private TaotaoResult insertItemDesc(Long itemId, String desc) {
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(new Date());
		itemDescMapper.insert(itemDesc);
		return TaotaoResult.ok();
	}
	/**
	 * 修改规格参数
	 * <p>Title: insertItemParamItem</p>
	 * <p>Description: </p>
	 * @param itemId
	 * @param itemParam
	 * @return
	 */
	private TaotaoResult updateItemParamItem(Long itemParamId, String itemParams) {
		
		TbItemParamItem itemParamItem = itemParamItemMapper.selectByPrimaryKey(itemParamId);
		itemParamItem.setParamData(itemParams);
		itemParamItem.setUpdated(new Date());
		//向表中修改数据
		int ok = itemParamItemMapper.updateByPrimaryKeySelective(itemParamItem);
		
		if( ok > 0){
			return TaotaoResult.ok(null);
		}
		return TaotaoResult.build(100,"修改商品规格失败！");
		
	}
	
	/**
	 * 添加规格参数
	 * <p>Title: insertItemParamItem</p>
	 * <p>Description: </p>
	 * @param itemId
	 * @param itemParam
	 * @return
	 */
	private TaotaoResult insertItemParamItem(Long itemId, String itemParam) {
		//创建一个pojo
		TbItemParamItem itemParamItem = new TbItemParamItem();
		itemParamItem.setItemId(itemId);
		itemParamItem.setParamData(itemParam);
		itemParamItem.setCreated(new Date());
		itemParamItem.setUpdated(new Date());
		//向表中插入数据
		itemParamItemMapper.insert(itemParamItem);
		
		return TaotaoResult.ok();
		
	}

	@Override
	public TaotaoResult deleteItem(String ids) {
		//批量删除商品
		List<Long> listid=new ArrayList<>();
		String [] strid=ids.split(",");
		for (String str : strid) {
			listid.add(Long.parseLong(str));
		}		
		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();		
		criteria.andIdIn(listid);
		int ok = itemMapper.deleteByExample(example);	
		if(ok==0){
			return TaotaoResult.build(100, "删除商品失败！");
		}
		//批量删除商品描述
		TaotaoResult result=deleteItemDesc(listid);
		if (result.getStatus() != 200) {			
			return TaotaoResult.build(100, "删除商品描述失败！");			
		}
		//批量删除商品规格
		result= deleteItemParamItem(listid);
		if (result.getStatus() != 200) {			
			return TaotaoResult.build(100, "删除商品规格失败！");			
		}
		return TaotaoResult.ok(null);
	}

	/**
	 * @Description:(删除商品描述)
	 * @param ids
	 * @return     
	 * @author:Lqkun
	 * @date:2018年9月19日下午12:57:50
	 */
	public TaotaoResult deleteItemDesc(List<Long> ids) {
		TbItemDescExample descExample = new TbItemDescExample();
		com.taotao.pojo.TbItemDescExample.Criteria criteria = descExample.createCriteria();
		criteria.andItemIdIn(ids);
		int ok = itemDescMapper.deleteByExample(descExample);
		if(ok==0){
			return TaotaoResult.build(100, "删除商品描述失败！");
		}
		return TaotaoResult.ok(null);
		
	}
	/**
	 * @Description:(删除商品规格)
	 * @param ids
	 * @return     
	 * @author:Lqkun
	 * @date:2018年9月19日下午12:57:50
	 */
	public TaotaoResult deleteItemParamItem(List<Long> ids) {
		TbItemParamItemExample itemParamItemExample = new TbItemParamItemExample();
		com.taotao.pojo.TbItemParamItemExample.Criteria criteria = itemParamItemExample.createCriteria();
		criteria.andItemIdIn(ids);
		int ok = itemParamItemMapper.deleteByExample(itemParamItemExample);
		if(ok==0){
			return TaotaoResult.build(100, "删除商品规格失败！");
		}
		return TaotaoResult.ok(null);
		
	}

}
