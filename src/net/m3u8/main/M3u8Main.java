package net.m3u8.main;

import net.m3u8.download.M3u8DownloadFactory;
import net.m3u8.dto.FileInfo;
import net.m3u8.listener.DownloadListener;
import net.m3u8.readfile.ReadFile;
import net.m3u8.utils.Constant;
import net.m3u8.utils.MyUtils;
import net.m3u8.writefile.WriteFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author liyaling
 * @email ts_liyaling@qq.com
 * @date 2019/12/14 16:02
 */

public class M3u8Main {

    private static final String M3U8URL = "";
    private static final String NAME = "";

    public static void main(String[] args) {
        //single
//        start(M3U8URL,NAME);

        //multi
        multi();
    }

    private static void multi() {
        List<FileInfo> list = ReadFile.readfile("E:\\Windows\\Downloads\\xxxx.m3u");

        list.forEach(e->{
            try {
                start(e.getUrl(), e.getName());
                WriteFile.writefile("E:\\m3u8Archive\\success.txt", e.getName() + "@@@@" + e.getUrl());

                System.out.println("=============================================");
                System.out.println("=============================================");
                System.out.println("=============================================");
                System.out.println("等待.............，开始下一个");
                Thread.sleep(20000L);

            }catch (Exception ex){
                ex.printStackTrace();
                WriteFile.writefile("E:\\m3u8Archive\\fail.txt", e.getName() + "@@@@" + e.getUrl());
            }
        });
    }

    private static void start(String url,String filename) {
        System.out.println("开始："+filename+"     url:"+url);
        M3u8DownloadFactory.M3u8Download m3u8Download = M3u8DownloadFactory.getInstance();
        //set url
        m3u8Download.setUrl(url);
        //设置生成目录
        m3u8Download.setDir("E://m3u8JavaTest");
        //归档目录
        m3u8Download.setArchiveDir("E://m3u8Archive/"+ MyUtils.getDay());
        //设置视频名称
        m3u8Download.setFileName(filename);
        //设置线程数
        m3u8Download.setThreadCount(100);
        //设置重试次数
        m3u8Download.setRetryCount(20);
        //设置连接超时时间（单位：毫秒）
        m3u8Download.setTimeoutMillisecond(30000L);
        /*
        设置日志级别
        可选值：NONE INFO DEBUG ERROR
        */
        m3u8Download.setLogLevel(Constant.INFO);
        //设置监听器间隔（单位：毫秒）
        m3u8Download.setInterval(1000L);
        //添加额外请求头
      /*  Map<String, Object> headersMap = new HashMap<>();
        headersMap.put("Content-Type", "text/html;charset=utf-8");
        m3u8Download.addRequestHeaderMap(headersMap);*/
        //添加监听器
        AtomicBoolean suc = new AtomicBoolean(false);
        m3u8Download.addListener(new DownloadListener() {
            @Override
            public void start() {
                System.out.println("开始下载！");
            }

            @Override
            public void process(String downloadUrl, int finished, int sum, float percent) {
                System.out.println("下载网址：" + downloadUrl + "\t已下载" + finished + "个\t一共" + sum + "个\t已完成" + percent + "%");
            }

            @Override
            public void speed(String speedPerSecond) {
//                System.out.println("下载速度：" + speedPerSecond);
            }

            @Override
            public void end() {
                suc.set(true);
                System.out.println("下载完毕");
            }
        });
        //开始下载
        m3u8Download.start();
        while (!suc.get()){
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
