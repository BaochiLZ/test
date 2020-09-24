package com.itheima.ssm.controller;

import com.github.pagehelper.PageInfo;
import com.itheima.ssm.domain.Product;
import com.itheima.ssm.service.IProductService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService productService;


    //查询全部产品
  /*  @RequestMapping("/findAll.do")
    public ModelAndView findAll() throws Exception {
        ModelAndView mv = new ModelAndView();
        List<Product> ps = productService.findAll();
        mv.addObject("productList", ps);
        mv.setViewName("product-list1");
        return mv;
    }*/

    @RequestMapping("/findAll.do")
    public ModelAndView findAll(@RequestParam(name = "page",required = true)Integer page,@RequestParam(name = "size",required = true,defaultValue = "4")Integer size) throws Exception{
        ModelAndView mv = new ModelAndView();
        List<Product> all = productService.findAll(page, size);
        PageInfo pageInfo = new PageInfo(all);
        mv.addObject("pageInfo",pageInfo);
        mv.setViewName("product-list1");
        return mv;

    }

    @RequestMapping("/save.do")
    public String save(Product product) throws  Exception{
        productService.save(product);
        return "redirect:findAll.do?page=1&size=4";
    }

    @RequestMapping("/findById.do")
    public ModelAndView findById(String id) throws Exception {
        Product product = productService.findById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("product",product);
        modelAndView.setViewName("product-update");
        return modelAndView;
    }

    @RequestMapping("/update.do")
    public String update(Product product,String id) throws  Exception{
        product.setId(id);
        productService.update(product);
        return "redirect:findAll.do?page=1&size=4";
    }
}
