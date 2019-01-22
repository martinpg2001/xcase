using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace XCase.REST.ProxyGenerator.RAML.Events
{
    /// <summary>
    /// Specifies the style of a sequence.
    /// </summary>
    public enum SequenceStyle
    {
        /// <summary>
        /// Let the emitter choose the style.
        /// </summary>
        Any,

        /// <summary>
        /// The block sequence style.
        /// </summary>
        Block,

        /// <summary>
        /// The flow sequence style.
        /// </summary>
        Flow
    }
}
