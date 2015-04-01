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

/**
 * <p>
 * Library if different higher level describers. The target is to provide as much information as
 * possible for different types of objects while providing as much robustness as possible for
 * too deep recursions and too long output.
 * </p>
 * <p>
 * All describers ordered by priority in
 * {@code com.github.mmichaelis.describeme.core.Describer} where the
 * {@link com.github.mmichaelis.describeme.library.FormattableDescriber} has the highest
 * priority. This way you can override the output of the DescribeMe framework easily by
 * implementing
 * {@link java.util.Formattable} in your objects.
 * </p>
 *
 * @since 1.0.0
 */
package com.github.mmichaelis.describeme.library;
