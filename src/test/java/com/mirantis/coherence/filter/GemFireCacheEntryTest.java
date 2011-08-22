package com.mirantis.coherence.filter;

import junit.framework.TestCase;

import com.gemstone.gemfire.internal.util.BlobHelper;
import com.mirantis.cachemod.filter.CacheEntry;

public class GemFireCacheEntryTest extends TestCase {

  public void test() throws Exception {
    CacheEntry entry = TestUtils.createCacheEntry("hello");
    byte[] blob = BlobHelper.serializeToBlob(entry);
    System.out.println("GF len = " + blob.length);
    CacheEntry cacheEntryCheck = (CacheEntry) BlobHelper.deserializeBlob(blob);
    TestUtils.assertEquals(entry, cacheEntryCheck);
    
    blob = TestUtils.serializeByJava(entry);
    System.out.println("Java len = " + blob.length);
  }
 

}
