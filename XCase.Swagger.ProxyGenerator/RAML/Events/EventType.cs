using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace XCase.REST.ProxyGenerator.RAML.Events
{
    internal enum EventType
    {
        None,
        StreamStart,
        StreamEnd,
        DocumentStart,
        DocumentEnd,
        Alias,
        Scalar,
        SequenceStart,
        SequenceEnd,
        MappingStart,
        MappingEnd,
        Comment,
    }
}
