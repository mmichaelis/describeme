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

import static com.github.mmichaelis.describeme.core.DescriberProperties.ELLIPSIS;
import static java.lang.String.valueOf;
import static java.util.Arrays.deepToString;

/**
 * @since $$SINCE:2015-03-16$$
 */
public final class DefaultDescriber extends AbstractDescriber {

  private static boolean isArray(@Nullable Object value) {
    return (value != null) && value.getClass().isArray();
  }

  @Override
  public boolean test(@Nullable Object value) {
    return true;
  }

  @Override
  protected void internalDescribeTo(@Nonnull Appendable appendable,
                                    @Nullable Object value,
                                    int maxCount,
                                    @Nonnull BiConsumer<Object, Object> recursiveConsumer) {
    String stringValue =
        isArray(value) ? deepToString((Object[]) value) : valueOf(value);
    int stringLength = stringValue.length();
    if ((maxCount <= DescriberProperties.UNLIMITED) || (stringLength < maxCount)) {
      AppendableUtil.silentAppend(appendable, stringValue);
    } else {
      AppendableUtil.silentAppend(appendable, stringValue.substring(0, maxCount), ELLIPSIS);
    }
  }

}
