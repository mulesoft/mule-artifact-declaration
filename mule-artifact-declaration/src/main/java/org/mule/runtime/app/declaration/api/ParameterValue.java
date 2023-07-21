/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.app.declaration.api;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.app.declaration.api.fluent.ParameterObjectValue;

/**
 * Represents the configured value of a given {@link ParameterElementDeclaration}
 *
 * @since 1.0
 */
@NoImplement
public interface ParameterValue {

  /**
   * Dispatches to the method with prefix "visit" with the specific value type as argument. Example
   * {@code visitObjectValue(ParameterObjectValue objectValue) } will be called when this value is an
   * {@link ParameterObjectValue}.
   * 
   * @param valueVisitor the visitor
   */
  void accept(ParameterValueVisitor valueVisitor);

}
