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

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Locale;

import com.gemstone.gemfire.DataSerializable;
import com.gemstone.gemfire.internal.util.BlobHelper;

public class GemFireCacheEntry extends CacheEntry implements DataSerializable {

  private static final long serialVersionUID = 1523452L;
  
  @Override
  public void fromData(DataInput in) throws IOException, ClassNotFoundException {
    key = in.readUTF();
    if (in.readBoolean()) {
      byte[] blob = new byte[in.readInt()];
      in.readFully(blob);
      userData = BlobHelper.deserializeBlob(blob);
    }
    if (in.readBoolean()) {
      String language = in.readUTF();
      String country = in.readUTF();
      try {
        locale = new Locale(language, country);
      }
      catch(Exception e) {
        // do not log
      }
    }
    expires = in.readLong();
    lastModified = in.readLong();
    maxAge = in.readLong();
    if (in.readBoolean()) {
      contentEncoding = in.readUTF();
    }
    if (in.readBoolean()) {
      contentType = in.readUTF();
    }
    content = new byte[in.readInt()];    
    in.readFully(content);
  }

  @Override
  public void toData(DataOutput out) throws IOException {
    out.writeUTF(key);
    out.writeBoolean(userData != null);
    if (userData != null) {
      byte[] userDataBlob = BlobHelper.serializeToBlob(userData);
      out.writeInt(userDataBlob.length);
      out.write(userDataBlob);
    }
    out.writeBoolean(locale != null);
    if (locale != null) {
      out.writeUTF(locale.getLanguage());
      out.writeUTF(locale.getCountry());
    }
    out.writeLong(expires);
    out.writeLong(lastModified);
    out.writeLong(maxAge);    
    out.writeBoolean(contentEncoding != null);
    if (contentEncoding != null) {
      out.writeUTF(contentEncoding);
    }
    out.writeBoolean(contentType != null);
    if (contentType != null) {
      out.writeUTF(contentType);
    }
    out.writeInt(content.length);
    out.write(content);
  }

}
