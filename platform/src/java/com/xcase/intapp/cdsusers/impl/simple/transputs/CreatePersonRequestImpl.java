package com.xcase.intapp.cdsusers.impl.simple.transputs;

import com.xcase.intapp.cdsusers.objects.PersonPostDTO;
import com.xcase.intapp.cdsusers.transputs.CreatePersonRequest;

public class CreatePersonRequestImpl extends CDSUsersRequestImpl implements CreatePersonRequest {
    private PersonPostDTO personPostDTO;
    private String operationPath = "api/v1/cds/persons";
    private String personString;
    
    public String getOperationPath() {
        return operationPath;
    }
    
    public String getPersonString() {
        return personString;
    }
    
    public void setOperationPath(String operationPath) {
        this.operationPath = operationPath;
    }

	@Override
	public void setPersonString(String personString) {
		this.personString = personString;
	}

    @Override
    public PersonPostDTO getPerson() {
        return personPostDTO;
    }

    @Override
    public void setPerson(PersonPostDTO personPostDTO) {
        this.personPostDTO = personPostDTO;
    }
}
