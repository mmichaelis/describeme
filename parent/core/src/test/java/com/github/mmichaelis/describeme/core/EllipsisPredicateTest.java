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

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests {@link EllipsisPredicate}.
 *
 * @since $SINCE$
 */
public class EllipsisPredicateTest {

  @Test
  public void exceedingMaxCountTruncates() throws Exception {
    List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
    StringBuilder appendable = new StringBuilder();
    Predicate<Object> predicate =
        new EllipsisPredicate(appendable, integers, 4, new MyBiConsumer(appendable), ", ");
    integers.stream().allMatch(predicate);
    assertThat("Result is truncated and ellipsis added.", appendable.toString(),
               is(equalTo("1, 2, 3, 4, ...")));
  }

  @Test
  public void unlimitedForUnlimitedMaxCount() throws Exception {
    List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
    StringBuilder appendable = new StringBuilder();
    Predicate<Object> predicate =
        new EllipsisPredicate(appendable, integers, -1, new MyBiConsumer(appendable), ", ");
    integers.stream().allMatch(predicate);
    assertThat("Result is truncated and ellipsis added.", appendable.toString(),
               is(equalTo("1, 2, 3, 4, 5")));
  }

  @Test
  public void exceedingMaxByFarCountTruncates() throws Exception {
    List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    StringBuilder appendable = new StringBuilder();
    Predicate<Object> predicate =
        new EllipsisPredicate(appendable, integers, 4, new MyBiConsumer(appendable), ", ");
    integers.stream().allMatch(predicate);
    assertThat("Result is truncated and ellipsis added.", appendable.toString(),
               is(equalTo("1, 2, 3, 4, ...")));
  }

  @Test
  public void limitHitsElementCountTransformsAll() throws Exception {
    List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
    StringBuilder appendable = new StringBuilder();
    Predicate<Object> predicate =
        new EllipsisPredicate(appendable, integers, 5, new MyBiConsumer(appendable), ", ");
    integers.stream().allMatch(predicate);
    assertThat("Result is truncated and ellipsis added.", appendable.toString(),
               is(equalTo("1, 2, 3, 4, 5")));
  }

  @Test
  public void onlyOneElementWithoutSeparator() throws Exception {
    List<Integer> integers = Collections.singletonList(1);
    StringBuilder appendable = new StringBuilder();
    Predicate<Object> predicate =
        new EllipsisPredicate(appendable, integers, 5, new MyBiConsumer(appendable), ", ");
    integers.stream().allMatch(predicate);
    assertThat("No separator added.", appendable.toString(),
               is(equalTo("1")));
  }

  @Test
  public void emptyListResultsInEmptyString() throws Exception {
    List<Integer> integers = Collections.emptyList();
    StringBuilder appendable = new StringBuilder();
    Predicate<Object> predicate =
        new EllipsisPredicate(appendable, integers, 5, new MyBiConsumer(appendable), ", ");
    integers.stream().allMatch(predicate);
    assertThat(appendable.toString(), is(equalTo("")));
  }

  @Test
  public void zeroLimitCausesDirectEllipsis() throws Exception {
    List<Integer> integers = Collections.singletonList(1);
    StringBuilder appendable = new StringBuilder();
    Predicate<Object> predicate =
        new EllipsisPredicate(appendable, integers, 0, new MyBiConsumer(appendable), ", ");
    integers.stream().allMatch(predicate);
    assertThat(appendable.toString(), is(equalTo("...")));
  }

  private static class MyBiConsumer implements BiConsumer<Object, Object> {

    private final StringBuilder appendable;

    MyBiConsumer(StringBuilder appendable) {
      this.appendable = appendable;
    }

    @Override
    public void accept(Object parent, Object current) {
      appendable.append(current);
    }
  }
}
