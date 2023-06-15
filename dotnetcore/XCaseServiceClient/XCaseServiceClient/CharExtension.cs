namespace XCaseServiceClient
{
    using System;
    using System.Reflection;
    using System.Windows.Forms;

    public static class CharExtension
    {
        public static void RenderChar(this Char parameterObject, TableLayoutPanel propertyTableLayoutPanel, object[] parameterArray, int index)
        {
            XCaseTextBox textBox = new XCaseTextBox();
            Char value = Char.MinValue;
            propertyTableLayoutPanel.Controls.Add(textBox, 1, index + 1);
            textBox.TextChanged += delegate(object sender, EventArgs e)
            {
                Char.TryParse(textBox.Text, out value);
                Type fieldType = textBox.FieldType;
                parameterObject = (char)ObjectFactory.CreateObjectFromTypeAndValue(fieldType, value);
                if (parameterArray != null && index >= 0 && index < parameterArray.Length)
                {
                    parameterArray[index] = parameterObject;
                }
            };
        }

        public static void RenderCharProperty(this Char propertyTypeObject, TableLayoutPanel propertyTableLayoutPanel, object parameterObject, PropertyInfo[] propertyInfoArray, int index)
        {
            XCaseTextBox textBox = new XCaseTextBox();
            Char value = Char.MinValue;
            propertyTableLayoutPanel.Controls.Add(textBox, 1, index + 1);
            textBox.TextChanged += delegate(object sender, EventArgs e)
            {
                Char.TryParse(textBox.Text, out value);
                Type fieldType = textBox.FieldType;
                propertyTypeObject = (Char)ObjectFactory.CreateObjectFromTypeAndValue(fieldType, value);
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

        public static void RenderResultChar(this Char resultObject, TableLayoutPanel resultTableLayoutPanel, int row)
        {
            XCaseTextBox textBox = new XCaseTextBox();
            textBox.Text = resultObject.ToString();
            resultTableLayoutPanel.Controls.Add(textBox, 1, row);
        }
    }
}
