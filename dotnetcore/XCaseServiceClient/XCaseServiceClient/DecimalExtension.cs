namespace XCaseServiceClient
{
    using System;
    using System.Reflection;
    using System.Windows.Forms;

    public static class DecimalExtension
    {
        public static void RenderDecimal(this Decimal parameterObject, TableLayoutPanel propertyTableLayoutPanel, object[] parameterArray, int index)
        {
            XCaseTextBox textBox = new XCaseTextBox();
            Decimal value = Decimal.Zero;
            propertyTableLayoutPanel.Controls.Add(textBox, 1, index + 1);
            textBox.TextChanged += delegate(object sender, EventArgs e)
            {
                Decimal.TryParse(textBox.Text, out value);
                Type fieldType = textBox.FieldType;
                parameterObject = (Decimal)ObjectFactory.CreateObjectFromTypeAndValue(fieldType, value);
                if (parameterArray != null && index >= 0 && index < parameterArray.Length)
                {
                    parameterArray[index] = parameterObject;
                }
            };
        }

        public static void RenderDecimalProperty(this Decimal propertyTypeObject, TableLayoutPanel propertyTableLayoutPanel, object parameterObject, PropertyInfo[] propertyInfoArray, int index)
        {
            XCaseTextBox textBox = new XCaseTextBox();
            Decimal value = Decimal.Zero;
            propertyTableLayoutPanel.Controls.Add(textBox, 1, index + 1);
            textBox.TextChanged += delegate(object sender, EventArgs e)
            {
                Decimal.TryParse(textBox.Text, out value);
                Type fieldType = textBox.FieldType;
                propertyTypeObject = (Decimal)ObjectFactory.CreateObjectFromTypeAndValue(fieldType, value);
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

        public static void RenderResultDecimal(this Decimal result, TableLayoutPanel resultTableLayoutPanel, int row)
        {
            XCaseTextBox textBox = new XCaseTextBox();
            textBox.Text = result.ToString();
            resultTableLayoutPanel.Controls.Add(textBox, 1, row);
        }
    }
}
