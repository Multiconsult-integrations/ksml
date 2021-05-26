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


import io.axual.ksml.definition.parser.BranchDefinitionParser;
import io.axual.ksml.definition.parser.ForEachActionDefinitionParser;
import io.axual.ksml.definition.parser.StreamDefinitionParser;
import io.axual.ksml.definition.parser.TopicNameExtractorDefinitionParser;
import io.axual.ksml.exception.KSMLParseException;
import io.axual.ksml.operation.BranchOperation;
import io.axual.ksml.operation.ForEachOperation;
import io.axual.ksml.operation.ToOperation;
import io.axual.ksml.operation.ToTopicNameExtractorOperation;
import io.axual.ksml.parser.ContextAwareParser;
import io.axual.ksml.parser.InlineOrReferenceParser;
import io.axual.ksml.parser.ListParser;
import io.axual.ksml.parser.ParseContext;
import io.axual.ksml.parser.StreamOperation;
import io.axual.ksml.parser.YamlNode;

import static io.axual.ksml.dsl.KSMLDSL.PIPELINE_BRANCH_ATTRIBUTE;
import static io.axual.ksml.dsl.KSMLDSL.PIPELINE_FOREACH_ATTRIBUTE;
import static io.axual.ksml.dsl.KSMLDSL.PIPELINE_TOTOPICNAMEEXTRACTOR_ATTRIBUTE;
import static io.axual.ksml.dsl.KSMLDSL.PIPELINE_TO_ATTRIBUTE;

public class PipelineSinkOperationParser extends ContextAwareParser<StreamOperation> {
    public PipelineSinkOperationParser(ParseContext context) {
        super(context);
    }

    @Override
    public StreamOperation parse(YamlNode node) {
        if (node == null) return null;
        if (node.get(PIPELINE_BRANCH_ATTRIBUTE) != null) {
            return new BranchOperation(determineName("branch"), new ListParser<>(new BranchDefinitionParser(context)).parse(node.get(PIPELINE_BRANCH_ATTRIBUTE)));
        }
        if (node.get(PIPELINE_FOREACH_ATTRIBUTE) != null) {
            return new ForEachOperation(determineName("for_each"), parseFunction(node, PIPELINE_FOREACH_ATTRIBUTE, new ForEachActionDefinitionParser()));
        }
        if (node.get(PIPELINE_TOTOPICNAMEEXTRACTOR_ATTRIBUTE) != null) {
            return new ToTopicNameExtractorOperation(determineName("to_name_extract"), parseFunction(node, PIPELINE_TOTOPICNAMEEXTRACTOR_ATTRIBUTE, new TopicNameExtractorDefinitionParser()));
        }
        if (node.get(PIPELINE_TO_ATTRIBUTE) != null) {
            final var def = new InlineOrReferenceParser<>(context.getStreamDefinitions(), new StreamDefinitionParser(), PIPELINE_TO_ATTRIBUTE).parse(node);
            if (def != null) {
                return new ToOperation(determineName("to"), def);
            }
            throw new KSMLParseException("Target stream not found or not specified");
        }
        return null;
    }
}