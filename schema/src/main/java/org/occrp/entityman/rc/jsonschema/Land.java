
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
 * Land
 * <p>
 * A parcel of land
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "parcel_number",
    "parcel_name",
    "parcel_area",
    "parcel_area_units",
    "usage_code",
    "usage_name"
})
public class Land
    extends Asset
{

    @JsonProperty("parcel_number")
    private String parcelNumber;
    @JsonProperty("parcel_name")
    private String parcelName;
    @JsonProperty("parcel_area")
    private Integer parcelArea;
    @JsonProperty("parcel_area_units")
    private String parcelAreaUnits;
    @JsonProperty("usage_code")
    private String usageCode;
    @JsonProperty("usage_name")
    private String usageName;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The parcelNumber
     */
    @JsonProperty("parcel_number")
    public String getParcelNumber() {
        return parcelNumber;
    }

    /**
     * 
     * @param parcelNumber
     *     The parcel_number
     */
    @JsonProperty("parcel_number")
    public void setParcelNumber(String parcelNumber) {
        this.parcelNumber = parcelNumber;
    }

    /**
     * 
     * @return
     *     The parcelName
     */
    @JsonProperty("parcel_name")
    public String getParcelName() {
        return parcelName;
    }

    /**
     * 
     * @param parcelName
     *     The parcel_name
     */
    @JsonProperty("parcel_name")
    public void setParcelName(String parcelName) {
        this.parcelName = parcelName;
    }

    /**
     * 
     * @return
     *     The parcelArea
     */
    @JsonProperty("parcel_area")
    public Integer getParcelArea() {
        return parcelArea;
    }

    /**
     * 
     * @param parcelArea
     *     The parcel_area
     */
    @JsonProperty("parcel_area")
    public void setParcelArea(Integer parcelArea) {
        this.parcelArea = parcelArea;
    }

    /**
     * 
     * @return
     *     The parcelAreaUnits
     */
    @JsonProperty("parcel_area_units")
    public String getParcelAreaUnits() {
        return parcelAreaUnits;
    }

    /**
     * 
     * @param parcelAreaUnits
     *     The parcel_area_units
     */
    @JsonProperty("parcel_area_units")
    public void setParcelAreaUnits(String parcelAreaUnits) {
        this.parcelAreaUnits = parcelAreaUnits;
    }

    /**
     * 
     * @return
     *     The usageCode
     */
    @JsonProperty("usage_code")
    public String getUsageCode() {
        return usageCode;
    }

    /**
     * 
     * @param usageCode
     *     The usage_code
     */
    @JsonProperty("usage_code")
    public void setUsageCode(String usageCode) {
        this.usageCode = usageCode;
    }

    /**
     * 
     * @return
     *     The usageName
     */
    @JsonProperty("usage_name")
    public String getUsageName() {
        return usageName;
    }

    /**
     * 
     * @param usageName
     *     The usage_name
     */
    @JsonProperty("usage_name")
    public void setUsageName(String usageName) {
        this.usageName = usageName;
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
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(parcelNumber).append(parcelName).append(parcelArea).append(parcelAreaUnits).append(usageCode).append(usageName).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Land) == false) {
            return false;
        }
        Land rhs = ((Land) other);
        return new EqualsBuilder().appendSuper(super.equals(other)).append(parcelNumber, rhs.parcelNumber).append(parcelName, rhs.parcelName).append(parcelArea, rhs.parcelArea).append(parcelAreaUnits, rhs.parcelAreaUnits).append(usageCode, rhs.usageCode).append(usageName, rhs.usageName).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
