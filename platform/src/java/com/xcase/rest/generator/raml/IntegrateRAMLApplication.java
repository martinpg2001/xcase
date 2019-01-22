package com.xcase.rest.generator.raml;

import com.xcase.rest.generator.raml.IntegrateRAMLProxy;
import com.xcase.rest.generator.raml.RAMLJavaProxyGenerator;
import com.xcase.rest.generator.RESTApiProxySettingsEndpoint;
import com.xcase.rest.generator.RESTServiceDefinition;
import java.lang.invoke.*;
import java.net.*;
import org.apache.logging.log4j.*;

/**
 *
 * @author martin
 */
public class IntegrateRAMLApplication {
   /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LOGGER.debug("starting main()");
        try {
            IntegrateRAMLProxy swaggerProxy = new IntegrateRAMLProxy(new URL("https://nightlya/"), "43112e189b20d69b3730d6e844258992");
            LOGGER.debug("created swaggerProxy");
            swaggerProxy.buildHttpClient();
            LOGGER.debug("built HttpClient");
            String swaggerDocument = "#%RAML 1.0\n" +
"---\n" +
"title: Jukebox API\n" +
"baseUri: http://jukebox.api.com\n" +
"version: v1\n" +
"\n" +
"types:\n" +
" song: !include jukebox-include-song.schema\n" +
" artist: !include jukebox-include-artist.schema\n" +
" album: !include jukebox-include-album.schema\n" +
"\n" +
"\n" +
"resourceTypes:\n" +
"  readOnlyCollection:\n" +
"    description: Collection of available <<resourcePathName>> in Jukebox.\n" +
"    get:\n" +
"      description: Get a list of <<resourcePathName>>.\n" +
"      responses:\n" +
"        200:\n" +
"          body:\n" +
"            application/json:\n" +
"              example: |\n" +
"                <<exampleCollection>>\n" +
"  collection:\n" +
"    description: Collection of available <<resourcePathName>> in Jukebox.\n" +
"    get:\n" +
"      description: Get a list of <<resourcePathName>>.\n" +
"      responses:\n" +
"        200:\n" +
"          body:\n" +
"            application/json:\n" +
"              example: |\n" +
"                <<exampleCollection>>\n" +
"    post:\n" +
"      description: |\n" +
"        Add a new <<resourcePathName|!singularize>> to Jukebox.\n" +
"      queryParameters:\n" +
"        access_token:\n" +
"          description: \"The access token provided by the authentication application\"\n" +
"          example: AABBCCDD\n" +
"          required: true\n" +
"          type: string\n" +
"      body:\n" +
"        application/json:\n" +
"          type: <<resourcePathName|!singularize>>\n" +
"          example: |\n" +
"            <<exampleItem>>\n" +
"      responses:\n" +
"        200:\n" +
"          body:\n" +
"            application/json:\n" +
"              example: |\n" +
"                { \"message\": \"The <<resourcePathName|!singularize>> has been properly entered\" }\n" +
"  collection-item:\n" +
"    description: Entity representing a <<resourcePathName|!singularize>>\n" +
"    get:\n" +
"      description: |\n" +
"        Get the <<resourcePathName|!singularize>>\n" +
"        with <<resourcePathName|!singularize>>Id =\n" +
"        {<<resourcePathName|!singularize>>Id}\n" +
"      responses:\n" +
"        200:\n" +
"          body:\n" +
"            application/json:\n" +
"              example: |\n" +
"                <<exampleItem>>\n" +
"        404:\n" +
"          body:\n" +
"            application/json:\n" +
"              example: |\n" +
"                {\"message\": \"<<resourcePathName|!singularize>> not found\" }\n" +
"traits:\n" +
"  searchable:\n" +
"    queryParameters:\n" +
"      query:\n" +
"        description: |\n" +
"          JSON array [{\"field1\",\"value1\",\"operator1\"},{\"field2\",\"value2\",\"operator2\"},...,{\"fieldN\",\"valueN\",\"operatorN\"}] <<description>>\n" +
"        example: |\n" +
"          <<example>>\n" +
"  orderable:\n" +
"    queryParameters:\n" +
"      orderBy:\n" +
"        description: |\n" +
"          Order by field: <<fieldsList>>\n" +
"        type: string\n" +
"        required: false\n" +
"      order:\n" +
"        description: Order\n" +
"        enum: [desc, asc]\n" +
"        default: desc\n" +
"        required: false\n" +
"  pageable:\n" +
"    queryParameters:\n" +
"      offset:\n" +
"        description: Skip over a number of elements by specifying an offset value for the query\n" +
"        type: integer\n" +
"        required: false\n" +
"        example: 20\n" +
"        default: 0\n" +
"      limit:\n" +
"        description: Limit the number of elements on the response\n" +
"        type: integer\n" +
"        required: false\n" +
"        example: 80\n" +
"        default: 10\n" +
"\n" +
"/songs:\n" +
"  type:\n" +
"    collection:\n" +
"      exampleCollection: !include jukebox-include-songs.sample\n" +
"      exampleItem: !include jukebox-include-song-new.sample\n" +
"  get:\n" +
"    is: [\n" +
"          searchable: {description: \"with valid searchable fields: songTitle\", example: \"[\\\"songTitle\\\", \\\"Get L\\\", \\\"like\\\"]\"},\n" +
"          orderable: {fieldsList: \"songTitle\"},\n" +
"          pageable\n" +
"        ]\n" +
"  /{songId}:\n" +
"    type:\n" +
"      collection-item:\n" +
"        exampleItem: !include jukebox-include-song-retrieve.sample\n" +
"    /file-content:\n" +
"      description: The file to be reproduced by the client\n" +
"      get:\n" +
"        description: Get the file content\n" +
"        responses:\n" +
"          200:\n" +
"            body:\n" +
"              application/octet-stream:\n" +
"                example:\n" +
"                  !include heybulldog.mp3\n" +
"      post:\n" +
"        description: |\n" +
"           Enters the file content for an existing song entity.\n" +
"           The song needs to be created for the `/songs/{songId}/file-content` to exist.\n" +
"           You can use this second resource to get and post the file to reproduce.\n" +
"           Use the \"binary/octet-stream\" content type to specify the content from any consumer (excepting web-browsers).\n" +
"           Use the \"multipart-form/data\" content type to upload a file which content will become the file-content\n" +
"        body:\n" +
"          application/octet-stream:\n" +
"          multipart/form-data:\n" +
"            properties:\n" +
"              file:\n" +
"                description: The file to be uploaded\n" +
"                required: true\n" +
"                type: file\n" +
"/artists:\n" +
"  type:\n" +
"    collection:\n" +
"      exampleCollection: !include jukebox-include-artists.sample\n" +
"      exampleItem: !include jukebox-include-artist-new.sample\n" +
"  get:\n" +
"    is: [\n" +
"          searchable: {description: \"with valid searchable fields: countryCode\", example: \"[\\\"countryCode\\\", \\\"FRA\\\", \\\"equals\\\"]\"},\n" +
"          orderable: {fieldsList: \"artistName, nationality\"},\n" +
"          pageable\n" +
"        ]\n" +
"  /{artistId}:\n" +
"    type:\n" +
"      collection-item:\n" +
"        exampleItem: !include jukebox-include-artist-retrieve.sample\n" +
"    /albums:\n" +
"      type:\n" +
"        readOnlyCollection:\n" +
"          exampleCollection: !include jukebox-include-artist-albums.sample\n" +
"      description: Collection of albulms belonging to the artist\n" +
"      get:\n" +
"        description: Get a specific artist's albums list\n" +
"        is: [orderable: {fieldsList: \"albumName\"}, pageable]\n" +
"/albums:\n" +
"  type:\n" +
"    collection:\n" +
"      exampleCollection: !include jukebox-include-albums.sample\n" +
"      exampleItem: !include jukebox-include-album-new.sample\n" +
"  get:\n" +
"    is: [\n" +
"          searchable: {description: \"with valid searchable fields: genreCode\", example: \"[\\\"genreCode\\\", \\\"ELE\\\", \\\"equals\\\"]\"},\n" +
"          orderable: {fieldsList: \"albumName, genre\"},\n" +
"          pageable\n" +
"        ]\n" +
"  /{albumId}:\n" +
"    type:\n" +
"      collection-item:\n" +
"        exampleItem: !include jukebox-include-album-retrieve.sample\n" +
"    /songs:\n" +
"      type:\n" +
"        readOnlyCollection:\n" +
"          exampleCollection: !include jukebox-include-album-songs.sample\n" +
"      get:\n" +
"        is: [orderable: {fieldsList: \"songTitle\"}]\n" +
"        description: Get the list of songs for the album with `albumId = {albumId}`";
//            LOGGER.debug("swaggerDocument is " + swaggerDocument);
            String packageName = swaggerProxy.getClass().getPackage().getName();
            LOGGER.debug("packageName is " + packageName);
            RAMLJavaProxyGenerator ramlJavaProxyGenerator = new RAMLJavaProxyGenerator(packageName);
            RESTApiProxySettingsEndpoint swaggerApiProxySettingsEndPoint = new RESTApiProxySettingsEndpoint("Java", swaggerProxy.getClass().getSimpleName());
            swaggerApiProxySettingsEndPoint.Url = "https://nightlya/api/";
            RESTApiProxySettingsEndpoint[] endpoints = new RESTApiProxySettingsEndpoint[] { swaggerApiProxySettingsEndPoint };
            RESTServiceDefinition swaggerServiceDefinition = ramlJavaProxyGenerator.generateSourceString(swaggerApiProxySettingsEndPoint, swaggerDocument, "Admin", "pass2app", "stage1");
            LOGGER.debug("swaggerServiceDefinition EndPoint is " + swaggerServiceDefinition.getEndPoint());
        } catch (Exception e) {
            LOGGER.warn("exception generating from Swagger document: " + e.getMessage());
        }
    }
}
