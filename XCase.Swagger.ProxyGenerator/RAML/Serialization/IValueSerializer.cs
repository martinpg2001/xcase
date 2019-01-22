using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace XCase.REST.ProxyGenerator.RAML.Serialization
{
    namespace YamlDotNet.Serialization
    {
        public interface IValueSerializer
        {
            void SerializeValue(IEmitter emitter, object value, Type type);
        }
    }
}
