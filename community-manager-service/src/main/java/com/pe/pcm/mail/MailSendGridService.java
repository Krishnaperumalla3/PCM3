///*
// * Copyright (c) 2020 Pragma Edge Inc
// *
// * Licensed under the Pragma Edge Inc
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// * https://pragmaedge.com/licenseagreement
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package com.pe.pcm.mail;
//
//import com.pe.pcm.common.CommunityManagerNameModel;
//import com.pe.pcm.enums.EmailFields;
//import com.pe.pcm.exception.GlobalExceptionHandler;
//import com.pe.pcm.miscellaneous.IndependentService;
//import com.pe.pcm.pem.PemEmailModel;
//import com.pe.pcm.user.entity.UserEntity;
//import com.pe.pcm.utils.CommonFunctions;
//import com.sendgrid.Method;
//import com.sendgrid.Request;
//import com.sendgrid.Response;
//import com.sendgrid.SendGrid;
//import com.sendgrid.helpers.mail.Mail;
//import com.sendgrid.helpers.mail.objects.Content;
//import com.sendgrid.helpers.mail.objects.Email;
//import com.sendgrid.helpers.mail.objects.Personalization;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.MessageSource;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//import org.thymeleaf.context.Context;
//import org.thymeleaf.spring5.SpringTemplateEngine;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Locale;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//import static com.pe.pcm.utils.CommonFunctions.isNotNull;
//import static com.pe.pcm.utils.PCMConstants.REGEX_EMAIL;
//import static java.lang.Boolean.*;
//import static java.lang.Boolean.TRUE;
//
///**
// * @author Chenchu Kiran.
// */
//@Service
//public class MailSendGridService {
//    private static final Logger LOGGER = LoggerFactory.getLogger(MailSendGridService.class);
//
//    private final SendGrid sendGrid;
//    private final MessageSource messageSource;
//    private final SpringTemplateEngine templateEngine;
//    private final IndependentService independentService;
//
//    private String baseUrl = "http://127.0.0.1:8181";
//    private final String appContactMail;
//    private final String signature;
//    private final String fromMail1;
//    private final String fromMail2;
//
//    @Autowired
//    public MailSendGridService(SendGrid sendGrid,
//                               MessageSource messageSource, SpringTemplateEngine templateEngine,
//                               IndependentService independentService,
//                               @Value("${spring.mail.app-contact-mail}") String appContactMail,
//                               @Value("${spring.mail.mail-signature}") String signature,
//                               @Value("${spring.mail.username}") String fromMail1,
//                               @Value("${spring.mail.from}") String fromMail2) {
//        this.sendGrid = sendGrid;
//        this.messageSource = messageSource;
//        this.templateEngine = templateEngine;
//        this.independentService = independentService;
//        this.appContactMail = appContactMail;
//        this.signature = signature;
//        this.fromMail1 = fromMail1;
//        this.fromMail2 = fromMail2;
//    }
//
//    private Boolean sendEmail(String[] toEmails, String[] bccEmails, String[] ccEmails, String subject, String content, boolean isMultipart, boolean isHtml) {
//        Mail mail = new Mail();
//        Personalization personalization = new Personalization();
////        mail.setFrom(new Email("kiran.elupuru@pragmaedge.com"));
//        mail.setFrom(new Email(fromMail1.endsWith(".com") ? fromMail1 : fromMail2));
//        mail.setSubject(subject);
//        Arrays.stream(formatEmails(toEmails)).forEach(toEmail -> personalization.addTo(new Email(toEmail)));
//        if (bccEmails != null && bccEmails.length >= 1) {
//            Arrays.stream(formatEmails(bccEmails)).forEach(bccEmail -> personalization.addBcc(new Email(bccEmail)));
//        }
//        if (ccEmails != null && ccEmails.length >= 1) {
//            Arrays.stream(formatEmails(ccEmails)).forEach(ccEmail -> personalization.addCc(new Email(ccEmail)));
//        }
//        mail.addPersonalization(personalization);
//        mail.addContent(new Content(isHtml ? "text/html" : "text/plain", content));
//
//        String profile = independentService.getActiveProfile();
//        if (profile.equals("pcm") || profile.equals("saml")) {
//            mail.setReplyTo(new Email("cm-noreply@noreply.com"));
//        } else {
//            mail.setReplyTo(new Email("pem-noreply@noreply.com"));
//        }
//        Request request = new Request();
//        try {
//            request.setMethod(Method.POST);
//            request.setEndpoint("mail/send");
//            request.setBody(mail.build());
//            Response re = sendGrid.api(request);
//            LOGGER.info("Mail send Response Code {}", re.getStatusCode());
//            LOGGER.info("Mail send Response Body {}", re.getBody());
//            LOGGER.info("Mail send Response Headers {}", re.getHeaders());
//        } catch (IOException ex) {
//            LOGGER.error("Error : ", ex);
//            return FALSE;
//        }
//        return TRUE;
//    }
//
//    @Async
//    public void sendEmailFromTemplate(UserEntity user, String templateName, String titleKey) {
//        Context context = new Context();
//        context.setVariable(EmailFields.USER.getDisplayField(), user);
//        context.setVariable(EmailFields.BASE_URL.getDisplayField(), this.baseUrl);
//        context.setVariable(EmailFields.CONTACTMAIL.getDisplayField(), appContactMail);
//        context.setVariable(EmailFields.SIGNATURE.getDisplayField(), signature);
//        String content = templateEngine.process(templateName, context);
//        String subject = messageSource.getMessage(titleKey, null, Locale.ENGLISH);
//        String[] toId = {user.getEmail()};
//        String[] bccId = {};
//        String[] ccId = {};
//        sendEmail(toId, bccId, ccId, subject, content, false, true);
//    }
//
//    @Async
//    public void sendActivationEmail(UserEntity user, String baseUrl) {
//        LOGGER.debug("Sending activation email to : {}", user.getEmail());
//        this.baseUrl = baseUrl;
//        sendEmailFromTemplate(user, "mail/activationEmail", "email.activation.title");
//    }
//
//    public void testMail() {
//        UserEntity user = new UserEntity();
//        user.setActivationKey("gsfsretfshs");
//        user.setFirstName("Kiran Reddy");
//        user.setEmail("kiran.elupuru@pragmaedge.com");
//        sendEmailFromTemplate(user, "mail/activationEmail", "email.activation.title");
//    }
//
//    @Async
//    public void sendCreationEmail(UserEntity user) {
//        LOGGER.debug("Sending creation email to '{}'", user.getEmail());
//        sendEmailFromTemplate(user, "mail/creationEmail", "email.activation.title");
//    }
//
//    @Async
//    public void sendPasswordResetMail(UserEntity user) {
//        LOGGER.debug("Sending password reset email to '{}'", user.getEmail());
//        sendEmailFromTemplate(user, "mail/passwordResetEmail", "email.reset.title");
//    }
//
//    @Async
//    public void sendPasswordMail(UserEntity user) {
//        LOGGER.debug("Sending password email to '{}'", user.getEmail());
//        sendEmailFromTemplate(user, "mail/notifyPassword", "email.password.title");
//    }
//
//    @Async
//    public void sendOtpMail(UserEntity user, String otp) {
//        user.setOtp(otp);
//        LOGGER.debug("Sending OTP to {}", user.getEmail());
//        sendEmailFromTemplate(user, "mail/otpEmail", "email.otp.title");
//    }
//
//    public void sendPemMail(PemEmailModel pemEmailModel) {
//        LOGGER.debug("Sending password email to : {}", pemEmailModel.getToMails());
//        sendEmailFromTemplateWithHub(pemEmailModel);
//    }
//
//    private void sendEmailFromTemplateWithHub(PemEmailModel pemEmailModel) {
//        List<String> toMailsList = new ArrayList<>();
//        List<String> bccMailsList = new ArrayList<>();
//        List<String> ccMailsList = new ArrayList<>();
//        if (pemEmailModel.isNormalMail()) {
//            toMailsList.addAll(Stream.of(pemEmailModel.getNormalToMails().split("[;,]"))
//                    .collect(Collectors.toCollection(ArrayList::new)));
//            if (isNotNull(pemEmailModel.getNormalBccMails())) {
//                bccMailsList.add(pemEmailModel.getNormalBccMails());
//            }
//            if (isNotNull(pemEmailModel.getNormalCcMails())) {
//                ccMailsList.add(pemEmailModel.getNormalCcMails());
//            }
//        } else {
//            toMailsList.addAll(pemEmailModel.getToMails().stream().map(CommunityManagerNameModel::getName).collect(Collectors.toList()));
//            bccMailsList.addAll(pemEmailModel.getBccMails().stream().map(CommunityManagerNameModel::getName).collect(Collectors.toList()));
//            ccMailsList.addAll(pemEmailModel.getCcMails().stream().map(CommunityManagerNameModel::getName).collect(Collectors.toList()));
//        }
//        String[] toMails = toMailsList.stream().map(String::trim).toArray(String[]::new);
//        String[] bccMails = bccMailsList.stream().map(String::trim).toArray(String[]::new);
//        String[] ccMails = ccMailsList.stream().map(String::trim).toArray(String[]::new);
//
//        //String[] toMails = pemEmailModel.getToMails().stream().map(CommunityManagerNameModel::getName).toArray(String[]::new)
//
//        Context context = new Context();
//        context.setVariable(EmailFields.USER.getDisplayField(), pemEmailModel.getUserName());
//        context.setVariable(EmailFields.PASSWORD.getDisplayField(), pemEmailModel.getPassword());
//        context.setVariable(EmailFields.PORT.getDisplayField(), pemEmailModel.getPort());
//        context.setVariable(EmailFields.HOST.getDisplayField(), pemEmailModel.getHost());
//        context.setVariable(EmailFields.SUBJECT.getDisplayField(), pemEmailModel.getSubject());
//        context.setVariable(EmailFields.DESC.getDisplayField(), pemEmailModel.getDesc());
//        context.setVariable(EmailFields.NOTE.getDisplayField(), pemEmailModel.getNote());
//        String content = templateEngine.process("mail/hubDetails", context);
//        if (pemEmailModel.isNormalMail()) {
//            if (bccMailsList.isEmpty()) {
//                if (!sendEmail(toMails, bccMails, ccMails, pemEmailModel.getSubject(), content, false, true)) {
//                    throw GlobalExceptionHandler.internalServerError("Unable to send Mail, Please try again after some time.");
//                }
//                return;
//            }
//            CommonFunctions.getPartitions(100, bccMailsList).forEach(bccList -> {
//                if (!sendEmail(toMails, bccList.stream().map(String::trim).toArray(String[]::new), ccMails, pemEmailModel.getSubject(), content, false, true)) {
//                    throw GlobalExceptionHandler.internalServerError("Unable to send Mail, Please try again after some time.");
//                }
//            });
//            return;
//        }
//
//        if (!sendEmail(toMails, bccMails, ccMails, pemEmailModel.getSubject(), content, false, true)) {
//            throw GlobalExceptionHandler.internalServerError("Unable to send Mail, Please try again after some time.");
//        }
//
//    }
//
//
//    private String[] formatEmails(String[] emails) {
//        return Arrays.stream(emails).filter(email -> !email.isEmpty()).filter(email -> email.matches(REGEX_EMAIL)).toArray(String[]::new);
//    }
//
//}
