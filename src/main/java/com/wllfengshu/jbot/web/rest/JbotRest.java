package com.wllfengshu.jbot.web.rest;

import com.wllfengshu.jbot.web.entity.ConnectInfo;
import com.wllfengshu.jbot.web.entity.DBInfo;
import com.wllfengshu.jbot.common.exception.CustomException;
import com.wllfengshu.jbot.web.service.JbotService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * rest
 * @author wllfengshu
 */
@RestController
@RequestMapping("/jbot")
public class JbotRest {

    @Autowired
    private JbotService jbotService;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @ApiOperation(value = "初始化项目",httpMethod = "GET")
    @ApiResponses({
            @ApiResponse(code=400, message="IllegalParam")
    })
    @RequestMapping(value = "/init",method = RequestMethod.GET)
    public Map<String, Object> initProject(
            HttpServletRequest request,
            HttpServletResponse response) throws CustomException {
        logger.info("JbotRest,init");
        return jbotService.initProject();
    }

    @ApiOperation(value = "设置项目",httpMethod = "POST")
    @ApiResponses({
            @ApiResponse(code=400, message="IllegalParam")
    })
    @RequestMapping(value = "/setting",method = RequestMethod.POST)
    public Map<String, Object> settingProject(
            @RequestBody @ApiParam(value = "数据库连接实体类（数据库连接信息）",required = true) ConnectInfo connectInfo,
            HttpServletRequest request,
            HttpServletResponse response) throws CustomException {
        logger.info("JbotRest,getTableFromDB-------->connectInfo:{}",connectInfo);
        return jbotService.settingProject(connectInfo, response);
    }

    @ApiOperation(value = "生成项目",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectName", value = "项目名（eg:jbot）", required = true, dataType = "string",paramType = "query"),
            @ApiImplicitParam(name = "packageName", value = "包名（不包含项目名,eg:com.wllfengshu）", required = true, dataType = "string",paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code=400, message="IllegalParam")
    })
    @RequestMapping(value = "/produce",method = RequestMethod.POST)
    public Map<String, Object> produceProject(
            @RequestParam(value = "projectName") String projectName,
            @RequestParam(value = "packageName") String packageName,
            @RequestBody @ApiParam(value = "数据库实体类（选择的表的集合）",required = true) DBInfo dbInfo,
            HttpServletRequest request,
            HttpServletResponse response) throws CustomException {
        logger.info("JbotRest,produceProject-------->dbInfo:{},projectName:{},packageName:{}",dbInfo,projectName,packageName);
        return jbotService.produceProject(projectName, packageName, dbInfo, response);
    }
}
