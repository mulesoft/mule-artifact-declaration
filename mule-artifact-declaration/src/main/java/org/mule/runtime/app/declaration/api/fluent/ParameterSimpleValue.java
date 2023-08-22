/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.app.declaration.api.fluent;

import static java.util.regex.Pattern.DOTALL;
import static java.util.regex.Pattern.MULTILINE;
import static java.util.regex.Pattern.compile;
import static org.apache.commons.lang3.StringUtils.removeEnd;
import static org.apache.commons.lang3.StringUtils.removeStart;
import static org.mule.runtime.app.declaration.api.fluent.SimpleValueType.STRING;
import org.mule.runtime.app.declaration.api.ParameterElementDeclaration;
import org.mule.runtime.app.declaration.api.ParameterValue;
import org.mule.runtime.app.declaration.api.ParameterValueVisitor;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Represents the configured simple value of a given {@link ParameterElementDeclaration}.
 *
 * @since 1.0
 */
public final class ParameterSimpleValue implements ParameterValue {

  private static final Pattern CDATA = compile("^<!\\[CDATA\\[(.+)\\]\\]>$", DOTALL | MULTILINE);

  private final String text;
  private final boolean isCData;
  private final SimpleValueType type;

  private ParameterSimpleValue(String text, boolean asCData, SimpleValueType type) {
    this.text = text;
    this.isCData = asCData;
    this.type = type;
  }

  public static ParameterValue of(String text) {
    return isCData(text) ? cdata(text) : plain(text);
  }

  public static ParameterValue of(String text, SimpleValueType type) {
    return isCData(text) ? cdata(text, type) : plain(text, type);
  }

  private static boolean isCData(String text) {
    return CDATA.matcher(text).matches();
  }

  public static ParameterValue plain(String text) {
    return plain(text, STRING);
  }

  public static ParameterValue plain(String text, SimpleValueType type) {
    return new ParameterSimpleValue(text, false, type);
  }

  public static ParameterValue cdata(String text) {
    return cdata(text, STRING);
  }

  public static ParameterValue cdata(String text, SimpleValueType type) {
    text = removeEnd(removeStart(text, "<![CDATA["), "]]>");
    return new ParameterSimpleValue(text, true, type);
  }

  public String getValue() {
    return text;
  }

  public boolean isCData() {
    return isCData;
  }

  public SimpleValueType getType() {
    return type;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void accept(ParameterValueVisitor valueVisitor) {
    valueVisitor.visitSimpleValue(this);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ParameterSimpleValue that = (ParameterSimpleValue) o;
    return isCData == that.isCData && Objects.equals(text, that.text) && Objects.equals(type, that.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(text, isCData, type);
  }

  @Override
  public String toString() {
    return text;
  }

}
