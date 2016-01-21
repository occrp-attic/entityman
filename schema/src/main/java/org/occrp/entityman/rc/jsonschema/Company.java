
package org.occrp.entityman.rc.jsonschema;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * Company
 * <p>
 * A company in OpenCorporates
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "company_number",
    "incorporation_date",
    "retrieved_at",
    "current_status",
    "company_type",
    "registry_url",
    "website",
    "telephone_number",
    "fax_number",
    "registered_address",
    "headquarters_address",
    "mailing_address",
    "filings",
    "industry_codes"
})
public class Company
    extends Organization
{

    /**
     * Company number
     * <p>
     * unique identifier given by the corporate register with which it is incorporated
     * 
     */
    @JsonProperty("company_number")
    private String companyNumber;
    @JsonProperty("incorporation_date")
    private String incorporationDate;
    @JsonProperty("retrieved_at")
    private Date retrievedAt;
    @JsonProperty("current_status")
    private String currentStatus;
    @JsonProperty("company_type")
    private String companyType;
    @JsonProperty("registry_url")
    private URI registryUrl;
    @JsonProperty("website")
    private String website;
    @JsonProperty("telephone_number")
    private String telephoneNumber;
    @JsonProperty("fax_number")
    private String faxNumber;
    /**
     * Address
     * <p>
     * An address object
     * 
     */
    @JsonProperty("registered_address")
    private Address registeredAddress;
    /**
     * Address
     * <p>
     * An address object
     * 
     */
    @JsonProperty("headquarters_address")
    private Address headquartersAddress;
    /**
     * Address
     * <p>
     * An address object
     * 
     */
    @JsonProperty("mailing_address")
    private Address mailingAddress;
    @JsonProperty("filings")
    private List<Filing> filings = new ArrayList<Filing>();
    @JsonProperty("industry_codes")
    private List<IndustryCode> industryCodes = new ArrayList<IndustryCode>();

    /**
     * Company number
     * <p>
     * unique identifier given by the corporate register with which it is incorporated
     * 
     * @return
     *     The companyNumber
     */
    @JsonProperty("company_number")
    public String getCompanyNumber() {
        return companyNumber;
    }

    /**
     * Company number
     * <p>
     * unique identifier given by the corporate register with which it is incorporated
     * 
     * @param companyNumber
     *     The company_number
     */
    @JsonProperty("company_number")
    public void setCompanyNumber(String companyNumber) {
        this.companyNumber = companyNumber;
    }

    /**
     * 
     * @return
     *     The incorporationDate
     */
    @JsonProperty("incorporation_date")
    public String getIncorporationDate() {
        return incorporationDate;
    }

    /**
     * 
     * @param incorporationDate
     *     The incorporation_date
     */
    @JsonProperty("incorporation_date")
    public void setIncorporationDate(String incorporationDate) {
        this.incorporationDate = incorporationDate;
    }

    /**
     * 
     * @return
     *     The retrievedAt
     */
    @JsonProperty("retrieved_at")
    public Date getRetrievedAt() {
        return retrievedAt;
    }

    /**
     * 
     * @param retrievedAt
     *     The retrieved_at
     */
    @JsonProperty("retrieved_at")
    public void setRetrievedAt(Date retrievedAt) {
        this.retrievedAt = retrievedAt;
    }

    /**
     * 
     * @return
     *     The currentStatus
     */
    @JsonProperty("current_status")
    public String getCurrentStatus() {
        return currentStatus;
    }

    /**
     * 
     * @param currentStatus
     *     The current_status
     */
    @JsonProperty("current_status")
    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    /**
     * 
     * @return
     *     The companyType
     */
    @JsonProperty("company_type")
    public String getCompanyType() {
        return companyType;
    }

    /**
     * 
     * @param companyType
     *     The company_type
     */
    @JsonProperty("company_type")
    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    /**
     * 
     * @return
     *     The registryUrl
     */
    @JsonProperty("registry_url")
    public URI getRegistryUrl() {
        return registryUrl;
    }

    /**
     * 
     * @param registryUrl
     *     The registry_url
     */
    @JsonProperty("registry_url")
    public void setRegistryUrl(URI registryUrl) {
        this.registryUrl = registryUrl;
    }

    /**
     * 
     * @return
     *     The website
     */
    @JsonProperty("website")
    public String getWebsite() {
        return website;
    }

    /**
     * 
     * @param website
     *     The website
     */
    @JsonProperty("website")
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * 
     * @return
     *     The telephoneNumber
     */
    @JsonProperty("telephone_number")
    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    /**
     * 
     * @param telephoneNumber
     *     The telephone_number
     */
    @JsonProperty("telephone_number")
    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    /**
     * 
     * @return
     *     The faxNumber
     */
    @JsonProperty("fax_number")
    public String getFaxNumber() {
        return faxNumber;
    }

    /**
     * 
     * @param faxNumber
     *     The fax_number
     */
    @JsonProperty("fax_number")
    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    /**
     * Address
     * <p>
     * An address object
     * 
     * @return
     *     The registeredAddress
     */
    @JsonProperty("registered_address")
    public Address getRegisteredAddress() {
        return registeredAddress;
    }

    /**
     * Address
     * <p>
     * An address object
     * 
     * @param registeredAddress
     *     The registered_address
     */
    @JsonProperty("registered_address")
    public void setRegisteredAddress(Address registeredAddress) {
        this.registeredAddress = registeredAddress;
    }

    /**
     * Address
     * <p>
     * An address object
     * 
     * @return
     *     The headquartersAddress
     */
    @JsonProperty("headquarters_address")
    public Address getHeadquartersAddress() {
        return headquartersAddress;
    }

    /**
     * Address
     * <p>
     * An address object
     * 
     * @param headquartersAddress
     *     The headquarters_address
     */
    @JsonProperty("headquarters_address")
    public void setHeadquartersAddress(Address headquartersAddress) {
        this.headquartersAddress = headquartersAddress;
    }

    /**
     * Address
     * <p>
     * An address object
     * 
     * @return
     *     The mailingAddress
     */
    @JsonProperty("mailing_address")
    public Address getMailingAddress() {
        return mailingAddress;
    }

    /**
     * Address
     * <p>
     * An address object
     * 
     * @param mailingAddress
     *     The mailing_address
     */
    @JsonProperty("mailing_address")
    public void setMailingAddress(Address mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    /**
     * 
     * @return
     *     The filings
     */
    @JsonProperty("filings")
    public List<Filing> getFilings() {
        return filings;
    }

    /**
     * 
     * @param filings
     *     The filings
     */
    @JsonProperty("filings")
    public void setFilings(List<Filing> filings) {
        this.filings = filings;
    }

    /**
     * 
     * @return
     *     The industryCodes
     */
    @JsonProperty("industry_codes")
    public List<IndustryCode> getIndustryCodes() {
        return industryCodes;
    }

    /**
     * 
     * @param industryCodes
     *     The industry_codes
     */
    @JsonProperty("industry_codes")
    public void setIndustryCodes(List<IndustryCode> industryCodes) {
        this.industryCodes = industryCodes;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(companyNumber).append(incorporationDate).append(retrievedAt).append(currentStatus).append(companyType).append(registryUrl).append(website).append(telephoneNumber).append(faxNumber).append(registeredAddress).append(headquartersAddress).append(mailingAddress).append(filings).append(industryCodes).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Company) == false) {
            return false;
        }
        Company rhs = ((Company) other);
        return new EqualsBuilder().appendSuper(super.equals(other)).append(companyNumber, rhs.companyNumber).append(incorporationDate, rhs.incorporationDate).append(retrievedAt, rhs.retrievedAt).append(currentStatus, rhs.currentStatus).append(companyType, rhs.companyType).append(registryUrl, rhs.registryUrl).append(website, rhs.website).append(telephoneNumber, rhs.telephoneNumber).append(faxNumber, rhs.faxNumber).append(registeredAddress, rhs.registeredAddress).append(headquartersAddress, rhs.headquartersAddress).append(mailingAddress, rhs.mailingAddress).append(filings, rhs.filings).append(industryCodes, rhs.industryCodes).isEquals();
    }

}
