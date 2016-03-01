/**
 * Stucco ToxML API - a Java interface for parsing and creating ToxML documents
 * Copyright (C) 2016 Scott Miller - Leadscope, Inc.
 *
 * Leadscope, Inc. licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.leadscope.stucco;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * A container of toxml objects - could be an ordered list or unordered set
 */
public interface ToxmlObjectContainer<T extends ToxmlObject> extends ToxmlObjectParent {
  /**
   * Adds a listener to this container.  Listeners will be notified
   * in reverse of the order in which they were added here (as per Swing convention).
   * <p>These listeners are held with hard references - so if you want your listener
   * to be garbage collectable while this composite is still being referenced,
   * you'll need to call removeListener(listener).
   */
  void addListener(ContainerListener<T> listener);
  
  /**
   * Removes a listener for updates to this container. Call this method when you
   * no longer want the listener to receive updates - and/or when you
   * want the listener to be garbage collected, but this composite is still being
   * referenced.
   * @param listener the listener to add
   */
  void removeListener(ContainerListener<T> listener);
  
  /**
   * Gets the tag under which the child objects are stored
   * @return the child tag
   */
  String getChildTag();
  
  /**
   * Gets the class of the child objects
   * @return the child class
   */
  Class<T> getChildClass();
  
  /**
   * Adds a child to this container
   * @param child the new child to add
   */
  void addChild(T child);
  
  /**
   * Creates a new child and adds it to the container - this method should generally only be
   * used for creating non-primitive values
   * @return the newly created value that was added
   */
  T addNew();
  
  /**
   * Removes the given child from this container. Removes the first occurence of the child that
   * equals() to this child. 
   * @param child the child to remove
   * @return true iff the child was found and removed
   */
  boolean removeChild(T child);

  /**
   * Gets the children currently stored in this container
   */
  List<T> getValues();

  default boolean isEmpty() {
    return getValues().isEmpty();
  }

  default int size() {
    return getValues().size();
  }

  default T get(int i) {
    return getValues().get(i);
  }

  default List<T> getChildren() {
    return new ArrayList<>(getValues());
  }

  /**
   * Returns a stream over the children
   */
  default Stream<T> stream() {
    return getValues().stream();
  }

  default Iterator<T> iterator() {
    return getValues().iterator();
  }

  default long count() {
    return stream().count();
  }

  default Stream<T> skip(long n) {
    return stream().skip(n);
  }
  default Stream<T> filter(Predicate<? super T> predicate) {
    return stream().filter(predicate);
  }
  default <R> Stream<R> map(Function<? super T, ? extends R> mapper) {
    return stream().map(mapper);
  }
  default IntStream mapToInt(ToIntFunction<? super T> mapper) {
    return stream().mapToInt(mapper);
  }
  default LongStream mapToLong(ToLongFunction<? super T> mapper) {
    return stream().mapToLong(mapper);
  }
  default DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper) {
    return stream().mapToDouble(mapper);
  }
  default <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
    return stream().flatMap(mapper);
  }
  default IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper) {
    return stream().flatMapToInt(mapper);
  }
  default LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper) {
    return stream().flatMapToLong(mapper);
  }
  default DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper) {
    return stream().flatMapToDouble(mapper);
  }
  default Stream<T> distinct() {
    return stream().distinct();
  }
  default Stream<T> sorted() {
    return stream().sorted();
  }
  default Stream<T> sorted(Comparator<? super T> comparator) {
    return stream().sorted(comparator);
  }
  default Stream<T> peek(Consumer<? super T> action) {
    return stream().peek(action);
  }
  default Stream<T> limit(long maxSize) {
    return stream().limit(maxSize);
  }
  default void forEach(Consumer<? super T> action) {
    stream().forEach(action);
  }
  default void forEachOrdered(Consumer<? super T> action) {
    stream().forEachOrdered(action);
  }
  default Object[] toArray() {
    return stream().toArray();
  }
  default <A> A[] toArray(IntFunction<A[]> generator) {
    return stream().toArray(generator);
  }
  default T reduce(T identity, BinaryOperator<T> accumulator) {
    return stream().reduce(identity, accumulator);
  }
  default Optional<T> reduce(BinaryOperator<T> accumulator) {
    return stream().reduce(accumulator);
  }
  default <U> U reduce(U identity,
               BiFunction<U, ? super T, U> accumulator,
               BinaryOperator<U> combiner) {
    return stream().reduce(identity, accumulator, combiner);
  }
  default <R> R collect(Supplier<R> supplier,
                BiConsumer<R, ? super T> accumulator,
                BiConsumer<R, R> combiner) {
    return stream().collect(supplier, accumulator, combiner);
  }
  default <R, A> R collect(Collector<? super T, A, R> collector) {
    return stream().collect(collector);
  }
  default Optional<T> min(Comparator<? super T> comparator) {
    return stream().min(comparator);
  }
  default Optional<T> max(Comparator<? super T> comparator) {
    return stream().max(comparator);
  }
  default boolean anyMatch(Predicate<? super T> predicate) {
    return stream().anyMatch(predicate);
  }
  default boolean allMatch(Predicate<? super T> predicate) {
    return stream().allMatch(predicate);
  }
  default boolean noneMatch(Predicate<? super T> predicate) {
    return stream().noneMatch(predicate);
  }
  default Optional<T> findFirst() {
    return stream().findFirst();
  }
  default Optional<T> findAny() {
    return stream().findAny();
  }
}
