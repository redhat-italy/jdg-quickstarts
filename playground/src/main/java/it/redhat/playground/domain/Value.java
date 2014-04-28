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

import java.io.Serializable;

public class Value implements Serializable {

    public Value(String val) {
      this.val = val;
    }

    public Value setVal(String val) {
        this.val = val;
        return this;
    }

    @Override
    public String toString() {
        return val;
    }

    public Value rotate(int offset) {

        offset = offset % 26 + 26;
        StringBuilder encoded = new StringBuilder();
        for (char i : val.toLowerCase().toCharArray()) {
            if (Character.isLetter(i)) {
                int j = (i - 'a' + offset) % 26;
                encoded.append((char) (j + 'a'));
            } else {
                encoded.append(i);
            }
        }
        return new Value(encoded.toString());

    }

    private String val;

}
