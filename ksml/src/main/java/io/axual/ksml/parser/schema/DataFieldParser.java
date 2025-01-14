package io.axual.ksml.parser.schema;

/*-
 * ========================LICENSE_START=================================
 * KSML
 * %%
 * Copyright (C) 2021 - 2022 Axual B.V.
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

import io.axual.ksml.parser.BaseParser;
import io.axual.ksml.parser.YamlNode;
import io.axual.ksml.data.schema.DataField;

import static io.axual.ksml.dsl.DataSchemaDSL.DATA_FIELD_DEFAULT_VALUE_FIELD;
import static io.axual.ksml.dsl.DataSchemaDSL.DATA_FIELD_DOC_FIELD;
import static io.axual.ksml.dsl.DataSchemaDSL.DATA_FIELD_NAME_FIELD;
import static io.axual.ksml.dsl.DataSchemaDSL.DATA_FIELD_ORDER_FIELD;

public class DataFieldParser extends BaseParser<DataField> {
    @Override
    public DataField parse(YamlNode node) {
        return new DataField(
                parseString(node, DATA_FIELD_NAME_FIELD),
                new DataSchemaParser().parse(node),
                parseString(node, DATA_FIELD_DOC_FIELD),
                new DataValueParser().parse(node.get(DATA_FIELD_DEFAULT_VALUE_FIELD)),
                new DataFieldOrderParser().parse(node.get(DATA_FIELD_ORDER_FIELD)));
    }
}
