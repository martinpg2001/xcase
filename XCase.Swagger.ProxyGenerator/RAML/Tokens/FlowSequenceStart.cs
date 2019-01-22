namespace XCase.REST.ProxyGenerator.RAML.Tokens
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;
    using System.Threading.Tasks;

    class FlowSequenceStart : Token
    {
        /// <summary>
        /// Initializes a new instance of the <see cref="FlowSequenceStart"/> class.
        /// </summary>
        public FlowSequenceStart()
            : this(Mark.Empty, Mark.Empty)
        {
        }

        /// <summary>
        /// Initializes a new instance of the <see cref="FlowSequenceStart"/> class.
        /// </summary>
        /// <param name="start">The start position of the token.</param>
        /// <param name="end">The end position of the token.</param>
        public FlowSequenceStart(Mark start, Mark end)
            : base(start, end)
        {
        }
    }
}
