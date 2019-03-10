package com.wllfengshu.common.utils;

import java.io.File;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.net.URLEncoder;

/**
 * 文件下载工具类
 * @author wllfengshu
 */
public class FileDownloadUtil {

    /**
     * 文件下载
     * @param fileName 文件名（带路径）
     * @param response 请求体
     */
    public static void download(String fileName, HttpServletResponse response){
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            File file = new File(fileName);
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
            response.setHeader("Content-Length", String.valueOf(file.length()));
            bis = new BufferedInputStream(new FileInputStream(file));
            bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[2048];
            while (true) {
                int bytesRead;
                if (-1 == (bytesRead = bis.read(buff, 0, buff.length)))
                    break;
                bos.write(buff, 0, bytesRead);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (bis != null){
                    bis.close();
                }
                if (bos != null){
                    bos.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
