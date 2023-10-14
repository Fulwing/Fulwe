package com.fulwin.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public final class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

// =============================common============================
    /**
     * Set the expiration time for a cache key.
     * @param key  Key
     * @param time Time in seconds
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get the expiration time for a key.
     * @param key Key, must not be null
     * @return Time in seconds, 0 means permanent
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * Check if a key exists.
     * @param key Key
     * @return true if exists, false otherwise
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Delete cache.
     * @param key One or more keys to delete
     */
    @SuppressWarnings("unchecked")
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete((Collection<String>) CollectionUtils.arrayToList(key));
            }
        }
    }

// ============================String=============================

    /**
     * Retrieve normal cache.
     * @param key Key
     * @return Value
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * Store value in normal cache.
     * @param key   Key
     * @param value Value
     * @return true if successful, false otherwise
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Store value in normal cache with expiration time.
     * @param key   Key
     * @param value Value
     * @param time  Time in seconds; if less than or equal to 0, it will be set to indefinite
     * @return true if successful, false otherwise
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Increment a numeric value.
     * @param key   Key
     * @param delta Value to increase (must be greater than 0)
     */
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("Increment factor must be greater than 0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * Decrement a numeric value.
     * @param key   Key
     * @param delta Value to decrease (must be less than 0)
     */
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("Decrement factor must be greater than 0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

// ================================Map=================================

    /**
     * Get a value from a hash.
     * @param key  Key, must not be null
     * @param item Item, must not be null
     */
    public Object hget(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * Get all key-value pairs for a hash key.
     * @param key Key
     * @return Map of key-value pairs
     */
    public Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * Store multiple key-value pairs in a hash.
     * @param key Key
     * @param map Map of key-value pairs
     * @return true if successful, false otherwise
     */
    public boolean hmset(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Store multiple key-value pairs in a hash with expiration time.
     * @param key  Key
     * @param map  Map of key-value pairs
     * @param time Time in seconds
     * @return true if successful, false otherwise
     */
    public boolean hmset(String key, Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Store a value in a hash. If the hash or key does not exist, it will be created.
     * @param key   Key
     * @param item  Item
     * @param value Value
     * @return true if successful, false otherwise
     */
    public boolean hset(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Store a value in a hash. If the hash or key does not exist, it will be created.
     * @param key   Key
     * @param item  Item
     * @param value Value
     * @param time  Time in seconds; if the hash table already has a time, it will replace the existing time
     * @return true if successful, false otherwise
     */
    public boolean hset(String key, String item, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Delete values from a hash table.
     * @param key  Key
     * @param item Item (can be multiple)
     */
    public void hdel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * Check if a hash table has a value for a given item.
     * @param key  Key
     * @param item Item
     * @return true if exists, false otherwise
     */
    public boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * Increment a value in a hash. If it does not exist, it will be created.
     * @param key  Key
     * @param item Item
     * @param by   Value to increase (must be greater than 0)
     */
    public double hincr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * Decrement a value in a hash.
     * @param key  Key
     * @param item Item
     * @param by   Value to decrease (must be less than 0)
     */
    public double hdecr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, -by);
    }

// ============================set=============================

    /**
     * Get all values from a set.
     * @param key Key
     */
    public Set<Object> sGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Check if a value exists in a set.
     * @param key   Key
     * @param value Value
     * @return true if exists, false otherwise
     */
    public boolean sHasKey(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Add data to a set cache.
     * @param key    Key
     * @param values Values (can be multiple)
     * @return Number of successful additions
     */
    public long sSet(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Add data to a set cache with expiration time.
     * @param key    Key
     * @param time   Time in seconds
     * @param values Values (can be multiple)
     * @return Number of successful additions
     */
    public long sSetAndTime(String key, long time, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0)
                expire(key, time);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Get the length of a set cache.
     * @param key Key
     */
    public long sGetSetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Remove values from a set.
     * @param key    Key
     * @param values Values (can be multiple)
     * @return Number of removed values
     */
    public long setRemove(String key, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

// ===============================list=================================

    /**
     * Get the contents of a list cache.
     * @param key   Key
     * @param start Start index
     * @param end   End index; 0 to -1 represents all values
     */
    public List<Object> lGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get the length of a list cache.
     * @param key Key
     */
    public long lGetListSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Get a value from a list based on index.
     * @param key   Key
     * @param index Index
     */
    public Object lGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Add a value to a list cache.
     * @param key   Key
     * @param value Value
     * @return true if successful, false otherwise
     */
    public boolean lSet(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Add a value to a list cache with expiration time.
     * @param key   Key
     * @param value Value
     * @param time  Time in seconds
     * @return true if successful, false otherwise
     */
    public boolean lSet(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0)
                expire(key, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Add multiple values to a list cache.
     * @param key   Key
     * @param value Values (can be multiple)
     * @return true if successful, false otherwise
     */
    public boolean lSet(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Add multiple values to a list cache with expiration time.
     * @param key   Key
     * @param value Values (can be multiple)
     * @param time  Time in seconds
     * @return true if successful, false otherwise
     */
    public boolean lSet(String key, List<Object> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0)
                expire(key, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Modify a data in a list based on index.
     * @param key   Key
     * @param index Index
     * @param value Value
     * @return true if successful, false otherwise
     */
    public boolean lUpdateIndex(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Remove N values from a list.
     * @param key   Key
     * @param count Number of values to remove
     * @param value Value
     * @return Number of removed values
     */
    public long lRemove(String key, long count, Object value) {
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


}
