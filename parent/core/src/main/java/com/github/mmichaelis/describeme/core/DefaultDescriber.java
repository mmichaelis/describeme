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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static java.lang.String.valueOf;
import static java.util.Arrays.deepToString;

/**
 * @since $SINCE$
 */
public final class DefaultDescriber implements Describer {

  private static boolean isArray(@Nullable Object value) {
    return (value != null) && value.getClass().isArray();
  }

  @Override
  public boolean test(@Nullable Object value) {
    return true;
  }

  @Override
  public void describeTo(@Nonnull Appendable appendable, @Nullable Object value, int maxDepth,
                         int maxCount) {
    // No truncation applied here. The idea is that for some values you do not want to have
    // truncation at all. And the ideal solution of this default implementation is that you
    // can just use the default rather than implementing your own Describer.
    String stringValue =
        isArray(value) ? deepToString((Object[]) value) : valueOf(value);
    AppendableUtil.silentAppend(appendable, stringValue);
  }

}
