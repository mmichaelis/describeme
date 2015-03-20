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

import org.slf4j.Logger;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.BiConsumer;

import javax.annotation.Nonnull;

import static com.github.mmichaelis.describeme.core.AppendableUtil.silentAppend;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @since $$SINCE:2015-03-19$$
 */
class RecursiveDescriptionConsumer implements BiConsumer<Object, Object> {

  private static final Logger LOG = getLogger(RecursiveDescriptionConsumer.class);

  private static final int DEJA_VU_INITIAL_CAPACITY = 16;
  @Nonnull
  private final Appendable appendable;
  private final int maxDepth;
  private final int maxCount;
  private final Collection<Object> dejaVu = new HashSet<>(DEJA_VU_INITIAL_CAPACITY);
  private int currentDepth;

  RecursiveDescriptionConsumer(@Nonnull Appendable appendable, int maxDepth, int maxCount) {
    this.appendable = appendable;
    this.maxDepth = DescriberProperties.MAX_DEPTH_FORCE ? DescriberProperties.MAX_DEPTH : maxDepth;
    this.maxCount = maxCount;
  }


  @Override
  public void accept(Object me, Object other) {
    if (isMaxDepthReached()) {
      silentAppend(appendable, DescriberProperties.RECURSION_PLACEHOLDER);
      return;
    }
    try {
      remember(me);
      down();
      try {
        if (dejaVu.contains(other)) {
          silentAppend(appendable, DescriberProperties.RECURSION_PLACEHOLDER);
        } else {
          Describe.describeTo(appendable, other, maxCount, this);
        }
      } finally {
        up();
        forget(me);
      }
    } catch (StackOverflowError e) {
      LOG.error(
          "Unable to evaluate description of object at depth {}. Please either increase JVM stacksize with -Xss or limit recursion depth with property {} (current value: {}).",
          currentDepth, DescriberProperties.P_DESCRIBE_MAX_DEPTH, DescriberProperties.MAX_DEPTH);
      throw e;
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

  private boolean remember(Object me) {
    return dejaVu.add(me);
  }

  private boolean forget(Object me) {
    return dejaVu.remove(me);
  }

  private void down() {
    currentDepth++;
  }

  private void up() {
    if (currentDepth > 0) {
      currentDepth--;
    }
  }

  private boolean isMaxDepthReached() {
    return currentDepth == maxDepth;
  }
}
