package io.axual.ksml.user;

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


import io.axual.ksml.data.object.DataList;
import io.axual.ksml.data.object.DataObject;
import io.axual.ksml.data.type.DataType;
import io.axual.ksml.data.type.ListType;
import io.axual.ksml.exception.KSMLExecutionException;
import io.axual.ksml.python.Invoker;
import io.axual.ksml.util.DataUtil;
import org.apache.kafka.streams.kstream.ValueMapperWithKey;

public class UserKeyValueToValueListTransformer extends Invoker implements ValueMapperWithKey<Object, Object, Iterable<DataObject>> {
    public UserKeyValueToValueListTransformer(UserFunction function) {
        super(function);
        verifyParameterCount(2);
        verifyResultReturned(new ListType(DataType.UNKNOWN));
    }

    @Override
    public Iterable<DataObject> apply(Object key, Object value) {
        var result = function.call(DataUtil.asDataObject(key), DataUtil.asDataObject(value));
        if (result instanceof DataList list) {
            return list;
        }
        throw new KSMLExecutionException("Expected list result from keyValueToKeyValueList transformer: " + function.name);
    }
}
