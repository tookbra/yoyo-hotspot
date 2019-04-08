package com.mars.yoyo.hotspot.db.datasource;

import java.util.Random;

/**
 * datasource context
 * @author tookbra
 * @date 2018/5/17
 * @description
 */
public class DataSourceContextHolder {
    public enum DbType {
        MASTER, SLAVE1
    }

    private static final ThreadLocal<DbType> contextHolder = new ThreadLocal<>();

    public static void setDbType(DbType dbType) {
        if (dbType == null) {
            throw new NullPointerException();
        }
        contextHolder.set(dbType);
    }

    public static DbType getDbType() {
        return contextHolder.get() == null ? DbType.MASTER : contextHolder.get();
    }

    public static void clearDbType() {
        contextHolder.remove();
    }

    private static final Random random = new Random();

    public static DbType getSlaveRandom() {
        DbType[] dbTypes = new DbType[]{DbType.SLAVE1};
        return dbTypes[random.nextInt(dbTypes.length)];
    }
}
