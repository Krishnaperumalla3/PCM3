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

package com.pe.pcm.mail;

import com.pe.pcm.common.CommunityManagerNameModel;
import com.pe.pcm.enums.EmailFields;
import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.miscellaneous.IndependentService;
import com.pe.pcm.pem.PemEmailModel;
import com.pe.pcm.user.entity.UserEntity;
import com.pe.pcm.utils.CommonFunctions;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.annotation.PostConstruct;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.pe.pcm.utils.CommonFunctions.isNotNull;
import static com.pe.pcm.utils.PCMConstants.REGEX_EMAIL;

/**
 * Service for sending emails.
 * <p>
 * We use the @Async annotation to send emails asynchronously.
 *
 * @author Kiran Reddy.
 */

@Service
public class MailService {

    private final Logger log = LoggerFactory.getLogger(MailService.class);

    private final JavaMailSender javaMailSender;
    private final MessageSource messageSource;
    private final SpringTemplateEngine templateEngine;
    private final IndependentService independentService;

    private String baseUrl = "http://127.0.0.1:8181";
    private final String appContactMail;
    private final String signature;
    private String appName;
    private final String fromMail1;
    private final String fromMail2;

    @Autowired
    public MailService(JavaMailSender javaMailSender,
                       MessageSource messageSource, SpringTemplateEngine templateEngine, IndependentService independentService,
                       @Value("${spring.mail.app-contact-mail}") String appContactMail, @Value("${spring.mail.mail-signature}") String signature,
                       @Value("${server.serverHeader}") String appName, @Value("${spring.mail.username}") String fromMail1,
                       @Value("${spring.mail.from}") String fromMail2) {

        this.javaMailSender = javaMailSender;
        this.messageSource = messageSource;
        this.templateEngine = templateEngine;
        this.independentService = independentService;
        this.appContactMail = appContactMail;
        this.signature = signature;
        this.appName = appName;
        this.fromMail1 = fromMail1;
        this.fromMail2 = fromMail2;
    }

    //@Async
    private boolean sendEmail(String[] toEmails, String[] bccEmails, String[] ccEmails, String subject, String content,
                              boolean isMultipart, boolean isHtml, PemEmailModel pemEmailModel) {
        log.debug("Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
                isMultipart, isHtml, toEmails, subject, content);
        /*Prepare message using a Spring helper*/
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());

            message.setTo(formatEmails(toEmails));
            if (bccEmails != null && bccEmails.length >= 1) {
                message.setBcc(formatEmails(bccEmails));
            }
            if (ccEmails != null && ccEmails.length >= 1) {
                message.setCc(formatEmails(ccEmails));
            }

            message.setFrom(fromMail1.endsWith(".com") ? fromMail1 : fromMail2);

            String profile = independentService.getActiveProfile();
            if (profile.equals("pcm") || profile.equals("saml")) {
                // message.setFrom(fromMail1.endsWith(".com") ? fromMail1 : "pcm_notifications-noreply@pragmaedge.com")
                message.setReplyTo("cm-noreply@noreply.com");
            } else {
                //message.setFrom(fromMail1.endsWith(".com") ? fromMail1 : "pem_notifications-noreply@pragmaedge.com")
                message.setReplyTo("pem-noreply@noreply.com");
            }

            message.setSubject(subject);
            message.setText(content, isHtml);
            if (isNotNull(pemEmailModel.getWithAttachment()) && pemEmailModel.getWithAttachment()) {
                File tmpFile = File.createTempFile(pemEmailModel.getAttachmentName(), pemEmailModel.getAttachmentExtension());
                try (FileWriter writer = new FileWriter(tmpFile)) {
                    writer.write(new String(Base64.decodeBase64(pemEmailModel.getAttachmentContentBase64().getBytes(StandardCharsets.UTF_8))));
                }
                message.addAttachment(Objects.requireNonNull(tmpFile.getName()), tmpFile);
            }
            log.debug("start mail sending");
            javaMailSender.send(mimeMessage);
            log.debug("successfully sent mail");
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.warn("Email could not be sent to user '{}'", toEmails, e);
            } else {
                log.warn("Email could not be sent to user '{}': {}", toEmails, e.getMessage());
            }
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    //    @Async
    public void sendEmailFromTemplate(UserEntity user, String templateName, String titleKey) {
        Context context = new Context();
        context.setVariable(EmailFields.USER.getDisplayField(), user);
        context.setVariable(EmailFields.BASE_URL.getDisplayField(), this.baseUrl);
        context.setVariable(EmailFields.CONTACTMAIL.getDisplayField(), appContactMail);
        context.setVariable(EmailFields.SIGNATURE.getDisplayField(), signature);
        context.setVariable(EmailFields.APP_NAME.getDisplayField(), appName);
        String content = templateEngine.process(templateName, context);
        Object[] ob = new Object[]{appName};
        String subject = messageSource.getMessage(titleKey, ob, Locale.ENGLISH);
        String[] toId = {user.getEmail()};
        String[] bccId = {};
        String[] ccId = {};
        sendEmail(toId, bccId, ccId, subject, content, false, true, new PemEmailModel());
    }

    @Async
    public void sendActivationEmail(UserEntity user, String baseUrl) {
        log.debug("Sending activation email to : {}", user.getEmail());
        this.baseUrl = baseUrl;
        sendEmailFromTemplate(user, "mail/activationEmail", "email.activation.title");
    }

    @Async
    public void sendCreationEmail(UserEntity user) {
        log.debug("Sending creation email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/creationEmail", "email.activation.title");
    }

    //    @Async
    public void sendPasswordResetMail(UserEntity user) {
        log.debug("Sending password reset email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/passwordResetEmail", "email.reset.title");
    }

    //    @Async
    public void sendPasswordMail(UserEntity user) {
        log.debug("Sending password email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/notifyPassword", "email.password.title");
    }

    //    @Async
    public void sendOtpMail(UserEntity user, String otp) {
        user.setOtp(otp);
        log.debug("Sending OTP to {}", user.getEmail());
        sendEmailFromTemplate(user, "mail/otpEmail", "email.otp.title");
    }

    public void sendPemMail(PemEmailModel pemEmailModel) {
        log.debug("Sending password email to : {}", pemEmailModel.getToMails());
        sendEmailFromTemplateWithHub(pemEmailModel);
    }

    private void sendEmailFromTemplateWithHub(PemEmailModel pemEmailModel) {
        List<String> toMailsList = new ArrayList<>();
        List<String> bccMailsList = new ArrayList<>();
        List<String> ccMailsList = new ArrayList<>();
        if (pemEmailModel.isNormalMail()) {
            toMailsList.addAll(Stream.of(pemEmailModel.getNormalToMails().split("[;,]"))
                    .collect(Collectors.toCollection(ArrayList::new)));
            if (isNotNull(pemEmailModel.getNormalBccMails())) {
                bccMailsList.add(pemEmailModel.getNormalBccMails());
            }
            if (isNotNull(pemEmailModel.getNormalCcMails())) {
                ccMailsList.add(pemEmailModel.getNormalCcMails());
            }
        } else {
            toMailsList.addAll(pemEmailModel.getToMails().stream().map(CommunityManagerNameModel::getName).collect(Collectors.toList()));
            bccMailsList.addAll(pemEmailModel.getBccMails().stream().map(CommunityManagerNameModel::getName).collect(Collectors.toList()));
            ccMailsList.addAll(pemEmailModel.getCcMails().stream().map(CommunityManagerNameModel::getName).collect(Collectors.toList()));
        }
        String[] toMails = toMailsList.stream().map(String::trim).toArray(String[]::new);
        String[] bccMails = bccMailsList.stream().map(String::trim).toArray(String[]::new);
        String[] ccMails = ccMailsList.stream().map(String::trim).toArray(String[]::new);

        //String[] toMails = pemEmailModel.getToMails().stream().map(CommunityManagerNameModel::getName).toArray(String[]::new)

        Context context = new Context();
        context.setVariable(EmailFields.USER.getDisplayField(), pemEmailModel.getUserName());
        context.setVariable(EmailFields.PASSWORD.getDisplayField(), pemEmailModel.getPassword());
        context.setVariable(EmailFields.PORT.getDisplayField(), pemEmailModel.getPort());
        context.setVariable(EmailFields.HOST.getDisplayField(), pemEmailModel.getHost());
        context.setVariable(EmailFields.SUBJECT.getDisplayField(), pemEmailModel.getSubject());
        context.setVariable(EmailFields.DESC.getDisplayField(), pemEmailModel.getDesc());
        context.setVariable(EmailFields.NOTE.getDisplayField(), pemEmailModel.getNote());
        String content = templateEngine.process("mail/hubDetails", context);
        //String subject = messageSource.getMessage("email.hub.title", null, Locale.ENGLISH)
        // log.debug(content.toString()+emails.get(0).toString()+emails.get(1).toString()+emails.get(2).toString())
        if (pemEmailModel.isNormalMail()) {
            if (bccMailsList.isEmpty()) {
                if (!sendEmail(toMails, bccMails, ccMails, pemEmailModel.getSubject(), content, false,
                        true, pemEmailModel)) {
                    throw GlobalExceptionHandler.internalServerError("Unable to send Mail, Please try again after some time.");
                }
                return;
            }
            CommonFunctions.getPartitions(100, bccMailsList).forEach(bccList -> {
                if (!sendEmail(toMails, bccList.stream().map(String::trim).toArray(String[]::new), ccMails,
                        pemEmailModel.getSubject(), content, false, true,
                        pemEmailModel)) {
                    throw GlobalExceptionHandler.internalServerError("Unable to send Mail, Please try again after some time.");
                }
            });
            return;
        }

        if (!sendEmail(toMails, bccMails, ccMails, pemEmailModel.getSubject(), content, false, true,
                pemEmailModel)) {
            throw GlobalExceptionHandler.internalServerError("Unable to send Mail, Please try again after some time.");
        }
    }

    private String[] formatEmails(String[] emails) {
        return Arrays.stream(emails).filter(email -> !email.isEmpty()).filter(email -> email.matches(REGEX_EMAIL)).toArray(String[]::new);
    }

    @PostConstruct
    public void doRun() {
        if (!isNotNull(appName)) {
            appName = "IBM Partner Engagement Manager Community Manager";
        }
    }

}
