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

import static com.github.mmichaelis.describeme.core.RootDescriber.rootDescriber;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;

/**
 * <p>
 * Describes a given value by strategies found along the classpath.
 * </p>
 *
 * @since 1.0.0
 */
public final class Describe {

  /**
   * Some rough guess how long strings might be.
   */
  private static final int INITIAL_DESCRIPTION_CAPACITY = 128;

  /**
   * Utility class, do not create.
   */
  private Describe() {
  }

  /**
   * <p>
   * Add the description of the object to the given appendable.
   * </p>
   *
   * @param appendable appendable to add value's string representation to
   * @param value      the value to describe
   * @throws NullPointerException if appendable is {@code null}
   * @throws DescriberIoException if a failure occurs accessing the appendable
   * @since 1.0.0
   */
  @Contract("null, _ -> fail")
  public static void describeTo(@NotNull Appendable appendable, @Nullable Object value) {
    rootDescriber().describeTo(appendable, value);
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
   *                   DescriberProperties#UNLIMITED}; the maxCount parameter does not promise any
   *                   length of the appended String but should denote a limit the String
   *                   representation for large objects; maxCount typically refers to list or array
   *                   elements
   * @throws NullPointerException if appendable is {@code null}
   * @throws DescriberIoException if a failure occurs accessing the appendable
   * @since 1.0.0
   */
  @Contract("null, _, _ -> fail")
  public static void describeTo(@NotNull Appendable appendable, @Nullable Object value,
                                int maxCount) {
    rootDescriber().describeTo(appendable, value, maxCount);
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
   * @param maxDepth   the maximum depth to possibly recurse into an object to describe it; a
   *                   negative value denotes <em>unlimited</em> - it is recommended to use {@link
   *                   DescriberProperties#UNLIMITED}
   * @throws NullPointerException if appendable is {@code null}
   * @throws DescriberIoException if a failure occurs accessing the appendable
   * @since 1.0.0
   */
  public static void describeTo(@NotNull Appendable appendable, @Nullable Object value,
                                int maxCount, int maxDepth) {
    rootDescriber().describeTo(appendable, value, maxCount, maxDepth);
  }

  /**
   * <p>
   * Describe the given value as string with the given restrictions applied.
   * </p>
   *
   * @param value    value to describe
   * @param maxCount limits the length of the value; the limit might refer to number of characters
   *                 or number of elements in an array - and describers might ignore it
   * @param maxDepth the maximum depth to recurse into e. g. arrays, lists and maps
   * @return value as String representation
   * @since 1.0.0
   */
  @NotNull
  public static String describe(@Nullable Object value, int maxCount, int maxDepth) {
    StringBuilder sb = new StringBuilder(INITIAL_DESCRIPTION_CAPACITY);
    describeTo(sb, value, maxCount, maxDepth);
    return sb.toString();
  }

  /**
   * <p>
   * Describe the given value as string.
   * </p>
   * <p>
   * While by default no constraints apply to the string transformation it can be controlled
   * through
   * system properties as denoted by {@link DescriberProperties#P_DESCRIBE_MAX_DEPTH} and
   * {@link DescriberProperties#P_DESCRIBE_MAX_COUNT} which are be default <em>unlimited</em>.
   * </p>
   *
   * @param value value to describe
   * @return value as String representation
   * @since 1.0.0
   */
  @NotNull
  public static String describe(@Nullable Object value) {
    StringBuilder sb = new StringBuilder(INITIAL_DESCRIPTION_CAPACITY);
    describeTo(sb, value);
    return sb.toString();
  }

  /**
   * <p>
   * Describe the given value as string with the given restrictions applied.
   * </p>
   * <p>
   * While by default recursive structures are not limited, the depth can be limited by setting
   * the system property as denoted by {@link DescriberProperties#P_DESCRIBE_MAX_DEPTH}. Any
   * negative value means: <em>unlimited</em>.
   * </p>
   *
   * @param value    value to describe
   * @param maxCount limits the length of the value; the limit might refer to number of characters
   *                 or number of elements in an array - and describers might ignore it
   * @return value as String representation
   * @since 1.0.0
   */
  @NotNull
  public static String describe(@Nullable Object value, int maxCount) {
    StringBuilder sb = new StringBuilder(INITIAL_DESCRIPTION_CAPACITY);
    describeTo(sb, value, maxCount);
    return sb.toString();
  }

  /**
   * <p>
   * Add the description of the object to the given appendable and recurse into
   * sub elements of value using the {@code recursiveMeAndOtherConsumer}.
   * </p>
   *
   * @param appendable                  appendable to add value's string representation to
   * @param value                       the value to describe
   * @param maxCount                    limits the elements to be added to the appendable; a
   *                                    negative value denotes
   *                                    <em>unlimited</em> - it is recommended to use {@link
   *                                    DescriberProperties#UNLIMITED}
   * @param recursiveMeAndOtherConsumer consumer for recursion; call it with current value and
   *                                    sub element as second argument such that the consumer can
   *                                    detect self-contained object hierarchies
   * @throws NullPointerException if appendable and/or recursiveMeAndOtherConsumer is
   *                              {@code null}
   * @throws DescriberIoException if a failure occurs accessing the appendable
   * @since 1.0.0
   */
  @Contract("null, _, _, _ -> fail; _, _, _, null -> fail")
  static void describeTo(@NotNull Appendable appendable,
                         @Nullable Object value,
                         int maxCount,
                         @NotNull BiConsumer<Object, Object> recursiveMeAndOtherConsumer) {
    rootDescriber().describeTo(appendable, value, maxCount, recursiveMeAndOtherConsumer);
  }

}
