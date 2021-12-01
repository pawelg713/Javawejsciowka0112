



public class SimpleWzimBoundedQueue<E> implements pl.sggw.WzimQueue<E> {


    int dodaj, zabierz, licz;

    public SimpleWzimBoundedQueue(final int maxCapacity) {
        final  E[] items = new E[maxCapacity];
    }

    public boolean add(final E e)
    {
        while(licz == items.length)
        {
            try {
                wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        int i = 0;
        while(items != null)
        {

            i++;
        }
        if(i < 100)
        {
            items[i] = e;
            return true;
        }
        else
            return false;
    }
    public boolean offer(final E e)
    {
        int i = 0;
        while(items != null)
        {

            i++;
        }
        if(i < 100)
        {
            items[i] = e;
            return true;
        }
        else
            try {
                throw new Exception();
            } catch (Exception k) {

            }
            return false;
    }

    public E remove()
    {
        if(items == null)
        {
            try {
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        int i = 0;
        while(items != null)
        {

            i++;
        }
        E x = items[i];
        items[i]  = null;
        return  x;
    }

    public E poll()
    {
        if(items == null)
        {

                try {
                    throw new Exception();
                } catch (Exception e) {
                    e.printStackTrace();
                }

        }
        int i = 0;
        while(items != null)
        {

            i++;
        }
        return items[i];
    }

    public E element()
    {
        if(items == null)
        {
            try {
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        int i = 0;
        while(items != null)
        {

            i++;
        }
        return items[i];
    }

    public E peek()
    {
        int i = 0;
        while(items != null)
        {

            i++;
        }
        return items[i];
    }

    /**
     *
     * @return current size
     */
    public int size()
    {
        return items.length;
    }

    /**
     *
     * @return maximum number of elements that this queue can hold
     */
    public int capacity()
    {
        return licz;
    }

}
