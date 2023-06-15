namespace XCaseServiceClient
{
    using System;
    using System.Reflection;
    using System.Windows.Forms;

    public static class StringExtension
    {
        public static void RenderString(this string parameterObject, TableLayoutPanel propertyTableLayoutPanel, object[] parameterArray, int index)
        {
            XCaseTextBox textBox = new XCaseTextBox();
            textBox.MaxLength = 0;
            if (parameterObject != null)
            {
                textBox.FieldType = parameterObject.GetType();
                if (parameterObject.GetType() == typeof(string))
                {
                    textBox.Text = (string)parameterObject;
                }
            }
            else
            {
                textBox.FieldType = typeof(string);
            }

            propertyTableLayoutPanel.Controls.Add(textBox, 1, index + 1);
            textBox.TextChanged += delegate(object sender, EventArgs e)
            {
                string value = textBox.Text;
                Type fieldType = textBox.FieldType;
                if (!fieldType.IsArray)
                {
                    parameterObject = (string)ObjectFactory.CreateObjectFromTypeAndValue(fieldType, value);
                    if (parameterArray != null && index >= 0 && index < parameterArray.Length)
                    {
                        parameterArray[index] = parameterObject;
                    }
                }
                else
                {
                    string[] valueArray = value.Split();
                    Type elementType = fieldType.GetElementType();
                    Array objectArray = Array.CreateInstance(elementType, valueArray.Length);
                    for (int k = 0; k < valueArray.Length; k++)
                    {
                        object objectValue = ObjectFactory.CreateObjectFromTypeAndValue(elementType, valueArray[k]);
                        objectArray.SetValue(objectValue, k);
                    }

                    parameterObject = objectArray.ToString();
                    if (parameterArray != null && index >= 0 && index < parameterArray.Length)
                    {
                        parameterArray[index] = parameterObject;
                    }
                }
            };
        }

        public static void RenderStringProperty(this string propertyTypeObject, TableLayoutPanel propertyTableLayoutPanel, object parameterObject, PropertyInfo[] propertyInfoArray, int index)
        {
            XCaseTextBox textBox = new XCaseTextBox();
            textBox.MaxLength = 0;
            if (propertyTypeObject != null)
            {
                textBox.FieldType = propertyTypeObject.GetType();
                if (propertyTypeObject.GetType() == typeof(string))
                {
                    textBox.Text = (string)propertyTypeObject;
                }
            }
            else
            {
                textBox.FieldType = typeof(string);
            }

            propertyTableLayoutPanel.Controls.Add(textBox, 1, index + 1);
            textBox.TextChanged += delegate(object sender, EventArgs e)
            {
                string value = textBox.Text;
                Type fieldType = textBox.FieldType;
                if (!fieldType.IsArray)
                {
                    propertyTypeObject = (string)ObjectFactory.CreateObjectFromTypeAndValue(fieldType, value);
                    if (propertyInfoArray != null && index >= 0 && index < propertyInfoArray.Length)
                    {
                        propertyInfoArray[index].SetValue(parameterObject, propertyTypeObject, null);
                    }
                }
                else
                {
                    string[] valueArray = value.Split();
                    Type elementType = fieldType.GetElementType();
                    Array objectArray = Array.CreateInstance(elementType, valueArray.Length);
                    for (int k = 0; k < valueArray.Length; k++)
                    {
                        object objectValue = ObjectFactory.CreateObjectFromTypeAndValue(elementType, valueArray[k]);
                        objectArray.SetValue(objectValue, k);
                    }

                    propertyTypeObject = objectArray.ToString();
                    if (propertyInfoArray != null && index >= 0 && index < propertyInfoArray.Length)
                    {
                        propertyInfoArray[index].SetValue(parameterObject, propertyTypeObject, null);
                    }
                }
            };
            Button nullButton = new Button();
            propertyTableLayoutPanel.Controls.Add(nullButton, 2, index + 1);
            nullButton.Text = XCaseServiceClient.Properties.Resources.Null;
            nullButton.MouseClick += delegate(object o, MouseEventArgs mev)
            {
                if (propertyInfoArray != null && index >= 0 && index < propertyInfoArray.Length)
                {
                    propertyTypeObject = null;
                    propertyInfoArray[index].SetValue(parameterObject, null, null);
                    textBox.Text = null;
                }
            };
        }

        public static void RenderResultString(this string result, TableLayoutPanel resultTableLayoutPanel, int row)
        {
            TextBox textBox = new TextBox();
            textBox.MaxLength = 0;
            textBox.Text = result;
            resultTableLayoutPanel.Controls.Add(textBox, 1, row);
            textBox.Dock = DockStyle.Fill;
        }
    }
}
