
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
 * Other name
 * <p>
 * An alternate or former name
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "name",
    "family_name",
    "given_name",
    "additional_name",
    "honorific_prefix",
    "honorific_suffix",
    "patronymic_name",
    "start_date",
    "end_date",
    "note"
})
public class OtherName {

    /**
     * An alternate or former name
     * 
     */
    @JsonProperty("name")
    private String name;
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
     * The date on which the name was adopted
     * 
     */
    @JsonProperty("start_date")
    private String startDate;
    /**
     * The date on which the name was abandoned
     * 
     */
    @JsonProperty("end_date")
    private String endDate;
    /**
     * A note, e.g. 'Birth name'
     * 
     */
    @JsonProperty("note")
    private String note;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * An alternate or former name
     * 
     * @return
     *     The name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * An alternate or former name
     * 
     * @param name
     *     The name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

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
     * The date on which the name was adopted
     * 
     * @return
     *     The startDate
     */
    @JsonProperty("start_date")
    public String getStartDate() {
        return startDate;
    }

    /**
     * The date on which the name was adopted
     * 
     * @param startDate
     *     The start_date
     */
    @JsonProperty("start_date")
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * The date on which the name was abandoned
     * 
     * @return
     *     The endDate
     */
    @JsonProperty("end_date")
    public String getEndDate() {
        return endDate;
    }

    /**
     * The date on which the name was abandoned
     * 
     * @param endDate
     *     The end_date
     */
    @JsonProperty("end_date")
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * A note, e.g. 'Birth name'
     * 
     * @return
     *     The note
     */
    @JsonProperty("note")
    public String getNote() {
        return note;
    }

    /**
     * A note, e.g. 'Birth name'
     * 
     * @param note
     *     The note
     */
    @JsonProperty("note")
    public void setNote(String note) {
        this.note = note;
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
        return new HashCodeBuilder().append(name).append(familyName).append(givenName).append(additionalName).append(honorificPrefix).append(honorificSuffix).append(patronymicName).append(startDate).append(endDate).append(note).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof OtherName) == false) {
            return false;
        }
        OtherName rhs = ((OtherName) other);
        return new EqualsBuilder().append(name, rhs.name).append(familyName, rhs.familyName).append(givenName, rhs.givenName).append(additionalName, rhs.additionalName).append(honorificPrefix, rhs.honorificPrefix).append(honorificSuffix, rhs.honorificSuffix).append(patronymicName, rhs.patronymicName).append(startDate, rhs.startDate).append(endDate, rhs.endDate).append(note, rhs.note).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
