using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace XCase.REST.ProxyGenerator.RAML
{
    public class Mark
    {
        /// <summary>
        /// Gets a <see cref="Mark"/> with empty values.
        /// </summary>
        public static readonly Mark Empty = new Mark();

        /// <summary>
        /// Gets / sets the absolute offset in the file
        /// </summary>
        public int Index { get; private set; }

        /// <summary>
        /// Gets / sets the number of the line
        /// </summary>
        public int Line { get; private set; }

        /// <summary>
        /// Gets / sets the index of the column
        /// </summary>
        public int Column { get; private set; }

        public Mark()
        {
            Line = 1;
            Column = 1;
        }

        public Mark(int index, int line, int column)
        {
            if (index < 0)
            {
                throw new ArgumentOutOfRangeException("index", "Index must be greater than or equal to zero.");
            }
            if (line < 1)
            {
                throw new ArgumentOutOfRangeException("line", "Line must be greater than or equal to 1.");
            }
            if (column < 1)
            {
                throw new ArgumentOutOfRangeException("column", "Column must be greater than or equal to 1.");
            }

            Index = index;
            Line = line;
            Column = column;
        }

        /// <summary>
        /// Returns a <see cref="System.String"/> that represents this instance.
        /// </summary>
        /// <returns>
        /// A <see cref="System.String"/> that represents this instance.
        /// </returns>
        public override string ToString()
        {
            return string.Format("Line: {0}, Col: {1}, Idx: {2}", Line, Column, Index);
        }

        /// <summary />
        public override bool Equals(object obj)
        {
            return Equals(obj as Mark);
        }

        /// <summary />
        public bool Equals(Mark other)
        {
            return other != null
                && Index == other.Index
                && Line == other.Line
                && Column == other.Column;
        }

        /// <summary />
        public override int GetHashCode()
        {
            return HashCode.CombineHashCodes(
                Index.GetHashCode(),
                HashCode.CombineHashCodes(
                    Line.GetHashCode(),
                    Column.GetHashCode()
                )
            );
        }

        /// <summary />
        public int CompareTo(object obj)
        {
            if (obj == null)
            {
                throw new ArgumentNullException("obj");
            }
            return CompareTo(obj as Mark);
        }

        /// <summary />
        public int CompareTo(Mark other)
        {
            if (other == null)
            {
                throw new ArgumentNullException("other");
            }

            var cmp = Line.CompareTo(other.Line);
            if (cmp == 0)
            {
                cmp = Column.CompareTo(other.Column);
            }
            return cmp;
        }
    }
}
