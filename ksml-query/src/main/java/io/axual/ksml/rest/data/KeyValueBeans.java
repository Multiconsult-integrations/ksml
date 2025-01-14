package io.axual.ksml.rest.data;

/*-
 * ========================LICENSE_START=================================
 * KSML Queryable State Store
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

import java.util.ArrayList;
import java.util.List;

/**
 * POJO representing store data elements
 */
public class KeyValueBeans {
    private final List<KeyValueBean> elements = new ArrayList<>();

    public List<KeyValueBean> elements() {
        return elements;
    }

    public KeyValueBeans add(Object key, Object value) {
        return add(new KeyValueBean(key, value));
    }

    public KeyValueBeans add(KeyValueBean element) {
        elements.add(element);
        return this;
    }

    public KeyValueBeans add(KeyValueBeans otherBeans) {
        elements.addAll(otherBeans.elements);
        return this;
    }

    @Override
    public String toString() {
        return "StoreData{" + "elements=" + elements + '}';
    }
}
