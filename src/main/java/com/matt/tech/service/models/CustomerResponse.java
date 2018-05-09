
package com.matt.tech.service.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * A Model Object that is to be built using the response data we get from the 3rd party service.
 *
 * @author mball
 * 
 */
@ApiModel
public class CustomerResponse {

    private boolean success;
    
    private String response;

    public CustomerResponse() {

    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    public void setResponse(String response) {
        this.response = response;
    }

    @ApiModelProperty(dataType = "Boolean", required = true)
    public boolean getSuccess() {
        return success;
    }

    @ApiModelProperty(dataType = "String", required = true)
    public String getResponse() {
        return response;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(success)
                .append(response)
                .hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CustomerResponse) {
            CustomerResponse sendCustomerResponse = (CustomerResponse) obj;
            return new EqualsBuilder()
                    .append(success, sendCustomerResponse.success)
                    .append(response, sendCustomerResponse.response)
                    .isEquals();
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("success", success)
                .append("response", response)
                .toString();
    }
}
