package cn.itsource.aigou.controller;

import cn.itsource.aigou.domain.Employee;
import cn.itsource.aigou.util.AjaxResult;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public AjaxResult login(@RequestBody Employee employee){
        if ("admin".equals(employee.getUsername())&&"0".equals(employee.getPassword())){
            return AjaxResult.me();
        }
        return AjaxResult.me().setSuccess(false).setMessage("用户名或密码不正确！");
    }
}
