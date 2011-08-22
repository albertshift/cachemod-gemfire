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

import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.CacheFactory;
import com.gemstone.gemfire.cache.Region;

public class GemFireCacheProvider implements CacheProvider {

  private Cache cache;
  private Region<String, GemFireCacheEntry> region;
  
  @Override
  public void init(String cacheName) {
    cache = CacheFactory.getAnyInstance();
    region = cache.getRegion(cacheName);   
  }

  @Override
  public CacheEntry instantiateEntry() {
    return new GemFireCacheEntry();
  }

  @Override
  public CacheEntry getEntry(String key) {
    return region.get(key);
  }

  @Override
  public void putEntry(String key, CacheEntry cacheEntry) {
    region.put(key, (GemFireCacheEntry) cacheEntry);
  }

  @Override
  public Object getCache() {
    return region;
  }

}
