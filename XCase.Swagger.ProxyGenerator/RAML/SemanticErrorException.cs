using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace XCase.REST.ProxyGenerator.RAML
{
    class SemanticErrorException : YamlException
    {
        /// <summary>
        /// Initializes a new instance of the <see cref="SemanticErrorException"/> class.
        /// </summary>
        public SemanticErrorException()
        {
        }

        /// <summary>
        /// Initializes a new instance of the <see cref="SemanticErrorException"/> class.
        /// </summary>
        /// <param name="message">The message.</param>
        public SemanticErrorException(string message)
            : base(message)
        {
        }

        /// <summary>
        /// Initializes a new instance of the <see cref="SemanticErrorException"/> class.
        /// </summary>
        public SemanticErrorException(Mark start, Mark end, string message)
            : base(start, end, message)
        {
        }

        /// <summary>
        /// Initializes a new instance of the <see cref="SemanticErrorException"/> class.
        /// </summary>
        /// <param name="message">The message.</param>
        /// <param name="inner">The inner.</param>
        public SemanticErrorException(string message, Exception inner)
            : base(message, inner)
        {
        }
    }
}
