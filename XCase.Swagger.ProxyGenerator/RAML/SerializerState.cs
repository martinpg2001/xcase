using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace XCase.REST.ProxyGenerator.RAML
{
    public sealed class SerializerState : IDisposable
    {
        private readonly IDictionary<Type, object> items = new Dictionary<Type, object>();

        public T Get<T>()
            where T : class, new()
        {
            object value;
            if (!items.TryGetValue(typeof(T), out value))
            {
                value = new T();
                items.Add(typeof(T), value);
            }
            return (T)value;
        }

        /// <summary>
        /// Invokes <see cref="IPostDeserializationCallback.OnDeserialization" /> on all
        /// objects added to this collection that implement <see cref="IPostDeserializationCallback" />.
        /// </summary>
        public void OnDeserialization()
        {
            foreach (var callback in items.Values.OfType<IPostDeserializationCallback>())
            {
                callback.OnDeserialization();
            }
        }

        public void Dispose()
        {
            foreach (var disposable in items.Values.OfType<IDisposable>())
            {
                disposable.Dispose();
            }
        }
    }
}
