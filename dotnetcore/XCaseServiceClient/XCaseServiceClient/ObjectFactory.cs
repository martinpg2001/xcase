namespace XCaseServiceClient
{
    using System;
    using System.Collections;
    using System.Collections.Generic;
    using System.Linq;
    using System.Reflection;
    using System.Runtime.Serialization;
    using System.Text;
    using System.Xml;
    using log4net;

    public class ObjectFactory
    {
        #region Logger Setup

        /// <summary>
        /// A log4net log instance.
        /// </summary>
        private static readonly ILog Log = log4net.LogManager.GetLogger(MethodBase.GetCurrentMethod().DeclaringType);

        #endregion

        private static Random random = new Random();

        public static object CreateDefaultObject(Type type)
        {
            Log.Debug("starting CreateDefaultObject() for " + type);
            ConstructorInfo[] constructorInfoArray = type.GetConstructors();
            if (constructorInfoArray.Length == 0)
            {
                //Log.Debug("constructor length is zero");
                ConstructorInfo[] rootConstructorInfoArray = GetRootElementType(type).GetConstructors();
                if (rootConstructorInfoArray.Length == 0)
                {
                    //Log.Debug("root constructor length is zero");
                }

                if (type.HasElementType)
                {
                    ConstructorInfo[] elementConstructorInfoArray = type.GetElementType().GetConstructors();
                    if (elementConstructorInfoArray.Length == 0)
                    {
                        //Log.Debug("element constructor length is zero");
                    }
                }
            }

            object defaultObject = null;
            try
            {
                if (!IsNullableType(type))
                {
                    Log.Debug("type is not nullable");
                    if (type.IsArray)
                    {
                        Log.Debug("type is array");
                        Type elementType = type.GetElementType();
                        Log.DebugFormat("element type is {0}", elementType);
                        defaultObject = Array.CreateInstance(elementType, 0);
                    }
                    else if (IsTupleType(type))
                    {
                        Type[] typeArray = type.GetGenericArguments();
                        object[] values = CreateArrayOfObjectsFromFieldInfoArray(typeArray);
                        ConstructorInfo constructorInfo = type.GetConstructor(typeArray);
                        if (constructorInfo != null)
                        {
                            defaultObject = constructorInfo.Invoke(values);
                            Log.Debug("created defaultObject for Tuple");
                        }
                    }
                    else if (type == typeof(Boolean))
                    {
                        defaultObject = true;
                    }
                    else if (GetRootElementType(type) == typeof(Boolean))
                    {
                        defaultObject = true;
                    }
                    else if (type == typeof(DateTime))
                    {
                        //Log.Debug("type is DateTime");
                        defaultObject = new XCaseDateTime();
                    }
                    else if (GetRootElementType(type) == typeof(DateTime))
                    {
                        //Log.Debug("root element type is DateTime");
                        defaultObject = new XCaseDateTime();
                    }
                    else if (type == typeof(Decimal))
                    {
                        Log.Debug("type is Decimal");
                        defaultObject = new Decimal(0);
                    }
                    else if (GetRootElementType(type) == typeof(Decimal))
                    {
                        Log.Debug("root element type is Decimal");
                        defaultObject = new Decimal(0);
                    }
                    else if (type == typeof(Double))
                    {
                        //Log.Debug("type is Double");
                        defaultObject = 0.0;
                    }
                    else if (GetRootElementType(type) == typeof(Double))
                    {
                        //Log.Debug("root element type is Double");
                        defaultObject = 0.0;
                    }
                    else if (type == typeof(int))
                    {
                        //Log.Debug("type is int");
                        defaultObject = 0;
                    }
                    else if (GetRootElementType(type) == typeof(int))
                    {
                        //Log.Debug("root type is int");
                        defaultObject = 0;
                    }
                    else if (type == typeof(Int64))
                    {
                        //Log.Debug("type is Int64");
                        defaultObject = 0;
                    }
                    else if (GetRootElementType(type) == typeof(Int64))
                    {
                        //Log.Debug("root type is Int64");
                        defaultObject = 0;
                    }
                    else if (type == typeof(long))
                    {
                        //Log.Debug("type is long");
                        defaultObject = 0;
                    }
                    else if (GetRootElementType(type) == typeof(long))
                    {
                        //Log.Debug("root type is long");
                        defaultObject = 0;
                    }
                    else if (type == typeof(string))
                    {
                        //Log.Debug("type is string");
                        defaultObject = string.Empty;
                    }
                    else if (type == typeof(TimeSpan))
                    {
                        //Log.Debug("type is TimeSpan");
                        defaultObject = TimeSpan.Zero;
                    }
                    else if (type == typeof(XmlAttribute))
                    {
                        Log.Debug("type is XmlAttribute");
                        XmlDocument xmlDocument = new XmlDocument();
                        XmlAttribute xmlAttribute = xmlDocument.CreateAttribute("attribute");
                        Log.Debug("created attribute");
                        defaultObject = xmlAttribute;
                        Log.Debug("default object set to xmlAttribute");
                    }
                    else if (GetRootElementType(type) == typeof(XmlAttribute))
                    {
                        Log.Debug("root type is XmlAttribute");
                        XmlDocument xmlDocument = new XmlDocument();
                        XmlAttribute xmlAttribute = xmlDocument.CreateAttribute("attribute");
                        defaultObject = xmlAttribute;
                    }
                    else if (type == typeof(XmlNode))
                    {
                        //Log.Debug("type is XmlNode");
                        XmlDocument xmlDocument = new XmlDocument();
                        XmlElement xmlRootElement = xmlDocument.CreateElement("", "ROOT", "");
                        xmlDocument.AppendChild(xmlRootElement);
                        defaultObject = xmlDocument;
                    }
                    else if (GetRootElementType(type) == typeof(XmlNode))
                    {
                        //Log.Debug("root type is XmlNode");
                        XmlDocument xmlDocument = new XmlDocument();
                        XmlElement xmlRootElement = xmlDocument.CreateElement("", "ROOT", "");
                        xmlDocument.AppendChild(xmlRootElement);
                        defaultObject = xmlDocument;
                    }
                    else
                    {
                        Log.Debug("non-nullable type is not accounted for");
                        try
                        {
                            defaultObject = Activator.CreateInstance(type, true);
                            Log.Debug("created default object of type " + type);
                        }
                        catch (Exception e)
                        {
                            Log.Debug("exception thrown using CreateInstance(type, true) " + e.Message);
                            Log.Debug("root element type is " + GetRootElementType(type));
                            if (type != GetRootElementType(type))
                            {
                                //Log.Debug("type is not equal to root element type");
                                try
                                {
                                    defaultObject = CreateDefaultObject(GetRootElementType(type));
                                }
                                catch (Exception f)
                                {
                                    Log.Debug("exception thrown using CreateDefaultObject(GetRootElementType(type) " + f.Message);
                                }
                            }
                            else
                            {
                                //Log.Debug("type is equal to root element type");
                            }
                        }
                    }
                }
                else
                {
                    Log.Debug("type is nullable");
                    Type underlyingType = Nullable.GetUnderlyingType(type);
                    Log.DebugFormat("underlying type is {0}", underlyingType.FullName);
                    if (IsEnumType(underlyingType))
                    {
                        Log.DebugFormat("underlying type is enum");
                        defaultObject = CreateDefaultObject(underlyingType);
                    }
                }
            }
            catch (Exception e)
            {
                Log.Debug("exception creating default object: " + e.Message);
                defaultObject = null;
            }

            Log.Debug("finishing CreateDefaultObject() for " + type);
            return defaultObject;
        }

        private static object[] CreateArrayOfObjectsFromFieldInfoArray(FieldInfo[] fieldInfoArray)
        {
            int arrayCount = fieldInfoArray.Count<FieldInfo>();
            object[] objectArray = new object[arrayCount];
            for (int i = 0; i < arrayCount; i++)
            {
                objectArray[i] = CreateDefaultObject(fieldInfoArray[i].GetType());
            }

            return objectArray;
        }

        private static object[] CreateArrayOfObjectsFromFieldInfoArray(Type[] typeArray)
        {
            int arrayCount = typeArray.Count<Type>();
            object[] objectArray = new object[arrayCount];
            for (int i = 0; i < arrayCount; i++)
            {
                objectArray[i] = CreateDefaultObject(typeArray[i]);
            }

            return objectArray;
        }

        public static object CreateObjectFromTypeAndValue(Type type, bool value)
        {
            //Log.Debug("starting CreateObjectFromTypeAndValue for bool");
            if (IsNullableType(type))
            {
                bool? valueObject = value;
                return valueObject;
            }

            return value;
        }

        public static object CreateObjectFromTypeAndValue(Type type, Byte value)
        {
            Log.Debug("starting CreateObjectFromTypeAndValue for Byte");
            if (IsNullableType(type))
            {
                Byte? valueObject = value;
                return valueObject;
            }

            return value;
        }

        public static object CreateObjectFromTypeAndValue(Type type, Char value)
        {
            Log.Debug("starting CreateObjectFromTypeAndValue for Char");
            if (IsNullableType(type))
            {
                Char? valueObject = value;
                return valueObject;
            }

            return value;
        }

        public static object CreateObjectFromTypeAndValue(Type type, Double value)
        {
            //Log.Debug("starting CreateObjectFromTypeAndValue for Double");
            if (IsNullableType(type))
            {
                Double? valueObject = value;
                return valueObject;
            }

            return value;
        }

        public static object CreateObjectFromTypeAndValue(Type type, Decimal value)
        {
            //Log.Debug("starting CreateObjectFromTypeAndValue for Decimal");
            if (IsNullableType(type))
            {
                Decimal? valueObject = value;
                return valueObject;
            }

            return value;
        }

        public static object CreateObjectFromTypeAndValue(Type type, DateTime value)
        {
            //Log.Debug("starting CreateObjectFromTypeAndValue for DateTime");
            if (IsNullableType(type))
            {
                //Log.Debug("type is nullable");
                DateTime? valueObject = value;
                return valueObject;
            }

            return value;
        }

        public static object CreateObjectFromTypeAndValue(Type type, Enum value)
        {
            //Log.Debug("starting CreateObjectFromTypeAndValue() for Enum");
            return value;
        }

        public static object CreateObjectFromTypeAndValue(Type type, Int32 value)
        {
            //Log.Debug("starting CreateObjectFromTypeAndValue for Int32");
            if (IsNullableType(type))
            {
                //Log.Debug("type is nullable");
                Int32? valueObject = value;
                return valueObject;
            }
            return value;
        }

        public static object CreateObjectFromTypeAndValue(Type type, Int64 value)
        {
            //Log.Debug("starting CreateObjectFromTypeAndValue for Int64");
            if (IsNullableType(type))
            {
                //Log.Debug("type is nullable");
                Int64? valueObject = value;
                return valueObject;
            }
            return value;
        }

        public static object CreateObjectFromTypeAndValue(Type type, Single value)
        {
            Log.Debug("starting CreateObjectFromTypeAndValue for Single");
            if (IsNullableType(type))
            {
                Single? valueObject = value;
                return valueObject;
            }

            return value;
        }

        public static object CreateObjectFromTypeAndValue(Type type, string value)
        {
            //Log.Debug("starting CreateObjectFromTypeAndValue() from {0} for string", type);
            object valueObject = null;
            if (type == typeof(Array))
            {
                valueObject = value.Split();
            }
            else if (IsNullableType(type))
            {
                //Log.Debug("type is nullable");
                Type underlyingType = Nullable.GetUnderlyingType(type);
                //Log.Debug("underlying type is " + underlyingType);
                object underlyingValueObject = CreateObjectFromTypeAndValue(underlyingType, value);
                //Log.Debug("Underlying type is " + underlyingType);
                try
                {
                    valueObject = Activator.CreateInstance(type, underlyingValueObject);
                    //Log.Debug("created instance of type " + valueObject.GetType());
                }
                catch (Exception e)
                {
                    Log.Debug("exception thrown using createObjectFromTypeAndValue(Type type, string value): " + e.Message);
                }

                valueObject = underlyingValueObject;
            }
            else if (type == typeof(int))
            {
                //Log.Debug("type is int");
                try
                {
                    valueObject = int.Parse(value);
                }
                catch (Exception)
                {
                    Log.Debug("invalid int value");
                }
            }
            else if (type.BaseType == typeof(Enum))
            {
                //Log.Debug("type is Enum");
                try
                {
                    valueObject = Enum.Parse(type, value);
                }
                catch (Exception)
                {
                    Log.Debug("invalid Enum value");
                    valueObject = Activator.CreateInstance(type);
                }
            }
            else
            {
                //Log.Debug("type is string");
                valueObject = value;
            }

            return valueObject;
        }

        public static object CreateObjectFromTypeAndValue(Type type, TimeSpan value)
        {
            Log.Debug("starting CreateObjectFromTypeAndValue for TimeSpan");
            if (IsNullableType(type))
            {
                TimeSpan? valueObject = value;
                return valueObject;
            }

            return value;
        }

        public static object CreateObjectFromTypeAndValue(Type type, XmlDocument value)
        {
            //Log.Debug("starting createObjectFromTypeAndValue() for XmlDocument");
            object valueObject = new XmlDocument();
            string valueContent = value.InnerXml;
            ((XmlDocument)valueObject).LoadXml(valueContent);
            return valueObject;
        }

        public static object CreateInt64ObjectFromTypeAndValue(Type type, Int64 value)
        {
            Log.DebugFormat("starting CreateInt64ObjectFromTypeAndValue for long {0}", value);
            if (IsNullableType(type))
            {
                //Log.Debug("type is nullable");
                Int64? valueObject = value;
                return valueObject;
            }

            return value;
        }

        public static Type GetRootElementType(Type type)
        {
            while (type.HasElementType)
            {
                type = type.GetElementType();
            }

            return type;
        }

        public static bool IsArrayList(Type type)
        {
            //Log.Debug("starting IsArrayList()");
            if (type == typeof(ArrayList))
            {
                //Log.Debug("type is ArrayList");
                return true;
            }

            return false;
        }

        public static bool IsArrayType(Type type)
        {
            //Log.Debug("starting IsArrayType()");
            if (type.IsArray)
            {
                Log.Debug("element type is " + type.GetElementType().ToString());
            }

            return type.IsArray;
        }

        public static bool IsICollectionType(Type type)
        {
            //Log.Debug("starting IsICollectionType()");
            if (IsStringType(type))
            {
                /* Do not want to handle string as ICollection even though it is */
                return false;
            }

            return typeof(ICollection).IsAssignableFrom(type);
        }

        public static bool IsIEnumerableType(Type type)
        {
            //Log.Debug("starting IsIEnumerableType()");
            if (IsStringType(type))
            {
                /* Do not want to handle string as IEnumerable even though it is */
                return false;
            }

            return typeof(IEnumerable).IsAssignableFrom(type);
        }

        public static bool IsBooleanType(Type type)
        {
            //Log.Debug("starting IsBooleanType()");
            if (type == null)
            {
                return false;
            }

            if (type == typeof(bool))
            {
                return true;
            }
            else if (type == typeof(Boolean))
            {
                return true;
            }
            else if (GetRootElementType(type) == typeof(Boolean))
            {
                return true;
            }
            else if (type != null && IsNullableType(type))
            {
                Log.DebugFormat("type is {0}", type.FullName);
                Type underlyingType = Nullable.GetUnderlyingType(type);
                if (underlyingType != null)
                {
                    Log.DebugFormat("underlying type is {0}", underlyingType);
                }
                else
                {
                    Log.DebugFormat("underlying type is null");
                }

                return IsBooleanType(underlyingType);
            }

            return false;
        }

        public static bool IsByteType(Type type)
        {
            //Log.Debug("starting IsByteType()");
            if (type == null)
            {
                return false;
            }

            if (type == typeof(Byte))
            {
                return true;
            }
            else if (GetRootElementType(type) == typeof(Byte))
            {
                return true;
            }
            else if (IsNullableType(type))
            {
                Log.Debug("type is nullable");
                Type nullableType = Nullable.GetUnderlyingType(type);
                Log.DebugFormat("nullable type is {0}", nullableType);
                return IsByteType(nullableType);
            }

            return false;
        }

        public static bool IsCharType(Type type)
        {
            //Log.Debug("starting IsCharType()");
            if (type == null)
            {
                return false;
            }

            if (type == typeof(Char))
            {
                return true;
            }
            else if (GetRootElementType(type) == typeof(Char))
            {
                return true;
            }
            else if (IsNullableType(type))
            {
                Log.Debug("type is nullable");
                Type nullableType = Nullable.GetUnderlyingType(type);
                Log.DebugFormat("nullable type is {0}", nullableType);
                return IsCharType(nullableType);
            }

            return false;
        }

        public static bool IsDateTimeType(Type type)
        {
            //Log.Debug("starting IsDateTimeType()");
            if (type == null)
            {
                return false;
            }

            if (type == typeof(XCaseDateTime))
            {
                return true;
            }
            else if (type == typeof(DateTime))
            {
                return true;
            }
            else if (IsNullableType(type))
            {
                Log.Debug("type is nullable");
                Type nullableType = Nullable.GetUnderlyingType(type);
                Log.DebugFormat("nullable type is {0}", nullableType);
                return IsDateTimeType(nullableType);
            }

            return false;
        }

        public static bool IsDecimalType(Type type)
        {
            //Log.Debug("starting IsDecimalType()");
            if (type == null)
            {
                return false;
            }

            if (type == typeof(Decimal))
            {
                return true;
            }
            else if (GetRootElementType(type) == typeof(Decimal))
            {
                return true;
            }
            else if (IsNullableType(type))
            {
                Log.Debug("type is nullable");
                Type nullableType = Nullable.GetUnderlyingType(type);
                Log.DebugFormat("nullable type is {0}", nullableType);
                return IsDecimalType(nullableType);
            }

            return false;
        }

        public static bool IsDictionaryType(Type type)
        {
            //Log.Debug("starting IsDictionaryType()");
            if (type == null)
            {
                return false;
            }

            return (typeof(IDictionary).IsAssignableFrom(type));
        }

        public static bool IsDoubleType(Type type)
        {
            //Log.Debug("starting IsDoubleType()");
            if (type == null)
            {
                return false;
            }

            if (type == typeof(Double))
            {
                return true;
            }
            else if (GetRootElementType(type) == typeof(Double))
            {
                return true;
            }
            else if (IsNullableType(type))
            {
                Log.Debug("type is nullable");
                Type nullableType = Nullable.GetUnderlyingType(type);
                Log.DebugFormat("nullable type is {0}", nullableType);
                return IsDoubleType(nullableType);
            }

            return false;
        }

        public static bool IsEnumType(Type type)
        {
            //Log.Debug("starting IsEnumType()");
            if (type == null)
            {
                return false;
            }

            if (type.IsEnum)
            {
                return true;
            }
            else if (IsNullableType(type))
            {
                //Log.Debug("type is nullable");
                Type nullableType = Nullable.GetUnderlyingType(type);
                //Log.Debug("nullable type is {0}", nullableType);
                return IsEnumType(nullableType);
            }

            return false;
        }

        public static bool IsExtensionDataObjectType(Type type)
        {
            //Log.Debug("starting IsExtensionDataObjectType()");
            if (type == null)
            {
                return false;
            }

            if (type == typeof(ExtensionDataObject))
            {
                return true;
            }

            return false;
        }

        public static bool IsIntegerType(Type type)
        {
            //Log.Debug("starting IsIntegerType()");
            if (type == null)
            {
                return false;
            }

            if (type == typeof(int))
            {
                return true;
            }
            else if (type == typeof(Int32))
            {
                return true;
            }
            else if (GetRootElementType(type) == typeof(Int32))
            {
                return true;
            }
            else if (IsNullableType(type))
            {
                Log.Debug("type is nullable");
                Type nullableType = Nullable.GetUnderlyingType(type);
                Log.DebugFormat("nullable type is {0}", nullableType);
                return IsIntegerType(nullableType);
            }

            return false;
        }

        public static bool IsInt64Type(Type type)
        {
            //Log.Debug("starting IsInt64Type()");
            if (type == null)
            {
                return false;
            }

            if (type == typeof(Int64))
            {
                return true;
            }
            else if (GetRootElementType(type) == typeof(Int64))
            {
                return true;
            }
            else if (IsNullableType(type))
            {
                Log.Debug("type is nullable");
                Type nullableType = Nullable.GetUnderlyingType(type);
                Log.DebugFormat("nullable type is {0}", nullableType);
                return IsInt64Type(nullableType);
            }

            return false;
        }

        public static bool IsListType(Type type)
        {
            Log.Debug("starting IsListType()");
            if (type.IsGenericType && (type.GetGenericTypeDefinition().IsAssignableFrom(typeof(List<>))))
            {
                Log.Debug("element type is " + type.GetGenericArguments()[0].ToString());
                return true;
            }

            return false;
        }

        public static bool IsLongType(Type type)
        {
            //Log.Debug("starting IsLongType()");
            if (type == null)
            {
                return false;
            }

            if (type == typeof(long))
            {
                return true;
            }
            else if (GetRootElementType(type) == typeof(long))
            {
                return true;
            }
            else if (IsNullableType(type))
            {
                Log.Debug("type is nullable");
                Type nullableType = Nullable.GetUnderlyingType(type);
                Log.DebugFormat("nullable type is {0}", nullableType);
                return IsLongType(nullableType);
            }

            return false;
        }

        public static bool IsSingleType(Type type)
        {
            //Log.Debug("starting IsSingleType()");
            if (type == null)
            {
                return false;
            }

            if (type == typeof(Single))
            {
                return true;
            }
            else if (GetRootElementType(type) == typeof(Single))
            {
                return true;
            }
            else if (IsNullableType(type))
            {
                Log.Debug("type is nullable");
                Type nullableType = Nullable.GetUnderlyingType(type);
                Log.DebugFormat("nullable type is {0}", nullableType);
                return IsSingleType(nullableType);
            }

            return false;
        }

        public static bool IsStringType(Type type)
        {
            //Log.Debug("starting isStringType()");
            if (type == null)
            {
                return false;
            }

            if (IsNullableType(type))
            {
                return false;
            }
            else if (type == typeof(string))
            {
                return true;
            }
            else if (IsNullableType(type))
            {
                //Log.Debug("type is nullable");
                Type nullableType = Nullable.GetUnderlyingType(type);
                //Log.Debug("nullable type is {0}", nullableType);
                return IsStringType(nullableType);
            }

            return false;
        }

        public static bool IsTimeSpanType(Type type)
        {
            //Log.Debug("starting IsTimeSpanType()");
            if (type == null)
            {
                return false;
            }

            if (IsNullableType(type))
            {
                return false;
            }
            else if (type == typeof(TimeSpan))
            {
                return true;
            }
            else if (IsNullableType(type))
            {
                //Log.Debug("type is nullable");
                Type nullableType = Nullable.GetUnderlyingType(type);
                //Log.Debug("nullable type is {0}", nullableType);
                return IsTimeSpanType(nullableType);
            }

            return false;
        }

        public static bool IsTupleType(Type type)
        {
            //Log.Debug("starting IsTupleType()");
            if (type == null || !type.IsGenericType)
            {
                return false;
            }

            Type genericType = type.GetGenericTypeDefinition();
            if (genericType.Equals(typeof(Tuple<>)) || 
                genericType.Equals(typeof(Tuple<,>)) || 
                genericType.Equals(typeof(Tuple<,,>)) || 
                genericType.Equals(typeof(Tuple<,,,>)) || 
                genericType.Equals(typeof(Tuple<,,,,>)) ||
                genericType.Equals(typeof(Tuple<,,,,,>)) ||
                genericType.Equals(typeof(Tuple<,,,,,,>)) ||
                genericType.Equals(typeof(Tuple<,,,,,,,>)))
            {
                return true;
            }

            return false;
        }

        public static bool IsNullableType(Type type)
        {
            //Log.Debug("starting isNullableType()");
            if (type == null)
            {
                return false;
            }

            if (type != null)
            {
                string typeName = type.Name;
                //Log.Debug("type name is " + typeName);
                return typeName.StartsWith("Nullable");
            }

            return false;
        }

        public static bool IsXmlDocumentType(Type type)
        {
            //Log.Debug("starting IsXmlDocumentType()");
            if (type == null)
            {
                return false;
            }

            if (type == typeof(XmlDocument))
            {
                //Log.Debug("type is XmlDocument");
                return true;
            }

            return false;
        }

        public static bool IsXmlElementType(Type type)
        {
            //Log.Debug("starting IsXmlElementType()");
            if (type == null)
            {
                return false;
            }

            if (type == typeof(XmlElement))
            {
                //Log.Debug("type is XmlElement");
                return true;
            }
            else if (GetRootElementType(type) == typeof(XmlElement))
            {
                //Log.Debug("type is XmlElement");
                return true;
            }

            return false;
        }

        public static bool IsXmlNodeType(Type type)
        {
            //Log.Debug("starting IsXmlNodeType()");
            if (type == null)
            {
                return false;
            }

            if (type == typeof(XmlNode))
            {
                //Log.Debug("type is XmlNode");
                return true;
            }
            else if (GetRootElementType(type) == typeof(XmlNode))
            {
                //Log.Debug("type is XmlNode");
                return true;
            }

            return false;
        }

        public static bool IsXmlAttributeType(Type type)
        {
            Log.Debug("starting IsXmlAttributeType()");
            if (type == null)
            {
                return false;
            }

            if (type == typeof(XmlAttribute))
            {
                Log.Debug("type is XmlAttribute");
                return true;
            }
            else if (GetRootElementType(type) == typeof(XmlAttribute))
            {
                Log.Debug("root type is XmlAttribute");
                return true;
            }

            return false;
        }

        public static bool IsInt16Type(Type type)
        {
            //Log.Debug("starting IsInt16Type()");
            if (type == null)
            {
                return false;
            }

            if (type == typeof(Int16))
            {
                return true;
            }
            else if (GetRootElementType(type) == typeof(Int16))
            {
                return true;
            }
            else if (IsNullableType(type))
            {
                Log.Debug("type is nullable");
                Type nullableType = Nullable.GetUnderlyingType(type);
                Log.DebugFormat("nullable type is {0}", nullableType);
                return IsInt16Type(nullableType);
            }

            return false;
        }

        public static object CreateInt16ObjectFromTypeAndValue(Type type, short value)
        {
            //Log.DebugFormat("starting CreateInt16ObjectFromTypeAndValue for short {0}", value);
            if (IsNullableType(type))
            {
                //Log.Debug("type is nullable");
                Int16? valueObject = value;
                return valueObject;
            }

            return value;
        }

        public static object CreateInt64ObjectFromTypeAndValue(Type type, string value)
        {
            Log.DebugFormat("starting CreateInt64ObjectFromTypeAndValue for string {0}", value);
            Int64 valueInt64 = 0;
            Int64.TryParse(value, out valueInt64);
            if (IsNullableType(type))
            {
                Log.Debug("type is nullable");
                Int64? valueObject = (Int64?)valueInt64;
                return valueObject;
            }

            return valueInt64;
        }

        public static TimeSpan CreateTimeSpanObjectFromTypeAndValue(Type type, TimeSpan value)
        {
            Log.DebugFormat("starting CreateTimeSpanObjectFromTypeAndValue for TimeSpan {0}", value);
            TimeSpan valueTimeSpan = value;
            if (IsNullableType(type))
            {
                Log.Debug("type is nullable");
                TimeSpan? valueObject = (TimeSpan?)valueTimeSpan;
                return valueTimeSpan;
            }

            return valueTimeSpan;
        }

        public static bool IsCancellationTokenType(Type type)
        {
            Log.Debug("starting IsCancellationTokenType()");
            if (type == typeof(System.Threading.CancellationToken))
            {
                Log.Debug("type is CancellationToken");
                return true;
            }

            return false;
        }

        public static object CreateUniqueObject(Type type)
        {
            Log.Debug("starting CreateUniqueObject()");
            if (type.Equals(typeof(string)))
            {
                return Guid.NewGuid().ToString();
            }
            else if (type.Equals(typeof(int)))
            {
                return random.Next();
            }
            else
            {
                return CreateDefaultObject(type);
            }
        }
    }
}
