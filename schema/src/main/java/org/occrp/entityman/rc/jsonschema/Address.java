
package org.occrp.entityman.rc.jsonschema;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * Address
 * <p>
 * An address object
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "text",
    "street_address",
    "locality",
    "region",
    "postal_code",
    "country"
})
public class Address {

    @JsonProperty("text")
    private Object text;
    @JsonProperty("street_address")
    private String streetAddress;
    @JsonProperty("locality")
    private String locality;
    @JsonProperty("region")
    private String region;
    @JsonProperty("postal_code")
    private String postalCode;
    @JsonProperty("country")
    private String country;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The text
     */
    @JsonProperty("text")
    public Object getText() {
        return text;
    }

    /**
     * 
     * @param text
     *     The text
     */
    @JsonProperty("text")
    public void setText(Object text) {
        this.text = text;
    }

    /**
     * 
     * @return
     *     The streetAddress
     */
    @JsonProperty("street_address")
    public String getStreetAddress() {
        return streetAddress;
    }

    /**
     * 
     * @param streetAddress
     *     The street_address
     */
    @JsonProperty("street_address")
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    /**
     * 
     * @return
     *     The locality
     */
    @JsonProperty("locality")
    public String getLocality() {
        return locality;
    }

    /**
     * 
     * @param locality
     *     The locality
     */
    @JsonProperty("locality")
    public void setLocality(String locality) {
        this.locality = locality;
    }

    /**
     * 
     * @return
     *     The region
     */
    @JsonProperty("region")
    public String getRegion() {
        return region;
    }

    /**
     * 
     * @param region
     *     The region
     */
    @JsonProperty("region")
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * 
     * @return
     *     The postalCode
     */
    @JsonProperty("postal_code")
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * 
     * @param postalCode
     *     The postal_code
     */
    @JsonProperty("postal_code")
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * 
     * @return
     *     The country
     */
    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    /**
     * 
     * @param country
     *     The country
     */
    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(text).append(streetAddress).append(locality).append(region).append(postalCode).append(country).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Address) == false) {
            return false;
        }
        Address rhs = ((Address) other);
        return new EqualsBuilder().append(text, rhs.text).append(streetAddress, rhs.streetAddress).append(locality, rhs.locality).append(region, rhs.region).append(postalCode, rhs.postalCode).append(country, rhs.country).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
