/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.app.declaration.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

public class ComponentElementDeclarationVisitorTestCase {

  @Test
  public void visitOperation() {
    ComponentElementDeclaration<?> operationElementDeclaration = new OperationElementDeclaration();
    ComponentElementDeclarationVisitor componentElementDeclarationVisitor = mock(ComponentElementDeclarationVisitor.class);
    operationElementDeclaration.accept(componentElementDeclarationVisitor);
    verify(componentElementDeclarationVisitor).visitOperationElementDeclaration(any());
  }

  @Test
  public void visitSource() {
    ComponentElementDeclaration<?> sourceElementDeclaration = new SourceElementDeclaration();
    ComponentElementDeclarationVisitor componentElementDeclarationVisitor = mock(ComponentElementDeclarationVisitor.class);
    sourceElementDeclaration.accept(componentElementDeclarationVisitor);
    verify(componentElementDeclarationVisitor).visitSourceElementDeclaration(any());
  }

  @Test
  public void visitRoute() {
    ComponentElementDeclaration<?> routeElementDeclaration = new RouteElementDeclaration("EXT", "name");
    ComponentElementDeclarationVisitor componentElementDeclarationVisitor = mock(ComponentElementDeclarationVisitor.class);
    routeElementDeclaration.accept(componentElementDeclarationVisitor);
    verify(componentElementDeclarationVisitor).visitRouteElementDeclaration(any());
  }

  @Test
  public void visitConstruct() {
    ComponentElementDeclaration<?> constructElementDeclaration = new ConstructElementDeclaration();
    ComponentElementDeclarationVisitor componentElementDeclarationVisitor = mock(ComponentElementDeclarationVisitor.class);
    constructElementDeclaration.accept(componentElementDeclarationVisitor);
    verify(componentElementDeclarationVisitor).visitConstructElementDeclaration(any());
  }

}
