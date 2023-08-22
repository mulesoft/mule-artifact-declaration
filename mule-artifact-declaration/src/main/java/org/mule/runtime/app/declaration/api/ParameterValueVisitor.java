/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
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
