/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.app.declaration.api;

import org.mule.api.annotation.NoImplement;

/**
 * Adds naming methods to an element declaration
 *
 * @since 1.0
 */
@NoImplement
public interface NamedElementDeclaration {

  /**
   * @return the name of the element
   */
  String getName();

  /**
   * Set's the name of the element
   *
   * @param name the name of the element
   */
  void setName(String name);

}
