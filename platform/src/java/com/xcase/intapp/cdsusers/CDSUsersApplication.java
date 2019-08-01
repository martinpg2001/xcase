package com.xcase.intapp.cdsusers;

import com.google.gson.JsonObject;
import com.xcase.common.impl.simple.core.CommonHTTPManager;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.intapp.cdsusers.constant.CDSUsersConstant;
import com.xcase.intapp.cdsusers.factories.CDSUsersRequestFactory;
import com.xcase.intapp.cdsusers.impl.simple.core.CDSUsersConfigurationManager;
import com.xcase.intapp.cdsusers.objects.AddressDTO;
import com.xcase.intapp.cdsusers.objects.CommunicationDTO;
import com.xcase.intapp.cdsusers.objects.KeyRef;
import com.xcase.intapp.cdsusers.objects.PersonPostDTO;
import com.xcase.intapp.cdsusers.objects.RolePostDTO;
import com.xcase.intapp.cdsusers.objects.UserPostDTO;
import com.xcase.intapp.cdsusers.transputs.*;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CDSUsersApplication {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LOGGER.debug("starting main()");
        CDSUsersExternalAPI cdsUsersExternalAPI = new SimpleCDSUsersImpl();
        LOGGER.debug("created cdsUsersExternalAPI");
        try {
            generateTokenPair();
            String accessToken = CDSUsersConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(CDSUsersConstant.ACCESS_TOKEN);
            String personId = "gilian.gilchrist@xcase.com";
            LOGGER.debug("personId is " + personId);
            /* Create person */
            LOGGER.debug("about to create person");
            CreatePersonRequest createPersonRequest = CDSUsersRequestFactory.createCreatePersonRequest(accessToken);
            LOGGER.debug("created createPersonRequest");
            PersonPostDTO personPostDTO = new PersonPostDTO();
            personPostDTO.costPoolId = "TRV";
            personPostDTO.email = "kevin.gilchrist@xcase.com";
            personPostDTO.firstName = "Kevin";
            personPostDTO.middleName = "Philip";
            personPostDTO.lastName = "Gilchrist";
            personPostDTO.middleName = "Philip";
            List<AddressDTO> addressDTOList = new ArrayList<AddressDTO>();
            AddressDTO homeAddressDTO = new AddressDTO();
            homeAddressDTO.city = "Salt Lake City";
            homeAddressDTO.country = "US";
            homeAddressDTO.postalCode = "01234";
            homeAddressDTO.state = "UT";
            homeAddressDTO.streetAddress = "18677 Park Lane";
            homeAddressDTO.type = AddressDTO.typeValues.Home;
            addressDTOList.add(homeAddressDTO);
            AddressDTO workAddressDTO = new AddressDTO();
            workAddressDTO.city = "Salt Lake City";
            workAddressDTO.country = "US";
            workAddressDTO.postalCode = "01235";
            workAddressDTO.state = "UT";
            workAddressDTO.streetAddress = "18676 Park Lane";
            workAddressDTO.type = AddressDTO.typeValues.Work;
            addressDTOList.add(workAddressDTO);
            AddressDTO[] addressDTOArray = addressDTOList.toArray(new AddressDTO[0]);
            personPostDTO.addresses = addressDTOArray;
            List<CommunicationDTO> communicationDTOList = new ArrayList<CommunicationDTO>();
            CommunicationDTO homeCommunicationDTO = new CommunicationDTO();
            homeCommunicationDTO.areaCode = 650;
            homeCommunicationDTO.countryCode = 2;
            homeCommunicationDTO.extension = "852";
            homeCommunicationDTO.number = "5551212";
            homeCommunicationDTO.type = CommunicationDTO.typeValues.Home;
            communicationDTOList.add(homeCommunicationDTO);
            CommunicationDTO workCommunicationDTO = new CommunicationDTO();
            workCommunicationDTO.areaCode = 650;
            workCommunicationDTO.countryCode = 3;
            workCommunicationDTO.extension = "853";
            workCommunicationDTO.number = "5551313";
            workCommunicationDTO.type = CommunicationDTO.typeValues.Work;
            communicationDTOList.add(workCommunicationDTO);
            CommunicationDTO[] communicationDTOArray = communicationDTOList.toArray(new CommunicationDTO[0]);
            personPostDTO.communications = communicationDTOArray;
            createPersonRequest.setPerson(personPostDTO);
            createPersonRequest.setPersonString("{\"personId\":null,\"firstName\":\"Imogen\",\"middleName\":\"Philippa\",\"lastName\":\"Gilchrist\",\"name\":\"Imogen Gilchrist\",\"titles\":[],\"email\":\"imogen.gilchrist@xcase.com\",\"costPoolId\":\"ACC\",\"addresses\":[{\"type\":\"Home\",\"country\":\"US\",\"state\":\"UT\",\"city\":\"Salt Lake City\",\"streetAddress\":\"1 Park Avenue\",\"postalCode\":\"09876\"},{\"type\":\"Work\",\"country\":\"US\",\"state\":\"UT\",\"city\":\"Salt Lake City\",\"streetAddress\":\"2 Park Avenue\",\"postalCode\":\"09875\"}],\"communications\":[{\"type\":\"Mobile\",\"areaCode\":\"408\",\"number\":\"5551212\"},{\"type\":\"Work\",\"areaCode\":\"408\",\"number\":\"6661212\"}],\"employee\":true,\"department\":{\"key\":\"QA\"},\"office\":{\"key\":\"LND\"},\"practiceAreas\":[{\"key\":\"LIT\"}],\"externalIds\":[]}");
            CreatePersonResponse createPersonResponse = cdsUsersExternalAPI.createPerson(createPersonRequest);
            LOGGER.debug("created person");
            /* Get persons */
            LOGGER.debug("about to get 10 persons");
            GetPersonsRequest getPersonsRequest = CDSUsersRequestFactory.createGetPersonsRequest(accessToken);
            getPersonsRequest.setLimit(0);
            getPersonsRequest.setSkip(0);
            LOGGER.debug("created getPersonsRequest");
            GetPersonsResponse getPersonsResponse = cdsUsersExternalAPI.getPersons(getPersonsRequest);
            LOGGER.debug("got persons");
            /* Create service user */
            LOGGER.debug("about to create service user");
            CreateServiceUserRequest createServiceUserRequest = CDSUsersRequestFactory.createCreateServiceUserRequest(accessToken);
            LOGGER.debug("created createServiceUserRequest");
            createServiceUserRequest.setServiceUserString("{\"userId\":\"TestCDS\",\"enabled\":true,\"name\":\"Test CDS Service User\",\"roles\":[]}");
            CreateServiceUserResponse createServiceUserResponse = cdsUsersExternalAPI.createServiceUser(createServiceUserRequest);
            LOGGER.debug("created service user");
            /* Create user */
            LOGGER.debug("about to create user");
            CreateUserRequest createUserRequest = CDSUsersRequestFactory.createCreateUserRequest(accessToken);
            LOGGER.debug("created createUserRequest");
            UserPostDTO userPostDTO = new UserPostDTO();
            userPostDTO.userId = "john.gilchrist@xcase.com";
            userPostDTO.email = "john.gilchrist@xcase.com";
            userPostDTO.enabled = true;
            userPostDTO.name = "John Gilchrist";
            userPostDTO.personKey = "-vGpeSugaRv20cCo";
            userPostDTO.timekeeper = true;
            createUserRequest.setUser(userPostDTO);
            createUserRequest.setUserString("{\"userId\":\"dennis.gilchrist@xcase.com\",\"email\":\"dennis.gilchrist@xcase.com\",\"enabled\":true,\"name\":\"Dennis Gilchrist\",\"personKey\":\"-vGpeSugaRv20cCo\",\"timeZoneId\":null,\"locale\":null,\"roles\":[],\"enableOtp\":null,\"timekeeper\":true,\"exchangeUsername\":\"\",\"exchangeHost\":\"\",\"emailAliases\":[],\"timeLinks\":{}}");
            CreateUserResponse createUserResponse = cdsUsersExternalAPI.createUser(createUserRequest);
            LOGGER.debug("created user");
            /* Find users */
            LOGGER.debug("about to find users");
            FindUsersRequest findUsersRequest = CDSUsersRequestFactory.createFindUsersRequest(accessToken);
            LOGGER.debug("created findUsersRequest");
            FindUsersResponse findUsersResponse = cdsUsersExternalAPI.findUsers(findUsersRequest);
            LOGGER.debug("found users");
            /* Find service users */
            LOGGER.debug("about to find service users");
            FindServiceUsersRequest findServiceUsersRequest = CDSUsersRequestFactory.createFindServiceUsersRequest(accessToken);
            LOGGER.debug("created findServiceUsersRequest");
            FindServiceUsersResponse findServiceUsersResponse = cdsUsersExternalAPI.findServiceUsers(findServiceUsersRequest);
            LOGGER.debug("found service users");
            /* Partially update user */
            LOGGER.debug("about to partially update user");
            PartiallyUpdateUserRequest partiallyUpdateUserRequest = CDSUsersRequestFactory.createPartiallyUpdateUserRequest(accessToken);
            LOGGER.debug("created partiallyUpdateUserRequest");
            partiallyUpdateUserRequest.setKey("j_GC6SvZOxsM9sC2");
            partiallyUpdateUserRequest.setUserString("{\"timekeeper\":false}");
            PartiallyUpdateUserResponse partiallyUpdateUserResponse = cdsUsersExternalAPI.partiallyUpdateUser(partiallyUpdateUserRequest);
            LOGGER.debug("partially updated user");
            /* Put user */
            LOGGER.debug("about to put user");
            PutUserRequest putUserRequest = CDSUsersRequestFactory.createPutUserRequest(accessToken);
            LOGGER.debug("created putUserRequest");
            putUserRequest.setKey("j_GC6SvZOxsM9sC2");
            putUserRequest.setUserString("{\"userId\" : \"dennis.gilchrist@xcase.com\",\"email\" : \"dennis.gilchrist@xcase.com\",\"enabled\" : true,\"name\" : \"Dennis Gilchrist\",\"firstName\" : \"Dennis\",\"lastName\" : \"Gilchrist\",\"personKey\" : \"-vGpeSugaRv20cCo\",\"external\" : true,\"userOrigin\" : \"REGULAR\",\"providerAlias\" : \"saml\",\"enableOtp\" : false,\"timekeeper\" : false,\"exchangeUsername\" : \"\",\"exchangeHost\" : \"\",\"readonly\" : false}");
            PutUserResponse putUserResponse = cdsUsersExternalAPI.putUser(putUserRequest);
            LOGGER.debug("put user");
            /* Get user */
            LOGGER.debug("about to get user");
            GetUserRequest getUserRequest = CDSUsersRequestFactory.createGetUserRequest(accessToken);
            LOGGER.debug("created getUserRequest");
            getUserRequest.setKey("j_GC6SvZOxsM9sC2");
            GetUserResponse getUserResponse = cdsUsersExternalAPI.getUser(getUserRequest);
            LOGGER.debug("get user");
            /* Get service user */
            LOGGER.debug("about to get service user");
            GetServiceUserRequest getServiceUserRequest = CDSUsersRequestFactory.createGetServiceUserRequest(accessToken);
            LOGGER.debug("created getServiceUserRequest");
            getServiceUserRequest.setKey("GPE45StBaht0sMA0");
            GetServiceUserResponse getServiceUserResponse = cdsUsersExternalAPI.getServiceUser(getServiceUserRequest);
            LOGGER.debug("get service user");
            /* Create role */
            LOGGER.debug("about to create role");
            CreateRoleRequest createRoleRequest = CDSUsersRequestFactory.createCreateRoleRequest(accessToken);
            LOGGER.debug("created createRoleRequest");
            RolePostDTO rolePostDTO = new RolePostDTO();
            rolePostDTO.capabilities = new KeyRef[0];
            rolePostDTO.description = "This is a test role.";
            userPostDTO.name = "TEST ROLE";
            createRoleRequest.setRole(rolePostDTO);
            createRoleRequest.setRoleString("{\"name\":\"TEST ROLE\",\"description\":\"This is a test role.\"}");
            CreateRoleResponse createRoleResponse = cdsUsersExternalAPI.createRole(createRoleRequest);
            LOGGER.debug("created role");
            /* Find all roles */
            LOGGER.debug("about to find all roles");
            FindRolesRequest findAllRolesRequest = CDSUsersRequestFactory.createFindRolesRequest(accessToken);
            LOGGER.debug("created findAllRolesRequest");
            findAllRolesRequest.setName(null);
            FindRolesResponse findAllRolesResponse = cdsUsersExternalAPI.findRoles(findAllRolesRequest);
            LOGGER.debug("found all roles");
            /* Find 10 roles */
            LOGGER.debug("about to find roles");
            FindRolesRequest findRolesRequest = CDSUsersRequestFactory.createFindRolesRequest(accessToken);
            LOGGER.debug("created findRolesRequest");
            findRolesRequest.setName("System Admin");
            findRolesRequest.setLimit(10);
            findRolesRequest.setSkip(5);
            FindRolesResponse findRolesResponse = cdsUsersExternalAPI.findRoles(findRolesRequest);
            LOGGER.debug("found roles");
            /* Set role to users */
            LOGGER.debug("about to set role to users");
            SetRoleUsersRequest setRoleUsersRequest = CDSUsersRequestFactory.createSetRoleUsersRequest(accessToken);
            LOGGER.debug("created setRoleUsersRequest");
            setRoleUsersRequest.setKey("HfFhDCuH4Rs4_8Ds");
            List<String> userList = new ArrayList<String>();
            userList.add("j_GC6SvZOxsM9sC2");
            String[] userArray = userList.toArray(new String[0]);
            setRoleUsersRequest.setUsers(userArray);
            SetRoleUsersResponse setRoleUsersResponse = cdsUsersExternalAPI.setRoleUsers(setRoleUsersRequest);
            LOGGER.debug("set role to users");
            /* Get roles assigned to user */
            LOGGER.debug("about to get roles assigned to user");
            GetUserRolesRequest getUserRolesRequest = CDSUsersRequestFactory.createGetUserRolesRequest(accessToken);
            LOGGER.debug("created getUserRolesRequest");
            getUserRolesRequest.setKey("j_GC6SvZOxsM9sC2");
            GetUserRolesResponse getUserRolesResponse = cdsUsersExternalAPI.getUserRoles(getUserRolesRequest);
            LOGGER.debug("got roles assigned to user");
            /* Delete role */
            LOGGER.debug("about to delete role");
            DeleteRoleRequest deleteRoleRequest = CDSUsersRequestFactory.createDeleteRoleRequest(accessToken);
            LOGGER.debug("created deleteRoleRequest");
            deleteRoleRequest.setKey("SvGWqysOUBs0pMDs");
            DeleteRoleResponse deleteRoleResponse = cdsUsersExternalAPI.deleteRole(deleteRoleRequest);
            LOGGER.debug("deleted role");
            /* Find 10 capabilities */
            LOGGER.debug("about to find capabilities");
            FindCapabilitiesRequest findCapabilitiesRequest = CDSUsersRequestFactory.createFindCapabilitiesRequest(accessToken);
            LOGGER.debug("created findCapabilitiesRequest");
            findCapabilitiesRequest.setRole("System Admin");
            findCapabilitiesRequest.setLimit(10);
            findCapabilitiesRequest.setSkip(5);
            FindCapabilitiesResponse findCapabilitiesResponse = cdsUsersExternalAPI.findCapabilities(findCapabilitiesRequest);
            LOGGER.debug("found capabilities");
            /* Get capability */
            LOGGER.debug("about to get capability");
            GetCapabilityRequest getCapabilityRequest = CDSUsersRequestFactory.createGetCapabilityRequest(accessToken);
            LOGGER.debug("created getCapabilityRequest");
            getCapabilityRequest.setKey("SfEDIyu2DxvB08OR");
            GetCapabilityResponse getCapabilityResponse = cdsUsersExternalAPI.getCapability(getCapabilityRequest);
            LOGGER.debug("got capability");
            /* Publish entities */
            LOGGER.debug("about to publish entities");
            PublishEntitiesRequest publishEntitiesRequest = CDSUsersRequestFactory.createPublishEntitiesRequest(accessToken);
            LOGGER.debug("created publishEntitiesRequest");
            publishEntitiesRequest.setTopic("MartinTopic");
            publishEntitiesRequest.setEntity("User");
            PublishEntitiesResponse publishEntitiesResponse = cdsUsersExternalAPI.publishEntities(publishEntitiesRequest);
            LOGGER.debug("published entities");
            /* Upload entities */
            LOGGER.debug("about to upload entities");
            UploadEntitiesRequest uploadPersonEntitiesRequest = CDSUsersRequestFactory.createUploadEntitiesRequest(accessToken);
            LOGGER.debug("created uploadPersonEntitiesRequest");
            uploadPersonEntitiesRequest.setEntity("Person");
            uploadPersonEntitiesRequest.setEntityString("[{\"firstName\":\"Ellen\",\"middleName\":\"Philippa\",\"lastName\":\"Gilchrist\",\"name\":\"Ellen Gilchrist\",\"titles\":[],\"email\":\"ellen.gilchrist@xcase.com\",\"costPoolId\":null,\"addresses\":[],\"communications\":[],\"employee\":true,\"department\":null,\"office\":null,\"practiceAreas\":[],\"externalIds\":[]},{\"firstName\":\"Frank\",\"middleName\":\"Philippa\",\"lastName\":\"Gilchrist\",\"name\":\"Frank Gilchrist\",\"titles\":[],\"email\":\"frank.gilchrist@xcase.com\",\"costPoolId\":null,\"addresses\":[],\"communications\":[],\"employee\":false,\"department\":null,\"office\":null,\"practiceAreas\":[],\"externalIds\":[]}]");
            UploadEntitiesResponse uploadPersonEntitiesResponse = cdsUsersExternalAPI.uploadEntities(uploadPersonEntitiesRequest);
            LOGGER.debug("uploaded person entities");
            UploadEntitiesRequest uploadRoleEntitiesRequest = CDSUsersRequestFactory.createUploadEntitiesRequest(accessToken);
            LOGGER.debug("created uploadRoleEntitiesRequest");
            uploadRoleEntitiesRequest.setEntity("Role");
            uploadRoleEntitiesRequest.setEntityString("[{\"name\":\"TEST ROLE 2\",\"description\":\"This is a second test role.\"}]");
            UploadEntitiesResponse uploadRoleEntitiesResponse = cdsUsersExternalAPI.uploadEntities(uploadRoleEntitiesRequest);
            LOGGER.debug("uploaded role entities");
            UploadEntitiesRequest uploadUserEntitiesRequest = CDSUsersRequestFactory.createUploadEntitiesRequest(accessToken);
            LOGGER.debug("created uploadUserEntitiesRequest");
            uploadUserEntitiesRequest.setEntity("User");
            uploadUserEntitiesRequest.setEntityString("[{\"userId\":\"ellen.gilchrist@xcase.com\",\"email\":\"ellen.gilchrist@xcase.com\",\"enabled\":true,\"name\":\"Ellen Gilchrist\",\"personKey\":\"-vGpeSugaRv20cCo\",\"timeZoneId\":null,\"locale\":null,\"roles\":[],\"enableOtp\":null,\"timekeeper\":true,\"exchangeUsername\":\"\",\"exchangeHost\":\"\",\"emailAliases\":[],\"timeLinks\":{}},{\"userId\":\"frank.gilchrist@intapp.com\",\"email\":\"frank.gilchrist@xcase.com\",\"enabled\":true,\"name\":\"Frank Gilchrist\",\"personKey\":\"7vHDVCunIRuXfsDq\",\"timeZoneId\":null,\"locale\":null,\"roles\":[],\"enableOtp\":null,\"timekeeper\":true,\"exchangeUsername\":\"\",\"exchangeHost\":\"\",\"emailAliases\":[],\"timeLinks\":{}}]");
            UploadEntitiesResponse uploadUserEntitiesResponse = cdsUsersExternalAPI.uploadEntities(uploadUserEntitiesRequest);
            LOGGER.debug("uploaded user entities");
        } catch (Exception e) {
            LOGGER.warn("exception executing methods: " + e.getMessage());
        }
    }

    private static void generateTokenPair() throws Exception, IOException {
        String cdsUsersClientID = CDSUsersConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(CDSUsersConstant.CONFIG_CLIENT_ID);
        String cdsUsersClientSecret = CDSUsersConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(CDSUsersConstant.CONFIG_CLIENT_SECRET);
        String cdsUsersSwaggerAPIURL = CDSUsersConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(CDSUsersConstant.CONFIG_SWAGGER_API_URL);
        String cdsUsersTokenURL = CDSUsersConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(CDSUsersConstant.CONFIG_TOKEN_URL);
        CommonHTTPManager httpManager = CommonHTTPManager.refreshCommonHTTPManager();
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair("grant_type", "client_credentials"));
        parameters.add(new BasicNameValuePair("client_id", cdsUsersClientID));
        parameters.add(new BasicNameValuePair("client_secret", cdsUsersClientSecret));
        parameters.add(new BasicNameValuePair("scope", "openid offline_access"));
        CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponseMethod("POST", cdsUsersTokenURL, null, parameters, null, null);
        LOGGER.debug("got response status code " + commonHttpResponse.getResponseCode());
        String responseEntityString = commonHttpResponse.getResponseEntityString();
        LOGGER.debug("responseEntityString is " + responseEntityString);
        JsonObject responseEntityJsonObject = (JsonObject) ConverterUtils.parseStringToJson(responseEntityString);
        String accessToken = responseEntityJsonObject.get("access_token").getAsString();
        LOGGER.debug("accessToken is " + accessToken);
        CDSUsersConfigurationManager.getConfigurationManager().getLocalConfig().setProperty(CDSUsersConstant.ACCESS_TOKEN, accessToken);
        CDSUsersConfigurationManager.getConfigurationManager().storeLocalConfigProperties();
        Header authorizationHeader = createCDSUsersAuthenticationTokenHeader(accessToken);
        LOGGER.debug("created Authorization header");
        Header acceptHeader = createAcceptHeader();
        Header contentTypeHeader = createContentTypeHeader();
        Header[] headers = {acceptHeader, authorizationHeader, contentTypeHeader};
        CommonHttpResponse swaggerHttpResponse = httpManager.doCommonHttpResponseMethod("GET", cdsUsersSwaggerAPIURL, null, parameters, null, null);
        LOGGER.debug("got response status code " + swaggerHttpResponse.getResponseCode());
        String swaggerResponseEntityString = swaggerHttpResponse.getResponseEntityString();
        LOGGER.debug("swaggerResponseEntityString is " + swaggerResponseEntityString);
        JsonObject swaggerEntityJsonObject = (JsonObject) ConverterUtils.parseStringToJson(swaggerResponseEntityString);
        String host = swaggerEntityJsonObject.get("host").getAsString();
        LOGGER.debug("host is " + host);
        String basePath = swaggerEntityJsonObject.get("basePath").getAsString();
        LOGGER.debug("basePath is " + basePath);
        CDSUsersConfigurationManager.getConfigurationManager().getLocalConfig().setProperty(CDSUsersConstant.API_VERSION_URL, "https://" + host + basePath);
        CDSUsersConfigurationManager.getConfigurationManager().storeLocalConfigProperties();
    }

    public static Header createAcceptHeader() {
        String acceptHeader = "application/json";
        LOGGER.debug("acceptHeader is " + acceptHeader);
        return new BasicHeader("Accept", acceptHeader);
    }

    public static Header createContentTypeHeader() {
        String contentTypeHeader = "application/json";
        LOGGER.debug("contentTypeHeader is " + contentTypeHeader);
        return new BasicHeader("Content-Type", contentTypeHeader);
    }

    public static Header createCDSUsersAuthenticationTokenHeader(String accessToken) {
        return new BasicHeader(CDSUsersConfigurationManager.getConfigurationManager().getConfig().getProperty(CDSUsersConstant.CONFIG_API_AUTHENTICATION_HEADER), accessToken);
    }
}
