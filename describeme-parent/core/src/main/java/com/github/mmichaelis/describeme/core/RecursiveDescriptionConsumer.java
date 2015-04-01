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

import static com.github.mmichaelis.describeme.core.AppendableUtil.silentAppend;
import static java.util.Objects.requireNonNull;

import com.google.common.base.MoreObjects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.BiConsumer;

/**
 * <p>
 * Consumer to dive into recursive structures to describe them.
 * </p>
 * <p>
 * The describer takes care of the recursion as well as to abort when a maximum depth is reached.
 * </p>
 *
 * @see DescriberProperties
 * @since 1.0.0
 */
class RecursiveDescriptionConsumer implements BiConsumer<Object, Object> {

  private static final int DEJA_VU_INITIAL_CAPACITY = 16;
  @NotNull
  private final Appendable appendable;
  private final int maxDepth;
  private final int maxCount;
  /**
   * Adapted from {@link java.util.Arrays#deepToString(Object[])} this field shall prevent
   * self-contained recursive structures.
   */
  private final Collection<Object> dejaVu = new HashSet<>(DEJA_VU_INITIAL_CAPACITY);
  private int currentDepth;

  RecursiveDescriptionConsumer(@NotNull Appendable appendable, int maxDepth, int maxCount) {
    this.appendable = requireNonNull(appendable, "appendable must be set.");
    this.maxDepth = maxDepth;
    this.maxCount = maxCount;
  }

  /**
   * <p>
   * Describes the given value and prevents recursion by remembering its parent.
   * </p>
   *
   * @param me    the parent which is currently described
   * @param other the child element which needs to be described
   */
  @Override
  public void accept(Object me, Object other) {
    if (isMaxDepthReached()) {
      silentAppend(appendable, DescriberProperties.RECURSION_PLACEHOLDER);
      return;
    }
    remember(me);
    down();
    try {
      if ((other != null) && dejaVu.contains(other)) {
        silentAppend(appendable, DescriberProperties.RECURSION_PLACEHOLDER);
      } else {
        Describe.describeTo(appendable, other, maxCount, this);
      }
    } finally {
      up();
      forget(me);
    }
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("appendable", appendable)
        .add("maxDepth", maxDepth)
        .add("maxCount", maxCount)
        .add("currentDepth", currentDepth)
        .add("dejaVu", dejaVu)
        .toString();
  }

  /**
   * Remember object so that we do not report it again during recursion.
   *
   * @param me object to remember
   * @return {@code true} if added, {@code false} if not
   */
  private boolean remember(@Nullable Object me) {
    return dejaVu.add(me);
  }

  /**
   * <p>
   * Forget object, as we left recursion. In other words: The same object might occur
   * multiple times on the same depth but not as descendant of itself.
   * </p>
   *
   * @param me object to forget
   * @return {@code true} if removed, {@code false} if not
   */
  private boolean forget(@Nullable Object me) {
    return dejaVu.remove(me);
  }

  /**
   * Increase depth. Only count depth if it is actually limited in order to prevent
   * overflows.
   */
  private void down() {
    if (maxDepth > DescriberProperties.UNLIMITED) {
      currentDepth++;
    }
  }

  /**
   * Decrease depth but only until depth 0 is reached.
   */
  private void up() {
    if (currentDepth > 0) {
      currentDepth--;
    }
  }

  /**
   * Validate if consumer is at maximum depth.
   *
   * @return {@code true} if maximum depth is reached; {@code false} otherwise
   */
  private boolean isMaxDepthReached() {
    return currentDepth == maxDepth;
  }
}
