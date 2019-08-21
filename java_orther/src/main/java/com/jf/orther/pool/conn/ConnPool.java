package com.jf.orther.pool.conn;

import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 池.
 *
 * @author Junfeng
 */
public class ConnPool<K, V> {

    private Map<K, LinkedList<EntityWrapper>> pool = new ConcurrentHashMap<>();

    private final Long expireAfterAccessMillis;
    /**
     * 连接最大使用时间
     */
    private final Long maxUsedTimeMillis;

    private final RemovalListener<K, V> removalListener;

    private final ConnLoader<? super K, V> loader;


    public ConnPool(ConnPoolBuilder<? super K, ? super V> builder, ConnLoader<? super K, V> loader) {
        if(null  == builder){
            throw new IllegalArgumentException("builder must not be null!");
        }
        this.removalListener = builder.getRemovalListener();
        this.loader = loader;
        this.expireAfterAccessMillis = builder.getExpireAfterAccessMillis();
        this.maxUsedTimeMillis = builder.getMaxUsedtimeMillis();
    }

    private void check() {
        for (LinkedList<EntityWrapper> entityWrappers : pool.values()) {
            for (EntityWrapper entityWrapper : entityWrappers) {
                /*
                 * 当连接未被使用时，查询最后访问时间是否超过了设定时间，如果超过则删除
                 * 当连接还在被使用时，最大使用时间不等于-1，检查是否超过了最大使用时间，如果超过则删除
                 */
                if ((!entityWrapper.isBusy()
                        && (System.currentTimeMillis() - entityWrapper.getLastAccessTime()) >= this.expireAfterAccessMillis)
                        || (this.maxUsedTimeMillis < 0
                        && (System.currentTimeMillis() - entityWrapper.getLastAccessTime()) >= this.maxUsedTimeMillis)) {
                    removalListener.onRemoval(entityWrapper.getK(), entityWrapper.getV());
                    entityWrappers.remove(entityWrapper);
                    if (entityWrappers.size() == 0) {
                        pool.remove(entityWrapper.getK());
                    }
                }
            }
        }
    }

    public synchronized void remove(K k, V v) {
        removalListener.onRemoval(k, v);
        LinkedList<EntityWrapper> entityWrappers = pool.get(k);
        for (EntityWrapper entityWrapper : entityWrappers) {
            if (entityWrapper.getV().equals(v)) {
                entityWrappers.remove(entityWrapper);
                if (entityWrappers.size() == 0) {
                    pool.remove(entityWrapper.getK());
                }
            }
        }
    }

    public synchronized V get(K k) {
        try {
            if (pool.containsKey(k)) {
                LinkedList<EntityWrapper> entities = pool.get(k);
                for (EntityWrapper entity : entities) {
                    if (!entity.isBusy()) {
                        entity.setBusy(true);
                        entity.setLastAccessTime(System.currentTimeMillis());
                        return entity.getV();
                    }
                }
            }
            if (null != loader) {
                try {
                    V v = loader.load(k);
                    EntityWrapper vEntityWrapper = new EntityWrapper(k, v);
                    LinkedList<EntityWrapper> entities = pool.get(k);
                    if (null == entities) {
                        entities = new LinkedList<>();
                    }
                    entities.add(vEntityWrapper);
                    pool.put(k, entities);
                    return v;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            } else {
                return null;
            }
        } finally {
            check();
        }
    }

    public synchronized void ret(V v) {
        for (LinkedList<EntityWrapper> entityWrappers : pool.values()) {
            for (EntityWrapper entityWrapper : entityWrappers) {
                if (entityWrapper.getV().equals(v)) {
                    entityWrapper.setBusy(false);
                    entityWrapper.setLastAccessTime(System.currentTimeMillis());
                }
            }
        }
    }

    public synchronized void ret(K k, V v) {
        LinkedList<EntityWrapper> entityWrappers = pool.get(k);
        for (EntityWrapper entityWrapper : entityWrappers) {
            if (entityWrapper.getV().equals(v)) {
                entityWrapper.setBusy(false);
                entityWrapper.setLastAccessTime(System.currentTimeMillis());
            }
        }
    }

    /**
     * EntityWrapper
     */
    class EntityWrapper {

        /**
         * key
         */
        private K k;
        /**
         * value
         */
        private V v;
        /**
         * 连接使用后归还的时间
         */
        private Long lastAccessTime;
        /**
         * 使用已经被使用
         */
        private boolean busy;

        EntityWrapper(K k, V v) {
            this.busy = true;
            this.lastAccessTime = System.currentTimeMillis();
            this.k = k;
            this.v = v;
        }

        public K getK() {
            return k;
        }

        public void setK(K k) {
            this.k = k;
        }

        public V getV() {
            return v;
        }

        public void setV(V v) {
            this.v = v;
        }

        public Long getLastAccessTime() {
            return lastAccessTime;
        }

        public void setLastAccessTime(Long lastAccessTime) {
            this.lastAccessTime = lastAccessTime;
        }

        public boolean isBusy() {
            return busy;
        }

        public void setBusy(boolean busy) {
            this.busy = busy;
        }
    }
}
