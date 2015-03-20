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

import java.util.function.Predicate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @since $$SINCE:2015-03-16$$
 */
public interface Describer extends Predicate<Object> {

  /**
   * <p>
   * Validate if this describer is applicable to the given type.
   * </p>
   *
   * @param value object to validate
   * @return {@code true} if this describer can describe the given value; {@code false} otherwise
   */
  @Override
  boolean test(@Nullable Object value);

  default void describeTo(@Nonnull Appendable appendable, @Nullable Object value) {
    describeTo(appendable, value, DescriberProperties.MAX_DEPTH);
  }

  default void describeTo(@Nonnull Appendable appendable, @Nullable Object value, int maxDepth) {
    describeTo(appendable, value, maxDepth, DescriberProperties.MAX_COUNT);
  }

  void describeTo(@Nonnull Appendable appendable, @Nullable Object value, int maxDepth, int maxCount);

}
