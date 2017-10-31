package ru.otus;

import java.util.*;
import java.util.function.UnaryOperator;

public class MyArrayList<T> implements List<T> {
    private final int INITIAL = 100;
    private Object[] array;
    private int size = 0;
    private int capacity;

    public MyArrayList() {
        this.array = new Object[INITIAL];
        this.capacity = INITIAL;
    }

    public MyArrayList(int initSize) {
        if (initSize < 0) {
            initSize = INITIAL;
        }
        this.array = new Object[initSize];
        this.capacity = INITIAL;
    }


    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    @Override
    public Iterator iterator() {
        return new MyIterator(0);
    }

    @Override
    public Object[] toArray() {
        Object[] arr = new Object[this.size];
        System.arraycopy(this.array, 0, arr, 0, this.size);
        return arr;
    }

    @Override
    public boolean add(T o) {
        if (this.array.length >= this.capacity) {
            increaseArray();
        }
        this.array[this.size++] = o;

        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index != -1) {
            if (index != this.size - 1) {
                System.arraycopy(this.array, index, this.array, index - 1, this.array.length - index);
            }
            this.array[--this.size] = null;
            if (this.size < this.capacity) {
                this.capacity /= 2;
                Object[] arr = new Object[this.capacity];
                System.arraycopy(this.array, 0, arr, 0, this.array.length);
                this.array = arr;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean addAll(Collection c) {
        return addAll(this.array.length, c);
    }

    @Override
    public boolean addAll(int index, Collection c) {
        if (c == null) {
            return false;
        }
        if (!isInRange(index)) {
            throw new IndexOutOfBoundsException();
        }
        int left = this.capacity - index;
        Object[] arr = new Object[left];
        System.arraycopy(this.array, index, arr, 0, left);
        while (this.array.length + c.size() > this.capacity) {
            increaseArray();
        }
        System.arraycopy(c.toArray(), 0, this.array, index, c.size());
        System.arraycopy(arr, 0, this.array, index + 1, left);
        return true;
    }

    @Override
    public void replaceAll(UnaryOperator operator) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void sort(Comparator c) {
        Object[] a = this.toArray();
        Arrays.sort(a, (Comparator)c);
        ListIterator i = this.listIterator();
        for (int j=0; j<a.length; j++) {
            i.next();
            i.set(a[j]);
        }
    }

    @Override
    public void clear() {
        this.array = new Object[INITIAL];
        this.capacity = INITIAL;
    }

    @Override
    public T get(int index) {
        if (!isInRange(index)) {
            throw new IndexOutOfBoundsException();
        }
        return (T) this.array[index];
    }

    @Override
    public T set(int index, T element) {
        if (!isInRange(index)) {
            throw new IndexOutOfBoundsException();
        }
        this.array[index] = element;
        return element;
    }

    @Override
    public void add(int index, T element) {
        throw new UnsupportedOperationException();

    }

    @Override
    public T remove(int index) {
        if (!isInRange(index)) {
            throw new IndexOutOfBoundsException();
        }
        Object returnElement = this.array[index];
        if (index != this.size - 1) {
            System.arraycopy(this.array, index, this.array, index - 1, this.array.length - index);
        }
        this.array[--this.size] = null;
        if (this.size < this.capacity) {
            this.capacity /= 2;
            Object[] arr = new Object[this.capacity];
            System.arraycopy(this.array, 0, arr, 0, this.array.length);
            this.array = arr;
        }
        return (T) returnElement;
    }

    @Override
    public int indexOf(Object o) {
        if (this.size > 0) {
            if (o == null) {
                for (int i = 0; i < this.size; i++) {
                    if (this.array[i] == null) {
                        return i;
                    }
                }
            } else {
                for (int i = 0; i < this.size; i++) {
                    if (o.equals(this.array[i])) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int result = -1;
        if (this.size > 0) {
            if (o == null) {
                for (int i = 0; i < this.size; i++) {
                    if (this.array[i] == null) {
                        result = i;
                    }
                }
            } else {
                for (int i = 0; i < this.size; i++) {
                    if (o.equals(this.array[i])) {
                        result = i;
                    }
                }
            }
        }
        return result;
    }

    @Override
    public ListIterator<T> listIterator() {
        return new MyListIterator(0);
    }

    @Override
    public ListIterator listIterator(int index) {
        return new MyListIterator(index);
    }

    @Override
    public List subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray(Object[] a) {
        throw new UnsupportedOperationException();
    }

    private void increaseArray() {
        this.capacity *= 2;
        Object[] arr = new Object[this.capacity];
        System.arraycopy(this.array, 0, arr, 0, array.length);
        this.array = arr;
    }

    private boolean isInRange(int index) {
        return !(index < 0 || index > this.size);
    }

    private class MyIterator implements
            Iterator<T> {
        protected int cursor = 0;

        public MyIterator(int cursor) {
            if (!MyArrayList.this.isInRange(cursor)) {
                throw new IndexOutOfBoundsException("Index: " + cursor);
            }
            this.cursor = cursor;
        }

        public boolean hasNext() {
            return this.cursor < MyArrayList.this.size;
        }

        public T next() {
            if(this.hasNext()) {
                return (T)MyArrayList.this.array[this.cursor++];
            }
            throw new NoSuchElementException();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private class MyListIterator extends MyIterator implements ListIterator<T> {

        public MyListIterator(int cursor) {
            super(cursor);
        }

        @Override
        public boolean hasPrevious() {
            return cursor > 0;
        }

        @Override
        public T previous() {
            if (!hasPrevious())
                throw new NoSuchElementException();
            return (T)MyArrayList.this.array[--cursor];
        }

        @Override
        public int nextIndex() {
            return cursor;
        }

        @Override
        public int previousIndex() {
            return cursor - 1;
        }

        @Override
        public void set(T element) {
            MyArrayList.this.set(cursor - 1, element);
        }

        @Override
        public void add(T t) {

            MyArrayList.this.add(t);
        }
    }
}
