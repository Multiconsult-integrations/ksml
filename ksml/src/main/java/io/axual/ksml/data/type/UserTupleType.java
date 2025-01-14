package io.axual.ksml.data.type;

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

import java.util.Arrays;
import java.util.Objects;

public class UserTupleType extends TupleType {
    private final UserType[] userTypes;

    public UserTupleType(UserType... subTypes) {
        super(dataTypesOf(subTypes));
        userTypes = subTypes;
    }

    @Override
    public String toString() {
        var subTypeStr = new StringBuilder();
        for (UserType subType : userTypes) {
            subTypeStr.append(subTypeStr.length() > 0 ? ", " : "").append(subType);
        }
        return containerName() + "<" + subTypeStr + ">";
    }

    @Override
    public String containerName() {
        return "UserTuple";
    }

    public int getUserTypeCount() {
        return userTypes.length;
    }

    public UserType getUserType(int index) {
        return userTypes[index];
    }

    private static DataType[] dataTypesOf(UserType[] userTypes) {
        var result = new DataType[userTypes.length];
        for (int index = 0; index < userTypes.length; index++) {
            result[index] = userTypes[index].dataType();
        }
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (!super.equals(other)) return false;
        return Arrays.equals(userTypes, ((UserTupleType) other).userTypes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), Arrays.hashCode(userTypes));
    }
}
