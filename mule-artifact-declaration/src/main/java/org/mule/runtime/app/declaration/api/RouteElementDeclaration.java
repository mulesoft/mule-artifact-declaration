/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.app.declaration.api;

/**
 * A programmatic descriptor of a {@link org.mule.runtime.api.meta.model.nested.NestedRouteModel} configuration.
 *
 * @since 1.0
 */
public final class RouteElementDeclaration extends ComponentElementDeclaration<RouteElementDeclaration> {

  public RouteElementDeclaration(String extension, String name) {
    setDeclaringExtension(extension);
    setName(name);
  }

  @Override
  public void accept(ComponentElementDeclarationVisitor visitor) {
    visitor.visitRouteElementDeclaration(this);
  }
}
