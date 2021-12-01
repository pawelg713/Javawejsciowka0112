package pl.sggw;

/**
 Capacity-restricted Queue
 @see <a href="https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/Queue.html">Queue</a>
 */

public interface WzimQueue<E> {

    public boolean add(final E e);

    public boolean offer(final E e);

    public E remove();

    public E poll();

    public E element();

    public E peek();

    /**
     *
     * @return current size
     */
    public int size();

    /**
     *
     * @return maximum number of elements that this queue can hold
     */
    public int capacity();
}