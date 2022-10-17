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

package com.pe.pcm.sterling.dto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Chenchu Kiran.
 */
public class YfsPersonInfoDTO implements Serializable {

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
    private String emailId;
    private Timestamp birthDate;

    public String getPersonId() {
        return personId;
    }

    public YfsPersonInfoDTO setPersonId(String personId) {
        this.personId = personId;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public YfsPersonInfoDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public YfsPersonInfoDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getMiddleName() {
        return middleName;
    }

    public YfsPersonInfoDTO setMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public YfsPersonInfoDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getSuffix() {
        return suffix;
    }

    public YfsPersonInfoDTO setSuffix(String suffix) {
        this.suffix = suffix;
        return this;
    }

    public String getDepartment() {
        return department;
    }

    public YfsPersonInfoDTO setDepartment(String department) {
        this.department = department;
        return this;
    }

    public String getCompany() {
        return company;
    }

    public YfsPersonInfoDTO setCompany(String company) {
        this.company = company;
        return this;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public YfsPersonInfoDTO setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
        return this;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public YfsPersonInfoDTO setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
        return this;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public YfsPersonInfoDTO setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
        return this;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public YfsPersonInfoDTO setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
        return this;
    }

    public String getAddressLine4() {
        return addressLine4;
    }

    public YfsPersonInfoDTO setAddressLine4(String addressLine4) {
        this.addressLine4 = addressLine4;
        return this;
    }

    public String getAddressLine5() {
        return addressLine5;
    }

    public YfsPersonInfoDTO setAddressLine5(String addressLine5) {
        this.addressLine5 = addressLine5;
        return this;
    }

    public String getAddressLine6() {
        return addressLine6;
    }

    public YfsPersonInfoDTO setAddressLine6(String addressLine6) {
        this.addressLine6 = addressLine6;
        return this;
    }

    public String getCity() {
        return city;
    }

    public YfsPersonInfoDTO setCity(String city) {
        this.city = city;
        return this;
    }

    public String getState() {
        return state;
    }

    public YfsPersonInfoDTO setState(String state) {
        this.state = state;
        return this;
    }

    public String getZipCode() {
        return zipCode;
    }

    public YfsPersonInfoDTO setZipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public YfsPersonInfoDTO setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getDayPhone() {
        return dayPhone;
    }

    public YfsPersonInfoDTO setDayPhone(String dayPhone) {
        this.dayPhone = dayPhone;
        return this;
    }

    public String getEmailId() {
        return emailId;
    }

    public YfsPersonInfoDTO setEmailId(String emailId) {
        this.emailId = emailId;
        return this;
    }

    public String getPersonInfoKey() {
        return personInfoKey;
    }

    public YfsPersonInfoDTO setPersonInfoKey(String personInfoKey) {
        this.personInfoKey = personInfoKey;
        return this;
    }

    public Timestamp getBirthDate() {
        return birthDate;
    }

    public YfsPersonInfoDTO setBirthDate(Timestamp birthDate) {
        this.birthDate = birthDate;
        return this;
    }
}
