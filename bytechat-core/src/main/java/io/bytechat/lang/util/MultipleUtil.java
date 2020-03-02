package io.bytechat.lang.util;

import cn.hutool.core.util.NetUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;

import java.util.LinkedHashSet;

/**
 * @author : denglinhai
 * @date : 13:49 2020/2/26
 * 多种小工具集合
 */
public class MultipleUtil {

    /**
     * 获取本机IPV4的地址
     * @return
     */
    public static String getLocalIPV4(){
        LinkedHashSet<String> ipv4s = NetUtil.localIpv4s();
        return ipv4s.isEmpty() ? "" : ipv4s.toArray()[0].toString();
    }

    /**
     * 将处理器名称换成小写
     * @param name
     * @return
     */
    public static String unifiedProcessorName(String name){
        return StrUtil.isEmpty(name) ? null : name.toLowerCase();
    }
}
