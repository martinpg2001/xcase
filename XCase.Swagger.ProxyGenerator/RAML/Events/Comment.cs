using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace XCase.REST.ProxyGenerator.RAML.Events
{
    public class Comment : ParsingEvent
    {
        public string Value { get; private set; }
        public bool IsInline { get; private set; }

        public Comment(string value, bool isInline)
            : this(value, isInline, Mark.Empty, Mark.Empty)
        {
        }

        public Comment(string value, bool isInline, Mark start, Mark end)
            : base(start, end)
        {
            Value = value;
            IsInline = isInline;
        }

        internal override EventType Type
        {
            get { return EventType.Comment; }
        }

        public override void Accept(IParsingEventVisitor visitor)
        {
            visitor.Visit(this);
        }

        /// <summary>
        /// Returns a <see cref="T:System.String"/> that represents the current <see cref="T:System.Object"/>.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"/> that represents the current <see cref="T:System.Object"/>.
        /// </returns>
        public override string ToString()
        {
            return string.Format(
                CultureInfo.InvariantCulture,
                "{0} Comment [{1}]",
                IsInline ? "Inline" : "Block",
                Value
            );
        }
    }
}
