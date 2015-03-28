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

import com.github.mmichaelis.describeme.core.AppendableUtil;
import com.github.mmichaelis.describeme.core.Describer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @since $SINCE$
 */
public class CharacterDescriber implements Describer {

  @Override
  public boolean test(@Nullable Object value) {
    return value instanceof Character;
  }

  @Override
  public void describeTo(@Nonnull Appendable appendable, @Nullable Object value, int maxDepth,
                         int maxCount) {
    AppendableUtil.silentAppend(appendable, "'", value, "'");
  }

}
