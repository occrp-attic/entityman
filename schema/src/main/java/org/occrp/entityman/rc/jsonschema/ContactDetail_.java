
package org.occrp.entityman.rc.jsonschema;

import java.util.ArrayList;
import java.util.Date;
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
 * Contact detail
 * <p>
 * A means of contacting an entity
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "label",
    "type",
    "value",
    "note",
    "valid_from",
    "valid_until",
    "created_at",
    "updated_at",
    "sources"
})
public class ContactDetail_ {

    /**
     * A human-readable label for the contact detail
     * 
     */
    @JsonProperty("label")
    private String label;
    /**
     * A type of medium, e.g. 'fax' or 'email'
     * (Required)
     * 
     */
    @JsonProperty("type")
    private String type;
    /**
     * A value, e.g. a phone number or email address
     * (Required)
     * 
     */
    @JsonProperty("value")
    private String value;
    /**
     * A note, e.g. for grouping contact details by physical location
     * 
     */
    @JsonProperty("note")
    private String note;
    /**
     * The date from which the contact detail is valid
     * 
     */
    @JsonProperty("valid_from")
    private String validFrom;
    /**
     * The date from which the contact detail is no longer valid
     * 
     */
    @JsonProperty("valid_until")
    private String validUntil;
    /**
     * The time at which the resource was created
     * 
     */
    @JsonProperty("created_at")
    private Date createdAt;
    /**
     * The time at which the resource was last modified
     * 
     */
    @JsonProperty("updated_at")
    private Date updatedAt;
    /**
     * URLs to documents from which the resource is derived
     * 
     */
    @JsonProperty("sources")
    private List<Link> sources = new ArrayList<Link>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * A human-readable label for the contact detail
     * 
     * @return
     *     The label
     */
    @JsonProperty("label")
    public String getLabel() {
        return label;
    }

    /**
     * A human-readable label for the contact detail
     * 
     * @param label
     *     The label
     */
    @JsonProperty("label")
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * A type of medium, e.g. 'fax' or 'email'
     * (Required)
     * 
     * @return
     *     The type
     */
    @JsonProperty("type")
    public String getType() {
        return type;
    }

    /**
     * A type of medium, e.g. 'fax' or 'email'
     * (Required)
     * 
     * @param type
     *     The type
     */
    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    /**
     * A value, e.g. a phone number or email address
     * (Required)
     * 
     * @return
     *     The value
     */
    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    /**
     * A value, e.g. a phone number or email address
     * (Required)
     * 
     * @param value
     *     The value
     */
    @JsonProperty("value")
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * A note, e.g. for grouping contact details by physical location
     * 
     * @return
     *     The note
     */
    @JsonProperty("note")
    public String getNote() {
        return note;
    }

    /**
     * A note, e.g. for grouping contact details by physical location
     * 
     * @param note
     *     The note
     */
    @JsonProperty("note")
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * The date from which the contact detail is valid
     * 
     * @return
     *     The validFrom
     */
    @JsonProperty("valid_from")
    public String getValidFrom() {
        return validFrom;
    }

    /**
     * The date from which the contact detail is valid
     * 
     * @param validFrom
     *     The valid_from
     */
    @JsonProperty("valid_from")
    public void setValidFrom(String validFrom) {
        this.validFrom = validFrom;
    }

    /**
     * The date from which the contact detail is no longer valid
     * 
     * @return
     *     The validUntil
     */
    @JsonProperty("valid_until")
    public String getValidUntil() {
        return validUntil;
    }

    /**
     * The date from which the contact detail is no longer valid
     * 
     * @param validUntil
     *     The valid_until
     */
    @JsonProperty("valid_until")
    public void setValidUntil(String validUntil) {
        this.validUntil = validUntil;
    }

    /**
     * The time at which the resource was created
     * 
     * @return
     *     The createdAt
     */
    @JsonProperty("created_at")
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * The time at which the resource was created
     * 
     * @param createdAt
     *     The created_at
     */
    @JsonProperty("created_at")
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * The time at which the resource was last modified
     * 
     * @return
     *     The updatedAt
     */
    @JsonProperty("updated_at")
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * The time at which the resource was last modified
     * 
     * @param updatedAt
     *     The updated_at
     */
    @JsonProperty("updated_at")
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * URLs to documents from which the resource is derived
     * 
     * @return
     *     The sources
     */
    @JsonProperty("sources")
    public List<Link> getSources() {
        return sources;
    }

    /**
     * URLs to documents from which the resource is derived
     * 
     * @param sources
     *     The sources
     */
    @JsonProperty("sources")
    public void setSources(List<Link> sources) {
        this.sources = sources;
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
        return new HashCodeBuilder().append(label).append(type).append(value).append(note).append(validFrom).append(validUntil).append(createdAt).append(updatedAt).append(sources).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ContactDetail_) == false) {
            return false;
        }
        ContactDetail_ rhs = ((ContactDetail_) other);
        return new EqualsBuilder().append(label, rhs.label).append(type, rhs.type).append(value, rhs.value).append(note, rhs.note).append(validFrom, rhs.validFrom).append(validUntil, rhs.validUntil).append(createdAt, rhs.createdAt).append(updatedAt, rhs.updatedAt).append(sources, rhs.sources).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
