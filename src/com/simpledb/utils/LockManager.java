package com.simpledb.utils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.Map;

public class LockManager {
  private final Map<String, ReentrantReadWriteLock> lockMap;

  public LockManager() {
    lockMap = new ConcurrentHashMap<>();
  }

  public void acquireReadLock(String tableName) {
    lockMap.computeIfAbsent(tableName, k -> new ReentrantReadWriteLock()).readLock().lock();
  }

  public void releaseReadLock(String tableName) {
    ReentrantReadWriteLock lock = lockMap.get(tableName);
    if (lock != null) {
      lock.readLock().unlock();
    }
  }

  public void acquireWriteLock(String tableName) {
    lockMap.computeIfAbsent(tableName, k -> new ReentrantReadWriteLock()).writeLock().lock();
  }

  public void releaseWriteLock(String tableName) {
    ReentrantReadWriteLock lock = lockMap.get(tableName);
    if (lock != null) {
      lock.writeLock().unlock();
    }
  }
}
