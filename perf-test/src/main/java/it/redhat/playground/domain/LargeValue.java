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

import org.infinispan.commons.marshall.AdvancedExternalizer;
import org.infinispan.commons.marshall.SerializeWith;
import org.infinispan.commons.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.nio.charset.Charset;
import java.util.Set;

@SerializeWith(LargeValue.LargeValueExternalizer.class)
public class LargeValue implements Value {

    public static final int MAX_VISIBLE_LENGTH = 20;
    private byte[] data;

    private LargeValue(byte[] data) {
        this.data = data;
    }

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

    public static class LargeValueExternalizer implements AdvancedExternalizer<LargeValue> {

        private static final Logger log = LoggerFactory.getLogger(LargeValueExternalizer.class);

        @Override
        public Set<Class<? extends LargeValue>> getTypeClasses() {
            return Util.<Class<? extends LargeValue>>asSet(LargeValue.class);
        }

        @Override
        public Integer getId() {
            return 1001;
        }

        @Override
        public void writeObject(ObjectOutput objectOutput, LargeValue value) throws IOException {
            if (log.isDebugEnabled()) {
                log.debug(String.format("AdvancedExternalizer writing object LargeValue [%s] of len %d", value.toString(), value.data.length));
            }
            objectOutput.writeInt(value.data.length);
            objectOutput.write(value.data, 0, value.data.length);
        }

        @Override
        public LargeValue readObject(ObjectInput objectInput) throws IOException, ClassNotFoundException {
            int len = objectInput.readInt();
            if (log.isDebugEnabled()) {
                log.debug(String.format("AdvancedExternalizer reading object LargeValue of len %d", len));
            }
            byte[] data = new byte[len];
            objectInput.read(data);
            return new LargeValue(data);
        }
    }
}
