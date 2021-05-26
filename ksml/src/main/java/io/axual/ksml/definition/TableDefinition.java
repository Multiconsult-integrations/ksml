package io.axual.ksml.definition;

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


import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;

import io.axual.ksml.generator.SerdeGenerator;
import io.axual.ksml.generator.StreamDataType;
import io.axual.ksml.stream.KTableWrapper;
import io.axual.ksml.stream.StreamWrapper;

public class TableDefinition extends BaseStreamDefinition {
    public TableDefinition(String topic, String keyType, String valueType) {
        super(topic, keyType, valueType);
    }

    @Override
    public StreamWrapper addToBuilder(StreamsBuilder builder, String name, SerdeGenerator serdeGenerator) {
        Serde<Object> keySerde = serdeGenerator.getSerdeForType(this.keyType, true);
        Serde<Object> valueSerde = serdeGenerator.getSerdeForType(this.valueType, false);
        return new KTableWrapper(
                builder.table(this.topic, Consumed.with(keySerde, valueSerde).withName(name)),
                new StreamDataType(this.keyType, keySerde),
                new StreamDataType(this.valueType, valueSerde));
    }
}