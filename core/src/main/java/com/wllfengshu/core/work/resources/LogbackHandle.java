package com.wllfengshu.core.work.resources;

import com.wllfengshu.common.constant.Collective;
import com.wllfengshu.common.utils.FileReplaceUtil;
import com.wllfengshu.common.model.RequestModel;

/**
 * 处理logback文件
 * @author wllfengshu
 */
public class LogbackHandle {

    public static void start(RequestModel requestModel){
        //1、修改里面的“包名”
        replace(requestModel);
    }

    private static void replace(RequestModel requestModel){
        FileReplaceUtil.replace(
                    requestModel.getResourcesPath()+"/logback.xml",
                             new String[]{Collective.MODEL_PACKAGE_NAME},
                             new String[]{requestModel.getPackageName()}
        );
    }
}
