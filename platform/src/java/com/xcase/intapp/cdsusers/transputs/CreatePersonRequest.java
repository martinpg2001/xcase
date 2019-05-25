package com.xcase.intapp.cdsusers.transputs;

/**
 * Use this interface to create a person using the CDS service.
 * @author martinpg
 *
 */
public interface CreatePersonRequest extends CDSUsersRequest {
    /**
     * Returns the URL to the create person operation.
     * @return the operation URL
     */
    public String getOperationPath();

    /**
     * Returns the entity string used to create the person. For example: 
     * {"firstName":"Dennis","middleName":"Philip","lastName":"Gilchrist","name":"Dennis Gilchrist","titles":[],"email":"dennis.gilchrist@intapp.com","costPoolId":null,"addresses":[],"communications":[],"employee":true,"department":null,"office":null,"practiceAreas":[],"externalIds":[]}
     * @return the entity string
     */
	public String getPersonString();

	public void setPersonString(String person);
}
