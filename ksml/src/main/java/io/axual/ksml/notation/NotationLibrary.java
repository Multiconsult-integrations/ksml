package io.axual.ksml.notation;

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

import io.axual.ksml.execution.FatalError;
import io.axual.ksml.notation.avro.AvroNotation;
import io.axual.ksml.notation.binary.BinaryNotation;
import io.axual.ksml.notation.csv.CsvDataObjectConverter;
import io.axual.ksml.notation.csv.CsvNotation;
import io.axual.ksml.notation.json.JsonDataObjectConverter;
import io.axual.ksml.notation.json.JsonNotation;
import io.axual.ksml.notation.soap.SOAPDataObjectConverter;
import io.axual.ksml.notation.soap.SOAPNotation;
import io.axual.ksml.notation.xml.XmlDataObjectConverter;
import io.axual.ksml.notation.xml.XmlNotation;

import java.util.HashMap;
import java.util.Map;

public class NotationLibrary {
    private record NotationEntry(Notation notation, NotationConverter converter) {
    }

    private final Map<String, NotationEntry> notationEntries = new HashMap<>();

    public NotationLibrary(Map<String, Object> configs) {
        register(AvroNotation.NOTATION_NAME, new AvroNotation(configs), null);
        register(BinaryNotation.NOTATION_NAME, new BinaryNotation(), null);
        register(CsvNotation.NOTATION_NAME, new CsvNotation(), new CsvDataObjectConverter());
        register(JsonNotation.NOTATION_NAME, new JsonNotation(), new JsonDataObjectConverter());
        register(SOAPNotation.NOTATION_NAME, new SOAPNotation(), new SOAPDataObjectConverter());
        register(XmlNotation.NOTATION_NAME, new XmlNotation(), new XmlDataObjectConverter());
    }

    public void register(String name, Notation notation) {
        register(name, notation, null);
    }

    public void register(String name, Notation notation, NotationConverter converter) {
        notationEntries.put(name, new NotationEntry(notation, converter));
    }

    public Notation get(String notation) {
        var result = notation != null ? notationEntries.get(notation) : null;
        if (result != null) return result.notation;
        throw FatalError.dataError("Data notation is not registered in the NotationLibrary: " + (notation != null ? notation : "null"));
    }

    public NotationConverter getConverter(String notation) {
        var result = notation != null ? notationEntries.get(notation) : null;
        if (result != null) return result.converter;
        throw FatalError.dataError("Data type notation not found: " + notation);
    }
}
