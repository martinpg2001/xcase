namespace XCaseServiceClient
{
    using System;
    using System.Reflection;
    using System.Windows.Forms;

    public static class DateTimeExtension
    {
        public static void RenderDateTime(this DateTime parameterObject, TableLayoutPanel propertyTableLayoutPanel, object[] parameterArray, int index)
        {
            XCaseDateTimePicker dateTimePicker = new XCaseDateTimePicker();
            if (true)
            {
                dateTimePicker.FieldType = parameterObject.GetType();
            }
            else
            {
                //dateTimePicker.FieldType = typeof(Nullable<DateTime>);
            }

            propertyTableLayoutPanel.Controls.Add(dateTimePicker, 1, index + 1);
            dateTimePicker.ValueChanged += delegate(object sender, EventArgs e)
            {
                DateTime value = dateTimePicker.Value;
                parameterObject = value;
                if (parameterArray != null && index >= 0 && index < parameterArray.Length)
                {
                    parameterArray[index] = parameterObject;
                }
            };
        }

        public static void RenderDateTimeProperty(this DateTime propertyTypeObject, TableLayoutPanel propertyTableLayoutPanel, object parameterObject, PropertyInfo[] propertyInfoArray, int index)
        {
            XCaseDateTimePicker dateTimePicker = new XCaseDateTimePicker(propertyTypeObject);
            if (true)
            {
                dateTimePicker.FieldType = propertyTypeObject.GetType();
            }
            else
            {
                //dateTimePicker.FieldType = typeof(Nullable<DateTime>);
            }

            propertyInfoArray[index].SetValue(parameterObject, dateTimePicker.Value, null);
            propertyTableLayoutPanel.Controls.Add(dateTimePicker, 1, index + 1);
            dateTimePicker.ValueChanged += delegate(object sender, EventArgs e)
            {
                DateTime value = dateTimePicker.Value;
                propertyTypeObject = value;
                if (propertyInfoArray != null && index >= 0 && index < propertyInfoArray.Length)
                {
                    propertyInfoArray[index].SetValue(parameterObject, propertyTypeObject, null);
                }
            };
            Button nullButton = new Button();
            propertyTableLayoutPanel.Controls.Add(nullButton, 2, index + 1);
            nullButton.Text = XCaseServiceClient.Properties.Resources.Null;
            nullButton.MouseClick += delegate(object o, MouseEventArgs mev)
            {
                if (propertyInfoArray != null && index >= 0 && index < propertyInfoArray.Length)
                {
                    propertyInfoArray[index].SetValue(parameterObject, null, null);
                }
            };
        }

        public static void RenderResultDateTime(this DateTime result, TableLayoutPanel resultTableLayoutPanel, int row)
        {
            XCaseTextBox textBox = new XCaseTextBox();
            textBox.Text = result.ToString();
            resultTableLayoutPanel.Controls.Add(textBox, 1, row);
        }
    }
}
