/*
 * Copyright 2011 Mirantis Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mirantis.cachemod.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.CacheFactory;
import com.gemstone.gemfire.cache.Region;

public class GemFireCacheProvider implements CacheProvider {

  private static final Log log = LogFactory.getLog(GemFireCacheProvider.class);
  
  private Cache cache;
  private Region<String, GemFireCacheEntry> region;
  
  @Override
  public void init(String cacheName) {
    cache = CacheFactory.getAnyInstance();
    if (cache == null) {
      throw new IllegalStateException("no cache in JVM");
    }
    region = cache.getRegion(cacheName);   
  }

  @Override
  public CacheEntry instantiateEntry() {
    return new GemFireCacheEntry();
  }

  @Override
  public CacheEntry getEntry(String key) {
    try {
      return region.get(key);
    }
    catch(Exception e) {
      log.error("fail to get page from cache " + key, e);
    }
    return null;
  }

  @Override
  public void putEntry(String key, CacheEntry cacheEntry) {
    try {
      region.put(key, (GemFireCacheEntry) cacheEntry);
    }
    catch(Exception e) {
      log.error("fail to put page to cache " + key, e);
    }
  }

  @Override
  public Object getCache() {
    return region;
  }

  @Override
  public int size() {
    return region.size();
  }
  
}
