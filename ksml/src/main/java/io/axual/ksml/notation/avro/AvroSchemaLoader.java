package io.axual.ksml.notation.avro;

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


import io.axual.ksml.data.schema.SchemaLoader;
import io.axual.ksml.data.schema.DataSchema;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.Schema;

@Slf4j
public class AvroSchemaLoader extends SchemaLoader {
    private static final AvroSchemaMapper MAPPER = new AvroSchemaMapper();

    public AvroSchemaLoader(String schemaDirectory) {
        super("AVRO", schemaDirectory, ".avsc");
    }

    @Override
    protected DataSchema parseSchema(String name, String schema) {
        var result = new Schema.Parser().parse(schema);
        return MAPPER.toDataSchema(name, result);
    }
}
