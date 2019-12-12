namespace XCaseServiceClient
{
    using System;
    using System.Collections.Generic;
    using System.IO;
    using System.Linq;
    using System.Text;
    using System.Threading.Tasks;
    using System.Xml;
    using System.Xml.Serialization;

    public class XCaseServiceDescription
    {
        public Config config;

        public static Config OpenFromFile(string filename)
        {
            XmlSerializer configSerializer = new XmlSerializer(typeof(Config));
            TextReader reader = new StreamReader(filename);
            Config config = (Config)configSerializer.Deserialize(reader);
            reader.Close();
            return config;
        }

        public static void SaveToFile(Stream stream, Config config)
        {
            XmlSerializer configSerializer = new XmlSerializer(typeof(Config));
            TextWriter writer = new StreamWriter(stream);
            configSerializer.Serialize(writer, config);
            writer.Close();
        }
    }

    public class Config
    {
        public string binding;
        public string client;
        public string domain;
        public string endpoint;
        public string language;
        public string message;
        public bool onpremise;
        public string password;
        public string security;
        public int timeout;
        public string type;
        public string username;
    }
}
