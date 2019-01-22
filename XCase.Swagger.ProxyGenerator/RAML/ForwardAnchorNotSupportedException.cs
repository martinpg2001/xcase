using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace XCase.REST.ProxyGenerator.RAML
{
    /// <summary>
    /// The exception that is thrown when an alias references an anchor
    /// that has not yet been defined in a context that does not support forward references.
    /// </summary>
    [Serializable]
    public class ForwardAnchorNotSupportedException : YamlException
    {
        /// <summary>
        /// Initializes a new instance of the <see cref="AnchorNotFoundException"/> class.
        /// </summary>
        public ForwardAnchorNotSupportedException()
        {
        }

        /// <summary>
        /// Initializes a new instance of the <see cref="AnchorNotFoundException"/> class.
        /// </summary>
        /// <param name="message">The message.</param>
        public ForwardAnchorNotSupportedException(string message)
            : base(message)
        {
        }

        /// <summary>
        /// Initializes a new instance of the <see cref="AnchorNotFoundException"/> class.
        /// </summary>
        public ForwardAnchorNotSupportedException(Mark start, Mark end, string message)
            : base(start, end, message)
        {
        }

        /// <summary>
        /// Initializes a new instance of the <see cref="AnchorNotFoundException"/> class.
        /// </summary>
        /// <param name="message">The message.</param>
        /// <param name="inner">The inner.</param>
        public ForwardAnchorNotSupportedException(string message, Exception inner)
            : base(message, inner)
        {
        }
    }
}
