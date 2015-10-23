
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
 * Identifier
 * <p>
 * An issued identifier
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "identifier",
    "scheme"
})
public class Identifier_ {

    /**
     * An issued identifier, e.g. a DUNS number
     * (Required)
     * 
     */
    @JsonProperty("identifier")
    private String identifier;
    /**
     * An identifier scheme, e.g. DUNS
     * 
     */
    @JsonProperty("scheme")
    private String scheme;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * An issued identifier, e.g. a DUNS number
     * (Required)
     * 
     * @return
     *     The identifier
     */
    @JsonProperty("identifier")
    public String getIdentifier() {
        return identifier;
    }

    /**
     * An issued identifier, e.g. a DUNS number
     * (Required)
     * 
     * @param identifier
     *     The identifier
     */
    @JsonProperty("identifier")
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * An identifier scheme, e.g. DUNS
     * 
     * @return
     *     The scheme
     */
    @JsonProperty("scheme")
    public String getScheme() {
        return scheme;
    }

    /**
     * An identifier scheme, e.g. DUNS
     * 
     * @param scheme
     *     The scheme
     */
    @JsonProperty("scheme")
    public void setScheme(String scheme) {
        this.scheme = scheme;
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
        return new HashCodeBuilder().append(identifier).append(scheme).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Identifier_) == false) {
            return false;
        }
        Identifier_ rhs = ((Identifier_) other);
        return new EqualsBuilder().append(identifier, rhs.identifier).append(scheme, rhs.scheme).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
