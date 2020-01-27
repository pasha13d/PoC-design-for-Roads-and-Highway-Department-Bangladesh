package com.secl.eservice.authentication.registration.model;

import java.util.Date;
import com.google.gson.annotations.Expose;
import com.secl.eservice.util.constant.Constant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveRegistration {

	private @Expose String userName, password, nameBn, nameEn, designation, fatherName, motherName, gender, email, mobileNo, nidNo, presentAddress, permanentAddress, status;
	
//	@JsonSerialize(using=DateTimeSerializer.class)
//	@JsonDeserialize(using=DateTimeDeserializer.class)
	private Date dateOfBirth;
	
    @Override
    public String toString() {
    	return Constant.print(this);
    }
}
