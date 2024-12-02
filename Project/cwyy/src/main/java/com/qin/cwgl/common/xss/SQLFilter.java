package com.qin.cwgl.common.xss;

import com.qin.cwgl.common.utils.RRException;
import org.apache.commons.lang.StringUtils;
//SQL 注入防护过滤器类，主要用于防止 SQL 注入攻击
//SQL injection protection filter class, mainly used to prevent SQL injection attacks
/**
 * SQL过滤
 */
public class SQLFilter {

    /**
     * SQL注入过滤
     * @param str  待验证的字符串
     */
    public static String sqlInject(String str){
        if(StringUtils.isBlank(str)){
            return null;
        }
        //去掉'|"|;|\字符
        str = StringUtils.replace(str, "'", "");
        str = StringUtils.replace(str, "\"", "");
        str = StringUtils.replace(str, ";", "");
        str = StringUtils.replace(str, "\\", "");

        //转换成小写
        str = str.toLowerCase();

        //非法字符 Illegal characters
        String[] keywords = {"master", "truncate", "insert", "select", "delete", "update", "declare", "alter", "drop"};

        //判断是否包含非法字符
        for(String keyword : keywords){
            if(str.indexOf(keyword) != -1){
                throw new RRException("Contains illegal characters");
            }
        }

        return str;
    }
}
