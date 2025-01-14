package io.axual.ksml.data.schema;

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

import java.util.Objects;

public class AnySchema extends DataSchema {
    public static final AnySchema INSTANCE = new AnySchema();

    private AnySchema() {
        super(Type.ANY);
    }

    @Override
    public boolean isAssignableFrom(DataSchema otherSchema) {
        // This schema is assumed to be assignable from any other schema (except null).
        return otherSchema != null;
    }

    @Override
    public boolean equals(Object other) {
        if (!super.equals(other)) return false;
        if (this == other) return true;
        return other.getClass() == getClass();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }
}
