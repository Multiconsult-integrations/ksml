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


import io.axual.ksml.definition.StoreDefinition;
import io.axual.ksml.parser.BaseParser;
import io.axual.ksml.parser.YamlNode;

import static io.axual.ksml.dsl.KSMLDSL.STORE_CACHING_ATTRIBUTE;
import static io.axual.ksml.dsl.KSMLDSL.STORE_NAME_ATTRIBUTE;
import static io.axual.ksml.dsl.KSMLDSL.STORE_RETENTION_ATTRIBUTE;

public class StoreDefinitionParser extends BaseParser<StoreDefinition> {
    @Override
    public StoreDefinition parse(YamlNode node) {
        if (node == null) return null;
        return new StoreDefinition(
                parseString(node, STORE_NAME_ATTRIBUTE),
                parseDuration(node, STORE_RETENTION_ATTRIBUTE),
                parseBoolean(node, STORE_CACHING_ATTRIBUTE));
    }
}
