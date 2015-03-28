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

import com.google.common.base.MoreObjects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;
import java.util.function.Predicate;

import static com.github.mmichaelis.describeme.core.AppendableUtil.silentAppend;
import static java.util.Objects.requireNonNull;

/**
 * <p>
 * Consumes objects to describe and appends an ellipsis once the maximum amount of
 * elements is reached. Objects are resolved recursively.
 * </p>
 * <p>
 * As predicate it signals when there is nothing more to add. So if filtering through a stream
 * you typically use {@link java.util.stream.Stream#allMatch(Predicate)} which will/might abort
 * filtering once the limit is reached.
 * </p>
 *
 * @since $SINCE$
 */
class EllipsisPredicate implements Predicate<Object> {

  @NotNull
  private final Appendable appendable;
  @Nullable
  private final Object parentObject;
  private final int maxCount;
  @NotNull
  private final BiConsumer<Object, Object> recursiveConsumer;
  private int count;

  EllipsisPredicate(@NotNull Appendable appendable,
                    @Nullable Object parentObject,
                    int maxCount,
                    @NotNull BiConsumer<Object, Object> recursiveConsumer) {
    this.appendable = requireNonNull(appendable, "Appendable must be set.");
    this.parentObject = parentObject;
    this.maxCount = maxCount;
    this.recursiveConsumer = requireNonNull(recursiveConsumer, "Recursive consumer must be set.");
    count = 0;
  }

  @Override
  public boolean test(@Nullable Object obj) {
    if (count > maxCount) {
      return false;
    }
    if (count > 0) {
      silentAppend(appendable, ", ");
    }
    boolean appended = false;
    if ((maxCount <= DescriberProperties.UNLIMITED) || (count < maxCount)) {
      recursiveConsumer.accept(parentObject, obj);
      appended = true;
    } else {
      silentAppend(appendable, DescriberProperties.ELLIPSIS);
    }
    count++;
    return appended;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("appendable", appendable)
        .add("parentObject", parentObject)
        .add("count", count)
        .add("maxCount", maxCount)
        .add("recursiveConsumer", recursiveConsumer)
        .toString();
  }
}
