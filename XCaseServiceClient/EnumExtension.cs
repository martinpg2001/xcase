namespace XCaseServiceClient
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Reflection;
    using System.Text;
    using System.Threading.Tasks;
    using System.Windows.Forms;

    public static class EnumExtension
    {
        public static void RenderEnum(this Enum parameterObject, TableLayoutPanel propertyTableLayoutPanel, object[] parameterArray, int index)
        {
            XCaseComboBox comboBox = new XCaseComboBox();
            if (parameterObject != null)
            {
                comboBox.FieldType = parameterObject.GetType();
                comboBox.DataSource = Enum.GetValues(parameterObject.GetType());
            }
            else
            {

            }

            propertyTableLayoutPanel.Controls.Add(comboBox, 1, index + 1);
            comboBox.SelectedIndexChanged += delegate(object sender, EventArgs e)
            {
                Enum value = (Enum)comboBox.SelectedValue;
                Type fieldType = comboBox.FieldType;
                parameterObject = (Enum)ObjectFactory.CreateObjectFromTypeAndValue(fieldType, value);
                if (parameterArray != null && index >= 0 && index < parameterArray.Length)
                {
                    parameterArray[index] = parameterObject;
                }
            };
        }

        public static void RenderEnumProperty(this object propertyTypeObject, TableLayoutPanel propertyTableLayoutPanel, object parameterObject, PropertyInfo[] propertyInfoArray, int index)
        {
            XCaseComboBox comboBox = new XCaseComboBox();
            if (propertyTypeObject != null)
            {
                comboBox.FieldType = propertyTypeObject.GetType();
                comboBox.DataSource = Enum.GetValues(propertyTypeObject.GetType());
            }
            else
            {

            }

            propertyTableLayoutPanel.Controls.Add(comboBox, 1, index + 1);
            comboBox.SelectedIndexChanged += delegate(object sender, EventArgs e)
            {
                Enum value = (Enum)comboBox.SelectedValue;
                Type fieldType = comboBox.FieldType;
                propertyTypeObject = ObjectFactory.CreateObjectFromTypeAndValue(fieldType, value);
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

        public static void RenderResultEnum(this Enum result, TableLayoutPanel resultTableLayoutPanel, int row)
        {
            XCaseTextBox textBox = new XCaseTextBox();
            textBox.Text = result.ToString();
            resultTableLayoutPanel.Controls.Add(textBox, 1, row);
        }
    }
}
