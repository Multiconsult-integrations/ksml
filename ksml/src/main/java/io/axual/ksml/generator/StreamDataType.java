package io.axual.ksml.generator;

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

import io.axual.ksml.data.type.DataType;
import io.axual.ksml.notation.Notation;

public class StreamDataType {
    public final DataType type;
    public final Notation notation;
    public final boolean isKey;

    public StreamDataType(DataType type, Notation notation, boolean isKey) {
        this.type = type;
        this.notation = notation;
        this.isKey = isKey;
    }

    public boolean isAssignableFrom(StreamDataType other) {
        return type.isAssignableFrom(other.type);
    }

    @Override
    public String toString() {
        return type + " (" + (notation != null ? "as " + notation.name() : "unknown notation") + ")";
    }

    public Serde<Object> getSerde() {
//        if (type instanceof WindowType) {
//            // For WindowTypes return a serde of the value contained within the window
//            return new StreamDataType(((WindowType) type).getWindowedType(), notation, isKey).getSerde();
//        }
        return notation.getSerde(type,isKey);
    }

    public static StreamDataType of(DataType type, Notation notation, boolean isKey) {
        return new StreamDataType(type, notation, isKey);
    }
}
