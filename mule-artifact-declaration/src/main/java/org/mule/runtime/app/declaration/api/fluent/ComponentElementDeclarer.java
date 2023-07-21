/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.app.declaration.api.fluent;

import org.mule.runtime.app.declaration.api.ComponentElementDeclaration;
import org.mule.runtime.app.declaration.api.RouteElementDeclaration;

import java.util.function.Consumer;

/**
 * Allows configuring an {@link ComponentElementDeclaration} through a fluent API
 *
 * @since 1.0
 */
public abstract class ComponentElementDeclarer<E extends ComponentElementDeclarer, D extends ComponentElementDeclaration>
    extends ParameterizedElementDeclarer<E, D>
    implements HasNestedComponentDeclarer<E>, HasNestedRoutesDeclaration<E> {

  ComponentElementDeclarer(D declaration) {
    super(declaration);
  }

  public E withConfig(String configRefName) {
    declaration.setConfigRef(configRefName);
    return (E) this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public E withComponent(ComponentElementDeclaration component) {
    declaration.addComponent(component);
    return (E) this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public E withRoute(RouteElementDeclaration component) {
    component.setDeclaringExtension(declaration.getDeclaringExtension());
    declaration.addComponent(component);
    return (E) this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public E withRoute(String name, Consumer<RouteElementDeclarer> enricher) {
    final RouteElementDeclarer declarer =
        new RouteElementDeclarer(new RouteElementDeclaration(declaration.getDeclaringExtension(), name));
    enricher.accept(declarer);
    declaration.addComponent(declarer.getDeclaration());
    return (E) this;
  }

}
