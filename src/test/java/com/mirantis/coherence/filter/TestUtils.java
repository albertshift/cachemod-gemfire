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

package com.mirantis.coherence.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Locale;

import junit.framework.TestCase;

import com.mirantis.cachemod.filter.CacheEntry;
import com.mirantis.cachemod.filter.GemFireCacheEntry;

public class TestUtils {

  public static byte[] serializeByJava(Object obj) throws IOException {
    ByteArrayOutputStream bout = new ByteArrayOutputStream();
    ObjectOutputStream out = new ObjectOutputStream(bout);
    out.writeObject(obj);
    out.close();
    return bout.toByteArray();
  }
  
  public static void assertEquals(CacheEntry cacheEntry, CacheEntry cacheEntryCheck) throws Exception {
    TestCase.assertEquals(cacheEntry.getContentEncoding(), cacheEntryCheck.getContentEncoding());
    TestCase.assertEquals(cacheEntry.getContentType(), cacheEntryCheck.getContentType());
    TestCase.assertEquals(cacheEntry.getExpires(), cacheEntryCheck.getExpires());
    TestCase.assertEquals(cacheEntry.getLastModified(), cacheEntryCheck.getLastModified());
    TestCase.assertEquals(cacheEntry.getMaxAge(), cacheEntryCheck.getMaxAge());
    TestCase.assertEquals(cacheEntry.getContent().length, cacheEntryCheck.getContent().length);
  }
  
  public static CacheEntry createCacheEntry(String responseText) throws IOException {
    CacheEntry entry = new GemFireCacheEntry();
    entry.setContentEncoding("UTF-8");
    entry.setContentType("text/html");
    entry.setExpires(-1);
    entry.setLastModified(System.currentTimeMillis());
    entry.setLocale(new Locale("en_US"));
    entry.setMaxAge(-1);
    entry.setContent("content".getBytes());
    return entry;
  }
  

  
}
