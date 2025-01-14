package io.axual.ksml.operation;

/*-
 * ========================LICENSE_START=================================
 * KSML
 * %%
 * Copyright (C) 2021 Axual B.V.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * =========================LICENSE_END==================================
 */


import org.apache.kafka.streams.kstream.Produced;

import io.axual.ksml.data.object.DataString;
import io.axual.ksml.data.type.StructType;
import io.axual.ksml.stream.KStreamWrapper;
import io.axual.ksml.stream.StreamWrapper;
import io.axual.ksml.user.UserFunction;
import io.axual.ksml.user.UserTopicNameExtractor;

import static io.axual.ksml.dsl.RecordContextSchema.RECORD_CONTEXT_SCHEMA;

public class ToTopicNameExtractorOperation extends BaseOperation {
    private static final String TOPICNAMEEXTRACTOR_NAME = "TopicNameExtractor";
    private final UserFunction topicNameExtractor;

    public ToTopicNameExtractorOperation(OperationConfig config, UserFunction topicNameExtractor) {
        super(config);
        this.topicNameExtractor = topicNameExtractor;
    }

    @Override
    public StreamWrapper apply(KStreamWrapper input) {
        /*    Kafka Streams method signature:
         *    void to(
         *          final TopicNameExtractor<K, V> topicExtractor,
         *          final Produced<K, V> produced)
         */

        var k = input.keyType().userType().dataType();
        var v = input.valueType().userType().dataType();
        checkFunction(TOPICNAMEEXTRACTOR_NAME, topicNameExtractor, equalTo(DataString.DATATYPE), superOf(k), superOf(v), superOf(new StructType(RECORD_CONTEXT_SCHEMA)));

        input.stream.to(new UserTopicNameExtractor(topicNameExtractor), Produced.with(input.keyType().getSerde(), input.valueType().getSerde()).withName(name));
        return null;
    }
}
