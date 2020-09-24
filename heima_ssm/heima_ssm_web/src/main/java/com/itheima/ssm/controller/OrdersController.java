package com.itheima.ssm.controller;

import com.github.pagehelper.PageInfo;
import com.itheima.ssm.domain.Orders;
import com.itheima.ssm.domain.Product;
import com.itheima.ssm.service.IOrdersService;
import com.itheima.ssm.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private IOrdersService ordersService;


    /*//查询全部产品
    @RequestMapping("/findAll.do")
    public ModelAndView findAll() throws Exception {
        ModelAndView mv = new ModelAndView();
        List<Orders> list = ordersService.findAll();
        mv.addObject("ordersList", list);
        mv.setViewName("orders-list");
        return mv;
    }*/

   @RequestMapping("/findById.do")
    public ModelAndView update(@RequestParam(name = "id",required = true)String id) throws Exception {
        Orders orders = ordersService.findById(id);
        ModelAndView mv = new ModelAndView();
        mv.addObject("orders",orders);
        mv.setViewName("orders-show");
       return mv;
    }

   @RequestMapping("/findAll.do")
   public ModelAndView findAll(@RequestParam(name = "page",required = true)Integer page,@RequestParam(name = "size",required = true,defaultValue = "4")Integer   size) throws Exception {
       ModelAndView mv = new ModelAndView();
       List<Orders> list = ordersService.findAll(page, size);
       PageInfo pageInfo = new PageInfo(list);
       mv.addObject("pageInfo",pageInfo);
       mv.setViewName("orders-page-list");
       return mv;
   }
}
