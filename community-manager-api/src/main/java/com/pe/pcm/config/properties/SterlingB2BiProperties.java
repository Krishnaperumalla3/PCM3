
//package com.pe.pcm.config.properties;
//
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.stereotype.Component;
//import org.springframework.validation.annotation.Validated;
//
///**
// * @author Kiran Reddy.
// */
//@Component
//@ConfigurationProperties(prefix = "sterling-b2bi")
//@Validated
//public class SterlingB2BiProperties {
//    private Boolean sfgV6Update;
//    private CoreBp coreBp;
//    private User user;
//
//    public Boolean getSfgV6Update() {
//        return sfgV6Update;
//    }
//
//    public SterlingB2BiProperties setSfgV6Update(Boolean sfgV6Update) {
//        this.sfgV6Update = sfgV6Update;
//        return this;
//    }
//
//    public CoreBp getCoreBp() {
//        return coreBp;
//    }
//
//    public SterlingB2BiProperties setCoreBp(CoreBp coreBp) {
//        this.coreBp = coreBp;
//        return this;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public SterlingB2BiProperties setUser(User user) {
//        this.user = user;
//        return this;
//    }
//
//    public static class CoreBp {
//        private String inbound;
//        private String outbound;
//
//        public String getInbound() {
//            return inbound;
//        }
//
//        public CoreBp setInbound(String inbound) {
//            this.inbound = inbound;
//            return this;
//        }
//
//        public String getOutbound() {
//            return outbound;
//        }
//
//        public CoreBp setOutbound(String outbound) {
//            this.outbound = outbound;
//            return this;
//        }
//    }
//
//    public static class User {
//        private String cmks;
//        private Boolean cmksValidation;
//        private String cmksValidationProfile;
//
//        public String getCmks() {
//            return cmks;
//        }
//
//        public User setCmks(String cmks) {
//            this.cmks = cmks;
//            return this;
//        }
//
//        public Boolean getCmksValidation() {
//            return cmksValidation;
//        }
//
//        public User setCmksValidation(Boolean cmksValidation) {
//            this.cmksValidation = cmksValidation;
//            return this;
//        }
//
//        public String getCmksValidationProfile() {
//            return cmksValidationProfile;
//        }
//
//        public User setCmksValidationProfile(String cmksValidationProfile) {
//            this.cmksValidationProfile = cmksValidationProfile;
//            return this;
//        }
//    }
//}
