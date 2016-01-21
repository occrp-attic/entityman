
package org.occrp.entityman.rc.jsonschema;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * Person
 * <p>
 * A real person, alive or dead
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "family_name",
    "given_name",
    "additional_name",
    "honorific_prefix",
    "honorific_suffix",
    "patronymic_name",
    "sort_name",
    "email",
    "gender",
    "birth_date",
    "death_date",
    "image",
    "summary",
    "biography",
    "residential_address",
    "postal_address",
    "contact_details"
})
public class Person
    extends Owner
{

    /**
     * One or more family names
     * 
     */
    @JsonProperty("family_name")
    private String familyName;
    /**
     * One or more primary given names
     * 
     */
    @JsonProperty("given_name")
    private String givenName;
    /**
     * One or more secondary given names
     * 
     */
    @JsonProperty("additional_name")
    private String additionalName;
    /**
     * One or more honorifics preceding a person's name
     * 
     */
    @JsonProperty("honorific_prefix")
    private String honorificPrefix;
    /**
     * One or more honorifics following a person's name
     * 
     */
    @JsonProperty("honorific_suffix")
    private String honorificSuffix;
    /**
     * One or more patronymic names
     * 
     */
    @JsonProperty("patronymic_name")
    private String patronymicName;
    /**
     * A name to use in a lexicographically ordered list
     * 
     */
    @JsonProperty("sort_name")
    private String sortName;
    /**
     * A preferred email address
     * 
     */
    @JsonProperty("email")
    private String email;
    /**
     * A gender
     * 
     */
    @JsonProperty("gender")
    private String gender;
    /**
     * A date of birth
     * 
     */
    @JsonProperty("birth_date")
    private String birthDate;
    /**
     * A date of death
     * 
     */
    @JsonProperty("death_date")
    private String deathDate;
    /**
     * A URL of a head shot
     * 
     */
    @JsonProperty("image")
    private URI image;
    /**
     * A one-line account of a person's life
     * 
     */
    @JsonProperty("summary")
    private String summary;
    /**
     * An extended account of a person's life
     * 
     */
    @JsonProperty("biography")
    private String biography;
    /**
     * Address
     * <p>
     * An address object
     * 
     */
    @JsonProperty("residential_address")
    private Address residentialAddress;
    /**
     * Address
     * <p>
     * An address object
     * 
     */
    @JsonProperty("postal_address")
    private Address postalAddress;
    /**
     * Means of contacting the person
     * 
     */
    @JsonProperty("contact_details")
    private List<ContactDetail> contactDetails = new ArrayList<ContactDetail>();

    /**
     * One or more family names
     * 
     * @return
     *     The familyName
     */
    @JsonProperty("family_name")
    public String getFamilyName() {
        return familyName;
    }

    /**
     * One or more family names
     * 
     * @param familyName
     *     The family_name
     */
    @JsonProperty("family_name")
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    /**
     * One or more primary given names
     * 
     * @return
     *     The givenName
     */
    @JsonProperty("given_name")
    public String getGivenName() {
        return givenName;
    }

    /**
     * One or more primary given names
     * 
     * @param givenName
     *     The given_name
     */
    @JsonProperty("given_name")
    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    /**
     * One or more secondary given names
     * 
     * @return
     *     The additionalName
     */
    @JsonProperty("additional_name")
    public String getAdditionalName() {
        return additionalName;
    }

    /**
     * One or more secondary given names
     * 
     * @param additionalName
     *     The additional_name
     */
    @JsonProperty("additional_name")
    public void setAdditionalName(String additionalName) {
        this.additionalName = additionalName;
    }

    /**
     * One or more honorifics preceding a person's name
     * 
     * @return
     *     The honorificPrefix
     */
    @JsonProperty("honorific_prefix")
    public String getHonorificPrefix() {
        return honorificPrefix;
    }

    /**
     * One or more honorifics preceding a person's name
     * 
     * @param honorificPrefix
     *     The honorific_prefix
     */
    @JsonProperty("honorific_prefix")
    public void setHonorificPrefix(String honorificPrefix) {
        this.honorificPrefix = honorificPrefix;
    }

    /**
     * One or more honorifics following a person's name
     * 
     * @return
     *     The honorificSuffix
     */
    @JsonProperty("honorific_suffix")
    public String getHonorificSuffix() {
        return honorificSuffix;
    }

    /**
     * One or more honorifics following a person's name
     * 
     * @param honorificSuffix
     *     The honorific_suffix
     */
    @JsonProperty("honorific_suffix")
    public void setHonorificSuffix(String honorificSuffix) {
        this.honorificSuffix = honorificSuffix;
    }

    /**
     * One or more patronymic names
     * 
     * @return
     *     The patronymicName
     */
    @JsonProperty("patronymic_name")
    public String getPatronymicName() {
        return patronymicName;
    }

    /**
     * One or more patronymic names
     * 
     * @param patronymicName
     *     The patronymic_name
     */
    @JsonProperty("patronymic_name")
    public void setPatronymicName(String patronymicName) {
        this.patronymicName = patronymicName;
    }

    /**
     * A name to use in a lexicographically ordered list
     * 
     * @return
     *     The sortName
     */
    @JsonProperty("sort_name")
    public String getSortName() {
        return sortName;
    }

    /**
     * A name to use in a lexicographically ordered list
     * 
     * @param sortName
     *     The sort_name
     */
    @JsonProperty("sort_name")
    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    /**
     * A preferred email address
     * 
     * @return
     *     The email
     */
    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    /**
     * A preferred email address
     * 
     * @param email
     *     The email
     */
    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * A gender
     * 
     * @return
     *     The gender
     */
    @JsonProperty("gender")
    public String getGender() {
        return gender;
    }

    /**
     * A gender
     * 
     * @param gender
     *     The gender
     */
    @JsonProperty("gender")
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * A date of birth
     * 
     * @return
     *     The birthDate
     */
    @JsonProperty("birth_date")
    public String getBirthDate() {
        return birthDate;
    }

    /**
     * A date of birth
     * 
     * @param birthDate
     *     The birth_date
     */
    @JsonProperty("birth_date")
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * A date of death
     * 
     * @return
     *     The deathDate
     */
    @JsonProperty("death_date")
    public String getDeathDate() {
        return deathDate;
    }

    /**
     * A date of death
     * 
     * @param deathDate
     *     The death_date
     */
    @JsonProperty("death_date")
    public void setDeathDate(String deathDate) {
        this.deathDate = deathDate;
    }

    /**
     * A URL of a head shot
     * 
     * @return
     *     The image
     */
    @JsonProperty("image")
    public URI getImage() {
        return image;
    }

    /**
     * A URL of a head shot
     * 
     * @param image
     *     The image
     */
    @JsonProperty("image")
    public void setImage(URI image) {
        this.image = image;
    }

    /**
     * A one-line account of a person's life
     * 
     * @return
     *     The summary
     */
    @JsonProperty("summary")
    public String getSummary() {
        return summary;
    }

    /**
     * A one-line account of a person's life
     * 
     * @param summary
     *     The summary
     */
    @JsonProperty("summary")
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * An extended account of a person's life
     * 
     * @return
     *     The biography
     */
    @JsonProperty("biography")
    public String getBiography() {
        return biography;
    }

    /**
     * An extended account of a person's life
     * 
     * @param biography
     *     The biography
     */
    @JsonProperty("biography")
    public void setBiography(String biography) {
        this.biography = biography;
    }

    /**
     * Address
     * <p>
     * An address object
     * 
     * @return
     *     The residentialAddress
     */
    @JsonProperty("residential_address")
    public Address getResidentialAddress() {
        return residentialAddress;
    }

    /**
     * Address
     * <p>
     * An address object
     * 
     * @param residentialAddress
     *     The residential_address
     */
    @JsonProperty("residential_address")
    public void setResidentialAddress(Address residentialAddress) {
        this.residentialAddress = residentialAddress;
    }

    /**
     * Address
     * <p>
     * An address object
     * 
     * @return
     *     The postalAddress
     */
    @JsonProperty("postal_address")
    public Address getPostalAddress() {
        return postalAddress;
    }

    /**
     * Address
     * <p>
     * An address object
     * 
     * @param postalAddress
     *     The postal_address
     */
    @JsonProperty("postal_address")
    public void setPostalAddress(Address postalAddress) {
        this.postalAddress = postalAddress;
    }

    /**
     * Means of contacting the person
     * 
     * @return
     *     The contactDetails
     */
    @JsonProperty("contact_details")
    public List<ContactDetail> getContactDetails() {
        return contactDetails;
    }

    /**
     * Means of contacting the person
     * 
     * @param contactDetails
     *     The contact_details
     */
    @JsonProperty("contact_details")
    public void setContactDetails(List<ContactDetail> contactDetails) {
        this.contactDetails = contactDetails;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(familyName).append(givenName).append(additionalName).append(honorificPrefix).append(honorificSuffix).append(patronymicName).append(sortName).append(email).append(gender).append(birthDate).append(deathDate).append(image).append(summary).append(biography).append(residentialAddress).append(postalAddress).append(contactDetails).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Person) == false) {
            return false;
        }
        Person rhs = ((Person) other);
        return new EqualsBuilder().appendSuper(super.equals(other)).append(familyName, rhs.familyName).append(givenName, rhs.givenName).append(additionalName, rhs.additionalName).append(honorificPrefix, rhs.honorificPrefix).append(honorificSuffix, rhs.honorificSuffix).append(patronymicName, rhs.patronymicName).append(sortName, rhs.sortName).append(email, rhs.email).append(gender, rhs.gender).append(birthDate, rhs.birthDate).append(deathDate, rhs.deathDate).append(image, rhs.image).append(summary, rhs.summary).append(biography, rhs.biography).append(residentialAddress, rhs.residentialAddress).append(postalAddress, rhs.postalAddress).append(contactDetails, rhs.contactDetails).isEquals();
    }

}
