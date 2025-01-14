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

import org.apache.kafka.streams.kstream.KeyValueMapper;
import org.apache.kafka.streams.kstream.Named;

import io.axual.ksml.data.mapper.DataObjectConverter;
import io.axual.ksml.data.type.UserType;
import io.axual.ksml.stream.KStreamWrapper;
import io.axual.ksml.stream.StreamWrapper;
import io.axual.ksml.util.DataUtil;

public class ConvertKeyOperation extends BaseOperation {
    private final DataObjectConverter mapper;
    private final UserType targetKeyType;

    public ConvertKeyOperation(OperationConfig config, UserType targetKeyType) {
        super(config);
        this.mapper = new DataObjectConverter(notationLibrary);
        this.targetKeyType = targetKeyType;
    }

    @Override
    public StreamWrapper apply(KStreamWrapper input) {
        // Set up the mapping function to convert the value
        KeyValueMapper<Object, Object, Object> converter = (key, value) -> {
            var keyAsData = DataUtil.asDataObject(key);
            return mapper.convert(input.keyType().userType().notation(), keyAsData, targetKeyType);
        };

        // Inject the mapper into the topology
        return new KStreamWrapper(
                input.stream.selectKey(converter, Named.as(name)),
                streamDataTypeOf(targetKeyType, true),
                input.valueType());
    }
}
