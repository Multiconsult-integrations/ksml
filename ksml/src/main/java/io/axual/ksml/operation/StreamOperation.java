package io.axual.ksml.operation;

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


import io.axual.ksml.exception.KSMLTopologyException;
import io.axual.ksml.stream.GlobalKTableWrapper;
import io.axual.ksml.stream.KGroupedStreamWrapper;
import io.axual.ksml.stream.KGroupedTableWrapper;
import io.axual.ksml.stream.KStreamWrapper;
import io.axual.ksml.stream.KTableWrapper;
import io.axual.ksml.stream.SessionWindowedKStreamWrapper;
import io.axual.ksml.stream.StreamWrapper;
import io.axual.ksml.stream.TimeWindowedKStreamWrapper;

public interface StreamOperation {

    default StreamWrapper apply(KStreamWrapper stream) {
        throw new KSMLTopologyException("Can not apply " + getClass().getSimpleName() + " to " + stream);
    }

    default StreamWrapper apply(KTableWrapper table) {
        throw new KSMLTopologyException("Can not apply " + getClass().getSimpleName() + " to " + table);
    }

    default StreamWrapper apply(GlobalKTableWrapper globalTable) {
        throw new KSMLTopologyException("Can not apply " + getClass().getSimpleName() + " to " + globalTable);
    }

    default StreamWrapper apply(KGroupedStreamWrapper groupedStream) {
        throw new KSMLTopologyException("Can not apply " + getClass().getSimpleName() + " to " + groupedStream);
    }

    default StreamWrapper apply(KGroupedTableWrapper groupedTable) {
        throw new KSMLTopologyException("Can not apply " + getClass().getSimpleName() + " to " + groupedTable);
    }

    default StreamWrapper apply(SessionWindowedKStreamWrapper sessionWindowedKStream) {
        throw new KSMLTopologyException("Can not apply " + getClass().getSimpleName() + " to " + sessionWindowedKStream);
    }

    default StreamWrapper apply(TimeWindowedKStreamWrapper timeWindowedKStream) {
        throw new KSMLTopologyException("Can not apply " + getClass().getSimpleName() + " to " + timeWindowedKStream);
    }
}
