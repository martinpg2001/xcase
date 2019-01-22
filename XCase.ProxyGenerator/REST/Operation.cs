namespace XCase.ProxyGenerator.REST
{
    using System.Collections.Generic;

    public class Operation
    {
        public Operation(string returnType, string method, string path, List<Parameter> parameters, string operationId, string description, string proxyName)
        {
            this.Path = path;
            this.Method = method;
            this.Parameters = parameters;
            this.OperationId = operationId;
            this.Description = description;
            this.ReturnType = returnType;
            this.ProxyName = proxyName;
        }

        public string ProxyName { get; set; }
        public string Path { get; set; }
        public string Method { get; set; }
        public List<Parameter> Parameters { get; set; }
        public string OperationId { get; set; }
        public string Description { get; set; }
        public string ReturnType { get; set; }

        public static string GetCleanJavaReturnType(string returnType)
        {
            bool isArray = false;
            if (!string.IsNullOrEmpty(returnType))
            {
                if (returnType.EndsWith("[]"))
                {
                    isArray = true;
                    returnType = GetCleanJavaReturnType(returnType.Substring(0, returnType.Length - 2));
                }

                if (returnType == "object")
                {
                    return "Object";
                }
                else if (returnType == "string")
                {
                    return "String";
                }

                if (isArray)
                {
                    return string.Format("{0}[]", returnType);
                }

                return returnType;
            }

            return returnType;
        }
    }
}