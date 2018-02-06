package com.dct.seckillDemo.web;



import com.dct.seckillDemo.dto.Exposer;
import com.dct.seckillDemo.dto.SeckillExecution;
import com.dct.seckillDemo.dto.SeckillResult;
import com.dct.seckillDemo.entity.Seckill;
import com.dct.seckillDemo.enums.SeckillStatEnum;
import com.dct.seckillDemo.exception.RepeatKillException;
import com.dct.seckillDemo.exception.SeckillCloseException;
import com.dct.seckillDemo.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/*
* /seckill/list    获取列表
* /seckill/{seckillId}/detail   物品详情
* /seckill/{seckillId}/exposer   获取物品秒杀地址
* /seckill/{seckillId}/{md5}/execution   执行物品秒杀
*
* /seckill/add  get获取添加页面
* /seckill/add  post向数据库中添加记录
* /seckill/{seckillId}/update  get获取更新页面
* /seckill/update  post更新数据
* /seckill//{seckillId}delete  删除数据
* */



@Controller
@RequestMapping("/seckill")//url:模块/资源/{}/细分
public class SeckillController
{
    @Autowired
    private SeckillService seckillService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(Model model)
    {
        //list.jsp+mode=ModelAndView
        //获取列表页
        List<Seckill> list=seckillService.getSeckillList();
        model.addAttribute("list",list);
        return "list";
    }

    @RequestMapping(value = "/{seckillId}/detail",method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId, Model model)
    {
        if (seckillId == null)
        {
            return "redirect:/seckill/list";
        }

        Seckill seckill=seckillService.getById(seckillId);
        if (seckill==null)
        {
            return "forward:/seckill/list";
        }

        model.addAttribute("seckill",seckill);

        return "detail";
    }


    @RequestMapping(value = "/add",method = {RequestMethod.GET})
    public String addItemUI(){

        return "add";
    }

    @RequestMapping(value = "/add",method = {RequestMethod.POST})
    public String addItemInfo(Seckill seckill){
        seckillService.addItem(seckill);
        return "redirect:/seckill/list";
    }

    @RequestMapping(value = "/{sk.seckillId}/delete",method = {RequestMethod.GET})
    public String deleteItem(@PathVariable("sk.seckillId") Long seckillId){
        seckillService.deleteItemById(seckillId);
        return "redirect:/seckill/list";
    }

    @RequestMapping(value = "/{sk.seckillId}/update",method = {RequestMethod.GET})
    public String updateItemUI(@PathVariable("sk.seckillId") Long seckillId,Model model){
        Seckill seckill = seckillService.getById(seckillId);
        model.addAttribute("item",seckill);
        return "update";
    }

    @RequestMapping(value = "/update",method = {RequestMethod.POST})
    public String updateItem(Seckill seckill){
        seckillService.updateItemById(seckill);
        return "redirect:/seckill/list";
    }



    //ajax ,json暴露秒杀接口的方法
    @RequestMapping(value = "/{seckillId}/exposer",
                    method = RequestMethod.GET,
                    produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Exposer> exposer(@PathVariable("seckillId") Long seckillId)
    {
        SeckillResult<Exposer> result;
        try{
            Exposer exposer=seckillService.exportSeckillUrl(seckillId);
            result=new SeckillResult<Exposer>(true,exposer);
        }catch (Exception e)
        {
            e.printStackTrace();
            result=new SeckillResult<Exposer>(false,e.getMessage());
        }

        return result;
    }

    @RequestMapping(value = "/{seckillId}/{md5}/execution",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,
                                                   @PathVariable("md5") String md5,
                                                   @CookieValue(value = "userPhone",required = false) Long userPhone)
    {
        if (userPhone==null)
        {
            return new SeckillResult<SeckillExecution>(false,"未注册");
        }
        SeckillResult<SeckillExecution> result;

        try {
            SeckillExecution execution = seckillService.executeSeckill(seckillId, userPhone, md5);
            return new SeckillResult<SeckillExecution>(true, execution);
        }catch (RepeatKillException e1)
        {
            SeckillExecution execution=new SeckillExecution(seckillId, SeckillStatEnum.REPEAT_KILL);
            return new SeckillResult<SeckillExecution>(true,execution);
        }catch (SeckillCloseException e2)
        {
            SeckillExecution execution=new SeckillExecution(seckillId, SeckillStatEnum.END);
            return new SeckillResult<SeckillExecution>(true,execution);
        }
        catch (Exception e)
        {
            SeckillExecution execution=new SeckillExecution(seckillId, SeckillStatEnum.INNER_ERROR);
            return new SeckillResult<SeckillExecution>(true,execution);
        }

    }

    //获取系统时间
    @RequestMapping(value = "/time/now",method = RequestMethod.GET)
    @ResponseBody
    public SeckillResult<Long> time()
    {
        Date now=new Date();
        return new SeckillResult<Long>(true,now.getTime());
    }
}























