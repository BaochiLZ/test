package com.itheima.ssm.controller;

import com.itheima.ssm.domain.Role;
import com.itheima.ssm.domain.UserInfo;
import com.itheima.ssm.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService userService;

    @RequestMapping("/findAll.do")
    public ModelAndView findAll(){
        ModelAndView mv = new ModelAndView();
        List<UserInfo> userList =  userService.findAll();
        mv.addObject("userList",userList);
        mv.setViewName("user-list");
        return mv;
    }

    @RequestMapping("/save.do")
    public String save(UserInfo userInfo) throws Exception{
        userService.save(userInfo);
        return "redirect:findAll.do";
    }

    @RequestMapping("/findById.do")
    public ModelAndView findById(String id) throws Exception{
        ModelAndView mv = new ModelAndView();
       UserInfo userInfo =  userService.findById(id);
       mv.addObject("user",userInfo);
        mv.setViewName("user-show1");
       return mv;
    }
    @RequestMapping("/findUserByIdAndAllRole.do")
   public ModelAndView FindUserByIdAndAllRole(@RequestParam(name = "id",required = true)String id) throws Exception {
        ModelAndView mv = new ModelAndView();
        UserInfo user = userService.findById(id);
        List<Role> otherRoles = userService.findOtherRoles(id);
        mv.addObject("user",user);
        mv.addObject("roleList",otherRoles);
        mv.setViewName("user-role-add");
        return mv;
    }

   @RequestMapping("/addRoleToUser.do")
    public String AddRoleToUser(@RequestParam(name = "userId",required = true) String userId,@RequestParam(name = "ids") String[] roleIds){
        userService.addRoleToUser(userId,roleIds);
        return "redirect:findAll.do";
    }
 }
