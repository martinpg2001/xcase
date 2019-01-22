/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.core;

/*
/// Interface for Web Proxy for CommonApi
 */
public interface ICommonApiWebProxy {

    void SyncBoardmembers(CompanyBoardMembersData[] body);

    void SyncShareholder(CompanyShareholdersData body);

    void SyncShareholders(CompanyShareholdersData[] body);

    void SetClientUsers(String clientId, ClientUserData[] body);

    void AddClientUsers(String clientId, ClientUserData[] body);

    void RemoveClientUsers(String clientId, ClientUserData[] body);

    void SetGroupRoles(String groupId, String[] roleNames);

    void AddGroupRoles(String groupId, String[] roleNames);

    void RemoveGroupRoles(String groupId, String[] roleNames);

    void SetGroupUsers(String groupId, String[] users);

    void AddGroupUsers(String groupId, String[] users);

    void RemoveGroupUsers(String groupId, String[] users);

    void SetMatterUsers(String matterId, MatterUserData[] body, String clientId);

    void AddMatterUsers(String matterId, MatterUserData[] body, String clientId);

    void RemoveMatterUsers(String matterId, MatterUserData[] body, String clientId);

    void SetRoleCapabilities(String roleName, String[] capabilities);

    void AddRoleCapabilities(String roleName, String[] capabilities);

    void RemoveRoleCapabilities(String roleName, String[] capabilities);

    void SetUserRoles(String userId, String[] roleNames);

    void AddUserRoles(String userId, String[] roleNames);

    void RemoveUserRoles(String userId, String[] roleNames);

    void ChangePassword(String userId, String password);

    ClientData CreateClient(CreateClientData body);

    void CreateClients(CreateClientData[] body);

    void UpdateClients(UpdateClientData[] body);

    int CreateGroup(CreateGroupData body);

    int CreateMatter(CreateMatterData body);

    void CreateMatters(CreateMatterData[] body);

    void UpdateMatters(UpdateMatterData[] body);

    GetEntitySecurityData[] GetClientSecurity(String clientId);

    void CreateOrReplaceClientSecurity(String clientId, CreateEntitySecurityData[] body);

    void DeleteClientSecurity(String clientId);

    GetEntitySecurityData[] GetMatterSecurity(String matterId, String clientId);

    void CreateOrReplaceMatterSecurity(String matterId, CreateEntitySecurityData[] body, String clientId);

    void DeleteMatterSecurity(String matterId, String clientId);

    void CreateParties(CreatePartyData[] body);

    int CreateParty(CreatePartyData body);

    int CreateRole(CreateRoleData body);

    int CreateUser(CreateUserData body);

    LookupData[] GetOffices();

    void CreateOffice(CreateOfficeData body);

    void UpdateOffice(String key, UpdateOfficeData body);

    IndustryCodeData[] GetStandardIndustryCodes();

    void CreateStandardIndustryCode(CreateIndustryCodeData body);

    void UpdateStandardIndustryCode(String key, UpdateIndustryCodeData body);

    IndustrySectorData[] GetIndustrySectors();

    void CreateIndustrySector(CreateIndustrySectorData body);

    void UpdateIndustrySector(String key, UpdateIndustrySectorData body);

    ClientUserTypeData[] GetClientUserTypes();

    void CreateClientUserType(CreateClientUserTypeData body);

    void UpdateClientUserType(String key, UpdateClientUserTypeData body);

    MatterUserTypeData[] GetMatterUserTypes();

    void CreateMatterUserType(CreateMatterUserTypeData body);

    void UpdateMatterUserType(String key, UpdateMatterUserTypeData body);

    PartyTypeData[] GetPartyTypes();

    LookupData[] GetClientStatuses();

    void CreateClientStatus(ClientStatusData body);

    void UpdateClientStatus(String key, ClientStatusData body);

    LookupData[] GetMatterStatuses();

    void CreateMatterStatus(MatterStatusData body);

    void UpdateMatterStatus(String key, MatterStatusData body);

    PracticeAreaData[] GetPracticeAreas();

    void CreatePracticeArea(CreatePracticeAreaData body);

    void UpdatePracticeArea(String key, UpdatePracticeAreaData body);

    AddressTypeData[] GetAddressTypes();

    void CreateAddressType(CreateAddressTypeData body);

    void UpdateAddressType(String key, UpdateAddressTypeData body);

    DepartmentData[] GetDepartments();

    void CreateDepartment(CreateDepartmentData body);

    void UpdateDepartment(String key, UpdateDepartmentData body);

    UserTitleData[] GetUserTitles();

    void CreateUserTitle(CreateUserTitleData body);

    void UpdateUserTitle(String key, UpdateUserTitleData body);

    void ActivateUser(String userId);

    void DeactivateUser(String userId);

    void DeleteClientSecurityForPrincipal(String clientId, String principalId, DeleteClientSecurityForPrincipalprincipalType principalType);

    void DeleteMatterSecurityForPrincipal(String matterId, String principalId, String clientId, DeleteMatterSecurityForPrincipalprincipalType principalType);

    void DisableUserLogin(String userId);

    void EnableUserLogin(String userId);

    ClientData GetClient(String clientId, String[] properties);

    void UpdateClient(String clientId, UpdateClientData body);

    GroupData GetGroup(String groupId, String[] properties);

    void UpdateGroup(String groupId, UpdateGroupData body);

    MatterData GetMatter(String matterId, String[] properties, String clientId);

    void UpdateMatter(String matterId, UpdateMatterData body, String clientId);

    PartyData GetParty(String partyId, String[] properties);

    void UpdateParty(String partyId, UpdatePartyData body);

    UserData GetUser(String userId);

    void UpdateUser(String userId, UpdateUserData body);

    RoleData GetRole(String roleName);

    void UpdateRole(String roleName, UpdateRoleData body);

    void SetUserDepartments(String userId, String[] departments);

    void SetUserImage(String userId, String image);

    void SetUserPracticeAreas(String userId, String[] practiceAreas);

    void SyncCorporateTree(CorporateTreeData body);

    void SyncCorporateTrees(CorporateTreeData[] body);

    void SyncBoardmember(CompanyBoardMembersData body);
}
