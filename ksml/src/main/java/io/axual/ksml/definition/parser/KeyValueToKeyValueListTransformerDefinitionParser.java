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


import io.axual.ksml.definition.KeyValueToKeyValueListTransformerDefinition;
import io.axual.ksml.exception.KSMLParseException;
import io.axual.ksml.parser.YamlNode;

public class KeyValueToKeyValueListTransformerDefinitionParser extends FunctionDefinitionParser {
    @Override
    public KeyValueToKeyValueListTransformerDefinition parse(YamlNode node) {
        if (node == null) return null;
        var transformer = super.parse(node);
        if (transformer.resultType != null) {
            return new KeyValueToKeyValueListTransformerDefinition(transformer);
        }
        throw new KSMLParseException(node, "Result dataType not correctly specified");
    }
}
