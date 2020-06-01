package com.starsee.controller;

import com.starsee.config.Swagger2Config;
import com.starsee.domain.User;
import io.swagger.annotations.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@Api(tags = "接口测试")
public class IndexController {

    @ApiOperation("获取字符串")
    @GetMapping("/hello")
    public String hello(String name) {
        return name + "hello world";
    }

    @ApiOperation("测试@RequestParam")
    @GetMapping("/world")
    public String world(@RequestParam Integer id) {
        return id + "world";
    }

    /**
     * httpMethod ： 定义词方法请求方式(注意:值必须大写，测试时发小小写报错)
     * value： 描述信息
     * response: 返回的结果类型
     */
    @ApiOperation(httpMethod = "POST", value = "登录接口", response = String.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功响应"),
            @ApiResponse(code = 500, message = "服务器内部错误")
    })
    /**
     *   name: 参数名称
     *   paramType:参数类型
     *   value:参数简要说明
     *   allowMultiple: 是否可以通过多次出现来接受多个值(默认false)
     *   required:是否为必传项
     *   dataTypeClass:指定参数类型
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "password", paramType = "字符串", dataTypeClass = String.class, value = "密码", allowMultiple = false, required = true),
            @ApiImplicitParam(name = "username", paramType = "字符串", value = "用户名", allowMultiple = false, required = true)
    })
    /**
     * 接口排序
     * 我们在对接口的分组排序后,有时候我们希望对我们分组下的接口进行排序,例如我们一个业务注册接口,
     * 分为几个步骤：step1、step2、step3等等,通过排序后,有助于我们开发效率的提升,减少前后端的沟通效率
     */
    @ApiOperationSupport(order = 1)
    @PostMapping("login")
    public String login(@RequestBody User user) {
        if (Objects.equals("123456", user.getPassword())) {
            return "登录成功";
        }
        return "登录失败";
    }


    // ===================================*****  动态参数  *****=========================================

    /**
     * 注意：此处  JSONObject 为jdk 中的json
     * example: 举例,参考值
     */
    @PostMapping("/createOrder421")
    @ApiOperation(value = "  json  动态创建显示参数")
    @ApiOperationSupport(params = @DynamicParameters(name = "创建用户信息", properties = {
            @DynamicParameter(name = "id", value = "主键", example = "1", required = true, dataTypeClass = Integer.class),
            @DynamicParameter(name = "username", value = "用户名", required = false)
    }))
    public JSONObject createUser(@RequestBody JSONObject jsonObject) throws JSONException {
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("name", "张三");
        return jsonObject;
    }

    @PostMapping("/createOrder422")
    @ApiOperation(value = "Map    动态创建显示参数")
    @DynamicParameters(name = "CreateOrderMapModel", properties = {
            @DynamicParameter(name = "id", value = "主键", example = "X000111", required = true, dataTypeClass = Integer.class),
            @DynamicParameter(name = "username", value = "用户名"),
            @DynamicParameter(name = "userInfo", value = "用户信息", dataTypeClass = User.class),
    })
    public Map<String, String> createUser(@RequestBody Map map) {
        Map<String, String> ma = new HashMap<>();
        ma.put("name", "李四");
        return ma;
    }

    // ===================================*****  动态响应  *****========================================
    @ApiOperationSupport(
            responses = @DynamicResponseParameters(properties = {
                    @DynamicParameter(value = "主键", name = "id"),
                    @DynamicParameter(value = "用户名", name = "username"),
                    @DynamicParameter(value = "用户", name = "user", dataTypeClass = User.class)
            })
    )
    @ApiOperation(value = "响应JSONObject类型")
    @GetMapping("/jsonObject")
    public JSONObject jsonObject() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "xx");
        return jsonObject;
    }
    // ===================================*****  忽略参数  *****========================================

    @ApiOperation(value = "新增Model接口1")
    @ApiOperationSupport(ignoreParameters = {"id", "user.id"})
    @PostMapping("/insertMode1l")
    public String insertModel1(User user) {
        return "普通对象忽略参数";
    }

    @ApiOperation(value = "新增Model接口")
    @ApiOperationSupport(ignoreParameters = {"user.id", "user.username"})
    @PostMapping("/insertModel")
    public String insertModel(@RequestBody User user) {
        return "JSON形式忽略参数";
    }

    /**
     * 此为方式一
     * 方式二：可在配置中进行统一忽略,可设置多个
     * 设置忽略类型  ignoredParameterTypes()
     *
     * @see Swagger2Config
     */
    @PostMapping("/createUser1")
    @ApiOperation(value = "创建用户")
    public String createUser(@RequestBody User user, @ApiIgnore HttpSession httpSession) {
        return "忽略参数class类型方式一";
    }

}
