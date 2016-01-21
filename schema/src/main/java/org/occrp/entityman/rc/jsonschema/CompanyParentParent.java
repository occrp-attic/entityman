
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
 * Legal Person
 * <p>
 * An individual or company
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "jurisdiction_code",
    "assets",
    "memberships"
})
public class CompanyParentParent
    extends Any
{

    /**
     * Jurisdiction
     * <p>
     * Code representing the jurisdiction/company register which is the canonical record of the company’s existence. Uses underscored ISO 3166-2 to represent it, e.g. es for Spain, us_de for Delaware
     * 
     */
    @JsonProperty("jurisdiction_code")
    private String jurisdictionCode;
    @JsonProperty("assets")
    private List<Asset__> assets = new ArrayList<Asset__>();
    /**
     * Memberships
     * <p>
     * The person's memberships
     * 
     */
    @JsonProperty("memberships")
    private List<Membership> memberships = new ArrayList<Membership>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * Jurisdiction
     * <p>
     * Code representing the jurisdiction/company register which is the canonical record of the company’s existence. Uses underscored ISO 3166-2 to represent it, e.g. es for Spain, us_de for Delaware
     * 
     * @return
     *     The jurisdictionCode
     */
    @JsonProperty("jurisdiction_code")
    public String getJurisdictionCode() {
        return jurisdictionCode;
    }

    /**
     * Jurisdiction
     * <p>
     * Code representing the jurisdiction/company register which is the canonical record of the company’s existence. Uses underscored ISO 3166-2 to represent it, e.g. es for Spain, us_de for Delaware
     * 
     * @param jurisdictionCode
     *     The jurisdiction_code
     */
    @JsonProperty("jurisdiction_code")
    public void setJurisdictionCode(String jurisdictionCode) {
        this.jurisdictionCode = jurisdictionCode;
    }

    /**
     * 
     * @return
     *     The assets
     */
    @JsonProperty("assets")
    public List<Asset__> getAssets() {
        return assets;
    }

    /**
     * 
     * @param assets
     *     The assets
     */
    @JsonProperty("assets")
    public void setAssets(List<Asset__> assets) {
        this.assets = assets;
    }

    /**
     * Memberships
     * <p>
     * The person's memberships
     * 
     * @return
     *     The memberships
     */
    @JsonProperty("memberships")
    public List<Membership> getMemberships() {
        return memberships;
    }

    /**
     * Memberships
     * <p>
     * The person's memberships
     * 
     * @param memberships
     *     The memberships
     */
    @JsonProperty("memberships")
    public void setMemberships(List<Membership> memberships) {
        this.memberships = memberships;
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
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(jurisdictionCode).append(assets).append(memberships).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof CompanyParentParent) == false) {
            return false;
        }
        CompanyParentParent rhs = ((CompanyParentParent) other);
        return new EqualsBuilder().appendSuper(super.equals(other)).append(jurisdictionCode, rhs.jurisdictionCode).append(assets, rhs.assets).append(memberships, rhs.memberships).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
