/*
 * Copyright (c) 2020 Pragma Edge Inc
 *
 * Licensed under the Pragma Edge Inc
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://pragmaedge.com/licenseagreement
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pe.pcm.sterling.yfs.entity;

import com.pe.pcm.protocol.as2.si.entity.SciAudit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;

/**
 * @author Chenchu Kiran.
 */
@Entity
@Table(name = "YFS_PERSON_INFO")
public class YfsPersonInfoEntity extends SciAudit implements Serializable {

    @Id
    private String personInfoKey;
    private String personId;
    private String title;
    private String firstName;
    private String middleName;
    private String lastName;
    private String suffix;
    private String department;
    private String company;
    private String jobTitle;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String addressLine5;
    private String addressLine6;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private String dayPhone;
    private String eveningPhone;
    private String mobilePhone;
    private String beeper;
    private String otherPhone;
    private String dayFaxNo;
    private String eveningFaxNo;
    @Column(name = "EMAILID")
    private String emailId;
    @Column(name = "ALTERNATE_EMAILID")
    private String alternateEmailId;
    private String preferredShipAddress;
    private String httpUrl;
    private String useCount;
    private String verificationStatus;
    private String isAddressVerified;
    private String latitude;
    private String longitude;
    private String taxGeoCode;
    private String errorTxt;
    private String isCommercialAddress;
    private String timeZone;

    private String addressType;
    private String countryOfResidence;
    private String subDepartment;
    private String streetName;
    private String buildingNumber;
    private String addressLine7;
    private String other;
    private Date birthDate;
    private String stateOfBirth;
    private String cityOfBirth;
    private String countryOfBirth;


    public String getPersonInfoKey() {
        return personInfoKey;
    }

    public YfsPersonInfoEntity setPersonInfoKey(String personInfoKey) {
        this.personInfoKey = personInfoKey;
        return this;
    }

    public String getPersonId() {
        return personId;
    }

    public YfsPersonInfoEntity setPersonId(String personId) {
        this.personId = personId;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public YfsPersonInfoEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public YfsPersonInfoEntity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getMiddleName() {
        return middleName;
    }

    public YfsPersonInfoEntity setMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public YfsPersonInfoEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getSuffix() {
        return suffix;
    }

    public YfsPersonInfoEntity setSuffix(String suffix) {
        this.suffix = suffix;
        return this;
    }

    public String getDepartment() {
        return department;
    }

    public YfsPersonInfoEntity setDepartment(String department) {
        this.department = department;
        return this;
    }

    public String getCompany() {
        return company;
    }

    public YfsPersonInfoEntity setCompany(String company) {
        this.company = company;
        return this;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public YfsPersonInfoEntity setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
        return this;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public YfsPersonInfoEntity setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
        return this;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public YfsPersonInfoEntity setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
        return this;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public YfsPersonInfoEntity setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
        return this;
    }

    public String getAddressLine4() {
        return addressLine4;
    }

    public YfsPersonInfoEntity setAddressLine4(String addressLine4) {
        this.addressLine4 = addressLine4;
        return this;
    }

    public String getAddressLine5() {
        return addressLine5;
    }

    public YfsPersonInfoEntity setAddressLine5(String addressLine5) {
        this.addressLine5 = addressLine5;
        return this;
    }

    public String getAddressLine6() {
        return addressLine6;
    }

    public YfsPersonInfoEntity setAddressLine6(String addressLine6) {
        this.addressLine6 = addressLine6;
        return this;
    }

    public String getCity() {
        return city;
    }

    public YfsPersonInfoEntity setCity(String city) {
        this.city = city;
        return this;
    }

    public String getState() {
        return state;
    }

    public YfsPersonInfoEntity setState(String state) {
        this.state = state;
        return this;
    }

    public String getZipCode() {
        return zipCode;
    }

    public YfsPersonInfoEntity setZipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public YfsPersonInfoEntity setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getDayPhone() {
        return dayPhone;
    }

    public YfsPersonInfoEntity setDayPhone(String dayPhone) {
        this.dayPhone = dayPhone;
        return this;
    }

    public String getEveningPhone() {
        return eveningPhone;
    }

    public YfsPersonInfoEntity setEveningPhone(String eveningPhone) {
        this.eveningPhone = eveningPhone;
        return this;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public YfsPersonInfoEntity setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
        return this;
    }

    public String getBeeper() {
        return beeper;
    }

    public YfsPersonInfoEntity setBeeper(String beeper) {
        this.beeper = beeper;
        return this;
    }

    public String getOtherPhone() {
        return otherPhone;
    }

    public YfsPersonInfoEntity setOtherPhone(String otherPhone) {
        this.otherPhone = otherPhone;
        return this;
    }

    public String getDayFaxNo() {
        return dayFaxNo;
    }

    public YfsPersonInfoEntity setDayFaxNo(String dayFaxNo) {
        this.dayFaxNo = dayFaxNo;
        return this;
    }

    public String getEveningFaxNo() {
        return eveningFaxNo;
    }

    public YfsPersonInfoEntity setEveningFaxNo(String eveningFaxNo) {
        this.eveningFaxNo = eveningFaxNo;
        return this;
    }

    public String getEmailId() {
        return emailId;
    }

    public YfsPersonInfoEntity setEmailId(String emailId) {
        this.emailId = emailId;
        return this;
    }

    public String getAlternateEmailId() {
        return alternateEmailId;
    }

    public YfsPersonInfoEntity setAlternateEmailId(String alternateEmailId) {
        this.alternateEmailId = alternateEmailId;
        return this;
    }

    public String getPreferredShipAddress() {
        return preferredShipAddress;
    }

    public YfsPersonInfoEntity setPreferredShipAddress(String preferredShipAddress) {
        this.preferredShipAddress = preferredShipAddress;
        return this;
    }

    public String getHttpUrl() {
        return httpUrl;
    }

    public YfsPersonInfoEntity setHttpUrl(String httpUrl) {
        this.httpUrl = httpUrl;
        return this;
    }

    public String getUseCount() {
        return useCount;
    }

    public YfsPersonInfoEntity setUseCount(String useCount) {
        this.useCount = useCount;
        return this;
    }

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public YfsPersonInfoEntity setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
        return this;
    }

    public String getIsAddressVerified() {
        return isAddressVerified;
    }

    public YfsPersonInfoEntity setIsAddressVerified(String isAddressVerified) {
        this.isAddressVerified = isAddressVerified;
        return this;
    }

    public String getLatitude() {
        return latitude;
    }

    public YfsPersonInfoEntity setLatitude(String latitude) {
        this.latitude = latitude;
        return this;
    }

    public String getLongitude() {
        return longitude;
    }

    public YfsPersonInfoEntity setLongitude(String longitude) {
        this.longitude = longitude;
        return this;
    }

    public String getTaxGeoCode() {
        return taxGeoCode;
    }

    public YfsPersonInfoEntity setTaxGeoCode(String taxGeoCode) {
        this.taxGeoCode = taxGeoCode;
        return this;
    }

    public String getErrorTxt() {
        return errorTxt;
    }

    public YfsPersonInfoEntity setErrorTxt(String errorTxt) {
        this.errorTxt = errorTxt;
        return this;
    }

    public String getIsCommercialAddress() {
        return isCommercialAddress;
    }

    public YfsPersonInfoEntity setIsCommercialAddress(String isCommercialAddress) {
        this.isCommercialAddress = isCommercialAddress;
        return this;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public YfsPersonInfoEntity setTimeZone(String timeZone) {
        this.timeZone = timeZone;
        return this;
    }

    public String getAddressType() {
        return addressType;
    }

    public YfsPersonInfoEntity setAddressType(String addressType) {
        this.addressType = addressType;
        return this;
    }

    public String getCountryOfResidence() {
        return countryOfResidence;
    }

    public YfsPersonInfoEntity setCountryOfResidence(String countryOfResidence) {
        this.countryOfResidence = countryOfResidence;
        return this;
    }

    public String getSubDepartment() {
        return subDepartment;
    }

    public YfsPersonInfoEntity setSubDepartment(String subDepartment) {
        this.subDepartment = subDepartment;
        return this;
    }

    public String getStreetName() {
        return streetName;
    }

    public YfsPersonInfoEntity setStreetName(String streetName) {
        this.streetName = streetName;
        return this;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public YfsPersonInfoEntity setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
        return this;
    }

    public String getAddressLine7() {
        return addressLine7;
    }

    public YfsPersonInfoEntity setAddressLine7(String addressLine7) {
        this.addressLine7 = addressLine7;
        return this;
    }

    public String getOther() {
        return other;
    }

    public YfsPersonInfoEntity setOther(String other) {
        this.other = other;
        return this;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public YfsPersonInfoEntity setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public String getStateOfBirth() {
        return stateOfBirth;
    }

    public YfsPersonInfoEntity setStateOfBirth(String stateOfBirth) {
        this.stateOfBirth = stateOfBirth;
        return this;
    }

    public String getCityOfBirth() {
        return cityOfBirth;
    }

    public YfsPersonInfoEntity setCityOfBirth(String cityOfBirth) {
        this.cityOfBirth = cityOfBirth;
        return this;
    }

    public String getCountryOfBirth() {
        return countryOfBirth;
    }

    public YfsPersonInfoEntity setCountryOfBirth(String countryOfBirth) {
        this.countryOfBirth = countryOfBirth;
        return this;
    }
}
