package io.axual.ksml.notation.csv;

/*-
 * ========================LICENSE_START=================================
 * KSML
 * %%
 * Copyright (C) 2021 - 2023 Axual B.V.
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

import io.axual.ksml.data.mapper.DataObjectMapper;
import io.axual.ksml.data.object.DataObject;
import io.axual.ksml.data.type.DataType;
import io.axual.ksml.data.type.ListType;
import io.axual.ksml.data.type.StructType;
import io.axual.ksml.data.type.UnionType;
import io.axual.ksml.data.type.UserType;
import io.axual.ksml.notation.json.JsonNotation;
import io.axual.ksml.notation.string.StringNotation;
import org.apache.kafka.common.serialization.Serde;

public class CsvNotation extends StringNotation {
    public static final String NOTATION_NAME = "CSV";
    public static final DataType DEFAULT_TYPE = new UnionType(
            new UserType(JsonNotation.NOTATION_NAME, new StructType()),
            new UserType(JsonNotation.NOTATION_NAME, new ListType()));
    private static final CsvDataObjectMapper MAPPER = new CsvDataObjectMapper();

    public CsvNotation() {
        super(new DataObjectMapper<>() {
            @Override
            public DataObject toDataObject(DataType expected, String value) {
                return MAPPER.toDataObject(expected, value);
            }

            @Override
            public String fromDataObject(DataObject value) {
                return MAPPER.fromDataObject(value);
            }
        });
    }

    @Override
    public String name() {
        return NOTATION_NAME;
    }

    @Override
    public Serde<Object> getSerde(DataType type, boolean isKey) {
        // CSV types should ways be Lists, Structs or the union of them both
        if (type instanceof ListType || type instanceof StructType || DEFAULT_TYPE.equals(type))
            return super.getSerde(type, isKey);
        // Other types can not be serialized as XML
        throw noSerdeFor(type);
    }
}
