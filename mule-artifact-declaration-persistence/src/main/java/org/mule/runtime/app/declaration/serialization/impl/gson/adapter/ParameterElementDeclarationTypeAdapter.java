/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.app.declaration.serialization.impl.gson.adapter;

import static com.google.gson.stream.JsonToken.NULL;
import static org.mule.runtime.app.declaration.serialization.impl.gson.adapter.ElementDeclarationSerializationUtils.NAME;
import static org.mule.runtime.app.declaration.serialization.impl.gson.adapter.ElementDeclarationSerializationUtils.PROPERTIES;
import static org.mule.runtime.app.declaration.serialization.impl.gson.adapter.ElementDeclarationSerializationUtils.VALUE;
import static org.mule.runtime.app.declaration.serialization.impl.gson.adapter.ElementDeclarationSerializationUtils.populateMetadataAwareObject;
import org.mule.runtime.app.declaration.api.ParameterElementDeclaration;
import org.mule.runtime.app.declaration.api.ParameterValue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

/**
 * A {@link TypeAdapter} for serializing instances of {@link ParameterElementDeclaration}
 *
 * @since 1.4.0
 */
public class ParameterElementDeclarationTypeAdapter extends TypeAdapter<ParameterElementDeclaration> {

  private final Gson delegate;

  public ParameterElementDeclarationTypeAdapter() {
    delegate = new GsonBuilder()
        .registerTypeAdapter(ParameterValue.class, new ParameterValueTypeAdapter())
        .create();
  }

  @Override
  public void write(JsonWriter out, ParameterElementDeclaration parameter) throws IOException {
    if (parameter == null) {
      out.nullValue();
      return;
    }

    out.beginObject();
    out.name(NAME).value(parameter.getName());
    populateMetadataAwareObject(delegate, out, parameter);
    out.name(VALUE).jsonValue(delegate.toJson(parameter.getValue(), ParameterValue.class));
    out.endObject();
  }

  @Override
  public ParameterElementDeclaration read(JsonReader in) throws IOException {
    if (in.peek() == NULL) {
      in.nextNull();
      return null;
    }

    final JsonElement parse = new JsonParser().parse(in);
    if (parse.isJsonObject()) {
      JsonObject jsonObject = parse.getAsJsonObject();
      JsonElement elementName = jsonObject.get(NAME);
      JsonElement elementValue = jsonObject.get(VALUE);
      if (elementName != null && elementValue != null) {

        ParameterElementDeclaration declaration = new ParameterElementDeclaration();
        declaration.setName(elementName.getAsString());
        declaration.setValue(delegate.fromJson(elementValue, ParameterValue.class));

        Map<String, Serializable> properties =
            delegate.fromJson(jsonObject.get(PROPERTIES), new TypeToken<Map<String, Serializable>>() {}.getType());
        properties.forEach(declaration::addMetadataProperty);
        return declaration;
      }
    }
    return null;
  }

}
