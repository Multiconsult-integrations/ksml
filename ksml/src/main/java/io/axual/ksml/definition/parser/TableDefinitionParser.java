package io.axual.ksml.definition.parser;

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


import io.axual.ksml.definition.TableDefinition;
import io.axual.ksml.parser.BaseParser;
import io.axual.ksml.parser.UserTypeParser;
import io.axual.ksml.parser.YamlNode;
import io.axual.ksml.store.StoreRegistry;

import static io.axual.ksml.dsl.KSMLDSL.KEYTYPE_ATTRIBUTE;
import static io.axual.ksml.dsl.KSMLDSL.QUERYABLE_ATTRIBUTE;
import static io.axual.ksml.dsl.KSMLDSL.TOPIC_ATTRIBUTE;
import static io.axual.ksml.dsl.KSMLDSL.VALUETYPE_ATTRIBUTE;

public class TableDefinitionParser extends BaseParser<TableDefinition> {
    private final StoreRegistry storeRegistry;

    public TableDefinitionParser(StoreRegistry storeRegistry) {
        this.storeRegistry = storeRegistry;
    }

    @Override
    public TableDefinition parse(YamlNode node) {
        if (node == null) return null;
        return new TableDefinition(
                parseString(node, TOPIC_ATTRIBUTE),
                UserTypeParser.parse(parseString(node, KEYTYPE_ATTRIBUTE)),
                UserTypeParser.parse(parseString(node, VALUETYPE_ATTRIBUTE)),
                parseBoolean(node, QUERYABLE_ATTRIBUTE),
                storeRegistry);
    }
}
