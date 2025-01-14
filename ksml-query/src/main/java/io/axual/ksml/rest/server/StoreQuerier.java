package io.axual.ksml.rest.server;

/*-
 * ========================LICENSE_START=================================
 * KSML Queryable State Store
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

import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.streams.KeyQueryMetadata;
import org.apache.kafka.streams.StreamsMetadata;

import java.util.Collection;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StoreQuerier {
    private final StreamsQuerier streamsQuerier;

    public StoreQuerier(StreamsQuerier streamsQuerier) {
        this.streamsQuerier = streamsQuerier;
    }

    public Collection<StreamsMetadata> metadataForStore(String storeName) {
        return streamsQuerier.allMetadataForStore(storeName);
    }

    public <K> KeyQueryMetadata metadataForKey(String storeName, K key, Serializer<K> serializer) {
        return streamsQuerier.queryMetadataForKey(storeName, key, serializer);
    }
}
