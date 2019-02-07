package com.wllfengshu.core.work.java;

import com.wllfengshu.common.model.RequestModel;
import com.wllfengshu.common.model.TableModel;
import com.wllfengshu.common.utils.FileUtil;
import com.wllfengshu.common.utils.StringUtil;

/**
 * 处理rest文件
 * @author wllfengshu
 */
public class RestHandle {

    public static void start(RequestModel requestModel){
        //1、生成对应的rest文件
        genFile(requestModel);
    }

    private static void genFile(RequestModel requestModel){
        for (TableModel t:requestModel.getTableModels()) {
            String rest=genData(t.getTableNameFLDTU(),t.getTableNameFUDTU(),t.getServiceClassName(),t.getEntityClassName(),requestModel.getRestPack());
            FileUtil.createFile(requestModel.getJavaPath()+"/"+StringUtil.spotToSlash(requestModel.getRestPack())+"/"+t.getTableNameFUDTU()+"Rest.java",rest);
        }
    }

    private static String genData(String tableNameFLDTU,String tableNameFUDTU,String serviceClassName,String entityClassName,String restPack){
        StringBuffer sb=new StringBuffer();
        sb.append(genHead(tableNameFLDTU,tableNameFUDTU,restPack,serviceClassName,entityClassName));
        sb.append(genMember(tableNameFLDTU,tableNameFUDTU));
        sb.append(genInsert(tableNameFLDTU,tableNameFUDTU));
        sb.append(genDelete(tableNameFLDTU,tableNameFUDTU));
        sb.append(genUpdate(tableNameFLDTU,tableNameFUDTU));
        sb.append(genSelect(tableNameFLDTU,tableNameFUDTU));
        sb.append(genSelectList(tableNameFLDTU));
        sb.append(genTail());
        return sb.toString();
    }

    /**
     * 生成头
     * @return
     */
    private static String genHead(String tableNameFLDTU,String tableNameFUDTU,String restPack,String serviceClassName,String entityClassName){
        StringBuffer sb=new StringBuffer();
        sb.append("package "+restPack+";\r\n\r\n");
        sb.append("import "+serviceClassName+";\r\n");
        sb.append("import "+entityClassName+";\r\n");
        sb.append("import io.swagger.annotations.*;\r\n");
        sb.append("import org.slf4j.Logger;\r\n");
        sb.append("import org.slf4j.LoggerFactory;\r\n");
        sb.append("import org.springframework.beans.factory.annotation.Autowired;\r\n");
        sb.append("import org.springframework.web.bind.annotation.*;\r\n");
        sb.append("import javax.servlet.http.HttpServletRequest;\r\n");
        sb.append("import javax.servlet.http.HttpServletResponse;\r\n");
        sb.append("import java.util.*;\r\n\r\n");
        sb.append("@RestController\r\n");
        sb.append("@RequestMapping(\"/"+tableNameFLDTU+"s\")\r\n");
        sb.append("public class "+tableNameFUDTU+"Rest {\r\n\r\n");
        return sb.toString();
    }

    /**
     * 生成尾
     * @return
     */
    private static String genTail(){
        return "\r\n}\r\n";
    }

    /**
     * 生成成员变量
     * @return
     */
    private static String genMember(String tableNameFLDTU,String tableNameFUDTU){
        StringBuffer sb=new StringBuffer();
        sb.append("\t@Autowired\r\n");
        sb.append("\tprivate "+tableNameFUDTU+"Service "+tableNameFLDTU+"Service;\r\n\r\n");
        sb.append("\tprivate Logger logger = LoggerFactory.getLogger(getClass());\r\n\r\n");
        return sb.toString();
    }

    /**
     * 生成插入语句
     * @return
     */
    private static String genInsert(String tableNameFLDTU,String tableNameFUDTU){
        StringBuffer sb=new StringBuffer();
        sb.append("\t@ApiOperation(value = \"插入\",httpMethod = \"POST\")\r\n");
        sb.append("\t@ApiResponses({\r\n");
        sb.append("\t\t@ApiResponse(code=400, message=\"IllegalParam\")\r\n");
        sb.append("\t})\r\n");
        sb.append("\t@RequestMapping(value = \"/"+tableNameFLDTU+"\",method = RequestMethod.POST)\r\n");
        sb.append("\tpublic Map<String, Object> insert(\r\n");
        sb.append("\t\tHttpServletRequest request,\r\n");
        sb.append("\t\tHttpServletResponse response,\r\n");
        sb.append("\t\t@RequestBody "+tableNameFUDTU+" entity){\r\n");
        sb.append("\t\treturn "+tableNameFLDTU+"Service.insert(entity);\r\n");
        sb.append("\t}\r\n\r\n");
        return sb.toString();
    }

    /**
     * 生成删除语句
     * @return
     */
    private static String genDelete(String tableNameFLDTU,String tableNameFUDTU){
        StringBuffer sb=new StringBuffer();
        sb.append("\t@ApiOperation(value = \"删除\",httpMethod = \"DELETE\")\r\n");
        sb.append("\t@ApiImplicitParam(name = \"id\", value = \"ID\", required = true, dataType = \"int\",paramType = \"path\")\r\n");
        sb.append("\t@ApiResponses({\r\n");
        sb.append("\t\t@ApiResponse(code=400, message=\"IllegalParam\")\r\n");
        sb.append("\t})\r\n");
        sb.append("\t@RequestMapping(value = \"/"+tableNameFLDTU+"/{id}\",method = RequestMethod.DELETE)\r\n");
        sb.append("\tpublic Map<String, Object> delete(\r\n");
        sb.append("\t\t@PathVariable(\"id\") Integer id,\r\n");
        sb.append("\t\tHttpServletRequest request,\r\n");
        sb.append("\t\tHttpServletResponse response){\r\n");
        sb.append("\t\treturn "+tableNameFLDTU+"Service.delete(id);\r\n");
        sb.append("\t}\r\n\r\n");
        return sb.toString();
    }

    /**
     * 生成更新语句
     * @return
     */
    private static String genUpdate(String tableNameFLDTU,String tableNameFUDTU){
        StringBuffer sb=new StringBuffer();
        sb.append("\t@ApiOperation(value = \"修改\",httpMethod = \"PUT\")\r\n");
        sb.append("\t@ApiResponses({\r\n");
        sb.append("\t\t@ApiResponse(code=400, message=\"IllegalParam\")\r\n");
        sb.append("\t})\r\n");
        sb.append("\t@RequestMapping(value = \"/"+tableNameFLDTU+"\",method = RequestMethod.PUT)\r\n");
        sb.append("\tpublic Map<String, Object> update(\r\n");
        sb.append("\t\tHttpServletRequest request,\r\n");
        sb.append("\t\tHttpServletResponse response,\r\n");
        sb.append("\t\t@RequestBody "+tableNameFUDTU+" entity){\r\n");
        sb.append("\t\treturn "+tableNameFLDTU+"Service.update(entity);\r\n");
        sb.append("\t}\r\n\r\n");
        return sb.toString();
    }

    /**
     * 生成查询语句（单条）
     * @return
     */
    private static String genSelect(String tableNameFLDTU,String tableNameFUDTU){
        StringBuffer sb=new StringBuffer();
        sb.append("\t@ApiOperation(value = \"按ID查询\",httpMethod = \"GET\")\r\n");
        sb.append("\t@ApiImplicitParam(name = \"id\", value = \"ID\", required = true, dataType = \"int\",paramType = \"path\")\r\n");
        sb.append("\t@ApiResponses({\r\n");
        sb.append("\t\t@ApiResponse(code=400, message=\"IllegalParam\")\r\n");
        sb.append("\t})\r\n");
        sb.append("\t@RequestMapping(value = \"/"+tableNameFLDTU+"/{id}\",method = RequestMethod.GET)\r\n");
        sb.append("\tpublic Map<String, Object> select(\r\n");
        sb.append("\t\t@PathVariable(\"id\") Integer id,\r\n");
        sb.append("\t\tHttpServletRequest request,\r\n");
        sb.append("\t\tHttpServletResponse response){\r\n");
        sb.append("\t\treturn "+tableNameFLDTU+"Service.select(id);\r\n");
        sb.append("\t}\r\n\r\n");
        return sb.toString();
    }

    /**
     * 生成查询语句（多条）
     * @return
     */
    private static String genSelectList(String tableNameFLDTU){
        StringBuffer sb=new StringBuffer();
        sb.append("\t@ApiOperation(value = \"查询\",httpMethod = \"GET\")\r\n");
        sb.append("\t@ApiImplicitParams({\r\n");
        sb.append("\t\t@ApiImplicitParam(name = \"pageNo\",value = \"页数(从0开始，默认0)\", dataType = \"int\",paramType = \"query\"),\r\n");
        sb.append("\t\t@ApiImplicitParam(name = \"pageSize\",value = \"每页的数量(默认10)\", dataType = \"int\",paramType = \"query\")\r\n");
        sb.append("\t})\r\n");
        sb.append("\t @ApiResponses({\r\n");
        sb.append("\t\t@ApiResponse(code=400, message=\"IllegalParam\")\r\n");
        sb.append("\t})\r\n");
        sb.append("\t@RequestMapping(value = \"/\",method = RequestMethod.GET)\r\n");
        sb.append("\tpublic Map<String, Object> selects(\r\n");
        sb.append("\t\t@RequestParam(value = \"pageNo\",required = false,defaultValue = \"0\") Integer pageNo,\r\n");
        sb.append("\t\t@RequestParam(value = \"pageSize\",required = false,defaultValue = \"10\") Integer pageSize,\r\n");
        sb.append("\t\tHttpServletRequest request,\r\n");
        sb.append("\t\tHttpServletResponse response){\r\n");
        sb.append("\t\tMap<String,Object> params = new HashMap<>();\r\n");
        sb.append("\t\tparams.put(\"pageNo\",pageNo);\r\n");
        sb.append("\t\tparams.put(\"pageSize\",pageSize);\r\n");
        sb.append("\t\treturn "+tableNameFLDTU+"Service.selects(params);\r\n");
        sb.append("\t}\r\n");
        return sb.toString();
    }
}
