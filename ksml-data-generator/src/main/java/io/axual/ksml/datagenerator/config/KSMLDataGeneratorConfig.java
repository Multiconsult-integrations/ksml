package io.axual.ksml.datagenerator.config;

/*-
 * ========================LICENSE_START=================================
 * KSML Example Producer
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

import io.axual.ksml.datagenerator.config.axual.AxualBackendConfig;
import io.axual.ksml.datagenerator.config.kafka.KafkaBackendConfig;
import io.axual.ksml.datagenerator.config.producer.DataGeneratorConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class KSMLDataGeneratorConfig {
    private DataGeneratorConfig producer;
    private AxualBackendConfig axual;
    private KafkaBackendConfig kafka;
}
