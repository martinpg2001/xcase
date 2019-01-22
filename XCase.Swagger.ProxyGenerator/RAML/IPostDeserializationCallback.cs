using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace XCase.REST.ProxyGenerator.RAML
{
    public interface IPostDeserializationCallback
    {
        void OnDeserialization();
    }
}
