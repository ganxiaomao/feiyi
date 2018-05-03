package com.github.springcloud.stockcrawler.service.impl;

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
