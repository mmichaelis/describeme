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

import java.util.function.BiConsumer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.github.mmichaelis.describeme.core.RootDescriber.rootDescriber;

/**
 * @since $$SINCE:2015-03-16$$
 */
public final class Describe {

  private Describe() {
  }

  public static void describeTo(@Nonnull Appendable appendable, @Nullable Object value) {
    rootDescriber().describeTo(appendable, value);
  }

  public static void describeTo(@Nonnull Appendable appendable, @Nullable Object value, int maxDepth) {
    rootDescriber().describeTo(appendable, value, maxDepth);
  }

  public static void describeTo(@Nonnull Appendable appendable, @Nullable Object value, int maxDepth, int maxCount) {
    rootDescriber().describeTo(appendable, value, maxDepth, maxCount);
  }

  static void describeTo(@Nonnull Appendable appendable, @Nullable Object value, int maxCount, @Nonnull
                         BiConsumer<Object, Object> recursiveDescriptionConsumer) {
    rootDescriber().describeTo(appendable, value, maxCount, recursiveDescriptionConsumer);
  }

  @SuppressWarnings("StringBufferWithoutInitialCapacity")
  @Nonnull
  public static String describe(@Nullable Object value, int maxDepth, int maxCount) {
    StringBuilder sb = new StringBuilder();
    describeTo(sb, value, maxDepth, maxCount);
    return sb.toString();
  }

  @Nonnull
  public static String describe(@Nullable Object value) {
    return describe(value, DescriberProperties.MAX_COUNT);
  }

  public static String describe(Object value, int maxCount) {
    int maxDepth = DescriberProperties.MAX_DEPTH;
    return describe(value, maxDepth, maxCount);
  }
}
