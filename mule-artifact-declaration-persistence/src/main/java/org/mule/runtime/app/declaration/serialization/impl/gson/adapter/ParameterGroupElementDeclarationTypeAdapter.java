/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.app.declaration.serialization.impl.gson.adapter;

import static com.google.gson.stream.JsonToken.NULL;
import static org.mule.runtime.app.declaration.api.fluent.ElementDeclarer.newParameterGroup;
import org.mule.runtime.app.declaration.api.ParameterElementDeclaration;
import org.mule.runtime.app.declaration.api.ParameterGroupElementDeclaration;
import org.mule.runtime.app.declaration.api.fluent.ParameterGroupElementDeclarer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * A {@link TypeAdapter} for serializing instances of {@link ParameterGroupElementDeclaration}
 *
 * @since 1.4.0
 */
public class ParameterGroupElementDeclarationTypeAdapter extends TypeAdapter<ParameterGroupElementDeclaration> {

  private final Gson delegate;

  public ParameterGroupElementDeclarationTypeAdapter() {
    delegate = new GsonBuilder()
        .registerTypeAdapter(ParameterElementDeclaration.class, new ParameterElementDeclarationTypeAdapter())
        .create();
  }

  @Override
  public void write(JsonWriter out, ParameterGroupElementDeclaration group) throws IOException {
    if (group == null) {
      out.nullValue();
      return;
    }

    out.beginObject();
    out.name(ElementDeclarationSerializationUtils.NAME).value(group.getName());
    ElementDeclarationSerializationUtils.populateCustomizableObject(delegate, out, group);
    ElementDeclarationSerializationUtils.populateMetadataAwareObject(delegate, out, group);
    out.name(ElementDeclarationSerializationUtils.PARAMETERS).jsonValue(delegate.toJson(group.getParameters()));
    out.endObject();
  }

  @Override
  public ParameterGroupElementDeclaration read(JsonReader in) throws IOException {
    if (in.peek() == NULL) {
      in.nextNull();
      return null;
    }

    final JsonElement parse = new JsonParser().parse(in);
    if (parse.isJsonObject()) {
      JsonObject jsonObject = parse.getAsJsonObject();
      JsonElement elementName = jsonObject.get(ElementDeclarationSerializationUtils.NAME);
      JsonElement elementParameters = jsonObject.get(ElementDeclarationSerializationUtils.PARAMETERS);
      if (elementName != null && elementParameters != null) {
        ParameterGroupElementDeclarer declarer =
            ElementDeclarationSerializationUtils.declareEnrichableElement(delegate, jsonObject,
                                                                          newParameterGroup(elementName.getAsString()));

        JsonArray parameters = elementParameters.getAsJsonArray();
        parameters.forEach(p -> {
          ParameterElementDeclaration param = delegate.fromJson(p, ParameterElementDeclaration.class);
          declarer.withParameter(param.getName(), param.getValue());
        });

        return declarer.getDeclaration();
      }
    }
    return null;
  }

}
