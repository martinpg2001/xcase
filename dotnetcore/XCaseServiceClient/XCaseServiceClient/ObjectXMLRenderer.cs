namespace XCaseServiceClient
{
    using System;
    using System.Collections.Generic;
    using System.IO;
    using System.Linq;
    using System.Reflection;
    using System.Text;
    using System.Xml;
    using Microsoft.Extensions.Logging;

    public class ObjectXMLRenderer
    {
        #region Logger Setup

        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILogger Log = (new LoggerFactory()).CreateLogger(MethodBase.GetCurrentMethod().DeclaringType);

        #endregion

        static XmlDocument objectXmlDocument = new XmlDocument();

        public static XmlElement RenderObject(XmlElement parentObjectElement, object o, string name)
        {
            Log.LogDebug("starting RenderObject({0})", o.GetType().Name);
            XmlElement objectXmlElement;
            if (parentObjectElement != null)
            {
                Log.LogDebug("parentObjectElement is not null");
                objectXmlElement = parentObjectElement;
            }
            else
            {
                Log.LogDebug("parentObjectElement is null");
                objectXmlElement = objectXmlDocument.CreateElement("type");
            }

            XmlAttribute propertyNameXmlAttribute = objectXmlDocument.CreateAttribute("name");
            propertyNameXmlAttribute.Value = name;
            if (ObjectFactory.IsArrayType(o.GetType()))
            {
                Log.LogDebug("object is array type");
                Type elementType = o.GetType().GetElementType();
                XmlElement arrayElement = objectXmlDocument.CreateElement("array" + elementType.Name);
                arrayElement.Attributes.Append(propertyNameXmlAttribute);
                objectXmlElement.AppendChild(arrayElement);
                Log.LogDebug("appended array element");
                object elementObject = ObjectFactory.CreateDefaultObject(elementType);
                Log.LogDebug("created default object of type " + elementType.ToString());
                XmlElement elementObjectXmlElement = RenderObject(arrayElement, elementObject, "example");
            }
            else if (ObjectFactory.IsBooleanType(o.GetType()))
            {
                XmlElement booleanElement = objectXmlDocument.CreateElement("boolean");
                booleanElement.Attributes.Append(propertyNameXmlAttribute);
                objectXmlElement.AppendChild(booleanElement);
            }
            else if (ObjectFactory.IsDateTimeType(o.GetType()))
            {
                XmlElement datetimeElement = objectXmlDocument.CreateElement("datetime");
                datetimeElement.Attributes.Append(propertyNameXmlAttribute);
                objectXmlElement.AppendChild(datetimeElement);
            }
            else if (ObjectFactory.IsEnumType(o.GetType()))
            {
                XmlElement enumElement = objectXmlDocument.CreateElement("enum");
                enumElement.Attributes.Append(propertyNameXmlAttribute);
                objectXmlElement.AppendChild(enumElement);
            }
            else if (ObjectFactory.IsIntegerType(o.GetType()))
            {
                XmlElement intElement = objectXmlDocument.CreateElement("int");
                intElement.Attributes.Append(propertyNameXmlAttribute);
                objectXmlElement.AppendChild(intElement);
            }
            else if (ObjectFactory.IsStringType(o.GetType()))
            {
                XmlElement stringElement = objectXmlDocument.CreateElement("string");
                stringElement.Attributes.Append(propertyNameXmlAttribute);
                objectXmlElement.AppendChild(stringElement);
            }
            else if (ObjectFactory.IsXmlAttributeType(o.GetType()))
            {
                XmlElement xmlAttributeElement = objectXmlDocument.CreateElement("xmlattribute");
                xmlAttributeElement.Attributes.Append(propertyNameXmlAttribute);
                objectXmlElement.AppendChild(xmlAttributeElement);
            }
            else
            {
                Log.LogDebug("object type is not standard type");
                string typeName = o.GetType().Name;
                Log.LogDebug("typeName is " + typeName);
            }

            return objectXmlElement;
        }

        /// <summary>
        /// This method writes the XmlNode (or XmlDocument) to the console.
        /// </summary>
        /// <param name="xmlNode">The xmlNode parameter</param>
        public static void WriteXmlNodeToLog(XmlNode xmlNode)
        {
            Log.LogDebug("starting WriteXmlNodeToLog()");
            StringWriter stringWriter = new StringWriter();
            XmlTextWriter xmlTextWriter = new XmlTextWriter(stringWriter);
            xmlTextWriter.Formatting = Formatting.Indented;
            xmlNode.WriteTo(xmlTextWriter);
            xmlTextWriter.Flush();
            Log.LogDebug(stringWriter.ToString() + Environment.NewLine);
        }
    }
}
