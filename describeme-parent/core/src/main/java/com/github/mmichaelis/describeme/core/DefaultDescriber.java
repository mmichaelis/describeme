/*
 * Copyright 2015 Mark Michaelis
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.mmichaelis.describeme.core;

import static java.lang.String.valueOf;
import static java.util.Arrays.deepToString;
import static java.util.Objects.requireNonNull;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * <p>
 * Default - and especially fallback describer if other describers are not available.
 * It is a low effort implementation using Java standard abilities to transform values
 * to strings.
 * </p>
 *
 * @since 1.0.0
 */
public final class DefaultDescriber implements Describer {

  /**
   * Determine if a value is an array.
   *
   * @param value value to validate
   * @return true iff. value is an array
   */
  @Contract("null -> false")
  private static boolean isArray(@Nullable Object value) {
    return (value != null) && value.getClass().isArray();
  }

  /**
   * The DefaultDescriber accepts anything.
   *
   * @param value object to validate
   * @return always {@code true}
   */
  @Override
  @Contract("_ -> true")
  public boolean test(@Nullable Object value) {
    return true;
  }

  @Override
  @Contract("null, _, _ -> fail")
  public void describeTo(@NotNull Appendable appendable, @Nullable Object value, int maxCount) {
    requireNonNull(appendable, "appendable must be defined.");
    // No truncation applied here. The idea is that for some values you do not want to have
    // truncation at all. And the ideal solution of this default implementation is that you
    // can just use the default rather than implementing your own Describer.
    String stringValue =
        isArray(value) ? deepToString((Object[]) value) : valueOf(value);
    AppendableUtil.silentAppend(appendable, stringValue);
  }

}
