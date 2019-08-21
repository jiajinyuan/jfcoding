package com.jf.orther.pool.conn;

/**
 * 加载器接口
 *
 * @param <K> k
 * @param <V> v
 * @author Junfeng
 */
public interface ConnLoader<K, V> {

    /**
     * Computes or retrieves the value corresponding to {@code key}.
     *
     * @param key the non-null key whose value should be loaded
     * @return the value associated with {@code key}; <b>must not be null</b>
     * @throws Exception            if unable to load the result
     * @throws InterruptedException if this method is interrupted. {@code InterruptedException} is
     *                              treated like any other {@code Exception} in all respects except that, when it is caught,
     *                              the thread's interrupt status is set
     */
    V load(K key) throws Exception;
}
