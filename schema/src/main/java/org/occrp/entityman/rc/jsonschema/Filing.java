
package org.occrp.entityman.rc.jsonschema;

import java.net.URI;
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
 * Filing
 * <p>
 * A statutory filing
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "title",
    "date",
    "description",
    "uid",
    "url",
    "filing_type_code",
    "filing_type_name",
    "other_attributes"
})
public class Filing {

    @JsonProperty("title")
    private String title;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("date")
    private String date;
    @JsonProperty("description")
    private String description;
    @JsonProperty("uid")
    private String uid;
    @JsonProperty("url")
    private URI url;
    @JsonProperty("filing_type_code")
    private String filingTypeCode;
    @JsonProperty("filing_type_name")
    private String filingTypeName;
    @JsonProperty("other_attributes")
    private OtherAttributes otherAttributes;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The title
     */
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The date
     */
    @JsonProperty("date")
    public String getDate() {
        return date;
    }

    /**
     * 
     * (Required)
     * 
     * @param date
     *     The date
     */
    @JsonProperty("date")
    public void setDate(String date) {
        this.date = date;
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
     *     The uid
     */
    @JsonProperty("uid")
    public String getUid() {
        return uid;
    }

    /**
     * 
     * @param uid
     *     The uid
     */
    @JsonProperty("uid")
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * 
     * @return
     *     The url
     */
    @JsonProperty("url")
    public URI getUrl() {
        return url;
    }

    /**
     * 
     * @param url
     *     The url
     */
    @JsonProperty("url")
    public void setUrl(URI url) {
        this.url = url;
    }

    /**
     * 
     * @return
     *     The filingTypeCode
     */
    @JsonProperty("filing_type_code")
    public String getFilingTypeCode() {
        return filingTypeCode;
    }

    /**
     * 
     * @param filingTypeCode
     *     The filing_type_code
     */
    @JsonProperty("filing_type_code")
    public void setFilingTypeCode(String filingTypeCode) {
        this.filingTypeCode = filingTypeCode;
    }

    /**
     * 
     * @return
     *     The filingTypeName
     */
    @JsonProperty("filing_type_name")
    public String getFilingTypeName() {
        return filingTypeName;
    }

    /**
     * 
     * @param filingTypeName
     *     The filing_type_name
     */
    @JsonProperty("filing_type_name")
    public void setFilingTypeName(String filingTypeName) {
        this.filingTypeName = filingTypeName;
    }

    /**
     * 
     * @return
     *     The otherAttributes
     */
    @JsonProperty("other_attributes")
    public OtherAttributes getOtherAttributes() {
        return otherAttributes;
    }

    /**
     * 
     * @param otherAttributes
     *     The other_attributes
     */
    @JsonProperty("other_attributes")
    public void setOtherAttributes(OtherAttributes otherAttributes) {
        this.otherAttributes = otherAttributes;
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
        return new HashCodeBuilder().append(title).append(date).append(description).append(uid).append(url).append(filingTypeCode).append(filingTypeName).append(otherAttributes).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Filing) == false) {
            return false;
        }
        Filing rhs = ((Filing) other);
        return new EqualsBuilder().append(title, rhs.title).append(date, rhs.date).append(description, rhs.description).append(uid, rhs.uid).append(url, rhs.url).append(filingTypeCode, rhs.filingTypeCode).append(filingTypeName, rhs.filingTypeName).append(otherAttributes, rhs.otherAttributes).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
