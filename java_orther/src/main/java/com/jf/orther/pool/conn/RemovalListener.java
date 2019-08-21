package com.jf.orther.pool.conn;

/**
 * 删除监听器接口.
 *
 * @author Junfeng
 */
public interface RemovalListener<K, V> {

    void onRemoval(K k, V v);
}

