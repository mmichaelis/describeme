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

import static java.text.MessageFormat.format;
import static java.util.Objects.requireNonNull;

/**
 * Extension to {@link Describer} which handles recursion.
 *
 * @since $SINCE$
 */
public interface RecursiveDescriber extends Describer {

  /**
   * <p>
   * Add the description of the object to the given appendable.
   * </p>
   * <p>
   * Default implementation with unlimited recursion depth.
   * </p>
   *
   * @param appendable appendable to add value's string representation to
   * @param value      the value to describe
   * @param maxCount   limits the elements to be added to the appendable; a negative value denotes
   *                   <em>unlimited</em> - it is recommended to use {@link
   *                   DescriberProperties#UNLIMITED}
   * @throws NullPointerException            if appendable is {@code null}
   * @throws DescriberIOException            if a failure occurs accessing the appendable
   * @throws DescriberNotApplicableException if the value handed over to the Describer cannot
   *                                         be handled by this describer; you should have called
   *                                         {@link #test(Object)} before
   * @since $SINCE$
   */
  @Override
  @Contract("null, _, _ -> fail")
  default void describeTo(@NotNull Appendable appendable, @Nullable Object value, int maxCount) {
    describeTo(appendable, value, maxCount, DescriberProperties.MAX_DEPTH);
  }

  /**
   * <p>
   * Add the description of the object to the given appendable.
   * </p>
   * <p>
   * Default implementation using some pre-defined recursion consumer which respects the
   * given depth.
   * </p>
   *
   * @param appendable appendable to add value's string representation to
   * @param value      the value to describe
   * @param maxCount   limits the elements to be added to the appendable; a negative value denotes
   *                   <em>unlimited</em> - it is recommended to use {@link
   *                   DescriberProperties#UNLIMITED}
   * @param maxDepth   the maximum depth to possibly recurse into an object to describe it; a
   *                   negative value denotes <em>unlimited</em> - it is recommended to use {@link
   *                   DescriberProperties#UNLIMITED}
   * @throws NullPointerException            if appendable is {@code null}
   * @throws DescriberIOException            if a failure occurs accessing the appendable
   * @throws DescriberNotApplicableException if the value handed over to the Describer cannot
   *                                         be handled by this describer; you should have called
   *                                         {@link #test(Object)} before
   * @see #getRecursiveMeAndOtherConsumer(Appendable, int, int)
   * @since $SINCE$
   */
  @Contract("null, _, _, _ -> fail")
  default void describeTo(@NotNull Appendable appendable, @Nullable Object value, int maxCount,
                          int maxDepth) {
    describeTo(appendable, value, maxCount,
               getRecursiveMeAndOtherConsumer(appendable, maxDepth, maxCount));
  }

  /**
   * <p>
   * Add the description of the object to the given appendable.
   * </p>
   *
   * @param appendable appendable to add value's string representation to
   * @param value      the value to describe
   * @param maxCount   limits the elements to be added to the appendable; a negative value denotes
   *                   <em>unlimited</em> - it is recommended to use {@link
   *                   DescriberProperties#UNLIMITED}
   * @throws NullPointerException            if appendable and/or recursiveMeAndOtherConsumer is
   *                                         {@code null}
   * @throws DescriberIOException            if a failure occurs accessing the appendable
   * @throws DescriberNotApplicableException if the value handed over to the Describer cannot
   *                                         be handled by this describer; you should have called
   *                                         {@link #test(Object)} before
   * @since $SINCE$
   */
  @Contract("null, _, _, _ -> fail; _, _, _, null -> fail")
  default void describeTo(@NotNull Appendable appendable,
                          @Nullable Object value,
                          int maxCount,
                          @NotNull BiConsumer<Object, Object> recursiveMeAndOtherConsumer) {
    requireNonNull(appendable, "appendable must be given.");
    if (!test(value)) {
      throw new DescriberNotApplicableException(
          format("Describer {0} not applicable to {1}.", getClass().getName(), value));
    }
    recursiveDescribeTo(appendable, value, maxCount, recursiveMeAndOtherConsumer);
  }

  /**
   * <p>
   * Provides an recursive consumer.
   * </p>
   * <p>
   * By default provides a recursive consumer respecting the given depth.
   * </p>
   *
   * @param appendable appendable to add the string representation to
   * @param maxDepth   maximum depth
   * @param maxCount   maximum element count
   * @return consumer
   */
  @NotNull
  default BiConsumer<Object, Object> getRecursiveMeAndOtherConsumer(
      @NotNull Appendable appendable,
      int maxDepth,
      int maxCount) {
    return new RecursiveDescriptionConsumer(appendable, maxDepth, maxCount);
  }


  void recursiveDescribeTo(@NotNull Appendable appendable,
                           @Nullable Object value,
                           int maxCount,
                           @NotNull BiConsumer<Object, Object> recursiveConsumer);
}
