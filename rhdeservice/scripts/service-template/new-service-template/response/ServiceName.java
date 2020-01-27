package <ServiceNameResponsePackage>.response;

import com.google.gson.annotations.Expose;

import com.secl.eservice.util.constant.Constant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class <ServiceName> {

    private @Expose String oid, nameEn, nameBn;

    @Override
    public String toString() {
    	return Constant.print(this);
    }

}
