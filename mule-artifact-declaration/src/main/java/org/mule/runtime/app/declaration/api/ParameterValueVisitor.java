/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.app.declaration.api;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.app.declaration.api.fluent.ParameterListValue;
import org.mule.runtime.app.declaration.api.fluent.ParameterObjectValue;
import org.mule.runtime.app.declaration.api.fluent.ParameterSimpleValue;

/**
 * Used in {@link ParameterValue#accept(ParameterValueVisitor)} as a visitor pattern.
 *
 * @since 1.0
 */
@NoImplement
public interface ParameterValueVisitor {

  default void visitSimpleValue(ParameterSimpleValue text) {
    // do nothing
  }

  default void visitListValue(ParameterListValue list) {
    // do nothing
  }

  default void visitObjectValue(ParameterObjectValue objectValue) {
    // do nothing
  }
}
