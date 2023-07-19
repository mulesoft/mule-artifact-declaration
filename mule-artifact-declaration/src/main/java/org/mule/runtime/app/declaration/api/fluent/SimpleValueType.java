/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.app.declaration.api.fluent;

/**
 * Represents the type of a {@link ParameterSimpleValue}'s value
 *
 * @since 1.2
 */
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
