/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.app.declaration.internal.utils;

import static java.lang.String.join;
import static org.mule.runtime.app.declaration.internal.utils.Preconditions.checkArgument;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Utility class to validate the names used for global components name attribute.
 *
 * @since 1.0
 */
public class NameValidationUtil {

  private static final List<String> SPECIAL_CHARACTERS = Arrays.asList("/", "[", "]", "{", "}", "#");
  private static final Pattern SPECIAL_CHARACTERS_PATTERN = Pattern.compile("[/\\[\\]{}#]");

  /**
   * Validates that the given {@code string} does not contain any {@link #SPECIAL_CHARACTERS} character
   *
   * @param string the string to validate.
   * @throws IllegalArgumentException if the string contains an invalid character.
   */
  public static void verifyStringDoesNotContainsReservedCharacters(String string) {
    checkArgument(!SPECIAL_CHARACTERS_PATTERN.matcher(string).find(),
                  "Invalid character used in location. Invalid characters are " + join(",", SPECIAL_CHARACTERS));
  }

}
