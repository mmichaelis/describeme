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

import static java.text.MessageFormat.format;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

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
 * <p>
 * For describers supporting recursion into their elements implement {@link RecursiveDescriber}.
 * </p>
 *
 * @see RecursiveDescriber
 * @since 1.0.0
 */
public interface Describer extends Predicate<Object> {

  /**
   * <p>
   * Validate if this describer is applicable to the given type. As the method gets the object
   * to describe you might also distinguish if a Describer is applicable by the concrete value.
   * </p>
   * <p>
   * Mind that an evaluation should be lightweight and should not depend on any mutable state
   * of the object. This is because {@code test()} might be called multiple times (expect two
   * times) for the same object.
   * </p>
   *
   * @param value object to validate
   * @return {@code true} if this describer can describe the given value; {@code false} otherwise
   * @since 1.0.0
   */
  @Override
  boolean test(@Nullable Object value);

  /**
   * <p>
   * Validates the value and returns it. If validation fails, thus if {@link #test(Object)}
   * return {@code false} an exception will be thrown.
   * </p>
   * <p>
   * The default implementation just calls {@link #test(Object)} for validation and throws
   * an exception if the value is not valid.
   * </p>
   *
   * @param value the value to validate
   * @return the value
   * @throws DescriberNotApplicableException if describer is not applicable
   */
  @Nullable
  default Object validatedValue(@Nullable Object value) {
    if (!test(value)) {
      throw new DescriberNotApplicableException(
          format("Describer {0} not applicable to {1}.", getClass().getName(), value));
    }
    return value;
  }

  /**
   * <p>
   * Add the description of the object to the given appendable.
   * </p>
   * <p>
   * Default implementation uses {@link DescriberProperties#MAX_COUNT} which is configurable
   * through system properties.
   * </p>
   *
   * @param appendable appendable to add value's string representation to
   * @param value      the value to describe
   * @throws NullPointerException            if appendable is {@code null}
   * @throws DescriberTempException            if a failure occurs accessing the appendable
   * @throws DescriberNotApplicableException if the value handed over to the Describer cannot
   *                                         be handled by this describer; you should have called
   *                                         {@link #test(Object)} before
   * @since 1.0.0
   */
  @Contract("null, _ -> fail")
  default void describeTo(@NotNull Appendable appendable, @Nullable Object value) {
    describeTo(appendable, value, DescriberProperties.MAX_COUNT);
  }

  /**
   * <p>
   * Add the description of the object to the given appendable.
   * </p>
   * <p>
   * On implementation it is recommended to use for example
   * {@link java.util.Objects#requireNonNull(Object)} to validate the appendable and to use
   * {@link #validatedValue(Object)} to validate that the object really matches the requirements
   * - just in case someone did not call {@link #test(Object)} before.
   * </p>
   *
   * @param appendable appendable to add value's string representation to
   * @param value      the value to describe
   * @param maxCount   limits the elements to be added to the appendable; a negative value denotes
   *                   <em>unlimited</em> - it is recommended to use {@link
   *                   DescriberProperties#UNLIMITED}; the maxCount parameter does not promise any
   *                   length of the appended String but should denote a limit the String
   *                   representation for large objects; maxCount typically refers to list or array
   *                   elements
   * @throws NullPointerException            if appendable is {@code null}
   * @throws DescriberTempException            if a failure occurs accessing the appendable
   * @throws DescriberNotApplicableException if the value handed over to the Describer cannot
   *                                         be handled by this describer; you should have called
   *                                         {@link #test(Object)} before
   * @since 1.0.0
   */
  @Contract("null, _, _ -> fail")
  void describeTo(@NotNull Appendable appendable, @Nullable Object value, int maxCount);

}
