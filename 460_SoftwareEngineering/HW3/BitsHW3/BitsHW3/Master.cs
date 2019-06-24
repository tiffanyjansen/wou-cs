using System;
using System.Text;
using System.Collections.Generic;

namespace BitsHW3
{
    /// <summary>
    /// Original by Sumit Ghosh "An Interesting Method to Generate Binary Numbers from 1 to n"
    /// at https://www.geeksforgeeks.org/interesting-method-generate-binary-numbers-1-n/
    /// 
    /// Adapted for CS 460 HW3.  This simple example demonstrates the rather powerful
    /// application of Breadth-First Search to enumeration of states problems.
    /// 
    /// here are easier ways to generate a list of binary values, but this technique
    ///  is very general and a good one to remember for other uses.
    /// </summary>
    class Master
    {
        /// <summary>
        /// Print the binary representation of all numbers from 1 up to Number.
        /// This is accomplished by using a FIFO queue to perform a level 
        /// order (i.e. BFS) traversal of a virtual binary tree that 
        /// looks like this:
        ///                 1
        ///             /       \
        ///            10       11
        ///           /  \     /  \
        ///         100  101  110  111
        ///         etc.
        /// and then storing each "value" in a list as it is "visited".
        /// </summary>
        /// <param name="Number">The number we would like to use</param>
        /// <returns>a LinkedList</returns>
        static LinkedList<string> GenerateBinaryRepresentationList(int Number)
        {
            //Create an empty queue of strings with which to perform the traversal
            LinkedQueue<StringBuilder> Queue = new LinkedQueue<StringBuilder>();

            //A list for returning the binary values
            LinkedList<string> Output = new LinkedList<string>();

            if(Number < 1)
            {
                //binary representation of negative values is not supported
                //return an empty list
                return Output;
            }

            //Enqueue the first binary number. Use a dynamic string to avoid string concat
            Queue.push(new StringBuilder("1"));

            //BFS
            while(Number-- > 0)
            {
                //print out the frot of queue
                StringBuilder QueueFront = Queue.pop();
                Output.AddLast(QueueFront.ToString());

                //Make a copy
                StringBuilder QueueFrontCopy = new StringBuilder(QueueFront.ToString());

                //Left child
                QueueFront.Append('0');
                Queue.push(QueueFront);
                //Right child
                QueueFrontCopy.Append('1');
                Queue.push(QueueFrontCopy);
            }
            return Output;
        }

        /// <summary>
        /// Driver program to test above function
        /// </summary>
        /// <param name="args"></param>
        static void Main(string[] args)
        {
            int Number = 10;
            if(args.Length < 1)
            {
                System.Console.WriteLine("Please invoke with the max value to print binary up to, like this:");
                System.Console.WriteLine("\tMaster 12");
                return;
            }
            try
            {
                Number = System.Convert.ToInt32(args[0]);
            }
            catch(NotFiniteNumberException)
            {
                System.Console.WriteLine("I'm sorry, I can't understand the number: " + args[0]);
                return;
            }

            LinkedList<string> Output = GenerateBinaryRepresentationList(Number);
            //Print it right justified. Longest string is the last one.
            //Print enough spaces to move it over the correct distance.
            int MaxLength = Output.Last.Value.Length;
            foreach(string s in Output)
            {
                for(int i = 0; i < MaxLength - s.Length; ++i)
                {
                    System.Console.Write(" ");
                }
                System.Console.WriteLine(s);
            }
        }
    }
}
