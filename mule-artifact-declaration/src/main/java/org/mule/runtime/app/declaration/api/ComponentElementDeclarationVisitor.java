/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.app.declaration.api;

import org.mule.runtime.app.declaration.api.ComponentElementDeclaration;
import org.mule.runtime.app.declaration.api.ConstructElementDeclaration;
import org.mule.runtime.app.declaration.api.OperationElementDeclaration;
import org.mule.runtime.app.declaration.api.RouteElementDeclaration;
import org.mule.runtime.app.declaration.api.SourceElementDeclaration;

/**
 * Visitor that can handle all subclasses of {@link ComponentElementDeclaration}
 *
 * @since 1.4.0
 */
public interface ComponentElementDeclarationVisitor {

  default void visitConstructElementDeclaration(ConstructElementDeclaration constructElementDeclaration) {
    // do nothing
  }

  default void visitOperationElementDeclaration(OperationElementDeclaration operationElementDeclaration) {
    // do nothing
  }

  default void visitSourceElementDeclaration(SourceElementDeclaration sourceElementDeclaration) {
    // do nothing
  }

  default void visitRouteElementDeclaration(RouteElementDeclaration routeElementDeclaration) {
    // do nothing
  }

}
