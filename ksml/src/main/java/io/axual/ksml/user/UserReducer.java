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


import io.axual.ksml.data.object.DataObject;
import io.axual.ksml.python.Invoker;
import io.axual.ksml.util.DataUtil;
import org.apache.kafka.streams.kstream.Reducer;

public class UserReducer extends Invoker implements Reducer<Object> {
    public UserReducer(UserFunction function) {
        super(function);
        verifyParameterCount(2);
        verify(function.parameters[0].type().equals(function.parameters[1].type()), "Reducer should take two parameters of the same dataType");
        verify(function.parameters[0].type().equals(function.resultType.dataType()), "Reducer should return same dataType as its parameters");
    }

    @Override
    public DataObject apply(Object value1, Object value2) {
        return function.call(DataUtil.asDataObject(value1), DataUtil.asDataObject(value2));
    }
}
