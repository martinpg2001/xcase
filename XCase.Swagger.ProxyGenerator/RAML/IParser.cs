using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using XCase.REST.ProxyGenerator.RAML.Events;

namespace XCase.REST.ProxyGenerator.RAML
{
    /// <summary>
    /// Represents a YAML stream paser.
    /// </summary>
    public interface IParser
    {
        /// <summary>
        /// Gets the current event. Returns null before the first call to <see cref="MoveNext" />,
        /// and also after <see cref="MoveNext" /> returns false.
        /// </summary>
        ParsingEvent Current { get; }

        /// <summary>
        /// Moves to the next event.
        /// </summary>
        /// <returns>Returns true if there are more events available, otherwise returns false.</returns>
        bool MoveNext();
    }
}
