using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace XCase.REST.ProxyGenerator.RAML
{
    public enum ScalarStyle
    {
        /// <summary>
        /// Let the emitter choose the style.
        /// </summary>
        Any,

        /// <summary>
        /// The plain scalar style.
        /// </summary>
        Plain,

        /// <summary>
        /// The single-quoted scalar style.
        /// </summary>
        SingleQuoted,

        /// <summary>
        /// The double-quoted scalar style.
        /// </summary>
        DoubleQuoted,

        /// <summary>
        /// The literal scalar style.
        /// </summary>
        Literal,

        /// <summary>
        /// The folded scalar style.
        /// </summary>
        Folded,
    }
}
