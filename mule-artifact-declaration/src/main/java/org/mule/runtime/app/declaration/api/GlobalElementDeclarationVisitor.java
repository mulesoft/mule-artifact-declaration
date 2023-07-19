/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.app.declaration.api;

import org.mule.api.annotation.NoImplement;

/**
 * Used in {@link GlobalElementDeclaration#accept(GlobalElementDeclarationVisitor)} as a visitor pattern.
 *
 * @since 1.0
 */
@NoImplement
public interface GlobalElementDeclarationVisitor {

  default void visit(ConfigurationElementDeclaration declaration) {
    // do nothing
  }

  default void visit(TopLevelParameterDeclaration declaration) {
    // do nothing
  }

  default void visit(ConstructElementDeclaration declaration) {
    // do nothing
  }
}
