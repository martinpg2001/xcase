using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace XCase.REST.ProxyGenerator.RAML.Tokens
{
    class Comment : Token
    {
        /// <summary>
        /// Gets the value of the comment
        /// </summary>
        public string Value { get; private set; }

        /// <summary>
        /// Gets a value indicating whether the comment appears other tokens on that line.
        /// </summary>
        public bool IsInline { get; private set; }

        /// <summary>
        /// Initializes a new instance of the <see cref="Comment"/> class.
        /// </summary>
        public Comment(string value, bool isInline)
            : this(value, isInline, Mark.Empty, Mark.Empty)
        {
        }

        /// <summary>
        /// Initializes a new instance of the <see cref="Comment"/> class.
        /// </summary>
        public Comment(string value, bool isInline, Mark start, Mark end)
            : base(start, end)
        {
            IsInline = isInline;
            Value = value;
        }
    }
}
