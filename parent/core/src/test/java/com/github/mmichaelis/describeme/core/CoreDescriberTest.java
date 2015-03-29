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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.io.IOException;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Formattable;
import java.util.Formatter;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.stream.Stream;

@RunWith(Parameterized.class)
public class CoreDescriberTest {

  private static final Object SOME_OBJECT = new Object();
  private static final Consumer<String> SOME_CONSUMER = (Consumer<String>) s -> {
  };
  private final Object toDescribe;
  private final String expectedDescription;

  public CoreDescriberTest(@Nullable Object toDescribe, @NotNull String expectedDescription) {
    this.toDescribe = toDescribe;
    this.expectedDescription = expectedDescription;
  }

  @NotNull
  @Parameters(name = "Test {index}: {0}, expecting toString: {1}")
  public static Collection<Object[]> data() {
    //noinspection RedundantCast
    Iterator<? extends Serializable> someIterator = Arrays.asList(1, "Test").iterator();
    Stream<? extends Serializable> someStream = Arrays.asList(1, "Test").stream();
    ToStringInterface someLambda = () -> "Lorem Ipsum Dolor";
    return Arrays.asList(
        new Object[][]{
            {null, "null"}, // 0
            {true, "true"}, // 1
            {false, "false"}, // 2
            {(byte) 0b0010_0101, "37"}, // 3
            {(short) 0b0010_0101, "37"}, // 4
            {0b11010010_01101001_10010100_10010010, "-764832622"}, // 5
            {12, "12"}, // 6
            {12L, "12"}, // 7
            {'c', "c"}, // 8
            {"Lorem Ipsum", "Lorem Ipsum"}, // 9
            {new StringBuilder("Lorem Ipsum Dolor"), "Lorem Ipsum Dolor"}, // 10
            {new StringBuffer("Lorem Ipsum"), "Lorem Ipsum"}, // 11
            {1.23456789123456789f, "1.2345679"}, // 12
            {1.23456789123456789d, "1.234567891234568"}, // 13
            {1.234e2, "123.4"}, // 14
            {SOME_OBJECT, String.valueOf(SOME_OBJECT)}, // 15
            {new Object[]{}, "[]"}, // 16
            {new Integer[]{1, 2}, "[1, 2]"}, // 17
            // Deep Test
            {new Object[]{1, new Object[]{2, new Object[]{3, new Integer[]{4}}}},
             "[1, [2, [3, [4]]]]"}, // 18
            {new Object[]{1, new Object[]{2, new Object[]{3, "Test"}}}, "[1, [2, [3, Test]]]"},
            // 19
            {Collections.<Integer>emptyList(), "[]"}, // 20
            {Arrays.asList(1, 2), "[1, 2]"}, // 21
            {Arrays.asList(1, "Test"), "[1, Test]"}, // 22
            {someIterator, String.valueOf(someIterator)}, // 23
            {someStream, String.valueOf(someStream)}, // 24
            // Can we do better for consumers?
            {SOME_CONSUMER, String.valueOf(SOME_CONSUMER)}, // 25
            // Lambdas can format themselves being Formattable
            {someLambda, String.valueOf(someLambda)}, // 26
            {new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13},
             "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13]"}, // 27
            {CoreDescriberTest.class, String.valueOf(CoreDescriberTest.class)}, // 28
        }
    );
  }

  @Test
  public void test() throws Exception {
    int maxDepth = 3;
    int maxCount = 12;
    assertThat("expecting toString value", Describe.describe(toDescribe, maxCount, maxDepth),
               is(expectedDescription));
  }

  // Idea taken from:
  // http://stackoverflow.com/questions/23628631/how-to-make-a-lambda-expression-define-tostring-in-java-8
  @FunctionalInterface
  private interface ToStringInterface extends Formattable {

    String asString();

    @Override
    default void formatTo(Formatter formatter, int flags, int width, int precision) {
      String str = asString();
      //noinspection resource
      formatter.format(MessageFormat.format("%1${0}.{1}s", width, precision), str);
      if (str.length() > precision) {
        try {
          formatter.out().append("...");
        } catch (IOException ignored) {
        }
      }
    }
  }
}
