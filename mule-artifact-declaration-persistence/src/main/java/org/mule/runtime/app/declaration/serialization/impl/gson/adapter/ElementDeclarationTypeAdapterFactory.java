/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.app.declaration.serialization.impl.gson.adapter;

import org.mule.runtime.app.declaration.api.ComponentElementDeclaration;
import org.mule.runtime.app.declaration.api.ConnectionElementDeclaration;
import org.mule.runtime.app.declaration.api.ElementDeclaration;
import org.mule.runtime.app.declaration.api.GlobalElementDeclaration;
import org.mule.runtime.app.declaration.api.RouteElementDeclaration;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

/**
 * {@link TypeAdapterFactory} implementation for creating {@link ElementDeclaration} {@link TypeAdapter}s
 *
 * @since 1.4.0
 */
public class ElementDeclarationTypeAdapterFactory implements TypeAdapterFactory {

  @Override
  public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
    if (GlobalElementDeclaration.class.isAssignableFrom(type.getRawType())) {
      return (TypeAdapter<T>) new GlobalElementDeclarationTypeAdapter(gson);
    } else if (ConnectionElementDeclaration.class.isAssignableFrom(type.getRawType())) {
      return (TypeAdapter<T>) new ConnectionElementDeclarationTypeAdapter(gson);
    } else if (RouteElementDeclaration.class.isAssignableFrom(type.getRawType())) {
      return (TypeAdapter<T>) new RouteElementDeclarationTypeAdapter(gson);
    } else if (ComponentElementDeclaration.class.isAssignableFrom(type.getRawType())) {
      return (TypeAdapter<T>) new ComponentElementDeclarationTypeAdapter(gson);
    }

    return null;
  }

}
