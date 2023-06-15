namespace XCaseServiceClient
{
    using System;
    using System.Reflection;
    using System.Windows.Forms;

    public static class BooleanExtension
    {
        public static void RenderBoolean(this bool parameterObject, TableLayoutPanel propertyTableLayoutPanel, object[] parameterArray, int index)
        {
            XCaseCheckBox checkBox = new XCaseCheckBox();
            if (propertyTableLayoutPanel != null)
            {
                propertyTableLayoutPanel.Controls.Add(checkBox, 1, index + 1);
                checkBox.CheckedChanged += delegate (object sender, EventArgs e)
                {
                    bool value = checkBox.Checked;
                    Type fieldType = checkBox.FieldType;
                    parameterObject = (bool)ObjectFactory.CreateObjectFromTypeAndValue(fieldType, value);
                    if (parameterArray != null && index >= 0 && index < parameterArray.Length)
                    {
                        parameterArray[index] = parameterObject;
                    }
                };
            }
        }

        public static void RenderBooleanProperty(this bool propertyTypeObject, TableLayoutPanel propertyTableLayoutPanel, object parameterObject, PropertyInfo[] propertyInfoArray, int index)
        {
            XCaseCheckBox checkBox = new XCaseCheckBox();
            if (propertyTableLayoutPanel != null)
            {
                propertyTableLayoutPanel.Controls.Add(checkBox, 1, index + 1);
                checkBox.CheckedChanged += delegate (object sender, EventArgs e)
                {
                    bool value = checkBox.Checked;
                    Type fieldType = checkBox.FieldType;
                    propertyTypeObject = (bool)ObjectFactory.CreateObjectFromTypeAndValue(fieldType, value);
                    if (propertyInfoArray != null && index >= 0 && index < propertyInfoArray.Length)
                    {
                        propertyInfoArray[index].SetValue(parameterObject, propertyTypeObject, null);
                    }
                };
                Button nullButton = new Button();
                propertyTableLayoutPanel.Controls.Add(nullButton, 2, index + 1);
                nullButton.Text = XCaseServiceClient.Properties.Resources.Null;
                nullButton.MouseClick += delegate (object o, MouseEventArgs mev)
                {
                    if (propertyInfoArray != null && index >= 0 && index < propertyInfoArray.Length)
                    {
                        propertyInfoArray[index].SetValue(parameterObject, null, null);
                    }
                };
            }
        }

        public static void RenderResultBoolean(this bool result, TableLayoutPanel tableLayoutPanel, int row)
        {
            if (tableLayoutPanel != null)
            {
                XCaseCheckBox checkBox = new XCaseCheckBox();
                checkBox.Checked = result;
                tableLayoutPanel.Controls.Add(checkBox, 1, row);
            }
        }
    }
}
