
package org.occrp.entityman.rc.jsonschema;

import java.net.URI;
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
 * Organization
 * <p>
 * A group with a common purpose or reason for existence that goes beyond the set of people belonging to it
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "classification",
    "area_id",
    "abstract",
    "description",
    "founding_date",
    "dissolution_date",
    "image",
    "contact_details",
    "members",
    "parents",
    "children"
})
public class Organization
    extends Owner
{

    /**
     * An organization category, e.g. committee
     * 
     */
    @JsonProperty("classification")
    private String classification;
    /**
     * The ID of the geographic area to which this organization is related
     * 
     */
    @JsonProperty("area_id")
    private String areaId;
    /**
     * A one-line description of an organization
     * 
     */
    @JsonProperty("abstract")
    private String _abstract;
    /**
     * An extended description of an organization
     * 
     */
    @JsonProperty("description")
    private String description;
    /**
     * A date of founding
     * 
     */
    @JsonProperty("founding_date")
    private String foundingDate;
    /**
     * A date of dissolution
     * 
     */
    @JsonProperty("dissolution_date")
    private String dissolutionDate;
    /**
     * A URL of an image
     * 
     */
    @JsonProperty("image")
    private URI image;
    /**
     * Means of contacting the organization
     * 
     */
    @JsonProperty("contact_details")
    private List<ContactDetail> contactDetails = new ArrayList<ContactDetail>();
    /**
     * Members
     * <p>
     * The memberships of the members of the organization and of the organization itself
     * 
     */
    @JsonProperty("members")
    private List<Membership> members = new ArrayList<Membership>();
    /**
     * The organization that contains this organization
     * 
     */
    @JsonProperty("parents")
    private List<Organization> parents = new ArrayList<Organization>();
    /**
     * The sub-organizations of the organization
     * 
     */
    @JsonProperty("children")
    private List<Organization> children = new ArrayList<Organization>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * An organization category, e.g. committee
     * 
     * @return
     *     The classification
     */
    @JsonProperty("classification")
    public String getClassification() {
        return classification;
    }

    /**
     * An organization category, e.g. committee
     * 
     * @param classification
     *     The classification
     */
    @JsonProperty("classification")
    public void setClassification(String classification) {
        this.classification = classification;
    }

    /**
     * The ID of the geographic area to which this organization is related
     * 
     * @return
     *     The areaId
     */
    @JsonProperty("area_id")
    public String getAreaId() {
        return areaId;
    }

    /**
     * The ID of the geographic area to which this organization is related
     * 
     * @param areaId
     *     The area_id
     */
    @JsonProperty("area_id")
    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    /**
     * A one-line description of an organization
     * 
     * @return
     *     The _abstract
     */
    @JsonProperty("abstract")
    public String getAbstract() {
        return _abstract;
    }

    /**
     * A one-line description of an organization
     * 
     * @param _abstract
     *     The abstract
     */
    @JsonProperty("abstract")
    public void setAbstract(String _abstract) {
        this._abstract = _abstract;
    }

    /**
     * An extended description of an organization
     * 
     * @return
     *     The description
     */
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     * An extended description of an organization
     * 
     * @param description
     *     The description
     */
    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * A date of founding
     * 
     * @return
     *     The foundingDate
     */
    @JsonProperty("founding_date")
    public String getFoundingDate() {
        return foundingDate;
    }

    /**
     * A date of founding
     * 
     * @param foundingDate
     *     The founding_date
     */
    @JsonProperty("founding_date")
    public void setFoundingDate(String foundingDate) {
        this.foundingDate = foundingDate;
    }

    /**
     * A date of dissolution
     * 
     * @return
     *     The dissolutionDate
     */
    @JsonProperty("dissolution_date")
    public String getDissolutionDate() {
        return dissolutionDate;
    }

    /**
     * A date of dissolution
     * 
     * @param dissolutionDate
     *     The dissolution_date
     */
    @JsonProperty("dissolution_date")
    public void setDissolutionDate(String dissolutionDate) {
        this.dissolutionDate = dissolutionDate;
    }

    /**
     * A URL of an image
     * 
     * @return
     *     The image
     */
    @JsonProperty("image")
    public URI getImage() {
        return image;
    }

    /**
     * A URL of an image
     * 
     * @param image
     *     The image
     */
    @JsonProperty("image")
    public void setImage(URI image) {
        this.image = image;
    }

    /**
     * Means of contacting the organization
     * 
     * @return
     *     The contactDetails
     */
    @JsonProperty("contact_details")
    public List<ContactDetail> getContactDetails() {
        return contactDetails;
    }

    /**
     * Means of contacting the organization
     * 
     * @param contactDetails
     *     The contact_details
     */
    @JsonProperty("contact_details")
    public void setContactDetails(List<ContactDetail> contactDetails) {
        this.contactDetails = contactDetails;
    }

    /**
     * Members
     * <p>
     * The memberships of the members of the organization and of the organization itself
     * 
     * @return
     *     The members
     */
    @JsonProperty("members")
    public List<Membership> getMembers() {
        return members;
    }

    /**
     * Members
     * <p>
     * The memberships of the members of the organization and of the organization itself
     * 
     * @param members
     *     The members
     */
    @JsonProperty("members")
    public void setMembers(List<Membership> members) {
        this.members = members;
    }

    /**
     * The organization that contains this organization
     * 
     * @return
     *     The parents
     */
    @JsonProperty("parents")
    public List<Organization> getParents() {
        return parents;
    }

    /**
     * The organization that contains this organization
     * 
     * @param parents
     *     The parents
     */
    @JsonProperty("parents")
    public void setParents(List<Organization> parents) {
        this.parents = parents;
    }

    /**
     * The sub-organizations of the organization
     * 
     * @return
     *     The children
     */
    @JsonProperty("children")
    public List<Organization> getChildren() {
        return children;
    }

    /**
     * The sub-organizations of the organization
     * 
     * @param children
     *     The children
     */
    @JsonProperty("children")
    public void setChildren(List<Organization> children) {
        this.children = children;
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
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(classification).append(areaId).append(_abstract).append(description).append(foundingDate).append(dissolutionDate).append(image).append(contactDetails).append(members).append(parents).append(children).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Organization) == false) {
            return false;
        }
        Organization rhs = ((Organization) other);
        return new EqualsBuilder().appendSuper(super.equals(other)).append(classification, rhs.classification).append(areaId, rhs.areaId).append(_abstract, rhs._abstract).append(description, rhs.description).append(foundingDate, rhs.foundingDate).append(dissolutionDate, rhs.dissolutionDate).append(image, rhs.image).append(contactDetails, rhs.contactDetails).append(members, rhs.members).append(parents, rhs.parents).append(children, rhs.children).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
