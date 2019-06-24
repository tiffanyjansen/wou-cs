using System;

namespace BitsHW3
{
    /// <summary>
    /// A FIFO queue interface. This ADT is suitable for a singly
    /// linked queue.
    /// </summary>
    /// <typeparam name="T"></typeparam>
    interface IQueue<T>
    {
        /// <summary>
        /// Add an element to the rear of the queue
        /// </summary>
        /// <param name="Element">The element we wish to add</param>
        /// <returns>the element that was enqueued</returns>
        T push(T Element);

        /// <summary>
        /// Remove and return the front of the element.
        /// </summary>
        /// <returns>The front of the element</returns>
        /// <exception cref="QueueUnderflowException">If the queue is empty</exception>
        T pop();

        /// <summary>
        /// Test if the queue is empty
        /// </summary>
        /// <returns>true if the queue is empty; otherwise false</returns>
        bool isEmpty();
    }
}