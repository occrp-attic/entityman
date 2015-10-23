
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
 * Industry code
 * <p>
 * An industry code from a standard code list (e.g. NAICS 2007 or NACE 2)
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "name",
    "code",
    "code_scheme_id",
    "start_date",
    "end_date"
})
public class IndustryCode_ {

    @JsonProperty("name")
    private String name;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("code")
    private String code;
    /**
     * An identifier representing industry code scheme. At the moment these are eu_nace_2, uk_sic_2003, uk_sic_2007, us_naics_2002, us_naics_2007, be_nace_2008, dk_db_2007, nz_bic_2006, no_sic_2007, anz_sic_2006, nz_bic_2006, in_nic_2004_mca, ca_qc_cae, lu_nace_2. For other code schemes, or details of these, contact info@opencorporates.com
     * (Required)
     * 
     */
    @JsonProperty("code_scheme_id")
    private String codeSchemeId;
    @JsonProperty("start_date")
    private String startDate;
    @JsonProperty("end_date")
    private String endDate;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The code
     */
    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    /**
     * 
     * (Required)
     * 
     * @param code
     *     The code
     */
    @JsonProperty("code")
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * An identifier representing industry code scheme. At the moment these are eu_nace_2, uk_sic_2003, uk_sic_2007, us_naics_2002, us_naics_2007, be_nace_2008, dk_db_2007, nz_bic_2006, no_sic_2007, anz_sic_2006, nz_bic_2006, in_nic_2004_mca, ca_qc_cae, lu_nace_2. For other code schemes, or details of these, contact info@opencorporates.com
     * (Required)
     * 
     * @return
     *     The codeSchemeId
     */
    @JsonProperty("code_scheme_id")
    public String getCodeSchemeId() {
        return codeSchemeId;
    }

    /**
     * An identifier representing industry code scheme. At the moment these are eu_nace_2, uk_sic_2003, uk_sic_2007, us_naics_2002, us_naics_2007, be_nace_2008, dk_db_2007, nz_bic_2006, no_sic_2007, anz_sic_2006, nz_bic_2006, in_nic_2004_mca, ca_qc_cae, lu_nace_2. For other code schemes, or details of these, contact info@opencorporates.com
     * (Required)
     * 
     * @param codeSchemeId
     *     The code_scheme_id
     */
    @JsonProperty("code_scheme_id")
    public void setCodeSchemeId(String codeSchemeId) {
        this.codeSchemeId = codeSchemeId;
    }

    /**
     * 
     * @return
     *     The startDate
     */
    @JsonProperty("start_date")
    public String getStartDate() {
        return startDate;
    }

    /**
     * 
     * @param startDate
     *     The start_date
     */
    @JsonProperty("start_date")
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * 
     * @return
     *     The endDate
     */
    @JsonProperty("end_date")
    public String getEndDate() {
        return endDate;
    }

    /**
     * 
     * @param endDate
     *     The end_date
     */
    @JsonProperty("end_date")
    public void setEndDate(String endDate) {
        this.endDate = endDate;
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
        return new HashCodeBuilder().append(name).append(code).append(codeSchemeId).append(startDate).append(endDate).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof IndustryCode_) == false) {
            return false;
        }
        IndustryCode_ rhs = ((IndustryCode_) other);
        return new EqualsBuilder().append(name, rhs.name).append(code, rhs.code).append(codeSchemeId, rhs.codeSchemeId).append(startDate, rhs.startDate).append(endDate, rhs.endDate).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
