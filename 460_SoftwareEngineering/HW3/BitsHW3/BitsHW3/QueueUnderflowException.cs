using System;

namespace BitsHW3
{
    /// <summary>
    /// A custom unchecked exception to represent situations where
    /// an illegal operation was performed on an empty queue.
    /// </summary>
    class QueueUnderflowException : InvalidOperationException
    {
        public QueueUnderflowException()
            :base()
        {
        }

        public QueueUnderflowException(string message)
            :base(message)
        {
        }
    }
}
