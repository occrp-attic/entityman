
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
 * Membership
 * <p>
 * A relationship between a member and an organization
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "id",
    "label",
    "role",
    "person",
    "organization",
    "start_date",
    "end_date",
    "contact_details",
    "links",
    "created_at",
    "updated_at",
    "sources"
})
public class Membership {

    /**
     * The membership's unique identifier
     * 
     */
    @JsonProperty("id")
    private String id;
    /**
     * Label
     * <p>
     * A label describing the membership
     * 
     */
    @JsonProperty("label")
    private String label;
    /**
     * Role
     * <p>
     * The role that the member fulfills in the organization
     * 
     */
    @JsonProperty("role")
    private String role;
    /**
     * Legal Person
     * <p>
     * An individual or company
     * 
     */
    @JsonProperty("person")
    private Owner person;
    /**
     * Organization
     * <p>
     * A group with a common purpose or reason for existence that goes beyond the set of people belonging to it
     * 
     */
    @JsonProperty("organization")
    private Organization organization;
    /**
     * The date on which the relationship began
     * 
     */
    @JsonProperty("start_date")
    private String startDate;
    /**
     * The date on which the relationship ended
     * 
     */
    @JsonProperty("end_date")
    private String endDate;
    /**
     * Means of contacting the member of the organization
     * 
     */
    @JsonProperty("contact_details")
    private List<ContactDetail> contactDetails = new ArrayList<ContactDetail>();
    /**
     * URLs to documents about the membership
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
     * URLs to documents from which the resource is derived
     * 
     */
    @JsonProperty("sources")
    private List<Link> sources = new ArrayList<Link>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * The membership's unique identifier
     * 
     * @return
     *     The id
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The membership's unique identifier
     * 
     * @param id
     *     The id
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Label
     * <p>
     * A label describing the membership
     * 
     * @return
     *     The label
     */
    @JsonProperty("label")
    public String getLabel() {
        return label;
    }

    /**
     * Label
     * <p>
     * A label describing the membership
     * 
     * @param label
     *     The label
     */
    @JsonProperty("label")
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Role
     * <p>
     * The role that the member fulfills in the organization
     * 
     * @return
     *     The role
     */
    @JsonProperty("role")
    public String getRole() {
        return role;
    }

    /**
     * Role
     * <p>
     * The role that the member fulfills in the organization
     * 
     * @param role
     *     The role
     */
    @JsonProperty("role")
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Legal Person
     * <p>
     * An individual or company
     * 
     * @return
     *     The person
     */
    @JsonProperty("person")
    public Owner getPerson() {
        return person;
    }

    /**
     * Legal Person
     * <p>
     * An individual or company
     * 
     * @param person
     *     The person
     */
    @JsonProperty("person")
    public void setPerson(Owner person) {
        this.person = person;
    }

    /**
     * Organization
     * <p>
     * A group with a common purpose or reason for existence that goes beyond the set of people belonging to it
     * 
     * @return
     *     The organization
     */
    @JsonProperty("organization")
    public Organization getOrganization() {
        return organization;
    }

    /**
     * Organization
     * <p>
     * A group with a common purpose or reason for existence that goes beyond the set of people belonging to it
     * 
     * @param organization
     *     The organization
     */
    @JsonProperty("organization")
    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    /**
     * The date on which the relationship began
     * 
     * @return
     *     The startDate
     */
    @JsonProperty("start_date")
    public String getStartDate() {
        return startDate;
    }

    /**
     * The date on which the relationship began
     * 
     * @param startDate
     *     The start_date
     */
    @JsonProperty("start_date")
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * The date on which the relationship ended
     * 
     * @return
     *     The endDate
     */
    @JsonProperty("end_date")
    public String getEndDate() {
        return endDate;
    }

    /**
     * The date on which the relationship ended
     * 
     * @param endDate
     *     The end_date
     */
    @JsonProperty("end_date")
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * Means of contacting the member of the organization
     * 
     * @return
     *     The contactDetails
     */
    @JsonProperty("contact_details")
    public List<ContactDetail> getContactDetails() {
        return contactDetails;
    }

    /**
     * Means of contacting the member of the organization
     * 
     * @param contactDetails
     *     The contact_details
     */
    @JsonProperty("contact_details")
    public void setContactDetails(List<ContactDetail> contactDetails) {
        this.contactDetails = contactDetails;
    }

    /**
     * URLs to documents about the membership
     * 
     * @return
     *     The links
     */
    @JsonProperty("links")
    public List<Link> getLinks() {
        return links;
    }

    /**
     * URLs to documents about the membership
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
        return new HashCodeBuilder().append(id).append(label).append(role).append(person).append(organization).append(startDate).append(endDate).append(contactDetails).append(links).append(createdAt).append(updatedAt).append(sources).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Membership) == false) {
            return false;
        }
        Membership rhs = ((Membership) other);
        return new EqualsBuilder().append(id, rhs.id).append(label, rhs.label).append(role, rhs.role).append(person, rhs.person).append(organization, rhs.organization).append(startDate, rhs.startDate).append(endDate, rhs.endDate).append(contactDetails, rhs.contactDetails).append(links, rhs.links).append(createdAt, rhs.createdAt).append(updatedAt, rhs.updatedAt).append(sources, rhs.sources).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
