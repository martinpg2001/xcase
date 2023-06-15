namespace XCaseServiceClient
{
    using System;
    using System.Reflection;
    using System.Windows.Forms;

    public static class SingleExtension
    {
        public static void RenderSingle(this Single parameterObject, TableLayoutPanel propertyTableLayoutPanel, object[] parameterArray, int index)
        {
            XCaseTextBox textBox = new XCaseTextBox();
            Single value = 0;
            propertyTableLayoutPanel.Controls.Add(textBox, 1, index + 1);
            textBox.TextChanged += delegate(object sender, EventArgs e)
            {
                bool parse = Single.TryParse(textBox.Text, out value);
                Type fieldType = textBox.FieldType;
                parameterObject = (Single)ObjectFactory.CreateObjectFromTypeAndValue(fieldType, value);
                if (parameterArray != null && index >= 0 && index < parameterArray.Length)
                {
                    parameterArray[index] = parameterObject;
                }
            };
        }

        public static void RenderSingleProperty(this Single propertyTypeObject, TableLayoutPanel propertyTableLayoutPanel, object parameterObject, PropertyInfo[] propertyInfoArray, int index)
        {
            XCaseTextBox textBox = new XCaseTextBox();
            Single value = 0;
            if (parameterObject != null)
            {
                textBox.FieldType = propertyTypeObject.GetType();
            }
            else
            {
                textBox.FieldType = typeof(Nullable<Single>);
            }

            propertyTableLayoutPanel.Controls.Add(textBox, 1, index + 1);
            textBox.TextChanged += delegate(object sender, EventArgs e)
            {
                bool parse = Single.TryParse(textBox.Text, out value);
                Type fieldType = textBox.FieldType;
                propertyTypeObject = (Single)ObjectFactory.CreateObjectFromTypeAndValue(fieldType, value);
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

        public static void RenderResultSingle(this Single result, TableLayoutPanel resultTableLayoutPanel, int row)
        {
            XCaseTextBox textBox = new XCaseTextBox();
            textBox.Text = result.ToString();
            resultTableLayoutPanel.Controls.Add(textBox, 1, row);
        }
    }
}
