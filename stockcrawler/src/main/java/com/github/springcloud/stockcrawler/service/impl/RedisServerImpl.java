package com.github.springcloud.stockcrawler.service.impl;

import com.github.springcloud.stockcrawler.common.JsonUtils;
import com.github.springcloud.stockcrawler.service.RedisServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by ganzhen on 2018/5/3.
 */
@Service("redisServerImpl")
@Transactional(rollbackFor = Exception.class)
public class RedisServerImpl implements RedisServer {

    private static int seconds = 86400;//3600*24

    @Autowired
    private RedisTemplate<String,?> redisTemplate;

    @Override
    public boolean set(final String key, final String value) throws Exception {
        Assert.hasText(key,"key couldn't be empty");
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Nullable
            @Override
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                redisConnection.set(serializer.serialize(key),serializer.serialize(value));
                return true;
            }
        });
        return result;
    }

    @Override
    public String get(final String key) throws Exception {
        Assert.hasText(key,"key couldn't be empty");
        String result = redisTemplate.execute(new RedisCallback<String>() {
            @Nullable
            @Override
            public String doInRedis(RedisConnection redisConnection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] value =  redisConnection.get(serializer.serialize(key));
                return serializer.deserialize(value);
            }
        });
        return result;
    }

    @Override
    public void hmset(String key, Object obj) throws Exception {
        Assert.hasText(key,"Key is not empty.");

        Map<byte[], byte[]> data=JsonUtils.readJsonByteMap(JsonUtils.getJsonString(obj));
        redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                connection.hMSet(serializer.serialize(key),data);
                return "";
            }
        });
    }

    @Override
    public <T> T hget(String key, Class<T> clz) throws Exception {
        Assert.hasText(key,"Key is not empty.");

        return redisTemplate.execute(new RedisCallback<T>() {

            @Override
            public T doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();

                Map<String,Object> result;

                Map<byte[],byte[]> data=connection.hGetAll(serializer.serialize(key));
                result= new HashMap<>();
                for (Map.Entry<byte[], byte[]> entry: data.entrySet()) {
                    result.put(serializer.deserialize(entry.getKey()),serializer.deserialize(entry.getValue()));
                }

                return JsonUtils.json2Obj(JsonUtils.getJsonString(result),clz);
            }
        });
    }

    @Override
    public void del(final String key) throws Exception {
        Assert.hasText(key,"key couldn't be empty");
        redisTemplate.execute(new RedisCallback<Long>() {
            @Nullable
            @Override
            public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                return redisConnection.del(serializer.serialize(key));
            }
        });
    }

    @Override
    public boolean expire(String key, long expire) {
        Assert.hasText(key,"key couldn't be empty");
        return redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }
}
