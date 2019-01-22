using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using XCase.REST.ProxyGenerator.RAML.Events;

namespace XCase.REST.ProxyGenerator.RAML
{
    public interface IEmitter
    {
        /// <summary>
        /// Emits an event.
        /// </summary>
        void Emit(ParsingEvent @event);
    }
}
