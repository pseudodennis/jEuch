package Support;

public interface DequeInterface<T> extends QueueInterface<T>
{
  void enqueueFront(T element) throws QueueOverflowException;
  // Throws QueueOverflowException if this queue is full;
  // otherwise, adds element to the front of this queue.

  T dequeueRear() throws QueueUnderflowException;
  // Throws QueueUnderflowException if this queue is empty;
  // otherwise, removes rear element from this queue and returns it.
}




