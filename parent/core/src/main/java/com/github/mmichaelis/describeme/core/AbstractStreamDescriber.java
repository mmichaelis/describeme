/*
 * Copyright 2015 Mark Michaelis
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.mmichaelis.describeme.core;

import com.google.common.base.MoreObjects;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Stream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.github.mmichaelis.describeme.core.AppendableUtil.silentAppend;

/**
 * @since $$SINCE:2015-03-20$$
 */
public abstract class AbstractStreamDescriber extends AbstractDescriber {

  @Nonnull
  private static Consumer<Object> ellipsisConsumer(@Nonnull Appendable appendable,
                                                   @Nullable Object parentObject, int maxCount,
                                                   @Nonnull BiConsumer<Object, Object> recursiveConsumer) {
    return new EllipsisConsumer(appendable, parentObject, maxCount, recursiveConsumer);
  }

  @Override
  protected final void internalDescribeTo(@Nonnull Appendable appendable, @Nullable Object value,
                                          int maxCount,
                                          @Nonnull BiConsumer<Object, Object> recursiveConsumer) {
    assert value != null : "value must not be null. Did you call test() before?";
    Stream<?> stream = valueAsStream(value);
    if (maxCount > DescriberProperties.UNLIMITED) {
      stream = stream.limit(maxCount + 1);
    }
    silentAppend(appendable, "[");
    stream.forEach(ellipsisConsumer(appendable, value, maxCount, recursiveConsumer));
    silentAppend(appendable, "]");
  }

  @Nonnull
  protected abstract Stream<?> valueAsStream(@Nonnull Object value);

  private static class EllipsisConsumer implements Consumer<Object> {

    @Nonnull
    private final Appendable appendable;
    @Nullable
    private final Object parentObject;
    private final int maxCount;
    @Nonnull
    private final BiConsumer<Object, Object> recursiveConsumer;
    private int count;

    EllipsisConsumer(@Nonnull Appendable appendable, @Nullable Object parentObject, int maxCount,
                     @Nonnull BiConsumer<Object, Object> recursiveConsumer) {
      this.appendable = appendable;
      this.parentObject = parentObject;
      this.maxCount = maxCount;
      this.recursiveConsumer = recursiveConsumer;
      count = 0;
    }

    @Override
    public void accept(@Nullable Object obj) {
      if (count > 0) {
        silentAppend(appendable, ", ");
      }
      if ((maxCount <= DescriberProperties.UNLIMITED) || (count < maxCount)) {
        recursiveConsumer.accept(parentObject, obj);
      } else {
        silentAppend(appendable, DescriberProperties.ELLIPSIS);
      }
      count++;
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

}
