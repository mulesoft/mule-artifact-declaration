/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.app.declaration.api.fluent;

/**
 * Provides a way to enrich a parameterized element with a parameter with an identifier of type {@link K}, and a value of type
 * {@link V} using a builder pattern with return type {@link R}
 *
 * @since 1.0
 */
public interface ParameterizedBuilder<K, V, R> {

  R withParameter(K key, V value);
}
