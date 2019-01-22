namespace XCase.REST.ProxyGenerator.RAML
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;
    using System.Threading.Tasks;

    public class YamlException : Exception
    {
        /// <summary>
        /// Gets the position in the input stream where the event that originated the exception starts.
        /// </summary>
        public Mark Start { get; private set; }

        /// <summary>
        /// Gets the position in the input stream where the event that originated the exception ends.
        /// </summary>
        public Mark End { get; private set; }

        /// <summary>
        /// Initializes a new instance of the <see cref="YamlException"/> class.
        /// </summary>
        public YamlException()
        {
        }

        /// <summary>
        /// Initializes a new instance of the <see cref="YamlException"/> class.
        /// </summary>
        /// <param name="message">The message.</param>
        public YamlException(string message)
            : base(message)
        {
        }

        /// <summary>
        /// Initializes a new instance of the <see cref="YamlException"/> class.
        /// </summary>
        public YamlException(Mark start, Mark end, string message)
            : this(start, end, message, null)
        {
        }

        /// <summary>
        /// Initializes a new instance of the <see cref="YamlException"/> class.
        /// </summary>
        public YamlException(Mark start, Mark end, string message, Exception innerException)
            : base(string.Format("({0}) - ({1}): {2}", start, end, message), innerException)
        {
            Start = start;
            End = end;
        }
        /// <summary>
        /// Initializes a new instance of the <see cref="YamlException"/> class.
        /// </summary>
        /// <param name="message">The message.</param>
        /// <param name="inner">The inner.</param>
        public YamlException(string message, Exception inner)
            : base(message, inner)
        {
        }
    }
}
