package io.axual.ksml.runner.backend.kafka;

/*-
 * ========================LICENSE_START=================================
 * KSML Runner
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



import com.google.auto.service.AutoService;

import io.axual.ksml.runner.backend.Backend;
import io.axual.ksml.runner.backend.BackendConfig;
import io.axual.ksml.runner.backend.BackendProvider;
import io.axual.ksml.runner.config.KSMLSourceConfig;

@AutoService(BackendProvider.class)
public class KafkaBackendProvider implements BackendProvider<KafkaBackendConfig> {

    private static final String PROVIDER_TYPE = "kafka";

    @Override
    public String getType() {
        return PROVIDER_TYPE;
    }

    @Override
    public Backend create(KSMLSourceConfig ksmlSourceConfig, BackendConfig backendConfig) {
        return new KafkaBackend(ksmlSourceConfig, (KafkaBackendConfig) backendConfig);
    }

    @Override
    public Class<KafkaBackendConfig> getConfigClass() {
        return KafkaBackendConfig.class;
    }
}