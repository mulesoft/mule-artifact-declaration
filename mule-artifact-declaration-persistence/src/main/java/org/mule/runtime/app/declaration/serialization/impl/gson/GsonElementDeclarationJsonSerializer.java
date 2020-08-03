/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.app.declaration.serialization.impl.gson;

import org.mule.runtime.app.declaration.api.ElementDeclaration;
import org.mule.runtime.app.declaration.api.ParameterGroupElementDeclaration;
import org.mule.runtime.app.declaration.api.ParameterValue;
import org.mule.runtime.app.declaration.serialization.api.ElementDeclarationJsonSerializer;
import org.mule.runtime.app.declaration.serialization.impl.gson.adapter.ElementDeclarationTypeAdapterFactory;
import org.mule.runtime.app.declaration.serialization.impl.gson.adapter.ParameterGroupElementDeclarationTypeAdapter;
import org.mule.runtime.app.declaration.serialization.impl.gson.adapter.ParameterValueTypeAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Implementation of an {@link ElementDeclarationJsonSerializer} that uses {@link Gson} as the underlying serializer.
 */
public class GsonElementDeclarationJsonSerializer implements ElementDeclarationJsonSerializer {

  private boolean prettyPrint;

  /**
   * Configures all required {@link TypeAdapter}s for a {@link Gson} serializer to be able to serialize and
   * deserialize instances of {@link ElementDeclaration}
   *
   * @param gsonBuilder the {@link GsonBuilder} to configure
   * @return the same {@link GsonBuilder} received.
   */
  public static GsonBuilder configureGsonForElementDeclaration(GsonBuilder gsonBuilder) {
    return gsonBuilder.registerTypeAdapterFactory(new ElementDeclarationTypeAdapterFactory())
        .registerTypeAdapter(ParameterValue.class, new ParameterValueTypeAdapter())
        .registerTypeAdapter(ParameterGroupElementDeclaration.class, new ParameterGroupElementDeclarationTypeAdapter());
  }

  public GsonElementDeclarationJsonSerializer(boolean prettyPrint) {
    this.prettyPrint = prettyPrint;
  }

  /**
   * Serializes an {@link ElementDeclaration} into JSON
   *
   * @param declaration {@link ElementDeclaration} to be serialized
   * @return {@link String} JSON representation of the {@link ElementDeclaration}
   */
  @Override
  public String serialize(ElementDeclaration declaration) {
    return createGson().toJson(declaration);
  }

  /**
   * Deserializes a JSON representation of an {@link ElementDeclaration}, to an actual instance of it.
   *
   * @param declaration serialized {@link ElementDeclaration}
   * @return an instance of {@link ElementDeclaration} based in the JSON
   */
  @Override
  public <T extends ElementDeclaration> T deserialize(String declaration, Class<T> clazz) {
    return createGson().fromJson(declaration, clazz);
  }

  private Gson createGson() {
    GsonBuilder gsonBuilder = configureGsonForElementDeclaration(new GsonBuilder());

    if (prettyPrint) {
      gsonBuilder.setPrettyPrinting();
    }

    return gsonBuilder.create();
  }

}

