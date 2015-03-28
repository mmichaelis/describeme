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

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;
import java.util.stream.Stream;

import static com.github.mmichaelis.describeme.core.AppendableUtil.silentAppend;

/**
 * <p>
 * Recurse into streams to describe their elements. If you have any structure which could
 * be transformed to a stream this is the recommended base class to use for your Describer.
 * </p>
 * <p>
 * It automatically handles maximum depth and maximum count if requested, thus if limited.
 * The only thing to do when implementing is to implement {@link #valueAsStream(Object)}.
 * </p>
 *
 * @since $SINCE$
 */
public abstract class AbstractStreamDescriber implements RecursiveDescriber {

  /**
   * {@inheritDoc}
   * <p>
   * Value must be convertible to a stream. Stream elements will be sequentially added
   * to the appendable, separated by commas and surrounded by square brackets.
   * </p>
   *
   * @since $SINCE$
   */
  @Override
  @Contract("null, _, _, _ -> fail; _, _, _, null -> fail")
  public final void describeTo(@NotNull Appendable appendable, @Nullable Object value,
                               int maxCount,
                               @NotNull BiConsumer<Object, Object> recursiveMeAndOtherConsumer) {
    validatedValue(value);
    assert value != null : "value must not be null. Did you call test() before?";
    Stream<?> stream = valueAsStream(value);
    silentAppend(appendable, "[");
    stream.allMatch(new EllipsisPredicate(appendable, value, maxCount,
                                          recursiveMeAndOtherConsumer));
    silentAppend(appendable, "]");
  }

  /**
   * <p>
   * Provide the values to describe as stream.
   * </p>
   *
   * @param value value to convert to a stream
   * @return stream
   * @since $SINCE$
   */
  @NotNull
  protected abstract Stream<?> valueAsStream(@NotNull Object value);

}
