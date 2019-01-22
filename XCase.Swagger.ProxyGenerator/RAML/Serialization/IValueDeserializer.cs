using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using XCase.REST.ProxyGenerator.RAML;

namespace XCase.REST.ProxyGenerator.RAML.Serialization
{
    public interface IValueDeserializer
    {
        object DeserializeValue(IParser parser, Type expectedType, SerializerState state, IValueDeserializer nestedObjectDeserializer);
    }
}
