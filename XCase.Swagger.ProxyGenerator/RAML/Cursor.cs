using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace XCase.REST.ProxyGenerator.RAML
{
    public class Cursor
    {
        public int Index { get; set; }
        public int Line { get; set; }
        public int LineOffset { get; set; }

        public Cursor()
        {
            Line = 1;
        }

        public Cursor(Cursor cursor)
        {
            Index = cursor.Index;
            Line = cursor.Line;
            LineOffset = cursor.LineOffset;
        }

        public Mark Mark()
        {
            return new Mark(Index, Line, LineOffset + 1);
        }

        public void Skip()
        {
            Index++;
            LineOffset++;
        }

        public void SkipLineByOffset(int offset)
        {
            Index += offset;
            Line++;
            LineOffset = 0;
        }

        public void ForceSkipLineAfterNonBreak()
        {
            if (LineOffset != 0)
            {
                Line++;
                LineOffset = 0;
            }
        }
    }
}
