using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using XCase.REST.ProxyGenerator.RAML.Tokens;

namespace XCase.REST.ProxyGenerator.RAML
{
    public class TagDirectiveCollection: KeyedCollection<string, TagDirective>
    {
        /// <summary>
        /// Initializes a new instance of the <see cref="TagDirectiveCollection"/> class.
        /// </summary>
        public TagDirectiveCollection()
        {
        }

        /// <summary>
        /// Initializes a new instance of the <see cref="TagDirectiveCollection"/> class.
        /// </summary>
        /// <param name="tagDirectives">Initial content of the collection.</param>
        public TagDirectiveCollection(IEnumerable<TagDirective> tagDirectives)
        {
            foreach (var tagDirective in tagDirectives)
            {
                Add(tagDirective);
            }
        }

        /// <summary/>
        protected override string GetKeyForItem(TagDirective item)
        {
            return item.Handle;
        }

        /// <summary>
        /// Gets a value indicating whether the collection contains a directive with the same handle
        /// </summary>
        public new bool Contains(TagDirective directive)
        {
            return Contains(GetKeyForItem(directive));
        }
    }
}
