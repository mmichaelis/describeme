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

import java.util.function.Predicate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * <p>
 * A describer is responsible for describing specific objects where describing means to provide
 * some representation as String.
 * </p>
 * <p>
 * The components are delivered via the Service Provider. Do not forget to register Describers
 * in
 * </p>
 * <pre>{@code
 * resources/META-INF/services/com.github.mmichaelis.describeme.core.Describer
 * }</pre>
 *
 * @see RecursiveDescriber
 * @since $SINCE$
 */
public interface Describer extends Predicate<Object> {

  /**
   * <p>
   * Validate if this describer is applicable to the given type. As the method gets the object
   * to describe you might also distinguish if a Describer is applicable by the concrete value.
   * </p>
   *
   * @param value object to validate
   * @return {@code true} if this describer can describe the given value; {@code false} otherwise
   * @since $SINCE$
   */
  @Override
  boolean test(@Nullable Object value);

  /**
   * <p>
   * Add the description of the object to the given appendable.
   * </p>
   * <dl>
   * <dt><strong>Defaults:</strong></dt>
   * <dd>
   * <dl>
   * <dt>{@code maxDepth}:</dt>
   * <dd>{@link DescriberProperties#MAX_DEPTH}</dd>
   * <dt>{@code maxCount}:</dt>
   * <dd>{@link DescriberProperties#MAX_COUNT}</dd>
   * </dl>
   * </dd>
   * </dl>
   *
   * @param appendable appendable to add value's string representation to
   * @param value      the value to describe
   * @throws NullPointerException            if appendable is {@code null}
   * @throws DescriberIOException            if a failure occurs accessing the appendable
   * @throws DescriberNotApplicableException if the value handed over to the Describer cannot
   *                                         be handled by this describer; you should have called
   *                                         {@link #test(Object)} before
   * @since $SINCE$
   */
  @Contract("null, _ -> fail")
  default void describeTo(@Nonnull Appendable appendable, @Nullable Object value) {
    describeTo(appendable, value, DescriberProperties.MAX_DEPTH);
  }

  /**
   * <p>
   * Add the description of the object to the given appendable.
   * </p>
   * <dl>
   * <dt><strong>Defaults:</strong></dt>
   * <dd>
   * <dl>
   * <dt>{@code maxCount}:</dt>
   * <dd>{@link DescriberProperties#MAX_COUNT}</dd>
   * </dl>
   * </dd>
   * </dl>
   *
   * @param appendable appendable to add value's string representation to
   * @param value      the value to describe
   * @param maxDepth   the maximum depth to possibly recurse into an object to describe it; a
   *                   negative value denotes <em>unlimited</em> - it is recommended to use {@link
   *                   DescriberProperties#UNLIMITED}
   * @throws NullPointerException            if appendable is {@code null}
   * @throws DescriberIOException            if a failure occurs accessing the appendable
   * @throws DescriberNotApplicableException if the value handed over to the Describer cannot
   *                                         be handled by this describer; you should have called
   *                                         {@link #test(Object)} before
   * @since $SINCE$
   */
  @Contract("null, _, _ -> fail")
  default void describeTo(@Nonnull Appendable appendable, @Nullable Object value, int maxDepth) {
    describeTo(appendable, value, maxDepth, DescriberProperties.MAX_COUNT);
  }

  /**
   * <p>
   * Add the description of the object to the given appendable.
   * </p>
   *
   * @param appendable appendable to add value's string representation to
   * @param value      the value to describe
   * @param maxDepth   the maximum depth to possibly recurse into an object to describe it; a
   *                   negative value denotes <em>unlimited</em> - it is recommended to use {@link
   *                   DescriberProperties#UNLIMITED}
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
  @Contract("null, _, _, _ -> fail")
  void describeTo(@Nonnull Appendable appendable, @Nullable Object value, int maxDepth,
                  int maxCount);

}
