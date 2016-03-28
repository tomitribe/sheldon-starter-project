/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.superbiz.commands;

import org.tomitribe.crest.api.Command;
import org.tomitribe.crest.api.StreamingOutput;
import org.tomitribe.sheldon.api.CommandListener;
import org.tomitribe.util.PrintString;

import javax.ejb.MessageDriven;
import java.io.PrintStream;
import java.util.Map;
import java.util.TreeSet;

@MessageDriven(name = "System")
public class SystemBean implements CommandListener {

    @Command
    public String date() {
        return String.format("%tc", System.currentTimeMillis());
    }

    @Command
    public String env() {
        final PrintString out = new PrintString();

        final Map<String, String> env = System.getenv();

        for (String key : new TreeSet<>(env.keySet())) {
            out.printf("\033[37m%s\033[0m = %s%n", key, env.get(key));
        }

        return out.toString();
    }

    @Command
    public StreamingOutput properties() {
        return outputStream -> System.getProperties().list(new PrintStream(outputStream));
    }

}
