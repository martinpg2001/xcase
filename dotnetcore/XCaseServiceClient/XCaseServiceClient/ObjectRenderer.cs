namespace XCaseServiceClient
{
    using System;
    using System.Collections;
    using System.Collections.Generic;
    using System.Drawing;
    using System.IO;
    using System.Linq;
    using System.Reflection;
    using System.Runtime.Serialization;
    using System.Text;
    using System.Windows.Forms;
    using System.Xml;
    using Microsoft.Extensions.Configuration;
    using Microsoft.Extensions.Logging;
    using Newtonsoft.Json;
    using Serilog;
    using Serilog.Core;
    using Serilog.Events;
    using Serilog.Formatting.Json;
    using Serilog.Configuration;
    using Serilog.Settings;

    public class ObjectRenderer
    {
        #region Logger Setup

        /// <summary>
        /// A log4net log instance.
        /// </summary>
        public static readonly Serilog.ILogger Log = new LoggerConfiguration().ReadFrom.Configuration(new ConfigurationBuilder()
            .SetBasePath(Directory.GetCurrentDirectory())
            .AddJsonFile("appsettings.json", optional: false, reloadOnChange: true)
            .Build()).CreateLogger();

        #endregion

        public static XCaseTableLayoutPanel RenderParameterObject(object parameterObject)
        {
            Log.Debug("starting RenderParameterObject()");
            if (parameterObject == null)
            {
                Log.Debug("parameter object is null");
                return new XCaseTableLayoutPanel();
            }

            Log.Debug("starting RenderParameterObject() for " + parameterObject.GetType());
            XCaseTableLayoutPanel propertyTableLayoutPanel = new XCaseTableLayoutPanel();
            propertyTableLayoutPanel.PropertyObject = parameterObject;
            propertyTableLayoutPanel.CellBorderStyle = TableLayoutPanelCellBorderStyle.Single;
            propertyTableLayoutPanel.AutoSize = true;
            propertyTableLayoutPanel.ColumnCount = 3;
            propertyTableLayoutPanel.RowStyles.Add(new RowStyle(SizeType.Percent, 100F));
            if (ObjectFactory.IsArrayType(parameterObject.GetType()))
            {
                /* Parameter object is array */
                Log.Debug("parameter object is array");
                RenderArray(propertyTableLayoutPanel, null, parameterObject, 0);
            }
            else if (ObjectFactory.IsDictionaryType(parameterObject.GetType()))
            {
                /* Property type is not array and is Dictionary */
                Log.Debug("parameter object is Dictionary");
                RenderDictionary(propertyTableLayoutPanel, null, parameterObject, 0);
            }
            else if (ObjectFactory.IsIEnumerableType(parameterObject.GetType()))
            {
                /* Parameter object is IEnumerable */
                Log.Debug("parameter object is IEnumerable");
                RenderIEnumerable(propertyTableLayoutPanel, null, parameterObject, 0);
            }
            else if (ObjectFactory.IsBooleanType(parameterObject.GetType()))
            {
                /* Parameter type is not array and is Boolean */
                //Log.Debug("parameter type is not array and is Boolean");
                ((bool)parameterObject).RenderBoolean(propertyTableLayoutPanel, null, 0);
            }
            else if (ObjectFactory.IsByteType(parameterObject.GetType()))
            {
                /* Parameter type is not array and is Byte */
                //Log.Debug("parameter type is not array and is Byte");
                ((Byte)parameterObject).RenderByte(propertyTableLayoutPanel, null, 0);
            }
            else if (ObjectFactory.IsCharType(parameterObject.GetType()))
            {
                /* Property type is not array and is Char */
                //Log.Debug("parameter type is not array and is Char");
                ((Char)parameterObject).RenderChar(propertyTableLayoutPanel, null, 0);
            }
            else if (ObjectFactory.IsDateTimeType(parameterObject.GetType()))
            {
                /* Property object is not array and is (XCase)datetime */
                //Log.Debug("parameter type is not array and is (XCase)datetime");
                ((DateTime)parameterObject).RenderDateTime(propertyTableLayoutPanel, null, 0);
            }
            else if (ObjectFactory.IsDecimalType(parameterObject.GetType()))
            {
                /* Property type is not array and is Decimal */
                //Log.Debug("parameter type is not array and is Decimal");
                ((Decimal)parameterObject).RenderDecimal(propertyTableLayoutPanel, null, 0);
            }
            else if (ObjectFactory.IsDoubleType(parameterObject.GetType()))
            {
                /* Property type is not array and is Double */
                //Log.Debug("parameter type is not array and is Double");
                ((Double)parameterObject).RenderDouble(propertyTableLayoutPanel, null, 0);
            }
            else if (ObjectFactory.IsEnumType(parameterObject.GetType()))
            {
                /* Property object is not array and is enum */
                //Log.Debug("parameter type is not array and is Enum");
                ((Enum)parameterObject).RenderEnum(propertyTableLayoutPanel, null, 0);
            }
            else if (ObjectFactory.IsExtensionDataObjectType(parameterObject.GetType()))
            {
                /* Property object is not array and is ExtensionDataObject */
                //Log.Debug("parameter type is not array and is ExtensionDataObject");
                RenderExtensionDataObject(propertyTableLayoutPanel, null, parameterObject, 0);
            }
            else if (ObjectFactory.IsIntegerType(parameterObject.GetType()))
            {
                /* Property type is not array and is Integer */
                //Log.Debug("parameter type is not array and is Integer");
                ((int)parameterObject).RenderInteger(propertyTableLayoutPanel, null, 0);
            }
            else if (ObjectFactory.IsInt16Type(parameterObject.GetType()))
            {
                /* Property type is not array and is Int16 */
                //Log.Debug("parameter type is not array and is Int16");
                ((Int16)parameterObject).RenderInt16(propertyTableLayoutPanel, null, 0);
            }
            else if (ObjectFactory.IsInt64Type(parameterObject.GetType()))
            {
                /* Property type is not array and is Int64 */
                //Log.Debug("parameter type is not array and is Int64");
                ((Int64)parameterObject).RenderInt64(propertyTableLayoutPanel, null, 0);
            }
            else if (ObjectFactory.IsLongType(parameterObject.GetType()))
            {
                /* Property type is not array and is Long */
                //Log.Debug("parameter type is not array and is Long");
                ((long)parameterObject).RenderLong(propertyTableLayoutPanel, null, 0);
            }
            else if (ObjectFactory.IsSingleType(parameterObject.GetType()))
            {
                /* Property type is not array and is Single */
                //Log.Debug("parameter type is not array and is Single");
                ((Single)parameterObject).RenderSingle(propertyTableLayoutPanel, null, 0);
            }
            else if (ObjectFactory.IsStringType(parameterObject.GetType()))
            {
                /* Property type is not array and is string */
                //Log.Debug("parameter type is not array and is String");
                ((string)parameterObject).RenderString(propertyTableLayoutPanel, null, 0);
            }
            else if (ObjectFactory.IsTimeSpanType(parameterObject.GetType()))
            {
                /* Property type is not array and is string */
                Log.Debug("parameter type is not array and is TimeSpan");
                ((TimeSpan)parameterObject).RenderTimeSpan(propertyTableLayoutPanel, null, 0);
            }
            else if (ObjectFactory.IsXmlDocumentType(parameterObject.GetType()))
            {
                /* Property type is not array and is XmlDocument */
                //Log.Debug("parameter type is not array and is XmlDocument");
                RenderXmlDocument(propertyTableLayoutPanel, null, parameterObject, 0);
            }
            else
            {
                /* Property type is not array and is complex type */
                Log.Debug("parameter type is not array and is complex type: {0}", parameterObject.GetType());
                /* Iterate through each property of the parameter */
                PropertyInfo[] propertyInfoArray = parameterObject.GetType().GetProperties();
                Log.Debug("number of properties is {0}", propertyInfoArray.Length);
                propertyTableLayoutPanel.RowStyles.Add(new RowStyle(SizeType.Percent, 100F));
                propertyTableLayoutPanel.RowCount = propertyInfoArray.Length + 2;
                Label propertyNameLabel = new Label();
                propertyNameLabel.Text = XCaseServiceClient.Properties.Resources.Name;
                propertyNameLabel.BackColor = XCaseServiceClientForm.labelBackgroundColor;
                propertyTableLayoutPanel.Controls.Add(propertyNameLabel, 0, 0);
                Label propertyValueLabel = new Label();
                propertyValueLabel.Text = XCaseServiceClient.Properties.Resources.Value;
                propertyValueLabel.BackColor = XCaseServiceClientForm.labelBackgroundColor;
                propertyTableLayoutPanel.Controls.Add(propertyValueLabel, 1, 0);
                for (int j = 0; j < propertyInfoArray.Length; j++)
                {
                    PropertyInfo propertyInfo = propertyInfoArray[j];
                    Log.Debug("property name is {0}", propertyInfo.Name);
                    Label propertyLabel = new Label();
                    propertyLabel.AutoSize = true;
                    propertyLabel.Text = string.Format("{0} ({1})", propertyInfo.Name, propertyInfo.PropertyType.Name);
                    propertyTableLayoutPanel.Controls.Add(propertyLabel, 0, j + 1);
                    Type propertyType = propertyInfo.PropertyType;
                    Log.Debug("property type is {0}", propertyType);
                    object propertyTypeObject = null;
                    if (propertyInfo.GetValue(parameterObject, null) == null)
                    {
                        //Log.Debug("property value is null");
                        propertyTypeObject = ObjectFactory.CreateDefaultObject(propertyType);
                        Log.Debug("created default property type object of type {0}", propertyType.Name);
                        propertyInfo.SetValue(parameterObject, propertyTypeObject, null);
                        //Log.Debug("set {0} to propertyTypeObject", propertyInfoArray[j].Name);
                    }
                    else
                    {
                        //Log.Debug("property value is not null");
                        propertyTypeObject = propertyInfo.GetValue(parameterObject, null);
                        //Log.Debug("propertyTypeObject is " + propertyTypeObject);
                    }

                    if (ObjectFactory.IsArrayType(propertyType))
                    {
                        //Log.Debug("property type is complex type and is array");
                        RenderArrayProperty(propertyTableLayoutPanel, parameterObject, propertyInfoArray, propertyTypeObject, j);                       
                    }
                    else if (ObjectFactory.IsBooleanType(propertyType))
                    {
                        //Log.Debug("property type is complex type and is boolean");
                        if (propertyTypeObject != null)
                        {
                            ((bool)propertyTypeObject).RenderBooleanProperty(propertyTableLayoutPanel, parameterObject, propertyInfoArray, j);
                        }
                        else
                        {
                            false.RenderBooleanProperty(propertyTableLayoutPanel, parameterObject, propertyInfoArray, j);
                        }
                    }
                    else if (ObjectFactory.IsByteType(propertyType))
                    {
                        //Log.Debug("property type is complex type and is Byte");
                        if (propertyTypeObject != null)
                        {
                            ((Byte)propertyTypeObject).RenderByteProperty(propertyTableLayoutPanel, parameterObject, propertyInfoArray, j);
                        }
                        else
                        {
                            Byte.MinValue.RenderByteProperty(propertyTableLayoutPanel, parameterObject, propertyInfoArray, j);
                        }
                    }
                    else if (ObjectFactory.IsCharType(propertyType))
                    {
                        /* Property type is complex type and is Char */
                        //Log.Debug("property type is complex type and is Char");
                        if (propertyTypeObject != null)
                        {
                            ((Char)propertyTypeObject).RenderCharProperty(propertyTableLayoutPanel, parameterObject, propertyInfoArray, j);
                        }
                        else
                        {
                            Char.MinValue.RenderCharProperty(propertyTableLayoutPanel, parameterObject, propertyInfoArray, j);
                        }
                    }
                    else if (ObjectFactory.IsDateTimeType(propertyType))
                    {
                        /* Property type is complex type and is DateTime */
                        //Log.Debug("property type is complex type and is DateTime");
                        if (propertyTypeObject != null)
                        {
                            ((DateTime)propertyTypeObject).RenderDateTimeProperty(propertyTableLayoutPanel, parameterObject, propertyInfoArray, j);
                        }
                        else
                        {
                            DateTime.MinValue.RenderDateTimeProperty(propertyTableLayoutPanel, parameterObject, propertyInfoArray, j);
                        }
                    }
                    else if (ObjectFactory.IsDecimalType(propertyType))
                    {
                        /* Property type is complex type and is Decimal */
                        //Log.Debug("property type is complex type and is Decimal");
                        if (propertyTypeObject != null)
                        {
                            ((Decimal)propertyTypeObject).RenderDecimalProperty(propertyTableLayoutPanel, parameterObject, propertyInfoArray, j);
                        }
                        else
                        {
                            Decimal.MinValue.RenderDecimalProperty(propertyTableLayoutPanel, parameterObject, propertyInfoArray, j);
                        }
                    }
                    else if (ObjectFactory.IsDictionaryType(propertyType))
                    {
                        /* Property type is complex type and is Dictionary */
                        //Log.Debug("property type is complex type and is Dictionary");
                        RenderDictionaryProperty(propertyTableLayoutPanel, parameterObject, propertyInfoArray, propertyTypeObject, j);
                    }
                    else if (ObjectFactory.IsDoubleType(propertyType))
                    {
                        /* Property type is complex type and is Double */
                        //Log.Debug("property type is complex type and is Double");
                        if (propertyTypeObject != null)
                        {
                            ((Double)propertyTypeObject).RenderDoubleProperty(propertyTableLayoutPanel, parameterObject, propertyInfoArray, j);
                        }
                        else
                        {
                            Double.MinValue.RenderDoubleProperty(propertyTableLayoutPanel, parameterObject, propertyInfoArray, j);
                        }
                    }
                    else if (ObjectFactory.IsEnumType(propertyType))
                    {
                        /* Property type is complex type and is enum */
                        //Log.Debug("property type is complex type and is enum");
                        if (propertyTypeObject != null)
                        {
                            ((Enum)propertyTypeObject).RenderEnumProperty(propertyTableLayoutPanel, parameterObject, propertyInfoArray, j);
                        }
                    }
                    else if (ObjectFactory.IsIntegerType(propertyType))
                    {
                        /* Property type is complex type and is integer */
                        //Log.Debug("property type is complex type and is integer");
                        if (propertyTypeObject != null)
                        {
                            ((int)propertyTypeObject).RenderIntegerProperty(propertyTableLayoutPanel, parameterObject, propertyInfoArray, j);
                        }
                        else
                        {
                            int.MinValue.RenderIntegerProperty(propertyTableLayoutPanel, parameterObject, propertyInfoArray, j);
                        }
                    }
                    else if (ObjectFactory.IsInt64Type(propertyType))
                    {
                        /* Property type is complex type and is Int64 */
                        //Log.Debug("property type is complex type and is Int64");
                        if (propertyTypeObject != null)
                        {
                            ((Int64)propertyTypeObject).RenderInt64Property(propertyTableLayoutPanel, parameterObject, propertyInfoArray, j);
                        }
                        else
                        {
                            Int64.MinValue.RenderInt64Property(propertyTableLayoutPanel, parameterObject, propertyInfoArray, j);
                        }
                    }
                    else if (ObjectFactory.IsLongType(propertyType))
                    {
                        /* Property type is complex type and is Long */
                        //Log.Debug("property type is complex type and is Long");
                        if (propertyTypeObject != null)
                        {
                            ((long)propertyTypeObject).RenderLongProperty(propertyTableLayoutPanel, parameterObject, propertyInfoArray, j);
                        }
                        else
                        {
                            long.MinValue.RenderLongProperty(propertyTableLayoutPanel, parameterObject, propertyInfoArray, j);
                        }
                    }
                    else if (ObjectFactory.IsSingleType(propertyType))
                    {
                        /* Property type is complex type and is Single */
                        //Log.Debug("property type is complex type and is Single");
                        if (propertyTypeObject != null)
                        {
                            ((Single)propertyTypeObject).RenderSingleProperty(propertyTableLayoutPanel, parameterObject, propertyInfoArray, j);
                        }
                        else
                        {
                            Single.MinValue.RenderSingleProperty(propertyTableLayoutPanel, parameterObject, propertyInfoArray, j);
                        }
                    }
                    else if (ObjectFactory.IsStringType(propertyType))
                    {
                        /* Property type is complex type and is String */
                        //Log.Debug("property type is complex type and is String");
                        if (propertyTypeObject != null)
                        {
                            ((string)propertyTypeObject).RenderStringProperty(propertyTableLayoutPanel, parameterObject, propertyInfoArray, j);
                        }
                        else
                        {
                            string.Empty.RenderStringProperty(propertyTableLayoutPanel, parameterObject, propertyInfoArray, j);
                        }
                    }
                    else if (ObjectFactory.IsTimeSpanType(propertyType))
                    {
                        /* Property type is complex type and is String */
                        Log.Debug("property type is complex type and is TimeSpan");
                        if (propertyTypeObject != null)
                        {
                            ((TimeSpan)propertyTypeObject).RenderTimeSpanProperty(propertyTableLayoutPanel, parameterObject, propertyInfoArray, j);
                        }
                        else
                        {
                            TimeSpan.Zero.RenderTimeSpanProperty(propertyTableLayoutPanel, parameterObject, propertyInfoArray, j);
                        }
                    }
                    else if (ObjectFactory.IsNullableType(propertyType))
                    {
                        //Log.Debug("property type is nullable");
                        Type underlyingPropertyType = Nullable.GetUnderlyingType(propertyType);
                        if (ObjectFactory.IsBooleanType(underlyingPropertyType))
                        {
                            //Log.Debug("underlying property type is nullable boolean");
                            XCaseCheckBox checkBox = new XCaseCheckBox();
                            checkBox.Index = j;
                            checkBox.FieldType = propertyType;
                            propertyTableLayoutPanel.Controls.Add(checkBox, 1, j + 1);
                            checkBox.CheckedChanged += delegate(object sender, EventArgs e)
                            {
                                bool value = checkBox.Checked;
                                int index = checkBox.Index;
                                Type fieldType = checkBox.FieldType;
                                Type underlyingFieldType = Nullable.GetUnderlyingType(fieldType);
                                //Log.Debug("property type is boolean");
                                //Log.Debug("about to create object for boolean");
                                object underlyingPropertyTypeObject = ObjectFactory.CreateObjectFromTypeAndValue(underlyingFieldType, value);
                                propertyTypeObject = Activator.CreateInstance(fieldType, underlyingPropertyTypeObject);
                                propertyInfoArray[index].SetValue(parameterObject, propertyTypeObject, null);
                            };
                        }
                        else if (ObjectFactory.IsByteType(underlyingPropertyType))
                        {
                            //Log.Debug("underlying property type is nullable Byte");
                            XCaseTextBox textBox = new XCaseTextBox();
                            textBox.Index = j;
                            textBox.FieldType = propertyType;
                            //Log.Debug("property type is " + propertyType);
                            propertyTableLayoutPanel.Controls.Add(textBox, 1, j + 1);
                            textBox.TextChanged += delegate(object sender, EventArgs e)
                            {
                                string value = textBox.Text;
                                int index = textBox.Index;
                                Type fieldType = textBox.FieldType;
                                Type underlyingFieldType = Nullable.GetUnderlyingType(fieldType);
                                //Log.Debug("about to create object for nullable string");
                                object underlyingPropertyTypeObject = ObjectFactory.CreateObjectFromTypeAndValue(underlyingFieldType, value);
                                propertyTypeObject = Activator.CreateInstance(fieldType, underlyingPropertyTypeObject);
                                propertyInfoArray[index].SetValue(parameterObject, propertyTypeObject, null);
                            };
                        }
                        else if (ObjectFactory.IsCharType(underlyingPropertyType))
                        {
                            //Log.Debug("underlying property type is nullable Char");
                            XCaseTextBox textBox = new XCaseTextBox();
                            textBox.Index = j;
                            textBox.FieldType = propertyType;
                            //Log.Debug("property type is " + propertyType);
                            propertyTableLayoutPanel.Controls.Add(textBox, 1, j + 1);
                            textBox.TextChanged += delegate(object sender, EventArgs e)
                            {
                                string value = textBox.Text;
                                int index = textBox.Index;
                                Type fieldType = textBox.FieldType;
                                Type underlyingFieldType = Nullable.GetUnderlyingType(fieldType);
                                //Log.Debug("about to create object for nullable string");
                                object underlyingPropertyTypeObject = ObjectFactory.CreateObjectFromTypeAndValue(underlyingFieldType, value);
                                propertyTypeObject = Activator.CreateInstance(fieldType, underlyingPropertyTypeObject);
                                propertyInfoArray[index].SetValue(parameterObject, propertyTypeObject, null);
                            };
                        }
                        else if (ObjectFactory.IsDateTimeType(underlyingPropertyType))
                        {
                            //Log.Debug("underlying property type is nullable datetime");
                            XCaseDateTimePicker dateTimePicker = new XCaseDateTimePicker();
                            dateTimePicker.Index = j;
                            dateTimePicker.FieldType = propertyType;
                            //Log.Debug("property type is " + propertyType);
                            propertyTableLayoutPanel.Controls.Add(dateTimePicker, 1, j + 1);
                            dateTimePicker.ValueChanged += delegate(object sender, EventArgs e)
                            {
                                DateTime value = dateTimePicker.Value;
                                //Log.Debug("DateTimePicker value is " + value);
                                int index = dateTimePicker.Index;
                                Type fieldType = dateTimePicker.FieldType;
                                Type underlyingFieldType = Nullable.GetUnderlyingType(fieldType);
                                //Log.Debug("about to create object for nullable datetime");
                                object underlyingPropertyTypeObject = ObjectFactory.CreateObjectFromTypeAndValue(underlyingFieldType, value);
                                propertyTypeObject = Activator.CreateInstance(fieldType, underlyingPropertyTypeObject);
                                propertyInfoArray[index].SetValue(parameterObject, propertyTypeObject, null);
                            };
                        }
                        else if (ObjectFactory.IsDecimalType(underlyingPropertyType))
                        {
                            //Log.Debug("underlying property type is nullable Decimal");
                            XCaseTextBox textBox = new XCaseTextBox();
                            textBox.Index = j;
                            textBox.FieldType = propertyType;
                            //Log.Debug("property type is " + propertyType);
                            propertyTableLayoutPanel.Controls.Add(textBox, 1, j + 1);
                            textBox.TextChanged += delegate(object sender, EventArgs e)
                            {
                                Decimal value = Decimal.Zero;
                                Decimal.TryParse(textBox.Text, out value);
                                int index = textBox.Index;
                                Type fieldType = textBox.FieldType;
                                Type underlyingFieldType = Nullable.GetUnderlyingType(fieldType);
                                //Log.Debug("about to create object for nullable string");
                                object underlyingPropertyTypeObject = ObjectFactory.CreateObjectFromTypeAndValue(underlyingFieldType, value);
                                propertyTypeObject = Activator.CreateInstance(fieldType, underlyingPropertyTypeObject);
                                propertyInfoArray[index].SetValue(parameterObject, propertyTypeObject, null);
                            };
                        }
                        else if (ObjectFactory.IsDoubleType(underlyingPropertyType))
                        {
                            //Log.Debug("underlying property type is nullable Double");
                            XCaseTextBox textBox = new XCaseTextBox();
                            textBox.Index = j;
                            textBox.FieldType = propertyType;
                            //Log.Debug("property type is " + propertyType);
                            propertyTableLayoutPanel.Controls.Add(textBox, 1, j + 1);
                            textBox.TextChanged += delegate(object sender, EventArgs e)
                            {
                                Double value = 0.0;
                                Double.TryParse(textBox.Text, out value);
                                int index = textBox.Index;
                                Type fieldType = textBox.FieldType;
                                Type underlyingFieldType = Nullable.GetUnderlyingType(fieldType);
                                //Log.Debug("about to create object for nullable string");
                                object underlyingPropertyTypeObject = ObjectFactory.CreateObjectFromTypeAndValue(underlyingFieldType, value);
                                propertyTypeObject = Activator.CreateInstance(fieldType, underlyingPropertyTypeObject);
                                propertyInfoArray[index].SetValue(parameterObject, propertyTypeObject, null);
                            };
                        }
                        else if (underlyingPropertyType.IsEnum)
                        {
                            //Log.Debug("underlying property type is nullable enum");
                            XCaseComboBox comboBox = new XCaseComboBox();
                            comboBox.Index = j;
                            comboBox.FieldType = propertyType;
                            comboBox.DataSource = Enum.GetValues(underlyingPropertyType);
                            propertyTableLayoutPanel.Controls.Add(comboBox, 1, j + 1);
                            comboBox.SelectedIndexChanged += delegate(object sender, EventArgs e)
                            {
                                Enum value = (Enum)comboBox.SelectedValue;
                                int index = comboBox.Index;
                                Type fieldType = comboBox.FieldType;
                                Type underlyingFieldType = Nullable.GetUnderlyingType(fieldType);
                                //Log.Debug("about to create object for nullable enum");
                                object underlyingPropertyTypeObject = ObjectFactory.CreateObjectFromTypeAndValue(underlyingFieldType, value);
                                propertyTypeObject = Activator.CreateInstance(fieldType, underlyingPropertyTypeObject);
                                propertyInfoArray[index].SetValue(parameterObject, propertyTypeObject, null);
                            };
                        }
                        else if (ObjectFactory.IsInt64Type(underlyingPropertyType))
                        {
                            Log.Debug("underlying property type is nullable Int64");
                            XCaseTextBox textBox = new XCaseTextBox();
                            textBox.Index = j;
                            textBox.FieldType = propertyType;
                            propertyTableLayoutPanel.Controls.Add(textBox, 1, j + 1);
                            textBox.TextChanged += delegate(object sender, EventArgs e)
                            {
                                Int64 value = 0;
                                Int64.TryParse(textBox.Text, out value);
                                int index = textBox.Index;
                                Type fieldType = textBox.FieldType;
                                Type underlyingFieldType = Nullable.GetUnderlyingType(fieldType);
                                Log.Debug("about to create object for nullable long");
                                object underlyingPropertyTypeObject = ObjectFactory.CreateInt64ObjectFromTypeAndValue(underlyingFieldType, value);
                                propertyTypeObject = Activator.CreateInstance(fieldType, underlyingPropertyTypeObject);
                                propertyInfoArray[index].SetValue(parameterObject, propertyTypeObject, null);
                            };
                        }
                        else if (ObjectFactory.IsLongType(underlyingPropertyType))
                        {
                            //Log.Debug("underlying property type is nullable Long");
                            XCaseTextBox textBox = new XCaseTextBox();
                            textBox.Index = j;
                            textBox.FieldType = propertyType;
                            propertyTableLayoutPanel.Controls.Add(textBox, 1, j + 1);
                            textBox.TextChanged += delegate(object sender, EventArgs e)
                            {
                                long value = 0;
                                long.TryParse(textBox.Text, out value);
                                int index = textBox.Index;
                                Type fieldType = textBox.FieldType;
                                Type underlyingFieldType = Nullable.GetUnderlyingType(fieldType);
                                Log.Debug("about to create object for nullable long");
                                object underlyingPropertyTypeObject = ObjectFactory.CreateInt64ObjectFromTypeAndValue(underlyingFieldType, value);
                                propertyTypeObject = Activator.CreateInstance(fieldType, underlyingPropertyTypeObject);
                                propertyInfoArray[index].SetValue(parameterObject, propertyTypeObject, null);
                            };
                        }
                        else if (ObjectFactory.IsSingleType(underlyingPropertyType))
                        {
                            //Log.Debug("underlying property type is nullable Single");
                            XCaseTextBox textBox = new XCaseTextBox();
                            textBox.Index = j;
                            textBox.FieldType = propertyType;
                            //Log.Debug("property type is " + propertyType);
                            propertyTableLayoutPanel.Controls.Add(textBox, 1, j + 1);
                            textBox.TextChanged += delegate(object sender, EventArgs e)
                            {
                                Single value = 0;
                                Single.TryParse(textBox.Text, out value);
                                int index = textBox.Index;
                                Type fieldType = textBox.FieldType;
                                Type underlyingFieldType = Nullable.GetUnderlyingType(fieldType);
                                //Log.Debug("about to create object for nullable string");
                                object underlyingPropertyTypeObject = ObjectFactory.CreateObjectFromTypeAndValue(underlyingFieldType, value);
                                propertyTypeObject = Activator.CreateInstance(fieldType, underlyingPropertyTypeObject);
                                propertyInfoArray[index].SetValue(parameterObject, propertyTypeObject, null);
                            };
                        }
                        else if (ObjectFactory.IsStringType(underlyingPropertyType))
                        {
                            //Log.Debug("underlying property type is nullable String");
                            XCaseTextBox textBox = new XCaseTextBox();
                            textBox.Index = j;
                            textBox.FieldType = propertyType;
                            //Log.Debug("property type is " + propertyType);
                            propertyTableLayoutPanel.Controls.Add(textBox, 1, j + 1);
                            textBox.TextChanged += delegate(object sender, EventArgs e)
                            {
                                string value = textBox.Text;
                                int index = textBox.Index;
                                Type fieldType = textBox.FieldType;
                                Type underlyingFieldType = Nullable.GetUnderlyingType(fieldType);
                                //Log.Debug("about to create object for nullable string");
                                object underlyingPropertyTypeObject = ObjectFactory.CreateObjectFromTypeAndValue(underlyingFieldType, value);
                                propertyTypeObject = Activator.CreateInstance(fieldType, underlyingPropertyTypeObject);
                                propertyInfoArray[index].SetValue(parameterObject, propertyTypeObject, null);
                            };
                        }
                        else
                        {
                            //Log.Debug("property type is nullable complex");
                        }
                    }
                    else
                    {
                        //Log.Debug("property type is " + propertyTypeObject.GetType());
                        TableLayoutPanel childTableLayoutPanel = RenderParameterObject(propertyTypeObject);
                        int index = j;
                        propertyTableLayoutPanel.Controls.Add(childTableLayoutPanel, 1, j + 1);
                        //Log.Debug("added child table layout panel for " + propertyInfoArray[j].Name + " on row " + (j + 1));
                        Button nullButton = new Button();
                        propertyTableLayoutPanel.Controls.Add(nullButton, 2, j + 1);
                        nullButton.Text = XCaseServiceClient.Properties.Resources.Null;
                        nullButton.MouseClick += delegate(object o, MouseEventArgs mev)
                        {
                            propertyTypeObject = null;
                            propertyInfoArray[index].SetValue(parameterObject, propertyTypeObject, null);
                        };
                    }
                }
            }

            propertyTableLayoutPanel.PerformLayout();
            propertyTableLayoutPanel.Dock = DockStyle.Fill;
            //Log.Debug("finishing renderPropertyObject for " + parameterObject.GetType());
            return propertyTableLayoutPanel;
        }

        public static XCaseTableLayoutPanel RenderPropertyObject(object parameterObject, PropertyInfo[] propertyInfoArray, object propertyObject)
        {
            Log.Debug("starting RenderPropertyObject()");
            XCaseTableLayoutPanel propertyTableLayoutPanel = new XCaseTableLayoutPanel();
            return propertyTableLayoutPanel;
        }

        /* This method renders the single panel for an object in an array */
        public static XCaseTableLayoutPanel RenderArrayObject(Array array, int index, object arrayObject)
        {
            Log.Debug("starting RenderArrayObject()");
            XCaseTableLayoutPanel arrayObjectTableLayoutPanel = new XCaseTableLayoutPanel();
            arrayObjectTableLayoutPanel.Index = index;
            if (arrayObject == null)
            {
                return arrayObjectTableLayoutPanel;
            }
            
            Type arrayObjectType = arrayObject.GetType();
            if (ObjectFactory.IsBooleanType(arrayObjectType))
            {
                XCaseCheckBox checkBox = new XCaseCheckBox();
                arrayObjectTableLayoutPanel.Controls.Add(checkBox, 1, 0);
                checkBox.CheckedChanged += delegate(object sender, EventArgs e)
                {
                    //Log.DebugFormat("check box changed to {0}", checkBox.Checked);
                    bool value = checkBox.Checked;
                    arrayObject = ObjectFactory.CreateObjectFromTypeAndValue(typeof(bool), value);
                    array.SetValue(arrayObject, arrayObjectTableLayoutPanel.Index);
                    //array[arrayObjectTableLayoutPanel.index] = arrayObject;
                };
            }
            else if (ObjectFactory.IsByteType(arrayObjectType))
            {
                XCaseTextBox xcaseTextBox = new XCaseTextBox();
                arrayObjectTableLayoutPanel.Controls.Add(xcaseTextBox, 1, 0);
                xcaseTextBox.TextChanged += delegate(object sender, EventArgs e)
                {
                    //Log.DebugFormat("text box changed to {0}", xcaseTextBox.Text);
                    byte value = 0;
                    Byte.TryParse(xcaseTextBox.Text, out value);
                    arrayObject = ObjectFactory.CreateObjectFromTypeAndValue(arrayObjectType, value);
                    array.SetValue(arrayObject, arrayObjectTableLayoutPanel.Index);
                    //array[arrayObjectTableLayoutPanel.index] = arrayObject;
                };
            }
            else if (ObjectFactory.IsDateTimeType(arrayObjectType))
            {
                XCaseDateTimePicker xcaseDateTimePicker = new XCaseDateTimePicker();
                arrayObjectTableLayoutPanel.Controls.Add(xcaseDateTimePicker, 1, 0);
                xcaseDateTimePicker.ValueChanged += delegate(object sender, EventArgs e)
                {
                    //Log.DebugFormat("text box changed to {0}", xcaseDateTimePicker.Value);
                    DateTime value = xcaseDateTimePicker.Value;
                    arrayObject = ObjectFactory.CreateObjectFromTypeAndValue(typeof(DateTime), value);
                    array.SetValue(arrayObject, arrayObjectTableLayoutPanel.Index);
                    //array[arrayObjectTableLayoutPanel.index] = arrayObject;
                };
            }
            else if (ObjectFactory.IsDecimalType(arrayObjectType))
            {
                XCaseTextBox xcaseTextBox = new XCaseTextBox();
                arrayObjectTableLayoutPanel.Controls.Add(xcaseTextBox, 1, 0);
                xcaseTextBox.TextChanged += delegate(object sender, EventArgs e)
                {
                    //Log.DebugFormat("text box changed to {0}", xcaseTextBox.Text);
                    Decimal value = Decimal.Zero;
                    Decimal.TryParse(xcaseTextBox.Text, out value);
                    arrayObject = ObjectFactory.CreateObjectFromTypeAndValue(arrayObjectType, value);
                    array.SetValue(arrayObject, arrayObjectTableLayoutPanel.Index);
                    //array[arrayObjectTableLayoutPanel.index] = arrayObject;
                };
            }
            else if (ObjectFactory.IsDoubleType(arrayObjectType))
            {
                XCaseTextBox xcaseTextBox = new XCaseTextBox();
                arrayObjectTableLayoutPanel.Controls.Add(xcaseTextBox, 1, 0);
                xcaseTextBox.TextChanged += delegate(object sender, EventArgs e)
                {
                    //Log.DebugFormat("text box changed to {0}", xcaseTextBox.Text);
                    Double value = 0.0;
                    Double.TryParse(xcaseTextBox.Text, out value);
                    arrayObject = ObjectFactory.CreateObjectFromTypeAndValue(arrayObjectType, value);
                    array.SetValue(arrayObject, arrayObjectTableLayoutPanel.Index);
                    //array[arrayObjectTableLayoutPanel.index] = arrayObject;
                };
            }
            else if (ObjectFactory.IsEnumType(arrayObjectType))
            {
                XCaseComboBox xcaseComboBox = new XCaseComboBox();
                arrayObjectTableLayoutPanel.Controls.Add(xcaseComboBox, 1, 0);
                xcaseComboBox.DataSource = Enum.GetValues(arrayObjectType);
                xcaseComboBox.SelectedValueChanged += delegate(object sender, EventArgs e)
                {
                    //Log.DebugFormat("combo box changed to {0}", xcaseComboBox.SelectedValue);
                    Enum value = (Enum)xcaseComboBox.SelectedValue;
                    arrayObject = ObjectFactory.CreateObjectFromTypeAndValue(arrayObjectType, value);
                    array.SetValue(arrayObject, arrayObjectTableLayoutPanel.Index);
                    //array[arrayObjectTableLayoutPanel.index] = arrayObject;
                };
            }
            else if (ObjectFactory.IsIntegerType(arrayObjectType))
            {
                XCaseTextBox xcaseTextBox = new XCaseTextBox();
                arrayObjectTableLayoutPanel.Controls.Add(xcaseTextBox, 1, 0);
                xcaseTextBox.TextChanged += delegate(object sender, EventArgs e)
                {
                    //Log.DebugFormat("text box changed to {0}", xcaseTextBox.Text);
                    int value = 0;
                    int.TryParse(xcaseTextBox.Text, out value);
                    arrayObject = ObjectFactory.CreateObjectFromTypeAndValue(arrayObjectType, value);
                    array.SetValue(arrayObject, arrayObjectTableLayoutPanel.Index);
                    //array[arrayObjectTableLayoutPanel.index] = arrayObject;
                };
            }
            else if (ObjectFactory.IsInt64Type(arrayObjectType))
            {
                XCaseTextBox xcaseTextBox = new XCaseTextBox();
                arrayObjectTableLayoutPanel.Controls.Add(xcaseTextBox, 1, 0);
                xcaseTextBox.TextChanged += delegate(object sender, EventArgs e)
                {
                    //Log.DebugFormat("text box changed to {0}", xcaseTextBox.Text);
                    Int64 value = 0;
                    Int64.TryParse(xcaseTextBox.Text, out value);
                    arrayObject = ObjectFactory.CreateInt64ObjectFromTypeAndValue(arrayObjectType, value);
                    array.SetValue(arrayObject, arrayObjectTableLayoutPanel.Index);
                    //array[arrayObjectTableLayoutPanel.index] = arrayObject;
                };
            }
            else if (ObjectFactory.IsLongType(arrayObjectType))
            {
                XCaseTextBox xcaseTextBox = new XCaseTextBox();
                arrayObjectTableLayoutPanel.Controls.Add(xcaseTextBox, 1, 0);
                xcaseTextBox.TextChanged += delegate(object sender, EventArgs e)
                {
                    //Log.DebugFormat("text box changed to {0}", xcaseTextBox.Text);
                    long value = 0;
                    long.TryParse(xcaseTextBox.Text, out value);
                    arrayObject = ObjectFactory.CreateInt64ObjectFromTypeAndValue(arrayObjectType, value);
                    array.SetValue(arrayObject, arrayObjectTableLayoutPanel.Index);
                    //array[arrayObjectTableLayoutPanel.index] = arrayObject;
                };
            }
            else if (ObjectFactory.IsSingleType(arrayObjectType))
            {
                XCaseTextBox xcaseTextBox = new XCaseTextBox();
                arrayObjectTableLayoutPanel.Controls.Add(xcaseTextBox, 1, 0);
                xcaseTextBox.TextChanged += delegate(object sender, EventArgs e)
                {
                    //Log.DebugFormat("text box changed to {0}", xcaseTextBox.Text);
                    Single value = 0;
                    Single.TryParse(xcaseTextBox.Text, out value);
                    arrayObject = ObjectFactory.CreateObjectFromTypeAndValue(arrayObjectType, value);
                    array.SetValue(arrayObject, arrayObjectTableLayoutPanel.Index);
                    //array[arrayObjectTableLayoutPanel.index] = arrayObject;
                };
            }
            else if (ObjectFactory.IsStringType(arrayObjectType))
            {
                XCaseTextBox xcaseTextBox = new XCaseTextBox();
                arrayObjectTableLayoutPanel.Controls.Add(xcaseTextBox, 1, 0);
                xcaseTextBox.TextChanged += delegate(object sender, EventArgs e)
                {
                    //Log.DebugFormat("text box changed to {0}", xcaseTextBox.Text);
                    string value = xcaseTextBox.Text;
                    //Log.DebugFormat("index is {0}", arrayObjectTableLayoutPanel.index);
                    arrayObject = ObjectFactory.CreateObjectFromTypeAndValue(typeof(string), value);
                    Log.Debug("arrayObject changed to {0}", arrayObject);
                    array.SetValue(arrayObject, arrayObjectTableLayoutPanel.Index);
                    //array[arrayObjectTableLayoutPanel.index] = arrayObject;
                };
            }
            else
            {
                arrayObjectTableLayoutPanel = RenderParameterObject(arrayObject);
            }

            return arrayObjectTableLayoutPanel;
        }

        public static void RenderIEnumerable(TableLayoutPanel propertyTableLayoutPanel, object[] parameterArray, object parameterObject, int index)
        {
            Log.Debug("starting RenderIEnumerable()");
            XCaseTableLayoutPanel arrayTableLayoutPanel = new XCaseTableLayoutPanel();
            arrayTableLayoutPanel.PropertyObject = parameterObject;
            arrayTableLayoutPanel.CellBorderStyle = TableLayoutPanelCellBorderStyle.Single;
            arrayTableLayoutPanel.AutoSize = true;
            arrayTableLayoutPanel.ColumnCount = 1;
            arrayTableLayoutPanel.AutoScroll = true;
            if (parameterObject == null)
            {
                return;
            }

            Type elementType = parameterObject.GetType().GetElementType();
            //Log.Debug("element type is " + elementType);
            int arrayLength = 0;
            arrayTableLayoutPanel.RowCount = arrayLength + 1;
            //Log.Debug("length of array is " + arrayLength);
            XCaseButton addButton = new XCaseButton();
            addButton.Text = "Add";
            addButton.PropertyObject = parameterObject;
            addButton.FieldType = parameterObject.GetType().GetGenericArguments()[0];
            //Log.Debug("set Add button properties");
            addButton.MouseClick += delegate(object sender, MouseEventArgs mea)
            {
                arrayLength = ((IList)parameterObject).Count;
                ArrayList parameterObjectArrayList = new ArrayList(((IList)parameterObject));
                object newArrayObject = ObjectFactory.CreateDefaultObject(addButton.FieldType);
                parameterObjectArrayList.Add(newArrayObject);
                parameterObject = parameterObjectArrayList.ToArray(newArrayObject.GetType());
                TableLayoutPanel newPropertyTableLayoutPanel = RenderArrayObject((Array)parameterObject, arrayLength, newArrayObject);
                arrayTableLayoutPanel.Controls.Add(newPropertyTableLayoutPanel, 0, parameterObjectArrayList.Count + 1);
                if (parameterArray != null && index >= 0 && index < parameterArray.Length)
                {
                    parameterArray[index] = parameterObject;
                }
            };
            arrayTableLayoutPanel.Controls.Add(addButton, 0, 0);
            for (int i = 0; i < arrayLength; i++)
            {
                object rowObject = ((Array)parameterObject).GetValue(i);
                if (rowObject != null)
                {
                    TableLayoutPanel rowTableLayoutPanel = RenderParameterObject(rowObject);
                    arrayTableLayoutPanel.Controls.Add(rowTableLayoutPanel, 0, i + 1);
                }
            }

            propertyTableLayoutPanel.Controls.Add(arrayTableLayoutPanel, 1, index + 1);
            Log.Debug("finishing RenderIEnumerable()");
        }

        public static void RenderArray(TableLayoutPanel propertyTableLayoutPanel, object[] parameterArray, object parameterObject, int index)
        {
            Log.Debug("starting RenderArray()");
            XCaseTableLayoutPanel arrayTableLayoutPanel = new XCaseTableLayoutPanel();
            arrayTableLayoutPanel.PropertyObject = parameterObject;
            arrayTableLayoutPanel.CellBorderStyle = TableLayoutPanelCellBorderStyle.Single;
            arrayTableLayoutPanel.AutoSize = true;
            arrayTableLayoutPanel.ColumnCount = 1;
            arrayTableLayoutPanel.AutoScroll = true;
            if (parameterObject == null)
            {
                return;
            }

            int arrayLength = ((Array)parameterObject).Length;
            arrayTableLayoutPanel.RowCount = arrayLength + 1;
            //Log.Debug("length of array is " + arrayLength);
            XCaseButton addButton = new XCaseButton();
            addButton.Text = "Add";
            addButton.PropertyObject = parameterObject;
            addButton.FieldType = parameterObject.GetType().GetElementType();
            //Log.Debug("set Add button properties");
            addButton.MouseClick += delegate(object sender, MouseEventArgs mea)
            {
                //Log.Debug("Add button clicked");
                arrayLength = ((Array)parameterObject).Length;
                ArrayList parameterObjectArrayList = new ArrayList(((Array)parameterObject));
                object newArrayObject = ObjectFactory.CreateDefaultObject(addButton.FieldType);
                parameterObjectArrayList.Add(newArrayObject);
                parameterObject = parameterObjectArrayList.ToArray(newArrayObject.GetType());
                //Log.Debug("parameterObject has length {0}", ((Array)parameterObject).Length);
                TableLayoutPanel newPropertyTableLayoutPanel = RenderArrayObject((Array)parameterObject, arrayLength, newArrayObject);
                //Log.Debug("rendered new property object");
                arrayTableLayoutPanel.Controls.Add(newPropertyTableLayoutPanel, 0, parameterObjectArrayList.Count + 1);
                if (parameterArray != null && index >= 0 && index < parameterArray.Length)
                {
                    parameterArray[index] = parameterObject;
                }
            };
            //Log.Debug("set click behavior");
            arrayTableLayoutPanel.Controls.Add(addButton, 0, 0);
            //Log.Debug("added button to panel");
            for (int i = 0; i < arrayLength; i++)
            {
                object rowObject = ((Array)parameterObject).GetValue(i);
                if (rowObject != null)
                {
                    //Log.Debug("row object is " + rowObject.GetType());
                    TableLayoutPanel rowTableLayoutPanel = RenderParameterObject(rowObject);
                    arrayTableLayoutPanel.Controls.Add(rowTableLayoutPanel, 0, i + 1);
                    //Log.Debug("added row");
                }
            }

            propertyTableLayoutPanel.Controls.Add(arrayTableLayoutPanel, 1, index + 1);
            Log.Debug("finishing RenderArray()");
        }

        /* This method renders an array as a table layout panel */
        public static void RenderArrayProperty(TableLayoutPanel propertyTableLayoutPanel, object parameterObject, PropertyInfo[] propertyInfoArray, object propertyTypeObject, int index)
        {
            //Log.Debug("starting RenderArrayProperty()");
            XCaseTableLayoutPanel arrayTableLayoutPanel = new XCaseTableLayoutPanel();
            arrayTableLayoutPanel.PropertyObject = parameterObject;
            arrayTableLayoutPanel.CellBorderStyle = TableLayoutPanelCellBorderStyle.Single;
            arrayTableLayoutPanel.AutoSize = true;
            arrayTableLayoutPanel.ColumnCount = 2;
            arrayTableLayoutPanel.AutoScroll = true;
            if (parameterObject == null || propertyTypeObject == null)
            {
                return;
            }

            int arrayLength = ((Array)propertyTypeObject).Length;
            arrayTableLayoutPanel.RowCount = arrayLength + 1;
            //Log.Debug("length of array is " + arrayLength);
            XCaseButton addButton = new XCaseButton();
            addButton.Text = "Add";
            addButton.PropertyObject = propertyTypeObject;
            addButton.FieldType = propertyTypeObject.GetType().GetElementType();
            //Log.Debug("set Add button properties");
            addButton.MouseClick += delegate(object sender, MouseEventArgs mea)
            {
                Log.Debug("Add button clicked");
                arrayLength = ((Array)propertyTypeObject).Length;
                ArrayList propertyObjectArrayList = new ArrayList(((Array)propertyTypeObject));
                object newArrayObject = ObjectFactory.CreateDefaultObject(addButton.FieldType);
                propertyObjectArrayList.Add(newArrayObject);
                propertyTypeObject = propertyObjectArrayList.ToArray(newArrayObject.GetType());
                Log.Debug("arrayLength is {0}", arrayLength);
                propertyInfoArray[index].SetValue(parameterObject, propertyTypeObject, null);
                TableLayoutPanel newPropertyTableLayoutPanel = RenderArrayObject((Array)propertyTypeObject, arrayLength, newArrayObject);
                arrayTableLayoutPanel.Controls.Add(newPropertyTableLayoutPanel, 0, propertyObjectArrayList.Count + 1);
            };
            //Log.Debug("set click behavior");
            arrayTableLayoutPanel.Controls.Add(addButton, 0, 0);
            //Log.Debug("added button to panel");
            for (int i = 0; i < arrayLength; i++)
            {
                object rowObject = ((Array)parameterObject).GetValue(i);
                if (rowObject != null)
                {
                    //Log.Debug("row object is " + rowObject.GetType());
                    TableLayoutPanel rowTableLayoutPanel = RenderParameterObject(rowObject);
                    arrayTableLayoutPanel.Controls.Add(rowTableLayoutPanel, 0, i + 1);
                    //Log.Debug("added row");
                    Button nullButton = new Button();
                    rowTableLayoutPanel.Controls.Add(nullButton, 1, index + 1);
                    nullButton.Text = XCaseServiceClient.Properties.Resources.Null;
                    nullButton.MouseClick += delegate(object o, MouseEventArgs mev)
                    {
                        if (propertyInfoArray != null && index >= 0 && index < propertyInfoArray.Length)
                        {
                            rowObject = null;
                        }
                    };
                }
            }

            propertyTableLayoutPanel.Controls.Add(arrayTableLayoutPanel, 1, index + 1);
            Log.Debug("finishing RenderArrayProperty()");
        }

        public static void RenderDictionary(TableLayoutPanel propertyTableLayoutPanel, object[] parameterArray, object parameterObject, int index)
        {
            Log.Debug("starting RenderDictionary()");
            if (parameterObject == null)
            {
                return;
            }

            Type[] dictionaryTypes = parameterObject.GetType().GetGenericArguments();
            Type keyType = dictionaryTypes[0];
            Type valueType = dictionaryTypes[1];
            XCaseTableLayoutPanel dictionaryTableLayoutPanel = new XCaseTableLayoutPanel();
            dictionaryTableLayoutPanel.PropertyObject = parameterObject;
            dictionaryTableLayoutPanel.AutoScroll = true;
            dictionaryTableLayoutPanel.AutoSize = true;
            dictionaryTableLayoutPanel.CellBorderStyle = TableLayoutPanelCellBorderStyle.Single;
            dictionaryTableLayoutPanel.ColumnCount = 1;
            dictionaryTableLayoutPanel.Dock = DockStyle.Fill;
            int dictionaryLength = (parameterObject as IDictionary).Count;
            dictionaryTableLayoutPanel.RowCount = dictionaryLength + 1;
            //Log.Debug("length of array is " + arrayLength);
            XCaseButton addButton = new XCaseButton();
            addButton.Text = "Add";
            addButton.PropertyObject = parameterObject;
            addButton.FieldType = parameterObject.GetType().GetElementType();
            //Log.Debug("set Add button properties");
            addButton.MouseClick += delegate(object sender, MouseEventArgs mea)
            {
                Log.Debug("Add button clicked");
                dictionaryLength = ((IDictionary)parameterObject).Count;
                object newKeyObject = ObjectFactory.CreateUniqueObject(keyType);
                object newValueObject = ObjectFactory.CreateDefaultObject(valueType);
                ((IDictionary)parameterObject).Add(newKeyObject, newValueObject);
                DictionaryEntry newDictionaryEntry = new DictionaryEntry();
                newDictionaryEntry.Key = newKeyObject;
                newDictionaryEntry.Value = newValueObject;
                TableLayoutPanel dictionaryEntryTableLayoutPanel = RenderDictionaryEntryObject((IDictionary)parameterObject, newDictionaryEntry);
                Log.Debug("rendered newDictionaryEntry");
                dictionaryTableLayoutPanel.Controls.Add(dictionaryEntryTableLayoutPanel, 0, ((IDictionary)parameterObject).Count + 1);
                if (parameterArray != null && index >= 0 && index < parameterArray.Length)
                {
                    parameterArray[index] = parameterObject;
                }
            };
            //Log.Debug("set click behavior");
            dictionaryTableLayoutPanel.Controls.Add(addButton, 0, 0);
            //Log.Debug("added button to panel");
            int i = 0;
            foreach(DictionaryEntry dictionaryEntry in ((IDictionary)parameterObject))
            {
                TableLayoutPanel rowTableLayoutPanel = RenderDictionaryEntryObject((IDictionary)parameterObject, dictionaryEntry);
                dictionaryTableLayoutPanel.Controls.Add(rowTableLayoutPanel, 0, i + 1);
                i++;
            }

            propertyTableLayoutPanel.Controls.Add(dictionaryTableLayoutPanel, 1, index + 1);
            Log.Debug("finishing RenderDictionary()");
        }

        private static TableLayoutPanel RenderDictionaryEntryObject(IDictionary dictionary, DictionaryEntry dictionaryEntry)
        {
            TableLayoutPanel dictionaryEntryTableLayoutPanel = new TableLayoutPanel();
            dictionaryEntryTableLayoutPanel.ColumnCount = 3;
            dictionaryEntryTableLayoutPanel.Dock = DockStyle.Fill;
            dictionaryEntryTableLayoutPanel.RowCount = 1;
            dictionaryEntryTableLayoutPanel.RowStyles.Add(new RowStyle(SizeType.Percent, 100F));
            //dictionaryEntryTableLayoutPanel.Controls.Add(RenderDictionaryKeyObject(dictionary, dictionaryEntry), 0, 0);
            /* Assume that the key is a string or enum */
            if (dictionaryEntry.Key is string)
            {
                XCaseTextBox keyTextBox = new XCaseTextBox();
                keyTextBox.Text = (string)dictionaryEntry.Key;
                dictionaryEntryTableLayoutPanel.Controls.Add(keyTextBox, 0, 0);
                keyTextBox.TextChanged += delegate(object sender, EventArgs e)
                {
                    //Log.DebugFormat("text box changed to {0}", keyTextBox.Text);
                    dictionary.Remove(dictionaryEntry.Key);
                    string value = keyTextBox.Text;
                    dictionaryEntry.Key = ObjectFactory.CreateObjectFromTypeAndValue(typeof(string), value);
                    dictionary.Add(dictionaryEntry.Key, dictionaryEntry.Value);
                };
            }
            else if (dictionaryEntry.Key is Enum)
            {
                XCaseComboBox comboBox = new XCaseComboBox();
                comboBox.FieldType = dictionaryEntry.Key.GetType();
                comboBox.DataSource = Enum.GetValues(dictionaryEntry.Key.GetType());
                dictionaryEntryTableLayoutPanel.Controls.Add(comboBox, 0, 0);
                comboBox.SelectedIndexChanged += delegate(object sender, EventArgs e)
                {
                    //Log.Debug("combo box changed to {0}", comboBox.SelectedValue);
                    dictionary.Remove(dictionaryEntry.Key);
                    dictionaryEntry.Key = (Enum)comboBox.SelectedValue;
                    dictionary.Add(dictionaryEntry.Key, dictionaryEntry.Value);
                };
            }
            else
            {
                Log.Warning("key type is not string or enum {0}", dictionaryEntry.Key.GetType().Name);
            }

            /* Assume that the value is a string or enum*/
            if (dictionaryEntry.Value is string)
            {
                XCaseTextBox valueTextBox = new XCaseTextBox();
                valueTextBox.Text = (string)dictionaryEntry.Value;
                dictionaryEntryTableLayoutPanel.Controls.Add(valueTextBox, 1, 0);
                valueTextBox.TextChanged += delegate(object sender, EventArgs e)
                {
                    //Log.DebugFormat("text box changed to {0}", valueTextBox.Text);
                    string value = valueTextBox.Text;
                    dictionaryEntry.Value = ObjectFactory.CreateObjectFromTypeAndValue(typeof(string), value);
                    dictionary[dictionaryEntry.Key] = dictionaryEntry.Value;
                };
            }
            else if (dictionaryEntry.Value is Enum)
            {
                XCaseComboBox comboBox = new XCaseComboBox();
                comboBox.FieldType = dictionaryEntry.Value.GetType();
                comboBox.DataSource = Enum.GetValues(dictionaryEntry.Value.GetType());
                dictionaryEntryTableLayoutPanel.Controls.Add(comboBox, 1, 0);
                comboBox.SelectedIndexChanged += delegate(object sender, EventArgs e)
                {
                    //Log.Debug("combo box changed to {0}", comboBox.SelectedValue);
                    dictionaryEntry.Value = (Enum)comboBox.SelectedValue;
                    dictionary[dictionaryEntry.Key] = dictionaryEntry.Value;
                };
            }
            else
            {
                Log.Warning("value type is not string or enum {0}", dictionaryEntry.Value.GetType().Name);
            }

            Button removeButton = new Button();
            removeButton.Text = "Remove";
            dictionaryEntryTableLayoutPanel.Controls.Add(removeButton, 2, 0);
            removeButton.MouseClick += delegate(object sender, MouseEventArgs mea)
            {
                dictionary.Remove(dictionaryEntry.Key);
            };
            return dictionaryEntryTableLayoutPanel;
        }

        private static void RenderArray(XCaseTableLayoutPanel propertyTableLayoutPanel, IDictionary dictionary, object dictionaryKeyObject, int p)
        {
            /* TODO: complete rendering of dictionary objects */
        }

        private static void RenderDictionaryKey(XCaseTableLayoutPanel propertyTableLayoutPanel, IDictionary dictionary, object dictionaryKeyObject, int p)
        {
            /* TODO: complete rendering of dictionary objects */
        }

        //private static void RenderIEnumerable(XCaseTableLayoutPanel propertyTableLayoutPanel, IDictionary dictionary, object dictionaryKeyObject, int p)
        //{
        //    /* TODO: complete rendering of dictionary objects */
        //}

        //private static void RenderBoolean(XCaseTableLayoutPanel propertyTableLayoutPanel, IDictionary dictionary, object dictionaryKeyObject, int p)
        //{
        //    /* TODO: complete rendering of dictionary objects */
        //}

        //private static void RenderByte(XCaseTableLayoutPanel propertyTableLayoutPanel, IDictionary dictionary, object dictionaryKeyObject, int p)
        //{
        //    /* TODO: complete rendering of dictionary objects */
        //}

        //private static void RenderChar(XCaseTableLayoutPanel propertyTableLayoutPanel, IDictionary dictionary, object dictionaryKeyObject, int p)
        //{
        //    /* TODO: complete rendering of dictionary objects */
        //}

        //private static void RenderDateTime(XCaseTableLayoutPanel propertyTableLayoutPanel, IDictionary dictionary, object dictionaryKeyObject, int p)
        //{
        //    /* TODO: complete rendering of dictionary objects */
        //}

        //private static void RenderDecimal(XCaseTableLayoutPanel propertyTableLayoutPanel, IDictionary dictionary, object dictionaryKeyObject, int p)
        //{
        //    /* TODO: complete rendering of dictionary objects */
        //}

        //private static void RenderDouble(XCaseTableLayoutPanel propertyTableLayoutPanel, IDictionary dictionary, object dictionaryKeyObject, int p)
        //{
        //    /* TODO: complete rendering of dictionary objects */
        //}

        //private static void RenderEnum(XCaseTableLayoutPanel propertyTableLayoutPanel, IDictionary dictionary, object dictionaryKeyObject, int p)
        //{
        //    /* TODO: complete rendering of dictionary objects */
        //}

        private static void RenderExtensionDataObject(XCaseTableLayoutPanel propertyTableLayoutPanel, IDictionary dictionary, object dictionaryKeyObject, int p)
        {
            /* TODO: complete rendering of dictionary objects */
        }

        //private static void RenderInteger(XCaseTableLayoutPanel propertyTableLayoutPanel, IDictionary dictionary, object dictionaryKeyObject, int p)
        //{
        //    /* TODO: complete rendering of dictionary objects */
        //}

        //private static void RenderInt16(XCaseTableLayoutPanel propertyTableLayoutPanel, IDictionary dictionary, object dictionaryKeyObject, int p)
        //{
        //    /* TODO: complete rendering of dictionary objects */
        //}

        //private static void RenderInt64(XCaseTableLayoutPanel propertyTableLayoutPanel, IDictionary dictionary, object dictionaryKeyObject, int p)
        //{
        //    /* TODO: complete rendering of dictionary objects */
        //}

        //private static void RenderLong(XCaseTableLayoutPanel propertyTableLayoutPanel, IDictionary dictionary, object dictionaryKeyObject, int p)
        //{
        //    /* TODO: complete rendering of dictionary objects */
        //}

        //private static void RenderSingle(XCaseTableLayoutPanel propertyTableLayoutPanel, IDictionary dictionary, object dictionaryKeyObject, int p)
        //{
        //    /* TODO: complete rendering of dictionary objects */
        //}

        private static void RenderDictionaryKeyString(XCaseTableLayoutPanel propertyTableLayoutPanel, IDictionary dictionary, DictionaryEntry dictionaryEntry, int index)
        {
            Log.Debug("starting RenderString()");
            XCaseTextBox textBox = new XCaseTextBox();
            object dictionaryKeyObject = dictionaryEntry.Key;
            if (dictionaryKeyObject.GetType() == typeof(string))
            {
                textBox.Text = (string)dictionaryKeyObject;
                Log.Debug("set text to {0}", (string)dictionaryKeyObject);
            }
            else
            {

            }

            textBox.FieldType = dictionaryKeyObject.GetType();
            //Log.Debug("property type is {0}", parameterObject.GetType());
            propertyTableLayoutPanel.Controls.Add(textBox, 1, index + 1);
            textBox.TextChanged += delegate(object sender, EventArgs e)
            {
                //Log.DebugFormat("text box changed to {0}", textBox.Text);
                string value = textBox.Text;
                //int index = textBox.Index;
                Type fieldType = textBox.FieldType;
                //Log.Debug("set field type to {0}", fieldType);
                if (!fieldType.IsArray)
                {
                    //Log.Debug("field type is not array");
                    dictionary.Remove(dictionaryKeyObject);
                    dictionaryKeyObject = ObjectFactory.CreateObjectFromTypeAndValue(fieldType, value);
                    dictionary.Add(dictionaryKeyObject, dictionaryEntry.Value);
                }
                else
                {
                    //Log.Debug("property type is array");
                    string[] valueArray = value.Split();
                    Type elementType = fieldType.GetElementType();
                    Array objectArray = Array.CreateInstance(elementType, valueArray.Length);
                    Log.Debug("created object array of length {0}", valueArray.Length);
                    for (int k = 0; k < valueArray.Length; k++)
                    {
                        //Log.Debug("field type is array");
                        object objectValue = ObjectFactory.CreateObjectFromTypeAndValue(elementType, valueArray[k]);
                        objectArray.SetValue(objectValue, k);
                    }

                    dictionaryKeyObject = objectArray;
                }
            };
            Log.Debug("finishing RenderDictionaryKeyString()");
        }

        private static void RenderXmlDocument(XCaseTableLayoutPanel propertyTableLayoutPanel, IDictionary dictionary, object dictionaryKeyObject, int p)
        {
            /* TODO: complete rendering of dictionary objects */
        }

        private static TableLayoutPanel RenderDictionaryEntryObject(DictionaryEntry dictionaryEntry)
        {
            TableLayoutPanel dictionaryEntryTableLayoutPanel = new TableLayoutPanel();
            dictionaryEntryTableLayoutPanel.ColumnCount = 2;
            dictionaryEntryTableLayoutPanel.Dock = DockStyle.Fill;
            dictionaryEntryTableLayoutPanel.RowCount = 1;
            dictionaryEntryTableLayoutPanel.RowStyles.Add(new RowStyle(SizeType.Percent, 100F));
            dictionaryEntryTableLayoutPanel.Controls.Add(RenderDictionaryKeyObject(dictionaryEntry.Key), 0, 0);
            dictionaryEntryTableLayoutPanel.Controls.Add(RenderDictionaryValueObject(dictionaryEntry.Value), 1, 0);
            return dictionaryEntryTableLayoutPanel;
        }

        private static Control RenderDictionaryValueObject(object dictionaryValueObject)
        {
            Log.Debug("starting RenderDictionaryValueObject()");
            if (dictionaryValueObject == null)
            {
                Log.Debug("parameter object is null");
                return new XCaseTableLayoutPanel();
            }

            Log.Debug("starting RenderDictionaryValueObject() for " + dictionaryValueObject.GetType());
            XCaseTableLayoutPanel propertyTableLayoutPanel = new XCaseTableLayoutPanel();
            propertyTableLayoutPanel.PropertyObject = dictionaryValueObject;
            propertyTableLayoutPanel.CellBorderStyle = TableLayoutPanelCellBorderStyle.Single;
            propertyTableLayoutPanel.AutoSize = true;
            propertyTableLayoutPanel.ColumnCount = 2;
            propertyTableLayoutPanel.RowStyles.Add(new RowStyle(SizeType.Percent, 100F));
            if (ObjectFactory.IsArrayType(dictionaryValueObject.GetType()))
            {
                /* Parameter object is array */
                Log.Debug("parameter object is array");
                RenderArray(propertyTableLayoutPanel, null, dictionaryValueObject, 0);
            }
            else if (ObjectFactory.IsDictionaryType(dictionaryValueObject.GetType()))
            {
                /* Property type is not array and is Dictionary */
                Log.Debug("parameter object is Dictionary");
                RenderDictionary(propertyTableLayoutPanel, null, dictionaryValueObject, 0);
            }
            else if (ObjectFactory.IsIEnumerableType(dictionaryValueObject.GetType()))
            {
                /* Parameter object is IEnumerable */
                Log.Debug("parameter object is IEnumerable");
                RenderIEnumerable(propertyTableLayoutPanel, null, dictionaryValueObject, 0);
            }
            else if (ObjectFactory.IsBooleanType(dictionaryValueObject.GetType()))
            {
                /* Parameter type is not array and is Boolean */
                //Log.Debug("parameter type is not array and is Boolean");
                ((bool)dictionaryValueObject).RenderBoolean(propertyTableLayoutPanel, null, 0);
            }
            else if (ObjectFactory.IsByteType(dictionaryValueObject.GetType()))
            {
                /* Parameter type is not array and is Byte */
                //Log.Debug("parameter type is not array and is Byte");
                ((Byte)dictionaryValueObject).RenderByte(propertyTableLayoutPanel, null, 0);
            }
            else if (ObjectFactory.IsCharType(dictionaryValueObject.GetType()))
            {
                /* Property type is not array and is Char */
                //Log.Debug("parameter type is not array and is Char");
                ((Char)dictionaryValueObject).RenderChar(propertyTableLayoutPanel, null, 0);
            }
            else if (ObjectFactory.IsDateTimeType(dictionaryValueObject.GetType()))
            {
                /* Property object is not array and is (XCase)datetime */
                //Log.Debug("parameter type is not array and is (XCase)datetime");
                ((DateTime)dictionaryValueObject).RenderDateTime(propertyTableLayoutPanel, null, 0);
            }
            else if (ObjectFactory.IsDecimalType(dictionaryValueObject.GetType()))
            {
                /* Property type is not array and is Decimal */
                //Log.Debug("parameter type is not array and is Decimal");
                ((Decimal)dictionaryValueObject).RenderDecimal(propertyTableLayoutPanel, null, 0);
            }
            else if (ObjectFactory.IsDoubleType(dictionaryValueObject.GetType()))
            {
                /* Property type is not array and is Double */
                //Log.Debug("parameter type is not array and is Double");
                ((Double)dictionaryValueObject).RenderDouble(propertyTableLayoutPanel, null, 0);
            }
            else if (ObjectFactory.IsEnumType(dictionaryValueObject.GetType()))
            {
                /* Property object is not array and is enum */
                //Log.Debug("parameter type is not array and is Enum");
                ((Enum)dictionaryValueObject).RenderEnum(propertyTableLayoutPanel, null, 0);
            }
            else if (ObjectFactory.IsExtensionDataObjectType(dictionaryValueObject.GetType()))
            {
                /* Property object is not array and is ExtensionDataObject */
                //Log.Debug("parameter type is not array and is ExtensionDataObject");
                RenderExtensionDataObject(propertyTableLayoutPanel, null, dictionaryValueObject, 0);
            }
            else if (ObjectFactory.IsIntegerType(dictionaryValueObject.GetType()))
            {
                /* Property type is not array and is Integer */
                //Log.Debug("parameter type is not array and is Integer");
                ((int)dictionaryValueObject).RenderInteger(propertyTableLayoutPanel, null, 0);
            }
            else if (ObjectFactory.IsInt16Type(dictionaryValueObject.GetType()))
            {
                /* Property type is not array and is Int16 */
                //Log.Debug("parameter type is not array and is Int16");
                ((Int16)dictionaryValueObject).RenderInt16(propertyTableLayoutPanel, null, 0);
            }
            else if (ObjectFactory.IsInt64Type(dictionaryValueObject.GetType()))
            {
                /* Property type is not array and is Int64 */
                //Log.Debug("parameter type is not array and is Int64");
                ((Int64)dictionaryValueObject).RenderInt64(propertyTableLayoutPanel, null, 0);
            }
            else if (ObjectFactory.IsLongType(dictionaryValueObject.GetType()))
            {
                /* Property type is not array and is Long */
                //Log.Debug("parameter type is not array and is Long");
                ((long)dictionaryValueObject).RenderLong(propertyTableLayoutPanel, null, 0);
            }
            else if (ObjectFactory.IsSingleType(dictionaryValueObject.GetType()))
            {
                /* Property type is not array and is Single */
                //Log.Debug("parameter type is not array and is Single");
                ((Single)dictionaryValueObject).RenderSingle(propertyTableLayoutPanel, null, 0);
            }
            else if (ObjectFactory.IsStringType(dictionaryValueObject.GetType()))
            {
                /* Property type is not array and is string */
                //Log.Debug("parameter type is not array and is String");
                ((string)dictionaryValueObject).RenderString(propertyTableLayoutPanel, null, 0);
            }
            else if (ObjectFactory.IsXmlDocumentType(dictionaryValueObject.GetType()))
            {
                /* Property type is not array and is XmlDocument */
                //Log.Debug("parameter type is not array and is XmlDocument");
                RenderXmlDocument(propertyTableLayoutPanel, null, dictionaryValueObject, 0);
            }
            else
            {
                /* Property type is not array and is complex type */
                Log.Debug("parameter type is not array and is complex type: {0}", dictionaryValueObject.GetType());
                /* Iterate through each property of the parameter */
                PropertyInfo[] propertyInfoArray = dictionaryValueObject.GetType().GetProperties();
                Log.Debug("number of properties is {0}", propertyInfoArray.Length);
                propertyTableLayoutPanel.RowStyles.Add(new RowStyle(SizeType.Percent, 100F));
                propertyTableLayoutPanel.RowCount = propertyInfoArray.Length + 2;
                Label propertyNameLabel = new Label();
                propertyNameLabel.Text = "Name";
                propertyTableLayoutPanel.Controls.Add(propertyNameLabel, 0, 0);
                Label propertyValueLabel = new Label();
                propertyValueLabel.Text = "Value";
                propertyTableLayoutPanel.Controls.Add(propertyValueLabel, 1, 0);
                for (int j = 0; j < propertyInfoArray.Length; j++)
                {
                    //Log.Debug("property name is {0}", propertyInfoArray[j].Name);
                    Label propertyLabel = new Label();
                    propertyLabel.AutoSize = true;
                    propertyLabel.Text = propertyInfoArray[j].Name;
                    propertyTableLayoutPanel.Controls.Add(propertyLabel, 0, j + 1);
                    Type propertyType = propertyInfoArray[j].PropertyType;
                    //Log.Debug("property type is {0}", propertyType);
                    object propertyTypeObject = null;
                    if (propertyInfoArray[j].GetValue(dictionaryValueObject, null) == null)
                    {
                        //Log.Debug("property value is null");
                        propertyTypeObject = ObjectFactory.CreateDefaultObject(propertyType);
                        //Log.Debug("created default property type object of type {0}", propertyType);
                        propertyInfoArray[j].SetValue(dictionaryValueObject, propertyTypeObject, null);
                        //Log.Debug("set {0} to propertyTypeObject", propertyInfoArray[j].Name);
                    }
                    else
                    {
                        //Log.Debug("property value is not null");
                        propertyTypeObject = propertyInfoArray[j].GetValue(dictionaryValueObject, null);
                        //Log.Debug("propertyTypeObject is " + propertyTypeObject);
                    }

                    if (ObjectFactory.IsArrayType(propertyType))
                    {
                        //Log.Debug("property type is complex type and is array");
                        RenderArrayProperty(propertyTableLayoutPanel, dictionaryValueObject, propertyInfoArray, propertyTypeObject, j);
                    }
                    else if (ObjectFactory.IsBooleanType(propertyType))
                    {
                        //Log.Debug("property type is complex type and is boolean");
                        ((bool)propertyTypeObject).RenderBooleanProperty(propertyTableLayoutPanel, dictionaryValueObject, propertyInfoArray, j);
                    }
                    else if (ObjectFactory.IsByteType(propertyType))
                    {
                        //Log.Debug("property type is complex type and is Byte");
                        ((Byte)propertyTypeObject).RenderByteProperty(propertyTableLayoutPanel, dictionaryValueObject, propertyInfoArray, j);
                    }
                    else if (ObjectFactory.IsCharType(propertyType))
                    {
                        /* Property type is complex type and is Char */
                        //Log.Debug("property type is complex type and is Char");
                        ((Char)propertyTypeObject).RenderCharProperty(propertyTableLayoutPanel, dictionaryValueObject, propertyInfoArray, j);
                    }
                    else if (ObjectFactory.IsDateTimeType(propertyType))
                    {
                        /* Property type is complex type and is DateTime */
                        //Log.Debug("property type is complex type and is DateTime");
                        ((DateTime)propertyTypeObject).RenderDateTimeProperty(propertyTableLayoutPanel, dictionaryValueObject, propertyInfoArray, j);
                    }
                    else if (ObjectFactory.IsDecimalType(propertyType))
                    {
                        /* Property type is complex type and is Decimal */
                        //Log.Debug("property type is complex type and is Decimal");
                        ((Decimal)propertyTypeObject).RenderDecimalProperty(propertyTableLayoutPanel, dictionaryValueObject, propertyInfoArray, j);
                    }
                    else if (ObjectFactory.IsDictionaryType(propertyType))
                    {
                        /* Property type is complex type and is Dictionary */
                        //Log.Debug("property type is complex type and is Dictionary");
                        RenderDictionaryProperty(propertyTableLayoutPanel, dictionaryValueObject, propertyInfoArray, propertyTypeObject, j);
                    }
                    else if (ObjectFactory.IsDoubleType(propertyType))
                    {
                        /* Property type is complex type and is Double */
                        //Log.Debug("property type is complex type and is Double");
                        ((Double)propertyTypeObject).RenderDoubleProperty(propertyTableLayoutPanel, dictionaryValueObject, propertyInfoArray, j);
                    }
                    else if (ObjectFactory.IsEnumType(propertyType))
                    {
                        /* Property type is complex type and is enum */
                        //Log.Debug("property type is complex type and is enum");
                        ((Enum)propertyTypeObject).RenderEnumProperty(propertyTableLayoutPanel, dictionaryValueObject, propertyInfoArray, j);
                    }
                    else if (ObjectFactory.IsIntegerType(propertyType))
                    {
                        /* Property type is complex type and is integer */
                        //Log.Debug("property type is complex type and is integer");
                        ((int)propertyTypeObject).RenderIntegerProperty(propertyTableLayoutPanel, dictionaryValueObject, propertyInfoArray, j);
                    }
                    else if (ObjectFactory.IsInt64Type(propertyType))
                    {
                        /* Property type is complex type and is Int64 */
                        //Log.Debug("property type is complex type and is Int64");
                        ((Int64)propertyTypeObject).RenderInt64Property(propertyTableLayoutPanel, dictionaryValueObject, propertyInfoArray, j);
                    }
                    else if (ObjectFactory.IsLongType(propertyType))
                    {
                        /* Property type is complex type and is Long */
                        //Log.Debug("property type is complex type and is Long");
                        ((long)propertyTypeObject).RenderLongProperty(propertyTableLayoutPanel, dictionaryValueObject, propertyInfoArray, j);
                    }
                    else if (ObjectFactory.IsSingleType(propertyType))
                    {
                        /* Property type is complex type and is Single */
                        //Log.Debug("property type is complex type and is Single");
                        ((Single)propertyTypeObject).RenderSingleProperty(propertyTableLayoutPanel, dictionaryValueObject, propertyInfoArray, j);
                    }
                    else if (ObjectFactory.IsStringType(propertyType))
                    {
                        /* Property type is complex type and is String */
                        //Log.Debug("property type is complex type and is String");
                        ((string)propertyTypeObject).RenderStringProperty(propertyTableLayoutPanel, dictionaryValueObject, propertyInfoArray, j);
                    }
                    else if (ObjectFactory.IsNullableType(propertyType))
                    {
                        //Log.Debug("property type is nullable");
                        Type underlyingPropertyType = Nullable.GetUnderlyingType(propertyType);
                        if (ObjectFactory.IsBooleanType(underlyingPropertyType))
                        {
                            //Log.Debug("underlying property type is nullable boolean");
                            XCaseCheckBox checkBox = new XCaseCheckBox();
                            checkBox.Index = j;
                            checkBox.FieldType = propertyType;
                            propertyTableLayoutPanel.Controls.Add(checkBox, 1, j + 1);
                            checkBox.CheckedChanged += delegate(object sender, EventArgs e)
                            {
                                bool value = checkBox.Checked;
                                int index = checkBox.Index;
                                Type fieldType = checkBox.FieldType;
                                Type underlyingFieldType = Nullable.GetUnderlyingType(fieldType);
                                //Log.Debug("property type is boolean");
                                //Log.Debug("about to create object for boolean");
                                object underlyingPropertyTypeObject = ObjectFactory.CreateObjectFromTypeAndValue(underlyingFieldType, value);
                                propertyTypeObject = Activator.CreateInstance(fieldType, underlyingPropertyTypeObject);
                                propertyInfoArray[index].SetValue(dictionaryValueObject, propertyTypeObject, null);
                            };
                        }
                        else if (ObjectFactory.IsByteType(underlyingPropertyType))
                        {
                            //Log.Debug("underlying property type is nullable Byte");
                            XCaseTextBox textBox = new XCaseTextBox();
                            textBox.Index = j;
                            textBox.FieldType = propertyType;
                            //Log.Debug("property type is " + propertyType);
                            propertyTableLayoutPanel.Controls.Add(textBox, 1, j + 1);
                            textBox.TextChanged += delegate(object sender, EventArgs e)
                            {
                                string value = textBox.Text;
                                int index = textBox.Index;
                                Type fieldType = textBox.FieldType;
                                Type underlyingFieldType = Nullable.GetUnderlyingType(fieldType);
                                //Log.Debug("about to create object for nullable string");
                                object underlyingPropertyTypeObject = ObjectFactory.CreateObjectFromTypeAndValue(underlyingFieldType, value);
                                propertyTypeObject = Activator.CreateInstance(fieldType, underlyingPropertyTypeObject);
                                propertyInfoArray[index].SetValue(dictionaryValueObject, propertyTypeObject, null);
                            };
                        }
                        else if (ObjectFactory.IsCharType(underlyingPropertyType))
                        {
                            //Log.Debug("underlying property type is nullable Char");
                            XCaseTextBox textBox = new XCaseTextBox();
                            textBox.Index = j;
                            textBox.FieldType = propertyType;
                            //Log.Debug("property type is " + propertyType);
                            propertyTableLayoutPanel.Controls.Add(textBox, 1, j + 1);
                            textBox.TextChanged += delegate(object sender, EventArgs e)
                            {
                                string value = textBox.Text;
                                int index = textBox.Index;
                                Type fieldType = textBox.FieldType;
                                Type underlyingFieldType = Nullable.GetUnderlyingType(fieldType);
                                //Log.Debug("about to create object for nullable string");
                                object underlyingPropertyTypeObject = ObjectFactory.CreateObjectFromTypeAndValue(underlyingFieldType, value);
                                propertyTypeObject = Activator.CreateInstance(fieldType, underlyingPropertyTypeObject);
                                propertyInfoArray[index].SetValue(dictionaryValueObject, propertyTypeObject, null);
                            };
                        }
                        else if (ObjectFactory.IsDateTimeType(underlyingPropertyType))
                        {
                            //Log.Debug("underlying property type is nullable datetime");
                            XCaseDateTimePicker dateTimePicker = new XCaseDateTimePicker();
                            dateTimePicker.Index = j;
                            dateTimePicker.FieldType = propertyType;
                            //Log.Debug("property type is " + propertyType);
                            propertyTableLayoutPanel.Controls.Add(dateTimePicker, 1, j + 1);
                            dateTimePicker.ValueChanged += delegate(object sender, EventArgs e)
                            {
                                DateTime value = dateTimePicker.Value;
                                //Log.Debug("DateTimePicker value is " + value);
                                int index = dateTimePicker.Index;
                                Type fieldType = dateTimePicker.FieldType;
                                Type underlyingFieldType = Nullable.GetUnderlyingType(fieldType);
                                //Log.Debug("about to create object for nullable datetime");
                                object underlyingPropertyTypeObject = ObjectFactory.CreateObjectFromTypeAndValue(underlyingFieldType, value);
                                propertyTypeObject = Activator.CreateInstance(fieldType, underlyingPropertyTypeObject);
                                propertyInfoArray[index].SetValue(dictionaryValueObject, propertyTypeObject, null);
                            };
                        }
                        else if (ObjectFactory.IsDecimalType(underlyingPropertyType))
                        {
                            //Log.Debug("underlying property type is nullable Decimal");
                            XCaseTextBox textBox = new XCaseTextBox();
                            textBox.Index = j;
                            textBox.FieldType = propertyType;
                            //Log.Debug("property type is " + propertyType);
                            propertyTableLayoutPanel.Controls.Add(textBox, 1, j + 1);
                            textBox.TextChanged += delegate(object sender, EventArgs e)
                            {
                                Decimal value = Decimal.Zero;
                                bool parse = Decimal.TryParse(textBox.Text, out value);
                                int index = textBox.Index;
                                Type fieldType = textBox.FieldType;
                                Type underlyingFieldType = Nullable.GetUnderlyingType(fieldType);
                                //Log.Debug("about to create object for nullable string");
                                object underlyingPropertyTypeObject = ObjectFactory.CreateObjectFromTypeAndValue(underlyingFieldType, value);
                                propertyTypeObject = Activator.CreateInstance(fieldType, underlyingPropertyTypeObject);
                                propertyInfoArray[index].SetValue(dictionaryValueObject, propertyTypeObject, null);
                            };
                        }
                        else if (ObjectFactory.IsDoubleType(underlyingPropertyType))
                        {
                            //Log.Debug("underlying property type is nullable Double");
                            XCaseTextBox textBox = new XCaseTextBox();
                            textBox.Index = j;
                            textBox.FieldType = propertyType;
                            //Log.Debug("property type is " + propertyType);
                            propertyTableLayoutPanel.Controls.Add(textBox, 1, j + 1);
                            textBox.TextChanged += delegate(object sender, EventArgs e)
                            {
                                Double value = 0.0;
                                Double.TryParse(textBox.Text, out value);
                                int index = textBox.Index;
                                Type fieldType = textBox.FieldType;
                                Type underlyingFieldType = Nullable.GetUnderlyingType(fieldType);
                                //Log.Debug("about to create object for nullable string");
                                object underlyingPropertyTypeObject = ObjectFactory.CreateObjectFromTypeAndValue(underlyingFieldType, value);
                                propertyTypeObject = Activator.CreateInstance(fieldType, underlyingPropertyTypeObject);
                                propertyInfoArray[index].SetValue(dictionaryValueObject, propertyTypeObject, null);
                            };
                        }
                        else if (underlyingPropertyType.IsEnum)
                        {
                            //Log.Debug("underlying property type is nullable enum");
                            XCaseComboBox comboBox = new XCaseComboBox();
                            comboBox.Index = j;
                            comboBox.FieldType = propertyType;
                            comboBox.DataSource = Enum.GetValues(underlyingPropertyType);
                            propertyTableLayoutPanel.Controls.Add(comboBox, 1, j + 1);
                            comboBox.SelectedIndexChanged += delegate(object sender, EventArgs e)
                            {
                                Enum value = (Enum)comboBox.SelectedValue;
                                int index = comboBox.Index;
                                Type fieldType = comboBox.FieldType;
                                Type underlyingFieldType = Nullable.GetUnderlyingType(fieldType);
                                //Log.Debug("about to create object for nullable enum");
                                object underlyingPropertyTypeObject = ObjectFactory.CreateObjectFromTypeAndValue(underlyingFieldType, value);
                                propertyTypeObject = Activator.CreateInstance(fieldType, underlyingPropertyTypeObject);
                                propertyInfoArray[index].SetValue(dictionaryValueObject, propertyTypeObject, null);
                            };
                        }
                        else if (ObjectFactory.IsInt64Type(underlyingPropertyType))
                        {
                            Log.Debug("underlying property type is nullable Int64");
                            XCaseTextBox textBox = new XCaseTextBox();
                            textBox.Index = j;
                            textBox.FieldType = propertyType;
                            propertyTableLayoutPanel.Controls.Add(textBox, 1, j + 1);
                            textBox.TextChanged += delegate(object sender, EventArgs e)
                            {
                                Int64 value = 0;
                                Int64.TryParse(textBox.Text, out value);
                                int index = textBox.Index;
                                Type fieldType = textBox.FieldType;
                                Type underlyingFieldType = Nullable.GetUnderlyingType(fieldType);
                                Log.Debug("about to create object for nullable long");
                                object underlyingPropertyTypeObject = ObjectFactory.CreateInt64ObjectFromTypeAndValue(underlyingFieldType, value);
                                propertyTypeObject = Activator.CreateInstance(fieldType, underlyingPropertyTypeObject);
                                propertyInfoArray[index].SetValue(dictionaryValueObject, propertyTypeObject, null);
                            };
                        }
                        else if (ObjectFactory.IsLongType(underlyingPropertyType))
                        {
                            //Log.Debug("underlying property type is nullable Long");
                            XCaseTextBox textBox = new XCaseTextBox();
                            textBox.Index = j;
                            textBox.FieldType = propertyType;
                            propertyTableLayoutPanel.Controls.Add(textBox, 1, j + 1);
                            textBox.TextChanged += delegate(object sender, EventArgs e)
                            {
                                long value = 0;
                                long.TryParse(textBox.Text, out value);
                                int index = textBox.Index;
                                Type fieldType = textBox.FieldType;
                                Type underlyingFieldType = Nullable.GetUnderlyingType(fieldType);
                                Log.Debug("about to create object for nullable long");
                                object underlyingPropertyTypeObject = ObjectFactory.CreateInt64ObjectFromTypeAndValue(underlyingFieldType, value);
                                propertyTypeObject = Activator.CreateInstance(fieldType, underlyingPropertyTypeObject);
                                propertyInfoArray[index].SetValue(dictionaryValueObject, propertyTypeObject, null);
                            };
                        }
                        else if (ObjectFactory.IsSingleType(underlyingPropertyType))
                        {
                            //Log.Debug("underlying property type is nullable Single");
                            XCaseTextBox textBox = new XCaseTextBox();
                            textBox.Index = j;
                            textBox.FieldType = propertyType;
                            //Log.Debug("property type is " + propertyType);
                            propertyTableLayoutPanel.Controls.Add(textBox, 1, j + 1);
                            textBox.TextChanged += delegate(object sender, EventArgs e)
                            {
                                Single value = 0;
                                Single.TryParse(textBox.Text, out value);
                                int index = textBox.Index;
                                Type fieldType = textBox.FieldType;
                                Type underlyingFieldType = Nullable.GetUnderlyingType(fieldType);
                                //Log.Debug("about to create object for nullable string");
                                object underlyingPropertyTypeObject = ObjectFactory.CreateObjectFromTypeAndValue(underlyingFieldType, value);
                                propertyTypeObject = Activator.CreateInstance(fieldType, underlyingPropertyTypeObject);
                                propertyInfoArray[index].SetValue(dictionaryValueObject, propertyTypeObject, null);
                            };
                        }
                        else if (ObjectFactory.IsStringType(underlyingPropertyType))
                        {
                            //Log.Debug("underlying property type is nullable String");
                            XCaseTextBox textBox = new XCaseTextBox();
                            textBox.Index = j;
                            textBox.FieldType = propertyType;
                            //Log.Debug("property type is " + propertyType);
                            propertyTableLayoutPanel.Controls.Add(textBox, 1, j + 1);
                            textBox.TextChanged += delegate(object sender, EventArgs e)
                            {
                                string value = textBox.Text;
                                int index = textBox.Index;
                                Type fieldType = textBox.FieldType;
                                Type underlyingFieldType = Nullable.GetUnderlyingType(fieldType);
                                //Log.Debug("about to create object for nullable string");
                                object underlyingPropertyTypeObject = ObjectFactory.CreateObjectFromTypeAndValue(underlyingFieldType, value);
                                propertyTypeObject = Activator.CreateInstance(fieldType, underlyingPropertyTypeObject);
                                propertyInfoArray[index].SetValue(dictionaryValueObject, propertyTypeObject, null);
                            };
                        }
                        else
                        {
                            //Log.Debug("property type is nullable complex");
                        }
                    }
                    else
                    {
                        //Log.Debug("property type is " + propertyTypeObject.GetType());
                        TableLayoutPanel childTableLayoutPanel = RenderParameterObject(propertyTypeObject);
                        propertyTableLayoutPanel.Controls.Add(childTableLayoutPanel, 1, j + 1);
                        //Log.Debug("added child table layout panel for " + propertyInfoArray[j].Name + " on row " + (j + 1));
                    }
                }
            }

            propertyTableLayoutPanel.PerformLayout();
            propertyTableLayoutPanel.Dock = DockStyle.Fill;
            //Log.Debug("finishing renderPropertyObject for " + parameterObject.GetType());
            return propertyTableLayoutPanel;
        }

        private static Control RenderDictionaryKeyObject(object dictionaryKeyObject)
        {
            Log.Debug("starting RenderDictionaryKeyObject()");
            if (dictionaryKeyObject == null)
            {
                Log.Debug("parameter object is null");
                return new XCaseTableLayoutPanel();
            }

            Log.Debug("starting RenderDictionaryKeyObject() for " + dictionaryKeyObject.GetType());
            XCaseTableLayoutPanel propertyTableLayoutPanel = new XCaseTableLayoutPanel();
            propertyTableLayoutPanel.PropertyObject = dictionaryKeyObject;
            propertyTableLayoutPanel.CellBorderStyle = TableLayoutPanelCellBorderStyle.Single;
            propertyTableLayoutPanel.AutoSize = true;
            propertyTableLayoutPanel.ColumnCount = 2;
            propertyTableLayoutPanel.RowStyles.Add(new RowStyle(SizeType.Percent, 100F));
            if (ObjectFactory.IsArrayType(dictionaryKeyObject.GetType()))
            {
                /* Parameter object is array */
                Log.Debug("parameter object is array");
                RenderArray(propertyTableLayoutPanel, null, dictionaryKeyObject, 0);
            }
            else if (ObjectFactory.IsDictionaryType(dictionaryKeyObject.GetType()))
            {
                /* Property type is not array and is Dictionary */
                Log.Debug("parameter object is Dictionary");
                RenderDictionary(propertyTableLayoutPanel, null, dictionaryKeyObject, 0);
            }
            else if (ObjectFactory.IsIEnumerableType(dictionaryKeyObject.GetType()))
            {
                /* Parameter object is IEnumerable */
                Log.Debug("parameter object is IEnumerable");
                RenderIEnumerable(propertyTableLayoutPanel, null, dictionaryKeyObject, 0);
            }
            else if (ObjectFactory.IsBooleanType(dictionaryKeyObject.GetType()))
            {
                /* Parameter type is not array and is Boolean */
                //Log.Debug("parameter type is not array and is Boolean");
                ((bool)dictionaryKeyObject).RenderBoolean(propertyTableLayoutPanel, null, 0);
            }
            else if (ObjectFactory.IsByteType(dictionaryKeyObject.GetType()))
            {
                /* Parameter type is not array and is Byte */
                //Log.Debug("parameter type is not array and is Byte");
                ((Byte)dictionaryKeyObject).RenderByte(propertyTableLayoutPanel, null, 0);
            }
            else if (ObjectFactory.IsCharType(dictionaryKeyObject.GetType()))
            {
                /* Property type is not array and is Char */
                //Log.Debug("parameter type is not array and is Char");
                ((Char)dictionaryKeyObject).RenderChar(propertyTableLayoutPanel, null, 0);
            }
            else if (ObjectFactory.IsDateTimeType(dictionaryKeyObject.GetType()))
            {
                /* Property object is not array and is (XCase)datetime */
                //Log.Debug("parameter type is not array and is (XCase)datetime");
                ((DateTime)dictionaryKeyObject).RenderDateTime(propertyTableLayoutPanel, null, 0);
            }
            else if (ObjectFactory.IsDecimalType(dictionaryKeyObject.GetType()))
            {
                /* Property type is not array and is Decimal */
                //Log.Debug("parameter type is not array and is Decimal");
                ((Decimal)dictionaryKeyObject).RenderDecimal(propertyTableLayoutPanel, null, 0);
            }
            else if (ObjectFactory.IsDoubleType(dictionaryKeyObject.GetType()))
            {
                /* Property type is not array and is Double */
                //Log.Debug("parameter type is not array and is Double");
                ((Double)dictionaryKeyObject).RenderDouble(propertyTableLayoutPanel, null, 0);
            }
            else if (ObjectFactory.IsEnumType(dictionaryKeyObject.GetType()))
            {
                /* Property object is not array and is enum */
                //Log.Debug("parameter type is not array and is Enum");
                ((Enum)dictionaryKeyObject).RenderEnum(propertyTableLayoutPanel, null, 0);
            }
            else if (ObjectFactory.IsExtensionDataObjectType(dictionaryKeyObject.GetType()))
            {
                /* Property object is not array and is ExtensionDataObject */
                //Log.Debug("parameter type is not array and is ExtensionDataObject");
                RenderExtensionDataObject(propertyTableLayoutPanel, null, dictionaryKeyObject, 0);
            }
            else if (ObjectFactory.IsIntegerType(dictionaryKeyObject.GetType()))
            {
                /* Property type is not array and is Integer */
                //Log.Debug("parameter type is not array and is Integer");
                ((int)dictionaryKeyObject).RenderInteger(propertyTableLayoutPanel, null, 0);
            }
            else if (ObjectFactory.IsInt16Type(dictionaryKeyObject.GetType()))
            {
                /* Property type is not array and is Int16 */
                //Log.Debug("parameter type is not array and is Int16");
                ((Int16)dictionaryKeyObject).RenderInt16(propertyTableLayoutPanel, null, 0);
            }
            else if (ObjectFactory.IsInt64Type(dictionaryKeyObject.GetType()))
            {
                /* Property type is not array and is Int64 */
                //Log.Debug("parameter type is not array and is Int64");
                ((Int64)dictionaryKeyObject).RenderInt64(propertyTableLayoutPanel, null, 0);
            }
            else if (ObjectFactory.IsLongType(dictionaryKeyObject.GetType()))
            {
                /* Property type is not array and is Long */
                //Log.Debug("parameter type is not array and is Long");
                ((long)dictionaryKeyObject).RenderLong(propertyTableLayoutPanel, null, 0);
            }
            else if (ObjectFactory.IsSingleType(dictionaryKeyObject.GetType()))
            {
                /* Property type is not array and is Single */
                //Log.Debug("parameter type is not array and is Single");
                ((Single)dictionaryKeyObject).RenderSingle(propertyTableLayoutPanel, null, 0);
            }
            else if (ObjectFactory.IsStringType(dictionaryKeyObject.GetType()))
            {
                /* Property type is not array and is string */
                //Log.Debug("parameter type is not array and is String");
                ((string)dictionaryKeyObject).RenderString(propertyTableLayoutPanel, null, 0);
            }
            else if (ObjectFactory.IsXmlDocumentType(dictionaryKeyObject.GetType()))
            {
                /* Property type is not array and is XmlDocument */
                //Log.Debug("parameter type is not array and is XmlDocument");
                RenderXmlDocument(propertyTableLayoutPanel, null, dictionaryKeyObject, 0);
            }
            else
            {
                /* Property type is not array and is complex type */
                Log.Debug("parameter type is not array and is complex type: {0}", dictionaryKeyObject.GetType());
                /* Iterate through each property of the parameter */
                PropertyInfo[] propertyInfoArray = dictionaryKeyObject.GetType().GetProperties();
                Log.Debug("number of properties is {0}", propertyInfoArray.Length);
                propertyTableLayoutPanel.RowStyles.Add(new RowStyle(SizeType.Percent, 100F));
                propertyTableLayoutPanel.RowCount = propertyInfoArray.Length + 2;
                Label propertyNameLabel = new Label();
                propertyNameLabel.Text = "Name";
                propertyTableLayoutPanel.Controls.Add(propertyNameLabel, 0, 0);
                Label propertyValueLabel = new Label();
                propertyValueLabel.Text = "Value";
                propertyTableLayoutPanel.Controls.Add(propertyValueLabel, 1, 0);
                for (int j = 0; j < propertyInfoArray.Length; j++)
                {
                    //Log.Debug("property name is {0}", propertyInfoArray[j].Name);
                    Label propertyLabel = new Label();
                    propertyLabel.AutoSize = true;
                    propertyLabel.Text = propertyInfoArray[j].Name;
                    propertyTableLayoutPanel.Controls.Add(propertyLabel, 0, j + 1);
                    Type propertyType = propertyInfoArray[j].PropertyType;
                    //Log.Debug("property type is {0}", propertyType);
                    object propertyTypeObject = null;
                    if (propertyInfoArray[j].GetValue(dictionaryKeyObject, null) == null)
                    {
                        //Log.Debug("property value is null");
                        propertyTypeObject = ObjectFactory.CreateDefaultObject(propertyType);
                        //Log.Debug("created default property type object of type {0}", propertyType);
                        propertyInfoArray[j].SetValue(dictionaryKeyObject, propertyTypeObject, null);
                        //Log.Debug("set {0} to propertyTypeObject", propertyInfoArray[j].Name);
                    }
                    else
                    {
                        //Log.Debug("property value is not null");
                        propertyTypeObject = propertyInfoArray[j].GetValue(dictionaryKeyObject, null);
                        //Log.Debug("propertyTypeObject is " + propertyTypeObject);
                    }

                    if (ObjectFactory.IsArrayType(propertyType))
                    {
                        //Log.Debug("property type is complex type and is array");
                        RenderArrayProperty(propertyTableLayoutPanel, dictionaryKeyObject, propertyInfoArray, propertyTypeObject, j);
                    }
                    else if (ObjectFactory.IsBooleanType(propertyType))
                    {
                        //Log.Debug("property type is complex type and is boolean");
                        ((bool)propertyTypeObject).RenderBooleanProperty(propertyTableLayoutPanel, dictionaryKeyObject, propertyInfoArray, j);
                    }
                    else if (ObjectFactory.IsByteType(propertyType))
                    {
                        //Log.Debug("property type is complex type and is Byte");
                        ((Byte)propertyTypeObject).RenderByteProperty(propertyTableLayoutPanel, dictionaryKeyObject, propertyInfoArray, j);
                    }
                    else if (ObjectFactory.IsCharType(propertyType))
                    {
                        /* Property type is complex type and is Char */
                        //Log.Debug("property type is complex type and is Char");
                        ((Char)propertyTypeObject).RenderCharProperty(propertyTableLayoutPanel, dictionaryKeyObject, propertyInfoArray, j);
                    }
                    else if (ObjectFactory.IsDateTimeType(propertyType))
                    {
                        /* Property type is complex type and is DateTime */
                        //Log.Debug("property type is complex type and is DateTime");
                        ((DateTime)propertyTypeObject).RenderDateTimeProperty(propertyTableLayoutPanel, dictionaryKeyObject, propertyInfoArray, j);
                    }
                    else if (ObjectFactory.IsDecimalType(propertyType))
                    {
                        /* Property type is complex type and is Decimal */
                        //Log.Debug("property type is complex type and is Decimal");
                        ((Decimal)propertyTypeObject).RenderDecimalProperty(propertyTableLayoutPanel, dictionaryKeyObject, propertyInfoArray, j);
                    }
                    else if (ObjectFactory.IsDictionaryType(propertyType))
                    {
                        /* Property type is complex type and is Dictionary */
                        //Log.Debug("property type is complex type and is Dictionary");
                        RenderDictionaryProperty(propertyTableLayoutPanel, dictionaryKeyObject, propertyInfoArray, propertyTypeObject, j);
                    }
                    else if (ObjectFactory.IsDoubleType(propertyType))
                    {
                        /* Property type is complex type and is Double */
                        //Log.Debug("property type is complex type and is Double");
                        ((Double)propertyTypeObject).RenderDoubleProperty(propertyTableLayoutPanel, dictionaryKeyObject, propertyInfoArray, j);
                    }
                    else if (ObjectFactory.IsEnumType(propertyType))
                    {
                        /* Property type is complex type and is enum */
                        //Log.Debug("property type is complex type and is enum");
                        ((Enum)propertyTypeObject).RenderEnumProperty(propertyTableLayoutPanel, dictionaryKeyObject, propertyInfoArray, j);
                    }
                    else if (ObjectFactory.IsIntegerType(propertyType))
                    {
                        /* Property type is complex type and is integer */
                        //Log.Debug("property type is complex type and is integer");
                        ((int)propertyTypeObject).RenderIntegerProperty(propertyTableLayoutPanel, dictionaryKeyObject, propertyInfoArray, j);
                    }
                    else if (ObjectFactory.IsInt64Type(propertyType))
                    {
                        /* Property type is complex type and is Int64 */
                        //Log.Debug("property type is complex type and is Int64");
                        ((Int64)propertyTypeObject).RenderInt64Property(propertyTableLayoutPanel, dictionaryKeyObject, propertyInfoArray, j);
                    }
                    else if (ObjectFactory.IsLongType(propertyType))
                    {
                        /* Property type is complex type and is Long */
                        //Log.Debug("property type is complex type and is Long");
                        ((long)propertyTypeObject).RenderLongProperty(propertyTableLayoutPanel, dictionaryKeyObject, propertyInfoArray, j);
                    }
                    else if (ObjectFactory.IsSingleType(propertyType))
                    {
                        /* Property type is complex type and is Single */
                        //Log.Debug("property type is complex type and is Single");
                        ((Single)propertyTypeObject).RenderSingleProperty(propertyTableLayoutPanel, dictionaryKeyObject, propertyInfoArray, j);
                    }
                    else if (ObjectFactory.IsStringType(propertyType))
                    {
                        /* Property type is complex type and is String */
                        //Log.Debug("property type is complex type and is String");
                        ((string)propertyTypeObject).RenderStringProperty(propertyTableLayoutPanel, dictionaryKeyObject, propertyInfoArray, j);
                    }
                    else if (ObjectFactory.IsNullableType(propertyType))
                    {
                        //Log.Debug("property type is nullable");
                        Type underlyingPropertyType = Nullable.GetUnderlyingType(propertyType);
                        if (ObjectFactory.IsBooleanType(underlyingPropertyType))
                        {
                            //Log.Debug("underlying property type is nullable boolean");
                            XCaseCheckBox checkBox = new XCaseCheckBox();
                            checkBox.Index = j;
                            checkBox.FieldType = propertyType;
                            propertyTableLayoutPanel.Controls.Add(checkBox, 1, j + 1);
                            checkBox.CheckedChanged += delegate(object sender, EventArgs e)
                            {
                                bool value = checkBox.Checked;
                                int index = checkBox.Index;
                                Type fieldType = checkBox.FieldType;
                                Type underlyingFieldType = Nullable.GetUnderlyingType(fieldType);
                                //Log.Debug("property type is boolean");
                                //Log.Debug("about to create object for boolean");
                                object underlyingPropertyTypeObject = ObjectFactory.CreateObjectFromTypeAndValue(underlyingFieldType, value);
                                propertyTypeObject = Activator.CreateInstance(fieldType, underlyingPropertyTypeObject);
                                propertyInfoArray[index].SetValue(dictionaryKeyObject, propertyTypeObject, null);
                            };
                        }
                        else if (ObjectFactory.IsByteType(underlyingPropertyType))
                        {
                            //Log.Debug("underlying property type is nullable Byte");
                            XCaseTextBox textBox = new XCaseTextBox();
                            textBox.Index = j;
                            textBox.FieldType = propertyType;
                            //Log.Debug("property type is " + propertyType);
                            propertyTableLayoutPanel.Controls.Add(textBox, 1, j + 1);
                            textBox.TextChanged += delegate(object sender, EventArgs e)
                            {
                                string value = textBox.Text;
                                int index = textBox.Index;
                                Type fieldType = textBox.FieldType;
                                Type underlyingFieldType = Nullable.GetUnderlyingType(fieldType);
                                //Log.Debug("about to create object for nullable string");
                                object underlyingPropertyTypeObject = ObjectFactory.CreateObjectFromTypeAndValue(underlyingFieldType, value);
                                propertyTypeObject = Activator.CreateInstance(fieldType, underlyingPropertyTypeObject);
                                propertyInfoArray[index].SetValue(dictionaryKeyObject, propertyTypeObject, null);
                            };
                        }
                        else if (ObjectFactory.IsCharType(underlyingPropertyType))
                        {
                            //Log.Debug("underlying property type is nullable Char");
                            XCaseTextBox textBox = new XCaseTextBox();
                            textBox.Index = j;
                            textBox.FieldType = propertyType;
                            //Log.Debug("property type is " + propertyType);
                            propertyTableLayoutPanel.Controls.Add(textBox, 1, j + 1);
                            textBox.TextChanged += delegate(object sender, EventArgs e)
                            {
                                string value = textBox.Text;
                                int index = textBox.Index;
                                Type fieldType = textBox.FieldType;
                                Type underlyingFieldType = Nullable.GetUnderlyingType(fieldType);
                                //Log.Debug("about to create object for nullable string");
                                object underlyingPropertyTypeObject = ObjectFactory.CreateObjectFromTypeAndValue(underlyingFieldType, value);
                                propertyTypeObject = Activator.CreateInstance(fieldType, underlyingPropertyTypeObject);
                                propertyInfoArray[index].SetValue(dictionaryKeyObject, propertyTypeObject, null);
                            };
                        }
                        else if (ObjectFactory.IsDateTimeType(underlyingPropertyType))
                        {
                            //Log.Debug("underlying property type is nullable datetime");
                            XCaseDateTimePicker dateTimePicker = new XCaseDateTimePicker();
                            dateTimePicker.Index = j;
                            dateTimePicker.FieldType = propertyType;
                            //Log.Debug("property type is " + propertyType);
                            propertyTableLayoutPanel.Controls.Add(dateTimePicker, 1, j + 1);
                            dateTimePicker.ValueChanged += delegate(object sender, EventArgs e)
                            {
                                DateTime value = dateTimePicker.Value;
                                //Log.Debug("DateTimePicker value is " + value);
                                int index = dateTimePicker.Index;
                                Type fieldType = dateTimePicker.FieldType;
                                Type underlyingFieldType = Nullable.GetUnderlyingType(fieldType);
                                //Log.Debug("about to create object for nullable datetime");
                                object underlyingPropertyTypeObject = ObjectFactory.CreateObjectFromTypeAndValue(underlyingFieldType, value);
                                propertyTypeObject = Activator.CreateInstance(fieldType, underlyingPropertyTypeObject);
                                propertyInfoArray[index].SetValue(dictionaryKeyObject, propertyTypeObject, null);
                            };
                        }
                        else if (ObjectFactory.IsDecimalType(underlyingPropertyType))
                        {
                            //Log.Debug("underlying property type is nullable Decimal");
                            XCaseTextBox textBox = new XCaseTextBox();
                            textBox.Index = j;
                            textBox.FieldType = propertyType;
                            //Log.Debug("property type is " + propertyType);
                            propertyTableLayoutPanel.Controls.Add(textBox, 1, j + 1);
                            textBox.TextChanged += delegate(object sender, EventArgs e)
                            {
                                Decimal value = Decimal.Zero;
                                Decimal.TryParse(textBox.Text, out value);
                                int index = textBox.Index;
                                Type fieldType = textBox.FieldType;
                                Type underlyingFieldType = Nullable.GetUnderlyingType(fieldType);
                                //Log.Debug("about to create object for nullable string");
                                object underlyingPropertyTypeObject = ObjectFactory.CreateObjectFromTypeAndValue(underlyingFieldType, value);
                                propertyTypeObject = Activator.CreateInstance(fieldType, underlyingPropertyTypeObject);
                                propertyInfoArray[index].SetValue(dictionaryKeyObject, propertyTypeObject, null);
                            };
                        }
                        else if (ObjectFactory.IsDoubleType(underlyingPropertyType))
                        {
                            //Log.Debug("underlying property type is nullable Double");
                            XCaseTextBox textBox = new XCaseTextBox();
                            textBox.Index = j;
                            textBox.FieldType = propertyType;
                            //Log.Debug("property type is " + propertyType);
                            propertyTableLayoutPanel.Controls.Add(textBox, 1, j + 1);
                            textBox.TextChanged += delegate(object sender, EventArgs e)
                            {
                                Double value = 0.0;
                                Double.TryParse(textBox.Text, out value);
                                int index = textBox.Index;
                                Type fieldType = textBox.FieldType;
                                Type underlyingFieldType = Nullable.GetUnderlyingType(fieldType);
                                //Log.Debug("about to create object for nullable string");
                                object underlyingPropertyTypeObject = ObjectFactory.CreateObjectFromTypeAndValue(underlyingFieldType, value);
                                propertyTypeObject = Activator.CreateInstance(fieldType, underlyingPropertyTypeObject);
                                propertyInfoArray[index].SetValue(dictionaryKeyObject, propertyTypeObject, null);
                            };
                        }
                        else if (underlyingPropertyType.IsEnum)
                        {
                            //Log.Debug("underlying property type is nullable enum");
                            XCaseComboBox comboBox = new XCaseComboBox();
                            comboBox.Index = j;
                            comboBox.FieldType = propertyType;
                            comboBox.DataSource = Enum.GetValues(underlyingPropertyType);
                            propertyTableLayoutPanel.Controls.Add(comboBox, 1, j + 1);
                            comboBox.SelectedIndexChanged += delegate(object sender, EventArgs e)
                            {
                                Enum value = (Enum)comboBox.SelectedValue;
                                int index = comboBox.Index;
                                Type fieldType = comboBox.FieldType;
                                Type underlyingFieldType = Nullable.GetUnderlyingType(fieldType);
                                //Log.Debug("about to create object for nullable enum");
                                object underlyingPropertyTypeObject = ObjectFactory.CreateObjectFromTypeAndValue(underlyingFieldType, value);
                                propertyTypeObject = Activator.CreateInstance(fieldType, underlyingPropertyTypeObject);
                                propertyInfoArray[index].SetValue(dictionaryKeyObject, propertyTypeObject, null);
                            };
                        }
                        else if (ObjectFactory.IsInt64Type(underlyingPropertyType))
                        {
                            Log.Debug("underlying property type is nullable Int64");
                            XCaseTextBox textBox = new XCaseTextBox();
                            textBox.Index = j;
                            textBox.FieldType = propertyType;
                            propertyTableLayoutPanel.Controls.Add(textBox, 1, j + 1);
                            textBox.TextChanged += delegate(object sender, EventArgs e)
                            {
                                Int64 value = 0;
                                Int64.TryParse(textBox.Text, out value);
                                int index = textBox.Index;
                                Type fieldType = textBox.FieldType;
                                Type underlyingFieldType = Nullable.GetUnderlyingType(fieldType);
                                Log.Debug("about to create object for nullable long");
                                object underlyingPropertyTypeObject = ObjectFactory.CreateInt64ObjectFromTypeAndValue(underlyingFieldType, value);
                                propertyTypeObject = Activator.CreateInstance(fieldType, underlyingPropertyTypeObject);
                                propertyInfoArray[index].SetValue(dictionaryKeyObject, propertyTypeObject, null);
                            };
                        }
                        else if (ObjectFactory.IsLongType(underlyingPropertyType))
                        {
                            //Log.Debug("underlying property type is nullable Long");
                            XCaseTextBox textBox = new XCaseTextBox();
                            textBox.Index = j;
                            textBox.FieldType = propertyType;
                            propertyTableLayoutPanel.Controls.Add(textBox, 1, j + 1);
                            textBox.TextChanged += delegate(object sender, EventArgs e)
                            {
                                long value = 0;
                                long.TryParse(textBox.Text, out value);
                                int index = textBox.Index;
                                Type fieldType = textBox.FieldType;
                                Type underlyingFieldType = Nullable.GetUnderlyingType(fieldType);
                                Log.Debug("about to create object for nullable long");
                                object underlyingPropertyTypeObject = ObjectFactory.CreateInt64ObjectFromTypeAndValue(underlyingFieldType, value);
                                propertyTypeObject = Activator.CreateInstance(fieldType, underlyingPropertyTypeObject);
                                propertyInfoArray[index].SetValue(dictionaryKeyObject, propertyTypeObject, null);
                            };
                        }
                        else if (ObjectFactory.IsSingleType(underlyingPropertyType))
                        {
                            //Log.Debug("underlying property type is nullable Single");
                            XCaseTextBox textBox = new XCaseTextBox();
                            textBox.Index = j;
                            textBox.FieldType = propertyType;
                            //Log.Debug("property type is " + propertyType);
                            propertyTableLayoutPanel.Controls.Add(textBox, 1, j + 1);
                            textBox.TextChanged += delegate(object sender, EventArgs e)
                            {
                                Single value = 0;
                                Single.TryParse(textBox.Text, out value);
                                int index = textBox.Index;
                                Type fieldType = textBox.FieldType;
                                Type underlyingFieldType = Nullable.GetUnderlyingType(fieldType);
                                //Log.Debug("about to create object for nullable string");
                                object underlyingPropertyTypeObject = ObjectFactory.CreateObjectFromTypeAndValue(underlyingFieldType, value);
                                propertyTypeObject = Activator.CreateInstance(fieldType, underlyingPropertyTypeObject);
                                propertyInfoArray[index].SetValue(dictionaryKeyObject, propertyTypeObject, null);
                            };
                        }
                        else if (ObjectFactory.IsStringType(underlyingPropertyType))
                        {
                            //Log.Debug("underlying property type is nullable String");
                            XCaseTextBox textBox = new XCaseTextBox();
                            textBox.Index = j;
                            textBox.FieldType = propertyType;
                            //Log.Debug("property type is " + propertyType);
                            propertyTableLayoutPanel.Controls.Add(textBox, 1, j + 1);
                            textBox.TextChanged += delegate(object sender, EventArgs e)
                            {
                                string value = textBox.Text;
                                int index = textBox.Index;
                                Type fieldType = textBox.FieldType;
                                Type underlyingFieldType = Nullable.GetUnderlyingType(fieldType);
                                //Log.Debug("about to create object for nullable string");
                                object underlyingPropertyTypeObject = ObjectFactory.CreateObjectFromTypeAndValue(underlyingFieldType, value);
                                propertyTypeObject = Activator.CreateInstance(fieldType, underlyingPropertyTypeObject);
                                propertyInfoArray[index].SetValue(dictionaryKeyObject, propertyTypeObject, null);
                            };
                        }
                        else
                        {
                            //Log.Debug("property type is nullable complex");
                        }
                    }
                    else
                    {
                        //Log.Debug("property type is " + propertyTypeObject.GetType());
                        TableLayoutPanel childTableLayoutPanel = RenderParameterObject(propertyTypeObject);
                        propertyTableLayoutPanel.Controls.Add(childTableLayoutPanel, 1, j + 1);
                        //Log.Debug("added child table layout panel for " + propertyInfoArray[j].Name + " on row " + (j + 1));
                    }
                }
            }

            propertyTableLayoutPanel.PerformLayout();
            propertyTableLayoutPanel.Dock = DockStyle.Fill;
            //Log.Debug("finishing renderPropertyObject for " + parameterObject.GetType());
            return propertyTableLayoutPanel;
        }

        public static void RenderDictionaryProperty(XCaseTableLayoutPanel propertyTableLayoutPanel, object parameterObject, PropertyInfo[] propertyInfoArray, object propertyTypeObject, int index)
        {
            Log.Debug("starting RenderDictionaryProperty()");
            Type[] dictionaryTypes = propertyTypeObject.GetType().GetGenericArguments();
            Type keyType = dictionaryTypes[0];
            Type valueType = dictionaryTypes[1];
            Type dictionaryType = typeof(Dictionary<,>);
            XCaseTableLayoutPanel dictionaryTableLayoutPanel = new XCaseTableLayoutPanel();
            dictionaryTableLayoutPanel.PropertyObject = parameterObject;
            dictionaryTableLayoutPanel.CellBorderStyle = TableLayoutPanelCellBorderStyle.Single;
            dictionaryTableLayoutPanel.AutoSize = true;
            dictionaryTableLayoutPanel.AutoScroll = true;
            dictionaryTableLayoutPanel.ColumnCount = 1;
            dictionaryTableLayoutPanel.Dock = DockStyle.Fill;
            dictionaryTableLayoutPanel.RowStyles.Add(new RowStyle(SizeType.Percent, 100F));
            int dictionaryLength = ((IDictionary)propertyTypeObject).Count;
            dictionaryTableLayoutPanel.RowCount = dictionaryLength + 1;
            //Log.Debug("length of array is " + arrayLength);
            XCaseButton addButton = new XCaseButton();
            addButton.Text = "Add";
            addButton.MouseClick += delegate(object sender, MouseEventArgs mea)
            {
                //Log.Debug("Add button clicked");
                dictionaryLength = ((IDictionary)propertyTypeObject).Count;
                object newKeyObject = ObjectFactory.CreateUniqueObject(keyType);
                object newValueObject = ObjectFactory.CreateDefaultObject(valueType);
                ((IDictionary)propertyTypeObject).Add(newKeyObject, newValueObject);
                DictionaryEntry newDictionaryEntry = new DictionaryEntry();
                newDictionaryEntry.Key = newKeyObject;
                newDictionaryEntry.Value = newValueObject;
                TableLayoutPanel dictionaryEntryTableLayoutPanel = new TableLayoutPanel();
                dictionaryEntryTableLayoutPanel.ColumnCount = 3;
                dictionaryEntryTableLayoutPanel.Dock = DockStyle.Fill;
                dictionaryEntryTableLayoutPanel.RowCount = 1;
                dictionaryEntryTableLayoutPanel.RowStyles.Add(new RowStyle(SizeType.Percent, 100F));
                /* Assume that the key is a string or enum */
                if (newDictionaryEntry.Key is string)
                {
                    XCaseTextBox keyTextBox = new XCaseTextBox();
                    keyTextBox.Text = (string)newDictionaryEntry.Key;
                    dictionaryEntryTableLayoutPanel.Controls.Add(keyTextBox, 0, 0);
                    keyTextBox.TextChanged += delegate(object keySender, EventArgs e)
                    {
                        //Log.DebugFormat("text box changed to {0}", keyTextBox.Text);
                        ((IDictionary)propertyTypeObject).Remove(newDictionaryEntry.Key);
                        string keyValue = keyTextBox.Text;
                        newDictionaryEntry.Key = ObjectFactory.CreateObjectFromTypeAndValue(typeof(string), keyValue);
                        ((IDictionary)propertyTypeObject).Add(newDictionaryEntry.Key, newDictionaryEntry.Value);
                    };
                }
                else if (newDictionaryEntry.Key is Enum)
                {
                    XCaseComboBox comboBox = new XCaseComboBox();
                    comboBox.FieldType = newDictionaryEntry.Key.GetType();
                    comboBox.DataSource = Enum.GetValues(newDictionaryEntry.Key.GetType());
                    dictionaryEntryTableLayoutPanel.Controls.Add(comboBox, 0, 0);
                    comboBox.SelectedIndexChanged += delegate(object keySender, EventArgs e)
                    {
                        //Log.Debug("combo box changed to {0}", comboBox.SelectedValue);
                        ((IDictionary)propertyTypeObject).Remove(newDictionaryEntry.Key);
                        newDictionaryEntry.Key = (Enum)comboBox.SelectedValue;
                        ((IDictionary)propertyTypeObject).Add(newDictionaryEntry.Key, newDictionaryEntry.Value);
                    };
                }
                else
                {
                    Log.Warning("key type is not string or enum {0}", newDictionaryEntry.Key.GetType().Name);
                }

                /* Assume that the value is a string or enum */
                if (newDictionaryEntry.Value is string)
                {
                    XCaseTextBox valueTextBox = new XCaseTextBox();
                    valueTextBox.Text = (string)newDictionaryEntry.Value;
                    dictionaryEntryTableLayoutPanel.Controls.Add(valueTextBox, 1, 0);
                    valueTextBox.TextChanged += delegate(object valueSender, EventArgs e)
                    {
                        //Log.DebugFormat("text box changed to {0}", valueTextBox.Text);
                        string newDictionaryEntryValue = valueTextBox.Text;
                        newDictionaryEntry.Value = ObjectFactory.CreateObjectFromTypeAndValue(typeof(string), newDictionaryEntryValue);
                        ((IDictionary)propertyTypeObject)[newDictionaryEntry.Key] = newDictionaryEntry.Value;
                    };
                }
                else if (newDictionaryEntry.Value is Enum)
                {
                    XCaseComboBox comboBox = new XCaseComboBox();
                    comboBox.FieldType = newDictionaryEntry.Value.GetType();
                    comboBox.DataSource = Enum.GetValues(newDictionaryEntry.Value.GetType());
                    dictionaryEntryTableLayoutPanel.Controls.Add(comboBox, 1, 0);
                    comboBox.SelectedIndexChanged += delegate(object valueSender, EventArgs e)
                    {
                        //Log.Debug("combo box changed to {0}", comboBox.SelectedValue);
                        newDictionaryEntry.Value = (Enum)comboBox.SelectedValue;
                        ((IDictionary)propertyTypeObject)[newDictionaryEntry.Key] = newDictionaryEntry.Value;
                    };
                }
                else
                {
                    Log.Warning("value type is not string or enum {0}", newDictionaryEntry.Value.GetType().Name);
                }

                Button removeButton = new Button();
                removeButton.Text = "Remove";
                dictionaryEntryTableLayoutPanel.Controls.Add(removeButton, 2, 0);
                removeButton.MouseClick += delegate(object removeSender, MouseEventArgs removemea)
                {
                    ((IDictionary)propertyTypeObject).Remove(newDictionaryEntry.Key);
                };
                dictionaryTableLayoutPanel.Controls.Add(dictionaryEntryTableLayoutPanel, 0, ((IDictionary)propertyTypeObject).Count + 1);
            };
            Log.Debug("set click behavior");
            dictionaryTableLayoutPanel.Controls.Add(addButton, 0, 0);
            Log.Debug("added button to panel");
            int i = 0;
            foreach (DictionaryEntry dictionaryEntry in ((IDictionary)propertyTypeObject))
            {
                TableLayoutPanel rowTableLayoutPanel = RenderDictionaryEntryObject(dictionaryEntry);
                dictionaryTableLayoutPanel.Controls.Add(rowTableLayoutPanel, 0, i + 1);
                i++;
            }

            propertyInfoArray[index].SetValue(parameterObject, propertyTypeObject, null);
            propertyTableLayoutPanel.Controls.Add(dictionaryTableLayoutPanel, 1, index + 1);
            Log.Debug("finishing RenderDictionaryProperty()");
        }

        public static void RenderExtensionDataObject(TableLayoutPanel propertyTableLayoutPanel, object[] parameterArray, object parameterObject, int index)
        {
            Log.Debug("starting RenderExtensionDataObject()");
            ExtensionDataObject value = (ExtensionDataObject)parameterObject;
            XCaseTextBox textBox = new XCaseTextBox();
            textBox.Multiline = true;
            propertyTableLayoutPanel.Controls.Add(textBox, 1, index + 1);
            textBox.TextChanged += delegate(object sender, EventArgs e)
            {
                //Log.Debug("combo box changed to {0}", comboBox.SelectedValue);
                //Enum value = (Enum)comboBox.SelectedValue;
                //int index = comboBox.Index;
                Type fieldType = textBox.FieldType;
                //Log.Debug("about to create object for enum");
                parameterObject = ObjectFactory.CreateObjectFromTypeAndValue(fieldType, value.ToString());
                if (parameterArray != null && index >= 0 && index < parameterArray.Length)
                {
                    parameterArray[index] = parameterObject;
                }
            };
            Log.Debug("finishing RenderExtensionDataObject()");
        }

        public static void RenderExtensionDataObjectProperty(TableLayoutPanel propertyTableLayoutPanel, object parameterObject, PropertyInfo[] propertyInfoArray, object propertyTypeObject, int index)
        {
            Log.Debug("starting RenderExtensionDataObjectProperty()");
            ExtensionDataObject value = (ExtensionDataObject)parameterObject;
            XCaseTextBox textBox = new XCaseTextBox();
            textBox.Multiline = true;
            textBox.Text = "To do: render ExtensionDataObject";
            propertyTableLayoutPanel.Controls.Add(textBox, 1, index + 1);
            textBox.TextChanged += delegate(object sender, EventArgs e)
            {
                //Log.Debug("combo box changed to {0}", comboBox.SelectedValue);
                //To do: how to populate value object.
                //int index = comboBox.Index;
                Type fieldType = textBox.FieldType;
                //Log.Debug("about to create object for enum");
                propertyTypeObject = ObjectFactory.CreateObjectFromTypeAndValue(fieldType, value.ToString());
                if (propertyInfoArray != null && index >= 0 && index < propertyInfoArray.Length)
                {
                    propertyInfoArray[index].SetValue(parameterObject, propertyTypeObject, null);
                }
            };
            Log.Debug("finishing RenderExtensionDataObjectProperty()");
        }

        public static void RenderXmlDocument(TableLayoutPanel propertyTableLayoutPanel, object[] parameterArray, object parameterObject, int index)
        {
            Log.Debug("starting RenderXmlDocument()");
            XCaseTextBox textBox = new XCaseTextBox();
            if (parameterObject != null)
            {
                textBox.FieldType = parameterObject.GetType();
            }
            else
            {
                textBox.FieldType = typeof(XmlDocument);
            }

            propertyTableLayoutPanel.Controls.Add(textBox, 1, index + 1);
            textBox.TextChanged += delegate(object sender, EventArgs e)
            {
                Type fieldType = textBox.FieldType;
                string value = textBox.Text;
                XmlDocument valueDocument = new XmlDocument();
                try
                {
                    valueDocument.LoadXml(value);
                    //Log.Debug("parameterObject is valid XML");
                    Log.Debug(valueDocument.ToString());
                    //Log.Debug("about to create object for XmlDocument");
                    parameterObject = ObjectFactory.CreateObjectFromTypeAndValue(fieldType, valueDocument);
                    Log.Debug(((XmlDocument)parameterObject).ToString());
                    if (parameterArray != null && index >= 0 && index < parameterArray.Length)
                    {
                        parameterArray[index] = parameterObject;
                    }
                }
                catch
                {
                    Log.Debug("string is not valid XML");
                }
            };
            Log.Debug("finishing RenderXmlDocument()");
        }

        public static void RenderXmlDocumentProperty(TableLayoutPanel propertyTableLayoutPanel, object parameterObject, PropertyInfo[] propertyInfoArray, object propertyTypeObject, int index)
        {
            Log.Debug("starting RenderXmlDocumentProperty()");
            XCaseTextBox textBox = new XCaseTextBox();
            if (parameterObject != null)
            {
                textBox.FieldType = propertyTypeObject.GetType();
            }
            else
            {
                textBox.FieldType = typeof(XmlDocument);
            }

            propertyTableLayoutPanel.Controls.Add(textBox, 1, index + 1);
            textBox.TextChanged += delegate(object sender, EventArgs e)
            {
                Type fieldType = textBox.FieldType;
                string value = textBox.Text;
                XmlDocument valueDocument = new XmlDocument();
                try
                {
                    valueDocument.LoadXml(value);
                    //Log.Debug("parameterObject is valid XML");
                    Log.Debug(valueDocument.ToString());
                    //Log.Debug("about to create object for XmlDocument");
                    propertyTypeObject = ObjectFactory.CreateObjectFromTypeAndValue(fieldType, valueDocument);
                    Log.Debug(((XmlDocument)propertyTypeObject).ToString());
                    if (propertyInfoArray != null && index >= 0 && index < propertyInfoArray.Length)
                    {
                        propertyInfoArray[index].SetValue(parameterObject, propertyTypeObject, null);
                    }
                }
                catch
                {
                    Log.Debug("string is not valid XML");
                }
            };
        }

        public static void RenderXmlNode(TableLayoutPanel propertyTableLayoutPanel, object[] parameterArray, object parameterObject, int index)
        {
            Log.Debug("starting RenderXmlNode()");
            XCaseTextBox textBox = new XCaseTextBox();
            textBox.Multiline = true;
            textBox.Size = new Size(300, 200);
            textBox.Index = index;
            if (parameterObject != null)
            {
                textBox.FieldType = parameterObject.GetType();
            }
            else
            {
                textBox.FieldType = typeof(XmlNode);
            }

            propertyTableLayoutPanel.Controls.Add(textBox, 1, index + 1);
            textBox.TextChanged += delegate(object sender, EventArgs e)
            {
                string value = textBox.Text;
                Type fieldType = textBox.FieldType;
                XmlDocument valueDocument = new XmlDocument();
                try
                {
                    valueDocument.LoadXml(value);
                    Log.Debug("parameterObject is valid XML");
                    Log.Debug(valueDocument.ToString());
                    Log.Debug("about to create object for XML document");
                    parameterObject = ObjectFactory.CreateObjectFromTypeAndValue(fieldType, valueDocument);
                    Log.Debug(((XmlDocument)parameterObject).ToString());
                    parameterArray[index] = parameterObject;
                }
                catch
                {
                    Log.Debug("string is not valid XML");
                }
            };
        }

        public static TableLayoutPanel RenderResultObject(object resultObject, int maxArrayLength)
        {
            Log.Debug("starting RenderResultObject()");
            if (resultObject == null)
            {
                Log.Debug("result object is null");
                return new TableLayoutPanel();
            }

            Type resultType = resultObject.GetType();
            Log.Debug("result object type is " + resultType);
            TableLayoutPanel resultTableLayoutPanel = new TableLayoutPanel();
            resultTableLayoutPanel.AutoScroll = true;
            resultTableLayoutPanel.CellBorderStyle = TableLayoutPanelCellBorderStyle.Single;
            resultTableLayoutPanel.AutoSize = true;
            resultTableLayoutPanel.ColumnCount = 1;
            resultTableLayoutPanel.Dock = DockStyle.Fill;
            Label resultTypeLabel = new Label();
            resultTypeLabel.AutoSize = true;
            resultTypeLabel.Dock = DockStyle.Fill;
            resultTypeLabel.Text = resultObject.GetType().Name;
            if (ObjectFactory.IsArrayType(resultType))
            {
                resultTypeLabel.Text = string.Format("{0} length {1}", resultObject.GetType().Name, ((Array)resultObject).Length);
            }
            else if (ObjectFactory.IsICollectionType(resultType))
            {
                resultTypeLabel.Text = string.Format("{0} length {1}", resultObject.GetType().Name, ((ICollection)resultObject).Count);
            }

            resultTableLayoutPanel.Controls.Add(resultTypeLabel, 0, 0);
            resultTableLayoutPanel.SetColumnSpan(resultTypeLabel, 2);
            if (ObjectFactory.IsArrayType(resultType))
            {
                Log.Debug("result object type is array");
                RenderTopResultArray(resultTableLayoutPanel, resultObject, 0, maxArrayLength);
                return resultTableLayoutPanel;
            }
            else if (ObjectFactory.IsIEnumerableType(resultType))
            {
                /* Parameter object is IEnumerable */
                Log.Debug("result object is IEnumerable");
                RenderTopResultIEnumerable(resultTableLayoutPanel, (IEnumerable)resultObject, 0, maxArrayLength);
                return resultTableLayoutPanel;
            }
            else
            {
                Log.Debug("result object type is not array");
                if (ObjectFactory.IsBooleanType(resultType))
                {
                    Log.Debug("Result object type is boolean type");
                    ((bool)resultObject).RenderResultBoolean(resultTableLayoutPanel, 0);
                }
                else if (ObjectFactory.IsByteType(resultType))
                {
                    Log.Debug("result object type is Byte type");
                    ((Byte)resultObject).RenderResultByte(resultTableLayoutPanel, 0);
                }
                else if (ObjectFactory.IsCancellationTokenType(resultType))
                {
                    Log.Debug("result object type is CancellationToken type");
                    RenderResultCancellationToken(resultTableLayoutPanel, resultObject, 0);
                }
                else if (ObjectFactory.IsCharType(resultType))
                {
                    Log.Debug("result object type is Char type");
                    ((Char)resultObject).RenderResultChar(resultTableLayoutPanel, 0);
                }
                else if (ObjectFactory.IsDateTimeType(resultType))
                {
                    Log.Debug("result object type is DateTime type");
                    ((DateTime)resultObject).RenderResultDateTime(resultTableLayoutPanel, 0);
                }
                else if (ObjectFactory.IsDecimalType(resultType))
                {
                    Log.Debug("result object type is Decimal type");
                    ((Decimal)resultObject).RenderResultDecimal(resultTableLayoutPanel, 0);
                }
                else if (ObjectFactory.IsDictionaryType(resultType))
                {
                    Log.Debug("result object type is Dictionary type");
                    RenderResultDictionary(resultTableLayoutPanel, resultObject, 0, maxArrayLength);
                }
                else if (ObjectFactory.IsDoubleType(resultType))
                {
                    Log.Debug("result object type is Double type");
                    ((Double)resultObject).RenderResultDouble(resultTableLayoutPanel, 0);
                }
                else if (ObjectFactory.IsEnumType(resultType))
                {
                    Log.Debug("result object type is Enum type");
                    ((Enum)resultObject).RenderResultEnum(resultTableLayoutPanel, 0);
                }
                else if (ObjectFactory.IsExtensionDataObjectType(resultType))
                {
                    Log.Debug("result object type is ExtensionDataObject type");
                    RenderResultExtensionDataObject(resultTableLayoutPanel, resultObject, 0);
                }
                else if (ObjectFactory.IsIntegerType(resultType))
                {
                    Log.Debug("result object type is Integer type");
                    ((int)resultObject).RenderResultInteger(resultTableLayoutPanel, 0);
                }
                else if (ObjectFactory.IsInt16Type(resultType))
                {
                    Log.Debug("result object type is Int16 type");
                    ((Int16)resultObject).RenderResultInt16(resultTableLayoutPanel, 0);
                }
                else if (ObjectFactory.IsInt64Type(resultType))
                {
                    Log.Debug("result object type is Int64 type");
                    ((Int64)resultObject).RenderResultInt64(resultTableLayoutPanel, 0);
                }
                else if (ObjectFactory.IsSingleType(resultType))
                {
                    Log.Debug("result object type is Single type");
                    ((Single)resultObject).RenderResultSingle(resultTableLayoutPanel, 0);
                }
                else if (ObjectFactory.IsStringType(resultType))
                {
                    Log.Debug("result object type is string type");
                    ((string)resultObject).RenderResultString(resultTableLayoutPanel, 0);
                }
                else if (ObjectFactory.IsXmlDocumentType(resultType))
                {
                    Log.Debug("result object type is XmlDocument type");
                    RenderResultXmlDocument(resultTableLayoutPanel, resultObject, 0);
                }
                else if (ObjectFactory.IsXmlElementType(resultType))
                {
                    Log.Debug("result object type is XmlElement type");
                    RenderResultXmlElement(resultTableLayoutPanel, resultObject, 0);
                }
                else
                {
                    Log.Debug("result object type is complex type");
                    RenderResultComplexType(resultTableLayoutPanel, resultObject, maxArrayLength);
                }
                
                Log.Debug("about to perform layout");
                resultTableLayoutPanel.AutoSize = true;
                resultTableLayoutPanel.PerformLayout();
                Log.Debug("about to return result table layout");
                return resultTableLayoutPanel;
            }
        }

        public static void RenderTopResultIEnumerable(TableLayoutPanel resultTableLayoutPanel, IEnumerable resultObject, int p, int maxArrayLength)
        {
            Log.Debug("starting RenderTopResultIEnumerable()");
            int arrayLength = ((ICollection)resultObject).Count;
            Type resultObjectType = resultObject.GetType();
            Log.Debug("type of resultObject is {0}", resultObjectType.ToString());
            resultTableLayoutPanel.RowCount = arrayLength;
            resultTableLayoutPanel.ColumnCount = 2;
            resultTableLayoutPanel.AutoSize = true;
            resultTableLayoutPanel.AutoScroll = true;
            resultTableLayoutPanel.Dock = DockStyle.Fill;
            int displayArrayLength = maxArrayLength > 0 ? Math.Min(arrayLength, maxArrayLength) : arrayLength;
            int i = 0;
            foreach (object arrayObject in (IEnumerable)resultObject)
            {
                Log.Debug("next object {0}", i);
                if (i < displayArrayLength)
                {
                    Log.Debug("next object is {0}", arrayObject);
                    Label rowLabel = new Label();
                    rowLabel.Text = i.ToString();
                    resultTableLayoutPanel.Controls.Add(rowLabel, 0, i + 1);
                    Type arrayObjectType = arrayObject.GetType();
                    Log.Debug("type of arrayObject is {0}", arrayObjectType.ToString());
                    TableLayoutPanel rowTableLayoutPanel = RenderResultObject(arrayObject, maxArrayLength);
                    rowTableLayoutPanel.AutoScroll = true;
                    rowTableLayoutPanel.Dock = DockStyle.Fill;
                    resultTableLayoutPanel.Controls.Add(rowTableLayoutPanel, 1, i + 1);
                    i++;
                }
                else
                {
                    break;
                }
            }

            Log.Debug("finishing RenderTopResultIEnumerable()");
        }

        public static void RenderResultCancellationToken(TableLayoutPanel resultTableLayoutPanel, object resultObject, int row)
        {
            Log.Debug("starting RenderResultCancellationToken() for {0}", resultObject);
            XCaseTextBox textBox = new XCaseTextBox();
            textBox.Text = resultObject.ToString();
            resultTableLayoutPanel.Controls.Add(textBox, 1, row);
        }

        public static void RenderResultDictionary(TableLayoutPanel resultTableLayoutPanel, object resultObject, int row, int maxArrayLength)
        {
            Log.Debug("starting RenderResultDictionary() for {0}", resultObject);
            TableLayoutPanel dictionaryTableLayoutPanel = new TableLayoutPanel();
            dictionaryTableLayoutPanel.AutoSize = true;
            dictionaryTableLayoutPanel.AutoScroll = true;
            dictionaryTableLayoutPanel.CellBorderStyle = TableLayoutPanelCellBorderStyle.Single;
            dictionaryTableLayoutPanel.ColumnCount = 2;
            dictionaryTableLayoutPanel.Dock = DockStyle.Fill;
            int entryCount = (int)resultObject.GetType().GetProperty("Count").GetValue(resultObject, null);
            Log.Debug("entryCount is {0}", entryCount);
            dictionaryTableLayoutPanel.RowCount = entryCount;
            int i = 0;
            foreach (object key in ((IDictionary)resultObject).Keys)
            {
                Log.Debug("next dictionary entry");
                object value = (resultObject as IDictionary)[key];
                dictionaryTableLayoutPanel.Controls.Add(RenderResultObject(key, maxArrayLength), 0, i);
                dictionaryTableLayoutPanel.Controls.Add(RenderResultObject(value, maxArrayLength), 1, i);
                i++;
            }

            Log.Debug("finished rendering result dictionary");
            resultTableLayoutPanel.Controls.Add(dictionaryTableLayoutPanel, 1, row);
        }

        public static void RenderResultExtensionDataObject(TableLayoutPanel resultTableLayoutPanel, Object resultObject, int row)
        {
            Log.Debug("starting RenderResultExtensionDataObject() for {0}", resultObject);
            /* TODO: consider how to handle extension data */
        }

        public static void RenderResultArray(TableLayoutPanel resultTableLayoutPanel, Object resultObject, int row, int maxArrayLength)
        {
            Log.Debug("starting RenderResultArray()");
            int arrayLength = (resultObject as Array).Length;
            TableLayoutPanel arrayLayoutPanel = new TableLayoutPanel();
            arrayLayoutPanel.RowCount = arrayLength;
            arrayLayoutPanel.ColumnCount = 2;
            arrayLayoutPanel.AutoScroll = true;
            int displayLength = maxArrayLength > 0 ? Math.Min(arrayLength, maxArrayLength) : arrayLength;
            for (int i = 0; i < displayLength; i++)
            {
                Log.Debug("next object {0}", i);
                Label rowLabel = new Label();
                rowLabel.Text = i.ToString();
                arrayLayoutPanel.Controls.Add(rowLabel, 0, i + 1);
                object arrayObject = (resultObject as Array).GetValue(i);
                Log.Debug("next array object is {0}", arrayObject);
                TableLayoutPanel rowTableLayoutPanel = RenderResultObject(arrayObject, maxArrayLength);
                rowTableLayoutPanel.AutoScroll = true;
                arrayLayoutPanel.Controls.Add(rowTableLayoutPanel, 1, i + 1);
                arrayLayoutPanel.Dock = DockStyle.Fill;
            }

            resultTableLayoutPanel.Controls.Add(arrayLayoutPanel, 1, row);
            Log.Debug("finishing RenderResultArray()");
        }

        public static void RenderTopResultArray(TableLayoutPanel resultTableLayoutPanel, Object resultObject, int row, int maxArrayLength)
        {
            Log.Debug("starting RenderTopResultArray()");
            int arrayLength = (resultObject as Array).Length;
            Type resultObjectType = resultObject.GetType();
            Log.Debug("type of resultObject is {0}", resultObjectType.ToString());
            resultTableLayoutPanel.RowCount = arrayLength;
            resultTableLayoutPanel.ColumnCount = 2;
            resultTableLayoutPanel.AutoSize = true;
            resultTableLayoutPanel.AutoScroll = true;
            resultTableLayoutPanel.Dock = DockStyle.Fill;
            int displayArrayLength = maxArrayLength > 0 ? Math.Min(arrayLength, maxArrayLength) : arrayLength;
            for (int i = 0; i < displayArrayLength; i++)
            {
                Log.Debug("next object {0}", i);
                Label rowLabel = new Label();
                rowLabel.Text = i.ToString();
                resultTableLayoutPanel.Controls.Add(rowLabel, 0, i + 1);
                object arrayObject = null;
                if (resultObject is Array)
                {
                    arrayObject = (resultObject as Array).GetValue(i);
                    Type arrayObjectType = arrayObject.GetType();
                    Log.Debug("type of arrayObject is {0}", arrayObjectType.ToString());
                }

                Log.Debug("next array object is {0}", arrayObject);
                TableLayoutPanel rowTableLayoutPanel = RenderResultObject(arrayObject, maxArrayLength);
                rowTableLayoutPanel.AutoScroll = true;
                rowTableLayoutPanel.Dock = DockStyle.Fill;
                resultTableLayoutPanel.Controls.Add(rowTableLayoutPanel, 1, i + 1);
            }

            Log.Debug("finishing RenderResultArray()");
        }

        public static void RenderResultXmlDocument(TableLayoutPanel resultTableLayoutPanel, Object resultObject, int row)
        {
            Log.Debug("starting RenderResultXmlDocument() for {0}", resultObject);
            XCaseTextBox textBox = new XCaseTextBox();
            textBox.Size = new Size(100, 100);
            textBox.Multiline = true;
            textBox.ScrollBars = ScrollBars.Both;
            textBox.Lines = new string[10];
            textBox.WordWrap = true;
            textBox.Dock = DockStyle.Fill;
            string resultOuterXML = ((XmlDocument)resultObject).OuterXml;
            //Log.Debug("resultOuterXML is " + resultOuterXML);
            textBox.Text = resultOuterXML;
            resultTableLayoutPanel.Controls.Add(textBox, 1, row);
            //resultTableLayoutPanel.SetColumnSpan(textBox, 2);
        }

        public static void RenderResultXmlElement(TableLayoutPanel resultTableLayoutPanel, Object resultObject, int row)
        {
            Log.Debug("starting RenderResultXmlElement() for {0}", resultObject);
            XCaseTextBox textBox = new XCaseTextBox();
            textBox.Size = new Size(100, 100);
            textBox.Multiline = true;
            textBox.ScrollBars = ScrollBars.Both;
            textBox.Lines = new string[10];
            textBox.WordWrap = true;
            textBox.Dock = DockStyle.Fill;
            string resultOuterXML = ((XmlElement)resultObject).OuterXml;
            //Log.Debug("resultOuterXML is " + resultOuterXML);
            textBox.Text = resultOuterXML;
            resultTableLayoutPanel.Controls.Add(textBox, 1, row);
            //resultTableLayoutPanel.SetColumnSpan(textBox, 2);
        }

        public static void RenderResultComplexType(TableLayoutPanel resultTableLayoutPanel, Object resultObject, int maxArrayLength)
        {
            Log.Debug("starting RenderResultComplexType()");
            PropertyInfo[] propertyInfoArray = resultObject.GetType().GetProperties();
            Log.Debug("result object property array length is {0}", propertyInfoArray.Length);
            for (int j = 0; j < propertyInfoArray.Length; j++)
            {
                Log.Debug("property name is " + propertyInfoArray[j].Name);
                Type propertyType = propertyInfoArray[j].PropertyType;
                Log.Debug("property type is " + propertyType);
                if (propertyInfoArray[j].GetIndexParameters().Length > 0)
                {
                    Log.Debug("property has index parameters");
                    break;
                }

                object resultPropertyObject = propertyInfoArray[j].GetValue(resultObject, null);
                Log.Debug("got property type object {0}", resultPropertyObject);
                Label propertyLabel = new Label();
                propertyLabel.AutoSize = true;
                propertyLabel.Text = propertyInfoArray[j].Name;
                if (ObjectFactory.IsArrayType(propertyInfoArray[j].PropertyType))
                {
                    if (((Array)resultPropertyObject) != null)
                    {
                        propertyLabel.Text = string.Format("{0} length {1}", propertyInfoArray[j].Name, ((Array)resultPropertyObject).Length);
                    }
                    else
                    {
                        if (resultPropertyObject != null)
                        {
                            Log.Warning("failed to cast resultPropertyObject to array {0}", resultPropertyObject.GetType());
                        }
                        else
                        {
                            Log.Warning("resultPropertyObject is null");
                        }
                    }
                }

                resultTableLayoutPanel.Controls.Add(propertyLabel, 0, j + 1);
                Log.Debug("added property label at (0, {0})", j + 1);
                if (resultPropertyObject != null)
                {
                    Log.Debug("resultPropertyObject is not null");
                    if (ObjectFactory.IsArrayType(resultPropertyObject.GetType()))
                    {
                        Log.Debug("element type of array is {0}", resultPropertyObject.GetType().GetElementType());
                        RenderResultArray(resultTableLayoutPanel, resultPropertyObject, j + 1, maxArrayLength);
                    }
                    else
                    {
                        Log.Debug("result property is not array");
                        Log.Debug("set " + propertyInfoArray[j].Name + " to propertyTypeObject");
                        if (ObjectFactory.IsBooleanType(propertyType))
                        {
                            Log.Debug("result property is Boolean type");
                            ((bool)resultPropertyObject).RenderResultBoolean(resultTableLayoutPanel, j + 1);
                        }
                        else if (ObjectFactory.IsByteType(propertyType))
                        {
                            Log.Debug("result property is Byte type");
                            ((Byte)resultPropertyObject).RenderResultByte(resultTableLayoutPanel, j + 1);
                        }
                        else if (ObjectFactory.IsCharType(propertyType))
                        {
                            Log.Debug("result property is Char type");
                            ((Char)resultPropertyObject).RenderResultChar(resultTableLayoutPanel, j + 1);
                        }
                        else if (ObjectFactory.IsDateTimeType(propertyType))
                        {
                            Log.Debug("result property is DateTime type");
                            ((DateTime)resultPropertyObject).RenderResultDateTime(resultTableLayoutPanel, j + 1);
                        }
                        else if (ObjectFactory.IsDecimalType(propertyType))
                        {
                            Log.Debug("result property is Decimal type");
                            ((Decimal)resultPropertyObject).RenderResultDecimal(resultTableLayoutPanel, j + 1);
                        }
                        else if (ObjectFactory.IsDoubleType(propertyType))
                        {
                            Log.Debug("result property is Double type");
                            ((Double)resultPropertyObject).RenderResultDouble(resultTableLayoutPanel, j + 1);
                        }
                        else if (ObjectFactory.IsEnumType(propertyType))
                        {
                            Log.Debug("result property is enum type");
                            ((Enum)resultPropertyObject).RenderResultEnum(resultTableLayoutPanel, j + 1);
                        }
                        else if (ObjectFactory.IsIntegerType(propertyType))
                        {
                            Log.Debug("result property is Integer type");
                            try
                            {
                                ((int)resultPropertyObject).RenderResultInteger(resultTableLayoutPanel, j + 1);
                            }
                            catch (Exception e)
                            {
                                Log.Debug("exception casting result property as int: " + e.Message);
                                ((Int64)resultPropertyObject).RenderResultInt64(resultTableLayoutPanel, j + 1);
                            }
                        }
                        else if (ObjectFactory.IsInt64Type(propertyType))
                        {
                            Log.Debug("result property is Int64 type");
                            ((Int64)resultPropertyObject).RenderResultInt64(resultTableLayoutPanel, j + 1);
                        }
                        else if (ObjectFactory.IsSingleType(propertyType))
                        {
                            Log.Debug("result property is Single type");
                            ((Single)resultPropertyObject).RenderResultSingle(resultTableLayoutPanel, j + 1);
                        }
                        else if (ObjectFactory.IsStringType(propertyType))
                        {
                            Log.Debug("result property is string type");
                            ((string)resultPropertyObject).RenderResultString(resultTableLayoutPanel, j + 1);
                        }
                        else
                        {
                            Log.Debug("result property is complex type " + resultPropertyObject.GetType());
                            TableLayoutPanel childTableLayoutPanel = RenderResultObject(resultPropertyObject, maxArrayLength);
                            Log.Debug("rendered property object");
                            resultTableLayoutPanel.Controls.Add(childTableLayoutPanel, 1, j + 1);
                            childTableLayoutPanel.Dock = DockStyle.Fill;
                            Log.Debug("added child table layout panel for " + propertyInfoArray[j].Name + " on row " + (j + 1));
                        }
                    }
                }
            }

            FieldInfo[] fieldInfoArray = resultObject.GetType().GetFields();
            Log.Debug("result object field array length is {0}", fieldInfoArray.Length);
            for (int j = 0; j < fieldInfoArray.Length; j++)
            {
                Log.Debug("field name is " + fieldInfoArray[j].Name);
                Log.Debug("field type is " + fieldInfoArray[j].FieldType);
                Label propertyLabel = new Label();
                propertyLabel.AutoSize = true;
                propertyLabel.Text = propertyInfoArray[j].Name;
                resultTableLayoutPanel.Controls.Add(propertyLabel, 0, j + 1);
                //Log.Debug("added property label at (0, {0})", j + 1);
                Type fieldType = fieldInfoArray[j].FieldType;
                //Log.Debug("property type is " + propertyType);
                object resultFieldObject = fieldInfoArray[j].GetValue(resultObject);
                //Log.Debug("created property type object {0}", resultPropertyObject);
                if (resultFieldObject != null)
                {
                    if (ObjectFactory.IsArrayType(resultFieldObject.GetType()))
                    {
                        Log.Debug("element type of array is {0}", resultFieldObject.GetType().GetElementType());
                        RenderResultArray(resultTableLayoutPanel, resultFieldObject, j + 1, maxArrayLength);
                    }
                    else
                    {
                        Log.Debug("result property is not array");
                        Log.Debug("set " + propertyInfoArray[j].Name + " to propertyTypeObject");
                        if (ObjectFactory.IsBooleanType(fieldType))
                        {
                            Log.Debug("result property is Boolean type");
                            ((bool)resultFieldObject).RenderResultBoolean(resultTableLayoutPanel, j + 1);
                        }
                        else if (ObjectFactory.IsByteType(fieldType))
                        {
                            Log.Debug("result property is Byte type");
                            ((Byte)resultFieldObject).RenderResultByte(resultTableLayoutPanel, j + 1);
                        }
                        else if (ObjectFactory.IsCharType(fieldType))
                        {
                            Log.Debug("result property is Char type");
                            ((Char)resultFieldObject).RenderResultChar(resultTableLayoutPanel, j + 1);
                        }
                        else if (ObjectFactory.IsDateTimeType(fieldType))
                        {
                            Log.Debug("result property is DateTime type");
                            ((DateTime)resultFieldObject).RenderResultDateTime(resultTableLayoutPanel, j + 1);
                        }
                        else if (ObjectFactory.IsDecimalType(fieldType))
                        {
                            Log.Debug("result property is Decimal type");
                            ((Decimal)resultFieldObject).RenderResultDecimal(resultTableLayoutPanel, j + 1);
                        }
                        else if (ObjectFactory.IsDoubleType(fieldType))
                        {
                            Log.Debug("result property is Double type");
                            ((Double)resultFieldObject).RenderResultDouble(resultTableLayoutPanel, j + 1);
                        }
                        else if (ObjectFactory.IsEnumType(fieldType))
                        {
                            Log.Debug("result property is Enum type");
                            ((Enum)resultFieldObject).RenderResultEnum(resultTableLayoutPanel, j + 1);
                        }
                        else if (ObjectFactory.IsIntegerType(fieldType))
                        {
                            Log.Debug("result property is Integer type");
                            ((int)resultFieldObject).RenderResultInteger(resultTableLayoutPanel, j + 1);
                        }
                        else if (ObjectFactory.IsInt64Type(fieldType))
                        {
                            Log.Debug("result property is Int64 type");
                            ((Int64)resultFieldObject).RenderResultInt64(resultTableLayoutPanel, j + 1);
                        }
                        else if (ObjectFactory.IsSingleType(fieldType))
                        {
                            Log.Debug("result property is Single type");
                            ((Single)resultFieldObject).RenderResultSingle(resultTableLayoutPanel, j + 1);
                        }
                        else if (ObjectFactory.IsStringType(fieldType))
                        {
                            Log.Debug("result property is String type");
                            ((string)resultFieldObject).RenderResultString(resultTableLayoutPanel, j + 1);
                        }
                        else
                        {
                            Log.Debug("result property is complex type " + resultFieldObject.GetType());
                            TableLayoutPanel childTableLayoutPanel = RenderResultObject(resultFieldObject, maxArrayLength);
                            Log.Debug("rendered result field object");
                            resultTableLayoutPanel.Controls.Add(childTableLayoutPanel, 1, j + 1);
                            childTableLayoutPanel.Dock = DockStyle.Fill;
                            Log.Debug("added child table layout panel for " + fieldInfoArray[j].Name + " on row " + (j + 1));
                        }
                    }
                }
            }

            Log.Debug("finishing RenderResultComplexType()");
        }

        public static void RenderList(TableLayoutPanel propertyTableLayoutPanel, object[] parameterArray, object parameterObject, int index)
        {
            Log.Debug("starting RenderList()");
            XCaseTableLayoutPanel listTableLayoutPanel = new XCaseTableLayoutPanel();
            listTableLayoutPanel.PropertyObject = parameterObject;
            listTableLayoutPanel.CellBorderStyle = TableLayoutPanelCellBorderStyle.Single;
            listTableLayoutPanel.AutoSize = true;
            listTableLayoutPanel.ColumnCount = 1;
            listTableLayoutPanel.AutoScroll = true;
            Type elementType = parameterObject.GetType().GetGenericArguments()[0];
            Log.Debug("list element type is " + elementType);
            int listCount = ((IList)parameterObject).Count;
            listTableLayoutPanel.RowCount = listCount + 1;
            Log.Debug("listCount is " + listCount);
            XCaseButton addButton = new XCaseButton();
            addButton.Text = "Add";
            addButton.PropertyObject = parameterObject;
            addButton.FieldType = elementType;
            addButton.MouseClick += delegate(object sender, MouseEventArgs mea)
            {
                Log.Debug("Add button clicked");
                listCount = ((IList)parameterObject).Count;
                ArrayList parameterObjectArrayList = new ArrayList(((IList)parameterObject));
                object newArrayObject = ObjectFactory.CreateDefaultObject(addButton.FieldType);
                parameterObjectArrayList.Add(newArrayObject);
                //parameterObject = parameterObjectArrayList.ToArray(newArrayObject.GetType());
                //Log.Debug("parameterObject has length {0}", ((Array)parameterObject).Length);
                TableLayoutPanel newPropertyTableLayoutPanel = RenderListObject((IList)parameterObject, listCount, newArrayObject);
                //Log.Debug("rendered new property object");
                listTableLayoutPanel.Controls.Add(newPropertyTableLayoutPanel, 0, parameterObjectArrayList.Count + 1);
                if (parameterArray != null && index >= 0 && index < parameterArray.Length)
                {
                    parameterArray[index] = parameterObject;
                }
            };
            //Log.Debug("set click behavior");
            listTableLayoutPanel.Controls.Add(addButton, 0, 0);
            //Log.Debug("added button to panel");
            int row = 0;
            foreach (object rowObject in ((IList)parameterObject))
            {
                if (rowObject != null)
                {
                    //Log.Debug("row object is " + rowObject.GetType());
                    TableLayoutPanel rowTableLayoutPanel = RenderParameterObject(rowObject);
                    listTableLayoutPanel.Controls.Add(rowTableLayoutPanel, 0, row + 1);
                    //Log.Debug("added row");
                    row++;
                }
            }

            propertyTableLayoutPanel.Controls.Add(listTableLayoutPanel, 1, index + 1);
            Log.Debug("finishing RenderList()");
        }

        public static TableLayoutPanel RenderListObject(IList list, int index, object listObject)
        {
            Log.Debug("starting RenderListObject()");
            XCaseTableLayoutPanel listObjectTableLayoutPanel = new XCaseTableLayoutPanel();
            listObjectTableLayoutPanel.Index = index;
            Type listObjectType = listObject.GetType();
            if (ObjectFactory.IsBooleanType(listObjectType))
            {
                XCaseCheckBox checkBox = new XCaseCheckBox();
                listObjectTableLayoutPanel.Controls.Add(checkBox, 1, 0);
                checkBox.CheckedChanged += delegate(object sender, EventArgs e)
                {
                    //Log.DebugFormat("check box changed to {0}", checkBox.Checked);
                    bool value = checkBox.Checked;
                    listObject = ObjectFactory.CreateObjectFromTypeAndValue(typeof(bool), value);
                    //list.Add(listObject);
                    //array[arrayObjectTableLayoutPanel.index] = arrayObject;
                };
            }
            else if (ObjectFactory.IsByteType(listObjectType))
            {
                XCaseTextBox xcaseTextBox = new XCaseTextBox();
                listObjectTableLayoutPanel.Controls.Add(xcaseTextBox, 1, 0);
                xcaseTextBox.TextChanged += delegate(object sender, EventArgs e)
                {
                    //Log.DebugFormat("text box changed to {0}", xcaseTextBox.Text);
                    byte value = 0;
                    bool result = Byte.TryParse(xcaseTextBox.Text, out value);
                    listObject = ObjectFactory.CreateObjectFromTypeAndValue(listObjectType, value);
                    //list.Add(listObject);
                    //array[arrayObjectTableLayoutPanel.index] = arrayObject;
                };
            }
            else if (ObjectFactory.IsDateTimeType(listObjectType))
            {
                XCaseDateTimePicker xcaseDateTimePicker = new XCaseDateTimePicker();
                listObjectTableLayoutPanel.Controls.Add(xcaseDateTimePicker, 1, 0);
                xcaseDateTimePicker.ValueChanged += delegate(object sender, EventArgs e)
                {
                    //Log.DebugFormat("text box changed to {0}", xcaseDateTimePicker.Value);
                    DateTime value = xcaseDateTimePicker.Value;
                    listObject = ObjectFactory.CreateObjectFromTypeAndValue(typeof(DateTime), value);
                    //list.Add(listObject);
                    //array[arrayObjectTableLayoutPanel.index] = arrayObject;
                };
            }
            else if (ObjectFactory.IsDecimalType(listObjectType))
            {
                XCaseTextBox xcaseTextBox = new XCaseTextBox();
                listObjectTableLayoutPanel.Controls.Add(xcaseTextBox, 1, 0);
                xcaseTextBox.TextChanged += delegate(object sender, EventArgs e)
                {
                    //Log.DebugFormat("text box changed to {0}", xcaseTextBox.Text);
                    Decimal value = Decimal.Zero;
                    Decimal.TryParse(xcaseTextBox.Text, out value);
                    listObject = ObjectFactory.CreateObjectFromTypeAndValue(listObjectType, value);
                    //list.Add(listObject);
                    //array[arrayObjectTableLayoutPanel.index] = arrayObject;
                };
            }
            else if (ObjectFactory.IsDoubleType(listObjectType))
            {
                XCaseTextBox xcaseTextBox = new XCaseTextBox();
                listObjectTableLayoutPanel.Controls.Add(xcaseTextBox, 1, 0);
                xcaseTextBox.TextChanged += delegate(object sender, EventArgs e)
                {
                    //Log.DebugFormat("text box changed to {0}", xcaseTextBox.Text);
                    Double value = 0.0;
                    Double.TryParse(xcaseTextBox.Text, out value);
                    listObject = ObjectFactory.CreateObjectFromTypeAndValue(listObjectType, value);
                    //list.Add(listObject);
                    //array[arrayObjectTableLayoutPanel.index] = arrayObject;
                };
            }
            else if (ObjectFactory.IsEnumType(listObjectType))
            {
                XCaseComboBox xcaseComboBox = new XCaseComboBox();
                listObjectTableLayoutPanel.Controls.Add(xcaseComboBox, 1, 0);
                xcaseComboBox.SelectedValueChanged += delegate(object sender, EventArgs e)
                {
                    //Log.DebugFormat("combo box changed to {0}", xcaseComboBox.SelectedValue);
                    Enum value = (Enum)xcaseComboBox.SelectedValue;
                    listObject = ObjectFactory.CreateObjectFromTypeAndValue(listObjectType, value);
                    //list.Add(listObject);
                    //array[arrayObjectTableLayoutPanel.index] = arrayObject;
                };
            }
            else if (ObjectFactory.IsIntegerType(listObjectType))
            {
                XCaseTextBox xcaseTextBox = new XCaseTextBox();
                listObjectTableLayoutPanel.Controls.Add(xcaseTextBox, 1, 0);
                xcaseTextBox.TextChanged += delegate(object sender, EventArgs e)
                {
                    //Log.DebugFormat("text box changed to {0}", xcaseTextBox.Text);
                    int value = 0;
                    int.TryParse(xcaseTextBox.Text, out value);
                    listObject = ObjectFactory.CreateObjectFromTypeAndValue(listObjectType, value);
                    //list.Add(listObject);
                    //array[arrayObjectTableLayoutPanel.index] = arrayObject;
                };
            }
            else if (ObjectFactory.IsInt64Type(listObjectType))
            {
                XCaseTextBox xcaseTextBox = new XCaseTextBox();
                listObjectTableLayoutPanel.Controls.Add(xcaseTextBox, 1, 0);
                xcaseTextBox.TextChanged += delegate(object sender, EventArgs e)
                {
                    //Log.DebugFormat("text box changed to {0}", xcaseTextBox.Text);
                    Int64 value = 0;
                    Int64.TryParse(xcaseTextBox.Text, out value);
                    listObject = ObjectFactory.CreateInt64ObjectFromTypeAndValue(listObjectType, value);
                    //list.Add(listObject);
                    //array[arrayObjectTableLayoutPanel.index] = arrayObject;
                };
            }
            else if (ObjectFactory.IsLongType(listObjectType))
            {
                XCaseTextBox xcaseTextBox = new XCaseTextBox();
                listObjectTableLayoutPanel.Controls.Add(xcaseTextBox, 1, 0);
                xcaseTextBox.TextChanged += delegate(object sender, EventArgs e)
                {
                    //Log.DebugFormat("text box changed to {0}", xcaseTextBox.Text);
                    long value = 0;
                    long.TryParse(xcaseTextBox.Text, out value);
                    listObject = ObjectFactory.CreateInt64ObjectFromTypeAndValue(listObjectType, value);
                    //list.Add(listObject);
                    //array[arrayObjectTableLayoutPanel.index] = arrayObject;
                };
            }
            else if (ObjectFactory.IsSingleType(listObjectType))
            {
                XCaseTextBox xcaseTextBox = new XCaseTextBox();
                listObjectTableLayoutPanel.Controls.Add(xcaseTextBox, 1, 0);
                xcaseTextBox.TextChanged += delegate(object sender, EventArgs e)
                {
                    //Log.DebugFormat("text box changed to {0}", xcaseTextBox.Text);
                    Single value = 0;
                    Single.TryParse(xcaseTextBox.Text, out value);
                    listObject = ObjectFactory.CreateObjectFromTypeAndValue(listObjectType, value);
                    //list.Add(listObject);
                    //array[arrayObjectTableLayoutPanel.index] = arrayObject;
                };
            }
            else if (ObjectFactory.IsStringType(listObjectType))
            {
                XCaseTextBox xcaseTextBox = new XCaseTextBox();
                listObjectTableLayoutPanel.Controls.Add(xcaseTextBox, 1, 0);
                xcaseTextBox.TextChanged += delegate(object sender, EventArgs e)
                {
                    //Log.DebugFormat("text box changed to {0}", xcaseTextBox.Text);
                    string value = xcaseTextBox.Text;
                    //Log.DebugFormat("index is {0}", arrayObjectTableLayoutPanel.index);
                    listObject = ObjectFactory.CreateObjectFromTypeAndValue(typeof(string), value);
                    Log.Debug("listObject changed to {0}", listObject);
                    //list.Add(listObject);
                    //array[arrayObjectTableLayoutPanel.index] = arrayObject;
                };
            }
            else
            {
                listObjectTableLayoutPanel = RenderParameterObject(listObject);
            }

            return listObjectTableLayoutPanel;
        }
    }
}
