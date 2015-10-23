
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
    "assets",
    "memberships"
})
public class Owner {

    @JsonProperty("assets")
    private List<Asset_> assets = new ArrayList<Asset_>();
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
     * 
     * @return
     *     The assets
     */
    @JsonProperty("assets")
    public List<Asset_> getAssets() {
        return assets;
    }

    /**
     * 
     * @param assets
     *     The assets
     */
    @JsonProperty("assets")
    public void setAssets(List<Asset_> assets) {
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
        return new HashCodeBuilder().append(assets).append(memberships).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Owner) == false) {
            return false;
        }
        Owner rhs = ((Owner) other);
        return new EqualsBuilder().append(assets, rhs.assets).append(memberships, rhs.memberships).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
