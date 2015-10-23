
package org.occrp.entityman.rc.jsonschema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
 * Asset
 * <p>
 * An asset owned by one or several owners
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "register_name",
    "description",
    "valuation",
    "valuation_currency",
    "valuation_date",
    "owners"
})
public class Asset
    extends Any
{

    @JsonProperty("register_name")
    private String registerName;
    @JsonProperty("description")
    private String description;
    @JsonProperty("valuation")
    private Integer valuation;
    @JsonProperty("valuation_currency")
    private String valuationCurrency;
    @JsonProperty("valuation_date")
    private String valuationDate;
    @JsonProperty("owners")
    private List<Owner> owners = new ArrayList<Owner>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The registerName
     */
    @JsonProperty("register_name")
    public String getRegisterName() {
        return registerName;
    }

    /**
     * 
     * @param registerName
     *     The register_name
     */
    @JsonProperty("register_name")
    public void setRegisterName(String registerName) {
        this.registerName = registerName;
    }

    /**
     * 
     * @return
     *     The description
     */
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *     The description
     */
    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     * @return
     *     The valuation
     */
    @JsonProperty("valuation")
    public Integer getValuation() {
        return valuation;
    }

    /**
     * 
     * @param valuation
     *     The valuation
     */
    @JsonProperty("valuation")
    public void setValuation(Integer valuation) {
        this.valuation = valuation;
    }

    /**
     * 
     * @return
     *     The valuationCurrency
     */
    @JsonProperty("valuation_currency")
    public String getValuationCurrency() {
        return valuationCurrency;
    }

    /**
     * 
     * @param valuationCurrency
     *     The valuation_currency
     */
    @JsonProperty("valuation_currency")
    public void setValuationCurrency(String valuationCurrency) {
        this.valuationCurrency = valuationCurrency;
    }

    /**
     * 
     * @return
     *     The valuationDate
     */
    @JsonProperty("valuation_date")
    public String getValuationDate() {
        return valuationDate;
    }

    /**
     * 
     * @param valuationDate
     *     The valuation_date
     */
    @JsonProperty("valuation_date")
    public void setValuationDate(String valuationDate) {
        this.valuationDate = valuationDate;
    }

    /**
     * 
     * @return
     *     The owners
     */
    @JsonProperty("owners")
    public List<Owner> getOwners() {
        return owners;
    }

    /**
     * 
     * @param owners
     *     The owners
     */
    @JsonProperty("owners")
    public void setOwners(List<Owner> owners) {
        this.owners = owners;
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
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(registerName).append(description).append(valuation).append(valuationCurrency).append(valuationDate).append(owners).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Asset) == false) {
            return false;
        }
        Asset rhs = ((Asset) other);
        return new EqualsBuilder().appendSuper(super.equals(other)).append(registerName, rhs.registerName).append(description, rhs.description).append(valuation, rhs.valuation).append(valuationCurrency, rhs.valuationCurrency).append(valuationDate, rhs.valuationDate).append(owners, rhs.owners).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
