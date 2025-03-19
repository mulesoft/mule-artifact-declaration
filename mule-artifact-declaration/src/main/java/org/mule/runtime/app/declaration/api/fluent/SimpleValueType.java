/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.app.declaration.api.fluent;

/**
 * Represents the type of a {@link ParameterSimpleValue}'s value
 *
 * @since 1.2
 * @deprecated Use mule-artifact-ast instead.
 */
@Deprecated
public enum SimpleValueType {

  /**
   * Represents a value of {@link org.mule.metadata.api.model.BooleanType} type
   */
  BOOLEAN,

  /**
   * Represents a value of {@link org.mule.metadata.api.model.DateTimeType} type
   */
  DATETIME,

  /**
   * Represents a value of {@link org.mule.metadata.api.model.TimeType} type
   */
  TIME,

  /**
   * Represents a value of {@link org.mule.metadata.api.model.NumberType} type
   */
  NUMBER,

  /**
   * Represents a value of {@link org.mule.metadata.api.model.StringType} or any other
   * {@link org.mule.metadata.api.model.SimpleType} type which is not explicitly listed before.
   */
  STRING
}
