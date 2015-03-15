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
jQuery('#terminal').ready(function ($) {
    var id = 1;
    $('body').terminal(function (command, term) {
        var cmd = $.terminal.splitCommand(command);

        if (cmd.name == 'help') {

            term.echo("address");
            term.echo("         Address of this cluster mode");
            term.echo("get <key>");
            term.echo("         Get an object from the grid.");
            term.echo("hashtags");
            term.echo("         List all registered hashtags feeding the grid");
            term.echo("help");
            term.echo("         List of all commands");
            term.echo("info");
            term.echo("         Information on cache.");
            term.echo("key");
            term.echo("         Get a key which is affine to this cluster node");
            term.echo("loadtest");
            term.echo("         Load example values in the grid");
            term.echo("local");
            term.echo("         List all local valuesFromKeys");
            term.echo("locate <key>");
            term.echo("         Locate an object in the grid.");
            term.echo("primary");
            term.echo("         List all local valuesFromKeys for which this node is primary.");
            term.echo("put <key> <value>");
            term.echo("			Put an object (id, value) in the grid.");
            term.echo("putIfAbsent|putifabsent|pia <key> <value>");
            term.echo("         Put an object (id, value) in the grid if not already present");
            term.echo("replica");
            term.echo("         List all local valuesFromKeys for which this node is a replica.");
            term.echo("routing");
            term.echo("         Print routing table.");
            term.echo("start <hashtag> <timeout>");
            term.echo("         Start feeding the grid with tweets containing the hashtag");
            term.echo("stats <hashtag>");
            term.echo("         Stats on tweets received with <hashtag>");
            term.echo("stop <hashtag>");
            term.echo("         Stop feeding the grid with tweets from <hashtag>");

        } else if (
            (cmd.name == 'address') ||
            (cmd.name == 'hashtags') ||
            (cmd.name == 'info') ||
            (cmd.name == 'key') ||
            (cmd.name == 'loadtest') ||
            (cmd.name == 'all') ||
            (cmd.name == 'local') ||
            (cmd.name == 'primary') ||
            (cmd.name == 'replica') ||
            (cmd.name == 'routing')
        ) {

            $.getJSON("rest/" + cmd.name)
                .done(function (json) {
                    term.echo(json.result);
                })
                .fail(function (jqxhr, textStatus, error) {
                    term.echo(error);
                });

        } else if (cmd.name == 'get') {
            key = cmd.args[0];
            if (key == undefined) {
                term.echo("Expected usage: get <key>");
            } else {
                if (key == parseInt(key, 10)) {
                    $.getJSON("rest/" + key)
                        .done(function (json) {
                            term.echo(json.result);
                        })
                        .fail(function (jqxhr, textStatus, error) {
                            term.echo(error);
                        });
                } else {
                    term.echo("Expected usage: get <key>\nValue for key has to be a number. Example:\n get 10");
                }
            }

        } else if (cmd.name == 'locate') {

            key = cmd.args[0];
            if (key == undefined) {
                term.echo("Expected usage: locate <key>");
            } else {
                if (key == parseInt(key, 10)) {
                    $.getJSON("rest/" + key + "/locate")
                        .done(function (json) {
                            term.echo(json.result);
                        })
                        .fail(function (jqxhr, textStatus, error) {
                            term.echo(error);
                        });
                } else {
                    term.echo("Expected usage: locate <key>\nValue for key has to be a number. Example:\n locate 10");
                }
            }

        } else if (cmd.name == 'put') {

            key = cmd.args[0];
            value = cmd.args[1];
            if (key == undefined || value == undefined) {
                term.echo("Expected usage: put <key> <value>");
            } else {
                if (key == parseInt(key, 10)) {

                    $.getJSON("rest/" + key + "/put/" + value)
                        .done(function (json) {
                            term.echo(json.result);
                        })
                        .fail(function (jqxhr, textStatus, error) {
                            term.echo(error);
                        });
                } else {
                    term.echo("Expected usage: put <key> <value>\nValue for key has to be a number. Example:\n put 10 test");
                }
            }

        } else if (cmd.name == 'putIfAbsent' || cmd.name == 'putifabsent' || cmd.name == 'pia') {

            key = cmd.args[0];
            value = cmd.args[1];
            if (key == undefined || value == undefined) {
                term.echo("Expected usage: putIfAbsent <key> <value>");
            } else {
                if (key == parseInt(key, 10)) {

                    $.getJSON("rest/" + key + "/pia/" + value)
                        .done(function (json) {
                            term.echo(json.result);
                        })
                        .fail(function (jqxhr, textStatus, error) {
                            term.echo(error);
                        });
                } else {
                    term.echo("Expected usage: putIfAbsent <key> <value>\nValue for key has to be a number. Example:\n putIfAbsent 10 test");
                }
            }

        } else if (cmd.name == 'start') {

            hashtag = cmd.args[0];
            timeout = cmd.args[1];
            if (hashtag == undefined || timeout == undefined) {
                term.echo("Expected usage: start <hashtag> <timeout>");
            } else {
                if (timeout == parseInt(timeout, 10)) {
                    term.echo("TODO");
                } else {
                    term.echo("Expected usage: start <hashtag> <timeout>\nValue for timeout has to be a number. Example:\n start infinispan 5");
                }
            }

        } else if (cmd.name == 'stats') {

            if (cmd.args[0] == undefined) {
                term.echo("Expected usage: stats <hashtag>");
            } else {
                term.echo("TODO");
            }

        } else if (cmd.name == 'stop') {

            if (cmd.args[0] == undefined) {
                term.echo("Expected usage: stop <hashtag>");
            } else {
                term.echo("TODO");
            }

        }
    }, {
        greetings: "---------------------------------------\n            JDG Playground Web CLI\n---------------------------------------",
        onBlur: function () {
            // prevent loosing focus
            return false;
        }
    });
});