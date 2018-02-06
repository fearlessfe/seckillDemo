package com.dct.seckillDemo.dao;

import com.dct.seckillDemo.entity.Seckill;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;

/**
 * Created by codingBoy on 16/11/26.
 */
@Repository
public interface SeckillDao
{

    /**
     * 减库存
     * @param seckillId
     * @param killTime
     * @return 如果影响行数>1，表示更新库存的记录行数
     */
    int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);

    /**
     * 根据id查询秒杀的商品信息
     * @param seckillId
     * @return
     */
    Seckill queryById(long seckillId);

    /**
     * 根据偏移量查询秒杀商品列表
     * @param offset
     * @param limit
     * @return
     */
    List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);

    //    添加秒杀物品
    boolean addItem(Seckill seckill);
    //    删除秒杀物品
    boolean deleteById(long seckillId);
    //    更新秒杀物品
    boolean updateItem(Seckill seckill);



}
