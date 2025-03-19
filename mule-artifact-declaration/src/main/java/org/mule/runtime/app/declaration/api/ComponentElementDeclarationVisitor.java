/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.app.declaration.api;

/**
 * Visitor that can handle all subclasses of {@link ComponentElementDeclaration}
 *
 * @since 1.4.0
 * @deprecated Use mule-artifact-ast instead.
 */
@Deprecated
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
