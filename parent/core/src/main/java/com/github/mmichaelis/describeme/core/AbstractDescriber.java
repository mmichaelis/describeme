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

import static java.text.MessageFormat.format;
import static java.util.Objects.requireNonNull;

/**
 * @since $$SINCE:2015-03-18$$
 */
public abstract class AbstractDescriber implements InternalDescriber {
  @Override
  public final void describeTo(@Nonnull Appendable appendable,
                               @Nullable Object value,
                               int maxDepth,
                               int maxCount) {
    describeTo(appendable, value, maxCount,
               new RecursiveDescriptionConsumer(appendable, maxDepth, maxCount));
  }

  @Override
  public final void describeTo(@Nonnull Appendable appendable,
                               @Nullable Object value,
                               int maxCount,
                               @Nonnull BiConsumer<Object, Object> recursiveMeAndOtherConsumer) {
    requireNonNull(appendable, "appendable must be given.");
    if (!test(value)) {
      throw new DescriberNotApplicableException(
          format("Describer {0} not applicable to {1}.", getClass().getName(), value));
    }
      internalDescribeTo(appendable, value, maxCount, recursiveMeAndOtherConsumer);
  }

  protected abstract void internalDescribeTo(@Nonnull Appendable appendable,
                                             @Nullable Object value,
                                             int maxCount,
                                             @Nonnull BiConsumer<Object,Object> recursiveConsumer);

}
