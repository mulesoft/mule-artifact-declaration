/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.app.declaration.api;

import org.mule.api.annotation.NoImplement;

/**
 * Used in {@link ParameterizedElementDeclaration#accept(ParameterizedElementDeclarationVisitor)} as a visitor pattern.
 *
 * @since 1.0
 */
@NoImplement
public interface ParameterizedElementDeclarationVisitor {

  default void visitConfigurationElementDeclaration(ConfigurationElementDeclaration configurationElementDeclaration) {
    // do nothing
  }

  default void visitConnectionElementDeclaration(ConnectionElementDeclaration connectionElementDeclaration) {
    // do nothing
  }

  default void visitComponentElementDeclaration(ComponentElementDeclaration componentElementDeclaration) {
    // do nothing
  }

}
