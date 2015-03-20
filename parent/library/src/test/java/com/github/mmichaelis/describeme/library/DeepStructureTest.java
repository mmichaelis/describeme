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

package com.github.mmichaelis.describeme.library;

import com.github.mmichaelis.describeme.core.Describe;
import com.github.mmichaelis.describeme.core.DescriberProperties;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

/**
 * Tests deep structures to be transformed into a string. Ideally StackOverflowErrors should
 * not occur.
 *
 * @since $$SINCE:2015-03-20$$
 */
public class DeepStructureTest {

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @SuppressWarnings({"JUnitTestMethodWithNoAssertions", "ObjectAllocationInLoop"})
  @Test
  public void testDeepList() throws Exception {
    List<Object> startObjects = new ArrayList<>(2);
    List<Object> objects = startObjects;
    for (int depth = 0; depth < 1000; depth++) {
      List<Object> newObjects = new ArrayList<>(2);
      objects.add(depth);
      objects.add(newObjects);
      objects = newObjects;
    }
    String result =
        Describe.describe(startObjects, DescriberProperties.UNLIMITED);
    assertThat("Result should be truncated.", result,
               containsString(DescriberProperties.RECURSION_PLACEHOLDER));
  }

  @Test
  public void testRecursion() throws Exception {
    Object[] array = new Object[1];
    array[0] = array;
    String result =
        Describe.describe(array, DescriberProperties.UNLIMITED);
    assertThat("Recursion should have been prevented.", result,
               containsString(DescriberProperties.RECURSION_PLACEHOLDER));
  }
}
