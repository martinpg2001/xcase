package com.xcase.rest.generator.raml.serialization;

import com.xcase.rest.generator.raml.events.*;

/**
 *
 * @author martinpg
 */
public interface INodeTypeResolver
{
    /// <summary>
    /// Determines the type of the specified node.
    /// </summary>
    /// <param name="nodeEvent">The node to be deserialized.</param>
    /// <param name="currentType">The type that has been determined so far.</param>
    /// <returns>
    /// true if <paramref name="currentType"/> has been resolved completely;
    /// false if the next type <see cref="INodeTypeResolver"/> should be invoked.
    /// </returns>
    boolean resolve(NodeEvent nodeEvent, Class currentType);
}
