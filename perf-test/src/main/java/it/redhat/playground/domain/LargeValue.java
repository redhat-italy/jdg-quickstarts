/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it.redhat.playground.domain;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

public class LargeValue implements Value {

    public static final int MAX_VISIBLE_LENGTH = 20;
    private byte[] data;

    public LargeValue(byte[] seed, LargeValue v) {
        this.data = new byte[v.data.length];
        System.arraycopy(v.data, 0, this.data, 0, v.data.length);
        System.arraycopy(seed, 0, this.data, 0, seed.length);
    }
    public LargeValue(String seed, int size) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(size);
        try {
            while (bos.size() < size) {
                bos.write(seed.getBytes());
            }
            data = bos.toByteArray();
        } catch (IOException e) {
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
            }
        }
    }

    public long size() {
        return data.length;
    }

    @Override
    public String toString() {
        String result = new String(data, 0, data.length > MAX_VISIBLE_LENGTH ? MAX_VISIBLE_LENGTH : data.length, Charset.forName("UTF-8"));
        if (data.length > MAX_VISIBLE_LENGTH) {
            result += "...";
        }
        return result;
    }
}
