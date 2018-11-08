package list;

import lombok.Getter;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class DLL<E> implements List<E>  {
    private Node<E> head;
    private Node<E> tail;
    private int size;

    public DLL(){
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        Node tmp = head;
        while(tmp != null) {
            if(Objects.equals(tmp, o)) return true;
            tmp = tmp.next;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new DLLIterator<E>(head);
    }

    @Override
    public Object[] toArray() {
        Object[] objects= new Object[size];
        Node<E> tmp = head;
        int i = 0;
        while (tmp != null) {
            objects[i++] = tmp.item;
            tmp = tmp.next;
        }
        return objects;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size)
            a = (T[])java.lang.reflect.Array.newInstance(
                    a.getClass().getComponentType(), size);
        int i = 0;
        Object[] result = a;
        for (Node x = head; x != null; x = x.next)
            result[i++] = x.item;

        if (a.length > size)
            a[size] = null;

        return a;
    }

    @Override
    public boolean add(E t) {
        addAndGetPointer(t);
        return true;
    }

    public Node addAndGetPointer(E t){
        final Node l = tail;
        final Node newNode = new Node<>(null, t, null);
        if(tail == null) tail = newNode;
        if(head == null) {
            head = newNode;
        }else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
        return newNode;
    }

    @Override
    public boolean remove(Object o) {
        if(o instanceof DLL.Node) {
            Node n = (Node)o;
            Node nPrev = n.prev;
            Node nNext = n.next;
            if(nPrev != null) nPrev.next = nNext;
            if(nNext != null) nNext.prev = nPrev;
            if(n == tail){
                if(nPrev == null) {
                    head = null;
                    tail = null;
                }
                else tail = nPrev;
            }
            size--;
        }else throw new IllegalArgumentException("Object must be instance of Node");
        return true;
    }

    public E removeTail(){
        E tailE = null;
        if(tail != null) {
            System.out.println("Removing tail of list : " + tail.item);
            tailE = tail.item;
            remove(tail);
        }
        return tailE;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for(E e : c) add(e);
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        while (head != null) {
            Node tmp = head;
            head = head.next;
            clearNode(tmp);
        }
        clearNode(tail);
        size = 0;
    }

    private void clearNode(Node node){
        node.next = null;
        node.prev = null;
        node.item = null;
        node = null;
    }


    @Override
    public E get(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E set(int index, E element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, E element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<E> listIterator() {
        return new DLLIterator<>(head);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    public boolean makeHead(Node<E> n) {
        remove(n);
        n.prev = null;
        n.next = head;
        head.prev = n;
        head = n;
        return true;
    }
//    public boolean swapNode(Node<E> n1, Node<E> n2){
//        Node n1Prev = n1.prev;
//        Node n1Next = n1.next;
//        Node n2Prev = n2.prev;
//        Node n2Next = n2.next;
//
//
//    }
//
//    public boolean swapWithHead(Node<E> n){
//        return swapNode(head, n);
//    }


    public static class Node<E> {
        @Getter E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private static class DLLIterator<E> implements ListIterator<E> {
        private Node<E> currentNode;
        private int currentIndex;

        public DLLIterator(Node<E> currentNode) {
            checkNull(currentNode);
            this.currentNode = currentNode;
            this.currentIndex = 0;
        }

        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        @Override
        public E next() {
            if (!hasNext())
                throw new NoSuchElementException();
            E item = currentNode.item;
            currentNode = currentNode.next;
            currentIndex++;
            return item;
        }

        @Override
        public boolean hasPrevious() {
            return currentNode.prev != null;
        }

        @Override
        public E previous() {
            if(!hasPrevious()) throw new NoSuchElementException("No previous element found");
            E item = currentNode.prev.item;
            currentNode = currentNode.prev;
            currentIndex--;
            return item;
        }

        @Override
        public int nextIndex() {
            return currentIndex;
        }

        @Override
        public int previousIndex() {
            return currentIndex-1;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Can't be removed using iterator");
        }

        @Override
        public void set(E e) {
            throw new UnsupportedOperationException("Can't be set using iterator");
        }

        @Override
        public void add(E e) {
            throw new UnsupportedOperationException("Can't be added using iterator");

        }

        private void checkNull(Node<E> node){
            if(node == null) throw new IllegalArgumentException("Iterator can't be created with null");
        }
    }
}
