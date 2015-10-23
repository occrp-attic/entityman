
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
 * Any entity
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "id",
    "name",
    "identifiers",
    "other_names",
    "links",
    "created_at",
    "updated_at",
    "sources"
})
public class Any {

    /**
     * ID
     * <p>
     * The person's unique identifier
     * 
     */
    @JsonProperty("id")
    private String id;
    /**
     * Name
     * <p>
     * A primary name, e.g. a legally recognized name
     * (Required)
     * 
     */
    @JsonProperty("name")
    private String name;
    /**
     * Identifiers
     * <p>
     * 
     * 
     */
    @JsonProperty("identifiers")
    private List<Identifier> identifiers = new ArrayList<Identifier>();
    /**
     * Alternate or former names
     * 
     */
    @JsonProperty("other_names")
    private List<OtherName> otherNames = new ArrayList<OtherName>();
    /**
     * Related links
     * <p>
     * URLs to documents about the person
     * 
     */
    @JsonProperty("links")
    private List<Link> links = new ArrayList<Link>();
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
     * Name of the source from which data was extracted
     * 
     */
    @JsonProperty("sources")
    private List<String> sources = new ArrayList<String>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * ID
     * <p>
     * The person's unique identifier
     * 
     * @return
     *     The id
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * ID
     * <p>
     * The person's unique identifier
     * 
     * @param id
     *     The id
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Name
     * <p>
     * A primary name, e.g. a legally recognized name
     * (Required)
     * 
     * @return
     *     The name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * Name
     * <p>
     * A primary name, e.g. a legally recognized name
     * (Required)
     * 
     * @param name
     *     The name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Identifiers
     * <p>
     * 
     * 
     * @return
     *     The identifiers
     */
    @JsonProperty("identifiers")
    public List<Identifier> getIdentifiers() {
        return identifiers;
    }

    /**
     * Identifiers
     * <p>
     * 
     * 
     * @param identifiers
     *     The identifiers
     */
    @JsonProperty("identifiers")
    public void setIdentifiers(List<Identifier> identifiers) {
        this.identifiers = identifiers;
    }

    /**
     * Alternate or former names
     * 
     * @return
     *     The otherNames
     */
    @JsonProperty("other_names")
    public List<OtherName> getOtherNames() {
        return otherNames;
    }

    /**
     * Alternate or former names
     * 
     * @param otherNames
     *     The other_names
     */
    @JsonProperty("other_names")
    public void setOtherNames(List<OtherName> otherNames) {
        this.otherNames = otherNames;
    }

    /**
     * Related links
     * <p>
     * URLs to documents about the person
     * 
     * @return
     *     The links
     */
    @JsonProperty("links")
    public List<Link> getLinks() {
        return links;
    }

    /**
     * Related links
     * <p>
     * URLs to documents about the person
     * 
     * @param links
     *     The links
     */
    @JsonProperty("links")
    public void setLinks(List<Link> links) {
        this.links = links;
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
     * Name of the source from which data was extracted
     * 
     * @return
     *     The sources
     */
    @JsonProperty("sources")
    public List<String> getSources() {
        return sources;
    }

    /**
     * Name of the source from which data was extracted
     * 
     * @param sources
     *     The sources
     */
    @JsonProperty("sources")
    public void setSources(List<String> sources) {
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
        return new HashCodeBuilder().append(id).append(name).append(identifiers).append(otherNames).append(links).append(createdAt).append(updatedAt).append(sources).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Any) == false) {
            return false;
        }
        Any rhs = ((Any) other);
        return new EqualsBuilder().append(id, rhs.id).append(name, rhs.name).append(identifiers, rhs.identifiers).append(otherNames, rhs.otherNames).append(links, rhs.links).append(createdAt, rhs.createdAt).append(updatedAt, rhs.updatedAt).append(sources, rhs.sources).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
