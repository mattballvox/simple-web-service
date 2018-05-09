package com.matt.tech.service.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * A Model Object that aims to make the requests for sending text messages as
 * generic as possible.
 *
 * @author mball
 */
@ApiModel
@XmlRootElement(name = "customerDetails")
public class CustomerRequest {

    /**
     * Name
     */
    private String name;
    /**
     * Username
     *
     */
    private String username;
    /**
     * Password
     */
    private String password;
    /**
     * VIPLevel
     */
    private int vipLevel;
    /**
     * AccountVerified
     */
    private boolean accountVerified;

    public CustomerRequest() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setVipLevel(int vipLevel) {
        this.vipLevel = vipLevel;
    }

    public void setAccountVerified(boolean accountVerified) {
        this.accountVerified = accountVerified;
    }

    @XmlElement(name = "customerName")
    public String getName() {
        return name;
    }

    @XmlElement(name = "customerUsername")
    @ApiModelProperty(dataType = "String", required = true)
    public String getUsername() {
        return username;
    }

    @XmlElement(name = "customerPassword")
    @ApiModelProperty(dataType = "String", required = true)
    public String getPassword() {
        return password;
    }

    @XmlElement(name = "customerLevel")
    public int getVipLevel() {
        return vipLevel;
    }

    @XmlElement(name = "customerVerified")
    public boolean getAccountVerified() {
        return accountVerified;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(name)
                .append(username)
                .append(password)
                .append(vipLevel)
                .append(accountVerified)
                .hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CustomerRequest) {
            final CustomerRequest incomingRequest = (CustomerRequest) obj;
            return new EqualsBuilder()
                    .append(name, incomingRequest.name)
                    .append(username, incomingRequest.username)
                    .append(password, incomingRequest.password)
                    .append(vipLevel, incomingRequest.vipLevel)
                    .append(accountVerified, incomingRequest.accountVerified)
                    .isEquals();
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("name", name)
                .append("username", username)
                .append("password", password)
                .append("vipLevel", vipLevel)
                .append("accountVerified", accountVerified)
                .toString();
    }
}
