/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.app.declaration.api.fluent;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.app.declaration.api.RouteElementDeclaration;

import java.util.function.Consumer;

/**
 * Allows configuring a nested {@link RouteElementDeclaration} through a fluent API
 *
 * @since 1.0
 * @deprecated Use mule-artifact-ast instead.
 */
@NoImplement
@Deprecated
public interface HasNestedRoutesDeclaration<T extends BaseElementDeclarer> {

  /**
   * Adds a {@link RouteElementDeclaration component} to the declaration being built
   *
   * @param declaration the {@link RouteElementDeclaration component} to add
   * @return {@code this} declarer
   */
  T withRoute(RouteElementDeclaration declaration);

  /**
   * Adds a {@link RouteElementDeclaration component} to the declaration being built
   *
   * @param name                the {@code name} of the new {@link RouteElementDeclaration}
   * @param declarationEnricher an enricher that should populate the given {@link RouteElementDeclarer} with the route
   *                            configuration
   * @return {@code this} declarer
   */
  T withRoute(String name, Consumer<RouteElementDeclarer> declarationEnricher);

}
