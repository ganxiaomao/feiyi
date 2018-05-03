package com.github.springcloud.stockcrawler.service;

/**
 * Created by ganzhen on 2018/5/3.
 */
public interface RedisServer {

    /**
     * 设置key和对应的值
     * @param key
     * @param value
     * @return
     * @throws Exception
     */
    public boolean set(String key, String value) throws Exception;

    /**
     * 获取key对应的value
     * @param key
     * @return
     * @throws Exception
     */
    public String get(String key) throws Exception;

    /**
     * 删除指定的key
     * @param key
     * @throws Exception
     */
    public void del(String key) throws Exception;

    /**
     * 设置key的实效时间
     * @param key
     * @param expire 单位为秒
     * @return
     */
    public boolean expire(String key, long expire);
}
