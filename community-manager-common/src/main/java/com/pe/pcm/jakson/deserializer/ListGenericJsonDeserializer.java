/*
 * Copyright (c) 2020 Pragma Edge Inc
 *
 * Licensed under the Pragma Edge Inc.
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://pragmaedge.com/licenseagreement
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pe.pcm.jakson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Chenchu Kiran.
 */
public class ListGenericJsonDeserializer<T> extends JsonDeserializer<List<T>> {

    private final Class<T> tClass;

    @SuppressWarnings("unchecked")
    public ListGenericJsonDeserializer() {
        final ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.tClass = (Class<T>) type.getActualTypeArguments()[0];
    }

    @Override
    public List<T> deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        final ObjectCodec objectCodec = jsonParser.getCodec();
        final JsonNode listOrObjectNode = objectCodec.readTree(jsonParser);
        final List<T> result = new ArrayList<>();
        if (listOrObjectNode.isArray()) {
            for (JsonNode node : listOrObjectNode) {
                result.add(objectCodec.treeToValue(node, tClass));
            }
        } else {
            result.add(objectCodec.treeToValue(listOrObjectNode, tClass));
        }
        return result;
    }
}
