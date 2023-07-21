/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.app.declaration.serialization.impl.gson.adapter;

import static com.google.gson.stream.JsonToken.NULL;
import static org.mule.runtime.app.declaration.api.fluent.ElementDeclarer.forExtension;
import static org.mule.runtime.app.declaration.serialization.impl.gson.adapter.ElementDeclarationSerializationUtils.CONNECTION;
import static org.mule.runtime.app.declaration.serialization.impl.gson.adapter.ElementDeclarationSerializationUtils.DECLARING_EXTENSION;
import static org.mule.runtime.app.declaration.serialization.impl.gson.adapter.ElementDeclarationSerializationUtils.KIND;
import static org.mule.runtime.app.declaration.serialization.impl.gson.adapter.ElementDeclarationSerializationUtils.NAME;
import static org.mule.runtime.app.declaration.serialization.impl.gson.adapter.ElementDeclarationSerializationUtils.declareParameterizedElement;
import static org.mule.runtime.app.declaration.serialization.impl.gson.adapter.ElementDeclarationSerializationUtils.populateParameterizedObject;
import org.mule.runtime.app.declaration.api.ConnectionElementDeclaration;
import org.mule.runtime.app.declaration.api.fluent.ConnectionElementDeclarer;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * A {@link TypeAdapter} for serializing instances of {@link ConnectionElementDeclaration}
 *
 * @since 1.4.0
 */
class ConnectionElementDeclarationTypeAdapter extends TypeAdapter<ConnectionElementDeclaration> {

  private final Gson delegate;

  public ConnectionElementDeclarationTypeAdapter(Gson delegate) {
    this.delegate = delegate;
  }

  @Override
  public void write(JsonWriter out, ConnectionElementDeclaration value) throws IOException {
    if (value == null) {
      out.nullValue();
      return;
    }

    out.beginObject();
    populateParameterizedObject(delegate, out, value, CONNECTION);
    out.endObject();
  }

  @Override
  public ConnectionElementDeclaration read(JsonReader in) throws IOException {
    if (in.peek() == NULL) {
      in.nextNull();
      return null;
    }

    final JsonElement parse = new JsonParser().parse(in);
    if (parse.isJsonObject()) {
      JsonObject jsonObject = parse.getAsJsonObject();
      JsonElement elementKind = jsonObject.get(KIND);
      if (elementKind != null && elementKind.getAsString().equals(CONNECTION)) {
        String name = jsonObject.get(NAME).getAsString();
        String declaringExtension = jsonObject.get(DECLARING_EXTENSION).getAsString();

        ConnectionElementDeclarer declarer = forExtension(declaringExtension).newConnection(name);
        declareParameterizedElement(delegate, jsonObject, declarer);

        return declarer.getDeclaration();
      }
    }
    return null;
  }
}
