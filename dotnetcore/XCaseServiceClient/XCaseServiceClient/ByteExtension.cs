namespace XCaseServiceClient
{
    using System;
    using System.Reflection;
    using System.Windows.Forms;

    public static class ByteExtension
    {
        public static void RenderByte(this Byte parameterObject, TableLayoutPanel propertyTableLayoutPanel, object[] parameterArray, int index)
        {
            XCaseTextBox textBox = new XCaseTextBox();
            Byte value = Byte.MinValue;
            propertyTableLayoutPanel.Controls.Add(textBox, 1, index + 1);
            textBox.TextChanged += delegate(object sender, EventArgs e)
            {
                Byte.TryParse(textBox.Text, out value);
                Type fieldType = textBox.FieldType;
                parameterObject = (Byte)ObjectFactory.CreateObjectFromTypeAndValue(fieldType, value);
                if (parameterArray != null && index >= 0 && index < parameterArray.Length)
                {
                    parameterArray[index] = parameterObject;
                }
            };
        }

        public static void RenderByteProperty(this Byte propertyTypeObject, TableLayoutPanel propertyTableLayoutPanel, object parameterObject, PropertyInfo[] propertyInfoArray, int index)
        {
            XCaseTextBox textBox = new XCaseTextBox();
            Byte value = Byte.MinValue;
            propertyTableLayoutPanel.Controls.Add(textBox, 1, index + 1);
            textBox.TextChanged += delegate(object sender, EventArgs e)
            {
                Byte.TryParse(textBox.Text, out value);
                Type fieldType = textBox.FieldType;
                propertyTypeObject = (Byte)ObjectFactory.CreateObjectFromTypeAndValue(fieldType, value);
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

        public static void RenderResultByte(this Byte result, TableLayoutPanel resultTableLayoutPanel, int row)
        {
            XCaseTextBox textBox = new XCaseTextBox();
            textBox.Text = result.ToString();
            resultTableLayoutPanel.Controls.Add(textBox, 1, row);
        }
    }
}
