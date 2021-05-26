package io.axual.ksml.operation.parser;

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


import io.axual.ksml.definition.parser.KeyValueToValueListTransformerDefinitionParser;
import io.axual.ksml.operation.TransformKeyValueToValueListOperation;
import io.axual.ksml.parser.ContextAwareParser;
import io.axual.ksml.parser.ParseContext;
import io.axual.ksml.parser.YamlNode;

import static io.axual.ksml.dsl.KSMLDSL.TRANSFORMKEYVALUETOVALUELIST_MAPPER_ATTRIBUTE;

public class TransformKeyValueToValueListOperationParser extends ContextAwareParser<TransformKeyValueToValueListOperation> {
    private final String name;

    protected TransformKeyValueToValueListOperationParser(String name, ParseContext context) {
        super(context);
        this.name = name;
    }

    @Override
    public TransformKeyValueToValueListOperation parse(YamlNode node) {
        if (node == null) return null;
        return new TransformKeyValueToValueListOperation(
                name,
                parseFunction(node, TRANSFORMKEYVALUETOVALUELIST_MAPPER_ATTRIBUTE, new KeyValueToValueListTransformerDefinitionParser()));
    }
}