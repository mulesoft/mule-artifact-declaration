/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.app.declaration.internal.serialization;

import org.mule.runtime.app.declaration.api.ElementDeclaration;
import org.mule.runtime.app.declaration.api.ParameterGroupElementDeclaration;
import org.mule.runtime.app.declaration.api.ParameterValue;
import org.mule.runtime.app.declaration.api.serialization.ElementDeclarationJsonSerializer;
import org.mule.runtime.app.declaration.internal.serialization.adapter.ElementDeclarationTypeAdapterFactory;
import org.mule.runtime.app.declaration.internal.serialization.adapter.ParameterGroupElementDeclarationTypeAdapter;
import org.mule.runtime.app.declaration.internal.serialization.adapter.ParameterValueTypeAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Default implementation of an {@link ElementDeclarationJsonSerializer}
 *
 * @since 1.0
 */
public class DefaultElementDeclarationJsonSerializer implements ElementDeclarationJsonSerializer {

  private boolean prettyPrint;

  public DefaultElementDeclarationJsonSerializer(boolean prettyPrint) {
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
    GsonBuilder gsonBuilder = new GsonBuilder()
        .registerTypeAdapterFactory(new ElementDeclarationTypeAdapterFactory())
        .registerTypeHierarchyAdapter(ParameterValue.class, new ParameterValueTypeAdapter())
        .registerTypeAdapter(ParameterGroupElementDeclaration.class, new ParameterGroupElementDeclarationTypeAdapter());

    if (prettyPrint) {
      gsonBuilder.setPrettyPrinting();
    }

    return gsonBuilder.create();
  }

}

