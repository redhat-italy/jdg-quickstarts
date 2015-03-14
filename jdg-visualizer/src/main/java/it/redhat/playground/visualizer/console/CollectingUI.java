/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it.redhat.playground.visualizer.console;

import it.redhat.playground.console.UI;

public class CollectingUI implements UI {

    @Override
    public void print(Object message) {
        result.append(convertCRToJson(message.toString()));
    }

    @Override
    public void println(Object message) {
        result.append(convertCRToJson(message.toString() + "\n"));
    }

    @Override
    public void print(String message) {
        result.append(convertCRToJson(message));
    }

    @Override
    public void println(String message) {
        result.append(convertCRToJson(message + "\n"));
    }

    @Override
    public void println() {
    }

    @Override
    public void printUsage() {

    }

    public String getResult() {
        return result.toString();
    }

    private String convertCRToJson(String text) {
        return text.replace("\n", "\\n");
    }

    private StringBuilder result = new StringBuilder();
}
