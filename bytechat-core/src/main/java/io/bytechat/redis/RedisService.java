package io.bytechat.redis;

import cn.hutool.core.lang.Singleton;
import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author : denglinhai
 * @date : 13:09 2020/5/9
 */
public class RedisService {

    private JedisPool pool;


    private RedisService(){
        pool = JedisService.getInstance().getJedisPool();
    }

    public static RedisService getInstance(){
        return Singleton.get(RedisService.class);
    }

    /**
     * 设置一个值
     * @param key
     * @param value
     * @param <T>
     */
    public <T> void set(String key, T value){
        Jedis jedis=null;
        try{
            jedis=pool.getResource();
            jedis.set(key,beanToString(value));
        }finally {
            jedis.close();
        }
    }


    /**
     * 获取单个值
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T get(String key,Class<T> clazz){
        Jedis jedis=null;
        try {
            jedis=pool.getResource();
            String value=jedis.get(key);

            return stringToBean(value,clazz);
        }finally {
            jedis.close();
        }
    }

    /**
     * 转化list
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> List<T> getList(String key, Class<T> clazz){
        Jedis jedis=null;
        try {
            jedis=pool.getResource();
            List<String> value=jedis.lrange(key, 0, -1);
            return stringToListBean(value,clazz);
        }finally {
            jedis.close();
        }
    }

    public Set<String> getKeys(){
        Jedis jedis=null;
        try {
            jedis=pool.getResource();
            Set<String> value=jedis.keys("*");
            return value;
        }finally {
            jedis.close();
        }
    }


    /**
     * 删除某个键
     * @param key
     */
    public void del(String key){
        Jedis jedis=null;
        try {
            jedis=pool.getResource();
            jedis.del(key);
        }finally {
            jedis.close();
        }
    }

    /**
     * 队列push
     * @param key
     * @param value
     * @param <T>
     */
    public <T> void lpush(String key,T value){
        Jedis jedis=null;
        try {
            jedis=pool.getResource();
            jedis.lpush(key,beanToString(value));
        }finally {
            jedis.close();
        }
    }

    public void delValue(String key, String value){
        Jedis jedis=null;
        try {
            jedis=pool.getResource();
            List<String> list=jedis.lrange(key, 0, -1);
            for (int i=0; i<list.size(); i++){
                if (value.equals(list.get(i))){
                    list.remove(i);
                    break;
                }
            }
            jedis.lpush(key, list.stream().toArray(String[]::new));
        }finally {
            jedis.close();
        }
    }

    /**
     * 队列pop
     * @param keyPrefix
     * @param key
     * @param tClass
     * @param <T>
     * @return
     */
//    public <T> T rpop(RedisPrefixKey keyPrefix,String key,Class<T> tClass){
//        Jedis jedis=null;
//        try {
//            jedis=pool.getResource();
//            String realKey=keyPrefix.getThisPrefix()+key;
//            String value=jedis.rpop(realKey);
//            return stringToBean(value,tClass);
//        }finally {
//            jedis.close();
//        }
//    }

    /**
     * 列表长度
     * @param key
     * @return
     */
    public long llength(String key){
        Jedis jedis=null;
        try {
            jedis=pool.getResource();
            return jedis.llen(key);
        }finally {
            jedis.close();
        }
    }

    /**
     * 键自减，使用于string
     * @param key
     * @return
     */
    public Long decKey(String key) {
        Jedis jedis=null;
        try {
            jedis=pool.getResource();
            return jedis.decr(key);
        }finally {
            jedis.close();
        }
    }

    /**
     * 键自增
     * @param key
     * @return
     */
    public Long incKey(String key){
        Jedis jedis=null;
        try {
            jedis=pool.getResource();
            return jedis.incr(key);
        }finally {
            jedis.close();
        }
    }

    /**
     * 判断键是否存在
     * @param key
     * @return
     */
    public boolean isExists(String key){
        Jedis jedis=null;
        try {
            jedis=pool.getResource();
            return jedis.exists(key);
        }finally {
            jedis.close();
        }
    }

    public static  <T> String beanToString(T value){
        if (value == null){
            //throw new NullPointerException("value  is null");
            return null;
        }
        Class clazz=value.getClass();
        if (clazz == int.class || clazz == Integer.class
                || clazz == long.class || clazz == Long.class
                || clazz == float.class || clazz == Float.class
                || clazz == double.class || clazz == Double.class){
            return value+"";
        }else  if (value.getClass() == String.class){
            return (String) value;
        }
        return JSON.toJSONString(value);
    }

    public static  <T> T stringToBean(String value,Class<T> tClass){
        if (value == null){
            //throw  new NullPointerException("value is null");
            return null;
        }
        if (tClass == String.class){
            return (T) value;
        }
        return JSON.parseObject(value,tClass);
    }

    public static <T> List<T> stringToArrayBean(String value,Class<T> tClass){
        if (value == null){
            return null;
        }
        return JSON.parseArray(value,tClass);
    }

    public static <T> List<T> stringToListBean(List<String> value,Class<T> tClass){
        if (value == null){
            return null;
        }
        List<T> newList = new ArrayList<>(value.size());
        for (int i=0; i<value.size(); i++){
            newList.add(JSON.parseObject(value.get(i), tClass));
        }
        return newList;
    }
}
