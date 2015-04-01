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

import static java.lang.reflect.Modifier.isFinal;
import static java.lang.reflect.Modifier.isPrivate;
import static java.text.MessageFormat.format;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.SelfDescribing;
import org.hamcrest.TypeSafeMatcher;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Matcher validating Utility class requirements.
 *
 * @since 1.0.0
 */
public final class IsUtilityClassMatcher extends TypeSafeMatcher<Class<?>> {

  private static final ThreadLocal<List<SelfDescribing>>
      TL_FAILURES =
      ThreadLocal.withInitial(ArrayList::new);

  private IsUtilityClassMatcher() {
  }

  public static Matcher<Class<?>> isUtilityClass() {
    return new IsUtilityClassMatcher();
  }

  @Override
  public void describeTo(Description description) {
    description.appendText("matches utility class requirements");
  }

  @SuppressWarnings("BooleanVariableAlwaysNegated")
  @Override
  protected boolean matchesSafely(Class<?> item) {
    Constructor<?>[] constructors = item.getDeclaredConstructors();
    String className = item.getName();
    if (constructors.length == 0) {

      addMessage(format("{0} does not define any constructor.", className));
    } else {
      boolean
          allConstructorsPrivate =
          Arrays.stream(constructors)
              .allMatch(constructor -> isPrivate(constructor.getModifiers()));
      if (!allConstructorsPrivate) {
        addMessage(format("{0} has constructors which are not private.", className));
      }
      Optional<Constructor<?>>
          noArgConstructorOptional =
          Arrays.stream(constructors).filter(constructor -> constructor.getParameterCount() == 0)
              .findFirst();
      if (noArgConstructorOptional.isPresent()) {
        Constructor<?> constructor = noArgConstructorOptional.get();
        try {
          constructor.setAccessible(true);
          constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
          addMessage(
              format(
                  "{0} cannot be instantiated through no-arg constructor: {1}",
                  className, e.getMessage()));
        }
      } else {
        addMessage(format("{0} misses a no-arg constructor.", className));
      }
    }
    if (!isFinal(item.getModifiers())) {
      addMessage(format("{0} is not final.", className));
    }
    return TL_FAILURES.get().isEmpty();
  }

  @Override
  protected void describeMismatchSafely(Class<?> item, Description mismatchDescription) {
    List<SelfDescribing> failures = TL_FAILURES.get();
    mismatchDescription.appendList("Failure(s): ", " ", "", failures);
  }

  private void addMessage(String message) {
    TL_FAILURES.get().add(description -> description.appendText(message));
  }
}
