package com.secl.eservice.master.email.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.secl.eservice.handler.AppException;
import com.secl.eservice.master.email.request.SendEmailRequest;
import com.secl.eservice.master.email.response.SendEmailResponse;
import com.secl.eservice.model.AuthUser;
import com.secl.eservice.service.BaseService;
import com.secl.eservice.util.constant.Code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("masterEmailV1SendEmailService")
public class SendEmailService extends BaseService<SendEmailRequest, SendEmailResponse> {


	@Override
	public SendEmailResponse perform(AuthUser user, String url, String version, SendEmailRequest request, SendEmailResponse response) throws AppException {
		try {
			log.info("email count - {}");
			sendEmail(user, request);
	//			response.getBody().setData(emailList);
		} catch (Exception e) {
			throw new AppException(request.getHeader(), Code.C_500.get(), e.getMessage());
		}
		return response;
	}
	
	@Autowired
    private JavaMailSender javaMailSender;
	
	void sendEmail(AuthUser user, SendEmailRequest request) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(request.getBody().getSendMail().getToAddress());

        msg.setSubject(request.getBody().getSendMail().getSubject());
        msg.setText(request.getBody().getSendMail().getBody());

        javaMailSender.send(msg);

    }

}
