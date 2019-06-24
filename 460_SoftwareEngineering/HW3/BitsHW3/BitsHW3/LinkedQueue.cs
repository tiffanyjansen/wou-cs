using System;

namespace BitsHW3
{
    /// <summary>
    /// A Singly linked FIFO Queue.
    /// From Dale, Joyce and Weems "Object-Oriented Data Structures Using Java"
    /// Modified for CS 460 HW3
    /// 
    /// See IQueue.cs for documentation
    /// </summary>
    class LinkedQueue<T> : IQueue<T>
    {
        private Node<T> Front;
        private Node<T> Rear;

        public LinkedQueue()
        {
            Front = null;
            Rear = null;
        }

        public T push(T element)
        {
            if(element == null)
            {
                throw new ArgumentNullException();
            }

            if (isEmpty())
            {
                Node<T> Temp = new Node<T>(element, null);
                Rear = Front = Temp;
            }
            else
            {
                //General case
                Node<T> Temp = new Node<T>(element, null);
                Rear.Next = Temp;
                Rear = Temp;
            }
            return element;
        }

        public T pop()
        {
            T Temp = default(T);
            if (isEmpty())
            {
                throw new QueueUnderflowException("The queue was empty when pop was invoked.");
            }
            else if(Front == Rear)
            {
                // one item in queue
                Temp = Front.Data;
                Front = null;
                Rear = null;
            }
            else
            {
                //General case
                Temp = Front.Data;
                Front = Front.Next;
            }
            return Temp;
        }

        public bool isEmpty()
        {
            if(Front == null && Rear == null)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    }
}