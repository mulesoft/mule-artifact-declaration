/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
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
