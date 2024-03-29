/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.app.declaration.serialization.impl.gson.adapter;

import static com.google.gson.stream.JsonToken.NULL;
import static org.mule.runtime.app.declaration.serialization.impl.gson.adapter.ElementDeclarationSerializationUtils.COMPONENTS;
import static org.mule.runtime.app.declaration.serialization.impl.gson.adapter.ElementDeclarationSerializationUtils.DECLARING_EXTENSION;
import static org.mule.runtime.app.declaration.serialization.impl.gson.adapter.ElementDeclarationSerializationUtils.KIND;
import static org.mule.runtime.app.declaration.serialization.impl.gson.adapter.ElementDeclarationSerializationUtils.NAME;
import static org.mule.runtime.app.declaration.serialization.impl.gson.adapter.ElementDeclarationSerializationUtils.ROUTE;
import static org.mule.runtime.app.declaration.serialization.impl.gson.adapter.ElementDeclarationSerializationUtils.declareParameterizedElement;
import static org.mule.runtime.app.declaration.serialization.impl.gson.adapter.ElementDeclarationSerializationUtils.populateParameterizedObject;
import org.mule.runtime.app.declaration.api.ComponentElementDeclaration;
import org.mule.runtime.app.declaration.api.ParameterElementDeclaration;
import org.mule.runtime.app.declaration.api.RouteElementDeclaration;
import org.mule.runtime.app.declaration.api.fluent.ElementDeclarer;
import org.mule.runtime.app.declaration.api.fluent.RouteElementDeclarer;

import com.google.gson.Gson;
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
 * A {@link TypeAdapter} for serializing instances of {@link ParameterElementDeclaration}
 *
 * @since 1.4.0
 */
class RouteElementDeclarationTypeAdapter extends TypeAdapter<RouteElementDeclaration> {

  private final Gson delegate;

  RouteElementDeclarationTypeAdapter(Gson delegate) {
    this.delegate = delegate;
  }

  @Override
  public void write(JsonWriter out, RouteElementDeclaration route) throws IOException {
    if (route == null) {
      out.nullValue();
      return;
    }

    out.beginObject();
    populateParameterizedObject(delegate, out, route, ROUTE);
    out.name(COMPONENTS).jsonValue(delegate.toJson(route.getComponents()));
    out.endObject();
  }

  @Override
  public RouteElementDeclaration read(JsonReader in) throws IOException {
    if (in.peek() == NULL) {
      in.nextNull();
      return null;
    }

    final JsonElement parse = new JsonParser().parse(in);
    if (parse.isJsonObject()) {
      JsonObject jsonObject = parse.getAsJsonObject();
      JsonElement elementKind = jsonObject.get(KIND);
      if (elementKind != null && elementKind.getAsString().equals(ROUTE)) {
        String name = jsonObject.get(NAME).getAsString();
        String declaringExtension = jsonObject.get(DECLARING_EXTENSION).getAsString();
        JsonArray components = jsonObject.get(COMPONENTS).getAsJsonArray();

        RouteElementDeclarer declarer = ElementDeclarer.forExtension(declaringExtension).newRoute(name);
        declareParameterizedElement(delegate, jsonObject, declarer);
        components.forEach(c -> declarer.withComponent(delegate.fromJson(c, ComponentElementDeclaration.class)));

        return declarer.getDeclaration();
      }
    }
    return null;
  }
}
