package com.nuc.server.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.nuc.server.util.MD5Util;
import com.nuc.server.bean.PageBean;
import com.nuc.server.bean.User;
import com.nuc.server.service.UserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nuc.server.util.ResponseUtil;
import com.nuc.server.util.StringUtil;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;
    private static final Logger log = Logger.getLogger(UserController.class);// 日志文件
    private Integer id;
    public static User loginUserNow = null;


    /**
     * 客户端登录
     */
    @RequestMapping("/androidLogin")
    @ResponseBody
    public void androidLogin(HttpServletResponse response, String userName, String password) throws IOException {

        try {
            String MD5pwd = MD5Util.MD5Encode(password, "UTF-8");
            password = MD5pwd;
        } catch (Exception e) {
            password = "";
        }
        System.out.println(userName);
        System.out.println(password);
        User user = userService.login_android(userName, password);
        
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JSONObject json = new JSONObject();
        if (user == null) {
            json.put("status", "0");
        } else {
            String sid = String.valueOf(user.getSid());
            String num = user.getNum();
            Integer id = user.getId();
            json.put("status", "1");
            json.put("sid",sid);
            json.put("num",num);
            json.put("id",id);
        }
        out.write(json.toString());
        System.out.println(json.toString());
        out.flush();
        out.close();

    }

    /**
     * 客户端注册
     */
    @RequestMapping("/androidRegister")
    @ResponseBody
    public void androidRegister(HttpServletResponse response, String userName, String password, String num, String sid, String role) throws Exception {

        try {
            String MD5pwd = MD5Util.MD5Encode(password, "UTF-8");
            password = MD5pwd;
        } catch (Exception e) {
            password = "";
        }

        id= userService.count()+1;
        role = "员工";
        Integer sid1 = Integer.valueOf(sid);
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JSONObject json = new JSONObject();
        System.out.println(userName);
        User user1 = userService.select(userName);
        if (user1 != null) {
            System.out.print("注册失败");
            json.put("status", "0");
        } else {
            userService.register_android(id,userName, password,num,sid1,role);
            System.out.print("注册成功");
            json.put("status", "1");
        }
        out.write(json.toString());
        out.flush();
        out.close();

    }

    /**
     * 客户端修改信息
     */
    @RequestMapping("/updateMessage")
    @ResponseBody
    public void updateMessage(HttpServletResponse response, String userName, String num, String id) throws Exception{
        Integer uid = Integer.valueOf(id);
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JSONObject json = new JSONObject();

        userService.updateMessage_android(userName,num,uid);
        json.put("status",1);

        out.write(json.toString());
        out.flush();
        out.close();
    }

    /**
     * 客户端修改密码
     */
    @RequestMapping("/updatePsd")
    @ResponseBody
    public void updatePsd(HttpServletResponse response, String password, String id) throws Exception{
        Integer uid = Integer.valueOf(id);

        try {
            String MD5pwd = MD5Util.MD5Encode(password, "UTF-8");
            password = MD5pwd;
        } catch (Exception e) {
            password = "";
        }
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JSONObject json = new JSONObject();

        userService.updatePsd_android(password,uid);
        json.put("status",1);

        out.write(json.toString());
        out.flush();
        out.close();
    }

    /**
     * 登录
     */
    @RequestMapping("/login")
    public String login(User user, HttpServletRequest request) {
        try {
            String MD5pwd = MD5Util.MD5Encode(user.getPassword(), "UTF-8");
            user.setPassword(MD5pwd);
        } catch (Exception e) {
            user.setPassword("");
        }
        User resultUser = userService.login(user);
        log.info("request: user/login , user: " + user.toString());
        if (resultUser == null) {
            request.setAttribute("user", user);
            request.setAttribute("errorMsg", "请认真核对账号、密码！");
            return "login";
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("currentUser", resultUser);
            MDC.put("userName", user.getUserName());
            loginUserNow = resultUser;
            return "redirect:main";
        }
    }

    /**
     * 修改密码
     */
    @RequestMapping("/modifyPassword")
    public String modifyPassword(User user, HttpServletResponse response) throws Exception {
        String MD5pwd = MD5Util.MD5Encode(user.getPassword(), "UTF-8");
        user.setPassword(MD5pwd);
        int resultTotal = userService.updateUser(user);
        JSONObject result = new JSONObject();
        if (resultTotal > 0) {
            result.put("success", true);
        } else {
            result.put("success", false);
        }
        log.info("request: user/modifyPassword , user: " + user.toString());
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 退出系统
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session) throws Exception {
        session.invalidate();
        log.info("request: user/logout");
        return "redirect:/login.jsp";
    }

    /**
     * 查询
     */
    @RequestMapping("/list")
    public String list(@RequestParam(value = "page", required = false) String page, @RequestParam(value = "rows", required = false) String rows, User s_user, HttpServletResponse response) throws Exception {
        PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
        Map<String, Object> map = new HashMap<String, Object>();
        List<User> userList;

        map.put("start", pageBean.getStart());
        map.put("size", pageBean.getPageSize());


       if (loginUserNow.getRole().equals("管理员")){
           if (s_user.getUserName() == null){
               System.out.println("管理员登录，未搜索");
               userList = userService.findAllUsers();
           } else {
               System.out.println("管理员登录，开始搜索");
               map.put("userName",s_user.getUserName());
               userList = userService.findUser(map);
           }

       } else if((s_user.getUserName() == null)){
           System.out.println("员工登录，未搜索");
           map.put("userName",loginUserNow.getUserName());
           userList = userService.findUser(map);
       } else {
           System.out.println("员工登录，开始搜索");
           if (loginUserNow.getUserName().equals(s_user.getUserName())){
               map.put("userName",s_user.getUserName());
               userList = userService.findUser(map);
           }else {
               userList = userService.findUser(map);
           }
       }


        Long total = userService.getTotalUser(map);

        JSONObject result = new JSONObject();
        JSONArray jsonArray = JSONArray.fromObject(userList);
        result.put("rows", jsonArray);
        result.put("total", total);
        log.info("request: user/list , map: " + map.toString());
        System.out.println(result);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 添加或修改
     */
    @RequestMapping("/save")
    public String save(User user, HttpServletResponse response) throws Exception {
        int resultTotal = 0;
        String MD5pwd = MD5Util.MD5Encode(user.getPassword(), "UTF-8");
        user.setPassword(MD5pwd);
        if (user.getId() == null) {
            resultTotal = userService.addUser(user);
        } else {
            resultTotal = userService.updateUser(user);
        }
        JSONObject result = new JSONObject();
        if (resultTotal > 0) {
            result.put("success", true);
        } else {
            result.put("success", false);
        }
        log.info("request: user/save , user: " + user.toString());
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public String delete(@RequestParam(value = "ids") String ids, HttpServletResponse response) throws Exception {
        JSONObject result = new JSONObject();
        String[] idsStr = ids.split(",");
        for (int i = 0; i < idsStr.length; i++) {
            userService.deleteUser(Integer.parseInt(idsStr[i]));
        }
        result.put("success", true);
        log.info("request: user/delete , ids: " + ids);
        ResponseUtil.write(response, result);
        return null;
    }

}
