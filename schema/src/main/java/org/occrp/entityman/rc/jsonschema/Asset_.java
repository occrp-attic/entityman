
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
 * Ownership
 * <p>
 * An ownership relation between a legal entity and an asset
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "id",
    "type",
    "share",
    "valuation",
    "valuation_currency",
    "valuation_date",
    "owner",
    "asset",
    "start_date",
    "end_date",
    "links",
    "sources"
})
public class Asset_ {

    /**
     * ID
     * <p>
     * The ownership's unique identifier
     * 
     */
    @JsonProperty("id")
    private String id;
    /**
     * Type
     * <p>
     * The type of ownership, e.g. beneficial or legal
     * 
     */
    @JsonProperty("type")
    private String type;
    /**
     * Share
     * <p>
     * The share of the ownership, e.g. percentage or share count
     * 
     */
    @JsonProperty("share")
    private String share;
    /**
     * Valuation
     * <p>
     * The estimated value of the share
     * 
     */
    @JsonProperty("valuation")
    private Integer valuation;
    /**
     * Currency of valuation
     * <p>
     * 
     * 
     */
    @JsonProperty("valuation_currency")
    private String valuationCurrency;
    /**
     * Valuation date
     * <p>
     * When the valuation was made
     * 
     */
    @JsonProperty("valuation_date")
    private String valuationDate;
    /**
     * Legal Person
     * <p>
     * An individual or company
     * 
     */
    @JsonProperty("owner")
    private Owner owner;
    /**
     * Asset
     * <p>
     * An asset owned by one or several owners
     * 
     */
    @JsonProperty("asset")
    private Asset__ asset;
    /**
     * Start date
     * <p>
     * The date on which the relationship began
     * 
     */
    @JsonProperty("start_date")
    private String startDate;
    /**
     * End date
     * <p>
     * The date on which the relationship ended
     * 
     */
    @JsonProperty("end_date")
    private String endDate;
    /**
     * URLs to documents about the ownership
     * 
     */
    @JsonProperty("links")
    private List<Link> links = new ArrayList<Link>();
    /**
     * URLs to documents from which the resource is derived
     * 
     */
    @JsonProperty("sources")
    private List<Link> sources = new ArrayList<Link>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * ID
     * <p>
     * The ownership's unique identifier
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
     * The ownership's unique identifier
     * 
     * @param id
     *     The id
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Type
     * <p>
     * The type of ownership, e.g. beneficial or legal
     * 
     * @return
     *     The type
     */
    @JsonProperty("type")
    public String getType() {
        return type;
    }

    /**
     * Type
     * <p>
     * The type of ownership, e.g. beneficial or legal
     * 
     * @param type
     *     The type
     */
    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Share
     * <p>
     * The share of the ownership, e.g. percentage or share count
     * 
     * @return
     *     The share
     */
    @JsonProperty("share")
    public String getShare() {
        return share;
    }

    /**
     * Share
     * <p>
     * The share of the ownership, e.g. percentage or share count
     * 
     * @param share
     *     The share
     */
    @JsonProperty("share")
    public void setShare(String share) {
        this.share = share;
    }

    /**
     * Valuation
     * <p>
     * The estimated value of the share
     * 
     * @return
     *     The valuation
     */
    @JsonProperty("valuation")
    public Integer getValuation() {
        return valuation;
    }

    /**
     * Valuation
     * <p>
     * The estimated value of the share
     * 
     * @param valuation
     *     The valuation
     */
    @JsonProperty("valuation")
    public void setValuation(Integer valuation) {
        this.valuation = valuation;
    }

    /**
     * Currency of valuation
     * <p>
     * 
     * 
     * @return
     *     The valuationCurrency
     */
    @JsonProperty("valuation_currency")
    public String getValuationCurrency() {
        return valuationCurrency;
    }

    /**
     * Currency of valuation
     * <p>
     * 
     * 
     * @param valuationCurrency
     *     The valuation_currency
     */
    @JsonProperty("valuation_currency")
    public void setValuationCurrency(String valuationCurrency) {
        this.valuationCurrency = valuationCurrency;
    }

    /**
     * Valuation date
     * <p>
     * When the valuation was made
     * 
     * @return
     *     The valuationDate
     */
    @JsonProperty("valuation_date")
    public String getValuationDate() {
        return valuationDate;
    }

    /**
     * Valuation date
     * <p>
     * When the valuation was made
     * 
     * @param valuationDate
     *     The valuation_date
     */
    @JsonProperty("valuation_date")
    public void setValuationDate(String valuationDate) {
        this.valuationDate = valuationDate;
    }

    /**
     * Legal Person
     * <p>
     * An individual or company
     * 
     * @return
     *     The owner
     */
    @JsonProperty("owner")
    public Owner getOwner() {
        return owner;
    }

    /**
     * Legal Person
     * <p>
     * An individual or company
     * 
     * @param owner
     *     The owner
     */
    @JsonProperty("owner")
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    /**
     * Asset
     * <p>
     * An asset owned by one or several owners
     * 
     * @return
     *     The asset
     */
    @JsonProperty("asset")
    public Asset__ getAsset() {
        return asset;
    }

    /**
     * Asset
     * <p>
     * An asset owned by one or several owners
     * 
     * @param asset
     *     The asset
     */
    @JsonProperty("asset")
    public void setAsset(Asset__ asset) {
        this.asset = asset;
    }

    /**
     * Start date
     * <p>
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
     * Start date
     * <p>
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
     * End date
     * <p>
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
     * End date
     * <p>
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
     * URLs to documents about the ownership
     * 
     * @return
     *     The links
     */
    @JsonProperty("links")
    public List<Link> getLinks() {
        return links;
    }

    /**
     * URLs to documents about the ownership
     * 
     * @param links
     *     The links
     */
    @JsonProperty("links")
    public void setLinks(List<Link> links) {
        this.links = links;
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
        return new HashCodeBuilder().append(id).append(type).append(share).append(valuation).append(valuationCurrency).append(valuationDate).append(owner).append(asset).append(startDate).append(endDate).append(links).append(sources).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Asset_) == false) {
            return false;
        }
        Asset_ rhs = ((Asset_) other);
        return new EqualsBuilder().append(id, rhs.id).append(type, rhs.type).append(share, rhs.share).append(valuation, rhs.valuation).append(valuationCurrency, rhs.valuationCurrency).append(valuationDate, rhs.valuationDate).append(owner, rhs.owner).append(asset, rhs.asset).append(startDate, rhs.startDate).append(endDate, rhs.endDate).append(links, rhs.links).append(sources, rhs.sources).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
