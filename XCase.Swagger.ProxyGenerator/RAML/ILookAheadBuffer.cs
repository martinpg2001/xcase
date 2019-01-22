using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace XCase.REST.ProxyGenerator.RAML
{
    internal interface ILookAheadBuffer
    {
        /// <summary>
        /// Gets a value indicating whether the end of the input reader has been reached.
        /// </summary>
        bool EndOfInput
        {
            get;
        }

        /// <summary>
        /// Gets the character at thhe specified offset.
        /// </summary>
        char Peek(int offset);

        /// <summary>
        /// Skips the next <paramref name="length"/> characters. Those characters must have been
        /// obtained first by calling the <see cref="Peek"/> method.
        /// </summary>
        void Skip(int length);
    }
}
