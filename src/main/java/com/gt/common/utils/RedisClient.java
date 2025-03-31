package com.gt.common.utils;


import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 基于redisTemplate封装的redis客户端
 *
 * @author guitao
 * @since  2024-06-20
 */
@Component
public class RedisClient {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;



    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout) {

        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @param unit    时间单位
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout, final TimeUnit unit) {

        Boolean ret = redisTemplate.expire(key, timeout, unit);
        return ret != null && ret;
    }

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param date 过期时间
     * @return true=设置成功；false=设置失败
     */
    public boolean expireAt(final String key, final Date date) {
        Boolean ret = redisTemplate.expireAt(key, date);
        return ret != null && ret;
    }

    /**
     * 删除单个key
     *
     * @param key 键
     * @return true=删除成功；false=删除失败
     */
    public boolean delKey(final String key) {

        Boolean ret = redisTemplate.delete(key);
        return ret != null && ret;
    }

    /**
     * 删除多个key
     *
     * @param keys 键集合
     * @return 成功删除的个数
     */
    public long delKeys(final Collection<String> keys) {

        return redisTemplate.delete(keys);
    }

    public <T> Set<T> allKeys(final String key) {

        return (Set<T>) redisTemplate.keys(key);
    }

    /**
     * 存入普通对象
     *
     * @param key   Redis键
     * @param value 值
     */
    public void setValue(final String key, final Object value) {

        redisTemplate.opsForValue().set(key, value);
    }

    // 存储普通对象操作

    /**
     * 存入普通对象
     *
     * @param key     键
     * @param value   值
     * @param timeout 有效期，单位秒
     */
    public void setValueTimeout(final String key, final Object value, final long timeout) {

        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * 获取普通对象
     *
     * @param key           键
     * @param typeReference 存储的对象类型
     * @return 对象
     */
    public <T> T getValue(final String key, TypeReference<T> typeReference) {

        Object value = redisTemplate.opsForValue().get(key);
        if (value != null) {
            return ObjectMapperUtil.getObjectMapper().convertValue(value, typeReference);
        }
        return null;
    }

    public Object getValue(final String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 判断是否存在 键key
     * @param key
     * @return
     */
    public boolean hasKey(final  String key) {
        return redisTemplate.hasKey(key);
    }

    // 存储Hash操作

    /**
     * 确定哈希hashKey是否存在
     *
     * @param key  键
     * @param hkey hash键
     * @return true=存在；false=不存在
     */
    public boolean hasHashKey(final String key, String hkey) {

        return redisTemplate.opsForHash().hasKey(key, hkey);
    }

    /**
     * 往Hash中存入数据
     *
     * @param key   Redis键
     * @param hKey  Hash键
     * @param value 值
     */
    public void hashPut(final String key, final String hKey, final Object value) {

        redisTemplate.opsForHash().put(key, hKey, value);
    }

    /**
     * 往Hash中存入多个数据
     *
     * @param key    Redis键
     * @param values Hash键值对
     */
    public void hashPutAll(final String key, final Map<String, Object> values) {

        redisTemplate.opsForHash().putAll(key, values);
    }

    /**
     * 获取Hash中的数据
     *
     * @param key           Redis键
     * @param hKey          Hash键
     * @param typeReference 存储的对象类型
     * @return Hash中的对象
     */
    public <T> T hashGet(final String key, final String hKey, final TypeReference<T> typeReference) {

        Object value = redisTemplate.opsForHash().get(key, hKey);
        return ObjectMapperUtil.getObjectMapper().convertValue(value, typeReference);
    }

    /**
     * 获取Hash中的数据
     *
     * @param key                 Redis键
     * @param typeReference4key   map键的类型
     * @param typeReference4value map值的类型
     * @return Hash对象
     */
    public <T, E> Map<T, E> hashGetAll(final String key,
                                           final TypeReference<T> typeReference4key,
                                           final TypeReference<E> typeReference4value) {
        Map<Object, Object> map = redisTemplate.opsForHash().entries(key);
        Map<T, E> convertedMap = new HashMap<>(map.size());
        map.forEach((k, v) -> convertedMap.put(ObjectMapperUtil.getObjectMapper().convertValue(k, typeReference4key),
                ObjectMapperUtil.getObjectMapper().convertValue(v, typeReference4value)));
        return convertedMap;
    }

    /**
     * 获取多个Hash中的数据
     *
     * @param key   Redis键
     * @param hKeys Hash键集合
     * @return Hash对象集合
     */
    public <T> List<T> hashMultiGet(final String key, final Collection<Object> hKeys,
                                      final TypeReference<List<T>> typeReference) {

        List<Object> values = redisTemplate.opsForHash().multiGet(key, hKeys);
        return ObjectMapperUtil.getObjectMapper().convertValue(values, typeReference);
    }

    /**
     * 删除Hash中的数据
     *
     * @param key   Redis键
     * @param hKeys Hash键集合
     * @return Hash对象集合
     */
    public long hashDeleteKeys(final String key, final Collection<Object> hKeys) {
        return redisTemplate.opsForHash().delete(key, hKeys);
    }

    // 存储Set相关操作

    /**
     * 往Set中存入数据
     *
     * @param key    Redis键
     * @param values 值
     * @return 存入的个数
     */
    public long setSet(final String key, final Object... values) {
        Long count = redisTemplate.opsForSet().add(key, values);
        return count == null ? 0 : count;
    }

    /**
     * 删除Set中的数据
     *
     * @param key    Redis键
     * @param values 值
     * @return 移除的个数
     */
    public long setDel(final String key, final Object... values) {
        Long count = redisTemplate.opsForSet().remove(key, values);
        return count == null ? 0 : count;
    }

    public long setDelValue(final String key, Object value) {
        Long count = redisTemplate.opsForSet().remove(key, value);
        return count == null ? 0 : count;
    }

    /**
     * 判断是否包含某一数据
     *
     * @param key    Redis键
     * @param value 值
     * @return 个数
     */
    public Boolean contains(String key, String value) {
        Boolean ret = redisTemplate.opsForSet().isMember(key,value);
        return ret;
    }

    /**
     * 获取set中的所有对象
     *
     * @param key           Redis键
     * @param
     * @return set集合
     */
    public <T> Set<T> getSetAll(final String key) {

        Set<Object> members = redisTemplate.opsForSet().members(key);
        return (Set<T>) members;
    }

    // 存储ZSet相关操作

    public Set<Object> zsetRange(final String key,long start,long end) {
        return redisTemplate.opsForZSet().range(key,start,end);
    }

    /**
     * 往ZSet中存入数据
     *
     * @param key    Redis键
     * @param values 值
     * @return 存入的个数
     */
    public long zsetSet(final String key, final Set<ZSetOperations.TypedTuple<Object>> values) {
        Long count = redisTemplate.opsForZSet().add(key, values);
        return count == null ? 0 : count;
    }

    /**
     * 往ZSet中存入数据
     * @param key
     * @param value
     * @param score
     * @return
     */
    public boolean zsetAdd(final String key,String value,double score) {
        return redisTemplate.opsForZSet().add(key,value,score);
    }

    /**
     * 删除zSet中的元素
     * @param key
     * @param values
     * @return
     */
    public long zsetDel(final String key,Object... values) {
        Long count =  redisTemplate.opsForZSet().remove(key,values);
        return count == null ? 0 : count;
    }

    /**
     * 删除ZSet中的数据
     *
     * @param key    Redis键
     * @param values 值
     * @return 移除的个数
     */
    public long zsetDel(final String key, final Set<ZSetOperations.TypedTuple<Object>> values) {
        Long count = redisTemplate.opsForZSet().remove(key, values);
        return count == null ? 0 : count;
    }

    // 存储List相关操作

    /**
     * 往List中存入数据
     *
     * @param key   Redis键
     * @param value 数据
     * @return 存入的个数
     */
    public long listPush(final String key, final Object value) {
        Long count = redisTemplate.opsForList().rightPush(key, value);
        return count == null ? 0 : count;
    }

    /**
     * 往List中存入多个数据
     *
     * @param key    Redis键
     * @param values 多个数据
     * @return 存入的个数
     */
    public long listPushAll(final String key, final Collection<Object> values) {
        Long count = redisTemplate.opsForList().rightPushAll(key, values);
        return count == null ? 0 : count;
    }

    /**
     * 往List中存入多个数据
     *
     * @param key    Redis键
     * @param values 多个数据
     * @return 存入的个数
     */
    public long listPushAll(final String key, final Object... values) {
        Long count = redisTemplate.opsForList().rightPushAll(key, values);
        return count == null ? 0 : count;
    }

    /**
     * 从List中获取begin到end之间的元素
     *
     * @param key           Redis键
     * @param start         开始位置
     * @param end           结束位置（start=0，end=-1表示获取全部元素）
     * @param typeReference set中元素的类型
     * @return List对象
     */
    public <T> List<T> listGet(final String key, final int start, final int end,
                               final TypeReference<T> typeReference) {
        List<Object> range = listGet(key, start, end);
        return (List<T>) ObjectMapperUtil.getObjectMapper().convertValue(range, typeReference);
    }


    public List<Object> listGet(final String key, final int start, final int end){
        return redisTemplate.opsForList().range(key, start, end);
    }

}

