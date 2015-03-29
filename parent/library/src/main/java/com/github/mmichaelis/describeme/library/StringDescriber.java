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

package com.github.mmichaelis.describeme.library;

import static com.github.mmichaelis.describeme.core.DescriberProperties.ELLIPSIS;

import com.github.mmichaelis.describeme.core.AppendableUtil;
import com.github.mmichaelis.describeme.core.Describer;
import com.github.mmichaelis.describeme.core.DescriberProperties;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @since 1.0.0
 */
public class StringDescriber implements Describer {

  @Override
  public boolean test(@Nullable Object value) {
    return value instanceof CharSequence;
  }

  @Override
  public void describeTo(@NotNull Appendable appendable,
                         @Nullable Object value,
                         int maxCount) {
    assert value != null : "value must not be null. Did you call test() before?";
    CharSequence charSequence = (CharSequence) value;
    int stringLength = charSequence.length();
    if ((maxCount <= DescriberProperties.UNLIMITED) || (stringLength < maxCount)) {
      AppendableUtil.silentAppend(appendable, '"', charSequence, '"');
    } else {
      AppendableUtil
          .silentAppend(appendable, '"', charSequence.subSequence(0, maxCount), ELLIPSIS, '"');
    }
  }

}
