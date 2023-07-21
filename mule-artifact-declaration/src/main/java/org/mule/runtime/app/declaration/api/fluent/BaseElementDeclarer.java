/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.app.declaration.api.fluent;


import org.mule.runtime.app.declaration.api.ElementDeclaration;

/**
 * Base declarer for a given {@link ElementDeclaration}
 *
 * @since 1.0
 */
public abstract class BaseElementDeclarer<T> {

  protected T declaration;

  BaseElementDeclarer(T declaration) {
    this.declaration = declaration;
  }

  /**
   * @return the {@code declaration} being built
   */
  public T getDeclaration() {
    return declaration;
  }

}
