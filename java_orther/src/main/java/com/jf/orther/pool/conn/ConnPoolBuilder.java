package com.jf.orther.pool.conn;

import com.google.common.base.MoreObjects;

import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * 池构造器
 *
 * @author Junfeng
 */
public class ConnPoolBuilder<K, V> {

    private static final int DEFAULT_EXPIRATION_NANOS = 0;

    private static final int UNSET_INT = -1;

    private long expireAfterAccessMillis = UNSET_INT;

    private long maxUsedtimeMillis = UNSET_INT;

    private RemovalListener<? super K, ? super V> removalListener;

    private ConnPoolBuilder() {
    }

    public static ConnPoolBuilder<Object, Object> newBuilder() {
        return new ConnPoolBuilder<>();
    }

    public <K1 extends K, V1 extends V> ConnPool<K1, V1> build(
            ConnLoader<? super K1, V1> loader) {
        return new ConnPool<>(this, loader);
    }

    public ConnPoolBuilder<K, V> expireAfterAccess(long duration, TimeUnit unit) {
        this.expireAfterAccessMillis = unit.toMillis(duration);
        return this;
    }

    long getExpireAfterAccessMillis() {
        return (expireAfterAccessMillis == UNSET_INT) ? DEFAULT_EXPIRATION_NANOS : expireAfterAccessMillis;
    }

    public ConnPoolBuilder<K, V> maxUsedtime(long duration, TimeUnit unit) {
        this.maxUsedtimeMillis = unit.toMillis(duration);
        return this;
    }

    long getMaxUsedtimeMillis() {
        return (maxUsedtimeMillis == UNSET_INT) ? DEFAULT_EXPIRATION_NANOS : maxUsedtimeMillis;
    }

    public <K1 extends K, V1 extends V> ConnPoolBuilder<K1, V1> removalBeforeListener(
            RemovalListener<? super K1, ? super V1> listener) {
        checkState(this.removalListener == null);
        // safely limiting the kinds of caches this can produce
        @SuppressWarnings("unchecked")
        ConnPoolBuilder<K1, V1> me = (ConnPoolBuilder<K1, V1>) this;
        me.removalListener = checkNotNull(listener);
        return me;
    }

    @SuppressWarnings("unchecked")
    <K1 extends K, V1 extends V> RemovalListener<K1, V1> getRemovalListener() {
        return (RemovalListener<K1, V1>)
                MoreObjects.firstNonNull(removalListener, ConnPoolBuilder.NullListener.INSTANCE);
    }

    enum NullListener implements RemovalListener<Object, Object> {
        INSTANCE {
            @Override
            public void onRemoval(Object o, Object o2) {
            }
        }
    }
}
