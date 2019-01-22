namespace XCase.REST.ProxyGenerator.RAML
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;
    using System.Threading.Tasks;
    using XCase.REST.ProxyGenerator.RAML.Tokens;

    public interface IScanner
    {
        /// <summary>
        /// Gets the current position inside the input stream.
        /// </summary>
        /// <value>The current position.</value>
        Mark CurrentPosition
        {
            get;
        }

        /// <summary>
        /// Gets the current token.
        /// </summary>
        Token Current
        {
            get;
        }

        /// <summary>
        /// Moves to the next token and consumes the current token.
        /// </summary>
        bool MoveNext();

        /// <summary>
        /// Moves to the next token without consuming the current token.
        /// </summary>
        bool MoveNextWithoutConsuming();

        /// <summary>
        /// Consumes the current token.
        /// </summary>
        void ConsumeCurrent();
    }
}
